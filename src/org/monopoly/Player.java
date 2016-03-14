package org.monopoly;

import java.util.ArrayList;
import java.util.Set;

public abstract class Player {

	final String playerToken;
	int balance, jailTime;
	boolean getOutOfJailFreeCard;
	Property position;
	ArrayList<Set<Property>> housableSets; //A
	ArrayList<Property> propertiesOwned;
	ArrayList<Property> mortgagedProperties = new ArrayList<>();
	
	public Player(String playerToken) {
		this.playerToken = playerToken;
		this.balance = 1500;
		this.jailTime = -1;
		this.getOutOfJailFreeCard = false;
		housableSets = new ArrayList<>();	// (A) the list of sets a player can buy houses at, which starts at empty. Here we're setting the class variable
		propertiesOwned = new ArrayList<>();
	}
	public void setBalance(int money) {
		balance = money;
	}
	public void setLocation(Property currentProperty) {
		position = currentProperty;
	}
	public void setJailTime(int jailCount) {
		jailTime = jailCount;
	}
	public void setGetOutOfJailFreeCard(boolean trueOrFalse) {
		getOutOfJailFreeCard = trueOrFalse;
	}
	
	
	public void addToHousableSets (Set<Property> a) { // the list of properties on which a player is eligible to build a house
		housableSets.add(a);
	}
	public void addToPropertiesOwnedList (Property property) {
		propertiesOwned.add(property);
	}
	public void addToMortgagedProperties (Property property) {
		mortgagedProperties.add(property);
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
	public int getJailTime() {
		return jailTime;
	}
	public boolean getGetOutOfJailFreeCard() {
		return getOutOfJailFreeCard;
	}
	public ArrayList<Set<Property>> getHousableSetList() {
		return housableSets;
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
		System.out.println(this + " bought a house on " + property);
		System.out.println(property + " now has " + property.getNumberOfHouses() + " houses.");
		System.out.println(this + " has $" + getBalance() + " left.");
	}
	public void whatHappensWhenYouMortgage(Property property) {
		addToMortgagedProperties(property); // use the add method, don't add directly to the list  
		property.changeMortgageStatus();
		int money = getBalance() + property.getMortgageCost();
		setBalance(money);
		System.out.println(this + " has mortgaged " + property);
		System.out.println(this + " now has $" + getBalance());
	}
	public void whatHappensWhenYouUnmortgage(Property property) {
		removeFromMortgagedProperties(property);
		property.changeMortgageStatus();
		int money = getBalance() - property.getMortgageCost();
		setBalance(money);
		System.out.println(this + " has unmortgaged " + property);
		System.out.println(this + " has $" + getBalance() + " left.");
	}
	
	//use this same prompt as a check for buying properties, houses, AND mortgaging properties
	public abstract boolean doYouWantToDoThis(String prompt) throws InterruptedException; //no body for abstract methods

	public abstract Property buyHouseB(ArrayList<Property> currentHousableProperties) throws InterruptedException;
	
	public abstract Property mortgagePropertiesB (ArrayList<Property> propertiesOwned) throws InterruptedException;
	
	public abstract Property unmortgageB (ArrayList<Property> mortgagedProperties) throws InterruptedException;
}

