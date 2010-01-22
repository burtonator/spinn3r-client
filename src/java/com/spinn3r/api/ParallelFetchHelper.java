package com.spinn3r.api;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class ParallelFetchHelper<ResultType extends BaseResult> {


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
        
        WorkUnit work = new WorkUnit( client, config, this );

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


    ////////////// support class ///////////////

    private class WorkUnit implements Callable<BaseClientResult<ResultType>> {


        private BaseClient<ResultType>          client;
        private Config<ResultType>              config;
        private ParallelFetchHelper<ResultType> helper;

        public WorkUnit (  BaseClient<ResultType> client_value, Config<ResultType> config_value, ParallelFetchHelper<ResultType> helper_value ) {
            client = client_value;
            config = config_value;
            helper = helper_value;
        }


        public BaseClientResult<ResultType> call () throws Exception {

            PartailBaseClientResult<ResultType> partial_result = client.partialFetch( config );

            Config<ResultType> next_config = config.clone();

            next_config.setNextRequestURL( partial_result.getNextRequestURL() );

            WorkUnit work = new WorkUnit( client, next_config, helper );

            // BUG: one bad thing about this is that we could stall the connection hear
            //      if the client stopes taking out results.
            helper.enqueue( work );

            BaseClientResult<ResultType> result  = client.compleatFetch( partial_result );

            return result;
        }

    }


}