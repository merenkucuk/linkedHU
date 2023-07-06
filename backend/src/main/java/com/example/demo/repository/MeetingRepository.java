package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Meeting;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long>{


}
