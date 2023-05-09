package com.inn.attendanceapi.controller;

import com.inn.attendanceapi.model.Module;
import com.inn.attendanceapi.wrapper.ModuleWrapper;
import com.inn.attendanceapi.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/branch")
public interface BranchCtrl {

    @PostMapping(path = "/addStudent")
    public ResponseEntity<String> addStudent(@RequestBody(required = true) Map<String,String> requestMap);

    @GetMapping(path = "/getStudents")
    public ResponseEntity<List<UserWrapper>>  getStudents(@RequestBody(required = true) Map<String,String> requestMap);

    @PostMapping(path = "/addModule")
    public ResponseEntity<String> addModule(@RequestBody(required = true) Map<String,String> requestMap);

    @GetMapping(path = "/getModules")
    public ResponseEntity<List<ModuleWrapper>>  getModules(@RequestBody(required = true) Map<String,String> requestMap);
}
