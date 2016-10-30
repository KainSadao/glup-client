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
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (View)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        int resId = 0;
        switch (position) {
            case 0:
                resId = R.id.step1;
                break;
            case 1:
                resId = R.id.step2;
                break;
            case 2:
                resId = R.id.step3;
                break;
        }
        return container.findViewById(resId);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
