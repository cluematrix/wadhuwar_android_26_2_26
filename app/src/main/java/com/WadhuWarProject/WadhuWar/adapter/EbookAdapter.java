
package com.WadhuWarProject.WadhuWar.adapter;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.activity.MelavaDetailActivity;
import com.WadhuWarProject.WadhuWar.activity.PaymentSuccessMelavaActivity;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.extras.HttpHeaderContentSpecifier;
import com.WadhuWarProject.WadhuWar.model.EbookList;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.MelawaList;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
//import com.paytm.pgsdk.PaytmOrder;
//import com.paytm.pgsdk.PaytmPGService;
//import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.sabpaisa.gateway.android.sdk.SabPaisaGateway;
import com.sabpaisa.gateway.android.sdk.interfaces.IPaymentSuccessCallBack;
import com.sabpaisa.gateway.android.sdk.models.TransactionResponsesModel;

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

public class EbookAdapter extends RecyclerView.Adapter<EbookAdapter.ViewHolder> implements IPaymentSuccessCallBack<TransactionResponsesModel> {
    ArrayList<EbookList.EbookListData> ebookListData;
    static boolean isNetworkAvailable;
    MelawaList melawaList;
    Context context;
    private final int timeoutInMS = 30000;
    int size;
    ProgressDialog progressDialog;
    InsertResponse payment_api_res;
    String checksumhash;
    UserData user;
    private String plan_id;
    private String paid_amount;
    private OnEbookPayClickListener payClickListener;


    public EbookAdapter(Context context, ArrayList<EbookList.EbookListData> ebookListData , OnEbookPayClickListener listener) {
        this.context = context;
        this.ebookListData = ebookListData;
        this.payClickListener = listener;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_ebook, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        user = SharedPrefManager.getInstance(context).getUser();

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");

        if(ebookListData!=null){

            if(ebookListData.get(position).getStatus().equals("1")){
                holder.pay_btn.setEnabled(false);
                holder.pay_btn.setText("Active");
            }else{
                holder.pay_btn.setText("Pay");
            }

            holder.amount.setText("\u20B9"+ebookListData.get(position).getAmount());
            paid_amount = String.valueOf(ebookListData.get(position).getAmount());
            plan_id =ebookListData.get(position).getId();
            holder.pay_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!isNetworkAvailable(context)) {

                        Snackbar mSnackbar = Snackbar.make(view, "Please Check Internet Connection!", Snackbar.LENGTH_LONG);
                        View mView = mSnackbar.getView();
                        TextView mTextView = (TextView) mView.findViewById(R.id.snackbar_text);
                        mTextView.setBackgroundColor(Color.RED);
                        mTextView.setTextColor(Color.WHITE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                            mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        else
                            mTextView.setGravity(Gravity.CENTER_HORIZONTAL);
                        mSnackbar.show();

                    }else {
//                        checkSumMethod(String.valueOf(user.getUser_id()),ebookListData.get(position).getAmount(),ebookListData.get(position).getId());
//                        makePayment(ebookListData.get(position).getAmount());
                        if (payClickListener != null) {
                            payClickListener.onPayClicked(ebookListData.get(position).getAmount(), ebookListData.get(position).getId());
                        }


                    }
                }
            });

        }


    }
    public void makePayment(String amount) {




//        double final_amount = Float.parseFloat(amount);
//        Log.d("amountInFloat", "amount: " + amount);
//
//        SabPaisaGateway sabPaisaGateway1 = SabPaisaGateway.Companion.builder()
//                .setAmount(Float.parseFloat(amount)) //Mandatory Parameter in Float Format
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
//        sabPaisaGateway1.init((Activity) this.context, this);
//        SabPaisaGateway.Companion.setInitUrlSabpaisa("https://securepay.sabpaisa.in/SabPaisa/sabPaisaInit?v=1");
//        SabPaisaGateway.Companion.setEndPointBaseUrlSabpaisa("https://securepay.sabpaisa.in");
//        SabPaisaGateway.Companion.setTxnEnquiryEndpointSabpaisa("https://txnenquiry.sabpaisa.in");
    }

    public interface OnEbookPayClickListener {
        void onPayClicked(String amount, String ebookId);
    }


    @Override
    public void onPaymentFail(@Nullable TransactionResponsesModel transactionResponsesModel) {
        Log.d("TAG", "onPaymentFail: " + transactionResponsesModel.toString());
        Toast.makeText(context, transactionResponsesModel.getBankMessage(), Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "Payment Fail ", Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onPaymentSuccess(@Nullable TransactionResponsesModel transactionResponsesModel) {

        Log.d("onPaymentSuccess", "onPaymentSuccess: " + transactionResponsesModel.toString());
        Toast.makeText(context, transactionResponsesModel.getBankMessage(), Toast.LENGTH_SHORT).show();

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

//        Log.d("paymentSuccess", " Membership Plan Data :- " + membershipPlanData.get(position));
        Log.d("paymentSuccess", " Membership Plan  ID:- " + plan_id);
        Log.d("paymentSuccess", " Membership  Price:- " + txn_amount);


//        paymentSuccessMemberMethod(String.valueOf(user.getUser_id()), membershipPlanData.get(position).getId(), order_id, txn_amount,
//                payment_mode, resp_code, res_msg, status, txn_id, gateway_name, bank_txn_id, banck_name);

// todo :: status code 0000 means payment success
        if (transactionResponsesModel.getStatusCode().equals("0000")) {
            paymentSuccessMemberMethod(String.valueOf(user.getUser_id()), plan_id, order_id,  /*paid_amount*/txn_amount,  payment_mode, resp_code,  res_msg,  status,  txn_id,  gateway_name,  bank_txn_id, banck_name);
        } else {
//            LayoutInflater inflater = LayoutInflater.from(context);

            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.custom_payment_failed_dialog, null);

            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setView(view)
                    .create();

            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                // Resize dialog to wrap content
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialog.getWindow().getAttributes());
                layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(layoutParams);
            }

            Button okBtn = view.findViewById(R.id.ok_button);
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

//            Toast.makeText(context, "Payment Fail", Toast.LENGTH_SHORT).show();
            Log.d("onPaymentSuccess", "onPaymentSuccess: " + transactionResponsesModel.toString());
        }
    }

    public  void  checkSumMethod(String login_userid,String final_amount, String id){

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

                    startPaytmWebHashMap((HashMap<String, String>) paytmParametersKeyValuePair, checksumhash,login_userid,id);

                } catch (JSONException e) {

                    e.printStackTrace();
                    Toast.makeText(context, "--error--", Toast.LENGTH_SHORT).show();
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

        Volley.newRequestQueue(context).add(stringRequest);

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
                              String login_userid,String id){

        if (progressDialog.isShowing()){
            progressDialog.cancel();
        }


//        PaytmPGService Service = PaytmPGService.getStagingService();

//        PaytmPGService Service = PaytmPGService.getProductionService();
//
//        Log.d("params_put_map",""+paytmParamsHashMap);
//
//        paytmParamsHashMap.put("CHECKSUMHASH",checksumhash);
//
//        PaytmOrder Order = new PaytmOrder(paytmParamsHashMap);

//        Service.initialize(Order, null);

//        Service.startPaymentTransaction(context, true, true, new PaytmPaymentTransactionCallback() {
//
//            public void someUIErrorOccurred(String inErrorMessage) {
//
//                Log.d("PAYTM_someUI",inErrorMessage);
//
//                Toast.makeText(context,"Some Error occured",Toast.LENGTH_LONG).show();
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
//                    paymentSuccessMemberMethod(login_userid,id,order_id,txn_amount,
//                            payment_mode,resp_code,res_msg,status,txn_id,gateway_name,bank_txn_id,banck_name);
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
//                Toast.makeText(context,"Network Error",Toast.LENGTH_LONG).show();
//            }
//
//
//            public void clientAuthenticationFailed(String inErrorMessage) {
//                Log.d("PAYTM_clientAuth",inErrorMessage);
//                Toast.makeText(context,"Authentication Failed",Toast.LENGTH_LONG).show();
//            }
//
//            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
//                Log.d("PAYTM_errorWebPage",inErrorMessage+"\n failing URL"+""+inFailingUrl+"\n"+iniErrorCode);
//                Toast.makeText(context,"Some Error Occured",Toast.LENGTH_LONG).show();
//
//            }
//
//            public void onBackPressedCancelTransaction() {
//
////                paytmParamsHashMap
////                cancelTransaction(paytmParamsHashMap);
//
//                Toast.makeText(context, "Transaction Failed", Toast.LENGTH_SHORT).show();
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
        Call<InsertResponse> userResponse = apiService.paymentSuccessEbook(login_userid,plan_id,order_id,paid_amount,payment_mode,resp_code,res_msg,status,txn_id,
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
                        Log.d("payment", "onResponse: "+response.body());
                        Toast.makeText(context, "Payment Success", Toast.LENGTH_SHORT).show();
                        setOrderSuccess();
                    }else{
                        Toast.makeText(context, "Payment Failed", Toast.LENGTH_SHORT).show();
                        Log.d("payment", "onResponse: "+response.code());
                        setApiFailure();
                    }

                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                System.out.println("msg1 payment******" + t.toString());
                progressDialog.cancel();

                setApiFailure();

                if(!isNetworkAvailable(context)){
//                    Toast.makeText(context , "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
                }else{
//                    Toast.makeText(PremiumActivity.this, "Please Refresh!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    public void setApiFailure(){

        System.out.println("555555555=================");



        Intent i = new Intent(context , PaymentSuccessMelavaActivity.class);
        i.putExtra("status","api_fail");
        context.startActivity(i);

    }



    public void setOrderSuccess(){

        System.out.println("333333333=================");


        Intent i = new Intent(context, PaymentSuccessMelavaActivity.class);
        i.putExtra("status","success");
        context.startActivity(i);


    }

    public void setOrderFailure(){

        System.out.println("44444444=================");


        Intent i = new Intent(context, PaymentSuccessMelavaActivity.class);
        i.putExtra("status","failure");
        context.startActivity(i);


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
    public int getItemCount() {


        return ebookListData.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView pay_btn,amount;

        public ViewHolder(View itemView) {
            super(itemView);

            pay_btn =  itemView.findViewById(R.id.pay_btn);
            amount =  itemView.findViewById(R.id.amount);


        }
    }
}