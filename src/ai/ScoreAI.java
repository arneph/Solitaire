package ai;

import java.util.Arrays;
import java.util.Random;

import model.*;

public class ScoreAI extends AI {

	public ScoreAI(Board board) {
		super(board);
	}
	
	private double getScore(Board originalBoard, Board board) {
		double score = 0;
		
		score -= 0.5 * board.getDeck().getTotalNumberOfCardsOnDeck();
		
		if (board.getNumberOfEmptyTableStacks() == 0) {
			score -= 0.25;
		}
		
		for (int i = 0; i < 7; i++) {
			TableStack stack = board.getTableStackAtIndex(i);
			
			score -= 3.0 * Math.log1p(stack.getNumberOfCoveredCards());
			score += 1.0 * stack.getNumberOfUncoveredCards();
		}
		
		score += 20.0 * board.getLowestNumberOfCardsOnAnyBlockStack();
		
		for (int i = 0; i < 4; i++) {
			BlockStack stack = board.getBlockStackAtIndex(i);
			
			score += 4.0 * Math.log1p(stack.getNumberOfCardsOnStack() - board.getLowestNumberOfCardsOnAnyBlockStack());
		}
		
		int m = originalBoard.getPossibleMoves().length;
		int n = board.getPossibleMoves().length;
		
		if (m + 3 <= n) {
			score += 1.0 * (n - m);
		}else if (m >= n) {
			score -= 1.0;
		}
		
		return score;
	}
	
	public Move getNextMove() {
		Move[] moves = getBoard().getPossibleMoves();
		Move[] bestMoves = new Move[0];
		double maxScore = -10000.0;
		
		double originalScore = getScore(getBoard(), getBoard());
		
		Random r = new Random(System.nanoTime());
		
		for (int i = 0; i < moves.length; i++) {
			Board board = getBoard().clone();
			Move move = board.equivalentMove(moves[i]);
			
			board.applyMove(move);
			
			double score = getScore(getBoard(), board);
			
			if (move instanceof DeckMove) {
				score -= 0.01;
			}
			if (score == originalScore) {
				continue;
			}
			
			if (score > maxScore) {
				bestMoves = new Move[] {moves[i]};
				maxScore = score;
				
			}else if (score == maxScore) {
				bestMoves = Arrays.copyOf(bestMoves, bestMoves.length);
				bestMoves[bestMoves.length - 1] = moves[i];
			}
		}
		
		int n = bestMoves.length;
		
		if (n == 0) return null;
		if (n == 1) return bestMoves[0];
		
		return moves[r.nextInt(n)];
	}
	
}
