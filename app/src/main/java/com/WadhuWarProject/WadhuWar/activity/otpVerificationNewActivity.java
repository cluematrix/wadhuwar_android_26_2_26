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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.SmsBroadcastReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.RegisterResponse;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.WadhuWarProject.WadhuWar.model.newLoginData;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class otpVerificationNewActivity extends AppCompatActivity {

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
    newLoginData loginData;

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
        setContentView(R.layout.activity_otp_verification_new);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_otp_verification_new);

        verificationTitleText = findViewById(R.id.otp_verification_title);
        resendOtpTxt = findViewById(R.id.otp_resend_btn_txt);
        otpLayout = findViewById(R.id.otp_otp_layout);
        submit_button = findViewById(R.id.submit_button);
        otp_edt_1 = findViewById(R.id.otp_edt_1);
        progress_bar = findViewById(R.id.progress_bar);
        submit_btn_txt = findViewById(R.id.submit_btn_txt);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");

        progress_bar.getIndeterminateDrawable()
                .setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.MULTIPLY);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            OTP_LOGIN = extras.getString("otp");
            MOBILE_NO = extras.getString("mobile");
            USER_ID = extras.getString("user_id");
        }

        char[] mobCharArrays = MOBILE_NO.toCharArray();
        String encodedMobString = "";
        for (int i = 0; i < mobCharArrays.length; i++) {
            if (i > (mobCharArrays.length - 4)) {
                encodedMobString += mobCharArrays[i];
            } else {
                encodedMobString += "X";
            }
        }
        verificationTitleText.setText(getResources()
                .getString(R.string.please_enter_the_otp_we) + encodedMobString);

        resendOtpTxt.setOnClickListener(v -> resendOtp(MOBILE_NO));

        submit_button.setOnClickListener(v -> {
            String user_otp = otp_edt_1.getText().toString();
            if (user_otp.equals(OTP_LOGIN)) {
                if (!isNetworkAvailable(otpVerificationNewActivity.this)) {
                    showSnackbar(v, "Please check internet Connection!");
                } else {
                    FirebaseApp.initializeApp(otpVerificationNewActivity.this);
                    FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> {
                        if (!TextUtils.isEmpty(token)) {
//                            otpVerification(token, USER_ID);
                            registerUser();
                        } else {
                            Log.w("FirebaseToken", "Token should not be null...");
                        }
                    });
                }
            } else {
                Toast.makeText(otpVerificationNewActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
            }
        });

        startSmsUserConsent();
    }

    private void registerBroadcastReceiver() {
        smsBroadcastReceiver = new SmsBroadcastReceiver();
        smsBroadcastReceiver.smsBroadcastReceiverListener =
                new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
                    @Override
                    public void onSuccess(Intent intent) {
                        otpFetch.launch(Intent.createChooser(intent, ""));
                    }

                    @Override
                    public void onFailure() {
                    }
                };

        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        ContextCompat.registerReceiver(
                this, smsBroadcastReceiver, intentFilter, ContextCompat.RECEIVER_NOT_EXPORTED
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (smsBroadcastReceiver != null) {
            try {
                unregisterReceiver(smsBroadcastReceiver);
            } catch (IllegalArgumentException e) {
                Log.e("OTPVerification", "Receiver not registered: " + e.getMessage());
            }
        }
    }

    private void startSmsUserConsent() {
        SmsRetrieverClient client = SmsRetriever.getClient(this);
        client.startSmsUserConsent(null)
                .addOnSuccessListener(aVoid -> Log.d("SmsConsent", "Started successfully"))
                .addOnFailureListener(e -> Log.e("SmsConsent", "Failed to start: " + e.getMessage()));
    }

    private void resendOtp(String mobile) {
        progressBar = ProgressDialog.show(otpVerificationNewActivity.this, "", "Please Wait...");
        Api apiService = RetrofitClient.getApiService();
        Call<newLoginData> userResponse = apiService.newReg(mobile);
        userResponse.enqueue(new Callback<newLoginData>() {
            @Override
            public void onResponse(Call<newLoginData> call, Response<newLoginData> response) {
                progressBar.dismiss();
                loginData = response.body();
                if (response.isSuccessful() && loginData.getResid().equals("200")) {
                    Toast.makeText(otpVerificationNewActivity.this, "New OTP generated", Toast.LENGTH_SHORT).show();
                    OTP_LOGIN = loginData.getOTP();
                } else {
                    Toast.makeText(otpVerificationNewActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<newLoginData> call, Throwable t) {
                progressBar.dismiss();
                showNetworkError();
            }
        });
    }

    // changes in this beacause code not support for an android 14
    private void otpVerification(String device_id, String user_id) {
        progressBar = ProgressDialog.show(otpVerificationNewActivity.this, "", "Please wait...");
        Api apiService = RetrofitClient.getApiService();
        Call<UserData> userResponse = apiService.verifyOTP(user_id, device_id);
        userResponse.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                progressBar.dismiss();
                otpUserData = response.body();
                if (response.isSuccessful() && otpUserData.getResid().equals("200")) {
//                    registerUser();
                    Toast.makeText(otpVerificationNewActivity.this, "Verification failed!", Toast.LENGTH_SHORT).show();

//                        SharedPrefManager.getInstance(otpVerificationNewActivity.this).saveUser(otpUserData);
//                    Intent intent = new Intent(otpVerificationNewActivity.this, MainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
                } else {
//                    Toast.makeText(otpVerificationNewActivity.this, "Verification failed!", Toast.LENGTH_SHORT).show();
                    registerUser();
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                progressBar.dismiss();
                showNetworkError();
            }
        });
    }

    private void registerUser(){
        // Show progress dialog
        progressDialog.show();

        String firstName = getIntent().getStringExtra("FIRST_NAME");
        String middleName = getIntent().getStringExtra("MIDDLE_NAME");
        String lastName = getIntent().getStringExtra("LAST_NAME");
        String email = getIntent().getStringExtra("EMAIL");
        String mobile_nu = getIntent().getStringExtra("mobile");
        String password = getIntent().getStringExtra("password");

        // Get input values
//        String fname = editTextFirstName.getText().toString().trim();
//        String lname = editTextLastName.getText().toString().trim();
//        String email = editTextEmail.getText().toString().trim();
//        String mobile = editTextMobile.getText().toString().trim();

        // Create API service
        Api apiService = RetrofitClient.getApiService();
        Call<RegisterResponse> call = apiService.registerResponse(firstName,middleName, lastName, email, mobile_nu,password);

        // Enqueue API call
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                // Dismiss progress dialog
                progressDialog.dismiss();

                if (response.isSuccessful() && response.body() != null) {
                    RegisterResponse registerResponse = response.body();
                    if (registerResponse.getResMsg() == "Email or Mobile Number already exists"){
                        Toast.makeText(otpVerificationNewActivity.this, "Email or Mobile Number already exists", Toast.LENGTH_SHORT).show();
                    } else {
                    UserData userData = new UserData(
                            registerResponse.getUserId(),
                            null, // name (not provided in RegisterResponse)
                            null, // mobile (not provided in RegisterResponse)
                            null, // image (not provided in RegisterResponse)
                            String.valueOf(registerResponse.getResid()), // converting int to string
                            registerResponse.getResMsg()
                    );

                    SharedPrefManager.getInstance(otpVerificationNewActivity.this).saveUser(userData);
//                    if (response.body().getResMsg().equalsIgnoreCase("Email or Mobile Number already exists")) {
//                        Toast.makeText(otpVerificationNewActivity.this, "Email or Mobile Number already exists11", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(otpVerificationNewActivity.this, RegistrationNewActivity.class);
//                        startActivity(intent);
//                    }else {

//                        RegisterResponse registerResponse = response.body();
                        Toast.makeText(otpVerificationNewActivity.this, "Registration successfully !", Toast.LENGTH_SHORT).show();

//                        SharedPrefManager.getInstance(otpVerificationNewActivity.this).saveUser(otpUserData);
                        Intent intent = new Intent(otpVerificationNewActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    finish();
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(otpVerificationNewActivity.this, "Registration failed11: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                // Dismiss progress dialog
                progressDialog.dismiss();
                // Handle network or other failures
                Toast.makeText(otpVerificationNewActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showNetworkError() {
        if (!isNetworkAvailable(this)) {
            Toast.makeText(this, "Please check internet connection!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please try again!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showSnackbar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        TextView textView = snackbarView.findViewById(R.id.snackbar_text);
        textView.setBackgroundColor(Color.RED);
        textView.setTextColor(Color.WHITE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        } else {
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
        }
        snackbar.show();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) return false;
        NetworkInfo[] info = connectivity.getAllNetworkInfo();
        if (info != null) {
            for (NetworkInfo networkInfo : info) {
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) return true;
            }
        }
        return false;
    }

    ActivityResultLauncher<Intent> otpFetch = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                        getOtpFromMessage(message);
                    }
                }
            });

    private void getOtpFromMessage(String message) {
        Pattern pattern = Pattern.compile("(|^)\\d{4}");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            otp_edt_1.setText(matcher.group(0));
        }
    }


//    @Override
//    public void networkAvailable() {
//        Log.d(TAG, "networkAvailable: ");
//    }
//
//    @Override
//    public void networkUnavailable() {
//
//    }
}