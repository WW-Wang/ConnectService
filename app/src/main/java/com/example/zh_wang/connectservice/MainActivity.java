package com.example.zh_wang.connectservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ServiceConnection {

    private EditText editTextData;
    private TextView showTextView;
    private MyService.MyBinder mBinder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextData = (EditText) findViewById(R.id.et_data);
        showTextView = (TextView) findViewById(R.id.tv_show);

        findViewById(R.id.btn_start_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyService.class);
                intent.putExtra("data", editTextData.getText().toString());
                startService(intent);
            }
        });

        findViewById(R.id.btn_stop_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this, MyService.class));
            }
        });

        findViewById(R.id.btn_bind_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService(new Intent(MainActivity.this, MyService.class), MainActivity.this, Context.BIND_AUTO_CREATE);
            }
        });

        findViewById(R.id.btn_unbind_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(MainActivity.this);
            }
        });

        findViewById(R.id.btn_syn_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBinder != null) {
                    mBinder.setData(editTextData.getText().toString());
                }
            }
        });
    }

    // 绑定成功的时候执行 所以先运行服务 再 绑定
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mBinder = (MyService.MyBinder) service;
//        mBinder.getService().setCallBack(new CallBack() {
//            @Override
//            public void onDataChange(String string) {
//
//            }
//        });
        Log.i(MyService.TAG, "服务已经开启");
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
