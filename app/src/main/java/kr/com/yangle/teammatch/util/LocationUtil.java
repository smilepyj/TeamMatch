package kr.com.yangle.teammatch.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import static android.content.Context.LOCATION_SERVICE;

public class LocationUtil {

    private final Context mContext;

    private LocationManager locationManager;

    private boolean isGPSEnable = false;
    private boolean isNetWorkEnable = false;
    private boolean isGetLocation = false;

    private double lastLatitude;
    private double lastLongitude;

    public LocationUtil(Context mContext) {
        this.mContext = mContext;
        getMyLocation();
    }

    @SuppressLint("MissingPermission")
    public Location getMyLocation() {
        Location currentLocation = null;

        Log.e("getMyLocation", "getMyLocation start");

        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            long minTime = 1000;
            float minDistance = 1;

            /*if(ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission Error", "권한이 없습니다.");
            } else {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, mLocationListener);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, mLocationListener);

                String locationProvider = LocationManager.GPS_PROVIDER;
                currentLocation = locationManager.getLastKnownLocation(locationProvider);
            }*/

            if(ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission Error", "권한이 없습니다.");
            } else {
                isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                isNetWorkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (!isGPSEnable && !isNetWorkEnable) {
                } else {
                    this.isGetLocation = true;
                    if (isGPSEnable) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, mLocationListener);

                        if (locationManager != null) {
                            currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (currentLocation != null) {
                                lastLatitude = currentLocation.getLatitude();
                                lastLongitude = currentLocation.getLongitude();
                            }
                        }

                    }

                    if (isNetWorkEnable) {
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, mLocationListener);
                        if (currentLocation == null) {
                            if (locationManager != null) {
                                currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                                if (currentLocation != null) {
                                    lastLatitude = currentLocation.getLatitude();
                                    lastLongitude = currentLocation.getLongitude();
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return currentLocation;
    }

    private final LocationListener mLocationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            lastLatitude = location.getLatitude();
            lastLongitude = location.getLongitude();
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
    };

    public double getLastLatitude() {
        return lastLatitude;
    }

    public double getLastLongitude() {
        return lastLongitude;
    }
}
