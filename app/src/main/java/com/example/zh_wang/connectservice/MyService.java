package com.example.zh_wang.connectservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

interface CallBack {
    void onDataChange(String string);
}

public class MyService extends Service {
    private boolean running = false;
    public final static String TAG = "SERVICE.TAG";
    private String data = "服务正在运行……";
    private CallBack callBack = null;

    public MyService() {
    }

    public CallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        running = true;

        new Thread(){
            @Override
            public void run() {
                super.run();

                int i = 0;

                while (running) {
                    i++;
                    Log.i(TAG, i+":"+data);

                    if (callBack != null) {
                        callBack.onDataChange(i+":"+data);
                    }

                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        data = intent.getStringExtra("data");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        running = false;
    }

    public class MyBinder extends android.os.Binder {

        public void setData(String data) {
            MyService.this.data = data;
        }

        public MyService getService() {
            return MyService.this;
        }
    }
}
