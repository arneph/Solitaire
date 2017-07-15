package model;

import java.util.*;

import model.Cards.*;

public class Board {
	private Deck deck;
	private TableStack[] tableStacks;
	private BlockStack[] blockStacks;
	
	public Board() {
		deck = new Deck();
		tableStacks = new TableStack[] {new TableStack(), 
										new TableStack(), 
										new TableStack(), 
										new TableStack(), 
										new TableStack(), 
										new TableStack(), 
										new TableStack()};
		blockStacks = new BlockStack[] {new BlockStack(), 
										new BlockStack(), 
										new BlockStack(), 
										new BlockStack()};
		
		Card[] shuffledCards = getShuffledCards();
		
		deck.setDeck(Arrays.copyOfRange(shuffledCards, 0, 24));
		
		tableStacks[0].setStack(Arrays.copyOfRange(shuffledCards, 24, 25));
		tableStacks[0].setNumberOfUncoveredCards(1);
		tableStacks[1].setStack(Arrays.copyOfRange(shuffledCards, 25, 27));
		tableStacks[1].setNumberOfUncoveredCards(1);
		tableStacks[2].setStack(Arrays.copyOfRange(shuffledCards, 27, 30));
		tableStacks[2].setNumberOfUncoveredCards(1);
		tableStacks[3].setStack(Arrays.copyOfRange(shuffledCards, 30, 34));
		tableStacks[3].setNumberOfUncoveredCards(1);
		tableStacks[4].setStack(Arrays.copyOfRange(shuffledCards, 34, 39));
		tableStacks[4].setNumberOfUncoveredCards(1);
		tableStacks[5].setStack(Arrays.copyOfRange(shuffledCards, 39, 45));
		tableStacks[5].setNumberOfUncoveredCards(1);
		tableStacks[6].setStack(Arrays.copyOfRange(shuffledCards, 45, 52));
		tableStacks[6].setNumberOfUncoveredCards(1);
	}
	
	private static Card[] getShuffledCards() {
		Card[] cards = new Card[52];
		
		for (int i = 0; i < 52; i++) {
			cards[i] = Card.values()[Cards.Card.ClubAce.ordinal() + i];
		}
		
		Random r = new Random(System.currentTimeMillis());
		
		for (int i = 0; i < 52; i++) {
			int j = r.nextInt(52);
			
			Card a = cards[i];
			Card b = cards[j];
			
			cards[i] = b;
			cards[j] = a;
		}
		
		return cards;
	}
	
	public Deck getDeck() {
		return deck;
	}
	
	public TableStack getTableStackAtIndex(int index) {
		if (index < 0 || index >= 7) {
			throw new IllegalArgumentException();
		}
		
		return tableStacks[index];
	}
	
	public TableStack[] getTableStacks() {
		return tableStacks;
	}
	
	public BlockStack getBlockStackAtIndex(int index) {
		if (index < 0 || index >= 4) {
			throw new IllegalArgumentException();
		}
		
		return blockStacks[index];
	}
	
	public BlockStack[] getBlockStacks() {
		return blockStacks;
	}
	
	public boolean isGameOver() {
		if (blockStacks[0].isComplete() == false) return false;
		if (blockStacks[1].isComplete() == false) return false;
		if (blockStacks[2].isComplete() == false) return false;
		if (blockStacks[3].isComplete() == false) return false;
		
		return true;
	}
	
}
