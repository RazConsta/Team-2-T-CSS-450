package com.example.cultivate_chat_app.ui.authorization.signin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cultivate_chat_app.R;
import com.example.cultivate_chat_app.databinding.FragmentSignInBinding;
import com.example.cultivate_chat_app.ui.authorization.register.RegisterFragmentDirections;

public class SignInFragment extends Fragment {

   private FragmentSignInBinding mBinding;
   private SignInViewModel mSignInModel;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      mSignInModel = new ViewModelProvider(getActivity())
              .get(SignInViewModel.class);
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      mBinding = FragmentSignInBinding.inflate(inflater);
      // Inflate the layout for this fragment
      return mBinding.getRoot();
   }

   @Override
   public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      mBinding = FragmentSignInBinding.bind(requireView());

      //On register button click, navigate to register
      mBinding.buttonToRegister.setOnClickListener(button ->
              Navigation.findNavController(getView()).navigate(
                      SignInFragmentDirections.actionSignInFragmentToRegisterFragment()
              ));
   }


}