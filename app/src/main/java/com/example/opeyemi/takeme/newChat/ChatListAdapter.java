package com.example.opeyemi.takeme.newChat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.opeyemi.takeme.ChatActivity;
import com.example.opeyemi.takeme.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListAdapterViewHolder> {

    private ArrayList<ChatObject> mChatList;


    public ChatListAdapter(ArrayList<ChatObject> chatList){
        mChatList = chatList;
    }

    @NonNull
    @Override
    public ChatListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_chat, parent,false);

        return new ChatListAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ChatListAdapterViewHolder holder, int position) {

        if (mChatList.get(position).getMessage() != "") {
            holder.message.setText(mChatList.get(position).getMessage());
            holder.message.setVisibility(View.VISIBLE);
            holder.imageView.setVisibility(View.GONE);
        }

        if (mChatList.get(position).getImage() != ""){

            Picasso.with(holder.imageView.getContext()).load(mChatList.get(position)
                    .getImage()).into(holder.imageView);

            holder.imageView.setVisibility(View.VISIBLE);
            holder.message.setVisibility(View.GONE);
        }

        holder.sender.setText(mChatList.get(position).getSenderId());
    }

    @Override
    public int getItemCount() {
        return mChatList.size();
    }

    public class ChatListAdapterViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout mLayout;
        private TextView sender;
        private TextView message;
        private ImageView imageView;

        public ChatListAdapterViewHolder(View itemView) {
            super(itemView);

            mLayout = itemView.findViewById(R.id.layout);
            sender = itemView.findViewById(R.id.sender);
            message = itemView.findViewById(R.id.chat);
            imageView = itemView.findViewById(R.id.image_message);
        }
    }

}