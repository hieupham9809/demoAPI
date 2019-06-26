package com.example.zingdemoapi.type;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.zingdemoapi.R;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    protected List<Video> list;
    protected Context context;
    protected LayoutInflater mLayoutInflater;

    public GridViewAdapter(Context mContext) {
        this.context = mContext;
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
        MyViewHolder myViewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.child_recycler_row, parent, false);
            myViewHolder = new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }

        //myViewHolder.childGridViewTitle.setText(mBoxObject.getTitle());
        Glide.with(context)
                .load(list.get(position).getThumbnail())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(myViewHolder.childGridviewImage);

        return convertView;

    }

    protected class MyViewHolder {
        TextView childGridViewTitle;
        ImageView childGridviewImage;

        MyViewHolder(View view) {
            childGridViewTitle = view.findViewById(R.id.child_recycler_title);
            childGridviewImage = view.findViewById(R.id.child_recycler_image);
        }
    }
}
