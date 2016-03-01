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
  	
  	public Property buyHouse (ArrayList<Property> propsWhereYouCanBuyHouse) {
  	  // if there are properties in the housable list, and player has (house*2) amount of money, buy the house
  	  if (propsWhereYouCanBuyHouse.size() > 0) {
  	    if (propsWhereYouCanBuyHouse.get(0).getBuyCost() * 2 < getBalance()) { 
          System.out.println("Bought a house: " + propsWhereYouCanBuyHouse.get(0));
          return propsWhereYouCanBuyHouse.get(0);
        }
  	  }
  	System.out.println("Player has chosen not to buy any houses.");
    return null;
  	}
  	
  	public Property mortgageProperties (ArrayList<Property> propertiesOwned) {
  	// the random player will attempt to mortgage everything
  		
  		for (int i = 0; i < propertiesOwned.size(); i++) {
  			return propertiesOwned.get(i);
  		}
  		return null;
  	}	
}
