package chat7.app.pro.chatapppro7.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
import chat7.app.pro.chatapppro7.ui.ChatDetailActivity;

/**
 * Created by Guest on 3/28/18.
 */

public class FirebaseChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    View mView;
    Context mContext;
    FirebaseUser user;

    public FirebaseChatViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindChat(Chat chat) {
        TextView chatNameTextView = (TextView) mView.findViewById(R.id.chatNameTextView);
        chatNameTextView.setText(chat.getPushId());
    }

    public void onClick(View view) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("chats");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Chat> chats = new ArrayList<Chat>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    chats.add(snapshot.getValue(Chat.class));
                }
                int itemPosition = getLayoutPosition();
                Intent intent = new Intent(mContext, ChatDetailActivity.class);
                intent.putExtra("chatId", chats.get(itemPosition).getPushId());
                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Chat on click", databaseError.toString());
            }
        });
    }
}
