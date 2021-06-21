package com.project.client.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.project.client.entity.Distrito;
import com.project.client.entity.Usuario;

@Controller
@RequestMapping("/perfil")
public class PerfilController {
	
	private String REST_USUARIO = "http://localhost:8081/usuario/";
	private String REST_DISTRITO="http://localhost:8081/distrito/";
	
	@RequestMapping("/")
	public String index(HttpServletRequest request, HttpServletResponse response,Model model) {
		HttpSession session= (HttpSession) request.getSession();
		try {
			RestTemplate rt = new RestTemplate();

			ResponseEntity<Distrito[]>response2=rt.getForEntity(REST_DISTRITO+"listar", Distrito[].class);

			Usuario usu = (Usuario) session.getAttribute("usuario");
			Distrito[] listaDis=response2.getBody();
 
			model.addAttribute("distritos",listaDis);
			
			System.out.println(usu.getNombres());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "usuario";
	}

}
