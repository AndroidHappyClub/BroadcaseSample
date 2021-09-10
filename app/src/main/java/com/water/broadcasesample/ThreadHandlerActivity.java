package com.water.broadcasesample;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ThreadHandlerActivity extends AppCompatActivity {
    private static final String Max = "Max";
    private EditText edContent;
    private Button btnPrime;
    private PrimeThread _thread;

    // 定义一个线程类
    class PrimeThread extends Thread
    {
        private Handler _handler;

        @Override
        public void run()
        {
            Looper.prepare();

            _handler = new Handler(){
                // 定义处理消息的方法
                @Override
                public void handleMessage(Message msg)
                {
                    if(msg.what == 0x123)
                    {
                        int max = msg.getData().getInt(Max);
                        List<Integer> primes = new ArrayList<Integer>();
                        // 计算从2开始、到upper的所有质数
                        outer:
                        for (int i = 2 ; i <= max ; i++)
                        {
                            // 用i处于从2开始、到i的平方根的所有数
                            for (int j = 2 ; j <= Math.sqrt(i) ; j++)
                            {
                                // 如果可以整除，表明这个数不是质数
                                if(i != 2 && i % j == 0)
                                {
                                    continue outer;
                                }
                            }
                            primes.add(i);
                        }
                        // 使用Toast显示统计出来的所有质数
                        Toast.makeText(ThreadHandlerActivity.this , primes.toString(),
                                Toast.LENGTH_LONG).show();
                    }
                }
            };

            Looper.loop();
        }

        public void sendMessage(Message msg)
        {
            _handler.sendMessage(msg);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_handler);
        edContent = (EditText)findViewById(R.id.edContent);
        btnPrime = (Button)findViewById(R.id.btnPrime);
        btnPrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 创建消息
                Message msg = new Message();
                msg.what = 0x123;
                Bundle bundle = new Bundle();
                bundle.putInt(Max , Integer.parseInt(edContent.getText().toString()));
                msg.setData(bundle);

                // 向新线程中的Handler发送消息
                _thread.sendMessage(msg);
            }
        });

        _thread = new PrimeThread();
        // 启动新线程
        _thread.start();
    }
}
