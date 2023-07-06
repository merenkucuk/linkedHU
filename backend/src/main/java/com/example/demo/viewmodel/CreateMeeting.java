package com.example.demo.viewmodel;

import javax.validation.constraints.NotNull;

import java.util.Date;



public class CreateMeeting{
	
	@NotNull
	private String title;
	
	@NotNull
	private String description;
	
    @NotNull
	private String image;

    @NotNull
	private String link;

	@NotNull
	private Date date;
	
	@NotNull
	private Long userId;
	
	public CreateMeeting() {
		
	}
	
	public CreateMeeting(String title, String description, String image, String link, Long userId) {
		super();
		this.title = title;
		this.description = description;
		this.image = image;
        this.link=link;
        this.userId=userId;
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
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

    public String getLink() {
		return this.link;
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
 
}
