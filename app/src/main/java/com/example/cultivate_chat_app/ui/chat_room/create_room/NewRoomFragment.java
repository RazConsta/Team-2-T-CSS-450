package com.example.cultivate_chat_app.ui.chat_room.create_room;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cultivate_chat_app.MainActivity;
import com.example.cultivate_chat_app.databinding.FragmentCreateNewChatBinding;
import com.example.cultivate_chat_app.ui.authorization.model.UserInfoViewModel;
import com.example.cultivate_chat_app.ui.contacts.AddFriendsRecyclerViewAdapter;
import com.example.cultivate_chat_app.ui.contacts.Contact;
import com.example.cultivate_chat_app.ui.contacts.ContactListViewModel;
import com.example.cultivate_chat_app.ui.contacts.SearchViewModel;

import java.util.HashMap;
import java.util.List;

public class NewRoomFragment extends Fragment {
    private FragmentCreateNewChatBinding mBinding;
    private RecyclerView mSearchedRecyclerView, mChosenRecyclerView;
    private UserInfoViewModel mUser;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentCreateNewChatBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSearchedRecyclerView = mBinding.listContactsInChatRoom;
        mChosenRecyclerView = mBinding.chosenMember;

        mUser = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);

        //this is for create chat room name
        NewRoomViewModel model = new ViewModelProvider((ViewModelStoreOwner) MainActivity.getActivity())
                .get(NewRoomViewModel.class);
        UserInfoViewModel user = new ViewModelProvider((ViewModelStoreOwner)
                MainActivity.getActivity()).get(UserInfoViewModel.class);
        mBinding.buttonAddPeople.setEnabled(false);
        mBinding.buttonAddPeople.setOnClickListener(button -> {
            if (mBinding.editChatName.getText().toString().equals("")) {
                mBinding.editChatName.setError("Cannot be Empty");
                return;
            }
            model.connectCreateRoom(mUser.getJwt(), mBinding.editChatName.getText().toString(), mUser.getId());
            navigateBack();
        });

        //this is for display the contact list
        ContactListViewModel contactModel = new ViewModelProvider((ViewModelStoreOwner) MainActivity.getActivity())
                .get(ContactListViewModel.class);

        contactModel.resetContacts();
        contactModel.connectContacts(user.getId(),user.getJwt(), "current");
        contactModel.addContactListObserver(getViewLifecycleOwner(), this::setAdapterForContact);

        //this is for display the chosen member list
        ContactListViewModel chosenModel = new ViewModelProvider((ViewModelStoreOwner) MainActivity.getActivity())
                .get(ContactListViewModel.class);
        chosenModel.resetChosens();
        chosenModel.connectGetRequest(-1);
        chosenModel.addChosenListObeserver(getViewLifecycleOwner(), this::setAdapterForChosen);

    }

    private void setAdapterForContact(List<Contact> contacts) {
        HashMap<Integer, Contact> contactMap = new HashMap<>();
        for (Contact contact : contacts) {
            contactMap.put(contacts.indexOf(contact), contact);
        }
        mSearchedRecyclerView.setAdapter(new ContactNewRoomRecyclerViewAdapter(contactMap));
    }

    private void setAdapterForChosen(List<Contact> contacts) {
        HashMap<Integer, Contact> contactMap = new HashMap<>();
        for (Contact contact : contacts) {
            contactMap.put(contacts.indexOf(contact), contact);
        }
        mSearchedRecyclerView.setAdapter(new ChosenMemberAdapter(contactMap));
    }

    private void navigateBack() {
        Navigation.findNavController(getView())
                .navigate(NewRoomFragmentDirections.actionListContactsCreateToChatsFragment());
    }

}
