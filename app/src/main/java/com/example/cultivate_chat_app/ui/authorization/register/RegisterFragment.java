package com.example.cultivate_chat_app.ui.authorization.register;

import static com.example.cultivate_chat_app.utils.PasswordValidator.*;
import static com.example.cultivate_chat_app.utils.PasswordValidator.checkClientPredicate;

import android.annotation.SuppressLint;
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
import com.example.cultivate_chat_app.databinding.FragmentRegisterBinding;
import com.example.cultivate_chat_app.ui.contacts.ContactsFragmentDirections;
import com.example.cultivate_chat_app.utils.PasswordValidator;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("NewApi") // suppresses AS API warnings
public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding mBinding;

    private RegisterViewModel mRegisterModel;

    private PasswordValidator mNameValidator = checkPwdLength(1);

    private PasswordValidator mEmailValidator = checkPwdLength(2)
            .and(checkExcludeWhiteSpace())
            .and(checkPwdSpecialChar("@"));

    private PasswordValidator mPassWordValidator =
            checkClientPredicate(pwd -> pwd.equals(mBinding.editRetypePassword.getText().toString()))
                    .and(checkPwdLength(7))
                    .and(checkPwdSpecialChar())
                    .and(checkExcludeWhiteSpace())
                    .and(checkPwdDigit())
                    .and(checkPwdLowerCase().or(checkPwdUpperCase()));

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRegisterModel = new ViewModelProvider(getActivity())
                .get(RegisterViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // mBinding = FragmentRegisterBinding.inflate(inflater, container, false);
        mBinding = FragmentRegisterBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.registerGobackButton.setOnClickListener(button -> navigateToLogin());
        mBinding.buttonRegister.setOnClickListener(this::attemptRegister);
        mRegisterModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeResponse);
    }

    /**
     * Navigate the user back to login
     */
    private void navigateToLogin() {
        Navigation.findNavController(getView())
                .navigate(RegisterFragmentDirections
                        .actionRegisterFragmentToSignInFragment());
    }

     private void attemptRegister(final View button) {
        validateFirst();
    }

    /* private void validateNick() {
        mNameValidator.processResult(
                mNameValidator.apply(mBinding.editNickname.getText().toString().trim()),
                this::validateFirst,
                result -> mBinding.editNickname.setError("Please enter a nickname."));
    } */

    private void validateFirst() {
        mNameValidator.processResult(
                mNameValidator.apply(mBinding.editFirst.getText().toString().trim()),
                this::validateLast,
                result -> mBinding.editFirst.setError("Please enter a first name."));
    }

    private void validateLast() {
        mNameValidator.processResult(
                mNameValidator.apply(mBinding.editLast.getText().toString().trim()),
                this::validateEmail,
                result -> mBinding.editLast.setError("Please enter a last name."));
    }

    private void validateEmail() {
        mEmailValidator.processResult(
                mEmailValidator.apply(mBinding.editEmail.getText().toString().trim()),
                this::validatePasswordsMatch,
                result -> mBinding.editEmail.setError("Please enter a valid Email address."));
    }

    private void validatePasswordsMatch() {
        PasswordValidator matchValidator =
                checkClientPredicate(
                        pwd -> pwd.equals(mBinding.editRetypePassword.getText().toString().trim()));

        mEmailValidator.processResult(
                matchValidator.apply(mBinding.editPassword.getText().toString().trim()),
                this::validatePassword,
                result -> mBinding.editPassword.setError("Passwords must match."));
    }

    private void validatePassword() {
        mPassWordValidator.processResult(
                mPassWordValidator.apply(mBinding.editPassword.getText().toString()),
                this::verifyAuthWithServer,
                result -> mBinding.editPassword.setError("Please enter a valid Password."));
    }

    private void verifyAuthWithServer() {
        mRegisterModel.connect(
                mBinding.editFirst.getText().toString(),
                mBinding.editLast.getText().toString(),
                mBinding.editEmail.getText().toString(),
                mBinding.editPassword.getText().toString());
        //This is an Asynchronous call. No statements after should rely on the
        //result of connect().
    }
//
    private void navigateToVerification() {
        RegisterFragmentDirections.ActionRegisterFragmentToEmailVerificationFragment directions =
                RegisterFragmentDirections.actionRegisterFragmentToEmailVerificationFragment(
                        mBinding.editEmail.getText().toString(),
                        mBinding.editPassword.getText().toString());

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
                    mBinding.editEmail.setError(
                            "Error Authenticating: " +
                                    response.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            } else {
                navigateToVerification();
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }
}