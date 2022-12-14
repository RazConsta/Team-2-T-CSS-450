package com.example.cultivate_chat_app.ui.authorization.register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
// import com.example.cultivate_chat_app.R;
import com.example.cultivate_chat_app.databinding.FragmentEmailVerificationBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmailVerificationFragment extends Fragment {

    private FragmentEmailVerificationBinding mBinding;
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

    /**
     * Navigate the user back to login
     */
    private void navigateToLogin() {
        EmailVerificationFragmentDirections.ActionEmailVerificationFragmentToSignInFragment directions =
                EmailVerificationFragmentDirections.actionEmailVerificationFragmentToSignInFragment();
        directions.setEmail(mArgs.getEmail());
        directions.setPassword(mArgs.getPassword());
        Navigation.findNavController(getView()).navigate(directions);
    }
}