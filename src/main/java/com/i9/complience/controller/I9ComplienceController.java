package com.i9.complience.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.i9.complience.service.I9ComplienceService;

@RestController
public class I9ComplienceController {

	@Autowired
	I9ComplienceService i9ComplienceService;

	@Value("${outputfile}")
	private String outputFilePath;

	@RequestMapping("/report")
	public StringBuffer getDetailReport() throws IOException {
		i9ComplienceService.getDetailReport();
		// InputStream in = getClass().getResourceAsStream(outputFilePath);
		File f = new File(outputFilePath);
		BufferedReader br = new BufferedReader(new FileReader(f));

		// Declaring a string variable
		StringBuffer sb = new StringBuffer();
		String st;
		// Condition holds true till
		// there is character in a string
		while ((st = br.readLine()) != null) {

			// Print the string
			sb.append(st);
			sb.append("<br>");
		}
		return sb;

	}

	@GetMapping("/")
	public String test() {
		return "Hello World, Spring Boot!";
	}

}