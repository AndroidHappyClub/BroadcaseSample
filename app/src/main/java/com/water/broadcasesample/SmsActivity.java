package com.water.broadcasesample;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import com.water.broadcasesample.receiver.SmsReceiver;
import com.water.utilities.PermissionHelper;

public class SmsActivity extends Activity
        implements SmsReceiver.IMessageListener {
    private TextView tvSms;
    private SmsReceiver _receiver;
    private String[] _permissions = {
            Manifest.permission.RECEIVE_SMS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        bindViews();
    }

    // 短信权限设置
    private void bindViews() {
        PermissionHelper.checkPermission(this, _permissions);

//        Intent intent = new Intent();
//        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//        Uri uri = Uri.fromParts("package", getApplication().getPackageName(), null);
//        intent.setData(uri);
//        startActivity(intent);

        tvSms = (TextView) findViewById(R.id.tvSms);
        _receiver = new SmsReceiver();

//        _receiver.setOnReceivedMessageListener(
//                new SmsReceiver.IMessageListener() {
//            @Override
//            public void OnReceived(String msg) {
//
//            }
//        });
        _receiver.setOnReceivedMessageListener(this);
    }

    @Override
    public void OnReceived(String message) {
        tvSms.setVisibility(View.VISIBLE);
        tvSms.setText(message);
    }
}