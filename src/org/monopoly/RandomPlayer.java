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
  		
  		// the correct machine strategy is probs to buy every property. below is the code for a coin-flip strategy
  		// limit the scope of variables as much as possible. return true if it's zero
  		// return rand.nextInt(2) == 0; 
  	}
  	public boolean buyHouse (ArrayList<Property> listOfPropertiesWhereYouCanCurrentlyBuyAHouse) {
    	return rand.nextInt(2) == 0;
    	// needs heuristic for when to buy a house
  }
}
