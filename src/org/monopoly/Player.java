package org.monopoly;

public abstract class Player {

	final String playerToken;
	int balance;
	Property property;
	
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
	public String toString() {
		return getToken().toString();
	}
	public void setLocation(Property currentProperty) {
		property = currentProperty;
	}
	public Property getLocation() {
		return property;
	}
	public abstract boolean buyProperty(); //no body for abstract
  public abstract boolean buyHouse();
}
