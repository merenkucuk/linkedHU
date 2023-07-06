package com.example.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="likes")
public class Like {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name="text")
  private String text;

  @Column(name="date")
  private Date date;

  @Column(name="user_id")
  private Long userId;

  @Column(name="post_id")
  private Long postId;

  public Like() {}

  public Like( Long userId, Long postId, String text, Date date){
    super();
	  
    this.userId=userId;
    this.text=text;
    this.date=date;
    this.postId=postId;
  }
  
  public Long getId() {
    return this.id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
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
