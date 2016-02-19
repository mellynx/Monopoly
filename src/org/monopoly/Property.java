package org.monopoly;

public class Property {
	
	final int buyCost, rentCost, houseCost;
	final String propertyName;
	Player propertyOwner;

	public Property(String name, int buyValue, int rentValue, int houseValue) {
		propertyName = name;
		buyCost = buyValue;
		rentCost = rentValue;
		houseCost = houseValue;
	}
	public void setPropertyOwner(Player playerOwner) {
		propertyOwner = playerOwner;
	}
	public String getName() {
		return propertyName;
	}
	public int getBuyCost() {
		return buyCost;
	}
	public int getRentCost() {
		return rentCost;
	}
	public Player getPropertyOwner() {
		return propertyOwner;
	}
	public String toString() {
		return getName().toString();
	}
}
