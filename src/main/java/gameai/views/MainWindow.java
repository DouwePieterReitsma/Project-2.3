package gameai.views;

import java.io.IOException;
import java.util.ArrayList;

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
	private TicTacToeView tictac;
	private ConnectionListenerThread connectThread;
	private Button sub;
	private Button player;
	private ArrayList<String> playerList;
	private ArrayList<String> gameList;
	private OthelloView othell;

	public MainWindow(ConnectionListenerThread connectThread){
		this.connectThread = connectThread;
		
		//knopjes waar je de verschillende modes kunt kiezen.
		sub = new Button("Inschrijven");
		player = new Button("Speler");
		Button chal = new Button("Uitdagen");

		// combobox om spellen uit te kiezen, later toevoegen dat je
		// de ondersteunde spellen opvraagd en dan er in zet.
		//ComboBox spel = new ComboBox();
		spel.getItems().addAll("Reversi","Boter kaas en eieren");
	


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
		chal.setOnAction(e -> challenge());

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
		mid.setStyle("-fx-border-color: red");
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
		tictac=new TicTacToeView();
		tictac.createUI(mid);

		mainScene = new Scene(root, 700, 700);
	}

	public Scene GetMainScene() {
		return mainScene;
	}


	public void UpdatePlayerList(ArrayList<String> pList) {
		playerList= pList;
	}

	public void UpdateGameList(ArrayList<String> gList) {
		gameList=gList;
	
	}
	
	

	public void subscribe() throws IOException{
		if(spel.getValue() != null) {
			String game = (String) spel.getValue();
			subscribeCommand = "subscribe "+ game +"";
			System.out.println(subscribeCommand);
			connectThread.subben(subscribeCommand);
		
		}
	}
	public void tegenAi() {
		
		othell = new OthelloView();
		othell.createUI(mid);
		String game = (String) spel.getValue();
		System.out.println(game + " spelen tegen de AI");

	}
	public void challenge() {
		if(spel.getValue() != null) {
			String game = (String) spel.getValue();
			challview = new ChallengeView();
			challview.createUI(mid , game , sub , player);
	}
}
}

