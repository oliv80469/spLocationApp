package com.example.oliv.myapplication;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oliv on 28.04.2016.
 */
public class LoacationData {

    // TODO to be extended with DB support
    ArrayList<Location> dataReceived = null;

    public LoacationData() {
        dataReceived = new ArrayList<Location>();
    }

    /**
     * return reference to list
     **/
    public ArrayList<Location> getDataReceived() {
        return dataReceived;
    }
}
