package gameai.models.othello;

import gameai.controllers.ConnectionListenerThread;

public class OthelloServerPlayer extends OthelloPlayer  {
	private ConnectionListenerThread connectThread;

	public OthelloServerPlayer(OthelloBoard board, OthelloColor playerColor, ConnectionListenerThread connectThread) {
		super(board, playerColor);

		this.connectThread = connectThread;
	}
}
