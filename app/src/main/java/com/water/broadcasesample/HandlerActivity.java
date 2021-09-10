package com.water.broadcasesample;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.water.utilities.PermissionHelper;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HandlerActivity extends AppCompatActivity {
    //用于显示ImageView
    ImageView ivShow;
    // 代表从网络下载得到的图片
    Bitmap _bmp;
    private String[] _permissions = {
            Manifest.permission.INTERNET
    };

    Handler _handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) // 如果该消息是本程序发的
            {
                if(_bmp == null){
                    ivShow.setImageResource(R.mipmap.ic_page_failed);
                }else{
                    // 使用ImageView显示该图片
                    ivShow.setImageBitmap(_bmp);
                }
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        ivShow = (ImageView) findViewById(R.id.ivShow);
        PermissionHelper.checkPermission(this, _permissions);


        new Thread() {
            @Override
            public void run() {
                try {
                    // 定义一个URL对象
                    URL url = new URL("http://www.itshixun.com/qst/images/gxhz.jpg");
                    InputStream is = url.openStream();
                    _bmp = BitmapFactory.decodeStream(is);

                    // 发送消息、通知UI组件显示该图片
                    _handler.sendEmptyMessage(0x123);
                    is.close();
                } catch (Exception e) {
                    String msg = e.getMessage();
                    Log.d("HandlerActivity", msg);
                    //出错也返回
                    _handler.sendEmptyMessage(0x123);
                }
            }
        }.start();
    }
}
