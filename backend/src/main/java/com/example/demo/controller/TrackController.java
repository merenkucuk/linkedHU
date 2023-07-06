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
import com.example.demo.model.Track;
import com.example.demo.model.User;
import com.example.demo.viewmodel.CreateTrack;
import com.example.demo.viewmodel.UserAccount;
import com.example.demo.repository.TrackRepository;
import com.example.demo.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:3000")
@Validated
@RestController
@RequestMapping("/api/V1/")
public class TrackController {
    @Autowired
	private TrackRepository trackRepository;

    // get read user
	@GetMapping("/tracks")
	public List<Track> getAllTracks() {
		return trackRepository.findAll();
	}

    //add and delete friend
	@PutMapping("/tracks")
	public ResponseEntity<Track> updateFriend(@RequestBody CreateTrack trackDetail) throws NoSuchProviderException {
		
		Track track =new Track(
            trackDetail.getUserId(),
            trackDetail.getPostId(),
            trackDetail.getType()
        );

        List<Track> allTracks= trackRepository.findAll();
        for (Track track2 : allTracks) {
            if(track2.getUserId()==trackDetail.getUserId() && track2.getPostId()==trackDetail.getPostId()){
                trackRepository.delete(track2);
            }
        }

        Track result=trackRepository.save(track);
        
		return ResponseEntity.ok(result);
	}

}
