package ai;

import model.*;

public abstract class AI {
	private Board board;
	
	public AI(Board board) {
		if (board == null) {
			throw new IllegalArgumentException();
		}
		
		this.board = board;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public abstract Move getNextMove();
	
}
