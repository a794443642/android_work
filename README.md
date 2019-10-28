# android_work
## 这次实验时遇到的问题
### First Problem
android.content.res.Resources$NotFoundException: String resource ID #0x34
这个问题可能是可能是setText设置了int值
还有可能你的布局,里面是不是引用什么资源没找到
### Second Problem
使用onServiceConnected()方法时报空指针
原因调用bindService()，之后不能会立即回调onServiceConnected()
他们之间是远程调用，是异步操作
### third Proble
遇到更新ui的问题(子线程不能直接更新ui)<br>
这里有几种方式<br>
1 直接使用使用按钮点击事件重写点击方法-->setTexgitt<br>
2.通过Activity中的runOnUIThread方法<br>
   Instace:<br>
```java  
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_text.setText("111111111111111");
                            }
                        });

                    }
                }).start();
 
```
2.通过Handler的post方法(本实验使用方法)
Instace:<br>
```java
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
```
3.通过handler发送消息来实现子线程更新UI
Instace:<br>
```java
private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                 这里更新视图setText("111111111111111");
                    break;
            }
        }
    };
    
```
发送消息<br>
```java
                     Message message = Message.obtain();
                        message.what = 1;
                        handler.sendMessage(message);
```
### fourth Proble
线程循环操作<br>
1.使用Handler
```java
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
```
2.使用timer
```java
          timer =new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
               
               
               
            }
        },1000,1000);
```

