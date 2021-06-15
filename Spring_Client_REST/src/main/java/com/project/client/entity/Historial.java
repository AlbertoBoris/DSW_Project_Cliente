package com.project.client.entity;

import java.io.Serializable;

public class Historial implements Serializable {

	private int codigo;
	private Mascota mascota;
	private String asunto;
	private String motivo;
	private String tratamiento;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Mascota getMascota() {
		return mascota;
	}

	public void setMascota(Mascota mascota) {
		this.mascota = mascota;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}

}
