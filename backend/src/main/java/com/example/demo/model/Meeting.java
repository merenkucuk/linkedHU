package com.example.demo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="meeting")
public class Meeting {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="description")
	private String description;
	
	@Column(name="image")
	private String image;

	@Column(name="link")
	private String link;

	@Column(name="date")
	private Date date;
	
	@Column(name="user_id")
	private Long userId;

	@OneToMany(targetEntity=Like.class)
	private List<Like> likes;

	@OneToMany(targetEntity = Comment.class)
	private List<Comment> comments;
	
	public Meeting() {}

	public Meeting(String title, String description, String image, String link, Date date, Long userId) {
		super();
		
		this.title = title;
		this.description = description;
		this.image=image;
		this.link = link;
		this.date=date;
		this.userId=userId;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return this.image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}

	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}

	public Date getDate(){
		return this.date;
	}
	
	public void setDate(Date date){
		this.date=date;
	}

	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<Like> getLikes() {
		return this.likes;
	}
	
	public void setLikes(List<Like> likes) {
		this.likes=likes;
	}

	public List<Comment> getComments() {
		return this.comments;
	}
	
	public void setComment(List<Comment> comments) {
		this.comments=comments;
	}
}
