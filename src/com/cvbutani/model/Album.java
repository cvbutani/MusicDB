package com.cvbutani.model;

/**
 * Author: cvbutani
 * Date: 07/05/18
 */

public class Album {
    private int id;
    private String name;
    private int artistID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArtistID() {
        return artistID;
    }

    public void setArtistID(int artistID) {
        this.artistID = artistID;
    }
}
