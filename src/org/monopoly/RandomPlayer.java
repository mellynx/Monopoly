package org.monopoly;

import java.util.ArrayList;
import java.util.Random;

public class RandomPlayer extends Player {
	
	private final Random random;
	
	public RandomPlayer(String playerToken) {
		super(playerToken);
		random = new Random();
	}

	public boolean chooseYesOrNo(String prompt) {
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

	public Property selectWhereToBuyHouse(ArrayList<Property> propsWhereYouCanBuyHouse) {
		// if there are properties where player can buy a house, pick one of these properties at random
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
	
	public Property selectWhereToSellHouse(ArrayList<Property> propsWhereYouCanSellHouse) {
		
		if (propsWhereYouCanSellHouse.size() > 0 && this.getBalance() < 100) {
			
			int randomProperty = random.nextInt(propsWhereYouCanSellHouse.size());
			return propsWhereYouCanSellHouse.get(randomProperty);
		}
		else if (propsWhereYouCanSellHouse.size() == 0) {
			System.out.println(this + " does not have any more houses to sell.");
		}
		return null;
	}

	public Property selectWhatToMortgage(ArrayList<Property> mortgageableProperties) {

		// if player owns unmortgaged properties and has less than $500, mortgage/return that property
		if (mortgageableProperties.size() > 0 && this.getBalance() < 500) {

			int randomProperty = random.nextInt(mortgageableProperties.size());
			return mortgageableProperties.get(randomProperty);
		}
		else if (mortgageableProperties.size() == 0) {
			System.out.println(this + " does not have any more properties to mortgage.");
		}
		return null;
	}

	public Property selectWhatToUnmortgage(ArrayList<Property> mortgagedProperties) {
		
		if (mortgagedProperties.size() > 0) {
			int randomProperty = random.nextInt(mortgagedProperties.size());

			if (this.getBalance() > 600) { 
				return mortgagedProperties.get(randomProperty);
			}
		}
		else {
			System.out.println(this + " does not have any more properties to unmortgage.");
		}
		return null;
	}
}
