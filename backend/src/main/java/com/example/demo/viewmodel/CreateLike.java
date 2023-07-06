package com.example.demo.viewmodel;

public class CreateLike {
    private Long userId;
    private Long postId;

    public CreateLike(){}

    public CreateLike(Long userId, Long postId){
        this.userId=userId;
        this.postId=postId;
    }

    public Long getUserId(){
        return this.userId;
    }
    public void setUserId(Long userId){
        this.userId=userId;
    }

    public Long getPostId(){
        return this.postId;
    }
    public void setPostId(Long postId){
        this.postId=postId;
    }
}
