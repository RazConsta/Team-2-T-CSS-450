package com.example.cultivate_chat_app.ui.contacts;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
                        showAcceptDialog(contact, holder.view, position));
                break;
            case NOT_FRIENDS:
                holder.addButton.setOnClickListener(button ->
                        showSendDialog(contact, holder.view, position));
        }
    }

    /**
     * Show Accept Requests Dialog for accepting friends requests
     * @param contact the contact
     * @param view the view for it to appear
     * @param position position of the contact
     */
    void showAcceptDialog(Contact contact, View view, int position) {
        Dialog dialog = new Dialog(view.getContext());
        dialog.setCancelable(true);

        dialog.setContentView(R.layout.dialog_contact_card_accept_requests);
        dialog.findViewById(R.id.button_ok).setOnClickListener(button -> {
            dialog.dismiss();
            mManage.connectAcceptRequest(contact.getId());
            removeFromView(position);
            Toast.makeText(MainActivity.getActivity(),"Accepted request successful!", Toast.LENGTH_SHORT).show();
        });
        dialog.findViewById(R.id.button_decline).setOnClickListener(button -> {
            dialog.dismiss();
            mManage.connectRemoveFriend(contact.getId());
            removeFromView(position);
            Toast.makeText(MainActivity.getActivity(),"Declined request successful!", Toast.LENGTH_SHORT).show();
        });
        dialog.findViewById(R.id.button_cancel).setOnClickListener(button -> dialog.dismiss());
        dialog.show();
    }

    /**
     * Show Send Requests Dialog for sending friends requests
     * @param contact the contact
     * @param view the view for it to appear
     * @param position position of the contact
     */
    void showSendDialog(Contact contact, View view, int position) {
        Dialog dialog = new Dialog(view.getContext());
        dialog.setCancelable(true);

        dialog.setContentView(R.layout.dialog_contact_card_send_requests);
        dialog.findViewById(R.id.button_ok).setOnClickListener(button -> {
            dialog.dismiss();
            mManage.connectSendRequest(contact.getId());
            removeFromView(position);
            Toast.makeText(MainActivity.getActivity(),"Sent request successful!", Toast.LENGTH_SHORT).show();
        });
        dialog.findViewById(R.id.button_cancel).setOnClickListener(button -> dialog.dismiss());
        dialog.show();
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
        //private final CardView cardLayout;
        private final View view;

        public ViewHolder(View itemView) {
            super(itemView);
            nickname = itemView.findViewById(R.id.contact_card_addfriends_nickname);
            fullName = itemView.findViewById(R.id.contact_card_addfriends_fullname);
            messageButton = itemView.findViewById(R.id.contact_card_addfriends_message_button);
            addButton = itemView.findViewById(R.id.contact_card_addfriends_add_button);
           // cardLayout = itemView.findViewById(R.id.contact_card_addfriends_root);
            view = itemView.getRootView();
        }
    }
}
