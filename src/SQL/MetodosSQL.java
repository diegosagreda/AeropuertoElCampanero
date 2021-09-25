package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Notificaciones.Notificacion;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.util.Duration;
import modelo.Aerolinea;
import modelo.Avion;
import modelo.Hangar;
import modelo.Piloto;
import modelo.Solicitud;
import modelo.Usuario;
import modelo.Vuelo;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class MetodosSQL {
	private final String DB = "jdbc:postgresql://localhost:5432/elcampanerodb"; 
    private final String USER = "postgres";
    private final String PASS = "postgres1";
    private Statement st = null;
    private  Connection conexion;

	public MetodosSQL() {
		super();
		try{
			Class.forName("org.postgresql.Driver");
            Connection conexion = DriverManager.getConnection(DB,USER,PASS);
             st = conexion.createStatement();
		}catch(ClassNotFoundException | SQLException ex){
			
		} 
    	
	}
	public void conectarBD(){
		try{
			conexion = DriverManager.getConnection(DB,USER,PASS);
			TrayNotification notification = new TrayNotification();
			notification.setMessage("Sistema en linea");
			notification.setAnimationType(AnimationType.POPUP);
			notification.setNotificationType(NotificationType.INFORMATION);
			notification.showAndDismiss(Duration.seconds(7));

		}catch(Exception ex){
			TrayNotification notification = new TrayNotification();
			notification.setMessage("El sistema no se encuentra en Linea");
			notification.setAnimationType(AnimationType.POPUP);
			notification.setNotificationType(NotificationType.INFORMATION);
			notification.showAndDismiss(Duration.seconds(7));

		}
	}
	//------------------------------------------------------------------------------------------
	public void activarUsuario(TableView<Usuario> tabla_usuarios,String id) {
		  try {
      	        Statement st = conexion.createStatement();
	            String sql = "update aerolineas set estado = 'ACTIVADO' where idaerolinea = "+"'"+id+"'";
	            st.execute(sql);
	            tabla_usuarios.getItems().clear();
	            cargarDatosUusuarios(tabla_usuarios); 
				
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("Usuario aerolinea activado");
				alert.show();
				//UNA VEZ SE ACTIVA UN USUARIO PASAMOS LA AEROLIENA A LA TABLA USUARIOS
				Solicitud solicitud = retornarInformacionAeroliena(id);
				String id_user = solicitud.getId_aerolinea();
				String nombre = solicitud.getNombre();
				String usuario = solicitud.getUsuario();
				String contrasena = solicitud.getContrasena();
				String rol = "Aerolinea";
				
				//CREAMOS LA SENTENCIA PARA AGREGAR UN NUEVO DATO A LA TABLA USUARIOS
				
				String sql2 = "insert into usuarios(idusuario,nombre,usuario,contrasena,rol)";
	            sql2 += "values ('"+id_user+"','"+nombre+"','"+usuario+"','"+contrasena+"','"+rol+"')";
				st.execute(sql2);
	            st.close();
				//new Notificacion("Usuario Activado",1);

       }catch (Exception ex) {
			
	  }
		
	}
	public Usuario buscarUsuarioEnAdministracion (String id){
		Usuario usuario = null;
		try{
			java.sql.Statement st = conexion.createStatement();
            
			String sql = "SELECT * FROM usuarios WHERE idusuario = "+"'"+id+"'";
			ResultSet resultSet = st.executeQuery(sql);
   
			while(resultSet.next()){
				String id_usuario = resultSet.getString("idusuario");
				String nombre = resultSet.getString("nombre");
				String usuario1 = resultSet.getString("usuario");
				String contrasena = resultSet.getString("contrasena");

				if(!id_usuario.equals("")) {
					usuario = new Usuario(id, nombre, usuario1, contrasena);
				}
			};

		}catch(Exception e){

		}
		return usuario;
	}
	//-----------------------------------------------------------------------------------------
	public void inactivarUsuario(TableView<Usuario> tabla_usuarios,String id) {
		   try {
       	    Statement st = conexion.createStatement();
	            String sql = "update aerolineas set estado = 'DESACTIVADO' where idaerolinea = "+"'"+id+"'";
	            st.execute(sql);
	            tabla_usuarios.getItems().clear();
	            cargarDatosUusuarios(tabla_usuarios);
				
				//AL INACTIVAR USUARIO LO ELIMINAMOS DE LOS USUARIOS
				Solicitud solicitud = retornarInformacionAeroliena(id);
				String usuario = solicitud.getUsuario();
				String sql2 = "DELETE FROM usuarios WHERE usuario = "+"'"+usuario+"'";
				st.execute(sql2);
				new Notificacion("Usuario Desactivado", 1);
	            st.close();
				
             }catch (Exception ex) {
			
		 }
		
	}
	public void eliminarUsuario(TableView<Usuario> tabla_usuarios,String id_aerolinea) {
		   try {
	       	    Statement st = conexion.createStatement();
		            String sql = "delete from aerolineas where idaerolinea = "+"'"+id_aerolinea+"'";
		            st.execute(sql);
		            st.close();
		            tabla_usuarios.getItems().clear();
		            cargarDatosUusuarios(tabla_usuarios);
		            
		            Alert alert = new Alert(AlertType.INFORMATION);
		            alert.setContentText("Solicitud Eliminada");
		            alert.show();
	             }catch (Exception ex) {
				
			 }
	}
	//------------------------------------------------------------------------------------------------
	//METODO PARA INICIAR SESION COMO ADMINISTRADOR
    public boolean iniciarSesion(String user, String pass,String rol) {
    	boolean estaa = false;
    	    // LOGIN - BUSQUEDA DE ADMINISTRADORES PARA INGRESAR EN EL SISTEMA
		        try{
		                java.sql.Statement st = conexion.createStatement();
			            String sql = "SELECT * FROM usuarios WHERE usuario = "+"'"+user+"'"+"AND contrasena = "+"'"+pass+"'"+"AND rol = "+"'"+rol+"'";
			            ResultSet resultSet = st.executeQuery(sql);

		            while(resultSet.next()){
		            	String usuario = resultSet.getString("usuario");
		            	if(!usuario.equals("")) estaa = true;    
		            };
		        }catch (Exception e) {}
	    return estaa;
    }//METODO PARA INICIAR SESION COMO ADMINISTRADOR
    public boolean iniciarSesionAerolinea(String user, String pass,String rol) {
	
		String estado = "ACTIVADO";
    	boolean estaa = false;
    	    // LOGIN - BUSQUEDA DE ADMINISTRADORES PARA INGRESAR EN EL SISTEMA
		        try{
		                java.sql.Statement st = conexion.createStatement();
			            String sql = "SELECT * FROM aerolineas WHERE usuario = "+"'"+user+"'"+"AND contrasena = "+"'"+pass+"'"+"AND estado = "+"'"+estado+"'";
			            ResultSet resultSet = st.executeQuery(sql);

		            while(resultSet.next()){
		            	String usuario = resultSet.getString("usuario");
		            	if(!usuario.equals("")) estaa = true;    
		            };
		        }catch (Exception e) {}
	    return estaa;
    }
	public boolean iniciarSesionAdministradorHangares(String user, String pass,String rol) {
	
    	boolean estaa = false;
    	    // LOGIN - BUSQUEDA DE ADMINISTRADORES PARA INGRESAR EN EL SISTEMA
		        try{
		                java.sql.Statement st = conexion.createStatement();
			            String sql = "SELECT * FROM usuarios WHERE usuario = "+"'"+user+"'"+"AND contrasena = "+"'"+pass+"'"+"AND rol = "+"'"+rol+"'";
			            ResultSet resultSet = st.executeQuery(sql);

		            while(resultSet.next()){
		            	String usuario = resultSet.getString("usuario");
		            	if(!usuario.equals("")) estaa = true;    
		            };
		        }catch (Exception e) {}
	    return estaa;
    }

 
    //--------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------
    //METODO PARA BUSCAR USUARIO POR ID
    public Boolean buscarAerlineaPorID(String id) {
    	Boolean estaa = false;
    	String usuario = "xxx";  
    	  try{
 		      
	            java.sql.Statement st = conexion.createStatement();
	            
		            String sql = "SELECT * FROM aerolineas WHERE idaerolinea = "+"'"+id+"'";
		            ResultSet resultSet = st.executeQuery(sql);
	       
	            while(resultSet.next()){
	            	usuario = resultSet.getString("usuario");
	            	if(usuario.equals("xxx")) {
	            		estaa = false;
	            	}else {
	            		estaa = true;
	            	}
	                  
	            };
	        }catch (Exception e) {
				
			}
		
	    return estaa;
	 }
    //----------------------------------------------------------------------------------------------------------
    //METODO PARA OBSERVAR EL ESTADO DE LA SOLICITUD   APROBADO - NO APROBADO
    public Boolean mirarEstadoSolicitudRegisro(String id) {
    	Boolean estaa = false;
    	String usuario = "xxx";  
   	   try{
		      
           java.sql.Statement st = conexion.createStatement();
           
	            String sql = "SELECT * FROM aerolineas WHERE idaerolinea = "+"'"+id+"'"+"AND estado = 'ACTIVADO'";
	            ResultSet resultSet = st.executeQuery(sql);
      
           while(resultSet.next()){
           	usuario = resultSet.getString("usuario");
           	if(usuario.equals("xxx")) {
           		estaa = false;
           	}else {
           		estaa = true;
           	}
                 
           };
         }catch (Exception e) {
			
		}
		
	    return estaa;
	 }
    //--------------------------------------------------------------------------------------------------------------
    public boolean buscarIDPilotoEnGeneral(String id) {
    	boolean esta = false;
	    	try{ 
	            java.sql.Statement st = conexion.createStatement();
	            
		            String sql = "SELECT * FROM pilotos WHERE idpiloto = "+"'"+id+"'";
		            ResultSet resultSet = st.executeQuery(sql);
					
	            while(resultSet.next()){
	            	String idPiloto = resultSet.getString("idpiloto");
	            	if(!idPiloto.equals("")) {
	            		esta = true;
	            	}
	                  
	            };
	        }catch (Exception e) {
				
			}
    	

    	return esta;
    }
    //----------------------------------------------------------------------------------------------------------------
    public boolean buscarIDAvionEnGeneral(String id) {
    	boolean esta = false;
	    	try{ 
	            java.sql.Statement st = conexion.createStatement();
	            
		            String sql = "SELECT * FROM aviones WHERE idavion = "+"'"+id+"'";
		            ResultSet resultSet = st.executeQuery(sql);
	       
	            while(resultSet.next()){
	            	String idavion = resultSet.getString("idavion");
	            	if(!idavion.equals("")) {
	            		esta = true;
	            	}
	                  
	            };
	        }catch (Exception e) {
				
			}
    	

    	return esta;
    }
    //----------------------------------------------------------------------------------------------------------------
    public boolean buscarIDPilotoPorAerolinea(String id,String idaerolinea) {
    	boolean esta = false;
	    	try{ 
	            java.sql.Statement st = conexion.createStatement();
	            
		            String sql = "SELECT * FROM pilotos WHERE idpiloto = "+"'"+id+"'"+"AND idaerolinea = "+"'"+idaerolinea+"'";
		            ResultSet resultSet = st.executeQuery(sql);
	       
	            while(resultSet.next()){
	            	String idPiloto = resultSet.getString("idpiloto");
	            	if(!idPiloto.equals("")) {
	            		esta = true;
	            	}
	                  
	            };
	        }catch (Exception e) {
				
			}
    	return esta;
    }
    
    //---------------------------------------------------------------------------------------------------------------------
    public String buscarIDaerolinea(String usuario) {
    	String idAerolienaEnviar = null;
    	try{ 
            java.sql.Statement st = conexion.createStatement();
            
	            String sql = "SELECT * FROM aerolineas WHERE usuario = "+"'"+usuario+"'";
	            ResultSet resultSet = st.executeQuery(sql);
       
            while(resultSet.next()){
            	String idAeroliena = resultSet.getString("idaerolinea");
            	if(!idAeroliena.equals("")) {
            		idAerolienaEnviar = idAeroliena;
            	}
                  
            };
        }catch (Exception e) {
			
		}
    	return idAerolienaEnviar;
    }
    //-----------------------------------------------------------------------------------------------------------------
    public Piloto retornarInformacionPiloto(String id) {
    	Piloto piloto = null;
    	try{ 
            java.sql.Statement st = conexion.createStatement();
            
	            String sql = "SELECT * FROM pilotos WHERE idpiloto = "+"'"+id+"'";
	            ResultSet resultSet = st.executeQuery(sql);
       
            while(resultSet.next()){
            	String nombre = resultSet.getString("nombre");
            	String apellido = resultSet.getString("apellido");
                piloto = new Piloto(nombre, apellido);
                  
            };
        }catch (Exception e) {
			
		}
    	
    	return piloto;
    }
    //--------------------------------------------------------------------------------------------------------------
    public boolean buscarIDAvion(int tipo, String id,String Aerolinea) {
    	
    	boolean esta = false;
    	if(tipo == 0) {
	    	try{ 
	            java.sql.Statement st = conexion.createStatement();
	            
		            String sql = "SELECT * FROM aviones WHERE idavion = "+"'"+id+"'";
		            ResultSet resultSet = st.executeQuery(sql);
	       
	            while(resultSet.next()){
	            	String idAvion = resultSet.getString("idavion");
	            	if(!idAvion.equals("")) {
	            		esta = true;
	            	}
	                  
	            };
	        }catch (Exception e) {}
    	}
    	if(tipo == 1) {
    		try{ 
	            java.sql.Statement st = conexion.createStatement();
	            
		            String sql = "SELECT * FROM aviones WHERE idavion = "+"'"+id+"'"+"AND idaerolinea = "+"'"+Aerolinea+"'";
		            ResultSet resultSet = st.executeQuery(sql);
	       
	            while(resultSet.next()){
	            	String idAvion = resultSet.getString("idavion");
	            	if(!idAvion.equals("")) {
	            		esta = true;
	            	}
	                  
	            };
	        }catch (Exception e) {}
    		
    	}
    	return esta;
    }
    //---------------------------------------------------------------------------------------------------------------
    public Avion retornarInformacionAvion(String id) {
    	Avion avion = null;
    	try{ 
            java.sql.Statement st = conexion.createStatement();
            
	            String sql = "SELECT * FROM aviones WHERE idavion = "+"'"+id+"'";
	            ResultSet resultSet = st.executeQuery(sql);
				
            while(resultSet.next()){
				
				String capacidad = resultSet.getString("capacidad");;
				String id_aerolinea = resultSet.getString("idaerolinea");
				String tipo = resultSet.getString("tipo");
				String tipo_propulsion = resultSet.getString("tipopropulsion");
				String modelo = resultSet.getString("modelo");
				String num_motores = resultSet.getString("numeromotores");
				String peso_nominal = resultSet.getString("pesonominal");
				
                avion = new Avion(id,id_aerolinea,tipo,capacidad,modelo,tipo_propulsion,peso_nominal,num_motores);
            };
        }catch (Exception e) {}
    	
    	return avion;
    }

	public Solicitud retornarInformacionAeroliena(String id){
		Solicitud solicitud = null;
		try{ 
            java.sql.Statement st = conexion.createStatement();
            
	            String sql = "SELECT * FROM aerolineas WHERE idaerolinea = "+"'"+id+"'";
	            ResultSet resultSet = st.executeQuery(sql);
       
            while(resultSet.next()){
            	String idaerolinea = resultSet.getString("idaerolinea");
				String nombre = resultSet.getString("nombre");
				String usuario = resultSet.getString("usuario");
				String contrasena = resultSet.getString("contrasena");
				String email = resultSet.getString("email");
				String estado = resultSet.getString("estado");

				solicitud = new Solicitud(idaerolinea, nombre, usuario, contrasena, email, estado);
                  
            };
        }catch (Exception e) {}
		return solicitud;
	}
	public String tomarNombreDeAerolineaConId(String id){
		String aerolinea = "";
		try{ 
            java.sql.Statement st = conexion.createStatement();
            
	            String sql = "SELECT * FROM aerolineas WHERE idaerolinea = "+"'"+id+"'";
	            ResultSet resultSet = st.executeQuery(sql);
       
            while(resultSet.next()){
            	aerolinea = resultSet.getString("nombre");      
            };
        }catch (Exception e) {}
		return aerolinea;
	}

    //---------------------------------------------------------------------------------------------------------------
    public boolean buscarDisponibilidadDeAgenda(String hora, String  fecha) {
    	boolean sihay = true;
     	try{ 
            java.sql.Statement st = conexion.createStatement();
            
	            String sql = "SELECT * FROM vuelos WHERE hora ="+"'"+hora+"'"+" AND fecha ="+"'"+fecha+"'";
	            ResultSet resultSet = st.executeQuery(sql);
       
            while(resultSet.next()){
            	String horas = resultSet.getString("hora");
            	if(!horas.equals("")) {
            		sihay = false;
            	}
            };
        }catch (Exception e) {
			
		}
    	return sihay;
    }
    //-----------------------------------------------------------------------------------------------------------------
    public boolean buscarIdVuelo(String id) {
    	boolean esta = false;
     	try{ 
            java.sql.Statement st = conexion.createStatement();
            
	            String sql = "SELECT * FROM vuelos WHERE idvuelo ="+"'"+id+"'";
	            ResultSet resultSet = st.executeQuery(sql);
       
            while(resultSet.next()){
            	String horas = resultSet.getString("hora");
            	if(!horas.equals("")) {
            		esta = true;
            	}
            };
        }catch (Exception e) {
			
		}
    	return esta;
    	
    }
	//-----------------------------------------------------------------------------------------------------------------
	public boolean buscarHangar(String codigo) {
    	boolean esta = false;
     	try{ 
            java.sql.Statement st = conexion.createStatement();
            
	            String sql = "SELECT * FROM hangares WHERE codigo ="+"'"+codigo+"'";
	            ResultSet resultSet = st.executeQuery(sql);
       
            while(resultSet.next()){
            	String cod = resultSet.getString("codigo");
            	if(!cod.equals("")) {
            		esta = true;
            	}
            };
        }catch (Exception e) {
			
		}
    	return esta;
    	
    }
	public Hangar retornarInformacionHangar(String codigo) {
    	Hangar hangar = null;
     	try{ 
            java.sql.Statement st = conexion.createStatement();
            
	            String sql = "SELECT * FROM hangares WHERE codigo ="+"'"+codigo+"'";
	            ResultSet resultSet = st.executeQuery(sql);
       
            while(resultSet.next()){
            	String codigoH = resultSet.getString("codigo");
				String estado = resultSet.getString("estado");
            	hangar = new Hangar(codigoH, estado);
            };
        }catch (Exception e) {
			
		}
    	return hangar;
    	
    }
	//---------------------------------------------------------------------------------------------------------------------
	public Hangar consultarDisponibilidadDeHangares(){
		Hangar hangar = null;
		try{ 
            java.sql.Statement st = conexion.createStatement();
            
	            String sql = "SELECT * FROM hangares";
	            ResultSet resultSet = st.executeQuery(sql);
       
            while(resultSet.next()){
            	String cod = resultSet.getString("codigo");
				String ubicacion = resultSet.getString("ubicacion");
				String capacidad = resultSet.getString("capacidad");
				String id_avion = resultSet.getString("id_avion");
				String estado = resultSet.getString("estado");
				String aerolinea = resultSet.getString("aerolinea");
                if(id_avion.equals("Vacio")){
					hangar = new Hangar(cod, ubicacion,capacidad,id_avion,estado,aerolinea);
					return hangar;
				}
            };
        }catch (Exception e) {
			
		}
		return hangar;
	}
	//--------------------------------------------------------------------------------------------------------------
    public boolean buscarIDAerolienaEnVuelos(String id) {
    	boolean esta = false;
     	try{ 
            java.sql.Statement st = conexion.createStatement();
            
	            String sql = "SELECT * FROM vuelos WHERE idaerolinea ="+"'"+id+"'";
	            ResultSet resultSet = st.executeQuery(sql);
       
            while(resultSet.next()){
            	String horas = resultSet.getString("hora");
            	if(!horas.equals("")) {
            		esta = true;
            	}
            };
        }catch (Exception e) {
			
		}
    	return esta;
    }
    //--------------------------------------------------------------------------------------------------------------
    public boolean buscarfechaEnVuelos(String fecha) {
    	boolean esta = false;
     	try{ 
            java.sql.Statement st = conexion.createStatement();
            
	            String sql = "SELECT * FROM vuelos WHERE fecha ="+"'"+fecha+"'";
	            ResultSet resultSet = st.executeQuery(sql);
       
            while(resultSet.next()){
            	String horas = resultSet.getString("hora");
            	if(!horas.equals("")) {
            		esta = true;
            	}
            };
        }catch (Exception e) {
			
		}
    	return esta;
    }
    //---------------------------------------------------------------------------------------------------------------
    public boolean buscarFechaYAerolineaVuelos(String fecha, String aerolinea) {
    	boolean esta = false;
     	try{ 
            java.sql.Statement st = conexion.createStatement();
            
	            String sql = "SELECT * FROM vuelos WHERE fecha ="+"'"+fecha+"'"+" AND "+" idaerolinea = "+"'"+aerolinea+"'";
	            ResultSet resultSet = st.executeQuery(sql);
       
            while(resultSet.next()){
            	String horas = resultSet.getString("hora");
            	if(!horas.equals("")) {
            		esta = true;
            	}
            };
        }catch (Exception e) {
			
		}
    	return esta;
    }
    public boolean buscarSolicitudRegistroNueva() {
    	boolean hay = false;
    	String estado = "";
    	ArrayList<String> estados = new ArrayList<String>();
     	   try{
 		      
   	        java.sql.Statement st = conexion.createStatement();
   	        
   	            String sql = "SELECT * FROM aerolineas";
   	            ResultSet resultSet = st.executeQuery(sql);
   	   
   	        while(resultSet.next()){
   	             estado = resultSet.getString("estado");
   	             estados.add(estado);
   	        };
   	    }catch (Exception e) {}
     	if(estados.contains("DESACTIVADO")) {
     		hay = true;
     	}
    	return hay;
    }
    //----------------------------------------------------------------------------------------------------------------
    public Vuelo retornarVueloDeAerolinea(String id,String idAerolinea) {
    	Vuelo vuelo = null;
     	try{ 
            java.sql.Statement st = conexion.createStatement();
            
	            String sql = "SELECT * FROM vuelos WHERE idvuelo ="+"'"+id+"'"+"AND idaerolinea = "+"'"+idAerolinea+"'";
	            ResultSet resultSet = st.executeQuery(sql);
       
            while(resultSet.next()){
            	String horas = resultSet.getString("hora");
            	if(!horas.equals("")) {
            		String id_vuelo = resultSet.getString("idvuelo");
            		String id_piloto = resultSet.getString("idpiloto");
            		String id_copiloto = resultSet.getString("idcopiloto");
            		String id_avion = resultSet.getString("idavion");
            		String hora = resultSet.getString("hora");
            		String fecha = resultSet.getString("fecha");
            		String tipo = resultSet.getString("tipo");
					String id_aerolinea = resultSet.getString("idaerolinea");
					String estado = resultSet.getString("estado");
					String destino = resultSet.getString("destino");
					String procedencia = resultSet.getString("procedencia");
            	
            		vuelo = new Vuelo(id_vuelo,id_piloto, id_copiloto, id_avion, hora,fecha,tipo,id_aerolinea,estado,destino,procedencia);
            	}
            };
        }catch (Exception e) {
			
		}
    	return vuelo;
    	
    }
	 //----------------------------------------------------------------------------------------------------------------
	public Vuelo retornarVueloEnGeneral(String id) {
			Vuelo vuelo = null;
			 try{ 
				java.sql.Statement st = conexion.createStatement();
				
					String sql = "SELECT * FROM vuelos WHERE idvuelo ="+"'"+id+"'";
					ResultSet resultSet = st.executeQuery(sql);
		   
				while(resultSet.next()){
					String horas = resultSet.getString("hora");
					if(!horas.equals("")) {
						String id_vuelo = resultSet.getString("idvuelo");
						String id_piloto = resultSet.getString("idpiloto");
						String id_copiloto = resultSet.getString("idcopiloto");
						String id_avion = resultSet.getString("idavion");
						String hora = resultSet.getString("hora");
						String fecha = resultSet.getString("fecha");
						String tipo = resultSet.getString("tipo");
						String id_aerolinea = resultSet.getString("idaerolinea");
						String estado = resultSet.getString("estado");
						String destino = resultSet.getString("destino");
						String procedencia = resultSet.getString("procedencia");
					
						vuelo = new Vuelo(id_vuelo,id_piloto, id_copiloto, id_avion, hora,fecha,tipo,id_aerolinea,estado,destino,procedencia);
					}
				};
			}catch (Exception e) {
				
			}
			return vuelo;
			
		}
    //---------------------------------------------------------------------------------------------------------------
	
    public void modificarInformacionVuelo(String id_vuelo,String id_piloto,String id_copiloto, String id_avion,String tipoVuelo,String destino,String procedencia) {
		try {
			Statement st = conexion.createStatement();

			//SI EXISTE UN CAMBIO DE TIPO DE VUELO MODIFICAMOS EL DESTINO Y LA PROCEDENCIA
			Vuelo vuelo = retornarVueloEnGeneral(id_vuelo);
			if(vuelo != null){
				String tipoVueloOriginal = vuelo.getTipo();
				String destinoOriginal = vuelo.getDestino();
				String procedenciaOriginal = vuelo.getProcedencia();

				if(!tipoVueloOriginal.equals(tipoVuelo)){
					
					String sql = "update vuelos set destino ="+"'"+procedencia+"'"+","+"procedencia = "+"'"+destino+"'"+" where idvuelo = "+"'"+id_vuelo+"'";
					st.execute(sql);
				}else{
					String sql = "update vuelos set destino ="+"'"+destino+"'"+","+"procedencia = "+"'"+procedencia+"'"+" where idvuelo = "+"'"+id_vuelo+"'";
					st.execute(sql);
				}
			}

      	      st = conexion.createStatement();
			  String sql = "update vuelos set idpiloto ="+"'"+id_piloto+"'"+","+"idcopiloto ="+"'"+id_copiloto+"'"+","+"idavion="+"'"+id_avion+"'"+","
			               +"tipo ="+"'"+tipoVuelo+"'"+" where idvuelo = "+"'"+id_vuelo+"'";
	            st.execute(sql);
	            st.close();
	            Notificacion notificacion = new Notificacion("Informacion modificada con exito", 1);


         }catch (Exception ex) {
			
	     }
    	
    }
    public void modificarInformacionPiloto(String id_pilotoModificar,String nombre, String apellido, String licencia,String horas_vuelo, String revision_medica,String id_piloto) {
    	
    	try {
      	    Statement st = conexion.createStatement();
      	      
	            String sql = "UPDATE pilotos set idpiloto = "+"'"+id_pilotoModificar+"'"+","+" nombre ="+"'"+nombre+"'"+","+" apellido ="+"'"+apellido+"'"+","
      	                    +" licencia ="+"'"+licencia+"'"+","+" horasvuelo = "+"'"+horas_vuelo+"'"+","+" revisionmedica ="+"'"+revision_medica+"'"+" WHERE idpiloto = "+"'"+id_piloto+"'";
	            
	    
	            st.execute(sql);
	          
	            st.close();
	            new Notificacion("Informacion modificada con exito", 1);
         }catch (Exception ex) {
			
	     }
    }
    public void modificarInformacionAvion(String id_avionModificar,String tipo, String capacidad, String modelo,String tipo_propulsion, String peso_nominal, String num_motores,String id_avion) {
    	try {
      	    Statement st = conexion.createStatement();
	            String sql = "update aviones set idavion ="+"'"+id_avionModificar+"'"+","+"tipo ="+"'"+tipo+"'"+","+"capacidad ="+"'"+capacidad+"'"+","
      	                    +"modelo ="+"'"+modelo+"'"+","+" tipopropulsion= "+"'"+tipo_propulsion+"'"+","+" pesonominal ="+"'"+peso_nominal+"'"+","
	            		    +" numeromotores = "+"'"+num_motores+"'"+" where idavion = "+"'"+id_avion+"'";
	            st.execute(sql);
	            st.close();
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("Informacion modificada con exito");
				alert.show();
	        
         }catch (Exception ex) {
			
	     }
    }
	public void modificarInformacionHangar(String codigo,String ubicacion, String capacidad, String tarifa) {
    	try {
      	    Statement st = conexion.createStatement();
	            String sql = "update hangares set ubicacion ="+"'"+ubicacion+"'"+","+"capacidad ="+"'"+capacidad+"'"+","
      	                    +"tarifa ="+"'"+tarifa+"'"+" where codigo = "+"'"+codigo+"'";
	            st.execute(sql);
	            st.close();
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("Informacion modificada con exito");
				alert.show();
         }catch (Exception ex) {
			
	     }
    }


	//--------------------------------------------------------------------------------------------------------------------------------------------------------
    public void reprogramarVuelo(String hora,String fecha,String id_vuelo) {
    	try {
      	    Statement st = conexion.createStatement();
	            String sql = "update vuelos set hora = "+"'"+hora+"'"+","+"fecha = "+"'"+fecha+"'"+" where idvuelo = "+"'"+id_vuelo+"'";
	            st.execute(sql);
	            st.close();
	            Notificacion notificacion = new Notificacion("Vuelo reprogramado con exito", 1);
         }catch (Exception ex) {
			
	     }
    }
    //----------------------------------------------------------------------------------------------------------------
    public void cancelarVueloDesdeAerolinea(String id_vuelo) {
    	try {
    		Statement st = conexion.createStatement();
    		String sql = "update vuelos set estado = 'CANCELADO' where idvuelo = "+"'"+id_vuelo+"'";
    		st.execute(sql);
            st.close();
            new Notificacion("Vuelo Cancelado", 1);
    	}catch (Exception e) {

		}
    }
    public void activarVueloAerolinea(String id_vuelo) {
    	try {
    		Statement st = conexion.createStatement();
    		String sql = "update vuelos set estado = 'AGENDADO' where idvuelo = "+"'"+id_vuelo+"'";
    		st.execute(sql);
            st.close();
            new Notificacion("Vuelo Activado", 1);
       
    	}catch (Exception e) {

		}
    }
    //----------------------------------------------------------------------------------------------------------------
    public void cancelarVueloDesdeAdministracion(int tipoCancelacion, String id_vuelo, String aerolinea, String fecha) {
    	/*
    	 * TIPOS DE CANCELACION
    	 * TIPO 1: CANCELAR POR ID DE VUELO
    	 * TIPO 2: CANCELAR POR NOMBRE DE AEROLINEA
    	 * TIPO 3: CANCELAR POR FECHA
    	 * */
    	DateTimeFormatter dtf5 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fech = dtf5.format(LocalDateTime.now());
      
        
    	if(tipoCancelacion == 1) {
    		boolean esta = buscarIdVuelo(id_vuelo);
    		if(esta) {
	    		try {
		    		Statement st = conexion.createStatement();
		    		String sql = "update vuelos set estado = 'CANCELADO' where idvuelo = "+"'"+id_vuelo+"'";
		    		st.execute(sql);
		            st.close();
		           new Notificacion("Vuelo Cancelado", 1);
	    		}catch (Exception e) {}
    		}else {
    			new Notificacion("No existe un vuelo con la ID registrada",3);
    		}
    	}if(tipoCancelacion == 2) {
    		boolean esta = buscarIDAerolienaEnVuelos(aerolinea);
    		if(esta) {
	    		try {
		    		Statement st = conexion.createStatement();
		    		String sql = "update vuelos set estado = 'CANCELADO' where idaerolinea = "+"'"+aerolinea+"'";
		    		st.execute(sql);
		            st.close();
		           new Notificacion("Vuelo Cancelado", 1);
	    		}catch (Exception e) {}
    		}else {
    			new Notificacion("La aeroliena registrada no tiene vuelos agendados",3);
    		}
    		
    	}if(tipoCancelacion == 3) {
    		boolean esta = buscarfechaEnVuelos(fecha);
    		if(esta) {
	    		try {
			    		Statement st = conexion.createStatement();
			    		String sql = "update vuelos set estado = 'CANCELADO' where fecha = "+"'"+fecha+"'";
			    		st.execute(sql);
			            st.close();
			            new Notificacion("Vuelo Cancelado", 1);
	    	
	    		}catch (Exception e) {}
    		}else if(!fecha.equals(fech)) {
    			new Notificacion("No hay vuelos en la fecha registrada",3);
    		}
    		if(fech.equals(fecha)) {
        		boolean esta2 = buscarfechaEnVuelos(fecha);
        		if(!esta2) {
        			new Notificacion("No hay vuelos agendados el dia de hoy",3);
        		}
        	} 
    	}
    }
   
    //--------------------------------------------------------------------------------------------------------------
    public String buscarUltimoRegistroVuelos() {
    	String ultimoIDVuelos = "0";
    	ArrayList<Integer> listaIds = new ArrayList<>();
    	try{ 
            java.sql.Statement st = conexion.createStatement();
            
	            String sql = "SELECT * FROM vuelos";
	            ResultSet resultSet = st.executeQuery(sql);
       
            while(resultSet.next()){
            	ultimoIDVuelos = resultSet.getString("idvuelo");
            	
            	listaIds.add(Integer.parseInt(ultimoIDVuelos));
            };
            listaIds.sort(null);
            ultimoIDVuelos =  ""+listaIds.get(listaIds.size()-1);
        }catch (Exception e) {
			
		}
    
    	return ultimoIDVuelos;
    }
    public void eliminarPiloto(String idPiloto) {
    	try {
    		java.sql.Statement st = conexion.createStatement();
    		
    		String sql = "DELETE FROM pilotos WHERE idpiloto = "+"'"+idPiloto+"'";
    		st.execute(sql);
    		new Notificacion("Piloto eliminado con exito", 1);
    	}catch (Exception e) {
		
		}
    	
    }
    public void eliminarAvion(String idAvion) {
    	try {
    		java.sql.Statement st = conexion.createStatement();
    		
    		String sql = "DELETE FROM aviones WHERE idavion = "+"'"+idAvion+"'";
    		st.execute(sql);
    		new Notificacion("Avion eliminado con exito", 1);
    	}catch (Exception e) {
		
		}
    	
    }
    public void eliminarVuelo(String id_vuelo) {
    	try {
    		java.sql.Statement st = conexion.createStatement();
    		
    		String sql = "DELETE FROM vuelos WHERE id_vuelo = "+"'"+id_vuelo+"'";
    		st.execute(sql);
    		Notificacion notificacion = new Notificacion("Vuelo eliminado con exito", 1);
    	}catch (Exception e) {
		
		}
    }
	public void eliminarHangar(String codigo) {
    	try {
    		java.sql.Statement st = conexion.createStatement();
    		
    		String sql = "DELETE FROM hangares WHERE codigo = "+"'"+codigo+"'";
    		st.execute(sql);
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("Hangar eliminado con exito");
			alert.show();
    		//new Notificacion("Vuelo eliminado con exito", 1);
    	}catch (Exception e) {
		
		}
    }
	public void eliminarUsuarioAdministrador(String id_usuario){
		try{
			java.sql.Statement st = conexion.createStatement();
    		
    		String sql = "DELETE FROM usuarios WHERE idusuario = "+"'"+id_usuario+"'";
    		st.execute(sql);
    		//new Notificacion("Vuelo eliminado con exito", 1);
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("Usuario eliminado con exito");
			alert.show();
		}catch(Exception e){

		}
	}

    
    //---------------------------------------------------------------------------------------------------------------
    //METDODO REGISTRAR NUEVA SOLICTUD DE AEROLINEA
    public void registrarSolicitudDeUsuario( String idAero,String nomAerol,String Usuar, String contra,String email,  String estado,int tipo) {
    	//Tipo 1: Desde la Aerolinea
		//Tipo 2: Desde administracion
		if(tipo == 1){
			boolean esta = buscarAerlineaPorID(idAero);
			boolean estaEstado = mirarEstadoSolicitudRegisro(idAero);
			if(!esta) {
				try{
				
					java.sql.Statement st = conexion.createStatement();
					String sql = "insert into aerolineas(idaerolinea,nombre,usuario,contrasena,email,estado)";
					sql += "values ('"+idAero+"','"+nomAerol+"','"+Usuar+"','"+contra+"','"+email+"','"+estado+"')";
					st.execute(sql);
					//new Notificacion("Solicitud enviada con exito", 1);
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("Solicitud enviada con exito");
					alert.show();
				}catch(Exception e ){
				}
				
			}else if(!estaEstado) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Ya se encuentra una solicitud con la id registrada, se encuentra pendiente por aprobacion de administracion");
				alert.show();
			}else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Ya se encuentra una Usuario con la id registrada, recuerda que solo puedes crear un solo usuario con la ID de la organizacion");
				alert.show();
				
			}
		}else if(tipo == 2){
			boolean esta = buscarAerlineaPorID(idAero);
			boolean estaEstado = mirarEstadoSolicitudRegisro(idAero);
			if(!esta) {
				try{
				
					java.sql.Statement st = conexion.createStatement();
					String sql = "insert into aerolineas(idaerolinea,nombre,usuario,contrasena,email,estado)";
					sql += "values ('"+idAero+"','"+nomAerol+"','"+Usuar+"','"+contra+"','"+email+"','"+estado+"')";
					st.execute(sql);
					//new Notificacion("Solicitud enviada con exito", 1);
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Aerolinea registrar con exito");
					alert.setContentText("Recuerde activar el usuario, para que dar ingreso al sistema");
					alert.show();

				}catch(Exception e ){
				}
				
			}else if(!estaEstado) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Ya se encuentra una solicitud con la id registrada, se encuentra pendiente por aprobacion de administracion");
				alert.show();
			}else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Ya se encuentra una Usuario con la id registrada, recuerda que solo puedes crear un solo usuario con la ID de la organizacion");
				alert.show();
				
			}
		}
    }
    //---------------------------------------------------------------------------------------------------------------------------------
    //METODO PARA REGISTRAR UN NUEVO PILOTO EN LA BASE DE DATOS
    public void registrarPilotoEnBD(String id_piloto, String id_aero, String nombre,String apellido ,String licencia, String hrs_vuelo, String fechaRevi) {
    	boolean esta = buscarIDPilotoEnGeneral(id_piloto);
    	if(!esta) {
    		try{
 	           
	            java.sql.Statement st = conexion.createStatement();
	            String sql = "insert into pilotos(idpiloto,idaerolinea,nombre,apellido,licencia,horasvuelo,revisionmedica)";
	            sql += "values ('"+id_piloto+"','"+id_aero+"','"+nombre+"','"+apellido+"','"+licencia+"','"+hrs_vuelo+"','"+fechaRevi+"')";
	            st.execute(sql);
	            // new Notificacion("Piloto registrado con exito", 1);
						Alert alert = new Alert(AlertType.INFORMATION);
	    				alert.setContentText("Piloto registrado con exito");
	    				alert.show();
	        }catch(Exception e ){
	        }
    	}else {
						Alert alert = new Alert(AlertType.ERROR);
	    				alert.setContentText("Ya se encuentra registrado un piloto con la misma ID");
	    				alert.show();
    	    // Notificacion notificacion = new Notificacion("Ya se encuentra registrado un piloto con la misma Id", 2);
    	}
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------
    //METODO PARA REGISTRAR UN NUEVO AVION EN LA BASE DE DATOS
    public void registrarAvionEnBD(String id_avion, String id_aerolinea, String tipo,String capacidad ,String modelo, String tipo_propulsion, String peso_nominal, String num_motores) {
    	boolean esta = buscarIDAvion(0,id_avion,null);
    	if(!esta) {
    		try{
	            java.sql.Statement st = conexion.createStatement();
	            String sql = "insert into aviones(idavion,idaerolinea,tipo,capacidad,modelo,tipopropulsion,pesonominal,numeromotores)";
	            sql += "values ('"+id_avion+"','"+id_aerolinea+"','"+tipo+"','"+capacidad+"','"+modelo+"','"+tipo_propulsion+"','"+peso_nominal+"','"+num_motores+"')";
	            st.execute(sql);
	            // new Notificacion("Avion registrado con exito", 1);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Avion registrado con exito");
				alert.show();
	        }catch(Exception e ){
	        }
    		
    	}else {
    		//Notificacion notificacion = new Notificacion("Ya se encuentra registrado una aeronave con la misma Id", 2);
    					Alert alert = new Alert(AlertType.ERROR);
	    				alert.setContentText("Ya se encuentra registrado un Avion con la misma ID");
	    				alert.show();
    	}
    }
    //---------------------------------------------------------------------------------------------------------------------------------------------
    //METODO PARA AGREGAR UN NUEVO VUELO A LA AGENDA DE OPERACION
    public void agendarVuelo(String id_vuelo,String id_piloto,String id_copiloto, String id_avion, String hora, String fecha, String tipo, String id_aerolinea,String estado,String destino,String procedencia) {
        boolean esta = buscarIdVuelo(id_vuelo);
        if(!esta) {
    		try{
	            java.sql.Statement st = conexion.createStatement();
	            String sql = "insert into vuelos(idvuelo,hora,fecha,tipo,estado,destino,procedencia,idpiloto,idaerolinea,idavion,idcopiloto)";
	            sql += "values ('"+id_vuelo+"','"+hora+"','"+fecha+"','"+tipo+"','"+estado+
				                "','"+destino+"','"+procedencia+"','"+id_piloto+"','"+id_aerolinea+"','"+id_avion+"','"+id_copiloto+"')";
	            st.execute(sql);
	            new Notificacion("Vuelo agendado con exito", 1);
	        }catch(Exception e ){
	        }
    		
        }else {
        	  new Notificacion("Vuelo no agendado", 2);
        }
    }
	//METODO PARA REGISTRAR UN HANGARR
    public void registrarNuevoHangar(String codigo, String ubicacion, String capacidad,String tarifa){
		boolean esta = buscarHangar(codigo);
		String estado = "Vacio", idavion = "Vacio";
		
    	if(!esta) {
			System.out.print("no esta");
    		try{
 	           
	            java.sql.Statement st = conexion.createStatement();
	            String sql = "INSERT INTO  hangares(codigo, ubicacion, capacidad, estado, tarifa, idavion)";
	            sql += "VALUES ('"+codigo+"','"+ubicacion+"','"+capacidad+"','"+estado+"','"+tarifa+"','"+idavion+"')";
	            st.execute(sql);
	            //new Notificacion("Hangar registrado con exito", 1);
				Alert alert = new Alert(AlertType.INFORMATION);
	    		alert.setContentText("Hangar registrado con exito");
	    		alert.show();
	        }catch(Exception e ){
	        }
    	}else{
			Alert alert = new Alert(AlertType.ERROR);
	    	alert.setContentText("Ya se encuentra registrado un hangar con el mismo codigo");
	    	alert.show();
    	    //new Notificacion("Ya se encuentra registrado un Hangar con el mismo codigo", 2);
    	}
	}

    public void reservarHangar(String codigo, String id_avion){
	    Avion avion = retornarInformacionAvion(id_avion);
	    String nom_aero = getNombreAeroliena(avion.getId_aerolinea());

		try{ 
			java.sql.Statement st = conexion.createStatement();
		    String sql = "update hangares set id_avion ="+"'"+id_avion+"'"+", estado = 'ocupado'"+","+"aerolinea = "+"'"+nom_aero+"'"+" where codigo = "+"'"+codigo+"'";
			st.execute(sql);
			new Notificacion("Hangar reservado con exito", 1);
		}catch(Exception e ){
		}	
	}
 
    //----------------------------------------------------------------------------------------------------------------------------------------
    public String getNombreAeroliena(String id) {
    	String nombreAeroleina = "";
    	try{ 
            java.sql.Statement st = conexion.createStatement();
            
	            String sql = "SELECT * FROM aerolineas WHERE idaerolinea= "+"'"+id+"'";
	            ResultSet resultSet = st.executeQuery(sql);
       
            while(resultSet.next()){
                nombreAeroleina = resultSet.getString("nombre");
                  
            };
        }catch (Exception e) {}
    	
    	return nombreAeroleina;
    }
    //---------------------------------------------------------------------------------
    public String tomarIDDeAerolineaConNombreAeroliena(String nombre) {
    	String id_aeroliena = "";
     	try{ 
            java.sql.Statement st = conexion.createStatement();
            
	            String sql = "SELECT * FROM aerolineas WHERE nombre= "+"'"+nombre+"'";
	            ResultSet resultSet = st.executeQuery(sql);
       
            while(resultSet.next()){
                id_aeroliena = resultSet.getString("idaerolinea");
                  
            };
        }catch (Exception e) {}
    	return id_aeroliena;
    }
	  //---------------------------------------------------------------------
    //METODO PARA CARGAR LA INFORMACION QUE EXISTE EN LA BASE DE DATOS Y MOSTRARLA
    //EN LA TABLA USUARIOS AEROLINEA
    
    public void cargarDatosUusuarios(TableView<Usuario> tabla_usuarios) {
    	
        try{
            java.sql.Statement st = conexion.createStatement();
            String sql = "SELECT * FROM aerolineas";
            ResultSet resultSet = st.executeQuery(sql);
       
            while(resultSet.next()){
            	String nombre = resultSet.getString("nombre");
            	String id = resultSet.getString("idaerolinea");
            	String usuario = resultSet.getString("usuario");
            	String contrasena = resultSet.getString("contrasena");
            	String email = resultSet.getString("email");
                String estado = resultSet.getString("estado");
                modelo.Usuario usuario2 = new modelo.Usuario(nombre, id, email, usuario, contrasena, estado);
                tabla_usuarios.getItems().add(usuario2);
            	
            };
            st.close();
            resultSet.close();
  
        }catch(Exception e ){       }  
    }
	//CREAR NUEVO USUARIO ADMINISTRADOR
	public void registrarNuevoUsuarioAdminitrador(String id,String nombre, String usuario, String contrasena){
		Usuario usuario1 = buscarUsuarioDesdeAdministracion(id);
		//CREAMOS LA SENTENCIA PARA AGREGAR UN NUEVO DATO A LA TABLA USUARIOS
		if(usuario1 == null ){
			
			try{
				
				Statement 
				st = conexion.createStatement();
				String sql = "insert into usuarios(idusuario,nombre,usuario,contrasena,rol)";
				sql += "values ('"+id+"','"+nombre+"','"+usuario+"','"+contrasena+"','"+"Administrador"+"')";
				st.execute(sql);
				st.close();

				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("Usuario registrado con exito");
				alert.show();
			}catch(Exception e){

			}
		}else{
			if(usuario1.getRol().equals("Aerolinea")){
				try{
					st = conexion.createStatement();
					String sql = "insert into usuarios(idusuario,nombre,usuario,contrasena,rol)";
					sql += "values ('"+id+"','"+nombre+"','"+usuario+"','"+contrasena+"','"+"Administrador"+"')";
					st.execute(sql);
					st.close();
	
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setContentText("Usuario registrado con exito");
					alert.show();
				}catch(Exception e){
	
				}
			}else{

				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Ya se encuentra registrado un usuario con la misma id");
				alert.show();

			}
			
		}
	}
	// BUSCAR AEROLINEA EN solicitudes
	public Solicitud buscar_solicitud(String id){
		Solicitud solicitud = null;
		try{
            java.sql.Statement st = conexion.createStatement();
            String sql = "SELECT * FROM aerolineas WHERE idaerolinea = "+"'"+id+"'";
            ResultSet resultSet = st.executeQuery(sql);
       
            while(resultSet.next()){
            	String nombre = resultSet.getString("nombre");
            	String id_aero = resultSet.getString("idaerolinea");
            	String usuario = resultSet.getString("usuario");
            	String contrasena = resultSet.getString("contrasena");
            	String email = resultSet.getString("email");
                String estado = resultSet.getString("estado");

                solicitud= new Solicitud(nombre, id, email, usuario, contrasena, estado);
            	
            };
            st.close();
            resultSet.close();
  
        }catch(Exception e ){       } 
		return solicitud;
	}
	// BUSCAR usuario del sistema desde administracion
	public Usuario buscarUsuarioDesdeAdministracion(String id){
		Usuario usuario = null;
		try{
            java.sql.Statement st = conexion.createStatement();
            String sql = "SELECT * FROM usuarios WHERE idusuario= "+"'"+id+"'";
            ResultSet resultSet = st.executeQuery(sql);
       
            while(resultSet.next()){
            	String nombre = resultSet.getString("nombre");
            	String id1 = resultSet.getString("idusuario");
            	String usuario1 = resultSet.getString("usuario");
            	String contrasena = resultSet.getString("contrasena");
				String rol = resultSet.getString("rol");
				usuario = new Usuario(id1, nombre, usuario1, contrasena,rol);
            	
            };
            st.close();
            resultSet.close();
  
        }catch(Exception e ){       } 
		return usuario;
	}

    //------------------------------------------------------------------------
    //CARGAR PILOTOS REGISTRADOS EN ADMINISTRACION
    public void cargarPilotosAdministracion(TableView<Piloto> tabla_pilotosAdmin) {
    	 try{
             java.sql.Statement st = conexion.createStatement();
             String sql = "SELECT * FROM pilotos";
             ResultSet resultSet = st.executeQuery(sql);
        
             while(resultSet.next()){
             	String nombre = resultSet.getString("nombre");
             	String id = resultSet.getString("idpiloto");
             	String id_aerolinea = resultSet.getString("idaerolinea");
             	
                Piloto piloto = new Piloto(id, getNombreAeroliena(id_aerolinea), nombre);
                tabla_pilotosAdmin.getItems().add(piloto);
             	
             };
             st.close();
             resultSet.close();
   
         }catch(Exception e ){       }  
    }
    //------------------------------------------------------------------------------
    // CARGAR PILOTOS REGISTRADOS EN SU RESPECTIVA AEROLINEA
    public void cargarPilotosAerolinea(TableView<Piloto> tabla_pilotos,String IdAeroliena) {
    	 try{
             java.sql.Statement st = conexion.createStatement();
             String sql = "SELECT * FROM pilotos WHERE idaerolinea ="+"'"+IdAeroliena+"'";
             ResultSet resultSet = st.executeQuery(sql);
        
             while(resultSet.next()){
            	String id = resultSet.getString("idpiloto");
             	String nombre = resultSet.getString("nombre");
             	String apellido = resultSet.getString("apellido");
             	String licencia = resultSet.getString("licencia");
             	String horasVuelo = resultSet.getString("horasvuelo");
             	String revision_medica = resultSet.getString("revisionmedica");
             	
             	
                Piloto piloto = new Piloto(id, nombre, apellido, licencia, horasVuelo, revision_medica);
                tabla_pilotos.getItems().add(piloto);
             };
             st.close();
             resultSet.close();
   
         }catch(Exception e ){       } 
    }
    //-----------------------------------------------------------------------------
    //CARGAR AVIONES REGISTRADOS EN ADMINISTRACION
    public void cargarAvionesAdministracion(TableView<Avion> tabla_AvionesAdmin) {
   	 try{
            java.sql.Statement st = conexion.createStatement();
            String sql = "SELECT * FROM aviones";
            ResultSet resultSet = st.executeQuery(sql);
       
            while(resultSet.next()){
            	String id_avion = resultSet.getString("idavion");
            	String tipo = resultSet.getString("tipo");
            	String modelo = resultSet.getString("modelo");
            	String id_aerolinea = resultSet.getString("idaerolinea");
            	
               Avion avion = new Avion(id_avion, getNombreAeroliena(id_aerolinea), tipo, modelo);
               tabla_AvionesAdmin.getItems().add(avion);
            	
            };
            st.close();
            resultSet.close();
  
        }catch(Exception e ){       }  
   }
   //-----------------------------------------------------------------------------------
    //CARGAR AVIONES DE AEROLIENA
    public void cargarAvionesAeroliena(TableView<Avion> tabla_aviones,String IdAeroliena) {
    	 try{
             java.sql.Statement st = conexion.createStatement();
             String sql = "SELECT * FROM aviones WHERE idaerolinea = "+"'"+IdAeroliena+"'";
             ResultSet resultSet = st.executeQuery(sql);
        
             while(resultSet.next()){
             	String id_avion = resultSet.getString("idavion");
             	String tipo = resultSet.getString("tipo");
             	String capacidad = resultSet.getString("capacidad");
             	String modelo = resultSet.getString("modelo");
             	String tipo_propulsion = resultSet.getString("tipopropulsion");
             	String peso_nominal = resultSet.getString("pesonominal");
             	String num_motores = resultSet.getString("numeromotores"); 
                
             	Avion avion = new Avion(id_avion, tipo, capacidad, modelo, tipo_propulsion, peso_nominal, num_motores);
             	tabla_aviones.getItems().add(avion);
             	
             	
             };
             st.close();
             resultSet.close();
   
         }catch(Exception e ){       }
    }
   //-----------------------------------------------------------------------------------
   //CARGAR AEROLINEAS REGISTRADAS EN ADMINISTRACION
    public void cargarAerolineasAdministracion(TableView tabla_AerolineasAdmin) {
      	 try{
               java.sql.Statement st = conexion.createStatement();
               String sql = "SELECT * FROM usuarios";
               ResultSet resultSet = st.executeQuery(sql);
          
               while(resultSet.next()){
               	String nombre = resultSet.getString("nombre");
               	String id = resultSet.getString("idusuario");
               	String usuario = resultSet.getString("usuario");
				String contrasena = resultSet.getString("contrasena");
				String rol = resultSet.getString("rol");
                
				Usuario usuarioObjeto = new Usuario(id,nombre,usuario,contrasena,rol);
				//Solicitud solicitud = new Solicitud(id_aerolinea, nombre, usuario, contrasena,null,null);

                tabla_AerolineasAdmin.getItems().add(usuarioObjeto);
               	
               };
               st.close();
               resultSet.close();
     
           }catch(Exception e ){       }  
      } 
   //------------------------------------------------------------------------
    //CARGAR VUELOS AGENDADOS EN AEROLINEA
    public void cargarVuelosAgendadosAerolinea(TableView<Vuelo> tabla_VuelosProgramados,String IDaerolinea) {
        try{
            java.sql.Statement st = conexion.createStatement();
            String sql = "SELECT * FROM vuelos WHERE idaerolinea = "+"'"+IDaerolinea+"'";
            ResultSet resultSet = st.executeQuery(sql);
       
            while(resultSet.next()){
            	String id_vuelo = resultSet.getString("idvuelo");
            	String id_piloto = resultSet.getString("idpiloto");
            	String id_copiloto = resultSet.getString("idcopiloto");
            	String id_avion = resultSet.getString("idavion");
            	String hora = resultSet.getString("hora");
            	String fecha = resultSet.getString("fecha");
            	String tipo = resultSet.getString("tipo");
            	String estado = resultSet.getString("estado");
            	String destino = resultSet.getString("destino");
				String procedencia = resultSet.getString("procedencia");
            	Vuelo vuelo = new Vuelo(id_vuelo, id_piloto, id_copiloto, id_avion, hora, fecha, tipo,estado,destino,procedencia);
              
                tabla_VuelosProgramados.getItems().add(vuelo);
            	
            };
            st.close();
            resultSet.close();
  
        }catch(Exception e ){       } 
    }
    //----------------------------------------------------------------------------------
    //CARGAR TODOS LOS VUELOS AGENDADOS
    public void cargarAgendaDeOperacion(VBox box,int tipoFiltro,String idvuelo, String aeroliena, String fechaVueloFiltro) {
    	
    	//FILTRADO TIPO : 0 GENERAL
    	/*
    	 * TIPO 1 : FILTRADO POR ID VUELO
    	 * TIPO 2 : FILTRADO POR AEROLINEA
    	 * TIPO 3 . FILTRADO POR FECHA
    	 * TIPO 4 : FILTRADO POR FECHA Y AEROLINEA
    	 * 
    	 */
    	
        try{
        	
            java.sql.Statement st = conexion.createStatement();
            ResultSet resultSet = null;
            if(tipoFiltro == 0) {
            	  String sql = "SELECT * FROM vuelos";
                  resultSet = st.executeQuery(sql);
            }if(tipoFiltro == 1) {
            	 String sql = "SELECT * FROM vuelos WHERE idvuelo = "+"'"+idvuelo+"'";
                 resultSet = st.executeQuery(sql);
            }if(tipoFiltro == 2) {
	           	 String sql = "SELECT * FROM vuelos WHERE idaerolinea = "+"'"+aeroliena+"'";
	             resultSet = st.executeQuery(sql);
            }if(tipoFiltro == 3) {
	           	 String sql = "SELECT * FROM vuelos WHERE fecha = "+"'"+fechaVueloFiltro+"'";
	             resultSet = st.executeQuery(sql);
            }if(tipoFiltro == 4) {
	           	 String sql = "SELECT * FROM vuelos WHERE fecha = "+"'"+fechaVueloFiltro+"'"+"AND "+"idaerolinea = "+"'"+aeroliena+"'";
	             resultSet = st.executeQuery(sql);
           }
            
       
            while(resultSet.next()){
            	String id_vuelo = resultSet.getString("idvuelo");
            	String id_avion = resultSet.getString("idavion");
            	String hora = resultSet.getString("hora");
            	String fecha = resultSet.getString("fecha");
            	String tipo = resultSet.getString("tipo");
            	String aerolinea = resultSet.getString("idaerolinea");
            	String estado = resultSet.getString("estado");
				String destino = resultSet.getString("destino");
				String procedencia = resultSet.getString("procedencia");
            	//--ID VUELO--------------------------------------------
            	AnchorPane anchorIDVuelo = new AnchorPane();
            	anchorIDVuelo.setStyle("-fx-background-color: black"); 
            	anchorIDVuelo.setMinWidth(50);
            	anchorIDVuelo.setMaxHeight(15);
            	Text idVuelo = new Text(id_vuelo);
            	idVuelo.setFill(Color.WHITE);
            	idVuelo.setFont(Font.font(15));
            	idVuelo.setX(5);
            	idVuelo.setY(15);
            	anchorIDVuelo.getChildren().add(idVuelo);

				//--DESTINO
				AnchorPane anchorDestino = new AnchorPane();
            	anchorDestino.setStyle("-fx-background-color: black"); 
            	anchorDestino.setMinWidth(130);
            	anchorDestino.setMaxHeight(15);
            	Text destinoVuelo = new Text(destino);
            	destinoVuelo.setFill(Color.WHITE);
            	destinoVuelo.setFont(Font.font(15));
            	destinoVuelo.setX(5);
            	destinoVuelo.setY(15);
            	anchorDestino.getChildren().add(destinoVuelo);

				//--PROCEDENCIA
				AnchorPane anchorProcedencia = new AnchorPane();
            	anchorProcedencia.setStyle("-fx-background-color: black"); 
            	anchorProcedencia.setMinWidth(110);
            	anchorProcedencia.setMaxHeight(15);
            	Text procedenciaVuelo = new Text(procedencia);
            	procedenciaVuelo.setFill(Color.WHITE);
            	procedenciaVuelo.setFont(Font.font(15));
            	procedenciaVuelo.setX(0);
            	procedenciaVuelo.setY(15);
            	anchorProcedencia.getChildren().add(procedenciaVuelo);
				
            	
				//--ID AVION
            	AnchorPane anchorIDAvion = new AnchorPane();
            	anchorIDAvion.setStyle("-fx-background-color: black"); 
            	anchorIDAvion.setMinWidth(60);
            	anchorIDAvion.setMaxHeight(15);
            	Text idAvion = new Text(id_avion);
            	idAvion.setFill(Color.WHITE);
            	idAvion.setFont(Font.font(15));
            	idAvion.setX(5);
            	idAvion.setY(15);
            	anchorIDAvion.getChildren().add(idAvion);
                
				//--HORA
            	AnchorPane anchorhora = new AnchorPane();
            	anchorhora.setStyle("-fx-background-color: black"); 
            	anchorhora.setMinWidth(60);
            	anchorhora.setMaxHeight(15);
            	Text horaVuelo = new Text(hora);
            	horaVuelo.setFill(Color.WHITE);
            	horaVuelo.setFont(Font.font(15));
            	horaVuelo.setX(5);
            	horaVuelo.setY(15);
            	horaVuelo.setFont(Font.loadFont(getClass().getResourceAsStream("/fonts/DS-DIGIT.TTF"), 18));
            	anchorhora.getChildren().add(horaVuelo);
            	
				//--FECHA
            	AnchorPane anchorfecha = new AnchorPane();
            	anchorfecha.setStyle("-fx-background-color: black"); 
             	anchorfecha.setMinWidth(90);
            	anchorfecha.setMaxHeight(15);
            	Text fechaVuelo = new Text(fecha);
            	fechaVuelo.setFill(Color.WHITE);
            	fechaVuelo.setFont(Font.font(15));
            	fechaVuelo.setX(5);
            	fechaVuelo.setY(15);
            	anchorfecha.getChildren().add(fechaVuelo);
            	
				//--TIPO
            	AnchorPane anchortipo = new AnchorPane();
            	anchortipo.setStyle("-fx-background-color: black"); 
             	anchortipo.setMinWidth(70);
            	anchortipo.setMaxHeight(15);
            	Text tipoVuelo = new Text(tipo);
            	tipoVuelo.setFill(Color.WHITE);
            	tipoVuelo.setFont(Font.font(15));
            	tipoVuelo.setX(5);
            	tipoVuelo.setY(15);
            	anchortipo.getChildren().add(tipoVuelo);
            	
				//--AEROLINEA
            	AnchorPane anchoraero = new AnchorPane();
            	anchoraero.setStyle("-fx-background-color: black"); 
             	anchoraero.setMinWidth(50);
            	anchoraero.setMaxHeight(15);
            	Text aero = new Text(getNombreAeroliena(aerolinea));
            	aero.setFill(Color.AQUA);
            	aero.setFont(Font.font(15));
            	aero.setX(5);
            	aero.setY(15);
            	anchoraero.getChildren().add(aero);
            	
				//--ESTADO
            	AnchorPane anchorEstado = new AnchorPane();
             	anchorEstado.setMinWidth(100);
            	anchorEstado.setMaxHeight(15);
            	Text estad = new Text(estado); 
            	estad.setFill(Color.WHITE);
            	estad.setFont(Font.font("Arial Black", 13));
            	estad.setX(5);
            	estad.setY(15);
            	anchorEstado.getChildren().add(estad);
            	if(estado.equals("CANCELADO")) {
            		anchorEstado.setStyle("-fx-background-color: RED"); 
            	}if(estado.equals("AGENDADO")) {
            		anchorEstado.setStyle("-fx-background-color: GREEN"); 
            	}
            	
            	
            	if(tipo.equals("Llegada")) {
            		tipoVuelo.setFill(Color.YELLOW); 
            	}
            	if(tipo.equals("Salida")) {
            		tipoVuelo.setFill(Color.GREEN); 
            	}
            	
            	
            	try {
            		HBox hbox = FXMLLoader.load(getClass().getResource("/vista/Vuelo.fxml"));
            		hbox.setSpacing(18);
            		hbox.setPadding(new Insets(10));
            		hbox.getChildren().addAll(anchorIDVuelo,anchorDestino,anchorProcedencia,anchoraero,anchorIDAvion,anchorhora,anchorfecha,anchortipo,anchorEstado);
            		box.getChildren().add(hbox);
            		
            	}catch (Exception e) {}
            	
            	
            };
            st.close();
            resultSet.close();
  
        }catch(Exception e ){       } 
    	
    }
	//Cargar lista de hangares en administracion
	public void cargarHangaresAdministracion(TableView<Hangar> tabla_reporteHangares) {
		try{
            java.sql.Statement st = conexion.createStatement();
            String sql = "SELECT * FROM hangares";
            ResultSet resultSet = st.executeQuery(sql);
            
            while(resultSet.next()){
			
            	String codigo =    resultSet.getString("codigo");
            	String ubicacion = resultSet.getString("ubicacion");
				String capacidad = resultSet.getString("capacidad");
				String id_avion  = resultSet.getString("idavion");
				String tarifa = resultSet.getString("tarifa");
				String estado =    resultSet.getString("estado");

				

		        Hangar hangar = new Hangar(codigo, ubicacion, capacidad,estado,tarifa,id_avion);
				tabla_reporteHangares.getItems().add(hangar);

            };
            st.close();
            resultSet.close();
  
        }catch(Exception e ){       } 
	}
	//Retornar hangar con toda su informacion
	public Hangar retornarHangar(String codigo){
		Hangar hangar = null;
		try{
            java.sql.Statement st = conexion.createStatement();
            String sql = "SELECT * FROM hangares where codigo ="+"'"+codigo+"'";
            ResultSet resultSet = st.executeQuery(sql);
            
            while(resultSet.next()){
			
            	String codigo1 =    resultSet.getString("codigo");
            	String ubicacion = resultSet.getString("ubicacion");
				String capacidad = resultSet.getString("capacidad");
				String id_avion  = resultSet.getString("idavion");
				String tarifa = resultSet.getString("tarifa");
				String estado =    resultSet.getString("estado");

				

		         hangar = new Hangar(codigo1, ubicacion, capacidad,estado,tarifa,id_avion);


            };
            st.close();
            resultSet.close();
  
        }catch(Exception e ){       } 
		return hangar;
	}

	public void cargarHangaresVaciosAdministracion(TableView<Hangar> tabla_reporteHangares) {
		
		try{
			
            java.sql.Statement st = conexion.createStatement();
            String sql = "SELECT * FROM hangares WHERE estado = 'Vacio'";
            ResultSet resultSet = st.executeQuery(sql);
            
            while(resultSet.next()){
			
            	String codigo =    resultSet.getString("codigo");
            	String ubicacion = resultSet.getString("ubicacion");
				String capacidad = resultSet.getString("capacidad");
				String id_avion  = resultSet.getString("idavion");
				String tarifa = resultSet.getString("tarifa");
				String estado =    resultSet.getString("estado");
				Button btn_reservar= new Button("Reservar");

				//btn_reservar.setStyle("-fx-text-fill: white");
				btn_reservar.setStyle("-fx-background-color: #2CB61B");
				
				
               
				Hangar hangar = new Hangar(codigo, ubicacion, capacidad,estado,tarifa,id_avion,btn_reservar);
				tabla_reporteHangares.getItems().add(hangar);
			
            };
            st.close();
            resultSet.close();
        }catch(Exception e ){       } 

	}
    //----------------------------------------------------------------------------
	//------------  TODO FACTURAS ------------------------------------------------
	public String numeroDeFactura(){
		String numeroDeFactura = "0";
		try{
			
            java.sql.Statement st = conexion.createStatement();
            String sql = "SELECT * FROM facturas";
            ResultSet resultSet = st.executeQuery(sql);
            
            while(resultSet.next()){
			
            	numeroDeFactura = resultSet.getString("numero");
			
            };

            st.close();
            resultSet.close();
        }catch(Exception e ){ }

		return numeroDeFactura;
	}
	//Registro de la factura en el sistema
	public void registrarFactura(String fechaIngreso, String horaIngreso, String codigoHangar, String idAvion){
		String numerofac = numeroDeFactura();
		int numeroFactura = Integer.parseInt(numerofac)+1;
		String numeroFacturaRegistrar = ""+numeroFactura;
		try{  
			java.sql.Statement st = conexion.createStatement();
			String sql = "INSERT INTO  facturas(numero,fechaingreso,horaingreso,codigohangar,idavion)";
			sql += "VALUES ('"+numeroFacturaRegistrar+"','"+fechaIngreso+"','"+horaIngreso+"','"+codigoHangar+"','"+idAvion+"')";
			
			
			//Una vez registramos la factura se procede a cambiarle el estado del hangar 
			String sql2 = "update hangares set estado = 'Ocupado' where codigo = "+"'"+codigoHangar+"'";
			
			st.execute(sql);
			st.execute(sql2);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("Hangar reservado con exito");
			alert.show();
		}catch(Exception e ){

		}
	}

    
    
}
