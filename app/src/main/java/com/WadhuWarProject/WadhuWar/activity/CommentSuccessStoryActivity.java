package com.WadhuWarProject.WadhuWar.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.adapter.CommentsAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.CommentList;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.SendCommentData;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class CommentSuccessStoryActivity extends AppCompatActivity implements  NetworkStateReceiver.NetworkStateReceiverListener {
    String _msg_et ="";
    FetchProfile fetchProfile;
    Toolbar toolbar;
    TextView write_commnet_block;
    static  RecyclerView comment_rv;
    static boolean isNetworkAvailable;
    private NetworkStateReceiver networkStateReceiver;

    SwipeRefreshLayout swipeRefreshLayout;
    String id;
    UserData user;
    ArrayList<CommentList.CommentListData> commentListData;
    ArrayList<CommentList.CommentListData> sendCommentListData;
    CommentList commentList;

    RelativeLayout progress_bar;

    SendCommentData sendCommentRes;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;
    NestedScrollView nestedScroll;
    LinearLayoutManager linearLayoutManager;


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
        setContentView(R.layout.activity_comment_success_story);

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
//        setContentView(R.layout.activity_comment_success_story);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        write_commnet_block = findViewById(R.id.write_commnet_block);
        comment_rv = findViewById(R.id.comment_rv);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        progress_bar = findViewById(R.id.progress_bar);

        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.black_arrow_back);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Comments");

        sendCommentListData = new ArrayList<>();
        commentListData = new ArrayList<>();

        Intent i = getIntent();
        if(i.getStringExtra("successstoryid")!=null){
            id = i.getStringExtra("successstoryid");
        }

        System.out.println("successstoryid=============" + id);

        user = SharedPrefManager.getInstance(CommentSuccessStoryActivity.this).getUser();

        comment_rv.setLayoutManager(new LinearLayoutManager(CommentSuccessStoryActivity.this, LinearLayoutManager.VERTICAL, false));


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        write_commnet_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(CommentSuccessStoryActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.comment_blog_dialog_box);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


                final EditText msg_et = (EditText) dialog.findViewById(R.id.msg_et);
                final TextView send_btn_txt =  dialog.findViewById(R.id.send_btn_txt);
                final RelativeLayout c_pb =  dialog.findViewById(R.id.c_pb);
                final LinearLayout send_btn_LL =  dialog.findViewById(R.id.send_btn_LL);


                send_btn_LL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        _msg_et = msg_et.getText().toString();
                        System.out.println("comm-------" + _msg_et);


                        sendCommentApi(_msg_et,send_btn_txt,c_pb,dialog);
                    }
                });

                dialog.show();


            }
        });

        fetchProfileData();

//        showCommentData();


        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/

    }


    public  void fetchProfileData(){

        System.out.println("user_id=====" + String.valueOf(user.getUser_id()));

        Api apiService = RetrofitClient.getApiService();
        Call<FetchProfile> userResponse = apiService.fetchProfile(String.valueOf(user.getUser_id()));
        userResponse.enqueue(new Callback<FetchProfile>() {

            @Override
            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {

                swipeRefreshLayout.setRefreshing(false);

                fetchProfile = response.body();
                if (response.isSuccessful()) {


                    if(fetchProfile.getResid().equals("200")){
                        showCommentData(fetchProfile);
                    }

                }

            }

            @Override
            public void onFailure(Call<FetchProfile> call, Throwable t) {
                System.out.println("msg1 my profile******" + t.toString());
                swipeRefreshLayout.setRefreshing(false);
                if(!isNetworkAvailable(CommentSuccessStoryActivity.this)){
//                    Toast.makeText(CommentSuccessStoryActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(CommentSuccessStoryActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    @Override
    public void networkAvailable() {

//        showCommentData();

        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                showCommentData();
                fetchProfileData();


//                swipeRefreshLayout.setRefreshing(false);
            }
        });


    }
    public  void showCommentData(  FetchProfile fetchProfile){


        System.out.println("comment blog id===========" + id);
        if (commentListData!=null )
            commentListData.clear();

        progress_bar.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<CommentList> userResponse = apiService.commentList(id);
        userResponse.enqueue(new Callback<CommentList>() {

            @Override
            public void onResponse(Call<CommentList> call, Response<CommentList> response) {
                swipeRefreshLayout.setRefreshing(false);
                commentList = response.body();

                progress_bar.setVisibility(View.GONE);
                comment_rv.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {

                    if(commentList.getResid().equals("200")) {

                        /*System.out.println("comments data===========" + new Gson().toJson(commentList));*/


                        commentListData = commentList.getCommentList();
                        if (commentListData != null) {

                            CommentsAdapter commentsAdapter = new CommentsAdapter(CommentSuccessStoryActivity.this, commentListData,fetchProfile.getGender());

                            comment_rv.setHasFixedSize(true);
                            comment_rv.setAdapter(commentsAdapter);
                            commentsAdapter.notifyDataSetChanged();

                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<CommentList> call, Throwable t) {
                progress_bar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                System.out.println("msg1 error comment******" + t.toString());

            }
        });
    }



    public  void sendCommentApi(String comment,TextView send_btn_txt, RelativeLayout c_pb,Dialog dialog){



        c_pb.setVisibility(View.VISIBLE);
        send_btn_txt.setText("Sending..");

        Api apiService = RetrofitClient.getApiService();
        Call<SendCommentData> userResponse = apiService.sendComment(id,String.valueOf(user.getUser_id()),user.getName(),comment);
        userResponse.enqueue(new Callback<SendCommentData>() {

            @Override
            public void onResponse(Call<SendCommentData> call, Response<SendCommentData> response) {
                sendCommentRes = response.body();

                c_pb.setVisibility(View.GONE);
                send_btn_txt.setText("Send");
                dialog.dismiss();


                if (response.isSuccessful()) {

                    System.out.println("resp send .......>>>" + new Gson().toJson(response.body()));

                    if(sendCommentRes.getResid().equals("200")){


                        CommentsAdapter commentsAdapter = new CommentsAdapter(CommentSuccessStoryActivity.this, commentListData,fetchProfile.getGender());

                        comment_rv.setHasFixedSize(true);
                        comment_rv.setAdapter(commentsAdapter);

                        System.out.println("send comment gender===========" + sendCommentRes.getGender());

                        commentListData.add(0,new CommentList.CommentListData(sendCommentRes.getCmt_id(),sendCommentRes.getMember_id(),sendCommentRes.getImage(),
                                sendCommentRes.getName(), sendCommentRes.getComment(),sendCommentRes.getDate(), sendCommentRes.getGender()));


                        commentsAdapter.notifyDataSetChanged();


                        Toast.makeText(CommentSuccessStoryActivity.this,"  Comment Added ",Toast.LENGTH_SHORT).show();


                    }else{
                        dialog.dismiss();

                        Toast.makeText(CommentSuccessStoryActivity.this,sendCommentRes.getResMsg(),Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<SendCommentData> call, Throwable t) {
                c_pb.setVisibility(View.GONE);
                send_btn_txt.setText("Send");
                dialog.dismiss();

                System.out.println("msg1 errorInsertResponse******" + t.toString());

            }
        });

    }

    @Override
    public void networkUnavailable() {
        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Toast.makeText(CommentSuccessStoryActivity.this,"Please check internet connection!",Toast.LENGTH_SHORT).show();

                swipeRefreshLayout.setRefreshing(false);
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
}