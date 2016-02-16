package org.monopoly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import com.sun.xml.internal.fastinfoset.sax.Properties;

public class Main {
	
	static ArrayList<Property> properties = new ArrayList<Property>();
	
	static Player playerOne = new Player ("Dog", 1000);
	static Player playerTwo = new Player ("Thimble", 1000);
	
	static Random random = new Random();
	
	private static String[] phrases = {"Player owns this property and nothing happens to this player.", 
			"Player owns this property, pay no attention to the screaming inside.", "Player owns this property and has let loose the dogs.",
			"Player owns this property and is beckoning you inside.", "Player owns this property and has reported you to the police."};

	public static void main (String [] args) throws IOException {
		
		addProperties();	// CALL add properties
		
		playerOne.setLocation(properties.get(0));
		playerTwo.setLocation(properties.get(0));
		
		while(true) {
			
			int dice = rollDice(random, 1, 12);
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
		properties.add(new Property("Park", 500, 10));
		properties.add(new Property("Boardwalk", 600, 200));	
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
		
		if (landedProperty.getPropertyOwner() == null) {
			if (player.getBalance() >= landedProperty.getBuyCost()) {
				System.out.println(player + " has bought " + landedProperty + " for $" + landedProperty.getBuyCost());
				player.setBalance(player.getBalance() - landedProperty.getBuyCost());
				landedProperty.setPropertyOwner(player);
			}
			else {
				System.out.println(player + " does not have enough money to buy this property.");
			}
		}
		else if (!landedProperty.getPropertyOwner().equals(player)) {
			System.out.println(player + " has paid $" + landedProperty.getRentCost() + " for landing on " + landedProperty);
			player.setBalance(player.getBalance() - landedProperty.getRentCost());
			otherPlayer.setBalance(otherPlayer.getBalance() + landedProperty.getRentCost());
		}
		else {
			
			String phrase = phrases[(int) (Math.random() * phrases.length)];
			System.out.println(phrase);
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

