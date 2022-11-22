package com.example.cultivate_chat_app.ui.contacts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cultivate_chat_app.MainActivity;
import com.example.cultivate_chat_app.R;
import com.example.cultivate_chat_app.databinding.FragmentAddFriendsBinding;
import com.example.cultivate_chat_app.ui.authorization.model.UserInfoViewModel;

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFriendsFragment extends Fragment {

    private FragmentAddFriendsBinding mBinding;
    private RecyclerView mReceivedRecyclerView, mSearchedRecyclerView;
    UserInfoViewModel mUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentAddFriendsBinding.inflate(inflater);
        return mBinding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mReceivedRecyclerView = mBinding.listReceivedRequests;
        mSearchedRecyclerView = mBinding.listSearchPeople;

        mBinding.buttonSearchPeople.setOnClickListener(button -> displaySearched());

        mUser = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);

       ContactListViewModel getRequests = new ViewModelProvider(
               (ViewModelStoreOwner) MainActivity.getActivity()).get(ContactListViewModel.class);

       getRequests.resetRequests();
       getRequests.connectContacts(mUser.getId(), mUser.getJwt(), "requests");
       getRequests.addPendingListObserver(getViewLifecycleOwner(), this::setAdapterForRequests);
    }

    /**
     * display searched info
     */
    private void displaySearched(){
        mBinding.editSearchPeople.setError(null);
        if (mBinding.editSearchPeople.getText().toString().equals("")) {
            mBinding.editSearchPeople.setError("Cannot be Empty");
            return;
        }

        SearchViewModel searchResult = new ViewModelProvider(getActivity()).get(SearchViewModel.class);
        Log.d("TTT", mBinding.editSearchPeople.getText().toString());
        searchResult.addSearchListObserver(getViewLifecycleOwner(), this::setAdapterForSearch);
        searchResult.connectSearch(mUser.getJwt(), mBinding.editSearchPeople.getText().toString());
    }

    /**
     * set adapter for searches
     * @param contacts all the searched contacts
     */
    private void setAdapterForSearch(List<Contact> contacts) {
        HashMap<Integer, Contact> contactMap = new HashMap<>();
        for (Contact contact : contacts)
            contactMap.put(contacts.indexOf(contact), contact);
        mSearchedRecyclerView.setAdapter(new ContactRecyclerViewAdapter( contactMap));
    }

    /**
     * set adapter for requests
     * @param contacts all the requests the user recieved
     */
    private void setAdapterForRequests(List<Contact> contacts) {
        HashMap<Integer, Contact> contactMap = new HashMap<>();
        for (Contact contact : contacts)
            contactMap.put(contacts.indexOf(contact), contact);
        mReceivedRecyclerView.setAdapter(new ContactRecyclerViewAdapter( contactMap));
    }
}