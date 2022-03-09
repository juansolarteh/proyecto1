package Modelo;

import java.util.Map;

public class Planta3 {
	private Map<String,Float> posX;
	private Map<String,Float> posY;
	private Map<String,Float> tiempo;
	private String urlFoto;
	private Boolean finalizado;
	public Planta3() {
		
	}
	public Planta3(Map<String, Float> posX, Map<String, Float> posY, Map<String, Float> tiempo, String urlFoto,
			Boolean finalizado) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.tiempo = tiempo;
		this.urlFoto = urlFoto;
		this.finalizado = finalizado;
	}
	public Map<String, Float> getPosX() {
		return posX;
	}
	public void setPosX(Map<String, Float> posX) {
		this.posX = posX;
	}
	public Map<String, Float> getPosY() {
		return posY;
	}
	public void setPosY(Map<String, Float> posY) {
		this.posY = posY;
	}
	public Map<String, Float> getTiempo() {
		return tiempo;
	}
	public void setTiempo(Map<String, Float> tiempo) {
		this.tiempo = tiempo;
	}
	public String getUrlFoto() {
		return urlFoto;
	}
	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}
	public Boolean getFinalizado() {
		return finalizado;
	}
	public void setFinalizado(Boolean finalizado) {
		this.finalizado = finalizado;
	}
	
}
