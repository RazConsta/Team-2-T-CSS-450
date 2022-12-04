package com.example.cultivate_chat_app.ui.chat_room;

import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cultivate_chat_app.MainActivity;
import com.example.cultivate_chat_app.R;
import java.util.HashMap;

public class RoomRecyclerViewAdapter extends RecyclerView.Adapter<RoomRecyclerViewAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * Objects from this class represent an individual row View from the List
     * of rows in the Contact Card Recycler View.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView roomName;
        private final TextView roomMessage;
        private final CardView cardLayout;
        private final View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roomName = itemView.findViewById(R.id.roomName);
            roomMessage = itemView.findViewById(R.id.roomLastestMessage);
            cardLayout = itemView.findViewById(R.id.room_card_root);
            view = itemView.getRootView();
        }
    }
}
