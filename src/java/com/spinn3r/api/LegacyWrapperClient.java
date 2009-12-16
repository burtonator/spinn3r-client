package com.spinn3r.api;


import java.io.IOException;
import java.io.InputStream;
import org.w3c.dom.Document;

public abstract class LegacyWrapperClient <ResultType extends BaseResult> extends BaseClient<ResultType> {

    protected Config config = null;


    /**
     * 
     * Get the value of <code>config</code>.
     *
     */
    public Config getConfig() { 
        return this.config;
    }

    /**
     * 
     * Set the value of <code>config</code>.
     *
     */
    public void setConfig( Config config ) { 
        this.config = config;
    }


    public void fetch() throws IOException,
                               ParseException,
                               InterruptedException {
        super.fetch( config );
    }


    public InputStream getInputStream() throws IOException {
        return super.getInputStream( config );
    }


    public Document doXmlFetch ( String resource, Config config ) throws IOException,
                                                      ParseException,
                                                      InterruptedException {
        return doXmlFetch( getConnection( resource ).getInputStream(), config );
    }
        
}
