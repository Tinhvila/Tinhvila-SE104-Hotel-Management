package com.HotelManagement.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.HotelManagement.Entity.RoomBill;
import com.HotelManagement.Entity.TypeCustomerTag;
import com.HotelManagement.Entity.CustomerRateCharge;
import com.HotelManagement.Entity.Room;

public class RoomBillDAO {
	private DataSource dataSource;
	
	public RoomBillDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	private void close(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
		if(conn != null) conn.close();
		if(stmt != null) stmt.close();
		if(rs != null) rs.close();
	}
	
	/*Select all type current room bills*/
	public List<RoomBill> getAllRoomBill() throws SQLException {
		List<RoomBill> listRoomBills = new ArrayList<RoomBill>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT MaPhieuThue, PHONG.MaPhong, TenPhong, NgayThue, NgayTra, DonGiaMotNgay, TinhTrangTraTien\r\n"
				+ " FROM PHIEUTHUEPHONG JOIN PHONG ON PHIEUTHUEPHONG.MaPhong = Phong.MaPhong\r\n"
				+ "ORDER BY TinhTrangTraTien;\r\n";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String roomBillId = rs.getString(1);
				String roomId = rs.getString(2);
				String roomName = rs.getString(3);
				String roomDateRent = rs.getString(4).substring(0, 16);
				String roomDateReturn = rs.getString(5).substring(0, 16);
				float roomPriceDay = rs.getFloat(6);
				int roomPaymentStatus = rs.getInt(7);
				
				RoomBill roomInfoIndex = new RoomBill(roomBillId, roomName, roomDateRent, roomPaymentStatus);
				roomInfoIndex.setRoomId(roomId);
				roomInfoIndex.setRoomDateReturn(roomDateReturn);
				roomInfoIndex.setRoomPriceDay(roomPriceDay);
				listRoomBills.add(roomInfoIndex);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			close(conn,stmt,rs);
		}
		
		return listRoomBills;
	}
	
	//Automatically update price room upon contact of selection, insert customer, update customer or delete customer for current bill
	public void updatePriceRoom_RoomBill(RoomBill rbUpdate) throws SQLException {
		Connection conn = null;
		ResultSet rsGetTypeCustomerCheck = null;
		ResultSet rsGetCountCustomerCheck = null;
		ResultSet rsGetCustomerCheck = null;
		
		PreparedStatement preStmtGetCountCustomerCheck = null;
		PreparedStatement preStmtGetCustomerCheck = null;
		Statement getTypeCustomerCheck = null;
		PreparedStatement preStmtUpdatePrice = null;
		
		//Get number of customers for multiplication check
		String sqlGetCountCustomerCheck = "SELECT COUNT(CPTP.MaKhachHang), PTP.MaPhieuThue, PTP.TinhTrangTraTien, LP.DonGia, KhachThu, TiLePhuThu\r\n"
				+ "FROM (((CHITIET_PTP CPTP JOIN LOAIKHACH LK ON CPTP.MaLoaiKhach = LK.MaLoaiKhach) \r\n"
				+ "	JOIN PHIEUTHUEPHONG PTP ON CPTP.MaPhieuThue = PTP.MaPhieuThue) JOIN\r\n"
				+ "    PHONG ON PTP.MaPhong = PHONG.MaPhong) JOIN LOAIPHONG LP ON PHONG.MaLoaiPhong = LP.MaLoaiPhong, TILEPHUTHU\r\n"
				+ "	WHERE PTP.MaPhieuThue = ?\r\n"
				+ "	GROUP BY PTP.MaPhieuThue, KhachThu\r\n"
				+ "    HAVING COUNT(CPTP.MaKhachHang) = KhachThu\r\n"
				+ "    ORDER BY PTP.MaPhieuThue, length(PTP.MaPhieuThue);";
		
		//Detect for type of customers for multiplication check
		String sqlGetCustomerCheck = "SELECT CPTP.MaKhachHang, PTP.MaPhieuThue, LK.MaLoaiKhach, LK.HeSoPhuThu, PTP.TinhTrangTraTien, PHONG.MaPhong, LP.TenLoaiPhong, LP.DonGia\r\n"
				+ "FROM (((CHITIET_PTP CPTP JOIN LOAIKHACH LK ON CPTP.MaLoaiKhach = LK.MaLoaiKhach) \r\n"
				+ "	JOIN PHIEUTHUEPHONG PTP ON CPTP.MaPhieuThue = PTP.MaPhieuThue) JOIN\r\n"
				+ "    PHONG ON PTP.MaPhong = PHONG.MaPhong) JOIN LOAIPHONG LP ON PHONG.MaLoaiPhong = LP.MaLoaiPhong\r\n"
				+ "    WHERE PTP.MaPhieuThue = ?\r\n"
				+ "    ORDER BY PTP.MaPhieuThue, length(PTP.MaPhieuThue);";
		
		//Detect of type of customers and add a new tag for that type of customer
		String sqlGetTypeCustomerCheck = "SELECT * FROM LOAIKHACH ORDER BY length(MaLoaiKhach), MaLoaiKhach;";
		
		String sqlUpdatePrice = "UPDATE PHIEUTHUEPHONG SET DonGiaMotNgay = ? WHERE MaPhieuThue = ?;";
		
		try {
			conn = dataSource.getConnection();
			
			List<TypeCustomerTag> listTypeCustomer = new ArrayList<>();
			List<CustomerRateCharge> listCustomer = new ArrayList<>();
			getTypeCustomerCheck = conn.createStatement();
			rsGetTypeCustomerCheck = getTypeCustomerCheck.executeQuery(sqlGetTypeCustomerCheck);
			
			//Get type of customer check
			while(rsGetTypeCustomerCheck.next()) {
				TypeCustomerTag tagCheck = new TypeCustomerTag();
				tagCheck.setTypeCustomerId(rsGetTypeCustomerCheck.getString(1));
				tagCheck.setTypeCustomerIsChecked(0);
				listTypeCustomer.add(tagCheck);
			}
			
			//Get current customer count
			preStmtGetCountCustomerCheck = conn.prepareStatement(sqlGetCountCustomerCheck);
			preStmtGetCountCustomerCheck.setString(1, rbUpdate.getRoomBillId());
			rsGetCountCustomerCheck = preStmtGetCountCustomerCheck.executeQuery();
			
			float getChargeRateCount = 0;
			int getCount = 0;
			int initialPrice = 0;
			while(rsGetCountCustomerCheck.next()) {
				getCount = rsGetCountCustomerCheck.getInt(1);
				initialPrice = rsGetCountCustomerCheck.getInt(4);
				getChargeRateCount = rsGetCountCustomerCheck.getFloat(6);
			}
			System.out.println("Count: " + getCount);
			System.out.println("Customer charge rate: " + getChargeRateCount);
			System.out.println("Initial price: " + initialPrice);
			
			preStmtGetCustomerCheck = conn.prepareStatement(sqlGetCustomerCheck);
			preStmtGetCustomerCheck.setString(1,  rbUpdate.getRoomBillId());
			rsGetCustomerCheck = preStmtGetCustomerCheck.executeQuery();
			
			while(rsGetCustomerCheck.next()) {
				CustomerRateCharge customerCheck = new CustomerRateCharge();
				customerCheck.setCustomerId(rsGetCustomerCheck.getString(1));
				customerCheck.setTypeCustomerId(rsGetCustomerCheck.getString(3));
				customerCheck.setCustomerChargeRate(rsGetCustomerCheck.getFloat(4));
				listCustomer.add(customerCheck);
			}
			
			float finalPricePerDay = initialPrice;
			//Calculate for each type of customer
			for(int i = 0; i < listCustomer.size(); i++) {
				for(int j = 0; j < listTypeCustomer.size(); j++) {
					if(listCustomer.get(i).getTypeCustomerId().equals(listTypeCustomer.get(j).getTypeCustomerId()) &&
							listTypeCustomer.get(j).getTypeCustomerIsChecked() == 0) {
						listTypeCustomer.get(j).setTypeCustomerIsChecked(1);
						finalPricePerDay = finalPricePerDay + initialPrice * (listCustomer.get(i).getCustomerChargeRate() - 1);
					}
				}
			}
			//Calculate for the count of customer
			finalPricePerDay = finalPricePerDay + initialPrice*(getChargeRateCount - 1);
			System.out.println("Final price: " + finalPricePerDay);
			
			preStmtUpdatePrice = conn.prepareStatement(sqlUpdatePrice);
			preStmtUpdatePrice.setFloat(1, finalPricePerDay);
			preStmtUpdatePrice.setString(2, rbUpdate.getRoomBillId());
			
			preStmtUpdatePrice.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(conn, getTypeCustomerCheck, rsGetTypeCustomerCheck);
			if(preStmtUpdatePrice != null) preStmtUpdatePrice.close();
			if(preStmtGetCustomerCheck != null) preStmtGetCustomerCheck.close();
			if(preStmtGetCountCustomerCheck != null) preStmtGetCountCustomerCheck.close();
			if(rsGetTypeCustomerCheck != null) rsGetTypeCustomerCheck.close();
			if(rsGetCustomerCheck != null) rsGetCustomerCheck.close();
			if(rsGetCountCustomerCheck != null) rsGetCountCustomerCheck.close();
		}	
	}
	
	/*
	public void updatePriceRoom_RoomBill(Room rUpdate) {
		
	}
	*/
	public String getRoomId(String roomName) throws SQLException {
		String roomId = "";
		
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		String sql = "SELECT MaPhong FROM PHONG WHERE TenPhong = ?";
		
		try {
			conn=dataSource.getConnection();
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, roomName);

			rs = preStmt.executeQuery();
			
			rs.next();
			roomId = rs.getString(1);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(conn, preStmt, rs);
		}
		
		return roomId;
	}
	
	public List<RoomBill> getPaidRoomBill() throws SQLException {
		List<RoomBill> listRoomBills = new ArrayList<RoomBill>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT MaPhieuThue, PHONG.MaPhong, TenPhong, NgayThue, NgayTra, DonGiaMotNgay, TinhTrangTraTien\r\n"
				+ " FROM PHIEUTHUEPHONG JOIN PHONG ON PHIEUTHUEPHONG.MaPhong = Phong.MaPhong\r\n"
				+ "WHERE TinhTrangTraTien = 1;";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String roomBillId = rs.getString(1);
				String roomId = rs.getString(2);
				String roomName = rs.getString(3);
				String roomDateRent = rs.getString(4).substring(0, 16);
				String roomDateReturn = rs.getString(5).substring(0, 16);
				float roomPriceDay = rs.getFloat(6);
				int roomPaymentStatus = rs.getInt(7);
				
				RoomBill roomInfoIndex = new RoomBill(roomBillId, roomName, roomDateRent , roomPaymentStatus);
				roomInfoIndex.setRoomId(roomId);
				roomInfoIndex.setRoomDateReturn(roomDateReturn);
				roomInfoIndex.setRoomPriceDay(roomPriceDay);
				listRoomBills.add(roomInfoIndex);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			close(conn,stmt,rs);
		}
		
		return listRoomBills;
	}
	
	public List<RoomBill> getUnpaidRoomBill() throws SQLException {
		List<RoomBill> listRoomBills = new ArrayList<RoomBill>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT MaPhieuThue, PHONG.MaPhong, TenPhong, NgayThue, NgayTra, DonGiaMotNgay, TinhTrangTraTien\r\n"
				+ " FROM PHIEUTHUEPHONG JOIN PHONG ON PHIEUTHUEPHONG.MaPhong = Phong.MaPhong\r\n"
				+ "WHERE TinhTrangTraTien = 0;";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String roomBillId = rs.getString(1);
				String roomId = rs.getString(2);
				String roomName = rs.getString(3);
				String roomDateRent = rs.getString(4).substring(0, 16);
				String roomDateReturn = rs.getString(5).substring(0, 16);
				float roomPriceDay = rs.getFloat(6);
				int roomPaymentStatus = rs.getInt(7);
				
				RoomBill roomInfoIndex = new RoomBill(roomBillId, roomName, roomDateRent , roomPaymentStatus);
				roomInfoIndex.setRoomId(roomId);
				roomInfoIndex.setRoomDateReturn(roomDateReturn);
				roomInfoIndex.setRoomPriceDay(roomPriceDay);
				listRoomBills.add(roomInfoIndex);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			close(conn,stmt,rs);
		}
		
		return listRoomBills;
	}
	
	public void insertRoomBill(RoomBill roomBill) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement preStmtInsertRoomBill = null;
		PreparedStatement preStmtUpdateRoomState = null;
		ResultSet rs = null;
		String querySelectId = "SELECT MaPhieuThue FROM PHIEUTHUEPHONG ORDER BY length(MaPhieuThue), MaPhieuThue;"; //Defined roomBillId by PTPx
		String queryInsertRoomBill = "INSERT INTO PHIEUTHUEPHONG VALUES(?, ?, ?, ?, 0, ?);";
		String queryUpdateRoomState = "UPDATE PHONG SET TinhTrang = 1 WHERE MaPhong = ?;";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(querySelectId);
			String currentRoomBillId = "";
			String nextRoomBillId = "";
			String prefixRoomBillId = "PTP";
			
			//Tracing to highest current key
			int max = 1;
			int traceUnindexed = 1;
			int fillUnindexed = 0;
			while(rs.next()) {
				currentRoomBillId = rs.getString(1);
				//System.out.println(currentRoomBillId);
				if(currentRoomBillId != "") {
					if(traceUnindexed != Integer.valueOf(currentRoomBillId.substring(3))) {
						fillUnindexed = 1;
						break;
					}
					else {
						traceUnindexed++;
					}
					if(Integer.parseInt(currentRoomBillId.substring(3)) > max){
						max = Integer.parseInt(currentRoomBillId.substring(3));
					}
				}
			}
			if(currentRoomBillId != "") {
				//System.out.println(currentRoomBillId);
				if(fillUnindexed == 1) {
					nextRoomBillId = prefixRoomBillId + Integer.toString(traceUnindexed);
				}
				else {
					nextRoomBillId = prefixRoomBillId + Integer.toString(max + 1);
				}
			}
				
			else
				nextRoomBillId = prefixRoomBillId + "1";
			
			//Insert into room bill
			preStmtInsertRoomBill = conn.prepareStatement(queryInsertRoomBill);
			preStmtInsertRoomBill.setString(1, nextRoomBillId);
			preStmtInsertRoomBill.setString(2, roomBill.getRoomId());
			preStmtInsertRoomBill.setString(3, roomBill.getRoomDateRent());
			preStmtInsertRoomBill.setString(4, roomBill.getRoomDateReturn());
			preStmtInsertRoomBill.setInt(5, 0);
			preStmtInsertRoomBill.execute();
			preStmtInsertRoomBill.close();
			
			//Update the room state
			preStmtUpdateRoomState = conn.prepareStatement(queryUpdateRoomState);
			preStmtUpdateRoomState.setString(1, roomBill.getRoomId());
			preStmtUpdateRoomState.execute();
			preStmtUpdateRoomState.close();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(conn, stmt, rs);
		}
	}
	
	public void deleteRoomBill(RoomBill roomBill) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement preStmt1 = null;
		PreparedStatement preStmt2 = null;
		PreparedStatement preStmt3 = null;
		ResultSet rs = null;
		
		//With unpaid room bill. When delete, update roomID status to 1
		String sqlDeleteCustomer = "DELETE FROM CHITIET_PTP WHERE MaPhieuThue = ?;";
		String sqlDeleteRoomBillUnPaid = "DELETE FROM PHIEUTHUEPHONG WHERE MaPhieuThue = ?;";
		String sqlUpdateRoomStatus = "UPDATE PHONG SET TinhTrang = 0 WHERE MaPhong = ?";
		//String sqlDeleteRoomBillPaid = "";
		
		try {
			conn = dataSource.getConnection();
			preStmt1 = conn.prepareStatement(sqlDeleteRoomBillUnPaid);
			preStmt2 = conn.prepareStatement(sqlUpdateRoomStatus);
			preStmt3 = conn.prepareStatement(sqlDeleteCustomer);
			
			preStmt1.setString(1, roomBill.getRoomBillId());
			preStmt2.setString(1, roomBill.getRoomId());
			preStmt3.setString(1, roomBill.getRoomBillId());
			
			preStmt3.execute();
			preStmt1.execute();
			preStmt2.execute();
			
			
			preStmt1.close();
			preStmt2.close();
			preStmt3.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
		finally {
			close(conn, stmt, rs);
		}
	}
	
	public void updateRoomBill(RoomBill roomBill, String oldRoomId) throws SQLException {
		Connection conn = null;
		PreparedStatement preStmt = null;
		PreparedStatement preStmt2 = null;
		PreparedStatement preStmt3 = null;
		ResultSet rs = null;
		
		String sqlUpdateRoomBill = "UPDATE PHIEUTHUEPHONG SET MaPhong = ?, NgayThue = ?, NgayTra = ? WHERE MaPhieuThue = ?;";
		
		//Changing new room to occupied and old room to available
		String sqlUpdateOldRoomState = "UPDATE PHONG SET TinhTrang = 0 WHERE MaPhong = ?";
		String sqlUpdateNewRoomState = "UPDATE PHONG SET TinhTrang = 1 WHERE MaPhong = ?"; 
		
		try {
			conn = dataSource.getConnection();
			preStmt = conn.prepareStatement(sqlUpdateRoomBill);
			preStmt2 = conn.prepareStatement(sqlUpdateOldRoomState);
			preStmt3 = conn.prepareStatement(sqlUpdateNewRoomState);
			
			preStmt.setString(1, roomBill.getRoomId());
			preStmt.setString(2, roomBill.getRoomDateRent());
			preStmt.setString(3, roomBill.getRoomDateReturn());
			preStmt.setString(4, roomBill.getRoomBillId());
			
			preStmt2.setString(1, oldRoomId);
			
			preStmt3.setString(1, roomBill.getRoomId());
			
			preStmt.execute();
			preStmt2.execute();
			preStmt3.execute();
		} 
		catch(SQLException e) {
			
		}
		finally {
			close(conn, preStmt, rs);
		}
 		
	}
	
	
}
