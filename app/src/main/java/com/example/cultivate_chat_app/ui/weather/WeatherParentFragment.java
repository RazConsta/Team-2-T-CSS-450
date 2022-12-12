package com.example.cultivate_chat_app.ui.weather;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Blob;

public class WeatherParentFragment extends Fragment {
   public FragmentWeatherParentBinding mBinding;

   //View Models
   public CurrentWeatherViewModel mCurrentWeatherViewModel;
   public HourlyWeatherForecastViewModel mHourlyWeatherForecastViewModel;
   public WeeklyWeatherForecastViewModel mWeeklyWeatherForecastViewModel;
   public LocationViewModel mLocationViewModel;

   private GoogleMap mMap;
   public int locationByDevice = 0;
   private GoogleMap.OnMapClickListener mMapClickListener = new GoogleMap.OnMapClickListener() {
      @Override
      public void onMapClick(LatLng latLng) {
         Log.d("LAT/LONG", latLng.toString());
         if (mMap != null) {
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng));
         }

         mLocationViewModel.mResponse.setValue(latLng);
      }
   };

   private OnMapReadyCallback mMapReadyCallback = googleMap -> {
      mMap = googleMap;
      setLocationByDeviceLocation();
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
      setLocationByDeviceLocation();
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
         }
  });

      //CurrentWeatherViewModel Observer
      mCurrentWeatherViewModel.addResponseObserver(getViewLifecycleOwner(), weather -> {
         try {
            mBinding.currentTemperatureTextView
                    .setText(weather.getString("temperature") + "°F");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
               int weatherIconId = getWeatherIcon(weather.getString("conditions"));
               mBinding.currentWeatherConditionsImageView.setImageResource(weatherIconId);
            }

         } catch (JSONException e) {
            e.printStackTrace();
         }
      });
      //HourlyWeatherForecastViewModel Observer
      mHourlyWeatherForecastViewModel.addResponseObserver(getViewLifecycleOwner(), temp ->
      {
         //get the array of hourly temperatures

         try {
            JSONArray hour1 = temp.getJSONArray("hour1");
            int hour1Temp= hour1.getInt(0);
            String hour1Conditions = hour1.getString(1);
            String hour1Time = hour1.getString(2);
            mBinding.hour1WeatherTextView.setText(hour1Time + "\n" + hour1Temp + "°F");

            JSONArray hour2 = temp.getJSONArray("hour2");
            int hour2Temp= hour2.getInt(0);
            String hour2Conditions = hour2.getString(1);
            String hour2Time = hour2.getString(2);
            mBinding.hour2WeatherTextView.setText(hour2Time + "\n" + hour2Temp + "°F");

            JSONArray hour3 = temp.getJSONArray("hour3");
            int hour3Temp= hour3.getInt(0);
            String hour3Conditions = hour3.getString(1);
            String hour3Time = hour3.getString(2);
            mBinding.hour3WeatherTextView.setText(hour3Time + "\n" + hour3Temp + "°F");

            JSONArray hour4 = temp.getJSONArray("hour4");
            int hour4Temp= hour4.getInt(0);
            String hour4Conditions = hour4.getString(1);
            String hour4Time = hour4.getString(2);
            mBinding.hour4WeatherTextView.setText(hour4Time + "\n" + hour4Temp + "°F");

            JSONArray hour5 = temp.getJSONArray("hour5");
            int hour5Temp= hour5.getInt(0);
            String hour5Conditions = hour5.getString(1);
            String hour5Time = hour5.getString(2);
            mBinding.hour5WeatherTextView.setText(hour5Time + "\n" + hour5Temp + "°F");

            JSONArray hour6 = temp.getJSONArray("hour6");
            int hour6Temp= hour6.getInt(0);
            String hour6Conditions = hour6.getString(1);
            String hour6Time = hour6.getString(2);
            mBinding.hour6WeatherTextView.setText(hour6Time + "\n" + hour6Temp + "°F");

            JSONArray hour7 = temp.getJSONArray("hour7");
            int hour7Temp= hour7.getInt(0);
            String hour7Conditions = hour7.getString(1);
            String hour7Time = hour7.getString(2);
            mBinding.hour7WeatherTextView.setText(hour7Time + "\n" + hour7Temp + "°F");

            JSONArray hour8 = temp.getJSONArray("hour8");
            int hour8Temp= hour8.getInt(0);
            String hour8Conditions = hour8.getString(1);
            String hour8Time = hour8.getString(2);
            mBinding.hour8WeatherTextView.setText(hour8Time + "\n" + hour8Temp + "°F");


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
               mBinding.hour1WeatherConditionsImageView.setImageResource(getWeatherIcon(hour1Conditions));
               mBinding.hour2WeatherConditionsImageView.setImageResource(getWeatherIcon(hour2Conditions));
               mBinding.hour3WeatherConditionsImageView.setImageResource(getWeatherIcon(hour3Conditions));
               mBinding.hour4WeatherConditionsImageView.setImageResource(getWeatherIcon(hour4Conditions));
               mBinding.hour5WeatherConditionsImageView.setImageResource(getWeatherIcon(hour5Conditions));
               mBinding.hour6WeatherConditionsImageView.setImageResource(getWeatherIcon(hour6Conditions));
               mBinding.hour7WeatherConditionsImageView.setImageResource(getWeatherIcon(hour7Conditions));
               mBinding.hour8WeatherConditionsImageView.setImageResource(getWeatherIcon(hour8Conditions));
            }
         } catch (JSONException e) {
            e.printStackTrace();
         }
      });

//      //WeeklyWeatherForecastViewModel Observer
      mWeeklyWeatherForecastViewModel.addResponseObserver(getViewLifecycleOwner(), temp ->
      {
         try {
            JSONArray day1 = temp.getJSONArray("day1");
            int day1HighTemp= day1.getInt(0);
            int day1LowTemp= day1.getInt(1);
            String day1Conditions = day1.getString(2);
            String day1Day = day1.getString(3);
            mBinding.weeklyWeatherDay1TextView.setText(day1Day + "\n" + day1HighTemp + "°F" + "/" + day1LowTemp + "°F");

            JSONArray day2 = temp.getJSONArray("day2");
            int day2HighTemp= day2.getInt(0);
            int day2LowTemp= day2.getInt(1);
            String day2Conditions = day2.getString(2);
            String day2Day = day2.getString(3);
            mBinding.weeklyWeatherDay2TextView.setText(day2Day + "\n" + day2HighTemp + "°F" + "/" + day2LowTemp + "°F");

            JSONArray day3 = temp.getJSONArray("day3");
            int day3HighTemp= day3.getInt(0);
            int day3LowTemp= day3.getInt(1);
            String day3Conditions = day3.getString(2);
            String day3Day = day3.getString(3);
            mBinding.weeklyWeatherDay3TextView.setText(day3Day + "\n" + day3HighTemp + "°F" + "/" + day3LowTemp + "°F");

            JSONArray day4 = temp.getJSONArray("day4");
            int day4HighTemp= day4.getInt(0);
            int day4LowTemp= day4.getInt(1);
            String day4Conditions = day4.getString(2);
            String day4Day = day4.getString(3);
            mBinding.weeklyWeatherDay4TextView.setText(day4Day + "\n" + day4HighTemp + "°F" + "/" + day4LowTemp + "°F");

            JSONArray day5 = temp.getJSONArray("day5");
            int day5HighTemp= day5.getInt(0);
            int day5LowTemp= day5.getInt(1);
            String day5Conditions = day5.getString(2);
            String day5Day = day5.getString(3);
            mBinding.weeklyWeatherDay5TextView.setText(day5Day + "\n" + day5HighTemp + "°F" + "/" + day5LowTemp + "°F");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

               mBinding.weeklyWeatherDay1ConditionsImageView.setImageResource(getWeatherIcon(day1Conditions));
               mBinding.weeklyWeatherDay2ConditionsImageView.setImageResource(getWeatherIcon(day2Conditions));
               mBinding.weeklyWeatherDay3ConditionsImageView.setImageResource(getWeatherIcon(day3Conditions));
               mBinding.weeklyWeatherDay4ConditionsImageView.setImageResource(getWeatherIcon(day4Conditions));
               mBinding.weeklyWeatherDay5ConditionsImageView.setImageResource(getWeatherIcon(day5Conditions));
            }











            } catch (JSONException e) {
            e.printStackTrace();
         }
      });
      SupportMapFragment mapFragment =
              (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
      //add this fragment as the OnMapReadyCallback -> See onMapReady()
      mapFragment.getMapAsync(this::onMapReady);


   }

   private int getWeatherIcon(String conditions) {
      System.out.println(conditions);
      conditions = conditions.toLowerCase();
      if (conditions.equals("clouds")) {
         return R.drawable.clouds;
      } else if (conditions.equals("rain")) {
         return R.drawable.rain;
      } else if (conditions.equals("clear")) {
         return R.drawable.clear;
      } else if (conditions.equals("snow")) {
         return R.drawable.snow;
      } else if (conditions.equals("drizzle")) {
         return R.drawable.drizzle;
      } else {
         return R.drawable.clear;
      }
   }

   public void setLocationByDeviceLocation() {
      if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
         LocationManager locationManager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);
         locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
               LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
               if (locationByDevice == 0){
                  mLocationViewModel.mResponse.setValue(latLng);
                  locationByDevice++;

               }
            }
         });
      } else {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
         }
      }
   }

}
