package com.WadhuWarProject.WadhuWar.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.WadhuWarProject.WadhuWar.R;
import com.viewpagerindicator.CirclePageIndicator;

public class SlideAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;
    CirclePageIndicator indicator;
    public SlideAdapter(Context context){
        this.context=context;
    }

    //Array
    public int[] list_images={

            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d

    };



    @Override
    public int getCount() {
        return list_images.length;
    }



    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view==(LinearLayout)obj;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_slide,container,false);

        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.slidelinearlayout);
        ImageView img = (ImageView)view.findViewById(R.id.slideimg);

        img.setImageResource(list_images[position]);

        System.out.println("slider ig-------");

        container.addView(view);



//        //====================
//
//
//        indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
//
//
//        indicator.setViewPager(viewPager);
//
//        final float density = getResources().getDisplayMetrics().density;
//
//        indicator.setStrokeColor(Color.parseColor("#ffffff"));
//
//        indicator.setStrokeWidth(3);
//        //Set circle indicator radius
//        indicator.setRadius(2 * density);
//
//
//

        //====================


        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}