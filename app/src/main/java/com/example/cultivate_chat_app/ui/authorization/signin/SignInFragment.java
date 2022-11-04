package com.example.cultivate_chat_app.ui.authorization.signin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      // Inflate the layout for this fragment

      return inflater.inflate(R.layout.fragment_sign_in, container, false);
   }

   @Override
   public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      //Use a Lamda expression to add the OnClickListener
      mBinding = FragmentSignInBinding.bind(requireView());

      //On button click, navigate to MainActivity
      mBinding.buttonToLogin.setOnClickListener(button -> {
//         Navigation.findNavController(requireView()).navigate(
//                 SignInFragmentDirections
//                         .actionSignInFragmentToMainActivity(
//                                 generateJwt(binding.editEmail.getText().toString())
//                         ));
         //This tells the containing Activity that we are done with it.
         //It will not be added to backstack.
//         getActivity().finish();
      });
      mBinding.buttonToRegister.setOnClickListener(button -> {
         Navigation.findNavController(requireView())
                 .navigate(R.id.action_signInFragment_to_registerFragment);
      });
   }

   @Override
   public void onDestroyView() {
      super.onDestroyView();
      mBinding = null;
   }
}