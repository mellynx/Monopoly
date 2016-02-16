package org.monopoly;

public class Property {
	
	final int buyCost, rentCost;
	final String propertyName;
	Player propertyOwner;

	public Property(String name, int buyValue, int rentValue) {
		propertyName = name;
		buyCost = buyValue;
		rentCost = rentValue;
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
