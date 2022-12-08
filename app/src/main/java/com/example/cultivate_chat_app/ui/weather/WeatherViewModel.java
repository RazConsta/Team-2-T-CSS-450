package com.example.cultivate_chat_app.ui.weather;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherViewModel extends AndroidViewModel {

   public WeatherViewModel mWeatherViewModel;
   public MutableLiveData<JSONObject> mWeatherResponse;

   public WeatherViewModel(@NonNull Application application) {
      super(application);
      mWeatherResponse = new MutableLiveData<JSONObject>();
   }

   public void addResponseObserver(@NonNull LifecycleOwner owner,
                                   @NonNull Observer<JSONObject> observer) {
      mWeatherResponse.observe(owner, observer);
   }

   @RequiresApi(api = Build.VERSION_CODES.N)
   public void connectGetZipToLatLong(int zipCode) {
      //String url = "https://cultivate-app-web-service.herokuapp.com/zipToLatLong";
      String url = "https://localhost:5000/currentWeather";
      Request request = null;
      try {
         request = new JsonObjectRequest(
                 Request.Method.GET,
                 url,
                 new JSONObject().put("zip", zipCode),
                 this::handleZipToLatLongResult,
                 this::handleZipToLatLongError);

      } catch (JSONException e) {
         e.printStackTrace();
      }
      request.setRetryPolicy(new DefaultRetryPolicy(
              10_000,
              DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
              DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
      Volley.newRequestQueue(getApplication().getApplicationContext()).add(request);
   }

   private void handleZipToLatLongError(VolleyError volleyError) {
      Log.e("CONNECTION ERROR",  "Get current weather failed"); //error.getLocalizedMessage());
      throw new IllegalStateException();//error.getMessage());
   }

   private void handleZipToLatLongResult(final JSONObject result) {
      try {
         String latitude = result.getString("latitude");
         String longitude = result.getString("longitude");
         LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
         mWeatherResponse.setValue(result);
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectGetWeather(latLng);
         }
         System.out.println("HANDLE RESULT ACTIVATES, Current weather: " + mWeatherResponse.getValue());
      } catch (Exception ex) {
         ex.printStackTrace();
         Log.e("ERROR!", ex.getMessage());
      }
   }

   @RequiresApi(api = Build.VERSION_CODES.N)
   public void connectGetWeather(LatLng latLng) {
      String url = "https://cultivate-app-web-service.herokuapp.com/currentWeather";
      //String url = "http://localhost:5000/currentWeather";

      Request request = null;
      try {
         request = new JsonObjectRequest(
                 Request.Method.GET,
                 url,
                 new JSONObject().put("latitude", latLng.latitude).put("longitude", latLng.longitude),
                 this::handleGetWeatherResult,
                 this::handleGetWeatherError);
      } catch (JSONException e) {
         e.printStackTrace();
      }
      request.setRetryPolicy(new DefaultRetryPolicy(
              10_000,
              DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
              DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
      Volley.newRequestQueue(getApplication().getApplicationContext()).add(request);
   }

   private void handleGetWeatherError(VolleyError volleyError) {
      Log.e("CONNECTION ERROR",  "Get weather failed");
      throw new IllegalStateException();
   }

   private void handleGetWeatherResult(JSONObject jsonObject) {
      try {
         mWeatherResponse.setValue(jsonObject);
      } catch (Exception ex) {
         ex.printStackTrace();
         Log.e("ERROR!", ex.getMessage());
      }
   }
}
