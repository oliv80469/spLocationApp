package com.example.oliv.myapplication;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by oliv on 28.04.2016.
 */

public class BackGround extends IntentService {

    LoacationData loacationData = new LoacationData();
    private LocationManager mLocationManager = null;

    private class LocationListener implements android.location.LocationListener
    {
        Location mLastLocation;

        public LocationListener(String provider)
        {
            Log.d(Constants.LOG_TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location)
        {
            Intent provideLocation = null;
            Bundle locationBundle = null;
            Parcel locationParcel = null;

            Log.d(Constants.LOG_TAG, "onLocationChanged: " + location);
            mLastLocation.set(location);

            // TODO filter and broadcast only if better value
            provideLocation = new Intent(Constants.ACTION_SET_CURRENT_LOCATION);
            locationBundle = new Bundle(location.getExtras());
            provideLocation.putExtras(locationBundle);

            // Broadcasts the Intent to receivers in this app.
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(provideLocation);
        }

        @Override
        public void onProviderDisabled(String provider)
        {
            Log.d(Constants.LOG_TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider)
        {
            Log.d(Constants.LOG_TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
            Log.d(Constants.LOG_TAG, "onStatusChanged: " + provider);
        }
    }

    LocationListener[] mLocationListeners = new LocationListener[] {
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    /**
     * Concstructor
     */
    public BackGround() {
        // call base class constructor
        super(BackGround.class.getName());
        Log.d(Constants.LOG_TAG, "background created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.e(Constants.LOG_TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY_COMPATIBILITY;
    }

    @Override
    public void onCreate()
    {
        Log.d(Constants.LOG_TAG, "onCreate");
        super.onCreate();

        // init network manager
        initializeLocationManager();
        // create NETWORK
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, Constants.LOCATION_INTERVAL, Constants.LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (java.lang.SecurityException ex) {
            Log.i(Constants.LOG_TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(Constants.LOG_TAG, "network provider does not exist, " + ex.getMessage());
        }
        // create GPS
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, Constants.LOCATION_INTERVAL, Constants.LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            Log.i(Constants.LOG_TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(Constants.LOG_TAG, "gps provider does not exist " + ex.getMessage());
        }
    }
    @Override
    public void onDestroy()
    {
        // Destroy all locationManagers
        Log.e(Constants.LOG_TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (SecurityException ex) {
                    Log.i(Constants.LOG_TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }

    private void initializeLocationManager() {
        Log.e(Constants.LOG_TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    /**
     * perform requested request
     * @param workIntent
     */
    @Override
    protected void onHandleIntent(Intent workIntent) {
        Log.e(Constants.LOG_TAG, "onHandleIntent");
        Intent provideLocation = null;
        Bundle locationBundle = null;

        if(workIntent.getAction() != null) {

            switch (workIntent.getAction()) {

                case Constants.ACTION_GET_CURRENT_LOCATION: {
                    Log.d(Constants.LOG_TAG, "ACTION_GET_CURRENT_LOCATION");
                    if (loacationData.getDataReceived().size() != 0)
                    {
                        // Puts the data into the Intent
                        provideLocation = new Intent(Constants.PROVIDE_CURRENT_LOCATION);
                        locationBundle = new Bundle(loacationData.getDataReceived().get(loacationData.getDataReceived().size()).getExtras());
                        provideLocation.putExtras(locationBundle);

                        // Broadcasts the Intent to receivers in this app.
                        LocalBroadcastManager.getInstance(this).sendBroadcast(provideLocation);
                    }
                };
                case Constants.ACTION_SET_CURRENT_LOCATION: {
                    Log.d(Constants.LOG_TAG, "ACTION_SET_CURRENT_LOCATION");
                    // add the given data
                    locationBundle = new Bundle(workIntent.getExtras());
                    loacationData.getDataReceived().add((Location) locationBundle.get(android.location.LocationManager.KEY_LOCATION_CHANGED));
                };
                case Constants.ACTION_GET_LOCATIONS: {
                    Log.d(Constants.LOG_TAG, "ACTION_GET_LOCATIONS");
                    // Puts the data into the Intent
                    // TODO complete when maps is working
//                provideLocation = new Intent(Constants.PROVIDE_LOCATIONS_ACTION);
//                locationBundle = new Bundle();
//                locationBundle.putStringArrayList(Constants.PROVIDE_LOCATIONS_EXTRA_KEY, loacationData.getDataReceived());
//                provideLocation.putExtra(Constants.PROVIDE_LOCATIONS_EXTRA_KEY, locationBundle);
//
//                // Broadcasts the Intent to receivers in this app.
//                LocalBroadcastManager.getInstance(this).sendBroadcast(provideLocation);
                };
                default: {
                    // Logger error
                    Log.e(Constants.LOG_TAG, "wrong intent action");
                };
            }
        }
        else  {
            // Logger error
            Log.e(Constants.LOG_TAG, "empty intent");
        }
    }
}
