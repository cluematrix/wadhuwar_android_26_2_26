package com.WadhuWarProject.WadhuWar.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.google.android.material.appbar.AppBarLayout;

import de.hdodenhof.circleimageview.CircleImageView;

public class PartnerPrefInDetailActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener{
    Toolbar toolbar;
    UserData user;
    int pref_count;
    CircleImageView his_her_profile;
    static boolean isNetworkAvailable;
    private NetworkStateReceiver networkStateReceiver;
    SwipeRefreshLayout swipeRefreshLayout;
    AppBarLayout toolbar_appbar;



    FetchProfile fetchProfile;

    static RelativeLayout internetOffRL;
    static ProgressBar simpleProgressBar;
    static TextView try_again_txt,couldnt_reach_internet_txt;

    TextView pref_marital_status,pref_age,pref_height,pref_height_to,pref_color_complexion,pref_education,pref_religion,pref_caste,pref_sub_caste,pref_sampraday,pref_life_Style,
            pref_diet,pref_expectation,pref_education_type,pref_occupation_type,pref_occupation_name,pref_salary_income,
            pref_country,pref_state,pref_district;


    FetchProfile fetchProfileofUsers;

    @SuppressLint("RestrictedApi")
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
        setContentView(R.layout.activity_partner_pref_in_detail);

        View root1 = findViewById(R.id.addressLL);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @SuppressLint("RestrictedApi")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_partner_pref_in_detail);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);

        pref_occupation_name =  findViewById(R.id.pref_occupation_name);
        pref_occupation_type =  findViewById(R.id.pref_occupation_type);
        pref_education_type =  findViewById(R.id.pref_education_type);
        pref_marital_status =  findViewById(R.id.pref_marital_status);
        pref_age =  findViewById(R.id.pref_age);
        pref_height =  findViewById(R.id.pref_height);
        pref_height_to =  findViewById(R.id.pref_height_to);
        pref_color_complexion =  findViewById(R.id.pref_color_complexion);
        pref_education =  findViewById(R.id.pref_education);
        pref_religion =  findViewById(R.id.pref_religion);
        pref_caste =  findViewById(R.id.pref_caste);
        pref_sub_caste =  findViewById(R.id.pref_sub_caste);
        pref_sampraday =  findViewById(R.id.pref_sampraday);
        pref_life_Style =  findViewById(R.id.pref_life_Style);
        pref_diet =  findViewById(R.id.pref_diet);
        pref_salary_income =  findViewById(R.id.pref_salary_income);
        pref_country =  findViewById(R.id.pref_country);
        pref_state =  findViewById(R.id.pref_state);
        pref_district =  findViewById(R.id.pref_district);

        toolbar =  findViewById(R.id.toolbar);


        internetOffRL  = (RelativeLayout) findViewById(R.id.internetOffRL);
        simpleProgressBar  = (ProgressBar) findViewById(R.id.simpleProgressBar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        try_again_txt  = (TextView) findViewById(R.id.try_again_txt);
        couldnt_reach_internet_txt  = (TextView) findViewById(R.id.couldnt_reach_internet_txt);


        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });





        Intent i = getIntent();
        if(i.getSerializableExtra("userdata")!=null){
            fetchProfileofUsers = (FetchProfile) i.getSerializableExtra("userdata");
        }

        user = SharedPrefManager.getInstance(PartnerPrefInDetailActivity.this).getUser();


        pref_marital_status.setText(fetchProfileofUsers.getPref_marital_name());
        if(!fetchProfileofUsers.getPref_agefrom().equals("Not Specified") && !fetchProfileofUsers.getPref_ageto().equals("Not Specified") ){
            if(!fetchProfileofUsers.getPref_agefrom().equals("Doesn't Matter") && !fetchProfileofUsers.getPref_ageto().equals("Doesn't Matter") ){
                pref_age.setText(fetchProfileofUsers.getPref_agefrom() + " to " +  fetchProfileofUsers.getPref_ageto());
            }else if(fetchProfileofUsers.getPref_agefrom().equals("Doesn't Matter") && !fetchProfileofUsers.getPref_ageto().equals("Doesn't Matter")){
                pref_age.setText("Upto " +  fetchProfileofUsers.getPref_ageto());

            }else if(!fetchProfileofUsers.getPref_agefrom().equals("Doesn't Matter") ){
                pref_age.setText("from " +  fetchProfileofUsers.getPref_agefrom());

            }


        }else{
            if(!fetchProfileofUsers.getPref_agefrom().equals("Not Specified") && fetchProfileofUsers.getPref_ageto().equals("Not Specified") ){
                pref_age.setText(fetchProfileofUsers.getPref_agefrom());

            }

            if(fetchProfileofUsers.getPref_agefrom().equals("Not Specified") && !fetchProfileofUsers.getPref_ageto().equals("Not Specified") ){
                pref_age.setText(fetchProfileofUsers.getPref_ageto());

            }
        }
        if(fetchProfileofUsers.getPref_agefrom().equals("Doesn't Matter") && fetchProfileofUsers.getPref_ageto().equals("Doesn't Matter") ){
            pref_age.setText("Doesn't Matter");

        }
        if(fetchProfileofUsers.getPref_agefrom().equals("Not Specified") && fetchProfileofUsers.getPref_ageto().equals("Not Specified") ){
            pref_age.setText("Not Specified");

        }

        pref_occupation_name.setText(fetchProfileofUsers.getPref_occ_name());
        pref_occupation_type.setText(fetchProfileofUsers.getPrefmain_occ_name());
        pref_height.setText(fetchProfileofUsers.getPref_height_from());
        pref_height_to.setText(fetchProfileofUsers.getPref_height_to());
        pref_color_complexion.setText(fetchProfileofUsers.getPref_complex_name());
        pref_education_type.setText(fetchProfileofUsers.getPrefmain_edu_name());
        pref_education.setText(fetchProfileofUsers.getPref_edu_name());
        pref_religion.setText(fetchProfileofUsers.getPref_religion_name());
        pref_caste.setText(fetchProfileofUsers.getPref_caste_name());
        pref_sub_caste.setText(fetchProfileofUsers.getPref_subcaste_name());
        pref_sampraday.setText(fetchProfileofUsers.getPref_sampraday_name());
        pref_life_Style.setText(fetchProfileofUsers.getPref_lifestyle_name());
        pref_diet.setText(fetchProfileofUsers.getPref_diet_name() );
        pref_salary_income.setText(fetchProfileofUsers.getYearly_salary() );
        pref_country.setText(fetchProfileofUsers.getPref_country_name() );
        pref_state.setText(fetchProfileofUsers.getPref_state_name() );
        pref_district.setText(fetchProfileofUsers.getPref_dist_name() );
       // pref_expectation.setText(fetchProfileofUsers.getPref_expect());






        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/



    }

    @SuppressLint("RestrictedApi")
    @Override
    public void networkAvailable() {


        final Handler handler = new Handler();

        Runnable delayrunnable = new Runnable() {

            @Override
            public void run() {

                internetOffRL.setVisibility(View.GONE);
                couldnt_reach_internet_txt.setVisibility(View.GONE);

            }
        };
        handler.postDelayed(delayrunnable, 3000);


    }

    @Override
    public void networkUnavailable() {

        internetOffRL.setVisibility(View.VISIBLE);

        try_again_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try_again_txt.setVisibility(View.GONE);
                simpleProgressBar.setVisibility(View.VISIBLE);
                if(!isNetworkAvailable()){
                    simpleProgressBar.postDelayed(new Runnable() {
                        public void run() {
                            couldnt_reach_internet_txt.postDelayed(new Runnable() {
                                public void run() {
                                    couldnt_reach_internet_txt.setVisibility(View.VISIBLE);
                                    try_again_txt.setVisibility(View.VISIBLE);
                                    simpleProgressBar.setVisibility(View.GONE);
                                    couldnt_reach_internet_txt.postDelayed(new Runnable() {
                                        public void run() {
                                            couldnt_reach_internet_txt.setVisibility(View.GONE);


                                        }
                                    }, 2000);
                                }
                            }, 2000);

                        }
                    }, 2000);
                }
                else{
                    networkUnavailable();
                }

            }
        });
    }

    public static boolean isNetworkAvailable(Context context) {
        isNetworkAvailable = false;
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        isNetworkAvailable = true;
                        return true;
                    }
                }
            }
        }
        return false;
    }//isNetworkAvailable()


    public boolean isNetworkAvailable(){

        ConnectivityManager connectivityManager=(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }




}