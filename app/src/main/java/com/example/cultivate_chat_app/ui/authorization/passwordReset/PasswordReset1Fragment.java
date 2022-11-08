package com.example.cultivate_chat_app.ui.authorization.passwordReset;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cultivate_chat_app.R;
import com.example.cultivate_chat_app.databinding.FragmentPasswordReset1Binding;
import com.example.cultivate_chat_app.ui.authorization.register.EmailVerificationFragmentDirections;
import com.example.cultivate_chat_app.ui.authorization.register.RegisterViewModel;

public class PasswordReset1Fragment extends Fragment {

   private FragmentPasswordReset1Binding mBinding;

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
      mBinding.buttonToResetPassword.setOnClickListener(button -> navigateToNext());
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

   /*private FragmentEmailVerificationBinding mBinding;
   private EmailVerificationFragmentArgs mArgs;

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      // Inflate the layout for this fragment
      mBinding = FragmentEmailVerificationBinding.inflate(inflater, container, false);
      mArgs = EmailVerificationFragmentArgs.fromBundle(getArguments());
      return mBinding.getRoot();
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      mBinding.buttonVerified.setOnClickListener(button -> navigateToLogin());
   }

   private void navigateToLogin() {
      EmailVerificationFragmentDirections.ActionEmailVerificationFragmentToSignInFragment directions =
              EmailVerificationFragmentDirections.actionEmailVerificationFragmentToSignInFragment();
      directions.setEmail(mArgs.getEmail());
      directions.setPassword(mArgs.getPassword());
      Navigation.findNavController(getView()).navigate(directions);
   } */
}