package com.example.cultivate_chat_app.ui.authorization;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cultivate_chat_app.R;
import com.example.cultivate_chat_app.databinding.FragmentSignInBinding;
import com.example.cultivate_chat_app.databinding.FragmentStartupBinding;
import com.example.cultivate_chat_app.ui.authorization.signin.SignInFragmentDirections;


public class StartupFragment extends Fragment {

   public FragmentStartupBinding mBinding;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      // Inflate the layout for this fragment
      mBinding = FragmentStartupBinding.inflate(inflater);
      // Inflate the layout for this fragment
      return mBinding.getRoot();
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);

      mBinding.signInButton.setOnClickListener(v -> {
         Navigation.findNavController(v).navigate(
                 StartupFragmentDirections.actionStartupFragmentToSignInFragment());
      });
      mBinding.signUpButton.setOnClickListener(v -> {
         Navigation.findNavController(v).navigate(
                 StartupFragmentDirections.actionStartupFragmentToRegisterFragment());
      });
   }
}