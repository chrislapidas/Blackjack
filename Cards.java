import java.util.*;

public class Cards {
	
	//array of strings that represent card values
	String[] cards = {"A", "A", "A", "A", "2", "2", "2", "2", "3", "3", "3", "3", "4", "4", "4", "4",
			"5", "5", "5", "5", "6", "6", "6", "6", "7", "7", "7", "7", "8", "8", "8", "8", 
			"9", "9", "9", "9", "T", "T", "T", "T", "J", "J", "J", "J", "Q", "Q", "Q", "Q",
			"K", "K", "K", "K", "A", "A", "A", "A"};
	
	//list (to be ArrayList) representing the deck of cards. the cards string array will be copied over
	//to cardDeck every time the deck is shuffled (game loop goes back to top)
	private List<String> cardDeck;
	
	//array of cards in dealer's hand
	private List<String> dealerHand;
	
	//array of cards in user's hand
	private List<String> userHand;
	
	Random rand = new Random();
	int randNum;
	
	//adds a random card (string) from cardDeck to dealerHand
	public void dealDealerCard(){
		randNum = rand.nextInt(cardDeck.size());
		dealerHand.add(cardDeck.remove(randNum));
	}
	
	//adds a random card (string) from cardDeck to userHand
	public void dealUserCard(){
		randNum = rand.nextInt(cardDeck.size());
		userHand.add(cardDeck.remove(randNum));
	}
	
	//calls functions to deal two cards to dealer and user each
	public void dealHands(){
		dealDealerCard();
		dealDealerCard();
		dealUserCard();
		dealUserCard();
	}
	
	//sets cardDeck to array list of cards (resets deck to original array of 52 strings)
	//also resets user and dealer hand arrays to new empty ArrayLists
	public void shuffleDeck(){
		cardDeck = new ArrayList<String>(Arrays.asList(cards));
		userHand = new ArrayList<String>();
		dealerHand = new ArrayList<String>();
	}
	
	//display cards already dealt in console to user
	//this one hides one dealer card similar to an actual blackjack game
	public void showCards(){
		System.out.println("Dealer: ? " + dealerHand.get(1) + "  Total: ?");
		System.out.print("You:   ");
		for(int i = 0; i < userHand.size(); i++){
			System.out.print(" " + userHand.get(i));
		}
		System.out.println("  Total: " + userHandTotal());
		
	}
	
	//display cards already dealt in console to user
	//this one does not hide any dealer cards
	public void showAllCards(){
		System.out.print("Dealer: ");
		for(int i = 0; i < dealerHand.size(); i++){
			System.out.print(" " + dealerHand.get(i));
		}
		System.out.println("  Total: " + dealerHandTotal());
		System.out.print("You:   ");
		for(int i = 0; i < userHand.size(); i++){
			System.out.print(" " + userHand.get(i));
		}
		System.out.println("  Total: " + userHandTotal());
	}
	
	//returns total value of cards in user's hand
	public int userHandTotal(){
		return (handTotal(userHand));
	}
	
	//returns total value of cards in dealer's hand
	public int dealerHandTotal(){
		return (handTotal(dealerHand));
	}

	//adds up total value of cards
	//input is an array of Strings (either dealerHand or userHand)
	//output is int which is total value of cards in input hand
	public int handTotal(List<String> inputHand){
		int handTotal = 0;
		int acesWorthEleven = 0;
		for(int i = 0; i < inputHand.size(); i++){
			
			switch (inputHand.get(i)){
				case "A": handTotal += 11;
					acesWorthEleven++;
					break;
				case "2": handTotal += 2;
					break;
				case "3": handTotal += 3;
					break;
				case "4": handTotal += 4;
					break;
				case "5": handTotal += 5;
					break;
				case "6": handTotal += 6;
					break;
				case "7": handTotal += 7;
					break;
				case "8": handTotal += 8;
					break;
				case "9": handTotal += 9;
					break;
				case "T":
				case "J":
				case "Q":
				case "K": handTotal += 10;
					break;
			}//switch
			
			if (handTotal > 21 && acesWorthEleven > 0){
				handTotal -= 10;
				acesWorthEleven--;
			}
			
		}//for
		
		return handTotal;
	}
	
	//continues to add cards to dealer's hand until dealer has at least 
	//17 points worth of cards in hand
	public void dealerFinishesHand(){
		while (dealerHandTotal() < 17){
			dealDealerCard();
		}
	}
	
	
	//GameResultEnum: WIN, LOSE, or DRAW
	//this function is used after user no longer has options during the hand
	//returns enum which is the reult of the game
	public GameResultEnum determineWinner(){
		
		int userTotal = userHandTotal();
		int dealerTotal = dealerHandTotal();
		
		if (userTotal == 21 && userHand.size() == 2){
			if(dealerTotal == 21){
				return GameResultEnum.DRAW;
			}
			else{
				return GameResultEnum.WIN;
			}
		}
		
		//check if user busted (got over 21 points)
		if (userTotal > 21){
			return GameResultEnum.LOSE;
		}
		
		//otherwise deal cards to dealer until dealer has at least 17 points
		//worth of cards in hand
		else{
			dealerFinishesHand();
			dealerTotal = dealerHandTotal();
		}
		
		//check if dealer busts
		if (dealerTotal > 21){
			return GameResultEnum.LOSE;
		}
		
		//otherwise check if user beat dealer
		else if (userTotal > dealerTotal){
			return GameResultEnum.WIN;
		}
		
		//if dealer beat user
		else if (dealerTotal > userTotal){
			return GameResultEnum.LOSE;
		}
		
		//if dealer and user tied
		else if (userTotal == dealerTotal){
			return GameResultEnum.DRAW;
		}
		
		//otherwise user lost
		else{
			return GameResultEnum.LOSE;
		}
	}

}
