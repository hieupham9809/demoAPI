package com.example.zingdemoapi.type;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.zingdemoapi.R;

import java.util.List;

public class ProgramGridViewAdapter extends BaseAdapter {
    private List<Program> list;
    private Context context;
    private LayoutInflater mLayoutInflater;
    public ProgramGridViewAdapter(Context mContext) {
        this.context = mContext;
    }
    public void setmResources(List<Program> mList) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        TitleImageViewHolder titleImageViewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.child_recycler_row, parent, false);
            titleImageViewHolder = new TitleImageViewHolder(convertView);
            convertView.setTag(titleImageViewHolder);
        } else {
            titleImageViewHolder = (TitleImageViewHolder) convertView.getTag();
        }
        //myViewHolder.childGridViewTitle.setText(mBoxObject.getTitle());
        Glide.with(context)
                .load(list.get(position).getThumbnail())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(titleImageViewHolder.childGridviewImage);
        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context, "ID: " + list.get(position).getId(), Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;

    }

    protected class TitleImageViewHolder {
        TextView childGridViewTitle;
        ImageView childGridviewImage;

        TitleImageViewHolder(View view) {
            childGridViewTitle = view.findViewById(R.id.child_recycler_title);
            childGridviewImage = view.findViewById(R.id.child_recycler_image);
        }
    }
}
