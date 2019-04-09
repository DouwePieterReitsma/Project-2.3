package gameai.views;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;





public class MnWindow extends Application{
	ComboBox spel = new ComboBox();
	@Override
	public void start(Stage primaryStage){
		
		//knopjes waar je de verschillende modes kunt kiezen.
		Button sub = new Button("Inschrijven");
		Button player = new Button("Speler");
		Button chal = new Button("Uitdagen");
		
		// combobox om spellen uit te kiezen, later toevoegen dat je 
		// de ondersteunde spellen opvraagd en dan er in zet.
		//ComboBox spel = new ComboBox();
		spel.getItems().addAll("Reversi","Boter kaas en eieren");
		
		
		//dat er ook iet gebeurt als je op een knopje drukt
		sub.setOnAction(e -> subscribe());
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
		BorderPane mid = new BorderPane();
		//TextField test = new TextField();
		mid.setStyle("-fx-border-color: red");
		//mid.setCenter(test);
		mid.setPrefSize(200, 200);
		
	
				
		
		// hier wordt alles in geplaatst
		BorderPane root = new BorderPane();
		root.setTop(pane);
		//root.setStyle("-fx-border-color: red");
		root.setCenter(mid);
		
		root.setPrefSize(700, 700);
		
		
		
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	public void subscribe() {
		String game = (String) spel.getValue();
		System.out.println("Inschrijven voor " + game);
	}
	public void tegenAi() {
		String game = (String) spel.getValue();
		System.out.println(game + " spelen tegen de AI");
		
	}
	public void challenge() {
		String game = (String) spel.getValue();
		System.out.println("Daag ...... uit om " + game + " te spelen");
	}
	
}