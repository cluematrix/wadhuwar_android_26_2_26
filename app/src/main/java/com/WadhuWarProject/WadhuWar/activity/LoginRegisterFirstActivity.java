package com.WadhuWarProject.WadhuWar.activity;


import static com.WadhuWarProject.WadhuWar.activity.PremiumActivity.isNetworkAvailable;

import android.Manifest;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.adapter.PhoneNumberAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.LoginData;
import com.WadhuWarProject.WadhuWar.model.newEmailData;
import com.WadhuWarProject.WadhuWar.model.newMobileData;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRegisterFirstActivity extends AppCompatActivity {
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "GoogleSignIn";
    EditText mobile;
    LoginData loginData;
    int MY_PERMISSIONS_REQUEST_CALL_PHONE = 100;
    String savedMobile;



    ProgressDialog progressBar;
    private static final int ACCOUNT_PICKER_REQUEST_CODE = 1000;
    EditText email;

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
        setContentView(R.layout.activity_login_register_first);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login_register_first);

        TextView btn = findViewById(R.id.login_text);
        Button signUpGoogleButton = findViewById(R.id.sign_up_google);
        Button signUpMobileButton = findViewById(R.id.sign_up_mobile);
        Button signUpEmailButton = findViewById(R.id.sign_up_email);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        savedMobile = SharedPrefManager.getInstance(this).getSavedMobile();


        // Configure Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginRegisterFirstActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signUpGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickAccount();
            }
        });

        signUpEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEmailDialog();
            }
        });

        signUpMobileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.READ_PHONE_NUMBERS)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) view.getContext(),
                            new String[]{Manifest.permission.READ_PHONE_NUMBERS}, 101);
                } else {
                    showPhonePickerDialog();
                }
            }
        });

//        signUpMobileButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (ActivityCompat.checkSelfPermission(LoginRegisterFirstActivity.this,
//                        Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED) {
//
//                    // Check if we can show the permission dialog
//                    if (getPermissionDeniedCount() < 2) {
//                        ActivityCompat.requestPermissions(LoginRegisterFirstActivity.this,
//                                new String[]{Manifest.permission.READ_PHONE_NUMBERS}, 101);
//                    } else {
//                        // After 2 denials, suggest going to settings
//                        Toast.makeText(LoginRegisterFirstActivity.this,
//                                "Permission permanently denied. Please enable it in Settings.",
//                                Toast.LENGTH_LONG).show();
//                        openAppSettings();
//                    }
//
//                } else {
//                    // Permission already granted
//                    showPhonePickerDialog();
//                }
//            }
//
//            // Track how many times user denied
//            private int getPermissionDeniedCount() {
//                SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
//                return prefs.getInt("permission_denied_count", 0);
//            }
//
//            private void incrementPermissionDeniedCount() {
//                SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
//                int count = prefs.getInt("permission_denied_count", 0);
//                prefs.edit().putInt("permission_denied_count", count + 1).apply();
//            }
//
//            private void openAppSettings() {
//                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                Uri uri = Uri.fromParts("package", getPackageName(), null);
//                intent.setData(uri);
//                startActivity(intent);
//            }
//        });

    }

    private void showEmailDialog()  {
        // Inflate the custom layout
        LayoutInflater inflater = LayoutInflater.from(this); // or requireContext() if in Fragment
        View dialogView = inflater.inflate(R.layout.dialog_input_email, null);

        EditText editTextEmailInput = dialogView.findViewById(R.id.editTextEmailInput);

        AlertDialog.Builder builder = new AlertDialog.Builder(this); // or requireContext() if in Fragment
        builder.setTitle("Email ");
        builder.setView(dialogView);

        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String input = editTextEmailInput.getText().toString();
                requestOtpEmail(input);
            }
        });

        builder.setNegativeButton("Cancel", null);

        builder.create().show();
    }




    private void showInputDialog() {
        // Inflate the custom layout
        LayoutInflater inflater = LayoutInflater.from(this); // or requireContext() if in Fragment
        View dialogView = inflater.inflate(R.layout.dialog_input, null);

        EditText editTextInput = dialogView.findViewById(R.id.editTextInput);

        AlertDialog.Builder builder = new AlertDialog.Builder(this); // or requireContext() if in Fragment
        builder.setTitle("Mobile Number");
        builder.setView(dialogView);

        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                String input = editTextInput.getText().toString();
//                requestOtp(input);
                String input = editTextInput.getText().toString().trim();
                input = input.replaceAll("[^0-9]", "");
                if (input.length() > 10) {
                    input = input.substring(input.length() - 10);
                }
                if (input.length() != 10) {
                    Toast.makeText(LoginRegisterFirstActivity.this,
                            "Please enter valid 10 digit mobile number",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                requestOtp(input);

            }
        });

        builder.setNegativeButton("Cancel", null);

        builder.create().show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                showPhonePickerDialog();
            } else {
                // Permission denied – track it
                incrementPermissionDeniedCount();

                Toast.makeText(this,
                        "Phone permission is required. Please enable it in app settings.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void incrementPermissionDeniedCount() {
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        int count = prefs.getInt("permission_denied_count", 0);
        prefs.edit().putInt("permission_denied_count", count + 1).apply();
    }

    private int getPermissionDeniedCount() {
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        return prefs.getInt("permission_denied_count", 0);
    }




//    private void showPhonePickerDialog() {
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View dialogView = inflater.inflate(R.layout.dialog_phone_login, null);
//        RecyclerView recyclerView = dialogView.findViewById(R.id.contacts);
//        Button noneOfTheAboveButton = dialogView.findViewById(R.id.btn_none_of_the_above);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        List<String> phoneNumbers = getPhoneNumbers();
//        if (phoneNumbers == null || phoneNumbers.isEmpty()) {
//            phoneNumbers = new ArrayList<>();
//            phoneNumbers.add("No phone numbers available");
//        }
//
//
//        dialogView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
//
//        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
//        builder.setView(dialogView)
//                .setCancelable(false);
//        AlertDialog dialog = builder.create();
//
//        if (dialog.getWindow() != null) {
//            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        }
//
//        noneOfTheAboveButton.setOnClickListener(v -> {
//            noneOfTheAboveButton.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
//            showInputDialog();
//            dialog.dismiss();
////            showAlternativeLoginDialog();
//        });
//
//        for (String contact:phoneNumbers) {
//            if (contact.equals("No SIM detected") || contact.equals("No phone numbers available")){
//                noneOfTheAboveButton.setText("Try Another Way");
//            }
//
//        }
//
//        PhoneNumberAdapter adapter = new PhoneNumberAdapter(phoneNumbers, selectedNumber -> {
//            if (mobile != null) {
//
//                if (selectedNumber.equals("No SIM detected")){
//                    Toast.makeText(LoginRegisterFirstActivity.this, "No SIM detected", Toast.LENGTH_SHORT).show();
//                    dialogView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
//                    dialog.dismiss();
//                    return null;
//
//                }else if (selectedNumber.equals("No phone numbers available")){
//
//                    Toast.makeText(LoginRegisterFirstActivity.this, "No phone numbers available", Toast.LENGTH_SHORT).show();
//                    dialogView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
//                    dialog.dismiss();
//                    return null;
//                }else {
//
//                    if (selectedNumber != null && selectedNumber.length() == 10 ) {
//                        mobile.setText(selectedNumber);
//                        dialogView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
//                        requestOtp(selectedNumber);
////                        recyclerView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
//                        dialog.dismiss();
//                    } else{
//                        mobile.setText(selectedNumber.substring(2)); // Remove '91'
//                        dialogView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
//                        requestOtp(selectedNumber);
//
////                        recyclerView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
//                        dialog.dismiss();
//                    }
//
////                    mobile.setText(selectedNumber);
////                    mobile.setText(selectedNumber.replace("91",""));
////                    mobile.setText( selectedNumber.substring(2));
////                    dialogView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
////                    dialog.dismiss();
//
//                    // Toast.makeText(LoginActivity.this, "Selected: " + selectedNumber, Toast.LENGTH_SHORT).show();
//                }
//            }
//            return null;
//        });
//        recyclerView.setAdapter(adapter);
//
//        dialog.show();
//    }

private void showPhonePickerDialog() {
    LayoutInflater inflater = LayoutInflater.from(this);
    View dialogView = inflater.inflate(R.layout.dialog_phone_login, null);
    RecyclerView recyclerView = dialogView.findViewById(R.id.contacts);
    Button noneOfTheAboveButton = dialogView.findViewById(R.id.btn_none_of_the_above);

    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    List<String> phoneNumbers = getPhoneNumbers();
    if (phoneNumbers == null || phoneNumbers.isEmpty()) {
        phoneNumbers = new ArrayList<>();
        phoneNumbers.add("No phone numbers available");
    }


    dialogView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));

    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
    builder.setView(dialogView)
            .setCancelable(false);
    AlertDialog dialog = builder.create();

    if (dialog.getWindow() != null) {
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    noneOfTheAboveButton.setOnClickListener(v -> {
        noneOfTheAboveButton.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
        showInputDialog();
        dialog.dismiss();
//            showAlternativeLoginDialog();
    });

    for (String contact:phoneNumbers) {
        if (contact.equals("No SIM detected") || contact.equals("No phone numbers available")){
            noneOfTheAboveButton.setText("Try Another Way");
        }

    }

    PhoneNumberAdapter adapter = new PhoneNumberAdapter(phoneNumbers, selectedNumber -> {
        if (mobile != null) {

            if (selectedNumber.equals("No SIM detected")){
                Toast.makeText(LoginRegisterFirstActivity.this, "No SIM detected", Toast.LENGTH_SHORT).show();
                dialogView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
                dialog.dismiss();
                return null;

            }else if (selectedNumber.equals("No phone numbers available")){

                Toast.makeText(LoginRegisterFirstActivity.this, "No phone numbers available", Toast.LENGTH_SHORT).show();
                dialogView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
                dialog.dismiss();
                return null;
            }else {

                if (selectedNumber != null && selectedNumber.length() == 10 ) {
                    mobile.setText(selectedNumber);
                    dialogView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
//                        requestOtp(selectedNumber);
                    requestOtpNumber(selectedNumber);
//                        recyclerView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
                    dialog.dismiss();
                } else{
                    mobile.setText(selectedNumber.substring(2)); // Remove '91'
                    dialogView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
//                        requestOtp(selectedNumber);
                    requestOtpNumber(selectedNumber);

//                        recyclerView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
                    dialog.dismiss();
                }

//                    mobile.setText(selectedNumber);
//                    mobile.setText(selectedNumber.replace("91",""));
//                    mobile.setText( selectedNumber.substring(2));
//                    dialogView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
//                    dialog.dismiss();

                // Toast.makeText(LoginActivity.this, "Selected: " + selectedNumber, Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    });
    recyclerView.setAdapter(adapter);

    dialog.show();
}

    private void requestOtpNumber(String input) {
        progressBar = ProgressDialog.show(LoginRegisterFirstActivity.this, "", "Please Wait...");

        Api apiService = RetrofitClient.getApiService();
        Call<LoginData> userResponse = apiService.login(input);
        userResponse.enqueue(new Callback<LoginData>() {

            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {

                progressBar.dismiss();

                loginData = response.body();
                if (response.isSuccessful()) {


                    if (loginData.getResid().equals("200")) {

                        String otp = String.valueOf(loginData.getOTP());
//                        Toast.makeText(LoginActivity.this, "otp" + otp, Toast.LENGTH_SHORT).show();
                        String user_id = String.valueOf(loginData.getUser_id());

                        Toast.makeText(LoginRegisterFirstActivity.this,loginData.getResMsg(),Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoginRegisterFirstActivity.this, OTPVerificationActivity.class);
                        System.out.println("otp-----------" + loginData.getOTP());
                        i.putExtra("otp", otp);
                        i.putExtra("user_id", user_id);
                        i.putExtra("mobile", input);
                        startActivity(i);

                    }
                    //deactivate account
                    else if(loginData.getResid().equals("203")){

                        /*dialog apply*/

                        final Dialog dialog = new Dialog(LoginRegisterFirstActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.login_admin_box);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                        TextView cancel_txt =  dialog.findViewById(R.id.cancel_txt);
                        TextView call_btn =  dialog.findViewById(R.id.call_btn);
                        TextView whatapp_txt =  dialog.findViewById(R.id.whatapp_txt);


                        LinearLayout call_LL =  dialog.findViewById(R.id.call_LL);

                        call_LL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent it = new Intent(Intent.ACTION_CALL);
                                it.setData(Uri.parse("tel:"+ "9420944984"));


                                if (ContextCompat.checkSelfPermission(LoginRegisterFirstActivity.this,
                                        Manifest.permission.CALL_PHONE)
                                        != PackageManager.PERMISSION_GRANTED) {

                                    ActivityCompat.requestPermissions(LoginRegisterFirstActivity.this,
                                            new String[]{Manifest.permission.CALL_PHONE},
                                            MY_PERMISSIONS_REQUEST_CALL_PHONE);


                                } else {
                                    //You already have permission
                                    try {
                                        startActivity(it);
                                    } catch(SecurityException e) {
                                        e.printStackTrace();
                                    }
                                }

                                dialog.dismiss();
                                dialog.cancel();
                            }
                        });

                        cancel_txt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                dialog.cancel();
                            }
                        });

                        call_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent it = new Intent(Intent.ACTION_CALL);
                                it.setData(Uri.parse("tel:"+ "9420944984"));


                                if (ContextCompat.checkSelfPermission(LoginRegisterFirstActivity.this,
                                        Manifest.permission.CALL_PHONE)
                                        != PackageManager.PERMISSION_GRANTED) {

                                    ActivityCompat.requestPermissions(LoginRegisterFirstActivity.this,
                                            new String[]{Manifest.permission.CALL_PHONE},
                                            MY_PERMISSIONS_REQUEST_CALL_PHONE);


                                } else {
                                    //You already have permission
                                    try {
                                        startActivity(it);
                                    } catch(SecurityException e) {
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

                                String phoneNumber = "91"+whatapp_no; //without '+'

                                Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" );

                                Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);

                                startActivity(sendIntent);
                            }
                        });



                        dialog.show();




                        /*dialog apply*/
                    }

                    //delete account
                    else if(loginData.getResid().equals("204")){

                        /*dialog apply*/

                        final Dialog dialog = new Dialog(LoginRegisterFirstActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.login_delete_admin_box);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                        TextView cancel_txt =  dialog.findViewById(R.id.cancel_txt);
                        TextView call_btn =  dialog.findViewById(R.id.call_btn);
                        TextView whatapp_txt =  dialog.findViewById(R.id.whatapp_txt);


                        LinearLayout call_LL =  dialog.findViewById(R.id.call_LL);

                        call_LL.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent it = new Intent(Intent.ACTION_CALL);
                                it.setData(Uri.parse("tel:"+ "9420944984"));


                                if (ContextCompat.checkSelfPermission(LoginRegisterFirstActivity.this,
                                        Manifest.permission.CALL_PHONE)
                                        != PackageManager.PERMISSION_GRANTED) {

                                    ActivityCompat.requestPermissions(LoginRegisterFirstActivity.this,
                                            new String[]{Manifest.permission.CALL_PHONE},
                                            MY_PERMISSIONS_REQUEST_CALL_PHONE);


                                } else {
                                    //You already have permission
                                    try {
                                        startActivity(it);
                                    } catch(SecurityException e) {
                                        e.printStackTrace();
                                    }
                                }

                                dialog.dismiss();
                                dialog.cancel();
                            }
                        });

                        cancel_txt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                dialog.cancel();
                            }
                        });

                        call_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent it = new Intent(Intent.ACTION_CALL);
                                it.setData(Uri.parse("tel:"+ "9420944984"));


                                if (ContextCompat.checkSelfPermission(LoginRegisterFirstActivity.this,
                                        Manifest.permission.CALL_PHONE)
                                        != PackageManager.PERMISSION_GRANTED) {

                                    ActivityCompat.requestPermissions(LoginRegisterFirstActivity.this,
                                            new String[]{Manifest.permission.CALL_PHONE},
                                            MY_PERMISSIONS_REQUEST_CALL_PHONE);


                                } else {
                                    //You already have permission
                                    try {
                                        startActivity(it);
                                    } catch(SecurityException e) {
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

                                String phoneNumber = "91"+whatapp_no; //without '+'

                                Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" );

                                Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);

                                startActivity(sendIntent);
                            }
                        });

                        dialog.show();


                        /*dialog apply*/
                    }else {
                        if (savedMobile.equals(input)){
                            Intent intent = new Intent(LoginRegisterFirstActivity.this, RegistrationActivity.class);
                            intent.putExtra("mobile", input);
                            startActivity(intent);
                            finish();
                        } else{
                            requestOtpReg(input);
                        }

//                        Toast.makeText(LoginActivity.this,loginData.getResMsg(),Toast.LENGTH_SHORT).show();

                    }
                }
            }

            private void requestOtpReg(String mobile) {
                progressBar = ProgressDialog.show(LoginRegisterFirstActivity.this, "", "Please Wait...");

                Api apiService = RetrofitClient.getApiService();
                Call<newMobileData> userResponse = apiService.newRegMobile(mobile);

                userResponse.enqueue(new Callback<newMobileData>() {
                    @Override
                    public void onResponse(Call<newMobileData> call, Response<newMobileData> response) {
                        progressBar.dismiss();

                        if (response.isSuccessful() && response.body() != null) {
                            newMobileData loginData = response.body();
                            String otp = String.valueOf(loginData.getOTP());
                            String user_id = String.valueOf(loginData.getUserId());
                            String reg_msg = String.valueOf(loginData.getResMsg());
                            String reg_id = String.valueOf(loginData.getResid());
                            Intent i = new Intent(LoginRegisterFirstActivity.this, MobileOtpVarifyActivity.class);
                            i.putExtra("otp", otp);
                            i.putExtra("mobile", mobile);
                            i.putExtra("user_id", user_id);
                            i.putExtra("reg_msg", reg_msg);
                            Log.d("otp", "onResponse: " + otp);
//                        Toast.makeText(LoginRegisterFirstActivity.this, loginData.getResMsg(), Toast.LENGTH_SHORT).show();
                            startActivity(i);

                        } else {
                            Toast.makeText(LoginRegisterFirstActivity.this, "Response Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<newMobileData> call, Throwable t) {
                        progressBar.dismiss();
                        Toast.makeText(LoginRegisterFirstActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                progressBar.dismiss();

                System.out.println("error login------" + t.toString());

                if(!isNetworkAvailable(LoginRegisterFirstActivity.this)){
                    Toast.makeText(LoginRegisterFirstActivity.this, "Please check internet connection!", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(LoginRegisterFirstActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();

                }

            }
        });





//        Intent i = new Intent(LoginActivity.this, OTPVerificationActivity.class);
//        startActivity(i);


    }



    public void requestOtp(String mobile) {
        progressBar = ProgressDialog.show(LoginRegisterFirstActivity.this, "", "Please Wait...");

        Api apiService = RetrofitClient.getApiService();
        Call<newMobileData> userResponse = apiService.newRegMobile(mobile);

        userResponse.enqueue(new Callback<newMobileData>() {
            @Override
            public void onResponse(Call<newMobileData> call, Response<newMobileData> response) {
                progressBar.dismiss();

                if (response.isSuccessful() && response.body() != null) {
                    newMobileData loginData = response.body();
                    String otp = String.valueOf(loginData.getOTP());
                    String user_id = String.valueOf(loginData.getUserId());
                    String reg_msg = String.valueOf(loginData.getResMsg());
                    String reg_id = String.valueOf(loginData.getResid());
                    Intent i = new Intent(LoginRegisterFirstActivity.this, MobileOtpVarifyActivity.class);
                    i.putExtra("otp", otp);
                    i.putExtra("mobile", mobile);
                    i.putExtra("user_id", user_id);
                    i.putExtra("reg_msg", reg_msg);
                    Log.d("otp", "onResponse: " + otp);
//                        Toast.makeText(LoginRegisterFirstActivity.this, loginData.getResMsg(), Toast.LENGTH_SHORT).show();
                    startActivity(i);

                } else {
                    Toast.makeText(LoginRegisterFirstActivity.this, "Response Error", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<newMobileData> call, Throwable t) {
                progressBar.dismiss();
                Toast.makeText(LoginRegisterFirstActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }




    // Get phone numbers from contacts and show in dialog by sagar on 9 April 2025
    private List<String> getPhoneNumbers() {
        List<String> numbers = new ArrayList<>();
        String simNumber = getPhoneNumber();
        if (simNumber != null) {
            numbers.add(simNumber);
        } else {
            numbers.add("No SIM detected");
        }
        return numbers;
    }

    @SuppressLint("HardwareIds")
    private String getPhoneNumber() {
        TelephonyManager telephonyManager =
                (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS)
                != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        return telephonyManager.getLine1Number();
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            handleSignInResult(task);
//        }
//    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                String email = account.getEmail();
                Toast.makeText(this, "Signed in as: " + email, Toast.LENGTH_LONG).show();
                Log.d(TAG, "Google Sign-In successful. Email: " + email);
                Intent intent = new Intent(this, RegistrationActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Failed to retrieve account details", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "Account is null");
            }
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode(), e);
            Toast.makeText(this, "Sign-in failed. Code: " + e.getStatusCode(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACCOUNT_PICKER_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                if (accountName != null) {
                    email.setText(accountName);
                    requestOtpEmail(accountName);
                    Log.d("email", "onActivityResult: " + accountName);
                    Toast.makeText(this, "Email" + accountName, Toast.LENGTH_SHORT).show();
//                    autoFillFormFirstData();
                } else {
//                    autoFillFormFirstData();
                    Toast.makeText(LoginRegisterFirstActivity.this, "No account selected", Toast.LENGTH_SHORT).show();
                }
            } else {
//                autoFillFormFirstData();
                Toast.makeText(LoginRegisterFirstActivity.this, "Account selection canceled", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == RC_SIGN_IN) {
            // Google Sign-In
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    public void requestOtpEmail(String email) {
        progressBar = ProgressDialog.show(LoginRegisterFirstActivity.this, "", "Please Wait...");

        Api apiService = RetrofitClient.getApiService();
        Call<newEmailData> userResponse = apiService.newEmail(email);

        userResponse.enqueue(new Callback<newEmailData>() {
            @Override
            public void onResponse(Call<newEmailData> call, Response<newEmailData> response) {
                progressBar.dismiss();


                if (response.isSuccessful() && response.body() != null) {
                    newEmailData loginEmailData = response.body();
                    String otp = String.valueOf(loginEmailData.getOTP());
                    String user_id = String.valueOf(loginEmailData.getUserId());
                    String reg_msg = String.valueOf(loginEmailData.getResMsg());
                    String reg_id = String.valueOf(loginEmailData.getResid());
                    Intent i = new Intent(LoginRegisterFirstActivity.this, EmailOtpVarifyActivity.class);
                    i.putExtra("otp", otp);
                    i.putExtra("email", email);
                    i.putExtra("user_id", user_id);
                    i.putExtra("reg_msg", reg_msg);
                    Log.d("otp", "onResponse: " + otp);
                    Toast.makeText(LoginRegisterFirstActivity.this, "Please check your Email , for Verification Code !", Toast.LENGTH_SHORT).show();
                    startActivity(i);
                } else {
                    Toast.makeText(LoginRegisterFirstActivity.this, "Response Error", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<newEmailData> call, Throwable t) {
                progressBar.dismiss();
                Toast.makeText(LoginRegisterFirstActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void pickAccount() {
        Intent intent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            intent = AccountManager.newChooseAccountIntent(
                    null, // No pre-selected account
                    null, // No allowed account types
                    new String[]{"com.google"}, // Filter to Google accounts
                    null, // Description text
                    null, // Auth token type
                    null, // Add account required features
                    null  // Add account options bundle
            );
        }

        startActivityForResult(intent, ACCOUNT_PICKER_REQUEST_CODE);
    }
}