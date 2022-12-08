package com.example.cultivate_chat_app.ui.weather.HourlyWeatherForecast;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

public class HourlyWeatherForecastViewModel extends AndroidViewModel {
   public MutableLiveData<JSONObject> mResponse;

   public HourlyWeatherForecastViewModel(@NonNull Application application) {
       super(application);
       mResponse = new MutableLiveData<>();
   }

   public void addResponseObserver(@NonNull LifecycleOwner owner,
                                   @NonNull Observer<? super JSONObject> observer) {
       mResponse.observe(owner, observer);
   }

   private void handleError(final VolleyError error) {
       Log.e("CONNECTION ERROR",  "Get hourly weather failed"); //error.getLocalizedMessage());
       throw new IllegalStateException();//error.getMessage());
   }

   private void handleResult(final JSONObject result) {
       mResponse.setValue(result);
       System.out.println("HANDLE RESULT ACTIVATES, Hourly weather: " + mResponse.getValue());
   }

   public void connectPost(LatLng latLng) {
       String url = "https://cultivate-app-web-service.herokuapp.com/24HourWeather";
       JSONObject body = new JSONObject();
      try {
         body.put("latitude", latLng.latitude);
         body.put("longitude", latLng.longitude);
      } catch (JSONException e) {
         e.printStackTrace();
      }
       Request request = new JsonObjectRequest(
               Request.Method.POST,
               url,
               body,
               this::handleResult,
               this::handleError);
       request.setRetryPolicy(new DefaultRetryPolicy(
               10_000,
               DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
               DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
       Volley.newRequestQueue(getApplication().getApplicationContext()).add(request);
   }
}