package com.example.opeyemi.takeme.newChat;

public class ChatObject {

    private String mChatId;
    private String mSenderId;
    private String mMessage;


    public ChatObject(String chatId){
        mChatId = chatId;
    }

    public ChatObject(String chatId, String sender, String message ){
        mChatId = chatId;
        mSenderId = sender;
        mMessage = message;
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
