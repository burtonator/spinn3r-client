package com.spinn3r.api;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class ParallelFetchHelper<ResultType extends BaseResult> {

    private static int  MAX_RETRY_COUNT   = 10;
    private static long QUEUE_WAIT_SLEEP  = 1 * 1000; // three seconds

    private BaseClient<ResultType> client;
    private Config<ResultType>     config;
    
    private LinkedBlockingDeque<WorkWrapper<ResultType>> outQueue;


    private ExecutorService workPool;
        
    public ParallelFetchHelper ( BaseClient<ResultType> client_value, Config<ResultType> config_value, int result_buffer_size, int parallelism ) {
        client = client_value;
        config = config_value.clone();
        
        outQueue = new LinkedBlockingDeque<WorkWrapper<ResultType>> ( result_buffer_size );
        workPool = Executors.newFixedThreadPool( parallelism );
    }


    public void start () throws InterruptedException {
        
        WorkUnit<ResultType> work = new WorkUnit<ResultType>( client, config, this, 0 );

        enqueue( work );
    }


    public BaseClientResult<ResultType> fetch () throws InterruptedException {

        BaseClientResult<ResultType> res = null;

        WorkWrapper<ResultType> wrapped = outQueue.takeFirst();

        try {
            res = wrapped.future.get();
        }
        
        catch ( InterruptedException e ) {
            enqueue( wrapped.work, false );
            throw e;
        }

        catch ( ExecutionException e ) {
            enqueue( wrapped.work, false );
            throw new RuntimeException ( e ); //BUG: should be better excption
        }

        return res;
    }


    private void enqueue ( WorkUnit<ResultType> work ) throws InterruptedException {
        enqueue( work, true );
    }

    private void enqueue ( WorkUnit<ResultType> work, boolean put_last ) throws InterruptedException {

        Future<BaseClientResult<ResultType>> work_future = workPool.submit( work );
        
        WorkWrapper<ResultType> wrapped = new WorkWrapper<ResultType>( work, work_future );

        if ( put_last )
            outQueue.putLast( wrapped );
        else
            outQueue.putFirst( wrapped );

                              
    }


    private boolean canEnqueue () {
        return outQueue.remainingCapacity() > 0;
    }

    ////////////// support class ///////////////

    private static class WorkWrapper<WrapperResultType extends BaseResult> {

        WorkUnit<WrapperResultType>                  work;
        Future<BaseClientResult<WrapperResultType>>  future;

        public WorkWrapper ( WorkUnit<WrapperResultType> work_value,  Future<BaseClientResult<WrapperResultType>> future_value ) {
            work   = work_value;
            future = future_value;
        }
        

    }


    private static class WorkUnit<WorkResultType extends BaseResult> implements Callable<BaseClientResult<WorkResultType>> {


        private BaseClient<WorkResultType>          client;
        private Config<WorkResultType>              config;
        private ParallelFetchHelper<WorkResultType> helper;
        private long                            sleep;

        public WorkUnit (  BaseClient<WorkResultType> client_value, Config<WorkResultType> config_value, ParallelFetchHelper<WorkResultType> helper_value, long sleep_value ) {
            client = client_value;
            config = config_value;
            helper = helper_value;
            sleep  = sleep_value;
        }


        public BaseClientResult<WorkResultType> call () throws Exception {

            BaseClientResult<WorkResultType> result = null;

            int     retry_count   = 0;
            boolean enqueued_next = false;

            if ( sleep > 0 ) {
                //System.out.printf("caught up sleeping for %s\n", sleep ); //BOOG
                Thread.sleep( sleep );
            }

            while ( true ) {

                if ( ! helper.canEnqueue() ) {
                    Thread.sleep( QUEUE_WAIT_SLEEP );
                    continue;
                }

                try {

                    PartailBaseClientResult<WorkResultType> partial_result = client.partialFetch( config );

                    if ( enqueued_next == false ) {
                        Config<WorkResultType> next_config = config.clone();

                        next_config.setNextRequestURL( partial_result.getNextRequestURL() );

                        long sleep = 0;

                        if ( ! partial_result.getHasMoreResults() )
                            sleep = next_config.getSleepInterval();

                        WorkUnit<WorkResultType> work = new WorkUnit<WorkResultType>( client, next_config, helper, sleep );

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