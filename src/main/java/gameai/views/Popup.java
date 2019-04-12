package gameai.views;

import java.io.IOException;

import gameai.controllers.ConnectionListenerThread;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;


public class Popup {
	private String player;
	private String game;
	private String id;
	private Stage popupwindow;
	private ConnectionListenerThread connectThread;

	public void display(String player, String id , String game, ConnectionListenerThread connectThread) {
		this.player = player;
		this.game = game;
		this.id = id;
	    this.connectThread = connectThread;

		popupwindow=new Stage();

		popupwindow.initModality(Modality.APPLICATION_MODAL);
		popupwindow.setTitle("Challenge");


		Label label1= new Label(player +" daagt je uit om " + game + " te spelen");

		Button Accept= new Button("Accepteren ");
		Button Decline= new Button("Weigeren");

		Accept.setOnAction(e ->{
			try {
				accept();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		Decline.setOnAction(e -> popupwindow.close());

		GridPane layout = new GridPane();
		layout.add(label1, 1, 1);
		layout.add(Accept, 2, 2);
		layout.add(Decline, 3, 2);
		layout.setConstraints(label1, 0, 0, 1, 1, HPos.RIGHT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
		layout.setConstraints(Accept, 0, 1, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
		layout.setConstraints(Decline, 1, 1, 1, 1, HPos.RIGHT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
		

		Scene scene1= new Scene(layout, 400, 150);

		popupwindow.setScene(scene1);

		popupwindow.showAndWait();
	}

	private void accept() throws IOException {
		String send = "challenge accept " + id + "/n" ;
		connectThread.sendCommand(send);
		popupwindow.close();
	}


}

