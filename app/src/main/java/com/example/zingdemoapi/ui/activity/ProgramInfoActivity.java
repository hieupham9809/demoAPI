package com.example.zingdemoapi.ui.activity;

import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.zingdemoapi.R;
import com.example.zingdemoapi.adapter.ArtistRecyclerViewAdapter;
import com.example.zingdemoapi.adapter.DataCommentRecyclerViewAdapter;
import com.example.zingdemoapi.adapter.EndlessParentScrollListener;
import com.example.zingdemoapi.adapter.EndlessRecyclerViewScrollListener;
import com.example.zingdemoapi.adapter.ProgramInfoDataAdapter;
import com.example.zingdemoapi.adapter.SeriesRecyclerViewAdapter;
import com.example.zingdemoapi.adapter.viewholder.ProgramInfoViewHolder;
import com.example.zingdemoapi.datamodel.DataComment;
import com.example.zingdemoapi.datamodel.Genre;
import com.example.zingdemoapi.datamodel.ProgramInfo;
import com.example.zingdemoapi.request.GlideRequest;
import com.example.zingdemoapi.request.RestApi;

import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ProgramInfoActivity extends BaseActivity {
    private ProgramInfo programInfo;
    private RequestManager requestManager;

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

    private EndlessRecyclerViewScrollListener scrollListener;
    //private DataCommentRecyclerViewAdapter dataCommentRecyclerViewAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int id;

    private Button btnLoad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_info);

        //actionBar.setDisplayShowHomeEnabled(true);
        requestManager = Glide.with(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("IDPROGRAM", 0);
        initRecyclerView();

        loadJSON(Integer.toString(id));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        actionBar.setTitle(intent.getStringExtra("TITLE"));
        //loadJSONfirst(Integer.toString(id), "0");
        //loadComment();
        //loadJSON(Integer.toString(id));
    }

    private void initRecyclerView() {
        tvName = findViewById(R.id.tv_name);
        bannerImage = findViewById(R.id.banner_image);
        tvDescription = findViewById(R.id.tv_description);
        tvGenre = findViewById(R.id.tv_genre);
        tvLink = findViewById(R.id.tv_link);
        tvFormat = findViewById(R.id.tv_format);
        tvListen = findViewById(R.id.tv_listen);
        tvComment = findViewById(R.id.tv_comment);
        tvRating = findViewById(R.id.tv_rating);
        tvReleaseDate = findViewById(R.id.tv_release_date);
        btnLoad = findViewById(R.id.btn_load_comment);
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadComment();
            }
        });

        artistRecyclerView = findViewById(R.id.artist_recycler_view);
        serieRecyclerView = findViewById(R.id.series_recycler_view);

        //dataCommentRecyclerViewAdapter = new DataCommentRecyclerViewAdapter(requestManager);

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

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadComment();
            }
        });

        ArtistRecyclerViewAdapter artistRecyclerViewAdapter = new ArtistRecyclerViewAdapter(programInfo.getArtists(), this, requestManager);
        artistRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        artistRecyclerView.setAdapter(artistRecyclerViewAdapter);

        SeriesRecyclerViewAdapter seriesRecyclerViewAdapter = new SeriesRecyclerViewAdapter(programInfo.getSeries(), this, requestManager);
        serieRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        serieRecyclerView.setAdapter(seriesRecyclerViewAdapter);


        //loadJSONfirst(programInfo.getId().toString(), "0");


    }
    private String getGenre(ProgramInfo programInfo) {
        String genre = "";
        for (Genre mGenre : programInfo.getGenres()) {
            genre = String.format("%s%s, ", genre, mGenre.getName());
        }
        return genre;
    }
    private void loadComment(){
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("IDPROGRAM", programInfo.getId());
        intent.putExtra("TITLE", programInfo.getName());
        startActivity(intent);
//        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                loadNextDataFromApi(page);
//            }
//        };
//        commentRecyclerView.addOnScrollListener(scrollListener);


        Log.d("ZingDemoApi", "load more");
    }
//    public void loadNextDataFromApi(int offset) {
//        // Send an API request to retrieve appropriate paginated data
//        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
//        //  --> Deserialize and construct new model objects from the API response
//        loadJSONComment(Integer.toString(id), Integer.toString(offset));
//
//
//    }
    private void loadJSON(String id) {

        subscribe(RestApi.getInstance().getProgramInfo(id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                , new Consumer<ProgramInfo>() {
                    @Override
                    public void accept(ProgramInfo response) throws Exception {
                        setData(response);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable error) throws Exception {
                        Log.d("ZingDemoApi", "Error" + error.getLocalizedMessage());

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d("ZingDemoApi", "Completed ProgramInfoActivity API");
                    }
                });

    }

//    private void loadJSONfirst(String id, String page) {
//
//        ProgramInfoActivity.this.subscribe(RestApi.getInstance().getDataComment(id, page)
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeOn(Schedulers.io())
//                , new Consumer<DataComment>() {
//                    @Override
//                    public void accept(DataComment response) throws Exception {
//                        dataCommentRecyclerViewAdapter.getCommentList().addAll(response.getCommentList());
//
//                        linearLayoutManager = new LinearLayoutManager(getBaseContext());
//                        commentRecyclerView.setAdapter(dataCommentRecyclerViewAdapter);
//
//                        commentRecyclerView.setLayoutManager(linearLayoutManager);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable error) throws Exception {
//                        Log.d("ZingDemoApi", "Error: " + error);
//                    }
//                }, new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        Log.d("ZingDemoApi", "Completed load comment API");
//                    }
//                });
//
//
//    }
//    private void loadJSONComment(String id, String page) {
//
//        ProgramInfoActivity.this.subscribe(RestApi.getInstance().getDataComment(id, page)
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeOn(Schedulers.io())
//                , new Consumer<DataComment>() {
//                    @Override
//                    public void accept(DataComment response) throws Exception {
//
//                        ProgramInfoActivity.this.handleResponse(response);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable error) throws Exception {
//                        Log.d("ZingDemoApi", "Error: " + error);
//                    }
//                }, new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        Log.d("ZingDemoApi", "Completed load comment API");
//                    }
//                });
//
//
//    }
//    private void handleResponse(DataComment mDataComment){
//        //  --> Append the new data objects to the existing set of items inside the array of items
//        dataCommentRecyclerViewAdapter.getCommentList().addAll(mDataComment.getCommentList());
//        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
//        dataCommentRecyclerViewAdapter.notifyItemRangeInserted(dataCommentRecyclerViewAdapter.getItemCount(), mDataComment.getCommentList().size());
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
