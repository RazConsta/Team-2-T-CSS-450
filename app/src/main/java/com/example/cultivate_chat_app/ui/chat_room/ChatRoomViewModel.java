package com.example.cultivate_chat_app.ui.chat_room;

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
import com.android.volley.toolbox.Volley;
import com.example.cultivate_chat_app.R;
import com.example.cultivate_chat_app.ui.chats.ChatRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatRoomViewModel extends AndroidViewModel {
    private MutableLiveData<List<Room>> mRoomList;

    public ChatRoomViewModel(@NonNull Application application) {
        super(application);
        mRoomList = new MutableLiveData<>();
        mRoomList.setValue(new ArrayList<>());
    }

    public void addRoomListObserver(@NonNull LifecycleOwner owner,
                                    @Nullable Observer<?super List<Room>> observer){
        mRoomList.observe(owner,observer);
    }

    public void resetRoom() { mRoomList.setValue(new ArrayList<>());}

    public void connectRoom(int roomId, String jwt) {

        String url = getApplication().getResources().getString(R.string.base_url) +
                "room/" + roomId;
    Log.d("Error", "JWT: " + jwt);
        Request<JSONObject> request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                result -> handleResult(result),
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
        Volley.newRequestQueue(getApplication().getApplicationContext())
                .add(request);
    }

    private void handleResult(final JSONObject result) {
        MutableLiveData<List<Room>> list = mRoomList;
        List<Integer> roomID = new ArrayList<>();
        try {
            JSONObject response = result;
            if (response.has("rows")) {
                JSONArray rows = response.getJSONArray("rows");
                for (int room = 0; room < rows.length(); room++) {
                    JSONObject roomJson = rows.getJSONObject(room);
                    int chatid = roomJson.getInt("chatid");
                    if (!roomID.contains(chatid)) {
                        roomID.add(chatid);
                    }
                }
                for (int i = 0; i< roomID.size(); i++){
                    JSONObject jsonRoom = rows.getJSONObject(i);
                    Room room = new Room(
                            jsonRoom.getString("name"),
                            jsonRoom.getString("message"));
                    if(!list.getValue().contains(room))
                        list.getValue().add(room);
                }
            } else {
                Log.e("ERROR", "No Room Exist In Server!");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ERROR", e.getMessage());
        }
        list.setValue(list.getValue());
    }

    /**
     * handle error from server
     * @param error error
     */
    private void handleError(final VolleyError error) {
        throw new IllegalStateException(error.getMessage());
    }
}