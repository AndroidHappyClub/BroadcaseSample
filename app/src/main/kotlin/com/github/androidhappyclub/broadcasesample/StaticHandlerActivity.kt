package com.github.androidhappyclub.broadcasesample

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import com.ave.vastgui.tools.viewbinding.viewBinding
import com.github.androidhappyclub.broadcasesample.databinding.ActivityStaticHandlerBinding
import com.github.androidhappyclub.broadcasesample.log.mLogFactory
import java.lang.ref.WeakReference
import java.util.Timer
import java.util.TimerTask

class StaticHandlerActivity : AppCompatActivity(R.layout.activity_static_handler) {

    private val handler = StaticHandler(this)
    private val binding by viewBinding(ActivityStaticHandlerBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 使用定时器,每隔200毫秒让handler发送一个空信息
        Timer().schedule(object : TimerTask() {
            override fun run() {
                handler.sendEmptyMessage(_what)
            }
        }, 0, 200)
    }

    private fun setText(text: String) {
        binding.tvContent.text = text
    }

    override fun onDestroy() {
        super.onDestroy()
        // 避免activity销毁时，messageQueue中的消息未处理完；
        // 故此时应把对应的message给清除出队列
        // _handler.removeCallbacks(postRunnable);
        // 清除runnable对应的message
        handler.removeMessages(_what)
    }

    private class StaticHandler(activity: StaticHandlerActivity) :
        Handler(Looper.getMainLooper()) {

        private val _wr: WeakReference<StaticHandlerActivity>
        private var _index = 0
        private val logger = mLogFactory.getLog(StaticHandler::class.java)

        init {
            _wr = WeakReference(activity)
        }

        override fun handleMessage(msg: Message) {
            try {
                if (msg.what == _what) {
                    _wr.get()?.setText("${_index++}")
                }
            } catch (ex: Exception) {
                logger.e(ex.message.toString())
            }
            super.handleMessage(msg)
        }
    }

    companion object {
        private const val _what = 0x123
    }

}