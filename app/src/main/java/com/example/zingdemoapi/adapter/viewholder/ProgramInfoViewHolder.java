package com.example.zingdemoapi.adapter.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.zingdemoapi.R;
import com.example.zingdemoapi.adapter.ArtistRecyclerViewAdapter;
import com.example.zingdemoapi.adapter.SeriesRecyclerViewAdapter;
import com.example.zingdemoapi.datamodel.Genre;
import com.example.zingdemoapi.datamodel.ProgramInfo;
import com.example.zingdemoapi.ui.view.ExpandableHeightGridView;

public class ProgramInfoViewHolder extends RecyclerView.ViewHolder {
    private ProgramInfo programInfo;
    private Context context;

    private TextView tvName;
    private ImageView bannerImage;
    private TextView tvDescription;
    private TextView tvGenre;
    private TextView tvLink;
    private TextView tvFormat;
    private TextView tvListen;
    private TextView tvComment;
    private TextView tvRating;
    private TextView tvReleaseDate;

    private RecyclerView artistRecyclerView;
    private RecyclerView serieRecyclerView;
    private ArtistRecyclerViewAdapter artistRecyclerViewAdapter;
    private SeriesRecyclerViewAdapter seriesRecyclerViewAdapter;

    public ProgramInfoViewHolder(@NonNull View itemView, Context mContext) {
        super(itemView);
        context = mContext;
        tvName = itemView.findViewById(R.id.tv_name);
        bannerImage = itemView.findViewById(R.id.banner_image);
        tvDescription = itemView.findViewById(R.id.tv_description);
        tvGenre = itemView.findViewById(R.id.tv_genre);
        tvLink = itemView.findViewById(R.id.tv_link);
        tvFormat = itemView.findViewById(R.id.tv_format);
        tvListen = itemView.findViewById(R.id.tv_listen);
        tvComment = itemView.findViewById(R.id.tv_comment);
        tvRating = itemView.findViewById(R.id.tv_rating);
        tvReleaseDate = itemView.findViewById(R.id.tv_release_date);

        artistRecyclerView = itemView.findViewById(R.id.artist_recycler_view);
        serieRecyclerView = itemView.findViewById(R.id.series_recycler_view);
    }
    public void setData(ProgramInfo mProgramInfo){
        programInfo = mProgramInfo;
        tvName.setText(programInfo.getName());
        Glide.with(context)
                .load(programInfo.getBanner())
                //.override(bannerImage.getLayoutParams().width, bannerImage.getLayoutParams().width * 9 / 16)
                //.override(150, 150)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(bannerImage);
        tvDescription.setText(programInfo.getDescription());
        tvGenre.setText(tvGenre.getText() +"    "+ getGenre(programInfo));
        tvLink.setText(tvLink.getText() +"    "+ programInfo.getUrl());
        tvFormat.setText(tvFormat.getText() +"    "+ programInfo.getFormat());
        tvListen.setText(tvListen.getText() +"    "+ programInfo.getListen().toString());
        tvComment.setText(tvComment.getText() +"    "+ programInfo.getComment().toString());
        tvRating.setText(tvRating.getText() +"    "+ programInfo.getRating().toString());
        tvReleaseDate.setText(tvReleaseDate.getText()+"    " + programInfo.getReleaseDate());

        artistRecyclerViewAdapter = new ArtistRecyclerViewAdapter(programInfo.getArtists(), context);
        artistRecyclerView.setLayoutManager(new LinearLayoutManager(context ,LinearLayoutManager.HORIZONTAL, false));
        artistRecyclerView.setAdapter(artistRecyclerViewAdapter);

        seriesRecyclerViewAdapter = new SeriesRecyclerViewAdapter(programInfo.getSeries(), context);
        serieRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        serieRecyclerView.setAdapter(seriesRecyclerViewAdapter);

    }
    private String getGenre(ProgramInfo programInfo){
        String genre = "";
        for (Genre mGenre : programInfo.getGenres()){
            genre = genre + mGenre.getName() + ", ";
        }
        return genre;
    }
}
