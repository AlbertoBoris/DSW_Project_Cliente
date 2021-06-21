package com.project.client.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.project.client.entity.Distrito;
import com.project.client.entity.Usuario;

@Controller
@RequestMapping("/login")

public class LoginController {
	
	private String REST_USUARIO = "http://localhost:8081/usuario/";
	private String REST_DISTRITO="http://localhost:8081/distrito/";
	
	@RequestMapping("/")
	public String index(Model model) {
		try {
			RestTemplate rt = new RestTemplate();

			ResponseEntity<Distrito[]>response2=rt.getForEntity(REST_DISTRITO+"listar", Distrito[].class);

			Distrito[] listaDis=response2.getBody();
 
			model.addAttribute("distritos",listaDis);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "login";
		
	}

	@RequestMapping("/log")
	public String login(HttpSession session,HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirect, @RequestParam("correo") String correo,@RequestParam("password") String password) {
		Usuario bean=null;
		String r = "";
		try {
			RestTemplate rt=new RestTemplate();
			
		   	ResponseEntity<Usuario>respuesta=rt.getForEntity(REST_USUARIO+"log/"+correo+"/"+password, Usuario.class);
			bean=respuesta.getBody();

			if(session.getAttribute("usuario")==null) {
				bean.setContrasena(null);
				session.setAttribute("usuario", bean);
			}

			if(bean!= null) {
				if(bean.getCodigo()==1) {
					redirect.addFlashAttribute("MENSAJE", "Ingreso");
					r = "redirect:/intranet/";
				}
				else {
					redirect.addFlashAttribute("MENSAJE", "Ingreso");
					r = "redirect:/inicio/";
				}
			}
			else {
				redirect.addFlashAttribute("MENSAJE", "Error al ingresar");
				r = "redirect:/login/";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session,RedirectAttributes redirect) {
		session.setAttribute("usuario", null);
		return "redirect:/login/";
	}
	
	@RequestMapping("/guardar")
	public String guardar(@RequestParam("codigo") int cod, @RequestParam("nombres") String nom,
			@RequestParam("apellidos") String ape, @RequestParam("direccion") String dir,
			@RequestParam("dni") String dni, @RequestParam("apodo") String apd, 
			@RequestParam("contrasena") String pass,@RequestParam("correo") String corr, 
			@RequestParam("fecha") String fecha,@RequestParam("telefono") String tel, 
			@RequestParam("sexo") String sex,@RequestParam("distrito") int codLab, 
			RedirectAttributes redirect) {
		try {

			Usuario bean = new Usuario();
			bean.setCodigo(cod);
			bean.setNombres(nom);
			bean.setApellidos(ape);
			bean.setDireccion(dir);
			bean.setDni(dni);
			bean.setApodo(apd);
			bean.setContrasena(pass);
			bean.setCorreo(corr);
			bean.setFecha_Nac(fecha);
			bean.setTelefono(tel);
			bean.setSexo(sex);
			Distrito lab = new Distrito();
			lab.setCodigo(codLab);
			bean.setDistrito(lab);
			// crear JSON
			Gson gson = new Gson();
			//
			String json = gson.toJson(bean);
			//
			RestTemplate rt = new RestTemplate();
			//
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> request = new HttpEntity<String>(json, headers);

			if (cod == 0) {
				rt.postForObject(REST_USUARIO + "registrar", request, String.class);
				redirect.addFlashAttribute("MENSAJE", "Usuario registrado");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/login/";
	}

}
