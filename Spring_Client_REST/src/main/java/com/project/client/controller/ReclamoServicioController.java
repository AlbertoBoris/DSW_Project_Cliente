package com.project.client.controller;

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
import com.project.client.entity.Reclamo;
import com.project.client.entity.Servicio;

@Controller
@RequestMapping("/reclamoServicio")
public class ReclamoServicioController {
	
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
		return "reclamoServicio";
	}
	
	@RequestMapping("/guardar")
	public String guardar(@RequestParam("codigo") int cod, @RequestParam("servicioRecl") int codServ,
			@RequestParam("fec_Inc") String fec, @RequestParam("asunto") String asu,
			@RequestParam("descripcion") String des, @RequestParam("contacto") String con,
			RedirectAttributes redirect) {
		try {

			Reclamo bean = new Reclamo();
			bean.setCodigo(cod);
			Servicio lab = new Servicio();
			lab.setCodigo(codServ);
			bean.setServicioRecl(lab);	
			bean.setFec_Inc(fec);								
			bean.setAsunto(asu);
			bean.setDescripcion(des);
			bean.setContacto(con);
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

			rt.postForObject(REST_RECLAMO + "registrar", request, String.class);
			redirect.addFlashAttribute("MENSAJE", "Reclamo registrado");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/reclamoServicio/";
	}	
}
