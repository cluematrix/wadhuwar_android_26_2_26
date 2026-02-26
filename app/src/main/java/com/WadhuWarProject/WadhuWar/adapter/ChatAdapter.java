package com.WadhuWarProject.WadhuWar.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.activity.ChatActivity;
import com.WadhuWarProject.WadhuWar.activity.SuccessStoriesDetailActivity;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.Chat;
import com.WadhuWarProject.WadhuWar.model.FetchProfile;
import com.WadhuWarProject.WadhuWar.model.NewMatchesList;
import com.WadhuWarProject.WadhuWar.model.NewmatchesTabList;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import io.github.rockerhieu.emojicon.EmojiconTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
//    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
//    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    UserData userData;
    private Context mContext;
    private ArrayList<Chat.ChatListData> chatListData = new ArrayList<>();
    Context context;
    private static final int VIEW_TYPE_MESSAGE_SENT = 0;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 1;
    private static final int LOADING = 2;
    private boolean isLoadingAdded = false;
    ProgressDialog progressBar;
    FetchProfile fetchProfileLoginUser;
    static boolean isNetworkAvailable;
    private Calendar calendar,calendar11;
    String today_date11,today_date22;
    String receiver_image;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat dateFormat11;
    View view;

    public ChatAdapter(Context context, String receiver_image) {
        this.mContext = context;
        this.chatListData = new ArrayList<>();
        this.receiver_image = receiver_image;

    }

    @Override
    public int getItemCount () {


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


    


    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        userData = SharedPrefManager.getInstance(mContext).getUser();
        String login_user_id = String.valueOf(userData.getUser_id());


//        System.out.println("size chat list---------" + chatListData.size());

//        return (position == blogListData.size() - 1 && isLoadingAdded) ? LOADING : ITEM;


        if(chatListData.size()!=0 && position == chatListData.size() - 1 && isLoadingAdded){
            return  LOADING;
        }else if(chatListData.size()!=0) {
//            System.out.println("position check 11-----------" + chatListData.get(position).getSender_id());

            if (chatListData.get(position).getSender_id() != null) {
                if (chatListData.get(position).getSender_id().equals(login_user_id)) {
                    // If the current user is the sender of the message
                    return VIEW_TYPE_MESSAGE_SENT;
                } else {
                    // If some other user sent the message
                    return VIEW_TYPE_MESSAGE_RECEIVED;
                }
            }
        }
            return 0;



   /*     if (chatListData.get(position).getSender_id().equals(login_user_id)) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }*/
    }








    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {

            case VIEW_TYPE_MESSAGE_SENT:

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sender, parent, false);
                viewHolder = new SentMessageHolder(view);
                    break;
            case VIEW_TYPE_MESSAGE_RECEIVED:

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receiver, parent, false);
                viewHolder = new ReceivedMessageHolder(view);
                break;

            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    public ArrayList<Chat.ChatListData> getNewChatData() {
        return chatListData;
    }


    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        UserMessage message = (UserMessage) chatListData.get(position);


        if (chatListData.size() != 0) {

            switch (holder.getItemViewType()) {
                case VIEW_TYPE_MESSAGE_SENT:
                    ((SentMessageHolder) holder).bind(chatListData.get(position));
                    break;
                case VIEW_TYPE_MESSAGE_RECEIVED:
                    ((ReceivedMessageHolder) holder).bind(chatListData.get(position));
                    break;

                case LOADING:
                    final LoadingVH holder2 = (LoadingVH) holder;

                    holder2.loadmore_progress.getIndeterminateDrawable().setColorFilter(Color.parseColor("#AB0A0A0A"), android.graphics.PorterDuff.Mode.MULTIPLY);

                    break;
            }
        }
    }






    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(Chat.ChatListData mc) {
        chatListData.add(mc);
        notifyItemInserted(chatListData.size() - 1);

    }


    public void addAll(ArrayList<Chat.ChatListData> mcList) {
        for (Chat.ChatListData mc : mcList) {
            add(mc);

        }
    }





    public void remove(Chat.ChatListData p) {
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
        add(new Chat.ChatListData());

//        add(new Chat.ChatListData(chatListData.get(0).getChat_id(),chatListData.get(0).getMessage(),chatListData.get(0).getSender_id(),
//                chatListData.get(0).getReceiver_id(),chatListData.get(0).getTimestamp()));
//        for(int i=0;i<chatListData.size();i++){
//            add(new Chat.ChatListData(chatListData.get(0).getChat_id(),chatListData.get(0).getMessage(),chatListData.get(0).getSender_id(),
//                    chatListData.get(0).getReceiver_id(),chatListData.get(0).getTimestamp()));
//        }

        }



    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = chatListData.size() - 1;

        System.out.println("position check----------" +  position);

        Chat.ChatListData item = getItem(position);

        if (item != null) {
            chatListData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Chat.ChatListData getItem(int position) {

        if(position>-1)
            return chatListData.get(position);
        else
            return null;
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */



    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView  timeText;
        LinearLayout senderLL;


        EmojiconTextView messageText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText =  itemView.findViewById(R.id.text_gchat_message_me);
            timeText = (TextView) itemView.findViewById(R.id.text_gchat_date_me);
            senderLL = itemView.findViewById(R.id.senderLL);





        }


        @RequiresApi(api = Build.VERSION_CODES.O)
        void bind(Chat.ChatListData msg) {
            messageText.setText(msg.getMessage());
            senderLL.setVisibility(View.VISIBLE);
            dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            calendar = Calendar.getInstance();
            today_date11 = dateFormat.format(calendar.getTime());



            String date_str = msg.getTimestamp();
            String[] splited = date_str.split("\\s+");

            System.out.println("check date==========" + msg.getMessage() + "========>" + today_date11.equals(splited[0]));


            if(today_date11.equals(splited[0])){
                timeText.setText((splited[1]) + " "+(splited[2]));

            }else{
                timeText.setText(msg.getTimestamp());

            }

        }

    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView timeText1, nameText;
        EmojiconTextView messageText;
        ImageView receiver_img;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.text_gchat_message_other);
            timeText1 = (TextView) itemView.findViewById(R.id.text_gchat_date_other);
            receiver_img =  itemView.findViewById(R.id.receiver_img);

        }

        void bind(Chat.ChatListData message) {


            messageText.setText(message.getMessage());
//            Picasso.with(context).load(receiver_image).into(receiver_img);

            // Format the stored timestamp into a readable String using method.
//            timeText.setText(message.getTimestamp());



            dateFormat11 = new SimpleDateFormat("dd-MM-yyyy");
            calendar11 = Calendar.getInstance();

            today_date22 = dateFormat11.format(calendar11.getTime());

            String date_str = message.getTimestamp();
            String[] splited = date_str.split("\\s+");

            System.out.println("check date==========" + message.getMessage() + "========>" + today_date22.equals(splited[0]));


            if(today_date22.equals(splited[0])){
                timeText1.setText((splited[1]) + " "+(splited[2]));

            }else{
                timeText1.setText(message.getTimestamp());

            }




            /*=======gender code=====*/


/*

            System.out.println("user_id=====" + String.valueOf(userData.getUser_id()));

            Api apiService = RetrofitClient.getApiService();
            Call<FetchProfile> userResponse = apiService.fetchProfile(String.valueOf(userData.getUser_id()));
            userResponse.enqueue(new Callback<FetchProfile>() {

                @Override
                public void onResponse(Call<FetchProfile> call, Response<FetchProfile> response) {
                    fetchProfileLoginUser = response.body();
//                    progressBar.dismiss();

                    if (response.isSuccessful()) {

                        if(receiver_image!=null){
                            if(!receiver_image.equals("null") || !receiver_image.trim().equals("")){
                                Picasso.with(context).load(receiver_image).into(receiver_img);
                            }else{
                                if(fetchProfileLoginUser.getGender().equals("Female")){
                                    Picasso.with(context).load(R.drawable.female_avtar).into(receiver_img);

                                }else {
                                    Picasso.with(context).load(R.drawable.male_avtar).into(receiver_img);
                                }
                            }
                        }

                    }

                }

                @Override
                public void onFailure(Call<FetchProfile> call, Throwable t) {
                    System.out.println("msg1 my profile******" + t.toString());
//                    progressBar.dismiss();
//                    Toast.makeText(context, "Please Refresh!", Toast.LENGTH_SHORT).show();

//                    if(!isNetworkAvailable(context)){
//                        Toast.makeText(context, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();
//
//                    }else{
//                        Toast.makeText(context, "Please Refresh!", Toast.LENGTH_SHORT).show();
//
//                    }

                }
            });
*/

            /*=======gender code=====*/


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