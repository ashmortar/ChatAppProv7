package chat7.app.pro.chatapppro7.services;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import chat7.app.pro.chatapppro7.models.Chat;
import chat7.app.pro.chatapppro7.models.User;

public class FirebaseService {

    public static void createUser(User user) {
        DatabaseReference mUserReference;
        mUserReference = FirebaseDatabase
                .getInstance()
                .getReference("users")
                .child(user.getuId());
//        DatabaseReference pushRef = mUserReference.push();
        mUserReference.setValue(user);
    }

    public static void createChat(Chat chat) {
        DatabaseReference mChatRef = FirebaseDatabase.getInstance().getReference("chats");
        DatabaseReference pushRef = mChatRef.push();
        String pushId = pushRef.getKey();
        chat.setPushId(pushId);
        pushRef.setValue(chat);

        for (int i = 0; i < chat.getUserIds().size(); i++) {
            DatabaseReference userRef = FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(chat.getUserIds().get(i))
                    .child("chats").push();
            userRef.setValue(chat.getPushId());
        }
    }
}
