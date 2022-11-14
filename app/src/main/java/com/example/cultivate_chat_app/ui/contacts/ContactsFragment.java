package com.example.cultivate_chat_app.ui.contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cultivate_chat_app.R;
import com.example.cultivate_chat_app.databinding.FragmentContactsBinding;

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {

    private FragmentContactsBinding mBinding;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentContactsBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = mBinding.contactList;
        mBinding.fabAddContact.setOnClickListener(button -> navigateToAddNewFriends());
    }

    /**
     * Set Adapter for the contacts
     * @param contacts list of users contacts
     */
    private void setAdapter(List<Contact> contacts) {
        HashMap<Integer, Contact> contactMap = new HashMap<>();
        for (Contact contact : contacts){
            contactMap.put(contacts.indexOf(contact), contact);

        }
        mRecyclerView.setAdapter(new ContactRecyclerViewAdapter(getActivity(), contactMap));
    }

    /**
     * Navigate to adding friends
     */
    private void navigateToAddNewFriends() {
        Navigation.findNavController(getView())
                .navigate(ContactsFragmentDirections
                        .actionContactsFragmentToAddFriendsFragment());
    }
}