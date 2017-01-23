package com.ttth.example;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ttth.account.MyAccount;
import com.ttth.adapter.ListAccountAdapter;
import com.ttth.data.GetData;
import com.ttth.network.NetworkReceiver;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String KEY_LIST = "key_list";
    private static final String KEY_SAVE = "key_save";
    private Button btnGetData;
    private RecyclerView rvListData;
    private ListAccountAdapter accountAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public static final String WIFI = "Wi-Fi";
    public static final String MOBILE = "mobile";
    public static String sPrefWifi = null,sPreMobile = null;
    private static boolean wifiConnected = false;
    private static boolean mobileConnected = false;
    public static boolean refeshDisplay = true;
    private NetworkReceiver receiver;
    private ArrayList<MyAccount> arrAccount;
    private GetData getData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNetworkManager();
        initView();
        saveData(savedInstanceState);
    }

    private void saveData(Bundle savedInstanceState) {
        if (savedInstanceState != null){
            arrAccount = savedInstanceState.getParcelableArrayList(KEY_SAVE);
            initData();
        }
    }

    private void initNetworkManager() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkReceiver();
        this.registerReceiver(receiver, intentFilter);
    }


    private void initView() {
        btnGetData = (Button) this.findViewById(R.id.btnGetData);
        layoutManager = new LinearLayoutManager(this);
        rvListData = (RecyclerView) this.findViewById(R.id.myRecycler);
        rvListData.setLayoutManager(layoutManager);

        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateConnectedFlag();
                myGetData();

            }
        });
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            arrAccount = new ArrayList<>();
            arrAccount.addAll((ArrayList< MyAccount>) msg.obj);
            initData();
        }
    };
    void initData(){
        accountAdapter = new ListAccountAdapter(arrAccount);
        rvListData.setAdapter(accountAdapter);
        accountAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null){
            this.unregisterReceiver(receiver);
        }
        try {
            getData.isProgressShow();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        sPrefWifi = preferences.getString(KEY_LIST,WIFI);
        sPreMobile = preferences.getString(KEY_LIST,MOBILE);
        updateConnectedFlag();
        Log.e("---MAIN-","------onstart");
    }
    public void updateConnectedFlag(){
        ConnectivityManager connectManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfor = connectManager.getActiveNetworkInfo();
        if (activeInfor != null && activeInfor.isConnected()){
            wifiConnected = activeInfor.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfor.getType() == ConnectivityManager.TYPE_MOBILE;
        }else {
            Toast.makeText(this,"Network isn't connect",Toast.LENGTH_SHORT).show();
            wifiConnected = false;
            mobileConnected = false;
        }
    }
    public void myGetData(){
        if (((sPrefWifi.equals(WIFI)) && (wifiConnected))|| ((sPreMobile.equals(MOBILE)) && (mobileConnected))) {
            final String link = "http://dvn.esy.es/get_persions_api";
            getData = new GetData(MainActivity.this, handler);
            getData.execute(link);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_SAVE,arrAccount);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.getParcelableArrayList(KEY_SAVE);
    }
}
