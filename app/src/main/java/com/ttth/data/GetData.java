package com.ttth.data;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.ttth.account.MyAccount;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Thanh Hang on 23/01/17.
 */

public class GetData extends AsyncTask<String, Void, ArrayList<MyAccount>> {
    private Context mContext;
    private Handler handler;
    private ArrayList<MyAccount> arrAccount;
    private ProgressDialog progressDialog;

    public GetData(Context mContext, Handler handler) {
        this.mContext = mContext;
        this.handler = handler;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        arrAccount = new ArrayList<>();

    }

    @Override
    protected ArrayList<MyAccount> doInBackground(String... params) {
        String link = params[0];
        try{
            URL url = new URL(link);
            Log.e("--------","+++++++++"+link);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(5000);
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder stringBuilder = new StringBuilder();
            int value = inputStream.read();
            while (value!=-1){
                char c = (char)value;
                stringBuilder.append(c);
                value = inputStream.read();
            }
            inputStream.close();
            String textData = stringBuilder.toString();
            getAccount(textData);
            Log.e("---GET_DATA-","-------"+arrAccount);
            Thread.sleep(5000);
            int timeOut = urlConnection.getConnectTimeout();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return arrAccount;
    }
    void getAccount(String result){
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("_id");
                String name = jsonObject.getString("name");
                String phone = jsonObject.getString("phone");
                String email =  jsonObject.getString("email");
                MyAccount myAccount = new MyAccount(id, name, phone, email);
                arrAccount.add(myAccount);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void isProgressShow(){
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onPostExecute(ArrayList<MyAccount> myAccounts) {
        super.onPostExecute(myAccounts);
        if (myAccounts != null){
            try {
                progressDialog.dismiss();
                Message message = new Message();
                message.obj = myAccounts;
                handler.handleMessage(message);

            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            Toast.makeText(mContext,"Qua trinh download bi gian doan", Toast.LENGTH_SHORT).show();
        }
    }
}
