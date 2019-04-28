package com.example.askweather;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController {
    private static AppController mInstance;
    private static Context mContext;
    private RequestQueue mRequestQueue;


    private AppController(Context context){
        mContext=context;
        mRequestQueue=getRequestQueue();
    }


    public RequestQueue getRequestQueue(){
        if(mRequestQueue==null){
                mRequestQueue= Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }
    public void addToRequestQueue(final Request request){
        getRequestQueue().add(request);
    }
    public void addToRequestQueueWithTag(final Request request,String tag){
        request.setTag(tag);
        getRequestQueue().add(request);
    }
    public static synchronized AppController getInstance(Context context){
        if(mInstance==null){
            mInstance=new AppController(context);
        }
        return mInstance;
    }
}
