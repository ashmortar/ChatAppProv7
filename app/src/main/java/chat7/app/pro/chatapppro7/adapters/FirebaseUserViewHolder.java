package chat7.app.pro.chatapppro7.adapters;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chat7.app.pro.chatapppro7.R;
import chat7.app.pro.chatapppro7.models.Chat;
import chat7.app.pro.chatapppro7.models.User;
import chat7.app.pro.chatapppro7.services.FirebaseService;
import chat7.app.pro.chatapppro7.ui.ChatDetailActivity;

public class FirebaseUserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View mView;
    Context mContext;
    FirebaseUser user;

    public FirebaseUserViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindUser(User user) {
        Log.d("bind user", "was called on " + user.getUserName());
        //bind views name and image if we use one
        TextView userNameTextView = (TextView) mView.findViewById(R.id.userNameTextView);
        //set user values to views
        userNameTextView.setText(user.getUserName());
    }

    @Override
    public void onClick(View view) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<User>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    users.add(snapshot.getValue(User.class));
                }
                int itemPosition = getLayoutPosition();
                String recipientId = users.get(itemPosition).getuId();
                Map<String, Boolean> chatMembers = new HashMap<>();
                String chatName = "Chat between " + user.getDisplayName() + " and " + users.get(itemPosition).getUserName();
                chatMembers.put(user.getUid(), true);
                chatMembers.put(recipientId, true);
                Chat newChat = new Chat(chatMembers);
                FirebaseService.createChat(newChat, chatName);
                Intent intent = new Intent(mContext, ChatDetailActivity.class);
                intent.putExtra("chatId", newChat.getPushId());
                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("User on click", databaseError.toString());
            }
        });

    }
}
