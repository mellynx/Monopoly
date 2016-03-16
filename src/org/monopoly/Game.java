package org.monopoly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import org.monopoly.Property.RentType;
import org.monopoly.Property.SpecialType;

import java.util.Map;

public class Game {
	//TODO: ideally, remove properties once players have mortgaged. things are removed once you unmortgage

	// the list of all properties on the board
	private final ArrayList<Property> boardProperties = new ArrayList<Property>();

	// maps each property to what set (color) of properties it belongs to
	private final Map<Property, Set<Property>> map = new HashMap<>();

	private final Player playerOne, playerTwo;
	private final Random random;

	/* Kind of cool random phrase generator 
	private static String[] phrases = { "Player owns this property and nothing happens to this player.", "Any other phrase you want." };
	String phrase = phrases[(int) (Math.random() * phrases.length)];
	*/

	public Game(Player playerOne, Player playerTwo) {
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		random = new Random();
		
		// the three calls below are in the constructor because they are involved in the setup of the game 
		// note how we call the board creator class 
		new BoardCreator(boardProperties, map).addProperties(); 
		// players start at the first property on the arrayList
		playerOne.setLocation(boardProperties.get(0)); 
		playerTwo.setLocation(boardProperties.get(0));
	}

	public void playGame() {
		
		while (true) {
			if (playGame(playerOne, playerTwo)) {
				break;
			}
			if (playGame(playerTwo, playerOne)) {
				break;
			}
		}
	}

	private boolean playGame(Player thisPlayer, Player otherPlayer) {

		int dice = rollDice();
		Property landed;

		// if player starts turn in jail
		if (thisPlayer.getJailTime() >= 0) { 
			beingInJail(thisPlayer);
		} 
		else {
			System.out.println("Player " + thisPlayer.getToken() + " has rolled a " + dice);
			landed = moveSpace(thisPlayer, dice);
			thisPlayer.setLocation(landed);
			System.out.println("Player " + thisPlayer.getToken() + " has landed on " + landed);
			System.out.println(thisPlayer + " has $" + thisPlayer.getBalance());
			afterLanding(thisPlayer, landed, dice);
		}

		if (thisPlayer.getHousableSetList().size() > 0) {
			checkMonopolies(thisPlayer);
		}

		if (thisPlayer.getMortgagedProperties().size() > 0) {
			unmortgageProperties(thisPlayer);
		}

		if (checkBalance(thisPlayer, otherPlayer)) {
			// return void means to exit out of the method at that statement,
			// without running the following statements.
			return true;
		}
		// Thread.sleep(2000);
		System.out.println("---------------------");

		return false;
	}

	private int rollDice() {
		return (random.nextInt(6) + 1 + random.nextInt(6) + 1);
	}

	private Property moveSpace(Player player, int roll) {

		// get the current index of where the player is
		int index = player.getLocation().getLocationIndex(boardProperties);
		index += roll;
		
		// giving players $200 every time they pass Go
		if (index >= boardProperties.size()) {
			System.out.println("Collect $200 for passing Go.");
			player.setBalance(player.getBalance() + 200);
		}

		index = index % boardProperties.size();

		return boardProperties.get(index);
	}

	public void afterLanding(Player player, Property landedProperty, int diceRoll) { 
		// if property is not owned by anyone
		if (landedProperty.getPropertyOwner() == null) {
			// if the property is a board property, nothing happens
			if (!landedProperty.getBuyableStatus()) {
				specialProperties(player, landedProperty);
			}
			// if property is not a board property, ask player if they want to buy the property
			else {
				System.out.println("This property costs $" + landedProperty.getBuyCost());
				String prompt = "Do you want to buy this property? (y/n)";
				// if player wants to buy the property
				try {
					if (player.chooseYesOrNo(prompt)) {
						// if player has the money
						if (player.getBalance() > landedProperty.getBuyCost()) {
							buyProperty(player, landedProperty);
						}
						// if player does not have the money
						else {
							// if player has properties to mortgage
							if (player.getPropertiesOwned().size() > player.getMortgagedProperties().size()) {

								prompt = "Player cannot afford to buy this property. Would you like to mortgage some properties? (y/n)";
								if (player.chooseYesOrNo(prompt)) {
									mortgageProperties(player); 

									// if this is enough for player to buy the property, let them
									if (player.getBalance() > landedProperty.getBuyCost()) {
										buyProperty(player, landedProperty);
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
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		// if property is owned by another player
		else if (!landedProperty.getPropertyOwner().equals(player)) {
			// if property is mortgaged, no rent must be paid. else, pay rent
			if (landedProperty.getMortgageStatus()) {
				System.out.println("This property is mortgaged and no rent must be paid.");
			} 
			else {
				payRent(player, landedProperty, diceRoll);
			}
		}

		// if property is owned by the player
		else {
			System.out.println(player + " owns this property and nothing happens to the player.");
		}
	}

	public void buyProperty(Player player, Property landedProperty) {
		System.out.println(player + " has bought " + landedProperty + " for $" + landedProperty.getBuyCost());
		player.subtractMoney(landedProperty.getBuyCost());
		System.out.println(player + " has $" + player.getBalance() + " left.");
		landedProperty.setPropertyOwner(player);
		player.addToPropertiesOwnedList(landedProperty);
		
		if (isPropertyPartOfMonopoly(player, landedProperty)) {
			addMonopoly(player, landedProperty);
		}
	}

	public void payRent(Player player, Property landedProperty, int diceRoll) {
		// prints statement of what's happening; deducts rent from player's
		// balance; prints out his new balance; adds rent to other player's balance
		System.out.println(player + " has paid $" + landedProperty.getRentCost(diceRoll) + " in rent to "
				+ landedProperty.getPropertyOwner() + " for landing on " + landedProperty);
		player.subtractMoney(landedProperty.getRentCost(diceRoll)); 
		System.out.println(player + " has $" + player.getBalance() + " left.");
		landedProperty.getPropertyOwner().addMoney(landedProperty.getRentCost(diceRoll));
	}

	public void checkMonopolies(Player player) { 
		// don't ask the below prompt if the "generate" list is null (either
		// because houses have been maxed out or player does not have enough money)
		if (getBuyableHouseLocations(player, player.getHousableSetList()).size() > 0) {

			String prompt = "Player has completed a monopoly and is eligible to buy houses. Would you like to buy any houses? (y/n)";
			try {
				if (player.chooseYesOrNo(prompt)) {

					if (player.getPropertiesOwned().size() > player.getMortgagedProperties().size()) {

						String promptB = "Would you like to mortgage some properties first? (y/n)";

						if (player.chooseYesOrNo(promptB)) {
							mortgageProperties(player);
						} 
						else {
							System.out.println(player + " has chosen not to mortgage any properties.");
						}
					}
					buyHouse(player);
				} 
				else {
					System.out.println(player + " has chosen not to buy any houses.");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void buyHouse(Player player) {
	
		while (true) {
			Property houseToBuy = null;
			
			try {
				houseToBuy = player.selectWhereToBuyHouse(getBuyableHouseLocations(player, player.getHousableSetList()));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (houseToBuy != null) {
				player.handleHouseBuying(houseToBuy);
			}
			else {
				System.out.println(player + " does not want to buy any more houses.");
				break;
			}
		}
	}

	/**
	 * javadoc documentation. note the two stars above the method below checks
	 * whether a property that a player owns is part of a property set/color
	 * where he can buy houses the method does not check whether he has enough
	 * money to
	 */
	public boolean isPropertyPartOfMonopoly(Player player, Property landedProperty) {
		Set<Property> color = map.get(landedProperty);

		// cannot call a method on a null object
		// ensure that utilities and railroads aren't eligible for monopolies
		for (Property a : color) {
			if (a.getPropertyOwner() != player || landedProperty.getRentType() == RentType.UTILITY
					|| landedProperty.getRentType() == RentType.RAILROAD) {
				return false;
			}
		}
		return true;
	}

	public void addMonopoly(Player player, Property landedProperty) {
		System.out.println(player + " has achieved a monopoly for " + map.get(landedProperty));
		Set<Property> color = map.get(landedProperty);
		player.addToHousableSets(color);
	}

	public ArrayList<Property> getBuyableHouseLocations(Player player, ArrayList<Set<Property>> monopolies) {
		
		ArrayList<Property> houseableProperties = new ArrayList<>();

		// loop through the list of all sets to pick out each individual set
		for (int i = 0; i < monopolies.size(); i++) {
			Set<Property> monopoly = monopolies.get(i);

			// for each set, return minimum house number across the properties
			// for that set
			int minHouseCountPerSet = findMinimumHouseNumber(monopoly);

			// looping through every property in a set
			for (Property property : monopoly) {
				// if player can afford a house there, if the houses are being
				// built evenly, if there aren't already the max number of
				// houses then add that property to the list we present to the player
				if (player.getBalance() > property.getHouseCost() && property.getNumberOfHouses() == minHouseCountPerSet && property.getNumberOfHouses() < 5) {
					houseableProperties.add(property);
				}
			}
		}
		return houseableProperties;
	}

	public int findMinimumHouseNumber(Set<Property> monopoly) {
		
		int minHouseCount = 100;

		// loop through each property in the set to find the minimum house number
		for (Property property : monopoly) {
			if (property.getNumberOfHouses() < minHouseCount) {
				minHouseCount = property.getNumberOfHouses();
			}
		}
		return minHouseCount;
	}

	
	public void mortgageProperties(Player player) {

		ArrayList<Property> mortgageableProperties = new ArrayList<Property>();

		for (Property property: player.getPropertiesOwned()) {
			if (!property.getMortgageStatus()) {
				mortgageableProperties.add(property);
			}
		}
		
		while (true) {
			Property toMortgage = null;
			
			try {
				toMortgage = player.selectWhatToMortgage(mortgageableProperties);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (toMortgage != null) {
				player.handleMortgaging(toMortgage);
			}
			else {
				System.out.println(player + " does not want to mortgage any more properties.");
				break;
			}
		}
	}

	public void unmortgageProperties(Player player) {

		String prompt = "You have mortgaged properties. Would you like to unmortgage them? (y/n)";
		
		try {
			if (player.chooseYesOrNo(prompt)) {
				while (true) {
					Property toUnmortgage;
					
					toUnmortgage = player.selectWhatToUnmortgage(player.getMortgagedProperties());
					
					if (toUnmortgage != null) {
						player.handleUnmortgaging(toUnmortgage);
					}
					else {
						System.out.println(player + " does not want to unmortgage any more properties.");
						break;
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean checkBalance(Player player, Player otherPlayer) {
		if (player.getBalance() < 0) {

			// as long as player has properties, they should mortgage them to
			// save the game. otherwise, they lose the game.
			if (player.getPropertiesOwned().size() > player.getMortgagedProperties().size()) {

				System.out.println("Player is out of money and must mortgage properties.");
				mortgageProperties(player);

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

	public void specialProperties(Player player, Property property) {

		SpecialType type = property.getSpecialType();
		
		switch (type) {
		// Go -- collecting $200 cannot be done here because you collect that every time you PASS go
		case GO: 
			break;
		case JAIL: 
			System.out.println("Passing through jail! Enjoy visiting!");
			break;
		case FREE_PARKING: 
			System.out.println("Chill on Free Parking. Nothing happens.");
			break;

		case INCOME_TAX: 
			if ((player.getBalance() / 10) < 200) { 
				System.out.println(player + " has paid $" + (player.getBalance() / 10) + " to Income Tax.");
				player.subtractMoney(player.getBalance() / 10);
			} 
			else {
				System.out.println(player + " has paid $200 to Income Tax.");
				player.subtractMoney(200);
			}
			break;
		case LUXURY_TAX:
			System.out.println(player + " has paid $75 to Luxury Tax.");
			player.subtractMoney(75);
			break;

		case GO_TO_JAIL: 
			System.out.println("Oh no! " + player + " is going to jail.");
			player.setLocation(boardProperties.get(10));
			player.addJailTime();
			break;

		case COMMUNITY_CHEST:
			communityChest(player);
			break;

		case CHANCE:
			chance(player);
			break;
		}
	}

	public void communityChest(Player player) {
		int rand = random.nextInt(8);

		switch (rand) {
		case 0:
			System.out.println("Community Chest: Collect $50 from every player.");
			player.addMoney(50);
			
			// use this to refer to OtherPlayer rather than passing it down through a bunch of methods 
			referToOtherPlayer(player).subtractMoney(50);
			break;
		case 1:
			System.out.println("Community Chest: Collect for services $25.");
			player.addMoney(25);
			break;
		case 2:
			System.out.println("Community Chest: Advance to Go. Collect $200.");
			player.setLocation(boardProperties.get(0));
			player.addMoney(200);
			afterLanding(player, boardProperties.get(0), 0);
			break;
		case 3:
			System.out.println("Community Chest: Pay hospital $100.");
			player.subtractMoney(100);
			break;
		case 4:
			System.out.println("Community Chest: Get out of jail free.");
			player.setGetOutOfJailFreeCard(true);
			break;
		case 5:
			System.out.println("Community Chest: Go directly to Jail. Do not pass Go, do not collect $200.");
			player.setLocation(boardProperties.get(10));
			player.addJailTime();
			break;
		case 6:
			System.out.println("Community Chest: You are assessed for street repairs. Pay $85 per house, $115 per hotel.");
			generalRepairHelper(player, 85, 115);
			break;

		case 7:
			System.out.println("Community Chest: From sale of stock, collect $45.");
			player.addMoney(45);
			break;
		}
	}

	public void chance(Player player) {
		int rand = random.nextInt(12);

		switch (rand) {
		case 0:
			System.out.println("Chance: Advance to Go, collect $200");
			player.setLocation(boardProperties.get(0));
			player.addMoney(200);
			afterLanding(player, boardProperties.get(0), 0);
			break;
		case 1:
			System.out.println("Chance: Bank pays you dividend of $50.");
			player.addMoney(50);
			break;
		case 2:
			System.out.println("Chance: Go back 3 spaces.");
			int index = player.getLocation().getLocationIndex(boardProperties);
			
			if (index < 3) {
				index = index + 40;
			}
			index -= 3;
			
			System.out.println(player + " has landed on " + boardProperties.get(index));
			player.setLocation(boardProperties.get(index)); 
			afterLanding(player, boardProperties.get(index), 0);
			break;
		case 3:
			System.out.println("Chance: Go directly to Jail. Do not pass Go, do not collect $200");
			player.setLocation(boardProperties.get(10));
			player.addJailTime();
			break;
		case 4:
			System.out.println("Chance: Pay poor tax of $15");
			player.subtractMoney(15);
			break;
		case 5:
			System.out.println("You have been elected chairman of the board. Pay each player $50");
			player.subtractMoney(50);
			referToOtherPlayer(player).addMoney(50);
			break;
		case 6:
			System.out.println("Advance to Reading Railroad. If you pass Go, collect $200");
			int indexB = player.getLocation().getLocationIndex(boardProperties);

			if (indexB > 5) {
				System.out.println("Collect $200 for passing Go.");
				player.addMoney(200);
			}

			player.setLocation(boardProperties.get(5));
			afterLanding(player, boardProperties.get(5), 0);

			break;
		case 7:
			System.out.println("Advance to Boardwalk.");
			player.setLocation(boardProperties.get(39));
			afterLanding(player, boardProperties.get(39), 0); 
			break;
		case 8:
			System.out.println("Your building and loan matures. Collect $150.");
			player.addMoney(150);
			break;
		case 9:
			System.out.println("Advance to Illinois Ave.");
			player.setLocation(boardProperties.get(24));
			afterLanding(player, boardProperties.get(24), 0);
			break;
		case 10:
			System.out.println("Get out of jail free.");
			player.setGetOutOfJailFreeCard(true);
			break;
		case 11:
			System.out.println("Make general repairs on your properties. For each house pay $25. For each hotel pay $100");
			generalRepairHelper(player, 25, 100);
			break;
		}
	}	
	public void generalRepairHelper(Player player, int houseCost, int hotelCost) {

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
		System.out.println(player + " will pay $" + houseCount * houseCost + " for houses and $" + hotelCount * hotelCost + " for hotels.");
		player.subtractMoney(houseCount * houseCost + hotelCount * hotelCost);
	}

	public void beingInJail(Player player) {
		/*
		 * In JAIL, player status JailTime = -1 if player is not in jail (or has
		 * just gotten out of jail) 0 if player has just landed in jail and 1-3
		 * for the subsequent rolls player tries to make to get out of jail
		 */

		// attempt to roll doubles
		int diceOne = random.nextInt(6) + 1;
		int diceTwo = random.nextInt(6) + 1;

		System.out.println(player + " rolls a " + diceOne + " and a " + diceTwo);

		if (diceOne == diceTwo) {
			System.out.println(player + " has rolled out of jail!");
			player.setJailTime(-1);
			
			// 10 is the locationIndex of jail, aka where the player is starting from 
			int index = 10 + diceOne + diceTwo; 
			System.out.println(player + " has landed on " + boardProperties.get(index));
			player.setLocation(boardProperties.get(index));
			afterLanding(player, boardProperties.get(index), diceOne + diceTwo);
		} 
		else {
			player.addJailTime();
			System.out.println("Player is still in jail and has rolled " + player.getJailTime() + " times.");

			// if player has get out of jail free card, use it, get out jail, do not move until next turn
			if (player.getOutOfJailFreeCard) {
				System.out.println(player + " has used a get-out-of-jail-free card.");
				player.setGetOutOfJailFreeCard(false);
				player.setJailTime(-1);
			} 
			else if (player.getJailTime() == 3) {
				System.out.println(player + " has rolled 3 times and now must pay $50 to get out of jail.");
				payToLeaveJail(player);
				checkBalance(player, referToOtherPlayer(player));
			} 
			else {
				String prompt = "Do you want to pay $50 to get out of jail? (y/n)";
				try {
					if (player.chooseYesOrNo(prompt)) {
						payToLeaveJail(player);
					} 
					else { 
						System.out.println(player + " has decided not to pay to leave jail.");
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}	
	
	public ArrayList<Property> getBoardProperties() {
		return boardProperties;
	}
	public Player referToOtherPlayer (Player player) {
		if (player == playerOne) {
			return playerTwo;
		}
		else {
			return playerOne;
		}
	}
	public void payToLeaveJail(Player player) {
		System.out.println(player + " has paid $50 to get out of jail.");
		player.subtractMoney(50);
		player.setJailTime(-1);
	}
}
