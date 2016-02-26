package org.monopoly;

import java.util.ArrayList;
import java.util.Random;

public class RandomPlayer extends Player {
  
  static Random rand = new Random();
 
  	public RandomPlayer(String playerToken) {
  		super(playerToken);
  	}
  	public boolean buyProperty () {
  		return true;
  		// because usually you try to buy every property. below is the code for a coin-flip strategy
  		// limit the scope of variables as much as possible. return true if it's zero
  		// return rand.nextInt(2) == 0; 
  	}
  	// I think this heuristic means that the player will keep buying the first house in the list that's available to buy
  	// which should rotate through the set evenly?
  	public boolean buyHouse (ArrayList<Property> listOfPropertiesWhereYouCanCurrentlyBuyAHouse) {
    	if (listOfPropertiesWhereYouCanCurrentlyBuyAHouse.get(0).getBuyCost() * 10 < getBalance()) {
    		System.out.println("Bought a house: " + listOfPropertiesWhereYouCanCurrentlyBuyAHouse.get(0));
    		listOfPropertiesWhereYouCanCurrentlyBuyAHouse.get(0).addOneHouse();
    		int money = getBalance() - listOfPropertiesWhereYouCanCurrentlyBuyAHouse.get(0).getHouseCost();
			setBalance(money);
			return true;
    	}
    	return false;
  	}
  	public boolean mortgageProperties (ArrayList<Property> propertiesOwned, ArrayList<Property> mortgagedProperties) {
  	// the random player will attempt to mortgage everything
  		
  		for (int i = 0; i < propertiesOwned.size(); i++) {
  			mortgagedProperties.add(propertiesOwned.get(i));
  			propertiesOwned.remove(propertiesOwned.get(i));
  			propertiesOwned.get(i).changeMortgageStatus();
  			int money = getBalance() + propertiesOwned.get(i).getMortgageCost();
  			setBalance(money);
  		}
  		return false;
  	}	
  	public boolean checkIfYourWantToMortgageProperties (String prompt) {
  		return true;
  	}
}
