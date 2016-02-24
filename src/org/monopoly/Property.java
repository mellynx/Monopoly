package org.monopoly;

public class Property {
	
	final int buyCost, houseCost;
	final String name;
	Player owner;
	int numberOfHouses;
	RentSchedule rentSchedule;
	boolean buyable = true;
	RentType rentType; // below, enum only tells us what the possible categories for enum are. Here, we must declare the class and variable
	
	enum RentType {
	  RAILROAD, UTILITY, REGULAR, NONE;
	 }
	
	// constructor for the main properties
	public Property(String name, int buyCost, int houseCost, RentSchedule rentSchedule) { 
		this.name = name;
		this.buyCost = buyCost;
		this.houseCost = houseCost;
		this.numberOfHouses = 0;
		this.rentSchedule = rentSchedule;
		rentType = RentType.REGULAR;  // this is weird format. yeah get used to it. You have to put RentType. in all the parameters passed
		// you can initialize stuff in a constructor without taking it as a parameters. number of houses starts at 0 for all properties
	}
	// constructor for railroads
	public Property(String name, int buyCost, RentType rentType, RentSchedule rentSchedule) {
	  this.name = name;
	  this.buyCost = buyCost;
	  this.rentType = rentType;
	  this.rentSchedule = rentSchedule;
	  houseCost = 0;
	}
	// constructor for utilities
	public Property(String name, int buyCost, RentType rentType) {
		this.name = name;
		this.buyCost = buyCost;
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
	  rentType = RentType.NONE;
	}
	public void setPropertyOwner(Player owner) {
		this.owner = owner;	// using this in setters and getters
	}
	public void addOneHouse() {
		numberOfHouses++;
	}
	public String getName() {
		return name;
	}
	public int getBuyCost() {
		return buyCost;
	}
	public int getRentCost(int dice) {
		if (rentType == rentType.REGULAR) {
			if (getNumberOfHouses() == 1) {
				return getRentSchedule().getHouseOne();
			}
			else if (getNumberOfHouses() == 2) {
				return getRentSchedule().getHouseTwo();
			}
			else if (getNumberOfHouses() == 3) {
				return getRentSchedule().getHouseThree();
			}
			else if (getNumberOfHouses() == 4) {
				return getRentSchedule().getHouseFour();
			}
			return getRentSchedule().getHotel();
		}
		else if (rentType == rentType.RAILROAD) {
			int railroadCount = 0;
			Player owner = getPropertyOwner();
			
			for (int i = 0; i < owner.getPropertiesOwned().size(); i++) {
				if (owner.getPropertiesOwned().get(i).getRentType() == rentType.RAILROAD) {
					railroadCount++;
				}
			}
			if (railroadCount == 1) {
				return getRentSchedule().getHouseOne();
			}
			else if (railroadCount == 2) {
				return getRentSchedule().getHouseTwo();
			}
			else if (railroadCount == 3) {
				return getRentSchedule().getHouseThree();
			}
			else {
				return getRentSchedule().getHouseFour();
			}
		}
		else {
			int utilityCount = 0;
			Player owner = getPropertyOwner();
			
			for (int j = 0; j< owner.getPropertiesOwned().size(); j++) {
				if (owner.getPropertiesOwned().get(j).getRentType() == rentType.UTILITY) {
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
	public Player getPropertyOwner() {
		return owner;
	}
	public String toString() {
		return getName();
	}
	public int getHouseCost() {
		return houseCost;
	}
	public int getNumberOfHouses() {
		return numberOfHouses;
	}
	public RentSchedule getRentSchedule() {
		return rentSchedule;
	}
	public RentType getRentType() {
		return rentType;
	}
}

