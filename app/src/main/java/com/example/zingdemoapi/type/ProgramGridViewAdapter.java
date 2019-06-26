package com.example.zingdemoapi.type;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.zingdemoapi.R;

public class ProgramGridViewAdapter extends GridViewAdapter {
    public ProgramGridViewAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.child_recycler_row, parent, false);
            myViewHolder = new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        myViewHolder.childGridViewTitle.setText(mBoxObject.getTitle());
        Glide.with(context)
                .load(mBoxObject.getPageList().get(position).getThumbnail())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(myViewHolder.childGridviewImage);
        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context, "ID: " + mBoxObject.getPageList().get(position).getId(), Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;

    }
}
