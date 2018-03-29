package chat7.app.pro.chatapppro7.services;



import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chat7.app.pro.chatapppro7.models.Chat;
import chat7.app.pro.chatapppro7.models.Message;
import chat7.app.pro.chatapppro7.models.User;

public class FirebaseService extends FirebaseMessagingService {

    public static void createUser(User user) {
        DatabaseReference mUserReference;
        mUserReference = FirebaseDatabase
                .getInstance()
                .getReference("users")
                .child(user.getuId());
//        DatabaseReference pushRef = mUserReference.push();
        mUserReference.setValue(user);
    }

    public static void createChat(Chat chat, String chatName) {
        DatabaseReference mChatRef = FirebaseDatabase.getInstance().getReference("chats");
        DatabaseReference pushRef = mChatRef.push();
        String pushId = pushRef.getKey();
        chat.setPushId(pushId);
        pushRef.setValue(chat);

        for (int i = 0; i < chat.getUserIds().size(); i++) {
            DatabaseReference userRef = FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(chat.getUserIds().keySet().toArray()[i].toString())
                    .child("chats")
                    .child(chat.getPushId());
            userRef.setValue(chatName);
        }
    }

    public static void sendMessage(Message message, String chatId, List<String> recipientIds) {
        DatabaseReference mChatRef = FirebaseDatabase.getInstance().getReference("chats").child(chatId).child("messages").push();
        message.setPushId(mChatRef.getKey());
        mChatRef.setValue(message);

        DatabaseReference mNotificationRef = FirebaseDatabase.getInstance().getReference("/notificationRequests");

        for (int i = 0; i < recipientIds.size(); i++) {
            Map notification = new HashMap<>();
            notification.put("recipientId", recipientIds.get(i));
            notification.put("message", message.getText());
            mNotificationRef.push().setValue(notification);
        }
    }

    public static ArrayList<String> getChatMembers(String chatId) {
        final ArrayList<String> chatMembers = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("chats")
                .child(chatId)
                .child("userIds");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    chatMembers.add(snapshot.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("getChatMembers error", "onCancelled: " + databaseError.getDetails());
            }
        });

        return chatMembers;
    }

}
