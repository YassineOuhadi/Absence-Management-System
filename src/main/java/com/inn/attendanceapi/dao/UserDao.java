package com.inn.attendanceapi.dao;

import com.inn.attendanceapi.model.User;
import com.inn.attendanceapi.wrapper.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UserDao extends JpaRepository<User,Integer> {

    User findByEmailId(@Param("email") String email);

    List<UserWrapper> getAllStudents();

    List<UserWrapper> getAllProfessors();

    List<String> getAllAdmin();

    @Transactional
    @Modifying
    Integer updateStatus(@Param("status") String status,@Param("id") Integer id);

    User findByEmail(String email);

    List<User> findByGroupId(@Param("groupId") Integer groupId);

}