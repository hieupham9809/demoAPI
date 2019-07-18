package com.example.zingdemoapi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.zingdemoapi.R;
import com.example.zingdemoapi.datamodel.Constant;
import com.example.zingdemoapi.datamodel.Program;
import com.example.zingdemoapi.request.GlideRequest;
import com.example.zingdemoapi.ui.activity.ProgramInfoActivity;
import com.example.zingdemoapi.ui.view.ProgramItemCustomView;

import java.util.List;

public class ProgramRecyclerViewAdapter extends RecyclerView.Adapter<ProgramRecyclerViewAdapter.TitleImageViewHolder> {
    private List<Program> list;
    private String title;
    private Context context;
    private LayoutInflater mLayoutInflater;
    private RequestManager requestManager;
    public ProgramRecyclerViewAdapter(Context mContext, RequestManager mRequestManager) {
        this.context = mContext;
        requestManager = mRequestManager;
    }
    public void setmResources(List<Program> mList, String mTitle ) {
        list = mList;
        title = mTitle;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TitleImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.program_recycler_item, viewGroup, false);
        return new TitleImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TitleImageViewHolder viewHolder, final int i) {
        viewHolder.programItemCustomView.setImageAndTitle(list.get(i).getThumbnail(), list.get(i).getTitle());
        viewHolder.programItemCustomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProgramInfoActivity.class);
                intent.putExtra(Constant.TITLE, title);
                intent.putExtra(Constant.PROGRAMID, list.get(i).getId());
                //Toast.makeText(context, "ID: " + list.get(position).getId(), Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        });
        //        GlideRequest.getInstance().loadImage(requestManager, list.get(i).getThumbnail(), viewHolder.childGridviewImage, R.drawable.default_thumbnail);
//        viewHolder.childGridviewImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, ProgramInfoActivity.class);
//                intent.putExtra(Constant.TITLE, title);
//                intent.putExtra(Constant.PROGRAMID, list.get(i).getId());
//                //Toast.makeText(context, "ID: " + list.get(position).getId(), Toast.LENGTH_SHORT).show();
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }
    public class TitleImageViewHolder extends RecyclerView.ViewHolder{
//        TextView childGridViewTitle;
//        ImageView childGridviewImage;
        ProgramItemCustomView programItemCustomView;
        TitleImageViewHolder(View view) {
            super(view);
            programItemCustomView = view.findViewById(R.id.program_item_custom_view);
//            childGridViewTitle = view.findViewById(R.id.child_recycler_title);
//            childGridviewImage = view.findViewById(R.id.child_recycler_image);
        }
    }
}
