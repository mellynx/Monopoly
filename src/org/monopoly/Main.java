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
    
    RentSchedule mediterraneanRS = new RentSchedule(10, 30, 90, 160, 250);
	RentSchedule balticRS = new RentSchedule(20, 60, 180, 320, 450);
	
	RentSchedule orientalRS = new RentSchedule(30, 90, 270, 400, 550);
	RentSchedule vermontRS = new RentSchedule(30, 90, 270, 500, 550);
	RentSchedule connecticutRS = new RentSchedule(40, 100, 300, 450, 600);
	
	RentSchedule stcharlesRS = new RentSchedule(50, 150, 450, 625, 750);
	RentSchedule statesRS = new RentSchedule(50, 150, 450, 625, 750);
	RentSchedule virginiaRS = new RentSchedule(60, 180, 500, 700, 900);
	
	RentSchedule stjamesRS = new RentSchedule(70, 200, 550, 750, 950);
	RentSchedule tennesseeRS = new RentSchedule(70, 200, 550, 750, 950);
	RentSchedule newyorkRS = new RentSchedule(80, 220, 600, 800, 1000);
	
	RentSchedule kentuckyRS = new RentSchedule(90, 250, 750, 875, 1050);
	RentSchedule indianaRS = new RentSchedule(90, 250, 700, 875, 1050);
	RentSchedule illinoisRS = new RentSchedule(100, 300, 750, 925, 1100);
	
	RentSchedule atlanticRS = new RentSchedule(110, 330, 800, 975, 1150);
	RentSchedule ventnorRS = new RentSchedule(110, 330, 800, 975, 1150);
	RentSchedule marvingardensRS = new RentSchedule(120, 360, 850, 1025, 1200);
	
	RentSchedule pacificRS = new RentSchedule(130, 390, 900, 1100, 1275);
	RentSchedule northcarolinaRS = new RentSchedule(130, 390, 900, 1100, 1275);
	RentSchedule pennsylvaniaRS = new RentSchedule(150, 450, 1000, 1200, 1400);
	
	RentSchedule parkRS = new RentSchedule(175, 500, 1100, 1300, 1500);
	RentSchedule boardwalkRS = new RentSchedule(200, 600, 1400, 1700, 2000);

    boardProperties.add(new Property("Go"));

    Property mediterranean = new Property("Mediterranean Ave", 60, 50, mediterraneanRS);
    boardProperties.add(mediterranean);
    brown.add(mediterranean);
    Property baltic = new Property("Baltic Ave", 60, 50, balticRS);
    boardProperties.add(baltic);
    brown.add(baltic);
    
    Property readingrr = new Property("Reading Railroad", 200, RentType.RAILROAD);
    boardProperties.add(readingrr);
    railroads.add(readingrr);

    Property oriental = new Property("Oriental Ave", 100, 50, orientalRS);
    boardProperties.add(oriental);
    lightBlue.add(oriental);
    Property vermont = new Property("Vermont Ave", 100, 50, vermontRS);
    boardProperties.add(vermont);
    lightBlue.add(vermont);
    Property connecticut = new Property("Connecticut Ave", 100, 50, connecticutRS);
    boardProperties.add(connecticut);
    lightBlue.add(connecticut);

    boardProperties.add(new Property("Jail"));

    Property stcharles = new Property("St. Charles Place", 140, 100, stcharlesRS);
    boardProperties.add(stcharles);
    pink.add(stcharles);
    Property electriccompany = new Property("Electic Company", 150, RentType.UTILITY);
    boardProperties.add(electriccompany);
    utilities.add(electriccompany);
    Property states = new Property("States Ave", 140, 100, statesRS);
    boardProperties.add(states);
    pink.add(states);
    Property virginia = new Property("Virgina Ave", 160, 100, virginiaRS);
    boardProperties.add(virginia);
    pink.add(virginia);

    Property pennsylvaniarr = new Property("Pennsylvania Railroad", 200, RentType.RAILROAD);
    boardProperties.add(pennsylvaniarr);
    railroads.add(pennsylvaniarr);

    Property stjames = new Property("St. James Place", 180, 100, stjamesRS);
    boardProperties.add(stjames);
    orange.add(stjames);
    Property tennessee = new Property("Tennessee Ave", 180, 100, tennesseeRS);
    boardProperties.add(tennessee);
    orange.add(tennessee);
    Property newyork = new Property("New York Ave", 200, 100, newyorkRS);
    boardProperties.add(newyork);
    orange.add(newyork);

    boardProperties.add(new Property("Free Parking"));

    Property illinois = new Property("Illinois", 240, 150, illinoisRS);
    boardProperties.add(illinois);
    red.add(illinois);
    Property indiana = new Property("Indiana", 220, 150, indianaRS);
    boardProperties.add(indiana);
    red.add(indiana);
    Property kentucky = new Property("Kentucky", 220, 150, kentuckyRS);
    boardProperties.add(kentucky);
    red.add(kentucky);

    Property borr = new Property("B & O Railroad", 200, RentType.RAILROAD);
    boardProperties.add(borr);
    railroads.add(borr);

    Property marvingardens = new Property("Marvin Gardens", 280, 150, marvingardensRS);
    boardProperties.add(marvingardens);
    yellow.add(marvingardens);
    Property ventnor = new Property("Ventnor", 260, 150, ventnorRS);
    boardProperties.add(ventnor);
    yellow.add(ventnor);
    Property waterworks = new Property("Water Works", 150, RentType.UTILITY);
    boardProperties.add(waterworks);
    utilities.add(waterworks);
    Property atlantic = new Property("Atlantic", 260, 150, atlanticRS);
    boardProperties.add(atlantic);
    yellow.add(atlantic);

    boardProperties.add(new Property("Go To Jail"));

    Property pennsylvania = new Property("Pennsylvania", 320, 200, pennsylvaniaRS);
    boardProperties.add(pennsylvania);
    green.add(pennsylvania);
    Property northcarolina = new Property("North Carolina", 300, 200, northcarolinaRS);
    boardProperties.add(northcarolina);
    green.add(northcarolina);
    Property pacific = new Property("Pacific", 300, 20, pacificRS);
    boardProperties.add(pacific);
    green.add(pacific);

    Property shortlinerr = new Property("Short Line Railroad", 200, RentType.RAILROAD);
    boardProperties.add(shortlinerr);
    railroads.add(shortlinerr);

    Property park = new Property("Park", 350, 200, parkRS);
    boardProperties.add(park);
    blue.add(park);
    Property boardwalk = new Property("Boardwalk", 400, 200, boardwalkRS);
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
				// if yes, check the color of that new property and PUT THAT COLOR into a list containing property sets, ie colors that players can buy houses on
				// later, keep checking this list to see when the player actually wants to buy these houses
				if (canPlayerBuyAHouse(player, landedProperty)) {
					System.out.println("You've achieved a monopoly for " + map.get(landedProperty));
					Set<Property> color  = map.get(landedProperty);
						player.addToPossibleHouseList(color);
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
		
	    for (Property a: color) {
	    	if (a.getPropertyOwner() != player) {
	    		return false;
	    	}
	    }
	    	return true;
		}
	public static void considerHouseList(Player player, ArrayList <Set<Property>> monopoliesOwned) {
		
		outer: while (true) {
			for (int i = 0; i < monopoliesOwned.size(); i++) { // for every set in the list of housable sets
				
				// put this here because these variables are different per set
				int minHouseCountPerSet = 100;
				ArrayList<Property> propertiesWhereYouCanCurrentlyBuyAHouse = null;
				
				  Set<Property> propertySet = monopoliesOwned.get(i);
				  
				  // looping through each property in a set, to find what the minimum amount of houses in that set is
				  for (Property housableProperty: propertySet) {
					  if (housableProperty.getNumberOfHouses() < minHouseCountPerSet) { 
						  minHouseCountPerSet = housableProperty.getNumberOfHouses();
					  };
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
			System.out.println(otherPlayer.getToken() + " has won the game!");
			return true;
		}
		return false;
	}
}


