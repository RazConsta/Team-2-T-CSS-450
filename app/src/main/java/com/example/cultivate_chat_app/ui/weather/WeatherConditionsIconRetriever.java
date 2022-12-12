//package com.example.cultivate_chat_app.ui.weather;
//
//import android.graphics.drawable.Icon;
//import android.os.Build;
//import android.os.Parcelable;
//import android.service.voice.VoiceInteractionSession;
//
//import com.example.cultivate_chat_app.R;
//
//
//public class WeatherConditionsIconRetriever {
//
//   public static Parcelable getWeatherConditionsDrawable(String conditions) {
//      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//         switch (conditions) {
//            case "clouds":
//               return Icon.createWithResource(getApplication().getApplicationContext(), R.drawable.clouds);
//            case "clear-night":
//               return Icon.createWithResource(getApplication().getApplicationContext(), R.drawable.clear_night);
//            case "rain":
//               return Icon.createWithResource(getApplication().getApplicationContext(), R.drawable.rain);
//            case "snow":
//               return Icon.createWithResource(getApplication().getApplicationContext(), R.drawable.snow);
//            case "sleet":
//               return Icon.createWithResource(getApplication().getApplicationContext(), R.drawable.sleet);
//            case "wind":
//               return Icon.createWithResource(getApplication().getApplicationContext(), R.drawable.wind);
//            case "fog":
//               return Icon.createWithResource(getApplication().getApplicationContext(), R.drawable.fog);
//            case "cloudy":
//               return Icon.createWithResource(getApplication().getApplicationContext(), R.drawable.cloudy);
//            case "partly-cloudy-day":
//               return Icon.createWithResource(getApplication().getApplicationContext(), R.drawable.partly_cloudy_day);
//            case "partly-cloudy-night":
//               return Icon.createWithResource(getApplication().getApplicationContext(), R.drawable.partly_cloudy_night);
//            default:
//               return Icon.createWithResource(getApplication().getApplicationContext(), R.drawable.clear_day);
//         }
//      }
//   }
//
//
//}
