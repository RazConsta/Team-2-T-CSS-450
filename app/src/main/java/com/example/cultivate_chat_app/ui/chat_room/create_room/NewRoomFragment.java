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

import com.example.cultivate_chat_app.MainActivity;
import com.example.cultivate_chat_app.databinding.FragmentCreateNewChatBinding;
import com.example.cultivate_chat_app.ui.authorization.model.UserInfoViewModel;

public class NewRoomFragment extends Fragment {
    private FragmentCreateNewChatBinding mBinding;
    private LinearLayout mCreateRoomNameLinearView;
    private RelativeLayout mContactRelativeLayout;
    private ListView mInvitedMemberListView;
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
        mCreateRoomNameLinearView = mBinding.linearLayout;
        mContactRelativeLayout = mBinding.relativeLayout;
        mInvitedMemberListView = mBinding.listView;
        mUser = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);

        NewRoomViewModel model = new ViewModelProvider((ViewModelStoreOwner) MainActivity.getActivity())
                .get(NewRoomViewModel.class);
        UserInfoViewModel user = new ViewModelProvider((ViewModelStoreOwner)
                MainActivity.getActivity()).get(UserInfoViewModel.class);

//        mBinding.buttonAddPeople.setVisibility(View.GONE);
        mBinding.buttonAddPeople.setOnClickListener(button -> {
            if (mBinding.editChatName.getText().toString().equals("")) {
                mBinding.editChatName.setError("Cannot be Empty");
                return;
            }
            model.connectCreateRoom(mUser.getJwt(), mBinding.editChatName.getText().toString(), mUser.getId());
            navigateBack();
        });
        Log.e("ERROR", "jwt" + mUser.getJwt());
    }

    private void navigateBack() {
        Navigation.findNavController(getView())
                .navigate(NewRoomFragmentDirections.actionListContactsCreateToChatsFragment());
    }

}
