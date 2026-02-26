package com.WadhuWarProject.WadhuWar.adapter;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.activity.ChatActivity;
import com.WadhuWarProject.WadhuWar.activity.DetailMatchesActivity;
import com.WadhuWarProject.WadhuWar.activity.DetailMatchesForSearchActivity;
import com.WadhuWarProject.WadhuWar.activity.PremiumActivity;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.extras.RoundedImageView;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.MemberList;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InboxSendAdapter extends   RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<MemberList.MemberListData> sendMemberListData ;
    Context context;
    String login_user_id;
    String loginUserPremiumCheck;
    BottomSheetDialog mBottomSheetDialog;
    View sheetView;
    ImageView close_btn;
    RoundedImageView img_box;
    TextView contact_no,whatapp_no,chat,mail;
    LinearLayout upgrade_now_btn;
    String loginUserConnectedCheck;
    int MY_PERMISSIONS_REQUEST_CALL_PHONE = 100;

    InsertResponse rejectResponse;

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;

    public InboxSendAdapter(Context context, String login_user_id, String loginUserPremiumCheck ) {
        this.context = context;
        this.login_user_id = login_user_id;
        this.loginUserPremiumCheck = loginUserPremiumCheck;
        this.sendMemberListData = new ArrayList<>();


    }


    public   ArrayList<MemberList.MemberListData> getMemberListData() {
        return sendMemberListData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_inbox_accepted, parent, false);
        viewHolder = new ProductViewHolder(v1);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position)   {        //getting the product of the specified position

        switch (getItemViewType(position)) {

            case ITEM:
                final ProductViewHolder holder = (ProductViewHolder) holder1;


                if(sendMemberListData!=null && sendMemberListData.size()!=0) {
//                    holder.contactLL.setVisibility(View.GONE);
                    holder.contactLL.setVisibility(View.VISIBLE);


                    System.out.println("member id 123===========" + sendMemberListData.get(position).getId());
                    holder.name.setText(sendMemberListData.get(position).getName());
                    holder.date.setText(sendMemberListData.get(position).getDate());

                    if(sendMemberListData.get(position).getImage()!=null) {
                        if (!sendMemberListData.get(position).getImage().equals("")) {
                            Glide.with(context.getApplicationContext()).load(sendMemberListData.get(position).getImage()).into(holder.img_profile);
                        }else{

                            if(sendMemberListData.get(position).getGender().equals("Female")){
                                holder.img_profile.setImageResource(R.drawable.female_avtar);
                            }else {
                                holder.img_profile.setImageResource(R.drawable.male_avtar);
                            }

                        }
                    }else{
                        if(sendMemberListData.get(position).getGender().equals("Female")){
                            holder.img_profile.setImageResource(R.drawable.female_avtar);
                        }else {
                            holder.img_profile.setImageResource(R.drawable.male_avtar);
                        }
                    }



                    if(!sendMemberListData.get(position).getAge().equals("Not Specified") &&
                            !sendMemberListData.get(position).getHeight().equals("Not Specified")  &&
                            !sendMemberListData.get(position).getCaste().equals("Not Specified") ){

                        holder.t1.setText(sendMemberListData.get(position).getAge()+" yrs, " + sendMemberListData.get(position).getHeight() +
                                ", "+ sendMemberListData.get(position).getCaste());

                    }else if(sendMemberListData.get(position).getAge().equals("Not Specified") &&
                            !sendMemberListData.get(position).getHeight().equals("Not Specified")  &&
                            !sendMemberListData.get(position).getCaste().equals("Not Specified") ){

                        holder.t1.setText( sendMemberListData.get(position).getHeight() +
                                ", "+ sendMemberListData.get(position).getCaste());
                    }else if(!sendMemberListData.get(position).getAge().equals("Not Specified") &&
                            sendMemberListData.get(position).getHeight().equals("Not Specified")  &&
                            !sendMemberListData.get(position).getCaste().equals("Not Specified") ){
                        holder.t1.setText(sendMemberListData.get(position).getAge()+" yrs, "  +
                                ", "+ sendMemberListData.get(position).getCaste());

                    }else if(!sendMemberListData.get(position).getAge().equals("Not Specified") &&
                            !sendMemberListData.get(position).getHeight().equals("Not Specified")  &&
                            sendMemberListData.get(position).getCaste().equals("Not Specified")){
                        holder.t1.setText(sendMemberListData.get(position).getAge()+" yrs, " + sendMemberListData.get(position).getHeight() );

                    }else if(sendMemberListData.get(position).getAge().equals("Not Specified") &&
                            sendMemberListData.get(position).getHeight().equals("Not Specified")  &&
                            !sendMemberListData.get(position).getCaste().equals("Not Specified")){
                        holder.t1.setText( sendMemberListData.get(position).getCaste());

                    }else if(!sendMemberListData.get(position).getAge().equals("Not Specified") &&
                            sendMemberListData.get(position).getHeight().equals("Not Specified")  &&
                            sendMemberListData.get(position).getCaste().equals("Not Specified") ){

                        holder.t1.setText(sendMemberListData.get(position).getAge()+" yrs" );

                    }else if(sendMemberListData.get(position).getAge().equals("Not Specified") &&
                            !sendMemberListData.get(position).getHeight().equals("Not Specified")  &&
                            sendMemberListData.get(position).getCaste().equals("Not Specified") ){

                        holder.t1.setText(sendMemberListData.get(position).getHeight());

                    }else if(sendMemberListData.get(position).getAge().equals("Not Specified") &&
                            sendMemberListData.get(position).getHeight().equals("Not Specified")  &&
                            sendMemberListData.get(position).getCaste().equals("Not Specified") ){
                        holder.t1.setVisibility(View.GONE);
                    }


                    /*note - add education */
                    if(!sendMemberListData.get(position).getDistrict().equals("Not Specified") &&
                            !sendMemberListData.get(position).getState().equals("Not Specified") ){
                        holder.t2.setText(sendMemberListData.get(position).getDistrict()+", "+sendMemberListData.get(position).getState() );

                    }else if(!sendMemberListData.get(position).getDistrict().equals("Not Specified") &&
                            sendMemberListData.get(position).getState().equals("Not Specified") ){
                        holder.t2.setText(sendMemberListData.get(position).getDistrict());

                    }else if(sendMemberListData.get(position).getDistrict().equals("Not Specified") &&
                            !sendMemberListData.get(position).getState().equals("Not Specified") ){
                        holder.t2.setText(sendMemberListData.get(position).getState() );

                    }else if(sendMemberListData.get(position).getDistrict().equals("Not Specified") &&
                            sendMemberListData.get(position).getState().equals("Not Specified") ){
                        holder.t2.setVisibility(View.GONE);
                    }


                    if(!sendMemberListData.get(position).getOccupation().equals("Not Specified")){
                        holder.t3.setText(sendMemberListData.get(position).getOccupation());
                    }else{
                        holder.t3.setVisibility(View.GONE);
                    }

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(context, DetailMatchesForSearchActivity.class);
                            i.putExtra("userid",sendMemberListData.get(position).getId());
                            context.startActivity(i);
                        }
                    });


                    holder.option_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            PopupMenu popup = new PopupMenu(context, view);
                            MenuInflater inflater = popup.getMenuInflater();
                            inflater.inflate(R.menu.decline_menu, popup.getMenu());
                            popup.show();
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    if (item.getItemId()== R.id.decline){



                                        new AlertDialog.Builder(context)
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .setMessage("Are you sure want to Decline?")
                                                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                                                {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        rejectProfile(sendMemberListData.get(position).getId(),login_user_id,position,holder);
                                                    }

                                                })
                                                .setNegativeButton("No", null)
                                                .show();



                                    }
                                    return false;
                                }
                            });
                        }
                    });


                    holder.whatapp_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            if(loginUserPremiumCheck.equals("1") && sendMemberListData.get(position).getIs_connected().equals("1")){

                                String whatapp_no ="123456789";

                                String phoneNumber = "91"+whatapp_no; //without '+'

                                Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" );

                                Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);

                                context.startActivity(sendIntent);

                            }else if(!loginUserPremiumCheck.equals("1")){
                                openBottomSheet(position);

                            }else if(!sendMemberListData.get(position).getIs_connected().equals("1")){
                                System.out.println("1111111111122222222233333333333333");
                                toast("Please send request or member not connected !");
                            }
                        }
                    });



                    holder.call_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            String mo_num = "";

                            if(loginUserPremiumCheck.equals("1") && sendMemberListData.get(position).getIs_connected().equals("1")){
                                Intent it = new Intent(Intent.ACTION_CALL);
                                it.setData(Uri.parse("tel:"+ mo_num));


                                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE},
                                            MY_PERMISSIONS_REQUEST_CALL_PHONE);

                                } else {
                                    //You already have permission
                                    try {
                                        context.startActivity(it);
                                    } catch(SecurityException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }else if(!loginUserPremiumCheck.equals("1")){
                                openBottomSheet(position);

                            }else if(!sendMemberListData.get(position).getIs_connected().equals("1")){

                                toast("Please send request or member not connected !");
                            }


                        }
                    });


                    holder.chat_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(context, ChatActivity.class);
                            i.putExtra("receiver_id",sendMemberListData.get(position).getId());
                            i.putExtra("receiver_name",sendMemberListData.get(position).getName());
                            i.putExtra("receiver_image",sendMemberListData.get(position).getImage());
                            context.startActivity(i);

                        }
                    });





                }



                break;
            case LOADING:
//                Do nothing

                final LoadingVH holder2 = (LoadingVH) holder1;

                holder2.loadmore_progress.getIndeterminateDrawable().setColorFilter(Color.parseColor("#AB0A0A0A"),android.graphics.PorterDuff.Mode.MULTIPLY);

                break;
        }

    }

    public void toast(String message)
    {
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.item_toast_red, null);
        TextView textView = (TextView) view.findViewById(R.id.txt_dia);
        textView.setText(message);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM|Gravity.FILL_HORIZONTAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public int getItemCount () {


        if (sendMemberListData != null) {
            if (sendMemberListData.size() == 0) {
                return 1;
            } else {
                return sendMemberListData.size();
            }
        } else {
            return 0;
        }


    }



    @Override
    public int getItemViewType(int position) {
        return (position == sendMemberListData.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }



       /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(MemberList.MemberListData mc) {
        sendMemberListData.add(mc);
        notifyItemInserted(sendMemberListData.size() - 1);
    }


    public void addAll(ArrayList<MemberList.MemberListData> mcList) {
        for (MemberList.MemberListData mc : mcList) {
            add(mc);

        }
    }





    public void remove(MemberList.MemberListData p) {
        int position = sendMemberListData.indexOf(p);
        if (position > -1) {
            sendMemberListData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;

        add(new MemberList.MemberListData());
    }




    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = sendMemberListData.size() - 1;
        MemberList.MemberListData item = getItem(position);

        if (item != null) {
            sendMemberListData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public MemberList.MemberListData getItem(int position) {
        return sendMemberListData.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    protected  class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView t1,t2,t3,name,date;
        RoundedImageView img_profile;
        CardView cv;
        ImageView option_btn;
        LinearLayout whatapp_btn,call_btn,chat_btn,contactLL;
        public ProductViewHolder(View itemView) {
            super(itemView);

            date =  itemView.findViewById(R.id.date);
            contactLL =  itemView.findViewById(R.id.contactLL);
            chat_btn =  itemView.findViewById(R.id.chat_btn);
            call_btn =  itemView.findViewById(R.id.call_btn);
            whatapp_btn =  itemView.findViewById(R.id.whatapp_btn);
            option_btn =  itemView.findViewById(R.id.option_btn);
            name =  itemView.findViewById(R.id.name);
            t1 =  itemView.findViewById(R.id.t1);
            t2 =  itemView.findViewById(R.id.t2);
            t3 =  itemView.findViewById(R.id.t3);
            img_profile =  itemView.findViewById(R.id.img_profile);
            cv =  itemView.findViewById(R.id.cv);

        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        ProgressBar loadmore_progress;
        public LoadingVH(View itemView) {
            super(itemView);


            loadmore_progress = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);
        }
    }







    public  void  openBottomSheet(int position){


        mBottomSheetDialog = new BottomSheetDialog(context);
        sheetView = ((FragmentActivity)context).getLayoutInflater().inflate(R.layout.inbox_bottom_sheet_content,null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();

        close_btn = sheetView.findViewById(R.id.close_btn);
        img_box = sheetView.findViewById(R.id.img_box);
        contact_no = sheetView.findViewById(R.id.contact_no);
        whatapp_no = sheetView.findViewById(R.id.whatapp_no);
        chat = sheetView.findViewById(R.id.chat);
        mail = sheetView.findViewById(R.id.mail);
        upgrade_now_btn = sheetView.findViewById(R.id.upgrade_now_btn);

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
            }
        });




//        Glide.with(context).load(sendMemberListData.get(position).getImage()).into(img_box);


        if(sendMemberListData.get(position).getImage()!=null) {
            if (!sendMemberListData.get(position).getImage().equals("")) {
                Glide.with(context.getApplicationContext()).load(sendMemberListData.get(position).getImage()).into(img_box);
            }else{

                if(sendMemberListData.get(position).getGender().equals("Female")){
                    img_box.setImageResource(R.drawable.female_avtar);
                }else {
                    img_box.setImageResource(R.drawable.male_avtar);
                }

            }
        }else{
            if(sendMemberListData.get(position).getGender().equals("Female")){
                img_box.setImageResource(R.drawable.female_avtar);
            }else {
                img_box.setImageResource(R.drawable.male_avtar);
            }
        }



        contact_no.setText("+91-**********");
        whatapp_no.setText("Chat via Whatsapp");
        chat.setText("Message via Shaadi Chat");
        mail.setText("***************@gmail.com");

        upgrade_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, PremiumActivity.class);
                context.startActivity(i);
                mBottomSheetDialog.dismiss();
            }
        });







    }





    public  void  rejectProfile(String member_id, String login_user_id,int pos, ProductViewHolder holder){


        System.out.println("member_id------" + member_id);
        System.out.println("login_user_id------" + login_user_id);
        System.out.println("pos------" + pos);
        System.out.println("holder------" + holder);


        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.requestRejected(member_id,login_user_id);
        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                rejectResponse = response.body();
//                holder.pb_decline.setVisibility(View.GONE);

//                System.out.println("res reject******" + new Gson().toJson(rejectResponse));

                if (response.isSuccessful()) {

                    if(rejectResponse.getResid().equals("200")){
                        sendMemberListData.remove(pos);
//                        notifyDataSetChanged();
                        notifyItemRemoved(pos);
//                        holder.itemView.setVisibility(View.GONE);

                        if(sendMemberListData.size()>0){
                            notifyItemRangeChanged(pos,sendMemberListData.size());
                        }

                        Toast.makeText(context,"Request Decline.",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context,rejectResponse.getResMsg(),Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
//                holder.pb_decline.setVisibility(View.GONE);
                System.out.println("msg1 error reject******" + t.toString());
                Toast.makeText(context,"Please Try again!",Toast.LENGTH_SHORT).show();

            }
        });


    }




}