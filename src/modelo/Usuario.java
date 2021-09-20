package modelo;

public class Usuario {
	
	private String nombre;
	private String id;
	private String email;
	private String usuario;
	private String contrasena;
	private String estado;
	private String rol;
	
	
    public Usuario(String nombre, String id, String email, String usuario, String contrasena, String estado) {
		super();
		this.nombre = nombre;
		this.id = id;
		this.email = email;
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.estado = estado;
	}
	


	public Usuario(String id,String nombre, String usuario, String contrasena, String rol) {
		this.nombre = nombre;
		this.id = id;
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.rol = rol;
	}



	public Usuario( String id,String nombre, String usuario, String contrasena) {
		this.nombre = nombre;
		this.id = id;
		this.usuario = usuario;
		this.contrasena = contrasena;
	}



	public Usuario(String usuario, String contrasena) {
		super();
		this.usuario = usuario;
		this.contrasena = contrasena;
	}


	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}


	public String isEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	

	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}



	public String getEstado() {
		return estado;
	}



	public String getRol() {
		return rol;
	}



	public void setRol(String rol) {
		this.rol = rol;
	}
	
	
	
	

}
