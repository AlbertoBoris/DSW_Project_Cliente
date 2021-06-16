package com.project.client.entity;

public class Reclamo {

	private int codigo;
	private Servicio servicioRecl;
	private String fec_Inc;
	private String asunto;
	private String descripcion;
	private String contacto;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Servicio getServicioRecl() {
		return servicioRecl;
	}

	public void setServicioRecl(Servicio servicioRecl) {
		this.servicioRecl = servicioRecl;
	}

	public String getFec_Inc() {
		return fec_Inc;
	}

	public void setFec_Inc(String fec_Inc) {
		this.fec_Inc = fec_Inc;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

}
