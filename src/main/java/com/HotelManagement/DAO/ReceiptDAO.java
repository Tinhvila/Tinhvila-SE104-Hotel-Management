package com.HotelManagement.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import com.HotelManagement.Entity.Receipt;
import com.HotelManagement.Entity.RoomBill;
import com.HotelManagement.Entity.TypeRoom;
import com.HotelManagement.Entity.Room;
import com.HotelManagement.Entity.Revenue;

public class ReceiptDAO {
	private DataSource dataSource;
	
	
	public ReceiptDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	private void close(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
		if(conn != null) conn.close();
		if(stmt != null) stmt.close();
		if(rs != null) rs.close();
	}
	
	public List<Receipt> getAllReceipt() throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String sqlQuery = "SELECT * FROM HOADON ORDER BY length(MaHoaDon), MaHoaDon, TinhTrangThanhToan;";
		List<Receipt> getList = new ArrayList<>();
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlQuery);
			
			while(rs.next()) {
				Receipt getIndex = new Receipt();
				getIndex.setReceiptId(rs.getString(1));
				getIndex.setReceiptCustomerName(rs.getString(2));
				
				getIndex.setReceiptDayCreated(rs.getString(3));
				if(getIndex.getReceiptDayCreated() != null)
					getIndex.setReceiptDayCreated(getIndex.getReceiptDayCreated().substring(0, 16));
				else
					getIndex.setReceiptDayCreated("");
				
				String receiptDayPaid = rs.getString(4);
				if(receiptDayPaid != null)
					getIndex.setReceiptDayPaid(receiptDayPaid.substring(0, 16));
				else
					getIndex.setReceiptDayPaid("");
				
				getIndex.setReceiptPrice(rs.getFloat(5));
				getIndex.setReceiptPaymentStatus(rs.getInt(6));
				getList.add(getIndex);
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(conn, stmt, rs);
		}
		return getList;
	}
	
	public void insertReceipt(Receipt rcp) throws SQLException {
		Connection conn = null;
		PreparedStatement preStmtInsert = null;
		Statement stmtGetIndex = null;
		ResultSet rs = null;
		
		String sqlGetIndex = "SELECT MaHoaDon FROM HOADON ORDER BY length(MaHoaDon), MaHoaDon;";
		String sqlInsert = "INSERT INTO HOADON(MaHoaDon, TenKhachHang, TriGia, TinhTrangThanhToan, NgayLap) VALUES(?,?,?,?,?);";
		
		try {
			conn = dataSource.getConnection();
			stmtGetIndex = conn.createStatement();
			rs = stmtGetIndex.executeQuery(sqlGetIndex);
			
			String currentReceipt = "";
			String nextReceiptId = "";
			String prefixReceiptId = "HD";
			
			int max = 1;
			int traceUnindexed = 1;
			int fillUnindexed = 0;
			
			while(rs.next()) {
				currentReceipt = rs.getString(1);
				//System.out.println(currentReceipt);
				if(currentReceipt != "") {
					if(traceUnindexed != Integer.valueOf(currentReceipt.substring(2))) {
						fillUnindexed = 1;
						break;
					}
					else {
						traceUnindexed++;
					}
					if(Integer.parseInt(currentReceipt.substring(2)) > max){
						max = Integer.parseInt(currentReceipt.substring(2));
					}
				}
			}
			if(currentReceipt != "") {
				//System.out.println(currentReceipt);
				if(fillUnindexed == 1) {
					nextReceiptId = prefixReceiptId + Integer.toString(traceUnindexed);
				}
				else {
					nextReceiptId = prefixReceiptId + Integer.toString(max + 1);
				}
			}
			else
				nextReceiptId = prefixReceiptId + "1";
			
			preStmtInsert = conn.prepareStatement(sqlInsert);
			preStmtInsert.setString(1, nextReceiptId);
			preStmtInsert.setString(2, rcp.getReceiptCustomerName());
			preStmtInsert.setFloat(3, rcp.getReceiptPrice());
			preStmtInsert.setInt(4, 0);
			//Get current date
			Date currentDate = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String currentDateTime = dateFormat.format(currentDate);
			preStmtInsert.setString(5, currentDateTime);
			
			preStmtInsert.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(conn, stmtGetIndex, rs);
			if(preStmtInsert != null)
				preStmtInsert.close();
		}
		
	}
	
	public void deleteReceipt(String receiptId) throws SQLException {
		
		Connection conn = null;
		PreparedStatement preStmtGetRoomBillId = null;
		PreparedStatement preStmtUpdateRoomBillIdState = null;
		PreparedStatement preStmtDeleteReceiptDetail = null;
		PreparedStatement preStmtDeleteReceipt = null;
		
		ResultSet rs = null;
		
		String sqlQueryGetRoomBillId = "SELECT MaPhieuThue FROM CHITIETHOADON WHERE MaHoaDon = ?";
		String sqlUpdateRoomBillState = "UPDATE PHIEUTHUEPHONG SET TinhTrangTraTien = 0 WHERE MaPhieuThue = ?;";
		String sqlDeleteReceiptDetail = "DELETE FROM CHITIETHOADON WHERE MaHoaDon = ?;";
		String sqlDeleteRoomBill = "DELETE FROM HOADON WHERE MaHoaDon = ?;";
		
		try {
			conn = dataSource.getConnection();
			
			List<RoomBill> getRoomBillIdList = new ArrayList<>();
			preStmtGetRoomBillId = conn.prepareStatement(sqlQueryGetRoomBillId);
			preStmtGetRoomBillId.setString(1, receiptId);
			
			//Get room bill id
			rs = preStmtGetRoomBillId.executeQuery();
			while(rs.next()) {
				RoomBill getRoomBillId = new RoomBill();
				getRoomBillId.setRoomBillId(rs.getString(1));
				getRoomBillIdList.add(getRoomBillId);
			}
			
			//Update room bill state from room bill id
			preStmtUpdateRoomBillIdState = conn.prepareStatement(sqlUpdateRoomBillState);
			for(int i = 0; i < getRoomBillIdList.size(); i++) {
				preStmtUpdateRoomBillIdState.setString(1, getRoomBillIdList.get(i).getRoomBillId());
				preStmtUpdateRoomBillIdState.addBatch();
			}
			preStmtUpdateRoomBillIdState.executeBatch();
			
			//Delete receipt detail
			preStmtDeleteReceiptDetail = conn.prepareStatement(sqlDeleteReceiptDetail);
			preStmtDeleteReceiptDetail.setString(1, receiptId);
			preStmtDeleteReceiptDetail.execute();
			
			//Delete receipt
			preStmtDeleteReceipt = conn.prepareStatement(sqlDeleteRoomBill);
			preStmtDeleteReceipt.setString(1, receiptId);
			preStmtDeleteReceipt.execute();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null) conn.close();
			if(preStmtDeleteReceipt != null) preStmtDeleteReceipt.close();
			if(preStmtUpdateRoomBillIdState != null) preStmtUpdateRoomBillIdState.close();
			if(preStmtGetRoomBillId != null) preStmtGetRoomBillId.close();
			if(preStmtDeleteReceiptDetail != null) preStmtDeleteReceiptDetail.close();
			if(rs != null) rs.close();
		}
		
	}
	
	
	
	//Get SUM total value for the bill
	public List<Receipt> getAllTotalValue() throws SQLException{
		List<Receipt> listTotalValue = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String sqlGetSum = "SELECT CTHD.MaHoaDon, SUM(ThanhTien) \r\n"
				+ "FROM HOADON HD JOIN CHITIETHOADON CTHD ON HD.MaHoaDon = CTHD.MaHoaDon \r\n"
				+ "GROUP BY CTHD.MaHoaDon;";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlGetSum);
			
			while(rs.next()) {
				Receipt getIndex = new Receipt();
				getIndex.setReceiptId(rs.getString(1));
				getIndex.setReceiptPrice(rs.getFloat(2));
				listTotalValue.add(getIndex);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(conn, stmt, rs);
		}
		return listTotalValue;
	}
	
	public void updateReceipt(Receipt rcp) throws SQLException {
		
		Connection conn = null;
		
		PreparedStatement preStmtGetSumTotal = null;
		PreparedStatement preStmtUpdateReceipt = null;
		PreparedStatement preStmtGetRoomBill = null;
		PreparedStatement preStmtUpdateRoomBillState = null;
		PreparedStatement preStmtGetRoom = null;
		PreparedStatement preStmtUpdateRoomState = null;
		
		ResultSet rsGetSumValue = null;
		ResultSet rsGetRoomBill = null;
		ResultSet rsGetRoom = null;
		
		List<RoomBill> getRoomBillIdList = new ArrayList<>();
		List<Room> getRoomIdList = new ArrayList<>();
		
		String sqlGetSumTotal =  "SELECT CTHD.MaHoaDon, SUM(ThanhTien) \r\n"
				+ "FROM HOADON HD JOIN CHITIETHOADON CTHD ON HD.MaHoaDon = CTHD.MaHoaDon \r\n"
				+ "WHERE CTHD.MaHoaDon = ? \r\n"
				+ "GROUP BY CTHD.MaHoaDon;";
		String sqlUpdateReceipt = "UPDATE HOADON SET NgayThanhToan = ?, TriGia = ?, TinhTrangThanhToan = ? WHERE MaHoaDon = ?";
		String sqlGetRoomBill = "SELECT MaPhieuThue FROM CHITIETHOADON WHERE MaHoaDon = ?;";
		String sqlUpdateRoomBillState = "UPDATE PHIEUTHUEPHONG SET TinhTrangTraTien = 1 WHERE MaPhieuThue = ?;";
		String sqlGetRoom = "SELECT PHONG.MaPhong \r\n"
				+ "FROM PHONG JOIN PHIEUTHUEPHONG PTP ON PHONG.MaPhong = PTP.MaPhong \r\n"
				+ "JOIN CHITIETHOADON CTHD ON CTHD.MaPhieuThue = PTP.MaPhieuThue \r\n"
				+ "JOIN HOADON HD ON CTHD.MaHoaDon = HD.MaHoaDon \r\n"
				+ "WHERE CTHD.MaHoaDon = ?;";
		String sqlUpdateRoomState = "UPDATE PHONG SET TinhTrang = 0 WHERE MaPhong = ?;";
		
		try {
			conn = dataSource.getConnection();
			
			//Get total value for the receipt
			preStmtGetSumTotal = conn.prepareStatement(sqlGetSumTotal);
			preStmtGetSumTotal.setString(1, rcp.getReceiptId());
			rsGetSumValue = preStmtGetSumTotal.executeQuery();
			
			float getSumTotal = 0;
			while(rsGetSumValue.next()) {
				getSumTotal = rsGetSumValue.getFloat(2);
			}
			
			//Update the receipt
			preStmtUpdateReceipt = conn.prepareStatement(sqlUpdateReceipt);
			//For payment daytime
			Date currentDate = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String currentDateTime = dateFormat.format(currentDate);
			preStmtUpdateReceipt.setString(1, currentDateTime);
			preStmtUpdateReceipt.setFloat(2, getSumTotal);
			preStmtUpdateReceipt.setInt(3, 1);
			preStmtUpdateReceipt.setString(4, rcp.getReceiptId());
			preStmtUpdateReceipt.execute();
			
			//Select all the room bill from receipt detail
			preStmtGetRoomBill = conn.prepareStatement(sqlGetRoomBill);
			preStmtGetRoomBill.setString(1, rcp.getReceiptId());
			rsGetRoomBill = preStmtGetRoomBill.executeQuery();
			
			while(rsGetRoomBill.next()) {
				RoomBill getIndex = new RoomBill();
				getIndex.setRoomBillId(rsGetRoomBill.getString(1));
				getRoomBillIdList.add(getIndex);
			}
			
			//Update all the room bill state to 1
			preStmtUpdateRoomBillState = conn.prepareStatement(sqlUpdateRoomBillState);
			for(int i = 0; i < getRoomBillIdList.size(); i++) {
				preStmtUpdateRoomBillState.setString(1, getRoomBillIdList.get(i).getRoomBillId());
				preStmtUpdateRoomBillState.addBatch();
			}
			preStmtUpdateRoomBillState.executeBatch();
			
			//Select all the room from room bill
			preStmtGetRoom = conn.prepareStatement(sqlGetRoom);
			preStmtGetRoom.setString(1, rcp.getReceiptId());
			rsGetRoom = preStmtGetRoom.executeQuery();
			
			while(rsGetRoom.next()) {
				Room getIndex = new Room();
				getIndex.setRoomId(rsGetRoom.getString(1));
				getRoomIdList.add(getIndex);
			}
			
			//Update room state
			preStmtUpdateRoomState = conn.prepareStatement(sqlUpdateRoomState);
			for(int i = 0; i < getRoomIdList.size(); i++) {
				preStmtUpdateRoomState.setString(1, getRoomIdList.get(i).getRoomId());
				preStmtUpdateRoomState.addBatch();
			}
			preStmtUpdateRoomState.executeBatch();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(conn != null) conn.close();
			if(rsGetSumValue != null) rsGetSumValue.close();
			if(rsGetRoomBill != null) rsGetRoomBill.close();
			if(rsGetRoom != null) rsGetRoom.close();
			if(preStmtUpdateReceipt != null) preStmtUpdateReceipt.close();
			if(preStmtUpdateRoomState != null) preStmtUpdateRoomState.close();
			if(preStmtUpdateRoomBillState != null) preStmtUpdateRoomBillState.close();
			if(preStmtGetRoomBill !=null) preStmtGetRoomBill.close();
			if(preStmtGetRoom != null) preStmtGetRoom.close();
			if(preStmtGetSumTotal != null) preStmtGetSumTotal.close();
		}
		
	}
	
	public void insertRevenue(Receipt rcp) throws SQLException {
		
		Connection conn = null;
		PreparedStatement preStmtGetTypeRoomList = null;
		PreparedStatement preStmtUpdateRevenue = null;
		PreparedStatement preStmtInsertRevenue = null;
		PreparedStatement preStmtGetExistedRevenue = null;
		Statement stmtGetRevenueId = null;
		Statement stmtGetTypeRoomId = null;
		
		ResultSet rsGetTypeRoomList = null;
		ResultSet rsGetRevenueId = null;
		ResultSet rsGetTypeRoomId = null;
		ResultSet rsGetExistedRevenue = null;
		
		String sqlGetTypeRoomList = "SELECT LP.MaLoaiPhong, LP.TenLoaiPhong, SUM(CTHD.ThanhTien), HD.NgayThanhToan\r\n"
				+ "FROM HOADON HD \r\n"
				+ "JOIN CHITIETHOADON CTHD ON HD.MaHoaDon = CTHD.MaHoaDon\r\n"
				+ "JOIN PHIEUTHUEPHONG PTP ON CTHD.MaPhieuThue = PTP.MaPhieuThue \r\n"
				+ "JOIN PHONG ON PTP.MaPhong = PHONG.MaPhong\r\n"
				+ "JOIN LOAIPHONG LP ON PHONG.MaLoaiPhong = LP.MaLoaiPhong\r\n"
				+ "WHERE CTHD.MaHoaDon = ?\r\n"
				+ "GROUP BY LP.MaLoaiPhong;";
		
		//For the non-existence primary key
		String sqlGetRevenueId = "SELECT Thang, Nam FROM DOANHTHU;";
		String sqlInsertRevenue = "INSERT INTO DOANHTHU VALUES(?, ?, ?, ?, ?);";
		String sqlGetTypeRoomId = "SELECT MaLoaiPhong FROM LOAIPHONG;";
		
		//For the existence primary key
		String sqlGetExistedRevenue = "SELECT * FROM DOANHTHU WHERE Thang = ? AND Nam = ?;";
		
		//Use for both
		String sqlUpdateRevenue = "UPDATE DOANHTHU SET DoanhThu = ?, TiLe = ? WHERE Thang = ? AND Nam = ? AND MaLoaiPhong = ?;";
		
		try {
			conn = dataSource.getConnection();
			
			List<TypeRoom> getTypeRoom = new ArrayList<>();
			List<Revenue> getRevenueTemp = new ArrayList<>();
			preStmtGetTypeRoomList = conn.prepareStatement(sqlGetTypeRoomList);
			preStmtGetTypeRoomList.setString(1, rcp.getReceiptId());
			rsGetTypeRoomList = preStmtGetTypeRoomList.executeQuery();
			
			//Get a temporary list for type of room revenue after a payment for receipt is completed
			//with the annualTime is the day payment with set string of yyyy-mm
			while(rsGetTypeRoomList.next()) {
				Revenue getIndex = new Revenue();
				getIndex.setTypeRoomId(rsGetTypeRoomList.getString(1));
				getIndex.setTypeRoomName(rsGetTypeRoomList.getString(2));
				getIndex.setRevenueValue(rsGetTypeRoomList.getFloat(3));
				getIndex.setAnnualTime(rsGetTypeRoomList.getString(4));
				getRevenueTemp.add(getIndex);
			}
			//System.out.println("Get Revenue Temp Successfully!");
			
			String getDatePayment = "";
			if(getRevenueTemp.size() > 0) {
				getDatePayment = getRevenueTemp.get(0).getAnnualTime().substring(0, 7);
				//System.out.println("Get Date Payment Successfully! Date Payment: " + getDatePayment);
			}
			
			//Select the table of revenue to check whether the key is existed
			stmtGetRevenueId = conn.createStatement();
			rsGetRevenueId = stmtGetRevenueId.executeQuery(sqlGetRevenueId);
			
			
			int isCreated = 0;
			while(rsGetRevenueId.next()) {
				if(getDatePayment != "" && Integer.parseInt(getDatePayment.substring(0, 4)) == rsGetRevenueId.getInt(2)
						&& Integer.parseInt(getDatePayment.substring(5)) == rsGetRevenueId.getInt(1)) {
					isCreated = 1;
					//System.out.println("Date Payment for Revenue is existed!");
					break;
				}
			}
			
			//If the annual time exists, update the value of revenue. Otherwise, insert into table
			if(isCreated == 0) {
				//System.out.println("Key does not exist!");
				stmtGetTypeRoomId = conn.createStatement();
				rsGetTypeRoomId = stmtGetTypeRoomId.executeQuery(sqlGetTypeRoomId);
				//Create the revenue table with value of 0 and rate of 0 for each type of rooms
				while(rsGetTypeRoomId.next()) {
					TypeRoom getIndex = new TypeRoom();
					getIndex.setTypeRoomID(rsGetTypeRoomId.getString(1));
					//System.out.println(getIndex.getTypeRoomID());
					getTypeRoom.add(getIndex);
				}
				
				//Insert into the table with value
				preStmtInsertRevenue = conn.prepareStatement(sqlInsertRevenue);
				for(int i = 0; i < getTypeRoom.size(); i++) {
					preStmtInsertRevenue.setInt(1, Integer.parseInt(getDatePayment.substring(5)));
					preStmtInsertRevenue.setInt(2, Integer.parseInt(getDatePayment.substring(0, 4))); //Year
					preStmtInsertRevenue.setString(3, getTypeRoom.get(i).getTypeRoomID());
					//System.out.println(getDatePayment + " " + getTypeRoom.get(i).getTypeRoomID());
					preStmtInsertRevenue.setFloat(4, 0);
					preStmtInsertRevenue.setFloat(5, 0);
					preStmtInsertRevenue.addBatch();
				}
				preStmtInsertRevenue.executeBatch();
				
				//Calculate the total revenue for the receipt of current month in year
				double totalRevenue = 0;
				for(int i = 0; i < getRevenueTemp.size(); i++) {
					totalRevenue = totalRevenue + getRevenueTemp.get(i).getRevenueValue();
				}
				
				//After insert into the table, update the data from the receipt to the table
				preStmtUpdateRevenue = conn.prepareStatement(sqlUpdateRevenue);
				for(int i = 0; i < getRevenueTemp.size(); i++) {
					preStmtUpdateRevenue.setFloat(1, getRevenueTemp.get(i).getRevenueValue());
					preStmtUpdateRevenue.setFloat(2, getRevenueTemp.get(i).getRevenueValue()/(float) totalRevenue);
					preStmtUpdateRevenue.setInt(3, Integer.parseInt(getDatePayment.substring(5)));
					preStmtUpdateRevenue.setInt(4, Integer.parseInt(getDatePayment.substring(0, 4)));
					preStmtUpdateRevenue.setString(5, getRevenueTemp.get(i).getTypeRoomId());
					preStmtUpdateRevenue.addBatch();
				}
				preStmtUpdateRevenue.executeBatch();
			}
			//If the annual time existed, collect data from the table with current revenue and add to the revenue,
			//then update
			else {
				List<Revenue> getExistedRevenue = new ArrayList<>();
				preStmtGetExistedRevenue = conn.prepareStatement(sqlGetExistedRevenue);
				preStmtGetExistedRevenue.setInt(1, Integer.parseInt(getDatePayment.substring(5)));
				preStmtGetExistedRevenue.setInt(2, Integer.parseInt(getDatePayment.substring(0, 4)));
				
				//Get data revenue from the table
				rsGetExistedRevenue = preStmtGetExistedRevenue.executeQuery();
				while(rsGetExistedRevenue.next()) {
					Revenue getIndex = new Revenue();
					getIndex.setMonth(rsGetExistedRevenue.getInt(1));
					getIndex.setYear(rsGetExistedRevenue.getInt(2));
					getIndex.setTypeRoomId(rsGetExistedRevenue.getString(3));
					getIndex.setRevenueValue(rsGetExistedRevenue.getFloat(4));
					getExistedRevenue.add(getIndex);
				}
				
				//Calculate for the current revenue with collected data from table
				double totalRevenue = 0;
				for(int i = 0; i < getExistedRevenue.size(); i++) {
					float temp = getExistedRevenue.get(i).getRevenueValue();
					//System.out.println(getExistedRevenue.get(i).getTypeRoomId() + " initial value: " + temp);
					for(int j = 0; j < getRevenueTemp.size(); j++) {
						//System.out.println(getRevenueTemp.get(j).getTypeRoomId() + " " + getRevenueTemp.get(j).getRevenueValue());
						if(getRevenueTemp.get(j).getTypeRoomId().equals(getExistedRevenue.get(i).getTypeRoomId())) {
							temp = temp + getRevenueTemp.get(j).getRevenueValue();
							//System.out.println("Addition value: " + temp + " value: " + getRevenueTemp.get(j).getRevenueValue());
						}
					}
					//Load into existed revenue
					//System.out.println("Final value: " + temp);
					getExistedRevenue.get(i).setRevenueValue(temp);
					//System.out.println(getExistedRevenue.get(i).getRevenueValue());
					totalRevenue = totalRevenue + temp;
				}
				
				//Update the revenue again
				preStmtUpdateRevenue = conn.prepareStatement(sqlUpdateRevenue);
				for(int i = 0; i < getExistedRevenue.size(); i++) {
					preStmtUpdateRevenue.setFloat(1, getExistedRevenue.get(i).getRevenueValue());
					preStmtUpdateRevenue.setFloat(2, getExistedRevenue.get(i).getRevenueValue()/(float) totalRevenue);
					preStmtUpdateRevenue.setInt(3, Integer.parseInt(getDatePayment.substring(5)));
					preStmtUpdateRevenue.setInt(4, Integer.parseInt(getDatePayment.substring(0, 4)));
					preStmtUpdateRevenue.setString(5, getExistedRevenue.get(i).getTypeRoomId());
					preStmtUpdateRevenue.addBatch();
				}
				preStmtUpdateRevenue.executeBatch();
			}

		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(conn != null) conn.close();
			if(preStmtGetTypeRoomList != null) preStmtGetTypeRoomList.close();
			if(preStmtUpdateRevenue != null) preStmtUpdateRevenue.close();
			if(preStmtInsertRevenue != null) preStmtInsertRevenue.close();
			if(rsGetTypeRoomList != null) rsGetTypeRoomList.close();
			if(rsGetRevenueId != null) rsGetRevenueId.close();
			if(stmtGetRevenueId != null) stmtGetRevenueId.close();
			if(stmtGetTypeRoomId != null) stmtGetTypeRoomId.close();
			if(rsGetTypeRoomId != null) rsGetTypeRoomId.close();
			if(preStmtGetExistedRevenue != null) preStmtGetExistedRevenue.close();
			if(rsGetExistedRevenue != null) rsGetExistedRevenue.close();
		}
		
		
	}
	
}
