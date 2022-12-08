//package com.example.cultivate_chat_app.ui.weather.WeeklyWeatherForecast;
//
//import androidx.annotation.RequiresApi;
//import androidx.lifecycle.ViewModelProvider;
//
//import android.os.Build;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.example.cultivate_chat_app.R;
//import com.example.cultivate_chat_app.databinding.FragmentCurrentWeatherBinding;
//import com.example.cultivate_chat_app.databinding.FragmentWeeklyWeatherForecastBinding;
//
//import org.json.JSONException;
//
//public class WeeklyWeatherForecastFragment extends Fragment {
//
//   private FragmentWeeklyWeatherForecastBinding mBinding;
//   private WeeklyWeatherForecastViewModel mWeeklyWeatherForecastViewModel;
//
//
//   @RequiresApi(api = Build.VERSION_CODES.N)
//   @Override
//   public void onCreate(Bundle savedInstanceState) {
//       super.onCreate(savedInstanceState);
//       mWeeklyWeatherForecastViewModel = new ViewModelProvider(this).get(WeeklyWeatherForecastViewModel.class);
//       mWeeklyWeatherForecastViewModel.connectGet();
//   }
//   @Override
//   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                            @Nullable Bundle savedInstanceState) {
//
//      mBinding = FragmentWeeklyWeatherForecastBinding.inflate(inflater);
//      return mBinding.getRoot();
//   }
//
//   @Override
//   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//      super.onViewCreated(view, savedInstanceState);
//      //Observer for the response
//      mWeeklyWeatherForecastViewModel.addResponseObserver(getViewLifecycleOwner(), temp ->
//      {
//         try {
//            mBinding.day1WeatherTextView.
//                    setText(mWeeklyWeatherForecastViewModel.mResponse.getValue().getString("day1"));
//            mBinding.day2WeatherTextView.
//                     setText(mWeeklyWeatherForecastViewModel.mResponse.getValue().getString("day2"));
//            mBinding.day3WeatherTextView.
//                     setText(mWeeklyWeatherForecastViewModel.mResponse.getValue().getString("day3"));
//            mBinding.day4WeatherTextView.
//                     setText(mWeeklyWeatherForecastViewModel.mResponse.getValue().getString("day4"));
//            mBinding.day5WeatherTextView.
//                     setText(mWeeklyWeatherForecastViewModel.mResponse.getValue().getString("day5"));
//         } catch (JSONException e) {
//            e.printStackTrace();
//         }
//      });
//   }
//}