package org.monopoly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsolePlayer extends Player {
  
  InputStreamReader reader = new InputStreamReader(System.in);
  BufferedReader keyboard = new BufferedReader(reader);
  
  String input;

  public ConsolePlayer(String token, int money) {
    super(token, money);
  }
  public boolean buyProperty() {
    
    System.out.println("Do you want to buy this property? (y/n)"); 
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
  public boolean buyHouse(){
    
    System.out.println("Do you want to buy a house? (y/n)"); 
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
