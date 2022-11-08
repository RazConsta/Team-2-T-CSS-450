package com.example.cultivate_chat_app.ui.authorization.passwordReset;

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
import com.example.cultivate_chat_app.databinding.FragmentPasswordReset2Binding;
import com.example.cultivate_chat_app.databinding.FragmentPasswordReset3Binding;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PasswordReset3Fragment} factory method to
 * create an instance of this fragment.
 */
public class PasswordReset3Fragment extends Fragment {

    private FragmentPasswordReset3Binding mBinding;
    private PasswordReset3FragmentArgs mArgs;
    private PasswordResetViewModel2 mPasswordResetModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPasswordResetModel = new ViewModelProvider(getActivity())
                .get(PasswordResetViewModel2.class);
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
        mBinding.buttonVerified.setOnClickListener(this::attemptReset);
        mPasswordResetModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeResponse);
    }

    private void attemptReset(final View button) {
        resetPasswordWithServer();
    }

    private void resetPasswordWithServer() {
        mPasswordResetModel.reset(mArgs.getEmail(), mBinding.newPassword.getText().toString());
        //This is an Asynchronous call. No statements after should rely on the
        //result of connect().
    }

    private void navigateToNext() {
        PasswordReset3FragmentDirections.ActionPasswordReset3FragmentToSignInFragment2 directions =
                PasswordReset3FragmentDirections.actionPasswordReset3FragmentToSignInFragment2();
        directions.setEmail(mArgs.getEmail());
        directions.setPassword(mBinding.newPassword.getText().toString());
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
                    mBinding.newPassword.setError(
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