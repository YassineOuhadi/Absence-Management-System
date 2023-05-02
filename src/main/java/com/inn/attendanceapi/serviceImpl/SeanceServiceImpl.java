package com.inn.attendanceapi.serviceImpl;

import com.inn.attendanceapi.constants.SystemCst;
import com.inn.attendanceapi.dao.*;
import com.inn.attendanceapi.jwt.JwtFilter;
import com.inn.attendanceapi.model.*;
import com.inn.attendanceapi.service.SeanceService;
import com.inn.attendanceapi.utils.SystemUtils;
import com.inn.attendanceapi.wrapper.SeanceParticipantWrapper;
import com.inn.attendanceapi.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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
    PresenceDao presenceDao;
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


    @Override
    public ResponseEntity<List<SeanceParticipantWrapper>> getParticipants(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (requestMap.containsKey("seance_id")) {
                    List<SeanceParticipantWrapper> participants = new ArrayList<>();
                    Integer seanceId = Integer.parseInt(requestMap.get("seance_id"));

                    List<SeanceParticipants> seanceParticipantsList = seanceParticipantsDao.findBySeanceId(seanceId);
                    for (SeanceParticipants seanceParticipant : seanceParticipantsList) {
                        User user = seanceParticipant.getUser();

                        Presence presence = presenceDao.findByUserAndSeance(seanceParticipant.getUser(), seanceParticipant.getSeance());
                        Time entryTime = null;

                        boolean isPresent = checkPresence(seanceParticipant);

                        if(isPresent){
                            entryTime = presence.getEntrytime();
                        }

                        if (requestMap.containsKey("status")) {
                            if (requestMap.get("status").equalsIgnoreCase("PRESENT") && !seanceParticipant.isPresence()) {
                                continue;
                            }
                            if (requestMap.get("status").equalsIgnoreCase("ABSENT") && seanceParticipant.isPresence()) {
                                continue;
                            }
                        }

                        SeanceParticipantWrapper participant = new SeanceParticipantWrapper(new UserWrapper(user), isPresent, entryTime);
                        participants.add(participant);
                    }

                    return new ResponseEntity<>(participants, HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public boolean checkPresence(SeanceParticipants seanceParticipant) {
        Presence presence = presenceDao.findByUserAndSeance(seanceParticipant.getUser(), seanceParticipant.getSeance());
        boolean isPresent = false;

        if (presence != null) {
            Time entryTime = presence.getEntrytime();

            if (!seanceParticipant.isPresence()) {
                LocalDate seanceDate = seanceParticipant.getSeance().getDate();
                Time seanceTime = seanceParticipant.getSeance().getTime();
                LocalDateTime seanceDateTime = LocalDateTime.of(seanceDate, seanceTime.toLocalTime());

                LocalDateTime startDateTime = seanceDateTime.minusMinutes(10);
                LocalDateTime endDateTime = seanceDateTime.plusHours(2);

                LocalDateTime entryDateTime = LocalDateTime.of(seanceDate, entryTime.toLocalTime());


                //if (entryDateTime.isAfter(startDateTime) && entryDateTime.isBefore(endDateTime) && LocalDateTime.now().isAfter(endDateTime)) This by arduino ide
                if (LocalDateTime.now().isAfter(endDateTime)) {
                    isPresent = true;
                    /*
                    seanceParticipant.setPresence(true);
                    presenceDao.save(presence);
                     */
                }
                else{
                    // :) Send to professor to validate !
                }
            } else {
                isPresent = true;
            }
            seanceParticipant.setPresence(isPresent);
            presenceDao.save(presence);
        }
        else{
            if (seanceParticipant.isPresence()) {
                seanceParticipant.setPresence(isPresent);
                seanceParticipantsDao.save(seanceParticipant);
            }
        }

        return isPresent;
    }




}
