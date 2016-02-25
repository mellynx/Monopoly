package org.monopoly;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
	static String input;
	
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
			playerOne.setLocation(landed);
			System.out.println("Player " + playerOne.getToken() + " has landed on " + landed);	
			afterLanding(playerOne, landed, dice);
			if (checkBalance(playerOne, playerTwo)) {
				return;
			}
			System.out.println("---------------------");
			
			dice = rollDice();
			
			System.out.println("Player " + playerTwo.getToken() + " has rolled a " + dice);
			landed = moveSpace(playerTwo, dice);
			playerTwo.setLocation(landed);
			System.out.println("Player " + playerTwo.getToken() + " has landed on " + landed);	
			afterLanding(playerTwo, landed, dice);
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
    
    RentSchedule mediterraneanRS = new RentSchedule(2, 10, 30, 90, 160, 250);
	RentSchedule balticRS = new RentSchedule(4, 20, 60, 180, 320, 450);
	
	RentSchedule orientalRS = new RentSchedule(6, 30, 90, 270, 400, 550);
	RentSchedule vermontRS = new RentSchedule(6, 30, 90, 270, 500, 550);
	RentSchedule connecticutRS = new RentSchedule(8, 40, 100, 300, 450, 600);
	
	RentSchedule stcharlesRS = new RentSchedule(10, 50, 150, 450, 625, 750);
	RentSchedule statesRS = new RentSchedule(10, 50, 150, 450, 625, 750);
	RentSchedule virginiaRS = new RentSchedule(12, 60, 180, 500, 700, 900);
	
	RentSchedule stjamesRS = new RentSchedule(14, 70, 200, 550, 750, 950);
	RentSchedule tennesseeRS = new RentSchedule(14, 70, 200, 550, 750, 950);
	RentSchedule newyorkRS = new RentSchedule(16, 80, 220, 600, 800, 1000);
	
	RentSchedule kentuckyRS = new RentSchedule(18, 90, 250, 750, 875, 1050);
	RentSchedule indianaRS = new RentSchedule(18, 90, 250, 700, 875, 1050);
	RentSchedule illinoisRS = new RentSchedule(20, 100, 300, 750, 925, 1100);
	
	RentSchedule atlanticRS = new RentSchedule(22, 110, 330, 800, 975, 1150);
	RentSchedule ventnorRS = new RentSchedule(22, 110, 330, 800, 975, 1150);
	RentSchedule marvingardensRS = new RentSchedule(24, 120, 360, 850, 1025, 1200);
	
	RentSchedule pacificRS = new RentSchedule(26, 130, 390, 900, 1100, 1275);
	RentSchedule northcarolinaRS = new RentSchedule(26, 130, 390, 900, 1100, 1275);
	RentSchedule pennsylvaniaRS = new RentSchedule(28, 150, 450, 1000, 1200, 1400);
	
	RentSchedule parkRS = new RentSchedule(35, 175, 500, 1100, 1300, 1500);
	RentSchedule boardwalkRS = new RentSchedule(50, 200, 600, 1400, 1700, 2000);
	
	RentSchedule readingrrRS = new RentSchedule(25, 50, 100, 200);
	RentSchedule pennsylvaniarrRS = new RentSchedule(25, 50, 100, 200);
	RentSchedule borrRS = new RentSchedule(25, 50, 100, 200);
	RentSchedule shortlinerrRS = new RentSchedule(25, 50, 100, 200);
	
	
	boardProperties.add(new Property("Go"));
	
	// buy cost, house cost
	addSingleProperty(new Property("Mediterranean Ave", 60, 50, 30, mediterraneanRS), brown);
	boardProperties.add(new Property("Community Chest"));
	addSingleProperty(new Property("Baltic Ave", 60, 50, 30, balticRS), brown);
	boardProperties.add(new Property("Income Tax"));
	
	addSingleProperty(new Property("Reading Railroad", 200, 100, RentType.RAILROAD, readingrrRS), railroads);
	
	addSingleProperty(new Property("Oriental Ave", 100, 50, 50, orientalRS), lightBlue);
	boardProperties.add(new Property("Chance"));
	addSingleProperty(new Property("Vermont Ave", 100, 50, 50, vermontRS), lightBlue);
	addSingleProperty(new Property("Connecticut Ave", 100, 50, 60, connecticutRS), lightBlue);
	
    boardProperties.add(new Property("Jail"));
    
    addSingleProperty(new Property("St. Charles Place", 140, 100, 70, stcharlesRS), pink);
    addSingleProperty(new Property("Electic Company", 150, RentType.UTILITY), utilities);
    addSingleProperty(new Property("States Ave", 140, 100, 70, statesRS), pink);
    addSingleProperty(new Property("Virgina Ave", 160, 100, 80, virginiaRS), pink);
    
    addSingleProperty(new Property("Pennsylvania Railroad", 200, 100, RentType.RAILROAD, pennsylvaniarrRS), railroads);
    
    addSingleProperty(new Property("St. James Place", 180, 100, 90, stjamesRS), orange);
    boardProperties.add(new Property("Community Chest"));
    addSingleProperty(new Property("Tennessee Ave", 180, 100, 90, tennesseeRS), orange);
    addSingleProperty(new Property("New York Ave", 200, 100, 100, newyorkRS), orange);
    
    boardProperties.add(new Property("Free Parking"));
    
    addSingleProperty(new Property("Kentucky", 220, 150, 110, kentuckyRS), red);
    boardProperties.add(new Property("Chance"));
    addSingleProperty(new Property("Indiana", 220, 150, 110, indianaRS), red);
    addSingleProperty(new Property("Illinois", 240, 150, 120, illinoisRS), red);
    
    addSingleProperty(new Property("B & O Railroad", 200, 100, RentType.RAILROAD, borrRS), railroads);
    
    addSingleProperty(new Property("Atlantic", 260, 150, 130, atlanticRS), yellow);
    addSingleProperty(new Property("Ventnor", 260, 150, 130, ventnorRS), yellow);
    addSingleProperty(new Property("Water Works", 150, RentType.UTILITY), utilities);
    addSingleProperty(new Property("Marvin Gardens", 280, 150, 140, marvingardensRS), yellow);
    
    boardProperties.add(new Property("Go To Jail"));
    
    addSingleProperty(new Property("Pacific", 300, 20, 150, pacificRS), green);
    addSingleProperty(new Property("North Carolina", 300, 200, 150, northcarolinaRS), green);
    boardProperties.add(new Property("Community Chest"));
    addSingleProperty(new Property("Pennsylvania", 320, 200, 160, pennsylvaniaRS), green);
    
    addSingleProperty(new Property("Short Line Railroad", 200, 100, RentType.RAILROAD, shortlinerrRS), railroads);
    
    boardProperties.add(new Property("Chance"));
    addSingleProperty(new Property("Park", 350, 200, 175, parkRS), blue);
    boardProperties.add(new Property("Luxuty Tax"));
    addSingleProperty(new Property("Boardwalk", 400, 200, 200, boardwalkRS), blue);
  }
	public static int rollDice() {
		return (random.nextInt(6) + 1 + random.nextInt(6) + 1);
	}
	public static Property moveSpace(Player player, int roll) {
		
		// get the current index of where the player is
		int index = player.getLocation().getLocationIndex(boardProperties);
		
		index = ((index + roll) % boardProperties.size());
		
		return boardProperties.get(index);
	}
	public static void afterLanding(Player player, Property landedProperty, int diceRoll) throws IOException {
		
	  // if the property does not belong to anyone; if the player has enough money to buy the property and chooses to
	  // deduct the cost of the property from balance, set owner for that property, add that property to list of player's owned properties
		if (landedProperty.getPropertyOwner() == null && landedProperty.getBuyableStatus()) {
			if (player.getBalance() >= landedProperty.getBuyCost() && player.buyProperty()) {
				System.out.println(player + " has bought " + landedProperty + " for $" + landedProperty.getBuyCost());
				player.setBalance(player.getBalance() - landedProperty.getBuyCost());
				landedProperty.setPropertyOwner(player);
				player.addToPropertiesOwnedList(landedProperty);
				
				// check if this newly purchased property allows players to buy houses
				// if yes, check the color of that new property and PUT THAT COLOR into a list containing property sets, ie colors that players can buy houses on
				// later, keep checking this list to see when the player actually wants to buy these houses
				if (canPlayerBuyAHouse(player, landedProperty)) {
					System.out.println("You've achieved a monopoly for " + map.get(landedProperty));
					Set<Property> color  = map.get(landedProperty);
						player.addToPossibleHouseList(color);
				}
			}
			else if (player.getBalance() < landedProperty.getBuyCost()) {
				
				/*
				 * System.out.println("You do not have enough money to buy houses. Would you like to mortgage any properties? (y/n)");
					if (player.checkMortgageBuying()) {
				}
				
				
				  
				  input = keyboard.readLine();
				  
				  if (input.equals("y")) {
					  player.mortgageProperties(player.getPropertiesOwned(), player.getMortgagedProperties());
				  }
				  else {
					  System.out.println("Player does not have enough money to purchase a house.");
				  }
				
				System.out.println(player + " did not buy this property.");
				 */
				
			}
		}
		// if the player lands on a board property, nothing happens
		else if (landedProperty.getBuyableStatus() == false) {
			System.out.println("The player landed on " + landedProperty + " and nothing happens.");
		}
		
		// if the player lands on someone else's property, they pay the rent
		else if (!landedProperty.getPropertyOwner().equals(player)) {
			System.out.println(player + " has paid $" + landedProperty.getRentCost(diceRoll) + " for landing on " + landedProperty);
			player.setBalance(player.getBalance() - landedProperty.getRentCost(diceRoll));
			landedProperty.getPropertyOwner().setBalance(landedProperty.getPropertyOwner().getBalance() + landedProperty.getRentCost(diceRoll));
		}
		// if the player lands on their own property, nothing happens
		else {
			String phrase = phrases[(int) (Math.random() * phrases.length)];
			System.out.println(phrase);
		}
		// at the end of every turn, go back to the list that tells us if a player is eligible to buy houses
		// if they are, let's revisit
		if (player.getPossibleHouseList().size() > 0) {
		  considerHouseList(player, player.getPossibleHouseList());
		}
	}
	
	/**
	 * javadoc documentation. note the two stars above
	 * the method below is strictly about whether a player meets the board requirements to buy houses, not whether they have the money to
	 */
	public static boolean canPlayerBuyAHouse(Player player, Property landedProperty) {
		Set<Property> color  = map.get(landedProperty);
		
		// cannot call a method on a null object
	    for (Property a: color) {
	    	if (a.getPropertyOwner() != player) {
	    		return false;
	    	}
	    }
	    return true;
	}
	
	public static void considerHouseList(Player player, ArrayList <Set<Property>> monopoliesOwned) throws IOException {
		
		outer: while (true) {
			for (int i = 0; i < monopoliesOwned.size(); i++) { // for every set in the list of housable sets
				
				// put this here because these variables are different per set
				int minHouseCountPerSet = 100;
				// the thing that you're actually passing to players to ask if they want to buy
				ArrayList<Property> propertiesWhereYouCanCurrentlyBuyAHouse = null;
				
				  Set<Property> propertySet = monopoliesOwned.get(i);
				  
				  // looping through each property in a set, to find what the minimum amount of houses in that set is
				  for (Property housableProperty: propertySet) {
					  if (housableProperty.getNumberOfHouses() < minHouseCountPerSet) { 
						  minHouseCountPerSet = housableProperty.getNumberOfHouses();
					  }
				  }
				 
				// looping through every property in a set
				  for (Property housableProperty: propertySet) { 
					  // for each property, IF the player can afford a house on that property
					  // and the number of houses on that property doesn't exceed the current min houseCount
					  // the 5th house is effectively the hotel
					  if (player.getBalance() > housableProperty.getHouseCost() && housableProperty.getNumberOfHouses() == minHouseCountPerSet && housableProperty.getNumberOfHouses() <= 5) {
						  // present that property in a list of things player can buy a house on, then loop. or break if the player wants to exit
						  propertiesWhereYouCanCurrentlyBuyAHouse.add(housableProperty);
					  }
					  else if (player.getBalance() < housableProperty.getHouseCost()) {
						  /*
						  System.out.println("You do not have enough money to buy houses. Would you like to mortgage any properties? (y/n)");
						  
						  input = keyboard.readLine();
						  
						  if (input.equals("y")) {
							  player.mortgageProperties(player.getPropertiesOwned(), player.getMortgagedProperties());
						  }
						  else {
							  System.out.println("Player does not have enough money to purchase a house.");
						  }
						  */
					  }
				  }
				  if (player.buyHouse(propertiesWhereYouCanCurrentlyBuyAHouse)) {
					  continue;
				  }
				  else {
					  break outer;
				  }
			  }				
		}
	}
	
	public static boolean checkBalance(Player player, Player otherPlayer) {
		if (player.getBalance() < 0) {
			
			// as long as player has properties, they should mortgage them to save the game. otherwise, they lose the game.
			if (player.getPropertiesOwned().size() > 0) {
				System.out.println("Player has no more money and needs to mortgage some properties.");
				player.mortgageProperties(player.getPropertiesOwned(), player.getMortgagedProperties());
				
				checkBalance(player, otherPlayer);
			}
			else {
				System.out.println(otherPlayer.getToken() + " has won the game!");
				return true;
			}
		}
		return false;
	}
	private static void addSingleProperty(Property property, Set<Property> set) {
	    boardProperties.add(property);
	    set.add(property);
	    map.put(property, set);
	}
	public static void askToMortgage() {
		
	}
}


