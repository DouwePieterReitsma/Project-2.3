package gameai.models;

import java.util.ArrayList;


public class TicTacToeBoard extends Board {
	public TicTacToeAI ai;
	private TicTacToeServerPlayer opponent;
	protected ArrayList<Position> array = new ArrayList<Position>();
	public TicTacToeFigure currentTurn = TicTacToeFigure.X;
	private boolean isGameOver;

	public TicTacToeBoard(TicTacToeAI ai, TicTacToeServerPlayer opponent) {
		super(3, 3);
		this.ai = ai;
		this.opponent = opponent;
		isGameOver = false;
		ai.setBoard(this);
		opponent.setBoard(this);
	}

	public TicTacToeBoard(TicTacToeBoard board) {
		super(3,3);
		Position[][] tempPositions = board.getPositions();
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				if (!tempPositions[y][x].isAvailable()) {
					TicTacToePiece tempFigure = (TicTacToePiece) tempPositions[y][x].getPiece();
					positions[y][x].setPiece(new TicTacToePiece(tempFigure.getFigure()));
				}
			}
		}
	}

	public void EndGame() {
		isGameOver = true;
	}

	public boolean GetGameOver() {
		return isGameOver;
	}

	public void play() {
		// Carries out the game locally

			TicTacToeFigure check = checkWinningConditions();
			if (check != null) {
				System.out.println("The winner is: " + check);
				isGameOver = true;
			}
			array = getLegalMoves();
			if (array.size() == 0) {
				System.out.println("It's a draw");
				isGameOver = true;
			}
		System.out.println(this);
	}

	@Override
	public ArrayList<Position> getLegalMoves() {
		// returns an array which contains the positions which aren't occupied.
		ArrayList<Position> temp = new ArrayList<Position>();
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				if (positions[y][x].isAvailable()) {
					temp.add(positions[y][x]);
				}
			}
		}
		return temp;
	}

	public TicTacToeFigure checkWinningConditions() {
		// Checks if the current state of the game has a winning condition. If so, it
		// returns the character which has won (Cross or Circle).If there is no winning
		// condition found, it returns null.
		if (positions[0][0].getPiece() != null && positions[1][1].getPiece() != null
				&& positions[2][2].getPiece() != null) {
			if (((TicTacToePiece) positions[0][0].getPiece()).equals(((TicTacToePiece) positions[1][1].getPiece()))
					&& ((TicTacToePiece) positions[1][1].getPiece())
							.equals(((TicTacToePiece) positions[2][2].getPiece()))) {
				return ((TicTacToePiece) positions[0][0].getPiece()).getFigure();
			}
		}
		if (positions[0][2].getPiece() != null && positions[1][1].getPiece() != null
				&& positions[2][0].getPiece() != null) {
			if (((TicTacToePiece) positions[0][2].getPiece()).equals(((TicTacToePiece) positions[1][1].getPiece()))
					&& ((TicTacToePiece) positions[1][1].getPiece())
							.equals(((TicTacToePiece) positions[2][0].getPiece()))) {
				return ((TicTacToePiece) positions[0][2].getPiece()).getFigure();
			}
		}

		for (int x = 0; x < 3; x++) {
			if (positions[0][x].getPiece() != null && positions[1][x].getPiece() != null
					&& positions[2][x].getPiece() != null) {
				if (((TicTacToePiece) positions[0][x].getPiece()).equals(((TicTacToePiece) positions[1][x].getPiece()))
						&& ((TicTacToePiece) positions[1][x].getPiece())
								.equals(((TicTacToePiece) positions[2][x].getPiece()))) {
					return ((TicTacToePiece) positions[0][x].getPiece()).getFigure();
				}
			}
			if (positions[x][0].getPiece() != null && positions[x][1].getPiece() != null
					&& positions[x][2].getPiece() != null) {
				if (((TicTacToePiece) positions[x][0].getPiece()).equals(((TicTacToePiece) positions[x][1].getPiece()))
						&& ((TicTacToePiece) positions[x][1].getPiece())
								.equals(((TicTacToePiece) positions[x][2].getPiece()))) {
					return ((TicTacToePiece) positions[x][0].getPiece()).getFigure();
				}
			}
		}
		return null;
	}

	public String toString() {
		// Overrides the toString method to display the board in the console.
		String board = "";
		for (int x = 0; x < 3; x++) {
			if (x != 0) {
				board += "\n";
			}
			for (int y = 0; y < 3; y++) {
				board += this.positions[y][x].getPiece() + " ";
			}
		}
		return board;
	}

	public ArrayList<Position> getLegalMovesList() {
		return array;
	}
    public void setTicTacToePieceAtPosition(Piece piece, Position position){
        position.setPiece(piece);
    }
}