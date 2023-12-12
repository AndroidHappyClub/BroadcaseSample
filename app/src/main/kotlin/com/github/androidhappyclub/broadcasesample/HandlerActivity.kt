package com.github.androidhappyclub.broadcasesample

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.ave.vastgui.tools.utils.permission.requestPermission
import com.ave.vastgui.tools.viewbinding.viewBinding
import com.github.androidhappyclub.broadcasesample.databinding.ActivityHandlerBinding
import com.github.androidhappyclub.broadcasesample.log.mLogFactory
import java.net.URL

class HandlerActivity : AppCompatActivity(R.layout.activity_handler) {

    private val binding by viewBinding(ActivityHandlerBinding::bind)
    private val logger = mLogFactory.getLog(HandlerActivity::class.java)

    // 代表从网络下载得到的图片
    private var bitmap: Bitmap? = null

    private val handler = Handler(Looper.getMainLooper()) { msg ->
        if (msg.what == 0x123) // 如果该消息是本程序发的
        {
            if (bitmap == null) {
                binding.ivShow.setImageResource(R.mipmap.ic_page_failed)
            } else {
                // 使用ImageView显示该图片
                binding.ivShow.setImageBitmap(bitmap)
            }
            true
        } else {
            false
        }
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermission(Manifest.permission.INTERNET) {
            granted = {
                logger.i("权限 $it 已被授予")
            }
        }

        object : Thread() {
            override fun run() {
                try {
                    // 定义一个URL对象
                    val url = URL("https://avatars.githubusercontent.com/u/46998172?v=4")
                    val inputStream = url.openStream()
                    bitmap = BitmapFactory.decodeStream(inputStream)

                    // 发送消息、通知UI组件显示该图片
                    handler.sendEmptyMessage(0x123)
                    inputStream.close()
                } catch (exception: Exception) {
                    val msg = exception.message.toString()
                    logger.d(msg)
                    //出错也返回
                    handler.sendEmptyMessage(0x123)
                }
            }
        }.start()
    }

}