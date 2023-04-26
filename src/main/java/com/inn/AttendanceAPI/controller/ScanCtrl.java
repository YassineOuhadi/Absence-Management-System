package com.inn.AttendanceAPI.controller;

import com.inn.AttendanceAPI.model.Scan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(path = "/scan")
public interface ScanCtrl {
    @GetMapping(path = "/getAllScans")
    ResponseEntity<List<Scan>> getAllScans();
}