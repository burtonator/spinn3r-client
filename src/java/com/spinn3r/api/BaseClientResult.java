package com.spinn3r.api;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.io.InputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;


public class BaseClientResult<ResultType> {

    /**
     * Go back in time to make sure we recrawl everything.
     */
    public static final int RESTART_BUFFER = 30 * 1000;

    private Config<ResultType> config = null;

    private String lastRequestURL = null;
    private String nextRequestURL = null;

    /**
     * Total time we took calling a method.
     */
    private long callDuration = -1;

    private long parseDuration = -1;

    protected List<ResultType> results = new ArrayList<ResultType>();


    /**
     * True if the last API call was compressed.
     */
    protected boolean isCompressed = false;

    /**
     * Get the last localInputStream we're using.  This is a
     * ByteArrayInputStream based InputStream.
     */
    private InputStream localInputStream = null;


    /**
     * Sample performance times...
     */
    private BandwidthSampler bs1   = new BandwidthSampler( 1L  * 60L * 1000L );
    private BandwidthSampler bs5   = new BandwidthSampler( 5L  * 60L * 1000L );
    private BandwidthSampler bs15  = new BandwidthSampler( 15L * 60L * 1000L );

    private boolean hasMoreResults        = true;
    private boolean hasMoreResultsHeadder = false;

    private int requestLimit = -1;

    public BaseClientResult ( Config<ResultType> config_value ) {
        config = config_value;
    }


    /**
     * Get the InputStream for dealing with the XML of the API directly.  This
     * is a LOCAL input stream so once fetch() has been called you can call this
     * API multiple times and you're reading from a local buffer.
     */
    public InputStream getInputStream() throws IOException {
        return getInputStream( true );
    }

    /**
     * Get the InputStream for dealing with the XML of the API directly.  This
     * is a LOCAL input stream so once fetch() has been called you can call this
     * API multiple times and you're reading from a local buffer.
     */
    public InputStream getInputStream( boolean decompress ) throws IOException {

        InputStream is = getLocalInputStream();

        //TODO: why do we need to reset?  I don't think we need reset and I
        //think this was added for gzip detection which we don't use.
        is.reset();
        
        //wrap the downloaded input stream with a gzip input stream when
        //necessary.
        if ( getIsCompressed() ) {

            //NOTE: this is a bug fix for Apache2.  If mod_compress is disabled
            //during LIVE http connections the result won't be compressed
            //content.  The GZIPInputStream class will first attempt to read the
            //gzip magic number in its constructor.  If the magic number is
            //incorrect then it will throw an exception.

            if ( decompress ) {

                try {
                    InputStream gz = new GZIPInputStream( is );
                    is = gz;
                } catch ( IOException e ) {

                    //TODO: are we going to add log4j support?
                    //log.warn( "Detected invalid gzip stream.  Using uncompressed stream.", e );
                    //reset since GZIPInputStream might have read some content
                    is.reset();
                }

            }
                
        }

        return is;

    }

    // **** Getter and setters **************************************************


    public BandwidthSampler getBs1 () {
        return bs1;
    }


    public BandwidthSampler getBs5 () {
        return bs5;
    }


    public BandwidthSampler getBs15 () {
        return bs15;
    }

    /**
     * Return the config used for this request.
     */
    public Config<ResultType> getConfig () {
        return config;
    }
    

    /**
     * 
     * Get the last requested URL for debug and logging purposes.
     *
     */
    public String getLastRequestURL() { 
        return this.lastRequestURL;
    }

    /**
     * 
     * Set the value of <code>lastRequestURL</code>.
     *
     */
    void setLastRequestURL( String lastRequestURL ) { 
        this.lastRequestURL = lastRequestURL;
    }

    /**
     * 
     * Get the value of <code>nextRequestURL</code>.
     *
     */
    public String getNextRequestURL() { 
        return this.nextRequestURL;
    }

    /**
     * 
     * Set the value of <code>nextRequestURL</code>.
     *
     */
    void setNextRequestURL( String next ) { 

        Config<ResultType> config = getConfig();

        //TODO: apply the correct hostname to the next request.

        if ( config.getHost() != null ) {
            String path = next.substring( next.indexOf( "/", "http://".length() ), next.length() );
            next = String.format( "http://%s%s", config.getHost(), path );
        }

        this.nextRequestURL = next;
    }

    /**
     * 
     * Get the value of <code>result</code>.
     *
     */
    public List<ResultType> getResults() { 
        return this.results;
    }

    void setResults( List<ResultType> results ) {
        this.results = results;
    }


    /**
     * 
     * Get the value of <code>callDuration</code>.
     *
     */
    public long getCallDuration() { 
        return this.callDuration;
    }

    /**
     * 
     * Set the value of <code>callDuration</code>.
     *
     */
    void setCallDuration( long callDuration ) { 
        this.callDuration = callDuration;
    }


    /**
     * 
     * Get the value of <code>parseDuration</code>.
     *
     */
    public long getParseDuration() { 
        return this.parseDuration;
    }

    /**
     * 
     * Set the value of <code>parseDuration</code>.
     *
     */
    void setParseDuration( long parseDuration ) { 
        this.parseDuration = parseDuration;
    }


    /**
     * When the API needs to shutdown you need to call this method FIRST and
     * persist it.  Then when the API starts you need to call config.setAfter()
     * with this value.
     */
    public Date getRestartPoint() {

        List<ResultType> results = getResults();

        if ( results == null || results.size() == 0 )
            return null;

        BaseResult item = (BaseResult)results.get( results.size() - 1 );

        return new Date( item.getPubDate().getTime() - RESTART_BUFFER );
        
    }


    /**
     * Return true if more results are available.
     *
     */
    public boolean getHasMoreResults() {
        return hasMoreResults;
    }


    /**
     * Set if more results are available.
     *
     */
    void setHasMoreResults( boolean value ) {
        hasMoreResults = value;
    }


    /**
     * Return true if more results are available.
     *
     */
    public boolean getHasMoreResultsHeadder() {
        return hasMoreResultsHeadder;
    }


    /**
     * Set if more results are available.
     *
     */
    void setHasMoreResultsHeadder( boolean value ) {
        hasMoreResultsHeadder = value;
    }

    /**
     * Return true if the localInputStreem is compressed.
     *
     */
    public boolean getIsCompressed() {
        return isCompressed;
    }


    /**
     * Set if the localInputStreem is compressed.
     *
     */
    void setIsCompressed( boolean value ) {
        isCompressed = value;
    }


    /**
     * Get the limit used to gennreat this result.
     */
    public int getRequestLimit () {
        return requestLimit;
    }


    void setRequestLimit ( int value ) {
        requestLimit = value;
    }


    private InputStream getLocalInputStream() {
        return localInputStream;
    }

    void setLocalInputStream( InputStream is ) {
        localInputStream = is;
    }




}