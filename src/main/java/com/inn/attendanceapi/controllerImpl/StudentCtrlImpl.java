package com.inn.attendanceapi.controllerImpl;

import com.inn.attendanceapi.constants.SystemCst;
import com.inn.attendanceapi.controller.StudentCtrl;
import com.inn.attendanceapi.service.StudentService;
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
public class StudentCtrlImpl implements StudentCtrl {

    @Autowired
    StudentService studentService;

    @Override
    public ResponseEntity<String> addNewStudent(Map<String, String> requestMap) {
        try{
            return studentService.addNewStudent(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemCst.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllStudents() {
        try{
            return studentService.getAllStudents();
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
