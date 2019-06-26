package com.example.zingdemoapi.type;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.zingdemoapi.R;

public class VideoViewHolder extends BaseHomeViewHolder {

    protected RecyclerView recyclerView;
    protected ExpandableHeightGridView gridView;
    protected Context context;
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
    GridViewAdapter gridViewAdapter;
    public void setData(BoxObject boxObject){
        if (gridViewAdapter == null){
            gridViewAdapter = new GridViewAdapter(context);

        }
        gridViewAdapter.setmResources(boxObject);
        gridView.setAdapter(gridViewAdapter);
        gridView.setExpanded(true);
    }
    public ExpandableHeightGridView getGridView() {
        return gridView;
    }

    public void setGridView(ExpandableHeightGridView gridView) {
        this.gridView = gridView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
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
