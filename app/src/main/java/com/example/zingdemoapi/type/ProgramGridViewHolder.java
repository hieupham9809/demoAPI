package com.example.zingdemoapi.type;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.zingdemoapi.R;

import java.util.List;

public class ProgramGridViewHolder extends BaseHomeViewHolder<Program> {
    private ExpandableHeightGridView gridView;
    private Context context;
    private ProgramGridViewAdapter programGridViewAdapter;
    ProgramGridViewHolder(@NonNull View itemView, Context mContext) {
        super(itemView);
        context = mContext;
        gridView = itemView.findViewById(R.id.grid_view);
    }
    @Override
    public void setData(List<Program> list){
        if (programGridViewAdapter == null){
            programGridViewAdapter = new ProgramGridViewAdapter(context);

        }
        programGridViewAdapter.setmResources(list);
        gridView.setAdapter(programGridViewAdapter);
        gridView.setExpanded(true);
    }
}
