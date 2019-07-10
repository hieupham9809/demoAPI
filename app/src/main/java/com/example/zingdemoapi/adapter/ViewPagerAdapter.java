package com.example.zingdemoapi.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.example.zingdemoapi.R;
import com.example.zingdemoapi.datamodel.Banner;
import com.example.zingdemoapi.request.GlideRequest;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<Banner> mResources;
    RequestManager requestManager;

    public ViewPagerAdapter(Context context, List<Banner> resources, RequestManager mRequestManager) {
        mResources = resources;
        requestManager = mRequestManager;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (mResources != null) {
            return mResources.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.viewpager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_pager_item);
        GlideRequest.getInstance().loadImage(requestManager, mResources.get(position).getThumbnail(), imageView, R.drawable.default_thumbnail);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
