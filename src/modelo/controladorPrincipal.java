package modelo;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import Notificaciones.Notificacion;
import SQL.MetodosSQL;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class controladorPrincipal implements Initializable {
	static String IDaerolinea = null;
	static String NOMBRE_AEROLINEA = null;
	static SQL.MetodosSQL metodosSQL;
	//----------------- L O G I N-------------------------
	@FXML private AnchorPane panelLogin,panel_welcome;
	@FXML private Text text_nombreAerolinea;
	@FXML private PasswordField txt_contrasena;
	@FXML private TextField txt_usuario;
	@FXML private ComboBox<String> cbx_orgaizacion;
	@FXML private Button btn_cerrarSesionPanePrinciAero;
	@FXML private ImageView imagenPrincipal1,imagenPrinicipal2;

	  //------------ P A N E L  D E  R E G I S T R O--------
	@FXML private AnchorPane panelRegistro;
	@FXML private TextField txt_nombreAeroliena,txt_idAerolinea,txt_emailAerolinea,txt_usuarAerolinea;
    @FXML private PasswordField txt_contra1Aerolinea,txt_contra2Aerolinea;
	@FXML private Label lb_errorNombre,lb_errorContra2;
	@FXML private Label lb_errorContra1;
	@FXML private Label lb_errorUsuario;
	@FXML private Label lb_errorEmail;
	@FXML private Label lb_errorID;
	@FXML private Label lb_errorContrasDiferentes;
	@FXML private Button btn_cancelFormuRegistroAero;
	@FXML private AnchorPane panelPrincipalAerolineas;
	  
	//----------------------------------------------------------------------------------------- 
	// TABLA DE USUARIOS AEROLINEA DEL SISTEMA
	@FXML private TableView<modelo.Usuario> tabla_usuarios;
	@FXML private TableColumn<modelo.Usuario,String> colnombre;
	@FXML private TableColumn<modelo.Usuario,String> colId;
    @FXML private TableColumn<modelo.Usuario,String> colemail;
    @FXML private TableColumn<modelo.Usuario,String> colusuario;
	@FXML private TableColumn<modelo.Usuario,String> colcontrasena;
	@FXML private TableColumn<modelo.Usuario,Boolean> colestado;
	@FXML private Button btn_activar;
    @FXML private Button btn_inactivar;
	@FXML private TextField txt_nombreAerolineaActiDesacEliminar;
	@FXML private TextField txt_usuarioAerolineaActiDesacEliminar;
	@FXML private TextField txt_contraAerolineaActiDesacEliminar;
	@FXML private TextField txt_emailAerolineaActiDesacEliminar;
	
	
	//--------------------------------------------------------------------------
	// PANEL PRINCIPAL AEROLINEAS
	@FXML private Button btn_registrarPiloto;
	@FXML private Button btn_registrarAvion;
	@FXML private Button btn_vuelosMenuAerolineas;
	@FXML private AnchorPane panelFormularioPiloto;
	@FXML private VBox panel_notificacionesAerolinea;
	//------------------------------------------------------------------------------
	// FORMULARIO REGISTRP PILOTOS
	@FXML private TextField txt_nomFormuPlito;
	@FXML private TextField txt_apellFormuPlito;
	@FXML private TextField txt_ceduFormuPlito;
	@FXML private TextField txt_hrsVueFormuPlito;
	@FXML private TextField txt_licenFormuPlito;
	@FXML private DatePicker txt_revisMedicaFormuPlito;
	@FXML private Text errorNombre;
	@FXML private Text errorApellidos;
	@FXML private Text errorCedula;
	@FXML private Text errorHorasVuelo;
	@FXML private Text errorRevisionMedico;
	@FXML private Text errorLicencia;
	@FXML private Button btn_regisFormu;
	//------FORMULARIO AVIONES------------------------------------------------------
	@FXML private AnchorPane panelFormularioAvion;
	@FXML private Button btn_cancelarFormuAvion;
	@FXML private Button btn_registrarFormuAvion;
    @FXML private TextField txt_idAvion;
	@FXML private TextField txt_pesoNominalAvion;
	@FXML private TextField txt_capacidadAvion;
	@FXML private RadioButton radio_cargaAvion;
	@FXML private RadioButton radio_pasajerosAvion;
	@FXML private ComboBox cbx_tipoPropulsionAvion;
	@FXML private ComboBox cbx_numMotoresAvion;
	@FXML private TextField txt_modeloAvion;
	//---------------------------------------------------
	//PANEL VUELOS AEROLINEAS
	// AGENDAR VUELO---------------------------------------------------------------------------------

    @FXML private AnchorPane panel_agendarVuelo;
	@FXML private AnchorPane panel_vuelosAerolineas;
	@FXML private AnchorPane panel_formularioAgendarVuelo;
	@FXML private AnchorPane panel_consultarDisponibilidad;
	@FXML private DatePicker txt_fechaVuelo;
	@FXML private ComboBox cbx_horaVuelo;
	@FXML private RadioButton radio_llegada;
	@FXML private RadioButton radio_salida;
	@FXML private TextField txt_idAvionBuscar;
	@FXML private Text error_idVuelo;
	@FXML private Text error_NoCopiloto;
	@FXML private Text error_NoPiloto;
    @FXML private Text error_NoAvion;
	@FXML private Text error_NoTipo,error_NoDestino,error_NoProcedencia;
	@FXML private TextField txt_idPilotoBuscar;
	@FXML private TextField txt_idCopilotoBuscar;
	@FXML private Button btn_buscarIDPiloto;
	@FXML private Button btn_buscarIDAvion;
	@FXML private TextField txt_tipoEncontrado;
	@FXML private TextField txt_numMotoresEncontrado;
	@FXML private TextField txt_PilotoEncontrado;
    @FXML private TextField txt_CoPilotoEncontrado,txt_destinoVuelo,txt_procedenciaVuelo;
	@FXML private Button btn_buscarIDCopiloto,btn_HangaresMenuAerolineas,btn_pagosMenuAerolineas,btn_reportesMenuAerolineas;
	@FXML private Pane panelBtnRegistrarPiloto,panelBtnRegistrarAvion,panelBtnVuelos,panelBtnReportes,panelBtnHangares,panelBtnPagoSeguro;
	@FXML void salirPanelFormularioVuelo(ActionEvent event) {
	    	this.panel_consultarDisponibilidad.setDisable(false);
	    	this.panel_formularioAgendarVuelo.setDisable(true);
	    	this.error_NoAvion.setVisible(false);
	    	this.error_NoPiloto.setVisible(false);
	    	this.error_NoTipo.setVisible(false);
			this.error_NoProcedencia.setVisible(false);
			this.error_NoDestino.setVisible(false);
	    	limpiarFormularioAgendarVuelos();
	    	this.txt_fechaVuelo.setValue(null);
	    	this.cbx_horaVuelo.setValue(null);
	    	
	    	  //ANOMACION DE DESPLAZAMIENTO DEL PANEL DE CONSULTAR DISPONIBILIDAD DE AGENDA
		     TranslateTransition slide = new TranslateTransition();
	         slide.setDuration(Duration.seconds(0.7));
	         slide.setNode(this.panel_consultarDisponibilidad);
	         slide.setToX(300);
	         slide.play();
	         
	         //ANIMACION PANEL FORMILARIO AGENDAR VUELO
		     TranslateTransition slide1 = new TranslateTransition();
	         slide1.setDuration(Duration.seconds(0.7));
	         slide1.setNode(this.panel_formularioAgendarVuelo);
	         slide1.setToX(-150);
	         slide1.play();
	         this.panel_formularioAgendarVuelo.setOpacity(0.3);
	    
	    }
	//-------------------------------------------------------------------------------------------------
	//LIMPIAR FORMUARIO DE AGENDAR VUELOS
	public void limpiarFormularioAgendarVuelos() {
	    	this.txt_idAvionBuscar.clear();
	    	this.txt_idPilotoBuscar.clear();
	    	this.txt_idCopilotoBuscar.clear();
	    	this.txt_numMotoresEncontrado.clear();
	    	this.txt_tipoEncontrado.clear();
	    	this.txt_PilotoEncontrado.clear();
	    	this.txt_idCopilotoBuscar.clear();
	    	this.txt_CoPilotoEncontrado.clear();
	    	this.txt_fechaVuelo.setValue(null);
	    	this.cbx_horaVuelo.setValue(null);
			this.txt_procedenciaVuelo.clear();
			this.txt_destinoVuelo.clear();	
	    }    
	//PANEL VUELOS PROGRAMADOS
	@FXML private AnchorPane panel_VuelosProgramados;
	@FXML private TableView<Vuelo> tabla_VuelosProgramados;
	@FXML private TableColumn columIdVuelo;
	@FXML private TableColumn columIdPiloto;
	@FXML private TableColumn columIdCopiloto;
	@FXML private TableColumn columIdAvion;
	@FXML private TableColumn columHora;
	@FXML private TableColumn columFecha;
	@FXML private TableColumn columTipo,columEstado,columDestinoVuelo,columProcedenciaVuelo;
	@FXML private TextField txt_idVueloCancelarEliminar;

	@FXML void cancelarVueloAerolinea(ActionEvent event) {
	    	String id_vuelo = this.txt_idVueloCancelarEliminar.getText();
	    	Vuelo vuelo = this.metodosSQL.retornarVueloDeAerolinea(id_vuelo, IDaerolinea);
	    	if(vuelo != null) {
	    		this.metodosSQL.cancelarVueloDesdeAerolinea(id_vuelo);
	    		
	    		//REFRESCAMO LA TABLA DE VUELOS PROGRAMADOS
	    		this.tabla_VuelosProgramados.getItems().clear();
	    		this.metodosSQL.cargarVuelosAgendadosAerolinea(tabla_VuelosProgramados,IDaerolinea);
	    	}else {
	    		Alert alert = new Alert(AlertType.ERROR);
	    		alert.setContentText("No se encuentra un vuelo con la id registrada");
	    		alert.show();
	    	}
	    }
	@FXML void eliminarVueloAerolinea(ActionEvent event) {
	    	String id_vuelo = this.txt_idVueloCancelarEliminar.getText();
	    	Vuelo vuelo = this.metodosSQL.retornarVueloDeAerolinea(id_vuelo, IDaerolinea);
	    	if(vuelo != null) {
	    		this.metodosSQL.eliminarVuelo(id_vuelo);
	    		
	    		//REFRESCAMO LA TABLA DE VUELOS PROGRAMADOS
	    		this.tabla_VuelosProgramados.getItems().clear();
	    		this.metodosSQL.cargarVuelosAgendadosAerolinea(tabla_VuelosProgramados,IDaerolinea);
	    	}else {
	    		Alert alert = new Alert(AlertType.ERROR);
	    		alert.setContentText("No se encuentra un vuelo con la id registrada");
	    		alert.show();
	    	}
	    }
	@FXML void activarVueloAerolinea(ActionEvent event) {
	     	String id_vuelo = this.txt_idVueloCancelarEliminar.getText();
	    	Vuelo vuelo = this.metodosSQL.retornarVueloDeAerolinea(id_vuelo, IDaerolinea);
	    	if(vuelo != null) {
	    		this.metodosSQL.activarVueloAerolinea(id_vuelo);
	    		
	    		//REFRESCAMO LA TABLA DE VUELOS PROGRAMADOS
	    		this.tabla_VuelosProgramados.getItems().clear();
	    		this.metodosSQL.cargarVuelosAgendadosAerolinea(tabla_VuelosProgramados,IDaerolinea);
	    	}else {
	    		Alert alert = new Alert(AlertType.ERROR);
	    		alert.setContentText("No se encuentra un vuelo con la id registrada");
	    		alert.show();
	    	}
	    }
	    
	 //PANEL REPROGRAMAR VUELOS
	//-----------------------------------------------------------------------------------------------
	@FXML private AnchorPane panelReprogramarVuelo,panel_consultarAgendaModificar;
    @FXML private AnchorPane panelModificarInfoVuelo;
	@FXML private TextField txt_vueloModificar,txt_DestinoModificar,txt_procedenciaModificar;
	@FXML private TextField txt_horaModificar;
	@FXML private TextField txt_FechaModificar;
	@FXML private TextField txt_IdPilotoModificar;
	@FXML private TextField txt_IdCopilotoModificar;
	@FXML private TextField txt_IdAvionModificar;
	@FXML private TextField txt_TipoVueloModificar;
	@FXML private ComboBox cbx_TipoVueloModificar;
    @FXML private Text erroNoIdVuelo;
	@FXML private Button btn_modificarInfoVuelo;
	@FXML private Button btn_modificarIdPiloto;
	@FXML private Button btn_modificarIdCopiloto;
	@FXML private Button btn_modificarIdAvion;
	@FXML private ComboBox cbx_horasModificar;
	@FXML private DatePicker  date_fechaModificar;
	@FXML private Button btn_reprogramarVuelo,boton_agendar,boton_reprogramar,boton_programados,botonReportePiloto,botonReporteAviones;
	
	@FXML void horaModificar(MouseEvent event) {
	    	this.btn_reprogramarVuelo.setDisable(true);
	}
	@FXML void buscarVueloModificar(ActionEvent actionEvent) {
			
	    	String idVuelo = txt_vueloModificar.getText();
	    	if(!idVuelo.equals("")) {
	    		Vuelo vuelo = metodosSQL.retornarVueloDeAerolinea(idVuelo,IDaerolinea);
	    		if(vuelo != null) {
	    			this.panelModificarInfoVuelo.setDisable(false);
	    			this.panel_consultarAgendaModificar.setDisable(false);
	    			//------------------------------------------------------------
	    			this.txt_horaModificar.setText(vuelo.getHora());
	    			this.txt_FechaModificar.setText(vuelo.getFecha());
	    			this.txt_IdPilotoModificar.setText(vuelo.getId_piloto());
	    			this.txt_IdCopilotoModificar.setText(vuelo.getId_copiloto());
	    			this.txt_IdAvionModificar.setText(vuelo.getId_avion());
	    			this.txt_TipoVueloModificar.setText(vuelo.getTipo());
					this.txt_DestinoModificar.setText(vuelo.getDestino());
					this.txt_procedenciaModificar.setText(vuelo.getProcedencia());
					this.cbx_TipoVueloModificar.setValue(txt_TipoVueloModificar.getText());

					//VALIDAR SI EL VUELO ES DE LLEGADA O DE SALIDA
					//SI EL VUELO ES DE SALIDA SOLO SE PUEDE MODIFICAR DESTINO
		
					if(txt_TipoVueloModificar.getText().equals("Salida")){
						this.txt_procedenciaModificar.setEditable(false);
						this.txt_DestinoModificar.setEditable(true);
						this.txt_DestinoModificar.setOnMousePressed(e->{
							
						});
						this.txt_procedenciaModificar.setOnMousePressed(e->{
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setContentText("El vuelo es de tipo Salida, solo se modificar el destino");
							alert.show();
						});
		
					}
					//SI EL VUELO ES DE LLEGADA SOLO SE PUEDE MODIFICAR PROCEDENCIA
					if(txt_TipoVueloModificar.getText().equals("Llegada")){
						this.txt_DestinoModificar.setEditable(false);
						this.txt_procedenciaModificar.setEditable(true);
						this.txt_procedenciaModificar.setOnMousePressed(e->{
						
						});
						this.txt_DestinoModificar.setOnMousePressed(e->{
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setContentText("El vuelo es de tipo Llegada, solo se modificar la procedencia");
							alert.show();
						});
						
					}
	    			//-----------------------------------------------------------------------------
	    			//BOTONES MODIFIICAR
	    			this.btn_modificarIdPiloto.setOnAction(e->{
	    				String id_piloto = this.txt_IdPilotoModificar.getText();
	    				boolean esta = metodosSQL.buscarIDPilotoPorAerolinea(id_piloto,IDaerolinea);
	    				if(esta) {
	    					if(!id_piloto.equals(txt_IdCopilotoModificar.getText())) {
	    						this.btn_modificarInfoVuelo.setDisable(false);
	    						Alert alert = new Alert(AlertType.INFORMATION);
		    					alert.setContentText("Piloto modificado con exito");
		    					alert.show();
	    					}else {
	    						Alert alert = new Alert(AlertType.ERROR);
	    						alert.setHeaderText("Operacion Invalida");
		    					alert.setContentText("La ID registrada corresponde al copiloto de este vuelo");
		    					alert.show();
		    					this.txt_IdPilotoModificar.setText(vuelo.getId_piloto());
		    					this.btn_modificarInfoVuelo.setDisable(false);
	    					}
	    				}else {
	    					Alert alert = new Alert(AlertType.ERROR);
	    					alert.setContentText("No se encuentra un piloto con la ID registrada");
	    					alert.show();
	    					this.txt_IdPilotoModificar.setText(vuelo.getId_piloto());
	    					this.btn_modificarInfoVuelo.setDisable(false);
	    				}
	    			});
	    			//----------------------------------------------------------------------------------
	    			this.btn_modificarIdCopiloto.setOnAction(e->{
	    				String id_copiloto = this.txt_IdCopilotoModificar.getText();
	    				boolean esta = metodosSQL.buscarIDPilotoPorAerolinea(id_copiloto,IDaerolinea);
                        if(esta) {
                        	if(!id_copiloto.equals(txt_IdPilotoModificar.getText())) {
	    						this.btn_modificarInfoVuelo.setDisable(false);
	    						Alert alert = new Alert(AlertType.INFORMATION);
		    					alert.setContentText("Copiloto modificado con exito");
		    					alert.show();
	    					}else {
	    						Alert alert = new Alert(AlertType.ERROR);
	    						alert.setHeaderText("Operacion Invalida");
		    					alert.setContentText("La ID registrada corresponde al Piloto de este vuelo");
		    					alert.show();
		    					this.txt_IdCopilotoModificar.setText(vuelo.getId_copiloto());
		    					this.btn_modificarInfoVuelo.setDisable(false);
	    					}
	    				}else {
	    					Alert alert = new Alert(AlertType.ERROR);
	    					alert.setContentText("No se encuentra un piloto con la ID registrada");
	    					alert.show();
	    					this.txt_IdCopilotoModificar.setText(vuelo.getId_copiloto());
	    					this.btn_modificarInfoVuelo.setDisable(false);
	    				}
	    			});
	    			//----------------------------------------------------------------------------------
	    			this.btn_modificarIdAvion.setOnAction(e->{
		    			String id_avion = this.txt_IdAvionModificar.getText();
	    				boolean esta = metodosSQL.buscarIDAvion(1,id_avion,IDaerolinea);
	    				if(esta) {
	    					this.btn_modificarInfoVuelo.setDisable(false);
    						Alert alert = new Alert(AlertType.INFORMATION);
	    					alert.setContentText("Avion modificado con exito");
	    					alert.show();
	    				}else {
	    					Alert alert = new Alert(AlertType.ERROR);
	    					alert.setContentText("No se encuentra un Avion con la ID registrada");
	    					alert.show();
	    					this.txt_IdAvionModificar.setText(vuelo.getId_avion());
	    					this.btn_modificarInfoVuelo.setDisable(false);
	    				}
	    			});
					//AÑADIMOS EVENTO A CAMPO DE TEXTO HORA Y FECHA MODIFICAR
					this.txt_horaModificar.setOnMousePressed(e ->{
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setContentText("Para modificar hora y fecha primero\nse debe consultar disponibilidad");
						alert.show();
					});
					this.txt_FechaModificar.setOnMousePressed(e ->{
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setContentText("Para modificar hora y fecha primero\nse debe consultar disponibilidad");
						alert.show();
					});
	    			
	    			
	    			//-----------------------------------------------------------
	    		}else {
	    			Alert alert = new Alert(AlertType.ERROR);
	    			alert.setContentText("No existe ningun vuelo con la ID registrada");
	    			alert.show();
	    		}
	    		this.erroNoIdVuelo.setVisible(false);
	    	}else {
	    		this.erroNoIdVuelo.setVisible(true);
	    	}
			 
	    }
	@FXML void modificarInformacionVuelo(ActionEvent event) {

			   
		    	String id_vuelo = this.txt_vueloModificar.getText();
		    	String id_piloto = this.txt_IdPilotoModificar.getText();
		    	String id_copiloto = this.txt_IdCopilotoModificar.getText();
		    	String id_avion = this.txt_IdAvionModificar.getText();
		    	String tipo = this.cbx_TipoVueloModificar.getSelectionModel().getSelectedItem().toString();
				String destino = this.txt_DestinoModificar.getText();
				String procedencia = this.txt_procedenciaModificar.getText();
			    
				
		    	metodosSQL.modificarInformacionVuelo(id_vuelo, id_piloto, id_copiloto, id_avion,tipo,destino,procedencia);
		    	
		    	//LIMPIAMOS LOS ESPACIOS DEL FORMULARIO
		    	this.txt_horaModificar.clear();
    			this.txt_FechaModificar.clear();
    			this.txt_IdPilotoModificar.clear();
    			this.txt_IdCopilotoModificar.clear();
    			this.txt_IdAvionModificar.clear();
    			this.txt_TipoVueloModificar.clear();
				this.txt_DestinoModificar.clear();
				this.txt_procedenciaModificar.clear();
				this.cbx_TipoVueloModificar.setValue(null);
    			
    			this.panelModificarInfoVuelo.setDisable(true);
		 
	    }
	@FXML void ocultarModificar(MouseEvent event) {
	    	this.btn_modificarInfoVuelo.setDisable(true);
	    }
	//--------------------------------------------------------------------
	static String hora1 = "";
	static String fecha1= "";

	@FXML void consultarDisponibilidadModificar(ActionEvent event) {
	    	hora1 = cbx_horasModificar.getValue().toString();
	    	fecha1 = date_fechaModificar.getValue().toString();
	 
	    	
	    	boolean sihay = metodosSQL.buscarDisponibilidadDeAgenda(hora1, fecha1);
	    	if(sihay) {
	    		this.btn_reprogramarVuelo.setDisable(false);
	    		Alert alert = new Alert(AlertType.INFORMATION);
	    		alert.setHeaderText("Si hay disponibilidad de agenda");
	    		alert.setContentText("Para continuar con el proceso presiona Reprogramar Vuelo");
	    		alert.show();
	    	}else {
	    		Alert alert = new Alert(AlertType.ERROR);
	    		alert.setContentText("No hay disponibilidad de agenda");
	    		alert.show();
	    		this.btn_reprogramarVuelo.setDisable(true);
	    	}
	    }
	//--------------------------------------------------------------------
	@FXML
	void reprogramarVueloHoraFecha (ActionEvent event) {
	
	    	metodosSQL.reprogramarVuelo(hora1, fecha1, txt_vueloModificar.getText());
	    	this.cbx_horasModificar.getSelectionModel().clearSelection();
	    	this.date_fechaModificar.setValue(null);
	    	this.btn_reprogramarVuelo.setDisable(true);
	    	this.panel_consultarAgendaModificar.setDisable(true);
	    }	    
    //------------------------------------------------------------------------------------------------
	static String hora;
	static String fecha;
	@FXML void consultarDisponibilidadAgenda(ActionEvent event) {
	    	
	    	try {
	         
	         hora = cbx_horaVuelo.getValue().toString();
	         fecha = txt_fechaVuelo.getValue().toString();
	    	
	         boolean sihay = metodosSQL.buscarDisponibilidadDeAgenda(hora,fecha);
	         if(sihay) {
	        	 Alert alert = new Alert(AlertType.INFORMATION);
	        	 alert.setContentText("Si hay disponiblidad, completa la informacion del formulario");
	        	 alert.show();
	        	 
	        	 this.panel_formularioAgendarVuelo.setDisable(false);
	        	 this.panel_consultarDisponibilidad.setDisable(true);
	        	 
	        	 //----------------------------------------------------------------
	        	  //ANOMACION DE DESPLAZAMIENTO DEL PANEL DE CONSULTAR DISPONIBILIDAD DE AGENDA
			     TranslateTransition slide = new TranslateTransition();
		         slide.setDuration(Duration.seconds(0.7));
		         slide.setNode(this.panel_consultarDisponibilidad);
		         slide.setToX(0);
		         slide.play();
		         
		         //ANIMACION PANEL FORMILARIO AGENDAR VUELO
			     TranslateTransition slide1 = new TranslateTransition();
		         slide1.setDuration(Duration.seconds(0.7));
		         slide1.setNode(this.panel_formularioAgendarVuelo);
		         slide1.setToX(0);
		         slide1.play();
		         this.panel_formularioAgendarVuelo.setOpacity(1);
	        	 //----------------------------------------------------------------
	         }else {
	        	 Alert alert = new Alert(AlertType.WARNING);
	        	 alert.setContentText("No hay disponiblidad");
	        	 alert.show();
	         }
	         
	    	}catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Campos Vacios");
				alert.show();
			}
	    	
	    }
        //-------------------------------------------------------------------------------------------------
	    static String idAvion = "";
	    static String idPiloto = "";
	    static String idCopiloto = "";
	    static String idVuelo = "0";
	    static String nombreCoPiloto;
	@FXML void agendarVuelo(ActionEvent event) {
	    	 this.boton_agendar.setStyle("-fx-background-color:      #2e4053");
	    	 this.boton_reprogramar.setStyle("-fx-background-color:  #1d2d3b");
	    	 this.boton_programados.setStyle("-fx-background-color:  #1d2d3b");
	    	 
	    	 
	    	 this.panel_agendarVuelo.setVisible(true);
	         this.panel_VuelosProgramados.setVisible(false);
		     this.panelReprogramarVuelo.setVisible(false);
		     this.tabla_VuelosProgramados.getItems().clear();
		     this.erroNoIdVuelo.setVisible(false);
		     this.panel_consultarDisponibilidad.setDisable(false);
		     
		     //ANOMACION DE DESPLAZAMIENTO DEL PANEL DE CONSULTAR DISPONIBILIDAD DE AGENDA
		     TranslateTransition slide = new TranslateTransition();
	         slide.setDuration(Duration.seconds(0.7));
	         slide.setNode(this.panel_consultarDisponibilidad);
	         slide.setToX(300);
	         slide.play();
	         
	         //ANIMACION PANEL FORMILARIO AGENDAR VUELO
		     TranslateTransition slide1 = new TranslateTransition();
	         slide1.setDuration(Duration.seconds(0.7));
	         slide1.setNode(this.panel_formularioAgendarVuelo);
	         slide1.setToX(-150);
	         slide1.play();
	         this.panel_formularioAgendarVuelo.setOpacity(0.3);
		  	 
	    }
	@FXML void agendarVueloEnBaseDatos(ActionEvent event) {
		
	    	 try {
	    			 
				     String destinoVuelo = txt_destinoVuelo.getText();
					 String procedenciaVuelo = txt_procedenciaVuelo.getText();
			    	 //-------------------------------------------------------------------------------------------
			    	 //DATOS DEL VUELO
			    	 String tipoVuelo= "";
			    	 if(this.radio_llegada.isSelected()) {
						 tipoVuelo = "Llegada";
					 }
			    	 if(this.radio_salida.isSelected()) {
						 tipoVuelo =  "Salida";
					}
			
			    	 if(tipoVuelo.equals("")) {this.error_NoTipo.setVisible(true);}  else {this.error_NoTipo.setVisible(false);}
			    	 if(idAvion.equals("")) {this.error_NoAvion.setVisible(true);}   else {this.error_NoAvion.setVisible(false);}
			       	 if(idPiloto.equals("")) {this.error_NoPiloto.setVisible(true);}  else {this.error_NoPiloto.setVisible(false);}
					 if(txt_procedenciaVuelo.getText().equals("")){this.error_NoProcedencia.setVisible(true);}
					 else{this.error_NoProcedencia.setVisible(false);}
					 if(txt_destinoVuelo.getText().equals("")){this.error_NoDestino.setVisible(true);} 
					 else{this.error_NoDestino.setVisible(false);}
					 
			       	 
			       	 //VALIDACION DE COPILOTO---------------------------------------------------------------------------------
			       	 Avion avion = metodosSQL.retornarInformacionAvion(idAvion);
			       	 int numeroMotores = Integer.parseInt(avion.getNum_motores());
			       	 String tipoAvion = avion.getTipo();
			       	 
			       	 
			       	 if(!idPiloto.equals("")&&!idAvion.equals("")&&!tipoVuelo.equals("")
						&&!destinoVuelo.equals("")&&!procedenciaVuelo.equals("")) {
			       		 //VALIDACION DE PASAJEROS
			       		 if(tipoAvion.equals("Pasajeros") && idCopiloto.equals("")) {
			       			 Alert alert = new Alert(AlertType.INFORMATION);
				        	 alert.setContentText("El avion registrado es de TIPO pasajeros, es necesario registrar un copiloto");
				        	 alert.show();
				        	 return;
			       		 }
			       		 //VALIDACION DEL NUMERO DE MOTORES
			       		 if(numeroMotores >= 2  && idCopiloto.equals("")) {
			       			 Alert alert = new Alert(AlertType.INFORMATION);
				        	 alert.setContentText("Los aviones con mas de 2 o mas motores deben contar con un copiloto");
				        	 alert.show();
				        	 return;
			       		 }
			       		 //----------------------------------------------------------------------------------
			       		 //GENERAMOS ID VUELO ALEATOREAMENTE
			       	     int actual = Integer.parseInt(idVuelo);
			       
				         actual++;
				         idVuelo = ""+actual;
				     
				         /***/
				         nombreCoPiloto =this.txt_CoPilotoEncontrado.getText();
				         //SI NO REGISTRA COPILOTO COLOCAMOS EN EL CAMPO EN LA VARIABLE GLOBAL NO REGISTRA
				         if(idCopiloto.equals("")) {
				        	 idCopiloto = "No registra";
				        	 nombreCoPiloto = "No registra";
				         }
			       	     //---------------------------------------------------------------------------------
				         //TICKED
				         String tiked = "----------------------------------------------------------------------\n";
				                tiked+= "INFORMACION VUELO\n\n";
				                tiked+= "Id Vuelo: "+idVuelo+"       \n Tipo Vuelo: "+tipoVuelo+"\n";
				                tiked+= "Hora:     "+hora+"          \n Fecha: "+fecha+"\n";
								tiked+= "Procedencia:     "+procedenciaVuelo+"          \n Destino: "+destinoVuelo+"\n\n\n";
				                tiked+= "INFORMACION AVION\n\n";
				                tiked+= "Id Avion: "+idAvion+"   Tipo Avion: "+tipoAvion+"\n\n\n";
				                tiked+= "INFORMACION  TRUPULACION\n";
				                tiked+= "Id Piloto:   "+idPiloto+"     \nNombre piloto:   "+this.txt_PilotoEncontrado.getText()+"\n";
				                tiked+= "Id Copiloto: "+idCopiloto+"   \nNombre Copiloto: "+nombreCoPiloto+"\n";
				                tiked+= "----------------------------------------------------------------------";
				                
				                
				         //----------------------------------------------------------------------------------
				       	Alert alert2 = new Alert(AlertType.CONFIRMATION);
				    	alert2.setTitle("Confirmar vuelo");
				    	alert2.setHeaderText("Confirmar Informacion");
				    	alert2.setContentText(tiked);

				    	Optional<ButtonType> result = alert2.showAndWait();
				    	if (result.get() == ButtonType.OK){
				    		//SI LA RESPUEST ES OK AGENDAMOS VUELO
							
							//------------------------------------------------------------------------------------------------------
							metodosSQL.agendarVuelo(idVuelo,idPiloto,idCopiloto,idAvion,hora,fecha,tipoVuelo,IDaerolinea,"AGENDADO",
							destinoVuelo,procedenciaVuelo);

                            //VALIDAMOS SI EL VUELO ES DE LLEGADA PARA DAR LA OPCION DE RESRVAR HANGAR
							/*
							if(tipoVuelo.equals("Llegada")){
								//VALIDACION PARA RESERVAR HAGAR EN EL CASO DE QUE EL VUELO SEA DE LLEGADA
								Alert alert3 = new Alert(AlertType.CONFIRMATION);
								alert3.setTitle("Reserva de Hangar");
								alert3.setContentText("Desea reservar hangar?");
								Optional<ButtonType> result1 = alert3.showAndWait();

								if(result1.get() == ButtonType.OK) {
									Hangar hangar = metodosSQL.consultarDisponibilidadDeHangares();
									
									if(hangar != null) {
										metodosSQL.reservarHangar(hangar.getCodigo(),idAvion);
										
									}else if (hangar == null) {
										Notificacion notificacion = new Notificacion("En el momento no hay hangares disponibles", 2);
									}
								}	
							}*/
				    	    //------------------------------------------------------------------------------------------------------
			                 //LIMPIAMOS Y DESACTUVAMOS EL FORMULARIO DE AGENDAR VUELO, HABILITAMOS EL DE CONSULTAR AGENDA
			                 this.panel_formularioAgendarVuelo.setDisable(true);
			                 limpiarFormularioAgendarVuelos();
			                 this.panel_consultarDisponibilidad.setDisable(false);
			                 
			                 //LIMPIAMOS LAS VARIABLES GLOBALES
			                 idCopiloto = "";
			                 idPiloto = "";
			                 
			           	     //ANOMACION DE DESPLAZAMIENTO DEL PANEL DE CONSULTAR DISPONIBILIDAD DE AGENDA
			    		     TranslateTransition slide = new TranslateTransition();
			    	         slide.setDuration(Duration.seconds(0.7));
			    	         slide.setNode(this.panel_consultarDisponibilidad);
			    	         slide.setToX(300);
			    	         slide.play();
			    	         
			    	         //ANIMACION PANEL FORMILARIO AGENDAR VUELO
			    		     TranslateTransition slide1 = new TranslateTransition();
			    	         slide1.setDuration(Duration.seconds(0.7));
			    	         slide1.setNode(this.panel_formularioAgendarVuelo);
			    	         slide1.setToX(-150);
			    	         slide1.play();
			    	         this.panel_formularioAgendarVuelo.setOpacity(0.3);
			                 
			                 
			                 //-------------------------------------------------------------------------------------------
				    	}if(result.get() == ButtonType.CANCEL) {
				    		//SI LA OPCION ES CANCELAR VOVEMOS A CARGAR EL ULTIMO VUELO AGENDADO
				    		idVuelo = metodosSQL.buscarUltimoRegistroVuelos();
				    	} else {
				    	    return;
				    	}
		
			       	 }else {
			       		 Alert alert = new Alert(AlertType.ERROR);
			   			 alert.setHeaderText("Campos Vacios ");
			   			 alert.setContentText("Informacion Incompleta para agendar un vuelo");
			   			 alert.show();
		       	     }
		       	 
		         }catch (Exception e) {
		        
				}
	    	
	    }
	@FXML void esVueloDeLlegada(ActionEvent event){
			//--------------------------------------------------------------------------------
			//AÑADIMOS ESCUCHADORES A LOS RADIO BUTTONS DE TIPO DE VUELO
			this.txt_destinoVuelo.setText("Pasto - Nariño");
		    this.txt_procedenciaVuelo.clear();
			this.txt_procedenciaVuelo.setEditable(true);
			this.txt_destinoVuelo.setEditable(false);
		}
	@FXML void esVueloDeSalida(ActionEvent event){
			//AÑADIMOS ESCUCHADORES A LOS RADIO BUTTONS DE TIPO DE VUELO
			this.txt_destinoVuelo.clear();
			this.txt_procedenciaVuelo.setText("Pasto - Nariño");
			this.txt_destinoVuelo.setEditable(true);
			this.txt_procedenciaVuelo.setEditable(false);
		}
	@FXML void buscarIdAvionVuelo(ActionEvent event) {
	   
	    	 boolean esta = metodosSQL.buscarIDAvion(1,this.txt_idAvionBuscar.getText(),IDaerolinea);
    		 if(esta) {
    			 Avion avion = metodosSQL.retornarInformacionAvion(this.txt_idAvionBuscar.getText());
    			 this.txt_numMotoresEncontrado.setText(avion.getNum_motores());
    			 this.txt_tipoEncontrado.setText(avion.getTipo());
    			 this.idAvion = txt_idAvionBuscar.getText();
    			 //OCULTAMOS ERROR SI TOD VA BIEN
    			 this.error_NoAvion.setVisible(false);
    			 Alert alert = new Alert(AlertType.INFORMATION);
    			 alert.setContentText("Avion registrado al vuelo con exito");
    			 alert.show();
    			 this.txt_idAvionBuscar.setEditable(false);
    			 
    		 }else {
    			 Alert alert = new Alert(AlertType.INFORMATION);
    			 alert.setContentText("No se encuentra un avion con la ID registrada");
    			 alert.show();
    			 this.idAvion = "";
    			 this.txt_numMotoresEncontrado.setText("");
    			 this.txt_tipoEncontrado.setText("");
    			 this.error_NoAvion.setVisible(true);
    		 }
	    }
	@FXML void limpiarInformacionAvion(ActionEvent event) {
	    	 this.txt_idAvionBuscar.clear();
	    	 this.txt_numMotoresEncontrado.clear();
			 this.txt_tipoEncontrado.clear();
			 this.idAvion = "";
			 this.txt_idAvionBuscar.setEditable(true);
	    }
	@FXML void buscarIdPlilotoVuelo(ActionEvent event) {
		    	boolean esta = metodosSQL.buscarIDPilotoPorAerolinea(this.txt_idPilotoBuscar.getText(),IDaerolinea);
		    	String id = this.txt_idPilotoBuscar.getText();
	   		 if(esta) {
	   			 Piloto piloto = metodosSQL.retornarInformacionPiloto(this.txt_idPilotoBuscar.getText());
	   			
	   			 if(!id.equals(idCopiloto)) {
		   			 String nombrePiloto = piloto.getNombre()+" "+piloto.getApellido();
		   			 this.txt_PilotoEncontrado.setText(nombrePiloto);
		   			 this.idPiloto = txt_idPilotoBuscar.getText();
		   			 //OCULTAMOS ERROR SI TOD VA BIEN
		   			 this.error_NoPiloto.setVisible(false);
		   			 Alert alert = new Alert(AlertType.INFORMATION);
		   			 alert.setContentText("Piloto registrado al vuelo con exito");
		   			 alert.show();
		   			 this.txt_idPilotoBuscar.setEditable(false);
	   			 }else {
	   				 Alert alert = new Alert(AlertType.ERROR);
		   			 alert.setContentText("En el momento ya existe un Copiloto registrado con esta ID: "+id);
		   			 alert.show();
		   			this.txt_PilotoEncontrado.setText("");
	   			 }
	   		 }else {
	   			 Alert alert = new Alert(AlertType.ERROR);
	   			 alert.setContentText("No se encuentra un Piloto con la ID registrada");
	   			 alert.show();
	   			 this.idPiloto = "";
	   			 this.txt_PilotoEncontrado.setText("");
	   			 this.error_NoPiloto.setVisible(true);
	   		 }

	    }
	@FXML void limpiarInformacionPiloto(ActionEvent event) {
	    	 this.txt_idPilotoBuscar.clear();
	    	 this.txt_PilotoEncontrado.clear();
   			 this.idPiloto = "";
   			this.txt_idPilotoBuscar.setEditable(true);
	    }
	@FXML void buscarIdCoplilotoVuelo(ActionEvent event) {
	  
	    	boolean esta = metodosSQL.buscarIDPilotoPorAerolinea(this.txt_idCopilotoBuscar.getText(),IDaerolinea);
	    	String id = this.txt_idCopilotoBuscar.getText();
		   		 if(esta) {
		   			 if(!id.equals(idPiloto)) {
			   			 Piloto piloto = metodosSQL.retornarInformacionPiloto(this.txt_idCopilotoBuscar.getText());
			   			 String nombrePiloto = piloto.getNombre()+" "+piloto.getApellido();
			   			 this.txt_CoPilotoEncontrado.setText(nombrePiloto);
			   			 this.idCopiloto = txt_idCopilotoBuscar.getText();
			   			 //OCULTAMOS ERROR SI TOD VA BIEN
			   			 this.error_NoCopiloto.setVisible(false);
			   			 Alert alert = new Alert(AlertType.INFORMATION);
			   			 alert.setContentText("Copiloto registrado al vuelo con exito");
			   			 alert.show();
		   			 }else {
		   				 Alert alert = new Alert(AlertType.ERROR);
			   			 alert.setContentText("En el momento ya existe un piloto registrado con esta ID: "+id);
			   			 alert.show();
			   			 this.txt_CoPilotoEncontrado.setText("");
			   			 this.txt_idCopilotoBuscar.setEditable(false);
		   			 }
		   		 }else {
		   			 Alert alert = new Alert(AlertType.ERROR);
		   			 alert.setContentText("No se encuentra un Piloto con la ID registrada");
		   			 alert.show();
		   			 this.idCopiloto = "";
		   			 this.txt_CoPilotoEncontrado.setText("");
		   		
		   			 this.error_NoCopiloto.setVisible(true);
		   		 }

	    }
	@FXML void limpiarInformacionCopiloto(ActionEvent event) {
	    	 this.txt_idCopilotoBuscar.clear();
	    	 this.txt_CoPilotoEncontrado.clear();
  			 this.idCopiloto = "";
  			 this.txt_idCopilotoBuscar.setEditable(true);
	    }
	@FXML void reprogramarVuelo(ActionEvent event) {
	    	 this.boton_agendar.setStyle("-fx-background-color:      #1d2d3b");
	    	 this.boton_reprogramar.setStyle("-fx-background-color:  #2e4053");
	    	 this.boton_programados.setStyle("-fx-background-color:  #1d2d3b");
	    	 
	    	// limpiarFormularioAgendarVuelos();
	    	 this.panel_VuelosProgramados.setVisible(false);
	    	 this.panel_agendarVuelo.setVisible(false);
	    	 this.panelReprogramarVuelo.setVisible(true);
	    	 this.tabla_VuelosProgramados.getItems().clear();
	    	 this.erroNoIdVuelo.setVisible(false);
	    }
	@FXML void mostrarVuelosProgramados(ActionEvent event) {
	         this.tabla_VuelosProgramados.getItems().clear();
	    	 this.boton_agendar.setStyle("-fx-background-color:      #1d2d3b");
	    	 this.boton_reprogramar.setStyle("-fx-background-color:  #1d2d3b");
	    	 this.boton_programados.setStyle("-fx-background-color:  #2e4053");
	    	 //--------------------------------------------------------------------------------------
	    	 this.panel_VuelosProgramados.setVisible(true);
	    	 this.panel_agendarVuelo.setVisible(false);
	    	 this.panelReprogramarVuelo.setVisible(false);
	    	 metodosSQL.cargarVuelosAgendadosAerolinea(this.tabla_VuelosProgramados,IDaerolinea);
	       //  limpiarFormularioAgendarVuelos();
	         this.erroNoIdVuelo.setVisible(false);
			 //LIMPIAMOS TABLA VUELOS AGENDADOS
			

	    }
	@FXML void salirPanelVuelosAerolineas(ActionEvent event) {
	    	this.panel_vuelosAerolineas.setVisible(false);
	    	this.panelPrincipalAerolineas.setVisible(true);
    		this.tabla_VuelosProgramados.getItems().clear();
    		limpiarFormularioAgendarVuelos();
    		 this.boton_agendar.setStyle("-fx-background-color:      #2e4053");
	    	 this.boton_reprogramar.setStyle("-fx-background-color:  #1d2d3b");
	    	 this.boton_programados.setStyle("-fx-background-color:  #1d2d3b");
	    	 
	    	  //ANOMACION DE DESPLAZAMIENTO DEL PANEL DE CONSULTAR DISPONIBILIDAD DE AGENDA
		     TranslateTransition slide = new TranslateTransition();
	         slide.setDuration(Duration.seconds(0.7));
	         slide.setNode(this.panel_consultarDisponibilidad);
	         slide.setToX(0);
	         slide.play();
	         
	         //ANIMACION PANEL FORMILARIO AGENDAR VUELO
		     TranslateTransition slide1 = new TranslateTransition();
	         slide1.setDuration(Duration.seconds(0.7));
	         slide1.setNode(this.panel_formularioAgendarVuelo);
	         slide1.setToX(0);
	         slide1.play();
	         this.panel_formularioAgendarVuelo.setOpacity(1);
	    } 
	//-----------------------------------------------------
	public void registrarPiloto() {
	    	try {

					String nombre = txt_nomFormuPlito.getText();
					String apellidos = txt_apellFormuPlito.getText();
					String cedula = txt_ceduFormuPlito.getText();
					String horasDeVuelo = txt_hrsVueFormuPlito.getText();
					String licencia = txt_licenFormuPlito.getText();
					String date = this.txt_revisMedicaFormuPlito.getValue().toString();

					if(nombre!="" && apellidos != "" && cedula != "" && horasDeVuelo != "" && licencia != "" && date != ""){
						metodosSQL.registrarPilotoEnBD(cedula,IDaerolinea, nombre, apellidos, licencia,horasDeVuelo, date);
						refrescarTablaPilotosAeroliena();
					}else{
						Alert alert = new Alert(AlertType.ERROR);
	    				alert.setContentText("Existen campos vacios");
	    				alert.show();
					}
				
	    	}catch (Exception e) {
	    		Alert alert = new Alert(AlertType.ERROR);
	    		alert.setContentText("Existen campos vacios");
	    		alert.show();
	    	}	
	    	
	    }
	public void registrarAvion() {
	    	try {

	    		String id_avion = this.txt_idAvion.getText();
	    		String capacidad = this.txt_capacidadAvion.getText();
	    		String modelo = this.txt_modeloAvion.getText();
	    		String pesoNominal = this.txt_pesoNominalAvion.getText();
	    		String numeroMotores = this.cbx_numMotoresAvion.getValue().toString();
	    		String tipoPropulsion = this.cbx_tipoPropulsionAvion.getValue().toString();
	    		String tipo = null;
	    		if(radio_cargaAvion.isSelected()) {
	    			tipo = "Carga";
	    		}else if(radio_pasajerosAvion.isSelected()) {
	    			tipo = "Pasajeros";
	    		}if(tipo.equals(null) || numeroMotores.equals(null)||tipoPropulsion.equals(null)) {
	    			Alert alert = new Alert(AlertType.ERROR);
		    		alert.setContentText("Existen campos vacios");
		    		alert.show();
	    		}else {	
					if(id_avion != "" && capacidad != "" && modelo != "" && pesoNominal != ""){
						metodosSQL.registrarAvionEnBD(id_avion, IDaerolinea, tipo, capacidad, modelo, tipoPropulsion, pesoNominal, numeroMotores);
						refrescarTablaAvionesAerolienas();
					}else{
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Existen campos vacios");
						alert.show();
					}
	    		}	
	    		
	    	}catch (Exception e) {
	    		Alert alert = new Alert(AlertType.ERROR);
	    		alert.setContentText("Existen campos vacios");
	    		alert.show();
			}
	    	
	    	
	    }
	public void ocultarErroresFormularioPilotos() {
	    	this.errorApellidos.setVisible(false);
	    	this.errorCedula.setVisible(false);
	    	this.errorHorasVuelo.setVisible(false);
	    	this.errorLicencia.setVisible(false);
	    	this.errorRevisionMedico.setVisible(false);
	    	this.errorNombre.setVisible(false);
	    }
	@FXML void cancelarFormularioPiloto(ActionEvent event) {
	    	
	    	this.panelFormularioPiloto.setVisible(false);
	    	this.panelPrincipalAerolineas.setOpacity(0.81);
	    	this.panelPrincipalAerolineas.setDisable(false);
	    	this.panel_reportesAerolienas.setDisable(false);
	    	limpiarFormularioPiloto();
	    }
	@FXML void cancelarFormularioAvion(ActionEvent event) {

	    	this.panelFormularioAvion.setVisible(false);
	    	this.panelPrincipalAerolineas.setOpacity(0.81);
	    	this.panelPrincipalAerolineas.setDisable(false);
	    	this.panel_reportesAerolienas.setDisable(false);
            limpiarFormularioAvion();
	    }    
	//--------------------------------------------------------------------------
	//PANEL REPORTES MENU AEROLIENAS
	    static String IDPILOTO_SELECCIONADO = "";
	    static String IDAVION_SELECCIONADO = "";
	    static Piloto piloto;
	    static Avion avion;
	@FXML private AnchorPane panel_reportesAerolienas,panelReporteAvionesAeroliena,panelReportePilotosAeroliena;
	@FXML private TableView<Piloto> tabla_pilotosDeAerolinea;
	@FXML private TableView<Avion> tabla_avionesAerolinea;
	@FXML private TableColumn columnIdPilotoAerolinea,columnNombrePilotoAerolinea,columnApellidoPilotoAerolinea,columnLicenciaPilotoAerolinea,columnHrsVueloPilotoAerolinea,columnRvsMedicaPilotoAerolinea;
	@FXML private TableColumn columnIdAvionAerolinea,columnModeloAvionAerolinea,columnCapacidadAvionAerolinea,columnTipoAvionAerolinea,columnTipoPropulAvionAerolinea,columnPesoAvionAerolinea,columnNumMotoresAvionAerolinea;
	@FXML private TextField txt_idPilotoAerolienaModificar,txt_nombrePilotoAerolienaModificar,txt_apellidoPilotoAerolienaModificar,txt_licenciaPilotoAerolienaModificar,txt_hrsVueloPilotoAerolienaModificar;
	@FXML private TextField txt_idAvionModificar,txt_modeloAvionModificar,txt_capacidadAvionModificar,txt_pesoAvionModificar,txt_motoresAvionModificar;
	@FXML private ComboBox cbx_tipoAvionModificar,cbx_propulsionAvionModificar;
	@FXML private DatePicker date_rvsMedicaPiloto;
	@FXML void mostrarMenuReportesAerolienas(ActionEvent event) {
	    	this.panelPrincipalAerolineas.setVisible(false);
	    	this.panel_reportesAerolienas.setVisible(true);
	    	//LIMPIAMOS LA TABLA DE PILOTOS Y AVIONES  LA VOLVEMOS A REFRESCAR
	    	refrescarTablaPilotosAeroliena();
	    	refrescarTablaAvionesAerolienas();
	    
	    }
	@FXML void salirPanelReportesAerolienas(ActionEvent event){
	    	this.panel_reportesAerolienas.setVisible(false);	
	    	this.panelPrincipalAerolineas.setVisible(true);
	    
	    }
	@FXML void nuevoPiloto(ActionEvent event) {
	    	this.panel_reportesAerolienas.setDisable(true);
	    	this.panelFormularioPiloto.setVisible(true);
	    }
	@FXML void nuevoAvion(ActionEvent event) {
	    	this.panel_reportesAerolienas.setDisable(true);
	    	this.panelFormularioAvion.setVisible(true);
	    }
	@FXML void eliminarAvion(ActionEvent event) {
	    	if(!IDAVION_SELECCIONADO.equals("")) {
		    	this.metodosSQL.eliminarAvion(IDAVION_SELECCIONADO);
		    	refrescarTablaAvionesAerolienas();
		    	IDAVION_SELECCIONADO = "";
		    	
		    	LimpiarCamposmodificarAvion(event);
	    	}else {
	    		Alert alert = new Alert(AlertType.INFORMATION);
	    		alert.setHeaderText("ERROR");
	    		alert.setContentText("Debes seleccionar un avion para Eliminar");
	    		alert.show();
	    	}
	    }
	@FXML void eliminarPiloto(ActionEvent event) {
	    	if(!IDPILOTO_SELECCIONADO.equals("")) {
		    	this.metodosSQL.eliminarPiloto(IDPILOTO_SELECCIONADO);
		    	refrescarTablaPilotosAeroliena();
		    	IDPILOTO_SELECCIONADO = "";
		    	
		    	limpiarCamposmodificarPiloto(event);
		    	
	    	}else {
	    		Alert alert = new Alert(AlertType.INFORMATION);
	    		alert.setHeaderText("ERROR");
	    		alert.setContentText("Debes seleccionar un avion para Eliminar");
	    		alert.show();
	    	}
	    }
	@FXML void modificarAvion(ActionEvent event) {
	    	//VALIDACION PARA QUE A LA HORA DE MODIFICAR SE SELECCIONE UN AVION DE LA TABLA
	    	if(!IDAVION_SELECCIONADO.equals("")) {
		    	String idAvionModificar = this.txt_idAvionModificar.getText();
		    	String modelo = this.txt_modeloAvionModificar.getText();
		    	String capacidad = this.txt_capacidadAvionModificar.getText();
		    	String peso = this.txt_pesoAvionModificar.getText();
		    	String numMotores = this.txt_motoresAvionModificar.getText();
		    	String tipo = this.cbx_tipoAvionModificar.getValue().toString();
		    	String propulsion =  this.cbx_propulsionAvionModificar.getValue().toString();
		    	String id_avion = this.avion.getId_avion();
		    	
		    	//VALIDAR QUE LOS CAMPOS A MODIFICIAR ESTEN COMPLETOS
		    	if(idAvionModificar.equals("") ||modelo.equals("") || capacidad.equals("")||peso.equals("")||numMotores.equals("")
		    	  ||tipo.equals("")||propulsion.equals("")||id_avion.equals("")) {
		    		Alert alert = new Alert(Alert.AlertType.ERROR);
	    			alert.setHeaderText("Campos Vacios");
	    			alert.setContentText("Se han encontrado campos vacios en la informacion que se decea modificar");
	    			alert.show();
		    	  }else {
			     	//VALIDACION PARA CAMBIAR LA ID DE UN AVION
			        if(!idAvionModificar.equals(avion.getId_avion())) {
			    		boolean esta = this.metodosSQL.buscarIDAvionEnGeneral(idAvionModificar);
			    		if(!esta) {
			    			this.metodosSQL.modificarInformacionAvion(idAvionModificar, tipo, capacidad, modelo, propulsion, peso, numMotores, id_avion);
			    		}else {
			    			Alert alert = new Alert(Alert.AlertType.ERROR);
			    			alert.setHeaderText("Ya se encuentra un Avion registrado con la ID: "+idAvionModificar);
			    			alert.show();
			    		}
			    	}else {
			    		this.metodosSQL.modificarInformacionAvion(idAvionModificar, tipo, capacidad, modelo, propulsion, peso, numMotores, id_avion);
			    	}
			    	//REFRESCAMOS LA TABLA DE PILOTOS
			        refrescarTablaAvionesAerolienas();
		    	  }
	    	}else {
	    		Alert alert = new Alert(Alert.AlertType.ERROR);
    			alert.setHeaderText("Se debe seleccionar un avion de la tabla para porder modificar");
    			alert.show();
	    	}
	    }
	@FXML void modificarPiloto(ActionEvent event) {
	    	//VALIDACION PARA QUE A LA HORA DE MODIFICAR SE SELECCIONE UN PILOTO DE LA TABLA
	    	if(!IDPILOTO_SELECCIONADO.equals("")) {
			    	String id_pilotoModificar = this.txt_idPilotoAerolienaModificar.getText();
			    	String nombre =  this.txt_nombrePilotoAerolienaModificar.getText();
			    	String apellido = this.txt_apellidoPilotoAerolienaModificar.getText();
			    	String licencia = this.txt_licenciaPilotoAerolienaModificar.getText();
			    	String hrs_vuelo = this.txt_hrsVueloPilotoAerolienaModificar.getText();
			    	String rvs_medica =this.date_rvsMedicaPiloto.getValue().toString();
			    	String id_piloto = this.piloto.getId_piloto();
			    	//VALIDACION CAMPOS COMPLETOS PARA MODIFICAR INFORMACION DE PILOTO
			    	  if(id_pilotoModificar.equals("")||nombre.equals("")||apellido.equals("")||licencia.equals("")||hrs_vuelo.equals("")||
			    	     rvs_medica.equals("")||id_piloto.equals("")) {
			    		    Alert alert = new Alert(Alert.AlertType.ERROR);
			    			alert.setHeaderText("Campos Vacios");
			    			alert.setContentText("Se han encontrado campos vacios en la informacion que se decea modificar");
			    			alert.show(); 
			    	  }else {
			    	  
				    	//VALIDACION PARA CAMBIAR LA ID DE UN PILOTO
				        if(!id_pilotoModificar.equals(piloto.getId_piloto())) {
				    		boolean esta = this.metodosSQL.buscarIDPilotoEnGeneral(id_pilotoModificar);
				    		if(!esta) {
				    			this.metodosSQL.modificarInformacionPiloto(id_pilotoModificar, nombre, apellido, licencia, hrs_vuelo, rvs_medica,id_piloto);
				    			
				    		}else {
				    			Alert alert = new Alert(Alert.AlertType.ERROR);
				    			alert.setHeaderText("Ya se encuentra un piloto registrado con la ID: "+id_pilotoModificar);
				    			alert.show();
				    		}
				    	}else {
				    		this.metodosSQL.modificarInformacionPiloto(id_pilotoModificar, nombre, apellido, licencia, hrs_vuelo, rvs_medica,id_piloto);
				    	}
				    	//REFRESCAMOS LA TABLA DE PILOTOS
				        refrescarTablaPilotosAeroliena();
			    	  }
	    	}else {
	    		Alert alert = new Alert(Alert.AlertType.ERROR);
    			alert.setHeaderText("Se debe seleccionar un Piloto de la tabla para porder modificar");
    			alert.show();
	    	}
	    }
	public void refrescarTablaPilotosAeroliena() {
	    	this.tabla_pilotosDeAerolinea.getItems().clear();
	    	this.metodosSQL.cargarPilotosAerolinea(tabla_pilotosDeAerolinea, IDaerolinea);
	    }
	public void refrescarTablaAvionesAerolienas() {
	    	this.tabla_avionesAerolinea.getItems().clear();
	    	this.metodosSQL.cargarAvionesAeroliena(tabla_avionesAerolinea, IDaerolinea);
	    }
	public void cargarCamposPilotoModificar(Piloto piloto) {
	    	
	    	this.txt_idPilotoAerolienaModificar.setText(piloto.getId_piloto());
	    	this.txt_nombrePilotoAerolienaModificar.setText(piloto.getNombre());
	    	this.txt_apellidoPilotoAerolienaModificar.setText(piloto.getApellido());
	    	this.txt_licenciaPilotoAerolienaModificar.setText(piloto.getLicencia());
	    	this.txt_hrsVueloPilotoAerolienaModificar.setText(piloto.getHoras_vuelo());
	    	
	    	//FORMATEAMOS FECHA
	    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		    LocalDate localDate = LocalDate.parse(piloto.getRevision_medica(), formatter);
	    	this.date_rvsMedicaPiloto.setValue(localDate);
	    }
	public void cargarCamposAvionModificar(Avion avion) {
	    	this.txt_idAvionModificar.setText(avion.getId_avion());
	    	this.txt_modeloAvionModificar.setText(avion.getModelo());
	    	this.txt_capacidadAvionModificar.setText(avion.getCapacidad());
	    	this.txt_pesoAvionModificar.setText(avion.getPeso_nominal());
	    	this.txt_motoresAvionModificar.setText(avion.getNum_motores());
	    	this.cbx_tipoAvionModificar.setValue(avion.getTipo());
	    	this.cbx_propulsionAvionModificar.setValue(avion.getTipo_propulsion());;
	    }
	@FXML void mostrarReportePilotosAeroliena() {
	    	this.panelReporteAvionesAeroliena.setVisible(false);
	    	this.panelReportePilotosAeroliena.setVisible(true);
	    	//LIMPIAMOS LA TABLA DE PILOTOS Y LA VOLVEMOS A REFRESCAR
	    	this.tabla_pilotosDeAerolinea.getItems().clear();
	    	this.metodosSQL.cargarPilotosAerolinea(tabla_pilotosDeAerolinea, IDaerolinea);
	    	 this.botonReporteAviones.setStyle("-fx-background-color:      #1d2d3b");
	    	 this.botonReportePiloto.setStyle("-fx-background-color:  #2e4053");
	    }
	@FXML void mostrarReporteAvionesAeroliena( ) {
	    	this.panelReporteAvionesAeroliena.setVisible(true);
	    	this.panelReportePilotosAeroliena.setVisible(false);
	    	//LIMPIAMOS LA TABLA DE AVIONES Y LA VOLVEMOS A REFRESCAR
	    	this.tabla_avionesAerolinea.getItems().clear();
	    	this.metodosSQL.cargarAvionesAeroliena(tabla_avionesAerolinea, IDaerolinea);
	    	 this.botonReportePiloto.setStyle("-fx-background-color:      #1d2d3b");
	    	 this.botonReporteAviones.setStyle("-fx-background-color:  #2e4053");
	    }
	@FXML void LimpiarCamposmodificarAvion(ActionEvent event) {
	    	this.txt_idAvionModificar.clear();
	    	this.txt_modeloAvionModificar.clear();
	    	this.txt_capacidadAvionModificar.clear();
	    	this.txt_pesoAvionModificar.clear();
	    	this.txt_motoresAvionModificar.clear();
	    	this.cbx_tipoAvionModificar.setValue(null);
	    	this.cbx_propulsionAvionModificar.setValue(null);;
	    	
	    	this.tabla_avionesAerolinea.getSelectionModel().clearSelection();
	    	this.IDAVION_SELECCIONADO = "";
	    }
	@FXML void limpiarCamposmodificarPiloto(ActionEvent event) {
	    	this.txt_idPilotoAerolienaModificar.clear();
	    	this.txt_nombrePilotoAerolienaModificar.clear();
	    	this.txt_apellidoPilotoAerolienaModificar.clear();
	    	this.txt_licenciaPilotoAerolienaModificar.clear();
	    	this.txt_hrsVueloPilotoAerolienaModificar.clear();
	    	this.date_rvsMedicaPiloto.setValue(null);
	    	
	    	this.tabla_pilotosDeAerolinea.getSelectionModel().clearSelection();
	    	this.IDPILOTO_SELECCIONADO = "";
	    	
	    }
	//--------------------------------------------------------------------------

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//--------------------------------------------------------
		this.metodosSQL = new MetodosSQL();
		//VALIDACION SI EL ES SISTEMA SE ENCUENTRA EN LINEA
		this.metodosSQL.conectarBD();
		
		
	    	//CARGAR ITEMS DEL LOGIN PRINCIPAL
	    	this.cbx_orgaizacion.getItems().addAll("Administrador","Aerolinea","Administrador Hangares");
			
	   
	  
	    	this.cbx_TipoVueloModificar.getItems().addAll("Llegada","Salida");
	    	this.cbx_numMotoresAvion.getItems().addAll("1","2","3","4","5","6");
	    	this.cbx_tipoPropulsionAvion.getItems().addAll("Reaccion","Turbo helice" ,"Helice");
	    	this.cbx_tipoAvionModificar.getItems().addAll("Carga","Pasajeros");
	    	this.cbx_propulsionAvionModificar.getItems().addAll("Reaccion","Turbo helice" ,"Helice");
	    	
	    	this.cbx_horaVuelo.getItems().addAll("00:00","00:30","01:00","01:30","02:00","02:30","03:00","03:30","04:00","04:30","05:00","05:30","06:00",
	    			                             "06:30","07:00","07:30","08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30",
	    			                             "13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30","18:00","18:30","19:00",
	    			                             "19:30","20:00","20:30","21:00","21:30","22:00","22:30","23:00","23:30");
	    	this.cbx_horasModificar.getItems().addAll("00:00","00:30","01:00","01:30","02:00","02:30","03:00","03:30","04:00","04:30","05:00","05:30","06:00",
                    "06:30","07:00","07:30","08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30",
                    "13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30","18:00","18:30","19:00",
                    "19:30","20:00","20:30","21:00","21:30","22:00","22:30","23:00","23:30");
	
	    	
	    	ToggleGroup radiosButon = new ToggleGroup();
	    	this.radio_cargaAvion.setToggleGroup(radiosButon);
	    	this.radio_pasajerosAvion.setToggleGroup(radiosButon);
	    	ToggleGroup radiosButo = new ToggleGroup();
	    	this.radio_llegada.setToggleGroup(radiosButo);
	    	this.radio_salida.setToggleGroup(radiosButo);
	        
	    	
	    	//--------------------------------------------------------------------
	    	//RELACIONANDO LA TABLA USUARIOS AEROLINEA CON LOS ATRBUTOS DE LA CLASE USUARIO
	    	this.colnombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
	    	this.colId.setCellValueFactory(new PropertyValueFactory<>("id"));
	    	this.colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
	    	this.colusuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
	    	this.colcontrasena.setCellValueFactory(new PropertyValueFactory<>("contrasena"));
	    	this.colestado.setCellValueFactory(new PropertyValueFactory<>("estado"));
	    	//--------- CARGAMOS LOS DATOS EN LA TABLA USUARIOS AEROLINEA ------------------------------
		    metodosSQL.cargarDatosUusuarios(this.tabla_usuarios);
	    	//-------------------------------------------------------------------------------
	        //ASIGNAMOS EVENTO A LA TABLA USUARIOS AEROLINEA PARA CONOCER QUE ITEMS SE ESTA SELECCIONADO
	    	tabla_usuarios.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
	    	     int x = tabla_usuarios.getSelectionModel().getFocusedIndex();
	    	     txt_idAerolineaActiDesacEliminar.setText(tabla_usuarios.getItems().get(x).getId());
				 txt_nombreAerolineaActiDesacEliminar.setText(tabla_usuarios.getItems().get(x).getNombre());
				 txt_usuarioAerolineaActiDesacEliminar.setText(tabla_usuarios.getItems().get(x).getUsuario());
				 txt_contraAerolineaActiDesacEliminar.setText(tabla_usuarios.getItems().get(x).getContrasena());
				 txt_emailAerolineaActiDesacEliminar.setText(tabla_usuarios.getItems().get(x).getEmail());
				 
	    	     //---------------------------------------------------------------------------------------  
	    	});
	    	//ASIGNAMOS UN ESCUCHADOR A LA TABLA DE PILOTOS AEROLINEA PARA CONOCER QUE ITEMS SE ESTA SELECCIONANDO
	    	this.tabla_pilotosDeAerolinea.addEventFilter(MouseEvent.MOUSE_CLICKED,e -> {
	    		int x = tabla_pilotosDeAerolinea.getSelectionModel().getFocusedIndex();
	    		Piloto piloto = (Piloto)tabla_pilotosDeAerolinea.getItems().get(x);
	    		this.piloto = piloto;
	    		IDPILOTO_SELECCIONADO = piloto.getId_piloto();
	    		//CARGAMOS LOS CAMPOS CON LA INFORMACION DEL PILOTO SELECCIONADO
	    		cargarCamposPilotoModificar(piloto);
	    	});
	    	//ASIGNAMOS UN ESCUCHADOR A LA TABLA DE AVIONES AEROLINEA PARA CONOCER QUE ITEMS SE ESTA SELECCIONANDO
	    	this.tabla_avionesAerolinea.addEventFilter(MouseEvent.MOUSE_CLICKED,e -> {
	    		int x = tabla_avionesAerolinea.getSelectionModel().getFocusedIndex();
	    		Avion avion= (Avion)tabla_avionesAerolinea.getItems().get(x);
	    		this.avion = avion;
	    		IDAVION_SELECCIONADO = avion.getId_avion();
	    		//CARGAMOS LOS CAMPOS CON LA INFORMACION DEL AVION SELECCIONADO
	    		cargarCamposAvionModificar(avion);
	    	});
			//ASIGNAMOS UN ESCUCHADOR A LA TABLA DE USUARIOS EN ADMINISTRACION
			this.tabla_aerolienasREgistradas.addEventFilter(MouseEvent.MOUSE_CLICKED,e -> {
				int x = tabla_aerolienasREgistradas.getSelectionModel().getFocusedIndex();

				this.id_usuario_del_sistema.setText(tabla_aerolienasREgistradas.getItems().get(x).getId());
				this.nombre_usuario_del_sistema.setText(tabla_aerolienasREgistradas.getItems().get(x).getNombre());
				this.usuario_usuario_del_sistema.setText(tabla_aerolienasREgistradas.getItems().get(x).getUsuario());
				this.contrasena_usuario_del_sistema.setText(tabla_aerolienasREgistradas.getItems().get(x).getContrasena());
			
			});
	    	//----------------------------------------------------------------------------------------
	    	//RELACIONAMOS LA TABLA DE VUELOS AGENDADOS
	    	this.columIdVuelo.setCellValueFactory(new PropertyValueFactory<>("id_vuelo"));
	    	this.columIdPiloto.setCellValueFactory(new PropertyValueFactory<>("id_piloto"));
	    	this.columIdCopiloto.setCellValueFactory(new PropertyValueFactory<>("id_copiloto"));
	    	this.columIdAvion.setCellValueFactory(new PropertyValueFactory<>("id_avion"));
	    	this.columHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
	    	this.columFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
	    	this.columTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
	    	this.columEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
			this.columDestinoVuelo.setCellValueFactory(new PropertyValueFactory<>("destino"));
			this.columProcedenciaVuelo.setCellValueFactory(new PropertyValueFactory<>("procedencia"));
			
			
	    	//A�ADIMOS ESCUCHADOR A LA TABLA DE VUELOS AGENDADOS DE AEROLIENA
	    	this.tabla_VuelosProgramados.addEventFilter(MouseEvent.MOUSE_CLICKED,e ->{
	    		int pos = tabla_VuelosProgramados.getSelectionModel().getSelectedIndex();
	    		txt_idVueloCancelarEliminar.setText(tabla_VuelosProgramados.getItems().get(pos).getId_vuelo());
	    	});
	    	
	    	//RELACIONAMOS LA TABLA DE PILOTOS REGISTRADOS PARA ADMINISTRACION
	    	this.columIdPilotoAdmin.setCellValueFactory(new PropertyValueFactory<>("id_piloto"));
	    	this.columNombrePilotoAdmin.setCellValueFactory(new PropertyValueFactory<>("nombre"));
	    	this.columAerolineaPilotoAdmin.setCellValueFactory(new PropertyValueFactory<>("id_aerolinea"));
	     	metodosSQL.cargarPilotosAdministracion(tabla_pilotosRegistrados);
	     	
	     	//RELACIONAMOS LA TABLA DE AVIONES PARA ADMINISTRACION
	     	this.columnaIDAvionAdmin.setCellValueFactory(new PropertyValueFactory<>("id_avion"));
	     	this.columnaTipoAvionAdmin.setCellValueFactory(new PropertyValueFactory<>("tipo"));
	     	this.columnaModeloAvionAdmin.setCellValueFactory(new PropertyValueFactory<>("modelo"));
	     	this.columnaAerolineaAvionAdmin.setCellValueFactory(new PropertyValueFactory<>("id_aerolinea"));
	    	metodosSQL.cargarAvionesAdministracion(tabla_avionesRegistrados);
	    	
	    	//RELACIONAMOS LA TABLA DE AEROLINEAS PARA ADMINISTRACION
	    	this.columnaIDAeroAdmin.setCellValueFactory(new PropertyValueFactory<>("id"));
	    	this.columnaNombreAeroAdmin.setCellValueFactory(new PropertyValueFactory<>("nombre"));
			this.columnaUsuarioAeroAdmin.setCellValueFactory(new PropertyValueFactory<>("usuario"));
			this.columnaContrasenaAeroAdmin.setCellValueFactory(new PropertyValueFactory<>("contrasena"));
			this.columnaRolAeroAdmin.setCellValueFactory(new PropertyValueFactory<>("rol"));
			
			

	    	metodosSQL.cargarAerolineasAdministracion(tabla_aerolienasREgistradas);
	    	
	    	//RELACIONAMOS LA TABLA DE PILOTOS PARA CADA AEROLIENA
	    	this.columnIdPilotoAerolinea.setCellValueFactory(new PropertyValueFactory<>("id_piloto"));
	    	this.columnNombrePilotoAerolinea.setCellValueFactory(new PropertyValueFactory<>("nombre"));
	    	this.columnApellidoPilotoAerolinea.setCellValueFactory(new PropertyValueFactory<>("apellido"));
	    	this.columnLicenciaPilotoAerolinea.setCellValueFactory(new PropertyValueFactory<>("licencia"));
	    	this.columnHrsVueloPilotoAerolinea.setCellValueFactory(new PropertyValueFactory<>("horas_vuelo"));
	    	this.columnRvsMedicaPilotoAerolinea.setCellValueFactory(new PropertyValueFactory<>("revision_medica"));
            metodosSQL.cargarPilotosAerolinea(tabla_pilotosDeAerolinea, IDaerolinea);
            
            //RELACIONAMOS LA TABLA DE AVIONES PARA CADA AEROLIENA
            this.columnIdAvionAerolinea.setCellValueFactory(new PropertyValueFactory<>("id_avion"));
            this.columnTipoAvionAerolinea.setCellValueFactory(new PropertyValueFactory<>("tipo"));
            this.columnCapacidadAvionAerolinea.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
            this.columnModeloAvionAerolinea.setCellValueFactory(new PropertyValueFactory<>("modelo"));
            this.columnTipoPropulAvionAerolinea.setCellValueFactory(new PropertyValueFactory<>("tipo_propulsion"));
            this.columnPesoAvionAerolinea.setCellValueFactory(new PropertyValueFactory<>("peso_nominal"));
            this.columnNumMotoresAvionAerolinea.setCellValueFactory(new PropertyValueFactory<>("num_motores"));
            metodosSQL.cargarAvionesAeroliena(tabla_avionesAerolinea, IDaerolinea);
            
            //RELACIONAMOS LA TABLA DE REPORTES DE HANGARES
			this.colum_codigoHangar.setCellValueFactory(new PropertyValueFactory<>("codigo"));
			this.colum_ubicacionHangar.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));
			this.colum_capacidadHangar.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
			this.colum_estadoHangar.setCellValueFactory(new PropertyValueFactory<>("estado"));
			this.colum_tarifaHangar.setCellValueFactory(new PropertyValueFactory<>("tarifa"));
			this.colum_idAvionHangar.setCellValueFactory(new PropertyValueFactory<>("id_avion"));
			metodosSQL.cargarHangaresAdministracion(tabla_Hangares);

			//A�ADIMOS ESCUCHADOR A LA TABLA DE HANGARES
	    	this.tabla_Hangares.addEventFilter(MouseEvent.MOUSE_CLICKED,e ->{
	    		int pos = tabla_Hangares.getSelectionModel().getSelectedIndex();
	    		txt_codigoHangarEliminar.setText(tabla_Hangares.getItems().get(pos).getCodigo());
				txt_ubicacionHangarModifi.setText(tabla_Hangares.getItems().get(pos).getUbicacion());
				txt_capacidadHangarModifi.setText(tabla_Hangares.getItems().get(pos).getCapacidad());
				txt_tarifaHangarModificar.setText(tabla_Hangares.getItems().get(pos).getTarifa());
					
	    	});
		
            
            
	    	//----------------------------------------------------------------------------------------
	    	//CERRAR SESION DE AEROLINEA
	    	 btn_cerrarSesionPanePrinciAero.setOnAction(e -> {
				 this.panelLogin.setVisible(true);
				 this.panelPrincipalAerolineas.setVisible(false);
				 this.panel_welcome.setVisible(true);
				
			 });
	    	 //CERRAR FORMULARIO DE REGISTRO DE NUEVO USUARIO
	    	 btn_cancelFormuRegistroAero.setOnAction(e -> {
	    		 this.panelRegistro.setVisible(false);
	    		 this.panel_welcome.setVisible(true);
	    	     this.panelLogin.setScaleX(1);
	    	     this.panelLogin.setScaleY(1);
	    	     
	    	     ocultarErrores();
	    	     TranslateTransition slide = new TranslateTransition();
	             slide.setDuration(Duration.seconds(0.7));
	             slide.setNode(panelLogin);
	             slide.setToX(0);
	             slide.play();
	             TranslateTransition slide1 = new TranslateTransition();
	             slide1.setDuration(Duration.seconds(0.7));
	             slide1.setNode(panelRegistro);
	             slide1.setToX(0);
	             slide1.play();
	    	 });
	    	 panelPrincipalAerolineas();
	    	 btn_regisFormu.setOnAction(ev -> {
	    		 registrarPiloto();
	    		 
	    	 });
	    	 btn_registrarFormuAvion.setOnAction(e->{
	    		 registrarAvion();
	    	 });
	    	 //TOMAMOS ULTIMO IDVUELOS DE LA AGENDA DE OPERACIONES
	    	 idVuelo = metodosSQL.buscarUltimoRegistroVuelos();
	    	
	    	 
	    	 
	    	 //-----------------------------------------------------------------------------------
	    	 //CARGANDO VUELOS
	    	 this.vbox_contenedorVuelos.setSpacing(10);
	    	 //CREAMOS NODO PARA VUELO
	    	 metodosSQL.cargarAgendaDeOperacion(vbox_contenedorVuelos,0,null,null,null);
	    	 
	    	 //COLOCCAR ICONO DE SOLICITUD NUEVA EN ADMINISTRACION
	    	 boolean hay = metodosSQL.buscarSolicitudRegistroNueva();
	    	 if(hay) {
	    		 this.imagenSolicitudNueva.setVisible(true);
	    	 }else {
	    		 this.imagenSolicitudNueva.setVisible(false);
	    	 }
	    	 //METODOS DE ANIMACION PANEL PRINCIPAL AEROLINEAS
	    	 animarPanelprincipalAerolienas(btn_registrarPiloto,panelBtnRegistrarPiloto);
	    	 animarPanelprincipalAerolienas(btn_registrarAvion,panelBtnRegistrarAvion);
	    	 animarPanelprincipalAerolienas(btn_vuelosMenuAerolineas,panelBtnVuelos);
	    	 animarPanelprincipalAerolienas(btn_reportesMenuAerolineas,panelBtnReportes);
	    	 animarPanelprincipalAerolienas(btn_HangaresMenuAerolineas,panelBtnHangares);
	    	 animarPanelprincipalAerolienas(btn_pagosMenuAerolineas,panelBtnPagoSeguro);
	    	 
			 //METODOS DE ANIMACION PANEL PRINCIPAL ADMINISTRACION
			 animarPanelprincipalAdministracion(btn_menuAdministracionHangaresAerolienas);
			 animarPanelprincipalAdministracion(btn_menuReportesAdministracion);
			 animarPanelprincipalAdministracion(btn_menuSolicitudesDeRegistro);
			 animarPanelprincipalAdministracion(btn_cerrarSesionAdministracion);
			
			 //----------------------------------------------------------------
			 //MOVER LA VENTANA DE NOTIFICACIONES POR DEFECTO
		 	 this.notificacionesAeroliena.setLayoutY(1000);
			 //-----------------------------------------------------------------
			 this.primera = 1;

	    }
	    //CERRAR SESION DE ADMINSTRADOR
	@FXML void cerrarSesion(ActionEvent event) {
	    	 this.panelPrincipalAdmin.setVisible(false);
	    	 this.panelLogin.setVisible(true);
			 this.panel_welcome.setVisible(true);
	     }
	@FXML void cerrarSesionAdminitracionHangares(ActionEvent event){
		     this.panel_menuPrinicipalHangares.setVisible(false);
	    	 this.panelLogin.setVisible(true);
			 this.panel_welcome.setVisible(true);
	}
	    //OCULTAR ICONOS DE LA PANTALLA INICIAL DEL SISTEMA
	public void ocultarIconosPanelPrincipal() {
			 this.panel_welcome.setVisible(false);
	    }
        
	    //INGRESAR AL SISTEMA EN GENERAL:  COMO AEROLINEA O ADMINISTRADOR, ADMINISTRADOR DE HANGARES
	@FXML void ingresar(ActionEvent event) {
	
		   try {
			 String tipoUsuario = cbx_orgaizacion.getSelectionModel().getSelectedItem().toString();
			 String usur = txt_usuario.getText();
			 String pass = txt_contrasena.getText();
			 //----------------------------------------------------------------------------------------------------------
			 //INGRESAR AL SISTEMA COMO ADMINISTRADOR
		     if(tipoUsuario.equals("Administrador")) {
				 boolean esta = metodosSQL.iniciarSesion(usur,pass,tipoUsuario);

				 if(esta) {
		
					 this.panelLogin.setVisible(false);
					 this.panelPrincipalAdmin.setVisible(true);
					 ocultarIconosPanelPrincipal();  
					 mostrarTodosLosVuleos(event);
				 }else{				 
					 // Notificacion notificacion = new Notificacion("El usuario o la contrasena no son correctos", 2);
					     Alert alert = new Alert(AlertType.ERROR);
				 		 alert.setHeaderText("Usuario o contraseña invalidos");
				 		 alert.show();
				 }
		     }
		     //----------------------------------------------------------------------------------------------------
		     //INGRESAR AL SISTEMA COMO AERLINEA
		     if(tipoUsuario.equals("Aerolinea")) {
				 boolean esta = metodosSQL.iniciarSesionAerolinea(usur,pass,tipoUsuario);
				 System.out.println(esta);
				 if(esta) {
				
					 this.panelLogin.setVisible(false);
					 this.panelPrincipalAerolineas.setVisible(true);
					 ocultarIconosPanelPrincipal();
					 this.IDaerolinea = metodosSQL.buscarIDaerolinea(usur);
					 this.text_nombreAerolinea.setText(usur);
				
				 }else{
					 //Notificacion notificacion = new Notificacion("El usuario o la contrasena no son correctos", 2);
					 Alert alert = new Alert(AlertType.ERROR);
					 alert.setHeaderText("Usuario o contraseña invalidos");
					 alert.show();
				 }
		     }
		     //--------------------------------------------------------------------------------------------------------
			 //INGRESAR AL SISTEMA COMO ADMINISTRADOR DE HANGARES
			 if(tipoUsuario.equals("Administrador Hangares")) {
				boolean esta = metodosSQL.iniciarSesionAdministradorHangares(usur,pass,tipoUsuario);
				System.out.println(esta);
				if(esta) {
			   
					this.panelLogin.setVisible(false);
					this.panel_menuPrinicipalHangares.setVisible(true);
					ocultarIconosPanelPrincipal();
			   
				}else{
					//Notificacion notificacion = new Notificacion("El usuario o la contrasena no son correctos", 2);
					Alert alert = new Alert(AlertType.ERROR);
					alert.setHeaderText("Usuario o contraseña invalidos");
					alert.show();
				}
			}
			//--------------------------------------------------------------------------------------------------------
		   }catch (Exception e) {
				 Alert alert = new Alert(AlertType.ERROR);
				 alert.setHeaderText("Informacion Incompleta para ingresar al sistema");
				 alert.show();
		   }
		
	 }
	   //--------------------------------------------------------------
	   //METODO PARA MOSTRAR EL FORMULARIO DE REGISTRO DE NUEVO USUARIO
    @FXML
    void registrar(MouseEvent event) {
    	   TranslateTransition slide = new TranslateTransition();
           slide.setDuration(Duration.seconds(0.7));
           slide.setNode(panelLogin);
           slide.setToX(-300);
           slide.play();
           
           TranslateTransition slide1 = new TranslateTransition();
           slide1.setDuration(Duration.seconds(0.7));
           slide1.setNode(panelRegistro);
           slide1.setToX(240);
           slide1.play();
           
           
	     	panelRegistro.setVisible(true);
			panel_welcome.setVisible(false);
	    	ocultarIconosPanelPrincipal();
	        this.panelLogin.setScaleX(0.9);
	        this.panelLogin.setScaleY(0.9);
	    	
      }
      //----------------------------------------------------------------
      // ENVIAR UNA SOLICITUD DE REGISTRO AL SISTEMA
    @FXML void enviarSolicitud(ActionEvent event) {
    	
	    	String idAero =  this.txt_idAerolinea.getText();
	    	String nombreAero = this.txt_nombreAeroliena.getText();
	    	String email = this.txt_emailAerolinea.getText();
	    	String usuario = this.txt_usuarAerolinea.getText();
	    	String contrasena1 = this.txt_contra1Aerolinea.getText();
	    	String contrasena2 = this.txt_contra2Aerolinea.getText();
	    	//----------------------------------------------------------
	    	//VALIDACION PARA QUE LOS CAMPOS DEL FORMULARIO NO QUEDEN VACIOS
	    	if(idAero.equals("")) {
	    		this.lb_errorID.setVisible(true);
	    	}if(nombreAero.equals("")){
	    		this.lb_errorNombre.setVisible(true);
	    	}if(email.equals("")){
	    		this.lb_errorEmail.setVisible(true);
	    	}if(usuario.equals("")){
	    		this.lb_errorUsuario.setVisible(true);
	    	}if(contrasena1.equals("")){
	    		this.lb_errorContra1.setVisible(true);
	    	}if(contrasena2.equals("")){
	    		this.lb_errorContra2.setVisible(true);
	    	}
	    	//-------------------------------------------------------------
	    	else if(contrasena1.equals(contrasena2)){
	    		//ENVIANDO NUEVA SOLICITUD DE REGISTRO
	    		metodosSQL.registrarSolicitudDeUsuario(idAero,nombreAero, usuario, contrasena1, email,"DESACTIVADO",1);
			
	    		ocultarErrores();
	    		limpiar();
	    	   	panelRegistro.setVisible(false);
	        	panelLogin.setVisible(true);
	        	panel_welcome.setVisible(true);
	        	  
	        	     TranslateTransition slide = new TranslateTransition();
		             slide.setDuration(Duration.seconds(0.7));
		             slide.setNode(panelLogin);
		             slide.setToX(0);
		             slide.play();
		             TranslateTransition slide1 = new TranslateTransition();
		             slide1.setDuration(Duration.seconds(0.7));
		             slide1.setNode(panelRegistro);
		             slide1.setToX(0);
		             slide1.play();
	    	}else {
	    		//SI LAS CONTRASE�AN NO COINSIDEN MOSTRAMOS ERRRORES
	    		lb_errorContrasDiferentes.setVisible(true);
	    		lb_errorContra1.setVisible(false);
	    		lb_errorContra2.setVisible(false);	
	    	}
	        this.panelLogin.setScaleX(1);
	        this.panelLogin.setScaleY(1);  
    }

   // SI TOD MARCHA BIEN OCULTAMOS ERRORES
    public void ocultarErrores () {
    	this.lb_errorContra1.setVisible(false);
    	this.lb_errorContra2.setVisible(false);
    	this.lb_errorEmail.setVisible(false);
    	this.lb_errorID.setVisible(false);
    	this.lb_errorNombre.setVisible(false);
    	this.lb_errorUsuario.setVisible(false);
    	this.lb_errorContrasDiferentes.setVisible(false);
    	
    }
    // LIMPIA LOS CAMPOS DEL FORMULARIO
    public void limpiar() {
    	this.txt_contra1Aerolinea.clear();
    	this.txt_contra2Aerolinea.clear();
    	this.txt_emailAerolinea.clear();
    	this.txt_nombreAeroliena.clear();
    	this.txt_usuarAerolinea.clear();
    	this.txt_idAerolinea.clear();
    
    }
    // LIMPIAR FORMULARIO DE REGISTRO PILOTO
    public void limpiarFormularioPiloto() {
    	this.txt_nomFormuPlito.clear();
    	this.txt_apellFormuPlito.clear();
    	this.txt_ceduFormuPlito.clear();
    	this.txt_hrsVueFormuPlito.clear();
    	this.txt_revisMedicaFormuPlito.setValue(null);
    	this.txt_licenFormuPlito.clear();
    }
    //LIMPIAR FORMULARIO DE REGISTRO AVION
    public void limpiarFormularioAvion() {
    	this.txt_idAvion.clear();
    	this.txt_modeloAvion.clear();
    	this.txt_capacidadAvion.clear();
    	this.cbx_tipoPropulsionAvion.setValue(null);;
    	this.txt_pesoNominalAvion.clear();
    	this.cbx_numMotoresAvion.setValue(null);;
    }
    //-----------------------------------------------------------------------------------------------------------------------
    //   �P A N E L   P R I N C I P A L   A D M I N I S T R A D O R E S
    @FXML private Label lb_solicitudesRegistro;
    @FXML private Label lb_reportes;
    @FXML private Label lb_adminHangares;
    @FXML private AnchorPane panelPrincipalAdmin;
    @FXML private AnchorPane panel_reportesAdministracion, panelReporteVuelos,panel_reporteHangares,panel_reporteFinaciero,panel_reportePIlotos,panel_reporteAviones,panel_reporteAerolineas;
    @FXML private AnchorPane panel_administracionHangares;
	@FXML private AnchorPane panelSolicitudRegistros,notificacionesAeroliena;
	@FXML private VBox vbox_contenedorVuelos,panel_ContenedorDeHangares;
	@FXML private TableView<Piloto> tabla_pilotosRegistrados;
	@FXML private TableView<Avion> tabla_avionesRegistrados;
	@FXML private TableView<Usuario> tabla_aerolienasREgistradas;
	@FXML private TableColumn columIdPilotoAdmin,columNombrePilotoAdmin,columAerolineaPilotoAdmin;
	@FXML private TableColumn columnaIDAvionAdmin,columnaTipoAvionAdmin,columnaModeloAvionAdmin,columnaAerolineaAvionAdmin;
	@FXML private TableColumn columnaIDAeroAdmin,columnaNombreAeroAdmin,columnaUsuarioAeroAdmin,columnaContrasenaAeroAdmin,columnaRolAeroAdmin;
	@FXML private TextField id_vueloFiltrarAdmin,aerolineaFiltrarAdmin,txt_idAerolineaActiDesacEliminar;
	@FXML private DatePicker fechaVueloFiltrarAdmin;
	@FXML private ImageView imagenSolicitudNueva;
	@FXML private Button btn_menuReportesAdministracion,btn_menuSolicitudesDeRegistro,btn_menuAdministracionHangaresAerolienas,btn_cerrarSesionAdministracion;
    @FXML private Rectangle cursor_pnPrinciAdmin;
	@FXML private TextField txt_codigoHangar,txt_ubicacionHangar,txt_capacidadHangar;
	@FXML private TableView<Hangar> tabla_reportesHangar;
	@FXML private TableColumn colum_codigoRH;
	@FXML private TableColumn colum_UbicacionRH;
	@FXML private TableColumn colum_capacidadRH;
	@FXML private TableColumn colum_estadoRH;
	@FXML private TableColumn colum_idAvionRH;
	@FXML private TableColumn colum_aerolineaRH;
	@FXML private TextField id_usuario_del_sistema,nombre_usuario_del_sistema,usuario_usuario_del_sistema,contrasena_usuario_del_sistema;
     
	//--------------------------------------------------------------------
	//METODOS DE ADMINISTRACION 
	@FXML void menuAdministracionHangaresAerolienas(ActionEvent event) {
		//REALIZAMOS CAMBIOS DE VENTANAS ENTRE LOS 3 MENUS PRINCIPALES
		this.panel_administracionHangares.setVisible(true);
		this.panel_reportesAdministracion.setVisible(false);
		this.panelSolicitudRegistros.setVisible(false);
		//------------------------------------------------------------------------------	
		animarCursorPanelPrinciAdminitracion(342);
	}
	@FXML void menuSolicitudesDeRegistro(ActionEvent event) {
		//REALIZAMOS CAMBIOS DE VENTANAS ENTRE LOS 3 MENUS PRINCIPALES
		this.panel_administracionHangares.setVisible(false);
		this.panel_reportesAdministracion.setVisible(false);
		this.panelSolicitudRegistros.setVisible(true);
		//-----------------------------------------------------------
		//LIMPIAMOS LA TABLA DE SOLICITUDES DE REGISTRO Y LAS VOLVEMOS A CARGAR
		this.tabla_usuarios.getItems().clear();
		this.metodosSQL.cargarDatosUusuarios(tabla_usuarios);
		//COLOCCAR ICONO DE SOLICITUD NUEVA EN ADMINISTRACION
   	     boolean hay = metodosSQL.buscarSolicitudRegistroNueva();
   	    if(hay) {
   	 	    this.imagenSolicitudNueva.setVisible(true);
   	    }else {
   		    this.imagenSolicitudNueva.setVisible(false);
   	     }
		//----------------------------------------------------------------------
		animarCursorPanelPrinciAdminitracion(160);
		
	}
	@FXML void menuReportesAdministracion(ActionEvent event) {
		//REALIZAMOS CAMBIOS DE VENTANAS ENTRE LOS 3 MENUS PRINCIPALES
		this.panel_administracionHangares.setVisible(false);
		this.panel_reportesAdministracion.setVisible(true);
		this.panelSolicitudRegistros.setVisible(false);
		//-----------------------------------------------------------	
		this.vbox_contenedorVuelos.getChildren().clear();
		this.metodosSQL.cargarAgendaDeOperacion(vbox_contenedorVuelos,0,null,null,null);
		animarCursorPanelPrinciAdminitracion(14);
	}
	@FXML void mostrarPanelReporteVuelos(ActionEvent event) {
		//CONFIGURAMOS EL CAMBIO ENTRE LOS PANELES DE REPORTES ADMINISTRACION
		this.panelReporteVuelos.setVisible(true);
		this.panel_reporteHangares.setVisible(false);
		this.panel_reporteFinaciero.setVisible(false);
		this.panel_reportePIlotos.setVisible(false);
		this.panel_reporteAviones.setVisible(false);
		this.panel_reporteAerolineas.setVisible(false);
		
		//LIMPIAMOS LA AGENDA DE OPERACIONES Y VOLVEMOS A CARGAR LA INFORMACION
		//CARGANDO VUELOS
   	    this.vbox_contenedorVuelos.setSpacing(10);
   	    this.vbox_contenedorVuelos.getChildren().clear();
   	     metodosSQL.cargarAgendaDeOperacion(vbox_contenedorVuelos,0,null,null,null);
		
	}
	@FXML void mostrarPanelReporteHangares(ActionEvent event) {
		//CONFIGURAMOS EL CAMBIO ENTRE LOS PANELES DE REPORTES ADMINISTRACION
		this.panelReporteVuelos.setVisible(false);
		this.panel_reporteHangares.setVisible(true);
		this.panel_reporteFinaciero.setVisible(false);
		this.panel_reportePIlotos.setVisible(false);
		this.panel_reporteAviones.setVisible(false);
		this.panel_reporteAerolineas.setVisible(false);
        
		//LIMPIAMOS LA TABLA DE HANGARES Y VOLVEMOS A CARGAR LA Informacion
	    this.tabla_reportesHangar.getItems().clear();
		this.metodosSQL.cargarHangaresAdministracion(tabla_reportesHangar);
	}
	@FXML void mostrarPanelReporteFinanciero(ActionEvent event) {
		//CONFIGURAMOS EL CAMBIO ENTRE LOS PANELES DE REPORTES ADMINISTRACION
		this.panelReporteVuelos.setVisible(false);
		this.panel_reporteHangares.setVisible(false);
		this.panel_reporteFinaciero.setVisible(true);
		this.panel_reportePIlotos.setVisible(false);
		this.panel_reporteAviones.setVisible(false);
		this.panel_reporteAerolineas.setVisible(false);
	}
	@FXML void mostrarPanelReportePilotos(ActionEvent event) {
		//CONFIGURAMOS EL CAMBIO ENTRE LOS PANELES DE REPORTES ADMINISTRACION
		this.panelReporteVuelos.setVisible(false);
		this.panel_reporteHangares.setVisible(false);
		this.panel_reporteFinaciero.setVisible(false);
		this.panel_reportePIlotos.setVisible(true);
		this.panel_reporteAviones.setVisible(false);
		this.panel_reporteAerolineas.setVisible(false);
		//-----------------------------------------------------------------
		//LIMPIAMOS LA TABLA DE PILOTOS  Y VOLVEMOS A CARGAR LA INFORMACION EN ADMINISTRACION
		this.tabla_pilotosRegistrados.getItems().clear();
		this.metodosSQL.cargarPilotosAdministracion(tabla_pilotosRegistrados);
		//------------------------------------------------------------------------------
	}
	@FXML void mostrarPanelReporteAviones(ActionEvent event) {
		//CONFIGURAMOS EL CAMBIO ENTRE LOS PANELES DE REPORTES ADMINISTRACION
		this.panelReporteVuelos.setVisible(false);
		this.panel_reporteHangares.setVisible(false);
		this.panel_reporteFinaciero.setVisible(false);
		this.panel_reportePIlotos.setVisible(false);
		this.panel_reporteAviones.setVisible(true);
		this.panel_reporteAerolineas.setVisible(false);
		//---------------------------------------------------------------------------------
		//LIMPIAMOS LA TABLA DE AVIONES  Y VOLVEMOS A CARGAR LA INFORMACION EN ADMINISTRACION
		this.tabla_avionesRegistrados.getItems().clear();
		this.metodosSQL.cargarAvionesAdministracion(tabla_avionesRegistrados);
		//----------------------------------------------------------------------------------
		
	}
	@FXML void mostrarPanelReporteAerolineas(ActionEvent event) {
		//CONFIGURAMOS EL CAMBIO ENTRE LOS PANELES DE REPORTES ADMINISTRACION
		this.panelReporteVuelos.setVisible(false);
		this.panel_reporteHangares.setVisible(false);
		this.panel_reporteFinaciero.setVisible(false);
		this.panel_reportePIlotos.setVisible(false);
		this.panel_reporteAviones.setVisible(false);
		this.panel_reporteAerolineas.setVisible(true);
		//LIMPIAMOS LA TABLA DE REPORTES DE AEROLINEAS Y VOLVEMOS A CARGAR LA INFORMACION
	    this.tabla_aerolienasREgistradas.getItems().clear();
		metodosSQL.cargarAerolineasAdministracion(tabla_aerolienasREgistradas);
		//-------------------------------------------------------------------------------
	}
	@FXML void filtrarVuelos(ActionEvent event) {
		   
		    String fechaVueloFiltrar = "";
			String idVueloFiltrar = id_vueloFiltrarAdmin.getText();
			String aerolienaVueloFiltrar = aerolineaFiltrarAdmin.getText();
			String id_aero = metodosSQL.tomarIDDeAerolineaConNombreAeroliena(aerolienaVueloFiltrar);
			try {
				fechaVueloFiltrar = fechaVueloFiltrarAdmin.getValue().toString();
			}catch (Exception e) {
				
			}
		
			//VALIDACION FILTRO TIPO 1 : FILTRO CON IDvUELO
			if(!idVueloFiltrar.isEmpty() && aerolienaVueloFiltrar.isEmpty() && fechaVueloFiltrar.isEmpty()) {
				//VALIDACION SI EL IDVUELO EXISTE
				boolean esta = this.metodosSQL.buscarIdVuelo(idVueloFiltrar);
				if(esta) {
					this.vbox_contenedorVuelos.getChildren().clear();
					this.metodosSQL.cargarAgendaDeOperacion(vbox_contenedorVuelos,1, idVueloFiltrar,null,null);
				}else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("No se encuentra un vuelo con la ID registrada");
					alert.show();
				}
			}
			//VALIDACION FILTRO TIPO 2: FILTRO CON NOMBRE AEROLINEA
			if(idVueloFiltrar.isEmpty() && !aerolienaVueloFiltrar.isEmpty() && fechaVueloFiltrar.isEmpty()) {
				//VALIDACION SI EL IDVUELO EXISTE
				boolean esta = this.metodosSQL.buscarIDAerolienaEnVuelos(id_aero);
				if(esta) {
					this.vbox_contenedorVuelos.getChildren().clear();
					this.metodosSQL.cargarAgendaDeOperacion(vbox_contenedorVuelos,2,null,id_aero,null);
				}else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("No se encuentra un vuelos con Aerolinea registrada");
					alert.show();
				}
			}
			//VALIDACION FILTRO TIPO 3: FILTRO CON NOMBRE AEROLINEA
			if(idVueloFiltrar.isEmpty() && aerolienaVueloFiltrar.isEmpty() && !fechaVueloFiltrar.isEmpty()) {
				//VALIDACION SI EL IDVUELO EXISTE
				boolean esta = this.metodosSQL.buscarfechaEnVuelos(fechaVueloFiltrar);
				if(esta) {
					this.vbox_contenedorVuelos.getChildren().clear();
					this.metodosSQL.cargarAgendaDeOperacion(vbox_contenedorVuelos,3,null,null,fechaVueloFiltrar);
				}else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("No hay vuelos en la fecha registrada");
					alert.show();
				}
			}
			//VALIDACION FILTRO TIPO 4: FILTRO CON NOMBRE AEROLINEA Y FECHA
			if(idVueloFiltrar.isEmpty() && !aerolienaVueloFiltrar.isEmpty() && !fechaVueloFiltrar.isEmpty()) {
				//VALIDACION SI EL IDVUELO EXISTE
				boolean esta = this.metodosSQL.buscarFechaYAerolineaVuelos(fechaVueloFiltrar,id_aero);
				if(esta) {
					this.vbox_contenedorVuelos.getChildren().clear();
					this.metodosSQL.cargarAgendaDeOperacion(vbox_contenedorVuelos,4,null,id_aero,fechaVueloFiltrar);
				}else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setContentText("No hay vuelos  con el filtro registrado");
					alert.show();
				}
			}
			
	}
	@FXML void limpiarCamposDeFiltro(ActionEvent event) {
		 this.id_vueloFiltrarAdmin.clear();
		 this.aerolineaFiltrarAdmin.clear();
		 this.fechaVueloFiltrarAdmin.setValue(null);
	}
	@FXML void mostrarTodosLosVuleos(ActionEvent event) {
		this.vbox_contenedorVuelos.getChildren().clear();
		this.metodosSQL.cargarAgendaDeOperacion(vbox_contenedorVuelos,0,null,null,null);
	}
	@FXML void cancelarVueloDesdeAdministracion(ActionEvent event) {
        try {
			HBox notiCancelVuelo = FXMLLoader.load(getClass().getResource("/vista/noti_vueloCancelado.fxml"));
			this.panel_notificacionesAerolinea.getChildren().add(notiCancelVuelo);
		} catch (IOException e1) {
			
		}
		//----------------------------------------------------------------
		String id_vuelo   = id_vueloFiltrarAdmin.getText();
		String aeroliena  = aerolineaFiltrarAdmin.getText();
		String id_aeroliena = this.metodosSQL.tomarIDDeAerolineaConNombreAeroliena(aeroliena);
		String fecha = "";
		try {
			fecha = fechaVueloFiltrarAdmin.getValue().toString();
		}catch (Exception e) {}
		
		//CANCELAR VUELO TIPO 1 CON ID DE VUELO
		if(!id_vuelo.isEmpty() && aeroliena.isEmpty() && fecha.isEmpty()) {
			this.metodosSQL.cancelarVueloDesdeAdministracion(1, id_vuelo,null,null);
			mostrarTodosLosVuleos(event);
			
		//CANCELAR VUELO TIPO 2 CON NOBBRE DE AEROLIENA
		}if(id_vuelo.isEmpty() && !aeroliena.isEmpty() && fecha.isEmpty()) {
			this.metodosSQL.cancelarVueloDesdeAdministracion(2,null,id_aeroliena,null);
			mostrarTodosLosVuleos(event);
			
		//CANCELAR VUELO TIPO 3 CON FECHA
		}if(id_vuelo.isEmpty() && aeroliena.isEmpty() && !fecha.isEmpty()) {
			this.metodosSQL.cancelarVueloDesdeAdministracion(3, null,null,fecha);
			mostrarTodosLosVuleos(event);
		     
		}
		
		//
		
	}
	@FXML void eliminarUsuario(ActionEvent event) {
		String id_aerolinea = txt_idAerolineaActiDesacEliminar.getText();
		this.metodosSQL.eliminarUsuario(tabla_usuarios, id_aerolinea);
	}
	@FXML void activarUsuario(ActionEvent event) {
		String id_aerolinea = txt_idAerolineaActiDesacEliminar.getText();
		if(id_aerolinea != ""){
			this.metodosSQL.activarUsuario(tabla_usuarios, id_aerolinea);
		}else{
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Selecciona o digita la ID del usuario activar");
			alert.show();
		}
	}
	@FXML void desactivarUsuario(ActionEvent event) {
		String id_aerolinea = txt_idAerolineaActiDesacEliminar.getText();
		this.metodosSQL.inactivarUsuario(tabla_usuarios, id_aerolinea);
	}

	@FXML void buscarAerolineaSolicitud(ActionEvent event){
		String id_aero_buscar = this.txt_idAerolineaActiDesacEliminar.getText();
		if(id_aero_buscar.equals("")){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Digita la id de la aerolinea");
			alert.show();
		}else{
			Solicitud solicitud = this.metodosSQL.buscar_solicitud(id_aero_buscar);
			if(solicitud != null){
				this.txt_nombreAerolineaActiDesacEliminar.setText(solicitud.getNombre());
				this.txt_usuarioAerolineaActiDesacEliminar.setText(solicitud.getUsuario());
				this.txt_contraAerolineaActiDesacEliminar.setText(solicitud.getContrasena());
				this.txt_emailAerolineaActiDesacEliminar.setText(solicitud.getEmail());;
			}else{
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("No se encontro aerolinea con ID registrada");
				alert.show();
			}
		}
	}
	@FXML void buscarUsuarioDesdeAdministracion(ActionEvent event){
		String id = this.id_usuario_del_sistema.getText();
		if(id.equals("")){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Digita la id del usuario");
			alert.show();
		}else{
			Usuario usuario = this.metodosSQL.buscarUsuarioDesdeAdministracion(id);
			if(usuario != null){
				this.id_usuario_del_sistema.setText(usuario.getId());;
				this.nombre_usuario_del_sistema.setText(usuario.getNombre());;
				this.usuario_usuario_del_sistema.setText(usuario.getUsuario());;
				this.contrasena_usuario_del_sistema.setText(usuario.getContrasena());;
			}else{
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("No se encontro aerolinea con ID registrada");
				alert.show();
			}
		}
	}
	@FXML void limpiarCamposReporteUsuarios(ActionEvent event){

		this.id_usuario_del_sistema.clear();
		this.nombre_usuario_del_sistema.clear();
		this.usuario_usuario_del_sistema.clear();
		this.contrasena_usuario_del_sistema.clear();
		
	}
	//CREAMOS NUEVO USUARIO ADMINISTRADOR
	@FXML void crearUsuarioAdministrador (ActionEvent event){

		String id = this.id_usuario_del_sistema.getText();
		String nombre = this.nombre_usuario_del_sistema.getText();
		String usuario = this.usuario_usuario_del_sistema.getText();
		String contrasena = this.contrasena_usuario_del_sistema.getText();

		//VALIDAMOS QUE TODOS LOS DATOS ESTEN COMPLETA
		if(id != "" && nombre != "" && usuario != "" && contrasena != ""){
			this.metodosSQL.registrarNuevoUsuarioAdminitrador(id, nombre, usuario, contrasena);
			this.tabla_aerolienasREgistradas.getItems().clear();
			this.metodosSQL.cargarAerolineasAdministracion(tabla_aerolienasREgistradas);
		}else{
			    Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Existen campos vacios para registrar usuario");
				alert.show();
		}
	}
	//ELIMINAR USUARIO DEL SISTEMA
	@FXML void eliminarUsuarioDelSistema(ActionEvent event){
		String id = this.id_usuario_del_sistema.getText();
		//VALIDAMOS QUE LA ID DEL USUARIO A ELIMINAR SI SE ENCUENTRE
		if(id != ""){
			this.metodosSQL.eliminarUsuarioAdministrador(id);
			this.tabla_aerolienasREgistradas.getItems().clear();
			this.metodosSQL.cargarAerolineasAdministracion(tabla_aerolienasREgistradas);
		}else{
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Selecciona o digita la ID del usuario a eliminar");
			alert.show();
		}
	}

	//**CREAMOS UNA NUEVO USUARIO AEROLINEA DESDE ADMINISTRACION */
	@FXML void crearAerolineaDesdeAdministracion(ActionEvent event){
		String id_aerolinea = this.txt_idAerolineaActiDesacEliminar.getText();
		String nombre = this.txt_nombreAerolineaActiDesacEliminar.getText();
		String usuario =this.txt_usuarioAerolineaActiDesacEliminar.getText();
		String contrasena = this.txt_contraAerolineaActiDesacEliminar.getText();
		String email = this.txt_emailAerolineaActiDesacEliminar.getText();

		//VALIDAMOS QUE TODOS LA INFORMACION ESTE COMPLETA PARA REGISTRAR AEROLINEA DEL
		if(id_aerolinea != "" && nombre != "" && usuario != "" && contrasena != "" && email != ""){
				this.metodosSQL.registrarSolicitudDeUsuario(id_aerolinea, nombre,usuario, contrasena, email,"DESACTIVADO",2);
				this.tabla_usuarios.getItems().clear();
				this.metodosSQL.cargarDatosUusuarios(tabla_usuarios);
		}else{
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Informacion Incompleta para registrar aerolinea");
				alert.show();
		}
		
	}

	//-------------------------------------------------------------------------
	//HANGARES ADMINISTRACION
	@FXML private AnchorPane panel_menuPrinicipalHangares,panelFormularioRegistroHangar,panel_ReservaHangar,panel_FormularioReservarHangar;
	@FXML private TextField codigoHangar,capacidadHangar,ubicacionHangar,tarifaPorHoraHangar,txt_codigoHangarEliminar,txt_ubicacionHangarModifi,
	txt_capacidadHangarModifi,txt_tarifaHangarModificar;
	@FXML private TableView<Hangar> tabla_Hangares;
	@FXML private TableColumn colum_codigoHangar,colum_ubicacionHangar,colum_capacidadHangar,colum_estadoHangar,colum_tarifaHangar,colum_idAvionHangar;

	@FXML void registrarHangar(ActionEvent event){
		
		String codigo = this.codigoHangar.getText();
		String ubicacion = this.ubicacionHangar.getText();
		String capacidad = this.capacidadHangar.getText();
		String tarifa   = this.tarifaPorHoraHangar.getText();

		//VALIDAMOS QUE TODOS LOS CAMPOS ESTEN COMPLETOS
		if(!codigo.equals("")&&!ubicacion.equals("")&&!capacidad.equals("")&&!tarifa.equals("")){
			metodosSQL.registrarNuevoHangar(codigo, ubicacion, capacidad, tarifa);
			this.tabla_Hangares.getItems().clear();
			metodosSQL.cargarHangaresAdministracion(tabla_Hangares);
		}else{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Existen campos vacios");
			alert.show();
		}
	}
	//MOSTRAMOS EL FORMULARIO DE REGISTRO DE HANGAR
	@FXML void mostrarFormuRegistroHangar(ActionEvent e){
		this.panelFormularioRegistroHangar.setVisible(true);
	 	this.panel_menuPrinicipalHangares.setDisable(true);
	}
	//OCULTAMOS EL FORMULARIO DE REGISTRO DE HANGAR
	@FXML void ocultarFormuRegistroHangar(ActionEvent e ){
		this.panelFormularioRegistroHangar.setVisible(false);
		this.panel_menuPrinicipalHangares.setDisable(false);
		this.panel_ReservaHangar.setDisable(false);
	}
	@FXML void mostrarMenuDeReservaDeHangar(ActionEvent e){
		this.panel_ReservaHangar.setVisible(true);
		this.panel_menuPrinicipalHangares.setVisible(false);
	}
	@FXML void adicionarNuevoHangarDesdeReportes(ActionEvent event){
		this.panelFormularioRegistroHangar.setVisible(true);
		this.panel_ReservaHangar.setDisable(true);
	}
	@FXML void eliminarHangar(ActionEvent event){
		String codigo = txt_codigoHangarEliminar.getText();
		if(!codigo.equals("")){
			metodosSQL.eliminarHangar(codigo);
			this.tabla_Hangares.getItems().clear();
			metodosSQL.cargarHangaresAdministracion(tabla_Hangares);
		}else{
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("Digita o selecciona el codigo del hangar a eliminar");
			alert.show();
		}
	}
	@FXML void salirDePanelReservas(ActionEvent event){
		this.panel_ReservaHangar.setVisible(false);
		this.panel_menuPrinicipalHangares.setVisible(true);
	}
	@FXML void modificarInformacionHangar(ActionEvent event){
		String codigo = txt_codigoHangarEliminar.getText();
		if(!codigo.equals("")){
			//VALIDAMOS QUE EL ESTADO DEL HANGAR SEA VACIO
			Hangar hangar = metodosSQL.retornarInformacionHangar(codigo);
			if(hangar.getEstado().equals("Vacio")){

				String ubicacion = txt_ubicacionHangarModifi.getText();
				String capacidad = txt_capacidadHangarModifi.getText();
				String tarifa =    txt_tarifaHangarModificar.getText();

				this.metodosSQL.modificarInformacionHangar(codigo, ubicacion, capacidad, tarifa);
				this.tabla_Hangares.getItems().clear();
			    this.metodosSQL.cargarHangaresAdministracion(tabla_Hangares);

			}else{
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("El hangar se encuentra ocupado");
				alert.setContentText("Para modificar la informacion el hangar debe estar desocupado");
				alert.show();
			}
		}else{
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Digita o selecciona el codigo del hangar a modificar");
			alert.show();
		}
	}
	@FXML void limpiarCamposModificarHangar(ActionEvent event){
				this.txt_codigoHangarEliminar.clear();
		        this.txt_ubicacionHangarModifi.clear();
				this.txt_capacidadHangarModifi.clear();
				this.txt_tarifaHangarModificar.clear();
	}
	//----------------------------------------------------------------------
    //----------------------------------------------------------------------
    // P A N E  L    PRINCIPAL AEROLINEAAS
    public void  panelPrincipalAerolineas() {
    	this.btn_registrarPiloto.setOnMouseClicked(e->{
    		this.panelFormularioPiloto.setVisible(true);
    		this.panelPrincipalAerolineas.setOpacity(0.3);
    		this.panelPrincipalAerolineas.setDisable(true);
    	});
    	this.btn_registrarAvion.setOnMouseClicked(e->{
    		this.panelFormularioAvion.setVisible(true);
    		this.panelPrincipalAerolineas.setOpacity(0.3);
    		this.panelPrincipalAerolineas.setDisable(true);
    	});
    	this.btn_vuelosMenuAerolineas.setOnMouseClicked(e -> {
    		this.panel_vuelosAerolineas.setVisible(true);
    		this.panelPrincipalAerolineas.setVisible(false);
    		//-----------------------------------------------------------------
    		this.tabla_VuelosProgramados.getItems().clear();
    		this.panel_agendarVuelo.setVisible(true);
    	    this.panel_VuelosProgramados.setVisible(false);
	    	this.panelReprogramarVuelo.setVisible(false);
	    	
	    	  //ANOMACION DE DESPLAZAMIENTO DEL PANEL DE CONSULTAR DISPONIBILIDAD DE AGENDA
		     TranslateTransition slide = new TranslateTransition();
	         slide.setDuration(Duration.seconds(0.7));
	         slide.setNode(this.panel_consultarDisponibilidad);
	         slide.setToX(300);
	         slide.play();
	         
	         //ANIMACION PANEL FORMILARIO AGENDAR VUELO
		     TranslateTransition slide1 = new TranslateTransition();
	         slide1.setDuration(Duration.seconds(0.7));
	         slide1.setNode(this.panel_formularioAgendarVuelo);
	         slide1.setToX(-150);
	         slide1.play();
	         this.panel_formularioAgendarVuelo.setOpacity(0.3);
	    	
    	});
    		
    }
	//MOSTRA NOTIFICACIONES
	static int primera;
	@FXML void mostrarNotificaciones(MouseEvent event){
        if(primera % 2 != 0){
			animarPanelDeNotificaciones(-950);
		}else{
			animarPanelDeNotificaciones(1000);
		}

        primera ++;
	}
    //------------------------------------------------------------------------
    //ANIMACIONES Y EFECTO
    //PANEL PRINCIPAL AEROLIENAS
    public void animarPanelprincipalAerolienas(Button btn,Pane pane) {
    	btn.setOnMouseEntered(e->{
    		btn.setMinSize(132,132);
    	});
    	
    	btn.setOnMouseExited(e->{
    		btn.setMinSize(122,122);
    	});
    	pane.setOnMouseEntered(e->{
    		pane.setStyle("-fx-background-radius: 30");
    		pane.setStyle("-fx-border-radius: 30");
    		pane.setStyle("-fx-background-color: green");
    	});
    	pane.setOnMouseExited(e->{
    		pane.setStyle("-fx-background-radius: 30");
    		pane.setStyle("-fx-border-radius: 30");
    		pane.setStyle("-fx-background-color:  #3e5871");
    	});
		
    }
	//PANEL PRINCIPAL ADMINISTRACION
	public void animarPanelprincipalAdministracion(Button button){
		button.setOnMouseEntered(e->{
    		button.setMinSize(103,103);	
    	});
    	
    	button.setOnMouseExited(e->{
    		button.setMinSize(93,93);		
    	});
	}
	//ANIMACION CURSOR PANEL PRINCIPAL ADMINISTRACION
    public void animarCursorPanelPrinciAdminitracion(int pos){
		TranslateTransition slide = new TranslateTransition();
	             slide.setDuration(Duration.seconds(0.5));
	             slide.setNode(this.cursor_pnPrinciAdmin);
	             slide.setToY(pos);
	             slide.play();
	}
	public void animarPanelDeNotificaciones(int pos){
		TranslateTransition slide = new TranslateTransition();
		slide.setDuration(Duration.seconds(0.5));
		slide.setNode(this.notificacionesAeroliena);
		slide.setToY(pos);
		slide.play();
	}
	@FXML void limpiarCamposSolicitudesRegistro(ActionEvent e){
		this.txt_idAerolineaActiDesacEliminar.clear();
		this.txt_nombreAerolineaActiDesacEliminar.clear();
		this.txt_usuarioAerolineaActiDesacEliminar.clear();
		this.txt_contraAerolineaActiDesacEliminar.clear();
		this.txt_emailAerolineaActiDesacEliminar.clear();
		
		
	}

}
