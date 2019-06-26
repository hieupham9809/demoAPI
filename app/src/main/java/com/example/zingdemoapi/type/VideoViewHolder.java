package com.example.zingdemoapi.type;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.zingdemoapi.R;

public class VideoViewHolder extends BaseHomeViewHolder {

    protected RecyclerView recyclerView;
    protected ExpandableHeightGridView gridView;
    protected Context context;
    protected GridViewAdapter gridViewAdapter;

    public void setData(BoxObject boxObject) {
        if (gridViewAdapter == null) {
            gridViewAdapter = new GridViewAdapter(context);

        }
        gridViewAdapter.setmResources(boxObject);
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
