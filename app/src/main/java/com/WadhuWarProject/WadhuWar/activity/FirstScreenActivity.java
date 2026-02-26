package com.WadhuWarProject.WadhuWar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.adapter.SlideAdapter;
import com.viewpagerindicator.CirclePageIndicator;

public class FirstScreenActivity extends AppCompatActivity {

    CirclePageIndicator indicator;
    private static int NUM_PAGES = 0;
    ViewPager viewPager;
    TextView register_btn,login_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }


        setContentView(R.layout.activity_first_screen);

        viewPager= (ViewPager)findViewById(R.id.viewPager);
        register_btn= findViewById(R.id.register_btn);
        login_btn= findViewById(R.id.login_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstScreenActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstScreenActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });



        SlideAdapter viewPagerAdapter = new SlideAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);

        //====================

        int space = 20;
//        indicator = (CirclePageIndicator) findViewById(R.id.indicator);


//        indicator.setViewPager(viewPager);

        final float density = getResources().getDisplayMetrics().density;

        indicator.setStrokeColor(Color.parseColor("#283581"));

        indicator.setStrokeWidth(0);
        //Set circle indicator radius
        indicator.setRadius(4 * density);




        //====================



    }
}