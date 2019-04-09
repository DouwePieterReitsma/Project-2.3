package gameai;

import gameai.models.Board;
import gameai.models.MiniMaxTicTacToe;
import gameai.models.RandomTicTacToeAI;
import gameai.models.TicTacToeBoard;
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
    }
}