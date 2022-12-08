package com.example.cultivate_chat_app.ui.chat_room;

import static com.example.cultivate_chat_app.utils.ThemeManager.getThemeColor;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cultivate_chat_app.MainActivity;
import com.example.cultivate_chat_app.R;
import com.example.cultivate_chat_app.databinding.FragmentChatRoomListBinding;
import com.example.cultivate_chat_app.ui.authorization.model.UserInfoViewModel;
import com.example.cultivate_chat_app.ui.contacts.ContactRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ChatRoomFragment extends Fragment {

    private FragmentChatRoomListBinding mBinding;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentChatRoomListBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = mBinding.roomList;

        ChatRoomViewModel model = new ViewModelProvider((ViewModelStoreOwner) MainActivity.getActivity())
                .get(ChatRoomViewModel.class);
        UserInfoViewModel user = new ViewModelProvider((ViewModelStoreOwner)
                MainActivity.getActivity()).get(UserInfoViewModel.class);


        model.resetRoom();
        model.addRoomListObserver(getViewLifecycleOwner(), this::setAdapter);
        model.connectRoom(user.getId(), user.getJwt());

        if (getThemeColor(getActivity()).equals("green")) {
            mBinding.floatingAddRoomButton.setBackgroundTintList(getResources().getColorStateList(R.color.green));
        } else {
            mBinding.floatingAddRoomButton.setBackgroundTintList(getResources().getColorStateList(R.color.yellow));
        }
        mBinding.floatingAddRoomButton.setOnClickListener(button -> navigateToCreateNewRoom());
    }

    private void navigateToCreateNewRoom() {
        Navigation.findNavController(getView())
                .navigate(ChatRoomFragmentDirections.actionChatsFragmentToListContactsCreate());
    }

    private void setAdapter(List<Room> rooms) {
        List<Room> roomMap = new ArrayList<>();
        for (Room room : rooms) {
            roomMap.add(room);
        }
        mRecyclerView.setAdapter(new RoomRecyclerViewAdapter(roomMap));
    }


}