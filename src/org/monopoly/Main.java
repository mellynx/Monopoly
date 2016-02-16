package org.monopoly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Main {
	
	static Player playerOne = new Player ("Dog", 1000);
	static Player playerTwo = new Player ("Thimble", 1000);
	
	static int propertyCount = 2;
	static Property park = new Property("Park", 500, 10);
	static Property boardwalk = new Property("Boardwalk", 600, 200);
	
	static Random random = new Random();
	
	private static String[] phrases = {"Player owns this property and nothing happens to this player.", 
			"Player owns this property and fuck you Isaac.", "Player owns this property and what has my life come to.",
			"Player owns this property and I want some boba.", "Player owns this property and can I kill myself now?"};

	public static void main (String [] args) throws IOException {
		
		while(true) {
			
			int dice = rollDice(random, 1, 12);
			Property landed;
			
			System.out.println("Player " + playerOne.getToken() + " has rolled a " + dice);
			landed = moveSpace(dice, propertyCount);
			System.out.println("Player " + playerOne.getToken() + " has landed on " + landed);	
			afterLanding(playerOne, playerTwo, landed);
			checkBalance(playerOne, playerTwo);
			System.out.println("---------------------");
			
			System.out.println("Player " + playerTwo.getToken() + " has rolled a " + dice);
			landed = moveSpace(dice, propertyCount);
			System.out.println("Player " + playerTwo.getToken() + " has landed on " + landed);	
			afterLanding(playerTwo, playerOne, landed);
			checkBalance(playerTwo, playerOne);
			System.out.println("---------------------");
		}
	}
	public static int rollDice(Random random, int low, int high) {
		return random.nextInt(high) + low;
	}
	public static Property moveSpace(int roll, int numberOfProperties) {
		
		Property [] propertiesArray = new Property [numberOfProperties];
		
		propertiesArray[0] = park;
		propertiesArray[1] = boardwalk;
		
		return(propertiesArray[roll % numberOfProperties]);
	}
	public static void afterLanding(Player player, Player otherPlayer, Property landedProperty) {
		
		Property landed2 = landedProperty;
		
		if (landed2.getPropertyOwner() == null) {
			if (player.getBalance() >= landed2.getBuyCost()) {
				System.out.println(player + " has bought " + landed2 + " for $" + landed2.getBuyCost());
				player.setBalance(player.getBalance() - landed2.getBuyCost());
				landed2.setPropertyOwner(player);
			}
			else {
				System.out.println(player + " does not have enough money to buy this property.");
			}
		}
		else if (!landed2.getPropertyOwner().equals(player)) {
			System.out.println(player + " has paid $" + landed2.getRentCost() + " for landing on " + landed2);
			player.setBalance(player.getBalance() - landed2.getRentCost());
			otherPlayer.setBalance(otherPlayer.getBalance() + landed2.getRentCost());
		}
		else {
			
			String phrase = phrases[(int) (Math.random() * phrases.length)];
			System.out.println(phrase);
		}
	}
	public static void checkBalance(Player player, Player otherPlayer) {
		if (player.getBalance() < 0) {
			System.out.println(otherPlayer.getToken() + " has won the game!");
			System.exit(0);
		}
	}
}

