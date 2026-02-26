package com.WadhuWarProject.WadhuWar.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.adapter.MyPhotosAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.UserData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CertificateActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener,
        ActivityCompat.OnRequestPermissionsResultCallback {
    static boolean isNetworkAvailable;
    private NetworkStateReceiver networkStateReceiver;
    Toolbar toolbar;
    UserData user;
    SwipeRefreshLayout swipeRefreshLayout;
    String imageEncoded;
    String encoded_img1;
    FetchProfile fetchProfile;
    RecyclerView image_rv;
    MyPhotosAdapter adapter;
    ProgressDialog progressBar;
    RelativeLayout add_photo_btn;


    boolean shouldAllowBack = false;
    boolean isCallFromEducationCertificate = false;

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
        setContentView(R.layout.activity_certificate);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_certificate);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
        toolbar = findViewById(R.id.toolbar);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        image_rv = findViewById(R.id.image_rv);
        add_photo_btn = findViewById(R.id.add_photo_btn);

        isCallFromEducationCertificate = getIntent().getBooleanExtra("isCallFromEducationCertificate", false);
        image_rv.setLayoutManager(new LinearLayoutManager(CertificateActivity.this, LinearLayoutManager.VERTICAL, false));


        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                onBackPressed();
////                finish();
//
//            }
//        });

        if (isCallFromEducationCertificate)
            getSupportActionBar().setTitle("My Education Certificate");
        else
            getSupportActionBar().setTitle("My Occupation Certificate");


        user = SharedPrefManager.getInstance(CertificateActivity.this).getUser();

        fetchProfileData();

        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/
    }

    @Override
    public void networkAvailable() {

//        fetchProfileData();


        add_photo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("image size-------" + fetchProfile.getImages().size());

                if (fetchProfile.getImages().size() == 2) {
                    Toast.makeText(CertificateActivity.this, "You have already uploaded 2 certificate.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(CertificateActivity.this, AddCertificateActivity.class);
                    i.putExtra("img_count", fetchProfile.getImages().size());
                    startActivity(i);
                    finish();
                }
            }
        });


        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                fetchProfileData();

//                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    @Override
    public void networkUnavailable() {
        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Toast.makeText(CertificateActivity.this, "Please check internet connection!", Toast.LENGTH_SHORT).show();

                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    public void fetchProfileData() {
        progressBar = ProgressDialog.show(CertificateActivity.this, "", "Please Wait...");


        System.out.println("user_id=====" + String.valueOf(user.getUser_id()));

        Api apiService = RetrofitClient.getApiService();
        Call<FetchProfile> userResponse = apiService.fetchProfile(String.valueOf(user.getUser_id()));
        userResponse.enqueue(new Callback<FetchProfile>() {

            @Override
            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {

                swipeRefreshLayout.setRefreshing(false);
                fetchProfile = response.body();
                progressBar.dismiss();

                if (response.isSuccessful()) {


                    if (fetchProfile.getImages() != null) {


                        if (fetchProfile.getImages().size() == 0) {

                            shouldAllowBack = false;
                            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    onBackPressed();

                                }
                            });

                        } else {

                            shouldAllowBack = true;

                            for (int i = 0; i < fetchProfile.getImages().size(); i++) {
                                System.out.println("img123------------" + fetchProfile.getImages().get(i).getImgs());
                            }

                            image_rv.setVisibility(View.VISIBLE);
                            adapter = new MyPhotosAdapter(CertificateActivity.this, fetchProfile.getImages());

                            image_rv.setHasFixedSize(true);
                            image_rv.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

onBackPressed();
//                finish();

                                }
                            });

                        }


                    }

                }


            }

            @Override
            public void onFailure(Call<FetchProfile> call, Throwable t) {
                System.out.println("msg1 error new******" + t.toString());
                progressBar.dismiss();
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

    @Override
    public void onBackPressed() {

    }


}