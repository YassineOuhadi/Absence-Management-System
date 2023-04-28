package com.inn.attendanceapi.controller;

import com.inn.attendanceapi.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(path = "/professor")
public interface ProfessorCtrl {

    @GetMapping(path = "/get")
    public ResponseEntity<List<UserWrapper>> getAllProfessors();
}
