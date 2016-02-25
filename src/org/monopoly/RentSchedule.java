package org.monopoly;

public class RentSchedule {
	int rent, house1, house2, house3, house4, hotel;
	
	// for regular properties
	public RentSchedule(int rent, int house1, int house2, int house3, int house4, int hotel) {
		this.rent = rent;
		this.house1 = house1;
		this.house2 = house2;
		this.house3 = house3;
		this.house4 = house4;
		this.hotel = hotel;
	}
	// for railroads. utilities don't need rent schedules
	public RentSchedule(int house1, int house2, int house3, int house4) {
		this.house1 = house1;
		this.house2 = house2;
		this.house3 = house3;
		this.house4 = house4;
	}
	public int getRent() {
		return rent;
	}
	public int getHouseOne() {
		return house1;
	}
	public int getHouseTwo() {
		return house2;
	}
	public int getHouseThree() {
		return house3;
	}
	public int getHouseFour() {
		return house4;
	}
	public int getHotel() {
		return hotel;
	}	
}
