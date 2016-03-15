package org.monopoly;

import java.util.ArrayList;
import java.util.Random;

public class RandomPlayer extends Player {
	
	private final Random random;
	
	public RandomPlayer(String playerToken) {
		super(playerToken);
		random = new Random();
	}

	public boolean doYouWantToDoThis(String prompt) {
		System.out.println(prompt);
		// if the player is in jail
		if (this.getJailTime() >= 0) {
			// circumstances in which you want to stay in jail and wait for the next roll
			if (this.getPropertiesOwned().size() > 7 || this.getBalance() < 200) {
				return false;
			}
			return true;
		} 
		else {
			return true;
		}
	}

	public Property buyHouseB(ArrayList<Property> propsWhereYouCanBuyHouse) {
		// if there are properties in the housable list, pick a random property from this list
		// if the player has (house*2) amount of money, buy a house there
		if (propsWhereYouCanBuyHouse.size() > 0) {
			int randomInt = random.nextInt(propsWhereYouCanBuyHouse.size());
			
			if (propsWhereYouCanBuyHouse.get(randomInt).getBuyCost() * 2 < getBalance()) { 
				return propsWhereYouCanBuyHouse.get(randomInt);
			}
		}
		System.out.println(this + " does not want to buy any houses.");
		return null;
	}

	public Property mortgagePropertiesB(ArrayList<Property> propsWhereYouCanMortgage) {
		// above, with propsWhereYouCanMortgage list, remember that this is not the
		// original propertiesOwned list being passed in, but the list that's
		// being passed in from morgagePropertiesA. NAMING is very important
		
		// the arrayList that is passed in here is: properties that a player owns that are not already mortgaged

		// if player owns unmortgaged properties, and has less than $500, mortgage/return that property
		if (propsWhereYouCanMortgage.size() > 0 && this.getBalance() < 500) {

			int randomProperty = random.nextInt(propsWhereYouCanMortgage.size());
			return propsWhereYouCanMortgage.get(randomProperty);
		}
		
		System.out.println(this + " does not want to mortgage any more properties."); 
		return null;
	}

	public Property unmortgageB(ArrayList<Property> mortgagedProperties) {
		
		if (mortgagedProperties.size() > 0) {
			int randomProperty = random.nextInt(mortgagedProperties.size());

			if (this.getBalance() > 600) { 
				return mortgagedProperties.get(randomProperty);
			}
			else {
				System.out.println(this + " does not want to unmortgage any more properties.");
			}
		}
		else {
			System.out.println(this + " does not have any more properties to mortgage.");
		}
		return null;
	}
}
