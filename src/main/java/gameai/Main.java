package gameai;

import gameai.models.Board;
import gameai.models.RandomTicTacToeAI;
import gameai.models.TicTacToeBoard;
import gameai.models.othello.OthelloBoard;
import gameai.models.othello.OthelloColor;
import gameai.models.othello.OthelloPiece;
import gameai.models.othello.OthelloPlayer;
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
//        Parent root = FXMLLoader.load(getClass().getResource("views/main.fxml"));
//
//        Scene scene = new Scene(root, 800, 600);
//
//        stage.setTitle("GameAI");
//        stage.setScene(scene);
//        stage.show();
    }

    public static void main(String[] args) {
        //launch();
        //Board board = new TicTacToeBoard(new RandomTicTacToeAI());

        OthelloBoard board = new OthelloBoard(OthelloColor.WHITE);

        OthelloPiece wp = new OthelloPiece(OthelloColor.WHITE);
        OthelloPiece bp = new OthelloPiece(OthelloColor.BLACK);

        board.getPositions()[3][3].setPiece(bp);
        board.getPositions()[4][3].setPiece(wp);
        board.getPositions()[3][4].setPiece(wp);
        board.getPositions()[4][4].setPiece(bp);

        //OthelloAI black = new RandomOthelloAI(board, OthelloColor.BLACK);
        OthelloAI white = new RandomOthelloAI(board, OthelloColor.WHITE);

        OthelloAI black = new AlphaBetaOthelloAI(board, OthelloColor.BLACK, 10);
        //OthelloAI white = new AlphaBetaOthelloAI(board, OthelloColor.WHITE, 1);

        //System.out.println(board);

        while(!board.isGameOver()) {
            white.play();
            black.play();
        }

        //white.play();

        //white.play();

        System.out.println(board.getMatchResult());
    }
}