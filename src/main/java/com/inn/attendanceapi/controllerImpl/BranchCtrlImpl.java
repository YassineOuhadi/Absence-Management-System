package com.inn.attendanceapi.controllerImpl;

import com.inn.attendanceapi.constants.SystemCst;
import com.inn.attendanceapi.controller.BranchCtrl;
import com.inn.attendanceapi.dao.YearBranchStudentsDao;
import com.inn.attendanceapi.model.Module;
import com.inn.attendanceapi.model.YearBranchStudents;
import com.inn.attendanceapi.service.BranchService;
import com.inn.attendanceapi.utils.SystemUtils;
import com.inn.attendanceapi.wrapper.ModuleWrapper;
import com.inn.attendanceapi.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class BranchCtrlImpl implements BranchCtrl {

    @Autowired
    BranchService branchService;

    @Override
    public ResponseEntity<String> addStudent(Map<String, String> requestMap) {
        try {
            return branchService.addStudent(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemCst.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getStudents(Map<String, String> requestMap) {
        try{
            return branchService.getStudents(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> addModule(Map<String, String> requestMap) {
        try {
            return branchService.addModule(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemCst.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ModuleWrapper>> getModules(Map<String, String> requestMap) {
        try{
            return branchService.getModules(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
