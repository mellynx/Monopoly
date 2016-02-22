package org.monopoly;

import java.util.ArrayList;

public abstract class Player {

	final String playerToken;
	int balance;
	Property property;
	ArrayList<Property> possibleHouses;
	
	public Player(String playerToken) {
		this.playerToken = playerToken;
		this.balance = 1000;
		possibleHouses = new ArrayList<>();	// the list of places a player can buy houses at, which starts at empty. Here we're setting the class variable
	}
	public void setBalance(int money) {
		balance = money;
	}
	public void setLocation(Property currentProperty) {
		property = currentProperty;
	}
	public void addToPossibleHouseList (Property property) { // the list of properties on which a player is eligible to build a house
		possibleHouses.add(property);
	}
	public String getToken() {
		return playerToken;
	}
	public int getBalance() {
		return balance;
	}
	public String toString() {
		return getToken().toString();
	}
	public Property getLocation() {
		return property;
	}
	public ArrayList<Property> getPossibleHouseList() {
		return possibleHouses;
	}
	public abstract boolean buyProperty(); //no body for abstract methods
	public abstract boolean buyHouse(Property property);
	public abstract void purchaseAHouse();
}
