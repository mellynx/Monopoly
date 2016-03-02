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
	
	public boolean doYouWantToDoThis(String prompt) {
	  
		String input;
    
		System.out.println(prompt); 
		
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
	
	public Property buyHouseB(ArrayList<Property> propsWhereYouCanBuyHouse) {
		
		String promptA = "Press the number of the property where you'd like to buy a house, or 0 to stop buying houses.";
		String promptB = "Bought a house: ";
		
		Property buyHouseResult = selectingFromList(propsWhereYouCanBuyHouse, promptA, promptB);
		return buyHouseResult;
	}
	
	public Property mortgagePropertiesB(ArrayList<Property> propertiesOwned) {
		
		String promptA = "Select the number of the property that you'd like to mortgage, or 0 to stop mortgaging properties.";
		String promptB = "Mortgaged: ";
		
		Property mortgagePropertiesResult = selectingFromList(propertiesOwned, promptA, promptB);
		return mortgagePropertiesResult;
	}	
	public Property unmortgageB (ArrayList<Property> mortgagedProperties) {
		
		String promptA = "Select the number of the property that you'd like to unmortgage, or 0 to stop unmortgaging properties.";
		String promptB = "Unmortgaged: ";

		Property unmortgagedResult = selectingFromList(mortgagedProperties, promptA, promptB);
		return unmortgagedResult;
	}
	
	
	public Property selectingFromList(ArrayList<Property> arrayListProperties, String prompt, String promptTwo) {
		
		// input is not constant throughout the game, so we want to have it as local variables even though we'll have to declare it four times. helps with bugs!
		String input;
		
		System.out.println(prompt);
		
		for (int i = 0; i < arrayListProperties.size(); i++) {
			System.out.println((i + 1) + ". " + arrayListProperties.get(i));
		}
		
		try {
			input = keyboard.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}	

		int c = Integer.parseInt(input);
		
		if (c != 0) {
			Property t = arrayListProperties.get(c - 1);
			System.out.println(promptTwo + t);
			return t;
		}
		return null;
	}
}
