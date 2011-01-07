package com.spinn3r.api;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.LinkedList;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class Factory {
    
    /*
     * This module provides a rotating log manager when 
     */
    protected class LogManagerModule extends AbstractModule {

        public LogManagerModule(File saveDirectory, int maxLogSize) {
            super();
            this.saveDirectory = saveDirectory;
            this.maxLogSize = maxLogSize;
        }

        protected void configure() {
            bind(OutputStream.class).toProvider(TempStreamManager.class);
        }

        @Provides
        protected TransactionHistoryManager getLogManager(Provider<SingleFileHistoryManager> provider) {
            return new RotatingFileManager(maxLogSize, provider);
        }

        @Provides
        public File getTempFile() throws IOException {
            return File.createTempFile("transaction", ".log", saveDirectory);
        }

        private final File saveDirectory;
        private final int maxLogSize;

    }
    
    protected class FreshStartModule extends AbstractModule {
        
        @Override
        protected void configure() {
            bind(UniversalCounter.class).in(Singleton.class);
        }
        
        @Provides
        protected SingleFileHistoryManager getSingleLogManager(OutputStream os, UniversalCounter counter) {
            return new SingleFileHistoryManager(os, counter);
        }
    }
    
    protected class RestartModule extends AbstractModule {
        
        private final long counter;
        private final String url;
        
        protected RestartModule(long counter, String url) {
            this.counter= counter;
            this.url = url;
        }
        
        @Override
        protected void configure() {
            bind(UniversalCounter.class).toInstance(new UniversalCounter(counter));
        }
        
        @Provides
        protected SingleFileHistoryManager getSingleLogManager(OutputStream os, UniversalCounter counter) {
            SingleFileHistoryManager manager = new SingleFileHistoryManager(os, counter);
            manager.log(url);
            
            return manager;
        }
    }

    /*
     * This module provides a Null Log Manager.
     */
    protected class NullLogModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(TransactionHistoryManager.class).to(NullLogManager.class).in(Singleton.class);
        }
    }
    
    private File saveDirectory;
    private int maxLogSize;
    
    public void enableLogging(File saveDirectory, int maxLogSize) {
        this.saveDirectory = saveDirectory;
        this.maxLogSize = maxLogSize;
    }
    
    
    private long counter;
    private String restartUrl;
    public void enableRestart(long counter, String url) {
        this.counter = counter;
        this.restartUrl = url;
    }

    protected Injector getInjector() {
        Collection<Module> modules = new LinkedList<Module>();

        if (saveDirectory == null) {
            modules.add(new NullLogModule());
        }
        else {
            modules.add(new LogManagerModule(saveDirectory, maxLogSize));
            if (restartUrl == null) 
                modules.add(new FreshStartModule());
            else
                modules.add(new RestartModule(counter, restartUrl));
        }
        

        return Guice.createInjector(modules);
    }

}
