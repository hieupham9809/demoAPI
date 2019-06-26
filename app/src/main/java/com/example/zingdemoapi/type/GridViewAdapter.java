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

public class GridViewAdapter extends BaseAdapter {
    BoxObject mBoxObject;
    Context context;
    protected LayoutInflater mLayoutInflater;
    public GridViewAdapter(Context mContext){
        this.context = mContext;
    }
    public void setmResources(BoxObject boxObject){
        mBoxObject = boxObject;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mBoxObject.getPageList().size();
    }

    @Override
    public Object getItem(int position) {
        return mBoxObject.getPageList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return mBoxObject.getPageList().get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder;
        if (convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.child_recycler_row, parent, false);
            myViewHolder = new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);
        }else{
            myViewHolder = (MyViewHolder)convertView.getTag();
        }

        myViewHolder.child_gridview_title.setText(mBoxObject.getTitle());
        Glide.with(context)
                .load(mBoxObject.getPageList().get(position).getThumbnail())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(myViewHolder.child_gridview_image);

        return convertView;

    }

    protected class MyViewHolder {
        TextView child_gridview_title;
        ImageView child_gridview_image;
        MyViewHolder(View view){
            child_gridview_title = view.findViewById(R.id.child_recycler_title);
            child_gridview_image = view.findViewById(R.id.child_recycler_image);
        }
    }
}
