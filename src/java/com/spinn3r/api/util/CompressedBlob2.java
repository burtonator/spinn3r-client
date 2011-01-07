package com.spinn3r.api.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Class which allows us to decompress a data byte array given a 
 * specified encoding scheme.
 * 
 * @author Kevin Burton
 * @version $Id: SHA1.java,v 1.9 2005/06/17 18:04:04 burton Exp $
 */
public class CompressedBlob2 
{
	/**
     * Specifies the type of string encoding to use regardless of whether it's
     * compressed or not.  NOTE.  This has to be language specific encoding to
     * use UTF-16 where appropriate.
     */
    public static final String ENCODING = "UTF-8";
    
    public static final int DECOMPRESSION_RATIO = 5;
    public static final int COMPRESSION_RATIO   = 3;

    public static final int BUFFER_SIZE = 4096;
    
	private byte[] data;
	private final String encoding;
	
	private String content = null;
	
	
	public CompressedBlob2(byte[] data, String encoding)
	{
		this.data = data;
		this.encoding = encoding;
	}
	
	/**
     * Given the binary data, determine the external form, correctly parse it
     * and return the value as a String.
	 * @throws IOException 
     */
    public String decompress() throws EncodingException, IOException  {

        if ( content != null )
            return content;

        // we don't have ANYTHING to decompress so don't do any work becuase we
        // would just throw an exception.
        if ( data == null || data.length == 0 )
            return null;

        byte[] b = data;
        
        //first base64 decode the content 
        if ( encoding.equals("zlib/base64") ) {
            b = Base64.decode( b );
        }

        if ( encoding.equals("zlib/base64") || encoding.equals("zlib") ) {
            
            //now tmp should be filled with the correct data without the FORMAT
            //identifier.
            ByteArrayInputStream bis =  new ByteArrayInputStream( b );

            //now use an input stream that can decompress the data.
            InputStream cis = CompressedBLOB.getCompressedInputStream( bis );
            
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
}
