package com.project.client.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.project.client.entity.Historial;
import com.project.client.entity.Mascota;
import com.project.client.entity.Pedido;
import com.project.client.entity.Reclamo;
import com.project.client.entity.Servicio;
import com.project.client.entity.Usuario;

@Controller
@RequestMapping("/intranet")
public class IntranetController {

	private String REST_MASCOTA = "http://localhost:8081/mascota/";
	private String REST_USUARIO = "http://localhost:8081/usuario/";
	private String REST_SERVICIO = "http://localhost:8081/servicio/";
	private String REST_HISTORIAL = "http://localhost:8081/historial/";
	private String REST_RECLAMO = "http://localhost:8081/reclamo/";
	private String REST_PEDIDO = "http://localhost:8081/pedido/";

	@RequestMapping("/")
	public String index(Model model) {
		try {
			RestTemplate rt = new RestTemplate();

			ResponseEntity<Mascota[]> response1 = rt.getForEntity(REST_MASCOTA + "listar", Mascota[].class);
			ResponseEntity<Usuario[]> response2 = rt.getForEntity(REST_USUARIO + "listar", Usuario[].class);
			ResponseEntity<Servicio[]> response3 = rt.getForEntity(REST_SERVICIO + "listar", Servicio[].class);
			ResponseEntity<Historial[]> response4 = rt.getForEntity(REST_HISTORIAL + "listar", Historial[].class);
			ResponseEntity<Reclamo[]> response5 = rt.getForEntity(REST_RECLAMO + "listar", Reclamo[].class);
			ResponseEntity<Pedido[]> response6 = rt.getForEntity(REST_PEDIDO + "listar", Pedido[].class);

			Mascota[] listaMas = response1.getBody();
			Usuario[] listaUsu = response2.getBody();
			Servicio[] listaSer = response3.getBody();
			Historial[] listaHisto = response4.getBody();
			Reclamo[] listaRecla = response5.getBody();
			Pedido[] listaPedi = response6.getBody();

			model.addAttribute("mascotas", "La cantidad total es: " + listaMas.length);
			model.addAttribute("usuarios", "La cantidad total es: " + listaUsu.length);
			model.addAttribute("servicios", "La cantidad total es: " + listaSer.length);
			model.addAttribute("historiales", "La cantidad total es: " + listaHisto.length);
			model.addAttribute("reclamos", "La cantidad total es: " + listaRecla.length);
			model.addAttribute("pedidos", "La cantidad total es: " + listaPedi.length);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "intranet";

	}

	@RequestMapping("/perfil")
	public String perfil() {

		return "perfil";

	}

}
