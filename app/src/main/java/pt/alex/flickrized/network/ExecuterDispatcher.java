package pt.alex.flickrized.network;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by nb19875 on 05/07/16.
 */
public class ExecuterDispatcher {

    private static ExecuterDispatcher instance;
    ThreadPoolExecutor executorPool;


    private ExecuterDispatcher() {

        executorPool = new ServiceExecuterPool(
                1
                , 5
                , 60
                , TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>()
        );
        executorPool.setRejectedExecutionHandler(new RejectedTaksHandler());
    }

    public static ExecuterDispatcher of() {
        if (instance == null) {
            instance = new ExecuterDispatcher();
        }
        return instance;
    }


    public void dispatch(Runnable r){
                executorPool.execute(r);
    }

    private static class ServiceExecuterPool extends ThreadPoolExecutor {
        public ServiceExecuterPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            super.beforeExecute(t, r);
            Log.i("ServiceExecuterPool" , "Number of used Threads:" + this.getActiveCount());
            Log.i("ServiceExecuterPool" , "Queue Size:" + this.getQueue().size());
            Log.i("ServiceExecuterPool" , "Executer before");
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);
            Log.i("ServiceExecuterPool" , "Executer after");
        }

        @Override
        protected void terminated() {
            super.terminated();
            Log.i("ServiceExecuterPool" , "Executer terminated!");
        }
    }

    private class RejectedTaksHandler implements RejectedExecutionHandler{

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            Log.i("RejectedTaksHandler" , "Executer rejected Task " + r.getClass().getCanonicalName());
        }

    }
}
