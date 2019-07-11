package com.example.zingdemoapi.adapter.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.zingdemoapi.R;
import com.example.zingdemoapi.adapter.GridViewAdapter;
import com.example.zingdemoapi.datamodel.Video;
import com.example.zingdemoapi.ui.view.ExpandableHeightGridView;

import java.util.List;

public class VideoViewHolder extends BaseHomeViewHolder<Video> {

    protected RecyclerView recyclerView;

    protected ExpandableHeightGridView gridView;
    //private GridView gridView;
    protected Context context;
    protected GridViewAdapter gridViewAdapter;
    private TextView tvTitle;
    @Override
    public void setData(List<Video> list, String mTitle) {
        tvTitle.setAllCaps(true);
        tvTitle.setText(mTitle);
        if (gridViewAdapter == null) {
            gridViewAdapter = new GridViewAdapter(context, requestManager);

        }
        gridViewAdapter.setmResources(list);
        gridView.setAdapter(gridViewAdapter);
        gridView.setExpanded(true);
    }

    public VideoViewHolder(@NonNull View itemView, Context mContext, RequestManager mRequestManager) {
        super(itemView,mRequestManager);

        this.context = mContext;
        gridView = (ExpandableHeightGridView) itemView.findViewById(R.id.grid_view);
        tvTitle = itemView.findViewById(R.id.tv_type_title_video);
    }

}
