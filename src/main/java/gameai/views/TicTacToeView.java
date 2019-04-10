package gameai.views;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class TicTacToeView extends GameBoardView {
	private Scene gameScene;
	private Label titleLabel;
	private GridPane gameBoard;
	

	private Image emptyImg;
	private Image playerImg;
	private Image enemyImg;

	private int xRows = 3;
	private int yRows = 3;

	private List<List<Button>> boardList;

	public void createUI(BorderPane parent) {
		this.gameScene = super.GetGameScene();
		this.titleLabel = super.GetTitleLabel();
		this.gameBoard = super.GetGameBoard();
		this.boardList = super.GetBoardList();

		//Set images
		emptyImg = new Image(getClass().getResource("/img/tictactoe/empty.png").toString());
		playerImg = new Image(getClass().getResource("/img/tictactoe/x.png").toString());
		enemyImg = new Image(getClass().getResource("/img/tictactoe/o.png").toString());

		//Set titleLabel
		titleLabel.setText("Tic-Tac-Toe");

		//Create Board
		CreateBoard();
		parent.setCenter(mainPane);
	}

	/*
    public void start(Stage stage) throws Exception{
		//Set title of stage
		stage.setTitle("TicTacToe");
        stage.setScene(gameScene);
        stage.setResizable(false);
		stage.show();
	}
	*/

	//Function to create gameboard
	private void CreateBoard() {
		//Make columns
		for(int y = 0; y < yRows; y++) {
			boardList.add(new ArrayList<Button>());
			for(int x = 0; x < xRows; x++) {
				Button column = new Button("");
				column.setStyle("-fx-background-color: transparent; -fx-opacity: 1.0;");
				column.setFont(new Font("Arial", 40));
				column.setAlignment(Pos.CENTER);
				column.setTextAlignment(TextAlignment.CENTER);
				column.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				ImageView emptyView = new ImageView(emptyImg);
				column.setGraphic(emptyView);
				gameBoard.add(column, x, y);
				gameBoard.setHgrow(column, Priority.ALWAYS);
				gameBoard.setVgrow(column, Priority.ALWAYS);
				gameBoard.setHalignment(column, HPos.CENTER);
				gameBoard.setValignment(column, VPos.CENTER);
				boardList.get(y).add(column);
				//Set click event
				column.setOnAction((event) -> {
					SetMove(gameBoard.getColumnIndex(column), gameBoard.getRowIndex(column), true);
		        });
			}
		}
	}

	private void SetMove(int columnId, int rowId, boolean isPlayer) {
		//Check if player or not
		if(isPlayer) {
			ImageView playerView = new ImageView(playerImg);
			boardList.get(rowId).get(columnId).setGraphic(playerView);
		}
		else {
			ImageView enemyView = new ImageView(enemyImg);
			boardList.get(rowId).get(columnId).setGraphic(enemyView);
		}
		//Set disabled to true
		boardList.get(rowId).get(columnId).setDisable(true);
	}

	private void CheckIfDone() {

	}

	/*Launch application
    public static void main(String[] args) {
        launch(args);
    }*/
}
