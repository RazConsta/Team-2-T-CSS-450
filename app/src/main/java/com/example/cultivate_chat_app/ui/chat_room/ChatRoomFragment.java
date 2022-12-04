package com.example.cultivate_chat_app.ui.chat_room;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cultivate_chat_app.R;
import com.example.cultivate_chat_app.databinding.FragmentChatRoomListBinding;
import com.example.cultivate_chat_app.databinding.FragmentContactsBinding;

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
//        super.onViewCreated(view, savedInstanceState);
//        mRecyclerView = mBinding.chatRoomList;
    }
}