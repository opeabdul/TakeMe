package com.example.opeyemi.takeme.common;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.example.opeyemi.takeme.SplashScreen;
import com.example.opeyemi.takeme.model.User;
import com.example.opeyemi.takeme.newChat.UserListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class Common {
    public static User currentUser;

    public static final String FRIENDLY_MSG_LENGTH = "friendly_msg_length";

    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 3;

    public static void logout(Context context){
        currentUser = null;
        Intent intent = new Intent(context, SplashScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public static String handlePhoneNumber(String phoneNumber){

        phoneNumber = phoneNumber.replaceAll(" ","")
                .replaceAll("-","")
                .replaceAll("\\(","")
                .replaceAll("\\)", "");


        if(String.valueOf(phoneNumber.charAt(0)).equals("+")){
            phoneNumber = phoneNumber.replace("+234", "0");
        }
        return phoneNumber;
    }


    public static void  createNewChat(final String phonenumber) {
        final DatabaseReference userChatDb = FirebaseDatabase.getInstance().getReference().child("user").child(Common.currentUser.getPhoneNumber()).child("chat");
        userChatDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean chatExists = false;
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userChats : dataSnapshot.getChildren()) {
                        if (userChats.getValue().equals(phonenumber)) {
                            chatExists = true;
                        }
                    }
                    if (!chatExists) {
                        String newChatKey = userChatDb.push().getKey();


                        //initialize Current user and chatting partner database chat database with the new phone numbers
                        userChatDb.child(newChatKey).setValue(phonenumber);
                        DatabaseReference secondUserDb = FirebaseDatabase.getInstance().getReference().child("user").child(phonenumber).child("chat").child(newChatKey);
                        secondUserDb.setValue(Common.currentUser.getPhoneNumber());


                        Map<String, Object> newChatMap = new HashMap<>();
                        newChatMap.put(Common.currentUser.getPhoneNumber(), true);
                        newChatMap.put(phonenumber, true);

                        DatabaseReference chatInfoDb = FirebaseDatabase.getInstance().getReference().child("chat")
                                .child(newChatKey).child("info");
                        chatInfoDb.child("users").setValue(newChatMap);
                        chatInfoDb.child("id").setValue(newChatKey);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
