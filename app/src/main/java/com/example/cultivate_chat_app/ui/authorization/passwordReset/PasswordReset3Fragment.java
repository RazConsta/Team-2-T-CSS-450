package com.example.cultivate_chat_app.ui.authorization.passwordReset;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cultivate_chat_app.R;
import com.example.cultivate_chat_app.databinding.FragmentPasswordReset2Binding;
import com.example.cultivate_chat_app.databinding.FragmentPasswordReset3Binding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PasswordReset3Fragment} factory method to
 * create an instance of this fragment.
 */
public class PasswordReset3Fragment extends Fragment {

    private FragmentPasswordReset3Binding mBinding;
    private PasswordReset3FragmentArgs mArgs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // mBinding = FragmentRegisterBinding.inflate(inflater, container, false);
        mBinding = FragmentPasswordReset3Binding.inflate(inflater, container, false);
        mArgs = PasswordReset3FragmentArgs.fromBundle(getArguments());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.buttonVerified.setOnClickListener(button -> navigateToNext());
    }

    private void navigateToNext() {
        PasswordReset3FragmentDirections.ActionPasswordReset3FragmentToSignInFragment2 directions =
                PasswordReset3FragmentDirections.actionPasswordReset3FragmentToSignInFragment2();
        directions.setEmail(mArgs.getEmail());
        directions.setPassword(mBinding.newPassword.getText().toString());
        Navigation.findNavController(getView()).navigate(directions);
    }

}