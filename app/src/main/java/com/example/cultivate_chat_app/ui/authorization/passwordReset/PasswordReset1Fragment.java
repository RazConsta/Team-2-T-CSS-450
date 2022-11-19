package com.example.cultivate_chat_app.ui.authorization.passwordReset;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cultivate_chat_app.R;
import com.example.cultivate_chat_app.databinding.FragmentPasswordReset1Binding;
import com.example.cultivate_chat_app.ui.authorization.register.RegisterFragmentDirections;
// import com.example.cultivate_chat_app.ui.authorization.register.EmailVerificationFragmentDirections;
// import com.example.cultivate_chat_app.ui.authorization.register.RegisterViewModel;

import org.json.JSONException;
import org.json.JSONObject;

public class PasswordReset1Fragment extends Fragment {

   private FragmentPasswordReset1Binding mBinding;

   private PasswordResetViewModel mPasswordResetModel;

   @Override
   public void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      mPasswordResetModel = new ViewModelProvider(getActivity())
              .get(PasswordResetViewModel.class);
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      // Inflate the layout for this fragment
      mBinding = FragmentPasswordReset1Binding.inflate(inflater, container, false);
      return mBinding.getRoot();
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);

      mBinding.buttonToResetPassword.setOnClickListener(this::attemptSend);
      mBinding.forgotPasswordGobackButton.setOnClickListener(button -> navigateToLogin());
      mPasswordResetModel.addResponseObserver(getViewLifecycleOwner(),
              this::observeResponse);
   }

   /**
    * Navigate the user back to login
    */
   private void navigateToLogin() {
      Navigation.findNavController(getView())
              .navigate(PasswordReset1FragmentDirections
                      .actionPasswordReset1FragmentToSignInFragment());
   }

   private void attemptSend(final View button) {
      verifyEmailWithServer();
   }

   private void verifyEmailWithServer() {
      mPasswordResetModel.verify(mBinding.editEmail.getText().toString());
      //This is an Asynchronous call. No statements after should rely on the
      //result of connect().
   }

   /**
    * Navigate the user back to login
    */
   private void navigateToNext() {
      PasswordReset1FragmentDirections.ActionPasswordReset1FragmentToPasswordReset2Fragment directions =
              PasswordReset1FragmentDirections.actionPasswordReset1FragmentToPasswordReset2Fragment(mBinding.editEmail.getText().toString());
      directions.setEmail(mBinding.editEmail.getText().toString());
      Navigation.findNavController(getView()).navigate(directions);
   }

   /**
    * An observer on the HTTP Response from the web server. This observer should be
    * attached to SignInViewModel.
    *
    * @param response the Response from the server
    */
   private void observeResponse(final JSONObject response) {
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
            navigateToNext();
         }
      } else {
         Log.d("JSON Response", "No Response");
      }
   }
}