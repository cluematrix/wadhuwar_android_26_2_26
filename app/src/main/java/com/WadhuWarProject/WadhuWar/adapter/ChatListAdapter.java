package com.WadhuWarProject.WadhuWar.adapter;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.activity.ChatActivity;
import com.WadhuWarProject.WadhuWar.activity.NewsDetailActivity;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.ChatList;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.NewsList;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.util.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<ChatList.ChatListData> chatListData = new ArrayList<>();
    ChatList chatList;
    Context context;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    InsertResponse updateStatus;
    UserData userData;

    public ChatListAdapter(Context context) {
        this.context = context;
        this.chatListData = new ArrayList<>();

        userData = SharedPrefManager.getInstance(context).getUser();
    }


    public ArrayList<ChatList.ChatListData> getNewsListData() {
        return chatListData;
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
        View v1 = inflater.inflate(R.layout.item_chat_list, parent, false);
        viewHolder = new ProductViewHolder(v1);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position) {        //getting the product of the specified position

        switch (getItemViewType(position)) {

            case ITEM:
                final ProductViewHolder holder = (ProductViewHolder) holder1;

                if (chatListData != null && chatListData.size() != 0) {
                    holder.itemView.setVisibility(View.VISIBLE);

                    holder.msg.setText(chatListData.get(position).getMessage());

                    holder.name.setText(chatListData.get(position).getName());
                    Glide.with(context.getApplicationContext()).load(chatListData.get(position).getImage()).into(holder.img);

                    String str = chatListData.get(position).getTimestamp();
                    String[] splitStr = str.split("\\s+");

                    String date1 = splitStr[0];
                    String date2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    System.out.println("date1 : " + splitStr[0]);
                    System.out.println("date2 : " + date2);

                    if (date1.equals(date2)) {
                        System.out.println("Date1 is equal Date2");
                        holder.date_time.setText(splitStr[1] + " " + splitStr[2]);

                    } else {
                        holder.date_time.setText(splitStr[0]);


                    }
                    if (chatListData.get(position).getMcount().equals("0")) {
                        holder.new_msg_dot.setVisibility(View.GONE);
                    } else {
                        holder.new_msg_dot.setVisibility(View.VISIBLE);
                    }

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            holder.new_msg_dot.setVisibility(View.GONE);
                            sendStatus(chatListData.get(position).getOp_id(), userData.getUser_id());
                            System.out.println("chatListData.get(position).getOp_id()======" + chatListData.get(position).getOp_id());

                            Intent i = new Intent(context, ChatActivity.class);
                            i.putExtra("receiver_id", chatListData.get(position).getOp_id());
                            i.putExtra("receiver_name", chatListData.get(position).getName());
                            i.putExtra("receiver_image", chatListData.get(position).getImage());
                            context.startActivity(i);
                        }
                    });
                    if (chatListData.get(position).getChkonline().equals("1")) {
                        holder.online_iv.setVisibility(View.VISIBLE);
                    } else {
                        holder.online_iv.setVisibility(View.GONE);
                    }


                } else {
                    holder.itemView.setVisibility(View.GONE);
                }

                break;
            case LOADING:
//                Do nothing

                final LoadingVH holder2 = (LoadingVH) holder1;

                holder2.loadmore_progress.getIndeterminateDrawable().setColorFilter(Color.parseColor("#AB0A0A0A"), android.graphics.PorterDuff.Mode.MULTIPLY);

                break;
        }

    }

    private void sendStatus(String op_id, int user_id) {

        System.out.println("op_id---------"+op_id);
        System.out.println("user_id---------"+user_id);
        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.updateChatRequest(op_id,user_id);
        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                updateStatus = response.body();

                System.out.println("update done=======" + new Gson().toJson(updateStatus));

                if (response.isSuccessful()) {
                    if (updateStatus.getResid().equals("200")) {
                        System.out.println("update done=======");
                    }

                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {


        if (chatListData != null) {
            if (chatListData.size() == 0) {
                return 1;
            } else {
                return chatListData.size();
            }
        } else {
            return 0;
        }


    }


    @Override
    public int getItemViewType(int position) {
        return (position == chatListData.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }



       /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(ChatList.ChatListData mc) {
        chatListData.add(mc);
        notifyItemInserted(chatListData.size() - 1);
    }


    public void addAll(ArrayList<ChatList.ChatListData> mcList) {
        for (ChatList.ChatListData mc : mcList) {
            add(mc);

        }
    }


    public void remove(ChatList.ChatListData p) {
        int position = chatListData.indexOf(p);
        if (position > -1) {
            chatListData.remove(position);
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

        add(new ChatList.ChatListData());
    }


    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = chatListData.size() - 1;
        ChatList.ChatListData item = getItem(position);

        if (item != null) {
            chatListData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public ChatList.ChatListData getItem(int position) {
        return chatListData.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */


    protected class ProductViewHolder extends RecyclerView.ViewHolder {


        TextView name, msg, date_time;
        ImageView img, new_msg_dot;
        FrameLayout online_iv;

        public ProductViewHolder(View itemView) {
            super(itemView);

            online_iv = itemView.findViewById(R.id.online_iv);
            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
            msg = itemView.findViewById(R.id.msg);
            date_time = itemView.findViewById(R.id.date_time);
            new_msg_dot = itemView.findViewById(R.id.new_msg_dot);

        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        ProgressBar loadmore_progress;

        public LoadingVH(View itemView) {
            super(itemView);


            loadmore_progress = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);
        }
    }

}