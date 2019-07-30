package com.example.zingdemoapi.ui.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
import com.example.zingdemoapi.adapter.SeriesRecyclerViewAdapter;
import com.example.zingdemoapi.database.DatabaseHelper;
import com.example.zingdemoapi.datamodel.Artist;
import com.example.zingdemoapi.datamodel.Constant;
import com.example.zingdemoapi.datamodel.Genre;
import com.example.zingdemoapi.datamodel.ProgramInfo;
import com.example.zingdemoapi.datamodel.Serie;
import com.example.zingdemoapi.request.GlideRequest;
import com.example.zingdemoapi.request.RestApi;
import com.example.zingdemoapi.ui.view.ArtistCustomView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
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

    private ArtistCustomView artistCustomView;
    private RecyclerView serieRecyclerView;

    private int mProgramId;

    private Button btnLoadComment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_info);

        requestManager = Glide.with(this);
        Intent intent = getIntent();
        if (intent.hasExtra(Constant.PROGRAMID)) {
            mProgramId = intent.getIntExtra(Constant.PROGRAMID, 0);
        }

        initProgramInfoView();

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(intent.getStringExtra(Constant.TITLE));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProgramInfo(mProgramId);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    private void initProgramInfoView() {
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
        btnLoadComment = findViewById(R.id.btn_load_comment);

//        artistRecyclerView = findViewById(R.id.artist_recycler_view);
        serieRecyclerView = findViewById(R.id.series_recycler_view);
        artistCustomView = findViewById(R.id.artist_custom_view);
        //dataCommentRecyclerViewAdapter = new DataCommentRecyclerViewAdapter(requestManager);

    }

    public void setProgramInfoDataForView(ProgramInfo mProgramInfo) {
        programInfo = mProgramInfo;
        if (programInfo != null) {
            tvName.setText(programInfo.getName());

            GlideRequest.getInstance().loadImage(requestManager, programInfo.getBanner(), bannerImage, R.drawable.default_thumbnail);
            tvDescription.setText(programInfo.getDescription());
            tvGenre.setText(String.format(Constant.PROGRAM_INFO_CONTENT_FORMAT, tvGenre.getText(), getGenre(programInfo)));
            tvLink.setText(String.format(Constant.PROGRAM_INFO_CONTENT_FORMAT, tvLink.getText(), programInfo.getUrl()));
            tvFormat.setText(String.format(Constant.PROGRAM_INFO_CONTENT_FORMAT, tvFormat.getText(), programInfo.getFormat()));
            tvListen.setText(String.format(Constant.PROGRAM_INFO_CONTENT_FORMAT, tvListen.getText(), programInfo.getListen().toString()));
            tvComment.setText(String.format(Constant.PROGRAM_INFO_CONTENT_FORMAT, tvComment.getText(), programInfo.getComment().toString()));
            tvRating.setText(String.format(Constant.PROGRAM_INFO_CONTENT_FORMAT, tvRating.getText(), programInfo.getRating().toString()));
            tvReleaseDate.setText(String.format(Constant.PROGRAM_INFO_CONTENT_FORMAT, tvReleaseDate.getText(), programInfo.getReleaseDate()));

            btnLoadComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadComment();
                }
            });

//        ArtistRecyclerViewAdapter artistRecyclerViewAdapter = new ArtistRecyclerViewAdapter(programInfo.getArtists(), this, requestManager);
//        artistRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        artistRecyclerView.setAdapter(artistRecyclerViewAdapter);
            //ArtistCustomView artistCustomView = new ArtistCustomView(this, programInfo.getArtists());
            if (programInfo.getArtists() != null) {
                artistCustomView.setArtistList(programInfo.getArtists());
            }

            //Canvas canvas = new Canvas();
            //artistCustomView.draw(canvas);
            //artistCustomView.invalidate();
            if (programInfo.getSeries() != null) {
                SeriesRecyclerViewAdapter seriesRecyclerViewAdapter = new SeriesRecyclerViewAdapter(programInfo.getSeries(), this, requestManager);
                serieRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                serieRecyclerView.setAdapter(seriesRecyclerViewAdapter);
            }
        }
//        storeProgramInLocal();
//        fetchProgramInLocal();
        //artistCustomView.mInvalidate();
    }





    private String getGenre(ProgramInfo programInfo) {
        String genre = "";
        if (programInfo.getGenres() != null) {
            for (Genre mGenre : programInfo.getGenres()) {
                genre = String.format("%s%s, ", genre, mGenre.getName());
            }
        }
        return genre;
    }

    private void loadComment() {
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra(Constant.PROGRAMID, programInfo.getId());
        intent.putExtra(Constant.TITLE, programInfo.getName());
        startActivity(intent);

    }

    private void loadProgramInfo(int programId) {

        subscribe(RestApi.getInstance().getProgramInfo(programId)
                        .subscribeOn(Schedulers.io())
                        .flatMap(new Function<ProgramInfo, ObservableSource<ProgramInfo>>() {
                            @Override
                            public ObservableSource<ProgramInfo> apply(ProgramInfo programInfo) throws Exception {
                                DatabaseHelper.storeProgramInLocal(getContentResolver(),programInfo);
                                return Observable.just(programInfo);

                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())

                , new Consumer<ProgramInfo>() {
                    @Override
                    public void accept(ProgramInfo response) throws Exception {
                        Log.d("ZingDemoApi", "set Info " + Thread.currentThread().getName());
                        setProgramInfoDataForView(response);
                        //storeProgramInLocal(response);
                    }
                }
                , new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable error) throws Exception {
                        Log.d(getString(R.string.app_tag), getString(R.string.error_message) + error.getLocalizedMessage() + " " + Thread.currentThread());
                        Observable.just(1)
                                .subscribeOn(Schedulers.newThread())
                                .flatMap(new Function<Integer, ObservableSource<ProgramInfo>>() {
                                    @Override
                                    public ObservableSource<ProgramInfo> apply(Integer integer) throws Exception {
                                        ProgramInfo programInfo = DatabaseHelper.fetchProgramInLocal(getContentResolver(), mProgramId);
                                        Log.d("ZingDemoApi", "fetched " + Thread.currentThread().getName());

                                        return Observable.just(programInfo);
                                    }
                                })
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        new Consumer<ProgramInfo>() {
                                            @Override
                                            public void accept(ProgramInfo response) throws Exception {
                                                setProgramInfoDataForView(response);

                                            }
                                        }
                                        , new Consumer<Throwable>() {
                                            @Override
                                            public void accept(Throwable throwable) throws Exception {
                                                Log.d("ZingDemoApi", "some error with fetchLocal line 433");
                                            }
                                        }
                                        , new Action() {
                                            @Override
                                            public void run() throws Exception {
                                                Log.d("ZingDemoApi", "Fetch local and set view complete");
                                            }
                                        }
                                );
                        //setProgramInfoDataForView(fetchProgramInLocal());

                    }
                }
                , new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(getString(R.string.app_tag), getString(R.string.programinfo_complete_message));
                    }
                });


    }


}
