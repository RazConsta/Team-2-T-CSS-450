package com.example.cultivate_chat_app.ui.weather.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class LocationViewModel extends ViewModel {

   private double mLatitude;
   private double mLongitude;

   public LocationViewModel(double latitude, double longitude) {
      mLatitude = latitude;
      mLongitude = longitude;
   }

   //make setters and getters for the lat and long
   public double getLatitude() {
      return mLatitude;
   }

   public double getLongitude() {
      return mLongitude;
   }

   public void setLatitude(double latitude) {
      mLatitude = latitude;
   }

   public void setLongitude(double longitude) {
      mLongitude = longitude;
   }

   public static class LocationViewModelFactory implements ViewModelProvider.Factory {
      private final double latitude;
      private final double longitude;

      public LocationViewModelFactory(double latitude, double longitude) {
         this.latitude = latitude;
         this.longitude = longitude;
      }

      @NonNull
      @Override
      public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
         if (modelClass == LocationViewModel.class) {
            return (T) new LocationViewModel(latitude, longitude);
         }
         throw new IllegalArgumentException(
                 "Argument must be: " + LocationViewModel.class);
      }
   }
}