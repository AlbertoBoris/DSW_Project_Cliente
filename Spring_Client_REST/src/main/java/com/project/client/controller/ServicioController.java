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
import com.project.client.entity.Servicio;
import com.project.client.entity.Horario;

@Controller
@RequestMapping("/servicio")
public class ServicioController {

	private String REST_SERVICIO = "http://localhost:8081/servicio/";
	private String REST_HORARIO = "http://localhost:8081/horario/";

	@RequestMapping("/")
	public String index(Model model) {
		try {
			RestTemplate rt = new RestTemplate();

			ResponseEntity<Servicio[]> response1 = rt.getForEntity(REST_SERVICIO + "listar", Servicio[].class);
			ResponseEntity<Horario[]> response2 = rt.getForEntity(REST_HORARIO + "listar", Horario[].class);

			Servicio[] listaSer = response1.getBody();
			Horario[] listaHorar = response2.getBody();

			model.addAttribute("servicios", listaSer);
			model.addAttribute("horarios", listaHorar);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "servicio";
	}

	@RequestMapping("/guardar")
	public String guardar(@RequestParam("codigo") int cod, @RequestParam("nombres") String nom,
			@RequestParam("precio") double pre, @RequestParam("descripcion") String des,
			@RequestParam("horario") int codHor, RedirectAttributes redirect) {
		try {

			Servicio bean = new Servicio();
			bean.setCodigo(cod);
			bean.setNombres(nom);
			bean.setPrecio(pre);
			bean.setDescripcion(des);
			Horario lab = new Horario();
			lab.setCodigo(codHor);
			bean.setHorario(lab);
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
				rt.postForObject(REST_SERVICIO + "registrar", request, String.class);
				redirect.addFlashAttribute("MENSAJE", "Servicio registrado");
			} else {
				rt.put(REST_SERVICIO + "actualizar", request, String.class);
				redirect.addFlashAttribute("MENSAJE", "Servicio actualizado");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/servicio/";
	}

	@RequestMapping("/buscar")
	@ResponseBody
	public Servicio buscar(@RequestParam("codigo") int cod) {
		Servicio bean = null;
		try {
			RestTemplate rt = new RestTemplate();

			ResponseEntity<Servicio> response = rt.getForEntity(REST_SERVICIO + "buscar/" + cod, Servicio.class);
			bean = response.getBody();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}

	@RequestMapping("/eliminar")
	public String eliminar(@RequestParam("codigo") int cod, RedirectAttributes redirect) {
		try {
			RestTemplate rt = new RestTemplate();
			rt.delete(REST_SERVICIO + "eliminar/" + cod);
			redirect.addFlashAttribute("MENSAJE", "Servicio eliminado");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/servicio/";

	}
	
	@RequestMapping("/consultaServicio")
	public String index() {
		return "consultaServicio";
	}
	
	@RequestMapping("/listado")
	@ResponseBody
	public Servicio[] listado(@RequestParam("nombre") String nombre) {
		Servicio[] data = null;
		try {
			RestTemplate rt=new RestTemplate();
			ResponseEntity<Servicio[]>response=rt.getForEntity(REST_SERVICIO+"consulta/"+nombre, Servicio[].class);
			data=response.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

}
