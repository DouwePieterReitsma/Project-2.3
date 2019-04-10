package gameai.views;

import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;


public class Popup {
	private static String uitdager;
	private static String spel;
	private static String nummer;
	private static Stage popupwindow;
	
   
	public static void display(String player, String game , String id) {
		uitdager = player;
		spel = game;
		nummer = id;
		
		popupwindow=new Stage();
      
		popupwindow.initModality(Modality.APPLICATION_MODAL);
		popupwindow.setTitle("Challenge");
      
      
		Label label1= new Label(uitdager +" daagt je uit om " + spel + " te spelen");
		
      
     
		Button Accept= new Button("Accepteren ");
		Button Decline= new Button("Weigeren");
     
     
		Accept.setOnAction(e ->accept());
		Decline.setOnAction(e -> popupwindow.close());
     
     
		GridPane layout = new GridPane();
		layout.add(label1, 1, 1);
		layout.add(Accept, 2, 2);
		layout.add(Decline, 1, 3);
	
		Scene scene1= new Scene(layout, 300, 250);
      
		popupwindow.setScene(scene1);
      
		popupwindow.showAndWait();
       
	}
	private static void accept() {
		//String send = "challenge accept " + nummer + "/n" ;
		popupwindow.close();
	}

}

