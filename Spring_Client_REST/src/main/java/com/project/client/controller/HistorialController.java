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
import com.project.client.entity.Historial;
import com.project.client.entity.Mascota;

@Controller
@RequestMapping("/historial")
public class HistorialController {

	private String REST_HISTORIAL = "http://localhost:8081/historial/";
	private String REST_MASCOTA = "http://localhost:8081/mascota/";

	@RequestMapping("/")
	public String index(Model model) {
		try {
			RestTemplate rt = new RestTemplate();

			ResponseEntity<Historial[]> response1 = rt.getForEntity(REST_HISTORIAL + "listar", Historial[].class);
			ResponseEntity<Mascota[]> response2 = rt.getForEntity(REST_MASCOTA + "listar", Mascota[].class);

			Historial[] listaHist = response1.getBody();
			Mascota[] listaMasc = response2.getBody();

			model.addAttribute("historiales", listaHist);
			model.addAttribute("mascotas", listaMasc);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "historial";
	}

	@RequestMapping("/guardar")
	public String guardar(@RequestParam("codigo") int cod, @RequestParam("mascota") int codMas,
			@RequestParam("asunto") String asu, @RequestParam("motivo") String mot,
			@RequestParam("tratamiento") String trat, RedirectAttributes redirect) {
		try {

			Historial bean = new Historial();
			bean.setCodigo(cod);
			Mascota lab = new Mascota();
			lab.setCodigo(codMas);
			bean.setMascota(lab);
			bean.setAsunto(asu);
			bean.setMotivo(mot);
			bean.setTratamiento(trat);
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
				rt.postForObject(REST_HISTORIAL + "registrar", request, String.class);
				redirect.addFlashAttribute("MENSAJE", "Historial registrado");
			} else {
				rt.put(REST_HISTORIAL + "actualizar", request, String.class);
				redirect.addFlashAttribute("MENSAJE", "Historial actualizado");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/historial/";
	}

	@RequestMapping("/buscar")
	@ResponseBody
	public Historial buscar(@RequestParam("codigo") int cod) {
		Historial bean = null;
		try {
			RestTemplate rt = new RestTemplate();

			ResponseEntity<Historial> response = rt.getForEntity(REST_HISTORIAL + "buscar/" + cod, Historial.class);
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
			rt.delete(REST_HISTORIAL + "eliminar/" + cod);
			redirect.addFlashAttribute("MENSAJE", "Historial eliminado");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/historial/";

	}

	@RequestMapping("/consultaHistorial")
	public String index() {
		return "consultaHistorial";
	}

	@RequestMapping("/listado")
	@ResponseBody
	public Historial[] listado(@RequestParam("mascota") String mascota) {
		Historial[] data = null;
		try {
			RestTemplate rt = new RestTemplate();
			ResponseEntity<Historial[]> response = rt.getForEntity(REST_HISTORIAL + "consulta/" + mascota,
					Historial[].class);
			data = response.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

}
