package com.cvbutani.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: cvbutani
 * Date: 06/05/18
 */

public class DataSource {

    public static final String DB_NAME = "music.db";
    public static final String DB_STRING = "jdbc:sqlite:/home/chirag/IdeaProjects/MusicDB/" + DB_NAME;

    public static final String TABLE_ALBUMS = "albums";
    public static final String COLUMN_ALBUMS_ID = "_id";
    public static final String COLUMN_ALBUMS_NAME = "name";
    public static final String COLUMN_ALBUMS_ARTIST = "artist";

    public static final String TABLE_ARTISTS = "artists";
    public static final String COLUMN_ARTISTS_ID = "_id";
    public static final String COLUMN_ARTISTS_NAME = "name";

    public static final String TABLE_SONGS = "songs";
    public static final String COLUMN_SONGS_TRACK = "track";
    public static final String COLUMN_SONGS_TITLE = "title";
    public static final String COLUMN_SONGS_ALBUM = "album";

    private Connection connection;

    public boolean open() {
        try {
            connection = DriverManager.getConnection(DB_STRING);
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close database: " + e.getMessage());
        }
    }

    public List<Artist> queryArtist() {

//        Statement statement = null;
//        ResultSet result = null;

        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery("SELECT * FROM " + TABLE_ARTISTS)) {

//            statement = connection.createStatement();
//            result = statement.executeQuery("SELECT * FROM " + TABLE_ARTISTS);

            List<Artist> artistsList = new ArrayList<>();
            while (result.next()) {
                Artist artist = new Artist();
                artist.setId(result.getInt(COLUMN_ARTISTS_ID));
                artist.setName(result.getString(COLUMN_ARTISTS_NAME));
                artistsList.add(artist);
            }
            return artistsList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
//        finally {
//            try {
//                if (result != null) {
//                    result.close();
//                }
//            } catch (SQLException e) {
//                System.out.println("Not able to close Result" + e.getMessage());
//            }
//            try {
//                if (statement != null) {
//                    statement.close();
//                }
//            } catch (SQLException e) {
//                System.out.println("Not able to close Statement "+ e.getMessage());
//            }
//        }
    }

}



