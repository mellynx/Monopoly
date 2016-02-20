package org.monopoly;

import java.util.Random;

public class RandomPlayer extends Player {
  
  static Random rand = new Random();
 
  	public RandomPlayer(String token, int money) {
  		super(token, money, null);
  	}
  	public boolean buyProperty (Property property) {
  		return rand.nextInt(2) == 0; // limit the scope of variables as much as possible. return true if it's zero
  	}
  	public boolean buyHouse () {
    	return rand.nextInt(2) == 0;
    // after buying a property, check if you own all the properties of that same color
    // and check if you have enough money. if yes to both, flip coins for as many houses as you can buy
  }
}
