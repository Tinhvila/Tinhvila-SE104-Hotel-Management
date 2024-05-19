package com.HotelManagement.Entity;

public class RoomBill {
	private String roomBillId;
	private String roomId;
	private String roomName;
	private String roomDateRent;
	private String roomDateReturn;
	private float roomPriceDay;
	public float getRoomPriceDay() {
		return roomPriceDay;
	}
	public void setRoomPriceDay(float roomPriceDay) {
		this.roomPriceDay = roomPriceDay;
	}

	private int roomPaymentStatus;
	
	
	
	public String getRoomDateReturn() {
		return roomDateReturn;
	}
	public void setRoomDateReturn(String roomDateReturn) {
		this.roomDateReturn = roomDateReturn;
	}
	public RoomBill(String roomBillId, String roomId, String roomName, String roomDateRent, String roomDateReturn,
			int roomPaymentStatus) {
		super();
		this.roomBillId = roomBillId;
		this.roomId = roomId;
		this.roomName = roomName;
		this.roomDateRent = roomDateRent;
		this.roomDateReturn = roomDateReturn;
		this.roomPaymentStatus = roomPaymentStatus;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomBillId() {
		return roomBillId;
	}
	public void setRoomBillId(String roomBillId) {
		this.roomBillId = roomBillId;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getRoomDateRent() {
		return roomDateRent;
	}
	public void setRoomDateRent(String roomDateRent) {
		this.roomDateRent = roomDateRent;
	}
	public int getRoomPaymentStatus() {
		return roomPaymentStatus;
	}
	public void setRoomPaymentStatus(int roomPaymentStatus) {
		this.roomPaymentStatus = roomPaymentStatus;
	}
	public RoomBill(String roomBillId, String roomId ,String roomName, String roomDateRent, int roomPaymentStatus) {
		super();
		this.roomBillId = roomBillId;
		this.roomId = roomId;
		this.roomName = roomName;
		this.roomDateRent = roomDateRent;
		this.roomPaymentStatus = roomPaymentStatus;
	}
	public RoomBill(String roomBillId, String roomName, String roomDateRent) {
		this.roomBillId = roomBillId;
		this.roomName = roomName;
		this.roomDateRent = roomDateRent;
	}
	
	public RoomBill(String roomBillId, String roomName, String roomDateRent, int roomPaymentStatus) {
		this.roomBillId = roomBillId;
		this.roomName = roomName;
		this.roomDateRent = roomDateRent;
		this.roomPaymentStatus = roomPaymentStatus;
	}
	
	public RoomBill(String roomId, String roomDateRent) {
		super();
		this.roomId = roomId;
		this.roomDateRent = roomDateRent;
	}
	public RoomBill() {
		
	}
	
	@Override
	public String toString() {
		return "RoomBill [roomBillId=" + roomBillId + ", roomId=" + roomId + ", roomName=" + roomName
				+ ", roomDateRent=" + roomDateRent + ", roomPaymentStatus=" + roomPaymentStatus + "]";
	}
	
}
