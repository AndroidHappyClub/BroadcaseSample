package com.github.androidhappyclub.broadcasesample.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.ave.vastgui.tools.utils.DateUtils.getCurrentTime

class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.provider.Telephony.SMS_RECEIVED") {
            val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            val sb = StringBuilder()
            for (message in messages) {
                val body = message.messageBody
                val address = message.originatingAddress
                sb.append("短信来自: $address \n")
                sb.append("短信内容: $body \n")
                sb.append("短信时间: ${getCurrentTime("yyyy-MM-dd HH:mm:ss")} \n")
            }
            _listener?.onReceived(sb.toString())
        }
    }

    fun setOnReceivedMessageListener(listener: IMessageListener?) {
        _listener = listener
    }

    // 回调接口
    interface IMessageListener {
        fun onReceived(message: String?)
    }

    companion object {
        private var _listener: IMessageListener? = null
    }

}