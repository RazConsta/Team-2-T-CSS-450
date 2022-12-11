package com.example.cultivate_chat_app.ui.chat_room.create_room;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cultivate_chat_app.R;
import com.example.cultivate_chat_app.ui.contacts.Contact;

import java.util.HashMap;

public class ChosenMemberAdapter extends RecyclerView.Adapter<ChosenMemberAdapter.ViewHolder> {
    private final HashMap<Integer, Contact> mChosen;

    public ChosenMemberAdapter(HashMap<Integer, Contact> chosen) {
        this.mChosen = chosen;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_chosen_member_card, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact chosen = mChosen.get(position);
        holder.firstname.setText(chosen.getFirstname());
        holder.view.getContext();
    }

    @Override
    public int getItemCount() {
        return mChosen.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView firstname;
        private final View view;
        private final CardView cardRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            firstname = itemView.findViewById(R.id.small_contact_nickname);
            cardRoot = itemView.findViewById(R.id.contact_chosen_card);
            view = itemView.getRootView();
        }
    }
}
