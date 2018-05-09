package com.cvbutani.model;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: cvbutani
 * Date: 06/05/18
 */

public class DataSource extends musicDB {

    private PreparedStatement querySongInfoView;

    private PreparedStatement insertIntoArtists;
    private PreparedStatement insertIntoAlbums;
    private PreparedStatement insertIntoSongs;

    private PreparedStatement queryArtist;
    private PreparedStatement queryAlbum;

    public void open() {
        try {
            connection = DriverManager.getConnection(DB_STRING);
            querySongInfoView = connection.prepareStatement(QUERY_VIEW_SONG_INFO_PREP);

            insertIntoArtists = connection.prepareStatement(INSERT_ARTIST, Statement.RETURN_GENERATED_KEYS);
            insertIntoAlbums = connection.prepareStatement(INSERT_ALBUMS, Statement.RETURN_GENERATED_KEYS);
            insertIntoSongs = connection.prepareStatement(INSERT_SONG);

            queryArtist = connection.prepareStatement(QUERY_ARTIST);
            queryAlbum = connection.prepareStatement(QUERY_ALBUM);

        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (querySongInfoView != null) {
                querySongInfoView.close();
            }
            if (insertIntoArtists != null) {
                insertIntoArtists.close();
            }
            if (insertIntoAlbums != null) {
                insertIntoAlbums.close();
            }
            if (insertIntoSongs != null) {
                insertIntoSongs.close();
            }
            if (queryArtist != null) {
                queryArtist.close();
            }
            if (queryAlbum != null) {
                queryAlbum.close();
            }
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

        return displayDetails(QUERY_ARTISTS_FOR_SONG_START, songName, QUERY_ARTISTS_FOR_SONG_SORT, sortOrder);

    }

    public boolean createSongForArtist() {

        try (Statement statement = connection.createStatement()) {

            statement.execute(CREATE_ARTIST_FOR_SONG_VIEW);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getCount(String table) {

        String sql = "SELECT COUNT(*) AS count, MIN(_id) AS min_id FROM " + table;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            int count = resultSet.getInt("count");
            int min = resultSet.getInt("min_id");
            System.out.println("Minimum: " + min);
            return count;

        } catch (SQLException e) {
            System.out.println("Query Failed: " + e.getMessage());
            return -1;
        }
    }

    public String setInOrder(String startMethodName, String name, String sortMethodName, int sortOrder) {
        StringBuilder sb = new StringBuilder(startMethodName);
        if (name != null) {
            sb.append(name);
            sb.append("\"");
        }
        if (sortMethodName != null) {
            if (sortOrder != ORDER_BY_NONE) {
                sb.append(sortMethodName);
                if (sortOrder == ORDER_BY_DESC) {
                    sb.append("DESC");
                } else {
                    sb.append("ASC");
                }
            }
        }
        return sb.toString();
    }

    public List<SongArtist> querySongInfoView(String title) {

//        return displayDetails(QUERY_VIEW_SONG_INFO, title, null, sortOrder);

        try {
            querySongInfoView.setString(1, title);
            ResultSet resultSet = querySongInfoView.executeQuery();
            List<SongArtist> songArtists = new ArrayList<>();

            while (resultSet.next()) {
                SongArtist songArtist = new SongArtist();
                songArtist.setArtistName(resultSet.getString(1));
                songArtist.setAlbumName(resultSet.getString(2));
                songArtist.setTrack(resultSet.getInt(3));
                songArtists.add(songArtist);
            }
            return songArtists;

        } catch (SQLException e) {
            System.out.println("Couldn't find artist or album from table !");
            return null;
        }
    }

    public List<SongArtist> displayDetails(String title, String name1, String name2, int sortOrder) {

        String sb = setInOrder(title, name1, name2, sortOrder);
        System.out.println("SQL statement: " + sb);

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sb)) {

            List<SongArtist> songArtists = new ArrayList<>();

            while (resultSet.next()) {
                SongArtist songArtist = new SongArtist();
                songArtist.setArtistName(resultSet.getString(1));
                songArtist.setAlbumName(resultSet.getString(2));
                songArtist.setTrack(resultSet.getInt(3));
                songArtists.add(songArtist);
            }
            return songArtists;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Database Transaction should be ACID-Compliant. They must meet following characteristics:
     * <p>
     * 1. Atomicity:   If a series of SQL statements change the database, then wither all the changes
     * are committed or none of them are.
     * <p>
     * 2. Consistency: Before a transaction begins, the database is in a valid state. When it completes,
     * the database is still in a valid state.
     * <p>
     * 3. Isolation:   Until the changes committed by a transaction are completed, they won't be visible
     * to other connections. Transactions can't depend on each other.
     * <p>
     * 4. Durability:  Once the changes performed by a transaction are committed to the database, the're
     * permanent. If an application then crashes or the database server goes down (in the
     * case of a client/server database like MYSQL), the changes made by the transaction
     * are still there when the application runs again, or the database comes back up.
     * <p>
     * Essentially Transaction ensures integrity of the data within a database.
     * <p>
     * COMMANDS:
     * 1.  BEGIN TRANSACTION:  To start transaction manually.
     * 2.  END TRANSACTION:    To end a transaction. Committing changes automatically ends a transaction.
     * Also, ending a transaction also commits any changes. In other words,
     * END TRANSACTION and COMMIT are aliases. We only have to use one when we
     * want to end a transaction and commit the changes.
     * 3.  COMMIT:             use this to commit the changes made by a transaction. As mentioned, this
     * ends the transaction, so we don't need to also run the END TRANSACTION COMMAND.
     * 4.  ROLLBACK:           this rolls back any uncommitted changes and ends the transaction. Note that it
     * can only rollback changes that have occurred since the last COMMIT or ROLLBACK.
     */

    public void insertSong(String title, String artist, String album, int track) {
        try {
            connection.setAutoCommit(false);

            int artistID = updateDatabase(artist, queryArtist, insertIntoArtists, 0);
            int albumID = updateDatabase(album, queryAlbum, insertIntoAlbums, artistID);

            insertIntoSongs.setInt(1, track);
            insertIntoSongs.setString(2, title);
            insertIntoSongs.setInt(3, albumID);

            int affectedRows = insertIntoSongs.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
            } else {
                throw new SQLException("The song insert failed");
            }
        } catch (SQLException e) {
            System.out.println("Insert song exception: " + e.getMessage());
            try {
                System.out.println("Performing Rollback");
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println("You are Fucked !!!!" + e.getMessage());
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit !!" + e.getMessage());
            }
        }
    }

    private int updateDatabase(String name, PreparedStatement queryStatement, PreparedStatement addStatement, int artistId) throws SQLException {
        queryStatement.setString(1, name);

        ResultSet results = queryStatement.executeQuery();
        if (results.next()) {
            return results.getInt(1);
        } else {
            addStatement.setString(1, name);
            if (artistId != 0) {
                addStatement.setInt(2, artistId);
            }
            int affectedRow = addStatement.executeUpdate();

            if (affectedRow != 1) {
                throw new SQLException("Couldn't insert ");
            }
            ResultSet generatedKeys = addStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Couldn't get anything !");
            }
        }
    }
}



