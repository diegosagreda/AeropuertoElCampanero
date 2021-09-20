package modelo;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		Image image = new Image(getClass().getResourceAsStream("/imagenes/icono.png"));
		/*Image image2 = new Image(getClass().getResourceAsStream("/imagenes/nombre.png"));
		
		ImageView icono = new ImageView(image2);
		icono.setFitHeight(30);
		icono.setFitWidth(30);
	    Button button = new Button("");
	    button.setLayoutX(1145);
	    button.setGraphic(icono);
	    button.setStyle("-fx-background-color: NULL");*/
	    
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/vista/Principal.fxml"));
			//root.getChildren().add(button);
			Scene scene = new Scene(root,1185,668);
			scene.setFill(null);
			
			//-------------------------------------------------------
			//SALIR
			//button.setOnAction(e->{primaryStage.close();});
			//-------------------------------------------------------
			
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Aeropuerto El Campanero");
			primaryStage.initStyle(StageStyle.DECORATED);
		    primaryStage.getIcons().add(image);
			primaryStage.show();
			
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
