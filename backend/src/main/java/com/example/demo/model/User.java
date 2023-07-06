package com.example.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="user")

public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Column(name="role")
	private String role;
	
	@OneToMany(targetEntity = Event.class)
	private List<Event> events;

	@OneToMany(targetEntity = Job.class)
	private List<Job> jobs;

	@OneToMany(targetEntity = Meeting.class)
	private List<Meeting> meetings;

	@OneToMany(targetEntity=Like.class)
	private List<Like> likes;

	@OneToMany(targetEntity = Comment.class)
	private List<Comment> comments;
	
	//private List<Chat> chats;
	@OneToMany(targetEntity = Friend.class)
	private List<Friend> friends;

	public User() {}
	
	public User(String firstName, String lastName, String email, String password, String role) {
		super();
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.role=role;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}

	public List<Event> getEvents(){
		return this.events;
	}
	
	public void setEvents(List<Event> events){
		this.events=events;
	}

	public List<Job> getJobs(){
		return this.jobs;
	}
	
	public void setJobs(List<Job> jobs){
		this.jobs=jobs;
	}

	public List<Meeting> getMeetings(){
		return this.meetings;
	}
	
	public void setMeetings(List<Meeting> meetings){
		this.meetings=meetings;
	}

	public List<Like> getLikes(){
		return this.likes;
	}
	
	public void setLikes(List<Like> likes){
		this.likes=likes;
	}

	public List<Comment> getComments(){
		return this.comments;
	}
	
	public void setComments(List<Comment> comments){
		this.comments=comments;
	}
        /*
	public List<Chat> getChats(){
		return this.chats;
	}
	public void setChats(List<Chat> chats){
		this.chats=chats;
	}
        */
	public List<Friend> getFriends(){
		return this.friends;
	}
	
	public void setFriends(List<Friend> friends){
		this.friends=friends;
	}
	
}
