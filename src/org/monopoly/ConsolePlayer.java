package org.monopoly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ConsolePlayer extends Player {

	// note the combination of buffered and input stream reader
	// Make this private and final. No one else besides things in this
	// class should use it and it should never be reassigned.
	private final BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

	public ConsolePlayer(String playerToken) {
		super(playerToken);
	}

	public boolean chooseYesOrNo(String prompt) {

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

	public Property selectWhereToBuyHouse(ArrayList<Property> propsWhereYouCanBuyHouse) {

		String prompt = "Press the number of the property where you'd like to buy a house, or 0 to stop buying houses.";

		return selectingFromList(propsWhereYouCanBuyHouse, prompt);
	}

	public Property selectWhatToMortgage(ArrayList<Property> propertiesOwned) {

		String prompt = "Select the number of the property that you'd like to mortgage, or 0 to stop mortgaging properties.";

		return selectingFromList(propertiesOwned, prompt);
	}

	public Property selectWhatToUnmortgage(ArrayList<Property> mortgagedProperties) {

		String prompt = "Select the number of the property that you'd like to unmortgage, or 0 to stop unmortgaging properties.";

		return selectingFromList(mortgagedProperties, prompt);
	}

	public Property selectingFromList(ArrayList<Property> arrayListProperties, String prompt) {

		// input is not constant throughout the game, so we want to have it as
		// local variables even though we'll have to declare it four times.
		// helps with bugs!
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

		while (c > arrayListProperties.size() + 1 || c < 1) {
			System.out.println("Please try again.");
			try {
				input = keyboard.readLine();
				c = Integer.parseInt(input);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (c > 0 && c < arrayListProperties.size() + 1) {
			Property t = arrayListProperties.get(c - 1);
			return t;
		}
		return null;
	}
}
