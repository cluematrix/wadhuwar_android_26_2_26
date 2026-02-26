package com.WadhuWarProject.WadhuWar.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.MembershipPlan;
import com.WadhuWarProject.WadhuWar.model.ProfilePercent;
import com.WadhuWarProject.WadhuWar.model.UserData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashActivity extends AppCompatActivity {
    Handler handler;
    Boolean user;
    //    boolean hasActivePlan = SharedPrefManager.getInstance(this).getMembershipStatus();
    boolean hasActivePlan; // just declare here
    UserData user1;
    FetchProfile fetchProfile;
    String type,savedMobile;
    ProfilePercent profilePercent;
    int percent;

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
        setContentView(R.layout.activity_splash);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
//        setContentView(R.layout.activity_splash);
        user1 = SharedPrefManager.getInstance(SplashActivity.this).getUser();
//        fetchProfileData();
        String Premium = "Premium";
        user= SharedPrefManager.getInstance(this).isLoggedIn();
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(getApplicationContext());
        hasActivePlan = sharedPrefManager.getMembershipStatus();
        type = sharedPrefManager.getType();
        savedMobile = SharedPrefManager.getInstance(this).getSavedMobile();

        checkProfilePercent();

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

//                Intent intent = new Intent(SplashActivity.this, FirstScreenActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                finish();
//                startActivity(intent);


//                if (!user) {
//
//                    System.out.println("user>>>>>>>>>." + user);
//
//                    Intent intent = new Intent(SplashActivity.this, LoginRegisterFirstActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    finish();
//                    startActivity(intent);
//
//                }
                if (!user) {
                    if (savedMobile != null && savedMobile.length() == 10) {
                        Intent intent = new Intent(SplashActivity.this, RegistrationActivity.class);
//                        intent.putExtra("mobile", mobile);
                        startActivity(intent);
                        finish();
                    } else {
                        System.out.println("user>>>>>>>>>." + user);
                        Intent intent = new Intent(SplashActivity.this, LoginRegisterFirstActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
                    }
                }
                else {

                    if (type.equals(Premium) || (percent < 60)){
//                    if (hasActivePlan) {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(SplashActivity.this, PremiumActivity.class);
                        intent.putExtra("activity_name", "splash");
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
                    }
                }
            }
        },2000);
        handler=new Handler();
    }

    public void checkProfilePercent() {
        System.out.println("profile percent api==========" + user1.getUser_id());
        Api apiService = RetrofitClient.getApiService();
        Call<ProfilePercent> userResponse = apiService.profilecompelte(String.valueOf(user1.getUser_id()));
        userResponse.enqueue(new Callback<ProfilePercent>() {
            @Override
            public void onResponse(Call<ProfilePercent> call, Response<ProfilePercent> response) {
                profilePercent = response.body();
                if (response.isSuccessful()) {
                    System.out.println("percent===========" + profilePercent.getPercent());
                    if (profilePercent.getResid().equals("200")) {
                        percent = Integer.parseInt(profilePercent.getPercent());
//                        int imgcount = Integer.parseInt(profilePercent.getImgcount());
//                        Toast.makeText(SplashActivity.this, "perc " + percent, Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<ProfilePercent> call, Throwable t) {
                System.out.println("msg1 my ProfilePercent******" + t.toString());
            }
        });
    }


    public  void fetchProfileData() {

        Api apiService = RetrofitClient.getApiService();
        Call<FetchProfile> userResponse = apiService.fetchProfile(String.valueOf(user1.getUser_id()));
        userResponse.enqueue(new Callback<FetchProfile>() {

            @Override
            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {
                fetchProfile = response.body();
                if (response.isSuccessful()) {
                    Toast.makeText(SplashActivity.this, "" + response, Toast.LENGTH_SHORT).show();
//                    if (fetchProfile.getIs_active().equals("0")){
//                        type = fetchProfile.getAcc_type();
//                    } else {
//
//                    }
                }
            }

            @Override
            public void onFailure(Call<FetchProfile> call, Throwable t) {

            }
        });
    }

}