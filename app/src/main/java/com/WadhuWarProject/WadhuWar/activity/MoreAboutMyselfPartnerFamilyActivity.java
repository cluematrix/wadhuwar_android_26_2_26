package com.WadhuWarProject.WadhuWar.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.FetchAboutMe;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class MoreAboutMyselfPartnerFamilyActivity extends AppCompatActivity implements  NetworkStateReceiver.NetworkStateReceiverListener{

    private NetworkStateReceiver networkStateReceiver;
    UserData user;

    EditText about_edt;
    FetchAboutMe fetchAboutMe;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView wrd_count;
    LinearLayout update_btn;
    static boolean isNetworkAvailable;
    ProgressDialog progressBar;

    InsertResponse edit_response;
    Toolbar toolbar;


    static RelativeLayout internetOffRL;
    static ProgressBar simpleProgressBar;
    static TextView try_again_txt,couldnt_reach_internet_txt;


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
        setContentView(R.layout.activity_more_about_myself_partner_family);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_more_about_myself_partner_family);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        about_edt = findViewById(R.id.about_edt);
        swipeRefreshLayout =  findViewById(R.id.swipeRefreshLayout);
        wrd_count =  findViewById(R.id.wrd_count);
        update_btn =  findViewById(R.id.update_btn);

        internetOffRL  = (RelativeLayout) findViewById(R.id.internetOffRL);
        simpleProgressBar  = (ProgressBar) findViewById(R.id.simpleProgressBar);
        try_again_txt  = (TextView) findViewById(R.id.try_again_txt);
        couldnt_reach_internet_txt  = (TextView) findViewById(R.id.couldnt_reach_internet_txt);


        user = SharedPrefManager.getInstance(MoreAboutMyselfPartnerFamilyActivity.this).getUser();

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("My Profile");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });


        about_edt.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                wrd_count.setText(String.valueOf(about_edt.getText().length()));

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });


        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String _about_edt = about_edt.getText().toString();



                if (!isNetworkAvailable(MoreAboutMyselfPartnerFamilyActivity.this)) {

                    Snackbar mSnackbar = Snackbar.make(v, "Please Check Internet Connection!", Snackbar.LENGTH_LONG);
                    View mView = mSnackbar.getView();
                    TextView mTextView = (TextView) mView.findViewById(R.id.snackbar_text);
                    mTextView.setBackgroundColor(Color.RED);
                    mTextView.setTextColor(Color.WHITE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                        mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    else
                        mTextView.setGravity(Gravity.CENTER_HORIZONTAL);
                    mSnackbar.show();

                } else {
                    updateData(_about_edt);
                }





            }
        });

        fetchAboutMeData();

        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/
    }


    @Override
    public void networkAvailable() {
//        fetchAboutMeData();

        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                fetchAboutMeData();

//                swipeRefreshLayout.setRefreshing(false);
            }
        });

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


    public void updateData(String _about_edt){

        progressBar = ProgressDialog.show(MoreAboutMyselfPartnerFamilyActivity.this, "", "Please Wait...");

        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.editAboutMe(String.valueOf(user.getUser_id()),"aboutme",_about_edt);

        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                edit_response = response.body();
                progressBar.dismiss();

                if (response.isSuccessful()) {
                    update_btn.setVisibility(View.VISIBLE);

                    String success = edit_response.getResid();

                    if (success.equals("200")) {

                        Toast.makeText(MoreAboutMyselfPartnerFamilyActivity.this,edit_response.getResMsg(),Toast.LENGTH_SHORT).show();

                        Intent i= new Intent(MoreAboutMyselfPartnerFamilyActivity.this,MyProfileActivity.class);
                        startActivity(i);
                        finish();
                    }else {
                        Toast.makeText(MoreAboutMyselfPartnerFamilyActivity.this,edit_response.getResMsg(),Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                progressBar.dismiss();

                System.out.println("err about edit******" + t.toString());

                if(!isNetworkAvailable(MoreAboutMyselfPartnerFamilyActivity.this)){
//                    Toast.makeText(MoreAboutMyselfPartnerFamilyActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(MoreAboutMyselfPartnerFamilyActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }


    @Override
    public void networkUnavailable() {
        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Toast.makeText(MoreAboutMyselfPartnerFamilyActivity.this,"Please check internet connection!",Toast.LENGTH_SHORT).show();

                swipeRefreshLayout.setRefreshing(false);
            }
        });

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
    public boolean isNetworkAvailable(){

        ConnectivityManager connectivityManager=(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null;
    }



    public  void fetchAboutMeData(){

        System.out.println("user_id111=====" + String.valueOf(user.getUser_id()));

        Api apiService = RetrofitClient.getApiService();
        Call<FetchAboutMe> userResponse = apiService.fetchAboutMe(String.valueOf(user.getUser_id()),"aboutme");
        userResponse.enqueue(new Callback<FetchAboutMe>() {

            @Override
            public void onResponse(Call<FetchAboutMe> call, Response<FetchAboutMe> response) {
                fetchAboutMe = response.body();
                swipeRefreshLayout.setRefreshing(false);

                /*System.out.println("fetme -------" + new Gson().toJson(fetchAboutMe));*/

                if (response.isSuccessful()) {

                    update_btn.setVisibility(View.VISIBLE);

                    about_edt.setText(fetchAboutMe.getAbout_me());

                    wrd_count.setText(String.valueOf(about_edt.length()));

                }

            }

            @Override
            public void onFailure(Call<FetchAboutMe> call, Throwable t) {
                System.out.println("msg1 error fetch about me******" + t.toString());
                swipeRefreshLayout.setRefreshing(false);
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


}