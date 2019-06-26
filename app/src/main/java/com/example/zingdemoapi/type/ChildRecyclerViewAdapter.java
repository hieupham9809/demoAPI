package com.example.zingdemoapi.type;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.zingdemoapi.R;

public class ChildRecyclerViewAdapter extends RecyclerView.Adapter<ChildRecyclerViewAdapter.MyViewHolder> {
    BoxObject mboxObject;
    public ChildRecyclerViewAdapter(BoxObject boxObject){
        mboxObject = boxObject;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.child_recycler_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        viewHolder.child_recycler_title.setText(mboxObject.getTitle());
        //Log.d("LOAD", mboxObject.getPageList().get(i))
        Glide.with(viewHolder.child_recycler_image.getContext())
                .load(mboxObject.getPageList().get(i).getThumbnail())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(viewHolder.child_recycler_image);

    }

    @Override
    public int getItemCount() {
        return mboxObject.getPageList().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView child_recycler_title;
        ImageView child_recycler_image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            child_recycler_title = itemView.findViewById(R.id.child_recycler_title);
            child_recycler_image = itemView.findViewById(R.id.child_recycler_image);
        }
    }
}
