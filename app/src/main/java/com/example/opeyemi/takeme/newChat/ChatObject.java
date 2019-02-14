package com.example.opeyemi.takeme.newChat;

import com.example.opeyemi.takeme.model.User;

import java.io.Serializable;
import java.util.ArrayList;

public class ChatObject implements Serializable {

    private String mChatId;
    private String mSenderId;
    private String mMessage;
    private String mImage;

    private ArrayList<User> userObjectArrayList = new ArrayList<>();




    public ChatObject(String chatId){
        mChatId = chatId;
    }

    public ChatObject(String chatId, String sender, String message, String image){
        mChatId = chatId;
        mSenderId = sender;
        mMessage = message;
        mImage = image;
    }

    public void addUserToChat(User user){
        userObjectArrayList.add(user);
    }

    public ArrayList<User> getUserObjectArrayList() {
        return userObjectArrayList;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String mImage) {
        this.mImage = mImage;
    }

    public String getSenderId() {
        return mSenderId;
    }

    public void setSenderId(String senderId) {
        this.mSenderId = senderId;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }

    public String getChatId() {
        return mChatId;
    }

    public void setmChatId(String chatId) {
        this.mChatId = chatId;
    }
}
