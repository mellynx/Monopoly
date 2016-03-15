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
	//DONE 

	// TODO: This is begging for some enclosing class or a map that holds all
	// these based on an enum for image type. I love enums and you should too!
	
	private Image board, dog, thimble, house, hotel, playerOneHor, playerTwoHor, mortgagedHor, mortgagedVert, playerOneVert, playerTwoVert, background, yesButton, noButton;
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

		board = new Image("res/board.jpg");
		dog = new Image("res/dog.png");
		thimble = new Image("res/thimble.png");
		house = new Image("res/house.png");
		hotel = new Image("res/hotel.png");

		playerOneHor = new Image("res/playerone.png");
		playerOneVert = new Image("res/playeronevert.png");
		playerTwoHor = new Image("res/playertwo.png");
		playerTwoVert = new Image("res/playertwovert.png");
		mortgagedHor = new Image("res/mortgaged.png");
		mortgagedVert = new Image("res/mortgagedvert.png");

		background = new Image("res/back.png");
		yesButton = new Image("res/yes.png");
		noButton = new Image("res/no.png");

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
		board.draw(0, 0, 768, 768);
		
		drawPlayer(g, playerA, dog);
		drawPlayer(g, playerB, thimble);

		for (int i = 0; i < game.getBoardProperties().size(); i++) {
			drawOwnership(game.getBoardProperties().get(i));
			drawMortgages(game.getBoardProperties().get(i));
			drawHouses(game.getBoardProperties().get(i));
		}

		if (playerB.getStatusType() == StatusType.BOOLEAN) {
			drawPrompt(g, 200);
			yesButton.draw(265, 400, 110, 45);
			noButton.draw(395, 400, 110, 45);
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

	
	public void drawPlayer(Graphics g, Player player, Image image) {
		Property property = player.getLocation();
		image.draw(tokenPosition(property).getX(), tokenPosition(property).getY(), 50, 50);
	}
	public void drawOwnership(Property property) {
		if (property.getPropertyOwner() == playerA) {
			drawHelperMethod(property, playerOneHor, playerOneVert, propertyOwnershipPosition(property));
		}
		else if (property.getPropertyOwner() == playerA) {
			drawHelperMethod(property, playerTwoHor, playerTwoVert, propertyOwnershipPosition(property));
		}
	}
	public void drawMortgages(Property property) {
		if (property.getMortgageStatus()) {
			drawHelperMethod(property, mortgagedHor, mortgagedVert, mortgagedPropertyPosition(property));
		}
	}	
	// helps draw ownership and mortgages
	public void drawHelperMethod(Property property, Image imageHor, Image imageVert, Position position) {
		int index = property.getLocationIndex(game.getBoardProperties());
		
		// horizontal properties
		if ((index > 0 && index < 10) || (index > 20 && index < 30)) {
			imageHor.draw(position.getX(), position.getY(), 63, 10);
		}
		else { // vertical properties
			imageVert.draw(position.getX(), position.getY(), 10, 63);
		}
	}
	public void drawHouses(Property property) {
		int numberOfHouses = property.getNumberOfHouses();

		if (numberOfHouses == 5) {
			drawHousesHelperMethod(property, hotel, housePosition(property), 20, 30);
		} 
		else {
			for (int i = 0; i < numberOfHouses; i++) {
				drawHousesHelperMethod(property, house, housePosition(property), i * 13, 25);
			}
		}
	}
	public void drawHousesHelperMethod(Property property, Image image, Position position, int fudge, int size) {
		int index = property.getLocationIndex(game.getBoardProperties());
		
		if ((index > 0 && index < 10) || (index > 20 && index < 30)) {
			image.draw(position.getX() + fudge, position.getY(), size, size);
		}
		else { // vertical properties
			image.draw(position.getX(), position.getY() + fudge, size, size);
		}
	}
	public void drawPrompt(Graphics g, int height) {
		background.draw(200, 270, 370, height);

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

	
	// these Position methods set the X and Y coordinates of ownership, mortgage, houses, and tokens
	public Position propertyOwnershipPosition(Property property) {
		return positionHelperMethod(property, 758, 0, 667, 63);
	}
	public Position mortgagedPropertyPosition(Property property) {
		return positionHelperMethod(property, 748, 10, 667, 63);
	}
	public Position housePosition(Property property) {
		return positionHelperMethod(property, 665, 75, 667, 63);
	}
	public Position tokenPosition(Property property) {
		return positionHelperMethod(property, 700, 20, 660, 60);
	}
	// sets position for token, property ownership, house, and mortgage methods
	public Position positionHelperMethod(Property property, int a, int b, int c, int d) {
		int index = property.getLocationIndex(game.getBoardProperties());
		Position position = new Position(0, 0);
		
		if (index > 0 && index < 10) {
			position.setY(a);
			position.setX(c - (index * d));
		}
		else if (index > 10 && index < 20) {
			position.setX(b);
			position.setY(c - (index % 10) * d);
		}
		else if (index > 20 && index < 30) {
			position.setY(b);
			position.setX(c - ((10 - (index % 20)) * d));	
		}
		else if (index > 30 && index < 40) {
			position.setX(a);
			position.setY(c - ((10 - (index % 30)) * d));
		}
		else {
			if (index == 0 || index == 30) {
				position.setX(720);
			}
			else if (index == 10 || index == 20) {
				position.setX(50);
			}
			if (index == 0 || index == 10) {
				position.setY(720);
			}
			else if (index == 20 || index == 30) {
				position.setY(50);
			}
		}
		return position;
	}
	
	
	public ArrayList<Property> getList() {
		return list;
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
}
