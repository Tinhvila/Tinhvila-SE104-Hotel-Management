package com.HotelManagement.Entity;

public class Receipt {
	private String receiptId;
	private String receiptCustomerName;
	private float receiptPrice;
	private int receiptPaymentStatus;
	private String receiptDayCreated;
	private String receiptDayPaid;
	
	public String getReceiptDayCreated() {
		return receiptDayCreated;
	}
	public void setReceiptDayCreated(String receiptDayCreated) {
		this.receiptDayCreated = receiptDayCreated;
	}
	public String getReceiptDayPaid() {
		return receiptDayPaid;
	}
	public void setReceiptDayPaid(String receiptDayPaid) {
		this.receiptDayPaid = receiptDayPaid;
	}
	public Receipt() {}
	public String getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}
	public String getReceiptCustomerName() {
		return receiptCustomerName;
	}
	public void setReceiptCustomerName(String receiptCustomerName) {
		this.receiptCustomerName = receiptCustomerName;
	}
	public float getReceiptPrice() {
		return receiptPrice;
	}
	public void setReceiptPrice(float receiptPrice) {
		this.receiptPrice = receiptPrice;
	}
	public int getReceiptPaymentStatus() {
		return receiptPaymentStatus;
	}
	public void setReceiptPaymentStatus(int receiptPaymentStatus) {
		this.receiptPaymentStatus = receiptPaymentStatus;
	}
	@Override
	public String toString() {
		return "Receipt [receiptId=" + receiptId + ", receiptCustomerName=" + receiptCustomerName + ", receiptPrice="
				+ receiptPrice + ", receiptPaymentStatus=" + receiptPaymentStatus + "]";
	}
	
	
}
