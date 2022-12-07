package com.example.cultivate_chat_app.ui.chat_room.create_room;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cultivate_chat_app.databinding.FragmentCreateNewChatBinding;

public class NewRoomFragment extends Fragment {
    private FragmentCreateNewChatBinding mBinding;
    private LinearLayout mCreateRoomNameLinearView;
    private RelativeLayout mContactRelativeLayout;
    private ListView mInvitedMemberListView;

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
        mCreateRoomNameLinearView = mBinding.linearLayout;
        mContactRelativeLayout = mBinding.relativeLayout;
        mInvitedMemberListView = mBinding.listView;
    }
}
