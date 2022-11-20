package com.example.cultivate_chat_app.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.auth0.android.jwt.JWT;
import com.example.cultivate_chat_app.R;
import com.example.cultivate_chat_app.databinding.FragmentHomeBinding;
import com.example.cultivate_chat_app.ui.authorization.model.UserInfoViewModel;
import com.example.cultivate_chat_app.ui.settings.PasswordViewModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private FragmentHomeBinding mBinding;
    private UserInfoViewModel mUserModel;
    private HomeViewModel mHomeViewModel;
    private String mNickname;

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
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
        JWT jwt = new JWT( mUserModel.getJwt());
        mBinding.welcomeHome.setText("Welcome, " + mUserModel.getEmail() + "!");
    }

//    public void getNickname(final String email) {
//        String url = "https://cultivate-app-web-service.herokuapp.com/getNickname";
//        JSONObject body = new JSONObject();
//        try {
//            body.put("email", email);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Request request = new JsonObjectRequest(
//                Request.Method.GET,
//                url,
//                body,
//                this::handleResult,
//                this::handleError);
//        request.setRetryPolicy(new DefaultRetryPolicy(
//                10_000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        //Instantiate the RequestQueue and add the request to the queue
//        Volley.newRequestQueue(getActivity())
//                .add(request);
//    }
//
//    public void handleResult(final JSONObject jsonResponse) {
//        System.out.println("entered handle nickname");
//        try {
//            // System.out.println("NICKNAME JSON RESPONSE" + jsonResponse);
//            // mNickname = jsonResponse.getString("nickname");
//            System.out.println("PRINTING JSON" + jsonResponse);
//            mBinding.welcomeHome.setText("Welcome, " + jsonResponse.getString("nickname") + "!");
//        } catch (JSONException e) {
//            System.out.println("could not handle nickname JSON response");
//        }
//    }
//
//    public void handleError(final VolleyError error) {
//        System.out.println("HANDLE NICKNAME ERROR");
//    }

}