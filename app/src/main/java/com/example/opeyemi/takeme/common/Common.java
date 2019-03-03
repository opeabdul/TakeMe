package com.example.opeyemi.takeme.common;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.example.opeyemi.takeme.R;
import com.example.opeyemi.takeme.SignInActivity;
import com.example.opeyemi.takeme.model.User;
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

    public static void logout(Context context) {
        currentUser = null;
        Intent intent = new Intent(context, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public static String handlePhoneNumber(String phoneNumber) {

        phoneNumber = phoneNumber.replaceAll(" ", "")
                .replaceAll("-", "")
                .replaceAll("\\(", "")
                .replaceAll("\\)", "");


        if (String.valueOf(phoneNumber.charAt(0)).equals("+")) {
            phoneNumber = phoneNumber.replace("+234", "0");
        }
        return phoneNumber;
    }


    public static void createNewChat(final String phoneNumber) {

        if (!phoneNumber.equals(currentUser.getPhoneNumber())) {

            final DatabaseReference userChatDb = FirebaseDatabase.getInstance().getReference().child("user").child(Common.currentUser.getPhoneNumber()).child("chat");
            userChatDb.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean chatExists = false;
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot userChats : dataSnapshot.getChildren()) {
                            if (userChats.getValue().equals(phoneNumber)) {
                                chatExists = true;

                            }
                        }
                        if (!chatExists) {
                            String newChatKey = userChatDb.push().getKey();


                            //initialize Current user and chatting partner database chat database with the new phone numbers
                            userChatDb.child(newChatKey).setValue(phoneNumber);
                            DatabaseReference secondUserDb = FirebaseDatabase.getInstance().getReference().child("user").child(phoneNumber).child("chat").child(newChatKey);
                            secondUserDb.setValue(Common.currentUser.getPhoneNumber());


                            Map<String, Object> newChatMap = new HashMap<>();
                            newChatMap.put(Common.currentUser.getPhoneNumber(), true);
                            newChatMap.put(phoneNumber, true);

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

    public static void call(final Context context, String userName, final String userPhoneNumber) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(R.string.phonecall)
                .setMessage(context.getString(R.string.dialog_make_a_phone_call, userName, userPhoneNumber))
                .setPositiveButton(R.string.call, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + userPhoneNumber));
                        try {
                            context.startActivity(intent);
                        } catch (SecurityException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
