package com.example.demo.viewmodel;

public class Post {

    private Long userId;

    private String type;

    public Post(){

    }
    
    public Post(String type,Long userId) {
		super();
		this.type = type;
        this.userId= userId;
		
	}

    public Long getUserId(){
        return this.userId;
    }

    public void setType(Long userId){
        this.userId=userId;
    }

    public String getType(){
        return this.type;
    }

    public void setType(String type){
        this.type=type;
    }

}
