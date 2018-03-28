package chat7.app.pro.chatapppro7.ui;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;
import chat7.app.pro.chatapppro7.R;
import chat7.app.pro.chatapppro7.adapters.FirebaseMessageViewHolder;
import chat7.app.pro.chatapppro7.models.Message;
import chat7.app.pro.chatapppro7.services.FirebaseService;

public class ChatDetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = ChatDetailActivity.class.getSimpleName();
    private Query query;
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    private String chatId;
    @BindView(R.id.messageRecyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.messageEditText) EditText mMessageEditText;
    @BindView(R.id.messageSendButton) Button mMessageSendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);
        ButterKnife.bind(this);
        chatId = getIntent().getStringExtra("chatId");
        query = FirebaseDatabase.getInstance().getReference("chats").child(chatId).child("messages");

        mMessageSendButton.setOnClickListener(this);
        setUpFirebaseAdapter();

    }

    private void setUpFirebaseAdapter() {

        FirebaseRecyclerOptions<Message> options = new FirebaseRecyclerOptions.Builder<Message>()
                .setQuery(query, Message.class)
                .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Message, FirebaseMessageViewHolder>(options) {
            @Override
            protected void onBindViewHolder(FirebaseMessageViewHolder holder, int position, Message model) {
                holder.bindMessage(model);

            }

            @NonNull
            @Override
            public FirebaseMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.message_list_item, parent, false);

                return new FirebaseMessageViewHolder(view);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view == mMessageSendButton) {
            String messageContent = mMessageEditText.getText().toString();
            if (messageContent != "") {
                String senderId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String senderName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                String text = messageContent;
                Message newMessage = new Message(senderId, senderName, text);
                FirebaseService.sendMessage(newMessage, chatId);
            } else {
                Toast.makeText(this, "please enter a message", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFirebaseAdapter.stopListening();
    }
}
