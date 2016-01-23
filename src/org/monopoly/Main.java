package org.monopoly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Main {
	
	static Player playerOne = new Player ("Dog", 1000);
	static Player playerTwo = new Player ("Thimble", 1000);
	
	static Property park = new Property("Park", 500, 10, null);
	static Property boardwalk = new Property("Boardwalk", 600, 200, null);
	
	static int randomInt;
	static Random random = new Random();
	static Property landed;
		
	public static void main (String [] args) throws IOException {
		while(true) {
			System.out.println("Player " + playerOne.getToken() + " has rolled a " + rollDice(random, 1, 12));
			landed = moveSpace(randomInt);
			System.out.println("Player " + playerOne.getToken() + " has landed on " + toString(landed));	
			afterLanding(playerOne, playerTwo);
			checkBalance(playerOne, playerTwo);
			System.out.println("---------------------");
			
			System.out.println("Player " + playerTwo.getToken() + " has rolled a " + rollDice(random, 1, 12));
			landed = moveSpace(randomInt);
			System.out.println("Player " + playerTwo.getToken() + " has landed on " + toString(landed));	
			afterLanding(playerTwo, playerOne);
			checkBalance(playerTwo, playerOne);
			System.out.println("---------------------");
		}
	}
	public static int rollDice(Random random, int low, int high) {
		randomInt = random.nextInt(high) + low;
		return randomInt;
	}
	public static Property moveSpace(int number) {
		if (number % 2 == 0) {
			return park;
		}
		else {
			return boardwalk;
		}
	}
	public static String toString(Property landed) {
		if (landed.getName().equals("Park")) {
			return "Park";
		}
		else {
			return "Boardwalk";
		}
	}
	public static void afterLanding(Player player, Player otherPlayer) {
		if (landed.getPropertyOwner() == null) {
			if (player.getBalance() > landed.getBuyCost()) {
				System.out.println(player.getToken() + " has bought " + toString(landed) + " for $" + landed.getBuyCost());
				player.setBalance(player.getBalance() - landed.getBuyCost());
				landed.setPropertyOwner(player.getToken());
			}
			else {
				System.out.println(player.getToken() + " does not have enough money to buy this property.");
			}
		}
		else if (!landed.getPropertyOwner().equals(player.getToken())) {
			System.out.println(player.getToken() + " has paid $" + landed.getRentCost() + " for landing on " + toString(landed));
			player.setBalance(player.getBalance() - landed.getRentCost());
			otherPlayer.setBalance(otherPlayer.getBalance() + landed.getRentCost());
		}
		else {
			System.out.println("Player owns this property and nothing happens to this player.");
		}
	}
	public static void checkBalance(Player player, Player otherPlayer) {
		if (player.getBalance() <= 0) {
			System.out.println(otherPlayer.getToken() + " has won the game!");
			System.exit(0);
		}
	}
}

