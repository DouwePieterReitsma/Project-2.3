package gameai;

import gameai.models.Board;
import gameai.models.MiniMaxTicTacToe;
import gameai.models.RandomTicTacToeAI;
import gameai.models.TicTacToeBoard;
import gameai.models.othello.OthelloBoard;
import gameai.models.othello.OthelloColor;
import gameai.models.othello.OthelloPiece;
import gameai.models.othello.ai.AlphaBetaOthelloAI;
import gameai.models.othello.ai.OthelloAI;
import gameai.models.othello.ai.RandomOthelloAI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("views/main.fxml"));

        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("GameAI");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        Board board = new TicTacToeBoard(new MiniMaxTicTacToe());

//        OthelloBoard board = new OthelloBoard(OthelloColor.WHITE);
//
//        OthelloPiece wp = new OthelloPiece(OthelloColor.WHITE);
//        OthelloPiece bp = new OthelloPiece(OthelloColor.BLACK);
//
//        board.getPositions()[3][3].setPiece(bp);
//        board.getPositions()[4][3].setPiece(wp);
//        board.getPositions()[3][4].setPiece(wp);
//        board.getPositions()[4][4].setPiece(bp);
//
//        System.out.println(board);
//
//        OthelloAI white = new AlphaBetaOthelloAI(board, OthelloColor.WHITE, 5);
//        OthelloAI black = new RandomOthelloAI(board, OthelloColor.BLACK);
//
//        while(!board.isGameOver()) {
//            white.play();
//            black.play();
//        }
//
//        System.out.println(board.getMatchResult());
    }
}