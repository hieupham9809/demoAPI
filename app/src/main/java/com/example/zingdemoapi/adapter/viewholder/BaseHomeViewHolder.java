package com.example.zingdemoapi.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.zingdemoapi.datamodel.BaseClass;

import java.util.List;

public abstract class BaseHomeViewHolder<T extends BaseClass> extends RecyclerView.ViewHolder {

    public BaseHomeViewHolder(@NonNull View itemView) {
        super(itemView);

    }
    public abstract void setData(List<T> list);
}
