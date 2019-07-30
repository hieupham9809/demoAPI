package com.example.zingdemoapi.services;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.example.zingdemoapi.R;
import com.example.zingdemoapi.datamodel.Artist;
import com.example.zingdemoapi.datamodel.Constant;
import com.example.zingdemoapi.datamodel.Genre;
import com.example.zingdemoapi.datamodel.ProgramInfo;
import com.example.zingdemoapi.datamodel.Serie;
import com.example.zingdemoapi.request.RestApi;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ProgramRequestService extends Service {
    private final IBinder localBinder = new LocalBinder();
    private final CompositeDisposable subscriptions = new CompositeDisposable();
    public ProgramRequestService(){}
    @Override
    public IBinder onBind(Intent intent) {
        return localBinder;
    }

    public class LocalBinder extends Binder {
        public ProgramRequestService getService(){
            return ProgramRequestService.this;
        }
    }
    public void requestAndInsertProgramInfo(String program_name){

        Disposable disposable =
                RestApi.getInstance().getProgramInfo(processProgramName(program_name))
//                .observeOn(Schedulers.)
                .subscribeOn(Schedulers.io())
                        .subscribe(
                                new Consumer<ProgramInfo>() {
                                    @Override
                                    public void accept(ProgramInfo response) throws Exception {
                                        if (response != null) {
                                            Log.d("ZingDemoApi", "get response, inserting to database");


                                            insertToDatabase(response);
                                        } else {
                                            Log.d("ZingDemoApi", "response null");
                                        }
                                    }
                                }
                                , new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable error) throws Exception {
                                        Log.d(getString(R.string.app_tag), getString(R.string.error_message)  +error.getLocalizedMessage());

                                    }
                                }, new Action() {
                                    @Override
                                    public void run() throws Exception {
                                        Log.d(getString(R.string.app_tag), getString(R.string.programinfo_service_complete_message));

                                    }
                                }
                        );

        subscriptions.add(disposable);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("ZingDemoApi", "service thread " + AndroidSchedulers.mainThread() );
    }
    private void insertToDatabase(ProgramInfo response){
        Uri uri;
//      INSERT TO PROGRAM TABLE

        //Log.d("ZingDemoApi", response.getDescription());
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
    private String processProgramName(String program_name){
        String joinName = TextUtils.join("-",program_name.split(" "));
        String temp = Normalizer.normalize(joinName, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String output = pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll(" ", "-").replaceAll("đ", "d");
        return output.replaceAll(" ","");
    }
}
