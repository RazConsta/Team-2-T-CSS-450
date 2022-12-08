package com.example.cultivate_chat_app.ui.chat_room.create_room;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Chat implements Serializable {

    private int mRoomId;

    public Chat(int roomid) {
        this.mRoomId = roomid;
    }

    public int getmRoomId() {
        return mRoomId;
    }

    public void setmRoomId(int roomid) {
        this.mRoomId = roomid;
    }

    @Override
    public boolean equals(Object o) {
        if (this.getClass() != o.getClass()){
            return false;
        }
        Chat other = (Chat) o;
        return this.mRoomId == other.mRoomId;
    }
}
