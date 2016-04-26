package com.example.james.cyclecoach;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;


/**
 * Created by Matt on 4/1/2016.
 */
public class GPSManager implements LocationListener {

    LanceMood lanceMood;
    LocationManager locationManager;
    String LOCATION_PROVIDER = LocationManager.NETWORK_PROVIDER;
    Location prevLocation;
    Location currentLocation;
    int distance;

    public GPSManager(LanceMood lm) {
        lanceMood = lm;
        locationManager = (LocationManager) lanceMood.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        LOCATION_PROVIDER = locationManager.getBestProvider(criteria, false);
    }

    public void register() {
        if (ActivityCompat.checkSelfPermission(lanceMood, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(lanceMood, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LOCATION_PROVIDER, 5000, 0, this);
        prevLocation = locationManager.getLastKnownLocation(LOCATION_PROVIDER);
        currentLocation = locationManager.getLastKnownLocation(LOCATION_PROVIDER);
    }

    public void unregister() {
        if (ActivityCompat.checkSelfPermission(lanceMood, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(lanceMood, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        prevLocation = currentLocation;
        currentLocation = location;
        distance += currentLocation.distanceTo(prevLocation);
        //lanceMood.updateDistance(distance);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
