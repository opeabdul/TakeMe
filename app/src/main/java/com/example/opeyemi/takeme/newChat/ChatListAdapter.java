package com.example.opeyemi.takeme.newChat;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.opeyemi.takeme.R;
import com.example.opeyemi.takeme.common.Common;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatListAdapter extends RecyclerView.Adapter {

    private ArrayList<ChatObject> mChatList;

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    public ChatListAdapter(ArrayList<ChatObject> chatList) {
        mChatList = chatList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.item_chat_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.item_chat_received, parent, false);
            return new ReceivedMessageHolder(view);
        }


        return null;

    }

    @Override
    public int getItemViewType(int position) {
        ChatObject chatObject = mChatList.get(position);

        if (chatObject.getChatId().equals(Common.currentUser.getPhoneNumber())) {
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        ChatObject chatObject = mChatList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(chatObject);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(chatObject);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mChatList.size();
    }

    public class SentMessageHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout mLayout;
        private TextView message;
        private ImageView imageView;
        private TextView messageTime;

        public SentMessageHolder(View itemView) {
            super(itemView);

            mLayout = itemView.findViewById(R.id.layout);
            message = itemView.findViewById(R.id.chat);
            imageView = itemView.findViewById(R.id.image_message);
            messageTime = itemView.findViewById(R.id.text_message_time);
        }


        public void bind(ChatObject chat) {
            if (!chat.getMessage().equals("")) {
                message.setText(chat.getMessage());
                messageTime.setText(chat.getTimeStamp());
                message.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
            }

            if (chat.getImage() != "") {

                Picasso.with(imageView.getContext()).load(chat
                        .getImage()).into(imageView);
                messageTime.setText(chat.getTimeStamp());
                imageView.setVisibility(View.VISIBLE);
                message.setVisibility(View.GONE);
            }


        }
    }


    public class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        private TextView message;
        private TextView sender;
        private ConstraintLayout layout;
        private ImageView senderImage;
        private ImageView imageMessage;
        private TextView messageTime;

        public ReceivedMessageHolder(View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.text_message_body);
            sender = itemView.findViewById(R.id.text_message_name);
            layout = itemView.findViewById(R.id.received_chat_constraintLayout);
            senderImage = itemView.findViewById(R.id.image_message_profile);
            imageMessage = itemView.findViewById(R.id.image_message);
            messageTime = itemView.findViewById(R.id.text_message_time);
        }

        public void bind(ChatObject chat) {

            if (chat.getMessage() != "") {
                message.setText(chat.getMessage());
                messageTime.setText(chat.getTimeStamp());
                message.setVisibility(View.VISIBLE);
                imageMessage.setVisibility(View.GONE);

            }

            if (chat.getImage() != "") {

                Picasso.with(imageMessage.getContext()).load(chat
                        .getImage()).into(imageMessage);
                messageTime.setText(chat.getTimeStamp());
                imageMessage.setVisibility(View.VISIBLE);
                message.setVisibility(View.GONE);

            }

            sender.setText(chat.getSenderId());
        }
    }
}


