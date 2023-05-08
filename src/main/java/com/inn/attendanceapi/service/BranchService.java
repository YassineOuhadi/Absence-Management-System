package com.inn.attendanceapi.service;

import com.inn.attendanceapi.model.Module;
import com.inn.attendanceapi.wrapper.ModuleWrapper;
import com.inn.attendanceapi.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface BranchService {

    ResponseEntity<String> addStudent(Map<String, String> requestMap);

    ResponseEntity<List<UserWrapper>> getStudents(Map<String, String> requestMap);

    ResponseEntity<String> addModule(Map<String, String> requestMap);

    ResponseEntity<List<ModuleWrapper>> getModules(Map<String, String> requestMap);
}
