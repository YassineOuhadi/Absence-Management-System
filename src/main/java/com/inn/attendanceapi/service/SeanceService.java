package com.inn.attendanceapi.service;

import com.inn.attendanceapi.wrapper.SeanceParticipantWrapper;
import com.inn.attendanceapi.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface SeanceService {

    ResponseEntity<String> addSeance(Map<String, String> requestMap);
    ResponseEntity<String> addParticipant(Map<String, String> requestMap);
    ResponseEntity<List<SeanceParticipantWrapper>> getParticipants(Map<String, String> requestMap);

    ResponseEntity<String> validatePresence(Map<String, String> requestMap);
}
