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

public class MainWindow  {
	private ComboBox spel = new ComboBox();
	private Scene mainScene;
	private String subscribeCommand;
	private BorderPane mid;
	private ChallengeView challview;
	private ConnectionListenerThread connectThread;
	private Button sub;
	private Button player;
	private Button chal;
	private TicTacToeView tictac;
	private ArrayList<String> playerList;
	private ArrayList<String> gameList;
	private OthelloView othell;
	private String username;

	private boolean loadingPlayers;

	public MainWindow(ConnectionListenerThread connectThread, String username) throws IOException
	{
		this.connectThread = connectThread;
		this.username = username;

		loadingPlayers = true;

		//knopjes waar je de verschillende modes kunt kiezen.
		chal = new Button("Uitdagen");
		sub = new Button("Inschrijven");
		player = new Button("Speler");

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
		player.setOnAction(e -> tegenAi());
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
		pane.add(player, 1, 0);
		pane.add(sub, 2, 0);
		pane.add(chal, 3, 0);
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

	public Scene GetMainScene() {
		return mainScene;
	}

	public boolean GetLoadingPlayers() {
		return loadingPlayers;
	}

	public void SetOthelloView(String username, String VS, boolean veryFirstTurn) {
		Platform.runLater(() -> {
			othell = new OthelloView();
					othell.createUI(mid,username, VS, veryFirstTurn);
					Main.GetMainStage().setTitle("Reversi");
	    });
	}

	public void UpdateOthelloBoard(OthelloBoard oBoard, boolean yourTurn) {
		Platform.runLater(() -> {
			othell.UpdatePositions(oBoard);
			othell.updateTurn(yourTurn);
			othell.UpdateScore(oBoard);
	    });
	}

	public void SetTicTacToeView(String username, String VS, boolean veryFirstTurn) {
		Platform.runLater(() -> {
			tictac = new TicTacToeView();
					tictac.createUI(mid,username, VS, veryFirstTurn);
					Main.GetMainStage().setTitle("TicTacToe");
	    });
	}

	public void UpdateTicTacToeBoard(TicTacToeBoard tBoard, boolean yourTurn) {
		Platform.runLater(() -> {
			tictac.UpdatePositions(tBoard);
			tictac.updateTurn(yourTurn);
	    });
	}

	public void ResetView() {
		Platform.runLater(() -> {
			Main.GetMainStage().setTitle("Wachtkamer");
			mid = new BorderPane();
			mid.setStyle("-fx-border-color: red");
	    });
	}

	public OthelloView GetOthelloView() {
		return othell;
	}

	public void ProcessPlayers() throws IOException {
		if(loadingPlayers && connectThread.GetPlayerList().size() > 0) {
			loadingPlayers = false;
			chal.setDisable(false);
			String game = (String) spel.getValue();
			challview = new ChallengeView();
			Platform.runLater(() ->
				challview.createUI(connectThread, mid , game , sub , player, username, connectThread.GetPlayerList())
			);
		}
	}

	public void subscribe() throws IOException{
		if(spel.getValue() != null) {
			String game = (String) spel.getValue();
			System.out.println("Inschrijven voor " + game);
			subscribeCommand = "subscribe "+ game + "\n";
			System.out.println(subscribeCommand);
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

	public void tegenAi() {
		if(spel.getValue().equals("Reversi")) {
			//othell = new OthelloView();
			//othell.createUI(mid);
			//Main.GetMainStage().setTitle("Othello");
		}
		else {
			//tictac = new TicTacToeView();
			//tictac.createUI(mid);
			//Main.GetMainStage().setTitle("Tic-Tac-Toe");
		}
	}
}