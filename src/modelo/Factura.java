package modelo;
import javafx.scene.control.Button;
public class Factura {

    String numero,idAvion,fechaIngreso,horaIngreso,codigoHangar,estado
    ,horaSalida,fechaSalida,valor;
    Button button;

    public Factura(String numero, String idAvion, String fechaIngreso, String horaIngreso, String codigoHangar,
            Button button) {
        this.numero = numero;
        this.idAvion = idAvion;
        this.fechaIngreso = fechaIngreso;
        this.horaIngreso = horaIngreso;
        this.codigoHangar = codigoHangar;
        this.button = button;
    }
    
    

    public Factura(String numero, String idAvion, String fechaIngreso, String horaIngreso, String codigoHangar,
            String estado, String horaSalida, String fechaSalida, String valor) {
        this.numero = numero;
        this.idAvion = idAvion;
        this.fechaIngreso = fechaIngreso;
        this.horaIngreso = horaIngreso;
        this.codigoHangar = codigoHangar;
        this.estado = estado;
        this.horaSalida = horaSalida;
        this.fechaSalida = fechaSalida;
        this.valor = valor;
    }



    public Factura(String numero, String idAvion, String fechaIngreso, String horaIngreso, String codigoHangar,
            String estado) {
        this.numero = numero;
        this.idAvion = idAvion;
        this.fechaIngreso = fechaIngreso;
        this.horaIngreso = horaIngreso;
        this.codigoHangar = codigoHangar;
        this.estado = estado;
    
    }

    

    public String getHoraSalida() {
        return horaSalida;
    }



    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }



    public String getFechaSalida() {
        return fechaSalida;
    }



    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }



    public String getValor() {
        return valor;
    }



    public void setValor(String valor) {
        this.valor = valor;
    }



    public String getEstado() {
        return estado;
    }



    public void setEstado(String estado) {
        this.estado = estado;
    }



    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getIdAvion() {
        return idAvion;
    }

    public void setIdAvion(String idAvion) {
        this.idAvion = idAvion;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getHoraIngreso() {
        return horaIngreso;
    }

    public void setHoraIngreso(String horaIngreso) {
        this.horaIngreso = horaIngreso;
    }

    public String getCodigoHangar() {
        return codigoHangar;
    }

    public void setCodigoHangar(String codigoHangar) {
        this.codigoHangar = codigoHangar;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
    
    
    
}
