package com.ttth.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.ttth.example.R;

import static com.ttth.example.MainActivity.MOBILE;
import static com.ttth.example.MainActivity.WIFI;
import static com.ttth.example.MainActivity.refeshDisplay;
import static com.ttth.example.MainActivity.sPreMobile;
import static com.ttth.example.MainActivity.sPrefWifi;

/**
 * Created by Thanh Hang on 23/01/17.
 */

public class NetworkReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectManager.getActiveNetworkInfo();
        if (WIFI.equals(sPrefWifi) && networkInfo!=null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
            refeshDisplay = true;
            Toast.makeText(context, R.string.wifi_connected,Toast.LENGTH_SHORT).show();
        }
        else if (MOBILE.equals(sPreMobile)&& networkInfo!=null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
            refeshDisplay = true;
            Toast.makeText(context, R.string.mobile_connected,Toast.LENGTH_SHORT).show();
        }
    }
}
