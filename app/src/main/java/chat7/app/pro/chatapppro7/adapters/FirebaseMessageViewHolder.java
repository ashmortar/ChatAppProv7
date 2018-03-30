package chat7.app.pro.chatapppro7.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import chat7.app.pro.chatapppro7.R;
import chat7.app.pro.chatapppro7.models.Message;

/**
 * Created by Guest on 3/28/18.
 */

public class FirebaseMessageViewHolder extends RecyclerView.ViewHolder {
    View mView;
    Context mContext;

    public FirebaseMessageViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindMessage(Message message) {
        TextView senderNameView = (TextView) mView.findViewById(R.id.messageSenderView);
        TextView messageContentView = (TextView) mView.findViewById(R.id.messageContentView);

        senderNameView.setText(message.getSenderName() + ": ");
        messageContentView.setText(" " + message.getText());

    }

}
