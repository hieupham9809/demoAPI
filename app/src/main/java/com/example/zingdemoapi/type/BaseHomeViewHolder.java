package com.example.zingdemoapi.type;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

public abstract class BaseHomeViewHolder<T extends BaseClass> extends RecyclerView.ViewHolder {

    public BaseHomeViewHolder(@NonNull View itemView) {
        super(itemView);

    }
    public abstract void setData(List<T> list);
}
