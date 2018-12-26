package com.example.opeyemi.takeme.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import de.hdodenhof.circleimageview.CircleImageView;
import com.example.opeyemi.takeme.R;

public class MessageViewHolder extends RecyclerView.ViewHolder {

    public TextView messageTextView;
    public ImageView messageImageView;
    public TextView messengerTextView;
    public CircleImageView messengerImageView;

    public MessageViewHolder(View v) {
        super(v);
        messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
        messageImageView = (ImageView) itemView.findViewById(R.id.messageImageView);
        messengerTextView = (TextView) itemView.findViewById(R.id.messengerTextView);
        messengerImageView = (CircleImageView) itemView.findViewById(R.id.messengerImageView);
    }
}
