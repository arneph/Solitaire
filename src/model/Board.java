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
	
	public int getIndexOfTableStack(TableStack tableStack) {
		for (int i = 0; i < 7; i++) {
			if (tableStacks[i] == tableStack) return i;
		}
		
		return -1;
	}
	
	public TableStack[] getTableStacks() {
		return tableStacks;
	}
	
	public int getNumberOfCoveredCardsOnTableStacks() {
		int n = 0;
		
		n += tableStacks[0].getNumberOfCoveredCards();
		n += tableStacks[1].getNumberOfCoveredCards();
		n += tableStacks[2].getNumberOfCoveredCards();
		n += tableStacks[3].getNumberOfCoveredCards();
		n += tableStacks[4].getNumberOfCoveredCards();
		n += tableStacks[5].getNumberOfCoveredCards();
		n += tableStacks[6].getNumberOfCoveredCards();
		
		return n;
	}
	
	public BlockStack getBlockStackAtIndex(int index) {
		if (index < 0 || index >= 4) {
			throw new IllegalArgumentException();
		}
		
		return blockStacks[index];
	}
	
	public int getIndexOfBlockStack(BlockStack blockStack) {
		for (int i = 0; i < 4; i++) {
			if (blockStacks[i] == blockStack) return i;
		}
		
		return -1;
	}
	
	public int getIndexOfClubsBlockStack(boolean proposeStack) {
		for (int i = 0; i < 4; i++) {
			if (blockStacks[i].getNumberOfCardsOnStack() == 0) continue;
			if (blockStacks[i].getCardAtIndex(0) != Cards.Card.ClubAce) continue;
			
			return i;
		}
		
		return -1;
	}
	
	public int getIndexOfDiamondsBlockStack() {
		for (int i = 0; i < 4; i++) {
			if (blockStacks[i].getNumberOfCardsOnStack() == 0) continue;
			if (blockStacks[i].getCardAtIndex(0) != Cards.Card.DiamondAce) continue;
			
			return i;
		}
		
		return -1;
	}
	
	public int getIndexOfHeartsBlockStack() {
		for (int i = 0; i < 4; i++) {
			if (blockStacks[i].getNumberOfCardsOnStack() == 0) continue;
			if (blockStacks[i].getCardAtIndex(0) != Cards.Card.HeartAce) continue;
			
			return i;
		}
		
		return -1;
	}
	
	public int getIndexOfSpadesBlockStack() {
		for (int i = 0; i < 4; i++) {
			if (blockStacks[i].getNumberOfCardsOnStack() == 0) continue;
			if (blockStacks[i].getCardAtIndex(0) != Cards.Card.SpadeAce) continue;
			
			return i;
		}
		
		return -1;
	}
	
	public BlockStack[] getBlockStacks() {
		return blockStacks;
	}
	
	public int getLowestNumberOfCardsOnAnyBlockStack() {
		int min = 13;
		
		min = Math.min(min, blockStacks[0].getNumberOfCardsOnStack());
		min = Math.min(min, blockStacks[1].getNumberOfCardsOnStack());
		min = Math.min(min, blockStacks[2].getNumberOfCardsOnStack());
		min = Math.min(min, blockStacks[3].getNumberOfCardsOnStack());
		
		return min;
	}
	
	public int getNumberOfCardsOnBlockStacks() {
		int n = 0;
		
		n += blockStacks[0].getNumberOfCardsOnStack();
		n += blockStacks[1].getNumberOfCardsOnStack();
		n += blockStacks[2].getNumberOfCardsOnStack();
		n += blockStacks[3].getNumberOfCardsOnStack();
		
		return n;
	}
	
	public BoardElement getLocationOfCard(Card card) {
		if (deck.getIndexOfCardOnDeck(card) != -1) return deck;
		if (deck.getIndexOfCardOnDeckStack(card) != -1) return deck;
		
		if (tableStacks[0].getIndexOfCard(card) != -1) return tableStacks[0];
		if (tableStacks[1].getIndexOfCard(card) != -1) return tableStacks[1];
		if (tableStacks[2].getIndexOfCard(card) != -1) return tableStacks[2];
		if (tableStacks[3].getIndexOfCard(card) != -1) return tableStacks[3];
		if (tableStacks[4].getIndexOfCard(card) != -1) return tableStacks[4];
		if (tableStacks[5].getIndexOfCard(card) != -1) return tableStacks[5];
		if (tableStacks[6].getIndexOfCard(card) != -1) return tableStacks[6];
		
		if (blockStacks[0].getIndexOfCard(card) != -1) return blockStacks[0];
		if (blockStacks[1].getIndexOfCard(card) != -1) return blockStacks[1];
		if (blockStacks[2].getIndexOfCard(card) != -1) return blockStacks[2];
		if (blockStacks[3].getIndexOfCard(card) != -1) return blockStacks[3];
		
		return null;
	}
	
	public boolean isElement(BoardElement element) {
		if (deck == element) return true;
		
		if (tableStacks[0] == element) return true;
		if (tableStacks[1] == element) return true;
		if (tableStacks[2] == element) return true;
		if (tableStacks[3] == element) return true;
		if (tableStacks[4] == element) return true;
		if (tableStacks[5] == element) return true;
		if (tableStacks[6] == element) return true;
		
		if (blockStacks[0] == element) return true;
		if (blockStacks[1] == element) return true;
		if (blockStacks[2] == element) return true;
		if (blockStacks[3] == element) return true;
		
		return false;
	}
	
	public Move[] getPossibleMoves() {
		int n = 0;
		Move[] moves = new Move[0];
		
		if (deck.getTotalNumberOfCardsOnDeck() > 0) {
			moves = Arrays.copyOf(moves, ++n);
			moves[n - 1] = new DeckMove(this);
		}
		
		if (deck.getNumberOfCardsOnDeckStack() > 0) {
			Card deckCard = deck.getTopCardOnDeckStack();
			
			for (int i = 0; i < 7; i++) {
				TableStack stack = tableStacks[i];
				
				if (stack.canAddCardsToStack(new Card[] {deckCard})) {
					moves = Arrays.copyOf(moves, ++n);
					moves[n - 1] = new CardMove(this, deck, stack, 1);
				}
			}
			
			for (int i = 0; i < 4; i++) {
				BlockStack stack = blockStacks[i];
				
				if (stack.canAddCardToStack(deckCard)) {
					moves = Arrays.copyOf(moves, ++n);
					moves[n - 1] = new CardMove(this, deck, stack, 1);
				}
			}
		}
		
		for (int i = 0; i < 7; i++) {
			TableStack a = tableStacks[i];
			
			int m1 = a.getNumberOfCardsOnStack();
			int m2 = a.getNumberOfUncoveredCards();
			
			if (m2 == 0) continue;
			
			for (int c = 1; c <= m2; c++) {
				Card[] cards = a.getCardsInRange(m1 - c, m1);
				
				for (int j = 0; j < 7; j++) {
					if (i == j) continue;
					
					TableStack b = tableStacks[j];
					
					if (b.canAddCardsToStack(cards)) {
						moves = Arrays.copyOf(moves, ++n);
						moves[n - 1] = new CardMove(this, a, b, c);
					}
				}
				
				if (c > 1) continue;
				
				for (int j = 0; j < 4; j++) {
					BlockStack b = blockStacks[j];
					
					if (b.canAddCardToStack(cards[0])) {
						moves = Arrays.copyOf(moves, ++n);
						moves[n - 1] = new CardMove(this, a, b, 1);
					}
				}
			}
		}

		for (int i = 0; i < 4; i++) {
			BlockStack blockStack = blockStacks[i];
			
			if (blockStack.getNumberOfCardsOnStack() == 0) continue;
			
			Card blockCard = blockStack.getTopCard();
			
			for (int j = 0; j < 7; j++) {
				TableStack tableStack = tableStacks[j];
				
				if (tableStack.canAddCardsToStack(new Card[] {blockCard})) {
					moves = Arrays.copyOf(moves, ++n);
					moves[n - 1] = new CardMove(this, blockStack, tableStack, 1);
				}
			}
		}
		
		return moves;
	}
	
	public Card[] getCardsInMove(Move move) {
		if (move == null || 
			move.getBoard() != this || 
			move.isPossible() == false) {
			throw new IllegalArgumentException();
		}
		
		if (move instanceof DeckMove) {
			return new Card[0];
			
		}else if (move instanceof CardMove) {
			CardMove cardMove = (CardMove) move;
			
			int n = cardMove.getCount();
			
			if (cardMove.getOrigin() instanceof Deck) {
				return new Card[] {deck.getTopCardOnDeckStack()};
				
			}else if (cardMove.getOrigin() instanceof TableStack) {
				TableStack stack = (TableStack) cardMove.getOrigin();
				int m = stack.getNumberOfCardsOnStack();
				
				return stack.getCardsInRange(m - n, m);
				
			}else if (cardMove.getOrigin() instanceof BlockStack) {
				BlockStack stack = (BlockStack) cardMove.getOrigin();
				
				return new Card[] {stack.getTopCard()};
			}
		}
		
		return null;
	}
	
	public void applyMove(Move move) {
		if (move == null || 
			move.getBoard() != this || 
			move.isPossible() == false) {
			throw new IllegalArgumentException();
		}
		
		if (move instanceof DeckMove) {
			deck.turnDeck();
			
		}else if (move instanceof CardMove) {
			CardMove cardMove = (CardMove) move;
			Card[] cards = null;

			int n = cardMove.getCount();
			
			if (cardMove.getOrigin() instanceof Deck) {
				cards = new Card[] {deck.getTopCardOnDeckStack()};
				
				deck.removeCardFromDeckStack();
				
			}else if (cardMove.getOrigin() instanceof TableStack) {
				TableStack stack = (TableStack) cardMove.getOrigin();
				int m = stack.getNumberOfCardsOnStack();
				
				cards = stack.getCardsInRange(m - n, m);
				
				stack.removeCardsFromStack(m - n);
				
			}else if (cardMove.getOrigin() instanceof BlockStack) {
				BlockStack stack = (BlockStack) cardMove.getOrigin();
				
				cards = new Card[] {stack.getTopCard()};
				
				stack.removeCardFromStack();
			}
			
			if (cardMove.getDestination() instanceof TableStack) {
				TableStack stack = (TableStack) cardMove.getDestination();
				
				stack.addCardsToStack(cards);
				
			}else if (cardMove.getDestination() instanceof BlockStack) {
				BlockStack stack = (BlockStack) cardMove.getDestination();
				
				stack.addCardToStack(cards[0]);
			}
		}
	}
	
	public boolean canFinishGameAutomatically() {
		if (isGameOver()) {
			return false;
		}
		
		if (deck.getNumberOfCardsOnDeck() != 0) return false;
		if (deck.getNumberOfCardsOnDeckStack() != 0) return false;
		
		if (tableStacks[0].getNumberOfCoveredCards() != 0) return false;
		if (tableStacks[1].getNumberOfCoveredCards() != 0) return false;
		if (tableStacks[2].getNumberOfCoveredCards() != 0) return false;
		if (tableStacks[3].getNumberOfCoveredCards() != 0) return false;
		if (tableStacks[4].getNumberOfCoveredCards() != 0) return false;
		if (tableStacks[5].getNumberOfCoveredCards() != 0) return false;
		if (tableStacks[6].getNumberOfCoveredCards() != 0) return false;
		
		return true;
	}
	
	public void finishGameAutomatically() {
		if (canFinishGameAutomatically() == false) {
			throw new IllegalArgumentException();
		}
		
		deck.setDeck(new Card[0]);
		deck.setDeckStack(new Card[0]);
		
		tableStacks[0].setStack(new Card[0]);
		tableStacks[1].setStack(new Card[0]);
		tableStacks[2].setStack(new Card[0]);
		tableStacks[3].setStack(new Card[0]);
		tableStacks[4].setStack(new Card[0]);
		tableStacks[5].setStack(new Card[0]);
		tableStacks[6].setStack(new Card[0]);
		
		BlockStack clubsStack = (BlockStack) getLocationOfCard(Card.ClubAce);
		BlockStack diamondsStack = (BlockStack) getLocationOfCard(Card.DiamondAce);
		BlockStack heartsStack = (BlockStack) getLocationOfCard(Card.HeartAce);
		BlockStack spadesStack = (BlockStack) getLocationOfCard(Card.SpadeAce);
		
		int i = 0;
		
		if (clubsStack == null) {
			while (blockStacks[i].getNumberOfCardsOnStack() != 0) i++;
			
			clubsStack = blockStacks[i];
			i++;
		}
		
		if (diamondsStack == null) {
			while (blockStacks[i].getNumberOfCardsOnStack() != 0) i++;
			
			diamondsStack = blockStacks[i];
			i++;
		}
		
		if (heartsStack == null) {
			while (blockStacks[i].getNumberOfCardsOnStack() != 0) i++;
			
			heartsStack = blockStacks[i];
			i++;
		}
		
		if (spadesStack == null) {
			while (blockStacks[i].getNumberOfCardsOnStack() != 0) i++;
			
			spadesStack = blockStacks[i];
		}
		
		clubsStack.setStack(Cards.clubs);
		diamondsStack.setStack(Cards.diamonds);
		heartsStack.setStack(Cards.hearts);
		spadesStack.setStack(Cards.spades);
	}
	
	public boolean isGameOver() {
		if (blockStacks[0].isComplete() == false) return false;
		if (blockStacks[1].isComplete() == false) return false;
		if (blockStacks[2].isComplete() == false) return false;
		if (blockStacks[3].isComplete() == false) return false;
		
		return true;
	}
	
}
