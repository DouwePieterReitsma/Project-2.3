package gameai.models;

import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;
/**
 * This class contains the Minimax AI for TicTacToe
 * @author Jelle
 *
 */
public class MiniMaxTicTacToeAI implements TicTacToeAI {
	public TicTacToeBoard board;
	public TicTacToeFigure player;
	public TicTacToeFigure opponent;
	private Position bestMove;

	/**
	 * Constructor of the class
	 * @param firstTurn
	 * The parameter defines the starting player. This sets the opponent and player symbol.
	 */
	public MiniMaxTicTacToeAI(boolean firstTurn) {
		if(firstTurn) {
			opponent = TicTacToeFigure.O;
			player = TicTacToeFigure.X;
		}
		else {
			opponent = TicTacToeFigure.X;
			player = TicTacToeFigure.O;
		}
	}

	/**
	 * The makeMove function defines the move to be taken by the AI
	 */
	@Override
	public void makeMove() {
		ArrayList<Position> available = board.getLegalMoves();

		if(this.board.currentTurn == player) {
			bestMove = calculateMove(player);

			for(Position move: available) {
				if(move.equals(bestMove)) {
					board.setTicTacToePieceAtPosition(new TicTacToePiece(player), move);
					board.currentTurn = opponent;
				}
				else {
					board.currentTurn = opponent;
				}
			}
		}
	}
	/**
	 * the setBoard function sets the board.
	 * @param board
	 * The board to be set.
	 */
	@Override
	public void setBoard(TicTacToeBoard board) {
		this.board = board;
	}
	
	/**
	 * The method calculates the value for each possible move it can legally take.
	 * @param figure
	 * The tic tac toe symbol to calculate the value for.
	 * @return
	 * The best possible move to make.
	 */
	public Position calculateMove(TicTacToeFigure figure) {
		TicTacToeBoard copyBoard = new TicTacToeBoard(board);
		ArrayList<Position> available = copyBoard.getLegalMoves();

		int bestMoveScore = -100;
		Position bestMove = null;
		for(Position move: available) {
			copyBoard.setTicTacToePieceAtPosition(new TicTacToePiece(figure), move);

			int moveScore = miniMax(copyBoard,false, figure);

			copyBoard.setTicTacToePieceAtPosition(null, move);

			if(moveScore > bestMoveScore) {
				bestMoveScore = moveScore;
				bestMove = move;
			}

		}
		return bestMove;
	}
	/**
	 * Recursive function which calculates the value for each move
	 * @param tempBoard
	 * the board with the current calculated moves on it.
	 * @param maximizing
	 * Is the current player maximizing or not
	 * @param figure
	 * The figure to calculate the value for.
	 * @return
	 * The value for each possible move.
	 */
	public int miniMax(TicTacToeBoard tempBoard, boolean maximizing, TicTacToeFigure figure) {
		TicTacToeFigure temp;
		TicTacToeBoard copyBoard = new TicTacToeBoard(tempBoard);
		boolean isMaximizing = maximizing;
		if(figure.equals(TicTacToeFigure.X)) {
			temp = TicTacToeFigure.O;
		}else {
			temp = TicTacToeFigure.X;
		}

		ArrayList<Position> available = copyBoard.getLegalMoves();


		if(available.size() == 0) {
			return 0;
		}
		if(isWinning(copyBoard) && !isMaximizing) {
			return 10;
		}
		if(isWinning(copyBoard) && isMaximizing) {
			return -10;
		}
		if(available.size() == 0) {
			return 0;
		}

		if(maximizing) {
			int score = Integer.MIN_VALUE;

			for(Position move: available) {
				copyBoard.setTicTacToePieceAtPosition(new TicTacToePiece(temp), move);


				score = max(score, miniMax(copyBoard, false, temp));
				copyBoard.setTicTacToePieceAtPosition(null, move);
			}
			return score;
		} else {
			int score = Integer.MAX_VALUE;

			for(Position move: available) {
				copyBoard.setTicTacToePieceAtPosition(new TicTacToePiece(temp), move);

				score = min(score, miniMax(copyBoard,true, temp));

				copyBoard.setTicTacToePieceAtPosition(null, move);
			}
			return score;
		}
	}
	/**
	 * Te method checks if the current board has a winning condition on it.
	 * @param boardCurrent
	 * The board to be checked.
	 * @return
	 * True if there is a winning condition. Otherwise false.
	 */
	public boolean isWinning(TicTacToeBoard boardCurrent) {
		if(boardCurrent.positions[0][0].getPiece() != null && boardCurrent.positions[1][1].getPiece() != null && boardCurrent.positions[2][2].getPiece() != null) {
        	if(((TicTacToePiece)boardCurrent.positions[0][0].getPiece()).equals(((TicTacToePiece)boardCurrent.positions[1][1].getPiece())) && ((TicTacToePiece)boardCurrent.positions[1][1].getPiece()).equals(((TicTacToePiece)boardCurrent.positions[2][2].getPiece()))) {
        		return true;
        	}
    	}
    	if(boardCurrent.positions[0][2].getPiece() != null && boardCurrent.positions[1][1].getPiece() != null && boardCurrent.positions[2][0].getPiece() != null) {
        	if(((TicTacToePiece)boardCurrent.positions[0][2].getPiece()).equals(((TicTacToePiece)boardCurrent.positions[1][1].getPiece())) && ((TicTacToePiece)boardCurrent.positions[1][1].getPiece()).equals(((TicTacToePiece)boardCurrent.positions[2][0].getPiece()))) {
        		return true;
        	}
    	}

        for(int x = 0; x < 3; x++) {
        	if(boardCurrent.positions[0][x].getPiece() != null && boardCurrent.positions[1][x].getPiece() != null && boardCurrent.positions[2][x].getPiece() != null) {
	        	if(((TicTacToePiece)boardCurrent.positions[0][x].getPiece()).equals(((TicTacToePiece)boardCurrent.positions[1][x].getPiece())) && ((TicTacToePiece)boardCurrent.positions[1][x].getPiece()).equals(((TicTacToePiece)boardCurrent.positions[2][x].getPiece()))) {
	        		return true;
	        	}
        	}
        	if(boardCurrent.positions[x][0].getPiece() != null && boardCurrent.positions[x][1].getPiece() != null && boardCurrent.positions[x][2].getPiece() != null) {
	        	if(((TicTacToePiece)boardCurrent.positions[x][0].getPiece()).equals(((TicTacToePiece)boardCurrent.positions[x][1].getPiece())) && ((TicTacToePiece)boardCurrent.positions[x][1].getPiece()).equals(((TicTacToePiece)boardCurrent.positions[x][2].getPiece()))) {
	        		return true;
	        	}
        	}
        }
		return false;
	}
	
	/**
	 * Getter for the player
	 * @return
	 * Player figure
	 */
	public TicTacToeFigure getPlayer() {
		return player;
	}
	
	/**
	 * Setter for the player
	 * @param player
	 * The player to be set.
	 */
	public void setPlayer(TicTacToeFigure player) {
		this.player = player;
	}

	/**
	 * The getter for the opponent
	 * @return
	 * Figure for the opponent
	 */
	public TicTacToeFigure getOpponent() {
		return opponent;
	}
	
	/**
	 * The setter for the opponent
	 * @param opponent
	 * The opponent figure to be set.
	 */
	public void setOpponent(TicTacToeFigure opponent) {
		this.opponent = opponent;
	}

	/**
	 * The getter for the board.
	 * @return
	 * Current board.
	 */
	public TicTacToeBoard getBoard() {
		return this.board;
	}
	
	/**
	 * Getter for the best posible move.
	 * @return
	 * Best possible move.
	 */
	public Position getBestMove() {
		return bestMove;
	}
}
