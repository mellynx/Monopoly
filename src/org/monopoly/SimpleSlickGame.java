package org.monopoly;

import java.util.ArrayList;
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

public class SimpleSlickGame extends BasicGame {
	Image imgBoard, imgDog, imgThimble, imgHouse, imgHotel, imgPlayerOneHor, imgPlayerTwoHor, imgMortgaged,
			imgMortgagedVert, imgPlayerOneVert, imgPlayerTwoVert, imgQuestionBackground, imgYesButton, imgNoButton;
	final Game game;
	ArrayList<Property> propertyList, mortgageList, unmortgageList;

	// executor handles threads. this makes another thing that will runs thread
	// for you
	// new single thread executor just runs another thread for you
	// there's the thread running through all our code, and the thread in the
	// below executor
	// to run code on this executor, do executor.submit (below, which allows you
	// to return things)
	// (as opposed to call) -- even though we return void
	// submit says run this code in your thread, which in this case is the
	// Single thread executor
	// now there's a thread running our stuff, and another thread rendering
	// stuff
	// a vairable inside submit must be final, so that it can't change.
	// but players don't need to be final, they can be called
	// be careful of modifying things in different threads.

	private ExecutorService executor = Executors.newSingleThreadExecutor();

	Player playerA = new RandomPlayer("Dog");
	GuiPlayer playerB = new GuiPlayer("Thimble"); // call specialized methods on
													// specialized players

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

		// for DoYouWantToDoThis questions
		if (playerB.getStatus()) {
			drawPrompt(g, 250);
			imgYesButton.draw(265, 440, 110, 45);
			imgNoButton.draw(395, 440, 110, 45);
		}
		// for buyHouseB
		else if (playerB.getStatusBuyHouse()) {
			drawPrompt(g, 400);
			
			propertyList = playerB.getListToPass();
			
			g.drawString("None", 240, 360);
			for (int i = 0; i < propertyList.size(); i++) {
				g.drawString(propertyList.get(i).getName(), 240, (380 + i * 20));
			}
		}
		// for mortgagePropertiesB
		else if (playerB.getStatusMortgage()) {
			drawPrompt(g, 400);
			
			mortgageList = playerB.getListToPass();
			
			g.drawString("None", 240, 360);
			for (int i = 0; i < mortgageList.size(); i++) {
				g.drawString(mortgageList.get(i).getName(), 240, (380 + i * 20));
			}
		}
		// for unmortgageB
		else if (playerB.getStatusUnmortgage()) {
			drawPrompt(g, 400);
			
			unmortgageList = playerB.getListToPass();
			
			g.drawString("None", 240, 360);
			for (int i = 0; i < unmortgageList.size(); i++) {
				g.drawString(unmortgageList.get(i).getName(), 240, (380 + i * 20));
			}
		}
		
	}
	public void drawPrompt(Graphics g, int height) {
		imgQuestionBackground.draw(200, 270, 350, height);
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
		if (playerB.getStatus()) {
			if (x > 265 && x < 375 && y > 440 && y < 485) {
				// need a place in gui player to receive answer to its questions -- aka the boolean answer
				playerB.setAnswer(true);
				playerB.getLatch().countDown();
			} 
			else if (x > 395 && x < 505 && y > 440 && y < 485){
				playerB.setAnswer(false);
				playerB.getLatch().countDown();
			}
		}
		// for buyHouseB
		else if (playerB.getStatusBuyHouse()) {
			selectionFromList(x, y, getPropertyList());
		}
		else if (playerB.getStatusMortgage()) {
			selectionFromList(x, y, getMortgageList());
		}
		else if (playerB.getStatusUnmortgage()) {
			selectionFromList(x, y, getUnmortgageList());
		}
	}
	
	public void selectionFromList(int x, int y, ArrayList<Property> someList) {
		if (x > 240 && x < 420 && y > 360 && y < 375) { // player selected none
			playerB.setPropertyAnswer(null);
		}
		else {
			for (int i = 0; i < someList.size(); i++) {
				if (x > 240 && x < 420 && (y > 380 + (10 * i)) && (y < 390 + (10 *i))) {
					playerB.setPropertyAnswer(someList.get(i));
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
			} else if (index > 10 && index < 20 || (index > 30 && index < 40)) {
				imgHotel.draw(housePosition(index).getX(), (housePosition(index).getY()) + 20, 30, 30);
			} else { // if (index > 20 && index < 30)
				imgHotel.draw((housePosition(index).getX()) + 20, housePosition(index).getY(), 30, 30);
			}
		} else {
			for (int i = 0; i < numberOfHouses; i++) {
				if ((index > 0 && index < 10) || (index > 20 && index < 30)) {
					imgHouse.draw(housePosition(index).getX() + (i * 13), housePosition(index).getY(), 25, 25);
				} else {
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
	
	public ArrayList<Property> getPropertyList() {
		return propertyList;
	}
	public ArrayList<Property> getMortgageList() {
		return mortgageList;
	}
	public ArrayList<Property> getUnmortgageList() {
		return unmortgageList;
	}
}
