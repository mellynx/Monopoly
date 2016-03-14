package org.monopoly;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.monopoly.GuiPlayer.StatusType;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class SimpleSlickGame extends BasicGame {
  
  // TODO: This is begging for some enclosing class or a map that holds all these based on an enum for image type.  I love enums and you should too!
	private Image imgBoard, imgDog, imgThimble, imgHouse, imgHotel, imgPlayerOneHor, imgPlayerTwoHor, imgMortgaged,
			imgMortgagedVert, imgPlayerOneVert, imgPlayerTwoVert, imgQuestionBackground, imgYesButton, imgNoButton;
	final Game game;
	
	private ArrayList<Property> list;

	private ExecutorService executor = Executors.newSingleThreadExecutor();

	Player playerA = new RandomPlayer("Dog");
	//remember that we are able to call specialized methods on specialized players (in addition to what we inherit)
	GuiPlayer playerB = new GuiPlayer("Thimble"); 
	 

	public SimpleSlickGame(String gamename) {
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

		imgQuestionBackground = new Image("res/back.png");
		imgYesButton = new Image("res/yes.png");
		imgNoButton = new Image("res/no.png");

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
	public void update(GameContainer gc, int i) throws SlickException {
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
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


		if (playerB.getStatusType() == StatusType.BOOLEAN) {
			drawPrompt(g, 250);
			imgYesButton.draw(265, 440, 110, 45);
			imgNoButton.draw(395, 440, 110, 45);
		}

		else if (playerB.getStatusType() == StatusType.HOUSE) {
			list = playerB.getListToPass();
			
			drawPrompt(g, 150 + (list.size() * 30));
			
			g.drawString("None", 240, 370);
			for (int i = 0; i < list.size(); i++) {
				g.drawString(list.get(i).getName(), 240, (400 + i * 30));
			}
		}

		else if (playerB.getStatusType() == StatusType.MORTGAGE) {
			list = playerB.getListToPass();
			
			drawPrompt(g, 150 + (list.size() * 30));
		
			g.drawString("None", 240, 370);
			for (int i = 0; i < list.size(); i++) {
				g.drawString(list.get(i).getName(), 240, (400 + i * 30));
			}
		}

		else if (playerB.getStatusType() == StatusType.UNMORTGAGE) {
			list = playerB.getListToPass();
			
			drawPrompt(g, 150 + (list.size() * 30));
			
			g.drawString("None", 240, 370);
			for (int i = 0; i < list.size(); i++) {
				g.drawString(list.get(i).getName(), 240, (400 + i * 30));
			}
		}
		
	}
	public void drawPrompt(Graphics g, int height) {
		imgQuestionBackground.draw(200, 270, 380, height);
		// use the power of g to draw the words we want
		// wordwrap method: first, split string into separate words
		// then, get the length of each word and decide to put on existing line or a new line
		String [] words = playerB.getPrompt().split(" ");
		StringBuffer strbuff = new StringBuffer();
		
		int stringWidth = 0;
		int lineCount = 0;
		for (int i = 0; i < words.length; i++) {
			int wordWidth = g.getFont().getWidth(words[i]);
			stringWidth += wordWidth;
			
			strbuff.append(words[i] + " ");
			if (stringWidth > 220) {
				g.drawString(strbuff.toString(), 230, (300 + (lineCount * 20)));
				stringWidth = 0;
				strbuff.setLength(0);
				lineCount++;
			}
		}
		g.drawString(strbuff.toString(), 230, (300 + (lineCount * 20)));
	}

	public void mouseReleased(int button, int x, int y) {
		// for DoYouWantToDoThis questions
		if (playerB.getStatusType() == StatusType.BOOLEAN) {
			if (x > 265 && x < 375 && y > 440 && y < 485) {
				// need a place in gui player to receive answer to its questions -- aka the boolean answer
				playerB.setAnswer(true);
				playerB.stopWaiting();
			} 
			else if (x > 395 && x < 505 && y > 440 && y < 485){
				playerB.setAnswer(false);
				playerB.stopWaiting();
			}
		}
		else if (playerB.getStatusType() == StatusType.HOUSE) {
			selectionFromList(x, y, getList());
		}
		else if (playerB.getStatusType() == StatusType.MORTGAGE) {
			selectionFromList(x, y, getList());
		}
		else if (playerB.getStatusType() == StatusType.UNMORTGAGE) {
			selectionFromList(x, y, getList());
		}
	}
	
	public void selectionFromList(int x, int y, ArrayList<Property> someList) {
		if (x > 240 && x < 420 && y > 370 && y < 385) { // player selected none
			playerB.setPropertyAnswer(null);
			playerB.stopWaiting();
		}
		else {
			for (int i = 0; i < someList.size(); i++) {
				if (x > 240 && x < 420 && (y > 400 + (30 * i)) && (y < 415 + (30 * i))) {
					playerB.setPropertyAnswer(someList.get(i));
					playerB.stopWaiting();
				}
			}
		}
	}

	public Position housePosition(int locationIndex) {
		Position position = new Position(0, 0);

		if (locationIndex >= 0 && locationIndex <= 10) {
			position.setY(665);
			if (locationIndex != 0 || locationIndex != 10) {
				position.setX(667 - (locationIndex * 63));
			}
		}
		if (locationIndex >= 10 && locationIndex <= 20) {
			position.setX(75);
			if (locationIndex != 10 || locationIndex != 20) {
				position.setY(667 - (locationIndex % 10) * 63);
			}
		}
		if (locationIndex >= 20 && locationIndex <= 30) {
			position.setY(75);
			if (locationIndex != 20 || locationIndex != 30) {
				position.setX(667 - ((10 - (locationIndex % 20)) * 63));
			}
		}
		if (locationIndex >= 30 && locationIndex <= 40) {
			position.setX(665);
			if (locationIndex != 30 || locationIndex != 40) {
				position.setY(667 - ((10 - (locationIndex % 30)) * 63));
			}
		}
		return position;
	}
	public void drawHouses(Property property) {
		int index = property.getLocationIndex(game.boardProperties);
		int numberOfHouses = property.getNumberOfHouses();

		if (numberOfHouses == 5) {
			if ((index > 0 && index < 10)) {
				imgHotel.draw((housePosition(index).getX()) + 20, housePosition(index).getY(), 30, 30);
			} 
			else if (index > 10 && index < 20 || (index > 30 && index < 40)) {
				imgHotel.draw(housePosition(index).getX(), (housePosition(index).getY()) + 20, 30, 30);
			} 
			else { // if (index > 20 && index < 30)
				imgHotel.draw((housePosition(index).getX()) + 20, housePosition(index).getY(), 30, 30);
			}
		} 
		else {
			for (int i = 0; i < numberOfHouses; i++) {
				if ((index > 0 && index < 10) || (index > 20 && index < 30)) {
					imgHouse.draw(housePosition(index).getX() + (i * 13), housePosition(index).getY(), 25, 25);
				} 
				else {
					imgHouse.draw(housePosition(index).getX(), housePosition(index).getY() + (i * 13), 25, 25);
				}
			}
		}
	}


	public Position mortgagedPropertyPosition(int locationIndex) {
		Position position = new Position(0, 0);

		if (locationIndex >= 0 && locationIndex <= 10) {
			position.setY(748);
			if (locationIndex != 0 || locationIndex != 10) {
				position.setX(667 - (locationIndex * 63));
			}
		}
		if (locationIndex >= 10 && locationIndex <= 20) {
			position.setX(10);
			if (locationIndex != 10 || locationIndex != 20) {
				position.setY(667 - (locationIndex % 10) * 63);
			}
		}
		if (locationIndex >= 20 && locationIndex <= 30) {
			position.setY(10);
			if (locationIndex != 20 || locationIndex != 30) {
				position.setX(667 - ((10 - (locationIndex % 20)) * 63));
			}
		}
		if (locationIndex >= 30 && locationIndex <= 40) {
			position.setX(748);
			if (locationIndex != 30 || locationIndex != 40) {
				position.setY(667 - ((10 - (locationIndex % 30)) * 63));
			}
		}
		return position;
	}

	public void drawMortgages(Property property) {
		int index = property.getLocationIndex(game.boardProperties);

		if (property.getMortgageStatus()) {
			if ((index > 0 && index < 10) || (index > 20 && index < 30)) {
				imgMortgaged.draw(mortgagedPropertyPosition(index).getX(), mortgagedPropertyPosition(index).getY(), 63,
						10);
			} else {
				imgMortgagedVert.draw(mortgagedPropertyPosition(index).getX(), mortgagedPropertyPosition(index).getY(),
						10, 63);
			}
		}
	}

	public Position propertyOwnershipPosition(int locationIndex) {
		Position position = new Position(0, 0);

		if (locationIndex >= 0 && locationIndex <= 10) {
			position.setY(758);
			if (locationIndex != 0 || locationIndex != 10) {
				position.setX(667 - (locationIndex * 63));
			}
		}
		if (locationIndex >= 10 && locationIndex <= 20) {
			position.setX(0);
			if (locationIndex != 10 || locationIndex != 20) {
				position.setY(667 - (locationIndex % 10) * 63);
			}
		}
		if (locationIndex >= 20 && locationIndex <= 30) {
			position.setY(0);
			if (locationIndex != 20 || locationIndex != 30) {
				position.setX(667 - ((10 - (locationIndex % 20)) * 63));
			}
		}
		if (locationIndex >= 30 && locationIndex <= 40) {
			position.setX(758);
			if (locationIndex != 30 || locationIndex != 40) {
				position.setY(667 - ((10 - (locationIndex % 30)) * 63));
			}
		}
		return position;
	}

	public void drawOwnership(Property property) {
		int index = property.getLocationIndex(game.boardProperties);

		if (property.getPropertyOwner() == playerA) {
			if ((index > 0 && index < 10) || (index > 20 && index < 30)) {
				imgPlayerOneHor.draw(propertyOwnershipPosition(index).getX(), propertyOwnershipPosition(index).getY(),
						63, 10);
			} else {
				imgPlayerOneVert.draw(propertyOwnershipPosition(index).getX(), propertyOwnershipPosition(index).getY(),
						10, 63);
			}
		} else if (property.getPropertyOwner() == playerB) {
			if ((index > 0 && index < 10) || (index > 20 && index < 30)) {
				imgPlayerTwoHor.draw(propertyOwnershipPosition(index).getX(), propertyOwnershipPosition(index).getY(),
						63, 10);
			} else {
				imgPlayerTwoVert.draw(propertyOwnershipPosition(index).getX(), propertyOwnershipPosition(index).getY(),
						10, 63);
			}
		}
	}

	public Position tokenPosition(int locationIndex) {
		Position position = new Position(0, 0);

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

	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new SimpleSlickGame("Simple Slick Game"));
			appgc.setDisplayMode(768, 768, false);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(SimpleSlickGame.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public ArrayList<Property> getList() {
		return list;
	}
}
