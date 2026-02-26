package com.WadhuWarProject.WadhuWar.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.model.Image;

import java.util.ArrayList;

public class ContactImageAdapter extends RecyclerView.Adapter<ContactImageAdapter.ViewHolder>{
    ArrayList<Image> img_array;
    private Image[] imgArry;

    Context context;

    // RecyclerView recyclerView;
    public ContactImageAdapter(Context context, ArrayList<Image> img_array) {
        this.context = context;
        this.img_array = img_array;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.custom_img, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Image myListData = img_array.get(position);

        System.out.println("size imge-------------" + img_array.size());


//
//        byte[] decodedString = Base64.decode(myListData.getImage(), Base64.DEFAULT);
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);
//        holder.img_photo.setImageBitmap(decodedByte);

        holder.img_photo.setImageBitmap(BitmapFactory.decodeFile(myListData.getImage()));



        holder.btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(view.getContext(),"click on item: ",Toast.LENGTH_LONG).show();

                img_array.remove(position);
                notifyItemRemoved(position);
                if(img_array.size()!=0)
                  notifyItemRangeChanged(position, img_array.size());
            }
        });
    }


    @Override
    public int getItemCount() {
        return img_array.size() > 5 ? 5 : img_array.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_photo;
       ImageButton btn_close;
        public ViewHolder(View itemView) {
            super(itemView);
            this.img_photo = (ImageView) itemView.findViewById(R.id.img_photo);
            this.btn_close = (ImageButton) itemView.findViewById(R.id.btn_close);

        }
    }
}