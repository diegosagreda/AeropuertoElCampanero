package modelo;
import javafx.scene.control.Button;
public class Hangar {

    private String codigo;
    private String ubicacion;
    private String capacidad;
    private String id_avion;
    private String estado;
    private String aerolinea;
    private String tarifa;
    private Button button;
  


    public Hangar(String codigo, String ubicacion, String capacidad, String estado, String tarifa, String id_avion) {
        this.codigo = codigo;
        this.ubicacion = ubicacion;
        this.capacidad = capacidad;
        this.id_avion = id_avion;
        this.estado = estado;
        this.tarifa = tarifa;
    }
    
    
    

    public Hangar(String codigo, String ubicacion, String capacidad, String estado, 
            String tarifa,String id_avion, Button button) {
        this.codigo = codigo;
        this.ubicacion = ubicacion;
        this.capacidad = capacidad;
        this.id_avion = id_avion;
        this.estado = estado;
        this.tarifa = tarifa;
        this.button = button;
    }

    


    public Button getButton() {
        return button;
    }




    public void setButton(Button button) {
        this.button = button;
    }




    public Hangar(String codigo, String estado) {
        this.codigo = codigo;
        this.estado = estado;
    }



    public String getTarifa() {
        return tarifa;
    }



    public void setTarifa(String tarifa) {
        this.tarifa = tarifa;
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
