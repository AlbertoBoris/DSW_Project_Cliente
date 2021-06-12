package com.project.client.entity;

import java.io.Serializable;

public class Distrito implements Serializable {

	private int codigo;
	private String nombre;

	/* Getters and Setters */
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

}
