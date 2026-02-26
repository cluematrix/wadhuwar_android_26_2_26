package com.WadhuWarProject.WadhuWar.activity;

import static com.WadhuWarProject.WadhuWar.activity.otpVerificationNewActivity.isNetworkAvailable;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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
import com.WadhuWarProject.WadhuWar.Receiver.SmsBroadcastReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.ProfilePercent;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.WadhuWarProject.WadhuWar.model.newMobileData;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileOtpVarifyActivity extends AppCompatActivity {

    public String OTP_LOGIN = null;
    public String mobile = null;
    public String user_id = null;
    public String reg_msg = null;
    EditText otpEditText;
    LinearLayout verifyButton;
    TextView resend;
    ProgressDialog progressBar;
    ProfilePercent profilePercent;
    UserData otpUserData;
    SmsBroadcastReceiver smsBroadcastReceiver;

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
        setContentView(R.layout.activity_mobile_otp_varify);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mobile_otp_varify);

        otpEditText = findViewById(R.id.otp_edt_1);
        verifyButton = findViewById(R.id.submit_button);
        resend = findViewById(R.id.otp_resend_btn_txt);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            OTP_LOGIN = extras.getString("otp");
            mobile = extras.getString("mobile");
            user_id = extras.getString("user_id");
            reg_msg = extras.getString("reg_msg");
        }
//        Toast.makeText(this, "otp" + user_id, Toast.LENGTH_SHORT).show();

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestOtp(mobile);
            }
        });

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredOtp = otpEditText.getText().toString().trim();



                if (enteredOtp.equals(OTP_LOGIN)) {
                    Toast.makeText(MobileOtpVarifyActivity.this, "OTP Verified Successfully", Toast.LENGTH_SHORT).show();

                    int userIdInt = 0;
                    userIdInt = Integer.parseInt(user_id);

                    try {
                        userIdInt = Integer.parseInt(user_id);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                    if (userIdInt != 0) {
                        if (!isNetworkAvailable(MobileOtpVarifyActivity.this)) {
                        } else {
                            FirebaseApp.initializeApp(MobileOtpVarifyActivity.this);
                            FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> {
                                if (!TextUtils.isEmpty(token)) {
                                    // If you have a method to verify OTP on server, call it here
                                    otpVerification(token, user_id);  // You can redirect to MainActivity here
                                } else {
                                    Log.w("FirebaseToken", "Token should not be null...");
                                }
                            });
                        }
                    } else {
                        Intent intent = new Intent(MobileOtpVarifyActivity.this, RegistrationActivity.class);
                        SharedPrefManager.getInstance(MobileOtpVarifyActivity.this).saveMobile(mobile);

                        intent.putExtra("mobile", mobile);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    Toast.makeText(MobileOtpVarifyActivity.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });

        startSmsUserConsent();

    }

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

    private void getOtpFromMessage(String message) {
        // This will match any 6 digit number in the message
        Pattern pattern = Pattern.compile("(|^)\\d{4}");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            otpEditText.setText(matcher.group(0));
        }
    }



    private void otpVerification(String device_id, String user_id) {
        progressBar = ProgressDialog.show(MobileOtpVarifyActivity.this, "", "Please wait...");
        Api apiService = RetrofitClient.getApiService();
        Call<UserData> userResponse = apiService.verifyOTP(user_id, device_id);
        userResponse.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                Log.d("device_id", "onResponse: " + device_id);
                progressBar.dismiss();
                otpUserData = response.body();
                if (response.isSuccessful() && otpUserData.getResid().equals("200")) {
                    SharedPrefManager.getInstance(MobileOtpVarifyActivity.this).saveUser(otpUserData);
                    Intent intent = new Intent(MobileOtpVarifyActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(MobileOtpVarifyActivity.this, "Verification failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                progressBar.dismiss();
            }
        });
    }


    public void checkProfilePercent(){
        Api apiService = RetrofitClient.getApiService();
        Call<ProfilePercent> userResponse = apiService.profilecompelte(user_id);
        userResponse.enqueue(new Callback<ProfilePercent>() {
            @Override
            public void onResponse(Call<ProfilePercent> call, Response<ProfilePercent> response) {
                profilePercent = response.body();
                int percent = Integer.parseInt(profilePercent.getPercent());
                Toast.makeText(MobileOtpVarifyActivity.this, "percent"+ percent, Toast.LENGTH_SHORT).show();
                if (percent <= 60 ){
                    Intent intent = new Intent(MobileOtpVarifyActivity.this, AboutMeAndMembershipActivity.class);
                    intent.putExtra("user_id", user_id);
                    startActivity(intent);
                } else{
                    Intent intent = new Intent(MobileOtpVarifyActivity.this, MainActivity.class);
                    intent.putExtra("user_id", user_id);
                    startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call<ProfilePercent> call, Throwable t) {
                System.out.println("msg1 my ProfilePercent******" + t.toString());
            }
        });
    }





    private void requestOtp(String mobile) {
        Api apiService = RetrofitClient.getApiService();
        Call<newMobileData> userResponse = apiService.newRegMobile(mobile);
        progressBar = ProgressDialog.show(MobileOtpVarifyActivity.this, "", "Please Wait...");

        userResponse.enqueue(new Callback<newMobileData>() {
            @Override
            public void onResponse(Call<newMobileData> call, Response<newMobileData> response) {
                progressBar.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    newMobileData loginData = response.body();
                    OTP_LOGIN = String.valueOf(loginData.getOTP());

                } else {
                    Toast.makeText(MobileOtpVarifyActivity.this, "Response Error", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<newMobileData> call, Throwable t) {
                Toast.makeText(MobileOtpVarifyActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}