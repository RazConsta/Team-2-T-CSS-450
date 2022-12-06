package com.example.cultivate_chat_app.ui.chat_room;

import java.io.Serializable;

public class Room implements Serializable {

    private final String mRoomName, mLatestMessage;

    public Room(String mRoomName, String mLatestMessage) {
        this.mRoomName = mRoomName;
        this.mLatestMessage = mLatestMessage;
    }

    public String getmRoomName() {
        return mRoomName;
    }

    public String getmLatestMessage() {
        return mLatestMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this.getClass() != o.getClass()){
            return false;
        }
        Room other = (Room) o;
        return this.mRoomName == other.mRoomName;
    }
}
