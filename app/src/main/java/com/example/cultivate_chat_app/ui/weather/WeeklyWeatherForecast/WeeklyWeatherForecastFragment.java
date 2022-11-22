package com.example.cultivate_chat_app.ui.weather.WeeklyWeatherForecast;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cultivate_chat_app.R;

public class WeeklyWeatherForecastFragment extends Fragment {

   private WeeklyWeatherForecastViewModel mViewModel;

   public static WeeklyWeatherForecastFragment newInstance() {
      return new WeeklyWeatherForecastFragment();
   }

   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_weekly_weather_forecast, container, false);
   }

   @Override
   public void onActivityCreated(@Nullable Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);
      mViewModel = new ViewModelProvider(this).get(WeeklyWeatherForecastViewModel.class);
      // TODO: Use the ViewModel
   }

}