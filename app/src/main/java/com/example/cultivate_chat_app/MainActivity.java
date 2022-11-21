package com.example.cultivate_chat_app;

import static com.example.cultivate_chat_app.utils.ThemeManager.getThemeColor;
import static com.example.cultivate_chat_app.utils.ThemeManager.setCustomizedThemes;

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

import com.example.cultivate_chat_app.databinding.ActivityMainBinding;
import com.example.cultivate_chat_app.services.PushReceiver;
import com.example.cultivate_chat_app.ui.authorization.model.NewMessageCountViewModel;
import com.example.cultivate_chat_app.ui.authorization.model.UserInfoViewModel;
import com.example.cultivate_chat_app.ui.authorization.signin.SignInViewModel;
import com.example.cultivate_chat_app.ui.chats.ChatMessage;
import com.example.cultivate_chat_app.ui.chats.ChatViewModel;
import com.example.cultivate_chat_app.ui.settings.NicknameDialog;
import com.example.cultivate_chat_app.ui.settings.NicknameViewModel;
import com.example.cultivate_chat_app.ui.settings.PasswordDialog;
import com.example.cultivate_chat_app.ui.settings.PasswordViewModel;
import com.example.cultivate_chat_app.ui.settings.SettingsFragment;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.util.AttributeSet;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity
        extends AppCompatActivity
        implements NicknameDialog.NicknameDialogListener,
                   PasswordDialog.PasswordDialogListener
                    {

    private NewMessageCountViewModel mNewMessageModel;
    private AppBarConfiguration mAppBarConfiguration;
    private NicknameViewModel nicknameViewModel;
    private PasswordViewModel passwordViewModel;
    private UserInfoViewModel mUser;
    private static Activity mMainActivity;
    private ActivityMainBinding binding;
    private MainPushMessageReceiver mPushMessageReceiver;


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
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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
        mNewMessageModel = new ViewModelProvider(this).get(NewMessageCountViewModel.class);

        navController.addOnDestinationChangedListener((controller, destination, arguments) ->{
            if(destination.getId() == R.id.settingsFragment && destination.getId() == R.id.chatsFragment) {
                // toolbar.setVisibility(View.GONE);
                navView.setVisibility(View.GONE);
                invalidateOptionsMenu(); // useless
                mNewMessageModel.reset();
            } else {
                // toolbar.setVisibility(View.VISIBLE);
                navView.setVisibility(View.VISIBLE);
            }
        });
        mNewMessageModel.addMessageCountObserver(this, count -> {
            BadgeDrawable badge = binding.navView.getOrCreateBadge(R.id.chatsFragment);
            badge.setMaxCharacterCount(2);
            if (count > 0) {
                //new messages! update and show the notification badge.
                badge.setNumber(count);
                badge.setVisible(true);
            } else {
                //user did some action to clear the new messages, remove the badge
                badge.clearNumber();
                badge.setVisible(false);
            }
        });


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        setCustomizedThemes(this, getThemeColor(this));
        return super.onCreateView(name, context, attrs);
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
            case R.id.logout_button:
                signOut();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.auth_graph);
        this.finish();
    }

    /**
     * A BroadcastReceiver that listens for messages sent from PushReceiver
     */
    private class MainPushMessageReceiver extends BroadcastReceiver {
        private ChatViewModel mModel =
                new ViewModelProvider(MainActivity.this)
                        .get(ChatViewModel.class);
        @Override
        public void onReceive(Context context, Intent intent) {
            NavController nc =
                    Navigation.findNavController(
                            MainActivity.this, R.id.nav_host_fragment);
            NavDestination nd = nc.getCurrentDestination();
            if (intent.hasExtra("chatMessage")) {
                ChatMessage cm = (ChatMessage) intent.getSerializableExtra("chatMessage");
                //If the user is not on the chat screen, update the
                // NewMessageCountView Model
                if (nd.getId() != R.id.chatsFragment) {
                    mNewMessageModel.increment();
                }
                //Inform the view model holding chatroom messages of the new
                //message.
                mModel.addMessage(intent.getIntExtra("chatid", -1), cm);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPushMessageReceiver == null) {
            mPushMessageReceiver = new MainPushMessageReceiver();
        }
        IntentFilter iFilter = new IntentFilter(PushReceiver.RECEIVED_NEW_MESSAGE);
        registerReceiver(mPushMessageReceiver, iFilter);
    }
    @Override
    public void onPause() {
        super.onPause();
        if (mPushMessageReceiver != null){
            unregisterReceiver(mPushMessageReceiver);
        }
    }
}