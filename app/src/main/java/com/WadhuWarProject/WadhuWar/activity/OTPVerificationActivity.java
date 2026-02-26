package com.WadhuWarProject.WadhuWar.activity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.Receiver.SmsBroadcastReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.LoginData;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OTPVerificationActivity extends AppCompatActivity implements
        NetworkStateReceiver.NetworkStateReceiverListener {

    SmsBroadcastReceiver smsBroadcastReceiver;
    private int RESOLVE_HINT = 2;
    UserData userData;

    static boolean isNetworkAvailable;
    ArrayList<EditText> otpEdtTxtFields;
    ProgressDialog progressBar;
    EditText otp_edt_1;
    UserData otpUserData;
    TextView resendOtpTxt;
    TextView verificationTitleText, submit_btn_txt;
    ProgressBar progress_bar;
    LinearLayout otpLayout, submit_button;
    private int CURRENT_EMPTY_POS = 0;
    public String VERIFICATION_ID = null;
    ProgressDialog progressDialog;
    public String OTP_LOGIN = null;
    public String MOBILE_NO = null;
    public String USER_ID = null;
    LoginData loginData;


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
        setContentView(R.layout.activity_otp_verification);

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
//        setContentView(R.layout.activity_otp_verification);
        verificationTitleText = findViewById(R.id.otp_verification_title);
        resendOtpTxt = findViewById(R.id.otp_resend_btn_txt);
        otpLayout = findViewById(R.id.otp_otp_layout);
        submit_button = findViewById(R.id.submit_button);
        otp_edt_1 = findViewById(R.id.otp_edt_1);
        progress_bar = findViewById(R.id.progress_bar);
        submit_btn_txt = findViewById(R.id.submit_btn_txt);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");

        progress_bar.getIndeterminateDrawable().setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.MULTIPLY);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            OTP_LOGIN = extras.getString("otp");
            MOBILE_NO = extras.getString("mobile");
            USER_ID = extras.getString("user_id");
        }

        System.out.println("mobile n --------" + MOBILE_NO);
        System.out.println("_otp n --------" + OTP_LOGIN);

        System.out.println("otp new 000-------" + VERIFICATION_ID);


        char[] mobCharArrays = MOBILE_NO.toCharArray();

        String encodedMobString = "";
        for (int i = 0; i < mobCharArrays.length; i++) {

            // characters visible from last 3rd character
            if (i > (mobCharArrays.length - 4)) {
                encodedMobString += mobCharArrays[i];
            }
            // else XXX characters would be set
            else {
                encodedMobString += "X";
            }

        }
        verificationTitleText.setText(getResources().getString(R.string.please_enter_the_otp_we) + encodedMobString);


        resendOtpTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOtp(MOBILE_NO);
            }
        });


        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_otp = otp_edt_1.getText().toString();

                System.out.println("otp new 222-------" + VERIFICATION_ID);
//                Intent i = new Intent(OTPVerificationActivity.this, MainActivity.class);
//                startActivity(i);


                if (user_otp.equals(OTP_LOGIN)) {

                    if (!isNetworkAvailable(OTPVerificationActivity.this)) {


                        Snackbar mSnackbar = Snackbar.make(v, "Please check internet Connection!", Snackbar.LENGTH_LONG);
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

                        FirebaseApp.initializeApp(OTPVerificationActivity.this);

                        FirebaseMessaging.getInstance().getToken()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        String token = task.getResult();
                                        Log.d("FCM_TOKEN", token);
                                    }else{
                                        Toast.makeText(OTPVerificationActivity.this, "text", Toast.LENGTH_SHORT).show();
                                    }
                                });


                        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> {
                            if (!TextUtils.isEmpty(token)) {

                                Log.d("11111111", "retrieve token successful : " + token);
                                String token11 = String.valueOf(FirebaseMessaging.getInstance().getToken());
                                otpVerification(token, USER_ID);

                            } else {
                                Log.w("2222222222222", "token should not be null...");
                            }
                        }).addOnFailureListener(e -> {
                            //handle e
                        }).addOnCanceledListener(() -> {
                            //handle cancel
                        }).addOnCompleteListener(task -> Log.v("333333", "This is the token : " + task.getResult()));


//                        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
//                            @Override
//                            public void onSuccess(InstanceIdResult instanceIdResult) {
//                                Log.e("FIREBASE_TOKEN",""+instanceIdResult.getToken());
//
//
//                                otpVerification(instanceIdResult.getToken(),USER_ID);
//
//                            }
//                        });


                    }

                } else {
                    Toast.makeText(OTPVerificationActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();

                }
            }
        });


        /*------------*/
        startSmsUserConsent();


        /*------------*/

    }


    public void otpVerification(String device_id, String user_id) {

        System.out.println("device_id----------" + device_id);
        System.out.println("user_id----------" + user_id);

        progressBar = ProgressDialog.show(OTPVerificationActivity.this, "", "Please wait...");


        Api apiService = RetrofitClient.getApiService();
        Call<UserData> userResponse = apiService.verifyOTP(user_id, device_id);
        userResponse.enqueue(new Callback<UserData>() {

            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {

                progressBar.dismiss();

//                System.out.println("verify otp resp---------" + new Gson().toJson(response).toString());

                otpUserData = response.body();
                if (response.isSuccessful()) {
                    otp_edt_1.setText("");
                    if (otpUserData.getResid().equals("200")) {
                        Toast.makeText(OTPVerificationActivity.this, otpUserData.getResMsg(), Toast.LENGTH_SHORT).show();
                        SharedPrefManager.getInstance(OTPVerificationActivity.this).saveUser((UserData) otpUserData);
                        setStatusUse("1", user_id);
                        Intent i = new Intent(OTPVerificationActivity.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);


                    } else {

                        Toast.makeText(OTPVerificationActivity.this, otpUserData.getResMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                progressBar.dismiss();

                System.out.println("error otp------" + t.toString());
                if (!isNetworkAvailable(OTPVerificationActivity.this)) {
                    Toast.makeText(OTPVerificationActivity.this, "Please check internet connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(OTPVerificationActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }

    public void setStatusUse(String status, String user_id) {


        System.out.println("otp user id=======" + user_id);

        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.setStatusOnline(user_id, status);
        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                if (response.isSuccessful()) {
//                    System.out.println("1111status resp-----------" +status+ "-------->" +  new Gson().toJson(response.body()));


                    if (response.body().getResid().equals("200")) {


                    } else {

                        Toast.makeText(OTPVerificationActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {

                System.out.println("1111chat status******" + t.toString());

            }
        });
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    public void resendOtp(String mobile) {

        progressBar = ProgressDialog.show(OTPVerificationActivity.this, "", "Please Wait...");

        Api apiService = RetrofitClient.getApiService();
        Call<LoginData> userResponse = apiService.login(mobile);
        userResponse.enqueue(new Callback<LoginData>() {

            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {

                progressBar.dismiss();

                loginData = response.body();
                if (response.isSuccessful()) {

                    if (loginData.getResid().equals("200")) {

                        if (response.isSuccessful()) {

                            Toast.makeText(OTPVerificationActivity.this, "New OTP generated", Toast.LENGTH_SHORT).show();
                            OTP_LOGIN = loginData.getOTP();


                        } else {
                            Toast.makeText(OTPVerificationActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();

                        }


                    } else {

                        Toast.makeText(OTPVerificationActivity.this, loginData.getResMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                progressBar.dismiss();

                System.out.println("error login------" + t.toString());

                if (!isNetworkAvailable(OTPVerificationActivity.this)) {
                    Toast.makeText(OTPVerificationActivity.this, "Please check internet connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(OTPVerificationActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    @Override
    public void networkAvailable() {

    }

    @Override
    public void networkUnavailable() {

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

    private void startSmsUserConsent() {
        SmsRetrieverClient client = SmsRetriever.getClient(this);
        //We can add sender phone number or leave it blank
        // I'm adding null here
        client.startSmsUserConsent(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
//                Toast.makeText(getApplicationContext(), "On Success", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getApplicationContext(), "On OnFailure", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void registerBroadcastReceiver() {
        smsBroadcastReceiver = new SmsBroadcastReceiver();
        smsBroadcastReceiver.smsBroadcastReceiverListener =
                new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
                    @Override
                    public void onSuccess(Intent intent) {
                        String message = SmsRetriever.EXTRA_SMS_MESSAGE;
                        System.out.println("messgae 222--------" + message.toString());
                        otpFetch.launch(Intent.createChooser(intent, ""));
                    }

                    @Override
                    public void onFailure() {
                    }
                };
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        // Use ContextCompat with RECEIVER_EXPORTED
        ContextCompat.registerReceiver(this, smsBroadcastReceiver, intentFilter, ContextCompat.RECEIVER_EXPORTED);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(smsBroadcastReceiver);
    }

    ActivityResultLauncher<Intent> otpFetch = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();


                        String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                        getOtpFromMessage(message);
                    }

                }

            });


    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1) {
//            if ((resultCode == RESULT_OK) && (data != null)) {
//                //That gives all message to us.
//                // We need to get the code from inside with regex
//                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
////                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//             /*   textViewMessage.setText(
//                        String.format("%s - %s", getString(R.string.received_message), message));*/
//                getOtpFromMessage(message);
//            }
//        }
//    }
    private void getOtpFromMessage(String message) {
        // This will match any 6 digit number in the message
        Pattern pattern = Pattern.compile("(|^)\\d{4}");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            otp_edt_1.setText(matcher.group(0));
        }
    }
}
