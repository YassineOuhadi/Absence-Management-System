package com.inn.attendanceapi.controllerImpl;

import com.inn.attendanceapi.constants.SystemCst;
import com.inn.attendanceapi.controller.SeanceCtrl;
import com.inn.attendanceapi.service.SeanceService;
import com.inn.attendanceapi.utils.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SeanceCtrlImpl implements SeanceCtrl {

    @Autowired
    SeanceService seanceService;

    @Override
    public ResponseEntity<String> addParticipant(Map<String, String> requestMap) {
        try {
            return seanceService.addParticipant(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemCst.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> addParticipants(Map<String, String> requestMap) {
        try {
            return seanceService.addParticipants(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemCst.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
