package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import com.example.demo.model.Event;
import com.example.demo.model.Job;
import com.example.demo.model.Meeting;
import com.example.demo.model.Track;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.JobRepository;
import com.example.demo.repository.MeetingRepository;
import com.example.demo.repository.TrackRepository;
import com.example.demo.viewmodel.AllPost;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/V1/")
public class PostController {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private TrackRepository trackRepository;

    @GetMapping("/posts")
    public List<AllPost> getAllPost() {

        List<AllPost> allPost = new ArrayList<>();
        List<Event> events = eventRepository.findAll();
        List<Job> jobs = jobRepository.findAll();
        List<Meeting> mettings = meetingRepository.findAll();

        for (Event event : events) {
                allPost.add(new AllPost(
                event.getId(),
                "event", 
                event.getTitle(),
                event.getDescription(), 
                "",
                event.getImage(),
                event.getDate()));
        }

        for (Job job : jobs) {
                allPost.add(new AllPost(
                job.getId(),
                "job", 
                job.getTitle(),
                job.getDescription(), 
                job.getLink(),
                job.getImage(),
                job.getDate()));
        }

        for (Meeting meeting : mettings) {
                allPost.add(new AllPost(
                meeting.getId(),
                "meeting", 
                meeting.getTitle(),
                meeting.getDescription(), 
                meeting.getLink(),
                meeting.getImage(),
                meeting.getDate()));
        }
        allPost.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
        return allPost;
    }

    // read all posts by userId
    @GetMapping("/posts/{userId}")
    public List<AllPost> getUserAllPost(@PathVariable Long userId) {
        List<AllPost> allPost = new ArrayList<>();

        List<Event> events = eventRepository.findAll();
        List<Job> jobs = jobRepository.findAll();
        List<Meeting> mettings = meetingRepository.findAll();

        for (Event event : events) {
            if (event.getUserId() == userId) {
                allPost.add(new AllPost(
                event.getId(),
                "event",  
                event.getTitle(),
                event.getDescription(), 
                event.getImage(),
                "",
                event.getDate()));
            }
        }

        for (Job job : jobs) {
            if (job.getUserId() == userId) {
                allPost.add(new AllPost(
                job.getId(),
                "job", 
                job.getTitle(),
                job.getDescription(), 
                job.getImage(),
                job.getLink(),
                job.getDate()));
            }
        }

        for (Meeting meeting : mettings) {
            if (meeting.getUserId() == userId) {
                allPost.add(new AllPost(
                meeting.getId(),
                "meeting", 
                meeting.getTitle(),
                meeting.getDescription(), 
                meeting.getImage(),
                meeting.getLink(),
                meeting.getDate()));
            }
        }
        allPost.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
        return allPost;
    }

    // read all posts by userId
    @GetMapping("/posts/track/{userId}")
    public List<AllPost> getUserTracks(@PathVariable Long userId) {
        List<AllPost> allPost = new ArrayList<>();

        List<Event> events = eventRepository.findAll();
        List<Job> jobs = jobRepository.findAll();
        List<Meeting> mettings = meetingRepository.findAll();
        List<Track> tracks = trackRepository.findAll();

        for (Event event : events) {
            for (Track track : tracks) {
                if(track.getType().equals("event") && track.getPostId()==event.getId()){
                    allPost.add(new AllPost(
                    event.getId(),
                    "event",  
                    event.getTitle(),
                    event.getDescription(), 
                    event.getImage(),
                    "",
                    event.getDate()));
                }
            }
        }

        for (Job job : jobs) {
            for (Track track : tracks) {
                if(track.getType().equals("event") && track.getPostId()==job.getId()){
                    allPost.add(new AllPost(
                    job.getId(),
                    "job", 
                    job.getTitle(),
                    job.getDescription(), 
                    job.getImage(),
                    job.getLink(),
                    job.getDate()));
                }
            }
        }

        for (Meeting meeting : mettings) {
            for (Track track : tracks) {
                if(track.getType().equals("event") && track.getPostId()==track.getId()){
                    allPost.add(new AllPost(
                    meeting.getId(),
                    "meeting", 
                    meeting.getTitle(),
                    meeting.getDescription(), 
                    meeting.getImage(),
                    meeting.getLink(),
                    meeting.getDate()));
                }
            }
        }
        allPost.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
        return allPost;
    }

}
