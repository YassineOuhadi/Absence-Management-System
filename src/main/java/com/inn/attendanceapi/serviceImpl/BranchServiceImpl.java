package com.inn.attendanceapi.serviceImpl;

import com.inn.attendanceapi.FactoryPattern.StudentFactory;
import com.inn.attendanceapi.FactoryPattern.UserFactory;
import com.inn.attendanceapi.constants.SystemCst;
import com.inn.attendanceapi.dao.ModuleDao;
import com.inn.attendanceapi.dao.SemesterBranchModulesDao;
import com.inn.attendanceapi.dao.UserDao;
import com.inn.attendanceapi.dao.YearBranchStudentsDao;
import com.inn.attendanceapi.jwt.JwtFilter;
import com.inn.attendanceapi.model.*;
import com.inn.attendanceapi.model.Module;
import com.inn.attendanceapi.service.BranchService;
import com.inn.attendanceapi.utils.SystemUtils;
import com.inn.attendanceapi.wrapper.ModuleWrapper;
import com.inn.attendanceapi.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class BranchServiceImpl implements BranchService {

    @Autowired
    YearBranchStudentsDao yearBranchStudentsDao;

    @Autowired
    SemesterBranchModulesDao semesterBranchModulesDao;

    @Autowired
    UserDao userDao;

    @Autowired
    ModuleDao moduleDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addStudent(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                if(requestMap.containsKey("year_id") && requestMap.containsKey("branch_id") && requestMap.containsKey("student_id") && requestMap.containsKey("level")){
                    Optional<User> optional = userDao.findById(Integer.parseInt(requestMap.get("student_id")));
                    if(optional.isPresent()){
                        User user = optional.get();
                        if(user.getRole() == UserFactory.UserRole.STUDENT){
                            yearBranchStudentsDao.save(getYearBranchStudentFromMap(requestMap));
                            return SystemUtils.getResponseEntity("Year Branch Student Added Successfully", HttpStatus.OK);
                        }
                    }
                    return SystemUtils.getResponseEntity("Student id does not exist", HttpStatus.OK);
                }
            }else{
                return SystemUtils.getResponseEntity(SystemCst.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemCst.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private YearBranchStudents getYearBranchStudentFromMap(Map<String, String> requestMap){
        Year year = new Year();
        Branch branch = new Branch();
        UserFactory studentFactory = new StudentFactory();
        User student = studentFactory.createUser();
        year.setId(Integer.parseInt(requestMap.get("year_id")));
        branch.setId(Integer.parseInt(requestMap.get("branch_id")));
        student.setId(Integer.parseInt(requestMap.get("student_id")));
        YearBranchStudents yearBranchStudents = new YearBranchStudents();
        yearBranchStudents.setYear(year);
        yearBranchStudents.setBranch(branch);
        yearBranchStudents.setStudent(student);
        yearBranchStudents.setLevel(YearBranchStudents.Level.valueOf(requestMap.get("level")));
        return yearBranchStudents;
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getStudents(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                if(requestMap.containsKey("year_id") && requestMap.containsKey("branch_id") && requestMap.containsKey("level")){
                    List<UserWrapper> students = new ArrayList<>();
                    Integer yearId = Integer.parseInt(requestMap.get("year_id"));
                    Integer branchId = Integer.parseInt(requestMap.get("branch_id"));
                    YearBranchStudents.Level level = YearBranchStudents.Level.valueOf(requestMap.get("level"));
                    List<YearBranchStudents> yearBranchStudentsList = yearBranchStudentsDao.findByYearIdAndBranchIdAndLevel(yearId, branchId, level);
                    for (YearBranchStudents yearBranchStudent : yearBranchStudentsList) {
                        students.add(new UserWrapper(yearBranchStudent.getStudent()));
                    }
                    return new ResponseEntity<>(students, HttpStatus.OK);
                }
            }else{
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> addModule(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                if(requestMap.containsKey("semester_id") && requestMap.containsKey("branch_id") && requestMap.containsKey("module_id") && requestMap.containsKey("level")){
                    Optional<com.inn.attendanceapi.model.Module> optional = moduleDao.findById(Integer.parseInt(requestMap.get("module_id")));
                    if(optional.isPresent()){
                        Module module = optional.get();
                        semesterBranchModulesDao.save(getSemesterBranchModuleFromMap(requestMap));
                        return SystemUtils.getResponseEntity("Semester Branch Module Added Successfully", HttpStatus.OK);
                    }
                    return SystemUtils.getResponseEntity("Module id does not exist", HttpStatus.OK);
                }
            }else{
                return SystemUtils.getResponseEntity(SystemCst.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return SystemUtils.getResponseEntity(SystemCst.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private SemesterBranchModules getSemesterBranchModuleFromMap(Map<String, String> requestMap){
        Semester semester = new Semester();
        Branch branch = new Branch();
        com.inn.attendanceapi.model.Module module = new com.inn.attendanceapi.model.Module();
        semester.setId(Integer.parseInt(requestMap.get("semester_id")));
        branch.setId(Integer.parseInt(requestMap.get("branch_id")));
        module.setId(Integer.parseInt(requestMap.get("module_id")));
        SemesterBranchModules semesterBranchModules = new SemesterBranchModules();
        semesterBranchModules.setSemester(semester);
        semesterBranchModules.setBranch(branch);
        semesterBranchModules.setModule(module);
        semesterBranchModules.setLevel(YearBranchStudents.Level.valueOf(requestMap.get("level")));
        return semesterBranchModules;
    }

    public ResponseEntity<List<ModuleWrapper>> getModules(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                if(requestMap.containsKey("semester_id") && requestMap.containsKey("branch_id") && requestMap.containsKey("level")){
                    List<ModuleWrapper> modules = new ArrayList<>();
                    Integer semesterId = Integer.parseInt(requestMap.get("semester_id"));
                    Integer branchId = Integer.parseInt(requestMap.get("branch_id"));
                    YearBranchStudents.Level level = YearBranchStudents.Level.valueOf(requestMap.get("level"));
                    List<SemesterBranchModules> semesterBranchModulesList = semesterBranchModulesDao.findBySemesterIdAndBranchIdAndLevel(semesterId, branchId, level);
                    for (SemesterBranchModules semesterBranchModule : semesterBranchModulesList) {
                        modules.add(new ModuleWrapper(semesterBranchModule.getModule()));
                    }
                    return new ResponseEntity<>(modules, HttpStatus.OK);
                }
            }else{
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
