package com.project.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inicio")
public class InicioController {

	@RequestMapping("/")
	public String index() {

		return "inicio";

	}

	@RequestMapping("/nosotros")
	public String info() {

		return "nosotros";

	}

}
