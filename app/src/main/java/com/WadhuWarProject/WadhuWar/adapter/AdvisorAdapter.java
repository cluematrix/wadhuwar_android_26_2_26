package com.WadhuWarProject.WadhuWar.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.model.AdvisorPlan;
import com.WadhuWarProject.WadhuWar.model.Checksum;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.MembershipPlan;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.bumptech.glide.Glide;
//import com.paytm.pgsdk.PaytmPGService;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class AdvisorAdapter extends PagerAdapter  {
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
    ArrayList<AdvisorPlan.AdvisoryPlanData> advisoryPlanData;

    String checksum_api_value;

    Checksum checksumRes;

    InsertResponse payment_api_res;

    String TAG ="111111111--";

    public AdvisorAdapter(Context context, ArrayList<AdvisorPlan.AdvisoryPlanData> advisoryPlanData) {
        this.context = context;
        this.advisoryPlanData = advisoryPlanData;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return advisoryPlanData.size();
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


        int SDK_INT = Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }


        View itemView = mLayoutInflater.inflate(R.layout.item_advisor, container, false);

        TextView month_day = (TextView) itemView.findViewById(R.id.month_day);
        LinearLayout bg_month_LL = (LinearLayout) itemView.findViewById(R.id.bg_month_LL);
        LinearLayout bg_day_LL = (LinearLayout) itemView.findViewById(R.id.bg_day_LL);
        TextView bg_day = (TextView) itemView.findViewById(R.id.bg_day);
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


        if(advisoryPlanData.get(position).getMonth().trim().equals("")){
            bg_month_LL.setVisibility(View.GONE);

        }else{
            bg_month_LL.setVisibility(View.VISIBLE);
            month_day.setText("For "+ advisoryPlanData.get(position).getMonth());
        }
        if(advisoryPlanData.get(position).getDay().trim().equals("")){
            bg_day_LL.setVisibility(View.GONE);

        }else{
            bg_day_LL.setVisibility(View.VISIBLE);
            bg_day.setText("Valid for "+ advisoryPlanData.get(position).getDay()+" days");
        }


        if(advisoryPlanData.get(position).getStatus().equals("1")){
            upgradedLL.setVisibility(View.VISIBLE);
            upgrade_now_btn.setVisibility(View.GONE);
        }else{
            upgradedLL.setVisibility(View.GONE);
            upgrade_now_btn.setVisibility(View.VISIBLE);
        }

        if(advisoryPlanData.get(position).getImage()!=null){
            if(!advisoryPlanData.get(position).getImage().equals(""))
                Glide.with(context.getApplicationContext()).load(advisoryPlanData.get(position).getImage()).into(image_logo);
//                Glide.with(context).load(advisoryPlanData.get(position).getImage()).into(image_logo);


        }


        premium_name.setText(advisoryPlanData.get(position).getName());
        days.setText(advisoryPlanData.get(position).getDays()+" Days");
        mathces_count.setText("View upto "+ advisoryPlanData.get(position).getMatches()+" Contact Numbers");
        amount.setText("\u20B9" + advisoryPlanData.get(position).getAmount());

        if(advisoryPlanData.get(position).getDiscount().equals("")){
            disountLL.setVisibility(View.GONE);

        }else{
            disountLL.setVisibility(View.VISIBLE);

            discount_price.setText(advisoryPlanData.get(position).getDiscounted_price() );
            discount_price.setPaintFlags(discount_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


            off_txt.setText(advisoryPlanData.get(position).getDiscount()+ "% off");

        }

        upgrade_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (clickListener!=null){

                    clickListener.paymentButtonClick(String.valueOf(user.getUser_id()),advisoryPlanData.get(position).getAmount(),position,advisoryPlanData);

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

                                    clickListener.paymentButtonClick(String.valueOf(user.getUser_id()),advisoryPlanData.get(position).getAmount(),position,advisoryPlanData);

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
                                       ArrayList<AdvisorPlan.AdvisoryPlanData> advisoryPlanData);

    }


}

