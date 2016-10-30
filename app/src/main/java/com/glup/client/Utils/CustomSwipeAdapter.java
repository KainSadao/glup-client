package com.glup.client.Utils;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.glup.client.R;

/**
 * Created by Kain on 30/10/2016.
 */

public class CustomSwipeAdapter extends PagerAdapter{

    private int[] images = {R.drawable.step1, R.drawable.step2, R.drawable.step3};
    private Context context;
    private LayoutInflater li;

    public CustomSwipeAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(R.layout.swipe_images, container, false);
        ImageView image = (ImageView)view.findViewById(R.id.image_viewpager);
        image.setImageResource(images[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
