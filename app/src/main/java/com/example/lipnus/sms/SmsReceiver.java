package com.example.lipnus.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
            Bundle extras = intent.getExtras();
            Status status = (Status) extras.get(SmsRetriever.EXTRA_STATUS);;

            switch(status.getStatusCode()) {
                case CommonStatusCodes.SUCCESS:
                    String message = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);
                    //여기서 받음
                    Log.d("SSS", "서버로부터 받은 Message: " + message);
                    break;
                case CommonStatusCodes.TIMEOUT:
                    //시간초과
                    Log.d("SSS", "onReceive - CommonStatusCodes.SUCCESS");
                    break;
            }
        }
    }


    //인터페이스
    public interface OTPReceiveListener {

        void onOTPReceived(String msg);

        void onOTPTimeOut();
    }
}
