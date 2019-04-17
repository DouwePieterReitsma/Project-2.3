package gameai.models;

/**
 * The class used to do the move of the opponent.
 * @author Jelle
 *
 */
public class TicTacToeServerPlayer implements TicTacToeAI {
	private TicTacToeBoard board;
	private TicTacToeFigure opponent;
	private TicTacToeFigure player;

	/**
	 * Constructor to set the figure of the opponent and the player.
	 * @param firstTurn
	 * The firstTurn decides which figure the opponent and the player are.
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
	 * Method which sets the move the opponent took in the model.
	 * @param position
	 * The position to make the move.
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
