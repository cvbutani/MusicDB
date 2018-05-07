package com.cvbutani.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: cvbutani
 * Date: 06/05/18
 */

public class DataSource extends musicDB {

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

    public List<Artist> queryArtist(int sortOrder) {

        StringBuilder sb = new StringBuilder("SELECT * FROM ");
        sb.append(TABLE_ARTISTS);
        if (sortOrder != ORDER_BY_NONE) {
            sb.append(" ORDER BY ");
            sb.append(COLUMN_ARTISTS_NAME);
            sb.append(" COLLATE NOCASE ");
            if (sortOrder == ORDER_BY_DESC) {
                sb.append("DESC");
            } else {
                sb.append("ASC");
            }

        }
//        Statement statement = null;
//        ResultSet result = null;

        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sb.toString())) {

            //  "SELECT * FROM " + TABLE_ARTISTS is replaced with sb.toString()
            //  to set order of the artist to either Ascending order or descending order.

//            statement = connection.createStatement();
//            result = statement.executeQuery("SELECT * FROM " + TABLE_ARTISTS);

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



