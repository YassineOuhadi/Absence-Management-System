package com.inn.attendanceapi.controller;

import com.inn.attendanceapi.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(path = "/student")
public interface StudentCtrl {

    @GetMapping(path = "/get")
    public ResponseEntity<List<UserWrapper>> getAllStudents();
}
