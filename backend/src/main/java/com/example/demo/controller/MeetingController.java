package com.example.demo.controller;

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

import com.example.demo.exception.UnauthorizedAction;
import com.example.demo.exception.ResourseNotFoundException;
import com.example.demo.model.Comment;
import com.example.demo.model.Like;
import com.example.demo.model.Meeting;
import com.example.demo.model.User;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.MeetingRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.viewmodel.CreateComment;
import com.example.demo.viewmodel.CreateLike;
import com.example.demo.viewmodel.CreateMeeting;
import com.example.demo.viewmodel.UpdateMeeting;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/V1/")
public class MeetingController {

	@Autowired
	private MeetingRepository meetingRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LikeRepository likeRepository;

	@Autowired
	private CommentRepository commentRepository;

	// get read all meetings
	@GetMapping("/meetings")
	public List<Meeting> getAllMeetings() {
		return meetingRepository.findAll();
	}

	// create a meeting
	@PostMapping("/meetings")
	public Meeting createJob(@RequestBody CreateMeeting createMeeting) {
		Meeting meeting = new Meeting(
				createMeeting.getTitle(),
				createMeeting.getDescription(),
				createMeeting.getImage(),
				createMeeting.getLink(),
				new Date(),
				createMeeting.getUserId());

		User user = userRepository.findById(createMeeting.getUserId())
				.orElseThrow(() -> new ResourseNotFoundException("User Not Found"));
		if(!user.getRole().equals("1")){
			throw new ResourseNotFoundException("meting oluşturma yetkiniz yok");
		}

		Meeting returnMeeting = meetingRepository.save(meeting);
		user.getMeetings().add(meeting);
		userRepository.save(user);
		return returnMeeting;
	}

	// get job by id
	@GetMapping("/meetings/{id}")
	public ResponseEntity<Meeting> getMeetingById(@PathVariable Long id) {
		Meeting meeting = meetingRepository.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("Meeting Not Found"));

		return ResponseEntity.ok(meeting);
	}

	// get meeting by title
	@GetMapping("/meetings/find/{title}")
	public Meeting getMeetingByTitle(@PathVariable String title) {
		List<Meeting> meetings = meetingRepository.findAll();
		for (int i = 0; i < meetings.size(); i++) {
			if (meetings.get(i).getTitle().equals(title)) {
				return meetings.get(i);
			}
		}
		Meeting nullMeeting = new Meeting();
		if (meetings.isEmpty()) {
			nullMeeting.setTitle("No meeting");
		}
		return nullMeeting;

	}

	// update meeting
	@PutMapping("/meetings/{id}")
	public ResponseEntity<Meeting> updateMeeting(@PathVariable Long id, @RequestBody UpdateMeeting meetingDetails) {
		Meeting meeting = meetingRepository.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("Meeting Not Found"));
		meeting.setTitle(meetingDetails.getTitle());
		meeting.setDescription(meetingDetails.getDescription());
		meeting.setLink(meetingDetails.getLink());
		Meeting updatedMeeting = meetingRepository.save(meeting);
		return ResponseEntity.ok(updatedMeeting);
	}

	// update meetings like
	@PutMapping("/meetings/like")
	public ResponseEntity<Meeting> updateLike(@RequestBody CreateLike likeDetail) {

		Meeting meeting = meetingRepository.findById(likeDetail.getPostId())
				.orElseThrow(() -> new ResourseNotFoundException("Event Not Found"));
		User user = userRepository.findById(likeDetail.getUserId())
				.orElseThrow(() -> new ResourseNotFoundException("Event Not Found"));
		Like like = new Like(
				user.getId(),
				meeting.getId(),
				user.getFirstName() + " " + user.getLastName(),
				new Date());

		for (int i = 0; i < meeting.getLikes().size(); i++) {
			if (meeting.getLikes().get(i).getUserId() == like.getUserId()) {
				meeting.getLikes().remove(i);
				for (int j = 0; j < user.getLikes().size(); j++) {
					if (user.getLikes().get(j).getPostId() == like.getPostId()) {
						user.getLikes().remove(j);
						break;
					}
				}
				Meeting updatedMeeting = meetingRepository.save(meeting);
				likeRepository.delete(like);
				userRepository.save(user);
				return ResponseEntity.ok(updatedMeeting);
			}
		}
		meeting.getLikes().add(like);
		user.getLikes().add(like);

		likeRepository.save(like);
		userRepository.save(user);

		Meeting updatedMeeting = meetingRepository.save(meeting);
		return ResponseEntity.ok(updatedMeeting);
	}

	// update meetings comment
	@PutMapping("/meetings/comment")
	public ResponseEntity<Meeting> updateComment(@RequestBody CreateComment commentDetail) {

		Meeting meeting = meetingRepository.findById(commentDetail.getPostId())
				.orElseThrow(() -> new ResourseNotFoundException("Event Not Found"));
		User user = userRepository.findById(commentDetail.getUserId())
				.orElseThrow(() -> new ResourseNotFoundException("Event Not Found"));
		Comment comment = new Comment(
				user.getId(),
				meeting.getId(),
				commentDetail.getText(),
				new Date());
		/*
		 * //selam
		 * for(int i=0; i<meeting.getComments().size();i++){
		 * if(meeting.getComments().get(i).getUserId()==comment.getUserId()){
		 * meeting.getComments().remove(i);
		 * for(int j=0;j<user.getComments().size(); j++){
		 * if(user.getComments().get(j).getPostId()==comment.getPostId()){
		 * user.getComments().remove(j);
		 * break;
		 * }
		 * }
		 * Meeting updatedMeeting = meetingRepository.save(meeting);
		 * commentRepository.delete(comment);
		 * userRepository.save(user);
		 * return ResponseEntity.ok(updatedMeeting);
		 * }
		 * }
		 */
		meeting.getComments().add(comment);
		user.getComments().add(comment);

		commentRepository.save(comment);
		userRepository.save(user);

		Meeting updatedEvent = meetingRepository.save(meeting);
		return ResponseEntity.ok(updatedEvent);
	}

	// delete meeting
	@DeleteMapping("/meetings/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteMeeting(@PathVariable Long id) {
		Meeting meeting = meetingRepository.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("meeting Not Found"));

		User user = userRepository.findById(meeting.getUserId())
				.orElseThrow(() -> new ResourseNotFoundException("Event Not Found"));
		
		user.getMeetings().remove(meeting);
		meetingRepository.delete(meeting);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);

		return ResponseEntity.ok(response);
	}

}
