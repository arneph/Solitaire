package ai;

import java.util.*;

import model.*;

public class RandomAI extends AI {
	
	public RandomAI(Board board) {
		super(board);
	}
	
	public Move getNextMove() {
		Move[] moves = getBoard().getPossibleMoves();
		
		int n = moves.length;
		
		if (n == 0) return null;
		
		Random r = new Random(System.nanoTime());
		
		return moves[r.nextInt(n)];
	}

}
