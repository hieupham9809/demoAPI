package com.example.zingdemoapi.datamodel;

import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.example.zingdemoapi.services.ProgramRequestService;
import com.example.zingdemoapi.ui.activity.ProgramInfoActivity;

import java.util.HashMap;

public class ProgramContentProvider extends ContentProvider {
    static final UriMatcher uriMatcher;
    private Context context;
    private ProgramRequestService programRequestService;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Constant.PROVIDER_NAME, Constant.PROGRAMS_TABLE_NAME, Constant.PROGRAMS);
        uriMatcher.addURI(Constant.PROVIDER_NAME, Constant.ARTISTS_TABLE_NAME, Constant.ARTISTS);
        uriMatcher.addURI(Constant.PROVIDER_NAME, Constant.GENRES_TABLE_NAME, Constant.GENRES);
        uriMatcher.addURI(Constant.PROVIDER_NAME, Constant.SERIES_TABLE_NAME, Constant.SERIES);
        uriMatcher.addURI(Constant.PROVIDER_NAME, Constant.PROGRAMS_ARTISTS_TABLE_NAME, Constant.PROGRAMS_ARTISTS);
        uriMatcher.addURI(Constant.PROVIDER_NAME, Constant.PROGRAMS_GENRES_TABLE_NAME, Constant.PROGRAMS_GENRES);
        uriMatcher.addURI(Constant.PROVIDER_NAME, Constant.PROGRAMS_TABLE_NAME+ "/#", Constant.PROGRAM_ID);
        uriMatcher.addURI(Constant.PROVIDER_NAME, Constant.ARTISTS_TABLE_NAME + "/#", Constant.ARTIST_ID);
        uriMatcher.addURI(Constant.PROVIDER_NAME, Constant.GENRES_TABLE_NAME + "/#", Constant.GENRE_ID);
        uriMatcher.addURI(Constant.PROVIDER_NAME, Constant.SHARE_PROGRAMS, Constant.SHARE_PROGRAMS_CODE);
    }

    /*
    *
    *
    * */

    private SQLiteDatabase database;
    private static HashMap<String, String> PROGRAMS_PROJECTION_MAP;
    private static final String CREATE_DB_PROGRAM_TABLE =
            " CREATE TABLE " + Constant.PROGRAMS_TABLE_NAME +
                    //" (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " (" + Constant.ID + " INTEGER PRIMARY KEY UNIQUE, " +
                    //" " + Constant.ID + " INTEGER, "+
                    " " + Constant.NAME + " TEXT NOT NULL, "+
                    " " + Constant.THUMBNAIL + " TEXT , "+
                    " " + Constant.DESCRIPTION + " TEXT , "+
                    " " + Constant.URL_PROGRAM + " TEXT , "+
                    " " + Constant.HAS_SUBTITLE + " INTEGER , "+
                    " " + Constant.FORMAT + " TEXT , "+
                    " " + Constant.LISTEN + " INTEGER , "+
                    " " + Constant.COMMENT + " INTEGER , "+
                    " " + Constant.RATING + " REAL , "+
                    " " + Constant.REQUIRE_PREMIUM + " INTEGER , "+
                    " " + Constant.SUBSCRIPTION + " INTEGER , "+
                    " " + Constant.IS_SUBS + " INTEGER , "+
                    " " + Constant.IS_FULL_EPISODE + " INTEGER , "+
                    " " + Constant.RELEASE_DATE + " TEXT , "+
                    " " + Constant.SHOW_TIMES + " TEXT , "+
                    " " + Constant.PG + " TEXT , "+
                    " " + Constant.DURATION + " TEXT , "+
                    " " + Constant.COVER + " TEXT , "+
                    " " + Constant.BANNER + " TEXT , "+
                    " " + Constant.CREATED_DATE + " INTEGER , "+
                    " " + Constant.MODIFIED_DATE + " INTEGER  "+
                    ");";
    private static final String CREATE_DB_ARTIST_TABLE =
            " CREATE TABLE "+ Constant.ARTISTS_TABLE_NAME +
            " (" + Constant.ID_ARTIST + " INTEGER PRIMARY KEY UNIQUE, " +
                    Constant.NAME_ARTIST + " TEXT NOT NULL, " +
                    Constant.DOB_ARTIST + " TEXT, " +
                    Constant.AVATAR + " TEXT " +
                    ");"
            ;
    private static final String CREATE_DB_GENRE_TABLE =
            " CREATE TABLE "+ Constant.GENRES_TABLE_NAME +
                    " (" + Constant.ID_GENRE + " INTEGER PRIMARY KEY UNIQUE, " +
                    Constant.NAME_GENRE + " TEXT " +
                    ");"
            ;
    private static final String CREATE_DB_SERIE_TABLE =
            " CREATE TABLE "+ Constant.SERIES_TABLE_NAME +
                    " (" + Constant.ID_SERIE + " INTEGER PRIMARY KEY UNIQUE, " +
                    Constant.NAME_SERIE + " TEXT, "+
                    Constant.ID + " INTEGER, "+
                    Constant.THUMBNAIL_SERIE + " TEXT " +
                    ");"
            ;
    private static final String CREATE_DB_PROGRAM_ARTIST_TABLE =
            " CREATE TABLE "+ Constant.PROGRAMS_ARTISTS_TABLE_NAME +
                    " ("  +
                    Constant.ID + " INTEGER NOT NULL , "+
                    Constant.ID_ARTIST + " INTEGER NOT NULL , " +
                    "PRIMARY KEY(" + Constant.ID + "," + Constant.ID_ARTIST + ")" +
                    ");"
            ;
    private static final String CREATE_DB_PROGRAM_GENRE_TABLE =
            " CREATE TABLE "+ Constant.PROGRAMS_GENRES_TABLE_NAME +
                    " ("  +
                    Constant.ID + " INTEGER NOT NULL , "+
                    Constant.ID_GENRE + " INTEGER NOT NULL , " +
                    "PRIMARY KEY(" + Constant.ID + "," + Constant.ID_GENRE + ")" +
                    ");"
            ;
    private static class ProgramDatabaseHelper extends SQLiteOpenHelper{

        public ProgramDatabaseHelper(Context context) {
            super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_PROGRAM_TABLE);
            db.execSQL(CREATE_DB_ARTIST_TABLE);
            db.execSQL(CREATE_DB_GENRE_TABLE);
            db.execSQL(CREATE_DB_SERIE_TABLE);
            db.execSQL(CREATE_DB_PROGRAM_ARTIST_TABLE);
            db.execSQL(CREATE_DB_PROGRAM_GENRE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + Constant.PROGRAMS_TABLE_NAME);
            onCreate(db);
        }
    }


    @Override
    public boolean onCreate() {
        context = getContext();
        ProgramDatabaseHelper programDatabaseHelper = new ProgramDatabaseHelper(context);
        Intent intent = new Intent(context, ProgramRequestService.class);
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        database = programDatabaseHelper.getWritableDatabase();
        return (database != null);
    }

    
    @Override
    public Cursor query( Uri uri,  String[] projection,  String selection,  String[] selectionArgs,  String sortOrder) {
//        database.execSQL("DROP TABLE IF EXISTS " + Constant.PROGRAMS_TABLE_NAME);
//        database.execSQL(CREATE_DB_PROGRAM_TABLE);
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        switch (uriMatcher.match(uri)){
            case Constant.PROGRAMS:
                qb.setTables(Constant.PROGRAMS_TABLE_NAME);
                qb.setProjectionMap(PROGRAMS_PROJECTION_MAP);

                break;
            case Constant.ARTISTS:
                qb.setTables(Constant.ARTISTS_TABLE_NAME);
                break;
            case Constant.GENRES:
                qb.setTables(Constant.GENRES_TABLE_NAME);
                break;
            case Constant.SERIES:
                qb.setTables(Constant.SERIES_TABLE_NAME);
                break;
            case Constant.PROGRAMS_ARTISTS:
                qb.setTables(Constant.PROGRAMS_ARTISTS_TABLE_NAME);
                break;
            case Constant.PROGRAMS_GENRES:
                qb.setTables(Constant.PROGRAMS_GENRES_TABLE_NAME);
                break;
            case Constant.ARTIST_ID:
                qb.setTables(Constant.ARTISTS_TABLE_NAME);
                qb.appendWhere(Constant.ID_ARTIST + "=" + uri.getPathSegments().get(1));
                break;
            case Constant.PROGRAM_ID:
                qb.setTables(Constant.PROGRAMS_TABLE_NAME);
                qb.appendWhere(Constant.ID + "=" + uri.getPathSegments().get(1));
                break;
            case Constant.GENRE_ID:
                qb.setTables(Constant.GENRES_TABLE_NAME);

                qb.appendWhere(Constant.ID_GENRE + "=" + uri.getPathSegments().get(1));
                break;
            case Constant.SHARE_PROGRAMS_CODE:
                qb.setTables(Constant.PROGRAMS_TABLE_NAME);
                Cursor cursor = qb.query(database, projection, Constant.NAME + " LIKE ? ", new String[]{"%" + selectionArgs[0] + "%"}, null, null, null);
                if (cursor == null){
                    Log.d("ZingDemoApi", "error in null cursor");
                } else if (cursor.getCount() < 1){
                    // has not in database, get request service and then insert to database
                    Log.d("ZingDemoApi", "data has not existed in database, get request api!");

                    programRequestService.requestAndInsertProgramInfo(selectionArgs[0]);
                    Log.d("ZingDemoApi", "query database again and return");
                    return qb.query(database, projection, Constant.NAME + " LIKE ? ", new String[]{"%" + selectionArgs[0] + "%"}, null, null, null);
                } else {
                    Log.d("ZingDemoApi", "data existed in database, show on screen");

                    return cursor;
                }

            default:
                Log.d("ZingDemoApi", "default: "+ uri.toString());

        }

        if (sortOrder == null || sortOrder.equals("")){
            sortOrder = Constant.NAME;
        }

        Cursor cursor = qb.query(database, projection, selection, selectionArgs, null, null, null);
        cursor.setNotificationUri(getContext().getContentResolver(), uri );

        return cursor;
    }

    
    @Override
    public String getType( Uri uri) {
        switch(uriMatcher.match(uri)){
            case Constant.PROGRAMS:
                return "vnd.android.cursor.dir/vnd.example.programs";
            case Constant.PROGRAM_ID:
                return "vnd.android.cursor.item/vnd.example.programs";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    
    @Override
    public Uri insert( Uri uri,  ContentValues values) {
        Uri _uri = null;

        switch(uriMatcher.match(uri)){
            case Constant.PROGRAMS:
                long programRowID = database.insertWithOnConflict(Constant.PROGRAMS_TABLE_NAME,"", values, SQLiteDatabase.CONFLICT_REPLACE);
                if (programRowID > 0){
                    _uri = ContentUris.withAppendedId(Constant.CONTENT_URI_PROGRAM, programRowID);
                    if (getContext() != null) {
                        getContext().getContentResolver().notifyChange(_uri, null);
                    }
                }
                break;
            case Constant.ARTISTS:
                long artistRowID = database.insertWithOnConflict(Constant.ARTISTS_TABLE_NAME, "", values, SQLiteDatabase.CONFLICT_REPLACE);
                if (artistRowID > 0){
                    _uri = ContentUris.withAppendedId(Constant.CONTENT_URI_ARTIST, artistRowID);
                    if (getContext() != null){
                        getContext().getContentResolver().notifyChange(_uri, null);
                    }
                }
                break;
            case Constant.GENRES:
                long genreRowID = database.insertWithOnConflict(Constant.GENRES_TABLE_NAME, "", values, SQLiteDatabase.CONFLICT_REPLACE);
                if (genreRowID > 0){
                    _uri = ContentUris.withAppendedId(Constant.CONTENT_URI_GENRE, genreRowID);
                    if (getContext() != null){
                        getContext().getContentResolver().notifyChange(_uri, null);
                    }
                }
                break;
            case Constant.SERIES:
                long serieRowID = database.insertWithOnConflict(Constant.SERIES_TABLE_NAME, "", values, SQLiteDatabase.CONFLICT_REPLACE);
                if (serieRowID > 0){
                    _uri = ContentUris.withAppendedId(Constant.CONTENT_URI_SERIE, serieRowID);
                    if (getContext() != null){
                        getContext().getContentResolver().notifyChange(_uri, null);
                    }
                }
                break;
            case Constant.PROGRAMS_ARTISTS:
                long programArtistRowID = database.insertWithOnConflict(Constant.PROGRAMS_ARTISTS_TABLE_NAME, "", values, SQLiteDatabase.CONFLICT_REPLACE);
                if (programArtistRowID > 0){
                    _uri = ContentUris.withAppendedId(Constant.CONTENT_URI_PROGRAM_ARTIST, programArtistRowID);
                    if (getContext() != null){
                        getContext().getContentResolver().notifyChange(_uri, null);
                    }
                }
                break;
            case Constant.PROGRAMS_GENRES:
                long programGenreRowID = database.insertWithOnConflict(Constant.PROGRAMS_GENRES_TABLE_NAME, "", values, SQLiteDatabase.CONFLICT_REPLACE);
                if (programGenreRowID > 0){
                    _uri = ContentUris.withAppendedId(Constant.CONTENT_URI_PROGRAM_GENRE, programGenreRowID);
                    if (getContext() != null){
                        getContext().getContentResolver().notifyChange(_uri, null);
                    }
                }
                break;
            default:
                throw new SQLException("Failed to add a record into " + uri);

        }

        Log.d("ZingDemoApi", "inserted");


        return _uri;

    }

    @Override
    public int delete( Uri uri,  String selection,  String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case Constant.PROGRAMS:
                count = database.delete(Constant.PROGRAMS_TABLE_NAME, selection, selectionArgs);
                break;
            case Constant.PROGRAM_ID:
                String id = uri.getPathSegments().get(1);
                count = database.delete(Constant.PROGRAMS_TABLE_NAME
                        , Constant._ID + "=" + id +
                                (!TextUtils.isEmpty(selection)? "AND (" + selection + ')':""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update( Uri uri,  ContentValues values,  String selection,  String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case Constant.PROGRAMS:
                count = database.update(Constant.PROGRAMS_TABLE_NAME, values, selection, selectionArgs);
                break;
            case Constant.PROGRAM_ID:
                String id = uri.getPathSegments().get(1);
                count = database.update(Constant.PROGRAMS_TABLE_NAME
                        , values
                        , Constant._ID + "=" + id +
                                (!TextUtils.isEmpty(selection)? "AND (" + selection + ')':""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ProgramRequestService.LocalBinder binder = (ProgramRequestService.LocalBinder) service;
            programRequestService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
