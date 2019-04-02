package gameai.models;

import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gameai.models.TicTacToePiece.TicTacToeFigure;

public class TicTacToeBoard extends Board {
	public int turn = 0;
    public TicTacToeBoard() {
        super(3,3);
        Random rand = new Random();
        while(true) {
            TicTacToeFigure check = checkWinningConditions();
            if(check != null) {
            	System.out.println("The winner is: " + check);
            	break;
            }
            ArrayList<Position> array = new ArrayList<Position>();
            array = getLegalMoves();
            if(array.size() == 0) {
            	System.out.println("It's a draw");
            	break;
            }
            int nextmove = rand.nextInt(array.size());
            if(turn == 0) {
            	array.get(nextmove).setPiece(new TicTacToePiece(TicTacToeFigure.CROSS));
            	turn = 1;
            }else {
            	array.get(nextmove).setPiece(new TicTacToePiece(TicTacToeFigure.CIRCLE));
            	turn = 0;
            }
            
            
        }
        for(int x = 0; x < 3; x++) {
        	for(int y = 0; y < 3; y++) {
        		System.out.println(positions[y][x]);
        	}
        }
    }

    @Override
    public ArrayList<Position> getLegalMoves() {
    	ArrayList<Position> temp = new ArrayList<Position>();
        for(int x = 0; x < 3; x++) {
        	for(int y = 0; y < 3; y++) {
        		if(positions[y][x].isAvailable()) {
        			temp.add(positions[y][x]);
        		}
        	}
        }
		return temp;
    }
    
    public TicTacToeFigure checkWinningConditions() {
    	if(positions[0][0].getPiece() != null && positions[1][1].getPiece() != null && positions[2][2].getPiece() != null) {
        	if(((TicTacToePiece)positions[0][0].getPiece()).equals(((TicTacToePiece)positions[1][1].getPiece())) && ((TicTacToePiece)positions[1][1].getPiece()).equals(((TicTacToePiece)positions[2][2].getPiece()))) {
        		return ((TicTacToePiece) positions[0][0].getPiece()).getFigure();
        	}
    	}
    	if(positions[0][2].getPiece() != null && positions[1][1].getPiece() != null && positions[2][0].getPiece() != null) {
        	if(((TicTacToePiece)positions[0][2].getPiece()).equals(((TicTacToePiece)positions[1][1].getPiece())) && ((TicTacToePiece)positions[1][1].getPiece()).equals(((TicTacToePiece)positions[2][0].getPiece()))) {
        		return ((TicTacToePiece) positions[0][2].getPiece()).getFigure();
        	}
    	}

        for(int x = 0; x < 3; x++) {
        	if(positions[0][x].getPiece() != null && positions[1][x].getPiece() != null && positions[2][x].getPiece() != null) {
	        	if(((TicTacToePiece)positions[0][x].getPiece()).equals(((TicTacToePiece)positions[1][x].getPiece())) && ((TicTacToePiece)positions[1][x].getPiece()).equals(((TicTacToePiece)positions[2][x].getPiece()))) {
	        		return ((TicTacToePiece) positions[0][x].getPiece()).getFigure();
	        	}
        	}
        	if(positions[x][0].getPiece() != null && positions[x][1].getPiece() != null && positions[x][2].getPiece() != null) {
	        	if(((TicTacToePiece)positions[x][0].getPiece()).equals(((TicTacToePiece)positions[x][1].getPiece())) && ((TicTacToePiece)positions[x][1].getPiece()).equals(((TicTacToePiece)positions[x][2].getPiece()))) {
	        		return ((TicTacToePiece) positions[x][0].getPiece()).getFigure();
	        	}
        	}
        }
		return null;
    }
}