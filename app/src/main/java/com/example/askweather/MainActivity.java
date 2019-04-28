package com.example.askweather;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    ImageView imgweatherbackground;
    DatabaseHelper dbHelper;
    EditText edcity;
    String fullUrl;
    String url1="http://api.openweathermap.org/data/2.5/weather?q=";
    String url2=",IND&APPID=fe44a2c83e558756d342db58661dadd7&units=metric";

    TextView tvtemperature;
    TextView tvdescription;
    Button btnsearch;

    JSONObject jsonObject;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edcity=(EditText)findViewById(R.id.city);
        tvtemperature=(TextView) findViewById(R.id.temperature);
        tvdescription=(TextView)findViewById(R.id.description);
        btnsearch=(Button)findViewById(R.id.search);
        imgweatherbackground=(ImageView)findViewById(R.id.weatherbackground);
        dbHelper=new DatabaseHelper(this);

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Cursor cursor=dbHelper.getData(edcity.getText().toString());
                //String dbcity=cursor.getString(1);
              //  if(dbcity==edcity.getText().toString()){
                //    edcity.setText(cursor.getString(1));
                  //  tvtemperature.setText(cursor.getString(2));
                    //tvdescription.setText(cursor.getString(3));
                    //Log.i("fetching from db","Feching values from database");
                //}
                //else{
                    askWeather();
                  //  Log.i("feching from api","Fetching values from api");
                //}
                //boolean isInserted=dbHelper.insertData(edcity.getText().toString(),
                  //      tvtemperature.getText().toString(),
                    //    tvdescription.getText().toString());
                //if(isInserted==true)
                  //  Toast.makeText(MainActivity.this,"Data inserted successfully",Toast.LENGTH_SHORT).show();
                //else
                  //  Toast.makeText(MainActivity.this,"Data insertion unsuccessfull",Toast.LENGTH_SHORT).show();

            }
        });

    }
      public boolean isNetworkAvailable(Context context)
     {
        final ConnectivityManager connectivityManager=((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo()!=null && connectivityManager.getActiveNetworkInfo().isConnected();
     }
     public void askWeather(){
         try {
             String encodedCityName= URLEncoder.encode(edcity.getText().toString(),"UTF-8");
             fullUrl=url1+encodedCityName+url2;

         } catch (UnsupportedEncodingException e) {
             e.printStackTrace();
         }
         if(!isNetworkAvailable(this)){
             Toast.makeText(MainActivity.this,"please check your internet connection",Toast.LENGTH_SHORT).show();
         }
         else {
             JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, fullUrl, null, new Response.Listener<JSONObject>() {
                 @Override
                 public void onResponse(JSONObject response) {
                     try {
                         jsonObject=(JSONObject) response.getJSONArray("weather").get(0);

                         tvdescription.setText(jsonObject.getString("description"));
                         tvtemperature.setText(response.getJSONObject("main").getString("temp"));

                         if(jsonObject.getString("main").equals("Clouds")){
                             imgweatherbackground.setImageResource(R.drawable.cloudy);
                         }
                         else if (jsonObject.getString("main").equals("Rain")){
                             imgweatherbackground.setImageResource(R.drawable.raining);
                         }
                         else if (jsonObject.getString("main").equals("Clear")){
                             imgweatherbackground.setImageResource(R.drawable.clearsky);
                         }
                         else {
                             imgweatherbackground.setImageResource(R.drawable.defaultweather);
                         }
                         removeKeyboard();

                     } catch (JSONException e) {
                         e.printStackTrace();
                     }

                 }
             }, new Response.ErrorListener() {
                 @Override
                 public void onErrorResponse(VolleyError error) {
                     Toast.makeText(MainActivity.this,"Error while Loading",Toast.LENGTH_SHORT).show();
                 }
             });
             AppController.getInstance(this).addToRequestQueue(jsonObjectRequest);
         }

     }
     public void removeKeyboard(){
        InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
         inputMethodManager.hideSoftInputFromWindow(tvdescription.getWindowToken(),0);

     }
}
