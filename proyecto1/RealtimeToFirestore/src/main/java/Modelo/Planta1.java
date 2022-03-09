package Modelo;

import java.util.Map;

public class Planta1 {
	//public int check;
	public Boolean datos;
	public Map<String, Float> elongaciones;
	public Boolean finalizado;	
	public Boolean iniciar;
	public int nRep;
	public int peso;
	public Map<String, Float> pesos;
	public Planta1() {
		
	}
	
	public Planta1(Boolean datos, Map<String, Float> elongaciones, Boolean finalizado, Boolean iniciar,
			int nRep, int peso, Map<String, Float> pesos) {
		super();
		//this.check = check;
		this.datos = datos;
		this.elongaciones = elongaciones;
		this.finalizado = finalizado;
		this.iniciar = iniciar;
		this.nRep = nRep;
		this.peso = peso;
		this.pesos = pesos;
	}

//	public int getCheck() {
//		return check;
//	}
//	public void setCheck(int check) {
//		this.check = check;
//	}
	public Boolean getDatos() {
		return datos;
	}
	public void setDatos(Boolean datos) {
		this.datos = datos;
	}
	public Map<String, Float> getElongaciones() {
		return elongaciones;
	}
	public void setElongaciones(Map<String, Float> elongaciones) {
		this.elongaciones = elongaciones;
	}
	public Boolean getFinalizado() {
		return finalizado;
	}
	public void setFinalizado(Boolean finalizado) {
		this.finalizado = finalizado;
	}
	public Boolean getIniciar() {
		return iniciar;
	}
	public void setIniciar(Boolean iniciar) {
		this.iniciar = iniciar;
	}
	public int getnRep() {
		return nRep;
	}
	public void setnRep(int nRep) {
		this.nRep = nRep;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	public Map<String, Float> getPesos() {
		return pesos;
	}
	public void setPesos(Map<String, Float> pesos) {
		this.pesos = pesos;
	}
	
}
