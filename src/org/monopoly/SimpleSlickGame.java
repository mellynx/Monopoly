package org.monopoly;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class SimpleSlickGame extends BasicGame
{
  Image imgBoard, imgDog, imgThimble, imgHouse, imgHotel;
  Game game;
  
  Player playerA = new RandomPlayer ("Dog");
  Player playerB = new RandomPlayer ("Thimble");
  
  public SimpleSlickGame(String gamename)
  {
    super(gamename);
  }

  @Override
  public void init(GameContainer gc) throws SlickException {
    
    imgBoard = new Image("res/board.jpg");
    imgDog = new Image("res/dog.png");
    imgThimble = new Image("res/thimble.png");
    imgHouse = new Image("res/house.png");
    imgHotel = new Image("res/hotel.png");
    
    game = new Game(playerA, playerB);
    try {
      game.playGame();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  
  @Override
  public void update(GameContainer gc, int i) throws SlickException {}

  @Override
  public void render(GameContainer gc, Graphics g) throws SlickException
  {
	  imgBoard.draw(0, 0, 768, 768);
	  
	  //imgHouse.draw(75, 65, 25, 25);
	  //imgHotel.draw(665, 75, 30, 30);
	    
	  int index = playerA.getLocation().getLocationIndex(game.boardProperties);
	  imgDog.draw(tokenPosition(index).getX(), tokenPosition(index).getY(), 50, 50);
	  imgThimble.draw(tokenPosition(index).getX(), tokenPosition(index).getY(), 50, 50);
  }
  public Position housePosition(int locationIndex) {
    Position position = new Position(0,0);
    
    if (locationIndex >= 0 && locationIndex <= 10) {
      position.setY(665);
      if (locationIndex != 0 || locationIndex != 10) {
        position.setX(655 - (locationIndex * 60));
      }
    }
    if (locationIndex >= 10 && locationIndex <= 20) {
      position.setX(75);
      if (locationIndex != 10 || locationIndex != 20) {
        position.setY(655 - (locationIndex % 10) * 60);
      }
    }
    if (locationIndex >= 20 && locationIndex <= 30) {
      position.setY(75);
      if (locationIndex != 20 || locationIndex != 30) {
        position.setX(655 - ((10 - (locationIndex % 20)) * 60));
      }
    }
    if (locationIndex >= 30 && locationIndex <= 40) {
      position.setX(665);
      if (locationIndex != 30 || locationIndex != 40) {
        position.setX(655 - ((10 - (locationIndex % 30)) * 60));
      }
    }
    return position;
  }
  
  public Position tokenPosition(int locationIndex) {
    Position position = new Position(0,0);
    /*
     * int [] array = {0, 20};
     * int random = new Random().nextInt(array.length);
    // get array[random];
     */
    
    if (locationIndex >= 0 && locationIndex <= 10) {
      position.setY(700);
      if (locationIndex != 0 || locationIndex != 10) {
        position.setX(660 - (locationIndex * 60));
      }
    }
    if (locationIndex >= 10 && locationIndex <= 20) {
      position.setX(20);
      if (locationIndex != 10 || locationIndex != 20) {
        position.setY(660 - (locationIndex % 10) * 60);
      }
    }
    if (locationIndex >= 20 && locationIndex <= 30) {
      position.setY(20);
      if (locationIndex != 20 || locationIndex != 30) {
        position.setX(660 - ((10 - (locationIndex % 20)) * 60));
      }
    }
    if (locationIndex >= 30 && locationIndex <= 40) {
      position.setX(690);
      if (locationIndex != 30 || locationIndex != 40) {
        position.setX(660 - ((10 - (locationIndex % 30)) * 60));
      }
    }
    return position;
  }

  public static void main(String[] args)
  {
    try
    {
      AppGameContainer appgc;
      appgc = new AppGameContainer(new SimpleSlickGame("Simple Slick Game"));
      appgc.setDisplayMode(768, 768, false);
      appgc.start();
    }
    catch (SlickException ex)
    {
      Logger.getLogger(SimpleSlickGame.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
