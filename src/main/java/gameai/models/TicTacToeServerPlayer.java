package gameai.models;

import gameai.models.othello.OthelloPiece;

public class TicTacToeServerPlayer implements TicTacToeAI {
	private TicTacToeBoard board;
	private TicTacToeFigure opponent;
	private TicTacToeFigure player;

	/**
	* Constructor for the opponent of TicTacToe when playing online
	* @author David Laan
	* @param firstTurn Tells the opponent which figure it gets.
	*/
	public TicTacToeServerPlayer(boolean firstTurn) {
		if(firstTurn) {
			opponent = TicTacToeFigure.X;
			player = TicTacToeFigure.O;
		}
		else {
			opponent = TicTacToeFigure.O;
			player = TicTacToeFigure.X;
		}
	}

	/**
	* Function to handle opponent's movement
	* @author David Laan
	* @param position The position the opponent has to move to.
	*/
	public void doMove(Position position) {
        if (board.currentTurn == opponent) {
            board.setTicTacToePieceAtPosition(new TicTacToePiece(opponent), position);
            board.currentTurn = player;
        }
    }

	/**
	* Function to set the gameboard
	* @author David Laan
	* @param board The gameboard
	*/
	@Override
	public void setBoard(TicTacToeBoard board) {
		// TODO Auto-generated method stub
		this.board = board;
	}

	/**
	* Function to get the gameboard
	* @author David Laan
	* @return Returns the gameboard
	*/
	public TicTacToeBoard getBoard() {
		return board;
	}

	@Override
	public void makeMove() {
		// TODO Auto-generated method stub

	}
}
