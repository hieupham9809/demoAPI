package com.example.zingdemoapi.adapter;


import android.content.Context;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.example.zingdemoapi.R;
import com.example.zingdemoapi.datamodel.Banner;
import com.example.zingdemoapi.datamodel.Home;
import com.example.zingdemoapi.datamodel.Program;
import com.example.zingdemoapi.datamodel.Type;
import com.example.zingdemoapi.datamodel.Video;
import com.example.zingdemoapi.adapter.viewholder.BannerBoxViewHolder;
import com.example.zingdemoapi.adapter.viewholder.BaseHomeViewHolder;
import com.example.zingdemoapi.adapter.viewholder.ProgramGridViewHolder;
import com.example.zingdemoapi.adapter.viewholder.VideoViewHolder;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;


public class DataAdapter extends RecyclerView.Adapter<BaseHomeViewHolder> {

    private Home homedata;
    private RequestManager requestManager;
    private Context context; // Activity/Fragment 's context

    public DataAdapter(Home home, Context mContext, RequestManager mRequestManager, CompositeDisposable mCompositeDisposable) {
        homedata = home;
        context = mContext;
        requestManager = mRequestManager;
    }


    @Override
    public BaseHomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == Type.BANNER) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);
            return new BannerBoxViewHolder(view, context, requestManager);
        } else if (viewType == Type.PROGRAM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_row, parent, false);
            return new ProgramGridViewHolder(view, context, requestManager);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_row, parent, false);
            return new VideoViewHolder(view, context, requestManager);
        }

    }


    @Override
    public int getItemViewType(int position) {
        return homedata.get(position).getType();

    }

    @Override
    public void onBindViewHolder(BaseHomeViewHolder holder, int position) {
        int type = getItemViewType(position);

        switch (type) {
            case Type.BANNER:
                List<Banner> banners = homedata.get(position).getPageList();
                BannerBoxViewHolder bannerBoxViewHolder = ((BannerBoxViewHolder) holder);
                bannerBoxViewHolder.setData(banners);
                break;

            case Type.PROGRAM:
                ProgramGridViewHolder programGridViewHolder = (ProgramGridViewHolder)holder;
                programGridViewHolder.setData(homedata.get(position).<Program>getPageList(), homedata.get(position).getTitle());
                break;
            case Type.VIDEO:
                VideoViewHolder videoViewHolder = (VideoViewHolder) holder;
                videoViewHolder.setData(homedata.get(position).<Video>getPageList(), homedata.get(position).getTitle());
                break;

            default:
                break;
        }


    }

    @Override
    public int getItemCount() {
        if (homedata != null) {
            return homedata.size();
        } else {
            return 0;
        }
    }


}
