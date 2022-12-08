package com.example.cultivate_chat_app.ui.weather.Location;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

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
}