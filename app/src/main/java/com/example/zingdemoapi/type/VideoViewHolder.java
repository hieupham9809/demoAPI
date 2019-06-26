package com.example.zingdemoapi.type;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.zingdemoapi.R;

import java.util.List;

public class VideoViewHolder extends BaseHomeViewHolder<Video> {

    protected RecyclerView recyclerView;
    protected ExpandableHeightGridView gridView;
    protected Context context;
    protected GridViewAdapter gridViewAdapter;

    @Override
    public void setData(List<Video> list) {
        if (gridViewAdapter == null) {
            gridViewAdapter = new GridViewAdapter(context);

        }
        gridViewAdapter.setmResources(list);
        gridView.setAdapter(gridViewAdapter);
        gridView.setExpanded(true);
    }

    public VideoViewHolder(@NonNull View itemView, Context mContext) {
        super(itemView);
//        recyclerView = itemView.findViewById(R.id.child_recycler_view);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext(), 2);
//        recyclerView.setLayoutManager(gridLayoutManager);
        this.context = mContext;
        gridView = itemView.findViewById(R.id.grid_view);

    }

}
