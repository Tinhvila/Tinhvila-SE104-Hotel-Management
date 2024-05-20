package com.HotelManagement.Entity;

public class Revenue {
	private int month;
	private int year;
	private String typeRoomId;
	private String typeRoomName;
	private float revenueValue;
	private float typeRoomRevenueRate;
	private String annualTime; //As string
	
	public String getAnnualTime() {
		return annualTime;
	}

	public void setAnnualTime(String annualTime) {
		this.annualTime = annualTime;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	
	public String getTypeRoomName() {
		return typeRoomName;
	}

	public void setTypeRoomName(String typeRoomName) {
		this.typeRoomName = typeRoomName;
	}

	
	
	public Revenue() {}


	public String getTypeRoomId() {
		return typeRoomId;
	}

	public void setTypeRoomId(String typeRoomId) {
		this.typeRoomId = typeRoomId;
	}

	public float getRevenueValue() {
		return revenueValue;
	}

	public void setRevenueValue(float revenueValue) {
		this.revenueValue = revenueValue;
	}

	public float getTypeRoomRevenueRate() {
		return typeRoomRevenueRate;
	}

	public void setTypeRoomRevenueRate(float typeRoomRevenueRate) {
		this.typeRoomRevenueRate = typeRoomRevenueRate;
	}
}
