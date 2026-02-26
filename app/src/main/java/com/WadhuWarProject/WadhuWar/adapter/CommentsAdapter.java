package com.WadhuWarProject.WadhuWar.adapter;


import static com.WadhuWarProject.WadhuWar.adapter.SeeAllRecentlyViewedAdapter.isNetworkAvailable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.activity.DetailMatchesActivity;
import com.WadhuWarProject.WadhuWar.activity.ReasonForReportActivity;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.CommentList;
import com.WadhuWarProject.WadhuWar.model.CommonResponse;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {
    ArrayList<CommentList.CommentListData> commentListData = new ArrayList<>();
    Context context;
    InsertResponse deleteRes;
    InsertResponse editRes;
    UserData user;

    String _msg_et;
    int size;
    String gender;

    public CommentsAdapter(Context context, ArrayList<CommentList.CommentListData> commentListData, String gender) {
        this.context = context;
        this.commentListData = commentListData;
        this.gender = gender;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_comment, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        user = SharedPrefManager.getInstance(context).getUser();

        if (commentListData.get(position).getImage() != null) {
            if (commentListData.get(position).getImage().isEmpty() || commentListData.get(position).getImage().equals("null")) {
                Glide.with(context.getApplicationContext()).load(R.drawable.default_avtar).into(holder.profile_image);
            } else {
                Glide.with(context.getApplicationContext()).load(commentListData.get(position).getImage()).into(holder.profile_image);
            }
        }


        holder.date.setText(commentListData.get(position).getDate());
        holder.name.setText(commentListData.get(position).getName());
        holder.comment.setText(commentListData.get(position).getComment());

        if (commentListData.get(position).getMember_id().equals(String.valueOf(user.getUser_id()))) {
            holder.option_btn.setVisibility(View.VISIBLE);
            holder.option_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    PopupMenu popup = new PopupMenu(context, view);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.comment_dlt_edt, popup.getMenu());
                    popup.show();
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getItemId() == R.id.edit) {

                                final Dialog dialog = new Dialog(context);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setCancelable(true);
                                dialog.setContentView(R.layout.comment_edit_blog_dialog_box);
                                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


                                final EditText msg_et = (EditText) dialog.findViewById(R.id.msg_et);
                                final TextView send_btn_txt = dialog.findViewById(R.id.send_btn_txt);
                                final RelativeLayout c_pb = dialog.findViewById(R.id.c_pb);
                                final LinearLayout send_btn_LL = dialog.findViewById(R.id.send_btn_LL);

                                msg_et.setText(commentListData.get(position).getComment());

                                send_btn_LL.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        _msg_et = msg_et.getText().toString();
                                        System.out.println("comm-------" + _msg_et);


                                        editComment(commentListData.get(position).getCmt_id(), String.valueOf(user.getUser_id()), _msg_et, send_btn_txt, c_pb, dialog, position);

                                    }
                                });


                                dialog.show();


                            }

                            if (item.getItemId() == R.id.delete) {

                                new AlertDialog.Builder(context)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setMessage("Are you sure want to delete?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (commentListData.size() != 0)
                                                    deleteComment(commentListData.get(position).getCmt_id(), String.valueOf(user.getUser_id()), holder, position);
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

        } else {
            holder.option_btn.setVisibility(View.VISIBLE);
            holder.option_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    PopupMenu popup = new PopupMenu(context, view);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.comment_block_reject, popup.getMenu());
                    popup.show();
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getItemId() == R.id.block_cmt) {
                                showConfirmationDialogBoxBlock(position);
                            }

                            if (item.getItemId() == R.id.report_cmt) {
                                Intent i = new Intent(context, ReasonForReportActivity.class);
                                i.putExtra("CommentID", commentListData.get(position).getCmt_id());
                                i.putExtra("Fname", commentListData.get(position).getName());
                                i.putExtra("isCallFromComment", true);
                                context.startActivity(i);
                            }
                            return false;
                        }
                    });
                }
            });

        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (commentListData.get(position).getMember_id() != null && commentListData.get(position).getGender() != null) {


                    if (commentListData.get(position).getMember_id().equals(String.valueOf(user.getUser_id()))) {


                    } else {
                        if (commentListData.get(position).getGender().equals(gender)) {

                        } else {
                            Intent i = new Intent(context, DetailMatchesActivity.class);
                            i.putExtra("userid", commentListData.get(position).getMember_id());
                            context.startActivity(i);
                        }


                    }


                }

            }
        });


    }

    public void editComment(String comment_id, String login_user_id, String _msg_et, TextView send_btn_txt, RelativeLayout c_pb, Dialog dialog, int pos) {

        c_pb.setVisibility(View.VISIBLE);
        send_btn_txt.setText("Updating..");

        System.out.println("_msg_et===========" + _msg_et);
        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.editComment(comment_id, login_user_id, _msg_et);
        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                editRes = response.body();

                c_pb.setVisibility(View.GONE);
                send_btn_txt.setText("Update ");
                dialog.dismiss();


                if (response.isSuccessful()) {

//                    System.out.println("resp InsertResponse.......>>>" + new Gson().toJson(response.body()));

                    if (editRes.getResid().equals("200")) {


                        commentListData.set(pos, new CommentList.CommentListData(comment_id, commentListData.get(pos).getMember_id(), commentListData.get(pos).getImage(), commentListData.get(pos).getName(),
                                _msg_et, commentListData.get(pos).getDate(), commentListData.get(pos).getGender()));

                        dialog.dismiss();
                        Toast.makeText(context, "  Comment Update ", Toast.LENGTH_SHORT).show();

                        notifyDataSetChanged();


                    } else {
                        dialog.dismiss();
                        Toast.makeText(context, editRes.getResMsg(), Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                c_pb.setVisibility(View.GONE);
                send_btn_txt.setText("Update");
                dialog.dismiss();
                System.out.println("msg1 edit******" + t.toString());

            }
        });
    }

    public void deleteComment(String cmt_id, String login_user_id, ViewHolder holder, int pos) {

        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.deleteComment(cmt_id, login_user_id);
        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                deleteRes = response.body();

//                System.out.println("res deleteRes******" + new Gson().toJson(deleteRes));

                if (response.isSuccessful()) {

                    if (deleteRes.getResid().equals("200")) {
                        commentListData.remove(pos);

                        notifyItemRemoved(pos);
//                        holder.itemView.setVisibility(View.GONE);

                        if (commentListData.size() > 0) {
                            notifyItemRangeChanged(pos, commentListData.size());
                        }

                        Toast.makeText(context, " Comment Delete", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, deleteRes.getResMsg(), Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {
                System.out.println("msg1 error deleteRes******" + t.toString());
                Toast.makeText(context, "Please Try again!", Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public int getItemCount() {

        return commentListData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView profile_image;
        TextView name, date, comment;

        ImageView option_btn;

        public ViewHolder(View itemView) {
            super(itemView);
            profile_image = itemView.findViewById(R.id.profile_image);
            name = (TextView) itemView.findViewById(R.id.name);
            date = (TextView) itemView.findViewById(R.id.date);
            comment = (TextView) itemView.findViewById(R.id.comment);
            option_btn = itemView.findViewById(R.id.option_btn);


        }
    }

    private void showConfirmationDialogBoxBlock(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Set the message show for the Alert time
        builder.setMessage("Blocked comment will not able to view your Profile or contact you on Wadhuwar");
        builder.setTitle("Are you sure you want to Block this comment?");
        builder.setCancelable(false);

        builder.setPositiveButton("OK", (DialogInterface.OnClickListener) (dialog, which) -> {
            blockComment(position, dialog);

        });

        builder.setNegativeButton("CANCEL", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void blockComment(int position, DialogInterface dialog) {
        Api apiService = RetrofitClient.getApiService();
        Call<CommonResponse> commonResponseCall = apiService.blockComment(commentListData.get(position)
                .getCmt_id(), String.valueOf(user.getUser_id()));
        commonResponseCall.enqueue(new Callback<CommonResponse>() {

            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse commonResponse = response.body();
                if (response.isSuccessful()) {
                    dialog.cancel();
                    if (commonResponse.getResid() == 200) {
                        Toast.makeText(context, "Block this comment", Toast.LENGTH_SHORT).show();
                        ((Activity)context).finish();
                    } else {
                        Toast.makeText(context, commonResponse.getResMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

                System.out.println("err UpdateResponse******" + t.toString());
                if (!isNetworkAvailable(context)) {
//                    Toast.makeText(context, "Please Check Internet Connection!", Toast.LENGTH_SHORT).show();

                } else {
//                    Toast.makeText(context, "Please Refresh!", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

}