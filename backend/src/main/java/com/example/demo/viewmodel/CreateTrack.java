package com.example.demo.viewmodel;



public class CreateTrack {

    private String type;

    private Long userId;

    private Long postId;

    public CreateTrack() {
    }

    public CreateTrack(Long userId, Long postId, String type) {
        this.userId = userId;
        this.postId = postId;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

}
