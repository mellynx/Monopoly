package org.monopoly;

import java.util.ArrayList;
import java.util.Set;

public abstract class Player {

	final String playerToken;
	int balance, jailTime;
	boolean getOutOfJailFreeCard;
	Property position;
	ArrayList<Set<Property>> monopoliesList; 
	ArrayList<Property> propertiesOwned;
	ArrayList<Property> mortgagedProperties = new ArrayList<>();
	
	public Player(String playerToken) {
		this.playerToken = playerToken;
		this.balance = 1500;
		this.jailTime = -1;
		this.getOutOfJailFreeCard = false;
		monopoliesList = new ArrayList<>();	
		propertiesOwned = new ArrayList<>();
	}
	public void setBalance(int money) {
		balance = money;
	}
	public void setLocation(Property currentProperty) {
		position = currentProperty;
	}
	public void setGetOutOfJailFreeCard(boolean trueOrFalse) {
		getOutOfJailFreeCard = trueOrFalse;
	}
	public void setJailTime(int jailCount) {
		jailTime = jailCount;
	}
	
	public void addJailTime() {
		setJailTime(getJailTime() + 1);
	}
	public void addToMonopolies (Set<Property> a) { 
		monopoliesList.add(a);
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
	
	public void addMoney(int money) {
		setBalance(getBalance() + money);
	}
	public void subtractMoney(int money) {
		setBalance(getBalance() - money);
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
	public ArrayList<Set<Property>> getMonopoliesList() {
		return monopoliesList;
	}
	public ArrayList<Property> getPropertiesOwned() {
		return propertiesOwned;
	}
	public ArrayList<Property> getMortgagedProperties() {
		return mortgagedProperties;
	}
	public int getHousesPlayerOwns() {
		int houseCount = 0; 
		
		for (int i = 0; i < propertiesOwned.size(); i++) {
			houseCount += propertiesOwned.get(i).getNumberOfHouses();
		}
		houseCount = houseCount - (getHotelsPlayerOwns() * 5);
		return houseCount;
	}
	public int getHotelsPlayerOwns() {
		int hotelCount = 0;
		
		for (int i = 0; i < propertiesOwned.size(); i++) {
			if (propertiesOwned.get(i).getNumberOfHouses() == 5) {
				hotelCount += 1;
			}
		}
		return hotelCount;
	}
	public int getNetWorth() {
		int worth = 0;
		
		worth += this.getBalance();
		
		for (int i = 0; i < propertiesOwned.size(); i++) {
			worth += propertiesOwned.get(i).getBuyCost();
			
			if (propertiesOwned.get(i).getNumberOfHouses() > 0) {
				worth += propertiesOwned.get(i).getHouseCost();
			}
		}
		return worth;
	}
	
	public String toString() {
		return getToken().toString();
	}
	
	public void handleHouseBuying(Property property) {
		property.addOneHouse();
		subtractMoney(property.getHouseCost());
		System.out.println(this + " bought a house on " + property);
		System.out.println(property + " now has " + property.getNumberOfHouses() + " houses.");
		System.out.println(this + " has $" + getBalance() + " left.");
	}
	public void handleHouseSelling(Property property) {
		property.subtractOneHouse();
		// houses are sold off at half their buying price 
		addMoney(property.getHouseCost() / 2);
		System.out.println(this + " has sold a house on " + property);
		System.out.println(property + " now has " + property.getNumberOfHouses() + " houses.");
		System.out.println(this + " now has $" + getBalance());
	}
	public void handleMortgaging(Property property) {
		addToMortgagedProperties(property);   
		property.changeMortgageStatus();
		addMoney(property.getMortgageCost());
		System.out.println(this + " has mortgaged " + property);
		System.out.println(this + " now has $" + getBalance());
	}
	public void handleUnmortgaging(Property property) {
		removeFromMortgagedProperties(property);
		property.changeMortgageStatus();
		// mortgages are lifted for an additional 10% of the mortgage cost 
		subtractMoney(((property.getMortgageCost() / 10) + property.getMortgageCost()));
		System.out.println(this + " has unmortgaged " + property);
		System.out.println(this + " has $" + getBalance() + " left.");
	}
	
	//use this same prompt as a check for buying properties, houses, mortgaging properties, and jail 
	public abstract boolean chooseYesOrNo(String prompt); 

	public abstract Property selectWhereToBuyHouse (ArrayList<Property> currentHousableProperties);
	
	public abstract Property selectWhereToSellHouse (ArrayList<Property> currentSellableProperties);
	
	public abstract Property selectWhatToMortgage (ArrayList<Property> mortgageableProperties);
	
	public abstract Property selectWhatToUnmortgage (ArrayList<Property> mortgagedProperties);
}

