package com.mehuljoisar.lockscreen.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mehuljoisar.lockscreen.datatypes.ImageLink;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mohammad on 12/29/2015.
 */
public class DataSynchronizerBackgroundService extends Service{
    @Override
    public IBinder onBind(Intent i){
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, startId, startId);
        return START_STICKY;
    }
    public void onCreate() {
        super.onCreate();

    }
    public static class WifiConnectedBroadcastReceiver extends BroadcastReceiver
    {
        //TODO: do when starting up the application, and implement a timer
        @Override
        public void onReceive(Context context,Intent i){
            syncDBWithServer(context);
        }
    }
    public static void syncDBWithServer(Context context){
        try {
/*            RequestQueue queue = Volley.newRequestQueue(context);
            HashMap<String, String> params = new HashMap<>();
            params.put("token", "29ecefddcc1704ee78f4f177a7452caca06a9d8055583dd347c922e8a914487bf04e61198faa2d88abc2f4e7b6da9d31");
            params.put("device", "e2c2810b440eb22a");

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, "http://hamtag.net/lock", new JSONObject(params),
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                ImageLink imageLink = new ObjectMapper().readValue(response.toString(), ImageLink.class);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });
            queue.add(jsonArrayRequest);*/
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
