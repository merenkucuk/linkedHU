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

import com.example.demo.exception.ResourseNotFoundException;
import com.example.demo.model.Comment;
import com.example.demo.model.Job;
import com.example.demo.model.Like;
import com.example.demo.model.User;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.JobRepository;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.viewmodel.CreateComment;
import com.example.demo.viewmodel.CreateJob;
import com.example.demo.viewmodel.CreateLike;
import com.example.demo.viewmodel.UpdateJob;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/V1/")
public class JobController {

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LikeRepository likeRepository;

	@Autowired
	private CommentRepository commentRepository;

	// get read all jobs
	@GetMapping("/jobs")
	public List<Job> getAllJobs() {
		return jobRepository.findAll();
	}

	// create a job
	@PostMapping("/jobs")
	public Job createJob(@RequestBody CreateJob createJob) {
		Job job = new Job(
				createJob.getTitle(),
				createJob.getDescription(),
				createJob.getImage(),
				createJob.getLink(),
				new Date(),
				createJob.getUserId());

		User user = userRepository.findById(createJob.getUserId())
				.orElseThrow(() -> new ResourseNotFoundException("User Not Found"));
		Job returnJob = jobRepository.save(job);
		user.getJobs().add(job);
		userRepository.save(user);
		return returnJob;
	}

	// get job by id
	@GetMapping("/jobs/{id}")
	public ResponseEntity<Job> getJobById(@PathVariable Long id) {
		Job job = jobRepository.findById(id).orElseThrow(() -> new ResourseNotFoundException("Job Not Found"));
		return ResponseEntity.ok(job);
	}

	// get event by userId
	@GetMapping("/jobs/byuser/{id}")
	public List<Job> getJobsByUserId(@PathVariable Long userId) {
		List<Job> jobs = jobRepository.findAll();
		List<Job> userJobs = new ArrayList<>();
		for (int i = 0; i < jobs.size(); i++) {
			if (jobs.get(i).getUserId() == userId) {
				userJobs.add(jobs.get(i));
			}
		}
		return userJobs;
	}

	// get job by title
	@GetMapping("/jobs/find/{title}")
	public Job getJobByTitle(@PathVariable String title) {
		List<Job> jobs = jobRepository.findAll();
		for (int i = 0; i < jobs.size(); i++) {
			if (jobs.get(i).getTitle().equals(title)) {
				return jobs.get(i);
			}
		}
		Job nullJob = new Job();
		if (jobs.isEmpty()) {
			nullJob.setTitle("No Job");
		}
		return nullJob;

	}

	// update job
	@PutMapping("/jobs/{id}")
	public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody UpdateJob jobDetails) {
		Job job = jobRepository.findById(id).orElseThrow(() -> new ResourseNotFoundException("Job Not Found"));
		job.setTitle(jobDetails.getTitle());
		job.setDescription(jobDetails.getDescription());
		job.setImage(jobDetails.getImage());
		job.setLink(jobDetails.getLink());
		Job updatedJob = jobRepository.save(job);
		return ResponseEntity.ok(updatedJob);
	}

	// update jobs like
	@PutMapping("/jobs/like")
	public ResponseEntity<Job> updateLike(@RequestBody CreateLike likeDetail) {

		Job job = jobRepository.findById(likeDetail.getPostId())
				.orElseThrow(() -> new ResourseNotFoundException("Event Not Found"));
		User user = userRepository.findById(likeDetail.getUserId())
				.orElseThrow(() -> new ResourseNotFoundException("Event Not Found"));
		Like like = new Like(
				user.getId(),
				job.getId(),
				user.getFirstName() + " " + user.getLastName(),
				new Date());

		for (int i = 0; i < job.getLikes().size(); i++) {
			if (job.getLikes().get(i).getUserId() == like.getUserId()) {
				job.getLikes().remove(i);
				for (int j = 0; j < user.getLikes().size(); j++) {
					if (user.getLikes().get(j).getPostId() == like.getPostId()) {
						user.getLikes().remove(j);
						break;
					}
				}
				Job updatedJob = jobRepository.save(job);
				likeRepository.delete(like);
				userRepository.save(user);
				return ResponseEntity.ok(updatedJob);
			}
		}
		job.getLikes().add(like);
		user.getLikes().add(like);

		likeRepository.save(like);
		userRepository.save(user);

		Job updatedJob = jobRepository.save(job);
		return ResponseEntity.ok(updatedJob);
	}

	// update jobs comment
	@PutMapping("/jobs/comment")
	public ResponseEntity<Job> updateComment(@RequestBody CreateComment commentDetail) {

		Job job = jobRepository.findById(commentDetail.getPostId())
				.orElseThrow(() -> new ResourseNotFoundException("Event Not Found"));
		User user = userRepository.findById(commentDetail.getUserId())
				.orElseThrow(() -> new ResourseNotFoundException("Event Not Found"));
		Comment comment = new Comment(
				user.getId(),
				job.getId(),
				commentDetail.getText(),
				new Date());
		/*
		 * for(int i=0; i<job.getComments().size();i++){
		 * if(job.getComments().get(i).getUserId()==comment.getUserId()){
		 * job.getComments().remove(i);
		 * for(int j=0;j<user.getComments().size(); j++){
		 * if(user.getComments().get(j).getPostId()==comment.getPostId()){
		 * user.getComments().remove(j);
		 * break;
		 * }
		 * }
		 * Job updatedJob = jobRepository.save(job);
		 * commentRepository.delete(comment);
		 * userRepository.save(user);
		 * return ResponseEntity.ok(updatedJob);
		 * }
		 * }
		 */
		job.getComments().add(comment);
		user.getComments().add(comment);

		commentRepository.save(comment);
		userRepository.save(user);

		Job updatedEvent = jobRepository.save(job);
		return ResponseEntity.ok(updatedEvent);
	}

	// delete job
	@DeleteMapping("/jobs/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteJob(@PathVariable Long id) {
		Job job = jobRepository.findById(id).orElseThrow(() -> new ResourseNotFoundException("Job Not Found"));
		User user = userRepository.findById(job.getUserId())
				.orElseThrow(() -> new ResourseNotFoundException("Event Not Found"));

		user.getJobs().remove(job);
		jobRepository.delete(job);

		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);

		return ResponseEntity.ok(response);
	}

}
