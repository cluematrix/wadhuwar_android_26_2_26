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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
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

public class FamilyInDetailActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener{
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

    ImageView father_mo_lock,mother_mo_lock,mama_mo_lock;

    TextView father_name,mother_name,mama_name,mamKul,father_mo,mother_mo,mama_mo,father_occucpation,mother_occupation,mama_occupation,no_bro_sis,sibling_names,
            married_sibling,user_marital_status,no_of_user_children,user_children_living_Status,user_children_age;


    CardView user_marital_child_cv;
    LinearLayout user_marital_heading,contact_connect_now_btn_LL,contact_connect_now_btn;

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
        setContentView(R.layout.activity_family_in_detail);

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
//        setContentView(R.layout.activity_family_in_detail);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);

        contact_connect_now_btn =  findViewById(R.id.contact_connect_now_btn);
        contact_connect_now_btn_LL =  findViewById(R.id.contact_connect_now_btn_LL);
        mama_mo_lock =  findViewById(R.id.mama_mo_lock);
        mother_mo_lock =  findViewById(R.id.mother_mo_lock);
        father_mo_lock =  findViewById(R.id.father_mo_lock);
        user_marital_child_cv =  findViewById(R.id.user_marital_child_cv);
        user_marital_heading =  findViewById(R.id.user_marital_heading);
        user_children_age =  findViewById(R.id.user_children_age);
        user_children_living_Status =  findViewById(R.id.user_children_living_Status);
        no_of_user_children =  findViewById(R.id.no_of_user_children);
        user_marital_status =  findViewById(R.id.user_marital_status);
        father_name =  findViewById(R.id.father_name);
        mother_name =  findViewById(R.id.mother_name);
        mama_name =  findViewById(R.id.mama_name);
        mamKul =  findViewById(R.id.mamKul);
        father_mo =  findViewById(R.id.father_mo);
        mother_mo =  findViewById(R.id.mother_mo);
        mama_mo =  findViewById(R.id.mama_mo);
        father_occucpation =  findViewById(R.id.father_occucpation);
        mother_occupation =  findViewById(R.id.mother_occupation);
        mama_occupation =  findViewById(R.id.mama_occupation);
        no_bro_sis =  findViewById(R.id.no_bro_sis);
        sibling_names =  findViewById(R.id.sibling_names);
        married_sibling =  findViewById(R.id.married_sibling);
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

        user = SharedPrefManager.getInstance(FamilyInDetailActivity.this).getUser();



        if(fetchProfileofUsers.getMarital_status_name().equals("Devorce") ||
                fetchProfileofUsers.getMarital_status_name().equals("Widow/Widower")){


            user_marital_heading.setVisibility(View.VISIBLE);
            user_marital_child_cv.setVisibility(View.VISIBLE);
            user_marital_status.setText("Marital Status: " + fetchProfileofUsers.getMarital_status_name());
            no_of_user_children.setText(fetchProfileofUsers.getNo_of_child());
            user_children_living_Status.setText(fetchProfileofUsers.getChild_stay());
            user_children_age.setText(fetchProfileofUsers.getAge_of_child());
        }else{
            user_marital_heading.setVisibility(View.GONE);
            user_marital_child_cv.setVisibility(View.GONE);
        }





        father_name.setText(fetchProfileofUsers.getFather_name());
        mother_name.setText(fetchProfileofUsers.getMother_name());
        mama_name.setText(fetchProfileofUsers.getMama_name());
        mamKul.setText(fetchProfileofUsers.getMamekul());
        father_occucpation.setText(fetchProfileofUsers.getFather_occ_name());
        mother_occupation.setText(fetchProfileofUsers.getMother_occ());
        mama_occupation.setText(fetchProfileofUsers.getMama_occ_name());
        no_bro_sis.setText("Brother: "+ fetchProfileofUsers.getBro_count() + " & "+"Sister: " + fetchProfileofUsers.getSis_count() );
        sibling_names.setText(fetchProfileofUsers.getSibling_names());
        married_sibling.setText(fetchProfileofUsers.getMarried_sibling());





        if(fetchProfileofUsers.getIs_connected().equals("1")){
            father_mo_lock.setVisibility(View.GONE);
            father_mo.setText(fetchProfileofUsers.getFather_mobile());

            mother_mo_lock.setVisibility(View.GONE);
            mother_mo.setText(fetchProfileofUsers.getMother_mobile());

            mama_mo_lock.setVisibility(View.GONE);
            mama_mo.setText(fetchProfileofUsers.getMama_mobile());

            contact_connect_now_btn_LL.setVisibility(View.GONE);

        }else {
            father_mo_lock.setVisibility(View.VISIBLE);
            father_mo.setText("******");

            mother_mo_lock.setVisibility(View.VISIBLE);
            mother_mo.setText("******");

            mama_mo_lock.setVisibility(View.VISIBLE);
            mama_mo.setText("******");



            contact_connect_now_btn_LL.setVisibility(View.VISIBLE);
            contact_connect_now_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(FamilyInDetailActivity.this,"Connect Now Click ",Toast.LENGTH_SHORT).show();
                }
            });

        }





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