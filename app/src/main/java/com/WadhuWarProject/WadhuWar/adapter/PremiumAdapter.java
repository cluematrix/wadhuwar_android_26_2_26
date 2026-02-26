package com.WadhuWarProject.WadhuWar.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.activity.AboutMeAndMembershipActivity;
import com.WadhuWarProject.WadhuWar.activity.AccountSettingActivity;
import com.WadhuWarProject.WadhuWar.activity.DetailMatchesActivity;
import com.WadhuWarProject.WadhuWar.activity.LoginActivity;
import com.WadhuWarProject.WadhuWar.activity.PaymentSuccessActivity;
import com.WadhuWarProject.WadhuWar.activity.PremiumActivity;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.extras.CheckSumServiceHelper;
import com.WadhuWarProject.WadhuWar.extras.Constants;
import com.WadhuWarProject.WadhuWar.extras.HttpHeaderContentSpecifier;
import com.WadhuWarProject.WadhuWar.model.Checksum;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.MembershipPlan;
import com.WadhuWarProject.WadhuWar.model.Paytm;
import com.WadhuWarProject.WadhuWar.model.UserData;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;


import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class PremiumAdapter extends PagerAdapter  {
    //    PaytmPGService Service;
    URL url;
    UserData user;
    String checksumhash;
    ProgressDialog progressDialog;
    Clicklistener clickListener;

    static boolean isNetworkAvailable;

    private final int timeoutInMS = 30000;

    Context context;
    LayoutInflater mLayoutInflater;
    ArrayList<MembershipPlan.MembershipPlanData> membershipPlanData;

    String checksum_api_value;

    Checksum checksumRes;

    InsertResponse payment_api_res;

    String TAG ="111111111--";

    public PremiumAdapter(Context context,  ArrayList<MembershipPlan.MembershipPlanData> membershipPlanData) {
        this.context = context;
        this.membershipPlanData = membershipPlanData;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return membershipPlanData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((ConstraintLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        // inflating the item.xml


        user = SharedPrefManager.getInstance(context).getUser();

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");


        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }


        View itemView = mLayoutInflater.inflate(R.layout.item_premium, container, false);

        ImageView view_blank = (ImageView) itemView.findViewById(R.id.view_blank);
        TextView premium_name = (TextView) itemView.findViewById(R.id.premium_name);
        TextView days = (TextView) itemView.findViewById(R.id.days);
        TextView amount = (TextView) itemView.findViewById(R.id.amount);
        TextView mathces_count = (TextView) itemView.findViewById(R.id.mathces_count);
        TextView off_txt = (TextView) itemView.findViewById(R.id.off_txt);
        TextView discount_price = (TextView) itemView.findViewById(R.id.discount_price);
        LinearLayout disountLL = (LinearLayout) itemView.findViewById(R.id.disountLL);
        ImageView image_logo = itemView.findViewById(R.id.image_logo);
        LinearLayout upgrade_now_btn = itemView.findViewById(R.id.upgrade_now_btn);
        LinearLayout upgradedLL = itemView.findViewById(R.id.upgradedLL);
        TextView desc_txt = itemView.findViewById(R.id.desc_txt);






        if(membershipPlanData.get(position).getStatus().equals("1")){
            upgradedLL.setVisibility(View.VISIBLE);
            upgrade_now_btn.setVisibility(View.GONE);
        }else{
            upgradedLL.setVisibility(View.GONE);
            upgrade_now_btn.setVisibility(View.VISIBLE);
        }

        if(membershipPlanData.get(position).getImage()!=null){
            if(!membershipPlanData.get(position).getImage().equals("")) {
                Glide.with(context.getApplicationContext()).load(membershipPlanData.get(position).getImage()).into(image_logo);



                Glide.with(context.getApplicationContext()).load(membershipPlanData.get(position).getImage())
                        .apply(bitmapTransform(new BlurTransformation(100)))
                        .into(view_blank);

            }
        }

        if(membershipPlanData.get(position).getDescription()!=null){
            if(!membershipPlanData.get(position).getDescription().trim().equals("null") && !membershipPlanData.get(position).getDescription().trim().equals("")){
                desc_txt.setVisibility(View.VISIBLE);
                desc_txt.setText(membershipPlanData.get(position).getDescription());

            }else{
                desc_txt.setVisibility(View.GONE);

            }
        }else{
            desc_txt.setVisibility(View.GONE);

        }

        premium_name.setText(membershipPlanData.get(position).getName());
//        days.setText(membershipPlanData.get(position).getDays()+" Days");
        days.setText(membershipPlanData.get(position).getMonth());
        mathces_count.setText("View upto "+ membershipPlanData.get(position).getMatches()+" Contact Numbers");
        amount.setText("\u20B9" + membershipPlanData.get(position).getAmount());

        if(membershipPlanData.get(position).getDiscount().equals("")){
            disountLL.setVisibility(View.GONE);

        }else{
            disountLL.setVisibility(View.VISIBLE);

            discount_price.setText(membershipPlanData.get(position).getDiscounted_price() );
            discount_price.setPaintFlags(discount_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


            off_txt.setText(membershipPlanData.get(position).getDiscount()+ "% off");
        }

        upgrade_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener!=null){
                    clickListener.paymentButtonClick(String.valueOf(user.getUser_id()),membershipPlanData.get(position).getAmount(),position,membershipPlanData);
                    Log.d("PaymentDebug", "clicked. User ID: " + user.getUser_id());
                }
            }
        });


        upgradedLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(context)
                        .setTitle("Are you sure to subscribe?")
                        .setMessage("If you activate this plan your previous plan is deactivated!")

                        .setPositiveButton("Renew ", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {


                                if (clickListener!=null){

                                    clickListener.paymentButtonClick(String.valueOf(user.getUser_id()),membershipPlanData.get(position).getAmount(),position,membershipPlanData);

                                }

                            }
                        })
                        .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // dismiss dialog here because user doesn't want to logout
                            }
                        })
                        .show();



               /* if (clickListener!=null){

                    clickListener.paymentButtonClick(String.valueOf(user.getUser_id()),membershipPlanData.get(position).getAmount(),position,membershipPlanData);

                }*/

            }

        });



        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        }

        Objects.requireNonNull(container).addView(itemView);

        return itemView;
    }

    public void setClickListener(Clicklistener clickListener){

        this.clickListener =  clickListener;
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((ConstraintLayout) object);
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


    public interface Clicklistener {

        public void paymentButtonClick(String login_userid,String final_amount,int position,
                                       ArrayList<MembershipPlan.MembershipPlanData> membershipPlanData);

    }


}

