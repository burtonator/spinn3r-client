package com.spinn3r.api;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class ParallelFetchHelper<ResultType extends BaseResult> {

    private static int  MAX_RETRY_COUNT   = 10;
    private static long QUEUE_WAIT_SLEEP        = 1 * 1000; // three seconds

    private BaseClient<ResultType> client;
    private Config<ResultType>     config;
    
    private LinkedBlockingQueue<Future<BaseClientResult<ResultType>>> outQueue;

    private ExecutorService workPool;
        
    public ParallelFetchHelper ( BaseClient<ResultType> client_value, Config<ResultType> config_value, int result_buffer_size, int parallelism ) {
        client = client_value;
        config = config_value.clone();
        
        outQueue = new LinkedBlockingQueue<Future<BaseClientResult<ResultType>>> ( result_buffer_size );
        workPool = Executors.newFixedThreadPool( parallelism );
    }


    public void start () throws InterruptedException {
        
        WorkUnit work = new WorkUnit( client, config, this, 0 );

        enqueue( work );
    }


    public BaseClientResult<ResultType> fetch () throws InterruptedException {

        BaseClientResult<ResultType> res = null;

        Future<BaseClientResult<ResultType>> result_future = outQueue.take();

        //BUG: there are failular cases to handle hear like the work did not compleat!!!


        try {
            res = result_future.get();
        }
        
        catch ( ExecutionException e ) {
            throw new RuntimeException ( e ); //BUG: should be better excption
        }

        return res;
    }


    private void enqueue ( WorkUnit work ) throws InterruptedException {

        Future<BaseClientResult<ResultType>> work_future = workPool.submit( work );
        
        outQueue.put( work_future );
    }


    private boolean canEnqueue () {
        return outQueue.remainingCapacity() > 0;
    }

    ////////////// support class ///////////////

    private class WorkUnit implements Callable<BaseClientResult<ResultType>> {


        private BaseClient<ResultType>          client;
        private Config<ResultType>              config;
        private ParallelFetchHelper<ResultType> helper;
        private long                            sleep;

        public WorkUnit (  BaseClient<ResultType> client_value, Config<ResultType> config_value, ParallelFetchHelper<ResultType> helper_value, long sleep_value ) {
            client = client_value;
            config = config_value;
            helper = helper_value;
            sleep  = sleep_value;
        }


        public BaseClientResult<ResultType> call () throws Exception {

            BaseClientResult<ResultType> result = null;

            int     retry_count   = 0;
            boolean enqueued_next = false;

            if ( sleep > 0 ) {
                System.out.printf("caught up sleeping for %s\n", sleep ); //BOOG
                Thread.sleep( sleep );
            }

            while ( true ) {

                if ( ! helper.canEnqueue() ) {
                    Thread.sleep( QUEUE_WAIT_SLEEP );
                    continue;
                }

                try {

                    PartailBaseClientResult<ResultType> partial_result = client.partialFetch( config );

                    if ( enqueued_next == false ) {
                        Config<ResultType> next_config = config.clone();

                        next_config.setNextRequestURL( partial_result.getNextRequestURL() );

                        long sleep = 0;

                        if ( ! partial_result.getHasMoreResults() )
                            sleep = next_config.getSleepInterval();

                        WorkUnit work = new WorkUnit( client, next_config, helper, sleep );

                        // BUG: one bad thing about this is that we could stall the connection hear
                        //      if the client stopes taking out results.
                        helper.enqueue( work );
                    }

                    enqueued_next = true;

                    result = client.compleatFetch( partial_result );

                    break;
                }

                catch ( Exception e ) {                    
                    // BUG: should log hear
                    if ( retry_count > MAX_RETRY_COUNT )
                        throw e;
                    else
                        continue;
                }

                finally {
                    retry_count++;
                }

            }

            return result;
        }

    }


}