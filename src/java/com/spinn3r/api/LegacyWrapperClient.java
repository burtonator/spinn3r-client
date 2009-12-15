package com.spinn3r.api;


import java.io.IOException;


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


}