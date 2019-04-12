package gameai.models;

import java.util.ArrayList;

import gameai.models.othello.OthelloBoard;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class MiniMaxTicTacToeAI implements TicTacToeAI {
	public TicTacToeBoard board;
	public TicTacToeFigure player;
	public TicTacToeFigure opponent;
	private Position bestMove;

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

	@Override
	public void makeMove() {
		ArrayList<Position> available = board.getLegalMoves();

		if(this.board.currentTurn == player) {
			bestMove = calculateMove(player);

			for(Position move: available) {
				if(move.equals(bestMove)) {
					board.setTicTacToePieceAtPosition(new TicTacToePiece(player), move);
					board.currentTurn = opponent;
					System.out.println("new position = " + move);
				}
				else {
					board.currentTurn = opponent;
				}
			}
		}
	}

	@Override
	public void setBoard(TicTacToeBoard board) {
		this.board = board;
	}

	public Position calculateMove(TicTacToeFigure figure) {
		TicTacToeBoard copyBoard = new TicTacToeBoard(board);
		ArrayList<Position> available = copyBoard.getLegalMoves();

		int bestMoveScore = -100;
		Position bestMove = null;
		for(Position move: available) {
			//TicTacToeBoard copyBoard = new TicTacToeBoard(board);
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

	public TicTacToeFigure getPlayer() {
		return player;
	}

	public void setPlayer(TicTacToeFigure player) {
		this.player = player;
	}

	public TicTacToeFigure getOpponent() {
		return opponent;
	}

	public void setOpponent(TicTacToeFigure opponent) {
		this.opponent = opponent;
	}

	public TicTacToeBoard getBoard() {
		// TODO Auto-generated method stub
		return this.board;
	}
	public Position getBestMove() {
		return bestMove;
	}
}
