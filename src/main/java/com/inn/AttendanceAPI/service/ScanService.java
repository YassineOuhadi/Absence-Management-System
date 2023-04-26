package com.inn.AttendanceAPI.service;

import com.inn.AttendanceAPI.model.Scan;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ScanService {
    ResponseEntity<List<Scan>> getAllScans() throws ExecutionException, InterruptedException;
}
