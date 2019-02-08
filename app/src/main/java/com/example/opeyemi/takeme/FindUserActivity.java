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

import com.example.opeyemi.takeme.common.Common;
import com.example.opeyemi.takeme.model.User;
import com.example.opeyemi.takeme.newChat.ChatObject;
import com.example.opeyemi.takeme.newChat.UserListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FindUserActivity extends AppCompatActivity {

    private RecyclerView userListRecyclerView;
    private UserListAdapter mUserListAdapter;
    private ArrayList<User> mContactList;
    private ArrayList<User> mUserList;
    private ArrayList<ChatObject> mChatList;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);


        initializeRecyclerView();
        requestContactAccessPermission();
        getContactList();
        getUserChatList();
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

        DatabaseReference mUserChatDb = FirebaseDatabase.getInstance().getReference().child("user").child(Common.currentUser.getPhoneNumber()).child("chat");

        mUserChatDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot dataSnapshotIterator : dataSnapshot.getChildren()){
                        ChatObject mChat = new ChatObject(dataSnapshotIterator.getKey());
                        String chatKey = dataSnapshotIterator.getKey();
                        mChatList.add(mChat);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

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

    private void getUserList(final User user){

        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("user");

        Query query = userDb.orderByChild("phoneNumber").equalTo(user.getPhoneNumber());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String name = "", phone = "";
                        if (dataSnapshot.child(user.getPhoneNumber()).child("phoneNumber").getValue() != null){
                            phone = dataSnapshot.child(user.getPhoneNumber()).child("phoneNumber").getValue().toString();
                        }

                        if (dataSnapshot.child(user.getPhoneNumber()).child("name").getValue() != null){
                            name = dataSnapshot.child(user.getPhoneNumber()).child("name").getValue().toString();
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
}
