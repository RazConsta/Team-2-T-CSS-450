package com.example.cultivate_chat_app.ui.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
    private EditText editTextReTypePassword;
    private PasswordDialogListener listener;

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
                        String currentPass = editTextCurrentPassword.getText().toString();
                        String newPass = editTextNewPassword.getText().toString();
                        String reTypePass = editTextReTypePassword.getText().toString();;

                        if(newPass.equals(reTypePass)){
                            listener.changePassword(currentPass, newPass);
                            //dialogInterface.dismiss();
                        } else{
                            //editTextReTypePassword.setError("Password doesn't match");
                            //editTextReTypePassword.requestFocus();
                        }
                    }
                });

        editTextCurrentPassword = view.findViewById(R.id.edit_current_password);
        editTextNewPassword = view.findViewById(R.id.edit_new_password);
        editTextReTypePassword = view.findViewById(R.id.edit_retype_password);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (PasswordDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface PasswordDialogListener {
        void changePassword(String oldPass, String newPass);
    }
}
