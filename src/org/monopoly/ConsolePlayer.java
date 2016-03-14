package org.monopoly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ConsolePlayer extends Player {
  
  // note the combination of buffered and input stream reader
  // TODO: Make this private and final.  No one else besides things in this class should use it and it should never be reassigned. 
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
	public boolean doYouWantToDoThisJail (String prompt) {  // TODO: This really makes me ask why this is a separate abstract function.
		return (doYouWantToDoThis(prompt));
	}
	
	public Property buyHouseB(ArrayList<Property> propsWhereYouCanBuyHouse) {
		
		String prompt = "Press the number of the property where you'd like to buy a house, or 0 to stop buying houses.";
		
		Property buyHouseResult = selectingFromList(propsWhereYouCanBuyHouse, prompt);  // TODO: You can collapse these two lines to just: return selectingFromList(...., same with the other two places.
		return buyHouseResult;
	}
	
	public Property mortgagePropertiesB(ArrayList<Property> propertiesOwned) {
		
		String prompt = "Select the number of the property that you'd like to mortgage, or 0 to stop mortgaging properties.";
		
		Property mortgagePropertiesResult = selectingFromList(propertiesOwned, prompt);
		return mortgagePropertiesResult;
	}	
	public Property unmortgageB (ArrayList<Property> mortgagedProperties) {
		
		String prompt = "Select the number of the property that you'd like to unmortgage, or 0 to stop unmortgaging properties.";

		Property unmortgagedResult = selectingFromList(mortgagedProperties, prompt);
		return unmortgagedResult;
	}
	
	
	public Property selectingFromList(ArrayList<Property> arrayListProperties, String prompt) {
		
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
		
		// TODO: You should also check if they chose a number longer than the array.
		if (c != 0) {
			Property t = arrayListProperties.get(c - 1);
			return t;
		}
		return null;
	}
}
