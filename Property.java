package org.monopoly;

public class Property {
	
	final int buyCost, rentCost, houseCost;
	final String name;
	Player owner;
	int numberOfHouses;

	public Property(String name, int buyCost, int rentCost, int houseCost, int numberOfHouses) {
		this.name = name;
		this.buyCost = buyCost;
		this.rentCost = rentCost;
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
		return rentCost;
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

