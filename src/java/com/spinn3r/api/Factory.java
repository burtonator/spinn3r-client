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
        protected TrackingTransactionManager getTrackingLogManager(SingleFileHistoryManager log) {
            return new TrackingTransactionManager(log);
        }

        @Provides
        protected SingleFileHistoryManager getSingleLogManager(OutputStream os) {
            return new SingleFileHistoryManager(os);
        }

        @Provides
        protected TransactionHistoryManager getLogManager(Provider<TrackingTransactionManager> provider) {
            return new RotatingFileManager(maxLogSize, provider);
        }

        @Provides
        public File getTempFile() throws IOException {
            return File.createTempFile("transaction", ".log", saveDirectory);
        }

        private final File saveDirectory;
        private final int maxLogSize;

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

    protected Injector getInjector(File saveDirectory, int maxLogSize) {
        Collection<Module> modules = new LinkedList<Module>();

        if (saveDirectory == null)
            modules.add(new NullLogModule());
        else
            modules.add(new LogManagerModule(saveDirectory, maxLogSize));

        return Guice.createInjector(modules);
    }

    protected Injector getInjector() {
        return getInjector(null, 0);
    }
}
