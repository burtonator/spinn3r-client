
package com.spinn3r.api.util;

import java.security.*;
import java.io.*;
import java.util.zip.*;

import com.jcraft.jzlib.*;


/**
 * 
 * TODO:
 * 
 * - How fast can this zlib engine compress across cores?  This is important
 * because if it's doing internal locking it will EASILY cause performance and
 * locking issues.
 *
 * - Support UTF-16 and UTF-32 for specific encodings
 *
 * - Support RAW binary storage instead of first base64 encoding the data.  We
 * should migrate to using this by enabling it in a new table and then back
 * porting this feature to other tables.
 *
 * - LZO
 * 
 * We should migrate this to full binary LZO.  It would make things REALLY tight
 * and fast.  I need to FULLY test it though.
 *
 * This library looks decent:
 * 
 * http://www.oberhumer.com/opensource/lzo/
 *
 * These numbers look REALLY nice:
 * 
 * http://www.mysqlperformanceblog.com/2008/06/05/how-would-you-compress-your-mysql-backup/
 * 
 * gzip     21MB/s  260MB   48 seconds
 * gzip(1)  37MB/s  320MB   27 seconds
 * LZO      97MB/s  390MB   10.5 seconds
 * LZO(1)   102MB/s 358MB   10 seconds
 *
 * So basically 2.7x faster and roughly the same output size as gzip.
 *
 * 
 * 
 */

/**
 * Facade which allows us to compress and decompress text easily and efficiently
 * from a central location with the correctly library and encodign support.
 * 
 * @author Kevin Burton
 * @version $Id: SHA1.java,v 1.9 2005/06/17 18:04:04 burton Exp $
 */
public class CompressedBLOB {

    public static boolean ENABLE_CACHED_BYTES = false;

    public static final int FORMAT_UNKNOWN      = 0;
    public static final int FORMAT_UTF8         = 1;
    public static final int FORMAT_BASE64_ZLIB  = 2;

    /**
     * UTF+ZLIB compression.
     *
     */
    public static final int FORMAT_ZLIB         = 4;

    /**
     * Specifies the default external format to use for new compressed data.
     */
    public static int EXTERNAL_FORMAT = FORMAT_BASE64_ZLIB;

    /**
     * Specify the default compression level.  See `level` for more information.
     *
     */
    public static int DEFAULT_LEVEL = 9;
    
    /**
     * Magic number for a RAW zlib binary stream.  Compression was about 3x
     * using this technique.
     */
    private static final byte[] MAGIC_ZLIB
        = new byte[] { 120, -38 };

    /**
     * Magic for Base64 encoded gzip data.  This was necessary when running on
     * MySQL 4.1.22 as it doesn't support binary data in replication.  We needed
     * to base64 encode this first BEFORE it could be stored. Note that this was
     * just a temporary fix.  Compression was about 2.3x using this technique.
     * 
     * See:
     *
     * http://feedblog.org/2007/09/24/is-mysql-binary-data-replication-broken/
     *
     * NOTE: the byte codes here are ascii for 0001
     * 
     */
    private static final byte[] MAGIC_BASE64_ZLIB
        = new byte[] { 0x30, 0x31 };

    /**
     * Simply a raw UTF8 string with no compression.
     *
     * NOTE: the byte codes here are ascii for 0002
     * 
     */
    private static final byte[] MAGIC_UTF8
        = new byte[] { 0x30, 0x32 };

    private static byte[][] MAGIC_LOOKUP = new byte[10][];

    static {
        MAGIC_LOOKUP[ FORMAT_ZLIB ]         = MAGIC_ZLIB;
        MAGIC_LOOKUP[ FORMAT_BASE64_ZLIB ]  = MAGIC_BASE64_ZLIB;
        MAGIC_LOOKUP[ FORMAT_UTF8 ]         = MAGIC_UTF8;
    }
    
    /**
     * Specifies the type of string encoding to use regardless of whether it's
     * compressed or not.  NOTE.  This has to be language specific encoding to
     * use UTF-16 where appropriate.
     */
    public static String ENCODING = "UTF-8";
    
    public static int DECOMPRESSION_RATIO = 5;
    public static int COMPRESSION_RATIO   = 3;

    public static int BUFFER_SIZE = 4096;

    /**
     * The internal content... 
     */
    protected String content = null;

    /**
     * compressed form of this content.
     */
    protected byte[] b = null;

    protected String lang = null;
    
    /**
     * Specify the default external format used for this content.
     *
     */
    protected int external_format = EXTERNAL_FORMAT;

    /**
     * Compression level to be used. Levels range from 0 (no compression) to 9
     * (best compression).
     */
    protected int level = DEFAULT_LEVEL;
    
    public CompressedBLOB( String v ) {
        this( EXTERNAL_FORMAT, v );
    }

    public CompressedBLOB( int external_format,
                           String v ) {
        this.content = v;
        this.external_format = external_format;
    }

    public CompressedBLOB( byte[] b ) {
        this.b = b;
    }

    /**
     * Set the lang of the target blog.  This is used as a hint to enable UTF-16
     * and potentially UTF-32 for language that are multi-byte.  This is a
     * future feature as we can't support this just yet.
     */
    public void setLang( String lang ) {
        this.lang = lang;
    }
    
    /**
     * Given the binary data, determine the external form, correctly parse it
     * and return the value as a String.
     */
    public String getContent() throws Exception {

        if ( content != null )
            return content;

        // we don't have ANYTHING to decompress so don't do any work becuase we
        // would just throw an exception.
        if ( b == null || b.length == 0 )
            return null;
        
        byte data[];

        int format = getExternalFormatIdentifier( b );

        //strip out the magic number
        if ( format != FORMAT_ZLIB ) {

            int width = MAGIC_ZLIB.length;
            byte[] tmp = new byte[ b.length - width ];
            System.arraycopy( b, width, tmp, 0, b.length - width );
            b = tmp;

        }
        
        //first base64 decode the content 
        if ( format == FORMAT_BASE64_ZLIB ) {
            b = Base64.decode( b );
        }

        if ( format == FORMAT_BASE64_ZLIB ||
             format == FORMAT_ZLIB ) {
            
            //now tmp should be filled with the correct data without the FORMAT
            //identifier.
            ByteArrayInputStream bis =  new ByteArrayInputStream( b );

            //now use an input stream that can decompress the data.
            InputStream cis = getCompressedInputStream( bis );
            
            //create a buffer to store the data. 
            ByteArrayOutputStream bos =
                new ByteArrayOutputStream( b.length * DECOMPRESSION_RATIO );
            
            byte[] buff = new byte[ BUFFER_SIZE ];
            int count;
            
            while( (count = cis.read( buff ) ) > 0 ) {
                
                //now write it to our temp byte array buffer
                bos.write( buff, 0, count );
                
            }
            
            cis.close();
            bos.close();
            
            data = bos.toByteArray();

        } else {
            data = b;
        } 

        return new String( data, ENCODING );
        
    }

    /**
     * Take the content we've been given and store it in the correct output
     * format.
     */
    public byte[] getBytes() throws Exception {

        if ( content == null )
            return new byte[ 0 ];

        if ( b != null )
            return b; /* FIXME: return the same as above? */
        
        // Get the content in UTF-8.  
        byte[] content_bytes = content.getBytes( ENCODING );
        byte[] b             = content_bytes;

        if ( external_format == FORMAT_BASE64_ZLIB ||
             external_format == FORMAT_ZLIB ) {
        
            //create a buffer to store the data. 
            ByteArrayOutputStream bos =
                new ByteArrayOutputStream( b.length / COMPRESSION_RATIO );
            
            //compress the content now.

            OutputStream cos = getCompressedOutputStream( bos );
            cos.write( b, 0, b.length );
            
            //close the streams 
            cos.close();
            bos.close();

            b = bos.toByteArray();

        }

        if ( external_format == FORMAT_BASE64_ZLIB ) 
            b = Base64.encodeAsByteArray( b );

        byte[] result = b;

        //add the magic number data
        byte[] magic = MAGIC_LOOKUP[ external_format ];
        
        //For SOME values of gzip+base64 the string might ACTUALLY be longer
        //than the original string.  In this case RESORT to using UTF8 style
        //encoding which is just the original string.
        if ( external_format == FORMAT_BASE64_ZLIB &&
             content_bytes.length < result.length ) {

            //just store it as RAW UTF8
            magic  = MAGIC_LOOKUP[ FORMAT_UTF8 ];
            result = content_bytes;
            
        }

        //the zlib format already has a magic number.
        if ( external_format != FORMAT_ZLIB ) {

            byte[] tmp = new byte[ magic.length + result.length ];

            System.arraycopy( magic,  0, tmp, 0,                 magic.length );
            System.arraycopy( result, 0, tmp, magic.length ,     result.length );

            result = tmp;
            
        } 

        if ( ENABLE_CACHED_BYTES ) 
            this.b = result;
        
        return result;
        
    }

    public String toString() {

        try {

            return getContent();

        }

        catch ( Exception t ) {
            throw new DecompressionException ( t );
        }
        
    }
    
    /**
     * Get the correct stream from the correctly underlying compression library.
     */
    public OutputStream getCompressedOutputStream( OutputStream os ) {

        //return new DeflaterOutputStream( os );
        
        return new ZOutputStream( os, level );
        
    }

    /**
     * Get the correct stream from the correctly underlying compression library.
     */
    public static InputStream getCompressedInputStream( InputStream is ) {

        //return new InflaterInputStream( is );

        return new ZInputStream( is );

    }

    /**
     * From a byte output stream return the external format revision it's using.
     */
    public static int getExternalFormatIdentifier( byte[] b ) {

        // NOTE: There are various more complex data structures to allow more
        // efficient lookups. I think the most efficient would be to create an
        // integer lookup table and bit shift each byte into a 32 bit integer.
        // The problem is that this would require a lot of memory on the client
        // to implement as a lookup. I could also potentially use four 8 bit
        // lookup arrays but I could only get this down to 8 instructions vs 12
        // at the maximum.  Either way it seems like a serious over optimization
        // considering all the UTF-8, base64, and content encoding this class
        // does.
        //
        // Furthermore, it seems to make enough sense to just put the most
        // probable identifier first in the comparison.

        if ( b == null || b.length < 2 )
            return EXTERNAL_FORMAT;

        if ( b[0] == MAGIC_BASE64_ZLIB[0] &&
             b[1] == MAGIC_BASE64_ZLIB[1] )      return FORMAT_BASE64_ZLIB;

        if ( b[0] == MAGIC_ZLIB[0] &&
             b[1] == MAGIC_ZLIB[1] )             return FORMAT_ZLIB;

        if ( b[0] == MAGIC_UTF8[0] &&
             b[1] == MAGIC_UTF8[1] )             return FORMAT_UTF8;

        return FORMAT_UNKNOWN;
        
    }

}