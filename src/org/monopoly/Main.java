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
	
	static Player playerOne = new RandomPlayer ("Dog", 1000, null);
	static Player playerTwo = new ConsolePlayer ("Thimble", 1000, null);
	
	static Random random = new Random();
	
	private static String[] phrases = {"Player owns this property and nothing happens to this player.", 
			"Player owns this property, pay no attention to the screaming inside.", "Player owns this property and has let loose the dogs.",
			"Player owns this property and is beckoning you inside.", "Player owns this property and has reported you to the police."};

	public static void main (String [] args) throws IOException {
		
		addProperties();	// remember you have to CALL add properties
		
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
		//String name, int buyCost, int houseCost, int numberOfHouses
		
		properties.add(new Property("Go", null, null, null)); // create a third type of player who owns board properties?
		
		properties.add(new Property("Mediterranean Ave", 60, 50, 0));
		properties.add(new Property("Baltic Ave", 60, 50, 0));
		properties.add(new Property("Reading Railroad", 200, null, null)); // railroad rents depend on how many you own, and no houses are possible
		
		properties.add(new Property("Oriental Ave", 100, 50, 0));
		properties.add(new Property("Vermont Ave", 100, 50, 0));
		properties.add(new Property("Connecticut Ave", 100, 50, 0));
		
		properties.add(new Property("St. Charles Place", 140, 100, 0));
		properties.add(new Property("Electic Company", 150, null, null)); // rents are based on dice roll, and houses are not possible
		properties.add(new Property("States Ave", 140, 100, 0));
		properties.add(new Property("Virgina Ave", 160, 100, 0));
		
		properties.add(new Property("Pennsylvania Railroad", 200, null, null));
		properties.add(new Property("St. James Place", 180, 100, 0));
		properties.add(new Property("Tennessee Ave", 180, 100, 0));
		properties.add(new Property("New York Ave", 200, 100, 0));
		
		properties.add(new Property("Kentucky", 220, 150, 0));
		properties.add(new Property("Indiana", 220, 150, 0));
		properties.add(new Property("Illinois", 240, 150, 0));
		properties.add(new Property("B & O Railroad", 200, null, null));
		
		properties.add(new Property("Atlantic", 260, 150, 0));
		properties.add(new Property("Ventnor", 260, 150, 0));
		properties.add(new Property("Water Works", 150, null, null));
		properties.add(new Property("Marvin Gardens", 280, 150, 0));
		
		properties.add(new Property("Pacific", 300, 200, 0));
		properties.add(new Property("North Carolina", 300, 200, 0));
		properties.add(new Property("Pennsylvania", 320, 200, 0));
		
		properties.add(new Property("Short Line Railroad", 200, null, null));
		properties.add(new Property("Park", 350, 200, 0));
		properties.add(new Property("Boardwalk", 400, 200, 0));	
		
		Set <Property> brown = new HashSet<Property>();
		Set <Property> lightBlue = new HashSet<Property>();
		Set <Property> pink = new HashSet<Property>();
		Set <Property> orange = new HashSet<Property>();
		Set <Property> red = new HashSet <Property>();
		Set <Property> yellow = new HashSet <Property>();
		Set <Property> green = new HashSet <Property>();
	    Set <Property> blue = new HashSet <Property>();
	    Set <Property> utilities = new HashSet <Property>();
	    Set <Property> railroads = new HashSet <Property>();
	    
	    brown.add(new Property("Mediterranean Ave", 60, 50, 0));
	    brown.add(new Property("Baltic Ave", 60, 50, 0));
	    
		lightBlue.add(new Property("Oriental Ave", 100, 50, 0));
		lightBlue.add(new Property("Vermont Ave", 100, 50, 0));
		lightBlue.add(new Property("Connecticut Ave", 100, 50, 0));
		
		pink.add(new Property("St. Charles Place", 140, 100, 0));
		pink.add(new Property("States Ave", 140, 100, 0));
		pink.add(new Property("Virgina Ave", 160, 100, 0));
		
		orange.add(new Property("St. James Place", 180, 100, 0));
		orange.add(new Property("Tennessee Ave", 180, 100, 0));
		orange.add(new Property("New York Ave", 200, 100, 0));
	    
	    red.add(new Property ("Illinois", 240, 150, 0));
	    red.add(new Property ("Indiana", 220, 150, 0));
	    red.add(new Property ("Kentucky", 220, 150, 0));
	    
	    yellow.add(new Property ("Marvin Gardens", 280, 150, 0));
	    yellow.add(new Property ("Ventnor", 260, 150, 0));
	    yellow.add(new Property ("Atlantic", 260, 150, 0));
	    
	    green.add(new Property ("Pennsylvania", 320, 200, 0));
	    green.add(new Property ("North Carolina", 300, 200, 0));
	    green.add(new Property ("Pacific", 300, 20, 0));
	    
	    blue.add(new Property ("Park", 350, 200, 0));
	    blue.add(new Property ("Boardwalk", 400, 200, 0));
	    
	    utilities.add(new Property ("Electic Company", 150, null, null));
	    utilities.add(new Property("Water Works", 150, null, null));
	    
	    railroads.add(new Property("Reading Railroad", 200, null, null));
	    railroads.add(new Property("Pennsylvania Railroad", 200, null, null));
	    railroads.add(new Property("B & O Railroad", 200, null, null));
	    railroads.add(new Property("Short Line Railroad", 200, null, null));
	    
	    for(Property a: brown) {
	    	map.put(a, brown);
	      	properties.add(a);
	    } 
	    for(Property a: lightBlue) {
	    	map.put(a, lightBlue);
	      	properties.add(a);
	    } 
	    for(Property a: pink) {
	    	map.put(a, pink);
	      	properties.add(a);
	    } 
	    for(Property a: orange) {
	    	map.put(a, orange);
	      	properties.add(a);
	    } 
	    for(Property a: red) {
	    	map.put(a, red);
	      	properties.add(a);
	    } 
	    for(Property a: yellow) {
	    	map.put(a, yellow);
	    	properties.add(a);
	    }
	    for(Property a: green) {
	    	map.put(a, green);
	    	properties.add(a);
	    }
	    for(Property a: blue) {
		   map.put(a, blue);
		   properties.add(a);
		}
	    for(Property a: utilities) {
	    	map.put(a, utilities);
	      	properties.add(a);
	    } 
	    for(Property a: railroads) {
	    	map.put(a, railroads);
	      	properties.add(a);
	    } 
	}
	public static int rollDice(Random random, int low, int high) {
		return random.nextInt(high) + low;
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
				
				// check if this newly purchased property allows players to buy houses
				// if yes, put this set into an array of hotel-able properties, then (later) keep checking this array to see if they can buy more houses
				if (canPlayerBuyAHouse(player, landedProperty)) {
					Set<Property> color  = map.get(landedProperty);
					for (Property a: color) {
						player.addToPossibleHouseList(a);
					}
				}
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
		// at the end of every turn, loop through the list of properties a player is eligible to buy houses for, and ask if they want to buy a house there
		// in real monopoly houses must be built evenly 
		if (player.getPossibleHouseList() != null) {
			
			//while they have enough money (to afford any house in this list), keep asking them if they want to buy houses
			// for either the console or random player, how do we control 
			// 1) buying a house on B but not A or C
			// 2) buying houses on ABC and looping around to buy second houses on each (houses must be built evenly)
			// 3) stopping when player is out of money or there's already a hotel on there
			while (player.getBalance() > player.getPossibleHouseList().get(0).getBuyCost()) {
		
				for (int i = 0; i < player.getPossibleHouseList().size(); i++) {
					Property potentialHouse = player.getPossibleHouseList().get(i);
			
					if (player.buyHouse(potentialHouse)) {
						potentialHouse.addOneHouse();
						player.setBalance(player.getBalance() - potentialHouse.getHouseCost());
					}
				}
			}
		}
	}
	public static boolean canPlayerBuyAHouse(Player player, Property landedProperty) {
		// strictly about whether they meet board requirements, not whether they have enough money
		Set<Property> color  = map.get(landedProperty);
		
	    for (Property a: color) {
	    	if (a.getPropertyOwner() != player) {
	    		return false;
	    	}
	    }
	    	return true;
		}
	public static boolean checkBalance(Player player, Player otherPlayer) {
		if (player.getBalance() < 0) {
			System.out.println(otherPlayer.getToken() + " has won the game!");
			return true;
		}
		return false;
	}
}


