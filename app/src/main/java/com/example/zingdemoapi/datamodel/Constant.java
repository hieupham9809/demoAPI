package com.example.zingdemoapi.datamodel;

import android.net.Uri;

public class Constant {

    public static final String PROGRAMID = "IDPROGRAM";
    public static final String TITLE = "TITLE";
    public static final String BASE = "http://dev.api.tv.zing.vn/";

    public static final String PROGRAM_INFO_CONTENT_FORMAT = "%s    %s";

    public static final int INITIAL_PAGE = 1;
    public static final int END_COMMENT_VIEW_TYPE = -1;

    public static final float NAME_ARTIST_TEXT_SIZE = 40f;

    public static final int NAME_PROGRAM_TEXT_PADDING_HEIGHT = 10;

    public static final int NUM_COLUMN_ARTIST = 3;
    public static final int NUM_COLUMN_PROGRAM_RECYCLER_VIEW = 2;

    public static final int MAX_LENGTH_FOR_ARTIST_NAME = 18;
    public static final int MAX_LENGTH_FOR_PADDING_TEXT = 12;

    public static final String PROVIDER_NAME = "com.example.zingdemoapi.database.ProgramContentProvider";

    //
    public static final String ROOT_URI = "content://";

    // CONSTANT FOR PROGRAM PROVIDER
    public static final String DATABASE_NAME = "ProgramContentProvider";
    public static final String PROGRAMS_TABLE_NAME = "programs";
    public static final String ARTISTS_TABLE_NAME = "artists";
    public static final String GENRES_TABLE_NAME = "genres";
    public static final String SERIES_TABLE_NAME = "series";
    public static final String PROGRAMS_ARTISTS_TABLE_NAME = "programs_artists";
    public static final String PROGRAMS_GENRES_TABLE_NAME = "programs_genres";

    public static final String SHARE_PROGRAMS = "share_programs";
//    URI
    public static final Uri CONTENT_URI_PROGRAM = Uri.parse("content://" + PROVIDER_NAME + "/" + PROGRAMS_TABLE_NAME);
    public static final Uri CONTENT_URI_ARTIST = Uri.parse("content://" + PROVIDER_NAME + "/" + ARTISTS_TABLE_NAME);
    public static final Uri CONTENT_URI_GENRE = Uri.parse("content://" + PROVIDER_NAME + "/" + GENRES_TABLE_NAME);
    public static final Uri CONTENT_URI_SERIE = Uri.parse("content://" + PROVIDER_NAME + "/" + SERIES_TABLE_NAME);
    public static final Uri CONTENT_URI_PROGRAM_ARTIST = Uri.parse("content://" + PROVIDER_NAME + "/" + PROGRAMS_ARTISTS_TABLE_NAME);
    public static final Uri CONTENT_URI_PROGRAM_GENRE = Uri.parse("content://" + PROVIDER_NAME + "/" + PROGRAMS_GENRES_TABLE_NAME);

    public static final Uri CONTENT_URI_SHARE_PROGRAMS = Uri.parse("content://" + PROVIDER_NAME + "/" + SHARE_PROGRAMS );
    public static final int DATABASE_VERSION = 1;
//    PROGRAM COLUMN
    public static final String _ID = "_id";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String THUMBNAIL = "thumbnail";
    public static final String DESCRIPTION = "description";
    public static final String URL_PROGRAM = "url";
    public static final String HAS_SUBTITLE = "hasSubTitle";
    public static final String FORMAT = "format";
    public static final String LISTEN = "listen";
    public static final String COMMENT = "comment";
    public static final String RATING = "rating";
    public static final String REQUIRE_PREMIUM = "requirePremium";
    public static final String SUBSCRIPTION = "subscription";
    public static final String IS_SUBS = "isSubs";
    public static final String IS_FULL_EPISODE = "isFullEpisode";
    public static final String RELEASE_DATE = "releaseDate";
    public static final String SHOW_TIMES = "showTimes";
    public static final String PG = "pg";
    public static final String DURATION = "duration";
    public static final String COVER = "cover";
    public static final String BANNER = "banner";
    public static final String CREATED_DATE = "createdDate";
    public static final String MODIFIED_DATE = "modifiedDate";

    public static final int PROGRAMS = 1;
    public static final int PROGRAM_ID = 2;
    public static final int ARTISTS = 3;
    public static final int ARTIST_ID = 4;
    public static final int GENRES = 5;
    public static final int GENRE_ID = 6;
    public static final int SERIES = 7;
    public static final int PROGRAMS_ARTISTS = 8;
    public static final int PROGRAMS_GENRES = 9;
    public static final int SHARE_PROGRAMS_CODE = 10;



//  ARTIST COLUMN
    public static final String ID_ARTIST = "id_artist";
    public static final String NAME_ARTIST = "name_artist";
    public static final String DOB_ARTIST = "dob_artist";
    public static final String AVATAR = "avatar";
//  GENRE COLUMN
    public static final String ID_GENRE = "id_genre";
    public static final String NAME_GENRE = "name_genre";
//  SERIE COLUMN
    public static final String ID_SERIE = "id_serie";
    public static final String THUMBNAIL_SERIE = "thumbnail_serie";
    public static final String NAME_SERIE = "name_serie";
//  PROGRAM_ARTIST COLUMN
    public static final String ID_PROGRAM_ARTIST = "id_program_artist";


    private Constant(){

    }
}
