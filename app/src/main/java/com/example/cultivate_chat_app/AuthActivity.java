package com.example.cultivate_chat_app;

import static com.example.cultivate_chat_app.utils.ThemeManager.getThemeColor;
import static com.example.cultivate_chat_app.utils.ThemeManager.setCustomizedThemes;
import static com.example.cultivate_chat_app.utils.ThemeManager.setThemeColor;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomizedThemes(this, getThemeColor(this));
        setContentView(R.layout.activity_auth);
        getSupportActionBar().hide();
    }
}