package com.example.cultivate_chat_app.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cultivate_chat_app.databinding.FragmentAboutUsBinding;
import com.example.cultivate_chat_app.databinding.FragmentSettingsBinding;
import com.example.cultivate_chat_app.databinding.FragmentSignInBinding;



public class AboutUsFragment extends Fragment {

    private FragmentAboutUsBinding mBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentAboutUsBinding.inflate(inflater);
        return mBinding.getRoot();

    }
}
