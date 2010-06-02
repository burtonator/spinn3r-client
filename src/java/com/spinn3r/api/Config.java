/*
 * Copyright 2007-2009 Tailrank, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.spinn3r.api;

import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import org.w3c.dom.Element;

import com.spinn3r.api.protobuf.ContentApi;

/**
 * Used to startup the API and specify defaults for limits, where to start
 * indexing, tiers, language, etc.
 */
public abstract class Config <ResultType> implements Cloneable {

    /**
     * Default hostname for building the router URL.  This can be changed to
     * use an additional dedicated host if necessary.
     */
    public static String DEFAULT_HOST = "api.spinn3r.com";

    /**
     * When we've ran out of results (because the client is up to date) then we
     * should spin for a few seconds.  If the sleep interval is -1 then we sleep
     * for a random amount of time between 0 and 30 seconds.
     */
    public static final long DEFAULT_SLEEP_INTERVAL = 30L * 1000L;

    /**
     * Default number of results to fetch.
     *
     */
    public static int      DEFAULT_LIMIT       = 1000;

    /**
     * When fetching the API this specifies the default version to return.
     */
    public static String   DEFAULT_VERSION     = "3.2.02";


    public static enum Format {
    	RSS("rss"), PROTOBUF("protobuf"), PROTOSTREAM("protostream");
    	
    	private final String urlEntry;
    	
    	Format(String urlEntry) {
    		this.urlEntry = urlEntry;
    	}
    	
    	public String getURLEntry()
    	{
    		return urlEntry;
    	}
    }
    
    private String         filter              = null;
    private int            limit               = DEFAULT_LIMIT;
    //private String         lang                = null;
    private String         version             = DEFAULT_VERSION;
    private String         vendor              = null;
    private Date           after               = new Date(); /* use epoch as default */
    private String         firstRequestURL     = null;
    private String         nextRequestURL      = null;
    private boolean        skipDescription     = false;
    private String         api                 = null;
    private Format         format              = Format.RSS;
    private String         host                = DEFAULT_HOST;
    private boolean        disableParse        = false;
    private String         commandLine         = "";


    /**
     * Get the router URL for API calls.
     */
    abstract public String getRouter();


    /**
     * Factory methoud for a result object
     */
    abstract public ResultType createResultObject ( ContentApi.Entry entry   ) throws ParseException;

    abstract public ResultType createResultObject ( Element          current ) throws ParseException;


    @SuppressWarnings("unchecked")
    public Config<ResultType> clone () {

        Config<ResultType> res;

        try {
        	
            res = (Config<ResultType>)super.clone();
        }

        catch ( CloneNotSupportedException e ) {
            throw new UnsupportedOperationException ( e );
        }

        return res;
    }


    /**
     * Get the host for this request
     */
    public String getHost () {
        return this.host;
    }


    /**
     * Set the host for this request.
     */ 
    public void setHost ( String value ) {
        this.host = value;
    }


    /**
     * 
     * Get the value of <code>filter</code>.
     *
     */
    public String getFilter() { 
        return this.filter;
    }

    /**
     * 
     * Set the value of <code>filter</code>.
     *
     */
    public void setFilter( String filter ) { 
        this.filter = filter;
    }
    
    
    /**
     * Set the format for the server output
     * 
     * @param format
     */
    public void setFormat(Format format)
    {
    	this.format = format;
    }
    
    /**
     * Get the format of the server output
     * 
     * @return the format of the server output
     */
    public Format getFormat()
    {
    	return this.format; 
    }



    /**
     * 
     * Returns true if this configuration object is set to use protobuf.
     * 
     * @deprecated use getFormat instead
     *
     */
    @Deprecated
    public boolean getUseProtobuf() { 
        return this.format == Format.PROTOBUF;
    }

    /**
     * 
     * Set the value of <code>useProtobuf</code>.
     *
     * If <code>useProtobuf</code> is true, then get the
     * response in the protobuf format. If <code>useProtobuf</code>
     * is false, then get the reponse in rss format; 
     * 
     * @deprecated Use setFormat instead
     * 
     */
    @Deprecated
    public void setUseProtobuf( boolean useProtobuf ) { 
        if(useProtobuf)
        	setFormat(Format.PROTOBUF);
        else
        	setFormat(Format.RSS);
    }


    /**
     * Get the value of <code>disableParse</code>
     */
    public boolean getDisableParse() {
        return this.disableParse;
    }


    /**
     * Set the value of <code>disableParse</code>
     */
    public void setDisableParse( boolean disableParse ) {
        this.disableParse = disableParse;
    }


    /**
     * How long we should sleep if an API call doesn't return enough values.
     */
    private long sleepInterval = DEFAULT_SLEEP_INTERVAL;

    /**
     * 
     * Set the value of <code>firstRequestURL</code>.
     *
     */
    public void setFirstRequestURL( String firstRequestURL ) { 
        this.firstRequestURL = firstRequestURL;
    }

    /**
     * 
     * Get the value of <code>firstRequestURL</code>.
     *
     */
    public String getFirstRequestURL() { 
        return this.firstRequestURL;
    }

    /**
     * 
     * Set the value of <code>firstRequestURL</code>.
     *
     */
    public void setNextRequestURL( String nextRequestURL ) { 
        this.nextRequestURL = nextRequestURL;
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
     * Get the value of <code>after</code>.
     *
     */
    public Date getAfter() { 
        return this.after;
    }

    /**
     * 
     * Set the value of <code>after</code>.
     *
     */
    public void setAfter( Date after ) { 
        this.after = after;
    }
    
    /**
     * 
     * Specify the vendor for this call.  This MUST be specified or the client
     * will not work.
     *
     */
    public String getVendor() { 
        return this.vendor;
    }

    /**
     * 
     * Set the value of <code>vendor</code>.
     *
     */
    public void setVendor( String vendor ) { 
        this.vendor = vendor;
    }

    /**
     * 
     * Get the value of <code>version</code>.
     *
     */
    public String getVersion() { 
        return this.version;
    }

    /**
     * 
     * Set the value of <code>version</code>.
     *
     */
    public void setVersion( String version ) { 
        this.version = version;
    }

    /**
     * 
     * Set the value of <code>limit</code>.
     *
     * @deprecated This method no longer has any effect. 
     * Having Spinn3r select the optimal values has
     * turned out to be better for everyone involved and yielded much higher
     * performance.
     */
    public void setLimit( int limit ) {        
	}
    /**
     * 
     * Get the value of <code>limit</code>.
     *
     */
    public int getLimit() { 
        return this.limit;
    }

    public long getSleepInterval() {

        long result = sleepInterval;
        
        if ( result == -1 ) {
            //use a random number generator to compute the
            float f = new Random().nextFloat();
            
            result = (long)(f * 30L);

        }
        
        return result;
    }


    /**
     * Return the maximum number of request per call.  If the API has more
     * content this might cause an error on our end if the limit is more than 10
     * or so.
     */
    abstract int getMaxLimit();

    /**
     * Return the optimal limit for fetches. This is used to boost performance
     * in some situations.  We have to resort to the conservative limit if the
     * HTTP server can't handle the result set size.
     */
    abstract int getOptimalLimit();
    
    /**
     * Conservative limit for items which should work in all situations (but
     * might be slower)
     */
    abstract int getConservativeLimit();


    /**
     * 
     * Get the value of <code>skipDescription</code>.
     *
     */
    public boolean getSkipDescription() { 
        return this.skipDescription;
    }

    /**
     * 
     * Set the value of <code>skipDescription</code>.
     *
     */
    public void setSkipDescription( boolean skipDescription ) { 
        this.skipDescription = skipDescription;
    }

    /**
     * 
     * Get the value of <code>api</code>.
     *
     */
    public String getApi() { 
        return this.api;
    }

    /**
     * 
     * Set the value of <code>api</code>.
     *
     */
    public void setApi( String api ) { 
        this.api = api;
    }
    
    
    /**
     * Set the value of the command Line
     * 
     * @param commandLine
     */
    public void setCommandLine(String commandLine) {
        this.commandLine = commandLine;
    }
    
    /**
     * Get the command Line of the application
     * 
     * @return
     */
    public String getCommandLine() {
        return commandLine;
    }



    /**
     * Generate the first request URL based just on configuration directives.
     */
    public String generateFirstRequestURL( int request_limit ) {

        StringBuffer params = new StringBuffer( 1024 ) ;

        if ( request_limit > getMaxLimit() )
            request_limit = getMaxLimit();
        
        addParam( params, "limit",   request_limit );
        addParam( params, "vendor",  getVendor() );
        addParam( params, "version", getVersion() );

        String filter = getFilter();

        if ( filter != null ) {
            addParam( params, "filter", URLEncoder.encode( filter ) );
        }

        /*
        if ( getSpamProbability() != DEFAULT_SPAM_PROBABILITY )
            addParam( params, "spam_probability", getSpamProbability() );
        */
            
        //AFTER param needs to be added which should be ISO8601
        addParam( params, "after",   toISO8601( getAfter() ) );

        //add optional params
        //OBSOLETE/REMOVED
        //addParam( params, "lang", getLang(), true );

        /*
        if ( getTierStart() >= 0 ) {
            String param_tier = "" + getTierStart() + ":" + getTierEnd();
            addParam( params, "tier", param_tier );
        }
        */

        if ( getSkipDescription() ) {
            addParam( params, "skip_description", "true" );
        }

        String result = getRouter() + params.toString();
        
        return result;
        
    }


    /**
     * Return a date to an ISO 8601 value for specifying to the URL with an
     * 'after' param.
     */
    public static String toISO8601( Date date ) {

        TimeZone tz = TimeZone.getTimeZone( "UTC" );
        
        Calendar c = Calendar.getInstance( tz );

        c.setTime( date );
        c.setTimeZone( tz );
        
        StringBuffer buff = new StringBuffer();

        buff.append( c.get( Calendar.YEAR ) );
        buff.append( "-" );
        padd( c.get( Calendar.MONTH ) + 1, buff );
        buff.append( "-" );
        padd( c.get( Calendar.DAY_OF_MONTH ), buff );
        buff.append( "T" );
        padd( c.get( Calendar.HOUR_OF_DAY ), buff );
        buff.append( ":" );
        padd( c.get( Calendar.MINUTE ), buff );
        buff.append( ":" );
        padd( c.get( Calendar.SECOND ), buff );
        buff.append( "Z" );

        return buff.toString();
        
    }


    protected static void padd( int v, StringBuffer buff ) {
        if ( v < 10 )
            buff.append( "0" );

        buff.append( v );
        
    }

    // **** URL request handling ************************************************

    public static void addParam( StringBuffer buff,
                                 String name,
                                 Object value ) {
        addParam( buff, name, value, false );
    }

    public static void addParam( StringBuffer buff,
                                 String name,
                                 Object value,
                                 boolean optional ) {
        addParam( buff, name, value, false, false );
    }
    
    /**
     * Add a parameter to the first request URL.  After the first call this is
     * no longer needed. 
     */
    public static void addParam( StringBuffer buff,
                                 String name,
                                 Object value,
                                 boolean optional,
                                 boolean urlencode ) {

        if ( optional && value == null )
            return;             

        if ( value != null && urlencode )
            value = URLEncoder.encode( value.toString() );
        
        if ( buff.length() > 0 )
            buff.append( "&" );
        
        buff.append( name );
        buff.append( "=" );
        buff.append( value );

    }

}
