package gameai.controllers;

import java.io.IOException;

import gameai.models.*;
import gameai.models.othello.OthelloPlayer;
import gameai.models.othello.OthelloServerPlayer;
import gameai.models.othello.ai.OthelloAI;

public class MoveController {
	private ConnectionListenerThread connectThread;
	private OthelloAI player;
	private OthelloPlayer opponent;
	private MiniMaxTicTacToeAI tPlayer;
	private TicTacToeServerPlayer tOpponent;
	private String type;

	/**
	* Constructor for MoveController when Reversi.
	* @author David Laan
	* @param connectThread The connectionlistenerthread so this class can communicate with it.
	* @param player The player of Reversi.
	* @param opponent The opponent of Reversi.
	* @param type A value that represents the current selected game.
	*/
	public MoveController(ConnectionListenerThread connectThread, OthelloAI player, OthelloServerPlayer opponent, String type) {
		this.connectThread = connectThread;
		this.player = player;
		this.opponent = opponent;
		this.type = type;
	}

	/**
	* Constructor for MoveController when TicTacToe.
	* @author David Laan
	* @param connectThread The connectionlistenerthread so this class can communicate with it.
	* @param player The player of TicTacToe.
	* @param opponent The opponent of TicTacToe.
	* @param type A value that represents the current selected game.
	*/
	public MoveController(ConnectionListenerThread connectThread, MiniMaxTicTacToeAI tPlayer, TicTacToeServerPlayer tOpponent, String type) {
		this.connectThread = connectThread;
		this.tPlayer = tPlayer;
		this.tOpponent = tOpponent;
		this.type = type;
	}

	/**
	* Function that handles AI movement for both games.
	* @author David Laan
	*/
	public void doAIMove() throws IOException {
		if(type.equals("Reversi")) {
			player.play();

			if (player.GetMove() != null ) {
				connectThread.doMove(player.GetMove());

				if(connectThread.getIllegalMove()) {
					endGame();
				}
			}
			else {
				connectThread.advanceTurn();
			}
		}
		else if(type.equals("TicTacToe")) {
			tPlayer.makeMove();
			connectThread.doMove(tPlayer.getBestMove());
			tPlayer.getBoard().play();
		}
	}

	/**
	* Function that checks enemy's movement.
	* @author David Laan
	*/
	public void checkEnemy() { //FINAL
		if(type.equals("Reversi")) {
			if(connectThread.getIllegalMove()) {
				endGame();
			}
			if(connectThread.GetEnemyMove() != -1) {
				int posX = (int)connectThread.GetEnemyMove() % 8;
				int posY = (int)Math.floor(connectThread.GetEnemyMove() / 8);
				Position newPos = player.getBoard().getPositions()[posY][posX];
				opponent.doMove(newPos);
				connectThread.ResetEnemyMove();
				connectThread.advanceTurn();
			}
			else if(connectThread.getState() != 2){
				endGame();
			}
		}
		else if(type.equals("TicTacToe")) {
			if(connectThread.getIllegalMove() || connectThread.GetState() != 2) {
				endGame();
			}
			else if(connectThread.getState() == 1){
				endGame();
			}
			else if(connectThread.GetEnemyMove() != -1) {
				int posX = (int)connectThread.GetEnemyMove() % 3;
				int posY = (int)Math.floor(connectThread.GetEnemyMove() / 3);
				Position newPos = tPlayer.getBoard().getPositions()[posY][posX];
				tOpponent.doMove(newPos);
				tOpponent.getBoard().play();
				connectThread.ResetEnemyMove();
				connectThread.advanceTurn();
			}
		}
	}

	/**
	* Function that ends the games
	* @author David Laan
	*/
	public void endGame() {
		if(type.equals("Reversi")) {
			player.getBoard().EndGame();
		}
		else if(type.equals("TicTacToe")) {
			tPlayer.getBoard().EndGame();
		}
	}

	/*public void doMove(Position position) throws IOException {
		if(connectThread.getYourTurn()) {
			player.doMove(position);
			connectThread.doMove(position);
		}
		else {
			opponent.doMove(position);
		}
	}*/
}
