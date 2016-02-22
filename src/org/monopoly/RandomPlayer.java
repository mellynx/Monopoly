package org.monopoly;

import java.util.Random;

public class RandomPlayer extends Player {
  
  static Random rand = new Random();
 
  	public RandomPlayer(String playerToken) {
  		super(playerToken);
  	}
  	public boolean buyProperty () {
  		return rand.nextInt(2) == 0; // limit the scope of variables as much as possible. return true if it's zero
  	}
  	public boolean buyHouse (Property property) {
    	return rand.nextInt(2) == 0;
    	// how does computer determine whether to buy more houses or buy property?
  }
  	public void purchaseAHouse() {
  	  
  	}
}
