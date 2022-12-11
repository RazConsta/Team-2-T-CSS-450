package com.example.cultivate_chat_app.ui.chat_room.create_room;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cultivate_chat_app.MainActivity;
import com.example.cultivate_chat_app.R;
import com.example.cultivate_chat_app.ui.contacts.Contact;
import com.example.cultivate_chat_app.ui.contacts.ContactListViewModel;

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
        holder.nickname.setText(chosen.getNickname());
        holder.view.getContext();
        ContactListViewModel deleteModel = new ViewModelProvider((ViewModelStoreOwner) MainActivity.getActivity())
                .get(ContactListViewModel.class);
        holder.deleteAction.setOnClickListener(item -> {
//            deleteModel.connectDeleteForSelectMember(holder.nickname.getText().toString());
            Log.e("ERROR", holder.nickname.getText().toString());
            deleteModel.connectDeleteForSelectMember(holder.nickname.getText().toString());
        });
    }

    @Override
    public int getItemCount() {
        return mChosen.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nickname;
        private final View view;
        private final CardView cardRoot;
        private final ImageView deleteAction;

        public ViewHolder(View itemView) {
            super(itemView);
            nickname = itemView.findViewById(R.id.small_contact_nickname);
            cardRoot = itemView.findViewById(R.id.contact_chosen_card);
            deleteAction = itemView.findViewById(R.id.delete_chosen_contact);
            view = itemView.getRootView();
        }
    }
}
