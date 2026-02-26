package com.WadhuWarProject.WadhuWar.activity;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.adapter.AdvisorAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.extras.HttpHeaderContentSpecifier;
import com.WadhuWarProject.WadhuWar.model.AdvisorPlan;
import com.WadhuWarProject.WadhuWar.model.CreateOrderId;
import com.WadhuWarProject.WadhuWar.model.GetPaymentDetails;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
//import com.paytm.pgsdk.PaytmOrder;
//import com.paytm.pgsdk.PaytmPGService;
//import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
//import com.rd.PageIndicatorView;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.sabpaisa.gateway.android.sdk.SabPaisaGateway;
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

public class AdvisorPlanActivity extends AppCompatActivity implements PaymentResultListener, NetworkStateReceiver.NetworkStateReceiverListener, AdvisorAdapter.Clicklistener, IPaymentSuccessCallBack<TransactionResponsesModel> {
    SwipeRefreshLayout swipeRefreshLayout;
    Toolbar toolbar;
    static boolean isNetworkAvailable;
    private NetworkStateReceiver networkStateReceiver;
    ViewPager viewPager;
    AdvisorPlan advisorPlan;
    ArrayList<AdvisorPlan.AdvisoryPlanData> advisoryPlanData = new ArrayList<>();

    AdvisorAdapter advisorAdapter;

    CardView coming_cv;
    LinearLayout mo_num;
    static RelativeLayout internetOffRL;
    static ProgressBar simpleProgressBar;
    static TextView try_again_txt,couldnt_reach_internet_txt;
    int MY_PERMISSIONS_REQUEST_CALL_PHONE = 100;
    public static AdvisorPlanActivity finishContext;

    String checksumhash;

    ProgressDialog progressDialog;
    InsertResponse payment_api_res;
    private final int timeoutInMS = 30000;
    UserData user;
    Checkout checkout = new Checkout();
//    PageIndicatorView pageIndicatorView;

    CreateOrderId orderResponse;
    String odr_id;


    private int itemPosition;
    private ArrayList<AdvisorPlan.AdvisoryPlanData> membershipPlanData;
    int position = 0;


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
        setContentView(R.layout.activity_advisor_plan);

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
//        setContentView(R.layout.activity_advisor_plan);
        user = SharedPrefManager.getInstance(AdvisorPlanActivity.this).getUser();


        coming_cv = findViewById(R.id.coming_cv);
//        pageIndicatorView = findViewById(R.id.pageIndicatorView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewPager);
        mo_num  =  findViewById(R.id.mo_num);
        internetOffRL  = (RelativeLayout) findViewById(R.id.internetOffRL);
        simpleProgressBar  = (ProgressBar) findViewById(R.id.simpleProgressBar);

        try_again_txt  = (TextView) findViewById(R.id.try_again_txt);

        couldnt_reach_internet_txt  = (TextView) findViewById(R.id.couldnt_reach_internet_txt);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        finishContext = this;


        getSupportActionBar().setTitle("Get Advisor");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });


        if (viewPager == null)
        {

            // Initializing view pager

            // Disable clip to padding
            viewPager.setClipToPadding(false);
            // set padding manually, the more you set the padding the more you see of prev & next page
            viewPager.setPadding(80, 0, 80, 0);
            // sets a margin b/w individual pages to ensure that there is a gap b/w them
            viewPager.setPageMargin(20);
        }

        mo_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(Intent.ACTION_CALL);
                it.setData(Uri.parse("tel:"+ "9420944984"));


                if (ContextCompat.checkSelfPermission(AdvisorPlanActivity.this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(AdvisorPlanActivity.this,
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



        progressDialog = new ProgressDialog(AdvisorPlanActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");


        if (ContextCompat.checkSelfPermission(AdvisorPlanActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) AdvisorPlanActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        }

        advisorListData(String.valueOf(user.getUser_id()));


        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/

    }

    @Override
    public void networkAvailable() {

//        advisorListData(String.valueOf(user.getUser_id()));

        swipeRefreshLayout.setColorScheme(R.color.LightGrey);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                advisorListData(String.valueOf(user.getUser_id()));

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

//                Toast.makeText(AdvisorPlanActivity.this,"Please check internet connection!",Toast.LENGTH_SHORT).show();

                swipeRefreshLayout.setRefreshing(false);
            }
        });


        internetOffRL.setVisibility(View.VISIBLE);

        try_again_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try_again_txt.setVisibility(View.GONE);
                simpleProgressBar.setVisibility(View.VISIBLE);
                if(!isNetworkAvailable()){
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
                }
                else{
                    networkUnavailable();
                }

            }
        });



    }

//    @Override
//                                           public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case 100: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//
//                } else {
//
//
//                }
//                return;
//            }
//
//            default:
//        }
//    }

    public boolean isNetworkAvailable(){

        ConnectivityManager connectivityManager=(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null;
    }


    public static AdvisorPlanActivity getInstance(){
        if (finishContext !=null){
            return finishContext;
        }
        return finishContext;
    }

    public  void  advisorListData(String login_userid){

        System.out.println("232323");



        Api apiService = RetrofitClient.getApiService();
        Call<AdvisorPlan> userResponse = apiService.getAdvisoryPlan(login_userid);
        userResponse.enqueue(new Callback<AdvisorPlan>() {

            @Override
            public void onResponse(Call<AdvisorPlan> call, Response<AdvisorPlan> response) {

                swipeRefreshLayout.setRefreshing(false);
                advisorPlan = response.body();

//                progress_bar_profiles.setVisibility(View.GONE);
                viewPager.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {


//                    System.out.println("resp advisor.......>>>" + new Gson().toJson(response.body()));


                    if(advisorPlan.getResid().equals("200")){
                        viewPager.setVisibility(View.VISIBLE);
//                        pageIndicatorView.setVisibility(View.VISIBLE);
                        coming_cv.setVisibility(View.GONE);

                        advisoryPlanData = advisorPlan.getAdvisoryPlan();
                        if(advisoryPlanData!=null) {

                            for (int i = 0; i < advisoryPlanData.size(); i++) {
//                            System.out.println("success story list .......>>>" + newsListData.get(i).getName());
                            }

                            advisorAdapter = new AdvisorAdapter(AdvisorPlanActivity.this, advisoryPlanData);
                            advisorAdapter.setClickListener(AdvisorPlanActivity.this);
                            viewPager.setAdapter(advisorAdapter);
                            advisorAdapter.notifyDataSetChanged();

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

                    }else if(advisorPlan.getResid().equals("201")){
                        viewPager.setVisibility(View.GONE);
//                        pageIndicatorView.setVisibility(View.GONE);
                        coming_cv.setVisibility(View.VISIBLE);

                    }



                }

            }

            @Override
            public void onFailure(Call<AdvisorPlan> call, Throwable t) {
//                progress_bar_profiles.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                System.out.println("msg1 error AdvisorPlan ******" + t.toString());

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
    public void paymentButtonClick(String login_userid,String final_amount,int itemPosition,
                                   ArrayList<AdvisorPlan.AdvisoryPlanData> advisoryPlanData) {




//        checkSumMethod(login_userid,final_amount,position,advisoryPlanData);
        position = itemPosition;

        membershipPlanData = advisoryPlanData;
        Log.d("paymentButtonClick", "Position Before:- " + position);
        Log.d("paymentButtonClick", " Membership Plan Data Before :- " + membershipPlanData.get(itemPosition));


        Log.d("paymentButtonClick", "Position After:- " + position);
        Log.d("paymentButtonClick", " Membership Plan Data After :- " + membershipPlanData.get(itemPosition).toString());

        Log.d("paymentButtonClick", " Membership Plan Data ID:- " + membershipPlanData.get(itemPosition).getId());
        Log.d("paymentButtonClick", " Membership Plan Data Price:- " + final_amount);



        double amountInRupees = Double.parseDouble(final_amount);
        int amountInPaise = (int)(amountInRupees * 100);

        String user_id = String.valueOf(user.getUser_id());

        Api apiService = RetrofitClient.getApiService();
        Call<CreateOrderId> userResponse = apiService.createOrderId(user_id,amountInPaise);
        userResponse.enqueue(new Callback<CreateOrderId>() {
            @Override
            public void onResponse(Call<CreateOrderId> call, Response<CreateOrderId> response) {
                if (response.isSuccessful()){
                    orderResponse = response.body();
                    odr_id = orderResponse.getOrderId();
                    if (odr_id!=null){
                        makePayment(login_userid, final_amount, membershipPlanData, position);
                    } else{

                    }
                } else {
//                    Toast.makeText(AdvisorPlanActivity.this, "Failed to fetch payment details", Toast.LENGTH_SHORT).show();
                    Log.e("PaymentResponse", "Error: Unsuccessful response or empty body");
                }
            }
            @Override
            public void onFailure(Call<CreateOrderId> call, Throwable t) {

            }
        });


//        makePayment(login_userid, final_amount, membershipPlanData, position);          // sabPaisa payment Gateway


    }
    public void makePayment(String login_userid, String final_amount, ArrayList<AdvisorPlan.AdvisoryPlanData> membershipPlanData, int position) {

        double amountInRupees = Double.parseDouble(final_amount);
        int amountInPaise = (int)(amountInRupees * 100);

        Log.d("amountInFloat", "amount: " + amountInPaise);

//        checkout.setKeyID("rzp_test_IuKzrMk2GZtjaw");
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
            prefill.put("contact", 1212121212);
            options.put("prefill", prefill);
            checkout.open(AdvisorPlanActivity.this, options);
        } catch (Exception e) {
//            Toast.makeText(AdvisorPlanActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            pay.setEnabled(true);
        }



//        double amount = Float.parseFloat(final_amount);
//        Log.d("amountInFloat", "amount: " + amount);
//
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
    public void onPaymentFail(@Nullable TransactionResponsesModel transactionResponsesModel) {
        Log.d("TAG", "onPaymentFail: " + transactionResponsesModel.toString());

    }

    @Override
    public void onPaymentSuccess(@Nullable TransactionResponsesModel transactionResponsesModel) {


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


// todo :: status code 0000 means payment success
        if (transactionResponsesModel.getStatusCode().equals("0000")) {
            paymentSuccessMemberMethod(String.valueOf(user.getUser_id()),advisoryPlanData.get(position).getId(),order_id,txn_amount,
                    payment_mode,resp_code,res_msg,status,txn_id,gateway_name,bank_txn_id,banck_name);
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
//            Toast.makeText(finishContext, "Payment Fail", Toast.LENGTH_SHORT).show();
            Log.d("onPaymentSuccess", "onPaymentSuccess: " + transactionResponsesModel.toString());
        }




    }


    public  void  checkSumMethod(String login_userid,String final_amount,int position,
                                 ArrayList<AdvisorPlan.AdvisoryPlanData> advisoryPlanData){

        progressDialog.show();
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://wadhuwar.com/Testing/Api_con/CreateChecksum", new com.android.volley.Response.Listener<String>() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://wadhuwar.com/Api_con/CreateChecksum", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("paytm detail",response);

                try {
                    JSONObject parentObj = new JSONObject(response);

                    JSONObject paymentParamsObj = parentObj.optJSONObject("params");

                    Map<String,String> paytmParametersKeyValuePair;

                    // paytm parameters such as checksum order_id etc. are required to intitiate paytm gateway
                    paytmParametersKeyValuePair = getPaytmHashMap(paymentParamsObj);
                    Log.e("PAYMENT_HASHMAP",paytmParametersKeyValuePair.toString());

                    startPaytmWebHashMap((HashMap<String, String>) paytmParametersKeyValuePair, checksumhash,login_userid,advisoryPlanData,position);

                } catch (JSONException e) {

                    e.printStackTrace();
                    Toast.makeText(AdvisorPlanActivity.this, "--error--", Toast.LENGTH_SHORT).show();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.cancel();

                try {
                    Log.e("PLACE_ERROR",new String(error.networkResponse.data,"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("login_userid",login_userid);
                params.put("final_amount",final_amount);

                System.out.println("login user_id--------" + login_userid);
                System.out.println("amout--------" + final_amount);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return HttpHeaderContentSpecifier.getHeaderContentAsApplicationFormUrlEncoded(); }
        };

        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(getRetryPolicy());

        Volley.newRequestQueue(AdvisorPlanActivity.this).add(stringRequest);

    }


    public Map<String,String> getPaytmHashMap(JSONObject paymentParamsObj){



        try{
            Iterator iterator   = paymentParamsObj.keys();
            List<String> keysList = new ArrayList<String>();

            keysList.clear();

            while(iterator.hasNext()) {
                //records keys for paytm parameters
                String key = (String) iterator.next();
                //generate key list to put values in hashmap
                keysList.add(key);
            }


            Map<String,String> paytmParametersKeyValuePair = new HashMap<>();

            String checksumhash = "";

            for (int i=0; i < keysList.size(); i++){

                if (keysList.get(i).equals("CHECKSUM")){

                    checksumhash = paymentParamsObj.getString(keysList.get(i));
                }

                else{

                    keysList.get(i);

                    paytmParametersKeyValuePair.put(keysList.get(i),paymentParamsObj.getString(keysList.get(i)));
                }

            }


            this.checksumhash = checksumhash;
            return paytmParametersKeyValuePair;
        }

        catch (JSONException e){
            e.printStackTrace();
        }

        return null;
    }


    void startPaytmWebHashMap(final HashMap<String, String> paytmParamsHashMap, String checksumhash,
                              String login_userid, ArrayList<AdvisorPlan.AdvisoryPlanData> advisoryPlanData, int position){

        if (progressDialog.isShowing()){
            progressDialog.cancel();
        }


//        PaytmPGService Service = PaytmPGService.getStagingService();

//        PaytmPGService Service = PaytmPGService.getProductionService();

        Log.d("params_put_map",""+paytmParamsHashMap);

        paytmParamsHashMap.put("CHECKSUMHASH",checksumhash);

//        PaytmOrder Order = new PaytmOrder(paytmParamsHashMap);

//        Service.initialize(Order, null);

//        Service.startPaymentTransaction(AdvisorPlanActivity.this, true, true, new PaytmPaymentTransactionCallback() {
//
//            public void someUIErrorOccurred(String inErrorMessage) {
//
//                Log.d("PAYTM_someUI",inErrorMessage);
//
//                Toast.makeText(AdvisorPlanActivity.this,"Some Error occured",Toast.LENGTH_LONG).show();
//            }
//
//
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            public void onTransactionResponse(Bundle inResponse) {
//
//
//
//
//                Log.d("paytm_txnRES===",inResponse.toString());
//
//                JSONObject json = getJsonFromBundle(inResponse);
//                Log.e("JSON_CONVERSION",""+json);
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
//
//
//                if(status!=null && !status.contentEquals("TXN_FAILURE"))
//                {
//
//
//                    paymentSuccessMemberMethod(login_userid,advisoryPlanData.get(position).getId(),order_id,txn_amount,
//                            payment_mode,resp_code,res_msg,status,txn_id,gateway_name,bank_txn_id,banck_name);
//
//
//                }
//                else
//                {
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
//                Toast.makeText(AdvisorPlanActivity.this,"Network Error",Toast.LENGTH_LONG).show();
//            }
//
//
//            public void clientAuthenticationFailed(String inErrorMessage) {
//                Log.d("PAYTM_clientAuth",inErrorMessage);
//                Toast.makeText(AdvisorPlanActivity.this,"Authentication Failed",Toast.LENGTH_LONG).show();
//            }
//
//            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
//                Log.d("PAYTM_errorWebPage",inErrorMessage+"\n failing URL"+""+inFailingUrl+"\n"+iniErrorCode);
//                Toast.makeText(AdvisorPlanActivity.this,"Some Error Occured",Toast.LENGTH_LONG).show();
//
//            }
//
//            public void onBackPressedCancelTransaction() {
//
////                paytmParamsHashMap
////                cancelTransaction(paytmParamsHashMap);
//
//                Toast.makeText(AdvisorPlanActivity.this, "Transaction Failed", Toast.LENGTH_SHORT).show();
//            }
//
//            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
//                Log.d("PAYTM_errorMessage",""+inResponse.toString());
//
////                cancelTransaction(paytmParamsHashMap);
//            }
//
//        });


    }




    public void paymentSuccessMemberMethod(String login_userid,String plan_id,String order_id,String paid_amount, String payment_mode,
                                           String resp_code,String res_msg,String status,String txn_id,String gateway_name,String bank_txn_id,
                                           String banck_name){
        progressDialog.show();


        System.out.println("login_userid=====" +login_userid);
        System.out.println("plan_id=====" +plan_id);
        System.out.println("order_id=====" +order_id);
        System.out.println("paid_amount=====" +paid_amount);
        System.out.println("payment_mode=====" +payment_mode);
        System.out.println("resp_code=====" +resp_code);
        System.out.println("res_msg=====" +res_msg);
        System.out.println("status=====" +status);
        System.out.println("txn_id=====" +txn_id);
        System.out.println("gateway_name=====" +gateway_name);
        System.out.println("bank_txn_id=====" +bank_txn_id);
        System.out.println("banck_name=====" +banck_name);

        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.paymentSuccessActivity(login_userid,plan_id,order_id,paid_amount,payment_mode,resp_code,res_msg,status,txn_id,
                gateway_name, bank_txn_id,banck_name);
        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                payment_api_res = response.body();
                progressDialog.cancel();

                /*System.out.println("payment response api ============" +  new Gson().toJson(payment_api_res));*/




                if (response.isSuccessful()) {
                    if(payment_api_res.getResid().equals("200")){
                        System.out.println("11111111111 ============" );

                        setOrderSuccess();
                    }else{
                        setApiFailure();
                    }

                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                System.out.println("msg1 payment******" + t.toString());
                progressDialog.cancel();

                setApiFailure();

                if(!isNetworkAvailable(AdvisorPlanActivity.this)){
//                    Toast.makeText(AdvisorPlanActivity.this, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
                }else{
//                    Toast.makeText(PremiumActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();




                }

            }
        });

    }


    public void setApiFailure(){

        System.out.println("555555555=================");

        AdvisorPlanActivity.getInstance().finish();

        Intent i = new Intent(AdvisorPlanActivity.this, PaymentSuccessActivity.class);
        i.putExtra("status","api_fail");
        AdvisorPlanActivity.this.startActivity(i);


    }



    public void setOrderSuccess(){

        System.out.println("333333333=================");

        AdvisorPlanActivity.getInstance().finish();

        Intent i = new Intent(AdvisorPlanActivity.this, AdvisorPaymentSuccessActivity.class);
        i.putExtra("status","success");
        AdvisorPlanActivity.this.startActivity(i);


    }

    public void setOrderFailure(){

        System.out.println("44444444=================");

        AdvisorPlanActivity.getInstance().finish();

        Intent i = new Intent(AdvisorPlanActivity.this, AdvisorPaymentSuccessActivity.class);
        i.putExtra("status","failure");
        AdvisorPlanActivity.this.startActivity(i);


    }




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public JSONObject getJsonFromBundle(Bundle inResponse){

        //convert bundle response to json and pass this in paytm transaction report
        JSONObject json = new JSONObject();
        Set<String> keys = inResponse.keySet();
        for (String key : keys) {
            try {
                // json.put(key, bundle.get(key)); see edit below
                json.put(key, JSONObject.wrap(inResponse.get(key)));
            } catch(JSONException e) {
                //Handle exception here
                e.printStackTrace();
            }
        }
        return json;
    }



    private DefaultRetryPolicy getRetryPolicy(){
        return new DefaultRetryPolicy(timeoutInMS,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
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

                    paymentSuccessMemberMethod(String.valueOf(user.getUser_id()),advisoryPlanData.get(position).getId(),order_id,txn_amount,
                            payment_mode,resp_code,res_msg,status,txn_id,gateway_name,bank_txn_id,banck_name);
                } else {
                    Toast.makeText(AdvisorPlanActivity.this, "Failed to fetch payment details", Toast.LENGTH_SHORT).show();
                    Log.e("PaymentResponse", "Error: Unsuccessful response or empty body");
                }
            }

            @Override
            public void onFailure(Call<GetPaymentDetails> call, Throwable t) {
                Toast.makeText(AdvisorPlanActivity.this, "API Call Failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("PaymentResponse", "API call failed", t);
            }
        });
    }



    @Override
    public void onPaymentError(int i, String s) {

    }
}