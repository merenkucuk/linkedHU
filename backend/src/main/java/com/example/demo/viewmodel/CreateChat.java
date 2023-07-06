package com.example.demo.viewmodel;

public class CreateChat {

    private Long senderId;

    private Long receiverId;

    private String message;

    public CreateChat(){

    }

    public CreateChat(Long senderId, Long receiverId, String message){
        super();
        this.senderId=senderId;
        this.receiverId=receiverId;
        this.message=message;
    }
    
    public Long getSenderId() {
        return this.senderId;
    }
    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return this.receiverId;
    }
    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

}
