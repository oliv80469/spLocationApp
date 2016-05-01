package com.example.oliv.myapplication;

/**
 * Created by oliv on 28.04.2016.
 */
public final class Constants {

    // Defines a custom Intent action
    public static final String ACTION_GET_CURRENT_LOCATION =
            "com.example.android.myapplication.GET_LOCATION";

    // Defines a custom Intent action
    public static final String ACTION_SET_CURRENT_LOCATION =
            "com.example.android.myapplication.SET_LOCATION";

    // Defines a custom Intent action
    public static final String ACTION_GET_LOCATIONS =
            "com.example.android.myapplication.GET_LOCATIONS";

    // Defines a custom Intent action
    public static final String PROVIDE_LOCATIONS_ACTION =
            "com.example.android.myapplication.PROVIDE_LOCATIONS";

    // Defines a custom Intent action
    public static final String PROVIDE_CURRENT_LOCATION =
            "com.example.android.myapplication.PROVIDE_CURRENT_LOCATION";

    // Defines a custom extra key
    public static final String PROVIDE_CURRENT_LOCATION_EXTRA_KEY =
            "com.example.android.myapplication.CURRENT_EXTRA_KEY";

    // Defines a custom extra key
    public static final String EXTRA_KEY_LOCATION =
            "com.example.android.myapplication.EXTRA_KEY_LONG";

    // Defines TAG marker
    public static final String LOG_TAG =  "BACKGROUND";

    // Defines min time for location update in ms
    public static final int LOCATION_INTERVAL = 0;

    // Defines min distance for location update in meter
    public static final float LOCATION_DISTANCE = 0;
}
