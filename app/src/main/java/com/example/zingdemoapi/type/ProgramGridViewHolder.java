package com.example.zingdemoapi.type;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.zingdemoapi.R;

public class ProgramGridViewHolder extends BaseHomeViewHolder {
    private ExpandableHeightGridView gridView;
    private Context context;
    private GridViewAdapter gridViewAdapter;
    ProgramGridViewHolder(@NonNull View itemView, Context mContext) {
        super(itemView);
        context = mContext;
        gridView = itemView.findViewById(R.id.grid_view);
    }
    @Override
    public void setData(BoxObject boxObject){
        if (gridViewAdapter == null){
            gridViewAdapter = new ProgramGridViewAdapter(context);

        }
        gridViewAdapter.setmResources(boxObject);
        gridView.setAdapter(gridViewAdapter);
        gridView.setExpanded(true);
    }
}
