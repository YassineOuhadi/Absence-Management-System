package com.inn.attendanceapi.dao;

import com.inn.attendanceapi.model.YearBranchStudents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface YearBranchStudentsDao extends JpaRepository<YearBranchStudents,Integer> {

    List<YearBranchStudents> findByYearIdAndBranchIdAndLevel(@Param("year_id") Integer year_id,@Param("branch_id") Integer branch_id,@Param("level") YearBranchStudents.Level level);
}
