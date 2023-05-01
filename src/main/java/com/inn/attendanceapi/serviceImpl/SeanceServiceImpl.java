package com.inn.attendanceapi.serviceImpl;

import com.inn.attendanceapi.constants.SystemCst;
import com.inn.attendanceapi.dao.GroupDao;
import com.inn.attendanceapi.dao.SeanceDao;
import com.inn.attendanceapi.dao.SeanceParticipantsDao;
import com.inn.attendanceapi.dao.UserDao;
import com.inn.attendanceapi.jwt.JwtFilter;
import com.inn.attendanceapi.model.*;
import com.inn.attendanceapi.service.SeanceService;
import com.inn.attendanceapi.utils.SystemUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class SeanceServiceImpl implements SeanceService {

    @Autowired
    SeanceParticipantsDao seanceParticipantsDao;
    @Autowired
    UserDao userDao;
    @Autowired
    GroupDao groupDao;
    @Autowired
    SeanceDao seanceDao;
    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addParticipant(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                if(requestMap.containsKey("seance_id") && requestMap.containsKey("participant_id")){
                    Optional<User> optional = userDao.findById(Integer.parseInt(requestMap.get("participant_id")));
                    if(optional.isPresent()){
                        User user = optional.get();
                        if(user.getRole() == User.UserRole.STUDENT){
                            seanceParticipantsDao.save(getSeanceParticipantFromMap(requestMap));
                            return SystemUtils.getResponseEntity("Student Participation Added Successfully", HttpStatus.OK);
                        }
                        else if (user.getRole() == User.UserRole.PROFESSOR){
                            Integer seanceId = Integer.parseInt(requestMap.get("seance_id"));
                            List<SeanceParticipants> seanceParticipants = seanceParticipantsDao.findBySeanceId(seanceId);
                            for (SeanceParticipants sp : seanceParticipants) {
                                if ((sp.getUser().getRole() == User.UserRole.PROFESSOR) && !(sp.getUser().equals(user))) {
                                    return SystemUtils.getResponseEntity("A professor is already registered for this seance", HttpStatus.OK);
                                }
                            }
                            seanceParticipantsDao.save(getSeanceParticipantFromMap(requestMap));
                            return SystemUtils.getResponseEntity("Professor Participation Added Successfully", HttpStatus.OK);
                        }
                    }
                    return SystemUtils.getResponseEntity("Participant id does not exist", HttpStatus.OK);
                }
            }else{
                return SystemUtils.getResponseEntity(SystemCst.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemCst.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private SeanceParticipants getSeanceParticipantFromMap(Map<String, String> requestMap){
        Seance seance = new Seance();
        User user = new User();
        seance.setId(Integer.parseInt(requestMap.get("seance_id")));
        user.setId(Integer.parseInt(requestMap.get("participant_id")));
        SeanceParticipants seanceParticipants = new SeanceParticipants();
        seanceParticipants.setSeance(seance);
        seanceParticipants.setUser(user);
        return seanceParticipants;
    }

    @Override
    public ResponseEntity<String> addParticipants(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                if(requestMap.containsKey("seance_id") && requestMap.containsKey("group_id")){
                    Integer seanceId = Integer.parseInt(requestMap.get("seance_id"));
                    Integer groupId = Integer.parseInt(requestMap.get("group_id"));

                    Optional<Group> optionalGroup = groupDao.findById(groupId);
                    if(optionalGroup.isPresent()){
                        Group group = optionalGroup.get();
                        List<User> students = userDao.findByGroupId(groupId);
                        for (User student : students) {
                            if(student.getRole() == User.UserRole.STUDENT){
                                SeanceParticipants seanceParticipant = new SeanceParticipants();
                                seanceParticipant.setSeance(seanceDao.getOne(seanceId));
                                seanceParticipant.setUser(student);
                                seanceParticipantsDao.save(seanceParticipant);
                            }
                        }

                        return SystemUtils.getResponseEntity("Participants Added Successfully", HttpStatus.OK);
                    }
                    else{
                        return SystemUtils.getResponseEntity("Group id does not exist", HttpStatus.OK);
                    }
                }
            }else{
                return SystemUtils.getResponseEntity(SystemCst.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemCst.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
