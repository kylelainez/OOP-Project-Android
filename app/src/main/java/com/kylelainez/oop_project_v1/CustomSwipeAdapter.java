package com.kylelainez.oop_project_v1;

import android.content.Context;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class CustomSwipeAdapter extends PagerAdapter {
    private int[] image_resources = {R.drawable.sample_ad,
                                    R.drawable.sample_ad2,
                                    R.drawable.kfc_doubledown,
                                    R.drawable.chowking_ad};
    private Context ctx;
    private LayoutInflater layoutInflater;
    private int custom_position = 0;

    public CustomSwipeAdapter(Context ctx){
        this.ctx = ctx;
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);
    }

    public Object instantiateItem (ViewGroup container, int position){
        if (custom_position > 3)
                custom_position = 0;

//        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout,container,false);
        ImageView imageView = item_view.findViewById(R.id.ad_image);
        imageView.setImageResource(image_resources[custom_position]);
        custom_position++;
        container.addView(item_view);

        return item_view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
