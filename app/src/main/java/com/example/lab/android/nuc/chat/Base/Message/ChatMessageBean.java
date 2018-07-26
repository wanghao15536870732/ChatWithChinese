package com.example.lab.android.nuc.chat.Base.Message;

public class ChatMessageBean {


    //图片消息
    private String imageUrl;
    private String imageIconUrl;
    private String imageLocal;
    private @ChatConst.SendState int sendState;
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setImageLocal(String imageLocal) {
        this.imageLocal = imageLocal;
    }
    public String getImageLocal() {
        return imageLocal;
    }
    public void setImageIconUrl(String imageIconUrl) {
        this.imageIconUrl = imageIconUrl;
    }

    public String getImageIconUrl() {
        return imageIconUrl;
    }

    public int getSendState() {
        return sendState;
    }

    public void setSendState(int sendState) {
        this.sendState = sendState;
    }

    //文本消息
    private String content;
    private String content_time;
    private String UserName;
    private String UserId;
    private String UserHeadIcon;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getContent_time() {
        return content_time;
    }
    public void setContent_time(String content_time) {
        this.content_time = content_time;
    }
    public String getUserName() {
        return UserName;
    }
    public void setUserName(String userName) {
        UserName = userName;
    }
    public String getUserId() {
        return UserId;
    }
    public void setUserId(String userId) {
        UserId = userId;
    }
    public String getUserHeadIcon() {
        return UserHeadIcon;
    }
    public void setUserHeadIcon(String userHeadIcon) {
        UserHeadIcon = userHeadIcon;
    }


    //语音消息
    private float time;
    private String filePath;
    public float getTime() {
        return time;
    }
    public void setTime(float time) {
        this.time = time;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }



    private int type;
    private int messagetype;

    public int getMessagetype() {
        return messagetype;
    }

    public void setMessagetype(int messagetype) {
        this.messagetype = messagetype;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ChatMessageBean(float time, String filePath,
                           int type, int messagetype,
                           String UserId, String UserName, String UserHeadIcn, String content, String content_time,
                           String imageUrl, String imageIconUrl, String imageLocal,int sendState) {
        super();
        this.time = time;
        this.filePath = filePath;
        this.UserId = UserId;
        this.UserName = UserName;
        this.UserHeadIcon = UserHeadIcn;
        this.content = content;
        this.content_time = content_time;
        this.imageUrl = imageUrl;
        this.imageIconUrl = imageIconUrl;
        this.imageLocal = imageLocal;
        this.type = type;
        this.messagetype = messagetype;
        this.sendState = sendState;
    }
}
