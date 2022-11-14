package com.example.cultivate_chat_app.ui.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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
    }


    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView nickname, fullName;
        ConstraintLayout cardLayout;
        View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nickname = itemView.findViewById(R.id.contact_card_nickname);
            fullName = itemView.findViewById(R.id.contact_card_fullname);
            cardLayout = itemView.findViewById(R.id.contact_card_root);
            view = itemView.getRootView();
        }
    }
}