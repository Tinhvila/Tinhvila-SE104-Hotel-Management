package com.HotelManagement.Entity;

public class TypeRoom {
	private String typeRoomID;
	private String nameTypeRoom;
	private int price;
	
	public TypeRoom(String nameTypeRoom, int price) {
		this.nameTypeRoom = nameTypeRoom;
		this.price = price;
	}
	
	public TypeRoom(String typeRoomID, String nameTypeRoom, int price) {
		this.typeRoomID = typeRoomID;
		this.nameTypeRoom = nameTypeRoom;
		this.price = price;
	}
	
	public String getTypeRoomID() {
		return typeRoomID;
	}
	public void setTypeRoomID(String typeRoomID) {
		this.typeRoomID = typeRoomID;
	}
	public String getNameTypeRoom() {
		return nameTypeRoom;
	}
	public void setNameTypeRoom(String nameTypeRoom) {
		this.nameTypeRoom = nameTypeRoom;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
}
