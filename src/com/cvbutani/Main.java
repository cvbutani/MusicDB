package com.cvbutani;

import com.cvbutani.model.Artist;
import com.cvbutani.model.DataSource;
import com.cvbutani.model.SongArtist;
import com.cvbutani.model.musicDB;

import java.util.List;

/**
 * Author: cvbutani
 * Date: 06/05/18
 */

public class Main {

    public static void main(String[] args) {
        DataSource dataSource = new DataSource();
        dataSource.open();

        List<Artist> artists = dataSource.queryArtist(DataSource.ORDER_BY_ASC);
        if (artists == null) {
            System.out.println("No Artist");
        }
        for (Artist artist : artists) {
            System.out.println("ID: " + artist.getId() + ", Name: " + artist.getName());
        }

        List<String> albumsOfArtist = dataSource.queryAlbumsForArtists("Iron Maiden", DataSource.ORDER_BY_ASC);

        for (String artist : albumsOfArtist) {
            System.out.println("Name: " + artist);
        }

        List<SongArtist> songArtists = dataSource.queryArtistForSong("Go Your Own Way", DataSource.ORDER_BY_DESC);

        if (songArtists == null) {
            System.out.println("Can't find artist for this song");
            return;
        }

        for (SongArtist songArtist : songArtists) {
            System.out.println("Song Album: " + songArtist.getAlbumName() + ", Album Artist: " + songArtist.getArtistName() + ", Songs: " + songArtist.getTrack());
        }

        int count = dataSource.getCount(musicDB.TABLE_SONGS);
        System.out.println("Number of song is: " + count);

        dataSource.createSongForArtist();
        System.out.println();

        songArtists = dataSource.querySongInfoView("Heartless", musicDB.ORDER_BY_NONE);
        if (songArtists == null) {
            System.out.println("Can't find artist for this song");
            return;
        }

        for (SongArtist songArtist : songArtists) {
            System.out.println("Song Album: " + songArtist.getAlbumName() + "\n" + ", Artist Name: " + songArtist.getArtistName() + "\n" + " , Songs: " + songArtist.getTrack());
        }

        dataSource.close();
    }
}
