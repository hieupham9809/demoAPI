package com.example.zingdemoapi.ui.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.zingdemoapi.R;
import com.example.zingdemoapi.adapter.ArtistRecyclerViewAdapter;
import com.example.zingdemoapi.adapter.SeriesRecyclerViewAdapter;
import com.example.zingdemoapi.datamodel.Artist;
import com.example.zingdemoapi.datamodel.Constant;
import com.example.zingdemoapi.datamodel.Genre;
import com.example.zingdemoapi.datamodel.ProgramContentProvider;
import com.example.zingdemoapi.datamodel.ProgramInfo;
import com.example.zingdemoapi.datamodel.Serie;
import com.example.zingdemoapi.request.GlideRequest;
import com.example.zingdemoapi.request.RestApi;
import com.example.zingdemoapi.ui.view.ArtistCustomView;

import java.util.ArrayList;
import java.util.List;

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
    protected void onResume(){
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
    public void storeProgramInLocal(ProgramInfo response){
        Uri uri;
//      INSERT TO PROGRAM TABLE
        ContentValues values = new ContentValues();
        values.put(Constant.ID, response.getId());
        values.put(Constant.NAME, response.getName());
        values.put(Constant.THUMBNAIL, response.getThumbnail());
        values.put(Constant.DESCRIPTION, response.getDescription());
        values.put(Constant.URL_PROGRAM, response.getUrl());
        values.put(Constant.HAS_SUBTITLE, response.getHasSubTitle()? 1 : 0);
        values.put(Constant.FORMAT, response.getFormat());
        values.put(Constant.LISTEN, response.getListen());
        values.put(Constant.COMMENT, response.getComment());
        values.put(Constant.RATING, response.getRating());
        values.put(Constant.REQUIRE_PREMIUM, response.getRequirePremium()? 1 : 0);
        values.put(Constant.SUBSCRIPTION, response.getSubscription());
        values.put(Constant.IS_SUBS, response.getIsSubs()? 1 : 0);
        values.put(Constant.IS_FULL_EPISODE, response.getIsFullEpisode()? 1 : 0);
        values.put(Constant.RELEASE_DATE, response.getReleaseDate());
        values.put(Constant.SHOW_TIMES, response.getShowTimes());
        values.put(Constant.PG, response.getPg());
        values.put(Constant.DURATION, response.getDuration());
        values.put(Constant.COVER, response.getCover());
        values.put(Constant.BANNER, response.getBanner());
        values.put(Constant.CREATED_DATE, response.getCreatedDate());
        values.put(Constant.MODIFIED_DATE, response.getModifiedDate());

        uri = getContentResolver().insert(Constant.CONTENT_URI_PROGRAM, values);
        Log.d("ZingDemoApi",uri != null ? uri.toString() : "uri null" );

//      INSERT TO SERIES TABLE
        List<Serie> serieList = response.getSeries();
        if (serieList.size() != 0){
            for (Serie serie : serieList){
                ContentValues serieContentValue = new ContentValues();
                serieContentValue.put(Constant.ID_SERIE, serie.getId());
                serieContentValue.put(Constant.NAME_SERIE, serie.getName());
                serieContentValue.put(Constant.THUMBNAIL_SERIE, serie.getThumbnail());
                serieContentValue.put(Constant.ID, response.getId());
                uri = getContentResolver().insert(Constant.CONTENT_URI_SERIE, serieContentValue);
                Log.d("ZingDemoApi",uri != null ? uri.toString() : "uri null" );

            }
        }

//      INSERT TO ARTIST TABLE
        List<Artist> artistList = response.getArtists();
        if (artistList.size() != 0){
            for (Artist artist : artistList){
                ContentValues programArtistValue = new ContentValues();
                programArtistValue.put(Constant.ID, response.getId());
                programArtistValue.put(Constant.ID_ARTIST, artist.getId());
                uri = getContentResolver().insert(Constant.CONTENT_URI_PROGRAM_ARTIST, programArtistValue);
                Log.d("ZingDemoApi",uri != null ? uri.toString() : "uri null" );

                ContentValues artistValue = new ContentValues();
                artistValue.put(Constant.ID_ARTIST, artist.getId());
                artistValue.put(Constant.NAME_ARTIST, artist.getName());
                artistValue.put(Constant.DOB_ARTIST, artist.getDob());
                artistValue.put(Constant.AVATAR, artist.getAvatar());
                uri = getContentResolver().insert(Constant.CONTENT_URI_ARTIST, artistValue);
                Log.d("ZingDemoApi",uri != null ? uri.toString() : "uri null" );


            }
        }
//      INSERT TO GENRE TABLE
        List<Genre> genreList = response.getGenres();
        if (genreList.size() != 0){
            for (Genre genre : genreList){
                ContentValues programGenreValue = new ContentValues();
                programGenreValue.put(Constant.ID, response.getId());
                programGenreValue.put(Constant.ID_GENRE, genre.getId());
                uri = getContentResolver().insert(Constant.CONTENT_URI_PROGRAM_GENRE, programGenreValue);
                Log.d("ZingDemoApi",uri != null ? uri.toString() : "uri null" );

                ContentValues genreValue = new ContentValues();
                genreValue.put(Constant.ID_GENRE, genre.getId());
                genreValue.put(Constant.NAME_GENRE, genre.getName());
                uri = getContentResolver().insert(Constant.CONTENT_URI_GENRE, genreValue);
                Log.d("ZingDemoApi",uri != null ? uri.toString() : "uri null" );

            }
        }
        //Toast.makeText(getBaseContext(), uri != null ? uri.toString() : "uri null", Toast.LENGTH_SHORT).show();
        Log.d("ZingDemoApi",uri != null ? uri.toString() : "uri null" );
    }

    private ProgramInfo fetchProgramInLocal(){
        String URL = "content://" + Constant.PROVIDER_NAME+"/programs/"+mProgramId ;
        Uri programs = Uri.parse(URL);
        Cursor serieCursor;
        Cursor programArtistCursor;
        Cursor artistCursor;
        Cursor programGenreCursor;
        Cursor genreCursor;
        Cursor cursor;
        cursor = getContentResolver().query(programs, null, null, null, "name");
        if (cursor != null && cursor.moveToFirst()) {
//        Log.d("ZingDemoApi", cursor.getInt(cursor.getColumnIndex(Constant.ID)) + " " + cursor.getColumnNames()[3]);

            programInfo = new ProgramInfo();
            programInfo.setId(cursor.getInt(cursor.getColumnIndex(Constant.ID)));
            programInfo.setName(cursor.getString(cursor.getColumnIndex(Constant.NAME)));
            programInfo.setThumbnail(cursor.getString(cursor.getColumnIndex(Constant.THUMBNAIL)));
            programInfo.setDescription(cursor.getString(cursor.getColumnIndex(Constant.DESCRIPTION)));
            programInfo.setUrl(cursor.getString(cursor.getColumnIndex(Constant.URL_PROGRAM)));
            programInfo.setHasSubTitle(cursor.getInt(cursor.getColumnIndex(Constant.HAS_SUBTITLE)) > 0);
            programInfo.setFormat(cursor.getString(cursor.getColumnIndex(Constant.FORMAT)));
            programInfo.setListen(cursor.getInt(cursor.getColumnIndex(Constant.LISTEN)));
            programInfo.setComment(cursor.getInt(cursor.getColumnIndex(Constant.COMMENT)));
            programInfo.setRating(cursor.getDouble(cursor.getColumnIndex(Constant.RATING)));
            programInfo.setRequirePremium(cursor.getInt(cursor.getColumnIndex(Constant.REQUIRE_PREMIUM)) > 0);
            programInfo.setSubscription(cursor.getInt(cursor.getColumnIndex(Constant.SUBSCRIPTION)));
            programInfo.setIsSubs(cursor.getInt(cursor.getColumnIndex(Constant.IS_SUBS)) > 0);
            programInfo.setIsFullEpisode(cursor.getInt(cursor.getColumnIndex(Constant.IS_FULL_EPISODE)) > 0);
            programInfo.setReleaseDate(cursor.getString(cursor.getColumnIndex(Constant.RELEASE_DATE)));
            programInfo.setShowTimes(cursor.getString(cursor.getColumnIndex(Constant.SHOW_TIMES)));
            programInfo.setPg(cursor.getString(cursor.getColumnIndex(Constant.PG)));
            programInfo.setDuration(cursor.getString(cursor.getColumnIndex(Constant.DURATION)));
            programInfo.setCover(cursor.getString(cursor.getColumnIndex(Constant.COVER)));
            programInfo.setBanner(cursor.getString(cursor.getColumnIndex(Constant.BANNER)));
            programInfo.setCreatedDate(cursor.getInt(cursor.getColumnIndex(Constant.CREATED_DATE)));
            programInfo.setModifiedDate(cursor.getInt(cursor.getColumnIndex(Constant.MODIFIED_DATE)));

            Toast.makeText(getBaseContext(),
                    cursor.getString(cursor.getColumnIndex(Constant.NAME)) +
                            ", " + cursor.getString(cursor.getColumnIndex(Constant.ID)), Toast.LENGTH_LONG).show();
            cursor.close();

//              QUERY SERIES TABLE
            List<Serie> serieList = new ArrayList<>();
            String selectionClause = Constant.ID + "=" + programInfo.getId();
            serieCursor = getContentResolver().query(Constant.CONTENT_URI_SERIE, null, selectionClause, null, null);

            if (serieCursor != null && serieCursor.moveToFirst()) {
//                Log.d("ZingDemoApi", serieCursor.getColumnNames()[0] + " " + serieCursor.getColumnNames()[3]);
                do {
                    Serie serie = new Serie();
                    serie.setId(serieCursor.getInt(serieCursor.getColumnIndex(Constant.ID_SERIE)));
                    serie.setName(serieCursor.getString(serieCursor.getColumnIndex(Constant.NAME_SERIE)));
                    serie.setThumbnail(serieCursor.getString(serieCursor.getColumnIndex(Constant.THUMBNAIL_SERIE)));
                    serieList.add(serie);
                } while (serieCursor.moveToNext());
                serieCursor.close();

            }
            programInfo.setSeries(serieList);

//            QUERY ARTIST TABLE
            List<Artist> artistList = new ArrayList<>();
            programArtistCursor = getContentResolver().query(Constant.CONTENT_URI_PROGRAM_ARTIST, null, selectionClause, null, null);
            if (programArtistCursor != null && programArtistCursor.moveToFirst()) {
                do {
                    String idUriString =
                            Constant.ROOT_URI
                                    + Constant.PROVIDER_NAME + "/"
                                    + Constant.ARTISTS_TABLE_NAME + "/"
                                    + programArtistCursor.getInt(programArtistCursor.getColumnIndex(Constant.ID_ARTIST));
                    artistCursor = getContentResolver()
                            .query(Uri.parse(idUriString), null, null, null, null);
                    if (artistCursor != null && artistCursor.moveToFirst()) {
                        Artist artist = new Artist();
                        artist.setId(artistCursor.getInt(artistCursor.getColumnIndex(Constant.ID_ARTIST)));
                        artist.setDob(artistCursor.getString(artistCursor.getColumnIndex(Constant.DOB_ARTIST)));
                        artist.setName(artistCursor.getString(artistCursor.getColumnIndex(Constant.NAME_ARTIST)));
                        artist.setAvatar(artistCursor.getString(artistCursor.getColumnIndex(Constant.AVATAR)));
                        artistList.add(artist);
                        artistCursor.close();
                    }
                } while (programArtistCursor.moveToNext());
                programArtistCursor.close();
            }

            programInfo.setArtists(artistList);
//          QUERY GENRE TABLE
            List<Genre> genreList = new ArrayList<>();
            programGenreCursor = getContentResolver().query(Constant.CONTENT_URI_PROGRAM_GENRE, null, selectionClause, null, null);
            if (programGenreCursor != null && programGenreCursor.moveToFirst()) {
                do {
                    String idUriString =
                            Constant.ROOT_URI
                                    + Constant.PROVIDER_NAME + "/"
                                    + Constant.GENRES_TABLE_NAME + "/"
                                    + programGenreCursor.getInt(programGenreCursor.getColumnIndex(Constant.ID_GENRE));
                    genreCursor = getContentResolver()
                            .query(Uri.parse(idUriString), null, null, null, null);
                    if (genreCursor != null && genreCursor.moveToFirst()) {
                        Genre genre = new Genre();
                        genre.setId(genreCursor.getInt(genreCursor.getColumnIndex(Constant.ID_GENRE)));
                        genre.setName(genreCursor.getString(genreCursor.getColumnIndex(Constant.NAME_GENRE)));
                        genreList.add(genre);
                        genreCursor.close();
                    }
                } while (programGenreCursor.moveToNext());
                programGenreCursor.close();
            }
            programInfo.setGenres(genreList);
            cursor.close();

//        }
        }
        return programInfo;
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
        if (isNetworkAvailable()) {
            subscribe(RestApi.getInstance().getProgramInfo(programId)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                    , new Consumer<ProgramInfo>() {
                        @Override
                        public void accept(ProgramInfo response) throws Exception {
                            setProgramInfoDataForView(response);
                            storeProgramInLocal(response);
                        }
                    }
                    , new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable error) throws Exception {
                            Log.d(getString(R.string.app_tag), getString(R.string.error_message) + error.getLocalizedMessage());

                        }
                    }
                    , new Action() {
                        @Override
                        public void run() throws Exception {
                            Log.d(getString(R.string.app_tag), getString(R.string.programinfo_complete_message));
                        }
                    });
        } else {
            setProgramInfoDataForView(fetchProgramInLocal());
        }
    }


}
