package com.inn.attendanceapi;

import org.quartz.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class main {

	public static void main(String[] args) throws SchedulerException {
		SpringApplication.run(main.class, args);
	}
}



