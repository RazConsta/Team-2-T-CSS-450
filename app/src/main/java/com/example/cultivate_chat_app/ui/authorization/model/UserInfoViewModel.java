package com.example.cultivate_chat_app.ui.authorization.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class UserInfoViewModel extends ViewModel {

    private final String mEmail;
    private final String mJwt;
    private final int mId;
    private String mFirst;
    private String mLast;
    private String mNick;
    private String mTheme;

    public UserInfoViewModel(String email, String jwt, String first, String last, String nick, int id) {
        mEmail = email;
        mJwt = jwt;
        mFirst = first;
        mLast = last;
        mNick = nick;
        mId = id;
    }

    public void setFirst(String mFirst) {
        this.mFirst = mFirst;
    }

    public void setLast(String mLast) {
        this.mLast = mLast;
    }

    public void setNick(String mNick) {
        this.mNick = mNick;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getJwt() {
        return mJwt;
    }

    public int getId() { return mId; }

    public String getFirst() { return mFirst; }

    public String getLast() { return mLast; }

    public String getNick() { return mNick; }

    public static class UserInfoViewModelFactory implements ViewModelProvider.Factory {

        private final String email;
        private final String jwt;
        private final int id;
        private String first;
        private String last;
        private String nick;

        public UserInfoViewModelFactory(String email, String jwt, String first, String last, String nick, int id) {
            this.email = email;
            this.jwt = jwt;
            this.first = first;
            this.last = last;
            this.nick = nick;
            this.id = id;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass == UserInfoViewModel.class) {
                return (T) new UserInfoViewModel(email, jwt, first, last, nick, id);
            }
            throw new IllegalArgumentException(
                    "Argument must be: " + UserInfoViewModel.class);
        }
    }


}

