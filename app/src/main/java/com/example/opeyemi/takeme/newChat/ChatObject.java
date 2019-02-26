package com.example.opeyemi.takeme.newChat;

import com.example.opeyemi.takeme.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class ChatObject implements Serializable {

    private String mChatId;
    private String mSenderId;
    private String mMessage;
    private String mImage;
    private String mTimeStamp;

    private ArrayList<User> userObjectArrayList = new ArrayList<>();




    public ChatObject(String chatId){
        mChatId = chatId;
    }

    public ChatObject(String chatId, String senderId, String message, String image, String timeStamp){
        mChatId = chatId;
        mSenderId = senderId;
        mMessage = message;
        mImage = image;
        mTimeStamp = timeStamp;
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

    public void setChatId(String chatId) {
        this.mChatId = chatId;
    }

    public String getTimeStamp() {
        return mTimeStamp;
    }

    public void setTimeStamp(String mCreateAt) {
        this.mTimeStamp = mCreateAt;
    }
}

