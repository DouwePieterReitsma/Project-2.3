package gameai.models;

import java.util.ArrayList;

import gameai.models.TicTacToePiece.TicTacToeFigure;

public class MiniMaxTicTacToe implements TicTacToeAI {
	public TicTacToeBoard board;
	public TicTacToeFigure player = TicTacToeFigure.CROSS;
	public TicTacToeFigure opponent = TicTacToeFigure.CIRCLE;
	protected ArrayList<Position> array;
	@Override
	public void makeMove() {
        miniMax(this.board, player);
	}

	@Override
	public void setBoard(TicTacToeBoard board) {
		this.board = board;
	}
	public void checkBestMove(TicTacToeBoard boardCurrent) {

	}
	
	
	
	public int miniMax(TicTacToeBoard boardCurrent, TicTacToeFigure figure) {
		TicTacToeFigure playertemp = figure;
		TicTacToeBoard copyBoard = boardCurrent;
		ArrayList<Position> available = copyBoard.getLegalMoves();
		for(Position position: available) {
			System.out.println(figure);
			System.out.println(boardCurrent);
			if(isWinning(copyBoard,opponent)) {
				return 10;
			}else if(isWinning(copyBoard,player)) {
				return -10;
			}else if(available.size() == 0) {
				return 0;
			}
			if(playertemp == player) {
				position.setPiece(new TicTacToePiece(getPlayer()));
				playertemp = opponent;
			}else if(playertemp == opponent) {
				position.setPiece(new TicTacToePiece(getOpponent()));
				playertemp = player;
			}
		}
		return miniMax(copyBoard, playertemp);
	}
	
	public boolean isWinning(TicTacToeBoard boardCurrent, TicTacToeFigure player) {
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
