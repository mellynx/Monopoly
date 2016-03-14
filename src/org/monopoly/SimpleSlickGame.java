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

	// TODO: This is begging for some enclosing class or a map that holds all
	// these based on an enum for image type. I love enums and you should too!
	private Image imgBoard, imgDog, imgThimble, imgHouse, imgHotel, imgPlayerOneHor, imgPlayerTwoHor, imgMortgaged,
			imgMortgagedVert, imgPlayerOneVert, imgPlayerTwoVert, imgQuestionBackground, imgYesButton, imgNoButton;
	final Game game;

	private ArrayList<Property> list;
	
	// study executors 
	private ExecutorService executor = Executors.newSingleThreadExecutor();

	Player playerA = new RandomPlayer("Dog");
	// remember that we are able to call specialized methods on specialized
	// players (in addition to what we inherit)
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
		
		drawPlayer(g, playerA, imgDog);
		drawPlayer(g, playerB, imgThimble);

		for (int i = 0; i < game.boardProperties.size(); i++) {
			drawOwnership(game.boardProperties.get(i));
			drawMortgages(game.boardProperties.get(i));
			drawHouses(game.boardProperties.get(i));
		}

		if (playerB.getStatusType() == StatusType.BOOLEAN) {
			drawPrompt(g, 200);
			imgYesButton.draw(265, 400, 110, 45);
			imgNoButton.draw(395, 400, 110, 45);
		}

		else if (playerB.getStatusType() == StatusType.LIST) {
			list = playerB.getListToPass();
			drawPrompt(g, 150 + (list.size() * 30));
			g.drawString("None", 240, 370);
			for (int i = 0; i < list.size(); i++) {
				g.drawString(list.get(i).getName(), 240, (400 + i * 30));
			}
		}
	}

	public void drawPrompt(Graphics g, int height) {
		imgQuestionBackground.draw(200, 270, 370, height);

		// word wrap method: first, split string into separate words. get the length of each word. 
		// if width of the current string > 220, draw the current string and reset strbuff
		String[] words = playerB.getPrompt().split(" ");
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
	
	public void drawPlayer(Graphics g, Player player, Image image) {
		int index = player.getLocation().getLocationIndex(game.boardProperties);
		image.draw(tokenPosition(index).getX(), tokenPosition(index).getY(), 50, 50);
	}

	public void mouseReleased(int button, int x, int y) {
		if (playerB.getStatusType() == StatusType.BOOLEAN) {
			if (x > 265 && x < 375 && y > 400 && y < 445) {
				playerB.setAnswer(true);
				playerB.stopWaiting();
			} 
			else if (x > 395 && x < 505 && y > 400 && y < 445) {
				playerB.setAnswer(false);
				playerB.stopWaiting();
			}
		} 
		else if (playerB.getStatusType() == StatusType.LIST) {
			selectionFromList(x, y, getList());
		} 
	}

	public void selectionFromList(int x, int y, ArrayList<Property> list) {
		// player selected none
		if (x > 240 && x < 420 && y > 370 && y < 385) { 
			playerB.setPropertyAnswer(null);
			playerB.stopWaiting();
		} 
		else {
			// use the list to generate all the options. Then set clickable territory for all these options based on how many there are
			for (int i = 0; i < list.size(); i++) {
				if (x > 240 && x < 420 && (y > 400 + (30 * i)) && (y < 415 + (30 * i))) {
					playerB.setPropertyAnswer(list.get(i));
					playerB.stopWaiting();
				}
			}
		}
	}
	
	public Position propertyOwnershipPosition(int locationIndex) {
		return positionHelperMethod(locationIndex, 758, 0, 667, 63);
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
		} 
		else if (property.getPropertyOwner() == playerB) {
			if ((index > 0 && index < 10) || (index > 20 && index < 30)) {
				imgPlayerTwoHor.draw(propertyOwnershipPosition(index).getX(), propertyOwnershipPosition(index).getY(),
						63, 10);
			} 
			else {
				imgPlayerTwoVert.draw(propertyOwnershipPosition(index).getX(), propertyOwnershipPosition(index).getY(),
						10, 63);
			}
		}
	}

	public Position housePosition(int locationIndex) {
		return positionHelperMethod(locationIndex, 665, 75, 667, 63);
	}

	public void drawHouses(Property property) {
		int index = property.getLocationIndex(game.boardProperties);
		int numberOfHouses = property.getNumberOfHouses();

		if (numberOfHouses == 5) {
			if ((index > 0 && index < 10) || (index > 20 && index < 30)) { // row 1 or row 3
				imgHotel.draw((housePosition(index).getX()) + 20, housePosition(index).getY(), 30, 30);
			} 
			else { // row 2 or row 4
				imgHotel.draw(housePosition(index).getX(), (housePosition(index).getY()) + 20, 30, 30);
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
		return positionHelperMethod(locationIndex, 748, 10, 667, 63);
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

	public Position tokenPosition(int locationIndex) {
		return positionHelperMethod(locationIndex, 700, 20, 660, 60);
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
	
	public Position positionHelperMethod(int locationIndex, int a, int b, int c, int d) {
		Position position = new Position(0, 0);
		
		if (locationIndex > 0 && locationIndex < 10) {
			position.setY(a);
			position.setX(c - (locationIndex * d));
		}
		else if (locationIndex > 10 && locationIndex < 20) {
			position.setX(b);
			position.setY(c - (locationIndex % 10) * d);
		}
		else if (locationIndex > 20 && locationIndex < 30) {
			position.setY(b);
			position.setX(c - ((10 - (locationIndex % 20)) * d));	
		}
		else if (locationIndex > 30 && locationIndex < 40) {
			position.setX(a);
			position.setY(c - ((10 - (locationIndex % 30)) * d));
		}
		else {
			if (locationIndex == 0 || locationIndex == 30) {
				position.setX(720);
			}
			else if (locationIndex == 10 || locationIndex == 20) {
				position.setX(50);
			}
			if (locationIndex == 0 || locationIndex == 10) {
				position.setY(720);
			}
			else if (locationIndex == 20 || locationIndex == 30) {
				position.setY(50);
			}
		}
		return position;
		//return positionHelperMethod(locationIndex, 700, 20, 660, 60);
	}
}
