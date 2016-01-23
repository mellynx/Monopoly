package org.monopoly;

public class Player {

	String playerToken;
	int balance;
	
	public Player(String token, int money) {
		playerToken = token;
		balance = money;
	}
	public String getToken() {
		return playerToken;
	}
	public void setBalance(int money) {
		balance = money;
	}
	public int getBalance() {
		return balance;
	}
}
