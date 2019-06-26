package com.example.zingdemoapi.type;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;


import com.example.zingdemoapi.R;

import java.util.List;

public class BannerBoxViewHolder extends BaseHomeViewHolder<Banner> {

    private ViewPager viewPager;
    private ViewPagerAdapter mPagerAdapter;
    private Context context;

    @Override
    public void setData(List<Banner> list) {
        if (mPagerAdapter == null) {
            mPagerAdapter = new ViewPagerAdapter(context);
        }
        mPagerAdapter.setmResources(list);
        viewPager.setAdapter(mPagerAdapter);

    }

    public BannerBoxViewHolder(@NonNull View itemView, Context mContext) {
        super(itemView);
        context = mContext;

        viewPager = itemView.findViewById(R.id.viewPager);
        viewPager.getLayoutParams().width = Resources.getSystem().getDisplayMetrics().widthPixels;
        viewPager.getLayoutParams().height = viewPager.getLayoutParams().width * 9 / 16;


    }
}
