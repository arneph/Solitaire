package ai;

import java.util.*;

import model.*;
import model.Cards.*;

public abstract class AI {
	private Board board;
	
	public AI() {
		board = null;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public void setBoard(Board b) {
		board = b;
	}
	
	public Move[] getPossibleMoves() {
		if (board == null || board.isGameOver()) {
			return new Move[0];
		}
		
		Move[] moves = new Move[0];
		
		Deck deck = board.getDeck();
		
		if (deck.getNumberOfCardsOnDeck() + 
			deck.getNumberOfCardsOnDeckStack() > 0) {
			moves = Arrays.copyOf(moves, moves.length + 1);
			moves[moves.length - 1] = new Move(board, 
			                                   board.getDeck(), 
			                                   board.getDeck(), 
			                                   1);
		}
		
		if (deck.getNumberOfCardsOnDeckStack() != 0) {
			Card deckCard = deck.getTopCardOnDeckStack();
			
			for (int i = 0; i < 7; i++) {
				TableStack stack = board.getTableStackAtIndex(i);
				
				if (stack.canAddCardsToStack(new Card[] {deckCard})) {
					moves = Arrays.copyOf(moves, moves.length + 1);
					moves[moves.length - 1] = new Move(board, deck, stack, 1);
				}
			}
			
			for (int i = 0; i < 4; i++) {
				BlockStack stack = board.getBlockStackAtIndex(i);
				
				if (stack.canAddCardToStack(deckCard)) {
					moves = Arrays.copyOf(moves, moves.length + 1);
					moves[moves.length - 1] = new Move(board, deck, stack, 1);
				}
			}
		}
		
		for (int i = 0; i < 7; i++) {
			TableStack a = board.getTableStackAtIndex(i);
			
			int m = a.getNumberOfCardsOnStack();
			int n = a.getNumberOfUncoveredCards();
			
			if (n == 0) continue;
			
			for (int c = 1; c <= n; c++) {
				Card[] cards = a.getCardsInRange(m - c, m);
				
				for (int j = 0; j < 7; j++) {
					if (i == j) continue;
					
					TableStack b = board.getTableStackAtIndex(j);
					
					if (b.canAddCardsToStack(cards)) {
						moves = Arrays.copyOf(moves, moves.length + 1);
						moves[moves.length - 1] = new Move(board, a, b, c);
					}
				}
				
				if (c > 1) continue;
				
				for (int j = 0; j < 4; j++) {
					BlockStack b = board.getBlockStackAtIndex(j);
					
					if (b.canAddCardToStack(cards[0])) {
						moves = Arrays.copyOf(moves, moves.length + 1);
						moves[moves.length - 1] = new Move(board, a, b, 1);
					}
				}
			}
		}
		
		for (int i = 0; i < 4; i++) {
			BlockStack blockStack = board.getBlockStackAtIndex(i);
			
			if (blockStack.getNumberOfCardsOnStack() == 0) continue;
			
			Card blockCard = blockStack.getTopCard();
			
			for (int j = 0; j < 7; j++) {
				TableStack tableStack = board.getTableStackAtIndex(j);
				
				if (tableStack.canAddCardsToStack(new Card[] {blockCard})) {
					moves = Arrays.copyOf(moves, moves.length + 1);
					moves[moves.length - 1] = new Move(board, blockStack, tableStack, 1);
				}
			}
		}
		
		for (Move m : moves) {
			if (!m.isPossible()) {
				System.out.println("SHIT!");
				m.isPossible();
			}
		}
		
		return moves;
	}
	
	public abstract Move getNextMove();
	
}
