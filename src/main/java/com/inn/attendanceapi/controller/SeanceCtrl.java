package com.inn.attendanceapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping(path = "/seance")
public interface SeanceCtrl {

    @PostMapping(path = "/addParticipant")
    public ResponseEntity<String> addParticipant(@RequestBody(required = true) Map<String,String> requestMap);

    @PostMapping(path = "/addParticipants")
    public ResponseEntity<String> addParticipants(@RequestBody(required = true) Map<String,String> requestMap);
}
