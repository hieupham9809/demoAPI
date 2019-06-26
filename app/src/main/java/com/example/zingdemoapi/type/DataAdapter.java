package com.example.zingdemoapi.type;


import android.content.Context;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zingdemoapi.R;


public class DataAdapter extends RecyclerView.Adapter<BaseHomeViewHolder> {

    private Home homedata;

    private VideoViewHolder videoViewHolder;
    private BannerBoxViewHolder bannerBoxViewHolder;


    Context context; // Activity/Fragment 's context

    public DataAdapter(Home home, Context mContext) {
        homedata = home;
        context = mContext;
    }


    @Override
    public BaseHomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == Type.BANNER) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);
            return new BannerBoxViewHolder(view, context);
        } else if (viewType == Type.PROGRAM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_row, parent, false);
            return new ProgramGridViewHolder(view, context);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_row, parent, false);
            return new VideoViewHolder(view, context);
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
                bannerBoxViewHolder = ((BannerBoxViewHolder) holder);

                bannerBoxViewHolder.setData(homedata.get(position).getPageList());
                break;

            case Type.PROGRAM:
            case Type.VIDEO:
                videoViewHolder = (VideoViewHolder) holder;

                videoViewHolder.setData(homedata.get(position));
                break;

            default:
                break;
        }


    }

    @Override
    public int getItemCount() {

        return homedata.size();
    }


}