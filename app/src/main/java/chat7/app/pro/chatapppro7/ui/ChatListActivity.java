package chat7.app.pro.chatapppro7.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import chat7.app.pro.chatapppro7.R;
import chat7.app.pro.chatapppro7.adapters.FirebaseChatViewHolder;
import chat7.app.pro.chatapppro7.adapters.FirebaseUserViewHolder;
import chat7.app.pro.chatapppro7.models.Chat;
import chat7.app.pro.chatapppro7.models.User;

public class ChatListActivity extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static final String TAG = ChatListActivity.class.getSimpleName();
    private Query query = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("chats");
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @BindView(R.id.chatTextView)
    TextView mChatTextView;
    @BindView(R.id.chatRecyclerView)
    RecyclerView mChatRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        ButterKnife.bind(this);
        setupFirebaseAdapter();
    }

    private void setupFirebaseAdapter() {
        FirebaseRecyclerOptions<String> options = new FirebaseRecyclerOptions.Builder<String>()
                .setQuery(query, String.class)
                .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<String, FirebaseChatViewHolder>(options) {
            @Override
            protected void onBindViewHolder(FirebaseChatViewHolder holder, int position, String model) {
                holder.bindChat(model);
            }

            @NonNull
            @Override
            public FirebaseChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.chat_list_item, parent, false);

                return new FirebaseChatViewHolder(view);
            }
        };
        mChatRecyclerView.setHasFixedSize(true);
        mChatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mChatRecyclerView.setAdapter(mFirebaseAdapter);
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
