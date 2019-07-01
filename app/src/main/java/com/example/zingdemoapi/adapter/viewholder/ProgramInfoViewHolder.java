package com.example.zingdemoapi.adapter.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.zingdemoapi.R;
import com.example.zingdemoapi.adapter.ArtistRecyclerViewAdapter;
import com.example.zingdemoapi.adapter.SeriesRecyclerViewAdapter;
import com.example.zingdemoapi.datamodel.Genre;
import com.example.zingdemoapi.datamodel.ProgramInfo;
import com.example.zingdemoapi.request.GlideRequest;

public class ProgramInfoViewHolder extends RecyclerView.ViewHolder {
    RequestManager requestManager;

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

    public ProgramInfoViewHolder(@NonNull View itemView, Context mContext, RequestManager mRequestManager) {
        super(itemView);
        requestManager = mRequestManager;
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

    public void setData(ProgramInfo mProgramInfo) {
        programInfo = mProgramInfo;
        tvName.setText(programInfo.getName());

        GlideRequest.getInstance().loadImage(requestManager, programInfo.getBanner(), bannerImage);
        tvDescription.setText(programInfo.getDescription());
        tvGenre.setText(String.format("%s    %s", tvGenre.getText(), getGenre(programInfo)));
        tvLink.setText(String.format("%s    %s", tvLink.getText(), programInfo.getUrl()));
        tvFormat.setText(String.format("%s    %s", tvFormat.getText(), programInfo.getFormat()));
        tvListen.setText(String.format("%s    %s", tvListen.getText(), programInfo.getListen().toString()));
        tvComment.setText(String.format("%s    %s", tvComment.getText(), programInfo.getComment().toString()));
        tvRating.setText(String.format("%s    %s", tvRating.getText(), programInfo.getRating().toString()));
        tvReleaseDate.setText(String.format("%s    %s", tvReleaseDate.getText(), programInfo.getReleaseDate()));

        ArtistRecyclerViewAdapter artistRecyclerViewAdapter = new ArtistRecyclerViewAdapter(programInfo.getArtists(), context, requestManager);
        artistRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        artistRecyclerView.setAdapter(artistRecyclerViewAdapter);

        SeriesRecyclerViewAdapter seriesRecyclerViewAdapter = new SeriesRecyclerViewAdapter(programInfo.getSeries(), context, requestManager);
        serieRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        serieRecyclerView.setAdapter(seriesRecyclerViewAdapter);

    }

    private String getGenre(ProgramInfo programInfo) {
        String genre = "";
        for (Genre mGenre : programInfo.getGenres()) {
            genre = String.format("%s%s, ", genre, mGenre.getName());
        }
        return genre;
    }
}
