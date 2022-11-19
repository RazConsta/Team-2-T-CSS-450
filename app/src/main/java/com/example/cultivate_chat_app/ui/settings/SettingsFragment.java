package com.example.cultivate_chat_app.ui.settings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cultivate_chat_app.MainActivity;
import com.example.cultivate_chat_app.R;
import com.example.cultivate_chat_app.databinding.FragmentSettingsBinding;
import com.example.cultivate_chat_app.databinding.FragmentSignInBinding;
import com.example.cultivate_chat_app.ui.authorization.model.UserInfoViewModel;
import com.example.cultivate_chat_app.ui.authorization.signin.SignInFragmentArgs;
import com.example.cultivate_chat_app.ui.authorization.signin.SignInFragmentDirections;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    private SettingsViewModel mSettings;
    private FragmentSettingsBinding mBinding;
    private NicknameViewModel nicknameViewModel;

    public SettingsFragment() {
        // Required empty public constructor\
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // mUser = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        // mSettings = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
    }

//    private void SetColor(String color) {
//        setThemeColor(getActivity(), color);
//        getActivity().recreate();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentSettingsBinding.inflate(inflater);
        return mBinding.getRoot();

    }

    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding = FragmentSettingsBinding.bind(requireView());

        //On "Change nickname" button click, navigate to NicknameDialog
        mBinding.nicknameTextView.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        SettingsFragmentDirections.actionSettingsFragmentToNicknameDialog()
                ));

        //On "Change nickname" button click, navigate to NicknameDialog
        mBinding.passwordTextView.setOnClickListener(button ->
                Navigation.findNavController(getView()).navigate(
                        SettingsFragmentDirections.actionSettingsFragmentToPasswordDialog()
                ));
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        mSettings.getUserInfo(mUser.getJwt());
//        mBinding = FragmentSettingsBinding.bind(getView());
//    }
}