package com.example.zingdemoapi.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.zingdemoapi.datamodel.Artist;
import com.example.zingdemoapi.datamodel.Constant;
import com.example.zingdemoapi.datamodel.Genre;
import com.example.zingdemoapi.datamodel.ProgramInfo;
import com.example.zingdemoapi.datamodel.Serie;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    public static void storeProgramInLocal(ContentResolver contentResolver, ProgramInfo response) {
        Log.d("ZingDemoApi", "store at " + Thread.currentThread().getName());

        Uri uri;
//      INSERT TO PROGRAM TABLE
        ContentValues values = new ContentValues();
        values.put(Constant.ID, response.getId());
        values.put(Constant.NAME, response.getName());
        values.put(Constant.THUMBNAIL, response.getThumbnail());
        values.put(Constant.DESCRIPTION, response.getDescription());
        values.put(Constant.URL_PROGRAM, response.getUrl());
        values.put(Constant.HAS_SUBTITLE, response.getHasSubTitle() ? 1 : 0);
        values.put(Constant.FORMAT, response.getFormat());
        values.put(Constant.LISTEN, response.getListen());
        values.put(Constant.COMMENT, response.getComment());
        values.put(Constant.RATING, response.getRating());
        values.put(Constant.REQUIRE_PREMIUM, response.getRequirePremium() ? 1 : 0);
        values.put(Constant.SUBSCRIPTION, response.getSubscription());
        values.put(Constant.IS_SUBS, response.getIsSubs() ? 1 : 0);
        values.put(Constant.IS_FULL_EPISODE, response.getIsFullEpisode() ? 1 : 0);
        values.put(Constant.RELEASE_DATE, response.getReleaseDate());
        values.put(Constant.SHOW_TIMES, response.getShowTimes());
        values.put(Constant.PG, response.getPg());
        values.put(Constant.DURATION, response.getDuration());
        values.put(Constant.COVER, response.getCover());
        values.put(Constant.BANNER, response.getBanner());
        values.put(Constant.CREATED_DATE, response.getCreatedDate());
        values.put(Constant.MODIFIED_DATE, response.getModifiedDate());

        uri = contentResolver.insert(Constant.CONTENT_URI_PROGRAM, values);
        Log.d("ZingDemoApi", uri != null ? uri.toString() : "uri null");

//      INSERT TO SERIES TABLE
        List<Serie> serieList = response.getSeries();
        if (serieList.size() != 0) {
            for (Serie serie : serieList) {
                ContentValues serieContentValue = new ContentValues();
                serieContentValue.put(Constant.ID_SERIE, serie.getId());
                serieContentValue.put(Constant.NAME_SERIE, serie.getName());
                serieContentValue.put(Constant.THUMBNAIL_SERIE, serie.getThumbnail());
                serieContentValue.put(Constant.ID, response.getId());
                uri = contentResolver.insert(Constant.CONTENT_URI_SERIE, serieContentValue);
                Log.d("ZingDemoApi", uri != null ? uri.toString() : "uri null");

            }
        }

//      INSERT TO ARTIST TABLE
        List<Artist> artistList = response.getArtists();
        if (artistList.size() != 0) {
            for (Artist artist : artistList) {
                ContentValues programArtistValue = new ContentValues();
                programArtistValue.put(Constant.ID, response.getId());
                programArtistValue.put(Constant.ID_ARTIST, artist.getId());
                uri = contentResolver.insert(Constant.CONTENT_URI_PROGRAM_ARTIST, programArtistValue);
                Log.d("ZingDemoApi", uri != null ? uri.toString() : "uri null");

                ContentValues artistValue = new ContentValues();
                artistValue.put(Constant.ID_ARTIST, artist.getId());
                artistValue.put(Constant.NAME_ARTIST, artist.getName());
                artistValue.put(Constant.DOB_ARTIST, artist.getDob());
                artistValue.put(Constant.AVATAR, artist.getAvatar());
                uri = contentResolver.insert(Constant.CONTENT_URI_ARTIST, artistValue);
                Log.d("ZingDemoApi", uri != null ? uri.toString() : "uri null");


            }
        }
//      INSERT TO GENRE TABLE
        List<Genre> genreList = response.getGenres();
        if (genreList.size() != 0) {
            for (Genre genre : genreList) {
                ContentValues programGenreValue = new ContentValues();
                programGenreValue.put(Constant.ID, response.getId());
                programGenreValue.put(Constant.ID_GENRE, genre.getId());
                uri = contentResolver.insert(Constant.CONTENT_URI_PROGRAM_GENRE, programGenreValue);
                Log.d("ZingDemoApi", uri != null ? uri.toString() : "uri null");

                ContentValues genreValue = new ContentValues();
                genreValue.put(Constant.ID_GENRE, genre.getId());
                genreValue.put(Constant.NAME_GENRE, genre.getName());
                uri = contentResolver.insert(Constant.CONTENT_URI_GENRE, genreValue);
                Log.d("ZingDemoApi", uri != null ? uri.toString() : "uri null");

            }
        }
        //Toast.makeText(getBaseContext(), uri != null ? uri.toString() : "uri null", Toast.LENGTH_SHORT).show();
        Log.d("ZingDemoApi", uri != null ? uri.toString() : "uri null");
    }

    public static ProgramInfo fetchProgramInLocal(ContentResolver contentResolver, int mProgramId) {
        Log.d("ZingDemoApi", "fetchProgramInLocal at " + Thread.currentThread().getName());
        String URL = "content://" + Constant.PROVIDER_NAME + "/programs/" + mProgramId;
        Uri programs = Uri.parse(URL);
        ProgramInfo programInfo = null;
        Cursor serieCursor;
        Cursor programArtistCursor;
        Cursor artistCursor;
        Cursor programGenreCursor;
        Cursor genreCursor;
        Cursor cursor;
        cursor = contentResolver.query(programs, null, null, null, "name");

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
            Log.d("ZingDemoApi", "HERE");

            Log.d("ZingDemoApi",
                    cursor.getString(cursor.getColumnIndex(Constant.NAME)) +
                            ", " + cursor.getString(cursor.getColumnIndex(Constant.ID)));

            cursor.close();

//              QUERY SERIES TABLE
            List<Serie> serieList = new ArrayList<>();
            String selectionClause = Constant.ID + "=" + programInfo.getId();
            serieCursor = contentResolver.query(Constant.CONTENT_URI_SERIE, null, selectionClause, null, null);
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
            programArtistCursor = contentResolver.query(Constant.CONTENT_URI_PROGRAM_ARTIST, null, selectionClause, null, null);
            if (programArtistCursor != null && programArtistCursor.moveToFirst()) {
                do {
                    String idUriString =
                            Constant.ROOT_URI
                                    + Constant.PROVIDER_NAME + "/"
                                    + Constant.ARTISTS_TABLE_NAME + "/"
                                    + programArtistCursor.getInt(programArtistCursor.getColumnIndex(Constant.ID_ARTIST));
                    artistCursor = contentResolver
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
            programGenreCursor = contentResolver.query(Constant.CONTENT_URI_PROGRAM_GENRE, null, selectionClause, null, null);
            if (programGenreCursor != null && programGenreCursor.moveToFirst()) {
                do {
                    String idUriString =
                            Constant.ROOT_URI
                                    + Constant.PROVIDER_NAME + "/"
                                    + Constant.GENRES_TABLE_NAME + "/"
                                    + programGenreCursor.getInt(programGenreCursor.getColumnIndex(Constant.ID_GENRE));
                    genreCursor = contentResolver
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
}
