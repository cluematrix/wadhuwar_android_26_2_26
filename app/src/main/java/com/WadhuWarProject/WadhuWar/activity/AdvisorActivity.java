package com.WadhuWarProject.WadhuWar.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.GetInterested;
import com.WadhuWarProject.WadhuWar.model.UserData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdvisorActivity extends AppCompatActivity {
    Toolbar toolbar;
    int MY_PERMISSIONS_REQUEST_CALL_PHONE = 100;

    TextView advisor_txt;
    String user_id;
    UserData user;
    LinearLayout view_planLL,mo_num;

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
        setContentView(R.layout.activity_advisor);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_advisor);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
        advisor_txt = findViewById(R.id.advisor_txt);
        mo_num = findViewById(R.id.mo_num);
        view_planLL = findViewById(R.id.view_planLL);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        user = SharedPrefManager.getInstance(AdvisorActivity.this).getUser();
        user_id= String.valueOf(user.getUser_id());

        getSupportActionBar().setTitle("Advisor");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        view_planLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdvisorActivity.this,AdvisorPlanActivity.class);
                interested(user_id);
                startActivity(i);
            }
        });
        mo_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(Intent.ACTION_CALL);
                it.setData(Uri.parse("tel:"+ "9420944984"));


                if (ContextCompat.checkSelfPermission(AdvisorActivity.this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(AdvisorActivity.this,
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
            }
        });


//        String first = "Experience Personalised Matchmaking starting at ";
//        String next = "<b><font color='#000000' size='18'>\u20B921,500</font></b>";
//        advisor_txt.setText(Html.fromHtml(first + next));
        String first = "Experience Personalised Matchmaking";
//        String next = "<b><font color='#000000' size='18'>\u20B921,500</font></b>";
        advisor_txt.setText(Html.fromHtml(first));
    }

    private void interested(String user_id) {
        Api apiService = RetrofitClient.getApiService();
        Call<GetInterested> userResponse = apiService.getInterested(user_id);
        userResponse.enqueue(new Callback<GetInterested>() {
            @Override
            public void onResponse(Call<GetInterested> call, Response<GetInterested> response) {
//                Toast.makeText(AdvisorActivity.this, "yes" + user_id, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<GetInterested> call, Throwable t) {
//                Toast.makeText(ChatActivity.this, "no", Toast.LENGTH_SHORT).show();
            }
        });
    }
}