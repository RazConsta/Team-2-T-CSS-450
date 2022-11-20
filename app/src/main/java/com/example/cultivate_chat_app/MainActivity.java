package com.example.cultivate_chat_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
// import androidx.navigation.NavControllerViewModel;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cultivate_chat_app.ui.authorization.model.UserInfoViewModel;
import com.example.cultivate_chat_app.ui.authorization.signin.SignInViewModel;
import com.example.cultivate_chat_app.ui.settings.NicknameDialog;
import com.example.cultivate_chat_app.ui.settings.NicknameViewModel;
import com.example.cultivate_chat_app.ui.settings.PasswordDialog;
import com.example.cultivate_chat_app.ui.settings.PasswordViewModel;
import com.example.cultivate_chat_app.ui.settings.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

public class MainActivity
        extends AppCompatActivity
        implements NicknameDialog.NicknameDialogListener,
                   PasswordDialog.PasswordDialogListener {

    private AppBarConfiguration mAppBarConfiguration;
    private NicknameViewModel nicknameViewModel;
    private PasswordViewModel passwordViewModel;
    private UserInfoViewModel mUser;
    private static Activity mMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainActivity = this;
        nicknameViewModel = new ViewModelProvider(this).get(NicknameViewModel.class);
        passwordViewModel = new ViewModelProvider(this).get(PasswordViewModel.class);
        MainActivityArgs args = MainActivityArgs.fromBundle(getIntent().getExtras());
        mUser = new ViewModelProvider(this,
                new UserInfoViewModel.UserInfoViewModelFactory(args.getEmail(), args.getJwt(), "", "", "", 0)
        ).get(UserInfoViewModel.class);

        setContentView(R.layout.activity_main);
        // getSupportActionBar().hide(); // Had to delete otherwise top bar would not show up
        // Make sure the new statements go BELOW setContentView
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.chatsFragment, R.id.contactsFragment, R.id.weatherFragment)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId() == R.id.settingsFragment) {
                    // toolbar.setVisibility(View.GONE);
                    navView.setVisibility(View.GONE);
                    invalidateOptionsMenu(); // useless
                } else {
                    // toolbar.setVisibility(View.VISIBLE);
                    navView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void changeNickname(String nickname) {
        nicknameViewModel.connect(mUser.getEmail(), nickname);
    }

    public void changePassword(String oldPass, String newPass) {
        passwordViewModel.connect(mUser.getEmail(), oldPass, newPass);
    }

    public static Activity getActivity(){
        return mMainActivity;
    }
    /*
    enable the go back function for the go back icon on the title.
     */
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dropdown_menu, menu);
        return true;
        // return super.onCreateOptionsMenu(menu);
    }

    // useless
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.settingsFragment:
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.settingsFragment);
                break;
            // case R.id.action_sign_out:
                // signOut();
                // break;
        }
        return super.onOptionsItemSelected(item);
    }
}











