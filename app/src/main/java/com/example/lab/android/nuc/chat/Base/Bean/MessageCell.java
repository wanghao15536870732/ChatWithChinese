package com.example.lab.android.nuc.chat.Base.Bean;

public class MessageCell {
    private String userPhotoUrl;

    private String cilentID;

    private String username;

    public MessageCell(String userPhotoUrl,String cilentID,String username){
        this.userPhotoUrl = userPhotoUrl;
        this.cilentID = cilentID;
        this.username = username;
    }

    public String getCilentID() {
        return cilentID;
    }

    public String getUsername() {
        return username;
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }
}
