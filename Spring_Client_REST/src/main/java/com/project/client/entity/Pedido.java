package com.project.client.entity;

import java.io.Serializable;

public class Pedido implements Serializable {

	private int codigo;
	private String fec_Ped;
	private Usuario usuario;
	private Servicio servicio;
	private Estado estado;
	private Horario horario;
	private Hora hora;
	private double importe;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getFec_Ped() {
		return fec_Ped;
	}

	public void setFec_Ped(String fec_Ped) {
		this.fec_Ped = fec_Ped;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Servicio getServicio() {
		return servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Horario getHorario() {
		return horario;
	}

	public void setHorario(Horario horario) {
		this.horario = horario;
	}

	public Hora getHora() {
		return hora;
	}

	public void setHora(Hora hora) {
		this.hora = hora;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

}
