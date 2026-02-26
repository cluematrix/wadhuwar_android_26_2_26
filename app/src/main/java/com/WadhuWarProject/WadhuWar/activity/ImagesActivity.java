package com.WadhuWarProject.WadhuWar.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.viewpager.widget.ViewPager;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.adapter.ImagesAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.CommonResponse;
import com.WadhuWarProject.WadhuWar.model.SliderListImage;
import com.WadhuWarProject.WadhuWar.model.UserData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImagesActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {


    static boolean isNetworkAvailable;
    private NetworkStateReceiver networkStateReceiver;
    ImagesAdapter imagesAdapter;
    ViewPager viewPager;
    ArrayList<SliderListImage> myList;
    ImageView connect_now_btn, cross;
    LinearLayout btn_block_report;
    boolean isCallFromSelfProfile = false;
    UserData user;
    String people_userid, f_name = "", m_name = "", l_name = "";

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
        setContentView(R.layout.activity_images);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_images);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
        viewPager = findViewById(R.id.viewpager);

        connect_now_btn = findViewById(R.id.connect_now_btn);
        cross = findViewById(R.id.cross);
        btn_block_report = findViewById(R.id.btn_block_report);


        myList = (ArrayList<SliderListImage>) getIntent().getSerializableExtra("mylist");
        user = SharedPrefManager.getInstance(this).getUser();

        Intent intent = getIntent();
        if (intent.getStringExtra("userid") != null) {
            people_userid = intent.getStringExtra("userid");
        }

        if (intent.getStringExtra("Fname") != null) {
            f_name = intent.getStringExtra("Fname");
        }

        if (intent.getStringExtra("Mname") != null) {
            m_name = intent.getStringExtra("Mname");
        }

        if (intent.getStringExtra("Lname") != null) {
            l_name = intent.getStringExtra("Lname");
        }

        if (myList != null) {
            for (int i = 0; i < myList.size(); i++) {
                System.out.println("img==========" + myList.get(i).getImgs());
            }
        }

        Intent i = getIntent();
        isCallFromSelfProfile = i.getBooleanExtra("isCallFromSelfProfile", false);

        if (isCallFromSelfProfile) {
            btn_block_report.setVisibility(View.GONE);
        } else {
            btn_block_report.setVisibility(View.VISIBLE);
        }

        imagesAdapter = new ImagesAdapter(ImagesActivity.this, myList);
        viewPager.setAdapter(imagesAdapter);

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/

        btn_block_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popup = new PopupMenu(ImagesActivity.this, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.profile_block_reject, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.block_profile) {
                            showConfirmationDialogBoxBlock();
                        }

                        if (item.getItemId() == R.id.report_profile) {
                            Intent i = new Intent(ImagesActivity.this, ReasonForReportActivity.class);
                            i.putExtra("userid", people_userid);
                            i.putExtra("Fname", f_name);
                            i.putExtra("Mname", m_name);
                            i.putExtra("Lname", l_name);
                            i.putExtra("isCallFromComment", false);
                            startActivity(i);
                        }
                        return false;
                    }
                });
            }
        });

    }

    private void showConfirmationDialogBoxBlock() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set the message show for the Alert time
        builder.setMessage("Blocked Member will not able to view your Profile or contact you on Wadhuwar");
        builder.setTitle("Are you sure you want to Block this profile?");
        builder.setCancelable(false);

        builder.setPositiveButton("OK", (DialogInterface.OnClickListener) (dialog, which) -> {
            blockUser(dialog);
        });

        builder.setNegativeButton("CANCEL", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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

    @Override
    public void networkAvailable() {

    }

    @Override
    public void networkUnavailable() {

    }

    private void blockUser(DialogInterface dialog) {
        Api apiService = RetrofitClient.getApiService();
        Call<CommonResponse> commonResponseCall = apiService.blockAccount(people_userid, String.valueOf(user.getUser_id()));
        commonResponseCall.enqueue(new Callback<CommonResponse>() {

            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse commonResponse = response.body();
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if (commonResponse.getResid() == 200) {
                        // finish();
                        Toast.makeText(ImagesActivity.this, "Block this user", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ImagesActivity.this, commonResponse.getResMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

                System.out.println("err UpdateResponse******" + t.toString());
                if (!isNetworkAvailable(ImagesActivity.this)) {
//                    Toast.makeText(ImagesActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(ImagesActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

}
