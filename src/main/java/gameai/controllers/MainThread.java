package gameai.controllers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MainThread implements Runnable {
	private ConnectionListenerThread connectThread;

	private ExecutorService threadPool;

	private Timeline verbindAnim;

	private Button loginButton;

	private String host;
	private String username;

	private int port;

	private Label errorLabel;

	private boolean hasConnected;
	private boolean isConnecting;

	public MainThread(ExecutorService threadPool, Label errorLabel, Button loginButton, String host, int port, String name) {
		//Set value
		this.threadPool = threadPool;
		this.errorLabel = errorLabel;
		this.loginButton = loginButton;
		this.host = host;
		this.port = port;
		this.username = name;
		hasConnected = false;
		isConnecting = false;

		//Maak animatie
        verbindAnim = new Timeline(
                new KeyFrame(Duration.ZERO,         event -> errorLabel.setText("Verbinden.")),
                new KeyFrame(Duration.seconds(1), event -> errorLabel.setText("Verbinden..")),
                new KeyFrame(Duration.seconds(2), event -> errorLabel.setText("Verbinden...")),
                new KeyFrame(Duration.seconds(3), event -> errorLabel.setText("Verbinden..."))
            );
    	verbindAnim.setCycleCount(Timeline.INDEFINITE);
	}

	public void run() {
		//Play animation
		verbindAnim.play();

		//Start connection
		isConnecting = true;

		//Create new connection thread
		connectThread = new ConnectionListenerThread(host, port, username);

		//Run the thread
		threadPool.execute(connectThread);

		//Loop
		while(true) {
			if(!hasConnected && isConnecting) {
				try {
					Thread.sleep(100);
					//Check connection status
					if(connectThread.GetConnectStatus() == 1) {
						//Connection failed
						verbindAnim.stop();
						isConnecting = false;
						//Set new text
						Platform.runLater(() -> {
							errorLabel.setText("Verbinding mislukt, check je host & poort.");
							});
						loginButton.setDisable(false);
						threadPool = Executors.newFixedThreadPool(5);
					}
					else if(connectThread.GetConnectStatus() == 2) {
						//Connection succesfull
						verbindAnim.stop();
						hasConnected = true;
						isConnecting = false;

						System.out.println("Success!");
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
