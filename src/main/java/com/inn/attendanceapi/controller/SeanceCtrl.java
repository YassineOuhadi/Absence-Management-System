package com.inn.attendanceapi.controller;

import com.inn.attendanceapi.wrapper.SeanceParticipantWrapper;
import com.inn.attendanceapi.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/seance")
public interface SeanceCtrl {

    @PostMapping(path = "/addParticipant")
    public ResponseEntity<String> addParticipant(@RequestBody(required = true) Map<String,String> requestMap);

    @PostMapping(path = "/addParticipants")
    public ResponseEntity<String> addParticipants(@RequestBody(required = true) Map<String,String> requestMap);

    @PostMapping(path = "/getParticipants")
    public ResponseEntity<List<SeanceParticipantWrapper>>  getParticipants(@RequestBody(required = true) Map<String,String> requestMap);
}
