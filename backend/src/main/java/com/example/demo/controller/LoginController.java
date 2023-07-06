package com.example.demo.controller;

import java.security.NoSuchProviderException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Security;
import com.example.demo.model.User;
import com.example.demo.viewmodel.UserAccount;
import com.example.demo.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/V1/")
public class LoginController {

	@Autowired
	private UserRepository userRepository;

	// get user by name
	@GetMapping("/sign-in")
	public User checkUser(@RequestParam String email, @RequestParam String password) {
		List<User> users = userRepository.findAll();
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getEmail().equals(email) && Security.CheckPassword(password, users.get(i).getPassword())) {
				
				return users.get(i);
				
			}
		}
		return new User();
	}

	// create user
	@PostMapping("/sign-up")
	public User createUser(@RequestBody UserAccount userAccount) throws NoSuchProviderException {

		User user = new User();
		user.setFirstName(userAccount.getFirstName());
		user.setLastName(userAccount.getLastName());
		user.setEmail(userAccount.getEmail());		
		user.setPassword(Security.HashPassword(userAccount.getPassword()));
		return userRepository.save(user);
	}

}
