package com.example.varsanigpt;

public class ChatModel {

    private String msg;
    private String chatBy;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getChatBy() {
        return chatBy;
    }

    public void setChatBy(String chatBy) {
        this.chatBy = chatBy;
    }


    public ChatModel(String msg, String chatBy) {
        this.msg = msg;
        this.chatBy = chatBy;
    }
}

