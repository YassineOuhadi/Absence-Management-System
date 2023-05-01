package com.inn.attendanceapi.service;

import com.inn.attendanceapi.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface StudentService {

    ResponseEntity<String> addNewStudent(Map<String,String> requestMap);

    ResponseEntity<List<UserWrapper>> getAllStudents();

}
