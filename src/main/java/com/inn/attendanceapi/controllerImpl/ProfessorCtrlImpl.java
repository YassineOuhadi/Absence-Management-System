package com.inn.attendanceapi.controllerImpl;

import com.inn.attendanceapi.controller.ProfessorCtrl;
import com.inn.attendanceapi.service.UserService;
import com.inn.attendanceapi.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProfessorCtrlImpl implements ProfessorCtrl {

    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<List<UserWrapper>> getAllProfessors() {
        try{
            return userService.getAllProfessors();
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
