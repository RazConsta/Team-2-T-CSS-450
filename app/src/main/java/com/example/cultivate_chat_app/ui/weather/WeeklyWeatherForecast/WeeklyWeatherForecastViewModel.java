package com.example.cultivate_chat_app.ui.weather.WeeklyWeatherForecast;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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

public class WeeklyWeatherForecastViewModel extends AndroidViewModel {

   public MutableLiveData<JSONObject>  mResponse;

   public WeeklyWeatherForecastViewModel(@NonNull Application application) {
      super(application);
      mResponse = new MutableLiveData<>();
   }

   public void addResponseObserver(@NonNull LifecycleOwner owner,
                                   @NonNull Observer<? super JSONObject> observer) {
      mResponse.observe(owner, observer);
   }

   private void handleError(final VolleyError error) {
      Log.e("CONNECTION ERROR",  "Get weekly weather failed");
      throw new IllegalStateException();//error.getMessage());
   }

   private void handleResult(final JSONObject result) {
      mResponse.setValue(result);
      System.out.println("HANDLE RESULT ACTIVATES, Weekly weather: " + mResponse.getValue());
   }

   @RequiresApi(api = Build.VERSION_CODES.N)
   public void connectPost(LatLng latLng) {
      String url = "https://cultivate-app-web-service.herokuapp.com/5DayWeather";
      JSONObject jsonBody = new JSONObject();
      try {
         jsonBody.put("latitude", latLng.latitude);
         jsonBody.put("longitude", latLng.longitude);

      } catch (JSONException e) {
         e.printStackTrace();
      }
      Request request = new JsonObjectRequest(
              Request.Method.POST,
              url,
              jsonBody,
              this::handleResult,
              this::handleError);
      request.setRetryPolicy(new DefaultRetryPolicy(
              10_000,
              DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
              DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
      Volley.newRequestQueue(getApplication().getApplicationContext()).add(request);
   }
}