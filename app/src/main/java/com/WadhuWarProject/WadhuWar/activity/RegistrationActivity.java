package com.WadhuWarProject.WadhuWar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.fragments.BasicInformationFragment;

public class RegistrationActivity extends AppCompatActivity  implements NetworkStateReceiver.NetworkStateReceiverListener {


    private String username;
    static boolean isNetworkAvailable;
    private NetworkStateReceiver networkStateReceiver;
    LinearLayout progress_num;
    Context context;
    public final Fragment fragment1 = new BasicInformationFragment();
    public final FragmentManager fm = getSupportFragmentManager();
    Fragment active;



    static TextView connection_txt;
    static BroadcastReceiver mNetworkReceiver;
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
        setContentView(R.layout.activity_registration);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_registration);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);

        internetOffRL  = (RelativeLayout) findViewById(R.id.internetOffRL);
        simpleProgressBar  = (ProgressBar) findViewById(R.id.simpleProgressBar);

        simpleProgressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#EC5252"),android.graphics.PorterDuff.Mode.MULTIPLY);


        try_again_txt  = (TextView) findViewById(R.id.try_again_txt);
        couldnt_reach_internet_txt  = (TextView) findViewById(R.id.couldnt_reach_internet_txt);

        fm.beginTransaction().add(R.id.profile_edit_framelayout, fragment1, "1").commit();
        active = fragment1;

        context = this.getApplicationContext();

        /*internet code*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener((NetworkStateReceiver.NetworkStateReceiverListener) this);
        this.registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        /*end internet code*/

    }


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

    public boolean isNetworkAvailable(){

        ConnectivityManager connectivityManager=(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null;
    }


    public void onDestroy() {
        super.onDestroy();
        networkStateReceiver.removeListener(this);
        this.unregisterReceiver(networkStateReceiver);
    }



    /* End code  Registration successfull or not*/
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
        super.onBackPressed();
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handleExit();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void handleExit() {
        super.onBackPressed();
        finish();
    }



}