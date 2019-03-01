package com.example.opeyemi.takeme;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.opeyemi.takeme.common.Common;
import com.example.opeyemi.takeme.model.User;
import com.example.opeyemi.takeme.newChat.ChatListAdapter;
import com.example.opeyemi.takeme.newChat.ChatObject;
import com.example.opeyemi.takeme.newChat.SendNotification;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.sql.Timestamp;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NewChatActivity extends AppCompatActivity {

    private RecyclerView chatListRecyclerView;
    private ChatListAdapter chatListAdapter;
    private ArrayList<ChatObject> chatList;
    private LinearLayoutManager linearLayoutManager;
    private EditText messageEditText;


    ChatObject mChatObject;

    DatabaseReference mChatMessagesDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        if(getIntent() != null){
            mChatObject = (ChatObject) getIntent().getSerializableExtra("chatObject");
        }

        mChatMessagesDb = FirebaseDatabase.getInstance().getReference().child("chat").child(mChatObject.getChatId()).child("messages");

        ImageView sendMessageButton =  findViewById(R.id.send_message_image_button);
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });


        ImageView sendImageButton = findViewById(R.id.send_image);
        sendImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });



        messageEditText = findViewById(R.id.send_edit_text);
        addListenerToMessageEditText(sendMessageButton);


        initializeRecyclerView();
        getChatMessages();

    }

    private void addListenerToMessageEditText(final ImageView sendMessageButton){
        messageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().isEmpty()){
                    sendMessageButton.setBackground(getResources().getDrawable(R.drawable.background_rounded_grey));
                } else {
                    sendMessageButton.setBackground(getResources().getDrawable(R.drawable.background_rounded_blue));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void sendMessage(){



        if (!messageEditText.getText().toString().isEmpty()){


            Timestamp timestamp = new Timestamp(new Date().getTime());

            DatabaseReference newMessageDB = mChatMessagesDb.push();

            HashMap<String, Object> newMessageMap = new HashMap<>();
            newMessageMap.put("text", messageEditText.getText().toString());
            newMessageMap.put("creator", Common.currentUser.getName());
            newMessageMap.put("chatId", Common.currentUser.getPhoneNumber());
            newMessageMap.put("timestamp", timestamp.getTime());

            updateChatDatabaseWithMessage( newMessageMap, newMessageDB);

            messageEditText.setText(null);


        }



    }

    private final int IMAGE_REQUEST_CODE = 1;


    private void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == IMAGE_REQUEST_CODE){
                if (data != null){
                    Uri imageUri = data.getData();
                    sendImageMessage(imageUri);
                }
            }
        }
    }

    private void sendImageMessage(final Uri imageUri){

        final String key = mChatMessagesDb.push().getKey();

        final StorageReference chatImageStorage = FirebaseStorage.getInstance().getReference()
                .child("chat").child(key);

        UploadTask uploadTask = chatImageStorage.putFile(imageUri);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                chatImageStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Timestamp timestamp = new Timestamp(new Date().getTime());
                        Map<String, Object> newMessageMap = new HashMap<>();
                        newMessageMap.put("image", uri.toString());
                        newMessageMap.put("creator", Common.currentUser.getName());
                        newMessageMap.put("chatId", Common.currentUser.getPhoneNumber());
                        newMessageMap.put("timestamp", timestamp.getTime());

                        updateChatDatabaseWithMessage(newMessageMap, mChatMessagesDb.child(key));

                    }
                });
            }
        });
    }

    public void updateChatDatabaseWithMessage(Map<String, Object> messageMap, DatabaseReference messageDb){

        messageDb.updateChildren(messageMap);

        String message;

        if (messageMap.get("text") != null)
            message = messageMap.get("text").toString();
        else
            message = "Media sent";

        for (User user : mChatObject.getUserObjectArrayList()){
            if(!user.getPhoneNumber().equals(Common.currentUser.getPhoneNumber())){
                new SendNotification(message, "New Message", user.getNotificationKey());
            }
        }

    }

    public void getChatMessages(){
        mChatMessagesDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()){
                    String creator = "";
                    String  text= "";
                    String image="";
                    String messageTime="";
                    String chatId = "";

                    if (dataSnapshot.child("creator").getValue() != null)
                        creator = dataSnapshot.child("creator").getValue().toString();

                    if (dataSnapshot.child("text").getValue() != null)
                        text = dataSnapshot.child("text").getValue().toString();

                    if (dataSnapshot.child("image").getValue() != null)
                        image = dataSnapshot.child("image").getValue().toString();

                    if (dataSnapshot.child("timestamp").getValue() != null){
                        messageTime = dataSnapshot.child("timestamp").getValue().toString();
                        messageTime = (String) DateFormat.format("HH:mm", new Date(Long.valueOf(messageTime)));
                    }

                    if(dataSnapshot.child("chatId").getValue() != null)
                        chatId = dataSnapshot.child("chatId").getValue().toString();


                    ChatObject chatObject = new ChatObject(chatId, creator, text, image, messageTime);

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
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(getApplicationContext(), FindUserActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_chat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(Common.currentUser == null){
            startActivity(new Intent(NewChatActivity.this, SignInActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
    }
}
