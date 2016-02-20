package org.monopoly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ConsolePlayer extends Player {
  
	InputStreamReader reader = new InputStreamReader(System.in);
	BufferedReader keyboard = new BufferedReader(reader);
  
	String input;

	public ConsolePlayer(String playerToken, int balance, ArrayList<Property> possibleHouses) {
		super(playerToken, balance, possibleHouses);
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
	public boolean buyHouse(Property property) {
    
		System.out.println("Do you want to buy a house? at " + property + "(y/n)"); 
   
		try {
			input = keyboard.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}	
    
		if (input == "y") {
			return true;
		}
    return false;
  }
}
