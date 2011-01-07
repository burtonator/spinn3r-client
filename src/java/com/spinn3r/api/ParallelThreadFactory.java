package com.spinn3r.api;

import java.util.concurrent.ThreadFactory;

public class ParallelThreadFactory implements ThreadFactory {
    
    private static ThreadGroup tg = new ThreadGroup( "spinn3r-parallel-client" );

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread( tg, r, "spinn3r-parallel-client-" + tg.activeCount() );
        t.setDaemon(true);
        return t;
        
    }

}
