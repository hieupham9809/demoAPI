package com.example.zingdemoapi.type;


import android.content.Context;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zingdemoapi.R;

import java.util.List;


public class DataAdapter extends RecyclerView.Adapter<BaseHomeViewHolder> {

    private Home homedata;

    private Context context; // Activity/Fragment 's context

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
                List<Banner> banners = homedata.get(position).getPageList();
                BannerBoxViewHolder bannerBoxViewHolder = ((BannerBoxViewHolder) holder);
                bannerBoxViewHolder.setData(banners);
                break;

            case Type.PROGRAM:
                ProgramGridViewHolder programGridViewHolder = (ProgramGridViewHolder)holder;
                programGridViewHolder.setData(homedata.get(position).<Program>getPageList());
                break;
            case Type.VIDEO:
                VideoViewHolder videoViewHolder = (VideoViewHolder) holder;
                videoViewHolder.setData(homedata.get(position).<Video>getPageList());
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
