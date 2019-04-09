package gameai.models;

import java.util.ArrayList;
import java.util.HashMap;

import gameai.models.TicTacToePiece.TicTacToeFigure;

public class MiniMaxTicTacToe implements TicTacToeAI {
	public TicTacToeBoard board;
	public TicTacToeFigure player = TicTacToeFigure.CROSS;
	public TicTacToeFigure opponent = TicTacToeFigure.CIRCLE;
	protected ArrayList<Position> array;
	private Position bestPlay;
	private Position worstPlay;
	int bestPlayScore;
	int worstPlayScore;
	int score2;
	private HashMap<Position,Integer> moves = new HashMap<Position,Integer>();

	@Override
	public void makeMove() {
		System.out.println(board + "\n");
        checkBestMove(board, player);
        array = board.getLegalMoves();
        if(this.board.getTurn() == 0) {
        	for(Position position: array) {
        		if(position.equals(bestPlay)) {
        			this.board.setTicTacToePieceAtPosition(new TicTacToePiece(player), position);
        		}
        	}
			this.board.setTurn(1);
        }
        else if(this.board.getTurn() == 1) {
        	for(Position position: array) {
        		if(position.equals(worstPlay)) {
        			this.board.setTicTacToePieceAtPosition(new TicTacToePiece(opponent), position);
        		}
        	}
        	this.board.setTurn(0);
        }
	}

	@Override
	public void setBoard(TicTacToeBoard board) {
		this.board = board;
	}
	public void checkBestMove(TicTacToeBoard boardCurrent, TicTacToeFigure figure) {
		int score = 0;
		moves.clear();
		TicTacToeFigure playertemp = figure;
		TicTacToeBoard copyBoard = new TicTacToeBoard(boardCurrent);
		ArrayList<Position> available = copyBoard.getLegalMoves();
		for(int x = 0; x < available.size() ; x++) {
			Position position = available.get(x);
		score = miniMax(copyBoard, playertemp);
		moves.put(position, score);
		score = 0;
		}
		int highest = Integer.MIN_VALUE;
		int lowest = Integer.MAX_VALUE;
		for(int value:moves.values()) {
			if(value > highest) {
				highest = value;
			}else if(value < lowest) {
				lowest = value;
			}
		}
		for(Position position: moves.keySet()) {
			if(moves.get(position)==highest) {
				bestPlay = position;
				bestPlayScore = highest;
			}
			if(moves.get(position)==lowest) {
				worstPlay = position;
				worstPlayScore = lowest;
			}
		}
		
	}
	
	
	
	public int miniMax(TicTacToeBoard boardCurrent, TicTacToeFigure figure) {
		TicTacToeFigure playertemp = figure;
		TicTacToeBoard copyBoard = new TicTacToeBoard(boardCurrent);
		ArrayList<Position> available = copyBoard.getLegalMoves();
		if(isWinning(boardCurrent)) {
			if(playertemp == player) {
				return 10;
			}
			else if(playertemp == opponent) {
				return -10;
			}

		}else if(available.size() == 0) {
			return 0;
		}

		for(int x = 0; x < available.size() ; x++) {
			Position position = available.get(x);
			if(playertemp == player) {
				position.setPiece(new TicTacToePiece(getPlayer()));
				playertemp = opponent;
			}else if(playertemp == opponent) {
				position.setPiece(new TicTacToePiece(getOpponent()));
				playertemp = player;
			}
			score2 += miniMax(copyBoard, playertemp);
			

		}
		return score2;
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
	
	public TicTacToeFigure getOpponent() {
		return opponent;
	}
	
	public void setOpponent(TicTacToeFigure figure) {
		this.opponent = figure;
	}
	
	public TicTacToeFigure getPlayer() {
		return player;
	}
	
	public void setPlayer(TicTacToeFigure figure) {
		this.player = figure;
	}
}
