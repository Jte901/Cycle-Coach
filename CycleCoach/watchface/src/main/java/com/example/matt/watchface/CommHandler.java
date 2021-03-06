package com.example.matt.watchface;

import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by Matt on 3/21/2016.
 */
public class CommHandler implements DataApi.DataListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    MainActivity mainActivity;
    GoogleApiClient mGoogleApiClient;
    public static final String UPDATE_PHONE = "update phone";
    public static final String UPDATE_WATCH = "update watch";

    public CommHandler(MainActivity ma) {
        mainActivity = ma;
        mGoogleApiClient = new GoogleApiClient.Builder(mainActivity)
                .addApi(Wearable.API).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Wearable.DataApi.addListener(mGoogleApiClient, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        for (DataEvent event : dataEventBuffer) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // DataItem changed
                DataItem item = event.getDataItem();
                if (item.getUri().getPath().compareTo("/" + UPDATE_WATCH) == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    int number = dataMap.getInt(UPDATE_WATCH);
                    mainActivity.updateMood(number);
                }
            } else if (event.getType() == DataEvent.TYPE_DELETED) {
                // DataItem deleted
            }
        }
    }

    public void sendToPhone(int number) {
        PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/" + UPDATE_PHONE);
        putDataMapReq.getDataMap().putInt(UPDATE_PHONE, number);
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        PendingResult<DataApi.DataItemResult> pendingResult =
                Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
