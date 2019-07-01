package com.example.zingdemoapi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.zingdemoapi.R;
import com.example.zingdemoapi.datamodel.Serie;
import com.example.zingdemoapi.request.GlideRequest;

import java.util.List;

public class SeriesRecyclerViewAdapter extends RecyclerView.Adapter<SeriesRecyclerViewAdapter.SeriesItemViewHolder> {
    List<Serie> serieList;
    Context context;
    RequestManager requestManager;
    public SeriesRecyclerViewAdapter(List<Serie> list, Context mContext, RequestManager mRequestManager){
        serieList = list;
        context = mContext;
        requestManager = mRequestManager;
    }
    @NonNull
    @Override
    public SeriesItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.series_recycler_item, viewGroup, false);
        return new SeriesItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesItemViewHolder seriesItemViewHolder, int i) {

        seriesItemViewHolder.tvSerieName.setText(serieList.get(i).getName());

        GlideRequest.getInstance().loadImage(requestManager, serieList.get(i).getThumbnail(), seriesItemViewHolder.ivSerieName);
    }

    @Override
    public int getItemCount() {
        if (serieList != null){
            return serieList.size();
        } else {
            return 0;
        }
    }

    public class SeriesItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvSerieName;
        ImageView ivSerieName;

        public SeriesItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSerieName = itemView.findViewById(R.id.tv_series_name);
            ivSerieName = itemView.findViewById(R.id.iv_series_image);
        }
    }
}
