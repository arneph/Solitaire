package model;

public class Cards {
	public static enum Card {
		None,
		ClubAce,
		Club2,
		Club3,
		Club4,
		Club5,
		Club6,
		Club7,
		Club8,
		Club9,
		Club10,
		ClubJack,
		ClubQueen,
		ClubKing,
		DiamondAce,
		Diamond2,
		Diamond3,
		Diamond4,
		Diamond5,
		Diamond6,
		Diamond7,
		Diamond8,
		Diamond9,
		Diamond10,
		DiamondJack,
		DiamondQueen,
		DiamondKing,
		HeartAce,
		Heart2,
		Heart3,
		Heart4,
		Heart5,
		Heart6,
		Heart7,
		Heart8,
		Heart9,
		Heart10,
		HeartJack,
		HeartQueen,
		HeartKing,
		SpadeAce,
		Spade2,
		Spade3,
		Spade4,
		Spade5,
		Spade6,
		Spade7,
		Spade8,
		Spade9,
		Spade10,
		SpadeJack,
		SpadeQueen,
		SpadeKing
	}
	
	public static enum CardColor {
		None,
		Black,
		Red
	}
	
	public static final Card None = Card.None;
	
	public static final Card[] Aces = new Card[] {Card.ClubAce, 
												  Card.DiamondAce, 
												  Card.HeartAce, 
												  Card.SpadeAce};
	public static final Card[] Twos = new Card[] {Card.Club2, 
												  Card.Diamond2, 
												  Card.Heart2, 
												  Card.Spade2};
	public static final Card[] Threes = new Card[] {Card.Club3, 
												  	Card.Diamond3, 
												  	Card.Heart3, 
												  	Card.Spade3};
	public static final Card[] fours = new Card[] {Card.Club4, 
												   Card.Diamond4, 
												   Card.Heart4, 
												   Card.Spade4};
	public static final Card[] fives = new Card[] {Card.Club5, 
												   Card.Diamond5, 
												   Card.Heart5, 
												   Card.Spade5};
	public static final Card[] sixes = new Card[] {Card.Club6, 
												   Card.Diamond6, 
												   Card.Heart6, 
												   Card.Spade6};
	public static final Card[] sevens = new Card[] {Card.Club7, 
												    Card.Diamond7, 
												    Card.Heart7, 
												    Card.Spade7};
	public static final Card[] eights = new Card[] {Card.Club8, 
												    Card.Diamond8, 
												    Card.Heart8, 
												    Card.Spade8};
	public static final Card[] nines = new Card[] {Card.Club9, 
												   Card.Diamond9, 
												   Card.Heart9, 
												   Card.Spade9};
	public static final Card[] tens = new Card[] {Card.Club10, 
												  Card.Diamond10, 
												  Card.Heart10, 
												  Card.Spade10};
	public static final Card[] jacks = new Card[] {Card.ClubJack, 
												   Card.DiamondJack, 
												   Card.HeartJack, 
												   Card.SpadeJack};
	public static final Card[] queens = new Card[] {Card.ClubQueen, 
												  	Card.DiamondQueen, 
												  	Card.HeartQueen, 
												  	Card.SpadeQueen};
	public static final Card[] kings = new Card[] {Card.ClubKing, 
												   Card.DiamondKing, 
												   Card.HeartKing, 
												   Card.SpadeKing};
	
	public static final Card[] clubs = new Card[] {Card.ClubAce, 
		   	   									   Card.Club2, 
		   	   									   Card.Club3, 
		   	   									   Card.Club4, 
		   	   									   Card.Club5, 
		   	   									   Card.Club6, 
		   	   									   Card.Club7, 
		   	   									   Card.Club8, 
		   	   									   Card.Club9, 
		   	   									   Card.Club10, 
		   	   									   Card.ClubJack, 
		   	   									   Card.ClubQueen, 
		   	   									   Card.ClubKing};
	public static final Card[] diamonds = new Card[] {Card.DiamondAce, 
		   	   									   	  Card.Diamond2, 
		   	   									   	  Card.Diamond3, 
		   	   									   	  Card.Diamond4, 
		   	   									   	  Card.Diamond5, 
		   	   									   	  Card.Diamond6, 
		   	   									   	  Card.Diamond7, 
		   	   									   	  Card.Diamond8, 
		   	   									   	  Card.Diamond9, 
		   	   									   	  Card.Diamond10, 
		   	   									   	  Card.DiamondJack, 
		   	   									   	  Card.DiamondQueen, 
		   	   									   	  Card.DiamondKing};
	public static final Card[] hearts = new Card[] {Card.HeartAce, 
		   	   									    Card.Heart2, 
		   	   									    Card.Heart3, 
		   	   									    Card.Heart4, 
		   	   									    Card.Heart5, 
		   	   									    Card.Heart6, 
		   	   									    Card.Heart7, 
		   	   									    Card.Heart8, 
		   	   									    Card.Heart9, 
		   	   									    Card.Heart10, 
		   	   									    Card.HeartJack, 
		   	   									    Card.HeartQueen, 
		   	   									    Card.HeartKing};
	public static final Card[] spades = new Card[] {Card.SpadeAce, 
		   	   									    Card.Spade2, 
		   	   									    Card.Spade3, 
		   	   									    Card.Spade4, 
		   	   									    Card.Spade5, 
		   	   									    Card.Spade6, 
		   	   									    Card.Spade7, 
		   	   									    Card.Spade8, 
		   	   									    Card.Spade9, 
		   	   									    Card.Spade10, 
		   	   									    Card.SpadeJack, 
		   	   									    Card.SpadeQueen, 
		   	   									    Card.SpadeKing};
	
	public static final Card[] blacks = new Card[] {Card.ClubAce, 
				   								    Card.Club2, 
				   								    Card.Club3, 
				   								    Card.Club4, 
				   								    Card.Club5, 
				   								    Card.Club6, 
				   								    Card.Club7, 
				   								    Card.Club8, 
				   								    Card.Club9, 
				   								    Card.Club10, 
				   								    Card.ClubJack, 
				   								    Card.ClubQueen, 
				   								    Card.ClubKing,
				   								    Card.SpadeAce, 
				   								    Card.Spade2, 
				   								    Card.Spade3, 
				   								    Card.Spade4, 
				   								    Card.Spade5, 
				   								    Card.Spade6, 
				   								    Card.Spade7, 
				   								    Card.Spade8, 
				   								    Card.Spade9, 
				   								    Card.Spade10, 
				   								    Card.SpadeJack, 
				   								    Card.SpadeQueen, 
				   								    Card.SpadeKing};
	public static final Card[] reds = new Card[] {Card.DiamondAce, 
			   	  								  Card.Diamond2, 
			   	  								  Card.Diamond3, 
			   	  								  Card.Diamond4, 
			   	  								  Card.Diamond5, 
			   	  								  Card.Diamond6, 
			   	  								  Card.Diamond7, 
			   	  								  Card.Diamond8, 
			   	  								  Card.Diamond9, 
			   	  								  Card.Diamond10, 
			   	  								  Card.DiamondJack, 
			   	  								  Card.DiamondQueen, 
			   	  								  Card.DiamondKing,
			   	  								  Card.HeartAce, 
			   	  								  Card.Heart2, 
			   	  								  Card.Heart3, 
			   	  								  Card.Heart4, 
			   	  								  Card.Heart5, 
			   	  								  Card.Heart6, 
			   	  								  Card.Heart7, 
			   	  								  Card.Heart8, 
			   	  								  Card.Heart9, 
			   	  								  Card.Heart10, 
			   	  								  Card.HeartJack, 
			   	  								  Card.HeartQueen, 
			   	  								  Card.HeartKing};
	
	public static boolean isNone(Card c) {
		return (Card.None == c);
	}
	
	public static boolean isAce(Card c) {
		if (Card.ClubAce == c) {
			return true;
		}else if (Card.DiamondAce == c) {
			return true;
		}else if (Card.HeartAce == c) {
			return true;
		}else if (Card.SpadeAce == c) {
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isTwo(Card c) {
		if (Card.Club2 == c) {
			return true;
		}else if (Card.Diamond2 == c) {
			return true;
		}else if (Card.Heart2 == c) {
			return true;
		}else if (Card.Spade2 == c) {
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isThree(Card c) {
		if (Card.Club3 == c) {
			return true;
		}else if (Card.Diamond3 == c) {
			return true;
		}else if (Card.Heart3 == c) {
			return true;
		}else if (Card.Spade3 == c) {
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isFour(Card c) {
		if (Card.Club4 == c) {
			return true;
		}else if (Card.Diamond4 == c) {
			return true;
		}else if (Card.Heart4 == c) {
			return true;
		}else if (Card.Spade4 == c) {
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isFive(Card c) {
		if (Card.Club5 == c) {
			return true;
		}else if (Card.Diamond5 == c) {
			return true;
		}else if (Card.Heart5 == c) {
			return true;
		}else if (Card.Spade5 == c) {
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isSix(Card c) {
		if (Card.Club6 == c) {
			return true;
		}else if (Card.Diamond6 == c) {
			return true;
		}else if (Card.Heart6 == c) {
			return true;
		}else if (Card.Spade6 == c) {
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isSeven(Card c) {
		if (Card.Club7 == c) {
			return true;
		}else if (Card.Diamond7 == c) {
			return true;
		}else if (Card.Heart7 == c) {
			return true;
		}else if (Card.Spade7 == c) {
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isEight(Card c) {
		if (Card.Club8 == c) {
			return true;
		}else if (Card.Diamond8 == c) {
			return true;
		}else if (Card.Heart8 == c) {
			return true;
		}else if (Card.Spade8 == c) {
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isNine(Card c) {
		if (Card.Club9 == c) {
			return true;
		}else if (Card.Diamond9 == c) {
			return true;
		}else if (Card.Heart9 == c) {
			return true;
		}else if (Card.Spade9 == c) {
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isTen(Card c) {
		if (Card.Club10 == c) {
			return true;
		}else if (Card.Diamond10 == c) {
			return true;
		}else if (Card.Heart10 == c) {
			return true;
		}else if (Card.Spade10 == c) {
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isJack(Card c) {
		if (Card.ClubJack == c) {
			return true;
		}else if (Card.DiamondJack == c) {
			return true;
		}else if (Card.HeartJack == c) {
			return true;
		}else if (Card.SpadeJack == c) {
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isQueen(Card c) {
		if (Card.ClubQueen == c) {
			return true;
		}else if (Card.DiamondQueen == c) {
			return true;
		}else if (Card.HeartQueen == c) {
			return true;
		}else if (Card.SpadeQueen == c) {
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isKing(Card c) {
		if (Card.ClubKing == c) {
			return true;
		}else if (Card.DiamondKing == c) {
			return true;
		}else if (Card.HeartKing == c) {
			return true;
		}else if (Card.SpadeKing == c) {
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isClub(Card c) {
		if (Card.ClubAce == c) {
			return true;
		}else if (Card.Club2 == c) {
			return true;
		}else if (Card.Club3 == c) {
			return true;
		}else if (Card.Club4 == c) {
			return true;
		}else if (Card.Club5 == c) {
			return true;
		}else if (Card.Club6 == c) {
			return true;
		}else if (Card.Club7 == c) {
			return true;
		}else if (Card.Club8 == c) {
			return true;
		}else if (Card.Club9 == c) {
			return true;
		}else if (Card.Club10 == c) {
			return true;
		}else if (Card.ClubJack == c) {
			return true;
		}else if (Card.ClubQueen == c) {
			return true;
		}else if (Card.ClubKing == c) {
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isDiamond(Card c) {
		if (Card.DiamondAce == c) {
			return true;
		}else if (Card.Diamond2 == c) {
			return true;
		}else if (Card.Diamond3 == c) {
			return true;
		}else if (Card.Diamond4 == c) {
			return true;
		}else if (Card.Diamond5 == c) {
			return true;
		}else if (Card.Diamond6 == c) {
			return true;
		}else if (Card.Diamond7 == c) {
			return true;
		}else if (Card.Diamond8 == c) {
			return true;
		}else if (Card.Diamond9 == c) {
			return true;
		}else if (Card.Diamond10 == c) {
			return true;
		}else if (Card.DiamondJack == c) {
			return true;
		}else if (Card.DiamondQueen == c) {
			return true;
		}else if (Card.DiamondKing == c) {
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isHeart(Card c) {
		if (Card.HeartAce == c) {
			return true;
		}else if (Card.Heart2 == c) {
			return true;
		}else if (Card.Heart3 == c) {
			return true;
		}else if (Card.Heart4 == c) {
			return true;
		}else if (Card.Heart5 == c) {
			return true;
		}else if (Card.Heart6 == c) {
			return true;
		}else if (Card.Heart7 == c) {
			return true;
		}else if (Card.Heart8 == c) {
			return true;
		}else if (Card.Heart9 == c) {
			return true;
		}else if (Card.Heart10 == c) {
			return true;
		}else if (Card.HeartJack == c) {
			return true;
		}else if (Card.HeartQueen == c) {
			return true;
		}else if (Card.HeartKing == c) {
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isSpade(Card c) {
		if (Card.SpadeAce == c) {
			return true;
		}else if (Card.Spade2 == c) {
			return true;
		}else if (Card.Spade3 == c) {
			return true;
		}else if (Card.Spade4 == c) {
			return true;
		}else if (Card.Spade5 == c) {
			return true;
		}else if (Card.Spade6 == c) {
			return true;
		}else if (Card.Spade7 == c) {
			return true;
		}else if (Card.Spade8 == c) {
			return true;
		}else if (Card.Spade9 == c) {
			return true;
		}else if (Card.Spade10 == c) {
			return true;
		}else if (Card.SpadeJack == c) {
			return true;
		}else if (Card.SpadeQueen == c) {
			return true;
		}else if (Card.SpadeKing == c) {
			return true;
		}else{
			return false;
		}
	}
	
	public static CardColor getColor(Card c) {
		if (isClub(c) || isSpade(c)) {
			return CardColor.Black;
		}else if (isDiamond(c) || isHeart(c)) {
			return CardColor.Red;
		}else{
			return CardColor.None;
		}
	}
	
	public static boolean isBlack(Card c) {
		if (isClub(c)) {
			return true;
		}else if (isSpade(c)) {
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isRed(Card c) {
		if (isDiamond(c)) {
			return true;
		}else if (isHeart(c)) {
			return true;
		}else{
			return false;
		}
	}
	
	public static Card getBlockPredecessor(Card c) {
		if (isNone(c)) {
			return Card.None;
		}else if (isAce(c)) {
			return Card.None;
		}else{
			return Card.values()[c.ordinal() - 1];
		}
	}
	
	public static Card getBlockSuccessor(Card c) {
		if (isNone(c)) {
			return Card.None;
		}else if (isKing(c)) {
			return Card.None;
		}else{
			return Card.values()[c.ordinal() + 1];
		}
	}
	
	public static boolean isBlockPredecessor(Card a, Card b) {
		if (isNone(a) || isNone(b)) {
			return false;
		}
		
		return (a == getBlockPredecessor(b));
	}
	
	public static boolean isBlockSuccessor(Card a, Card b) {
		if (isNone(a) || isNone(b)) {
			return false;
		}
		
		return (a == getBlockSuccessor(b));
	}
	
	public static boolean isOrderPredecessor(Card a, Card b) {
		if (isNone(a) || isNone(b)) {
			return false;
		}else if (isAce(b)) {
			return false;
		}
		
		return ((a.ordinal() - 1) % 13) + 1 == ((b.ordinal() - 1) % 13);
	}
	
	public static boolean isOrderSuccessor(Card a, Card b) {
		if (isNone(a) || isNone(b)) {
			return false;
		}else if (isKing(b)) {
			return false;
		}
		
		return ((a.ordinal() - 1) % 13) - 1 == ((b.ordinal() - 1) % 13);
	}
	
	public static boolean isTableStackPredecessor(Card a, Card b) {
		if (isOrderPredecessor(a, b) == false) {
			return false;
		}
		
		if (isRed(a) && isBlack(b)) {
			return true;
		}else if (isBlack(a) && isRed(b)) {
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isTableStackSuccessor(Card a, Card b) {
		if (isOrderSuccessor(a, b) == false) {
			return false;
		}
		
		if (isRed(a) && isBlack(b)) {
			return true;
		}else if (isBlack(a) && isRed(b)) {
			return true;
		}else{
			return false;
		}
	}
	
}
