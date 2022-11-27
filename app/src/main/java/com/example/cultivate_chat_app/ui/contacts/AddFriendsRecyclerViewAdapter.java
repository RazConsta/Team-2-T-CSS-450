package com.example.cultivate_chat_app.ui.contacts;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cultivate_chat_app.MainActivity;
import com.example.cultivate_chat_app.R;

import java.util.HashMap;

/**
 * A separate adapter class for contact card in AddFriendsFragment
 */
public class AddFriendsRecyclerViewAdapter extends RecyclerView.Adapter<AddFriendsRecyclerViewAdapter.ViewHolder> {

    private final HashMap<Integer,Contact> mContacts;
    private ManagerFriendViewModel mManage;

    /**
     * Constructor
     // * @param context current context
     * @param contacts current contacts
     */
    public AddFriendsRecyclerViewAdapter(HashMap<Integer,Contact> contacts){
        this.mContacts = contacts;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_contact_card_addfriends,parent,false);
        mManage = new ViewModelProvider(
                (ViewModelStoreOwner) MainActivity.getActivity()).get(ManagerFriendViewModel.class);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = mContacts.get(position);
        holder.nickname.setText(contact.getNickname());
        holder.fullName.setText(contact.getFirstname() +" "+contact.getLastname());

        holder.messageButton.setOnClickListener(Navigation.createNavigateOnClickListener
                (R.id.action_addFriendsFragment_to_chatsFragment));

        switch (contact.getStatus()){
            case RECEIVED_REQUEST:
                holder.addButton.setOnClickListener(button ->
                        acceptRequest(contact, position));
                break;
            case NOT_FRIENDS:
                holder.addButton.setOnClickListener(button ->
                        sendRequest(contact, position));
        }
    }

    /**
     * Accept Friend Request
     * @param contact Contact of the request to accept
     * @param position position of the contact
     */
    private void acceptRequest(Contact contact, int position){
        mManage.connectAcceptRequest(contact.getId());

        removeFromView(position);
    }

    /**
     * Send a friend request
     * @param contact contact to send the request
     * @param position position of the contact
     */
    private void sendRequest(Contact contact, int position){
        mManage.connectSendRequest(contact.getId());
        removeFromView(position);
    }

    /**
     * Remove contact from list
     * @param position position of the contact
     */
    private void removeFromView(int position){
        mContacts.remove(position);
        notifyItemRemoved(position);
        notifyItemChanged(position, mContacts.size());
    }


    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    /**
     * Objects from this class represent an individual row View from the List
     * of rows in the Contact Card Recycler View.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView nickname;
        private final TextView fullName;
        private final ImageView messageButton;
        private final ImageView addButton;
        private final CardView cardLayout;
        private final View view;

        public ViewHolder(View itemView) {
            super(itemView);
            nickname = itemView.findViewById(R.id.contact_card_addfriends_nickname);
            fullName = itemView.findViewById(R.id.contact_card_addfriends_fullname);
            messageButton = itemView.findViewById(R.id.contact_card_addfriends_message_button);
            addButton = itemView.findViewById(R.id.contact_card_addfriends_add_button);
            cardLayout = itemView.findViewById(R.id.contact_card_addfriends_root);
            view = itemView.getRootView();
        }
    }
}
