package chat7.app.pro.chatapppro7.services;



import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import chat7.app.pro.chatapppro7.models.Chat;
import chat7.app.pro.chatapppro7.models.Message;
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

    public static void sendMessage(Message message, String chatId) {
        DatabaseReference mChatRef = FirebaseDatabase.getInstance().getReference("chats").child(chatId).child("messages").push();
        message.setPushId(mChatRef.getKey());
        mChatRef.setValue(message);
    }
}
