package com.example.zingdemoapi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zingdemoapi.datamodel.Artist;

import java.util.List;

public class ArtistGridviewAdapter extends BaseAdapter {
    private List<Artist> artistsList;
    private Context context;
    private LayoutInflater mLayoutInflater;
    public ArtistGridviewAdapter(List<Artist> artistList, Context mContext){
        context = mContext;
        artistsList = artistList;
        mLayoutInflater = LayoutInflater.from (mContext);
    }

    @Override
    public int getCount() {
        if (artistsList != null){
            return artistsList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return artistsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return artistsList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    private class ArtistViewHolder {
        ImageView artistImage;
        TextView artistName;
        TextView artistDOB;

        ArtistViewHolder (View view){

        }
    }
}
