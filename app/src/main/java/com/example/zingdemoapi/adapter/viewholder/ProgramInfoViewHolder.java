package com.example.zingdemoapi.adapter.viewholder;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.zingdemoapi.R;
import com.example.zingdemoapi.adapter.ArtistRecyclerViewAdapter;
import com.example.zingdemoapi.adapter.DataCommentRecyclerViewAdapter;
import com.example.zingdemoapi.adapter.EndlessParentScrollListener;
import com.example.zingdemoapi.adapter.EndlessRecyclerViewScrollListener;
import com.example.zingdemoapi.adapter.SeriesRecyclerViewAdapter;
import com.example.zingdemoapi.datamodel.Comment;
import com.example.zingdemoapi.datamodel.DataComment;
import com.example.zingdemoapi.datamodel.Genre;
import com.example.zingdemoapi.datamodel.ProgramInfo;
import com.example.zingdemoapi.request.GlideRequest;
import com.example.zingdemoapi.request.RestApi;
import com.example.zingdemoapi.ui.activity.BaseActivity;
import com.example.zingdemoapi.ui.activity.ProgramInfoActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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

    private Button btnLoadComment;

    private RecyclerView artistRecyclerView;
    private RecyclerView serieRecyclerView;
    private RecyclerView commentRecyclerView;
    private NestedScrollView nestedScrollView;

    private EndlessRecyclerViewScrollListener scrollListener;
    private DataCommentRecyclerViewAdapter dataCommentRecyclerViewAdapter;

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

        btnLoadComment = itemView.findViewById(R.id.btn_load_comment);

        artistRecyclerView = itemView.findViewById(R.id.artist_recycler_view);
        serieRecyclerView = itemView.findViewById(R.id.series_recycler_view);
        commentRecyclerView = itemView.findViewById(R.id.comment_recycler_view);

        nestedScrollView = (NestedScrollView) itemView.findViewById(R.id.scrollView);
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

        btnLoadComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadComment();
            }
        });

        ArtistRecyclerViewAdapter artistRecyclerViewAdapter = new ArtistRecyclerViewAdapter(programInfo.getArtists(), context, requestManager);
        artistRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        artistRecyclerView.setAdapter(artistRecyclerViewAdapter);

        SeriesRecyclerViewAdapter seriesRecyclerViewAdapter = new SeriesRecyclerViewAdapter(programInfo.getSeries(), context, requestManager);
        serieRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        serieRecyclerView.setAdapter(seriesRecyclerViewAdapter);

        dataCommentRecyclerViewAdapter = new DataCommentRecyclerViewAdapter(requestManager);

        loadJSONfirst(programInfo.getId().toString(), "0");


    }

    private String getGenre(ProgramInfo programInfo) {
        String genre = "";
        for (Genre mGenre : programInfo.getGenres()) {
            genre = String.format("%s%s, ", genre, mGenre.getName());
        }
        return genre;
    }
    private void loadComment(){
        // load api
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        //commentRecyclerView.setLayoutManager(linearLayoutManager);
        commentRecyclerView.setAdapter(dataCommentRecyclerViewAdapter);

        commentRecyclerView.setLayoutManager(linearLayoutManager);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextDataFromApi(page);
            }
        };
        commentRecyclerView.addOnScrollListener(scrollListener);
        //commentRecyclerView.setNestedScrollingEnabled(false);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//        commentRecyclerView.setLayoutManager(linearLayoutManager);
//        nestedScrollView.setOnScrollChangeListener(new EndlessParentScrollListener(linearLayoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount) {
//                loadNextDataFromApi(page);
//            }
//        });
        Log.d("ZingDemoApi", "load more");
    }
    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        loadJSON(programInfo.getId().toString(), Integer.toString(offset));
        //  --> Append the new data objects to the existing set of items inside the array of items

        //Log.d("ZingDemoApi", "adapter list size: "+ dataCommentRecyclerViewAdapter.getCommentList().size());
        //Log.d("ZingDemoApi", "before dataComment list size: "+ dataComment.getCommentList().size());
//        ArrayList<Comment> commentList = new ArrayList<>(dataComment.getCommentList());
//        Log.d("ZingDemoApi", "before dataComment list size: "+ dataComment.getCommentList().size());
//
//        dataCommentRecyclerViewAdapter.getCommentList().addAll(commentList);
//
//        Log.d("ZingDemoApi", "after dataComment list size: "+ commentList.size());

        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
    }
    private void loadJSONfirst(String id, String page) {

        ((BaseActivity)context).subscribe(RestApi.getInstance().getDataComment(id, page)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                , new Consumer<DataComment>() {
                    @Override
                    public void accept(DataComment response) throws Exception {


                        dataCommentRecyclerViewAdapter.getCommentList().addAll(response.getCommentList());



//        dataCommentRecyclerViewAdapter = new DataCommentRecyclerViewAdapter(requestManager);
                        //commentRecyclerView.setAdapter(dataCommentRecyclerViewAdapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable error) throws Exception {
                        Log.d("ZingDemoApi", "Error: " + error);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d("ZingDemoApi", "Completed load comment API");
                    }
                });


    }
    private void loadJSON(String id, String page) {

        ((BaseActivity)context).subscribe(RestApi.getInstance().getDataComment(id, page)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                , new Consumer<DataComment>() {
                    @Override
                    public void accept(DataComment response) throws Exception {

                        ProgramInfoViewHolder.this.handleResponse(response);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable error) throws Exception {
                        Log.d("ZingDemoApi", "Error: " + error);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d("ZingDemoApi", "Completed load comment API");
                    }
                });


    }
    private void handleResponse(DataComment mDataComment){
        //Log.d("ZingDemoApi", "response list size: "+mDataComment.getCommentList().size());
        //dataComment = mDataComment;
        dataCommentRecyclerViewAdapter.getCommentList().addAll(mDataComment.getCommentList());
        dataCommentRecyclerViewAdapter.notifyItemRangeInserted(dataCommentRecyclerViewAdapter.getItemCount(), mDataComment.getCommentList().size());

    }
}
