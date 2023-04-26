package com.inn.AttendanceAPI.controllerImpl;


import com.inn.AttendanceAPI.controller.ScanCtrl;
import com.inn.AttendanceAPI.model.Scan;
import com.inn.AttendanceAPI.service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ScanCtrlImpl implements ScanCtrl {

    @Autowired
    ScanService scanService;

    @Override
    public ResponseEntity<List<Scan>> getAllScans() {
        try {
            return scanService.getAllScans();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
