package com.example.zingdemoapi.type;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

public class ProgramGridViewHolder extends VideoViewHolder {
    ProgramGridViewHolder(@NonNull View itemView, Context mContext) {
        super(itemView, mContext);
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
