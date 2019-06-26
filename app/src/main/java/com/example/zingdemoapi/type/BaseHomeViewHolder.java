package com.example.zingdemoapi.type;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseHomeViewHolder extends RecyclerView.ViewHolder {

    public BaseHomeViewHolder(@NonNull View itemView) {
        super(itemView);

    }
    public abstract void setData(BoxObject boxObject);
}
