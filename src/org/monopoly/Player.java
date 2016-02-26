package org.monopoly;

import java.util.ArrayList;
import java.util.Set;

public abstract class Player {

	final String playerToken;
	int balance;
	Property property;
	ArrayList<Set<Property>> possibleHouses;
	ArrayList<Property> propertiesOwned;
	ArrayList<Property> mortgagedProperties;
	
	public Player(String playerToken) {
		this.playerToken = playerToken;
		this.balance = 1500;
		possibleHouses = new ArrayList<>();	// the list of sets a player can buy houses at, which starts at empty. Here we're setting the class variable
		propertiesOwned = new ArrayList<>();
	}
	public void setBalance(int money) {
		balance = money;
	}
	public void setLocation(Property currentProperty) {
		property = currentProperty;
	}
	public void addToPossibleHouseList (Set<Property> a) { // the list of properties on which a player is eligible to build a house
		possibleHouses.add(a);
	}
	public void addToPropertiesOwnedList (Property property) {
		propertiesOwned.add(property);
	}
	public void addToMortgagedProperties (Property property) {
		mortgagedProperties.add(property);
	}
	public String getToken() {
		return playerToken;
	}
	public int getBalance() {
		return balance;
	}
	public Property getLocation() {
		return property;
	}
	public ArrayList<Set<Property>> getPossibleHouseList() {
		return possibleHouses;
	}
	public ArrayList<Property> getPropertiesOwned() {
		return propertiesOwned;
	}
	public ArrayList<Property> getMortgagedProperties() {
		return mortgagedProperties;
	}
	public String toString() {
		return getToken().toString();
	}
	public abstract boolean buyProperty(); //no body for abstract methods
	public abstract boolean buyHouse(ArrayList<Property> listOfPropertiesWhereYouCanCurrentlyBuyAHouse);
	public abstract boolean mortgageProperties (ArrayList<Property> propertiesOwned, ArrayList<Property> mortgagedProperties);
	public abstract boolean checkIfYourWantToMortgageProperties (String prompt);
}
