package ai;

import java.util.*;

public class RandomAI extends AI {
	
	public Move getNextMove() {
		Move[] moves = getPossibleMoves();
		
		int n = moves.length;
		
		if (n == 0) return null;
		
		Random r = new Random(System.nanoTime());
		
		return moves[r.nextInt(n)];
	}

}
