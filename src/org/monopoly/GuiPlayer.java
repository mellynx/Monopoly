package org.monopoly;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class GuiPlayer extends Player {

	// https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/CountDownLatch.html
	// need to make a new latch every time, because they can't ever count up
  // TODO: Instead of separate status booleans, make an enum which is like NONE, BOOLEAN, HOUSE, MORTGAGE etc then you don't have to check a bunch of variables and it makes it easier to add new states in the future.  Also you remove a bunch of getters.
  // TODO: Also all of the class variables in this class should be private since you have setters and getters as necessary
	boolean status, answer, statusBuyHouse, statusMortgage, statusUnmortgage;
	Property propertyAnswer;
	
	// TODO: You don't really need the "toPass' suffix here.
	String promptToPass, optionsToPass;
	ArrayList<Property> propertiesToPass;
	
	CountDownLatch doneSignal;

	public GuiPlayer(String playerToken) {
		super(playerToken);
	}

	@Override
	public boolean doYouWantToDoThis(String prompt) throws InterruptedException { // TODO: Try catch this interrupted exception.  IT doesn't make sense in general for this prompt to throw interrupted exceptions.
		// the doneSignal has a wait count of 1; we tell it to await an answer, then trigger the release from the mouse click in SimpleSlickGame
		status = true;
		// TODO: You don't have to use setters and getters within the owning class unless you expect there are side effects to getting and setting.
		setPrompt(prompt);
		// TODO: countdownLatch is how it works, not what it does.  The function should be called something like waitForInput() or something.
		countdownLatch();
		status = false;
		return getAnswer();
	}

	@Override
	public boolean doYouWantToDoThisJail(String prompt) throws InterruptedException {
		boolean toReturn = doYouWantToDoThis(prompt);
		return toReturn;
	}

	@Override
	public Property buyHouseB(ArrayList<Property> currentHousableProperties) throws InterruptedException {
		statusBuyHouse = true;
		setPrompt("Click the property name where you'd like to buy a house, or 'none' to stop buying houses.");
		setListToPass(currentHousableProperties);
		countdownLatch();
		statusBuyHouse = false;
		return getPropertyAnswer();
	}

	@Override
	public Property mortgagePropertiesB(ArrayList<Property> propertiesOwned) throws InterruptedException {
		statusMortgage = true;
		setPrompt("Click the property name that you'd like to mortgage, or 'none' to stop mortgaging.");
		setListToPass(propertiesOwned);
		countdownLatch();
		statusMortgage = false;
		return getPropertyAnswer();
	}

	@Override
	public Property unmortgageB(ArrayList<Property> mortgagedProperties) throws InterruptedException {
		statusUnmortgage = true;
		setPrompt("Click the property name that you'd like to unmortgage, or 'none' to stop umortgaging.");
		setListToPass(mortgagedProperties);
		countdownLatch();
		statusUnmortgage = false;
		return getPropertyAnswer();
	}
	
	public void countdownLatch () throws InterruptedException {
		// need to initialize this right before the await()
		doneSignal = new CountDownLatch(1);
		doneSignal.await();
	}
	
	public boolean getStatus() {
		return status;
	}
	public boolean getStatusBuyHouse() {
		return statusBuyHouse;
	}
	public boolean getStatusMortgage() {
		return statusMortgage;
	}
	public boolean getStatusUnmortgage() {
		return statusUnmortgage;
	}
	
	// TODO: In the same vein of, what not how...this is exposing how something happens, instead what is important is _what_ is happening.  I would call this method stopWaiting or answerGiven or something and return void and just do latch.countdown() in here.
	public CountDownLatch getLatch() {
		return doneSignal;
	}

	// TODO: If nothing outside of this class shoudl be setting this variable, just have a getter and no setter (have code in this class set the variable directly)
	public void setPrompt(String promptToPass) {
		this.promptToPass = promptToPass;
	}
	public String getPrompt() {
		return promptToPass;
	}

	// TODO: Ditto with this, no need for setter
	public void setOptions(String optionsToPass) {
		this.optionsToPass = optionsToPass;
	}
	public String getOptions() {
		return optionsToPass;
	}

	public void setAnswer(boolean answer) {
		this.answer = answer;
	}
	
	// TODO: Ditto with this, things outside of this class will never need to get back the answers, only set them.
	public boolean getAnswer() {
		return answer;
	}
	
	public void setPropertyAnswer(Property propertyAnswer) {
		this.propertyAnswer = propertyAnswer;
	}
	public Property getPropertyAnswer() {
		return propertyAnswer;
	}
	
	// TODO: Ditto, no setter needed
	public void setListToPass(ArrayList<Property> propertiesToPass) {
		this.propertiesToPass = propertiesToPass;
	}
	public ArrayList<Property> getListToPass() {
		return propertiesToPass;
	}
}
