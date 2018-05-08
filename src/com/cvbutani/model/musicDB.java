package com.cvbutani.model;

import java.sql.Connection;

/**
 * Author: cvbutani
 * Date: 07/05/18
 */

public class musicDB {

    public static final String DB_NAME = "music.db";
    public static final String DB_STRING = "jdbc:sqlite:/home/chirag/IdeaProjects/MusicDB/" + DB_NAME;

    public static final String TABLE_ALBUMS = "albums";
    public static final String COLUMN_ALBUMS_ID = "_id";
    public static final String COLUMN_ALBUMS_NAME = "name";
    public static final String COLUMN_ALBUMS_ARTIST = "artist";
    public static final int INDEX_ALBUM_ID = 1;
    public static final int INDEX_ALBUM_NAME = 2;
    public static final int INDEX_ALBUM_ARTIST = 3;

    public static final String TABLE_ARTISTS = "artists";
    public static final String COLUMN_ARTISTS_ID = "_id";
    public static final String COLUMN_ARTISTS_NAME = "name";
    public static final int INDEX_ARTIST_ID = 1;
    public static final int INDEX_ARTIST_NAME = 2;

    public static final String TABLE_SONGS = "songs";
    public static final String COLUMN_SONGS_ID = "_id";
    public static final String COLUMN_SONGS_TRACK = "track";
    public static final String COLUMN_SONGS_TITLE = "title";
    public static final String COLUMN_SONGS_ALBUM = "album";
    public static final int INDEX_SONG_ID = 1;
    public static final int INDEX_SONG_TRACK = 2;
    public static final int INDEX_SONG_TITLE = 3;
    public static final int INDEX_SONG_ALBUM = 4;

    public Connection connection;

    public static final int ORDER_BY_NONE = 1;
    public static final int ORDER_BY_ASC = 2;
    public static final int ORDER_BY_DESC = 3;

    public static final String QUARY_ARTIST_START = "SELECT * FROM " + TABLE_ARTISTS;
    public static final String QUARY_ARTIST_SORT = " ORDER BY " + COLUMN_ARTISTS_NAME + " COLLATE NOCASE ";

    public static final String QUERY_ALBUMS_BY_ARTIST_START =
            "SELECT " + TABLE_ALBUMS + '.' + COLUMN_ALBUMS_NAME + " FROM " + TABLE_ALBUMS +
                    " INNER JOIN " + TABLE_ARTISTS + " ON " + TABLE_ALBUMS + '.' + COLUMN_ALBUMS_ARTIST + " = " +
                    TABLE_ARTISTS + '.' + COLUMN_ARTISTS_ID + " WHERE " + TABLE_ARTISTS + '.' +
                    COLUMN_ARTISTS_NAME + " = \"";

    public static final String QUERY_ALBUMS_BY_ARTIST_SORT =
            " ORDER BY " + TABLE_ALBUMS + '.' + COLUMN_ALBUMS_NAME + " COLLATE NOCASE ";

    public static final String QUERY_ARTISTS_FOR_SONG_START =
            "SELECT " + TABLE_ARTISTS + '.' + COLUMN_ARTISTS_NAME + ", " + TABLE_ALBUMS + '.' +
                    COLUMN_ALBUMS_NAME + "," + TABLE_SONGS + '.' + COLUMN_SONGS_TRACK + " FROM " + TABLE_SONGS +
                    " INNER JOIN " + TABLE_ALBUMS + " ON " + TABLE_SONGS + '.' + COLUMN_SONGS_ALBUM + " = " +
                    TABLE_ALBUMS + '.' + COLUMN_ALBUMS_ID + " INNER JOIN " + TABLE_ARTISTS + " ON " + TABLE_ALBUMS + '.' +
                    COLUMN_ALBUMS_ARTIST + "=" + TABLE_ARTISTS + '.' + COLUMN_ARTISTS_ID + " WHERE " +
                    TABLE_SONGS + '.' + COLUMN_SONGS_TITLE + " = \"";


    public static final String QUERY_ARTISTS_FOR_SONG_SORT =
            " ORDER BY " + TABLE_ARTISTS + '.' + COLUMN_ARTISTS_NAME +
                    ", " + TABLE_ALBUMS + '.' + COLUMN_ALBUMS_NAME + " COLLATE NOCASE ";


}
