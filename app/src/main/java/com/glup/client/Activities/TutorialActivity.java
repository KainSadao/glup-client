package com.glup.client.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.glup.client.R;
import com.glup.client.Utils.CustomSwipeAdapter;

/**
 * Created by Kain on 24/10/2016.
 */

public class TutorialActivity extends AppCompatActivity{

    private ViewPager viewPager;
    private CustomSwipeAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        viewPager = (ViewPager)findViewById(R.id.pageviewer);
        viewPager.setOffscreenPageLimit(3);
        adapter = new CustomSwipeAdapter(this);
        viewPager.setAdapter(adapter);
        /*viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(TutorialActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/
    }
}
