package org.monopoly;

import java.util.ArrayList;
import java.util.HashMap;
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
	
	final Game game;
	private ArrayList<Property> list;
	private HashMap<ImageType, Image> map = new HashMap<>();
	
	enum ImageType {
		BOARD, DOG, THIMBLE, HOUSE, HOTEL, PLAYER_ONE_HOR, PLAYER_TWO_HOR, MORTGAGED_HOR, MORTGAGED_VERT, PLAYER_ONE_VERT, PLAYER_TWO_VERT, BACKGROUND, YES_BUTTON, NO_BUTTON;
	}

	private ExecutorService executor = Executors.newSingleThreadExecutor();

	Player playerA = new RandomPlayer("Dog");
	GuiPlayer playerB = new GuiPlayer("Thimble");
	
	public SimpleSlickGame(String gamename) {
		super(gamename);
		game = new Game(playerA, playerB);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		
		map.put(ImageType.BOARD, new Image("res/board.jpg"));
		map.put(ImageType.DOG, new Image("res/dog.png"));
		map.put(ImageType.THIMBLE, new Image("res/thimble.png"));
		map.put(ImageType.HOUSE, new Image("res/house.png"));
		map.put(ImageType.HOTEL, new Image("res/hotel.png"));
		
		map.put(ImageType.PLAYER_ONE_HOR, new Image("res/playerone.png"));
		map.put(ImageType.PLAYER_ONE_VERT, new Image("res/playeronevert.png"));
		map.put(ImageType.PLAYER_TWO_HOR, new Image("res/playertwo.png"));
		map.put(ImageType.PLAYER_TWO_VERT, new Image("res/playertwovert.png"));
		map.put(ImageType.MORTGAGED_HOR, new Image("res/mortgaged.png"));
		map.put(ImageType.MORTGAGED_VERT, new Image("res/mortgagedvert.png"));
		
		map.put(ImageType.BACKGROUND, new Image("res/back.png"));
		map.put(ImageType.YES_BUTTON, new Image("res/yes.png"));
		map.put(ImageType.NO_BUTTON, new Image("res/no.png"));

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
		map.get(ImageType.BOARD).draw(0, 0, 768, 768);
		
		drawPlayer(g, playerA, map.get(ImageType.DOG));
		drawPlayer(g, playerB, map.get(ImageType.THIMBLE));
		drawBalance(g, playerA, "pink", 110, 625, 130, 628);
		drawBalance(g, playerB, "blue", 420, 125, 435, 128);

		for (int i = 0; i < game.getBoardProperties().size(); i++) {
			drawOwnership(game.getBoardProperties().get(i));
			drawMortgages(game.getBoardProperties().get(i));
			drawHouses(game.getBoardProperties().get(i));
		}

		if (playerB.getStatusType() == StatusType.BOOLEAN) {
			drawPrompt(g, 200);
			map.get(ImageType.YES_BUTTON).draw(265, 400, 110, 45);
			map.get(ImageType.NO_BUTTON).draw(395, 400, 110, 45);
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
	public void drawBalance(Graphics g, Player player, String color, int backgroundX, int backgroundY, int textX, int textY) {
		map.get(ImageType.BACKGROUND).draw(backgroundX, backgroundY, 220, 25);
		g.drawString(player.getToken() + "(" + color + "): $" + player.getBalance(), textX, textY);
	}
	public void drawOwnership(Property property) {
		if (property.getPropertyOwner() == playerA) {
			drawHelperMethod(property, map.get(ImageType.PLAYER_ONE_HOR), map.get(ImageType.PLAYER_ONE_VERT), propertyOwnershipPosition(property));
		}
		else if (property.getPropertyOwner() == playerB) {
			drawHelperMethod(property, map.get(ImageType.PLAYER_TWO_HOR), map.get(ImageType.PLAYER_TWO_VERT), propertyOwnershipPosition(property));
		}
	}
	public void drawMortgages(Property property) {
		if (property.getMortgageStatus()) {
			drawHelperMethod(property, map.get(ImageType.MORTGAGED_HOR), map.get(ImageType.MORTGAGED_VERT), mortgagedPropertyPosition(property));
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
			drawHousesHelperMethod(property, map.get(ImageType.HOTEL), housePosition(property), 20, 30);
		} 
		else {
			for (int i = 0; i < numberOfHouses; i++) {
				drawHousesHelperMethod(property, map.get(ImageType.HOUSE), housePosition(property), i * 13, 25);
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
		map.get(ImageType.BACKGROUND).draw(200, 270, 370, height);

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
