package modelo;

public class Avion {
	
	private String id_avion,id_aerolinea,tipo,capacidad,modelo,tipo_propulsion,peso_nominal, num_motores;

   

	public Avion(String id_avion, String id_aerolinea, String tipo, String capacidad, String modelo,
			String tipo_propulsion, String peso_nominal, String num_motores) {
		this.id_avion = id_avion;
		this.id_aerolinea = id_aerolinea;
		this.tipo = tipo;
		this.capacidad = capacidad;
		this.modelo = modelo;
		this.tipo_propulsion = tipo_propulsion;
		this.peso_nominal = peso_nominal;
		this.num_motores = num_motores;
	}
	
	

	public Avion(String id_avion, String tipo, String capacidad, String modelo, String tipo_propulsion,
			String peso_nominal, String num_motores) {
		super();
		this.id_avion = id_avion;
		this.tipo = tipo;
		this.capacidad = capacidad;
		this.modelo = modelo;
		this.tipo_propulsion = tipo_propulsion;
		this.peso_nominal = peso_nominal;
		this.num_motores = num_motores;
	}



	public Avion(String id_avion, String id_aerolinea, String tipo, String modelo) {
		super();
		this.id_avion = id_avion;
		this.id_aerolinea = id_aerolinea;
		this.tipo = tipo;
		this.modelo = modelo;
	}



	public Avion(String id_avion,String tipo, String num_motores) {
		super();
		this.id_avion = id_avion;
		this.tipo = tipo;
		this.num_motores = num_motores;
	}
	




	public Avion(String id_avion, String id_aerolinea) {
		this.id_avion = id_avion;
		this.id_aerolinea = id_aerolinea;
	}



	public String getId_avion() {
		return id_avion;
	}

	public void setId_avion(String id_avion) {
		this.id_avion = id_avion;
	}

	public String getId_aerolinea() {
		return id_aerolinea;
	}

	public void setId_aerolinea(String id_aerolinea) {
		this.id_aerolinea = id_aerolinea;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getTipo_propulsion() {
		return tipo_propulsion;
	}

	public void setTipo_propulsion(String tipo_propulsion) {
		this.tipo_propulsion = tipo_propulsion;
	}

	public String getPeso_nominal() {
		return peso_nominal;
	}

	public void setPeso_nominal(String peso_nominal) {
		this.peso_nominal = peso_nominal;
	}

	public String getNum_motores() {
		return num_motores;
	}

	public void setNum_motores(String num_motores) {
		this.num_motores = num_motores;
	}



	@Override
	public String toString() {
		return "Avion [capacidad=" + capacidad + ", id_aerolinea=" + id_aerolinea + ", id_avion=" + id_avion
				+ ", modelo=" + modelo + ", num_motores=" + num_motores + ", peso_nominal=" + peso_nominal + ", tipo="
				+ tipo + ", tipo_propulsion=" + tipo_propulsion + "]";
	}

    
	

}
