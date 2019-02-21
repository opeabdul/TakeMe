package com.example.opeyemi.takeme;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.opeyemi.takeme.bottomNavigationViewHelper.BaseActivity;
import com.example.opeyemi.takeme.common.Common;
import com.example.opeyemi.takeme.model.User;
import com.example.opeyemi.takeme.newChat.ChatObject;
import com.example.opeyemi.takeme.newChat.SendNotification;
import com.example.opeyemi.takeme.newChat.UserListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FindUserActivity extends BaseActivity {

    private RecyclerView userListRecyclerView;
    private UserListAdapter mUserListAdapter;
    private ArrayList<User> mContactList;
    private ArrayList<User> mUserList;
    private ArrayList<ChatObject> mChatList;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initializeRecyclerView();
        initializeUserForOneSignalUse();
        requestContactAccessPermission();
        getUserList();
        getUserChatList();
    }



    private void initializeUserForOneSignalUse() {
        OneSignal.startInit(this).init();
        OneSignal.setSubscription(true);
        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                FirebaseDatabase.getInstance().getReference().child("user")
                        .child(Common.currentUser.getPhoneNumber()).child("notificationKey")
                        .setValue(userId);

            }
        });
        OneSignal.setInFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification);
    }

    private void initializeRecyclerView() {
        mContactList = new ArrayList<>();
        mUserList = new ArrayList<>();
        mChatList = new ArrayList<>();
        userListRecyclerView =  findViewById(R.id.user_list_recycler_view);
        mUserListAdapter = new UserListAdapter(mUserList, mChatList);
        mLinearLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL,false);
        userListRecyclerView.setHasFixedSize(true);
        userListRecyclerView.setLayoutManager(mLinearLayoutManager);
        userListRecyclerView.setAdapter(mUserListAdapter);
    }

    private void getUserChatList(){

        DatabaseReference mUserChatDb = FirebaseDatabase.getInstance().getReference().child("user")
                .child(Common.currentUser.getPhoneNumber()).child("chat");

        mUserChatDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot dataSnapshotIterator : dataSnapshot.getChildren()){
                        ChatObject mChat = new ChatObject(dataSnapshotIterator.getKey());

                        //do not add user twice to the chat list
                        boolean exists = false;
                        for (ChatObject mChatListIterator: mChatList){
                            if (mChatListIterator.getChatId().equals(mChat.getChatId())){
                                exists = true;
                            }
                        }
                        if (exists){
                            continue;
                        }
                        mChatList.add(mChat);
                        getChatData(mChat.getChatId());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getChatData(String chatId){
        DatabaseReference mChatDb = FirebaseDatabase.getInstance().getReference().child("chat")
                .child(chatId).child("info");
        mChatDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String chatId = "";

                    if (dataSnapshot.child("id").getValue() != null)
                        chatId = dataSnapshot.child("id").getValue().toString();

                    for (DataSnapshot userSnapshot : dataSnapshot.child("users").getChildren()){
                        for (ChatObject chat : mChatList){
                            if(chat.getChatId().equals(chatId)){
                                User user = new  User(userSnapshot.getKey());
                                chat.addUserToChat(user);
                                getUserData(user);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getUserData(User user) {
        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("user").child(user.getPhoneNumber());
        userDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = new User(dataSnapshot.getKey());

                if (dataSnapshot.child("notificationKey").getValue() != null){
                    user.setNotificationKey(dataSnapshot.child("notificationKey").getValue().toString());
                }

                for (ChatObject chat : mChatList){
                    for (User userIterator : chat.getUserObjectArrayList()){
                        if(userIterator.getPhoneNumber().equals(user.getPhoneNumber())){
                            userIterator.setNotificationKey(user.getNotificationKey());
                        }
                    }
                }
                mUserListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
    private void getContactList(){
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        while(phones.moveToNext()){
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            number = Common.handlePhoneNumber(number);

            User user = new User(name,null, number);
            mContactList.add(user);

                getUserList(user);

        }
    }
    */
    private void getUserList(){
        DatabaseReference userChatDb = FirebaseDatabase.getInstance().getReference().child("user")
                .child(Common.currentUser.getPhoneNumber()).child("chat");

        userChatDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("user");
                    for( DataSnapshot phoneChats : dataSnapshot.getChildren()){
                        if (phoneChats.getValue() != null){
                            final String currentPhoneNumber = phoneChats.getValue().toString();
                            Query query = userDb.orderByChild("phoneNumber").equalTo(currentPhoneNumber);
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()){
                                        String name = "", phone = "";
                                        if (dataSnapshot.child(currentPhoneNumber).child("phoneNumber").getValue() != null){
                                            phone = dataSnapshot.child(currentPhoneNumber).child("phoneNumber").getValue().toString();
                                        }

                                        if (dataSnapshot.child(currentPhoneNumber).child("name").getValue() != null){
                                            name = dataSnapshot.child(currentPhoneNumber).child("name").getValue().toString();
                                        }

                                        User newUser = new User(name, null, phone);
                                        mUserList.add(newUser);
                                        mUserListAdapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }


    private void requestContactAccessPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions( new String[] {Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_CONTACTS}, 1);
        }
    }

    @Override
    protected void onStart() {

        super.onStart();
        if(Common.currentUser == null){
            startActivity(new Intent(FindUserActivity.this, SignInActivity.class));
        }


    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_find_user;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_chat;
    }
}
