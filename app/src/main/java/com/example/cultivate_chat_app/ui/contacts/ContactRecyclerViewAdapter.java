package com.example.cultivate_chat_app.ui.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cultivate_chat_app.R;

import java.util.HashMap;

public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewAdapter.ViewHolder> {

    protected final HashMap<Integer,Contact> mContacts;
    protected final Context mContext;


    /**
     * Constructor
     * @param context current context
     * @param contacts current contacts
     */
    public ContactRecyclerViewAdapter(Context context, HashMap<Integer,Contact> contacts){


        mContacts = contacts;
        mContext = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.fragment_contact_card,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = mContacts.get(position);
        holder.nickname.setText(mContacts.get(position).getNickname());
        holder.fullName.setText(mContacts.get(position).getFirstname()
                +" "+mContacts.get(position).getLastname());

        // contact card message button action
        holder.messageButton.setOnClickListener(Navigation.createNavigateOnClickListener
                (R.id.action_contactsFragment_to_chatsFragment));
        holder.messageButton.setOnClickListener(Navigation.createNavigateOnClickListener
                (R.id.action_contactsFragment_to_chatsFragment));

        // contact card option button action
        holder.optionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(mContext, holder.messageButton);
                popup.inflate(R.menu.concact_card_menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.add_friend:
                                //handle addfriend click
                                break;
                            case R.id.remove_friend:
                                //handle remove friend click
                                break;
                        }
                        return false;
                    }
                });
            }
        });

    }


    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView nickname, fullName;
        ImageView messageButton, optionButton;
        ConstraintLayout cardLayout;
        View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nickname = itemView.findViewById(R.id.contact_card_nickname);
            fullName = itemView.findViewById(R.id.contact_card_fullname);
            messageButton = itemView.findViewById(R.id.contact_card_message_friend_button);
            optionButton = itemView.findViewById(R.id.contact_card_option_button);
            cardLayout = itemView.findViewById(R.id.contact_card_root);
            view = itemView.getRootView();
        }
    }
}