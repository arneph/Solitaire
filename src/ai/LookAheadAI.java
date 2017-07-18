package ai;

import model.*;

public class LookAheadAI extends AI {
	
	public LookAheadAI(Board board) {
		super(board);
	}
	/*
	private double getScore(Board board) {
		double score = 0;
		
		score -= 0.5 * board.getDeck().getTotalNumberOfCardsOnDeck();
		
		if (board.getNumberOfEmptyTableStacks() == 0) {
			score -= 0.25;
		}
		
		for (int i = 0; i < 7; i++) {
			TableStack stack = board.getTableStackAtIndex(i);
			
			if (stack.getNumberOfUncoveredCards() == 0 && 
				stack.getNumberOfCoveredCards() > 0) {
				score += 1.5;
			}
			
			score -= 3.0 * Math.log1p(stack.getNumberOfCoveredCards());
			score += 1.0 * stack.getNumberOfUncoveredCards();
		}
		
		score += 20.0 * board.getLowestNumberOfCardsOnAnyBlockStack();
		
		for (int i = 0; i < 4; i++) {
			BlockStack stack = board.getBlockStackAtIndex(i);
			
			score += 4.0 * Math.log1p(stack.getNumberOfCardsOnStack() - board.getLowestNumberOfCardsOnAnyBlockStack());
		}
		
		return score;
	}
	*/
	private int getScore(Board board) {
		int score = 0;
		
		score -= board.getDeck().getNumberOfCardsOnDeck();
		score -= board.getDeck().getNumberOfCardsOnDeckStack();
		
		if (Cards.isKing(board.getDeck().getTopCardOnDeckStack())) {
			score --;
		}
		
		for (int i = 0; i < 7; i++) {
			TableStack stack = board.getTableStackAtIndex(i);
			
			if (stack.getNumberOfUncoveredCards() == 0 && 
				stack.getNumberOfCoveredCards() > 0) {
				score += 4;
			}
		}
		
		score -= 4 * board.getNumberOfCoveredCardsOnTableStacks();
		
		score += board.getNumberOfEmptyTableStacks();
		
		score += 64 * board.getLowestNumberOfCardsOnAnyBlockStack();
		score += 8 * board.getNumberOfCardsOnBlockStacks();
		
		return score;
	}
	
	public Move getNextMove() {
		Move nextMove = getBestMove(getBoard(), 1);
		
		return nextMove;
	}
	
	private Move getBestMove(Board board, int depth) {
		Move[] possibleMoves = board.getPossibleMoves();
		
		if (possibleMoves.length == 0) {
			return null;
		}else if (possibleMoves.length == 1) {
			return possibleMoves[0];
		}
		
		double bestScore = -10000.0;
		Move bestMove = null;
		
		for (Move move : possibleMoves) {
			Board b = board.clone();
			Move currentMove = b.equivalentMove(move);
			
			b.applyHypotheticalMove(currentMove);
			
			double score = getScore(b);
			
			if (bestScore < score) {
				bestScore = score;
				bestMove = move;
			}
			
			if (depth == 0) continue;
			
			Move nextMove = getBestMove(b, depth - 1);
			
			if (nextMove == null) continue;
			
			b.applyHypotheticalMove(nextMove);
			
			score = getScore(b);
			
			if (bestScore < score) {
				bestScore = score;
				bestMove = move;
			}
		}
		
		return bestMove;
	}
	
}
