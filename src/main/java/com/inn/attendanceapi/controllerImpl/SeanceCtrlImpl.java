package com.inn.attendanceapi.controllerImpl;

import com.inn.attendanceapi.constants.SystemCst;
import com.inn.attendanceapi.controller.SeanceCtrl;
import com.inn.attendanceapi.model.Justification;
import com.inn.attendanceapi.service.SeanceService;
import com.inn.attendanceapi.utils.SystemUtils;
import com.inn.attendanceapi.wrapper.SeanceParticipantWrapper;
import com.inn.attendanceapi.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class SeanceCtrlImpl implements SeanceCtrl {

    @Autowired
    SeanceService seanceService;

    @Override
    public ResponseEntity<String> addSeance(Map<String, String> requestMap) {
        try {
            return seanceService.addSeance(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemCst.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

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
    public ResponseEntity<List<SeanceParticipantWrapper>> getParticipants(Map<String, String> requestMap) {
        try{
            return seanceService.getParticipants(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> validatePresence(Map<String, String> requestMap) {
        try {
            return seanceService.validatePresence(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemCst.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> justifyAbsence(Map<String, String> requestMap) {
        try {
            return seanceService.justifyAbsence(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemCst.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> validateJustification(Map<String, String> requestMap) {
        try {
            return seanceService.validateJustification(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemCst.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Justification> getJustification(Map<String, String> requestMap) {
        try {
            return seanceService.getJustification(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Justification(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
