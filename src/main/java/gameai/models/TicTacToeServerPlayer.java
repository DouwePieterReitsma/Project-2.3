package gameai.models;

import gameai.models.othello.OthelloPiece;

public class TicTacToeServerPlayer implements TicTacToeAI {
	private TicTacToeBoard board;
	private TicTacToeFigure opponent;
	private TicTacToeFigure player;

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

	public void doMove(Position position) {
        if (board.currentTurn == opponent) {
            board.setTicTacToePieceAtPosition(new TicTacToePiece(opponent), position);
            board.currentTurn = player;
        }
    }

	@Override
	public void setBoard(TicTacToeBoard board) {
		// TODO Auto-generated method stub
		this.board = board;
	}

	public TicTacToeBoard getBoard() {
		return board;
	}

	@Override
	public void makeMove() {
		// TODO Auto-generated method stub

	}
}
