package com.inn.attendanceapi.serviceImpl;

import com.inn.attendanceapi.constants.SystemCst;
import com.inn.attendanceapi.dao.*;
import com.inn.attendanceapi.jwt.JwtFilter;
import com.inn.attendanceapi.model.*;
import com.inn.attendanceapi.service.SeanceService;
import com.inn.attendanceapi.utils.SystemUtils;
import com.inn.attendanceapi.record.PresenceRecord;
import com.inn.attendanceapi.wrapper.SeanceParticipantWrapper;
import com.inn.attendanceapi.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    SemesterDao semesterDao;

    @Autowired
    ElementDao elementDao;

    @Autowired
    SalleDao salleDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addSeance(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                if(requestMap.containsKey("date") && requestMap.containsKey("time") && requestMap.containsKey("duration") && requestMap.containsKey("salle_id") && requestMap.containsKey("type") && requestMap.containsKey("semester_id") && requestMap.containsKey("element_id")){
                    Optional<Semester> optionalSemester = semesterDao.findById(Integer.parseInt(requestMap.get("semester_id")));
                    if(optionalSemester.isPresent()){
                        Optional<Element> optionalElement = elementDao.findById(Integer.parseInt(requestMap.get("element_id")));
                        if(optionalElement.isPresent()){

                            Optional<Salle> optionalSalle = salleDao.findById(Integer.parseInt(requestMap.get("salle_id")));
                            if (optionalSalle.isPresent()) {

                                Salle salle = optionalSalle.get();
                                LocalDate date = LocalDate.parse(requestMap.get("date"));
                                Time startTime = Time.valueOf(requestMap.get("time")+":00");
                                Time duration = Time.valueOf(requestMap.get("duration")+":00");

                                if (isSeanceConflict(salle, date, startTime, duration)) {
                                    return SystemUtils.getResponseEntity("There is a conflict with an existing seance", HttpStatus.BAD_REQUEST);
                                }

                                Seance seance = getSeanceFromMap(requestMap);
                                seanceDao.save(seance);
                                return SystemUtils.getResponseEntity("Seance Participation Added Successfully", HttpStatus.OK);
                            }

                            return SystemUtils.getResponseEntity("Salle id does not exist", HttpStatus.OK);

                        }
                        return SystemUtils.getResponseEntity("Element id does not exist", HttpStatus.OK);
                    }
                    return SystemUtils.getResponseEntity("Semester id does not exist", HttpStatus.OK);
                }
            }else{
                return SystemUtils.getResponseEntity(SystemCst.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemCst.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Seance getSeanceFromMap(Map<String, String> requestMap){
        Semester semester = new Semester();
        Element element = new Element();
        Seance seance = new Seance();
        Salle salle = new Salle();
        seance.setDate(LocalDate.parse(requestMap.get("date")));
        seance.setTime(Time.valueOf(requestMap.get("time")+":00"));
        seance.setDuration(Time.valueOf(requestMap.get("duration")+":00"));
        seance.setType(Seance.SeanceType.valueOf(requestMap.get("type")));
        semester.setId(Integer.parseInt(requestMap.get("semester_id")));
        element.setId(Integer.parseInt(requestMap.get("element_id")));
        salle.setId(Integer.valueOf(requestMap.get("salle_id")));
        seance.setSemester(semester);
        seance.setElement(element);
        seance.setSalle(salle);
        return seance;
    }


    public boolean isSeanceConflict(Salle salle, LocalDate date, Time startTime, Time duration) {
        LocalTime endTime = startTime.toLocalTime().plusMinutes(duration.getTime() / 60000);
        List<Seance> allSeances = seanceDao.findAllSeancesBySalleAndDate(salle, date);
        for (Seance seance : allSeances) {
            if (seance.conflictsWith(startTime.toLocalTime(), endTime)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public ResponseEntity<String> addParticipant(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                if(requestMap.containsKey("seance_id")){

                if(requestMap.containsKey("participant_id")){
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

                else if(requestMap.containsKey("group_id")){
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
    public ResponseEntity<List<SeanceParticipantWrapper>> getParticipants(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin() || jwtFilter.isProfessor()) {
                if (requestMap.containsKey("seance_id")) {
                    Integer seanceId = Integer.parseInt(requestMap.get("seance_id"));

                    if (jwtFilter.isProfessor()) {
                        User professor = seanceParticipantsDao.findBySeanceIdAndUserRole(seanceId,User.UserRole.PROFESSOR);
                        if (professor != null && !professor.getEmail().equals(jwtFilter.getCurrentUser())) {
                            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
                        }
                    }

                    Optional<Seance> optional = seanceDao.findById(seanceId);
                    if (optional.isEmpty()) {
                        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
                    }

                    Seance seance = optional.get();
                    LocalDate seanceDate = seance.getDate();
                    Time seanceTime = seance.getTime();
                    Time seanceDuration = seance.getDuration();
                    LocalDateTime seanceDebutDateTime = LocalDateTime.of(seanceDate, seanceTime.toLocalTime());
                    LocalDateTime seanceEndDateTime = seanceDebutDateTime.plusHours(seanceDuration.getHours()).plusMinutes(seanceDuration.getMinutes());

                    if(LocalDateTime.now().isBefore(seanceDebutDateTime)){
                        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.PRECONDITION_FAILED);
                    }

                    if(jwtFilter.isAdmin() && LocalDateTime.now().isBefore(seanceEndDateTime)){
                        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
                    }

                    List<SeanceParticipants> seanceParticipantsList = seanceParticipantsDao.findBySeanceId(seanceId);
                    List<SeanceParticipantWrapper> participants = new ArrayList<>();

                    // Check if seance is currently ongoing
                    if (LocalDateTime.now().isBefore(seanceEndDateTime)) {
                    }

                    for (SeanceParticipants seanceParticipant : seanceParticipantsList) {
                        User user = seanceParticipant.getUser();

                        if (jwtFilter.isProfessor() && user.getRole() != User.UserRole.STUDENT) {
                            continue;
                        }

                        boolean isPresent = checkPresence(seanceParticipant).isPresent();
                        Time entryTime = checkPresence(seanceParticipant).entryTime();

                        if (requestMap.containsKey("status")) {
                            if (requestMap.get("status").equalsIgnoreCase("PRESENT") && !isPresent) {
                                continue;
                            }
                            if (requestMap.get("status").equalsIgnoreCase("ABSENT") && isPresent) {
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

    public PresenceRecord checkPresence(SeanceParticipants seanceParticipant) {
        Presence presence = presenceDao.findByUserAndSeance(seanceParticipant.getUser(), seanceParticipant.getSeance());
        boolean isPresent = false;
        Time entryTime = null;
        if (presence != null) {
            entryTime = presence.getEntrytime();
            if (!seanceParticipant.isPresence()) {
                LocalDate seanceDate = seanceParticipant.getSeance().getDate();
                Time seanceTime = seanceParticipant.getSeance().getTime();
                Time seanceDuration = seanceParticipant.getSeance().getDuration();
                LocalDateTime seanceDateTime = LocalDateTime.of(seanceDate, seanceTime.toLocalTime());
                LocalDateTime endDateTime = seanceDateTime.plusHours(seanceDuration.getHours()).plusMinutes(seanceDuration.getMinutes());
                if (LocalDateTime.now().isAfter(endDateTime)) {
                    isPresent = true;
                }
            } else {
                isPresent = true;
            }
        }
        seanceParticipant.setPresence(isPresent);
        seanceParticipantsDao.save(seanceParticipant);
        return new PresenceRecord(isPresent, entryTime);
    }


    @Override
    public ResponseEntity<String> validatePresence(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isProfessor()){
                //
            }else{
                return SystemUtils.getResponseEntity(SystemCst.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemCst.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
