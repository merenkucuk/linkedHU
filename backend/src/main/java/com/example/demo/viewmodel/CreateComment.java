package com.example.demo.viewmodel;


public class CreateComment {

    private String text;
 
    private Long userId;

    private Long postId;

  public CreateComment() {
  }

  public CreateComment( Long userId, Long postId, String text){
    super();
    this.userId=userId;
    this.postId=postId;
    this.text=text;  
  }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
