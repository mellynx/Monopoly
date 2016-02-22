package org.monopoly;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.monopoly.Property.RentType;

import java.util.Map;

public class Main {
	
  //the list of all properties on the board
	static ArrayList<Property> boardProperties = new ArrayList<Property>();
	
  //maps each property to what set (color) of properties it belongs to
	static Map<Property, Set <Property>> map = new HashMap<>();
	
	static Player playerOne = new RandomPlayer ("Dog");
	static Player playerTwo = new ConsolePlayer ("Thimble");
	
	static Random random = new Random();
	
	private static String[] phrases = {"Player owns this property and nothing happens to this player.", 
			"Player owns this property, pay no attention to the screaming inside.", "Player owns this property and has let loose the dogs.",
			"Player owns this property and is beckoning you inside.", "Player owns this property and has reported you to the police."};

	public static void main (String [] args) throws IOException {
		
		addProperties();	// remember you have to CALL add properties
		
		playerOne.setLocation(boardProperties.get(0)); // players start at the first property on the arrayList
		playerTwo.setLocation(boardProperties.get(0));
		
		while(true) {
			
			int dice = rollDice();
			Property landed;
			
			System.out.println("Player " + playerOne.getToken() + " has rolled a " + dice);
			landed = moveSpace(playerOne, dice);
			System.out.println("Player " + playerOne.getToken() + " has landed on " + landed);	
			afterLanding(playerOne, landed);
			if (checkBalance(playerOne, playerTwo)) {
				return;
			}
			System.out.println("---------------------");
			
			dice = rollDice();
			
			System.out.println("Player " + playerTwo.getToken() + " has rolled a " + dice);
			landed = moveSpace(playerTwo, dice);
			System.out.println("Player " + playerTwo.getToken() + " has landed on " + landed);	
			afterLanding(playerTwo, landed);
			if (checkBalance(playerTwo, playerOne)) {
				return;
			}
			System.out.println("---------------------");
		}
	}

  public static void addProperties() {
    // ctrl+f+shift to auto format 

    Set<Property> brown = new HashSet<Property>();
    Set<Property> lightBlue = new HashSet<Property>();
    Set<Property> pink = new HashSet<Property>();
    Set<Property> orange = new HashSet<Property>();
    Set<Property> red = new HashSet<Property>();
    Set<Property> yellow = new HashSet<Property>();
    Set<Property> green = new HashSet<Property>();
    Set<Property> blue = new HashSet<Property>();
    Set<Property> utilities = new HashSet<Property>();
    Set<Property> railroads = new HashSet<Property>();

    boardProperties.add(new Property("Go"));

    Property mediterranean = new Property("Mediterranean Ave", 60, 50);
    boardProperties.add(mediterranean);
    brown.add(mediterranean);
    Property baltic = new Property("Baltic Ave", 60, 50);
    boardProperties.add(baltic);
    brown.add(baltic);
    
    Property readingrr = new Property("Reading Railroad", 200, RentType.RAILROAD);
    boardProperties.add(readingrr);
    railroads.add(readingrr);

    Property oriental = new Property("Oriental Ave", 100, 50);
    boardProperties.add(oriental);
    lightBlue.add(oriental);
    Property vermont = new Property("Vermont Ave", 100, 50);
    boardProperties.add(vermont);
    lightBlue.add(vermont);
    Property connecticut = new Property("Connecticut Ave", 100, 50);
    boardProperties.add(connecticut);
    lightBlue.add(connecticut);

    boardProperties.add(new Property("Jail"));

    Property stcharles = new Property("St. Charles Place", 140, 100);
    boardProperties.add(stcharles);
    pink.add(stcharles);
    Property electriccompany = new Property("Electic Company", 150, RentType.UTILITY);
    boardProperties.add(electriccompany);
    utilities.add(electriccompany);
    Property states = new Property("States Ave", 140, 100);
    boardProperties.add(states);
    pink.add(states);
    Property virginia = new Property("Virgina Ave", 160, 100);
    boardProperties.add(virginia);
    pink.add(virginia);

    Property pennsylvaniarr = new Property("Pennsylvania Railroad", 200, RentType.RAILROAD);
    boardProperties.add(pennsylvaniarr);
    railroads.add(pennsylvaniarr);

    Property stjames = new Property("St. James Place", 180, 100);
    boardProperties.add(stjames);
    orange.add(stjames);
    Property tennessee = new Property("Tennessee Ave", 180, 100);
    boardProperties.add(tennessee);
    orange.add(tennessee);
    Property newyork = new Property("New York Ave", 200, 100);
    boardProperties.add(newyork);
    orange.add(newyork);

    boardProperties.add(new Property("Free Parking"));

    Property illinois = new Property("Illinois", 240, 150);
    boardProperties.add(illinois);
    red.add(illinois);
    Property indiana = new Property("Indiana", 220, 150);
    boardProperties.add(indiana);
    red.add(indiana);
    Property kentucky = new Property("Kentucky", 220, 150);
    boardProperties.add(kentucky);
    red.add(kentucky);

    Property borr = new Property("B & O Railroad", 200, RentType.RAILROAD);
    boardProperties.add(borr);
    railroads.add(borr);

    Property marvingardens = new Property("Marvin Gardens", 280, 150);
    boardProperties.add(marvingardens);
    yellow.add(marvingardens);
    Property ventnor = new Property("Ventnor", 260, 150);
    boardProperties.add(ventnor);
    yellow.add(ventnor);
    Property waterworks = new Property("Water Works", 150, RentType.UTILITY);
    boardProperties.add(waterworks);
    utilities.add(waterworks);
    Property atlantic = new Property("Atlantic", 260, 150);
    boardProperties.add(atlantic);
    yellow.add(atlantic);

    boardProperties.add(new Property("Go To Jail"));

    Property pennsylvania = new Property("Pennsylvania", 320, 200);
    boardProperties.add(pennsylvania);
    green.add(pennsylvania);
    Property northcarolina = new Property("North Carolina", 300, 200);
    boardProperties.add(northcarolina);
    green.add(northcarolina);
    Property pacific = new Property("Pacific", 300, 20);
    boardProperties.add(pacific);
    green.add(pacific);

    Property shortlinerr = new Property("Short Line Railroad", 200, RentType.RAILROAD);
    boardProperties.add(shortlinerr);
    railroads.add(shortlinerr);

    Property park = new Property("Park", 350, 200);
    boardProperties.add(park);
    blue.add(park);
    Property boardwalk = new Property("Boardwalk", 400, 200);
    boardProperties.add(boardwalk);
    blue.add(boardwalk);
  }
	public static int rollDice() {
		return (random.nextInt(6) + 1 + random.nextInt(6) + 1);
	}
	public static Property moveSpace(Player player, int roll) {
		
		int locationIndex = 0;
		
		for (int i = 0; i < boardProperties.size(); i++){
			if (boardProperties.get(i) == player.getLocation()) {
				locationIndex = i;
			}
		}
		locationIndex = locationIndex + (roll % boardProperties.size());
		
		return boardProperties.get(locationIndex);
	}
	public static void afterLanding(Player player, Property landedProperty) {
		
	  // if the property does not belong to anyone; if the player has enough money to buy the property and chooses to
		if (landedProperty.getPropertyOwner() == null) {
			if (player.getBalance() >= landedProperty.getBuyCost() && player.buyProperty()) {
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
			landedProperty.getPropertyOwner().setBalance(landedProperty.getPropertyOwner().getBalance() + landedProperty.getRentCost());
		}
		// if the player lands on their own property, nothing happens
		else {
			String phrase = phrases[(int) (Math.random() * phrases.length)];
			System.out.println(phrase);
		}
		// at the end of every turn, loop through the list of properties a player is eligible to buy houses for, and ask if they want to buy a house there
		// in real monopoly houses must be built evenly 
		if (player.getPossibleHouseList().size() > 0) {
		  buyAHouse(player, player.getPossibleHouseList());
		}
	}
	
	/**
	 * javadoc documentation. note the two stars above
	 * the method below is strictly about whether a player meets the board requirements to buy houses, not whether they have the money to
	 */
	public static boolean canPlayerBuyAHouse(Player player, Property landedProperty) {
		Set<Property> color  = map.get(landedProperty);
		
	    for (Property a: color) {
	    	if (a.getPropertyOwner() != player) {
	    		return false;
	    	}
	    }
	    	return true;
		}
	public static void buyAHouse(Player player, ArrayList <Property> monopoliesOwned) {
	  int houseCount = 0; // currently allowed number of houses per property, in order to keep number of houses on properties even
	  
	  for (int i = 0; i < monopoliesOwned.size(); i++) {
	    while (player.getBalance() > monopoliesOwned.get(i).getHouseCost() && monopoliesOwned.get(i).getNumberOfHouses() <= houseCount ) {
	      player.purchaseAHouse();
	    }
	  }
	  
	  // check the sets, for each green, spit out the properties that are housable based on evenness
	  
	  //build the choices, then pass it to player., which is an abstract method. evenness and money in here
	  
	  // loop through possibleHouses, present the whole set to player (plus null), and have them return what they want to buy, until they say null
	  // still gotta check for money and house even-ness
	  
	  // here, we know that the possibleHouses list is not empty, AKA player has ability to buy houses
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
	
	public static boolean checkBalance(Player player, Player otherPlayer) {
		if (player.getBalance() < 0) {
			System.out.println(otherPlayer.getToken() + " has won the game!");
			return true;
		}
		return false;
	}
}


