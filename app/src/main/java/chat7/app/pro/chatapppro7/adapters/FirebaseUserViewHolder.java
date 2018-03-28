package chat7.app.pro.chatapppro7.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import chat7.app.pro.chatapppro7.R;
import chat7.app.pro.chatapppro7.models.User;

/**
 * Created by Guest on 3/28/18.
 */

public class FirebaseUserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View mView;
    Context mContext;

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
        Toast.makeText(mContext, "Take us to the Chat page", Toast.LENGTH_SHORT).show();
    }
}
