package com.journalapp.u.alc;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class AppSwipeExecutors {
    private static AppSwipeExecutors mInstance;
    private final Executor diskInputOutput;
    private final Executor mainThread;
    private final Executor networkInputOutput;
    private static final Object LOCK=new Object();

    private AppSwipeExecutors(Executor diskInputOutput,Executor networkInputOutput, Executor mainThread){
       this.diskInputOutput=diskInputOutput;
       this.mainThread=mainThread;
       this.networkInputOutput=networkInputOutput;
    }

    public static AppSwipeExecutors getInstance(){
        if (mInstance==null){
            synchronized (LOCK){
                mInstance=new AppSwipeExecutors(Executors.newSingleThreadExecutor(),Executors.newFixedThreadPool(3),new MainThreadExecutor());
            }
        }
        return mInstance;
    }

    public Executor getDiskInputOutput() {
        return diskInputOutput;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadProcessHandler= new Handler(Looper.getMainLooper());
        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadProcessHandler.post(command);
        }
    }
    //Get functionality and use
    /*public Executor getMainThread() {
        return mainThread;
    }

    public Executor getNetworkInputOutput() {
        return networkInputOutput;
    }*/
}
