package com.WadhuWarProject.WadhuWar.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.WadhuWarProject.WadhuWar.R;
import com.WadhuWarProject.WadhuWar.activity.ZoomActivity;
import com.WadhuWarProject.WadhuWar.model.SliderListImage;
import com.bumptech.glide.Glide;
import com.jsibbold.zoomage.ZoomageView;
import com.ortiz.touchview.TouchImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class BlogImagesAdapter extends PagerAdapter {


    Context context;

    ArrayList<SliderListImage> myList = new ArrayList<>();

    // Layout Inflater
    LayoutInflater mLayoutInflater;
    LayoutInflater inflater;


    // Viewpager Constructor
    public BlogImagesAdapter(Context context, ArrayList<SliderListImage> myList) {
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
        View view = inflater.inflate(R.layout.item_blog_images,container,false);
        TextView total_num,current_num;

        ZoomageView imageView = view.findViewById(R.id.imageViewMain);
        total_num = view.findViewById(R.id.total_num);
        current_num = view.findViewById(R.id.current_num);

        total_num.setText(String.valueOf(myList.size()));
        current_num.setText(String.valueOf(position+1));

        if(myList.get(position).getImgs().length()!=0) {

            Glide.with(context.getApplicationContext()).load(myList.get(position).getImgs()).into(imageView);


            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(context,
                            R.anim.zoom_in, R.anim.zoom_out).toBundle();


                    context.startActivity(
                            new Intent(context, ZoomActivity.class)
                                    .putExtra("img_url", myList.get(position).getImgs())
                            , bundle
                    );

                }
            });
        }


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