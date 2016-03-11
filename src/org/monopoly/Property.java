package org.monopoly;

import java.util.ArrayList;

public class Property {
	
	final int buyCost, houseCost, mortgageCost;
	final String name;
	Player owner;
	int numberOfHouses;
	RentSchedule rentSchedule;
	boolean buyable = true;
	boolean mortgageStatus = false;
	RentType rentType; // below, enum only tells us what the possible categories for enum are. Here, we must declare the class and variable
	
	enum RentType {
	  RAILROAD, UTILITY, REGULAR, NONE;
	 }
	
	// constructor for the main properties
	public Property(String name, int buyCost, int houseCost, int mortgageCost, RentSchedule rentSchedule) { 
		this.name = name;
		this.buyCost = buyCost;
		this.houseCost = houseCost;
		this.mortgageCost = mortgageCost;
		this.numberOfHouses = 0;
		this.rentSchedule = rentSchedule;
		rentType = RentType.REGULAR;  // this is weird format. yeah get used to it. You have to put RentType. in all the parameters passed
		// you can initialize stuff in a constructor without taking it as a parameters. number of houses starts at 0 for all properties
	}
	// constructor for railroads
	public Property(String name, RentType rentType, RentSchedule rentSchedule) {
	  this.name = name;
	  this.buyCost = 200;
	  this.mortgageCost = 100;
	  this.rentType = rentType;
	  this.rentSchedule = rentSchedule;
	  houseCost = 0;
	}
	// constructor for utilities
	public Property(String name, RentType rentType) {
		this.name = name;
		this.buyCost = 150;
		this.mortgageCost = 75;
		this.rentType = rentType;
		houseCost = 0;
	}
	// constructor for board properties (like "Go")
	// buyable is now default true for all properties except board properties
	// houseCost and buyCost must be initialized because they are final, which means they can only be set once. AKA they must be set here
	public Property(String name) {
	  this.name = name;
	  buyable = false;
	  houseCost = 0;
	  buyCost = 0;
	  mortgageCost = 0;
	  rentType = RentType.NONE;
	}
	
	
	public void setPropertyOwner(Player owner) {
		this.owner = owner;	// using this in setters and getters
	}
	
	
	public String getName() {
		return name;
	}
	public int getBuyCost() {
		return buyCost;
	}
	public Player getPropertyOwner() {
		return owner;
	}
	public int getHouseCost() {
		return houseCost;
	}
	public int getNumberOfHouses() {
		return numberOfHouses;
	}
	public RentType getRentType() { // figure out if a property is regular, utility, railroad, etc
		return rentType;
	}
	public boolean getBuyableStatus() { // separates board properties from all other properties
		return buyable;
	}
	public boolean getMortgageStatus() {
		return mortgageStatus;
	}
	public int getMortgageCost() {
		return mortgageCost;
	}
	public int getLocationIndex(ArrayList<Property> boardOfProperties) {
		// below, access the current property by using 'this'
		int locationIndex = 0;
		
		for (int i = 0; i < boardOfProperties.size(); i++) {
			if (this == boardOfProperties.get(i)) {
				locationIndex = i;
			}
		}
		return locationIndex;
	}
	public int getRentCost(int dice) {
		if (rentType == RentType.REGULAR) {
			return rentSchedule.getRent(getNumberOfHouses());
		}
		else if (rentType == RentType.RAILROAD) {
			int railroadCount = 0;
			Player owner = getPropertyOwner();
			
			for (int i = 0; i < owner.getPropertiesOwned().size(); i++) {
				if (owner.getPropertiesOwned().get(i).getRentType() == RentType.RAILROAD) {
					railroadCount++;
				}
			}
			return rentSchedule.getRent(railroadCount);
		}
		else {
			int utilityCount = 0;
			Player owner = getPropertyOwner();
			
			for (int j = 0; j< owner.getPropertiesOwned().size(); j++) {
				if (owner.getPropertiesOwned().get(j).getRentType() == RentType.UTILITY) {
					utilityCount++;
				}
			}
			if (utilityCount == 1) {
				return dice * 4;
			}
			else {
				return dice * 10;
			}
		}
	}
	
	
	public void addOneHouse() {
		numberOfHouses++;
	}
	public void changeMortgageStatus() {
		if (getMortgageStatus()) {
			mortgageStatus = false;
		}
		else {
			mortgageStatus = true;
		}
	}
	public String toString() {
		return getName();
	}
	
}

