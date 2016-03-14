package org.monopoly;

import java.util.ArrayList;
import java.util.Random;

public class RandomPlayer extends Player {
  // TODO: This is quite correct for code you don't ever want to test.  For testable code, take a random in in the constructor.  Make it non-static, and make it private final.
  static Random rand = new Random();
 // TODO: You need to fix your spacing in all of these files, use ctrl+shift+f if you want to be lazy.
  	public RandomPlayer(String playerToken) {
  		super(playerToken);
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
  	
  	public Property buyHouseB (ArrayList<Property> propsWhereYouCanBuyHouse) {
  	  // if there are properties in the housable list, and player has (house*2) amount of money, buy the house
  	  if (propsWhereYouCanBuyHouse.size() > 0) {  
  	    if (propsWhereYouCanBuyHouse.get(0).getBuyCost() * 2 < getBalance()) { // TODO: Why not choose a random one?
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
  	// TODO: Do not make a new random here.  New random makes a new psuedo random sequence based on the current millisecond, so this wont be rnadom at all if it gets called twice very quickly.  Either way,
  	  // use the class level one, that's what its there for.  Makes testing easier too.
  		Random random = new Random();  
  		
  	// if player has unmortgaged properties, has less than $500, and the random property chosen is unmortgaged, return that property
  		if (propertiesAvailableToMortgage.size() > 0 && this.getBalance() < 500) {
  		  
  			int randomProperty = random.nextInt(propertiesAvailableToMortgage.size());
  			// TODO: Your indentation is terrible here!
  	  		
  		// TODO: All these properties should be mortgageable based on the contract this function provides via name (and comment when you write it) so you don't need to check this.
  	  			if (!propertiesAvailableToMortgage.get(randomProperty).getMortgageStatus()) {  
  	  	  			return propertiesAvailableToMortgage.get(randomProperty);
  	  	  		}
  	  			// TODO: You shouldn't need to do this at all, all these properties are mortgageable, just return a random one.
  	  			// otherwise, return the first unmortgaged property in player's propertiesOwned list 
  	  	  		else {
  	  	  			for (int i = 0; i < propertiesAvailableToMortgage.size(); i++) {
  	  	  				if (!propertiesAvailableToMortgage.get(i).getMortgageStatus()) {
  	  	  					return propertiesAvailableToMortgage.get(i);
  	  	  				}
  	  	  		}
  	  		}
  		}
  		System.out.println(this + " does not want to mortgage any more properties.");  // TODO: A line like this belongs in the Player class if anywhere at all.
  		return null;
  	}	
  	
  	public Property unmortgageB (ArrayList<Property> mortgagedProperties) {

  		if (this.getBalance() > 800 && mortgagedProperties.size() > 0) {  // TODO: I assume 800 is enough to unmortgage anything, so why not return a random choice, not just the first one?
  			return mortgagedProperties.get(0);
  		}
  		System.out.println(this + " does not want to unmortgage any more properties.");
  		return null;
  	}
}
