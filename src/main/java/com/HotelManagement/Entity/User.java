package com.HotelManagement.Entity;

public class User {
	
	private String userName;   
	private String passWord;
	private String fullName; 
	private String authorizationID;
	
	
	
	public User(String userName, String passWord, String fullName, String authorizationID) {
		this.userName = userName;
		this.passWord = passWord;
		this.fullName = fullName;
		this.authorizationID = authorizationID;
	}
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getAuthorizationID() {
		return authorizationID;
	}
	public void setAuthorizationID(String authorizationID) {
		this.authorizationID = authorizationID;
	}


	@Override
	public String toString() {
		return "User [userName=" + userName + ", passWord=" + passWord + ", fullName=" + fullName + ", authorizationID="
				+ authorizationID + "]";
	}
	
}



