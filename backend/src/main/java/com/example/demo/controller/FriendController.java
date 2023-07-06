package com.example.demo.controller;

import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourseNotFoundException;
import com.example.demo.exception.UnauthorizedAction;
import com.example.demo.model.Friend;
import com.example.demo.model.Security;
import com.example.demo.model.User;
import com.example.demo.viewmodel.CreateFriend;
import com.example.demo.viewmodel.UserAccount;
import com.example.demo.repository.FriendRepository;
import com.example.demo.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:3000")
@Validated
@RestController
@RequestMapping("/api/V1/")
public class FriendController {
    
    @Autowired
	private UserRepository userRepository;

    @Autowired
	private FriendRepository friendRepository;

    //get all friends table
    @GetMapping("/friends")
	public List<Friend> getAllFriend() {
		return friendRepository.findAll();
	}

    //add and delete friend
	@PutMapping("/friends")
	public ResponseEntity<Friend> updateFriend(@RequestBody CreateFriend friendDetail) throws NoSuchProviderException {
		
		Friend friend =new Friend(
            friendDetail.getUserId(),
            friendDetail.getFriendId()
        );

        User user = userRepository.findById(friendDetail.getUserId()).orElseThrow(() -> new ResourseNotFoundException("User Not Found"));
        for (Friend friend2 : user.getFriends()) {
            if(friend2.getFriendId()==friendDetail.getFriendId()){
                user.getFriends().remove(friend2);
                friendRepository.delete(friend2);
                userRepository.save(user);
                return ResponseEntity.ok(friend2);
            }
        }
        user.getFriends().add(friend);
        friendRepository.save(friend);
        userRepository.save(user);
		return ResponseEntity.ok(friend);
	}




}
