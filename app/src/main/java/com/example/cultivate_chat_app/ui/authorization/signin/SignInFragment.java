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
      mBinding = FragmentSignInBinding.inflate(inflater, container, false);
      return mBinding.getRoot();
   }

   @Override
   public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      //Use a Lamda expression to add the OnClickListener
      mBinding.buttonToRegister.setOnClickListener(button -> processRegister());
   }

   @Override
   public void onDestroyView() {
      super.onDestroyView();
      mBinding = null;
   }

   private void processRegister() {
      Log.d("REGISTER", "Process to register");
      Navigation.findNavController(getView()).navigate(R.id.action_signInFragment_to_registerFragment);
   }

}