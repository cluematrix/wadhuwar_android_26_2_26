package com.WadhuWarProject.WadhuWar.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.model.UserData;

public class HelpSupportActivity extends AppCompatActivity {
    Toolbar toolbar;
    UserData user;
    TextView userName, call1, call2,email;
    int MY_PERMISSIONS_REQUEST_CALL_PHONE = 100;

    RelativeLayout proRel, memRel, searchRel;

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
        setContentView(R.layout.activity_help_support);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_help_support);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Help & Support");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        email = findViewById(R.id.email);
        call2 = findViewById(R.id.call2);
        call1 = findViewById(R.id.call1);
        userName = findViewById(R.id.textUsername);
        proRel = findViewById(R.id.proHelpRel);
        memRel = findViewById(R.id.memberspayRel);
        searchRel = findViewById(R.id.searchOpRel);

        user = SharedPrefManager.getInstance(HelpSupportActivity.this).getUser();

        userName.setText("Hi " + user.getName() + ", " + "how can we help you?");

        proRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HelpSupportActivity.this, ProfilePhotosHelpActivity.class));
            }
        });

        memRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HelpSupportActivity.this, MembersPayHelpActivity.class));
            }
        });

        searchRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HelpSupportActivity.this, SearchHelpActivity.class));
            }
        });

        call1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(Intent.ACTION_CALL);
                it.setData(Uri.parse("tel:" + "9420944984"));


                if (ContextCompat.checkSelfPermission(HelpSupportActivity.this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(HelpSupportActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE);


                } else {
                    //You already have permission
                    try {
                        startActivity(it);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }


            }
        });


        call2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(Intent.ACTION_CALL);
                it.setData(Uri.parse("tel:" + "9420944984"));


                if (ContextCompat.checkSelfPermission(HelpSupportActivity.this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(HelpSupportActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE);


                } else {
                    //You already have permission
                    try {
                        startActivity(it);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }

            }
        });


        call2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(Intent.ACTION_CALL);
                it.setData(Uri.parse("tel:" + "9420944984"));


                if (ContextCompat.checkSelfPermission(HelpSupportActivity.this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(HelpSupportActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE);


                } else {
                    //You already have permission
                    try {
                        startActivity(it);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });
    }

    private void sendEmail() {
//        String[] to = {"support@buddhistwadhuwar.com"}; // recipient email address
        String[] to = {"support@wadhuwar.com"}; // recipient email address

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);

        startActivity(Intent.createChooser(emailIntent, "Send Email"));
    }

}