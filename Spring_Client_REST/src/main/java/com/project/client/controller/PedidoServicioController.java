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
import com.project.client.entity.Estado;
import com.project.client.entity.Hora;
import com.project.client.entity.Horario;
import com.project.client.entity.Pedido;
import com.project.client.entity.Servicio;
import com.project.client.entity.Usuario;

@Controller
@RequestMapping("/pedidoServicio")
public class PedidoServicioController {

	private String REST_PEDIDO = "http://localhost:8081/pedido/";
	private String REST_USUARIO = "http://localhost:8081/usuario/";
	private String REST_SERVICIO = "http://localhost:8081/servicio/";
	private String REST_ESTADO = "http://localhost:8081/estado/";
	private String REST_HORARIO = "http://localhost:8081/horario/";
	private String REST_HORA = "http://localhost:8081/hora/";

	@RequestMapping("/")
	public String index(Model model) {
		try {
			RestTemplate rt = new RestTemplate();

			ResponseEntity<Pedido[]> response1 = rt.getForEntity(REST_PEDIDO + "listar", Pedido[].class);
			ResponseEntity<Usuario[]> response2 = rt.getForEntity(REST_USUARIO + "listar", Usuario[].class);
			ResponseEntity<Servicio[]> response3 = rt.getForEntity(REST_SERVICIO + "listar", Servicio[].class);
			ResponseEntity<Estado[]> response4 = rt.getForEntity(REST_ESTADO + "listar", Estado[].class);
			ResponseEntity<Horario[]> response5 = rt.getForEntity(REST_HORARIO + "listar", Horario[].class);
			ResponseEntity<Hora[]> response6 = rt.getForEntity(REST_HORA + "listar", Hora[].class);

			Pedido[] listaPedi = response1.getBody();
			Usuario[] listaUsu = response2.getBody();
			Servicio[] listaServ = response3.getBody();
			Estado[] listaEsta = response4.getBody();
			Horario[] listaHorari = response5.getBody();
			Hora[] listaHora = response6.getBody();

			model.addAttribute("pedidos", listaPedi);
			model.addAttribute("usuarios", listaUsu);
			model.addAttribute("servicios", listaServ);
			model.addAttribute("estados", listaEsta);
			model.addAttribute("horarios", listaHorari);
			model.addAttribute("horas", listaHora);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "pedidoServicio";
	}
	
	@RequestMapping("/guardar")
	public String guardar(@RequestParam("codigo") int cod, @RequestParam("fec_Ped") String fec, 
			@RequestParam("usuario") int codUsu, @RequestParam("servicio") int codServ, 
			@RequestParam("estado") int codEsta, @RequestParam("horario") int codHorar,
			@RequestParam("hora") int codHor, @RequestParam("importe") double imp, 
			RedirectAttributes redirect) {
		try {

			Pedido bean = new Pedido();
			bean.setCodigo(cod);
			bean.setFec_Ped(fec);
			
			//Usuario
			Usuario usu = new Usuario();
			usu.setCodigo(codUsu);
			bean.setUsuario(usu);
			//Servicio
			Servicio serv = new Servicio();
			serv.setCodigo(codServ);
			bean.setServicio(serv);
			//Estado
			Estado est = new Estado();
			est.setCodigo(codEsta);
			bean.setEstado(est);
			//Horario
			Horario horar = new Horario();
			horar.setCodigo(codHorar);
			bean.setHorario(horar);
			//Hora
			Hora hor = new Hora();
			hor.setCodigo(codHor);
			
			bean.setHora(hor);			
			bean.setImporte(imp);
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
				rt.postForObject(REST_PEDIDO + "registrar", request, String.class);
				redirect.addFlashAttribute("MENSAJE", "Pedido registrado");
			} else {
				rt.put(REST_PEDIDO + "actualizar", request, String.class);
				redirect.addFlashAttribute("MENSAJE", "Pedido actualizado");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/pedidoServicio/";
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
}
