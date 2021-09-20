package modelo;

public class Hangar {

    private String codigo;
    private String ubicacion;
    private String capacidad;
    private String id_avion;
    private String estado;
    private String aerolinea;
    

    public Hangar(String codigo, String ubicacion, String capacidad, String id_avion, String estado, String aerolinea) {
        this.codigo = codigo;
        this.ubicacion = ubicacion;
        this.capacidad = capacidad;
        this.id_avion = id_avion;
        this.estado = estado;
        this.aerolinea = aerolinea;
    }


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }

    public String getId_avion() {
        return id_avion;
    }

    public void setId_avion(String id_avion) {
        this.id_avion = id_avion;
    }


    public String getEstado() {
        return estado;
    }


    public void setEstado(String estado) {
        this.estado = estado;
    }


    public String getAerolinea() {
        return aerolinea;
    }


    public void setAerolinea(String aerolinea) {
        this.aerolinea = aerolinea;
    }


    @Override
    public String toString() {
        return "Hangar [codigo=" + codigo + ", estado=" + estado + ", id_avion=" + id_avion + "]";
    }
    
    
    
    
    
}
