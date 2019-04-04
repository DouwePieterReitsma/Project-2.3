package gameai.views;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public abstract class GameBoardView extends Application {
	private BorderPane mainPane;
	private BorderPane middlePane;
	private GridPane gameBoard;
	private HBox middleTitleBox;
	private Scene gameScene;
	private Label titleLabel;
	private Label typeLabel;
	private Label clientLabel;
	private Label enemyLabel;
	private Label turnLabel;
	private VBox titleBox;

	private List<List<Button>> boardList = new ArrayList<List<Button>>();

	public GameBoardView() {
		//Create border pane
        mainPane = new BorderPane();
        middlePane = new BorderPane();

        //Set gameboard
        gameBoard = new GridPane();
        gameBoard.setAlignment(Pos.CENTER);
        gameBoard.setPadding(new Insets(15));
        gameBoard.setGridLinesVisible(true);

        //Create and set titleLabel
        titleLabel = new Label("");
        titleLabel.setStyle("-fx-font-weight: bold");
        titleLabel.setFont(new Font("Arial", 30));

        //Create typelabel
        typeLabel = new Label("Computer vs. AI");

        //Create turnLabel
        turnLabel = new Label("Aan de beurt: Jij");
        turnLabel.setFont(new Font("Arial", 25));
        turnLabel.setStyle("-fx-font-weight: bold");
        turnLabel.setPadding(new Insets(5, 15, 5, 5));

        //Create client & enemylabels
        clientLabel = new Label("Jij: X");
        clientLabel.setFont(new Font("Arial", 20));
        clientLabel.setStyle("-fx-font-weight: bold");
        enemyLabel = new Label("Tegenstander: O");
        enemyLabel.setFont(new Font("Arial", 20));
        enemyLabel.setStyle("-fx-font-weight: bold");

        //Create vbox
        titleBox = new VBox(2);
        titleBox.getChildren().add(titleLabel);
        titleBox.getChildren().add(typeLabel);
        titleBox.setAlignment(Pos.CENTER);

        //Create hbox
        middleTitleBox = new HBox(100);
        middleTitleBox.getChildren().add(clientLabel);
        middleTitleBox.getChildren().add(enemyLabel);
        middleTitleBox.setAlignment(Pos.CENTER);
        middleTitleBox.setPadding(new Insets(10, 10, 10, 10));

        //Add border around middlepane
        middlePane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        //Add to middlePane
        middlePane.setTop(middleTitleBox);
        middlePane.setCenter(gameBoard);

        //Set alignments of middlepane
        middlePane.setAlignment(middleTitleBox, Pos.TOP_CENTER);
        middlePane.setAlignment(gameBoard, Pos.CENTER);

        //Add to mainPane
        mainPane.setTop(titleBox);
        mainPane.setCenter(middlePane);
        mainPane.setBottom(turnLabel);

        //Set alignments of mainpane
        mainPane.setAlignment(titleBox, Pos.TOP_CENTER);
        mainPane.setAlignment(middlePane, Pos.CENTER);
        mainPane.setAlignment(turnLabel, Pos.BOTTOM_RIGHT);

		//Create new game scene
		gameScene = new Scene(mainPane, 700, 700);
	}

	//Getters
	public Scene GetGameScene() {
		return gameScene;
	}

	public BorderPane GetMiddlePane() {
		return middlePane;
	}

	public GridPane GetGameBoard() {
		return gameBoard;
	}

	public List<List<Button>> GetBoardList() {
		return boardList;
	}

	public Label GetTitleLabel() {
		return titleLabel;
	}

	public Label GetTypeLabel() {
		return typeLabel;
	}

	public Label GetClientLabel() {
		return clientLabel;
	}

	public Label GetEnemyLabel() {
		return enemyLabel;
	}

	public Label GetTurnLabel() {
		return turnLabel;
	}
}
