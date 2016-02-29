package org.monopoly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ConsolePlayer extends Player {
  
  // note the combination of buffered and input stream reader
  BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

	public ConsolePlayer(String playerToken) {
		super(playerToken);
	}
	public boolean buyProperty() {
	  
	  String input;
    
		System.out.println("Do you want to buy this property? (y/n)"); 
		
		try {
			input = keyboard.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		if (input.equals("y")) {
			return true;
		}
		return false;
	} 
	public Property buyHouse(ArrayList<Property> propsWhereYouCanBuyHouse) {
	  // input is not constant throughout the game, so we want to have it as local variables even though we'll have to declare it four times
	  // helps with bugs!
	  String input;
    
		System.out.println("Press the number of the property where you'd like to buy a house, or 0 to stop buying houses.");
		
		for (int i = 0; i < propsWhereYouCanBuyHouse.size(); i++) {
			System.out.println((i + 1) + ". " + propsWhereYouCanBuyHouse.get(i));
		}
   
		try {
			input = keyboard.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}	
		
		int c = Integer.parseInt(input);
		
		if (c != 0) {
			Property t = propsWhereYouCanBuyHouse.get(c - 1);
		    System.out.println("Bought a house: " + t);
		    return t;
		}
		return null;
	}
	public Property mortgageProperties (ArrayList<Property> propertiesOwned) {
	  String input;
	  
		System.out.println("Select the number of the property that you'd like to mortgage, or 0 to stop mortgaging properties.");

		for (int i = 0; i < propertiesOwned.size(); i++) {
			System.out.println((i + 1) + ". " + propertiesOwned.get(i));
		}

		try {
			input = keyboard.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}	

		int c = Integer.parseInt(input);

		if (c != 0) {
				Property t = propertiesOwned.get(c - 1);
				return t;
		}
		return null;
	}
	public boolean checkIfYourWantToMortgageProperties (String prompt) {
	  String input = null;
	  
		System.out.println(prompt);

		try {
			input = keyboard.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (input.equals("y")) {
			return true;
		}
		return false;
	}
}
