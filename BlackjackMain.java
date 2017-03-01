/*      BLACKJACK
 * 
 * 		This is a console-based blackjack game. Have fun!
 * 
 * 		Written by Chris Lapidas
 * 		Last updated: 2/27/2017
 * 
 * 		TODO:
 * 			-Front-end using Swing
 * 			-Option for user to see the probability of winning based on current cards. Could have
 * 				different probabilities for each option (hit, stay, double down). User could
 * 				pay an extra 25% of bet to see probabilities.
 * 
 */

import java.util.*;

public class BlackjackMain {

	public static void main(String[] args){
		
		//init Cards and Wallet objects
		Wallet userWallet = new Wallet(1000);
		Cards cardDeck = new Cards();
		
		System.out.println("Welcome to Blackjack!");
		
		//Scanner object used for user input in console
		Scanner userInput = new Scanner(System.in);
		
		//blackjack game loop
		boolean outerGameLoop = true;
		while(outerGameLoop){
			
			//prompt user for bet
			boolean validBet = false;
			int userBet = 0;
			
			//while loop, prompts user for bet and checks for valid input
			while (validBet == false){
				System.out.println("You have " + userWallet.getWallet() + " dollars.");
				System.out.print("Please enter a bet: ");
				
				//check if user tried to bet less than 1 or more than is in wallet
				try {
					userBet = userInput.nextInt();
					if (userBet < 1 || userBet > userWallet.getWallet()){
						System.out.println();
						System.out.println("Invalid input, please try again:");
					}
					else{
						validBet = true;
					}
					//catch non-int input
				} catch(Exception e){
					System.out.println();
					System.out.println("Invalid input, please try again:");
					userInput.next();
				}
				System.out.println();
			} //while validBet == false
			
			//initialize card deck and hands
			cardDeck.shuffleDeck();
			cardDeck.dealHands();
			
			//inner game loop, used to continue prompting user for options while hand is in play
			boolean innerGameLoop = true;
			
			//if user hits blackjack on deal
			if(cardDeck.userHandTotal() == 21){
				innerGameLoop = false;
				userBet *= 2;
				System.out.println("Blackjack!!");
			}
			
			//while loop to continue prompting user for options while hand is in play
			while(innerGameLoop){
				
				//show the user the dealt cards, prompt for choice
				cardDeck.showCards();
				System.out.println("1) Hit");
				System.out.println("2) Stay");
				System.out.println("3) Double Down");
				
				//user input 
				String userChoice = userInput.next();
				
				//hit
				if (userChoice.startsWith("1") || userChoice.startsWith("H")){
					cardDeck.dealUserCard();
					
					//inner loop ends if user hits 21 or more
					if (cardDeck.userHandTotal() > 20){
						innerGameLoop = false;
					}
				}
				
				//stay
				else if (userChoice.startsWith("2") || userChoice.startsWith("S")){
					innerGameLoop = false;
				}
				
				//double down
				else if (userChoice.startsWith("3") || userChoice.startsWith("D")){
					cardDeck.dealUserCard();
					userBet *= 2;
					innerGameLoop = false;
				}
				
				else{
					System.out.println("Invalid input, please try again:");
				}
				System.out.println();
				
			} //while innerGameLoop == true
			
			//determineWinner() deals cards to dealer if needed and checks to see who won
			//GameResultEnum is WIN, LOSE, or DRAW
			GameResultEnum gameResult = cardDeck.determineWinner();
			
			//show user all cards after hand is over
			cardDeck.showAllCards();
			
			//user wins
			if (gameResult == GameResultEnum.WIN){
				System.out.println("Congratulations, You Win!");
				userWallet.walletWin(userBet);
			}
			
			//user loses
			else if (gameResult == GameResultEnum.LOSE){
				System.out.println("You Lose!");
				userWallet.walletLose(userBet);
			}
			
			//draw
			else{
				System.out.println("Draw!");
			}
			
			//user presses enter to continue outerGameLoop
			System.out.println("Press enter to continue...");
	        	try{
	            System.in.read();
	        }  
	        catch(Exception e){}
			 
		}//while outerGameLoop
	}
}




