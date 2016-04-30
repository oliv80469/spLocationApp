package com.example.oliv.myapplication;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
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
            Log.e(Constants.LOG_TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location)
        {
            Log.e(Constants.LOG_TAG, "onLocationChanged: " + location);
            mLastLocation.set(location);
        }

        @Override
        public void onProviderDisabled(String provider)
        {
            Log.e(Constants.LOG_TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider)
        {
            Log.e(Constants.LOG_TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
            Log.e(Constants.LOG_TAG, "onStatusChanged: " + provider);
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
        super("BackGround");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.e(Constants.LOG_TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate()
    {
        Log.e(Constants.LOG_TAG, "onCreate");
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

        Intent provideLocation = null;
        Bundle locationBundle = null;
        switch (workIntent.getAction())
        {
            case Constants.GET_CURRENT_LOCATION:
            {
                // Puts the data into the Intent
                provideLocation = new Intent(Constants.PROVIDE_CURRENT_LOCATION);
                locationBundle = new Bundle();
                locationBundle.putString(Constants.PROVIDE_CURRENT_LOCATIONS_EXTRA_KEY,
                        loacationData.getDataReceived().get(loacationData.getDataReceived().size()));
                provideLocation.putExtra(Constants.PROVIDE_CURRENT_LOCATIONS_EXTRA_KEY, locationBundle);

                // Broadcasts the Intent to receivers in this app.
                LocalBroadcastManager.getInstance(this).sendBroadcast(provideLocation);
            };
            case Constants.GET_LOCATIONS_ACTION :
            {
                // Puts the data into the Intent
                provideLocation = new Intent(Constants.PROVIDE_LOCATIONS_ACTION);
                locationBundle = new Bundle();
                locationBundle.putStringArrayList(Constants.PROVIDE_LOCATIONS_EXTRA_KEY, loacationData.getDataReceived());
                provideLocation.putExtra(Constants.PROVIDE_LOCATIONS_EXTRA_KEY, locationBundle);

                // Broadcasts the Intent to receivers in this app.
                LocalBroadcastManager.getInstance(this).sendBroadcast(provideLocation);
            }
            default:
            {
                // Logger error
                Log.e(Constants.LOG_TAG, "wrong intent action");
            }

        }
    }
}