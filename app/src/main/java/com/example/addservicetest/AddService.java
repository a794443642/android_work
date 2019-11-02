package com.example.addservicetest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class AddService extends Service {
    private double cout1;
    private double cout2;
    private double sum;
    private MyBinder binder=new MyBinder();
    class MyBinder extends Binder {
        public double getSum(){
            sum=cout1+cout2;
            System.out.println(cout1+"  "+cout2+"   "+sum);
            return sum;
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("addService is started");
        this.cout1= Double.valueOf(intent.getExtras().getString("cout1"));
        this.cout2= Double.valueOf(intent.getExtras().getString("cout2"));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("addService is binded");
        return binder;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("addService is destroyed");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("addService is created");
    }
}
