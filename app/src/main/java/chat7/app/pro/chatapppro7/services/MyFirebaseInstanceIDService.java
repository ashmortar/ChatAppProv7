package chat7.app.pro.chatapppro7.services;


import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        sendRegistrationToServer(refreshedToken);
    }

    public static void sendRegistrationToServer(String refreshedToken) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("tokens").push();
        reference.setValue(refreshedToken);
    }
}
