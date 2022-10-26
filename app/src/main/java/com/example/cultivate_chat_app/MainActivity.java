package com.example.cultivate_chat_app;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      Log.i("MainActivity", "The onCreate method has ran.");
   }
}