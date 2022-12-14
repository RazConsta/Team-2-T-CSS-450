package com.example.cultivate_chat_app.ui.chat_room.create_room;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.cultivate_chat_app.R;
import com.example.cultivate_chat_app.io.RequestQueueSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NewRoomViewModel extends AndroidViewModel {

    private final MutableLiveData<Chat> mChat;

    public NewRoomViewModel(@NonNull Application application) {
        super(application);
        mChat = new MutableLiveData<>();
        mChat.setValue(new Chat(0));
    }

    /**
     * Add observer for the chat id return after create chat room
     * @param owner
     * @param observer
     */
    public void addChatIdObserver(@NonNull LifecycleOwner owner,
                                  @Nullable Observer<?super Chat> observer) {
        mChat.observe(owner, observer);
    }

    private void handleError(VolleyError volleyError) {
        Log.e("Error", volleyError.getLocalizedMessage());
    }
    /**
     * send a post request to create a chat room
     * @param jwt encrypt header
     * @param roomName room name for the chat room
     */
    public void connectCreateRoom(String jwt, String roomName, int memberId) {
        String url = getApplication().getResources().getString(R.string.room_url);
        JSONObject body = new JSONObject();
        try {
            body.put("name", roomName);
            body.put("memberid", memberId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body,
                this::handleResultAddLoginMember,
                this::handleError) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", jwt);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
                .addToRequestQueue(request);
    }

    private void handleResultAddLoginMember(JSONObject result){
        JSONObject response = result;
        Log.e("ERR", response.toString());
        if (response.has("message")) {
            Log.d("CHATID", "Success create a chat room!");
        } else {
            Log.e("CHATID", "No chat id found!");
        }
    }

    private void handleResultAddChosenMember(JSONObject result) {
        JSONObject response = result;
        if (response.has("message")) {
            Log.d("CHATID", "Success add members!");
        } else {
            Log.e("CHATID", "No chat id found!");
        }
    }

    public void connectUpdateSelectMember(String jwt) {
        //https://cultivate-app-web-service.herokuapp.com/room/update
        String url = getApplication().getResources().getString(R.string.room_url_update);
        Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                null,
                this::handleResultAddChosenMember,
                this::handleError){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", jwt);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
                .addToRequestQueue(request);
    }
}
