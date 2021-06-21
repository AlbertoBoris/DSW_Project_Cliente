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
import com.project.client.entity.Mascota;
import com.project.client.entity.Usuario;

@Controller
@RequestMapping("/mascota")
public class MascotaController {
	
	private String REST_MASCOTA="http://localhost:8081/mascota/";
	private String REST_USUARIO = "http://localhost:8081/usuario/";	

	@RequestMapping("/")
	public String index(Model model) {
		try {
			RestTemplate rt = new RestTemplate();

			ResponseEntity<Mascota[]> response1 = rt.getForEntity(REST_MASCOTA + "listar", Mascota[].class);
			ResponseEntity<Usuario[]> response2 = rt.getForEntity(REST_USUARIO+"listar", Usuario[].class);

			Mascota[] listaMas = response1.getBody();
			Usuario[] listaUsu = response2.getBody();
 
			model.addAttribute("mascotas", listaMas);
			model.addAttribute("usuarios", listaUsu);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "mascota";
	}

	@RequestMapping("/guardar")
	public String guardar(@RequestParam("codigo") int cod, @RequestParam("nombre") String nom,
			@RequestParam("animal") String ani, @RequestParam("raza") String raz, 
			@RequestParam("fec_Nac") String fecha, @RequestParam("usuario") int codUsu, 
			RedirectAttributes redirect) {
		try {

			Mascota bean = new Mascota();
			bean.setCodigo(cod);
			bean.setNombre(nom);
			bean.setAnimal(ani);
			bean.setRaza(raz);
			bean.setFec_Nac(fecha);
			Usuario lab = new Usuario();
			lab.setCodigo(codUsu);
			bean.setUsuario(lab);
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
				rt.postForObject(REST_MASCOTA + "registrar", request, String.class);
				redirect.addFlashAttribute("MENSAJE", "Mascota registrada");
			} else {
				rt.put(REST_MASCOTA + "actualizar", request, String.class);
				redirect.addFlashAttribute("MENSAJE", "Mascota actualizada");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/mascota/";
	}
	
	@RequestMapping("/buscar")
	@ResponseBody
	public Mascota buscar(@RequestParam("codigo") int cod) {
		Mascota bean=null;
		try {
			RestTemplate rt=new RestTemplate();
			
		   	ResponseEntity<Mascota>response=rt.getForEntity(REST_MASCOTA+"buscar/"+cod, Mascota.class);
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
			rt.delete(REST_MASCOTA+"eliminar/"+cod);
			redirect.addFlashAttribute("MENSAJE","Mascota eliminada");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/mascota/";
		
	}
	
	@RequestMapping("/consultaMascota")
	public String index() {
		
		return "consultaMascota";
	}
	
	@RequestMapping("/listado")
	@ResponseBody
	public Mascota[] listado(@RequestParam("usuario") int usuario) {
		Mascota[] data=null;
		try {
			RestTemplate rt=new RestTemplate();
			ResponseEntity<Mascota[]>response=rt.getForEntity(REST_MASCOTA+"consulta2/"+usuario, Mascota[].class);
			data=response.getBody();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
}
