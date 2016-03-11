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
  	public boolean doYouWantToDoThisJail (String prompt) {
  		System.out.println(prompt);
  		
  		// circumstances in which you want to stay in jail and wait for the next roll
  		if (this.getPropertiesOwned().size() > 7 || this.getBalance() < 200) {
  			return false;
  		}
		return true;  
  	}
  	
  	public Property buyHouseB (ArrayList<Property> propsWhereYouCanBuyHouse) {
  	  // if there are properties in the housable list, and player has (house*2) amount of money, buy the house
  	  if (propsWhereYouCanBuyHouse.size() > 0) {
  	    if (propsWhereYouCanBuyHouse.get(0).getBuyCost() * 2 < getBalance()) {
          return propsWhereYouCanBuyHouse.get(0);
        }
  	  }
  	System.out.println(this + " does not want to buy any more houses.");
    return null;
  	}
  	
  	public Property mortgagePropertiesB (ArrayList<Property> propertiesAvailableToMortgage) {
  	  // above, with propertiesOwnedVariable, remember that this is not the original propertiesOwned list being passed in, but the list that's being passed in from morgagePropertiesA
  	  // NAMING is very important
  	  
  	// player will try to mortgage random properties
  		Random random = new Random();
  		
  	// if player has unmortgaged properties, has less than $500, and the random property chosen is unmortgaged, return that property
  		if (propertiesAvailableToMortgage.size() > 0 && this.getBalance() < 500) {
  			int randomProperty = random.nextInt(propertiesAvailableToMortgage.size());
  	  		
  	  			if (!propertiesAvailableToMortgage.get(randomProperty).getMortgageStatus()) {
  	  	  			return propertiesAvailableToMortgage.get(randomProperty);
  	  	  		}
  	  			// otherwise, return the first unmortgaged property in player's propertiesOwned list 
  	  	  		else {
  	  	  			for (int i = 0; i < propertiesAvailableToMortgage.size(); i++) {
  	  	  				if (!propertiesAvailableToMortgage.get(i).getMortgageStatus()) {
  	  	  					return propertiesAvailableToMortgage.get(i);
  	  	  				}
  	  	  		}
  	  		}
  		}
  		System.out.println(this + " does not want to mortgage any more properties.");
  		return null;
  	}	
  	
  	public Property unmortgageB (ArrayList<Property> mortgagedProperties) {

  		if (this.getBalance() > 800 && mortgagedProperties.size() > 0) {
  			return mortgagedProperties.get(0);
  		}
  		System.out.println(this + " does not want to unmortgage any more properties.");
  		return null;
  	}
}
