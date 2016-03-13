package org.monopoly;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class GuiPlayer extends Player {

	// https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/CountDownLatch.html
	// need to make a new latch every time, because they can't ever count up

	boolean status, answer, statusBuyHouse, statusMortgage, statusUnmortgage;
	Property propertyAnswer;
	String promptToPass, optionsToPass;
	ArrayList<Property> propertiesToPass;
	CountDownLatch doneSignal;

	public GuiPlayer(String playerToken) {
		super(playerToken);
	}

	@Override
	public boolean doYouWantToDoThis(String prompt) throws InterruptedException {
		// the doneSignal has a wait count of 1; we tell it to await an answer, then trigger the release from the mouse click in SimpleSlickGame
		status = true;
		setPrompt(prompt);
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
	
	public CountDownLatch getLatch() {
		return doneSignal;
	}

	public void setPrompt(String promptToPass) {
		this.promptToPass = promptToPass;
	}
	public String getPrompt() {
		return promptToPass;
	}

	public void setOptions(String optionsToPass) {
		this.optionsToPass = optionsToPass;
	}
	public String getOptions() {
		return optionsToPass;
	}

	public void setAnswer(boolean answer) {
		this.answer = answer;
	}
	public boolean getAnswer() {
		return answer;
	}
	
	public void setPropertyAnswer(Property propertyAnswer) {
		this.propertyAnswer = propertyAnswer;
	}
	public Property getPropertyAnswer() {
		return propertyAnswer;
	}
	
	public void setListToPass(ArrayList<Property> propertiesToPass) {
		this.propertiesToPass = propertiesToPass;
	}
	public ArrayList<Property> getListToPass() {
		return propertiesToPass;
	}
}
