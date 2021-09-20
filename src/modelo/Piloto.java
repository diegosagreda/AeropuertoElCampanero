package modelo;

public class Piloto {
	
	private String id_piloto,id_aerolinea,nombre,apellido,licencia,horas_vuelo,revision_medica;

	public Piloto(String id_piloto, String id_aerolinea, String nombre, String apellido, String licencia,
			String horas_vuelo, String revision_medica) {
		super();
		this.id_piloto = id_piloto;
		this.id_aerolinea = id_aerolinea;
		this.nombre = nombre;
		this.apellido = apellido;
		this.licencia = licencia;
		this.horas_vuelo = horas_vuelo;
		this.revision_medica = revision_medica;
	}
	
	

	public Piloto(String id_piloto, String nombre, String apellido, String licencia, String horas_vuelo,
			String revision_medica) {
		super();
		this.id_piloto = id_piloto;
		this.nombre = nombre;
		this.apellido = apellido;
		this.licencia = licencia;
		this.horas_vuelo = horas_vuelo;
		this.revision_medica = revision_medica;
	}



	public Piloto(String nombre, String apellido) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
	}
	


	public Piloto(String id_piloto, String id_aerolinea, String nombre) {
		super();
		this.id_piloto = id_piloto;
		this.id_aerolinea = id_aerolinea;
		this.nombre = nombre;
	}


	public String getId_piloto() {
		return id_piloto;
	}

	public void setId_piloto(String id_piloto) {
		this.id_piloto = id_piloto;
	}

	public String getId_aerolinea() {
		return id_aerolinea;
	}

	public void setId_aerolinea(String id_aerolinea) {
		this.id_aerolinea = id_aerolinea;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getLicencia() {
		return licencia;
	}

	public void setLicencia(String licencia) {
		this.licencia = licencia;
	}

	public String getHoras_vuelo() {
		return horas_vuelo;
	}

	public void setHoras_vuelo(String horas_vuelo) {
		this.horas_vuelo = horas_vuelo;
	}

	public String getRevision_medica() {
		return revision_medica;
	}

	public void setRevision_medica(String revision_medica) {
		this.revision_medica = revision_medica;
	}
	

}
