package modelo;

public  class Solicitud {
    
    private String id_aerolinea;
    private String nombre;
    private String usuario;
    private String contrasena;
    private String email;
    private String estado;
    
    public Solicitud(String id_aerolinea, String nombre, String usuario, String contrasena, String email,
            String estado) {
        this.id_aerolinea = id_aerolinea;
        this.nombre = nombre;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.email = email;
        this.estado = estado;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
    
}