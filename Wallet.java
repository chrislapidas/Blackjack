//Wallet class, used to keep track of user's money for betting
public class Wallet {
	
	private int cash;
	
	//constructor
	public Wallet(int startingCash){
		cash = startingCash;
	}
	
	public int getWallet(){
		return cash;
	}
	
	public void setWallet(int newWallet){
		cash = newWallet;
	}

	public void walletWin(int winCash){
		cash += winCash;
	}
	
	public void walletLose(int loseCash){
		cash -= loseCash;
	}
}
