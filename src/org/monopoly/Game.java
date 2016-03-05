package org.monopoly;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.monopoly.Property.RentType;

import java.util.Map;

public class Game {
  
  //the list of all properties on the board
  ArrayList<Property> boardProperties = new ArrayList<Property>();
  
  //maps each property to what set (color) of properties it belongs to
  Map<Property, Set <Property>> map = new HashMap<>();
  
  Player playerOne = new RandomPlayer ("Dog");
  Player playerTwo = new RandomPlayer ("Thimble");
  
  Random random = new Random();
  
  private static String[] phrases = {"Player owns this property and nothing happens to this player."};

  public void playGame() throws IOException {
    
    addProperties();  // remember you have to CALL add properties
    
    playerOne.setLocation(boardProperties.get(0)); // players start at the first property on the arrayList
    playerTwo.setLocation(boardProperties.get(0));
    
    while(true) {
      
      if (twoPlayerGamePtOne(playerOne, playerTwo)) {
    	  break;
      }
      if (twoPlayerGamePtOne(playerTwo, playerOne)) {
    	  break;
      }
    }
  }

  public void addProperties() {
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
    boardProperties.add(new Property("Luxury Tax"));
    addSingleProperty(new Property("Boardwalk", 400, 200, 200, boardwalkRS), blue);
  }
  
  private void addSingleProperty(Property property, Set<Property> set) {
	  boardProperties.add(property);
      set.add(property);
      map.put(property, set);
  }
  
  private boolean twoPlayerGamePtOne (Player thisPlayer, Player otherPlayer) throws IOException {
    
	int dice = rollDice();
	boolean status = false;
	  
    //if player starts turn in jail 
    if (thisPlayer.getLocation() == boardProperties.get(10) && thisPlayer.getJailTime() >= 0) {
    	beingInJail(thisPlayer, otherPlayer);
    }
    else {	
        System.out.println("Player " + thisPlayer.getToken() + " has rolled a " + dice);
        status = twoPlayerGamePtTwo(thisPlayer, otherPlayer, dice);
    }
    return status;
  }
  private boolean twoPlayerGamePtTwo (Player thisPlayer, Player otherPlayer, int dice) throws IOException {  
	  
	  Property landed;
	    
        landed = moveSpace(thisPlayer, dice);
        thisPlayer.setLocation(landed);
        System.out.println("Player " + thisPlayer.getToken() + " has landed on " + landed);  
        afterLanding(thisPlayer, landed, dice);
        
        if (thisPlayer.getHousableSetList().size() > 0) {
        	checkMonopolies(thisPlayer);
        }
        
        if (thisPlayer.getMortgagedProperties().size() > 0) {
        	unmortgageA(thisPlayer);
        }
        
        if (checkBalance(thisPlayer, otherPlayer)) {
        	// return void means to exit out of the method at that statement, without running the following statements.
        	return true;
        }
        System.out.println("---------------------");  

    return false;
  }
  
  
  public int rollDice() {
    return (random.nextInt(6) + 1 + random.nextInt(6) + 1);
  }
  
  public Property moveSpace(Player player, int roll) {
    
    // get the current index of where the player is
    int index = player.getLocation().getLocationIndex(boardProperties);
    
    // giving players $200 every time they pass Go
    if (index + roll >= boardProperties.size()) {
      System.out.println("Collect $200 for passing Go.");
      player.setBalance(player.getBalance() + 200);
    }
    
    index = ((index + roll) % boardProperties.size());
    
    return boardProperties.get(index);
  }
  
  public void afterLanding(Player player, Property landedProperty, int diceRoll) throws IOException {
	  
	 // if property is not owned by anyone
	 if (landedProperty.getPropertyOwner() == null) {
		 // if the property is a board property, nothing happens
		 if (!landedProperty.getBuyableStatus()) {
			   System.out.println(player + " landed on " + landedProperty);
			   specialProperties(player, landedProperty);
			   System.out.println(player + " has $" + player.getBalance());
		 }
		 // if property is not a board property, ask player if they want to buy the property
		 else {
			 System.out.println(player + " has $" + player.getBalance() + " and the property costs $" + landedProperty.getBuyCost());
			 String prompt = "Do you want to buy this property? (y/n)";
			// if player wants to buy the property
			 if (player.doYouWantToDoThis(prompt)) {
				// if player has the money
				 if (player.getBalance() > landedProperty.getBuyCost()) {
					 buyProperty(player, landedProperty);
					 
					 //if this newly purchased property completes a monopoly, save the set
					 if (isPropertyPartOfHousableSet(player, landedProperty)) {
						 addToHousableSetList(player, landedProperty);
					 }
				 }
				 // if player does not have the money
				 else {
					 // if player has properties to mortgage 
					 System.out.println(player + " has $" + player.getBalance() + " and the property costs $" + landedProperty.getBuyCost());
					 
					 if (player.getPropertiesOwned().size() > player.getMortgagedProperties().size()) {
						 
						 String promptB = "Player cannot afford to buy this property. Would you like to mortgage some properties? (y/n)";
						 if (player.doYouWantToDoThis(promptB)) {
							 mortgagePropertiesA(player);
							 
							 // if this is enough for player to buy the property, let them
							 if (player.getBalance() > landedProperty.getBuyCost()) {
								 buyProperty(player, landedProperty);
								 
								 //if this newly purchased property completes a monopoly, save the set
								 if (isPropertyPartOfHousableSet(player, landedProperty)) {
									 addToHousableSetList(player, landedProperty);
								 }
							 }
							 else {
								 System.out.println(player + " still does not have enough money to buy the property.");
							 }
						 }
						 else {
							 System.out.println(player + " cannot afford to buy this property.");
						 }				 
					 }
					 // if player does not have any properties to mortgage
					 else {
						 System.out.println(player + " cannot afford to buy this property.");
					 }
				 }
			 }
			 // if player does not want to buy
			 else {
				 System.out.println(player + " has chosen not to buy this property.");
			 }
		 }
	 }
	 
	 // if property is owned by another player
	 else if (!landedProperty.getPropertyOwner().equals(player)) {
		 // if property is mortgaged, no rent must be paid. else, pay rent
		 if (landedProperty.getMortgageStatus()) {
		      System.out.println("This property is mortgaged and no rent must be paid.");
		      System.out.println(player + " has $" + player.getBalance());
		 }
		 else {
			 payRent(player, landedProperty, diceRoll);
		 }
	 }
	 
	 // if property is owned by the player
	 else { 
		 String phrase = phrases[(int) (Math.random() * phrases.length)];
	     System.out.println(phrase);
	     System.out.println(player + " has $" + player.getBalance());
	 }
  }
  
  
  public void buyProperty(Player player, Property landedProperty) {
	  System.out.println(player + " has bought " + landedProperty + " for $" + landedProperty.getBuyCost());
      player.setBalance(player.getBalance() - landedProperty.getBuyCost());
      System.out.println(player + " has $" + player.getBalance() + " left.");
      landedProperty.setPropertyOwner(player);
      player.addToPropertiesOwnedList(landedProperty);
  }
  public void payRent(Player player, Property landedProperty, int diceRoll) {
	  // prints statement of what's happening; deducts rent from player's balance; prints out his new balance; adds rent to other player's balance 
	  System.out.println(player + " has paid $" + landedProperty.getRentCost(diceRoll) + " for landing on " + landedProperty);
	  player.setBalance(player.getBalance() - landedProperty.getRentCost(diceRoll));
	  System.out.println(player + " has $" + player.getBalance() + " left.");
	  landedProperty.getPropertyOwner().setBalance(landedProperty.getPropertyOwner().getBalance() + landedProperty.getRentCost(diceRoll));
  }
  
  
  public void checkMonopolies (Player player) {
	// stop asking the below prompt if the "generate" list is null (either because houses have been maxed out or player does not have enough money
	 if (generatingListOfPropsWherePlayerCanBuyHouse(player, player.getHousableSetList()).size() > 0) {
		 
		 String prompt = "Player has completed a monopoly and are eligible to buy houses. Would you like to buy any? (y/n)";
	     if (player.doYouWantToDoThis(prompt)) {
	    	 
	    	System.out.println(player + " has $" + player.getBalance());
	    	String promptB = "Would you like to mortgage some properties first? (y/n)";
	    	
	    	if (player.doYouWantToDoThis(promptB)) {
	    		mortgagePropertiesA(player);
	    	}
	    	else {
	    		System.out.println(player + " has chosen not to mortgage any properties.");
	    	}
	    	buyHouseA(player);
	     }
	     else {
	    	 System.out.println(player + " has chosen not to buy any houses.");
	     }
	 }
  }
  public void buyHouseA (Player player) {
	Property houseBought;
	
	do {
		houseBought = player
				.buyHouseB(generatingListOfPropsWherePlayerCanBuyHouse(player, player.getHousableSetList()));

		if (houseBought != null) {
			player.whatHappensWhenYouBuyHouse(houseBought);
		}
	} 
	while (houseBought != null);
  }
  /**
   * javadoc documentation. note the two stars above
   * the method below checks whether a property that a player owns is part of a property set/color where he can buy houses
   * the method does not check whether he has enough money to 
   */
  public boolean isPropertyPartOfHousableSet (Player player, Property landedProperty) {
    Set<Property> color  = map.get(landedProperty);
    
    // cannot call a method on a null object
    // ensure that utilities and railroads aren't eligible for monopolies
    for (Property a: color) {
        if (a.getPropertyOwner() != player || landedProperty.getRentType() == RentType.UTILITY || landedProperty.getRentType() == RentType.RAILROAD) {
        	return false;
        }
    }
    return true;
  }
  public void addToHousableSetList(Player player, Property landedProperty) {
	  System.out.println(player + " has achieved a monopoly for " + map.get(landedProperty));
      Set<Property> color = map.get(landedProperty);
      player.addToHousableSets(color);
  }
  public ArrayList<Property> generatingListOfPropsWherePlayerCanBuyHouse (Player player, ArrayList <Set<Property>> housableSetsA) {
	  
	ArrayList<Property> housablePropertiesToPassToPlayer = new ArrayList<>();
	
	// loop through the list of all sets to pick out each individual set 
	for (int i = 0; i < housableSetsA.size(); i++) {

		Set<Property> propertySetB = housableSetsA.get(i);
		
		// for each set, return minimum house number across the properties for that set
		int minHouseCountPerSet = findMinimumHouseNumber(propertySetB);
	
		// looping through every property in a set
		for (Property housablePropertyC : propertySetB) { 
			// if player can afford a house there, if the houses are being built evenly, if there aren't already the max number of houses
			// then add that property to the list we present to the player
			if (player.getBalance() > housablePropertyC.getHouseCost() && housablePropertyC.getNumberOfHouses() == minHouseCountPerSet 
			   && housablePropertyC.getNumberOfHouses() < 5) {
					housablePropertiesToPassToPlayer.add(housablePropertyC);
			}
		}
	}
	return housablePropertiesToPassToPlayer;		   	
  }
  public int findMinimumHouseNumber(Set<Property> propertySetB) {
	  int minHouseCountPerSet = 100;
		
  	  // loop through each property in the set to find the minimum house number 
	  for (Property housablePropertyC: propertySetB) {
		  if (housablePropertyC.getNumberOfHouses() < minHouseCountPerSet) { 
			  minHouseCountPerSet = housablePropertyC.getNumberOfHouses();
		  }
	  }
	  return minHouseCountPerSet;
  }     
  
  
  public void mortgagePropertiesA (Player player) {
	  
	  ArrayList<Property> propertiesToPassForMortgage = new ArrayList<Property>();
	  
	  for (int i = 0; i < player.getPropertiesOwned().size(); i++) {
		  if (!player.getPropertiesOwned().get(i).getMortgageStatus() && player.getPropertiesOwned().get(i).getRentType() == RentType.REGULAR) {
			  propertiesToPassForMortgage.add(player.getPropertiesOwned().get(i));
		  }
	  }
	  
	  Property mortgaged;
		do {
			mortgaged = player.mortgagePropertiesB(propertiesToPassForMortgage);
			
			if (mortgaged != null) {
  			player.whatHappensWhenYouMortgage(mortgaged);
  		}
  	}
      while (mortgaged != null);
  }
  public void unmortgageA (Player player) {
	
	String prompt = "You have mortgaged properties. Would you like to unmortgage them? (y/n)";

	if (player.doYouWantToDoThis(prompt)) { 
		
		Property unmortgaged;
		do {
			unmortgaged = player.unmortgageB(player.getMortgagedProperties());
			
			if (unmortgaged != null) {
	  			player.whatHappensWhenYouUnmortgage(unmortgaged);
			}
		}
		while (unmortgaged!= null);
	 }
  }
  
  
  public boolean checkBalance(Player player, Player otherPlayer) {
	if (player.getBalance() < 0) {

		// as long as player has properties, they should mortgage them to save the game. otherwise, they lose the game.
		if (player.getPropertiesOwned().size() > player.getMortgagedProperties().size()) {
			
			System.out.println("Player is out of money and must mortgage properties.");
			mortgagePropertiesA(player);
			
			if (player.getBalance() < 0) {
				System.out.println(otherPlayer.getToken() + " has won the game!");
				return true;
			}
		} 
		else {
			System.out.println(otherPlayer.getToken() + " has won the game!");
			return true;
		}
	}
	return false;
  } 
  
  
  public void specialProperties (Player player, Property property) {
	 
	  int locationIndex = property.getLocationIndex(boardProperties);
	  
	  switch(locationIndex) {
	  	case 0: //Go -- collecting $200 cannot be done here because you collect that every time you PASS go
	  		System.out.print("");
	  	case 10: //Jail 
	  		System.out.println("Passing through jail! Enjoy visiting!");
	  	case 20: //Free Parking 
	  		System.out.println("Chill on Free Parking. Nothing happens.");
	  		
	  	case 4: //Income Tax (pay $200 or 10%)
	  		if ((player.getBalance() * 10) < 200) {
	  			subtractMoney(player, (player.getBalance() * 10));
	  		}
	  		else {
	  			subtractMoney(player, 200);
	  		}
	  		
	  	case 38: //Luxury Tax
	  		subtractMoney(player, 75);
	  	
	  	case 30: //Go To Jail
	  		System.out.println("Oh no! " + player + " is going to jail.");
	  		player.setLocation(boardProperties.get(10));
	  		addJailTime(player); 
	  	
	  	/*
	  	 * case 2: //Community Chest	
	  	case 17: 	
	  	case 33: 	
	  		
	  	case 7: //Chance
	  	case 22: 
	  	case 36:
	  	 */ 	
	  }
  }
  public void addMoney(Player player, int money) {
	  player.setBalance(player.getBalance() + money);
  }
  public void subtractMoney(Player player, int money) {
	  player.setBalance(player.getBalance() - money);
  }
  public void addJailTime(Player player) {
	  player.setJailTime(player.getJailTime() + 1);
  }
  public void beingInJail(Player player, Player otherPlayer) throws IOException {
	  
	  /**
	   * In JAIL, player status JailTime = -1 if player is not in jail (or has just gotten out of jail)
	   * 0 if player has just landed in jail 
	   * and 0-1 for the subsequent rolls player tries to make to get out of jail 
	   */

	  // attempt to roll doubles
	  int diceOne = random.nextInt(6) + 1;
	  int diceTwo = random.nextInt(6) + 1;
	  
	  System.out.println(player + " rolls a " + diceOne + " and a " + diceTwo);
	  
	  if (diceOne == diceTwo) {
		  System.out.println("Player has rolled out of jail!");
		  player.setJailTime(-1);
		  twoPlayerGamePtTwo(player, otherPlayer, diceOne + diceTwo);
	  }
	  else {
		  addJailTime(player);
		  System.out.println("Player is still in jail and has rolled " + player.getJailTime() + " time.");
	  

		  // if player has get out of jail free card, use it, get out jail, do not move until next turn 
		  if (player.getOutOfJailFreeCard) {
			  player.setGetOutOfJailFreeCard(false);
			  player.setJailTime(-1);
		  }
		  else {
			  String prompt = "Do you want to pay $50 to get out of jail? (y/n)";
			  if (player.doYouWantToDoThis(prompt)) {
				  subtractMoney(player, 50);
				  player.setJailTime(-1);
			  }
		  }
		  if (player.getJailTime() == 3) {
			  System.out.println("Player has rolled 3 times and now must pay $50 to get out of jail.");
			  subtractMoney(player, 50);
			  checkBalance(player, otherPlayer);
		  }
	  }
  }
}


