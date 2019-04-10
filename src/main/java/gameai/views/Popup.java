package gameai.views;

import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;


public class Popup {
   
	public static void display() {
		Stage popupwindow=new Stage();
      
		popupwindow.initModality(Modality.APPLICATION_MODAL);
		popupwindow.setTitle("Challenge");
      
      
		Label label1= new Label("blablab daagt je uit voor .....");
		
      
     
		Button button1= new Button("Close this pop up window");
     
     
		button1.setOnAction(e -> popupwindow.close());
     
     
		GridPane layout = new GridPane();
		layout.add(label1, 1, 1);
		layout.add(button1, 2, 2);
	
		Scene scene1= new Scene(layout, 300, 250);
      
		popupwindow.setScene(scene1);
      
		popupwindow.showAndWait();
       
	}

}

