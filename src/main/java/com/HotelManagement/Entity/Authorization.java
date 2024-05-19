package com.HotelManagement.Entity;

public class Authorization {
	
	private String authorizationId;
	private String authorizationName;
	private int roomCategoryScreen;
	private int billForRentScreen;
	private int searchScreen;
	private int recieptScreen;
	private int revenueScreen;
	private int authorizationScreen;
	private int settingScreen;
	
	
	
	public Authorization(String authorizationId, String authorizationName, int roomCategoryScreen,
			int billForRentScreen, int searchScreen, int recieptScreen, int revenueScreen, int authorizationScreen,
			int settingScreen) {
		this.authorizationId = authorizationId;
		this.authorizationName = authorizationName;
		this.roomCategoryScreen = roomCategoryScreen;
		this.billForRentScreen = billForRentScreen;
		this.searchScreen = searchScreen;
		this.recieptScreen = recieptScreen;
		this.revenueScreen = revenueScreen;
		this.authorizationScreen = authorizationScreen;
		this.settingScreen = settingScreen;
	}
	
	
	public String getAuthorizationId() {
		return authorizationId;
	}
	public void setAuthorizationId(String authorizationId) {
		this.authorizationId = authorizationId;
	}
	public String getAuthorizationName() {
		return authorizationName;
	}
	public void setAuthorizationName(String authorizationName) {
		this.authorizationName = authorizationName;
	}
	public int getRoomCategoryScreen() {
		return roomCategoryScreen;
	}
	public void setRoomCategoryScreen(int roomCategoryScreen) {
		this.roomCategoryScreen = roomCategoryScreen;
	}
	public int getBillForRentScreen() {
		return billForRentScreen;
	}
	public void setBillForRentScreen(int billForRentScreen) {
		this.billForRentScreen = billForRentScreen;
	}
	public int getSearchScreen() {
		return searchScreen;
	}
	public void setSearchScreen(int searchScreen) {
		this.searchScreen = searchScreen;
	}
	public int getRecieptScreen() {
		return recieptScreen;
	}
	public void setRecieptScreen(int recieptScreen) {
		this.recieptScreen = recieptScreen;
	}
	public int getRevenueScreen() {
		return revenueScreen;
	}
	public void setRevenueScreen(int revenueScreen) {
		this.revenueScreen = revenueScreen;
	}
	public int getAuthorizationScreen() {
		return authorizationScreen;
	}
	public void setAuthorizationScreen(int authorizationScreen) {
		this.authorizationScreen = authorizationScreen;
	}
	public int getSettingScreen() {
		return settingScreen;
	}
	public void setSettingScreen(int settingScreen) {
		this.settingScreen = settingScreen;
	}


	@Override
	public String toString() {
		return "Authorization [authorizationId=" + authorizationId + ", authorizationName=" + authorizationName
				+ ", roomCategoryScreen=" + roomCategoryScreen + ", billForRentScreen=" + billForRentScreen
				+ ", searchScreen=" + searchScreen + ", recieptScreen=" + recieptScreen + ", revenueScreen="
				+ revenueScreen + ", authorizatonScreen=" + authorizationScreen + ", settingScreen=" + settingScreen
				+ "]";
	}
	
}
