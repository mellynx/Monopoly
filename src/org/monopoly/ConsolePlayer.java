package org.monopoly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ConsolePlayer extends Player {
  
	InputStreamReader reader = new InputStreamReader(System.in);
	BufferedReader keyboard = new BufferedReader(reader);
  
	String input;

	public ConsolePlayer(String playerToken) {
		super(playerToken);
	}
	public boolean buyProperty() {
    
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
	public boolean buyHouse(ArrayList<Property> listOfPropertiesWhereYouCanCurrentlyBuyAHouse) {
    
		System.out.println("Press the number of the property where you'd like to buy a house, or 0 to stop buying houses.");
		
		for (int i = 0; i < listOfPropertiesWhereYouCanCurrentlyBuyAHouse.size(); i++) {
			System.out.println((i + 1) + ". " + listOfPropertiesWhereYouCanCurrentlyBuyAHouse.get(i));
		}
   
		try {
			input = keyboard.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}	
		
		int c = Integer.parseInt(input);
		
		if (c != 0) {
			Property t = listOfPropertiesWhereYouCanCurrentlyBuyAHouse.get(c - 1);
		    System.out.println("Bought a house: " + t);
		    
			t.addOneHouse();	
			int money = getBalance() - t.getHouseCost();
			setBalance(money);
			return true;
		}
		return false; 
	}
	public boolean mortgageProperties (ArrayList<Property> propertiesOwned, ArrayList<Property> mortgagedProperties) {
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
			    System.out.println("Mortgaged: " + t);
			    
				mortgagedProperties.add(t);
				propertiesOwned.remove(t);
				t.changeMortgageStatus();
				int money = getBalance() + t.getMortgageCost();
				setBalance(money);
				return true;
		}
		return false;
	}
	public boolean checkIfYourWantToMortgageProperties (String prompt) {
		System.out.println(prompt);

		try {
			input = keyboard.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (input.equals("y")) {
			return true;
		}
		return false;
	}
}
