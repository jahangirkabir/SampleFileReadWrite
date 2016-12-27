package com.jahanbabu.samplefilereadwrite;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    double lat = 23.43241;
    double lng = 90.23421;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i < 10; i++){
            lat += 0.1;
            lng += 0.1;
//            LatLng latLng = new LatLng(lat, lng);
//            latLngs.add(latLng);

            JSONObject object = new JSONObject();
            try {
                object.put("latitude", lat);
                object.put("longitude", lng);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(object);
        }

//        Main list object
//        JSONObject objMainList = new JSONObject();
//
//        //prepare item array for "A"
//        JSONArray arrForA = new JSONArray();
//        JSONObject itemA = new JSONObject();
//        itemA.put("name", "aaa");
//        arrForA.put(itemA);

        JSONObject object = new JSONObject();
        try {
            for(int i = 0; i < 10; i++){
                object.put(i+lat+""+lng, jsonArray);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
//        Log.e("JSON", object.toString());
//        saveData(this, object.toString());


//        Log.e("JSON READ > ", getData(this));
//        String json = getData(this);

        Log.e("JSON READ > ", readFileFromRaw(this));
        String json = readFileFromRaw(this);

        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator iterator = jsonObject.keys();
            while(iterator.hasNext()){
                String key = (String)iterator.next();
                Log.e("JSON KEY > ", key);
//                JSONObject issue = jsonObject.getJSONObject(key);
                //  get id from  issue
//                String _pubKey = issue.optString("id");

                JSONArray myjsonArray = jsonObject.getJSONArray(key);
                Log.e("JSON Value > ", myjsonArray.length() + " --- " +myjsonArray.toString());
                for (int j = 0; j < myjsonArray.length(); j++){
                    JSONObject cjo = new JSONObject();
                    cjo = myjsonArray.getJSONObject(j);
                    String lat = cjo.getString("latitude");
                    String lng = cjo.getString("longitude");
                    Log.e("LAT - LNG : ", lat+"---"+lng);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    static String fileName = "rvarea.json";

    public static void saveData(Context context, String mJsonResponse) {
        try {
            FileWriter file = new FileWriter(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS).getPath() + "/" + fileName);
            file.write(mJsonResponse);
            file.flush();
            file.close();
        } catch (IOException e) {
            Log.e("TAG", "Error in Writing: " + e.getLocalizedMessage());
        }
    }

    public static String getData(Context context) {
        try {
            File f = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS).getPath() + "/" + fileName);
            //check whether file exists
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer);
        } catch (IOException e) {
            Log.e("TAG", "Error in Reading: " + e.getLocalizedMessage());
            return null;
        }
    }

    public String readFileFromRaw(Context context){
        try {
            InputStream is = context.getResources().openRawResource(R.raw.rvarea);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer);
        } catch (IOException e) {
            Log.e("TAG", "Error in Reading: " + e.getLocalizedMessage());
            return null;
        }
    }
}
