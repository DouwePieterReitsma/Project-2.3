package gameai.views;

import java.util.ArrayList;
import java.util.List;

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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class OthelloView extends GameBoardView {
	private Scene gameScene;
	private Label titleLabel;
	private Label typeLabel;
	private Label turnLabel;
	private GridPane gameBoard;
	private Label clientLabel;
	private Label enemyLabel;
	private Label clientLabelScore;
	private Label enemyLabelScore;
	
	
	private boolean veryFirstTurn;
	private String username;
	private String VS;
	


	private Image emptyImg;
	private Image whiteImg;
	private Image blackImg;

	private int playerColor = 0; //0 = white, 1 = black

	private int xRows = 8;
	private int yRows = 8;

	private OthelloBoard oBoard;

	private List<List<Button>> boardList;

	public void createUI(BorderPane parent, String username, String VS, boolean veryFirstTurn) {
		this.gameScene = super.GetGameScene();
		this.titleLabel = super.GetTitleLabel();
		this.gameBoard = super.GetGameBoard();
		this.boardList = super.GetBoardList();
		this.typeLabel= super.GetTypeLabel();
		this.turnLabel = super.GetTurnLabel();
		this.clientLabel =super.GetClientLabel();
		this.enemyLabel = super.GetEnemyLabel();
		this.clientLabelScore =super.GetClientLabelScore();
		this.enemyLabelScore = super.GetEnemyLabelScore();
		this.username = username;
		this.veryFirstTurn = veryFirstTurn;
		this.VS = VS;
		//Set images
		emptyImg = new Image(getClass().getResource("/img/othello/empty.png").toString());
		whiteImg = new Image(getClass().getResource("/img/othello/white.png").toString());
		blackImg = new Image(getClass().getResource("/img/othello/black.png").toString());

		//Set titlelabel
		titleLabel.setText("Othello");
		//set typelabel
		titleLabel.setText( username +" vs. " + VS );
		//set client and enemylabel
		setColor();

		//Create board
		CreateBoard();

		parent.setCenter(mainPane);
	}

	//Function to create gameboard
	private void CreateBoard() {
		for(int y = 0; y < yRows; y++) {
			boardList.add(new ArrayList<Button>());
			for(int x = 0; x < xRows; x++) {
				Button column = new Button("");
				column.setStyle("-fx-background-color: transparent; -fx-opacity: 1.0;");
				column.setAlignment(Pos.CENTER);
				column.setTextAlignment(TextAlignment.CENTER);

				//Check if not middle
				if(y == 3 && x == 3 || y == 4 && x == 4) { //White start
					ImageView whiteView = new ImageView(whiteImg);
					column.setGraphic(whiteView);
					column.setDisable(true);
				}
				else if(y == 3 && x == 4 || y == 4 && x == 3) { //Black start
					ImageView blackView = new ImageView(blackImg);
					column.setGraphic(blackView);
					column.setDisable(true);
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

	public void UpdatePositions(OthelloBoard oBoard) {
		for(int y = 0; y < yRows; y++) {
			for(int x = 0; x < xRows; x++) {
				Button column = boardList.get(y).get(x);

				if(!oBoard.getPositions()[y][x].isAvailable()) {
					if(((OthelloPiece)(oBoard.getPositions()[y][x]).getPiece()).getColor() == OthelloColor.WHITE) { //White start
						ImageView whiteView = new ImageView(whiteImg);
						column.setGraphic(whiteView);
						column.setDisable(true);
					}
					else if(((OthelloPiece)(oBoard.getPositions()[y][x]).getPiece()).getColor() == OthelloColor.BLACK) { //Black start
						ImageView blackView = new ImageView(blackImg);
						column.setGraphic(blackView);
						column.setDisable(true);
					}
				}
				else {
					ImageView emptyView = new ImageView(emptyImg);
					column.setGraphic(emptyView);
				}
			}
		}
	}
	public void UpdateScore(OthelloBoard oBoard) {
		if (veryFirstTurn) {
			enemyLabelScore.setText(Integer.toString(oBoard.getPlayerScore(OthelloColor.BLACK)));
			clientLabelScore.setText(Integer.toString(oBoard.getPlayerScore(OthelloColor.WHITE)));
		}else {
			enemyLabelScore.setText(Integer.toString(oBoard.getPlayerScore(OthelloColor.WHITE)));
			clientLabelScore.setText(Integer.toString(oBoard.getPlayerScore(OthelloColor.BLACK)));
		}
	}

	public void updateTurn(boolean yourTurn) {
		if(yourTurn) {
			turnLabel.setText("Aan de beurt: " + username );
		}else {
			turnLabel.setText("Aan de beurt: " + VS);
		}
		
	}
	public void setColor() {
		if (veryFirstTurn) {
			enemyLabel.setText(username+ " : Zwart ");
			clientLabel.setText(VS + " : Wit ");
		}else {
			enemyLabel.setText(username + " : Wit ");
			clientLabel.setText(VS + " : Zwart ");
		}
	}

	public void SetMove(int columnId, int rowId, boolean isPlayer) {
		//Check if player or not
		if(isPlayer) {
			if(playerColor == 0) {
				ImageView whiteView = new ImageView(whiteImg);
				boardList.get(rowId).get(columnId).setGraphic(whiteView);
			}
			else {
				ImageView blackView = new ImageView(blackImg);
				boardList.get(rowId).get(columnId).setGraphic(blackView);
			}
		}
		else {
			if(playerColor == 0) {
				ImageView blackView = new ImageView(blackImg);
				boardList.get(rowId).get(columnId).setGraphic(blackView);
			}
			else {
				ImageView whiteView = new ImageView(whiteImg);
				boardList.get(rowId).get(columnId).setGraphic(whiteView);
			}
		}
		//Set disabled to true
		boardList.get(rowId).get(columnId).setDisable(true);
	}

	/*@Override
    public void start(Stage stage) throws Exception{
		//Set title of stage
		stage.setTitle("Othello");
		stage.setScene(gameScene);
	    stage.show();
	}

	//Launch application
    public static void main(String[] args) {
        launch(args);
    }*/
}
