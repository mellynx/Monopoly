package org.monopoly;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;



public class GuiPlayer extends Player {
  // DONE
	// countdown latches: https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/CountDownLatch.html
	// need to make a new latch every time, because they can't ever count up
	
  private boolean answer;
	private Property propertyAnswer;
	private  String prompt;
	private ArrayList<Property> propertiesToPass;
	private StatusType statusType;
	CountDownLatch latch;
	
	enum StatusType {
	  NONE, BOOLEAN, HOUSE, MORTGAGE, UNMORTGAGE;
	}

	public GuiPlayer(String playerToken) {
		super(playerToken);
	}

	@Override
	public boolean doYouWantToDoThis(String prompt) {  
		statusType = StatusType.BOOLEAN;
		this.prompt = prompt;
		
	  // the doneSignal has a wait count of 1; we tell it to await an answer, then trigger the release from the mouse click in SimpleSlickGame
		try {
      waitForInput();
    } 
		catch (InterruptedException e) {
      e.printStackTrace();
    }
		return answer;
	}

	@Override
	public Property buyHouseB(ArrayList<Property> currentHousableProperties) throws InterruptedException {
	  statusType = StatusType.HOUSE;
		prompt = "Click the property name where you'd like to buy a house, or 'none' to stop buying houses.";
		propertiesToPass = currentHousableProperties;
		waitForInput();
		return propertyAnswer;
	}

	@Override
	public Property mortgagePropertiesB(ArrayList<Property> propertiesOwned) throws InterruptedException {
	  statusType = StatusType.MORTGAGE;
		prompt = "Click the property name that you'd like to mortgage, or 'none' to stop mortgaging.";
		propertiesToPass = propertiesOwned;
		waitForInput();
		return propertyAnswer;
	}

	@Override
	public Property unmortgageB(ArrayList<Property> mortgagedProperties) throws InterruptedException {
	  statusType = StatusType.UNMORTGAGE;
		prompt = "Click the property name that you'd like to unmortgage, or 'none' to stop umortgaging.";
		propertiesToPass = mortgagedProperties;
		waitForInput();
		return propertyAnswer;
	}
	
	public void waitForInput() throws InterruptedException {
		// need to initialize this right before the await()
		latch = new CountDownLatch(1);
		latch.await();
	}
	public void stopWaiting() {
	  latch.countDown();
	}
	
	// when you can just access class variables within the class, you don't need setters (or getters) for the method that's already in the class. See "return propertyAnswer" instead of return getPropertyAnswer
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
