package com.github.androidhappyclub.broadcasesample

import android.Manifest
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ave.vastgui.tools.utils.permission.requestPermission
import com.ave.vastgui.tools.viewbinding.viewBinding
import com.github.androidhappyclub.broadcasesample.databinding.ActivitySmsBinding
import com.github.androidhappyclub.broadcasesample.log.mLogFactory
import com.github.androidhappyclub.broadcasesample.receiver.SmsReceiver
import com.github.androidhappyclub.broadcasesample.receiver.SmsReceiver.IMessageListener

class SmsActivity : AppCompatActivity(R.layout.activity_sms), IMessageListener {

    private val binding by viewBinding(ActivitySmsBinding::bind)
    private val logger = mLogFactory.getLog(SmsActivity::class.java)
    private val receiver = SmsReceiver().apply {
        setOnReceivedMessageListener(this@SmsActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermission(Manifest.permission.RECEIVE_SMS) {
            granted = {
                logger.d("权限 $it 已经被授予")
            }
        }

        registerReceiver(receiver, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    override fun onReceived(message: String?) {
        binding.tvSms.visibility = View.VISIBLE
        binding.tvSms.text = message
    }

}