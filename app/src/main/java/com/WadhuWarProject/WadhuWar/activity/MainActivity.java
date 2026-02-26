package com.WadhuWarProject.WadhuWar.activity;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.fragments.HomeFragment;
import com.WadhuWarProject.WadhuWar.fragments.InboxFragment;
import com.WadhuWarProject.WadhuWar.fragments.MatchesFragment;
import com.WadhuWarProject.WadhuWar.fragments.MelavaFragment;
import com.WadhuWarProject.WadhuWar.fragments.MyProfileFragment;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.ProfilePercent;
import com.WadhuWarProject.WadhuWar.model.UpdateResponse;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {
    private Boolean exit = false;
    ProgressDialog progressBar;

    private static final int WRITE_PERMISSION = 0x01;
    Button count_msg;
    ProgressBar progress_bar;
    public static MainActivity finishConext;
    ImageView imageView_search;
    InsertResponse notificationMsg;
    Intent mServiceIntent;
    static boolean isNetworkAvailable;
    UserData user;
    static RelativeLayout internetOffRL;
    static ProgressBar simpleProgressBar;
    static TextView try_again_txt, couldnt_reach_internet_txt;
    public static BottomNavigationView navigation;
    private NetworkStateReceiver networkStateReceiver;
    FetchProfile fetchProfile;
    private static Context mContext;
    BottomNavigationView navView;
    Fragment fragment1 = new HomeFragment();
    Fragment fragment2 = new MyProfileFragment();
    Fragment fragment3 = new MatchesFragment();
    Fragment fragment4 = new InboxFragment();
    Fragment fragment5 = new MelavaFragment();
    public final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment3;
    String activity_name;


    UpdateResponse updateResponse;
    ProfilePercent profilePercent;
    InsertResponse response_check;
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

        setContentView(R.layout.activity_main);

        View root = findViewById(R.id.container);

        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets statusBars = insets.getInsets(WindowInsetsCompat.Type.statusBars());

            v.setPadding(
                    v.getPaddingLeft(),
                    statusBars.top,
                    v.getPaddingRight(),
                    0
            );
            return insets;
        });
        Log.d("DebugLog", "MainActivity: onCreate started");
        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_main);
//        Log.d("DebugLog", "MainActivity: onCreate started");
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);



        navView = findViewById(R.id.nav_view);

        internetOffRL = (RelativeLayout) findViewById(R.id.internetOffRL);
        simpleProgressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);
        simpleProgressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#EC5252"), android.graphics.PorterDuff.Mode.MULTIPLY);
        try_again_txt = (TextView) findViewById(R.id.try_again_txt);
        couldnt_reach_internet_txt = (TextView) findViewById(R.id.couldnt_reach_internet_txt);
        finishConext = this;

        user = SharedPrefManager.getInstance(MainActivity.this).getUser();

        fm.beginTransaction().add(R.id.fragment_container, fragment5, "5").hide(fragment5).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment4, "4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment1, "1").hide(fragment1).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment3, "3").commit();

        navigation = findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        String userId = String.valueOf(SharedPrefManager.getInstance(this).getUser().getUser_id());

        /*internet code*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener((NetworkStateReceiver.NetworkStateReceiverListener) this);
        this.registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        /*end internet code*/

        navigation.getMenu().findItem(R.id.navigation_mathces).setChecked(true);


        /*check  app active or not*/

        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        ActivityManager.RunningTaskInfo task = tasks.get(0); // current task
        ComponentName rootActivity = task.baseActivity;


        String currentPackageName = rootActivity.getPackageName();
        if (currentPackageName.equals("com.WadhuWarProject.WadhuWar")) {

            //  Toast.makeText(MainActivity.this, "wadhu war run=============", Toast.LENGTH_SHORT);
            System.out.println("wadhu war run=================");
        } else {
            //  Toast.makeText(MainActivity.this, "wadhu war not run=============", Toast.LENGTH_SHORT);
            System.out.println("wadhu war not run=================");
        }
        /*end check  app active or not*/

        /*device name*/
//        String str = android.os.Build.MODEL;

        String reqString = Build.MANUFACTURER
                + " " + Build.MODEL + " " + Build.VERSION.RELEASE
                + " " + Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName();
        System.out.println("device name where app running111=================" + reqString);

        /*device name*/


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {
                Log.d("permission", "permission denied to WRITE_EXTERNAL_STORAGE - requesting it");
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, 1);
            }
        }


        checkUpdate();
        getMsgCount(MainActivity.this, user);

      /*  BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);

        BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(4);

        View messageBadgeView = LayoutInflater.from(this).inflate(R.layout.notification_badge, menuView, false);
        TextView textView = messageBadgeView.findViewById(R.id.count_msg);
        textView.setText("15");

        itemView.addView(messageBadgeView);*/

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getMsgCount(MainActivity.this, user);
                handler.postDelayed(this, 3000);

            }
        }, 3000);


        fetchProfile();
//        checkProfilePercent();

        handleIntent(getIntent());


        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();

//        requestWritePermission();


        /*------------*/


//        Intent i = getIntent();
//        if(i!=null){
//            activity_name = i.getStringExtra("activity_name");
//        }
//
//        if(activity_name!=null){
//            if(activity_name.equals("splash")){
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        final Intent mainIntent = new Intent(MainActivity.this, PremiumActivity.class);
//                        mainIntent.putExtra("activity_name","splash");
//                        MainActivity.this.startActivity(mainIntent);
//                        MainActivity.this.finish();
//                    }
//                }, 3000);
//            }else{
//
//            }
//        }else{
//
//        }


        FirebaseApp.initializeApp(MainActivity.this);
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> {
            if (!TextUtils.isEmpty(token)) {
                Log.d("device_id", "onResponse: " + token);
                otpVerification(token, userId);
            } else {
                Log.w("FirebaseToken", "Token should not be null...");
            }
        });
    }

    private void otpVerification(String device_id, String user_id) {
        Api apiService = RetrofitClient.getApiService();
        Call<UserData> userResponse = apiService.verifyOTP(user_id, device_id);
        userResponse.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                Log.d("device_id", "onResponse: " + device_id);
                user = response.body();
                if (response.isSuccessful() && user.getResid().equals("200")) {
                    SharedPrefManager.getInstance(MainActivity.this).saveUser(user);
                } else {
                    Toast.makeText(MainActivity.this, "Verification failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
            }
        });
    }

    private void requestWritePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION);
            }
        }
    }

    public void fetchProfile() {

        progressBar = ProgressDialog.show(MainActivity.this, "", "Please Wait...");

        System.out.println("user_id===== main activity " + String.valueOf(user.getUser_id()));

        Api apiService = RetrofitClient.getApiService();
        Call<FetchProfile> userResponse = apiService.fetchProfile(String.valueOf(user.getUser_id()));
        userResponse.enqueue(new Callback<FetchProfile>() {

            @Override
            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {
                progressBar.dismiss();

//                System.out.println("11 user_id===== main activity resp " +  new Gson().toJson(response.body()));

                fetchProfile = response.body();

//                System.out.println("user_id===== main activity resp " +  new Gson().toJson(fetchProfile));

                if (response.isSuccessful()) {
//                    System.out.println("22 ser_id===== main activity resp " +  new Gson().toJson(fetchProfile));

                    String acc_type = response.body().getAcc_type();

                    if (fetchProfile.getResid().equals("200")) {
                        SharedPrefManager.getInstance(MainActivity.this).saveProfileData(fetchProfile);

                        if (fetchProfile.getIs_active() != null) {
                            if (fetchProfile.getIs_active().equals("1")) {
//                                checkProfilePercent();
                            } else {
                                showBox();
                            }
                        }
                    } else {
                        System.out.println("else 1010101010==========");
                        showBox();
                    }
                } else {
                    System.out.println("else 2020202020==========");
                    showBox();
                }

            }

            @Override
            public void onFailure(Call<FetchProfile> call, Throwable t) {
                System.out.println("msg1 my profile******" + t.toString());
                progressBar.dismiss();
            }
        });

    }

    public void showBox() {

        /*dialog apply*/


        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.login_admin_box);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        TextView cancel_txt = dialog.findViewById(R.id.cancel_txt);
        TextView call_btn = dialog.findViewById(R.id.call_btn);
        TextView whatapp_txt = dialog.findViewById(R.id.whatapp_txt);
        cancel_txt.setVisibility(View.GONE);
        TextView emailTxt = dialog.findViewById(R.id.email_id_txt);

        if (emailTxt != null) {
            emailTxt.setText(" support@wadhuwar.com");
            emailTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    emailIntent.setData(Uri.parse("mailto:support@wadhuwar.com"));
                    try {
                        startActivity(emailIntent);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "No email client found", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            });
        } else {
            Log.e("not", "email_id_txt not found in layout");
            Toast.makeText(MainActivity.this, "Email view not found", Toast.LENGTH_SHORT).show();
        }

        LinearLayout call_LL = dialog.findViewById(R.id.call_LL);

        call_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Intent.ACTION_CALL);
                it.setData(Uri.parse("tel:" + "9420944984"));


                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(MainActivity.this,
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

                dialog.dismiss();
                dialog.cancel();
            }
        });

    /*    cancel_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog.cancel();
            }
        });*/

        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Intent.ACTION_CALL);
                it.setData(Uri.parse("tel:" + "9420944984"));


                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(MainActivity.this,
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

                dialog.dismiss();
                dialog.cancel();
            }
        });

        whatapp_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String whatapp_no = "9420944984";

                String phoneNumber = "91" + whatapp_no; //without '+'

                Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=");

                Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);

                startActivity(sendIntent);
            }
        });


        dialog.show();




        /*dialog apply*/
    }

    public void checkProfilePercent() {

        System.out.println("profile percent api==========" + user.getUser_id());
        Api apiService = RetrofitClient.getApiService();
        Call<ProfilePercent> userResponse = apiService.profilecompelte(String.valueOf(user.getUser_id()));
        userResponse.enqueue(new Callback<ProfilePercent>() {

            @Override
            public void onResponse(Call<ProfilePercent> call, Response<ProfilePercent> response) {
                profilePercent = response.body();


                if (response.isSuccessful()) {

                    System.out.println("percent===========" + profilePercent.getPercent());

                    if (profilePercent.getResid().equals("200")) {
                        int percent = Integer.parseInt(profilePercent.getPercent());
                        int imgcount = Integer.parseInt(profilePercent.getImgcount());

                        if (imgcount == 0) {

                            /*dialog apply*/


                            final Dialog dialog = new Dialog(MainActivity.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.profile_box);
                            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                            dialog.getWindow().setGravity(Gravity.BOTTOM);

                            LinearLayout add_profile_btn = dialog.findViewById(R.id.add_profile_btn);


                            add_profile_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i = new Intent(MainActivity.this, MyPhotosActivity.class);


                                    startActivity(i);
                                    dialog.dismiss();
                                    dialog.cancel();
                                }
                            });


                            dialog.show();


                            /*dialog apply*/

                        } else if (percent < 60) {
                            /*dialog apply*/


                            final Dialog dialog = new Dialog(MainActivity.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.percent_box);
                            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                            TextView percent_txt = dialog.findViewById(R.id.percent_txt);
                            LinearLayout edit_profile_btn = dialog.findViewById(R.id.edit_profile_btn);
                            ProgressBar progress_bar = dialog.findViewById(R.id.progress_bar);

                            percent_txt.setText("  " + String.valueOf(percent) + "%");

                            progress_bar.setProgress(percent);

                            edit_profile_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i = new Intent(MainActivity.this, MyProfileActivity.class);


                                    startActivity(i);
                                    dialog.dismiss();
                                    dialog.cancel();
                                }
                            });

                            dialog.show();

                            /*dialog apply*/
                        } else {
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfilePercent> call, Throwable t) {
                System.out.println("msg1 my ProfilePercent******" + t.toString());
//                progressBar.dismiss();

//                if(!isNetworkAvailable(ChatActivity.this)){
//                    Toast.makeText(ChatActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
//
//                }else{
//                    Toast.makeText(ChatActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();
//
//                }
            }
        });
    }


    private void handleIntent(Intent intent) {
        Intent appLinkIntent = intent;
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();

        System.out.println("data11---------------" + appLinkIntent);
        System.out.println("data22---------------" + appLinkAction);
        System.out.println("data33---------------" + appLinkData);
        System.out.println("Intent.ACTION_VIEW---------------" + Intent.ACTION_VIEW);


        if (Intent.ACTION_VIEW.equals(appLinkAction) && appLinkData != null) {


            Boolean user1 = SharedPrefManager.getInstance(this).isLoggedIn();

            if (user1) {

            } else {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }

            System.out.println("1111111111111111111111111111111111");


        } else {
            System.out.println("2222222222222222222222222222222222");


        }

    }


    @Override
    protected void onResume() {
        super.onResume();
//        Toast.makeText(getApplicationContext(), "onResumed called", Toast.LENGTH_LONG).show();

//        forceUpdate();
        checkProfilePercent();
        getMsgCount(this, user);
    }


    @Override
    public void onPause() {
        super.onPause();
//        Toast.makeText(getApplicationContext(), "onPause called", Toast.LENGTH_LONG).show();

//        isOnline(false);
    }

    public void checkOnOff(String user_id, String status) {
        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.checkOnlineOffline(user_id, status);
        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                response_check = response.body();

                if (response.isSuccessful()) {

                    if (response_check.getResid().equals("200")) {


                        System.out.println("1111111111111111 on");
                    }
                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {

                System.out.println("err caste******" + t.toString());
                if (!isNetworkAvailable(MainActivity.this)) {
//                    Toast.makeText(MainActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(MainActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

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

    public static MainActivity getInstance() {
        if (finishConext != null) {
            return finishConext;
        }

        return finishConext;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finishConext = null;
    }

    @Override
    public void networkAvailable() {

        System.out.println("online=============>>>");

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

        System.out.println("offline=============>>>");

        internetOffRL.setVisibility(View.VISIBLE);

        try_again_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try_again_txt.setVisibility(View.GONE);
                simpleProgressBar.setVisibility(View.VISIBLE);
                if (!isNetworkAvailable()) {
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
                } else {
                    networkUnavailable();
                }

            }
        });


    }

    public boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null;
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_home) {
                fm.beginTransaction().hide(active).show(fragment1).commit();
                active = fragment1;
                return true;
            } else if (itemId == R.id.navigation_myshaddi) {
                fm.beginTransaction().hide(active).show(fragment2).commit();
                active = fragment2;
                return true;
            } else if (itemId == R.id.navigation_mathces) {
                fm.beginTransaction().hide(active).show(fragment3).commit();
                active = fragment3;
                return true;
            } else if (itemId == R.id.navigation_inbox) {
                fm.beginTransaction().hide(active).show(fragment4).commit();
                active = fragment4;
                return true;
            } else if (itemId == R.id.navigation_melava) {
                fm.beginTransaction().hide(active).show(fragment5).commit();
                active = fragment5;
                return true;
            }

            return false;
        }
    };


    public void onBackPressed() {

        int seletedItemId = navigation.getSelectedItemId();

        if (fm.getBackStackEntryCount() > 0) {

            fm.popBackStack();

        } else {


            if (R.id.navigation_home != seletedItemId) {
                navigation.setSelectedItemId(R.id.navigation_home);
            } else {
//                super.onBackPressed();


                if (exit) {
                    finish();
                    moveTaskToBack(true);
                } else {
                 /*   Toast.makeText(this, "Press Back again to exit.", Toast.LENGTH_SHORT).show();
                    exit = true;
                    new Handler().postDelayed(() -> exit = false, 3 * 1000);*/
                    shareDialogBox();
                }


//                new AlertDialog.Builder(this)
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .setTitle("Closing App?")
//                        .setMessage("Are you sure want to close app?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
//                        {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                MainActivity.super.onBackPressed();
//                                finish();
//                            }
//
//                        })
//                        .setNegativeButton("No", null)
//                        .show();
//

            }

        }

    }


    public void forceUpdate() {
        PackageManager packageManager = this.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        String currentVersion = packageInfo.versionName;
        System.out.println(" packageInfo.versionName=========" + packageInfo.versionName);
        new ForceUpdateAsync(currentVersion, MainActivity.this).execute();


    }

    @Override
    protected void onStart() {
        super.onStart();
//        Toast.makeText(getApplicationContext(), "onStart called", Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onStop() {
        super.onStop();  // Always call the superclass method first
//        Toast.makeText(getApplicationContext(), "onStop called", Toast.LENGTH_LONG).show();
    }

    private void checkUpdate() {

        PackageManager packageManager = getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        int currentVersion = packageInfo.versionCode;
        System.out.println("currentVersion check =========" + String.valueOf(currentVersion));


        Api apiService = RetrofitClient.getApiService();
        Call<UpdateResponse> userResponse = apiService.checkUpdate(String.valueOf(currentVersion));
        userResponse.enqueue(new Callback<UpdateResponse>() {

            @Override
            public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {
                updateResponse = response.body();

                if (response.isSuccessful()) {

                    if (updateResponse.getResid().equals("200")) {


                        if (updateResponse.status.equals("1")) {
                            showUpdateDialog(updateResponse.status);
                        } else {

                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<UpdateResponse> call, Throwable t) {

                System.out.println("err UpdateResponse******" + t.toString());
                if (!isNetworkAvailable(MainActivity.this)) {
//                    Toast.makeText(MainActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(MainActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void showUpdateDialog(String status) {

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.update_ask_box);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        TextView update_app_btn = dialog.findViewById(R.id.update_app_btn);


        update_app_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                dialog.dismiss();
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public static void getMsgCount(Context mContext, UserData user) {
        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.notificationCount(String.valueOf(user.getUser_id()));
        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {

                InsertResponse notificationMsg = response.body();
                if (response.isSuccessful()) {
                    setBadge(mContext, notificationMsg);
                }
                // System.out.println("notificationMsg.getCount()=========" + notificationMsg.getCount());
            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                System.out.println("msg1 my profile******" + t.toString());
                if (!isNetworkAvailable(mContext)) {
//                    Toast.makeText(mContext, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(mContext, "Please Refresh!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public static void setBadge(Context mContext, InsertResponse notificationMsg) {
        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) navigation.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(3);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;

        View badge = LayoutInflater.from(mContext)
                .inflate(R.layout.notification_badge, itemView, true);

        Button count_msg = badge.findViewById(R.id.count_msg);

        if (notificationMsg.getResid().equals("200")) {
            if (!notificationMsg.getCount().equals("0")) {
                count_msg.setVisibility(View.VISIBLE);
                if (Integer.parseInt(notificationMsg.getCount()) > 9)
                    count_msg.setText("9+");
                else
                    count_msg.setText(notificationMsg.getCount());
            } else {
                count_msg.setVisibility(View.GONE);
            }
        }

    }

    /*dialog apply*/

    void shareDialogBox() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.share_dialog_box);
        dialog.getWindow().

                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().

                setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        TextView no_btn = dialog.findViewById(R.id.no_btn);
        TextView share_btn = dialog.findViewById(R.id.share_btn);
        no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finishAffinity();
                moveTaskToBack(true);
            }
        });

        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = "https://wadhuwar.com/Matrimonial/index.php?id=" + user.getUser_id();

                String string =
                        "*Cast / Subcaste* : " + fetchProfile.getCaste_name() + " / " + fetchProfile.getSubcaste_name() +
                                "\n\n" +
                                "*Age / Height* : " + fetchProfile.getAge() + " / " + fetchProfile.getHeight() +
                                "\n\n" +
                                "*Education* : " + fetchProfile.getEducation_name() +
                                "\n\n" +
                                "*Profession / Income* : " + fetchProfile.getOccupation_name() + " / " + fetchProfile.getYearly_salary() +
                                "\n\n" +
                                "*Permanent Location / State* : " + fetchProfile.getPer_address() + " / " + fetchProfile.getPer_state_name() +
                                "\n\n" +
                                "*Current / Work Location* : " + fetchProfile.getCurrent_address() + " / " + fetchProfile.getOfc_address();

                String shareText =
                        link +
                                "\n*" + fetchProfile.getFname() + "*\n" +
                                "*(" + fetchProfile.getAcc_id() + ")*\n\n" +
                                "*Bride Groom Introduction*\n" +
                                "\n" +
                                string +
                                "\n\n" +
                                "*Download Free Register App Use my referral code and see your favorite bride groom (WAWM00" + fetchProfile.getUser_id() + ")*\n" +
                                "\n" +
                                "*Search the link \uD83D\uDC49\uD83C\uDFFB*\n" +
                                "*https://wadhuwar.com/Matrimonial/index.php?id=" + user.getUser_id() + "*\n" +
                                "\n" +
                                "★★★★★★★★★★★★\n" +
                                "*100% Reliable Bridesmaid Introducing*";

                // Show progress dialog
                ProgressDialog progressBar = ProgressDialog.show(MainActivity.this, "", "Please Wait...");

                // Check if images exist
                if (fetchProfile.getImages() == null || fetchProfile.getImages().isEmpty()) {
                    // No image, share text only
                    progressBar.dismiss();
                    Intent textOnlyIntent = new Intent(Intent.ACTION_SEND);
                    textOnlyIntent.setType("text/plain");
                    textOnlyIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                    startActivity(Intent.createChooser(textOnlyIntent, "Share Profile"));
                    return;
                }

                // Load image using Picasso
                String imageUrl = fetchProfile.getImages().get(0).getImgs();
                Log.d("ImageURL", "Image URL: " + imageUrl);

                Picasso.get().load(imageUrl).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        try {
                            // Save image to external files directory
                            File picturesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                            File mydir = new File(picturesDir, "shared_profile");

                            Log.d("ShareDebug", "Pictures dir: " + (picturesDir != null ? picturesDir.getAbsolutePath() : "null"));
                            Log.d("ShareDebug", "Target dir: " + mydir.getAbsolutePath());

                            if (!mydir.exists()) {
                                boolean created = mydir.mkdirs();
                                Log.d("ShareDebug", "Directory created: " + created);
                                if (!created) {
                                    throw new IOException("Failed to create directory");
                                }
                            }

                            File imageFile = new File(mydir, "shared_" + System.currentTimeMillis() + ".jpg");
                            Log.d("ShareDebug", "Image file path: " + imageFile.getAbsolutePath());

                            FileOutputStream outputStream = new FileOutputStream(imageFile);
                            Bitmap watermarkedBitmap = addWatermark(bitmap, "Wadhuwar.com");
                            watermarkedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                            outputStream.flush();
                            outputStream.close();

                            Log.d("ShareDebug", "File created, size: " + imageFile.length() + " bytes");
                            Log.d("ShareDebug", "File exists: " + imageFile.exists());

                            // Get URI for the image file using FileProvider
                            Uri uri = FileProvider.getUriForFile(
                                    MainActivity.this,
                                    getPackageName() + ".fileprovider",
                                    imageFile
                            );
                            Log.d("ShareDebug", "URI created: " + uri.toString());

                            // Create Intent to share both image and text
                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("image/*");
                            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                            progressBar.dismiss();
                            startActivity(Intent.createChooser(shareIntent, "Share Profile"));

                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e("ShareError", "Error saving image: " + e.getMessage());
                            progressBar.dismiss();
                            // Fallback to sharing text only
                            shareTextOnly(shareText);
                        }
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        Log.e("ImageLoadError", "Failed to load image: " + e.getMessage());
                        progressBar.dismiss();
                        // Fallback to sharing text only
                        shareTextOnly(shareText);
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        // Keep progress dialog visible while loading
                    }
                });
            }

            private void shareTextOnly(String shareText) {
                Intent textOnlyIntent = new Intent(Intent.ACTION_SEND);
                textOnlyIntent.setType("text/plain");
                textOnlyIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                startActivity(Intent.createChooser(textOnlyIntent, "Share Profile"));
            }

            private Bitmap addWatermark(Bitmap originalBitmap, String watermarkText) {
                Bitmap result = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
                Canvas canvas = new Canvas(result);

                Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                textPaint.setColor(Color.parseColor("#80000000"));
                textPaint.setTextSize(28);
                textPaint.setFakeBoldText(true);

                Rect textBounds = new Rect();
                textPaint.getTextBounds(watermarkText, 0, watermarkText.length(), textBounds);

                float centerX = result.getWidth() / 2f;
                float centerY = result.getHeight() / 2f;

                canvas.save();
                canvas.rotate(34, centerX, centerY);

                canvas.drawText(
                        watermarkText,
                        centerX - textBounds.width() / 2f,
                        centerY + textBounds.height() / 2f,
                        textPaint
                );

                canvas.restore();
                return result;
            }
        });
//        share_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                    String link = "https://wadhuwar.com/Matrimonial/index.php?id=" + user.getUser_id();
//
//                    String string =
//                            "*Cast / Subcaste* : " + fetchProfile.getCaste_name() + " / " + fetchProfile.getSubcaste_name() +
//                                    "\n\n" +
//                                    "*Age / Height* : " + fetchProfile.getAge() + " / " + fetchProfile.getHeight() +
//                                    "\n\n" +
//                                    "*Education* : " + fetchProfile.getEducation_name() +
//                                    "\n\n" +
//                                    "*Profession / Income* : " + fetchProfile.getOccupation_name() + " / " + fetchProfile.getYearly_salary() +
//                                    "\n\n" +
//                                    "*Permanent Location / State* : " + fetchProfile.getPer_address() + " / " + fetchProfile.getPer_state_name() +
//                                    "\n\n" +
//                                    "*Current / Work Location* : " + fetchProfile.getCurrent_address() + " / " + fetchProfile.getOfc_address();
//
//                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//                    sharingIntent.setType("text/plain");
//                    sharingIntent.putExtra(Intent.EXTRA_TEXT,
//                            link +
//                                    "*" + fetchProfile.getFname() + "*\n" +
//                                    "*(" + fetchProfile.getAcc_id() + ")*\n\n" +
//                                    "*Bride Groom Introduction*\n" +
//                                    "\n" +
//                                                   /* "*I invite you by sharing the marriage bride and groom introduction link | I use Trusted Wadhu War Parichay App |*\n" +
//                                                    "\n" +*/
//                                    string +
//                                    "\n" +
//                                    "\n" +
//                                    "*Download Free Register App Use my referral code and see your favorite bride groom (WAWM00" + fetchProfile.getUser_id() + ")*\n" +
//                                    "\n" +
//                                    "*Search the link \uD83D\uDC49\uD83C\uDFFB*\n" +
//                                    "*https://wadhuwar.com/Matrimonial* \n" +
//                                    "\n" +
//                                    "★★★★★★★★★★★★\n" +
//                                    "*100% Reliable Bridesmaid Introducing*");
//
//                    startActivity(Intent.createChooser(sharingIntent, "Share Image"));
//            }
//        });
        dialog.show();
    }
}
