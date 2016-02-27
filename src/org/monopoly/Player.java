package org.monopoly;

import java.util.ArrayList;
import java.util.Set;

public abstract class Player {

	final String playerToken;
	int balance;
	Property position;
	ArrayList<Set<Property>> possibleHouses;
	ArrayList<Property> propertiesOwned;
	ArrayList<Property> mortgagedProperties = new ArrayList<>();
	
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
		position = currentProperty;
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
	public void removeFromPropertiesOwnedList (Property property) {
    propertiesOwned.remove(property);
  }
  public void removeFromMortgagedProperties (Property property) {
    mortgagedProperties.remove(property);
  }
	public String getToken() {
		return playerToken;
	}
	public int getBalance() {
		return balance;
	}
	public Property getLocation() {
		return position;
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
	public void whatHappensWhenYouBuyHouse(Property property) {
	  property.addOneHouse();
	  int money = getBalance() - property.getHouseCost();
	  setBalance(money);
	  System.out.println("Player has $" + getBalance() + " left.");
	}
	public void whatHappensWhenYouMortgage(Property property) {
	  System.out.println("Mortgaged: " + property);
	  addToMortgagedProperties(property); // use the add method, don't add directly to the list  
	  removeFromPropertiesOwnedList(property);
	  property.changeMortgageStatus();
	  int money = getBalance() + property.getMortgageCost();
	  setBalance(money);
	  System.out.println("Player now has $" + getBalance());
	}
	public abstract boolean buyProperty(); //no body for abstract methods
	public abstract Property buyHouse(ArrayList<Property> currentHousableProperties);
	public abstract Property mortgageProperties (ArrayList<Property> propertiesOwned);
	public abstract boolean checkIfYourWantToMortgageProperties (String prompt);
}

