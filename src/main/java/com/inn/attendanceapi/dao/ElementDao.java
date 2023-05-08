package com.inn.attendanceapi.dao;

import com.inn.attendanceapi.model.Element;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElementDao extends JpaRepository<Element,Integer> {
}
