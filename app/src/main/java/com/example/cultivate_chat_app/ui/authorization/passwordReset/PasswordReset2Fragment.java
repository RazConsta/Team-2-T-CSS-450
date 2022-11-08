package com.example.cultivate_chat_app.ui.authorization.passwordReset;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cultivate_chat_app.databinding.FragmentPasswordReset2Binding;
import com.example.cultivate_chat_app.ui.authorization.register.EmailVerificationFragmentArgs;
import com.example.cultivate_chat_app.ui.authorization.register.EmailVerificationFragmentDirections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PasswordReset2Fragment} factory method to
 * create an instance of this fragment.
 */
public class PasswordReset2Fragment extends Fragment {

    private FragmentPasswordReset2Binding mBinding;
    private PasswordReset2FragmentArgs mArgs;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentPasswordResetVerification2Binding(inflater, container, false);
        mArgs = EmailVerificationFragmentArgs.fromBundle(getArguments());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.buttonVerified.setOnClickListener(button -> navigateToLogin());
    }

    /**
     * Navigate the user back to login
     */
    private void navigateToLogin() {
        EmailVerificationFragmentDirections.ActionEmailVerificationFragmentToSignInFragment directions =
                EmailVerificationFragmentDirections.actionEmailVerificationFragmentToSignInFragment();
        directions.setEmail(mArgs.getEmail());
        directions.setPassword("");
        Navigation.findNavController(getView()).navigate(directions);
    }
}