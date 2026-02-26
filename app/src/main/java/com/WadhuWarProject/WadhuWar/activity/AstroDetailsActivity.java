package com.WadhuWarProject.WadhuWar.activity;

import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import com.WadhuWarProject.WadhuWar.model.FetchAstroLifestyle;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AstroDetailsActivity extends AppCompatActivity   implements  NetworkStateReceiver.NetworkStateReceiverListener{

    private NetworkStateReceiver networkStateReceiver;
    static boolean isNetworkAvailable;
    Spinner manglik_spinner;

    String manglik_str;
    ArrayAdapter<String> manglik_adapter;
    List<String> manglik_list;
    UserData user;
    LinearLayout update_btn;
    FetchAstroLifestyle fetchAstroLifestyle;
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
        setContentView(R.layout.activity_astro_details);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
//        setContentView(R.layout.activity_astro_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        internetOffRL  = (RelativeLayout) findViewById(R.id.internetOffRL);
        simpleProgressBar  = (ProgressBar) findViewById(R.id.simpleProgressBar);
        try_again_txt  = (TextView) findViewById(R.id.try_again_txt);
        couldnt_reach_internet_txt  = (TextView) findViewById(R.id.couldnt_reach_internet_txt);


        manglik_spinner =  findViewById(R.id.manglik_spinner);
        update_btn =  findViewById(R.id.update_btn);
        user = SharedPrefManager.getInstance(AstroDetailsActivity.this).getUser();

        manglik_list = new ArrayList<String>();
        manglik_list.add("Select Manglik or Not");
        manglik_list.add("Yes");
        manglik_list.add("No");

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

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(allDone()) {

                    if (manglik_str.contentEquals("Select Manglik or Not")) {
                        manglik_str = "";
                    }


                    updateData(String.valueOf(user.getUser_id()), manglik_str);
                }
            }
        });

        fetchManglikData();

        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/

    }


    public void updateData(String user_id,String manglik_str){



        progressBar = ProgressDialog.show(AstroDetailsActivity.this, "", "Please Wait...");

        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.editAstroLifestyle(user_id,"astro",manglik_str);


        userResponse.enqueue(new Callback<InsertResponse>() {


            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                edit_response = response.body();
                progressBar.dismiss();

                if (response.isSuccessful()) {

                    String success = edit_response.getResid();

                    if (success.equals("200")) {
                        Toast.makeText(AstroDetailsActivity.this,edit_response.getResMsg(),Toast.LENGTH_SHORT).show();

                        Intent i= new Intent(AstroDetailsActivity.this,MyProfileActivity.class);
                        startActivity(i);
                        finish();
                    }else {
                        Toast.makeText(AstroDetailsActivity.this,edit_response.getResMsg(),Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                progressBar.dismiss();

                System.out.println("err accomo******" + t.toString());

                if(!isNetworkAvailable(AstroDetailsActivity.this)){
//                    Toast.makeText(AstroDetailsActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(AstroDetailsActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }


    private Boolean allDone(){


        View selectedView_manglik_spinner= manglik_spinner.getSelectedView();

        if(selectedView_manglik_spinner==null ){
            return false;
        }



        return true;
    }

    @Override
    public void networkAvailable() {
//        fetchManglikData();

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
    public boolean isNetworkAvailable(){

        ConnectivityManager connectivityManager=(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null;
    }



    public  void fetchManglikData(){

        System.out.println("user_id111=====" + String.valueOf(user.getUser_id()));

        Api apiService = RetrofitClient.getApiService();
        Call<FetchAstroLifestyle> userResponse = apiService.fetchAstroLifestyle(String.valueOf(user.getUser_id()),"astro");
        userResponse.enqueue(new Callback<FetchAstroLifestyle>() {

            @Override
            public void onResponse(Call<FetchAstroLifestyle> call, Response<FetchAstroLifestyle> response) {
                fetchAstroLifestyle = response.body();


                /*System.out.println("fetchFamily -------" + new Gson().toJson(fetchAstroLifestyle));*/

                if (response.isSuccessful()) {
                    update_btn.setVisibility(View.VISIBLE);

                    getManglik();

                }

            }

            @Override
            public void onFailure(Call<FetchAstroLifestyle> call, Throwable t) {
                System.out.println("msg1 error fetch family******" + t.toString());

            }
        });

    }



    public  void  getManglik(){

        manglik_adapter = new ArrayAdapter<String>(AstroDetailsActivity.this, R.layout.simple_spinner_item, manglik_list);

        manglik_spinner.setAdapter(manglik_adapter);

        int pos;
        for(int i=0;i<manglik_list.size();i++) {
            if (manglik_list.get(i).contains(fetchAstroLifestyle.getManglik())) {
                pos = i;
                System.out.println("state i-----------" + pos);
                manglik_spinner.setSelection(pos);
            }
        }
        manglik_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                manglik_str = manglik_spinner.getItemAtPosition(position).toString();
                System.out.println("manglik_list------" + manglik_str);


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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