package com.example.zingdemoapi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.zingdemoapi.R;
import com.example.zingdemoapi.datamodel.Artist;

import java.util.List;

public class ArtistRecyclerViewAdapter extends RecyclerView.Adapter<ArtistRecyclerViewAdapter.ArtistViewHolder> {
    private List<Artist> artistList;

    private Context context;

    public ArtistRecyclerViewAdapter(List<Artist> mArtistList, Context mContext){
        artistList = mArtistList;
        context = mContext;
    }
    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.artist_recycler_item, viewGroup, false );
        return new ArtistViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder artistViewHolder, int i) {
        artistViewHolder.artistDOB.setText(artistList.get(i).getDob());
        artistViewHolder.artistName.setText(artistList.get(i).getName());
        Glide.with(context)
                .load(artistList.get(i).getAvatar())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(artistViewHolder.artistImage);
    }

    @Override
    public int getItemCount() {
        if (artistList != null){
            return artistList.size();
        } else {
            return 0;
        }
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder {
        private ImageView artistImage;
        private TextView artistName;
        private TextView artistDOB;

        //private Context context;
        ArtistViewHolder (View view){
            super(view);
            //context = mContext;
            artistImage = view.findViewById(R.id.iv_artist_image);
            artistName = view.findViewById(R.id.tv_artist_name);
            artistDOB  = view.findViewById(R.id.tv_artist_DOB);
        }
    }
}
