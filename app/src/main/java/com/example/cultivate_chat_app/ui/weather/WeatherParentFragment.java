package com.example.cultivate_chat_app.ui.weather;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cultivate_chat_app.R;
import com.example.cultivate_chat_app.databinding.FragmentWeatherParentBinding;
import com.example.cultivate_chat_app.ui.weather.CurrentWeather.CurrentWeatherViewModel;
import com.example.cultivate_chat_app.ui.weather.HourlyWeatherForecast.HourlyWeatherForecastViewModel;
import com.example.cultivate_chat_app.ui.weather.Location.LocationViewModel;
import com.example.cultivate_chat_app.ui.weather.WeeklyWeatherForecast.WeeklyWeatherForecastViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

public class WeatherParentFragment extends Fragment {
   public FragmentWeatherParentBinding mBinding;

   //View Models
   public CurrentWeatherViewModel mCurrentWeatherViewModel;
   public HourlyWeatherForecastViewModel mHourlyWeatherForecastViewModel;
   public WeeklyWeatherForecastViewModel mWeeklyWeatherForecastViewModel;
   public LocationViewModel mLocationViewModel;

   private GoogleMap mMap;

   private GoogleMap.OnMapClickListener mMapClickListener = new GoogleMap.OnMapClickListener() {
      @Override
      public void onMapClick(LatLng latLng) {
         Log.d("LAT/LONG", latLng.toString());
         if (mMap != null) {
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng));
         }

         mLocationViewModel.mResponse.setValue(latLng);

         //update viewmodel
         //make a location provider
         //mLocationViewModel.mResponse.setValue(latLng);
         //do not zoom in
         //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

      }
   };

   private OnMapReadyCallback mMapReadyCallback = googleMap -> {
      mMap = googleMap;
      // Add a marker in Sydney and move the camera
      //LatLng sydney = new LatLng(-34, 151);
      //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
      //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
   };

   public void onMapReady(GoogleMap googleMap) {
      mMap = googleMap;
      mLocationViewModel.addReponseObserver(getViewLifecycleOwner(), latLng -> {

         if(mLocationViewModel != null) {
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
               googleMap.setMyLocationEnabled(true);
            } else {
               requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                       1);
            }
            //Zoom levels are from 2.0f (zoomed out) to 21.f (zoomed in)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));

         }
      });
      mMap.setOnMapClickListener(mMapClickListener);
   }

   private void setWeatherByLocation() {
//      //get device location
//      if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//         mMap.setMyLocationEnabled(true);
//         Location location = mMap.getMyLocation();
//         LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//         mLocationViewModel.mResponse.setValue(latLng);
//      } else {
//         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//         }
//      }
   }

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      mCurrentWeatherViewModel = new ViewModelProvider(this).get(CurrentWeatherViewModel.class);
      mHourlyWeatherForecastViewModel = new ViewModelProvider(this).get(HourlyWeatherForecastViewModel.class);
      mWeeklyWeatherForecastViewModel = new ViewModelProvider(this).get(WeeklyWeatherForecastViewModel.class);
      mLocationViewModel = new ViewModelProvider(this).get(LocationViewModel.class);
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      // Inflate the layout for this fragment
      mBinding = FragmentWeatherParentBinding.inflate(inflater);
      return mBinding.getRoot();
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      mBinding.zipCodeSubmitButton.setOnClickListener(v -> {
         String zipCode = mBinding.zipCodeEditText.getText().toString();
         if(zipCode.length() == 5) {
            mLocationViewModel.connectPost(zipCode);
         }
      });

      //LocationViewModel Observer
      mLocationViewModel.addReponseObserver(getViewLifecycleOwner(), latLng -> {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mCurrentWeatherViewModel.connectPost(latLng);
            mHourlyWeatherForecastViewModel.connectPost(latLng);
            mWeeklyWeatherForecastViewModel.connectPost(latLng);
            mBinding.currentWeatherLocationTextView
                    .setText("Lat:" + latLng.latitude + " Long:" + latLng.longitude);
         }
//         mHourlyWeatherForecastViewModel.updateHourlyWeatherForecast(latLng);
//         mWeeklyWeatherForecastViewModel.updateWeeklyWeatherForecast(latLng);
      });

      //CurrentWeatherViewModel Observer
      mCurrentWeatherViewModel.addResponseObserver(getViewLifecycleOwner(), weather -> {
         try {
            mBinding.currentTemperatureTextView
                    .setText(weather.getString("temperature") + "°F");
            mBinding.currentWeatherConditionsTextView
                    .setText(weather.getString("conditions"));
         } catch (JSONException e) {
            e.printStackTrace();
         }
      });
      //HourlyWeatherForecastViewModel Observer
      mHourlyWeatherForecastViewModel.addResponseObserver(getViewLifecycleOwner(), temp ->
      {
         try {
            mBinding.hour1WeatherTextView.setText(mHourlyWeatherForecastViewModel.mResponse.getValue().getString("hour1"));
            mBinding.hour2WeatherTextView.setText(mHourlyWeatherForecastViewModel.mResponse.getValue().getString("hour2"));
            mBinding.hour3WeatherTextView.setText(mHourlyWeatherForecastViewModel.mResponse.getValue().getString("hour3"));
            mBinding.hour4WeatherTextView.setText(mHourlyWeatherForecastViewModel.mResponse.getValue().getString("hour4"));
            mBinding.hour5WeatherTextView.setText(mHourlyWeatherForecastViewModel.mResponse.getValue().getString("hour5"));
            mBinding.hour6WeatherTextView.setText(mHourlyWeatherForecastViewModel.mResponse.getValue().getString("hour6"));
            mBinding.hour7WeatherTextView.setText(mHourlyWeatherForecastViewModel.mResponse.getValue().getString("hour7"));
            mBinding.hour8WeatherTextView.setText(mHourlyWeatherForecastViewModel.mResponse.getValue().getString("hour8"));

         } catch (JSONException e) {
            e.printStackTrace();
         }
      });

//      //WeeklyWeatherForecastViewModel Observer
      mWeeklyWeatherForecastViewModel.addResponseObserver(getViewLifecycleOwner(), temp ->
      {
         try {
            mBinding.day1WeatherTextView.
                    setText(mWeeklyWeatherForecastViewModel.mResponse.getValue().getString("day1"));
            mBinding.day2WeatherTextView.
                    setText(mWeeklyWeatherForecastViewModel.mResponse.getValue().getString("day2"));
            mBinding.day3WeatherTextView.
                    setText(mWeeklyWeatherForecastViewModel.mResponse.getValue().getString("day3"));
            mBinding.day4WeatherTextView.
                    setText(mWeeklyWeatherForecastViewModel.mResponse.getValue().getString("day4"));
            mBinding.day5WeatherTextView.
                    setText(mWeeklyWeatherForecastViewModel.mResponse.getValue().getString("day5"));
         } catch (JSONException e) {
            e.printStackTrace();
         }
      });
      SupportMapFragment mapFragment =
              (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
      //add this fragment as the OnMapReadyCallback -> See onMapReady()
      mapFragment.getMapAsync(this::onMapReady);


   }
}