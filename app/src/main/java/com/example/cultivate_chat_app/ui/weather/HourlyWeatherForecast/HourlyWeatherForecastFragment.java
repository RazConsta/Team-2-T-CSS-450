package com.example.cultivate_chat_app.ui.weather.HourlyWeatherForecast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cultivate_chat_app.R;
import com.example.cultivate_chat_app.databinding.FragmentHourlyWeatherForecastBinding;

import org.json.JSONException;

public class HourlyWeatherForecastFragment extends Fragment {

   private FragmentHourlyWeatherForecastBinding mBinding;
   private HourlyWeatherForecastViewModel mHourlyWeatherForecastViewModel;

   @RequiresApi(api = Build.VERSION_CODES.N)
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       mHourlyWeatherForecastViewModel= new ViewModelProvider(this).get(HourlyWeatherForecastViewModel.class);
       mHourlyWeatherForecastViewModel.connectGet();
   }

   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {
      mBinding = FragmentHourlyWeatherForecastBinding.inflate(inflater);
      return mBinding.getRoot();
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
      super.onViewCreated(view, savedInstanceState);
      //Observer for the response
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
   }
}