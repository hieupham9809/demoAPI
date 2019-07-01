package com.example.zingdemoapi.adapter.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.bumptech.glide.RequestManager;
import com.example.zingdemoapi.R;
import com.example.zingdemoapi.adapter.ProgramGridViewAdapter;
import com.example.zingdemoapi.datamodel.Program;
import com.example.zingdemoapi.ui.view.ExpandableHeightGridView;

import java.util.List;

public class ProgramGridViewHolder extends BaseHomeViewHolder<Program> {
    private ExpandableHeightGridView gridView;
    private Context context;
    private ProgramGridViewAdapter programGridViewAdapter;
    public ProgramGridViewHolder(@NonNull View itemView, Context mContext, RequestManager mRequestManager) {
        super(itemView, mRequestManager);
        context = mContext;
        gridView = itemView.findViewById(R.id.grid_view);
    }
    @Override
    public void setData(List<Program> list){
        if (programGridViewAdapter == null){
            programGridViewAdapter = new ProgramGridViewAdapter(context, requestManager);

        }
        programGridViewAdapter.setmResources(list);
        gridView.setAdapter(programGridViewAdapter);
        gridView.setExpanded(true);

    }
}
