package com.example.servicetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Handler handler=new Handler();
    private boolean isConnected = false;
    int cout;

    private MyService.Mybinder binder;
    private ServiceConnection conn =new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("service is connected");
            isConnected=true;
            binder= (MyService.Mybinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            System.out.println("service is disconnected");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent intent=new Intent(MainActivity.this,MyService.class);
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在这里bindService()，之后不能会立即回调onServiceConnected()
                        bindService(intent,conn, Service.BIND_AUTO_CREATE);
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected){
                    unbindService(conn);

                }

            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {

                        cout=binder.getInt();
                        TextView textView = findViewById(R.id.textview1);
                        textView.setText(String.valueOf(cout));
                        System.out.println("this is myactivity"+cout);
                        handler.postDelayed(this, 2000);
                    }

                };
                handler.post(runnable);
                handler.postDelayed(runnable,2000);
            }
        });
    }
}
