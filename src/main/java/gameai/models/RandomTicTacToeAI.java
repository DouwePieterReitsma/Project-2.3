package gameai.models;

import java.util.ArrayList;
import java.util.Random;


public class RandomTicTacToeAI implements TicTacToeAI {
	public TicTacToeBoard board;
	public Random rand;
	protected ArrayList<Position> array;
	
	public RandomTicTacToeAI() {
		rand = new Random();
	}
	@Override
	public void makeMove() {
        array = board.getLegalMovesList();

        int nextmove = rand.nextInt(array.size());
		if(board.getTurn() == 0) {
		   	array.get(nextmove).setPiece(new TicTacToePiece(TicTacToeFigure.CROSS));
        	board.setTurn(1);
		}else if(board.getTurn() == 1){
		   	array.get(nextmove).setPiece(new TicTacToePiece(TicTacToeFigure.CIRCLE));
        	board.setTurn(0);
		}
		
	}
	public void setBoard(TicTacToeBoard board) {
		this.board = board;
	}
}
