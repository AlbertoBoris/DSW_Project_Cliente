package com.project.client.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.project.client.entity.Distrito;
import com.project.client.entity.Usuario;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	private String REST_USUARIO = "http://localhost:8081/usuario/";
	private String REST_DISTRITO="http://localhost:8081/distrito/";

	@RequestMapping("/")
	public String index(Model model) {
		try {
			RestTemplate rt = new RestTemplate();

			ResponseEntity<Usuario[]> response1 = rt.getForEntity(REST_USUARIO + "listar", Usuario[].class);
			ResponseEntity<Distrito[]>response2=rt.getForEntity(REST_DISTRITO+"listar", Distrito[].class);

			Usuario[] listaUsu = response1.getBody();
			Distrito[] listaDis=response2.getBody();
 
			model.addAttribute("usuarios", listaUsu);
			model.addAttribute("distritos",listaDis);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "usuario";
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
			} else {
				rt.put(REST_USUARIO + "actualizar", request, String.class);
				redirect.addFlashAttribute("MENSAJE", "Usuario actualizado");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/usuario/";
	}
	
	@RequestMapping("/buscar")
	@ResponseBody
	public Usuario buscar(@RequestParam("codigo") int cod) {
		Usuario bean=null;
		try {
			RestTemplate rt=new RestTemplate();
			
		   	ResponseEntity<Usuario>response=rt.getForEntity(REST_USUARIO+"buscar/"+cod, Usuario.class);
			bean=response.getBody();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
		
	}
	
	@RequestMapping("/eliminar")
	public String eliminar(@RequestParam("codigo") int cod,RedirectAttributes redirect) {
		try {
			RestTemplate rt=new RestTemplate();
			rt.delete(REST_USUARIO+"eliminar/"+cod);
			redirect.addFlashAttribute("MENSAJE","Usuario eliminado");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/usuario/";
		
	}
	
	@RequestMapping("/consultaUsuario")
	public String index() {
		
		return "consultaUsuario";
	}
	
	@RequestMapping("/listado")
	@ResponseBody
	public Usuario[] listado(@RequestParam("dni") String dni) {
		Usuario[] data=null;
		try {
			RestTemplate rt=new RestTemplate();
			ResponseEntity<Usuario[]>response=rt.getForEntity(REST_USUARIO+"consulta1/"+dni, Usuario[].class);
			data=response.getBody();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
}
