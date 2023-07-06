package com.example.demo.viewmodel;

import java.util.Date;

public class AllPost {

    private String type;

    private String title;

    private String description;

    private String image; /// image for evet, job

    private String link; /// job

    private Date date;

    private Long id;

    public AllPost() {

    }

    public AllPost(Long id, String type, String title, String description, String image, String link,
            Date date) {
        super();
        this.id = id;
        this.type = type;
        this.title = title;
        this.description = description;
        this.image = image;
        this.link = link;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
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
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
