package com.WadhuWarProject.WadhuWar.activity;

import static com.WadhuWarProject.WadhuWar.fragments.MatchesRecentlyViewedTabFragment.isNetworkAvailable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.Receiver.NetworkStateReceiver;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.adapter.EbookAdapter;
import com.WadhuWarProject.WadhuWar.adapter.MelavaAdapter;
import com.WadhuWarProject.WadhuWar.adapter.PremiumMatchesAdapter;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.CreateOrderId;
import com.WadhuWarProject.WadhuWar.model.EbookList;
import com.WadhuWarProject.WadhuWar.model.GetPaymentDetails;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.MelawaList;
import com.WadhuWarProject.WadhuWar.model.PremiunMatchesList;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class EBookActivity extends AppCompatActivity implements PaymentResultListener,  NetworkStateReceiver.NetworkStateReceiverListener{
    static boolean isNetworkAvailable;
    RecyclerView ebok_rv;
    RelativeLayout progress_bar;
    private NetworkStateReceiver networkStateReceiver;
    SwipeRefreshLayout swipeRefreshLayout;
    UserData user;

    EbookAdapter ebookAdapter;
    EbookList ebookLists;
    ArrayList<EbookList.EbookListData> ebookListData;
    InsertResponse payment_api_res;

    LinearLayout empty_ebookLL;
    Toolbar toolbar;
    private String plan_id;

    CreateOrderId orderResponse;
    String odr_id;
    RecyclerView premium_rv;
    ArrayList<PremiunMatchesList.PremiunMatchesListData> premiunMatchesListData = new ArrayList<>();
    PremiunMatchesList premiunMatchesList;
    PremiumMatchesAdapter premiumMatchesAdapter;
    CardView premiumCV;

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
        setContentView(R.layout.activity_e_book);

        View root1 = findViewById(R.id.container);
        ViewCompat.setOnApplyWindowInsetsListener(root1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
//        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
//        setContentView(R.layout.activity_e_book);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        premium_rv = findViewById(R.id.premium_rv);
        premiumCV = findViewById(R.id.premiumCV);
        empty_ebookLL = findViewById(R.id.empty_ebookLL);
        ebok_rv = findViewById(R.id.ebok_rv);
        progress_bar = findViewById(R.id.progress_bar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        user = SharedPrefManager.getInstance(EBookActivity.this).getUser();
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("E-book");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        getEbook();


        /*if net off*/
        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        /*end code if net off*/

        premium_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        fetchPremiumListData();

    }

    public  void  fetchPremiumListData(){

        System.out.println("333333333333333333");

        if (premiunMatchesListData!=null )
            premiunMatchesListData.clear();


        Api apiService = RetrofitClient.getApiService();
        Call<PremiunMatchesList> userResponse = apiService.premiumMatches(String.valueOf(user.getUser_id()),"5","1");
        userResponse.enqueue(new Callback<PremiunMatchesList>() {

            @Override
            public void onResponse(Call<PremiunMatchesList> call, Response<PremiunMatchesList> response) {
                premiunMatchesList = response.body();

                premium_rv.setVisibility(View.VISIBLE);

                if (response.isSuccessful()) {

//                    System.out.println("premium.......>>>" + new Gson().toJson(response.body()));

                    if(premiunMatchesList.getResid().equals("200")) {
                        premiumCV.setVisibility(View.VISIBLE);


                        premiunMatchesListData = premiunMatchesList.getPremiumMatchesList();
                        if (premiunMatchesListData != null) {

                            System.out.println("premiunMatchesListData size .......>>>" + premiunMatchesListData.size());
                            if (premiunMatchesListData.size() == 0) {
                            }

                            for (int i = 0; i < premiunMatchesListData.size(); i++) {
//                            System.out.println("success story list .......>>>" + newsListData.get(i).getName());
                            }



                            premiumMatchesAdapter = new PremiumMatchesAdapter(EBookActivity.this, premiunMatchesListData);

                            premium_rv.setHasFixedSize(true);
                            premium_rv.setAdapter(premiumMatchesAdapter);
                            premiumMatchesAdapter.notifyDataSetChanged();


                        }

                    }else{
                    }


                }

            }

            @Override
            public void onFailure(Call<PremiunMatchesList> call, Throwable t) {

                System.out.println("msg1 error premium list******" + t.toString());
                if(!isNetworkAvailable(EBookActivity.this)){
//                    Toast.makeText(getActivity(), "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                }else{
//                    Toast.makeText(getActivity(), "Please Refresh!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }


    @Override
    public void networkAvailable() {

//        getEbook();

        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getEbook();

//                swipeRefreshLayout.setRefreshing(false);
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


    public  void  getEbook(){

        System.out.println("333333333333333333");

        if (ebookListData!=null )
            ebookListData.clear();

        progress_bar.setVisibility(View.VISIBLE);

        Api apiService = RetrofitClient.getApiService();
        Call<EbookList> userResponse = apiService.getEbookList(String.valueOf(user.getUser_id()));
        userResponse.enqueue(new Callback<EbookList>() {

            @Override
            public void onResponse(Call<EbookList> call, Response<EbookList> response) {
                swipeRefreshLayout.setRefreshing(false);
                ebookLists = response.body();

                progress_bar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    /* System.out.println("resp melava.......>>>" + new Gson().toJson(response.body()));*/

                    if(ebookLists.getResid().equals("200")) {
                        ebok_rv.setVisibility(View.VISIBLE);

                        empty_ebookLL.setVisibility(View.GONE);

                        ebookListData = ebookLists.getEbookList();
                        if (ebookListData != null) {

//                            ebookAdapter = new EbookAdapter(EBookActivity.this, ebookListData);


                            ebookAdapter = new EbookAdapter(EBookActivity.this, ebookListData, new EbookAdapter.OnEbookPayClickListener() {
                                @Override
                                public void onPayClicked(String amount, String ebookId) {
                                    // Call Razorpay or other payment code here using `amount`
                                    plan_id  = ebookId;


                                    double amountInRupees = Double.parseDouble(amount);
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
                                                    makePayment(amount, ebookId);
                                                } else{

                                                }
                                            } else {
                                                Toast.makeText(EBookActivity.this, "Failed to fetch payment details", Toast.LENGTH_SHORT).show();
                                                Log.e("PaymentResponse", "Error: Unsuccessful response or empty body");
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<CreateOrderId> call, Throwable t) {

                                        }
                                    });

//                                    makePayment(amount, ebookId);
                                }
                                public void makePayment(String amount, String ebookId) {
                                    Checkout checkout = new Checkout();
                                    checkout.setKeyID("rzp_live_hhB9Fu0OH9XX0X"); // Use your live or test key here

                                    double amountInRupees = Double.parseDouble(amount);
                                    int amountInPaise = (int)(amountInRupees * 100);

                                    try {
                                        JSONObject options = new JSONObject();
                                        options.put("name", "wadhuwar.com");
                                        options.put("description", "Payment for Ebook ID: " + ebookId);
                                        options.put("currency", "INR");
                                        options.put("amount", amountInPaise);

                                        options.put("order_id", odr_id);

                                        JSONObject prefill = new JSONObject();
                                        prefill.put("email", "Wadhuwar75@gmail.com");
                                        prefill.put("contact", "1212121212");
                                        options.put("prefill", prefill);

                                        checkout.open(EBookActivity.this, options); // Start Razorpay UI

                                    } catch (Exception e) {
//                                        Toast.makeText(EBookActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });


                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(EBookActivity.this);
                            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            ebok_rv.setLayoutManager(linearLayoutManager);

                            ebok_rv.setAdapter(ebookAdapter);
                            ebookAdapter.notifyDataSetChanged();

                        }
                    }else{
                        empty_ebookLL.setVisibility(View.VISIBLE);

                    }

                }
            }

            @Override
            public void onFailure(Call<EbookList> call, Throwable t) {
                progress_bar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                System.out.println("msg1 error EbookList******" + t.toString());

            }
        });
    }




    @Override
    public void networkUnavailable() {
        swipeRefreshLayout.setColorScheme(R.color.LightGrey);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

//                Toast.makeText(EBookActivity.this,"Please check internet connection!",Toast.LENGTH_SHORT).show();

                swipeRefreshLayout.setRefreshing(false);
            }
        });


    }

    public boolean isNetworkAvailable(){

        ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null;
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

                    paymentSuccessMemberMethod(String.valueOf(user.getUser_id()), plan_id, order_id,  /*paid_amount*/txn_amount,  payment_mode, resp_code,  res_msg,  status,  txn_id,  gateway_name,  bank_txn_id, banck_name);



                } else {
                    Toast.makeText(EBookActivity.this, "Failed to fetch payment details", Toast.LENGTH_SHORT).show();
                    Log.e("PaymentResponse", "Error: Unsuccessful response or empty body");
                }
            }

            public void paymentSuccessMemberMethod(String login_userid,String plan_id,String order_id,String paid_amount, String payment_mode,
                                                   String resp_code,String res_msg,String status,String txn_id,String gateway_name,String bank_txn_id,
                                                   String banck_name){


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
                Call<InsertResponse> userResponse = apiService.paymentSuccessEbook(login_userid,plan_id,order_id,paid_amount,payment_mode,resp_code,res_msg,status,txn_id,
                        gateway_name, bank_txn_id,banck_name);
                userResponse.enqueue(new Callback<InsertResponse>() {

                    @Override
                    public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                        payment_api_res = response.body();

                        /*System.out.println("payment response api ============" +  new Gson().toJson(payment_api_res));*/

                        if (response.isSuccessful()) {
                            if(payment_api_res.getResid().equals("200")){
                                System.out.println("11111111111 ============" );
                                Log.d("payment", "onResponse: "+response.body());
                                Toast.makeText(EBookActivity.this, "Payment Success", Toast.LENGTH_SHORT).show();
                                setOrderSuccess();
                            }else{
                                Toast.makeText(EBookActivity.this, "Payment Failed", Toast.LENGTH_SHORT).show();
                                Log.d("payment", "onResponse: "+response.code());
                            }

                        }


                    }
                    public void setOrderSuccess(){

                        System.out.println("333333333=================");


                        Intent i = new Intent(EBookActivity.this, PaymentSuccessMelavaActivity.class);
                        i.putExtra("status","success");
                        EBookActivity.this.startActivity(i);
                    }

                    @Override
                    public void onFailure(Call<InsertResponse> call, Throwable t) {
                        System.out.println("msg1 payment******" + t.toString());

                        if(!isNetworkAvailable(EBookActivity.this)){
//                    Toast.makeText(context , "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
                        }else{
//                    Toast.makeText(PremiumActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }

            @Override
            public void onFailure(Call<GetPaymentDetails> call, Throwable t) {
                Toast.makeText(EBookActivity.this, "API Call Failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("PaymentResponse", "API call failed", t);
            }
        });
    }


    @Override
    public void onPaymentError(int i, String s) {

    }
}