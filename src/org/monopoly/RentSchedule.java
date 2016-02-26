package org.monopoly;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.monopoly.Property.RentType;

public class RentSchedule {
	int rent, house1, house2, house3, house4, hotel;
	
	//static means it is the same thing across instances of the class
	// not static means there is one for each instance of the class
	Map<Integer, Integer> map = new HashMap<>();
	
	// for regular properties
	
	//in the constructor you should popuylate the map using the arguments from the constructort
	// and in get rent just lookup in the map using that enuym
	public RentSchedule(int rent, int house1, int house2, int house3, int house4, int hotel) {
		map.put(0, rent);
		map.put(1, house1);
		map.put(2, house2);
		map.put(3, house3);
		map.put(4, house4);
		map.put(5, hotel);
	}
	// for railroads. utilities don't need rent schedules
	public RentSchedule(int house1, int house2, int house3, int house4) {
		map.put(0, house1);
		map.put(1, house2);
		map.put(2, house3);
		map.put(3, house4);
	}
	// never put code outside of a method
	public int getRent(int house) {
		return map.get(house);
	}
}
