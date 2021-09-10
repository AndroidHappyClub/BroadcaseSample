package com.water.broadcasesample.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SmsReceiver extends BroadcastReceiver {
    private static IMessageListener _listener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            StringBuilder sb = new StringBuilder();

            for (Object pdu:pdus) {
                SmsMessage sm = SmsMessage.createFromPdu((byte[]) pdu);

                String body = sm.getMessageBody().toString();
                String address = sm.getOriginatingAddress();
                Date date = new Date(sm.getTimestampMillis());
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                sb.append("短信来自: " + address + "\n");
                sb.append("短信内容: " + body + "\n");
                sb.append( "短信时间: " + sdf.format(date) + "\n");
            }
            _listener.OnReceived(sb.toString());
        }
    }



    // 回调接口
    public interface IMessageListener {
        void OnReceived(String message);
    }

    public void setOnReceivedMessageListener(IMessageListener listener) {
        this._listener = listener;
    }
}