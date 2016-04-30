package com.example.oliv.myapplication;

/**
 * Created by oliv on 28.04.2016.
 */
public final class Constants {

    // Defines a custom Intent action
    public static final String GET_CURRENT_LOCATION =
            "com.example.android.myapplication.ADD_LOCATION";

    // Defines a custom Intent action
    public static final String GET_LOCATIONS_ACTION =
            "com.example.android.myapplication.GET_LOCATIONS";

    // Defines a custom Intent action
    public static final String PROVIDE_LOCATIONS_ACTION =
            "com.example.android.myapplication.PROVIDE_LOCATIONS";

    // Defines a custom Intent action
    public static final String PROVIDE_CURRENT_LOCATION =
            "com.example.android.myapplication.PROVIDE_CURRENT_LOCATION";

    // Defines a custom extra key
    public static final String PROVIDE_CURRENT_LOCATIONS_EXTRA_KEY =
            "com.example.android.myapplication.CURRENT_EXTRA_KEY";

    // Defines a custom extra key
    public static final String PROVIDE_LOCATIONS_EXTRA_KEY =
            "com.example.android.myapplication.EXTRA_KEY";

    // Defines TAG marker
    public static final String LOG_TAG =  "BACKGROUND";

    // Defines min time for location update in ms
    public static final int LOCATION_INTERVAL = 1000;

    // Defines min distance for location update in meter
    public static final float LOCATION_DISTANCE = 10f;
}
