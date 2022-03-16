package Modelo;

import java.util.Map;

public class Planta2 {
	private Map<String, Float> errores;
	private Map<String, Float> gravedadN;
	private Map<String, Float> tiempo;
	private Float ErrorG;
	private Float gravedad;
	private Boolean Finalizado;
	private Boolean enviados;
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

	public Boolean getEnviados() {
		return enviados;
	}

	public void setEnviados(Boolean enviados) {
		this.enviados = enviados;
	}

	public Float getErrorG() {
		return ErrorG;
	}

	public void setErrorG(Float errorG) {
		ErrorG = errorG;
	}

	public Float getGravedad() {
		return gravedad;
	}

	public void setGravedad(Float gravedad) {
		this.gravedad = gravedad;
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
