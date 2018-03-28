package chat7.app.pro.chatapppro7.services;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
}
