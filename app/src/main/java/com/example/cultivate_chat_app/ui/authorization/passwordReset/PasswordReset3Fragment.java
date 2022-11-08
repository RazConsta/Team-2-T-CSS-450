package com.example.cultivate_chat_app.ui.authorization.passwordReset;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cultivate_chat_app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PasswordReset3Fragment} factory method to
 * create an instance of this fragment.
 */
public class PasswordReset3Fragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_password_reset_1, container, false);
    }

}