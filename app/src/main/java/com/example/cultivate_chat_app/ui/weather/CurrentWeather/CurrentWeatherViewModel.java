package com.example.cultivate_chat_app.ui.weather.CurrentWeather;

import android.app.Application;
import android.location.Location;
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

public class CurrentWeatherViewModel extends AndroidViewModel{

   public MutableLiveData<JSONObject> mResponse;

   public CurrentWeatherViewModel(@NonNull Application application) {
      super(application);
      mResponse = new MutableLiveData<>();
   }
   public void addResponseObserver(@NonNull LifecycleOwner owner,
                                   @NonNull Observer<? super JSONObject> observer) {
      mResponse.observe(owner, observer);
   }

   private void handleError(final VolleyError error) {
      Log.e("CONNECTION ERROR",  "Get current weather failed"); //error.getLocalizedMessage());
      throw new IllegalStateException();//error.getMessage());
   }

   @RequiresApi(api = Build.VERSION_CODES.N)
   private void handleResult(final JSONObject result) {
      try {
         String temperature = result.getString("temperature");

         JSONObject response = new JSONObject();
         response.put("temperature", temperature);
         response.put("conditions", result.getString("conditions"));
         mResponse.setValue(response);
      } catch (JSONException ex) {
         ex.printStackTrace();
         Log.e("ERROR!", ex.getMessage());
      }
   }

   @RequiresApi(api = Build.VERSION_CODES.N)
   public void connectPost(LatLng latLng) {
      String url = "https://cultivate-app-web-service.herokuapp.com/currentWeather";
      JsonObjectRequest request = null;
      try {
         JSONObject jsonBody = new JSONObject();
         jsonBody.put("latitude", "" + latLng.latitude);
         jsonBody.put("longitude", "" + latLng.longitude);

         request = new JsonObjectRequest(
                 Request.Method.POST,
                 url,
                 jsonBody,
                 this::handleResult,
                 this::handleError);

      } catch (JSONException e) {
         e.printStackTrace();
      }
      if (request != null) {
         request.setRetryPolicy(new DefaultRetryPolicy(
                 10_000,
                 DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                 DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
      }

      Volley.newRequestQueue(getApplication().getApplicationContext()).add(request);
   }

}