package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.events.EventTarget;

import com.example.demo.exception.UnauthorizedAction;
import com.example.demo.exception.ResourseNotFoundException;
import com.example.demo.model.Comment;
import com.example.demo.model.Event;
import com.example.demo.model.Like;
import com.example.demo.model.User;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.viewmodel.CreateComment;
import com.example.demo.viewmodel.CreateEvent;
import com.example.demo.viewmodel.CreateLike;
import com.example.demo.viewmodel.UpdateEvent;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/V1/")
public class EventController {

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LikeRepository likeRepository;

	@Autowired
	private CommentRepository commentRepository;

	// get read all events
	@GetMapping("/events")
	public List<Event> getAllEvent() {
		return eventRepository.findAll();
	}

	// create event
	@PostMapping("/events")
	public Event createEvent(@RequestBody CreateEvent createEvent) {
		Event event = new Event(
				createEvent.getTitle(),
				createEvent.getDescription(),
				createEvent.getImage(),
				new Date(),
				createEvent.getUserId());

		User user = userRepository.findById(createEvent.getUserId())
				.orElseThrow(() -> new ResourseNotFoundException("User Not Found"));
		Event returnEvent = eventRepository.save(event);
		user.getEvents().add(event);
		userRepository.save(user);
		return returnEvent;
	}

	// get event by id
	@GetMapping("/events/{id}")
	public ResponseEntity<Event> getEventById(@PathVariable Long id) {
		Event event = eventRepository.findById(id).orElseThrow(() -> new ResourseNotFoundException("Event Not Found"));
		return ResponseEntity.ok(event);
	}

	// get event by title
	@GetMapping("/events/find/{title}")
	public Event getEventByTitle(@PathVariable String title) {
		List<Event> events = eventRepository.findAll();
		for (int i = 0; i < events.size(); i++) {
			if (events.get(i).getTitle().equals(title)) {
				return events.get(i);
			}
		}
		Event nullEvent = new Event();
		if (events.isEmpty()) {
			nullEvent.setTitle("No Event");
		}
		return nullEvent;

	}

	// get event by userId
	@GetMapping("/events/byuser/{userId}")
	public List<Event> getEventsByUserId(@PathVariable Long userId) {
		List<Event> events = eventRepository.findAll();
		List<Event> userEvents = new ArrayList<>();
		for (int i = 0; i < events.size(); i++) {
			if (events.get(i).getUserId() == userId) {
				userEvents.add(events.get(i));
			}
		}
		return userEvents;
	}

	// update event
	@PutMapping("/events/{id}")
	public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody UpdateEvent eventDetails) {
		Event event = eventRepository.findById(id).orElseThrow(() -> new ResourseNotFoundException("Event Not Found"));
		event.setTitle(eventDetails.getTitle());
		event.setDescription(eventDetails.getDescription());
		event.setImage(eventDetails.getImage());
		Event updatedEvent = eventRepository.save(event);
		return ResponseEntity.ok(updatedEvent);
	}

	// update events like
	@PutMapping("/events/like")
	public ResponseEntity<Event> updateLike(@RequestBody CreateLike likeDetail) {

		Event event = eventRepository.findById(likeDetail.getPostId())
				.orElseThrow(() -> new ResourseNotFoundException("Event Not Found"));
		User user = userRepository.findById(likeDetail.getUserId())
				.orElseThrow(() -> new ResourseNotFoundException("Event Not Found"));
		Like like = new Like(
				user.getId(),
				event.getId(),
				user.getFirstName() + " " + user.getLastName(),
				new Date());

		for (int i = 0; i < event.getLikes().size(); i++) {
			if (event.getLikes().get(i).getUserId() == like.getUserId()) {
				event.getLikes().remove(i);
				for (int j = 0; j < user.getLikes().size(); j++) {
					if (user.getLikes().get(j).getPostId() == like.getPostId()) {
						user.getLikes().remove(j);
						break;
					}
				}
				Event updatedEvent = eventRepository.save(event);
				likeRepository.delete(like);
				userRepository.save(user);
				return ResponseEntity.ok(updatedEvent);
			}
		}
		event.getLikes().add(like);
		user.getLikes().add(like);

		likeRepository.save(like);
		userRepository.save(user);

		Event updatedEvent = eventRepository.save(event);
		return ResponseEntity.ok(updatedEvent);
	}

	// update events comment
	@PutMapping("/events/comment")
	public ResponseEntity<Event> updateComment(@RequestBody CreateComment commentDetail) {

		Event event = eventRepository.findById(commentDetail.getPostId())
				.orElseThrow(() -> new ResourseNotFoundException("Event Not Found"));
		User user = userRepository.findById(commentDetail.getUserId())
				.orElseThrow(() -> new ResourseNotFoundException("Event Not Found"));
		Comment comment = new Comment(
				user.getId(),
				event.getId(),
				commentDetail.getText(),
				new Date());
		
		event.getComments().add(comment);
		user.getComments().add(comment);

		commentRepository.save(comment);
		userRepository.save(user);

		Event updatedEvent = eventRepository.save(event);
		return ResponseEntity.ok(updatedEvent);
	}

	// delete event
	@DeleteMapping("/events/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEvent(@PathVariable Long id) {
		Event event = eventRepository.findById(id).orElseThrow(() -> new ResourseNotFoundException("Event Not Found"));
		User user = userRepository.findById(event.getUserId())
				.orElseThrow(() -> new ResourseNotFoundException("Event Not Found"));

		user.getEvents().remove(event);
		eventRepository.delete(event);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

}