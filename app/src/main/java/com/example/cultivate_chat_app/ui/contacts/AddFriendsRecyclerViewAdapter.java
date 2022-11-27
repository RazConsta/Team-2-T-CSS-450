package com.example.cultivate_chat_app.ui.contacts;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cultivate_chat_app.R;

import java.util.HashMap;

/**
 * A separate adapter class for contact card in AddFriendsFragment
 */
public class AddFriendsRecyclerViewAdapter extends RecyclerView.Adapter<AddFriendsRecyclerViewAdapter.ViewHolder> {

    private final HashMap<Integer,Contact> mContacts;

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

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = mContacts.get(position);
        assert contact != null;
        holder.nickname.setText(contact.getNickname());
        holder.fullName.setText(contact.getFirstname() +" "+contact.getLastname());

        //TODO contact card message button action
        holder.messageButton.setOnClickListener(Navigation.createNavigateOnClickListener
                (R.id.action_addFriendsFragment_to_chatsFragment));

        // TODO uncomment first, will need them later  // contact card option button action
//        holder.optionButton.setOnClickListener(new View.OnClickListener(){
//          @Override
//            public void onClick(View view) {
//                PopupMenu popup = new PopupMenu(holder.view.getContext(),holder.optionButton);
//                popup.inflate(R.menu.contact_card_menu);
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()){
//                            case R.id.add_friend:
//                                // handle add friend
//                            case R.id.remove_friend:
//                                // handle remove friend ("not friends yet, need to add first")
//                        }
//                        return false;
//                    }
//                });
//            }
//        });
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
        private final ImageView optionButton;
        private final CardView cardLayout;
        private final View view;

        public ViewHolder(View itemView) {
            super(itemView);
            nickname = itemView.findViewById(R.id.contact_card_addfriends_nickname);
            fullName = itemView.findViewById(R.id.contact_card_addfriends_fullname);
            messageButton = itemView.findViewById(R.id.contact_card_addfriends_message_button);
            optionButton = itemView.findViewById(R.id.contact_card_addfriends_option_button);
            cardLayout = itemView.findViewById(R.id.contact_card_addfriends_root);
            view = itemView.getRootView();
        }
    }
}
