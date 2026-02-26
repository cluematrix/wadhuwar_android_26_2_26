package com.WadhuWarProject.WadhuWar.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.model.SliderListImage;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jsibbold.zoomage.ZoomageView;
import com.ortiz.touchview.TouchImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class ImagesAdapter extends PagerAdapter {


    Context context;

    ArrayList<SliderListImage> myList = new ArrayList<>();

    // Layout Inflater
    LayoutInflater mLayoutInflater;
    LayoutInflater inflater;


    // Viewpager Constructor
    public ImagesAdapter(Context context, ArrayList<SliderListImage> myList) {
        this.context = context;
        this.myList = myList;
    }

    @Override
    public int getCount() {
        if(myList!=null)
            return myList.size();
        else
            return 0;
    }



    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
//        View itemView = mLayoutInflater.inflate(R.layout.item_images, container, false);

        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_images,container,false);
        TextView total_num,current_num;

        TouchImageView imageView = view.findViewById(R.id.imageViewMain);
        total_num = view.findViewById(R.id.total_num);
        current_num = view.findViewById(R.id.current_num);

        total_num.setText(String.valueOf(myList.size()));
        current_num.setText(String.valueOf(position+1));

        System.out.println("detail img=========" + myList.get(position).getImgs());
//        Glide.with(context).load(myList.get(position).getImgs()).into(imageView);


        Glide.with(context.getApplicationContext())
                .asBitmap()
                .load(myList.get(position).getImgs())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        imageView.setImageBitmap(resource);
                    }
                });



        Objects.requireNonNull(container).addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((FrameLayout) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view==(FrameLayout)obj;
    }

}