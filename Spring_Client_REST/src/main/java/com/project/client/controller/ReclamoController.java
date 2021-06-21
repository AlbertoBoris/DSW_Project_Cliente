package com.project.client.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.client.entity.Reclamo;
import com.project.client.entity.Servicio;

@Controller
@RequestMapping("/reclamo")
public class ReclamoController {

	private String REST_RECLAMO = "http://localhost:8081/reclamo/";
	private String REST_SERVICIO = "http://localhost:8081/servicio/";
	

	@RequestMapping("/")
	public String index(Model model) {
		try {
			RestTemplate rt = new RestTemplate();

			ResponseEntity<Reclamo[]> response1 = rt.getForEntity(REST_RECLAMO + "listar", Reclamo[].class);
			ResponseEntity<Servicio[]> response2 = rt.getForEntity(REST_SERVICIO + "listar", Servicio[].class);

			Reclamo[] listaHist = response1.getBody();
			Servicio[] listaMasc = response2.getBody();

			model.addAttribute("reclamos", listaHist);
			model.addAttribute("servicios", listaMasc);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "reclamo";
	}

	
	@RequestMapping("/eliminar")
	public String eliminar(@RequestParam("codigo") int cod, RedirectAttributes redirect) {
		try {
			RestTemplate rt = new RestTemplate();
			rt.delete(REST_RECLAMO + "eliminar/" + cod);
			redirect.addFlashAttribute("MENSAJE", "Reclamo eliminado");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/reclamo/";

	}
	
	@RequestMapping("/consultaReclamo")
	public String index() {
		return "consultaReclamo";
	}
	
	@RequestMapping("/listado")
	@ResponseBody
	public Reclamo[] listado(@RequestParam("nombre") String nombre) {
		Reclamo[] data = null;
		try {
			RestTemplate rt=new RestTemplate();
			ResponseEntity<Reclamo[]>response=rt.getForEntity(REST_RECLAMO+"consulta/"+nombre, Reclamo[].class);
			data=response.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

}
