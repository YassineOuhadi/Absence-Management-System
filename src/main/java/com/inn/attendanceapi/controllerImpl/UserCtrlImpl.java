package com.inn.attendanceapi.controllerImpl;

import com.inn.attendanceapi.constants.SystemCst;
import com.inn.attendanceapi.controller.UserCtrl;
import com.inn.attendanceapi.service.UserService;
import com.inn.attendanceapi.utils.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserCtrlImpl implements UserCtrl {

    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try{
            return userService.login(requestMap);
        }catch(Exception e){
            e.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemCst.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try{
            return userService.update(requestMap);
        }catch (Exception e){
            e.printStackTrace();

        }
        return SystemUtils.getResponseEntity(SystemCst.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> checkToken() {
        try {
            return userService.checkToken();
        }catch (Exception e){
            e.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemCst.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {
            return userService.changePassword(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemCst.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try {
            return userService.forgotPassword(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemCst.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
