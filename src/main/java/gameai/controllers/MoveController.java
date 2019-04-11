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

	public MoveController(ConnectionListenerThread connectThread, OthelloAI player, OthelloServerPlayer opponent) {
		this.connectThread = connectThread;
		this.player = player;
		this.opponent = opponent;
	}

	public void doAIMove() throws IOException {
		player.play();
		connectThread.doMove(player.GetMove());
	}

	public void checkEnemy() { //FINAL
		if(connectThread.GetEnemyMove() != -1) {
			int posX = (int)connectThread.GetEnemyMove() % 8;
			int posY = (int)Math.floor(connectThread.GetEnemyMove() / 8);
			System.out.println(posX + " en " + posY);
			Position newPos = player.getBoard().getPositions()[posY][posX];
			opponent.doMove(newPos);
			connectThread.ResetEnemyMove();
			connectThread.advanceTurn();
		}
	}

	public void doMove(Position position) throws IOException {
		if(connectThread.getYourTurn()) {
			player.doMove(position);
			connectThread.doMove(position);
		}
		else {
			opponent.doMove(position);
		}
	}
}
