package org.monopoly;

public class Property {
	
	final int buyCost, houseCost;
	final String name;
	Player owner;
	int numberOfHouses;

	public Property(String name, int buyCost, int houseCost, int numberOfHouses) {
		this.name = name;
		this.buyCost = buyCost;
		this.houseCost = houseCost;
		this.numberOfHouses = numberOfHouses;
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
	public int getRentCost() {
		return buyCost;	// **just a placeholder
		// calculation based on number of houses property has
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
}

