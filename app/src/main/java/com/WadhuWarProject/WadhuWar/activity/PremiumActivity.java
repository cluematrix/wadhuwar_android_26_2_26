package com.WadhuWarProject.WadhuWar.activity;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;

import com.WadhuWarProject.WadhuWar.adapter.PremiumAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.extras.HttpHeaderContentSpecifier;
import com.WadhuWarProject.WadhuWar.model.CreateOrderId;
import com.WadhuWarProject.WadhuWar.model.GetInterested;
import com.WadhuWarProject.WadhuWar.model.GetPaymentDetails;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.MembershipPlan;
import com.WadhuWarProject.WadhuWar.model.MessageLimit;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.paytm.pgsdk.PaytmOrder;
//import com.paytm.pgsdk.PaytmPGService;
//import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
//import com.rd.PageIndicatorView;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.sabpaisa.gateway.android.sdk.interfaces.IPaymentSuccessCallBack;
import com.sabpaisa.gateway.android.sdk.models.TransactionResponsesModel;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//public class PremiumActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener, PremiumAdapter.Clicklistener, IPaymentSuccessCallBack<TransactionResponsesModel> {
public class PremiumActivity extends BaseActivity implements PaymentResultListener, NetworkStateReceiver.NetworkStateReceiverListener, PremiumAdapter.Clicklistener, IPaymentSuccessCallBack<TransactionResponsesModel> {

    SwipeRefreshLayout swipeRefreshLayout;
    Toolbar toolbar, toolbar22;

    Checkout checkout = new Checkout();

    static boolean isNetworkAvailable;
    private NetworkStateReceiver networkStateReceiver;
    ViewPager viewPager;
    MembershipPlan membershipPlan;
    ArrayList<MembershipPlan.MembershipPlanData> membershipPlanData = new ArrayList<>();
    int position = 0;
    ImageView back_arrow;
    RelativeLayout custom_toolbar;
    PremiumAdapter premiumAdapter;

    LinearLayout mo_num, view_planLL;
    static RelativeLayout internetOffRL;
    static ProgressBar simpleProgressBar;
    static TextView try_again_txt, couldnt_reach_internet_txt;
    int MY_PERMISSIONS_REQUEST_CALL_PHONE = 100;
    public static PremiumActivity finishContext;

    String checksumhash,user_id;

    CreateOrderId orderResponse;
    String odr_id;

    ProgressDialog progressDialog;
    InsertResponse payment_api_res;
    private final int timeoutInMS = 30000;
    UserData user;

    TextView advisor_txt;

    String activity_name;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
//
//        Window window = getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
//
//        WindowInsetsControllerCompat controller =
//                WindowCompat.getInsetsController(window, window.getDecorView());
//        controller.setAppearanceLightStatusBars(true);
//
//        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE,
//                WindowManager.LayoutParams.FLAG_SECURE);
//        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
//        setContentView(R.layout.activity_premium);
//
//        View root1 = findViewById(R.id.container);
//
//        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//
//            v.setPadding(
//                    v.getPaddingLeft(),
//                    0,
//                    v.getPaddingRight(),
//                    systemBars.bottom
//            );
//            return insets;
//        });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Checkout.preload(getApplicationContext());
        getWindow(). setFlags(WindowManager.LayoutParams. FLAG_SECURE, WindowManager.LayoutParams. FLAG_SECURE);
        setContentView(R.layout.activity_premium);
        user = SharedPrefManager.getInstance(PremiumActivity.this).getUser();
        user_id= String.valueOf(user.getUser_id());


        back_arrow = findViewById(R.id.back_arrow);
        custom_toolbar = findViewById(R.id.custom_toolbar);
        advisor_txt = findViewById(R.id.advisor_txt);
        view_planLL = findViewById(R.id.view_planLL);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewPager);
        mo_num = findViewById(R.id.mo_num);
        internetOffRL = (RelativeLayout) findViewById(R.id.internetOffRL);
        simpleProgressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);

        try_again_txt = (TextView) findViewById(R.id.try_again_txt);

        couldnt_reach_internet_txt = (TextView) findViewById(R.id.couldnt_reach_internet_txt);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        finishContext = this;


        getSupportActionBar().setTitle("Upgrade to Premium");

        Intent i = getIntent();
        if (i != null) {
            activity_name = i.getStringExtra("activity_name");
        }

        if (activity_name != null) {
            if (activity_name.equals("splash")) {
                toolbar.setVisibility(View.GONE);
                custom_toolbar.setVisibility(View.VISIBLE);
            } else {
                toolbar.setVisibility(View.VISIBLE);
                custom_toolbar.setVisibility(View.GONE);
            }
        } else {
            toolbar.setVisibility(View.VISIBLE);
            custom_toolbar.setVisibility(View.GONE);
        }


        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PremiumActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();


            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        if (viewPager == null) {

            // Initializing view pager

            // Disable clip to padding
            viewPager.setClipToPadding(false);
            // set padding manually, the more you set the padding the more you see of prev & next page
            viewPager.setPadding(80, 0, 80, 0);
            // sets a margin b/w individual pages to ensure that there is a gap b/w them
            viewPager.setPageMargin(20);
        }
        view_planLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PremiumActivity.this, AdvisorPlanActivity.class);
                interested(user_id);
                startActivity(i);
            }
        });
        mo_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(Intent.ACTION_CALL);
                it.setData(Uri.parse("tel:" + "9420944984"));


                if (ContextCompat.checkSelfPermission(PremiumActivity.this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(PremiumActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE);


                } else {
                    //You already have permission
                    try {
                        startActivity(it);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                }


            }
        });


        progressDialog = new ProgressDialog(PremiumActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");


        if (ContextCompat.checkSelfPermission(PremiumActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) PremiumActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        }


//        String first = "Experience Personalised Matchmaking starting at ";
//        String next = "<b><font color='#000000' size='18'>\u20B921,500</font></b>";
//        advisor_txt.setText(Html.fromHtml(first + next));
        String first = "Experience Personalised Matchmaking";
//        String next = "<b><font color='#000000' size='18'>\u20B921,500</font></b>";
        advisor_txt.setText(Html.fromHtml(first ));

        premiumListData(String.valueOf(user.getUser_id()));

        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/

    }

    @Override
    public void networkAvailable() {

//        premiumListData(String.valueOf(user.getUser_id()));

        swipeRefreshLayout.setColorScheme(R.color.LightGrey);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                premiumListData(String.valueOf(user.getUser_id()));

//                swipeRefreshLayout.setRefreshing(false);
            }
        });

        final Handler handler = new Handler();

        Runnable delayrunnable = new Runnable() {

            @Override
            public void run() {

                internetOffRL.setVisibility(View.GONE);
                couldnt_reach_internet_txt.setVisibility(View.GONE);

            }
        };
        handler.postDelayed(delayrunnable, 3000);


    }

    @Override
    public void networkUnavailable() {
        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

//                Toast.makeText(PremiumActivity.this, "Please check internet connection!", Toast.LENGTH_SHORT).show();

                swipeRefreshLayout.setRefreshing(false);
            }
        });


        internetOffRL.setVisibility(View.VISIBLE);

        try_again_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try_again_txt.setVisibility(View.GONE);
                simpleProgressBar.setVisibility(View.VISIBLE);
                if (!isNetworkAvailable()) {
                    simpleProgressBar.postDelayed(new Runnable() {
                        public void run() {
                            couldnt_reach_internet_txt.postDelayed(new Runnable() {
                                public void run() {
                                    couldnt_reach_internet_txt.setVisibility(View.VISIBLE);
                                    try_again_txt.setVisibility(View.VISIBLE);
                                    simpleProgressBar.setVisibility(View.GONE);
                                    couldnt_reach_internet_txt.postDelayed(new Runnable() {
                                        public void run() {
                                            couldnt_reach_internet_txt.setVisibility(View.GONE);


                                        }
                                    }, 2000);
                                }
                            }, 2000);

                        }
                    }, 2000);
                } else {
                    networkUnavailable();
                }

            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {


                }
                return;
            }

            default:
        }
    }

    public boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null;
    }


    public static PremiumActivity getInstance() {
        if (finishContext != null) {
            return finishContext;
        }
        return finishContext;
    }

    public void premiumListData(String login_userid) {

        System.out.println("232323");


        Api apiService = RetrofitClient.getApiService();
        Call<MembershipPlan> userResponse = apiService.membershipPlan("16", login_userid);
        userResponse.enqueue(new Callback<MembershipPlan>() {

            @Override
            public void onResponse(Call<MembershipPlan> call, Response<MembershipPlan> response) {

                swipeRefreshLayout.setRefreshing(false);
                membershipPlan = response.body();

//                progress_bar_profiles.setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {

//                    System.out.println("resp premium.......>>>" + new Gson().toJson(response.body()));

                    membershipPlanData = membershipPlan.getMembershipPlan();

//                     Check for active plan
                    boolean hasActivePlan = false;
                    for (MembershipPlan.MembershipPlanData plan : membershipPlanData) {
                        if ("1".equals(plan.getStatus())) {
                            hasActivePlan = true;
                            break; // Stop checking after first match
                        }
                    }
                    // Store in SharedPreferences
                    SharedPrefManager.getInstance(PremiumActivity.this).saveMembershipStatus(hasActivePlan);

                    if (membershipPlanData != null) {

                        for (int i = 0; i < membershipPlanData.size(); i++) {
//                            System.out.println("success story list .......>>>" + newsListData.get(i).getName());
                        }

                        premiumAdapter = new PremiumAdapter(PremiumActivity.this, membershipPlanData);
                        premiumAdapter.setClickListener(PremiumActivity.this);
                        viewPager.setAdapter(premiumAdapter);
                        premiumAdapter.notifyDataSetChanged();

//new try not regomendation by client : 18-6-2025
                        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
                            @Override
                            public void transformPage(@NonNull View page, float position) {
                                float MIN_SCALE = 0.75f;
                                float MAX_ROTATION = 30f;
                                float MAX_TRANSLATION_Y = 80f;

                                if (position < -1 || position > 1) {
                                    page.setAlpha(0f);
                                } else {
                                    float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position) * 0.25f);
                                    page.setScaleX(scaleFactor);
                                    page.setScaleY(scaleFactor);

                                    float rotationY = -position * MAX_ROTATION;
                                    page.setRotationY(rotationY);

                                    float translateY = Math.abs(position) * MAX_TRANSLATION_Y;
                                    page.setTranslationY(translateY);

                                    float alpha = 1 - Math.abs(position) * 0.3f;
                                    page.setAlpha(alpha);
                                }
                            }
                        });
///

                        SpringDotsIndicator as = findViewById(R.id.dot2);
                        as.attachTo(viewPager);
                    }
                }
            }

            @Override
            public void onFailure(Call<MembershipPlan> call, Throwable t) {
//                progress_bar_profiles.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                System.out.println("msg1 error profile slider******" + t.toString());
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


    @Override
    public void paymentButtonClick(String login_userid, String final_amount, int itemPosition, ArrayList<MembershipPlan.MembershipPlanData> membershipPlan) {
//        checkSumMethod(login_userid,final_amount,position,membershipPlanData);

        Log.d("paymentButtonClick", "Position Before:- " + position);
        Log.d("paymentButtonClick", " Membership Plan Data Before :- " + membershipPlanData.get(itemPosition));
        position = itemPosition;
        membershipPlanData = membershipPlan;

        Log.d("paymentButtonClick", "Position After:- " + position);
        Log.d("paymentButtonClick", " Membership Plan Data After :- " + membershipPlanData.get(itemPosition).toString());

        Log.d("paymentButtonClick", " Membership Plan Data ID:- " + membershipPlanData.get(itemPosition).getId());
        Log.d("paymentButtonClick", " Membership Plan Data Price:- " + final_amount);

        Log.d("loginUserID", "login_userid: " + user.getUser_id());
        double amountInRupees = Double.parseDouble(final_amount);
        int amountInPaise = (int)(amountInRupees * 100);
//        getOrderId(login_userid,amountInPaise);
//        private void getOrderId(String loginUserid, int amountInPaise) {
            Api apiService = RetrofitClient.getApiService();
            Call<CreateOrderId> userResponse = apiService.createOrderId(login_userid,amountInPaise);
            userResponse.enqueue(new Callback<CreateOrderId>() {
                @Override
                public void onResponse(Call<CreateOrderId> call, Response<CreateOrderId> response) {
                    if (response.isSuccessful()){
                        orderResponse = response.body();
                        odr_id = orderResponse.getOrderId();
                        if (odr_id!=null){
                            makePayment(login_userid, final_amount, membershipPlanData, position);
//                            Toast.makeText(PremiumActivity.this, "order" + odr_id, Toast.LENGTH_SHORT).show();
                        } else{

                        }
                    } else {
                        Toast.makeText(PremiumActivity.this, "Failed to fetch payment details", Toast.LENGTH_SHORT).show();
                        Log.e("PaymentResponse", "Error: Unsuccessful response or empty body");
                    }
                }
                @Override
                public void onFailure(Call<CreateOrderId> call, Throwable t) {

                }
            });

//        makePayment(login_userid, final_amount, membershipPlanData, position);          // sabPaisa payment Gateway

    }


    public void makePayment(String login_userid, String final_amount, ArrayList<MembershipPlan.MembershipPlanData> membershipPlanData, int position) {
//        double amount = Float.parseFloat(final_amount);
        double amountInRupees = Double.parseDouble(final_amount);
        int amountInPaise = (int)(amountInRupees * 100);
//        String order = "Wadhuwar"+user.getUser_id()+membershipPlanData.get(position).getId();

        Log.d("amountInFloat", "amount: " + amountInPaise);

        checkout.setKeyID("rzp_live_hhB9Fu0OH9XX0X");
        try {
            JSONObject options = new JSONObject();
            options.put("name", "wadhuwar.com");
            options.put("description", " Payment to wadhuwar.com");
            options.put("currency", "INR");
            options.put("amount",amountInPaise);
            options.put("order_id", odr_id);  // ✅ Must pass this
//            options.put("amount", "100"); // 100 INR in paise
            JSONObject prefill = new JSONObject();
            prefill.put("email", "Wadhuwar75@gmail.com");
            prefill.put("contact", "1212121212");
            options.put("prefill", prefill);
            checkout.open(PremiumActivity.this, options);
        } catch (Exception e) {
//            Toast.makeText(PremiumActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            pay.setEnabled(true);
        }

//        SabPaisaGateway sabPaisaGateway1 = SabPaisaGateway.Companion.builder()
//                .setAmount(Float.parseFloat(final_amount)) //Mandatory Parameter in Float Format
//                .setFirstName(user.getName()) //Mandatory Parameter
//                .setLastName(" ") //Mandatory Parameter
//                .setMobileNumber(user.getMobile()) //Mandatory Parameter
//                .setEmailId("Wadhuwar75@gmail.com")//Mandatory Parameter
//                .setClientCode("WAHU75")
//                .setAesApiIv("55QVm8EUD4Jmmyhj")
//                .setAesApiKey("VfTe9AbISD9FRsUO")
//                .setTransUserName("Wadhuwar75_16187").setTransUserName("Wadhuwar75_16187")
//                .setTransUserPassword("WAHU75_SP16187")
//                .setIsProd(true)
//                .build();
//        sabPaisaGateway1.init(this, this);
//        SabPaisaGateway.Companion.setInitUrlSabpaisa("https://securepay.sabpaisa.in/SabPaisa/sabPaisaInit?v=1");
//        SabPaisaGateway.Companion.setEndPointBaseUrlSabpaisa("https://securepay.sabpaisa.in");
//        SabPaisaGateway.Companion.setTxnEnquiryEndpointSabpaisa("https://txnenquiry.sabpaisa.in");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Checkout.clearUserData(this); // Fix for leaked receiver
    }

    @Override
    public void onPaymentFail(@Nullable TransactionResponsesModel transactionResponsesModel) {
        Log.d("TAG", "onPaymentFail: " + transactionResponsesModel.toString());
//        Toast.makeText(this, transactionResponsesModel.getStatus().toString(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(PremiumActivity.this, "Payment Failed", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPaymentSuccess(@Nullable TransactionResponsesModel transactionResponsesModel) {

        Log.d("onPaymentSuccess", "onPaymentSuccess: " + transactionResponsesModel.toString());
//        Toast.makeText(this, transactionResponsesModel.getBankMessage(), Toast.LENGTH_SHORT).show();

        // TODO :-----PARAMETERS-------

        /**   "login_userid"
         "plan_id"
         "order_id"
         "paid_amount"
         "payment_mode"
         "response_code"
         "response_message"
         "transaction_status"
         "transaction_id"
         "gateway_name"
         "bank_txt_id"
         "bankname"*/

        String status = transactionResponsesModel.getStatus();
        String banck_name = transactionResponsesModel.getBankName();
        String order_id = transactionResponsesModel.getSabpaisaTxnId();
        String txn_amount = transactionResponsesModel.getAmount();
        String txn_date = transactionResponsesModel.getTransDate();
        String txn_id = transactionResponsesModel.getClientTxnId();
        String resp_code = transactionResponsesModel.getStatusCode();
        String payment_mode = transactionResponsesModel.getPaymentMode();
        String bank_txn_id = transactionResponsesModel.getBankTxnId();
        String gateway_name = "SabPaisa";
        String res_msg = transactionResponsesModel.getBankMessage();

        Log.d("paymentSuccess", " Membership Plan Data :- " + membershipPlanData.get(position));
        Log.d("paymentSuccess", " Membership Plan  ID:- " + membershipPlanData.get(position).getId());
        Log.d("paymentSuccess", " Membership  Price:- " + txn_amount);

//if (resp_code.equals("0000")) {
//    paymentSuccessMemberMethod(String.valueOf(user.getUser_id()), membershipPlanData.get(position).getId(), order_id, txn_amount,
//            payment_mode, resp_code, res_msg, status, txn_id, gateway_name, bank_txn_id, banck_name);
//} else{
//    Toast.makeText(finishContext, "Payment fail ", Toast.LENGTH_SHORT).show();
//}
// todo :: status code 0000 means payment success
        if (transactionResponsesModel.getStatusCode().equals("0000")) {
            paymentSuccessMemberMethod(String.valueOf(user.getUser_id()), membershipPlanData.get(position).getId(), order_id, txn_amount,
                    payment_mode, resp_code, res_msg, status, txn_id, gateway_name, bank_txn_id, banck_name);

        } else {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.custom_payment_failed_dialog, null);

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setView(view)
                    .create();

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

// Resize dialog to wrap content
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(dialog.getWindow().getAttributes());
            layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(layoutParams);

// Handle OK button click
            Button okBtn = view.findViewById(R.id.ok_button);
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

// Log for debugging
            Log.d("onPaymentSuccess", "onPaymentSuccess: " + transactionResponsesModel.toString());


//            Toast.makeText(this, "Payment Fail", Toast.LENGTH_SHORT).show();
            Log.d("onPaymentSuccess", "onPaymentSuccess: " + transactionResponsesModel.toString());
        }
//
    }
    public void checkSumMethod(String login_userid, String final_amount, int position,
                               ArrayList<MembershipPlan.MembershipPlanData> membershipPlanData) {

        progressDialog.show();
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://wadhuwar.com/Testing/Api_con/CreateChecksum", new com.android.volley.Response.Listener<String>() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://wadhuwar.com/Api_con/CreateChecksum", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("paytm detail", response);

                try {
                    JSONObject parentObj = new JSONObject(response);
                    JSONObject paymentParamsObj = parentObj.optJSONObject("params");
                    Map<String, String> paytmParametersKeyValuePair;

                    // paytm parameters such as checksum order_id etc. are required to intitiate paytm gateway
                    paytmParametersKeyValuePair = getPaytmHashMap(paymentParamsObj);
                    Log.e("PAYMENT_HASHMAP", paytmParametersKeyValuePair.toString());
//                    startPaytmWebHashMap((HashMap<String, String>) paytmParametersKeyValuePair, checksumhash, login_userid, membershipPlanData, position);
//     todo:: commented by sagar on 20/12/2024
                } catch (JSONException e) {

                    e.printStackTrace();
                    Toast.makeText(PremiumActivity.this, "--error--", Toast.LENGTH_SHORT).show();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.cancel();

                try {
                    Log.e("PLACE_ERROR", new String(error.networkResponse.data, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("login_userid", login_userid);
                params.put("final_amount", final_amount);

                System.out.println("login user_id--------" + login_userid);
                System.out.println("amout--------" + final_amount);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return HttpHeaderContentSpecifier.getHeaderContentAsApplicationFormUrlEncoded();
            }
        };

        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(getRetryPolicy());

        Volley.newRequestQueue(PremiumActivity.this).add(stringRequest);

    }


    public Map<String, String> getPaytmHashMap(JSONObject paymentParamsObj) {


        try {
            Iterator iterator = paymentParamsObj.keys();
            List<String> keysList = new ArrayList<String>();

            keysList.clear();

            while (iterator.hasNext()) {
                //records keys for paytm parameters
                String key = (String) iterator.next();
                //generate key list to put values in hashmap
                keysList.add(key);
            }


            Map<String, String> paytmParametersKeyValuePair = new HashMap<>();

            String checksumhash = "";

            for (int i = 0; i < keysList.size(); i++) {

                if (keysList.get(i).equals("CHECKSUM")) {

                    checksumhash = paymentParamsObj.getString(keysList.get(i));
                } else {

                    keysList.get(i);

                    paytmParametersKeyValuePair.put(keysList.get(i), paymentParamsObj.getString(keysList.get(i)));
                }

            }


            this.checksumhash = checksumhash;
            return paytmParametersKeyValuePair;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////

//     todo:: commented by sagar on 20/12/2024



//    void startPaytmWebHashMap(final HashMap<String, String> paytmParamsHashMap, String checksumhash,
//                              String login_userid, ArrayList<MembershipPlan.MembershipPlanData> membershipPlanData, int position) {
//
//        if (progressDialog.isShowing()) {
//            progressDialog.cancel();
//        }
//
//
////        PaytmPGService Service = PaytmPGService.getStagingService();
//
//        PaytmPGService Service = PaytmPGService.getProductionService();
//
//        Log.d("params_put_map", "" + paytmParamsHashMap);
//
//        paytmParamsHashMap.put("CHECKSUMHASH", checksumhash);
//
//        PaytmOrder Order = new PaytmOrder(paytmParamsHashMap);
//
//        Service.initialize(Order, null);
//
//        Service.startPaymentTransaction(PremiumActivity.this, true, true, new PaytmPaymentTransactionCallback() {
//
//            public void someUIErrorOccurred(String inErrorMessage) {
//
//                Log.d("PAYTM_someUI", inErrorMessage);
//
//                Toast.makeText(PremiumActivity.this, "Some Error occured", Toast.LENGTH_LONG).show();
//            }
//
//
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            public void onTransactionResponse(Bundle inResponse) {
//
//
//                Log.d("paytm_txnRES===", inResponse.toString());
//
//                JSONObject json = getJsonFromBundle(inResponse);
//                Log.e("JSON_CONVERSION", "" + json);
//
//
//
//                /*------------*/
//
//                String status = inResponse.getString("STATUS");
//                String checks_t = inResponse.getString("CHECKSUMHASH");
//                String banck_name = inResponse.getString("BANKNAME");
//                String order_id = inResponse.getString("ORDERID");
//                String txn_amount = inResponse.getString("TXNAMOUNT");
//                String txn_date = inResponse.getString("TXNDATE");
//                String mid_t = inResponse.getString("MID");
//                String txn_id = inResponse.getString("TXNID");
//                String resp_code = inResponse.getString("RESPCODE");
//                String payment_mode = inResponse.getString("PAYMENTMODE");
//                String bank_txn_id = inResponse.getString("BANKTXNID");
//                String currency = inResponse.getString("CURRENCY");
//                String gateway_name = inResponse.getString("GATEWAYNAME");
//                String res_msg = inResponse.getString("RESPMSG");
//
//
//                if (status != null && !status.contentEquals("TXN_FAILURE")) {
//
//
//                    paymentSuccessMemberMethod(login_userid, membershipPlanData.get(position).getId(), order_id, txn_amount,
//                            payment_mode, resp_code, res_msg, status, txn_id, gateway_name, bank_txn_id, banck_name);
//
//
//                } else {
//
//                    setOrderFailure();
//
//                    System.out.println("11============" + inResponse.getString("STATUS"));
//
//                }
//
//                /*------------*/
//
//                System.out.println("22============" + inResponse);
//
//            }
//
//            public void networkNotAvailable() {
//                Toast.makeText(PremiumActivity.this, "Network Error", Toast.LENGTH_LONG).show();
//            }
//
//
//            public void clientAuthenticationFailed(String inErrorMessage) {
//                Log.d("PAYTM_clientAuth", inErrorMessage);
//                Toast.makeText(PremiumActivity.this, "Authentication Failed", Toast.LENGTH_LONG).show();
//            }
//
//            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
//                Log.d("PAYTM_errorWebPage", inErrorMessage + "\n failing URL" + "" + inFailingUrl + "\n" + iniErrorCode);
//                Toast.makeText(PremiumActivity.this, "Some Error Occured", Toast.LENGTH_LONG).show();
//
//            }
//
//            public void onBackPressedCancelTransaction() {
//
////                paytmParamsHashMap
////                cancelTransaction(paytmParamsHashMap);
//
//                Toast.makeText(PremiumActivity.this, "Transaction Failed", Toast.LENGTH_SHORT).show();
//            }
//
//            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
//                Log.d("PAYTM_errorMessage", "" + inResponse.toString());
//
////                cancelTransaction(paytmParamsHashMap);
//            }
//
//        });
//
//
//    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void paymentSuccessMemberMethod(String login_userid, String plan_id, String order_id, String paid_amount, String payment_mode,
                                           String resp_code, String res_msg, String status, String txn_id, String gateway_name, String bank_txn_id,
                                           String banck_name) {
        progressDialog.show();

        System.out.println("login_userid=====" + login_userid);
        System.out.println("plan_id=====" + plan_id);
        System.out.println("order_id=====" + order_id);
        System.out.println("paid_amount=====" + paid_amount);
        System.out.println("payment_mode=====" + payment_mode);
        System.out.println("resp_code=====" + resp_code);
        System.out.println("res_msg=====" + res_msg);
        System.out.println("status=====" + status);
        System.out.println("txn_id=====" + txn_id);
        System.out.println("gateway_name=====" + gateway_name);
        System.out.println("bank_txn_id=====" + bank_txn_id);
        System.out.println("banck_name=====" + banck_name);

        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.paymentSuccessMember(login_userid, plan_id, order_id, paid_amount, payment_mode, resp_code, res_msg, status, txn_id, gateway_name, bank_txn_id, banck_name);
        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                payment_api_res = response.body();
                progressDialog.cancel();

                /*System.out.println("payment response api ============" +  new Gson().toJson(payment_api_res));*/

                if (response.isSuccessful()) {
                    if (payment_api_res.getResid().equals("200")) {
                        System.out.println("11111111111 ============");
//                        Toast.makeText(PremiumActivity.this, "payment response: " + response, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(PremiumActivity.this, "response message: " + response.message() + "\nCode: " + response.code(), Toast.LENGTH_SHORT).show();
                        Log.d("payment_response", "payment response: " + response);
                        setOrderSuccess();
                    } else {
                        Toast.makeText(PremiumActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                        setApiFailure();
                        Log.d("payment_response", "payment response: " + response);

                    }

                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                System.out.println("msg1 payment******" + t.toString());
                progressDialog.cancel();

                setApiFailure();

                if (!isNetworkAvailable(PremiumActivity.this)) {
//                    Toast.makeText(PremiumActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(PremiumActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();


                }

            }
        });

    }


    public void setApiFailure() {

        System.out.println("555555555=================");

        PremiumActivity.getInstance().finish();

        Intent i = new Intent(PremiumActivity.this, PaymentSuccessActivity.class);
        i.putExtra("status", "api_fail");
        PremiumActivity.this.startActivity(i);
    }

    public void setOrderSuccess() {

        System.out.println("333333333=================");

        PremiumActivity.getInstance().finish();

        Intent i = new Intent(PremiumActivity.this, PaymentSuccessActivity.class);
        i.putExtra("status", "success");
        PremiumActivity.this.startActivity(i);


    }

    public void setOrderFailure() {

        System.out.println("44444444=================");

        PremiumActivity.getInstance().finish();

        Intent i = new Intent(PremiumActivity.this, PaymentSuccessActivity.class);
        i.putExtra("status", "failure");
        PremiumActivity.this.startActivity(i);

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public JSONObject getJsonFromBundle(Bundle inResponse) {

        //convert bundle response to json and pass this in paytm transaction report
        JSONObject json = new JSONObject();
        Set<String> keys = inResponse.keySet();
        for (String key : keys) {
            try {
                // json.put(key, bundle.get(key)); see edit below
                json.put(key, JSONObject.wrap(inResponse.get(key)));
            } catch (JSONException e) {
                //Handle exception here
                e.printStackTrace();
            }
        }
        return json;
    }


    private DefaultRetryPolicy getRetryPolicy() {
        return new DefaultRetryPolicy(timeoutInMS, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }


    public void onBackPressed() {
        super.onBackPressed();
        if (activity_name != null) {
            if (activity_name.equals("splash")) {
                Intent intent = new Intent(PremiumActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);

            } else {
                finish();
            }
        } else {
            finish();
        }

    }


    @Override
    public void onPaymentSuccess(String razorpayPaymentId) {
        Log.d("PaymentStatus", "Payment Successful: " + razorpayPaymentId);

        // Call the method with payment ID
        getPaymentResponse(razorpayPaymentId);
    }

    private void getPaymentResponse(String paymentId) {
        Api apiService = RetrofitClient.getApiService();
        Call<GetPaymentDetails> call = apiService.getPaymentDetails(paymentId);

        call.enqueue(new Callback<GetPaymentDetails>() {
            @Override
            public void onResponse(Call<GetPaymentDetails> call, Response<GetPaymentDetails> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GetPaymentDetails paymentDetails = response.body();

                    String status = "SUCCESS";
                    String banck_name = paymentDetails.getBank();
                    String order_id = paymentDetails.getOrderId();

//                    String txn_amount = String.valueOf(paymentDetails.getAmount());

                    double amountInRupees = paymentDetails.getAmount() / 100.0;
                    String txn_amount = String.format("%.2f", amountInRupees);

                    String txn_id = paymentDetails.getId();
                    String resp_code = "200";
                    String payment_mode = paymentDetails.getMethod();
                    String bank_txn_id = paymentDetails.getOrderId();
                    String gateway_name = "RazorPay";
                    String res_msg = "Payment By App";


                    paymentSuccessMemberMethod(String.valueOf(user.getUser_id()), membershipPlanData.get(position).getId(), order_id, txn_amount,
                            payment_mode, resp_code, res_msg, status, txn_id, gateway_name, bank_txn_id, banck_name);


                } else {
                    Toast.makeText(PremiumActivity.this, "Failed to fetch payment details", Toast.LENGTH_SHORT).show();
                    Log.e("PaymentResponse", "Error: Unsuccessful response or empty body");
                }
            }

            @Override
            public void onFailure(Call<GetPaymentDetails> call, Throwable t) {
                Toast.makeText(PremiumActivity.this, "API Call Failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("PaymentResponse", "API call failed", t);
            }
        });
    }


    @Override
    public void onPaymentError(int i, String s) {
//        Toast.makeText(this, "Payment Failed: " + s, Toast.LENGTH_LONG).show();
        Log.d("faild ", s);
    }

    private void interested(String user_id) {
        Api apiService = RetrofitClient.getApiService();
        Call<GetInterested> userResponse = apiService.getInterested(user_id);
        userResponse.enqueue(new Callback<GetInterested>() {
            @Override
            public void onResponse(Call<GetInterested> call, Response<GetInterested> response) {
//                Toast.makeText(PremiumActivity.this, "yes" + user_id, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<GetInterested> call, Throwable t) {
//                Toast.makeText(ChatActivity.this, "no", Toast.LENGTH_SHORT).show();
            }
        });
    }
}