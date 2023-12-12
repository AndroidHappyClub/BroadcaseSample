package com.github.androidhappyclub.broadcasesample

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ave.vastgui.tools.view.extension.refreshWithInvalidate
import com.ave.vastgui.tools.viewbinding.viewBinding
import com.github.androidhappyclub.broadcasesample.databinding.ActivityAsyncTaskBinding
import com.github.androidhappyclub.broadcasesample.log.mLogFactory

class AsyncTaskActivity : AppCompatActivity(R.layout.activity_async_task) {

    private val binding by viewBinding(ActivityAsyncTaskBinding::bind)
    private val logger = mLogFactory.getLog(AsyncTaskActivity::class.java)

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnDownload.setOnClickListener { v: View? ->
            val dt = DownloadTask()
            dt.execute(100)
        }

    }

    private inner class DownloadTask : AsyncTask<Int, Int, String>() {

        override fun onPreExecute() {
            // 第一个执行方法
            super.onPreExecute()
            logger.i("下载任务开始")
        }

        override fun doInBackground(vararg params: Int?): String {
            // 第二个执行方法,onPreExecute()执行完后执行
            for (i in 0..100) {
                publishProgress(i)
                try {
                    Thread.sleep((params[0] ?: 0).toLong())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            return "执行完毕"
        }

        override fun onProgressUpdate(vararg progress: Int?) {
            binding.pbSchedule.refreshWithInvalidate {
                mCurrentProgress = progress[0]?.toFloat() ?: 0f
            }
            binding.tvTitle.text = "${progress[0]}%"
            super.onProgressUpdate(*progress)
        }

        override fun onPostExecute(result: String) {
            title = result
            super.onPostExecute(result)
        }
    }
}