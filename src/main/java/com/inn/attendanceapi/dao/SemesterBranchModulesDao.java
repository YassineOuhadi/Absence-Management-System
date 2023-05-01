package com.inn.attendanceapi.dao;

import com.inn.attendanceapi.model.SemesterBranchModules;
import com.inn.attendanceapi.model.YearBranchStudents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SemesterBranchModulesDao extends JpaRepository<SemesterBranchModules,Integer> {

    List<SemesterBranchModules> findBySemesterIdAndBranchIdAndLevel(@Param("semester_id") Integer semester_id, @Param("branch_id") Integer branch_id, @Param("level") YearBranchStudents.Level level);
}
