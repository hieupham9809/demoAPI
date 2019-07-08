package com.example.zingdemoapi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.zingdemoapi.R;
import com.example.zingdemoapi.datamodel.Program;
import com.example.zingdemoapi.request.GlideRequest;
import com.example.zingdemoapi.ui.activity.ProgramInfoActivity;
import com.example.zingdemoapi.ui.activity.ProgramInfoActivitys;

import java.util.List;

public class ProgramGridViewAdapter extends BaseAdapter {
    private List<Program> list;
    private String title;
    private Context context;
    private LayoutInflater mLayoutInflater;
    private RequestManager requestManager;
    public ProgramGridViewAdapter(Context mContext, RequestManager mRequestManager) {
        this.context = mContext;
        requestManager = mRequestManager;
    }
    public void setmResources(List<Program> mList, String mTitle ) {
        list = mList;
        title = mTitle;
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
        titleImageViewHolder.childGridViewTitle.setText(list.get(position).getTitle());
        GlideRequest.getInstance().loadImage(requestManager, list.get(position).getThumbnail(), titleImageViewHolder.childGridviewImage);

        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProgramInfoActivity.class);
//                intent.putExtra("TITLE", title);
                intent.putExtra("IDPROGRAM", list.get(position).getId());
                //Toast.makeText(context, "ID: " + list.get(position).getId(), Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
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
