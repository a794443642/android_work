package com.example.servicetest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

public class MyService extends Service {
    private int count=0;
    private boolean flag;
    class Mybinder extends Binder{
        public int getInt(){
            return count;
        }
    }
    private Mybinder binder=new Mybinder();
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("this service is binded");
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.flag=true;
        System.out.println("this setvice is Destroyed");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("this service is statred");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("启动一个线程生产随机数");
        new Thread(){
            @Override
            public void run() {
                while(!flag){
                    try{
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    MyService.this.count= (int) (Math.random()*100);
                    System.out.println(count);
                }
            }
        }.start();
    }
}
