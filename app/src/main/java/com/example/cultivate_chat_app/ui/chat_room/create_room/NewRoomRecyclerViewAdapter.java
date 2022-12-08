package com.example.cultivate_chat_app.ui.chat_room.create_room;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cultivate_chat_app.R;
import com.example.cultivate_chat_app.ui.contacts.Contact;
import com.example.cultivate_chat_app.ui.contacts.ContactRecyclerViewAdapter;

import java.util.HashMap;

public class NewRoomRecyclerViewAdapter extends RecyclerView.Adapter<NewRoomRecyclerViewAdapter.ViewHolder> {
    private final HashMap<Integer, Contact> mContacts;

    /**
     * Constructor
     * @param contacts
     */
    public NewRoomRecyclerViewAdapter(HashMap<Integer, Contact> contacts) {
        super();
        this.mContacts = contacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_accepted_member, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = mContacts.get(position);
        holder.nickName.setText(contact.getNickname());
        holder.fullName.setText(contact.getFirstname() +" "+contact.getLastname());
//        holder.cardLayout.setOnClickListener();
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    /**
     * Objects from this class represent an individual row View from the List
     * of rows in the Room Card Recycler View.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nickName;
        private final TextView fullName;
        private final CardView cardLayout;
        private final View view;

        public ViewHolder(View itemView) {
            super(itemView);
            nickName = itemView.findViewById(R.id.contact_card_nickname);
            fullName = itemView.findViewById(R.id.contact_card_fullname);
            cardLayout = itemView.findViewById(R.id.accepted_member_card_root);
            view = itemView.getRootView();
        }
    }

}
