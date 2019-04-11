package gameai.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import gameai.Main;
import gameai.models.othello.OthelloBoard;
import gameai.models.othello.OthelloColor;
import gameai.models.othello.OthelloPiece;
import gameai.models.othello.OthelloPlayer;
import gameai.models.othello.OthelloServerPlayer;
import gameai.models.othello.ai.AlphaBetaOthelloAI;
import gameai.models.othello.ai.OthelloAI;
import gameai.models.othello.ai.RandomOthelloAI;
import gameai.views.*;
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
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainThread implements Runnable {
	private ConnectionListenerThread connectThread;

	private ExecutorService threadPool;

	private MoveController moveController;

	private MainWindow mainWindow;

	private Timeline verbindAnim;

	private Button loginButton;

	private String host;
	private String username;

	private int port;
	private int timer;
	private int state;

	private Label errorLabel;

	private boolean hasConnected;
	private boolean isConnecting;
	private boolean inGame;

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
		inGame = false;

		connectThread = null;

		state = 0; //0 is Login, 1 = main, 2 = game

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
			//Set state
			state = connectThread.GetState();
			//Sleep
			try {
				Thread.sleep(timer);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//State handlers
			if(state == 0) {
				CheckConnection();
			}
			else if(state == 1) {
				timer = 1000;

				try {
					MainMenuHandler();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(state == 2) {
				try {
					GameHandler();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	//Function for checking connection
	private void CheckConnection() {
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
					threadPool = Executors.newFixedThreadPool(6);
				}
				else if(connectThread.GetConnectStatus() == 2) {
					//Connection failed
					verbindAnim.stop();
					isConnecting = false;
					//Set new text
					Platform.runLater(() -> {
						errorLabel.setText("Verbinding mislukt, er is al een speler met deze naam.");
						});
					loginButton.setDisable(false);
					threadPool = Executors.newFixedThreadPool(6);
				}
				else if(connectThread.GetConnectStatus() == 3) {
					//Connection successfull
					verbindAnim.stop();
					hasConnected = true;
					isConnecting = false;
					connectThread.UpdateGameList();
				}
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	//Function to handle main menu
	private void MainMenuHandler() throws IOException, InterruptedException {
		//Create main menu
		if(mainWindow == null && connectThread.GetGameList().size() > 0) {
			mainWindow = new MainWindow(connectThread, username);
			Platform.runLater(new Runnable() {
				@Override public void run() {
					Main.GetMainStage().setScene(mainWindow.GetMainScene());
					Main.GetMainStage().setTitle("Wachtkamer");
	             }});
		}
		else if(mainWindow != null) {
			//Load players
			if(mainWindow.GetLoadingPlayers()) {
				mainWindow.ProcessPlayers();
			}
			if (connectThread.getChallenged()) {
			ArrayList<String> info = connectThread.GetChallengeList();
			Main.runPopup(info.get(0),info.get(1),info.get(2), connectThread);
			connectThread.setChallFalse();
			}
		}
	}

	//Function to handle game menus

	private void GameHandler() throws InterruptedException {
		//Startup game
		if(!inGame) {
			inGame = true;
			switch(connectThread.getGame())
			{
				case "Reversi":

					mainWindow.SetOthelloView();

					System.out.println("asdasdf");

					Thread.sleep(1000);

					OthelloBoard board = new OthelloBoard(OthelloColor.BLACK);

					OthelloPiece wp = new OthelloPiece(OthelloColor.WHITE);
			        OthelloPiece bp = new OthelloPiece(OthelloColor.BLACK);

					board.getPositions()[3][3].setPiece(wp);
					board.getPositions()[4][3].setPiece(bp);
					board.getPositions()[3][4].setPiece(bp);
					board.getPositions()[4][4].setPiece(wp);

					OthelloAI ai = null;
					OthelloServerPlayer opponent = null;

					if(connectThread.getYourTurn()) {
						System.out.println("yourturn");
						ai = new AlphaBetaOthelloAI(board, OthelloColor.BLACK, 6);
						opponent = new OthelloServerPlayer(board, OthelloColor.WHITE, connectThread);
					}
					else {
						System.out.println("notyourturn");
						ai = new AlphaBetaOthelloAI(board, OthelloColor.WHITE, 6);
						opponent = new OthelloServerPlayer(board, OthelloColor.BLACK, connectThread);
					}
					//OthelloAI black = new RandomOthelloAI(board, OthelloColor.BLACK);

					moveController = new MoveController(connectThread, ai, opponent);

					while(!board.isGameOver()) {
						if(connectThread.getYourTurn() && connectThread.GetState() == 2) {
							try {
								moveController.doAIMove();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else if(connectThread.GetState() == 2) {
							Thread.sleep(200);
							//Check enemy movement
							moveController.checkEnemy();
						}
						mainWindow.UpdateOthelloBoard(board);
					}
					break;
				case "Tic-tac-toe":

					break;
			}
		}
	}

	public void writeAccept(String tekst) throws IOException {
		connectThread.sendCommand(tekst);
	}


	//Close application
    public void stop(){
    	threadPool.shutdown();
		while(!threadPool.isTerminated()) {}
		System.out.println("Application succesfully closed!");
	}
}
