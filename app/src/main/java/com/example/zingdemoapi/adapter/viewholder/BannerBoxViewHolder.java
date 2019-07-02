package com.example.zingdemoapi.adapter.viewholder;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;


import com.bumptech.glide.RequestManager;
import com.example.zingdemoapi.R;
import com.example.zingdemoapi.adapter.ViewPagerAdapter;
import com.example.zingdemoapi.datamodel.Banner;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BannerBoxViewHolder extends BaseHomeViewHolder<Banner> {

    private ViewPager viewPager;
    private TabLayout indicator;
    private ViewPagerAdapter mPagerAdapter;
    private Context context;


    @Override
    public void setData(List<Banner> list, String mTitle) {

    }

    public void setData(List<Banner> list) {
        if (mPagerAdapter == null) {
            mPagerAdapter = new ViewPagerAdapter(context, list, requestManager);
        }

        viewPager.setAdapter(mPagerAdapter);
        indicator.setupWithViewPager(viewPager, true);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(list.size()), 4000, 6000);

    }

    public BannerBoxViewHolder(@NonNull View itemView, Context mContext, RequestManager mRequestManager) {
        super(itemView, mRequestManager);
        context = mContext;

        viewPager = itemView.findViewById(R.id.viewPager);
        viewPager.getLayoutParams().width = Resources.getSystem().getDisplayMetrics().widthPixels;
        viewPager.getLayoutParams().height = viewPager.getLayoutParams().width * 9 / 16;

        indicator = itemView.findViewById(R.id.indicator);

    }

    private class SliderTimer extends TimerTask {
        private int size;

        SliderTimer(int mSize) {
            size = mSize;
        }

        @Override
        public void run() {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() < size - 1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
}
