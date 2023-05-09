package com.inn.attendanceapi.controllerImpl;

import com.inn.attendanceapi.constants.SystemCst;
import com.inn.attendanceapi.controller.ProfessorCtrl;
import com.inn.attendanceapi.service.ProfessorService;
import com.inn.attendanceapi.utils.SystemUtils;
import com.inn.attendanceapi.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ProfessorCtrlImpl implements ProfessorCtrl {

    @Autowired
    ProfessorService professorService;

    @Override
    public ResponseEntity<String> addNewProfessor(Map<String, String> requestMap) {
        try{
            return professorService.addNewProfessor(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemCst.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllProfessors() {
        try{
            return professorService.getAllProfessors();
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
