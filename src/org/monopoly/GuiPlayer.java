package org.monopoly;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class GuiPlayer extends Player {
	// countdown latches: https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/CountDownLatch.html
	// need to make a new latch every time, because they can't ever count up

	private boolean answer;
	private Property propertyAnswer;
	private String prompt;
	private ArrayList<Property> propertiesToPass;
	private StatusType statusType;
	CountDownLatch latch;

	enum StatusType {
		NONE, BOOLEAN, LIST;
	}

	public GuiPlayer(String playerToken) {
		super(playerToken);
	}

	@Override
	public boolean chooseYesOrNo(String prompt) {
		statusType = StatusType.BOOLEAN;
		this.prompt = prompt;
		waitForInput();
		return answer;
	}

	@Override
	public Property selectWhereToBuyHouse(ArrayList<Property> currentHousableProperties) {
		statusType = StatusType.LIST;
		prompt = "Click the property name where you'd like to buy a house, or 'none' to stop buying houses.";
		propertiesToPass = currentHousableProperties;
		waitForInput();
		return propertyAnswer;
	}
	
	@Override
	public Property selectWhereToSellHouse(ArrayList<Property> currentSellableProperties) {
		statusType = StatusType.LIST;
		prompt = "Click on the property where you'd like to sell a house, or 'none' to stop selling houses.";
		propertiesToPass = currentSellableProperties;
		waitForInput();
		return propertyAnswer;
	}

	@Override
	public Property selectWhatToMortgage(ArrayList<Property> mortgageableProperties) {
		statusType = StatusType.LIST;
		prompt = "Click the property name that you'd like to mortgage, or 'none' to stop mortgaging.";
		propertiesToPass = mortgageableProperties;
		waitForInput();
		return propertyAnswer;
	}

	@Override
	public Property selectWhatToUnmortgage(ArrayList<Property> mortgagedProperties) {
		statusType = StatusType.LIST;
		prompt = "Click the property name that you'd like to unmortgage, or 'none' to stop umortgaging.";
		propertiesToPass = mortgagedProperties;
		waitForInput();
		return propertyAnswer;
	}

	
	public void waitForInput() {
		// need to initialize this right before the await()
		latch = new CountDownLatch(1);
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void stopWaiting() {
		latch.countDown();
	}

	
	// when you can just access class variables within the class, you don't need setters (or getters) for 
	// the method that's already in the class. See "return propertyAnswer" instead of return getPropertyAnswer
	public String getPrompt() {
		return prompt;
	}
	public ArrayList<Property> getListToPass() {
		return propertiesToPass;
	}
	public StatusType getStatusType() {
		return statusType;
	}

	
	public void setAnswer(boolean answer) {
		this.answer = answer;
	}
	public void setPropertyAnswer(Property propertyAnswer) {
		this.propertyAnswer = propertyAnswer;
	}
}
