package com.example.addservicetest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private AddService.MyBinder binder;
    TextView textView;
    Intent intent;
    EditText text1;
    EditText text2;
    boolean flag=false;
    private double sum;
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("Service is connected");
            binder= (AddService.MyBinder) service;
            sum=binder.getSum();
            textView.setText(String.valueOf(sum));
            flag=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            flag=false;
            System.out.println("Service is disconnected");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         text1=findViewById(R.id.text1);
         text2=findViewById(R.id.text2);
        textView=findViewById(R.id.text3);
        Button button=findViewById(R.id.addbutton);
        intent=new Intent(MainActivity.this,AddService.class);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag){
                    unbindService(connection);
                }
                if(!text1.getText().toString().equals("")&&!text2.getText().toString().equals("")) {
                    intent.putExtra("cout1", text1.getText().toString());
                    intent.putExtra("cout2", text2.getText().toString());
                    startService(intent);
                    bindService(intent, connection, Service.BIND_AUTO_CREATE);
                }else {
                    textView.setText("出错");
                }
            }
        });


    }
}
