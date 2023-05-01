package com.inn.attendanceapi.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface SeanceService {

    ResponseEntity<String> addParticipant(Map<String, String> requestMap);

    ResponseEntity<String> addParticipants(Map<String, String> requestMap);
}
