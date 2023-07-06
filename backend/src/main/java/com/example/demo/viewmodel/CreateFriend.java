package com.example.demo.viewmodel;

public class CreateFriend {

    private Long userId;

    private Long friendId;
    

    public CreateFriend(){

    }

    public CreateFriend(Long userId, Long friendId){
        super();
        this.userId=userId;
        this.friendId=friendId;
    }

    public Long getUserId(){
        return this.userId;
    }
    public void setUserId(Long userId){
        this.userId=userId;
    }

    public Long getFriendId(){
        return this.friendId;
    }
    public void setFriendId(Long friendId){
        this.friendId=friendId;
    }

}
