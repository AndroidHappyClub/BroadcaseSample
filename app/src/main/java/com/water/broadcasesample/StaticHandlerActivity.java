package com.water.broadcasesample;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

public class StaticHandlerActivity extends AppCompatActivity {
    private TextView tvContent;
    static class StaticHandler extends Handler {
        private final WeakReference<StaticHandlerActivity> _wr;
        private int _index = 0;

        public StaticHandler(StaticHandlerActivity activity) {
            _wr = new WeakReference<StaticHandlerActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            try {
                if (msg.what == _what) {
                    _wr.get().setText(String.valueOf(_index++));
                }
            }catch (Exception ex)
            {
                Log.e("",ex.getMessage());
            }
            super.handleMessage(msg);
        }
    }

    private StaticHandler _handler = new StaticHandler(this);
    private static int _what = 0x123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_handler);
        tvContent = (TextView) findViewById(R.id.tvContent);

        // 使用定时器,每隔200毫秒让handler发送一个空信息
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                _handler.sendEmptyMessage(_what);
            }
        }, 0,200);
    }

    private void setText(String text)
    {
        tvContent.setText(text);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 避免activity销毁时，messageQueue中的消息未处理完；
        // 故此时应把对应的message给清除出队列
        // _handler.removeCallbacks(postRunnable);
        // 清除runnable对应的message
        _handler.removeMessages(_what);

    }
}
