package com.cvbutani;

import com.cvbutani.model.Artist;
import com.cvbutani.model.DataSource;

import java.util.List;

/**
 * Author: cvbutani
 * Date: 06/05/18
 */

public class Main {

    public static void main(String[] args) {
        DataSource dataSource = new DataSource();
        dataSource.open();

        List<Artist> artists = dataSource.queryArtist();
        if(artists == null){
            System.out.println("No Artist");
        }

        for(Artist artist: artists){
            System.out.println( "ID: " + artist.getId() + ", Name: " + artist.getName());
        }
        dataSource.close();
    }
}
