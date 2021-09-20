package modelo;

public class Vuelo {

	private String id_vuelo;
	private String id_piloto;
	private String id_copiloto;
	private String id_avion;
	private String hora;
	private String fecha;
	private String tipo;
	private String id_aerolinea;
	private String estado;
    private String destino;
	private String procedencia;
    
    
	
    
	public Vuelo(String id_vuelo, String id_piloto, String id_copiloto, String id_avion, String hora, String fecha,
			String tipo, String id_aerolinea, String estado, String destino, String procedencia) {
		this.id_vuelo = id_vuelo;
		this.id_piloto = id_piloto;
		this.id_copiloto = id_copiloto;
		this.id_avion = id_avion;
		this.hora = hora;
		this.fecha = fecha;
		this.tipo = tipo;
		this.id_aerolinea = id_aerolinea;
		this.estado = estado;
		this.destino = destino;
		this.procedencia = procedencia;
	}


	public Vuelo(String id_vuelo, String id_piloto, String id_copiloto, String id_avion, String hora, String fecha,
			String tipo, String estado, String destino, String procedencia) {
		this.id_vuelo = id_vuelo;
		this.id_piloto = id_piloto;
		this.id_copiloto = id_copiloto;
		this.id_avion = id_avion;
		this.hora = hora;
		this.fecha = fecha;
		this.tipo = tipo;
		this.estado = estado;
		this.destino = destino;
		this.procedencia = procedencia;
	}


	public Vuelo(String id_vuelo, String id_piloto, String id_copiloto, String id_avion, String hora, String fecha,
			String tipo, String estado, String destino) {
		this.id_vuelo = id_vuelo;
		this.id_piloto = id_piloto;
		this.id_copiloto = id_copiloto;
		this.id_avion = id_avion;
		this.hora = hora;
		this.fecha = fecha;
		this.tipo = tipo;
		this.estado = estado;
		this.destino = destino;
	}


	public Vuelo(String id_vuelo, String id_piloto, String id_copiloto, String id_avion, String hora, String fecha,
			String tipo) {
		super();
		this.id_vuelo = id_vuelo;
		this.id_piloto = id_piloto;
		this.id_copiloto = id_copiloto;
		this.id_avion = id_avion;
		this.hora = hora;
		this.fecha = fecha;
		this.tipo = tipo;
		
	}
	

	public Vuelo(String id_vuelo, String id_piloto, String id_copiloto, String id_avion, String hora, String fecha,
			String tipo, String estado) {
		super();
		this.id_vuelo = id_vuelo;
		this.id_piloto = id_piloto;
		this.id_copiloto = id_copiloto;
		this.id_avion = id_avion;
		this.hora = hora;
		this.fecha = fecha;
		this.tipo = tipo;
		this.estado = estado;
	}


    

	public String getId_aerolinea() {
		return id_aerolinea;
	}


	public void setId_aerolinea(String id_aerolinea) {
		this.id_aerolinea = id_aerolinea;
	}


	public String getProcedencia() {
		return procedencia;
	}


	public void setProcedencia(String procedencia) {
		this.procedencia = procedencia;
	}


	public String getDestino() {
		return destino;
	}


	public void setDestino(String destino) {
		this.destino = destino;
	}


	public String getId_vuelo() {
		return id_vuelo;
	}

	public void setId_vuelo(String id_vuelo) {
		this.id_vuelo = id_vuelo;
	}

	public String getId_piloto() {
		return id_piloto;
	}

	public void setId_piloto(String id_piloto) {
		this.id_piloto = id_piloto;
	}

	public String getId_copiloto() {
		return id_copiloto;
	}

	public void setId_copiloto(String id_copiloto) {
		this.id_copiloto = id_copiloto;
	}

	public String getId_avion() {
		return id_avion;
	}

	public void setId_avion(String id_avion) {
		this.id_avion = id_avion;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	
}
