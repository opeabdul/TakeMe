package com.example.opeyemi.takeme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.opeyemi.takeme.common.Common;
import com.example.opeyemi.takeme.newChat.ChatListAdapter;
import com.example.opeyemi.takeme.newChat.ChatObject;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewChatActivity extends AppCompatActivity {

    private RecyclerView chatListRecyclerView;
    private ChatListAdapter chatListAdapter;
    private ArrayList<ChatObject> chatList;
    private LinearLayoutManager linearLayoutManager;

    String mChatId;

    DatabaseReference mChatDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);

        if(getIntent() != null){
            mChatId = getIntent().getExtras().getString("ChatId");
        }


        mChatDB = FirebaseDatabase.getInstance().getReference().child("chat").child(mChatId);

        ImageView sendButton = findViewById(R.id.send_message_image_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });


        initializeRecyclerView();
        getChatMessages();

    }

    private void sendMessage(){
        EditText messageEditText = findViewById(R.id.send_edit_text);

        if (!messageEditText.getText().toString().isEmpty()){

            DatabaseReference newMessageDB = mChatDB.push();

            Map newMessageMap = new HashMap<>();
            newMessageMap.put("text", messageEditText.getText().toString());
            newMessageMap.put("creator", Common.currentUser.getPhoneNumber());

            newMessageDB.updateChildren(newMessageMap);
        }

        messageEditText.setText(null);

    }

    public void getChatMessages(){
        mChatDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()){
                    String creator = "";
                    String  text= "";

                    if (dataSnapshot.child("creator").getValue() != null){
                        creator = dataSnapshot.child("creator").getValue().toString();
                    }
                    if (dataSnapshot.child("text").getValue() != null){
                        text = dataSnapshot.child("text").getValue().toString();
                    }

                    ChatObject chatObject = new ChatObject(dataSnapshot.getKey(), creator, text);

                    chatList.add(chatObject);
                    chatListAdapter.notifyDataSetChanged();
                    linearLayoutManager.scrollToPosition(chatList.size()-1);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initializeRecyclerView() {
        chatList = new ArrayList<>();
        chatListRecyclerView =  findViewById(R.id.chat_list_recycler_view);
        chatListAdapter = new ChatListAdapter(chatList);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL,false);
        chatListRecyclerView.setHasFixedSize(true);
        chatListRecyclerView.setLayoutManager(linearLayoutManager);
        chatListRecyclerView.setAdapter(chatListAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(Common.currentUser == null){
            startActivity(new Intent(NewChatActivity.this, SignInActivity.class));
        }
    }

}
