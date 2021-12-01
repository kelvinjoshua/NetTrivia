package com.example.ogtrivia.Controller;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class Singleton extends Application {
    private static Singleton instance;
    private RequestQueue requestQueue;

  /*For network reliant application,singleton instance with application wide context*/
    public static synchronized Singleton getInstance() {
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            //instantiate requestqueue object
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }
    /*Enqueue the request/s*/
    public <T> void addToRequestQueue(Request<T> req) {
        //method returns our queue object
        getRequestQueue().add(req);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
