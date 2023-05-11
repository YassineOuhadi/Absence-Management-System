package com.inn.attendanceapi.controller;

import com.inn.attendanceapi.model.Justification;
import com.inn.attendanceapi.wrapper.SeanceParticipantWrapper;
import com.inn.attendanceapi.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/seance")
public interface SeanceCtrl {

    @PostMapping(path = "/add")
    public ResponseEntity<String> addSeance(@RequestBody(required = true) Map<String,String> requestMap);

    @PostMapping(path = "/addParticipant")
    public ResponseEntity<String> addParticipant(@RequestBody(required = true) Map<String,String> requestMap);
    @GetMapping(path = "/getParticipants")
    public ResponseEntity<List<SeanceParticipantWrapper>>  getParticipants(@RequestBody(required = true) Map<String,String> requestMap);

    @PostMapping(path = "/validatePresence")
    public ResponseEntity<String> validatePresence(@RequestBody(required = true) Map<String,String> requestMap);


    @PostMapping(path = "/justifyAbsence")
    public ResponseEntity<String> justifyAbsence(@RequestBody(required = true) Map<String,String> requestMap);

    @GetMapping(path = "/getJustification")
    public ResponseEntity<Justification> getJustification(@RequestBody(required = true) Map<String,String> requestMap);

    @PostMapping(path = "/validateJustification")
    public ResponseEntity<String> validateJustification(@RequestBody(required = true) Map<String,String> requestMap);
}
