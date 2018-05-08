package com.cvbutani.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: cvbutani
 * Date: 06/05/18
 */

public class DataSource extends musicDB {

    public void open() {
        try {
            connection = DriverManager.getConnection(DB_STRING);
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
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

    public List<Artist> queryArtist(int sortOrder) {

        String sb = setInOrder(QUARY_ARTIST_START, null, QUARY_ARTIST_SORT, sortOrder);
        System.out.println("SQL statement: " + sb);

        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sb)) {

            List<Artist> artistsList = new ArrayList<>();

            while (result.next()) {
                Artist artist = new Artist();
                artist.setId(result.getInt(INDEX_ARTIST_ID));
                artist.setName(result.getString(INDEX_ARTIST_NAME));
                artistsList.add(artist);
            }
            return artistsList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> queryAlbumsForArtists(String artistName, int sortOrder) {

        String sb = setInOrder(QUERY_ALBUMS_BY_ARTIST_START, artistName, QUERY_ALBUMS_BY_ARTIST_SORT, sortOrder);
        System.out.println("SQL statement: " + sb);

        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sb)) {

            List<String> albums = new ArrayList<>();

            while (result.next()) {
                albums.add(result.getString(1));
            }
            return albums;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SongArtist> queryArtistForSong(String songName, int sortOrder) {

        String sb = setInOrder(QUERY_ARTISTS_FOR_SONG_START, songName, QUERY_ARTISTS_FOR_SONG_SORT, sortOrder);
        System.out.println("SQL Statement: " + sb);

        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sb)) {

            List<SongArtist> songArtistList = new ArrayList<>();

            while (result.next()) {
                SongArtist songArtist = new SongArtist();
                songArtist.setArtistName(result.getString(1));
                songArtist.setAlbumName(result.getString(2));
                songArtist.setTrack(result.getInt(3));
                songArtistList.add(songArtist);
            }
            return songArtistList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String setInOrder(String startMethodName, String name, String sortMethodName, int sortOrder) {
        StringBuilder sb = new StringBuilder(startMethodName);
        if (name != null) {
            sb.append(name);
            sb.append("\"");
        }

        if (sortOrder != ORDER_BY_NONE) {
            sb.append(sortMethodName);
            if (sortOrder == ORDER_BY_DESC) {
                sb.append("DESC");
            } else {
                sb.append("ASC");
            }
        }
        return sb.toString();
    }

}



