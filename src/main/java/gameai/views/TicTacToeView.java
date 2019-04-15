package gameai.views;

import java.util.ArrayList;
import java.util.List;

import gameai.models.TicTacToeBoard;
import gameai.models.TicTacToeFigure;
import gameai.models.TicTacToePiece;
import gameai.models.othello.OthelloBoard;
import gameai.models.othello.OthelloColor;
import gameai.models.othello.OthelloPiece;
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
	private Label typeLabel;
	private Label turnLabel;
	private Label clientLabel;
	private Label enemyLabel;
	
	private boolean veryFirstTurn;
	private String username;
	private String VS;

	private Image emptyImg;
	private Image playerImg;
	private Image enemyImg;

	private int xRows = 3;
	private int yRows = 3;

	private List<List<Button>> boardList;

	public void createUI(BorderPane parent,String username, String VS, boolean veryFirstTurn) {
		this.gameScene = super.GetGameScene();
		this.titleLabel = super.GetTitleLabel();
		this.gameBoard = super.GetGameBoard();
		this.boardList = super.GetBoardList();
		this.typeLabel= super.GetTypeLabel();
		this.turnLabel = super.GetTurnLabel();
		this.clientLabel =super.GetClientLabel();
		this.enemyLabel = super.GetEnemyLabel();
		this.username = username;
		this.veryFirstTurn = veryFirstTurn;
		this.VS = VS;

		//Set images
		emptyImg = new Image(getClass().getResource("/img/tictactoe/empty.png").toString());
		playerImg = new Image(getClass().getResource("/img/tictactoe/x.png").toString());
		enemyImg = new Image(getClass().getResource("/img/tictactoe/o.png").toString());

		//Set titleLabel
		titleLabel.setText("Tic-Tac-Toe");
		//Set typeLabel
		titleLabel.setText( username +" vs. " + VS );
		
		setXO();

		//Create Board
		CreateBoard();
		parent.setCenter(mainPane);
	}

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

	public void UpdatePositions(TicTacToeBoard tBoard) {
		boardList.clear();
		for(int y = 0; y < yRows; y++) {
			boardList.add(new ArrayList<Button>());
			for(int x = 0; x < xRows; x++) {
				Button column = new Button("");
				column.setStyle("-fx-background-color: transparent; -fx-opacity: 1.0;");
				column.setAlignment(Pos.CENTER);
				column.setTextAlignment(TextAlignment.CENTER);

				if(!tBoard.getPositions()[y][x].isAvailable()) {
					if(((TicTacToePiece)(tBoard.getPositions()[y][x]).getPiece()).getFigure() == TicTacToeFigure.X) { //White start
						ImageView xView = new ImageView(playerImg);
						column.setGraphic(xView);
						column.setDisable(true);
					}
					else if(((TicTacToePiece)(tBoard.getPositions()[y][x]).getPiece()).getFigure() == TicTacToeFigure.O) { //Black start
						ImageView oView = new ImageView(enemyImg);
						column.setGraphic(oView);
						column.setDisable(true);
					}
				}
				else {
					ImageView emptyView = new ImageView(emptyImg);
					column.setGraphic(emptyView);
				}

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
	public void setXO() {
		if (veryFirstTurn) {
			enemyLabel.setText(username+ " : X ");
			clientLabel.setText(VS + " : O ");
		}else {
			enemyLabel.setText(username + " : O ");
			clientLabel.setText(VS + " : X ");
		}
	}
	public void updateTurn(boolean yourTurn) {
		if(yourTurn) {
			turnLabel.setText("Aan de beurt: " + username );
		}else {
			turnLabel.setText("Aan de beurt: " + VS);
		}
	}

	/*Launch application
    public static void main(String[] args) {
        launch(args);
    }*/
}

