package Modelo;

import java.util.Map;

public class Planta2 {
	private Map<String, Float> errores;
	private Map<String, Float> gravedadN;
	private Map<String, Float> tiempo;
	private Boolean Finalizado;
	public Planta2() {
		
	}
	
	public Planta2(Map<String, Float> errores, Map<String, Float> gravedadN, Map<String, Float> tiempo,
			Boolean finalizado) {
		super();
		this.errores = errores;
		this.gravedadN = gravedadN;
		this.tiempo = tiempo;
		Finalizado = finalizado;
	}

	public Map<String, Float> getErrores() {
		return errores;
	}
	public void setErrores(Map<String, Float> errores) {
		this.errores = errores;
	}
	public Map<String, Float> getGravedadN() {
		return gravedadN;
	}
	public void setGravedadN(Map<String, Float> gravedadN) {
		this.gravedadN = gravedadN;
	}
	public Map<String, Float> getTiempo() {
		return tiempo;
	}
	public void setTiempo(Map<String, Float> tiempo) {
		this.tiempo = tiempo;
	}
	public Boolean getFinalizado() {
		return Finalizado;
	}
	public void setFinalizado(Boolean finalizado) {
		Finalizado = finalizado;
	}
	
}
