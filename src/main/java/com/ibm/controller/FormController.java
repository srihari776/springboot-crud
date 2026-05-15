package com.ibm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller

public class FormController {
	
	
	@GetMapping("/form")
	public String welcome() {
		System.out.println("came to form");
		return "form";
	}
	
}
