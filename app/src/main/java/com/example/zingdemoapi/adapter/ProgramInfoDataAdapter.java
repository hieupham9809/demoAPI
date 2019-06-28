package com.example.zingdemoapi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zingdemoapi.R;
import com.example.zingdemoapi.adapter.viewholder.ProgramInfoViewHolder;
import com.example.zingdemoapi.datamodel.ProgramInfo;

public class ProgramInfoDataAdapter extends RecyclerView.Adapter<ProgramInfoViewHolder> {
    private ProgramInfo programInfo;

    private Context context;

    public ProgramInfoDataAdapter(ProgramInfo mProgramInfo, Context mContext){
        programInfo = mProgramInfo;
        context = mContext;
    }
    @NonNull
    @Override
    public ProgramInfoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.program_info_layout, viewGroup, false);
        return new ProgramInfoViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramInfoViewHolder programInfoViewHolder, int i) {
        programInfoViewHolder.setData(programInfo);
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
