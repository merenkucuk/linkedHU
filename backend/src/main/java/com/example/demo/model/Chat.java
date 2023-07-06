package com.example.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="sender_Id")
    private Long senderId;

    @Column(name="receiver_Id")
    private Long receiverId;

    @Column(name="message")
    private String message;
    
    @Column(name="date")
    private Date date;

    public Chat(){}

    public Chat(Long senderId, Long receiverId, String message){
        super();
        
        this.senderId=senderId;
        this.receiverId=receiverId;
        this.message=message;
        this.date= new Date();
    }
    
    public Long getId(){
        return this.id;
    }

    public Date getDate() {
        return date;
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
