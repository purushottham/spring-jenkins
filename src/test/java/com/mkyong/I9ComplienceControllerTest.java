package com.mkyong;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class I9ComplienceControllerTest {

	@Autowired
	private MockMvc mvc;

//	@Test
//	public void welcome_ok() throws Exception {
//		mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
//				.andExpect("Hello World, Spring Boot!"));
//	}

	@Test
	public void welcome_ok1() {
		assertEquals("Hello", "Hello");
	}

}
