package com.WadhuWarProject.WadhuWar.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;

public class PaymentSuccessMelavaActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener{

    LinearLayout successLL,failureLL,apiFailureLL,callLL;
    Button btn,btn2,btn3;
    String status;
    int MY_PERMISSIONS_REQUEST_CALL_PHONE = 100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

        WindowInsetsControllerCompat controller =
                WindowCompat.getInsetsController(window, window.getDecorView());
        controller.setAppearanceLightStatusBars(true);

        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
        setContentView(R.layout.payment_success_layout);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.payment_success_layout);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
        btn = findViewById(R.id.btn);
        btn2 = findViewById(R.id.btn2);
        successLL = findViewById(R.id.successLL);
        failureLL = findViewById(R.id.failureLL);
        apiFailureLL = findViewById(R.id.apiFailureLL);
        btn3 = findViewById(R.id.btn3);
        callLL = findViewById(R.id.callLL);


        Bundle extras = getIntent().getExtras();
        if(extras == null) {
        } else {
            status= extras.getString("status");
        }

        if(status.equals("success")){
            successLL.setVisibility(View.VISIBLE);
            failureLL.setVisibility(View.GONE);
            apiFailureLL.setVisibility(View.GONE);
        }else if(status.equals("failure")){
            successLL.setVisibility(View.GONE);
            failureLL.setVisibility(View.VISIBLE);
            apiFailureLL.setVisibility(View.GONE);
        }else{
            successLL.setVisibility(View.GONE);
            failureLL.setVisibility(View.GONE);
            apiFailureLL.setVisibility(View.VISIBLE);
        }



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PaymentSuccessMelavaActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PaymentSuccessMelavaActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PaymentSuccessMelavaActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        callLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Intent.ACTION_CALL);
                it.setData(Uri.parse("tel:"+ "9420944984"));


                if (ContextCompat.checkSelfPermission(PaymentSuccessMelavaActivity.this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(PaymentSuccessMelavaActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE);


                } else {
                    //You already have permission
                    try {
                        startActivity(it);
                    } catch(SecurityException e) {
                        e.printStackTrace();
                    }
                }


            }
        });


    }



    @Override
    protected void onPause() {
        super.onPause();



    }




    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void networkAvailable() {

    }

    @Override
    public void networkUnavailable() {

    }



}
