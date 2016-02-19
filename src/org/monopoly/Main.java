package org.monopoly;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Map;

public class Main {
	
	static ArrayList<Property> properties = new ArrayList<Property>();
	// the list of all properties on the board
	
	static Map<Property, Set <Property>> map = new HashMap<>();
  // maps each property to what set (color) of properties it belongs to
	
	static Player playerOne = new RandomPlayer ("Dog", 1000);
	static Player playerTwo = new ConsolePlayer ("Thimble", 1000);
	
	static Random random = new Random();
	
	private static String[] phrases = {"Player owns this property and nothing happens to this player.", 
			"Player owns this property, pay no attention to the screaming inside.", "Player owns this property and has let loose the dogs.",
			"Player owns this property and is beckoning you inside.", "Player owns this property and has reported you to the police."};

	public static void main (String [] args) throws IOException {
		
		addProperties();	// you have to CALL add properties
		
		playerOne.setLocation(properties.get(0)); // players start at the first property on the arrayList
		playerTwo.setLocation(properties.get(0));
		
		while(true) {
			
			int dice = rollDice(random, 1, 6) + rollDice(random, 1, 6);
			Property landed;
			
			System.out.println("Player " + playerOne.getToken() + " has rolled a " + dice);
			landed = moveSpace(playerOne, dice);
			System.out.println("Player " + playerOne.getToken() + " has landed on " + landed);	
			afterLanding(playerOne, playerTwo, landed);
			if (checkBalance(playerOne, playerTwo) == true) {
				return;
			}
			System.out.println("---------------------");
			
			dice = rollDice(random, 1, 12);
			
			System.out.println("Player " + playerTwo.getToken() + " has rolled a " + dice);
			landed = moveSpace(playerTwo, dice);
			System.out.println("Player " + playerTwo.getToken() + " has landed on " + landed);	
			afterLanding(playerTwo, playerOne, landed);
			if (checkBalance(playerTwo, playerOne) == true) {
				return;
			}
			System.out.println("---------------------");
		}
	}
	public static void addProperties() {
		properties.add(new Property("Park", 350, 35, 200));
		properties.add(new Property("Boardwalk", 400, 50, 200));	
		
		properties.add(new Property("Pennsylvania", 320, 28, 200));
		properties.add(new Property("North Carolina", 300, 26, 200));
		properties.add(new Property("Pacific", 300, 26, 200));
		
		properties.add(new Property("Marvin Gardens", 280, 24, 150));
		properties.add(new Property("Ventnor", 260, 22, 150));
		properties.add(new Property("Atlantic", 260, 22, 150));
		
		properties.add(new Property("Illinois", 240, 20, 150));
		properties.add(new Property("Indiana", 220, 18, 150));
		properties.add(new Property("Kentucky", 220, 18, 150));
    
    Set <Property> blue = new HashSet <Property>();
    Set <Property> green = new HashSet <Property>();
    Set <Property> yellow = new HashSet <Property>();
    Set <Property> red = new HashSet <Property>();
    
    blue.add(new Property ("Park", 350, 35, 200));
    blue.add(new Property ("Boardwalk", 400, 50, 200));
    
    green.add(new Property ("Pennsylvania", 320, 28, 200));
    green.add(new Property ("North Carolina", 300, 26, 200));
    green.add(new Property ("Pacific", 300,26, 20));
    
    yellow.add(new Property ("Marvin Gardens", 280, 24, 150));
    yellow.add(new Property ("Ventnor", 260, 22, 150));
    yellow.add(new Property ("Atlantic", 260, 22, 150));
    
    red.add(new Property ("Illinois", 240, 20, 150));
    red.add(new Property ("Indiana", 220, 18, 150));
    red.add(new Property ("Kentucky", 220, 18, 150));
    
    for(Property a: blue) {
      map.put(a, blue);
      properties.add(a);
    }
    for(Property a: green) {
      map.put(a, green);
      properties.add(a);
    }
    for(Property a: yellow) {
      map.put(a, yellow);
      properties.add(a);
    }
    for(Property a: red) {
      map.put(a, red);
      properties.add(a);
    } 
	}
	
	public static int rollDice(Random random, int low, int high) {
		return random.nextInt(high) + low;  // the method for rolling a dice between two values
	}
	public static Property moveSpace(Player player, int roll) {
		
		int locationIndex = 0;
		
		for (int i = 0; i < properties.size(); i++){
			if (properties.get(i) == player.getLocation()) {
				locationIndex = i;
			}
		}
		locationIndex = locationIndex + (roll % properties.size());
		
		return properties.get(locationIndex);
	}
	public static void afterLanding(Player player, Player otherPlayer, Property landedProperty) {
		
	  // if the property does not belong to anyone; if the player has enough money to buy the property and chooses to
		if (landedProperty.getPropertyOwner() == null) {
			if (player.getBalance() >= landedProperty.getBuyCost() && player.buyProperty() == true) {
				System.out.println(player + " has bought " + landedProperty + " for $" + landedProperty.getBuyCost());
				player.setBalance(player.getBalance() - landedProperty.getBuyCost());
				landedProperty.setPropertyOwner(player);
				
				// check if player has a full set of properties. if yes, add to list of sets a player can buy houses in 
				// attempt to buy houses immediately
				// keep attempting to buy houses at the end of every turn
			}
			else {
				System.out.println(player + " did not buy this property.");
			}
		}
		// if the player lands on someone else's property, they pay the rent
		else if (!landedProperty.getPropertyOwner().equals(player)) {
			System.out.println(player + " has paid $" + landedProperty.getRentCost() + " for landing on " + landedProperty);
			player.setBalance(player.getBalance() - landedProperty.getRentCost());
			otherPlayer.setBalance(otherPlayer.getBalance() + landedProperty.getRentCost());
		}
		// if the player lands on their own property, nothing happens
		else {
			String phrase = phrases[(int) (Math.random() * phrases.length)];
			System.out.println(phrase);
		}
	}
	public static boolean canPlayerBuyAHouse(Player player, Property landedProperty) {
	  Set<Property> color  = map.get(landedProperty);
    for (Property a: color) {
      if (a.getPropertyOwner() != player){
        return false;
      }
    }
    return true;
	}
	public static int buyHouses(Player player) {
	  
	}
	public static boolean checkBalance(Player player, Player otherPlayer) {
		if (player.getBalance() < 0) {
			System.out.println(otherPlayer.getToken() + " has won the game!");
			return true;
		}
		return false;
	}
}

