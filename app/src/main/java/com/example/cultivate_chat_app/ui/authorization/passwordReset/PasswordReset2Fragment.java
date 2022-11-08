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
import com.example.cultivate_chat_app.databinding.FragmentEmailVerificationBinding;
import com.example.cultivate_chat_app.databinding.FragmentPasswordReset2Binding;
import com.example.cultivate_chat_app.databinding.FragmentRegisterBinding;
import com.example.cultivate_chat_app.ui.authorization.register.EmailVerificationFragmentArgs;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PasswordReset2Fragment} factory method to
 * create an instance of this fragment.
 */
public class PasswordReset2Fragment extends Fragment {

    private FragmentPasswordReset2Binding mBinding;
    private PasswordReset2FragmentArgs mArgs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // mBinding = FragmentRegisterBinding.inflate(inflater, container, false);
        mBinding = FragmentPasswordReset2Binding.inflate(inflater, container, false);
        mArgs = PasswordReset2FragmentArgs.fromBundle(getArguments());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.buttonToVerify.setOnClickListener(button -> navigateToNext());
    }

    private void navigateToNext() {
        PasswordReset2FragmentDirections.ActionPasswordReset2FragmentToPasswordReset3Fragment directions =
                PasswordReset2FragmentDirections.actionPasswordReset2FragmentToPasswordReset3Fragment(mArgs.getEmail());
        Navigation.findNavController(getView()).navigate(directions);
    }
}