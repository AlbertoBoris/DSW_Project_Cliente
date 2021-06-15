package com.project.client.entity;

import java.io.Serializable;
import java.util.Date;

public class Mascota implements Serializable {

	private int codigo;
	private String nombre;
	private String animal;
	private String raza;
	private String fec_Nac;
	private Usuario usuario;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAnimal() {
		return animal;
	}

	public void setAnimal(String animal) {
		this.animal = animal;
	}

	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public String getFec_Nac() {
		return fec_Nac;
	}

	public void setFec_Nac(String fec_Nac) {
		this.fec_Nac = fec_Nac;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
