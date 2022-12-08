package com.example.cultivate_chat_app.ui.weather.Location;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

public class LocationViewModel extends AndroidViewModel {

   public MutableLiveData<LatLng> mResponse;

   public LocationViewModel(@NonNull Application application) {
      super(application);
      mResponse = new MutableLiveData<>();
   }

   public void addReponseObserver(@NonNull LifecycleOwner owner,
                                  @NonNull Observer<? super LatLng> observer) {
      mResponse.observe(owner, observer);
   }

   //create a post request to convert zip code to lat and long
   public void connectPost(String zipCode) {
      String url = "https://cultivate-app-web-service.herokuapp.com/zipToLatLong";
      JSONObject body = new JSONObject();
      try {
         body.put("zip", zipCode);
      } catch (Exception e) {
         e.printStackTrace();
      }
      Request request = new JsonObjectRequest(
              Request.Method.POST,
              url,
              body,
              this::handleResult,
              this::handleError);
      request.setRetryPolicy(new DefaultRetryPolicy(
              10000,
              DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
              DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
      Volley.newRequestQueue(getApplication()).add(request);
   }

   private void handleError(VolleyError volleyError) {
      System.out.println("ERROR: " + volleyError.getMessage());
      throw new IllegalStateException(volleyError.getMessage());

   }

   private void handleResult(JSONObject jsonObject) {
      try {
         double lat = jsonObject.getDouble("latitude");
         double lon = jsonObject.getDouble("longitude");
         LatLng latLng = new LatLng(lat, lon);
         mResponse.setValue(latLng);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}