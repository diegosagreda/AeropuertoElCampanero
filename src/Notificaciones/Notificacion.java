package Notificaciones;

import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class Notificacion {
    
		public Notificacion(String mensaje,int tipo) {
			/*
			 * TIPO 1: SUCCESS
			 * TIPO 2: ERROR
			 * TIPO 3: INFORMACION
			 * */
			TrayNotification notification = new TrayNotification();
			notification.setAnimationType(AnimationType.POPUP);
			switch (tipo) {
			   case 1:
				   notification.setNotificationType(NotificationType.SUCCESS);
				   break;
			   case 2:
				   notification.setNotificationType(NotificationType.ERROR);
				   break;
			   case 3:
				   notification.setNotificationType(NotificationType.INFORMATION);
				   break;	
			}
			notification.setMessage(mensaje);
			notification.showAndDismiss(Duration.seconds(4));
			
		}
}
