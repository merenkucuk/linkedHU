package com.example.demo.controller;

import java.security.NoSuchProviderException;
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
import com.example.demo.model.Security;
import com.example.demo.model.User;
import com.example.demo.viewmodel.UserAccount;
import com.example.demo.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:3000")
@Validated
@RestController
@RequestMapping("/api/V1/")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	// get read user
	@GetMapping("/users")
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	// create user
	@PostMapping("/users")
	public User createUser(@RequestBody @Valid UserAccount userAccount) throws NoSuchProviderException {

		if (userAccount.getPassword().length() < 8) {
			return new User();
		}

		User user = new User();
		user.setFirstName(userAccount.getFirstName());
		user.setLastName(userAccount.getLastName());
		user.setEmail(userAccount.getEmail());
		user.setRole(userAccount.getRole());
		user.setPassword(Security.HashPassword(userAccount.getPassword()));
		user.setRole(userAccount.getRole());
		return userRepository.save(user);
	}

	// get user by id
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourseNotFoundException("User Not Found"));

		return ResponseEntity.ok(user);
	}

	// get all users without id
	@GetMapping("/users/without/{id}")
	public List<User> getUserWithoutId(@PathVariable Long id) {
		List<User> users = userRepository.findAll();
		User user = userRepository.findById(id).orElseThrow(() -> new ResourseNotFoundException("User Not Found"));
		users.remove(user);
		return users;
	}

	// get user by name
	@GetMapping("/users/find/{email}")
	public User getUserByName(@PathVariable String email) {
		List<User> users = userRepository.findAll();
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getEmail().equals(email)) {
				return users.get(i);
			}
		}
		User nullUser = new User();
		if (users.isEmpty()) {
			nullUser.setFirstName("No User");
		}
		return nullUser;

	}

	// update user
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourseNotFoundException("User Not Found"));
		user.setFirstName(userDetails.getFirstName());
		user.setLastName(userDetails.getLastName());

		User updatedUser = userRepository.save(user);

		return ResponseEntity.ok(updatedUser);
	}

	// delete user
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourseNotFoundException("User Not Found"));

		userRepository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);

		return ResponseEntity.ok(response);
	}

}
