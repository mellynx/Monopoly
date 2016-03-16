package org.monopoly;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.monopoly.Property.RentType;
import org.monopoly.Property.SpecialType;

public class BoardCreator {
	
	ArrayList<Property> boardProperties;
	Map<Property, Set<Property>> map;
	
	public BoardCreator(ArrayList<Property> boardProperties, Map<Property, Set<Property>> map) {
		this.boardProperties = boardProperties;
		this.map = map;
	}

	public void addProperties() {
		// remember you have to CALL add properties
		
		Set<Property> brown = new HashSet<Property>();
		Set<Property> lightBlue = new HashSet<Property>();
		Set<Property> pink = new HashSet<Property>();
		Set<Property> orange = new HashSet<Property>();
		Set<Property> red = new HashSet<Property>();
		Set<Property> yellow = new HashSet<Property>();
		Set<Property> green = new HashSet<Property>();
		Set<Property> blue = new HashSet<Property>();
		Set<Property> utilities = new HashSet<Property>();
		Set<Property> railroads = new HashSet<Property>();

		RentSchedule mediterraneanRS = new RentSchedule(2, 10, 30, 90, 160, 250);
		RentSchedule balticRS = new RentSchedule(4, 20, 60, 180, 320, 450);

		RentSchedule orientalRS = new RentSchedule(6, 30, 90, 270, 400, 550);
		RentSchedule vermontRS = new RentSchedule(6, 30, 90, 270, 500, 550);
		RentSchedule connecticutRS = new RentSchedule(8, 40, 100, 300, 450, 600);

		RentSchedule stcharlesRS = new RentSchedule(10, 50, 150, 450, 625, 750);
		RentSchedule statesRS = new RentSchedule(10, 50, 150, 450, 625, 750);
		RentSchedule virginiaRS = new RentSchedule(12, 60, 180, 500, 700, 900);

		RentSchedule stjamesRS = new RentSchedule(14, 70, 200, 550, 750, 950);
		RentSchedule tennesseeRS = new RentSchedule(14, 70, 200, 550, 750, 950);
		RentSchedule newyorkRS = new RentSchedule(16, 80, 220, 600, 800, 1000);

		RentSchedule kentuckyRS = new RentSchedule(18, 90, 250, 750, 875, 1050);
		RentSchedule indianaRS = new RentSchedule(18, 90, 250, 700, 875, 1050);
		RentSchedule illinoisRS = new RentSchedule(20, 100, 300, 750, 925, 1100);

		RentSchedule atlanticRS = new RentSchedule(22, 110, 330, 800, 975, 1150);
		RentSchedule ventnorRS = new RentSchedule(22, 110, 330, 800, 975, 1150);
		RentSchedule marvingardensRS = new RentSchedule(24, 120, 360, 850, 1025, 1200);

		RentSchedule pacificRS = new RentSchedule(26, 130, 390, 900, 1100, 1275);
		RentSchedule northcarolinaRS = new RentSchedule(26, 130, 390, 900, 1100, 1275);
		RentSchedule pennsylvaniaRS = new RentSchedule(28, 150, 450, 1000, 1200, 1400);

		RentSchedule parkRS = new RentSchedule(35, 175, 500, 1100, 1300, 1500);
		RentSchedule boardwalkRS = new RentSchedule(50, 200, 600, 1400, 1700, 2000);

		RentSchedule readingrrRS = new RentSchedule(25, 50, 100, 200);
		RentSchedule pennsylvaniarrRS = new RentSchedule(25, 50, 100, 200);
		RentSchedule borrRS = new RentSchedule(25, 50, 100, 200);
		RentSchedule shortlinerrRS = new RentSchedule(25, 50, 100, 200);

		boardProperties.add(new Property("Go", SpecialType.GO));

		// buy cost, house cost
		addSingleProperty(new Property("Mediterranean Ave", 60, 50, 30, mediterraneanRS), brown);
		boardProperties.add(new Property("Community Chest", SpecialType.COMMUNITY_CHEST));
		addSingleProperty(new Property("Baltic Ave", 60, 50, 30, balticRS), brown);
		boardProperties.add(new Property("Income Tax", SpecialType.INCOME_TAX));

		addSingleProperty(new Property("Reading Railroad", RentType.RAILROAD, readingrrRS), railroads);

		addSingleProperty(new Property("Oriental Ave", 100, 50, 50, orientalRS), lightBlue);
		boardProperties.add(new Property("Chance", SpecialType.CHANCE));
		addSingleProperty(new Property("Vermont Ave", 100, 50, 50, vermontRS), lightBlue);
		addSingleProperty(new Property("Connecticut Ave", 100, 50, 60, connecticutRS), lightBlue);

		boardProperties.add(new Property("Jail", SpecialType.JAIL));

		addSingleProperty(new Property("St. Charles Place", 140, 100, 70, stcharlesRS), pink);
		addSingleProperty(new Property("Electric Company", RentType.UTILITY), utilities);
		addSingleProperty(new Property("States Ave", 140, 100, 70, statesRS), pink);
		addSingleProperty(new Property("Virgina Ave", 160, 100, 80, virginiaRS), pink);

		addSingleProperty(new Property("Pennsylvania Railroad", RentType.RAILROAD, pennsylvaniarrRS), railroads);

		addSingleProperty(new Property("St. James Place", 180, 100, 90, stjamesRS), orange);
		boardProperties.add(new Property("Community Chest", SpecialType.COMMUNITY_CHEST));
		addSingleProperty(new Property("Tennessee Ave", 180, 100, 90, tennesseeRS), orange);
		addSingleProperty(new Property("New York Ave", 200, 100, 100, newyorkRS), orange);

		boardProperties.add(new Property("Free Parking", SpecialType.FREE_PARKING));

		addSingleProperty(new Property("Kentucky", 220, 150, 110, kentuckyRS), red);
		boardProperties.add(new Property("Chance", SpecialType.CHANCE));
		addSingleProperty(new Property("Indiana", 220, 150, 110, indianaRS), red);
		addSingleProperty(new Property("Illinois", 240, 150, 120, illinoisRS), red);

		addSingleProperty(new Property("B & O Railroad", RentType.RAILROAD, borrRS), railroads);

		addSingleProperty(new Property("Atlantic", 260, 150, 130, atlanticRS), yellow);
		addSingleProperty(new Property("Ventnor", 260, 150, 130, ventnorRS), yellow);
		addSingleProperty(new Property("Water Works", RentType.UTILITY), utilities);
		addSingleProperty(new Property("Marvin Gardens", 280, 150, 140, marvingardensRS), yellow);

		boardProperties.add(new Property("Go To Jail", SpecialType.GO_TO_JAIL));

		addSingleProperty(new Property("Pacific", 300, 20, 150, pacificRS), green);
		addSingleProperty(new Property("North Carolina", 300, 200, 150, northcarolinaRS), green);
		boardProperties.add(new Property("Community Chest", SpecialType.COMMUNITY_CHEST));
		addSingleProperty(new Property("Pennsylvania", 320, 200, 160, pennsylvaniaRS), green);

		addSingleProperty(new Property("Short Line Railroad", RentType.RAILROAD, shortlinerrRS), railroads);

		boardProperties.add(new Property("Chance", SpecialType.CHANCE));
		addSingleProperty(new Property("Park", 350, 200, 175, parkRS), blue);
		boardProperties.add(new Property("Luxury Tax", SpecialType.LUXURY_TAX));
		addSingleProperty(new Property("Boardwalk", 400, 200, 200, boardwalkRS), blue);
	}
	private void addSingleProperty(Property property, Set<Property> set) {
		boardProperties.add(property);
		set.add(property);
		map.put(property, set);
	}
}
