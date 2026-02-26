package com.WadhuWarProject.WadhuWar.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.WadhuWarProject.WadhuWar.applications.MyApplication;
import com.WadhuWarProject.WadhuWar.model.DeleteProfile;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.UserData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountSettingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    UserData userData;

    Toolbar toolbar;
    ProgressDialog progressBar;

    RelativeLayout alertsRL,mobileRL,emailRL,shadi_meetRL,contact_filter_RL,verification_badge_RL,date_birth_RL,hide_delete_profile,
            logoutRL,deleteRL,hide_profile,unhide_profile;

    UserData user;
    DeleteProfile deleteResponse;

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
        setContentView(R.layout.activity_account_setting);

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
//        setContentView(R.layout.activity_account_setting);
        unhide_profile = findViewById(R.id.unhide_profile);
        hide_profile= findViewById(R.id.hide_profile);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        alertsRL =  findViewById(R.id.alertsRL);
        mobileRL =  findViewById(R.id.mobileRL);
        emailRL =  findViewById(R.id.emailRL);
        shadi_meetRL =  findViewById(R.id.shadi_meetRL);
        contact_filter_RL =  findViewById(R.id.contact_filter_RL);
        verification_badge_RL =  findViewById(R.id.verification_badge_RL);
        date_birth_RL =  findViewById(R.id.date_birth_RL);
        hide_delete_profile =  findViewById(R.id.hide_delete_profile);
        logoutRL =  findViewById(R.id.logoutRL);
        deleteRL =  findViewById(R.id.deleteRL);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Account Setting");

        user = SharedPrefManager.getInstance(AccountSettingActivity.this).getUser();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        hide_profile.setOnClickListener(v -> {
            hideProfilehide(String.valueOf(user.getUser_id()),2);
        });
        unhide_profile.setOnClickListener(v -> {
            hideProfilehide(String.valueOf(user.getUser_id()),0);
        });

        alertsRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AccountSettingActivity.this, AlertActivity.class);
                startActivity(i);
            }
        });

        mobileRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AccountSettingActivity.this, MobileEditActivity.class);
                startActivity(i);
            }
        });

        emailRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AccountSettingActivity.this);

                View viewInflated = LayoutInflater.from(AccountSettingActivity.this).inflate(R.layout.email_edit_dialog_box, null, false);
                final EditText email = (EditText) viewInflated.findViewById(R.id.email);

                builder.setView(viewInflated);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        m_Text = mobile.getText().toString();
                        System.out.println("email-------" +  email.getText().toString());


                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });



        shadi_meetRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AccountSettingActivity.this, ShaadiMeetActivity.class);
                startActivity(i);
            }
        });

        contact_filter_RL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AccountSettingActivity.this, ContactFiltersActivity.class);
                startActivity(i);
            }
        });



        verification_badge_RL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AccountSettingActivity.this);

                View viewInflated = LayoutInflater.from(AccountSettingActivity.this).inflate(R.layout.verification_badge_dialog_box, null, false);

                builder.setView(viewInflated);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        m_Text = mobile.getText().toString();


                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });


        date_birth_RL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AccountSettingActivity.this);

                View viewInflated = LayoutInflater.from(AccountSettingActivity.this).inflate(R.layout.date_birth_dialog_box, null, false);

                builder.setView(viewInflated);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        m_Text = mobile.getText().toString();


                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });



        hide_delete_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AccountSettingActivity.this, HideDeleteActivity.class);
                startActivity(i);
            }
        });



        logoutRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(AccountSettingActivity.this)
                        .setTitle("Logout App")
                        .setMessage("Are you sure you want to logout this app?")

                        .setPositiveButton("Logout ", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {


                                /*--------*/

                                setStatusUse("0");

                                /*--------*/




                            }
                        })
                        .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // dismiss dialog here because user doesn't want to logout
                            }
                        })
                        .show();
            }
        });



        deleteRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(AccountSettingActivity.this)
                        .setTitle("Confirm Delete Profile")
                        .setMessage("Are you sure you want to delete your profile!")

                        .setPositiveButton("Delete ", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                SharedPrefManager.getInstance(AccountSettingActivity.this).clear();

                                Api apiService = RetrofitClient.getApiService();
                                Call<DeleteProfile> deleteProfile = apiService.deleteProfile(String.valueOf(user.getUser_id()));
                                deleteProfile.enqueue(new Callback<DeleteProfile>() {

                                    @Override
                                    public void onResponse(Call<DeleteProfile> call, Response<DeleteProfile> response) {

                                        deleteResponse = response.body();

                                        if (response.isSuccessful()) {

                                            if(deleteResponse.getResid().equals("200")){
                                                Toast.makeText(AccountSettingActivity.this, deleteResponse.getResMsg(), Toast.LENGTH_SHORT).show();

//                                                Intent intent = new Intent(AccountSettingActivity.this, LoginActivity.class);
                                                Intent intent = new Intent(AccountSettingActivity.this, LoginRegisterFirstActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);

                                            }else{
                                                Toast.makeText(AccountSettingActivity.this, deleteResponse.getResMsg(),Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<DeleteProfile> call, Throwable t) {

                                    }
                                });
                            }
                        })
                        .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // dismiss dialog here because user doesn't want to logout
                            }
                        }).show();
            }
        });

// for visibility, when hide the visibility gone to unhide
        String isHidden1 = SharedPrefManager.getInstance(AccountSettingActivity.this).getString();
        if (isHidden1.equals("2")){
            unhide_profile.setVisibility(View.VISIBLE);
            hide_profile.setVisibility(View.GONE);
        }else {
            unhide_profile.setVisibility(View.GONE);
            hide_profile.setVisibility(View.VISIBLE);
        }
    }

    // profile hide
    private void hideProfilehide(String userId,int hide) {
        progressBar = ProgressDialog.show(AccountSettingActivity.this, "", "Please Wait...");

        Api apiService = RetrofitClient.getApiService();
        Call<ResponseBody> userResponse = apiService.hideProfile(userId, hide);

        userResponse.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    progressBar.dismiss();
                    if (hide == 2){
                        SharedPrefManager.getInstance(AccountSettingActivity.this).saveString("0");
                        Toast.makeText(AccountSettingActivity.this,"Hide !", Toast.LENGTH_SHORT).show();
                    }else {
                        SharedPrefManager.getInstance(AccountSettingActivity.this).saveString("2");
                        Toast.makeText(AccountSettingActivity.this,"Unhide !", Toast.LENGTH_SHORT).show();
                    }
                    hide_profile.setVisibility(hide_profile.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);

                    unhide_profile.setVisibility(unhide_profile.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);

//                    finish();
                }else {
                    progressBar.dismiss();
                    Toast.makeText(AccountSettingActivity.this,"Unable To hide",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.dismiss();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public  void  setStatusUse(String status){

        userData = SharedPrefManager.getInstance(AccountSettingActivity.this).getUser();

        System.out.println("user id======="  + userData.getUser_id());

        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.setStatusOnline(String.valueOf(userData.getUser_id()),status);
        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                if (response.isSuccessful()) {
//                    System.out.println("1111status resp-----------" +status+ "-------->" +  new Gson().toJson(response.body()));


                    if(response.body().getResid().equals("200")){


                        SharedPrefManager.getInstance(AccountSettingActivity.this).clear();

//                        Intent intent = new Intent(AccountSettingActivity.this, LoginActivity.class);
                        Intent intent = new Intent(AccountSettingActivity.this, LoginRegisterFirstActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else{

                        Toast.makeText(AccountSettingActivity.this,"Please try again!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {

                System.out.println("1111chat status******" + t.toString());

            }
        });
    }

}