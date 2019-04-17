package gameai.views;

import java.io.IOException;

import java.util.ArrayList;

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
/**
 * 
 * After the user logs in the gets send to the mainwindow, this is the class that is used to create that window.
 * This is also the class that hold all the other views.
 * @author Bram
 *
 */
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
 * creates the actual main window
 * 
 * @param connectThread
 * @param username
 * @throws IOException
 */
	public MainWindow(ConnectionListenerThread connectThread, String username) throws IOException
	{
		this.connectThread = connectThread;
		this.username = username;

		loadingPlayers = true;
		inGame = false;

		//button to choose different modes
		chal = new Button("Uitdagen");
		sub = new Button("Inschrijven");

		// adding games to the combobox
		for(int i = 0; i < connectThread.GetGameList().size(); i++) {
			spel.getItems().add(connectThread.GetGameList().get(i));
		}


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

		// Top pane where all the buttons and the  combobox gets added to
		GridPane pane = new GridPane();
	
		pane.setHgap(30.0);

		pane.add(spel, 0, 0);
		pane.add(sub, 1, 0);
		pane.add(chal, 2, 0);
		

		// middle pane that gets to hold all the games and the challenge view
		mid = new BorderPane();
		

		BorderPane root = new BorderPane();
		root.setTop(pane);

		root.setCenter(mid);
		

		mainScene = new Scene(root, 700, 700);
	}
	/**
	 * this method is used to return the mainScene
	 * @return Scene this return the mainScene of this class 
	 */
	public Scene GetMainScene() {
		return mainScene;
	}
	/**
	 * this method is used to get the value of loadingPlayers. 
	 * @return boolean This returns whether loadingPlayers is true or false
	 */
	public boolean GetLoadingPlayers() {
		return loadingPlayers;
	}
/**
 * This method is used to set the othelloview in borderpane mid
 * @param username Name of the user
 * @param VS Name of the other player
 * @param veryFirstTurn whether or not you have the first turn
 */
	public void SetOthelloView(String username, String VS, boolean veryFirstTurn) {
		Platform.runLater(() -> {
			othell = new OthelloView();
					othell.createUI(mid,username, VS, veryFirstTurn);
					Main.GetMainStage().setTitle("Reversi");
	    });
	}
/**
 * This method used to update the positions, turn and the score.
 * 
 * @param oBoard passes through the Othelloboard oBoard
 * @param yourTurn boolean to see if it is your turn
 */
	public void UpdateOthelloBoard(OthelloBoard oBoard, boolean yourTurn) {
		Platform.runLater(() -> {
			othell.UpdatePositions(oBoard);
			othell.updateTurn(yourTurn);
			othell.UpdateScore(oBoard);
	    });
	}
	/**
	 * This method is used to set the TicTacToeView in borderpane mid
	 * 
	 * @param username name of the user	
	 * @param VS name of the other player
	 * @param veryFirstTurn whether or not you have the first turn
	 */

	public void SetTicTacToeView(String username, String VS, boolean veryFirstTurn) {
		Platform.runLater(() -> {
			tictac = new TicTacToeView();
					tictac.createUI(mid,username, VS, veryFirstTurn);
					Main.GetMainStage().setTitle("TicTacToe");
	    });
	}
/**
 * This method is used to update the positions and the turn
 * @param tBoard 
 * @param yourTurn
 */
	public void UpdateTicTacToeBoard(TicTacToeBoard tBoard, boolean yourTurn) {
		Platform.runLater(() -> {
			tictac.UpdatePositions(tBoard);
			tictac.updateTurn(yourTurn);
	    });
	}
	/**
	 * This method is used to reset the middle pane that holds the games and challengeview
	 */

	public void ResetView() {
		Platform.runLater(() -> {
			Main.GetMainStage().setTitle("Wachtkamer");
			mid = new BorderPane();
			mid.setStyle("-fx-border-color: red");
	    });
	}
	/**
	 * This method is used to get othell
	 * @return OthelloView
	 */

	public OthelloView GetOthelloView() {
		return othell;
	}
	/**
	 * this method is used to create the ChallengeView
	 * @throws IOException
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
 * this method is used to check if you are in a game and disable the buttons
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
	/**
	 * this method is used to subscribe to a game
	 * @throws IOException
	 */

	public void subscribe() throws IOException{
		if(spel.getValue() != null) {
			String game = (String) spel.getValue();
			subscribeCommand = "subscribe "+ game + "\n";
			connectThread.sendCommand(subscribeCommand);
		}
	}
/**
 * this method is used to get the data necessary to create the ChallengeView
 * @throws IOException
 * @throws InterruptedException
 */
	public void challenge() throws IOException, InterruptedException {
		if(spel.getValue() != null) {
			connectThread.GetPlayerList().clear();
			connectThread.UpdatePlayerList();
			chal.setDisable(true);
			loadingPlayers = true;
		}
	}


}