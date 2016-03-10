package org.monopoly;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
  Image imgBoard, imgDog, imgThimble, imgHouse, imgHotel, imgPlayerOneHor, imgPlayerTwoHor, imgMortgaged, imgMortgagedVert, imgPlayerOneVert, imgPlayerTwoVert;
  final Game game;
  
  // executor handles threads. this makes another thing that will runs thread for you 
  // new single thread executor just runs another thread for you
  // there's the thread running through all our code, and the thread in the below executor
  // to run code on this executor, do executor.submit (below, which allows you to return things)
  // (as opposed to call) -- even though we return void 
  // submit says run this code in your thread, which in this case is the Single thread executor 
  // now there's a thread running our stuff, and another thread rendering stuff 
  // a vairable inside submit must be final, so that it can't change. 
  // but players don't need to be final, they can be called
  // be careful of modifying things in different threads. 
  
  private ExecutorService executor = Executors.newSingleThreadExecutor();
  
  Player playerA = new RandomPlayer ("Dog");
  Player playerB = new RandomPlayer ("Thimble");
  
  public SimpleSlickGame(String gamename)
  {
    super(gamename);
    game = new Game(playerA, playerB);
  }

  @Override
  public void init(GameContainer gc) throws SlickException {
    
    imgBoard = new Image("res/board.jpg");
    imgDog = new Image("res/dog.png");
    imgThimble = new Image("res/thimble.png");
    imgHouse = new Image("res/house.png");
    imgHotel = new Image("res/hotel.png");
    
    imgPlayerOneHor = new Image("res/playerone.png");
    imgPlayerOneVert = new Image("res/playeronevert.png");
    imgPlayerTwoHor = new Image("res/playertwo.png");
    imgPlayerTwoVert = new Image("res/playertwovert.png");
    imgMortgaged = new Image("res/mortgaged.png");
    imgMortgagedVert = new Image("res/mortgagedvert.png");
    
    executor.submit(new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        try {
          game.playGame();
        } catch (Throwable t) {
          t.printStackTrace();
        }
        return null;
      }
    });
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  
  @Override
  public void update(GameContainer gc, int i) throws SlickException {}

  @Override
  public void render(GameContainer gc, Graphics g) throws SlickException
  {
	  imgBoard.draw(0, 0, 768, 768);
	    
	  int index = playerA.getLocation().getLocationIndex(game.boardProperties);
	  imgDog.draw(tokenPosition(index).getX(), tokenPosition(index).getY(), 50, 50);
	  int indexB = playerB.getLocation().getLocationIndex(game.boardProperties);
	  imgThimble.draw(tokenPosition(indexB).getX(), tokenPosition(indexB).getY(), 50, 50);
	  
	  for (int i = 0; i < game.boardProperties.size(); i++) {
		  drawOwnership(game.boardProperties.get(i));
		  drawMortgages(game.boardProperties.get(i));
		  drawHouses(game.boardProperties.get(i));
	  }
	  // TODO render all properties and see if they have houses
  }
  
  
  public Position housePosition(int locationIndex) {
	  //TODO make offsets for houses
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
        position.setY(655 - ((10 - (locationIndex % 30)) * 60));
      }
    }
    return position;
  }
  public void drawHouses(Property property) {
	  int index = property.getLocationIndex(game.boardProperties);
	  int numberOfHouses = property.getNumberOfHouses();
	  
	  for (int i = 0; i < numberOfHouses; i++) {
		  if ((index > 0 && index < 10) || (index > 20 && index < 30)) {
			  imgHouse.draw(housePosition(index).getX() + (i * 10), housePosition(index).getY(), 25, 25);
			  
			  if (i == 4) {
				  imgHotel.draw(housePosition(index).getX() + (i * 10), housePosition(index).getY(), 30, 30);
			  }
		  }
		  else {
			  imgHouse.draw(housePosition(index).getX(), housePosition(index).getY() + (i * 10), 25, 25);
			  
			  if (i == 4) {
				  imgHotel.draw(housePosition(index).getX(), housePosition(index).getY() + (i * 10), 30, 30);
			  }
		  } 
	  }   
  }
  
  
  public Position mortgagedPropertyPosition(int locationIndex) {
	  Position position = new Position(0,0);
	  
	  if (locationIndex >= 0 && locationIndex <= 10) {
	      position.setY(748);
	      if (locationIndex != 0 || locationIndex != 10) {
	        position.setX(665 - (locationIndex * 62));
	      }
	    }
	  if (locationIndex >= 10 && locationIndex <= 20) {
	      position.setX(10);
	      if (locationIndex != 10 || locationIndex != 20) {
	        position.setY(665 - (locationIndex % 10) * 62);
	      }
	    }
	  if (locationIndex >= 20 && locationIndex <= 30) {
	      position.setY(10);
	      if (locationIndex != 20 || locationIndex != 30) {
	        position.setX(665 - ((10 - (locationIndex % 20)) * 62));
	      }
	    }
	  if (locationIndex >= 30 && locationIndex <= 40) {
	      position.setX(748);
	      if (locationIndex != 30 || locationIndex != 40) {
	        position.setY(665 - ((10 - (locationIndex % 30)) * 62));
	      }
	    }
	  return position;
  }
  public void drawMortgages(Property property) {
	  int index = property.getLocationIndex(game.boardProperties);
	  
	  if (property.getMortgageStatus()) {
		  if ((index > 0 && index < 10) || (index > 20 && index < 30)) {
			  imgMortgaged.draw(mortgagedPropertyPosition(index).getX(), mortgagedPropertyPosition(index).getY(), 60, 10);
		  }
		  else {
			  imgMortgagedVert.draw(mortgagedPropertyPosition(index).getX(), mortgagedPropertyPosition(index).getY(), 10, 60);
		  }  
	  }
  }
  
  
  public Position propertyOwnershipPosition(int locationIndex) {
	  Position position = new Position(0,0);
	  
	  if (locationIndex >= 0 && locationIndex <= 10) {
	      position.setY(758);
	      if (locationIndex != 0 || locationIndex != 10) {
	        position.setX(665 - (locationIndex * 62));
	      }
	    }
	  if (locationIndex >= 10 && locationIndex <= 20) {
	      position.setX(0);
	      if (locationIndex != 10 || locationIndex != 20) {
	        position.setY(665 - (locationIndex % 10) * 62);
	      }
	    }
	  if (locationIndex >= 20 && locationIndex <= 30) {
	      position.setY(0);
	      if (locationIndex != 20 || locationIndex != 30) {
	        position.setX(665 - ((10 - (locationIndex % 20)) * 62));
	      }
	    }
	  if (locationIndex >= 30 && locationIndex <= 40) {
	      position.setX(758);
	      if (locationIndex != 30 || locationIndex != 40) {
	        position.setY(665 - ((10 - (locationIndex % 30)) * 62));
	      }
	    }
	  return position;
  }
  public void drawOwnership(Property property) {
	  int index = property.getLocationIndex(game.boardProperties);
	  
	  if (property.getPropertyOwner() == playerA) {
		  if ((index > 0 && index < 10) || (index > 20 && index < 30)) {
			  imgPlayerOneHor.draw(propertyOwnershipPosition(index).getX(), propertyOwnershipPosition(index).getY(), 60, 10);
		  }
		  else {
			  imgPlayerOneVert.draw(propertyOwnershipPosition(index).getX(), propertyOwnershipPosition(index).getY(), 10, 60);
		  }  
	  }
	  else if (property.getPropertyOwner() == playerB) {
		  if ((index > 0 && index < 10) || (index > 20 && index < 30)) {
			  imgPlayerTwoHor.draw(propertyOwnershipPosition(index).getX(), propertyOwnershipPosition(index).getY(), 60, 10);
		  }
		  else {
			  imgPlayerTwoVert.draw(propertyOwnershipPosition(index).getX(), propertyOwnershipPosition(index).getY(), 10, 60);
		  }
	  }
  }
  
  
  public Position tokenPosition(int locationIndex) {
    Position position = new Position(0,0);
    /* TODO make offsets if two players are in same position
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
        position.setY(660 - ((10 - (locationIndex % 30)) * 60));
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
