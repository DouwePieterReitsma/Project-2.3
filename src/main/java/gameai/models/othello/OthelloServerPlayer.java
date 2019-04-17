package gameai.models.othello;

import gameai.controllers.ConnectionListenerThread;

public class OthelloServerPlayer extends OthelloPlayer  {

	/**
	* Constructor for the opponent of Reversi when playing online
	* @author David Laan
	* @param board The gameboard
	* @param playerColor The color of the opponent
	*/
	public OthelloServerPlayer(OthelloBoard board, OthelloColor playerColor) {
		super(board, playerColor);
	}
}
