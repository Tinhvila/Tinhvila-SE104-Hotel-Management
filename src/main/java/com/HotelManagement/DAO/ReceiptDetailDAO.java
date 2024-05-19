package com.HotelManagement.DAO;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import com.HotelManagement.Entity.Receipt;
import com.HotelManagement.Entity.ReceiptDetail;
import com.HotelManagement.Entity.RoomBill;
import com.HotelManagement.Entity.CountReceiptDetail;

public class ReceiptDetailDAO {
	private DataSource dataSource;
	
	public ReceiptDetailDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	private void close(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
		if(conn != null) conn.close();
		if(stmt != null) stmt.close();
		if(rs != null) rs.close();
	}
	
	public List<ReceiptDetail> getAllReceiptDetail() throws SQLException{
		List<ReceiptDetail> listReceipt = new ArrayList<>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		
		String sqlSelect = "SELECT CTHD.MaChiTietHoaDon, CTHD.MaHoaDon, PTP.MaPhieuThue, \r\n"
				+ "CTHD.SoNgayThue, CTHD.ThanhTien, PTP.MaPhong, PTP.NgayThue, PTP.NgayTra, PTP.DonGiaMotNgay, PHONG.TenPhong\r\n"
				+ "FROM CHITIETHOADON CTHD JOIN PHIEUTHUEPHONG PTP ON CTHD.MaPhieuThue = PTP.MaPhieuThue \r\n"
				+ "JOIN PHONG ON PTP.MaPhong = PHONG.MaPhong;";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlSelect);
			
			while(rs.next()) {
				String receiptDetailId = rs.getString(1);
				String receiptId = rs.getString(2);
				
				RoomBill receiptRoomBillInfo = new RoomBill();
				receiptRoomBillInfo.setRoomBillId(rs.getString(3));
				int countRoomDayRent = rs.getInt(4);
				float receiptTotalValue = rs.getFloat(5);
				receiptRoomBillInfo.setRoomId(rs.getString(6));
				receiptRoomBillInfo.setRoomDateRent(rs.getString(7));
				receiptRoomBillInfo.setRoomDateReturn(rs.getString(8));
				receiptRoomBillInfo.setRoomPriceDay(rs.getFloat(9));
				receiptRoomBillInfo.setRoomName(rs.getString(10));
				
				ReceiptDetail addIndex = new ReceiptDetail();
				addIndex.setReceiptDetailId(receiptDetailId);
				addIndex.setReceiptId(receiptId);
				addIndex.setReceiptRoomBillInfo(receiptRoomBillInfo);
				addIndex.setCountRoomDayRent(countRoomDayRent);
				addIndex.setReceiptTotalValue(receiptTotalValue);
				
				listReceipt.add(addIndex);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, stmt, rs);
		}
		return listReceipt;
	}
	
	public List<CountReceiptDetail> countReceiptDetail() throws SQLException {
		List<CountReceiptDetail> countReceiptList = new ArrayList<>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String sqlCountReceiptDetail = "SELECT  MaHoaDon, COUNT(*) FROM CHITIETHOADON GROUP BY MaHoaDon;";
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlCountReceiptDetail);
			
			while(rs.next()) {
				CountReceiptDetail getIndex = new CountReceiptDetail();
				getIndex.setReceiptId(rs.getString(1));
				getIndex.setCountReceipt(rs.getInt(2));
				countReceiptList.add(getIndex);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(conn, stmt, rs);
		}
		
		return countReceiptList;
	}
	
	public void insertReceiptDetail(String receiptId, String roomBillId) throws SQLException, ParseException {
		Connection conn = null;
		PreparedStatement preStmtInsert = null;
		PreparedStatement preStmtGetRoomBillId = null;
		PreparedStatement preStmtUpdateRoomBillState = null;
		Statement stmtGetReceiptDetailId = null;
		ResultSet rsGetReceiptDetailId = null;
		ResultSet rsGetRoomBill = null;
		
		String sqlGetReceiptDetailId = "SELECT MaChiTietHoaDon \r\n"
				+ "FROM CHITIETHOADON ORDER BY length(MaChiTietHoaDon), MaChiTietHoaDon";
		String sqlInsert = "INSERT INTO CHITIETHOADON VALUES(?, ?, ?, ?, ?);";
		String sqlGetRoomBillId = "SELECT * FROM PHIEUTHUEPHONG WHERE MaPhieuThue = ?";
		String sqlUpdateRoomBillState = "UPDATE PHIEUTHUEPHONG SET TinhTrangTraTien = -1 WHERE MaPhieuThue = ?;";
		
		try {
			conn = dataSource.getConnection();
			stmtGetReceiptDetailId = conn.createStatement();
			rsGetReceiptDetailId = stmtGetReceiptDetailId.executeQuery(sqlGetReceiptDetailId);
			String currentReceiptDetailId = "";
			String nextReceiptDetailId = "";
			String prefixReceiptDetailId = "CTHD";
			
			//Generate ReceiptDetail primary key
			int max = 1;
			int traceUnindexed = 1;
			int fillUnindexed = 0;
			while(rsGetReceiptDetailId.next()) {
				currentReceiptDetailId = rsGetReceiptDetailId.getString(1);
				if(currentReceiptDetailId != "") {
					if(traceUnindexed != Integer.valueOf(currentReceiptDetailId.substring(4))) {
						fillUnindexed = 1;
						break;
					}
					else {
						traceUnindexed++;
					}
					if(Integer.valueOf(currentReceiptDetailId.substring(4)) > max){
						max = Integer.valueOf(currentReceiptDetailId.substring(4));
					}
				}
			}
			if(currentReceiptDetailId != "") {
				if(fillUnindexed == 1) {
					nextReceiptDetailId = prefixReceiptDetailId + Integer.toString(traceUnindexed);
				}
				else {
					nextReceiptDetailId = prefixReceiptDetailId + Integer.toString(max + 1);
				}
			}else {
				nextReceiptDetailId = prefixReceiptDetailId + "1";
			}
			
			
			//Get RoomBill to calculate the total of room rent = room rent (1 day) * total of day rent
			preStmtGetRoomBillId = conn.prepareStatement(sqlGetRoomBillId);
			preStmtGetRoomBillId.setString(1, roomBillId);
			rsGetRoomBill = preStmtGetRoomBillId.executeQuery();
			
			RoomBill getRoomBillInfo = new RoomBill();
			int totalDayRent = 0;
			float totalValue = 0;
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date dateRent = new Date();
			Date dateReturn = new Date();
			while(rsGetRoomBill.next()) {
				getRoomBillInfo.setRoomBillId(rsGetRoomBill.getString(1));
				getRoomBillInfo.setRoomDateRent(rsGetRoomBill.getString(3));
				getRoomBillInfo.setRoomDateReturn(rsGetRoomBill.getString(4));
				getRoomBillInfo.setRoomPriceDay(rsGetRoomBill.getFloat(5));
			}
			dateRent = dateFormat.parse(getRoomBillInfo.getRoomDateRent());
			dateReturn = dateFormat.parse(getRoomBillInfo.getRoomDateReturn());
			long dayTimeAsMilliseconds = (dateReturn.getTime() - dateRent.getTime());
			totalDayRent = (int)(Math.ceil((float) dayTimeAsMilliseconds / (1000*60*60*24)));
			totalValue = getRoomBillInfo.getRoomPriceDay() * totalDayRent;
			
			/*
			System.out.println("Date rent: " + getRoomBillInfo.getRoomDateRent());
			System.out.println("Date return: " + getRoomBillInfo.getRoomDateReturn());
			System.out.println("Days rented: " + totalDayRent);
			System.out.println("Total price: " + totalValue);
			*/
			
			//After get all information, insert into ReceiptDetail
			preStmtInsert = conn.prepareStatement(sqlInsert);
			preStmtInsert.setString(1, nextReceiptDetailId);
			preStmtInsert.setString(2, receiptId);
			preStmtInsert.setString(3, roomBillId);
			preStmtInsert.setInt(4, totalDayRent);
			preStmtInsert.setFloat(5, totalValue);
			preStmtInsert.execute();
			
			//After insert into ReceiptDetail, update the RoomBill state from not paid to pending
			preStmtUpdateRoomBillState = conn.prepareStatement(sqlUpdateRoomBillState);
			preStmtUpdateRoomBillState.setString(1, roomBillId);
			preStmtUpdateRoomBillState.execute();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(conn, stmtGetReceiptDetailId, rsGetReceiptDetailId);
			if(preStmtInsert != null) preStmtInsert.close();
			if(preStmtGetRoomBillId != null) preStmtGetRoomBillId.close();
			if(rsGetRoomBill != null) rsGetRoomBill.close();
			if(preStmtUpdateRoomBillState != null) preStmtUpdateRoomBillState.close();
		}	
	}
	
	public void deleteReceiptDetail(String receiptDetailId, String roomBillId) throws SQLException {
		Connection conn = null;
		PreparedStatement preStmtDeleteReceiptDetail = null;
		PreparedStatement preStmtUpdateRoomBillIdState = null;
		
		String sqlDeleteReceiptDetail = "DELETE FROM CHITIETHOADON WHERE MaChiTietHoaDon = ?;";
		String sqlUpdateRoomBillIdState = "UPDATE PHIEUTHUEPHONG SET TinhTrangTraTien = 0 WHERE MaPhieuThue = ?";
		
		try {
			conn = dataSource.getConnection();
			preStmtDeleteReceiptDetail = conn.prepareStatement(sqlDeleteReceiptDetail);
			preStmtDeleteReceiptDetail.setString(1, receiptDetailId);
			preStmtDeleteReceiptDetail.execute();
			
			preStmtUpdateRoomBillIdState = conn.prepareStatement(sqlUpdateRoomBillIdState);
			preStmtUpdateRoomBillIdState.setString(1, roomBillId);
			preStmtUpdateRoomBillIdState.execute();
			
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(conn != null) conn.close();
			if(preStmtDeleteReceiptDetail != null) preStmtDeleteReceiptDetail.close();
			if(preStmtUpdateRoomBillIdState != null) preStmtUpdateRoomBillIdState.close();
		}
	}
	
}
