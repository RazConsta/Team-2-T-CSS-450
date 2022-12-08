package com.example.cultivate_chat_app.ui.weather.CurrentWeather;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cultivate_chat_app.databinding.FragmentCurrentWeatherBinding;

import org.json.JSONException;

public class CurrentWeatherFragment extends Fragment {

    private FragmentCurrentWeatherBinding mBinding;
    private CurrentWeatherViewModel mCurrentWeatherViewModel;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentWeatherViewModel = new ViewModelProvider(this).get(CurrentWeatherViewModel.class);
        mCurrentWeatherViewModel.connectGet();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentCurrentWeatherBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Obtain access to the ViewModel. If this fragment object is new, the ViewModel
        //will be re/created. Note the parameter to the ViewModelProvider constructor - this.
        mCurrentWeatherViewModel = new ViewModelProvider(getActivity()).get(CurrentWeatherViewModel.class);
        mCurrentWeatherViewModel.addResponseObserver(getViewLifecycleOwner(), temp ->
        {
            try {
                mBinding.currentTemperatureTextView
                        .setText(mCurrentWeatherViewModel.mResponse.getValue().getString("temperature") + "°F");
                mBinding.currentWeatherConditionsTextView
                        .setText(mCurrentWeatherViewModel.mResponse.getValue().getString("conditions") );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }
}