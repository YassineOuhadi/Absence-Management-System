package com.inn.attendanceapi.service;

import com.inn.attendanceapi.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ProfessorService {

    ResponseEntity<String> addNewProfessor(Map<String,String> requestMap);

    ResponseEntity<List<UserWrapper>> getAllProfessors();
}
