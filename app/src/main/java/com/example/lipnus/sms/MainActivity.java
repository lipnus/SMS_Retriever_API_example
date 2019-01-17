package com.example.lipnus.sms;

import android.content.Context;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;



public class MainActivity extends AppCompatActivity implements Button.OnClickListener, SmsReceiver.OTPReceiveListener {

    Button phone_selector_btn;
    TextView otp_tv;

    SmsReceiver smsReceiver;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        smsReceiver = new SmsReceiver();
        setLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SmsRetrieverClient client = SmsRetriever.getClient(this /* context */);
        Task<Void> task = client.startSmsRetriever();

        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
                registerReceiver(smsReceiver, intentFilter);
                Log.e("testest", "onSuccess");
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("testest", "onFailure" + e.toString());
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();

        this.unregisterReceiver(smsReceiver);
    }

    @Override
    public void onOTPReceived(String msg) {
        otp_tv.setText("OTP Number : " +  msg);
    }

    @Override
    public void onOTPTimeOut() {
        otp_tv.setText("Timeout");
    }



    private void setLayout(){

        //Button
        phone_selector_btn = findViewById(R.id.btn_phone_selector);
        phone_selector_btn.setOnClickListener(this);

        //TextView
        otp_tv = findViewById(R.id.otp_tv);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_phone_selector:
                break;
        }
    }

}
