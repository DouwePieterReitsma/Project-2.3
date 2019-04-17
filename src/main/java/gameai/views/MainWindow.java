package gameai.views;

import java.io.IOException;
import java.util.ArrayList;

import com.sun.xml.internal.bind.v2.runtime.Name;

import gameai.controllers.ConnectionListenerThread;
import gameai.models.TicTacToeBoard;
import gameai.models.othello.OthelloBoard;
import javafx.application.Application;
import javafx.application.Platform;
import gameai.Main;
import gameai.controllers.ConnectionListenerThread;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainWindow  {
	private ComboBox spel = new ComboBox();
	private Scene mainScene;
	private String subscribeCommand;
	private BorderPane mid;
	private ChallengeView challview;
	private ConnectionListenerThread connectThread;
	private Button sub;
	private Button chal;
	private TicTacToeView tictac;
	private ArrayList<String> playerList;
	private ArrayList<String> gameList;
	private OthelloView othell;
	private String username;

	private boolean loadingPlayers;
	private boolean inGame;

	/**
	* Constructor for the mainwindow
	* @author David Laan
	* @param connectThread The connectionlistenerthread
	* @param username The name of the client's user
	* @exception IOException
	*/
	public MainWindow(ConnectionListenerThread connectThread, String username) throws IOException
	{
		this.connectThread = connectThread;
		this.username = username;

		loadingPlayers = true;
		inGame = false;

		//knopjes waar je de verschillende modes kunt kiezen.
		chal = new Button("Uitdagen");
		sub = new Button("Inschrijven");

		// combobox om spellen uit te kiezen, later toevoegen dat je
		// de ondersteunde spellen opvraagd en dan er in zet.
		//ComboBox spel = new ComboBox();
		for(int i = 0; i < connectThread.GetGameList().size(); i++) {
			spel.getItems().add(connectThread.GetGameList().get(i));
		}


		//dat er ook iet gebeurt als je op een knopje drukt
		sub.setOnAction(e ->{
			try {
				subscribe();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		chal.setOnAction(e -> {
			try {
				challenge();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		// Bovenste pane waar de knopjes in worden geplaatst
		GridPane pane = new GridPane();
		//pane.setPadding(new Insets(5,5,5,5));
		pane.setHgap(30.0);
		//pane.setStyle("-fx-border-color: red");

		pane.add(spel, 0, 0);
		pane.add(sub, 1, 0);
		pane.add(chal, 2, 0);
		//pane.setLeft(spel);
		//pane.setCenter(player);
		//pane.setCenter(sub);
		//pane.setRight(chal);

		// middelste pane wat de spellen gaat bevatten en de lijst met spelers om te challenge
		mid = new BorderPane();
		//TextField test = new TextField();
		//mid.setCenter(test);
		//mid.setPrefSize(200, 200);

		// hier wordt alles in geplaatst
		BorderPane root = new BorderPane();
		root.setTop(pane);

		//root.setStyle("-fx-border-color: red");
		root.setCenter(mid);
		//challview = new ChallengeView();
		//challview.createUI(mid);
		//othell = new OthelloView();
		//othell.createUI(mid);
		//tictac=new TicTacToeView();
		//tictac.createUI(mid);

		mainScene = new Scene(root, 700, 700);
	}

	/**
	* Returns the mainScene
	* @author David Laan
	* @return Returns the mainScene
	*/
	public Scene GetMainScene() {
		return mainScene;
	}

	/**
	* Returns if the window is loading players
	* @author David Laan
	* @return Returns if the windows is loading players
	*/
	public boolean GetLoadingPlayers() {
		return loadingPlayers;
	}

	/**
	* Sets the view to Reversi
	* @author David Laan
	* @param username Gives the username of the client's user
	* @param VS Gives the opponent's name
	* @param veryFirstTurn
	*/
	public void SetOthelloView(String username, String VS, boolean veryFirstTurn) {
		Platform.runLater(() -> {
			othell = new OthelloView();
					othell.createUI(mid,username, VS, veryFirstTurn);
					Main.GetMainStage().setTitle("Reversi");
	    });
	}

	/**
	* Function to update the Reversi gameboard view
	* @author David Laan
	* @param oBoard The gameboard
	* @param yourTurn Checks if its the player or opponent's turn
	*/
	public void UpdateOthelloBoard(OthelloBoard oBoard, boolean yourTurn) {
		Platform.runLater(() -> {
			othell.UpdatePositions(oBoard);
			othell.updateTurn(yourTurn);
			othell.UpdateScore(oBoard);
	    });
	}

	/**
	* Function to set the view to TicTacToe
	* @author David Laan
	* @param username The client's username
	* @param VS The opponent's name
	* @param veryFirstTurn Checks if its the first turn
	*/
	public void SetTicTacToeView(String username, String VS, boolean veryFirstTurn) {
		Platform.runLater(() -> {
			tictac = new TicTacToeView();
					tictac.createUI(mid,username, VS, veryFirstTurn);
					Main.GetMainStage().setTitle("TicTacToe");
	    });
	}

	/**
	* Constructor for the opponent of Reversi when playing online
	* @author David Laan
	* @param tBoard The gameboard
	* @param yourTurn Checks if its the player's turn or not
	*/
	public void UpdateTicTacToeBoard(TicTacToeBoard tBoard, boolean yourTurn) {
		Platform.runLater(() -> {
			tictac.UpdatePositions(tBoard);
			tictac.updateTurn(yourTurn);
	    });
	}

	/**
	* Function to reset the view
	* @author David Laan
	*/
	public void ResetView() {
		Platform.runLater(() -> {
			Main.GetMainStage().setTitle("Wachtkamer");
			mid = new BorderPane();
			mid.setStyle("-fx-border-color: red");
	    });
	}

	/**
	* Function to return the reversi game view
	* @author David Laan
	* @return Returns the Reversi gameview
	*/
	public OthelloView GetOthelloView() {
		return othell;
	}

	/**
	* Function to process the requested playerlist.
	* @author David Laan
	* @exception IOException
	*/
	public void ProcessPlayers() throws IOException {
		if(loadingPlayers && connectThread.GetPlayerList().size() > 0) {
			loadingPlayers = false;
			chal.setDisable(false);
			String game = (String) spel.getValue();
			challview = new ChallengeView();
			Platform.runLater(() ->
				challview.createUI(connectThread, mid , game , sub , username, connectThread.GetPlayerList())
			);
		}
	}

	/**
	* Function to check if client is ingame
	* @author David Laan
	*/
	public void CheckInGame() {
		if(connectThread.getState() == 2 && !inGame) {
			inGame = true;

			chal.setDisable(true);
			sub.setDisable(true);
			spel.setDisable(true);
		}
		else if (inGame){
			inGame = false;

			chal.setDisable(false);
			sub.setDisable(false);
			spel.setDisable(false);
		}
	}

	public void subscribe() throws IOException{
		if(spel.getValue() != null) {
			String game = (String) spel.getValue();
			subscribeCommand = "subscribe "+ game + "\n";
			connectThread.sendCommand(subscribeCommand);
		}
	}

	public void challenge() throws IOException, InterruptedException {
		if(spel.getValue() != null) {
			connectThread.GetPlayerList().clear();
			connectThread.UpdatePlayerList();
			chal.setDisable(true);
			loadingPlayers = true;
		}
	}


}