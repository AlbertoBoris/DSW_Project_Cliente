package com.project.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/intranet")
public class IntranetController {
	
	@RequestMapping("/")
	public String index() {
		
		return "intranet";
		
	}
	
	@RequestMapping("/perfil")
	public String perfil() {
		
		return "perfil";
		
	}

}
