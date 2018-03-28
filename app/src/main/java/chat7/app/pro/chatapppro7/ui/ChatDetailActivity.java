package chat7.app.pro.chatapppro7.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import chat7.app.pro.chatapppro7.R;

public class ChatDetailActivity extends AppCompatActivity {
    public static final String TAG = ChatDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);
        String chatId = getIntent().getStringExtra("chatId");
        Log.d(TAG, "onCreate: " + chatId);
    }
}
