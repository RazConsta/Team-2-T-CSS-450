package com.example.cultivate_chat_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

   private AppBarConfiguration mAppBarConfiguration;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      // Make sure the new statements go BELOW setContentView
      BottomNavigationView navView = findViewById(R.id.nav_view);
      // Passing each menu ID as a set of Ids because each
      // menu should be considered as top level destinations.
      mAppBarConfiguration = new AppBarConfiguration.Builder(
              R.id.homeFragment, R.id.chatsFragment, R.id.contactsFragment, R.id.weatherFragment, R.id.registerFragment)
              .build();
      NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
      NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
      NavigationUI.setupWithNavController(navView, navController);
   }
}