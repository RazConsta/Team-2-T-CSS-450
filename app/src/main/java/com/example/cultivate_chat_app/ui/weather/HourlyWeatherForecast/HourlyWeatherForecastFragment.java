package com.example.cultivate_chat_app.ui.weather.HourlyWeatherForecast;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cultivate_chat_app.R;

public class HourlyWeatherForecastFragment extends Fragment {

   private HourlyWeatherForecastViewModel mViewModel;

   public static HourlyWeatherForecastFragment newInstance() {
      return new HourlyWeatherForecastFragment();
   }

   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_hourly_weather_forecast, container, false);
   }

   @Override
   public void onActivityCreated(@Nullable Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);
      mViewModel = new ViewModelProvider(this).get(HourlyWeatherForecastViewModel.class);
      // TODO: Use the ViewModel
   }

}