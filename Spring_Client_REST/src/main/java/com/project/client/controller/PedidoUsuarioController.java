package com.project.client.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.project.client.entity.Pedido;

@Controller
@RequestMapping("/pedidoUsuario")
public class PedidoUsuarioController {

	private String REST_PEDIDO = "http://localhost:8081/pedido/";
	
	@RequestMapping("/")
	public String index() {
		return "pedidoUsuario";
	}

	@RequestMapping("/listado")
	@ResponseBody
	public Pedido[] listado(@RequestParam("usuario") int usuario) {
		Pedido[] data = null;
		try {
			RestTemplate rt = new RestTemplate();
			ResponseEntity<Pedido[]> response = rt.getForEntity(REST_PEDIDO + "usuConsulta/" + usuario, Pedido[].class);
			data = response.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

}
