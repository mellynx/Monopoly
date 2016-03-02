package org.monopoly;

import java.util.ArrayList;
import java.util.Random;

public class RandomPlayer extends Player {
  
  static Random rand = new Random();
 
  	public RandomPlayer(String playerToken) {
  		super(playerToken);
  	}
  	
  	public boolean doYouWantToDoThis(String prompt) {
  		System.out.println(prompt); 
  		return true;
  	}
  	
  	public Property buyHouseB (ArrayList<Property> propsWhereYouCanBuyHouse) {
  	  // if there are properties in the housable list, and player has (house*2) amount of money, buy the house
  	  if (propsWhereYouCanBuyHouse.size() > 0) {
  	    if (propsWhereYouCanBuyHouse.get(0).getBuyCost() * 2 < getBalance()) { 
          System.out.println("Bought a house: " + propsWhereYouCanBuyHouse.get(0));
          return propsWhereYouCanBuyHouse.get(0);
        }
  	  }
  	System.out.println(this + " does not want to buy any more houses.");
    return null;
  	}
  	
  	public Property mortgagePropertiesB (ArrayList<Property> propertiesOwned) {
  	// player will try to mortgage random properties
  	// we need a stop condition (balance), otherwise RandomPlayer will mortgage everything until he hits null
  		Random random = new Random();
  	
  		int houseNumber = random.nextInt(propertiesOwned.size());
  		
  		if (propertiesOwned.size() > mortgagedProperties.size() && this.getBalance() < 500) {
  			if (!propertiesOwned.get(houseNumber).getMortgageStatus()) {
  				System.out.println("Mortgaged: " + propertiesOwned.get(houseNumber));
  	  			return propertiesOwned.get(houseNumber);
  	  		}
  	  		else {
  	  			for (int i = 0; i < propertiesOwned.size(); i++) {
  	  				if (!propertiesOwned.get(i).getMortgageStatus()) {
  	  					System.out.println("Mortgaged: " + propertiesOwned.get(i));
  	  					return propertiesOwned.get(i);
  	  				}
  	  			}
  	  		}
  		}
  		System.out.println(this + " does not want to mortgage any more properties.");
  		return null;
  	}	
  	
  	public Property unmortgageB (ArrayList<Property> mortgagedProperties) {

  		if (this.getBalance() > 800 && mortgagedProperties.size() > 0) {
  			System.out.println("Unmortgaged: " + mortgagedProperties.get(0));
  			return mortgagedProperties.get(0);
  		}
  		System.out.println(this + " does not want to unmortgage any more properties.");
  		return null;
  	}
}
