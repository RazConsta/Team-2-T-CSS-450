package com.example.cultivate_chat_app.ui.chat_room;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.cultivate_chat_app.R;
import com.example.cultivate_chat_app.ui.contacts.Contact;
import com.example.cultivate_chat_app.ui.contacts.ContactRecyclerViewAdapter;

import java.util.HashMap;
import java.util.List;

public class RoomRecyclerViewAdapter extends RecyclerView.Adapter<RoomRecyclerViewAdapter.ViewHolder> {

    private final List<Room> mRooms;

    public RoomRecyclerViewAdapter(List<Room> mRooms) {
        this.mRooms = mRooms;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_chat_room_card, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("RECYCLERVIEW", "ROOM = " + mRooms.get(position));
        Room room = mRooms.get(position);
        holder.roomName.setText(room.getmRoomName());
        holder.roomMessage.setText(room.getmLatestMessage());
        holder.cardLayout.setOnClickListener(Navigation.createNavigateOnClickListener
                (R.id.action_chatsFragment_to_chats));

    }

    @Override
    public int getItemCount() {
        return mRooms.size();
    }

    /**
     * Objects from this class represent an individual row View from the List
     * of rows in the Room Card Recycler View.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView roomName;
        private final TextView roomMessage;
        private final CardView cardLayout;
        private final View view;

        public ViewHolder(View itemView) {
            super(itemView);
            roomName = itemView.findViewById(R.id.roomName);
            roomMessage = itemView.findViewById(R.id.roomLastestMessage);
            cardLayout = itemView.findViewById(R.id.room_card_root);
            view = itemView.getRootView();
        }
    }
}
