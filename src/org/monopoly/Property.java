package org.monopoly;

public class Property {
	
	int buyCost, rentCost;
	String propertyName, propertyOwner;

	public Property(String name, int buyValue, int rentValue, String playerToken) {
		propertyName = name;
		buyCost = buyValue;
		rentCost = rentValue;
		propertyOwner = playerToken;
	}
	public void setOwner(String playerToken) {
		propertyOwner = playerToken;
	}
	public void setPropertyOwner(String playerToken) {
		propertyOwner = playerToken;
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
	public String getPropertyOwner() {
		return propertyOwner;
	}
}
