package com.example.cultivate_chat_app.ui.weather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cultivate_chat_app.R;
import com.example.cultivate_chat_app.databinding.FragmentWeatherParentBinding;
import com.example.cultivate_chat_app.ui.weather.CurrentWeather.CurrentWeatherFragment;
import com.example.cultivate_chat_app.ui.weather.HourlyWeatherForecast.HourlyWeatherForecastFragment;
import com.example.cultivate_chat_app.ui.weather.Location.LocationFragment;
import com.example.cultivate_chat_app.ui.weather.WeeklyWeatherForecast.WeeklyWeatherForecastFragment;

public class WeatherParentFragment extends Fragment {
   public WeatherParentFragment mBinding;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      FragmentManager fragmentManager = getParentFragmentManager();
      fragmentManager.beginTransaction()
              .replace(R.id.currentWeatherFrameLayout, CurrentWeatherFragment.class, null)
              .setReorderingAllowed(true)
              .addToBackStack("currentWeather")
              .commit();

      fragmentManager.beginTransaction()
              .replace(R.id.weeklyWeatherFrameLayout, WeeklyWeatherForecastFragment.class, null)
              .setReorderingAllowed(true)
              .addToBackStack("weeklyWeatherForecast")
              .commit();

      fragmentManager.beginTransaction()
              .replace(R.id.hourlyWeatherFrameLayout, HourlyWeatherForecastFragment.class, null)
              .setReorderingAllowed(true)
              .addToBackStack("hourlyWeatherForecast")
              .commit();

      Fragment fragment=new LocationFragment();
      fragmentManager.beginTransaction()
              .replace(R.id.locationFrameLayout, fragment, null)
              .setReorderingAllowed(true)
              .addToBackStack("location")
              .commit();
   }
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_weather_parent, container, false);
   }
}