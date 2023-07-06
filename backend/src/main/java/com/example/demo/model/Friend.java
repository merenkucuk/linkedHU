package com.example.demo.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="friend")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_Id")
    private Long userId;

    @Column(name="friend_Id")
    private Long friendId;
    
    @Column(name="date")
    private Date date;

    public Friend() {}

    public Friend(Long userId, Long friendId) {
        super();
        
        this.userId=userId;
        this.friendId=friendId;
        this.date=new Date();
    }

    public Long getId() {
        return this.id;
    }

    public Date getDate() {
        return this.date;
    }
    
    public Long getUserId() {
        return this.userId;
    }
    
    public void setUserId(Long userId) {
        this.userId=userId;
    }

    public Long getFriendId() {
        return this.friendId;
    }
    
    public void setFriendId(Long friendId) {
        this.friendId=friendId;
    }

}
