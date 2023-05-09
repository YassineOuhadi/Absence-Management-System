package com.inn.attendanceapi.controller;

import com.inn.attendanceapi.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/professor")
public interface ProfessorCtrl {

    @PostMapping(path = "/add")
    public ResponseEntity<String> addNewProfessor(@RequestBody(required = true) Map<String,String> requestMap);

    @GetMapping(path = "/get")
    public ResponseEntity<List<UserWrapper>> getAllProfessors();
}
