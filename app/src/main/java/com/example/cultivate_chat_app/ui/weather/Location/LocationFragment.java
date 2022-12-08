//package com.example.cultivate_chat_app.ui.weather.Location;
//
//import static android.content.Intent.getIntent;
//
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.os.Build;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.core.content.ContextCompat;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.ViewModelProvider;
//
//import android.support.v4.media.MediaBrowserCompat;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.example.cultivate_chat_app.MainActivityArgs;
//import com.example.cultivate_chat_app.R;
//import com.example.cultivate_chat_app.databinding.FragmentLocationBinding;
//import com.example.cultivate_chat_app.ui.authorization.model.UserInfoViewModel;
//import com.example.cultivate_chat_app.ui.weather.CurrentWeather.CurrentWeatherFragment;
//import com.example.cultivate_chat_app.ui.weather.CurrentWeather.CurrentWeatherViewModel;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//public class LocationFragment extends Fragment {
//
//   private LocationViewModel mLocationViewModel;
//   private GoogleMap mMap;
//
//   private GoogleMap.OnMapClickListener mMapClickListener = new GoogleMap.OnMapClickListener() {
//      @Override
//      public void onMapClick(LatLng latLng) {
//         Log.d("LAT/LONG", latLng.toString());
//         if (mMap != null) {
//            mMap.clear();
//            mMap.addMarker(new MarkerOptions().position(latLng));
//         }
//
//         //prevent the map from zooming in on the marker
//         //mModel.setLocation(new Location("dummy"));
//         //update viewmodel
//         //make a location provider
//         Location location = new Location("Map");
//         location.setLatitude(latLng.latitude);
//         location.setLongitude(latLng.longitude);
//         //mLocationViewModel.setLocation(location);
//         //do not zoom in
//         //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//
//      }
//   };
//
//   private OnMapReadyCallback mMapReadyCallback = googleMap -> {
//      mMap = googleMap;
//      // Add a marker in Sydney and move the camera
//      //LatLng sydney = new LatLng(-34, 151);
//      //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//      //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//
//   };
//
//   @Override
//   public void onCreate(@Nullable Bundle savedInstanceState) {
//      super.onCreate(savedInstanceState);
//      //create a location view model instance from the factory
//      mLocationViewModel = new ViewModelProvider(this,
//              new LocationViewModel.LocationViewModelFactory(0, 0))
//                  .get(LocationViewModel.class);
//   }
//
//
//
//   public void onMapReady(GoogleMap googleMap) {
//      mMap = googleMap;
//
//      //mLocationViewModel.addLocationObserver(getViewLifecycleOwner(), location -> {
//         if(mLocationViewModel != null) {
//            googleMap.getUiSettings().setZoomControlsEnabled(true);
//            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
//                    == PackageManager.PERMISSION_GRANTED) {
//               googleMap.setMyLocationEnabled(true);
//            } else {
//               requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                       1);
//            }
//            //final LatLng c = new LatLng(location.getLatitude(), location.getLongitude());
//            //Zoom levels are from 2.0f (zoomed out) to 21.f (zoomed in)
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(c, 15.0f));
//         }
//      //});
//      mMap.setOnMapClickListener(mMapClickListener);
//   }
//
//
//
//   @Override
//   public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                            Bundle savedInstanceState) {
//      // Inflate the layout for this fragment
//      return inflater.inflate(R.layout.fragment_location, container, false);
//   }
//
//   @Override
//   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//      super.onViewCreated(view, savedInstanceState);
//      FragmentLocationBinding binding = FragmentLocationBinding.bind(getView());
//      mLocationViewModel.addLocationObserver(getViewLifecycleOwner(), location ->
//              binding.textLatLong.setText(location.toString()));
//      // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//      SupportMapFragment mapFragment =
//              (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//      //add this fragment as the OnMapReadyCallback -> See onMapReady()
//      mapFragment.getMapAsync(this::onMapReady);
//   }
//}
