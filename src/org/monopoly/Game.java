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
  
  // TODO: private final, make a getter
  //the list of all properties on the board
  ArrayList<Property> boardProperties = new ArrayList<Property>();
  
  // TODO: private final, no one should be touching this
  //maps each property to what set (color) of properties it belongs to
  Map<Property, Set <Property>> map = new HashMap<>();
  
  // TODO: Private final
  Player playerOne, playerTwo;
  
  // TODO: Take in a random to the constructor to help testing.  Also, private final
  Random random = new Random();
  
  // TODO: You've only got one String!  That doesn't deserve an array!
  private static String[] phrases = {"Player owns this property and nothing happens to this player."};
  
  public Game (Player playerOne, Player playerTwo) {
	  this.playerOne = playerOne;
	  this.playerTwo = playerTwo;
  }

  public void playGame() throws IOException, InterruptedException {
    // TODO: Move addProperties and the two setLocations into the constructor since they are involved in the setup of the game.
    addProperties();  // remember you have to CALL add properties
    
    playerOne.setLocation(boardProperties.get(0)); // players start at the first property on the arrayList
    playerTwo.setLocation(boardProperties.get(0));
    
    while(true) {
      // TODO: Naming!  What does twoPlayerGamePtOne mean?? What is actually happening?  See my further down comment on naming as commands.
      if (twoPlayerGamePtOne(playerOne, playerTwo)) {
    	  break;
      }
      if (twoPlayerGamePtOne(playerTwo, playerOne)) {
    	  break;
      }
    }
  }

  // TODO: I'd consider moving this into another class called like BoardCreator, just for readability.  It's a giant thing in the middle of this class that is sort of unrelated.  
  // Make a static method addProperties where you pass in your empty board properties array list and your map of sets.
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
  
    addSingleProperty(new Property("Reading Railroad", RentType.RAILROAD, readingrrRS), railroads);
  
    addSingleProperty(new Property("Oriental Ave", 100, 50, 50, orientalRS), lightBlue);
    boardProperties.add(new Property("Chance"));
    addSingleProperty(new Property("Vermont Ave", 100, 50, 50, vermontRS), lightBlue);
    addSingleProperty(new Property("Connecticut Ave", 100, 50, 60, connecticutRS), lightBlue);
  
    boardProperties.add(new Property("Jail"));
    
    addSingleProperty(new Property("St. Charles Place", 140, 100, 70, stcharlesRS), pink);
    addSingleProperty(new Property("Electric Company", RentType.UTILITY), utilities);
    addSingleProperty(new Property("States Ave", 140, 100, 70, statesRS), pink);
    addSingleProperty(new Property("Virgina Ave", 160, 100, 80, virginiaRS), pink);
    
    addSingleProperty(new Property("Pennsylvania Railroad", RentType.RAILROAD, pennsylvaniarrRS), railroads);
    
    addSingleProperty(new Property("St. James Place", 180, 100, 90, stjamesRS), orange);
    boardProperties.add(new Property("Community Chest"));
    addSingleProperty(new Property("Tennessee Ave", 180, 100, 90, tennesseeRS), orange);
    addSingleProperty(new Property("New York Ave", 200, 100, 100, newyorkRS), orange);
    
    boardProperties.add(new Property("Free Parking"));
    
    addSingleProperty(new Property("Kentucky", 220, 150, 110, kentuckyRS), red);
    boardProperties.add(new Property("Chance"));
    addSingleProperty(new Property("Indiana", 220, 150, 110, indianaRS), red);
    addSingleProperty(new Property("Illinois", 240, 150, 120, illinoisRS), red);
    
    addSingleProperty(new Property("B & O Railroad", RentType.RAILROAD, borrRS), railroads);
    
    addSingleProperty(new Property("Atlantic", 260, 150, 130, atlanticRS), yellow);
    addSingleProperty(new Property("Ventnor", 260, 150, 130, ventnorRS), yellow);
    addSingleProperty(new Property("Water Works", RentType.UTILITY), utilities);
    addSingleProperty(new Property("Marvin Gardens", 280, 150, 140, marvingardensRS), yellow);
    
    boardProperties.add(new Property("Go To Jail"));
    
    addSingleProperty(new Property("Pacific", 300, 20, 150, pacificRS), green);
    addSingleProperty(new Property("North Carolina", 300, 200, 150, northcarolinaRS), green);
    boardProperties.add(new Property("Community Chest"));
    addSingleProperty(new Property("Pennsylvania", 320, 200, 160, pennsylvaniaRS), green);
    
    addSingleProperty(new Property("Short Line Railroad", RentType.RAILROAD, shortlinerrRS), railroads);
    
    boardProperties.add(new Property("Chance"));
    addSingleProperty(new Property("Park", 350, 200, 175, parkRS), blue);
    boardProperties.add(new Property("Luxury Tax"));
    addSingleProperty(new Property("Boardwalk", 400, 200, 200, boardwalkRS), blue);
  }
  
  // TODO: This would also get moved into that BoardCreator class.  That would also help keep this class a bit cleaner.
  private void addSingleProperty(Property property, Set<Property> set) {
	  boardProperties.add(property);
      set.add(property);
      map.put(property, set);
  }
  
  private boolean twoPlayerGamePtOne (Player thisPlayer, Player otherPlayer) throws IOException, InterruptedException {
    
	int dice = rollDice();
	boolean status = false;
	  
    //if player starts turn in jail 
    if (thisPlayer.getLocation() == boardProperties.get(10) && thisPlayer.getJailTime() >= 0) {  // TODO: You shouldn't need to check their position.  Jailtime or whatever variable should clearly tell you if they're in jail or not.
      // TODO: Lol.  Consider you are ordering someone to do something.  You're like "Hey Isaac, being in jail this player".  That sounds wrong.  More like "Hey Isaac, handleJail for this player" or something.
    	beingInJail(thisPlayer, otherPlayer);   
    }
    else {	
        System.out.println("Player " + thisPlayer.getToken() + " has rolled a " + dice);
        // TODO: Part two of the game?? What??  "Isaac!  Hey do a thing for me: twoPlayerGamePtTwo"
        status = twoPlayerGamePtTwo(thisPlayer, otherPlayer, dice);
    }
    return status;
  }
  
  // TODO: This doesn't really feel like enough code to really be in a separate method if you can't come up for a good name for what it is separate from the previous.
  private boolean twoPlayerGamePtTwo (Player thisPlayer, Player otherPlayer, int dice) throws IOException, InterruptedException {  // TODO: Where are all these exceptions coming from? You should try/catch them instead of bubbling them up like this.
	  
	  Property landed;
	    
        landed = moveSpace(thisPlayer, dice);
        thisPlayer.setLocation(landed);
        System.out.println("Player " + thisPlayer.getToken() + " has landed on " + landed);  
        afterLanding(thisPlayer, otherPlayer, landed, dice);
        
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
        //Thread.sleep(2000);
        System.out.println("---------------------");  

    return false;
  }
  
  // TODO: Should probably be private since other people shouldn't be calling this?
  public int rollDice() {
    return (random.nextInt(6) + 1 + random.nextInt(6) + 1);
  }
  // TODO: Should others be calling this?  Probably not, so private.
  public Property moveSpace(Player player, int roll) {
    
    // get the current index of where the player is
    int index = player.getLocation().getLocationIndex(boardProperties);
    // TODO: You might consider doing index += roll here and save doing it twice in the next couple of lines.
    // giving players $200 every time they pass Go
    if (index + roll >= boardProperties.size()) {
      System.out.println("Collect $200 for passing Go.");
      player.setBalance(player.getBalance() + 200);
    }
    
    index = ((index + roll) % boardProperties.size());
    
    return boardProperties.get(index);
  }
  // TODO: Do you really need to pass otherPlayer in here?
  public void afterLanding(Player player, Player otherPlayer, Property landedProperty, int diceRoll) throws IOException, InterruptedException {  // TODO: These exceptions probably shouldn't be bubbling up.
	  
	 // if property is not owned by anyone
	 if (landedProperty.getPropertyOwner() == null) {
		 // if the property is a board property, nothing happens
		 if (!landedProperty.getBuyableStatus()) {
			   specialProperties(player, otherPlayer, landedProperty);
			   System.out.println(player + " has $" + player.getBalance());
		 }
		 // if property is not a board property, ask player if they want to buy the property
		 else {
			 System.out.println(player + " has $" + player.getBalance() + " and the property costs $" + landedProperty.getBuyCost());
			 String prompt = "Do you want to buy this property? (y/n)";
			// if player wants to buy the property
			 if (player.doYouWantToDoThis(prompt)) {
				// if player has the money
				 if (player.getBalance() > landedProperty.getBuyCost()) {  // TODO: You should probably check if they have the money before asking if they want to buy it.
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
						 
					   // TODO: If you don't care about the value of the previous prompt anymore (I don't think you do, just reuse prompt.
						 String promptB = "Player cannot afford to buy this property. Would you like to mortgage some properties? (y/n)";
						 if (player.doYouWantToDoThis(promptB)) {
							 mortgagePropertiesA(player);  // TODO: We talked aboutthis but A and B suffixes don't count as proper naming.
							 
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
		      // TODO: Why are you printing out the player's balance here?
		      System.out.println(player + " has $" + player.getBalance());
		 }
		 else {
			 payRent(player, landedProperty, diceRoll);
		 }
	 }
	 
	 // if property is owned by the player
	 else { 
	   // TODO: Just print the one phrase here.
		 String phrase = phrases[(int) (Math.random() * phrases.length)];
	     System.out.println(phrase);
	     // TODO: WHy are you printing the player's balance here?
	     System.out.println(player + " has $" + player.getBalance());
	 }
  }
  
  
  public void buyProperty(Player player, Property landedProperty) {
	  System.out.println(player + " has bought " + landedProperty + " for $" + landedProperty.getBuyCost());
      player.setBalance(player.getBalance() - landedProperty.getBuyCost());
      System.out.println(player + " has $" + player.getBalance() + " left.");
      landedProperty.setPropertyOwner(player);
      player.addToPropertiesOwnedList(landedProperty);
      // TODO: Why not check new monopolies here instead of in multiple places above?
  }
  public void payRent(Player player, Property landedProperty, int diceRoll) {
	  // prints statement of what's happening; deducts rent from player's balance; prints out his new balance; adds rent to other player's balance 
	  System.out.println(player + " has paid $" + landedProperty.getRentCost(diceRoll) + " in rent to " + landedProperty.getPropertyOwner() + " for landing on " + landedProperty);
	  player.setBalance(player.getBalance() - landedProperty.getRentCost(diceRoll));
	  System.out.println(player + " has $" + player.getBalance() + " left.");
	  // TODO: Sounds like you really need a Player .addmoney function
	  landedProperty.getPropertyOwner().setBalance(landedProperty.getPropertyOwner().getBalance() + landedProperty.getRentCost(diceRoll));
  }
  
  
  public void checkMonopolies (Player player) throws InterruptedException { // TODO: Don't bubble this exception up
	// stop asking the below prompt if the "generate" list is null (either because houses have been maxed out or player does not have enough money
    // TODO: getBuyableHouseLocations()
	 if (generatingListOfPropsWherePlayerCanBuyHouse(player, player.getHousableSetList()).size() > 0) {
		 
		 String prompt = "Player has completed a monopoly and is eligible to buy houses. Would you like to buy any houses? (y/n)";
	     if (player.doYouWantToDoThis(prompt)) {
	    	 // TODO: The GUI should really jsut display how much money you have at all times, so this is just useful for ConsolePlayer
	    	System.out.println(player + " has $" + player.getBalance());
	    	
	    	if (player.getPropertiesOwned().size() > player.getMortgagedProperties().size()) {
	    		
	    		String promptB = "Would you like to mortgage some properties first? (y/n)";
		    	
		    	if (player.doYouWantToDoThis(promptB)) {
		    		mortgagePropertiesA(player);
		    	}
		    	else {
		    		System.out.println(player + " has chosen not to mortgage any properties.");
		    	}
	    	}
	    	buyHouseA(player);
	     }
	     else {
	    	 System.out.println(player + " has chosen not to buy any houses.");
	     }
	 }
  }
  
  // TODO: buyHouse()
  public void buyHouseA (Player player) throws InterruptedException {
	Property houseBought;
	
	// TODO: You could make this loop simpler by changing it to a normal while loop, and doing if (houseBought == null) break;  Change the while to while(true) and move houseBought to being declared inside the loop.
	do {
	  // TODO: player.choosePropertyForHouse()
		houseBought = player.buyHouseB(generatingListOfPropsWherePlayerCanBuyHouse(player, player.getHousableSetList()));

		if (houseBought != null) {
		  // TODO: I'm assuming I said something about renaming this somewhere else.
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
  // TODO: How about isPropertyPartOfMonopoly()
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
  // TODO: How about addMonpoly?
  public void addToHousableSetList(Player player, Property landedProperty) {
	  System.out.println(player + " has achieved a monopoly for " + map.get(landedProperty));
      Set<Property> color = map.get(landedProperty);
      player.addToHousableSets(color);
  }
  
  // TODO: houseableSetsA -> monopolies
  public ArrayList<Property> generatingListOfPropsWherePlayerCanBuyHouse (Player player, ArrayList <Set<Property>> housableSetsA) {
	  // TODO: housablePropertiesToPassToPlayer -> buildableProperties?  Or since this is what we are returning just "properties" since we don't have to distingusih from any other thing called properties in this function.
	ArrayList<Property> housablePropertiesToPassToPlayer = new ArrayList<>();
	
	// loop through the list of all sets to pick out each individual set 
	for (int i = 0; i < housableSetsA.size(); i++) {
// TODO: propertSetB -> monopoly
		Set<Property> propertySetB = housableSetsA.get(i);
		
		// for each set, return minimum house number across the properties for that set
		int minHouseCountPerSet = findMinimumHouseNumber(propertySetB);
	
		// looping through every property in a set
		// TODO: housablePropertyC -> property
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
  // TODO: propertySetB -> monopoly
  public int findMinimumHouseNumber(Set<Property> propertySetB) {
    // TODO: minHouseCountPerSet -> minimum or just min will work.
	  int minHouseCountPerSet = 100;
		
  	  // loop through each property in the set to find the minimum house number
	  // TODO: housablePropertyC -> property
	  for (Property housablePropertyC: propertySetB) {
		  if (housablePropertyC.getNumberOfHouses() < minHouseCountPerSet) { 
			  minHouseCountPerSet = housablePropertyC.getNumberOfHouses();
		  }
	  }
	  return minHouseCountPerSet;
  }     
  
  // TODO: How about just mortgageProperties
  public void mortgagePropertiesA (Player player) throws InterruptedException {
	  // TODO: mortgageableProperties or just properties
	  ArrayList<Property> propertiesToPassForMortgage = new ArrayList<Property>();
	  
	  // TODO: Instead, use a foreach loop: for (Property p : player.getPropertiesOwned()) { ...
	  for (int i = 0; i < player.getPropertiesOwned().size(); i++) {
		  if (!player.getPropertiesOwned().get(i).getMortgageStatus()) {
			  propertiesToPassForMortgage.add(player.getPropertiesOwned().get(i));
		  }
	  }
	  
	  // TODO: I would probably term this "chosen" or "toMortgage" since it hasn't been mortgaged yet.
	  // TODO: Switch to a normal while loop like mentioned above
	  Property mortgaged;
		do {
		  // TODO: mortgagePropertiesB -> choosePropertyToMortgage
			mortgaged = player.mortgagePropertiesB(propertiesToPassForMortgage);
			
			if (mortgaged != null) {
			  // TODO: Something like handleMortgage
  			player.whatHappensWhenYouMortgage(mortgaged);
			}
		}
		while (mortgaged != null);
  }
  
  // TODO: unmortgageProperties
  public void unmortgageA (Player player) throws InterruptedException {
	
	String prompt = "You have mortgaged properties. Would you like to unmortgage them? (y/n)";

	if (player.doYouWantToDoThis(prompt)) { 
		// TODO: again chosen or toUnmortgage
		Property unmortgaged;
		// TODO: Switch to normal while loop.  Do/whiles are rare and thus more confusing
		do {
		  // TODO: player.choosePropertyToUnmortgage
			unmortgaged = player.unmortgageB(player.getMortgagedProperties());
			
			if (unmortgaged != null) {
			    // TODO: Something like handleUnmortgage
	  			player.whatHappensWhenYouUnmortgage(unmortgaged);
			}
		}
		while (unmortgaged!= null);
	 }
  }
  
  // TODO: Don't bubble up this exception.  Methods should only throw an exception that makes sense logically.  It doesn't make sense that checking someones balance could be interrupted.  It might make sense if checking someones balalnce resulted in an OutOfMoneyExceptoin or something.
  public boolean checkBalance(Player player, Player otherPlayer) throws InterruptedException {
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
  
  // TODO: these exceptions shouldn't be bubbling up
  public void specialProperties (Player player, Player otherPlayer, Property property) throws IOException, InterruptedException {
	 
	  int locationIndex = property.getLocationIndex(boardProperties);
	  // TODO: It would be a lot cleaner if there was an enum in the property that was GO, JAIL, FREE_PARKING etc. instead of relying on an index like this.
	  switch(locationIndex) {
	  	case 0: //Go -- collecting $200 cannot be done here because you collect that every time you PASS go
	  		System.out.print("");  // TODO: Why are you printing nothing?
	  		break;
	  	case 10: //Jail 
	  	  // TODO: Stuff like this should really be passed to the player.log method you should create.
	  		System.out.println("Passing through jail! Enjoy visiting!");
	  		break;
	  	case 20: //Free Parking 
	  		System.out.println("Chill on Free Parking. Nothing happens.");
	  		break;
	  		
	  	case 4: //Income Tax (pay $200 or 10%)
	  		if ((player.getBalance() * 10) < 200) {  // TODO: Balance TIMES 10?  Shouldn't it be divided?
	  			System.out.println("Pay 10% of your balance on income tax.");  // TODO: This line is unnecessary
	  			System.out.println(player + " has paid $" + (player.getBalance() * 10) + " to Income Tax.");
	  			subtractMoney(player, (player.getBalance() * 10));
	  		}
	  		else {
	  			System.out.println("Pay $200 to Income Tax.");  // TODO: This line is unnecesary
	  			System.out.println(player + " has paid $200 to Income Tax.");
	  			subtractMoney(player, 200);
	  		}
	  		break;
	  	case 38: //Luxury Tax
	  		System.out.println("Pay $75 to Luxury Tax.");  // TODO: This line is unnecssary
  			System.out.println(player + " has paid $75 to Luxury Tax.");
	  		subtractMoney(player, 75);
	  		break;
	  	
	  	case 30: //Go To Jail
	  		System.out.println("Oh no! " + player + " is going to jail.");
	  		player.setLocation(boardProperties.get(10));
	  		addJailTime(player); 
	  		break;
	  	
	  	case 2: //Community Chest	
	  	case 17: 	
	  	case 33: 	
	  		communityChest(player, otherPlayer);
	  		break;
	  		
	  	case 7: //Chance
	  	case 22: 
	  	case 36:
	  		chance(player, otherPlayer);
	  		break;
	  }
  }
  public void communityChest(Player player, Player otherPlayer) throws IOException, InterruptedException {
	  int rand = random.nextInt(8);
	  
	  switch(rand) {
	  	case 0: 
	  		 System.out.println("Community Chest: Collect $50 from every player.");
	  		 addMoney(player, 50);
	  		 // TODO: You don't really need the other player reference here, you can figure it out.  That'll save you from having to pass it down through a bunch of methods.
	  		 subtractMoney(otherPlayer, 50); 
	  		 break;
	  	case 1: 
	  		System.out.println("Community Chest: Collect for services $25.");
	  		addMoney(player, 25);
	  		break;
	  	case 2: 
	  		System.out.println("Community Chest: Advance to Go. Collect $200.");
	  		player.setLocation(boardProperties.get(0));
			addMoney(player, 200);
		// TODO: Noooooo this is really bad.  There are a bunch of side effects of this function, you can't just call it like this.  Also, when you return from the current method it will go there and do all the stuff you want anyway.
			// This is sort of like a solider telling a general to do something.
			twoPlayerGamePtTwo (player, otherPlayer, 0);  
	  		break;
	  	case 3: 
	  		System.out.println("Community Chest: Pay hospital $100.");
	  		subtractMoney(player, 100);
	  		break;
	  	case 4: 
	  		System.out.println("Community Chest: Get out of jail free.");
	  		player.setGetOutOfJailFreeCard(true);
	  		break;
	  	case 5: 
	  		System.out.println("Community Chest: Go directly to Jail. Do not pass Go, do not collect $200.");
	  		player.setLocation(boardProperties.get(10));
	  		addJailTime(player); 
	  		break;
	  	case 6: 
	  		
	  		System.out.println("Community Chest: You are assessed for street repairs. Pay $85 per house, $115 per hotel.");
	  		
	  		int houseCount = 0;
	  		int hotelCount = 0;
	  		
	  		for (int i = 0; i < player.getPropertiesOwned().size(); i++) {
	  			houseCount += player.getPropertiesOwned().get(i).getNumberOfHouses();
	  			
	  			if (player.getPropertiesOwned().get(i).getNumberOfHouses() == 5) {
	  				hotelCount += 1;
	  				houseCount -= 5;
	  			}
	  		}
	  		
	  		System.out.println(player + " owns " + houseCount + " houses and " + hotelCount + " hotels.");
	  		System.out.println(player + " will pay $" + houseCount*85 + " for houses and $" + hotelCount*115 + " for hotels.");
	  		subtractMoney(player, (houseCount*85 + hotelCount*115));
	  		break;
	  		
	  	case 7: 
	  		System.out.println("Community Chest: From sale of stock, collect $45.");
	  		addMoney(player, 45);
	  		break;
	  }
  }
  public void chance(Player player, Player otherPlayer) throws IOException, InterruptedException {
	  int rand = random.nextInt(12);
	  
	  switch(rand) {
		  case 0: 
			  System.out.println("Chance: Advance to Go, collect $200");
			  player.setLocation(boardProperties.get(0));
			  addMoney(player, 200);
			  twoPlayerGamePtTwo(player, otherPlayer, 0);  // TODO: again nooooooooo
			  break;
		  case 1: 
			  System.out.println("Chance: Bank pays you dividend of $50.");
			  addMoney(player, 50);
			  break;
		  case 2: 
			  System.out.println("Chance: Go back 3 spaces.");
			  int index = player.getLocation().getLocationIndex(boardProperties);
			  player.setLocation(boardProperties.get(index - 3));  // TODO: What if they are at property 0?
			  twoPlayerGamePtTwo(player, otherPlayer, 0);  // TODO: Stahp
			  break;
		  case 3: 
			  System.out.println("Chance: Go directly to Jail. Do not pass Go, do not collect $200");
			  player.setLocation(boardProperties.get(10));
		  	  addJailTime(player); 
			  break;
		  case 4: 
			  System.out.println("Chance: Pay poor tax of $15");
			  subtractMoney(player, 15);
			  break;
		  case 5: 
			  System.out.println("You have been elected chairman of the board. Pay each player $50");
			  subtractMoney(player, 50);
			  addMoney(otherPlayer, 50);
			  break;
		  case 6: 
			  System.out.println("Advance to Reading Railroad. If you pass Go, collect $200");
			  int indexB = player.getLocation().getLocationIndex(boardProperties);
			  
			  if (indexB > 5) {
				  System.out.println("Collect $200 for passing Go.");
				  addMoney(player, 200);
			  }
			  
			  player.setLocation(boardProperties.get(5));
			  twoPlayerGamePtTwo (player, otherPlayer, 0);  // TODO: Plz stahp
			  
			  break;
		  case 7: 
			  System.out.println("Advance to Boardwalk.");
			  player.setLocation(boardProperties.get(39));
			  twoPlayerGamePtTwo (player, otherPlayer, 0);  // TODO: Y u do dis
			  break;
		  case 8: 
			  System.out.println("Your building and loan matures. Collect $150.");
			  addMoney(player, 150);
			  break;
		  case 9: 
			  System.out.println("Advance to Illinois Ave.");
			  player.setLocation(boardProperties.get(24));
			  twoPlayerGamePtTwo (player, otherPlayer, 0);  // TODO: nope
			  break;
		  case 10: 
			  System.out.println("Get out of jail free.");
			  player.setGetOutOfJailFreeCard(true);
			  break;
		  case 11: 
			  System.out.println("Make general repairs on your properties. For each house pay $25. For each hotel pay $100");
		  		// TODO: Since you do this in two places, instead make a helper function that returns number of houses & hotels (or two helper functions one for each)
		  		int houseCount = 0;
		  		int hotelCount = 0;
		  		
		  		for (int i = 0; i < player.getPropertiesOwned().size(); i++) {
		  			houseCount += player.getPropertiesOwned().get(i).getNumberOfHouses();
		  			
		  			if (player.getPropertiesOwned().get(i).getNumberOfHouses() == 5) {
		  				hotelCount += 1;
		  				houseCount -= 5;
		  			}
		  		}
		  		
		  		System.out.println(player + " owns " + houseCount + " houses and " + hotelCount + " hotels.");
		  		System.out.println(player + " will pay $" + houseCount*25 + " for houses and $" + hotelCount*100 + " for hotels.");
		  		subtractMoney(player, (houseCount*25 + hotelCount*100));
		  		break;
	  }
  }
  
  // TODO: Since this is solely affecting a variable in the player class, this should probably just be in Player
  public void addJailTime(Player player) {
	  player.setJailTime(player.getJailTime() + 1);
  }
  public void beingInJail(Player player, Player otherPlayer) throws IOException, InterruptedException {
	  // TODO: Two stars /** means javadoc. That is only for methods and classes.  You want /* here for just a normal multiline comment.
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
		  twoPlayerGamePtTwo(player, otherPlayer, diceOne + diceTwo);  // TODO: oh god why
	  }
	  else {
		  addJailTime(player);
		  System.out.println("Player is still in jail and has rolled " + player.getJailTime() + " times.");

		  // if player has get out of jail free card, use it, get out jail, do not move until next turn 
		  // TODO: WHat if a player wants to use a get out of jail free card immediately?
		  if (player.getOutOfJailFreeCard) {
			  System.out.println(player + " has used a get-out-of-jail-free card.");
			  player.setGetOutOfJailFreeCard(false);
			  player.setJailTime(-1);
			  System.out.println("----------------");
		  }
		  else if (player.getJailTime() == 3) {
			  System.out.println(player + " has rolled 3 times and now must pay $50 to get out of jail.");
			  System.out.println(player + " has paid $50 to get out of jail.");
			  subtractMoney(player, 50);
			  player.setJailTime(-1);
			  System.out.println("----------------");
			  checkBalance(player, otherPlayer);
		  }
		  else {
			  String prompt = "Do you want to pay $50 to get out of jail? (y/n)";
			  if (player.doYouWantToDoThisJail(prompt)) {
				  System.out.println(player + " has paid $50 to get out of jail.");
				  subtractMoney(player, 50);
				  player.setJailTime(-1);
				  System.out.println("----------------");
			  }
			  else { // TODO: All of these prints really only help the console player so really, they should only be in the console player.  And maybe the random player for debugging random vs random.
				  System.out.println(player + " has decided not to pay to leave jail.");
				  System.out.println("----------------");
			  }
		  } 
	  }
  }
  
  // TODO: Put these functions in Player.  They are setters for Player variables with no other side effects.
  public void addMoney(Player player, int money) {
	  player.setBalance(player.getBalance() + money);
  }
  public void subtractMoney(Player player, int money) {
	  player.setBalance(player.getBalance() - money);
  }
}


