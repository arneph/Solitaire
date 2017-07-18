package model;

import java.util.*;

import model.Cards.*;

public class Deck extends BoardElement {
	private Card[] deck;
	private Card[] deckStack;
	
	public Deck() {
		deck = new Card[0];
		deckStack = new Card[0];
	}
	
	public int getNumberOfCardsOnDeck() {
		return deck.length;
	}
	
	public Card getCardOnDeckAtIndex(int index) {
		if (index < 0 || index >= deck.length) {
			throw new IllegalArgumentException();
		}
		
		return deck[index];
	}
	
	public int getIndexOfCardOnDeck(Card card) {
		for (int i = 0; i < deck.length; i++) {
			if (deck[i] == card) return i;
		}
		
		return -1;
	}
	
	public Card[] getDeck() {
		return deck;
	}
	
	public void setDeck(Card[] d) {
		if (d == null) {
			throw new IllegalArgumentException();
		}
		
		deck = d;
	}
	
	public int getNumberOfCardsOnDeckStack() {
		return deckStack.length;
	}
	
	public Card getCardOnDeckStackAtIndex(int index) {
		if (index < 0 || index >= deckStack.length) {
			throw new IllegalArgumentException();
		}
		
		return deckStack[index];
	}
	
	public Card[] getVisibleCardsOnDeckStack() {
		if (deckStack.length == 0) {
			return new Card[0];
		}
		
		return Arrays.copyOfRange(deckStack, 
		                          Math.max(0, deckStack.length - 3), 
		                          Math.max(0, deckStack.length));
	}
	
	public int getIndexOfCardOnDeckStack(Card card) {
		for (int i = 0; i < deckStack.length; i++) {
			if (deckStack[i] == card) return i;
		}
		
		return -1;
	}
	
	public Card getTopCardOnDeckStack() {
		if (deckStack.length == 0) {
			return Card.None;
		}
		
		return deckStack[deckStack.length - 1];
	}
	
	public Card[] getDeckStack() {
		return deckStack;
	}
	
	public void setDeckStack(Card[] d) {
		if (d == null) {
			throw new IllegalArgumentException();
		}
		
		deckStack = d;
	}
	
	public int getTotalNumberOfCardsOnDeck() {
		return deck.length + deckStack.length;
	}
	
	public void turnDeck() {
		if (deck.length == 0) {
			if (deckStack.length == 0) {
				return;
			}else{
				deck = deckStack;
				deckStack = new Card[0];
				
				int n = deck.length;
				
				for (int i = 0; i < n / 2; i++) {
					Card a = deck[i];
					Card b = deck[n - 1 - i];
					
					deck[i] = b;
					deck[n - 1 - i] = a;
				}
			}
			
		}else{
			int n1 = deck.length;
			int n2 = deckStack.length;
			int m = Math.min(n1, 3);
			
			deckStack = Arrays.copyOf(deckStack, n2 + m);
			
			for (int i = 0; i < m; i++) {
				deckStack[n2 + i] = deck[n1 - 1 - i];
			}
			
			deck = Arrays.copyOf(deck, n1 - m);
		}
	}
	
	public void removeCardFromDeckStack() {
		int n = deckStack.length;
		
		if (n == 0) {
			return;
		}
		
		deckStack = Arrays.copyOf(deckStack, n - 1);
	}
	
}
