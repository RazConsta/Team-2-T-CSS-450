package com.example.cultivate_chat_app.ui.contacts;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cultivate_chat_app.MainActivity;
import com.example.cultivate_chat_app.R;

import java.util.HashMap;

public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewAdapter.ViewHolder> {

    private final HashMap<Integer,Contact> mContacts;
    private final Context mContext;
    private ManagerFriendViewModel mManage;


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

        mManage = new ViewModelProvider((ViewModelStoreOwner) MainActivity.getActivity()).get(ManagerFriendViewModel.class);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = mContacts.get(position);
        holder.nickname.setText(contact.getNickname());
        holder.fullName.setText(contact.getFirstname() +" "+contact.getLastname());

        // TODO uncomment first, will need them later //contact card message button action
//        holder.messageButton.setOnClickListener(Navigation.createNavigateOnClickListener
//                (R.id.action_contactsFragment_to_chatsFragment));
//        holder.messageButton.setOnClickListener(Navigation.createNavigateOnClickListener
//                (R.id.action_contactsFragment_to_chatsFragment));

        // TODO uncomment first, will need them later  // contact card option button action
//        holder.optionButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                PopupMenu popup = new PopupMenu(mContext, holder.messageButton);
//                popup.inflate(R.menu.concact_card_menu);
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()){
//                            case R.id.add_friend:
//                                showAddDialog(contact,holder.view,position);
//                                break;
//                            case R.id.remove_friend:
//                                showRemoveDialog(contact,holder.view,position);
//                                break;
//                        }
//                        return false;
//                    }
//                });
//            }
//        });

    }

    //TODO uncomment first, will need it later
//    /**
//     * Show Remove Dialog for removing friends
//     *  @param contact the contact
//     * @param view the view for it to appear
//     * @param position position of the contact
//     */
//    void showAddDialog(Contact contact, View view, int position) {
//        Dialog dialog = new Dialog(view.getContext());
//        dialog.setCancelable(true);
//
//        dialog.setContentView(R.layout.dialog_contact_card_add_friend);
//        dialog.findViewById(R.id.button_ok).setOnClickListener(button -> {
//            dialog.dismiss();
//            switch (mContacts.get(position).getStatus()){
//                case NOT_FRIENDS:
//                    mManage.connectSendRequest(contact.getId());
//                    break;
//                case RECEIVED_REQUEST:
//                    mManage.connectAcceptRequest(contact.getId());
//            }
//
//            removeFromView(position);
//        });
//        dialog.findViewById(R.id.button_cancel).setOnClickListener(button -> dialog.dismiss());
//        dialog.show();
//    }

    //TODO uncomment first, will need it later
//    /**
//     * Show Remove Dialog for removing friends
//     *  @param contact the contact
//     * @param view the view for it to appear
//     * @param position position of the contact
//     */
//    void showRemoveDialog(Contact contact, View view, int position) {
//        Dialog dialog = new Dialog(view.getContext());
//        dialog.setCancelable(true);
//
//        dialog.setContentView(R.layout.dialog_contact_card_remove_friend);
//        dialog.findViewById(R.id.button_ok).setOnClickListener(button -> {
//            dialog.dismiss();
//            mManage.connectRemoveFriend(contact.getId());
//
//            removeFromView(position);
//        });
//        dialog.findViewById(R.id.button_cancel).setOnClickListener(button -> dialog.dismiss());
//        dialog.show();
//    }

    //TODO uncomment first, will need it later
//    /**
//     * Remove contact from list
//     * @param position position of the contact
//     */
//    private void removeFromView(int position){
//        mContacts.remove(position);
//        notifyItemRemoved(position);
//        notifyItemChanged(position, mContacts.size());
//    }

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