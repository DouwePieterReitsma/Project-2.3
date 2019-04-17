package gameai.models;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class contains the AI for Tic Tac Toe which makes random moves.
 * @author Jelle
 *
 */
public class RandomTicTacToeAI implements TicTacToeAI {
	public TicTacToeBoard board;
	public Random rand;
	protected ArrayList<Position> array;
	
	/**
	 * Constructor for the random tic tac toe ai class.
	 * The constructor creates a new random.
	 */
	public RandomTicTacToeAI() {
		rand = new Random();
	}
	
	@Override
	/**
	 * Makes a move by making a random number between null and the size of the legal moves list.
	 * It then takes this number and gets a moves from the legal moves list.
	 */
	public void makeMove() {
        array = board.getLegalMovesList();

        int nextmove = rand.nextInt(array.size());
		if(board.getTurn() == 0) {
		   	array.get(nextmove).setPiece(new TicTacToePiece(TicTacToeFigure.X));
        	board.setTurn(1);
		}else if(board.getTurn() == 1){
		   	array.get(nextmove).setPiece(new TicTacToePiece(TicTacToeFigure.O));
        	board.setTurn(0);
		}
		
	}
	
	/**
	 * The setter for the current board.
	 * @param board
	 * The board to be set.
	 */
	public void setBoard(TicTacToeBoard board) {
		this.board = board;
	}
}
