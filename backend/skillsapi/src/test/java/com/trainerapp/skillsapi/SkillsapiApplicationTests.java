package com.trainerapp.skillsapi;

import static org.assertj.core.api.Assertions.assertThat;

import com.trainerapp.skillsapi.controllers.AdminController;
import com.trainerapp.skillsapi.controllers.AttendeeController;
import com.trainerapp.skillsapi.controllers.ApplicationController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@AutoConfigureMockMvc
class SkillsapiApplicationTests {

	@Autowired
	private ApplicationController userController;
	@Autowired
	private AdminController adminController;
	@Autowired
	private AttendeeController attendeeController;


	@Test
	public void contextLoads() throws Exception{
		assertThat(userController).isNotNull();
		assertThat(adminController).isNotNull();
		assertThat(attendeeController).isNotNull();
	}




}

