package com.WadhuWarProject.WadhuWar.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.adapter.ChatAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.extras.BlurTransformation;
import com.WadhuWarProject.WadhuWar.model.Chat;
import com.WadhuWarProject.WadhuWar.model.ChatStatusResponse;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.MessageLimit;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import io.github.rockerhieu.emojicon.EmojiconEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {
    private final String KEY_RECYCLER_STATE = "recycler_state";

    TextView last_seen_online;
    LinearLayout toolbar_nameLL;
    String checkNewLoad;
    String last_chat_id;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;
    NestedScrollView nestedScroll;
    LinearLayoutManager linearLayoutManager;
    String today_date11, today_date22;

    private RecyclerView mMessageRecycler;
    private ChatAdapter mMessageAdapter;
    ArrayList<Chat.ChatListData> chatListData = new ArrayList<>();
    ArrayList<Chat.ChatListData> chatListData_new = new ArrayList<>();
    Chat chat;
    private NetworkStateReceiver networkStateReceiver;
    UserData userData;
    private Parcelable recyclerViewState;
    String receiver_id, sender_id,user_id, receiver_name/*, receiver_image*/;
    String receiver_image;

    String isProfileHidden;
    TextView receiverChattxt;
    Toolbar toolbar;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressDialog progressBar;
    static boolean isNetworkAvailable;
    ImageView button_gchat_send;
//    EditText edit_gchat_message;

    private SimpleDateFormat dateFormat11;
    private Calendar calendar, calendar11;
    FrameLayout online_iv;
    EmojiconEditText edit_gchat_message;
    InsertResponse sendMsgResp;

    CircleImageView circleImageView;
    int r_pos1;
    FetchProfile fetchProfileLoginUser;
    ChatStatusResponse chatStatusResponse;
    String s_pos;
    UserData user;
    int firstVisibleItemPosition11;
    LinearLayout new_mgLL;

    Handler mHandler = new Handler();

    private final Runnable m_Runnable = new Runnable() {
        public void run() {


            System.out.println("run method call------");
            getnewchatidStatus();
            ChatActivity.this.mHandler.postDelayed(m_Runnable, 2000);

            /*getChatData();

//            System.out.println("11111111111111111111");
           ChatActivity.this.mHandler.postDelayed(m_Runnable, 1000);*/

        }

    };


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
        setContentView(R.layout.activity_chat);

//        View root1 = findViewById(R.id.container);
//        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
//            return insets;
//        });

        View root = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime());
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            v.setPadding(
                    0,
                    systemBars.top,
                    0,
                    imeInsets.bottom
            );
            return insets;
        });

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
//        nestedScroll = findViewById(R.id.nestedScroll);
        last_seen_online = findViewById(R.id.last_seen_online);
        online_iv = findViewById(R.id.online_iv);
        toolbar_nameLL = findViewById(R.id.toolbar_nameLL);
        new_mgLL = findViewById(R.id.new_mgLL);
        mMessageRecycler = findViewById(R.id.recycler_gchat);
        toolbar = findViewById(R.id.toolbar);
//        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        button_gchat_send = findViewById(R.id.button_gchat_send);
        edit_gchat_message = findViewById(R.id.edit_gchat_message);
        circleImageView = findViewById(R.id.chatReceiveImage);
        receiverChattxt = findViewById(R.id.chatReceivertxt);
//        recyclerViewState = mMessageRecycler.getLayoutManager().onSaveInstanceState();


        userData = SharedPrefManager.getInstance(ChatActivity.this).getUser();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            receiver_id = bundle.getString("receiver_id");
            receiver_name = bundle.getString("receiver_name");
            receiver_image = bundle.getString("receiver_image");
            isProfileHidden = bundle.getString("hideProfile");
        }

        sender_id = String.valueOf(userData.getUser_id());
        user_id = String.valueOf(userData.getUser_id());

        System.out.println("receiver_id=========" + receiver_id);
        System.out.println("receiver_name=========" + receiver_name);
        System.out.println("sender_id=========" + sender_id);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.black_arrow_back);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Log.d("receiver_image", "onCreate: " + receiver_image);

        // Check if receiver_image is not empty or null
//        if (receiver_image != null && !receiver_image.isEmpty()) {
//            Log.d("receiver_image", "onCreate: " + receiver_image);
//
//            if (isProfileHidden!=null){
//                Log.d("receiver_image", "onCreate: " + receiver_image);
//                if (isProfileHidden.equals("2")){
//                    Glide.with(getApplicationContext()).
//                            load(receiver_image)
//                            .transform(new BlurTransformation(this, 25)) // ⬅️ Correct context
//                            .placeholder(R.drawable.male_avtar)
//                            .error(R.drawable.no_photo_available)
//                            .into(circleImageView);
//                    Log.d("receiver_image", "onCreate: " + receiver_image);
//                }
//                else{
//                    // Load image with Picasso
//                    // slashh by tanmay 23-12-2024
////                    Picasso.with(getApplicationContext())
////                            .load(receiver_image)
////                            .placeholder(R.drawable.male_avtar)
////                            .error(R.drawable.no_photo_available)
////                            .into(circleImageView);
//                    Picasso.get()
//                            .load(receiver_image) // Make sure receiver_image is a valid URL
//                            .placeholder(R.drawable.male_avtar) // Placeholder image while loading
//                            .error(R.drawable.no_photo_available) // Error image if loading fails
//                            .into(circleImageView); // The ImageView where the image will be loaded
//                }
//            }
//        }

        try {

            if (isProfileHidden!=null && isProfileHidden.equals("2")){
                Glide.with(getApplicationContext()).
                        load(receiver_image)
                        .transform(new BlurTransformation(getApplicationContext(), 25))
                        .placeholder(R.drawable.male_avtar)
                        .error(R.drawable.no_photo_available)
                        .into(circleImageView);

            }else {
                Picasso.get()
                            .load(receiver_image) // Make sure receiver_image is a valid URL
                            .placeholder(R.drawable.male_avtar) // Placeholder image while loading
                            .error(R.drawable.no_photo_available) // Error image if loading fails
                            .into(circleImageView); // The ImageView where the image will be loaded
            }
        } catch (Exception e) {
            e.printStackTrace();

            if (receiver_image != null && !receiver_image.trim().isEmpty()) {
                Picasso.get()
                        .load(receiver_image)
                        .placeholder(R.drawable.male_avtar)
                        .error(R.drawable.male_avtar) // Also add an error fallback
                        .into(circleImageView);
            } else {
                circleImageView.setImageResource(R.drawable.male_avtar);
            }
        }

        receiverChattxt.setText(receiver_name);
//        getSupportActionBar().setTitle(receiver_name);
        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isProfileHidden!=null){
                if (isProfileHidden.equals("2")) {
                    Toast.makeText(ChatActivity.this, "This Profile is Hidden", Toast.LENGTH_SHORT).show();
                }}else {
                    Intent i = new Intent(ChatActivity.this, DetailMatchesForSearchActivity.class);
                    i.putExtra("userid", receiver_id);
                    startActivity(i);
                }
            }
        });


        this.mHandler = new Handler();

        this.mHandler.postDelayed(m_Runnable, 2000);

        /*button_gchat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msg = edit_gchat_message.getText().toString();

                System.out.println("msg=======" +  msg);
                edit_gchat_message.setText("");

                if(msg.trim().isEmpty()){

                }else{
                    sendMessage(msg);
                }
            }
        });*/
//        mMessageRecycler.setHasFixedSize(true);

//        linearLayoutManager = new LinearLayoutManager(ChatActivity.this);
//        linearLayoutManager.setStackFromEnd(true);
//        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager = new LinearLayoutManager(ChatActivity.this, LinearLayoutManager.VERTICAL, true);
        linearLayoutManager.setStackFromEnd(false);
//        linearLayoutManager.setReverseLayout(true);
        mMessageRecycler.setLayoutManager(linearLayoutManager);
        mMessageAdapter = new ChatAdapter(ChatActivity.this, receiver_image);
        mMessageRecycler.setItemAnimator(null);
        mMessageRecycler.setAdapter(mMessageAdapter);


        mMessageRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    firstVisibleItemPosition11 = linearLayoutManager.findFirstVisibleItemPosition();
                    System.out.println(" 11 value scroll-----------" + firstVisibleItemPosition11);
                }
            }
        });


//        mMessageRecycler.smoothScrollToPosition(mMessageAdapter.getItemCount());
//        mMessageRecycler.smoothScrollToPosition(mMessageAdapter.getItemCount());

//        mMessageRecycler.scrollToPosition(chatListData.size());
//        mMessageRecycler.scrollToPosition(chatListData.size()-1);



        /*----------------*/

       /* mMessageRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy<0){
                    System.out.println("dy-------------"+ dy);
                }else{
                    System.out.println("no dy-------------" + dy);

                }

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading && !isLastPage()) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == chatListData.size() - 1) {
                        //bottom of list!
                        loadMoreItems();
                        isLoading = true;
                    }
                }
            }
        });*/

        /*----------------*/


        mMessageRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


                System.out.println("scroolll============");

                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                System.out.println("visibleItemCount============" + visibleItemCount);
                System.out.println("totalItemCount============" + totalItemCount);
                System.out.println("firstVisibleItemPosition============" + firstVisibleItemPosition);


                if (!isLoading() && !isLastPage()) {

                    System.out.println("111 scroolll============");
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {

                        loadMoreItems();
                    }
                }

            }
        });


       /* mMessageRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                    System.out.println(" 11 value scroll-----------" +firstVisibleItemPosition );


                    if(firstVisibleItemPosition==0){

                    }


                }
            }
        });*/

        mMessageRecycler.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @SuppressLint("RestrictedApi")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onScrollChanged() {


                System.out.println("value scroll-----------" + mMessageRecycler.getScrollY());
                if (mMessageRecycler.getScrollY() > 1000) {

//                    swip_topLL.setVisibility(View.VISIBLE);
//
//                    swip_topLL.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                            nestedScroll.smoothScrollTo(0,0);
//
//                        }
//                    });


                } else {
//                    swip_topLL.setVisibility(View.GONE);

                }

            }
        });


        fetchLoginProfileData();//my data
        progressBar = ProgressDialog.show(ChatActivity.this, "", "Please Wait...");

        getChatData();

        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/
    }

    private int getCurrentItem() {
        return ((LinearLayoutManager) mMessageRecycler.getLayoutManager())
                .findFirstVisibleItemPosition();
    }


    protected void loadMoreItems() {
        isLoading = true;
        currentPage += 1;
        System.out.println("currentPage 11>>>>>>>>>>>" + currentPage);
        getChatDataNext(currentPage);
    }

    public int getTotalPageCount() {
        return TOTAL_PAGES;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public boolean isLoading() {
        return isLoading;
    }


    @Override
    public void onSaveInstanceState(Bundle state) {
        if (state == null) {
            state = new Bundle();
        }
        super.onSaveInstanceState(state);
        state.putParcelable(KEY_RECYCLER_STATE, mMessageRecycler.getLayoutManager().onSaveInstanceState());

    }


    public void getnewchatidStatus() {

        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.getnewchatidStatus(receiver_id, sender_id, last_chat_id);
        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                if (response.isSuccessful()) {
//                    System.out.println("new msg-----------" + new Gson().toJson(response.body()));


                    if (response.body().getResid().equals("200")) {
                        System.out.println("11new msg-----------" + firstVisibleItemPosition11);


                        if (firstVisibleItemPosition11 == 0) {
                            new_mgLL.setVisibility(View.GONE);
                            getChatData();
                        } else {
                            new_mgLL.setVisibility(View.VISIBLE);
                        }

                            /*new_mgLL.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mMessageRecycler.scrollToPosition(0);
                                    new_mgLL.setVisibility(View.GONE);
                                    getChatData();
                                }
                            });*/


                    } else {

                    }
                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {

                System.out.println("chat status******" + t.toString());

            }
        });
    }

    private void messageLimit(String member_id) {
        Api apiService = RetrofitClient.getApiService();
        Call<MessageLimit> userResponse = apiService.messageLimit(member_id);
        userResponse.enqueue(new Callback<MessageLimit>() {
            @Override
            public void onResponse(Call<MessageLimit> call, Response<MessageLimit> response) {
//                Toast.makeText(ChatActivity.this, "yes", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<MessageLimit> call, Throwable t) {
//                Toast.makeText(ChatActivity.this, "no", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void sendMessage(String msg) {

//        c_pb.setVisibility(View.VISIBLE);

        button_gchat_send.setClickable(true);

        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.saveChatting(sender_id, receiver_id, msg);
        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                sendMsgResp = response.body();

                button_gchat_send.setClickable(true);

                if (response.isSuccessful()) {

//                    System.out.println("resp InsertResponse.......>>>" + new Gson().toJson(response.body()));

                    if (sendMsgResp.getResid().equals("200")) {

                        edit_gchat_message.setText("");
//                        getnewchatidStatus();

                    } else {

                        Toast.makeText(ChatActivity.this, sendMsgResp.getResMsg(), Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {


                System.out.println("msg1 send msg******" + t.toString());

            }
        });
    }

    @Override
    public void networkAvailable() {
//
//        fetchLoginProfileData();//my data
//        progressBar = ProgressDialog.show(ChatActivity.this, "", "Please Wait...");
//
//        getChatData();

//        getStatusReceiver(receiver_id);

    }


    public void getStatusReceiver(String receiver_id) {
        Api apiService = RetrofitClient.getApiService();
        Call<ChatStatusResponse> userResponse = apiService.getOnline(String.valueOf(receiver_id));
        userResponse.enqueue(new Callback<ChatStatusResponse>() {

            @Override
            public void onResponse(Call<ChatStatusResponse> call, Response<ChatStatusResponse> response) {
                chatStatusResponse = response.body();

                if (response.isSuccessful()) {

                    System.out.println("chat check status===========" + chatStatusResponse.getStatus());

                    if (chatStatusResponse.getResid().equals("200")) {

                        if (chatStatusResponse.getStatus().trim().equals("1")) {
                            online_iv.setVisibility(View.VISIBLE);
                        } else {
                            online_iv.setVisibility(View.GONE);
                        }

                    }


                }

            }

            @Override
            public void onFailure(Call<ChatStatusResponse> call, Throwable t) {
                System.out.println("msg1 my receiver status******" + t.toString());
                progressBar.dismiss();

//                if(!isNetworkAvailable(ChatActivity.this)){
//                    Toast.makeText(ChatActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
//
//                }else{
//                    Toast.makeText(ChatActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();
//
//                }

            }
        });
    }

    public void fetchLoginProfileData() {


        System.out.println("user_id=====" + String.valueOf(sender_id));

        Api apiService = RetrofitClient.getApiService();
        Call<FetchProfile> userResponse = apiService.fetchProfile(String.valueOf(sender_id));
        userResponse.enqueue(new Callback<FetchProfile>() {

            @Override
            public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {
                fetchProfileLoginUser = response.body();

                if (response.isSuccessful()) {

                    System.out.println("preium check chat===========" + fetchProfileLoginUser.getPremium());


                    button_gchat_send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Api apiService = RetrofitClient.getApiService();
                            Call<FetchProfile> userResponse = apiService.fetchProfile(String.valueOf(sender_id));

                            userResponse.enqueue(new Callback<FetchProfile>() {
                                @Override
                                public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {
                                    fetchProfileLoginUser = response.body();

                                    if (fetchProfileLoginUser != null && fetchProfileLoginUser.getPremium() != null) {
                                        String premium = fetchProfileLoginUser.getPremium();
                                        String limit = fetchProfileLoginUser.getMsgLimit();

                                        if ("1".equals(premium) || limit == null || "0".equals(limit)) {
                                            String msg = edit_gchat_message.getText().toString().trim();
                                            edit_gchat_message.setText("");

                                            if (!msg.isEmpty()) {
                                                sendMessage(msg);
                                                messageLimit(user_id);
                                            }
                                        } else {
                                            // Show dialog
                                            final Dialog dialog = new Dialog(ChatActivity.this);
                                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            dialog.setCancelable(false);
                                            dialog.setContentView(R.layout.chat_premium_box);
                                            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                            LinearLayout upgrade_now_btn = dialog.findViewById(R.id.upgrade_now_btn);
                                            LinearLayout close_btn = dialog.findViewById(R.id.close_btn);

                                            upgrade_now_btn.setOnClickListener(v -> {
                                                dialog.dismiss();
                                                startActivity(new Intent(ChatActivity.this, PremiumActivity.class));
                                            });

                                            close_btn.setOnClickListener(v -> dialog.dismiss());

                                            dialog.show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<FetchProfile> call, Throwable t) {
                                    Toast.makeText(ChatActivity.this, "Failed to fetch user profile", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                }

            }

            @Override
            public void onFailure(Call<FetchProfile> call, Throwable t) {
                System.out.println("msg1 my profile******" + t.toString());
                progressBar.dismiss();

                if (!isNetworkAvailable(ChatActivity.this)) {
//                    Toast.makeText(ChatActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(ChatActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    @Override
    public void networkUnavailable() {
        /*swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Toast.makeText(ChatActivity.this,"Please check internet connection!", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });*/
    }


    public void getChatData() {

     /*   if (chatListData!=null )
            chatListData.clear();*/

        mMessageAdapter.getNewChatData().clear();
        if (chatListData != null)
            chatListData.clear();

        Api apiService = RetrofitClient.getApiService();

        System.out.println("sender_id=========0" + sender_id);
        System.out.println("receiver_id=========0" + receiver_id);
        new_mgLL.setVisibility(View.GONE);
        Call<Chat> userResponse = apiService.getChatting(sender_id, receiver_id, "20", "1");
        userResponse.enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> call, Response<Chat> response) {
                chat = response.body();
                checkNewLoad = "1";

//                System.out.println("chat resp=========0" +  new Gson().toJson(chat));

                progressBar.dismiss();
                if (response.isSuccessful()) {
                    String success = chat.getRes();

                    if (success.equals("200")) {


                        last_chat_id = response.body().getLast_id();

                        String last_seen_online_str = response.body().getLast_seen();

                        System.out.println("last_seen_online_str=========" + last_seen_online_str);

                        if (!last_seen_online_str.trim().equals("")) {
                            if (last_seen_online_str.equals("Online")) {
                                last_seen_online.setText(last_seen_online_str);
                                online_iv.setVisibility(View.VISIBLE);

                            } else {


                                dateFormat11 = new SimpleDateFormat("dd-MM-yyyy");
                                calendar11 = Calendar.getInstance();

                                today_date22 = dateFormat11.format(calendar11.getTime());

                                String date_str = last_seen_online_str;
                                String[] splited = date_str.split("\\s+");

//                                System.out.println("check date==========" + message.getMessage() + "========>" + today_date22.equals(splited[0]));


                                if (today_date22.equals(splited[0])) {
                                    last_seen_online.setText((splited[1]) + " " + (splited[2]));

                                } else {
                                    last_seen_online.setText(last_seen_online_str);

                                }
                            }

                        }


                        TOTAL_PAGES = Integer.parseInt(chat.getTotal_pages());
                        chatListData = chat.getChat_list();

//                        System.out.println("chat resp=========0" +  new Gson().toJson(chatListData));

//                        mMessageRecycler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                mMessageRecycler.smoothScrollToPosition(0);
//                            }
//                        });

                        if (chatListData != null) {
                            if (!chatListData.isEmpty()) {

                                mMessageAdapter.addAll(chatListData);


                                if (chatListData.size() < 20) {

                                    isLastPage = true;
                                    isLoading = false;
                                } else {
                                    if (currentPage <= TOTAL_PAGES)
                                        mMessageAdapter.addLoadingFooter();
                                    else isLastPage = true;
                                }
                            }
                        }


                    } else {

                    }

                }

            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
//                progress_bar_main.setVisibility(View.GONE);

                System.out.println("msg1 Chat******" + t.toString());
                progressBar.dismiss();
                if (!isNetworkAvailable(ChatActivity.this)) {
                    Toast.makeText(ChatActivity.this, "Please check internet connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(ChatActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    public void getChatDataNext(int pos) {

        Api apiService = RetrofitClient.getApiService();
        Call<Chat> userResponse = apiService.getChatting(sender_id, receiver_id, "20", String.valueOf(pos));

        userResponse.enqueue(new Callback<Chat>() {

            @Override
            public void onResponse(Call<Chat> call, Response<Chat> response) {
                chat = response.body();

//                System.out.println("chat resp=========1" +  new Gson().toJson(chat));


                if (response.isSuccessful()) {


                    String success = chat.getRes();

                    if (success.equals("200")) {

                        checkNewLoad = "2";

                        mMessageAdapter.removeLoadingFooter();
                        isLoading = false;

                        chatListData = chat.getChat_list();
                        if (chatListData != null) {


                            if (!chatListData.isEmpty()) {

                                /*chatListData_new.clear();
                                chatListData_new.add(new Chat.ChatListData(chatListData.get(0).getChat_id(),chatListData.get(0).getMessage(),chatListData.get(0).getSender_id(),
                                        chatListData.get(0).getReceiver_id(),chatListData.get(0).getTimestamp()));*/


                                mMessageAdapter.addAll(chatListData);

                                if (chatListData.size() < 20) {
                                    isLastPage = true;
                                    isLoading = false;
                                } else {
                                    if (currentPage != TOTAL_PAGES)
                                        mMessageAdapter.addLoadingFooter();
                                    else isLastPage = true;
                                }
                            }
                        }
                    } else {

                    }

                }

            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
//                progress_bar_main.setVisibility(View.GONE);

                System.out.println("msg1 Chat******" + t.toString());
                progressBar.dismiss();
                if (!isNetworkAvailable(ChatActivity.this)) {
                    Toast.makeText(ChatActivity.this, "Please check internet connection!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(ChatActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();

                }
            }
        });
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


}