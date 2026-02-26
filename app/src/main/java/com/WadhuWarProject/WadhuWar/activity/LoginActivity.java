package com.WadhuWarProject.WadhuWar.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.adapter.PhoneNumberAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.LoginData;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener{

    LoginData loginData;
    EditText mobile;
    ProgressDialog progressBar;
    LinearLayout submitBtn;
    ProgressDialog progressDialog;
    static boolean isNetworkAvailable;
    private NetworkStateReceiver networkStateReceiver;
    int MY_PERMISSIONS_REQUEST_CALL_PHONE = 100;
    private boolean isCheckBoxClick = false;
    private MaterialCheckBox checkbox;
    TextView register_btn;

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
        setContentView(R.layout.activity_login);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_NUMBERS}, 101);
        } else {
            showPhonePickerDialog();
        }


        mobile = findViewById(R.id.mobile);
        submitBtn = findViewById(R.id.login_submit_btn);
        register_btn = findViewById(R.id.register_btn);
        checkbox = findViewById(R.id.checkbox);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i= new Intent(LoginActivity.this, LoginRegisterFirstActivity.class);
                Intent i= new Intent(LoginActivity.this, LoginRegisterFirstActivity.class);
                startActivity(i);
            }
        });

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isCheckBoxClick = b;
            }
        });


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String _mobile = mobile.getText().toString().trim();


                if (checkValidation()) {

                    if (!isNetworkAvailable(LoginActivity.this)) {
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
                    }else{


                        requestOtp(_mobile);
                    }
                }

            }
        });


        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/


        }

    private Boolean checkValidation() {

        if (mobile.getText().toString().isEmpty()) {
            mobile.setError("Please enter mobile number");
            mobile.requestFocus();
            return false;
        } else if (mobile.getText().toString().length() != 10) {
            mobile.setError("Mobile number should be 10 digit");
            mobile.requestFocus();
            return false;
        }else if (!isCheckBoxClick) {
            Toast.makeText(this, "Please agree to T&C and privacy policy", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    public  void requestOtp(String mobile){
        progressBar = ProgressDialog.show(LoginActivity.this, "", "Please Wait...");

        Api apiService = RetrofitClient.getApiService();
        Call<LoginData> userResponse = apiService.login(mobile);
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

                        Toast.makeText(LoginActivity.this,loginData.getResMsg(),Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoginActivity.this, OTPVerificationActivity.class);
                        System.out.println("otp-----------" + loginData.getOTP());
                        i.putExtra("otp", otp);
                        i.putExtra("user_id", user_id);
                        i.putExtra("mobile", mobile);
                        startActivity(i);

                    }
                    //deactivate account
                    else if(loginData.getResid().equals("203")){

                        /*dialog apply*/

                        final Dialog dialog = new Dialog(LoginActivity.this);
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


                                if (ContextCompat.checkSelfPermission(LoginActivity.this,
                                        Manifest.permission.CALL_PHONE)
                                        != PackageManager.PERMISSION_GRANTED) {

                                    ActivityCompat.requestPermissions(LoginActivity.this,
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


                                if (ContextCompat.checkSelfPermission(LoginActivity.this,
                                        Manifest.permission.CALL_PHONE)
                                        != PackageManager.PERMISSION_GRANTED) {

                                    ActivityCompat.requestPermissions(LoginActivity.this,
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

                        final Dialog dialog = new Dialog(LoginActivity.this);
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


                                if (ContextCompat.checkSelfPermission(LoginActivity.this,
                                        Manifest.permission.CALL_PHONE)
                                        != PackageManager.PERMISSION_GRANTED) {

                                    ActivityCompat.requestPermissions(LoginActivity.this,
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


                                if (ContextCompat.checkSelfPermission(LoginActivity.this,
                                        Manifest.permission.CALL_PHONE)
                                        != PackageManager.PERMISSION_GRANTED) {

                                    ActivityCompat.requestPermissions(LoginActivity.this,
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

                        Toast.makeText(LoginActivity.this,loginData.getResMsg(),Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                progressBar.dismiss();

                System.out.println("error login------" + t.toString());

                if(!isNetworkAvailable(LoginActivity.this)){
                    Toast.makeText(LoginActivity.this, "Please check internet connection!", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(LoginActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();

                }

            }
        });





//        Intent i = new Intent(LoginActivity.this, OTPVerificationActivity.class);
//        startActivity(i);


    }

        @Override
    protected void onPause() {
        super.onPause();



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
    protected void onResume() {
        super.onResume();


        
    }
    //        by sagar on 10 April 2025
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

    // Get phone numbers from contacts and show in dialog by sagar on 9 April 2025
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

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setCancelable(false);
        AlertDialog dialog = builder.create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        noneOfTheAboveButton.setOnClickListener(v -> {
            noneOfTheAboveButton.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
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
                    Toast.makeText(LoginActivity.this, "No SIM detected", Toast.LENGTH_SHORT).show();
                    mobile.setText("");
                    dialogView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
                    dialog.dismiss();
                    return null;

                }else if (selectedNumber.equals("No phone numbers available")){

                    Toast.makeText(LoginActivity.this, "No phone numbers available", Toast.LENGTH_SHORT).show();
                    mobile.setText("");
                    dialogView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
                    dialog.dismiss();
                    return null;
                }else {
                    if (selectedNumber != null && selectedNumber.length() == 10 ) {
                        mobile.setText(selectedNumber);
                        dialogView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
                        dialog.dismiss();
                    } else{
                        mobile.setText(selectedNumber.substring(2)); // Remove '91'
                        dialogView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
                        dialog.dismiss();
                    }
                    // Toast.makeText(LoginActivity.this, "Selected: " + selectedNumber, Toast.LENGTH_SHORT).show();
                }
            }
            return null;
        });
        recyclerView.setAdapter(adapter);

        dialog.show();
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

    @Override
    public void networkAvailable() {

    }

    @Override
    public void networkUnavailable() {

    }

    public void onClickPrivacyPolicy(View view) {
        String url = "https://wadhuwar.com/Privacypolicy.html";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }


    public void onClickTermsCondition(View view) {
        String url = "https://wadhuwar.com/termsandcondition.html";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
