package com.example.cultivate_chat_app.ui.authorization.register;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cultivate_chat_app.R;
import com.example.cultivate_chat_app.databinding.FragmentEmailVerificationBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmailVerificationFragment extends Fragment {

    private FragmentEmailVerificationBinding mBinding;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentEmailVerificationBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }
}