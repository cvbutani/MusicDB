package com.cvbutani;

import com.cvbutani.model.DataSource;

/**
 * Author: cvbutani
 * Date: 06/05/18
 */

public class Main {

    public static void main(String[] args) {
        DataSource dataSource = new DataSource();
        if (dataSource.open()){
            System.out.println("Can't open DataSource");
            return;
        }
        dataSource.close();
    }
}
