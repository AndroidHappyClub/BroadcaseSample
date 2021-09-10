package com.water.broadcasesample;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AsyncTaskActivity extends AppCompatActivity {
    private Button btnDownload;
    private ProgressBar pbSchedule;
    private TextView tvTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        pbSchedule= findViewById(R.id.pbSchedule);
        tvTitle= findViewById(R.id.tvTitle);

        btnDownload = findViewById(R.id.btnDownload);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadTask dt = new DownloadTask();
                dt.execute(100);
            }
        });
    }

    private class DownloadTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected void onPreExecute() {
            //第一个执行方法
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... params) {
            //第二个执行方法,onPreExecute()执行完后执行
            for(int i=0;i<=100;i++){
                publishProgress(i);

                try {
                    Thread.sleep(params[0]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "执行完毕";
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            pbSchedule.setProgress(progress[0]);
            tvTitle.setText(progress[0]+"%");
            super.onProgressUpdate(progress);
        }

        @Override
        protected void onPostExecute(String result) {
            setTitle(result);
            super.onPostExecute(result);
        }
    }
}
