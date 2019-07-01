package com.example.zingdemoapi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.zingdemoapi.R;
import com.example.zingdemoapi.datamodel.Video;
import com.example.zingdemoapi.request.GlideRequest;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    protected List<Video> list;
    protected Context context;
    protected LayoutInflater mLayoutInflater;
    private RequestManager requestManager;

    public GridViewAdapter(Context mContext, RequestManager mRequestManager) {
        this.context = mContext;
        requestManager = mRequestManager;
    }

    public void setmResources(List<Video> mList) {
        list = mList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridViewItemViewHolder gridViewItemViewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.child_recycler_row, parent, false);
            gridViewItemViewHolder = new GridViewItemViewHolder(convertView);
            convertView.setTag(gridViewItemViewHolder);
        } else {
            gridViewItemViewHolder = (GridViewItemViewHolder) convertView.getTag();
        }

        GlideRequest.getInstance().loadImage(requestManager, list.get(position).getThumbnail(),gridViewItemViewHolder.childGridviewImage);

        return convertView;

    }

    protected class GridViewItemViewHolder {
        TextView childGridViewTitle;
        ImageView childGridviewImage;

        GridViewItemViewHolder(View view) {
            childGridViewTitle = view.findViewById(R.id.child_recycler_title);
            childGridviewImage = view.findViewById(R.id.child_recycler_image);
        }
    }
}
