//package com.example.cultivate_chat_app.ui.weather;
//
//import android.app.Application;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.AndroidViewModel;
//import androidx.lifecycle.LifecycleOwner;
//import androidx.lifecycle.MutableLiveData;
//import androidx.lifecycle.Observer;
//
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.Request;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.function.IntFunction;
//
//public class WeatherForecastViewModel extends AndroidViewModel {
//
//   private MutableLiveData<List<Weather>> mWeatherForecast;
//
//   public WeatherForecastViewModel(@NonNull Application application) {
//      super(application);
//      mWeatherForecast = new MutableLiveData<>();
//      mWeatherForecast.setValue(new ArrayList<>());
//   }
//   public void addWeatherObserver(@NonNull LifecycleOwner owner,
//                                   @NonNull Observer<? super List<Weather>> observer) {
//      mWeatherForecast.observe(owner, observer);
//   }
//
//   private void handleError(final VolleyError error) {
//      Log.e("CONNECTION ERROR",  " WeatherForecastViewModel GET"); //error.getLocalizedMessage());
//      throw new IllegalStateException();//error.getMessage());
//   }
//
//   private void handleResult(final JSONObject result) {
//      IntFunction<String> getString =
//              getApplication().getResources()::getString;
//      try {
//         JSONObject root = result;
//         if (root.has(getString.apply(R.string.keys_json_blogs_response))) {
//            JSONObject response =
//                    root.getJSONObject(getString.apply(
//                            R.string.keys_json_blogs_response));
//            if (response.has(getString.apply(R.string.keys_json_blogs_data))) {
//               JSONArray data = response.getJSONArray(
//                       getString.apply(R.string.keys_json_blogs_data));
//               for(int i = 0; i < data.length(); i++) {
//                  JSONObject jsonBlog = data.getJSONObject(i);
//                  BlogPost post = new BlogPost.Builder(
//                          jsonBlog.getString(
//                                  getString.apply(
//                                          R.string.keys_json_blogs_pubdate)),
//                          jsonBlog.getString(
//                                  getString.apply(
//                                          R.string.keys_json_blogs_title)))
//                          .addTeaser(jsonBlog.getString(
//                                  getString.apply(
//                                          R.string.keys_json_blogs_teaser)))
//                          .addUrl(jsonBlog.getString(
//                                  getString.apply(
//                                          R.string.keys_json_blogs_url)))
//                          .build();
//                  if (!mBlogList.getValue().contains(post)) {
//                     mBlogList.getValue().add(post);
//                  }
//               }
//            } else {
//               Log.e("ERROR!", "No data array");
//            }
//         } else {
//            Log.e("ERROR!", "No response");
//         }
//      } catch (JSONException e) {
//         e.printStackTrace();
//         Log.e("ERROR!", e.getMessage());
//      }
//      mBlogList.setValue(mBlogList.getValue());
//   }
//
//   public void connectGet() {
//      String url =
//              "https://cfb3-tcss450-labs-2021sp.herokuapp.com/phish/blog/get";
//      Request request = new JsonObjectRequest(
//              Request.Method.GET,
//              url,
//              null, //no body for this get request
//              this::handleResult,
//              this::handleError) {
//         @Override
//         public Map<String, String> getHeaders() {
//            Map<String, String> headers = new HashMap<>();
//            // add headers <key,value>
//            headers.put("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImNmYjMxQGZha2UuZW1haWwuY29tIiwibWVtYmVyaWQiOjI4OCwiaWF0IjoxNjY2NTU3ODQ2LCJleHAiOjE2NzUxOTc4NDZ9.f7IVbAzJbX72vjRUbadIksMzNm6xkPi1l_R_C4O9zb4");
//            return headers;
//         }
//      };
//      request.setRetryPolicy(new DefaultRetryPolicy(
//              10_000,
//              DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//              DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//      //Instantiate the RequestQueue and add the request to the queue
//      Volley.newRequestQueue(getApplication().getApplicationContext())
//              .add(request);
//   }
//}
