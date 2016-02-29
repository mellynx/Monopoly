package org.monopoly;

import java.io.IOException;
// general facts: in general you should not have recursive methods, and if you do they should be really simple

public class Main {
  public static void main (String [] args) throws IOException {
    Game game = new Game();
    game.playGame();
  }
}

