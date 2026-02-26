package com.WadhuWarProject.WadhuWar.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.model.Chat;
import com.WadhuWarProject.WadhuWar.model.UserData;

import java.util.ArrayList;

public class ChatTestAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    UserData userData;
    private Context mContext;
    private ArrayList<Chat.ChatListData> chatListData = new ArrayList<>();
    Context context;
    private static final int ITEM = 0;
    private static final int LOADING = 3;
    private boolean isLoadingAdded = false;


    public ChatTestAdapter(Context context) {
        this.mContext = context;
        this.chatListData = new ArrayList<>();

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


        if(position == chatListData.size() - 1 && isLoadingAdded){
            return  LOADING;
        }else if (chatListData.get(position).getSender_id().equals(login_user_id)) {
                    // If the current user is the sender of the message
                    return VIEW_TYPE_MESSAGE_SENT;
                } else {
                    // If some other user sent the message
                    return VIEW_TYPE_MESSAGE_RECEIVED;
                }


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
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_sender, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_receiver, parent, false);
            return new ReceivedMessageHolder(view);
        }  else if (viewType == LOADING) {
            View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress, parent, false);
            return new LoadingVH(v2);

        }
        return null;
    }

    public ArrayList<Chat.ChatListData> getNewChatData() {
        return chatListData;
    }


    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        UserMessage message = (UserMessage) chatListData.get(position);
        String message =  chatListData.get(position).getMessage();


        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(chatListData.get(position));
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(chatListData.get(position));
                break;

            case LOADING:
                final LoadingVH holder2 = (LoadingVH) holder;

                holder2.loadmore_progress.getIndeterminateDrawable().setColorFilter(Color.parseColor("#AB0A0A0A"),android.graphics.PorterDuff.Mode.MULTIPLY);

                break;
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

//        add(new Chat.ChatListData());
        for(int i=0;i<chatListData.size();i++){
            add(new Chat.ChatListData(chatListData.get(i).getChat_id(),chatListData.get(i).getMessage(),chatListData.get(i).getSender_id(),
                    chatListData.get(i).getReceiver_id(),chatListData.get(i).getTimestamp()));
        }

        }



    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = chatListData.size() - 1;
        Chat.ChatListData item = getItem(position);

        if (item != null) {
            chatListData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Chat.ChatListData getItem(int position) {
        return chatListData.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */



    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_gchat_message_me);
            timeText = (TextView) itemView.findViewById(R.id.text_gchat_date_me);
        }

        void bind(Chat.ChatListData msg) {
            messageText.setText(msg.getMessage());
            timeText.setText(msg.getTimestamp());
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
        ImageView profileImage;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_gchat_message_other);
            timeText = (TextView) itemView.findViewById(R.id.text_gchat_date_other);

        }

        void bind(Chat.ChatListData message) {
            messageText.setText(message.getMessage());

            // Format the stored timestamp into a readable String using method.
            timeText.setText(message.getTimestamp());

//            nameText.setText("aaaa");

            // Insert the profile image from the URL into the ImageView.
//            Utils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(), profileImage);
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