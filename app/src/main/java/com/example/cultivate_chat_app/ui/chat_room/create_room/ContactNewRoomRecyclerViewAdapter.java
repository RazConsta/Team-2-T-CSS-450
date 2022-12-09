package com.example.cultivate_chat_app.ui.chat_room.create_room;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cultivate_chat_app.R;

import com.example.cultivate_chat_app.ui.contacts.Contact;

import java.util.HashMap;

public class ContactNewRoomRecyclerViewAdapter extends  RecyclerView.Adapter<ContactNewRoomRecyclerViewAdapter.ViewHolder> {
    private final HashMap<Integer,Contact> mContacts;

    public ContactNewRoomRecyclerViewAdapter(HashMap<Integer, Contact> mContacts) {
        this.mContacts = mContacts;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_room_create_contact,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = mContacts.get(position);
        holder.nickname.setText(contact.getNickname());
        holder.fullName.setText(contact.getFirstname() +" "+contact.getLastname());
        holder.view.getContext();
    }


    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView nickname;
        private final TextView fullName;
        private final View view;

        public ViewHolder(View itemView) {
            super(itemView);
            nickname = itemView.findViewById(R.id.contact_card_nickname_in_room);
            fullName = itemView.findViewById(R.id.contact_card_fullname_in_room);
            //cardLayout = itemView.findViewById(R.id.contact_card_root);
            view = itemView.getRootView();
        }
    }
}
