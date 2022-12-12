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
import com.example.cultivate_chat_app.ui.authorization.register.RegisterFragment;
import com.example.cultivate_chat_app.ui.authorization.register.RegisterFragmentDirections;
import com.example.cultivate_chat_app.ui.chat_room.create_room.NewRoomFragment;
import com.example.cultivate_chat_app.ui.chats.ChatFragment;
import com.example.cultivate_chat_app.ui.chats.ChatSendViewModel;
import com.example.cultivate_chat_app.ui.contacts.Contact;
import com.example.cultivate_chat_app.ui.contacts.ContactRecyclerViewAdapter;

import org.w3c.dom.Text;

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
        Room room = mRooms.get(position);
        holder.roomName.setText(room.getmRoomName());
        holder.roomMessage.setText(room.getmLatestMessage());
        holder.chatid.setText(room.getmChatID());
        holder.chatid.setVisibility(View.GONE);
        holder.view.getContext();
        holder.cardLayout.setOnClickListener( item -> {
                    ChatRoomFragmentDirections.ActionChatsFragmentToChats directions =
                            ChatRoomFragmentDirections.actionChatsFragmentToChats(Integer.parseInt(room.getmChatID()));
                    Navigation.findNavController(holder.view).navigate(directions);
                }

//            Log.e("ERRR", holder.chatid.getText().toString());
//            Navigation.createNavigateOnClickListener(get
//            ChatFragment test = new ChatFragment(new ChatSendViewModel(provider.get(ChatSendViewModel.class)), Integer.parseInt(holder.chatid.getText().toString()),)
        );

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
        private final TextView chatid;

        public ViewHolder(View itemView) {
            super(itemView);
            roomName = itemView.findViewById(R.id.roomName);
            roomMessage = itemView.findViewById(R.id.roomLastestMessage);
            cardLayout = itemView.findViewById(R.id.room_card_root);
            chatid = itemView.findViewById(R.id.secret_chat_id);
            view = itemView.getRootView();
        }
    }
}
