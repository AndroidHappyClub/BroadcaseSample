package com.github.androidhappyclub.broadcasesample

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ave.vastgui.tools.view.toast.SimpleToast.showLongMsg
import com.ave.vastgui.tools.viewbinding.viewBinding
import com.github.androidhappyclub.broadcasesample.databinding.ActivityThreadHandlerBinding
import kotlin.math.sqrt

class ThreadHandlerActivity : AppCompatActivity(R.layout.activity_thread_handler) {

    private lateinit var thread: PrimeThread
    private val binding by viewBinding(ActivityThreadHandlerBinding::bind)

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val btnPrime = findViewById<Button>(R.id.btnPrime)
        btnPrime.setOnClickListener {
            // 创建消息
            val msg = Message().apply {
                what = 0x123
                data = Bundle().apply {
                    putInt(Max, binding.edContent.text.toString().toInt())
                }
            }

            // 向新线程中的Handler发送消息
            thread.sendMessage(msg)
        }
        thread = PrimeThread()
        // 启动新线程
        thread.start()
    }

    // 定义一个线程类
    private class PrimeThread : Thread() {
        private var _handler: Handler? = null

        override fun run() {
            Looper.prepare()
            _handler = Handler(Looper.getMainLooper()) { msg: Message ->
                if (msg.what == 0x123) {
                    val max = msg.data.getInt(Max)
                    val primes: MutableList<Int> = ArrayList()
                    // 计算从2开始、到upper的所有质数
                    outer@ for (i in 2..max) {
                        // 用i处于从2开始、到i的平方根的所有数
                        var j = 2
                        while (j <= sqrt(i.toDouble())) {
                            // 如果可以整除，表明这个数不是质数
                            if (i != 2 && i % j == 0) {
                                continue@outer
                            }
                            j++
                        }
                        primes.add(i)
                    }
                    // 使用Toast显示统计出来的所有质数
                    showLongMsg(primes.toString())
                }
                true
            }
            Looper.loop()
        }

        fun sendMessage(msg: Message) {
            _handler?.sendMessage(msg)
        }
    }

    companion object {
        private const val Max = "Max"
    }

}