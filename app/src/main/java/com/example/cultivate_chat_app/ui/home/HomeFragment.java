package com.example.cultivate_chat_app.ui.home;

import static com.example.cultivate_chat_app.utils.ThemeManager.getThemeColor;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cultivate_chat_app.R;
import com.example.cultivate_chat_app.databinding.FragmentHomeBinding;
import com.example.cultivate_chat_app.ui.authorization.model.NewFriendRequestCountViewModel;
import com.example.cultivate_chat_app.ui.authorization.model.UserInfoViewModel;
import com.example.cultivate_chat_app.ui.weather.CurrentWeather.CurrentWeatherViewModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.badge.BadgeDrawable;

import org.json.JSONException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private FragmentHomeBinding mBinding;
    private UserInfoViewModel mUserModel;
    private HomeViewModel mHomeViewModel;
    private CurrentWeatherViewModel mCurrentWeatherViewModel;
    private NewFriendRequestCountViewModel mNewFriendModel;

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewFriendModel = new ViewModelProvider(this).get(NewFriendRequestCountViewModel.class);
        mHomeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        mCurrentWeatherViewModel = new ViewModelProvider(this).get(CurrentWeatherViewModel.class);
        setLocationByDeviceLocation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_home, container, false);
        mBinding = FragmentHomeBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mUserModel = provider.get(UserInfoViewModel.class);
        if (mUserModel.getIncomingRequests() > 0) {
            mBinding.roundedRectangle2.setVisibility(View.VISIBLE);
            mBinding.newRequest.setText("Incoming friend requests: " + mUserModel.getIncomingRequests());
        } else {
            mBinding.roundedRectangle2.setVisibility(View.GONE);
        }
        // JWT jwt = new JWT( mUserModel.getJwt());

        SharedPreferences prefs =
                getActivity().getSharedPreferences(
                        getString(R.string.keys_shared_prefs),
                        Context.MODE_PRIVATE);


        if (prefs.contains(getString(R.string.keys_prefs_jwt))) {
            String token = prefs.getString(getString(R.string.keys_prefs_jwt), "");
        }

        mBinding.welcomeHome.setText("Welcome, " + prefs.getString("nickname", "") + "!");


        // mBinding.welcomeHome.setText("Welcome, " + mUserModel.getNick() + "!");

        if (getThemeColor(getActivity()).equals("green")) {
            mBinding.roundedRectangle.setBackgroundResource(R.drawable.green_rounded_rectangle);
            mBinding.roundedRectangle2.setBackgroundResource(R.drawable.green_rounded_rectangle);
        } else {
            mBinding.roundedRectangle.setBackgroundResource(R.drawable.alternate_rounded_rectangle);
            mBinding.roundedRectangle2.setBackgroundResource(R.drawable.alternate_rounded_rectangle);
        }


        mCurrentWeatherViewModel.addResponseObserver(getViewLifecycleOwner(), temp ->
        {
            try {
                mBinding.homeTempTextView
                        .setText(mCurrentWeatherViewModel.mResponse.getValue().getString("temperature") + "°F");
                mBinding.homeConditionsTextView
                        .setText(mCurrentWeatherViewModel.mResponse.getValue().getString("conditions") );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }


    public void setLocationByDeviceLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        mCurrentWeatherViewModel.connectPost(latLng);
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