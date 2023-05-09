package com.inn.attendanceapi.dao;

import com.inn.attendanceapi.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleDao extends JpaRepository<Module,Integer> {
}
