package com.example.zingdemoapi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.zingdemoapi.R;
import com.example.zingdemoapi.datamodel.Video;
import com.example.zingdemoapi.request.GlideRequest;
import com.example.zingdemoapi.ui.view.ProgramItemCustomView;

import java.util.List;

public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.TitleImageViewHolder> {
    private List<Video> list;
    private String title;
    private Context context;
    private LayoutInflater mLayoutInflater;
    private RequestManager requestManager;
    public VideoRecyclerViewAdapter(Context mContext, RequestManager mRequestManager) {
        this.context = mContext;
        requestManager = mRequestManager;
    }
    public void setmResources(List<Video> mList ) {
        list = mList;

        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public VideoRecyclerViewAdapter.TitleImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.program_recycler_item, viewGroup, false);
        return new VideoRecyclerViewAdapter.TitleImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoRecyclerViewAdapter.TitleImageViewHolder viewHolder, final int i) {
//        viewHolder.childGridViewTitle.setText(list.get(i).getTitle());
//        GlideRequest.getInstance().loadImage(requestManager, list.get(i).getThumbnail(), viewHolder.childGridviewImage, R.drawable.default_thumbnail);
        viewHolder.programItemCustomView.setImageAndTitle(list.get(i).getThumbnail(),list.get(i).getTitle());

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
//            childGridViewTitle = view.findViewById(R.id.child_recycler_title);
//            childGridviewImage = view.findViewById(R.id.child_recycler_image);
            programItemCustomView = view.findViewById(R.id.program_item_custom_view);
        }
    }
}
