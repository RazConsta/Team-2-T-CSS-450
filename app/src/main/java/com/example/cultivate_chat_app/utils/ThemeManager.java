package com.example.cultivate_chat_app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import com.example.cultivate_chat_app.R;

public class ThemeManager {

    public static void setCustomizedThemes(Context context, String theme) {
        switch (theme) {
            case"green":
                context.setTheme(R.style.Theme_Green);
                break;
            case"yellow":
                context.setTheme(R.style.Theme_Yellow);
                break;
        }
    }

    public static void setThemeColor(Context context, String themeColor) {
        SharedPreferences sharedpreferences = context.getSharedPreferences("theme_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("theme", themeColor);
        editor.apply();
    }

    public static String getThemeColor(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences("theme_data", Context.MODE_PRIVATE);
        return sharedpreferences.getString("theme", "green");
    }
}
