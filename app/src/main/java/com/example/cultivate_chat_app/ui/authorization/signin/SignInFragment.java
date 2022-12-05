package com.example.cultivate_chat_app.ui.authorization.signin;

import static com.example.cultivate_chat_app.utils.PasswordValidator.checkExcludeWhiteSpace;
import static com.example.cultivate_chat_app.utils.PasswordValidator.checkPwdLength;
import static com.example.cultivate_chat_app.utils.PasswordValidator.checkPwdSpecialChar;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import static com.example.cultivate_chat_app.utils.PasswordValidator.*;
import static com.example.cultivate_chat_app.utils.ThemeManager.getThemeColor;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.auth0.android.jwt.JWT;
import com.example.cultivate_chat_app.R;
import com.example.cultivate_chat_app.databinding.FragmentSignInBinding;
import com.example.cultivate_chat_app.ui.authorization.model.PushyTokenViewModel;
import com.example.cultivate_chat_app.ui.authorization.model.UserInfoViewModel;
import com.example.cultivate_chat_app.utils.PasswordValidator;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint("NewApi")
public class SignInFragment extends Fragment {

   private FragmentSignInBinding mBinding;
   private SignInViewModel mSignInModel;
   private UserInfoViewModel mUserViewModel;
   private PushyTokenViewModel mPushyTokenViewModel;

   private final PasswordValidator mEmailValidator = checkPwdLength(2)
           .and(checkExcludeWhiteSpace())
           .and(checkPwdSpecialChar("@"));

   private final PasswordValidator mPassWordValidator = checkPwdLength(1)
           .and(checkExcludeWhiteSpace());

   public SignInFragment() {
      // Required empty public constructor
   }

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      mSignInModel = new ViewModelProvider(requireActivity())
              .get(SignInViewModel.class);
      //gain a reference to the PushyTokenViewModel
      mPushyTokenViewModel = new ViewModelProvider(requireActivity())
              .get(PushyTokenViewModel.class);
   }

   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      mBinding = FragmentSignInBinding.inflate(inflater);
      // Inflate the layout for this fragment
      return mBinding.getRoot();
   }

   @Override
   public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      mBinding = FragmentSignInBinding.bind(requireView());

//      if (getThemeColor(this.getActivity()).equals("green")) {
//         mBinding.coloredRectangle.setBackgroundColor(getResources().getColor(R.color.green));
//         mBinding.editEmail.setBackgroundTintList(getResources().getColorStateList(R.color.green));
//         mBinding.editPassword.setBackgroundTintList(getResources().getColorStateList(R.color.green));
//         mBinding.checkBox.setButtonTintList(getResources().getColorStateList(R.color.green));
//      } else {
//         mBinding.coloredRectangle.setBackgroundColor(getResources().getColor(R.color.yellow));
//         mBinding.editEmail.setBackgroundTintList(getResources().getColorStateList(R.color.yellow));
//         mBinding.editPassword.setBackgroundTintList(getResources().getColorStateList(R.color.yellow));
//         mBinding.checkBox.setButtonTintList(getResources().getColorStateList(R.color.yellow));
//      }

      //On "Forgot passsword?" button click, navigate to PasswordResetFragment
      mBinding.forgotPassword.setOnClickListener(button ->
              Navigation.findNavController(requireView()).navigate(
                      SignInFragmentDirections.actionSignInFragmentToPasswordResetFragment()
              ));

      //On sign in button click, verify account and then navigate to main activity
      mBinding.buttonToLogin.setOnClickListener(this::attemptSignIn);

      mSignInModel.addResponseObserver(
              getViewLifecycleOwner(),
              this::observeSignInResponse);

      SignInFragmentArgs args = SignInFragmentArgs.fromBundle(getArguments());
      mBinding.editEmail.setText(args.getEmail().equals("default") ? "" : args.getEmail());
      mBinding.editPassword.setText(args.getPassword().equals("default") ? "" : args.getPassword());
      //don't allow sign in until pushy token retrieved
      mPushyTokenViewModel.addTokenObserver(getViewLifecycleOwner(), token ->
              mBinding.buttonToLogin.setEnabled(!token.isEmpty()));
      //add an observer to the PushyViewModel response
      mPushyTokenViewModel.addResponseObserver(
              getViewLifecycleOwner(),
              this::observePushyPutResponse);
   }

   private void attemptSignIn(final View button) {
      validateEmail();
   }

   private void validateEmail() {
      mEmailValidator.processResult(
              mEmailValidator.apply(mBinding.editEmail.getText().toString().trim()),
              this::validatePassword,
              result -> mBinding.editEmail.setError("Invalid email"));
   }

   private void validatePassword() {
      String password = mBinding.editPassword.getText().toString();
      if (password.equals("")) {
         mBinding.editPassword.setError("Enter password");
      } else {
            mPassWordValidator.processResult(
                    mPassWordValidator.apply(mBinding.editPassword.getText().toString()),
                    this::verifyAuthWithServer,
                    result -> mBinding.editPassword.setError("Invalid password"));

      }
      mBinding.editPassword.requestFocus();

   }

   private void verifyAuthWithServer() {
      mSignInModel.connect(
              mBinding.editEmail.getText().toString(),
              mBinding.editPassword.getText().toString());
      //This is an Asynchronous call. No statements after should rely on the
      //result of connect().
   }

   /*
    * Helper to abstract the request to send the pushy token to the web service.
    */
   private void sendPushyToken() {
      mPushyTokenViewModel.sendTokenToWebservice(mUserViewModel.getJwt());
   }

   @Override
   public void onStart() {
      super.onStart();
      SharedPreferences prefs =
              getActivity().getSharedPreferences(
                      getString(R.string.keys_shared_prefs),
                      Context.MODE_PRIVATE);
      if (prefs.contains(getString(R.string.keys_prefs_jwt))) {
         String token = prefs.getString(getString(R.string.keys_prefs_jwt), "");
         JWT jwt = new JWT(token);
         // Check to see if the web token is still valid or not. To make a JWT expire after a
         // longer or shorter time period, change the expiration time when the JWT is
         // created on the web service.
         if(!jwt.isExpired(0)) {
            String email = jwt.getClaim("email").asString();
            int id = Integer.valueOf(jwt.getClaim("memberid").asString());
            navigateToSuccess(email, token, "firstName", "lastName", "nickName", id);
            return;
         }
      }
   }

   /**
    * Helper to abstract the navigation to the Activity past Authentication.
    * @param email users email
    * @param jwt the JSON Web Token supplied by the server
    */
   private void navigateToSuccess(String email, String jwt, String first, String last, String nick, int id) {

      if (mBinding.checkBox.isChecked()) {
         SharedPreferences prefs =
                 getActivity().getSharedPreferences(
                         getString(R.string.keys_shared_prefs),
                         Context.MODE_PRIVATE);
         //Store the credentials in SharedPrefs
         prefs.edit().putString(getString(R.string.keys_prefs_jwt), jwt).apply();
         if (mUserViewModel != null) {
            prefs.edit().putString("nickname", mUserViewModel.getNick()).apply();
         }
      }

      Navigation.findNavController(requireView())
              .navigate(SignInFragmentDirections
                      .actionSignInFragmentToMainActivity(email, jwt, first, last, nick, id));

//      //Remove THIS activity from the Task list. Pops off the backstack
//      getActivity().finish();
   }

   /**
    *  An observer on the HTTP Response from the web server. This observer should be
    *  attached to PushyTokenViewModel.
    * @param response the Response from the server
    */
   private void observePushyPutResponse(final JSONObject response) {
      if (response.length() > 0) {
         if (response.has("code")) {
            try {
               mBinding.editEmail.setError(
                       "Error Authenticating: " +
                               response.getJSONObject("data").getString("message"));
            } catch (JSONException e) {
               Log.e("JSON Parse Error", e.getMessage());
            }
         } else {
            navigateToSuccess(
                    mBinding.editEmail.getText().toString(),
                    mUserViewModel.getJwt(), mUserViewModel.getFirst(), mUserViewModel.getLast(), mUserViewModel.getNick(), mUserViewModel.getId()
            );
         }
      }
   }

   /**
    * An observer on the HTTP Response from the web server. This observer should be
    * attached to SignInViewModel.
    *
    * @param response the Response from the server
    */
   private void observeSignInResponse(final JSONObject response) {
      if (response.length() > 0) {
         if (response.has("code")) {
            try {
               mBinding.editEmail.setError(
                       "Error Authenticating: " +
                               response.getJSONObject("data").getString("message"));
            } catch (JSONException e) {
               Log.e("JSON Parse Error", e.getMessage());
            }
         } else {
            try {
               mUserViewModel = new ViewModelProvider(requireActivity(),
                       new UserInfoViewModel.UserInfoViewModelFactory(
                               mBinding.editEmail.getText().toString(),
                               response.getString("token"),
                               response.getString(("firstname")),
                               response.getString("lastname"),
                               response.getString("nickname"),
                               response.getInt("memberid")
                       )).get(UserInfoViewModel.class);
               sendPushyToken();
            } catch (JSONException e) {
               Log.e("JSON Parse Error", e.getMessage());
            }
         }
      } else {
         Log.d("JSON Response", "No Response");
      }
   }
}