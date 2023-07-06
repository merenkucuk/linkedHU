package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Track;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long>{
	


}
