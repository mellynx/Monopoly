package org.monopoly;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class GuiPlayer extends Player {
  
  // cuz objects have wait and notify
  // https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/CountDownLatch.html. countdown latches make a new one every time. can't ever count up
  CountDownLatch
  boolean status;
  boolean answer;
  String promptToPass, optionsToPass;
  
  public GuiPlayer(String playerToken) {
    super(playerToken);
  }

  @Override
  public boolean doYouWantToDoThis(String prompt) throws InterruptedException {
    // show dialog box, what options do we show, wait for an answer (in this function)
    // use this boolean to trigger the popup box 
    status = true;
    setPrompt(prompt);  
    
    status = false;
    return getAnswer();
  }

  @Override
  public boolean doYouWantToDoThisJail(String prompt) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Property buyHouseB(ArrayList<Property> currentHousableProperties) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Property mortgagePropertiesB(ArrayList<Property> propertiesOwned) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Property unmortgageB(ArrayList<Property> mortgagedProperties) {
    // TODO Auto-generated method stub
    return null;
  }
  
  public boolean getStatus() {
    return status;
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
}
