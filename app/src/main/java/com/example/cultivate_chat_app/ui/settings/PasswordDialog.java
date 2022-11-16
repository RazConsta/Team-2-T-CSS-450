package com.example.cultivate_chat_app.ui.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.cultivate_chat_app.R;

public class PasswordDialog extends AppCompatDialogFragment {

    private EditText editTextCurrentPassword;
    private EditText editTextNewPassword;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.password_dialog, null);

        builder.setView(view)
                .setTitle("Change password")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton(("ok"), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        editTextCurrentPassword = view.findViewById(R.id.edit_current_password);
        editTextNewPassword = view.findViewById(R.id.edit_new_password);
        return builder.create();
    }
}
