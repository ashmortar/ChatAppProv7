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

import java.util.ArrayList;
import java.util.List;

import chat7.app.pro.chatapppro7.R;
import chat7.app.pro.chatapppro7.models.Chat;
import chat7.app.pro.chatapppro7.services.FirebaseService;
import chat7.app.pro.chatapppro7.ui.ChatDetailActivity;

/**
 * Created by Guest on 3/28/18.
 */

public class FirebaseChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    String TAG = "wtf";
    View mView;
    Context mContext;
    FirebaseUser user;

    public FirebaseChatViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindChat(String chat) {
        TextView chatNameTextView = (TextView) mView.findViewById(R.id.chatNameTextView);
        chatNameTextView.setText(chat);
    }

    public void onClick(View view) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("chats");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> chatIds = new ArrayList<String>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    chatIds.add(snapshot.getKey());
                    Log.d(TAG, "onDataChange: " + snapshot.getKey());
                }
                int itemPosition = getLayoutPosition();
                Intent intent = new Intent(mContext, ChatDetailActivity.class);
                intent.putExtra("chatId", chatIds.get(itemPosition));
                intent.putStringArrayListExtra("chatMembers", FirebaseService.getChatMembers(chatIds.get(itemPosition)));
                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Chat on click", databaseError.toString());
            }
        });
    }
}
