package com.HotelManagement.DAO;

import com.HotelManagement.Entity.Customer_RoomBillDetail;
import com.HotelManagement.Entity.RoomBill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import com.HotelManagement.Entity.CountCustomerRoomBill;

public class Customer_RoomBillDetailDAO {
	private DataSource dataSource;
	
	public Customer_RoomBillDetailDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	private void close(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
		if(conn != null) conn.close();
		if(stmt != null) stmt.close();
		if(rs != null) rs.close();
	}
	
	public List<Customer_RoomBillDetail> getCustomerInRoomBill() throws SQLException {
		List<Customer_RoomBillDetail> listCustomers = new ArrayList<Customer_RoomBillDetail>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT MaKhachHang, PTP.MaPhieuThue, TenKhachHang, DiaChi, CMND, LK.MaLoaiKhach \r\n"
				+ "FROM CHITIET_PTP CPTP JOIN PHIEUTHUEPHONG PTP ON CPTP.MaPhieuThue = PTP.MaPhieuThue JOIN\r\n"
				+ "LOAIKHACH LK ON CPTP.MaLoaiKhach = LK.MaLoaiKhach\r\n"
				+ "ORDER BY length(MaKhachHang), MaKhachHang, MaPhieuThue;";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Customer_RoomBillDetail csIndex = new Customer_RoomBillDetail();
				csIndex.setCustomerId(rs.getString(1));
				csIndex.setRoomBillId(rs.getString(2));
				csIndex.setCustomerName(rs.getString(3));
				csIndex.setCustomerAddress(rs.getString(4));
				csIndex.setCustomerIdentityCode(rs.getString(5));
				csIndex.setTypeCustomerId(rs.getString(6));
				
				listCustomers.add(csIndex);
			}
			
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			close(conn,stmt,rs);
		}
		return listCustomers;
	}
	
	public List<CountCustomerRoomBill> getCurrentCustomerInRoomBillId() throws SQLException{
		List<CountCustomerRoomBill> listCurrentCustomerRoomBillId = new ArrayList<>(); 
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT PTP.MaPhieuThue, COUNT(MaKhachHang)\r\n"
				+ "FROM CHITIET_PTP CPTP JOIN PHIEUTHUEPHONG PTP ON CPTP.MaPhieuThue = PTP.MaPhieuThue \r\n"
				+ "GROUP BY PTP.MaPhieuThue;";
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				CountCustomerRoomBill ccrb = new CountCustomerRoomBill();
				ccrb.setRoomBillId(rs.getString(1));
				ccrb.setCountCustomer(rs.getInt(2));
				
				listCurrentCustomerRoomBillId.add(ccrb);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			close(conn,stmt,rs);
		}
		
		return listCurrentCustomerRoomBillId;
	}
	
	public int getMaxCustomerConstraint() throws SQLException {
		Connection conn=null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int getVal = -1;
		
		String sql = "SELECT * FROM THAMSO WHERE TenThamSo = ?";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "SoKhachToiDaMotPhong");
			rs = stmt.executeQuery();
			rs.next();
			getVal = (int) rs.getFloat(2);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(conn, stmt, rs);
		}
		
		return getVal;
	}
	
	public void insertCustomer(Customer_RoomBillDetail cs) throws SQLException {
		
		Connection conn = null;
		PreparedStatement stmtCheckCondition = null;
		Statement stmtGetId = null;
		PreparedStatement preStmtCountCurrentCustomer = null;
		PreparedStatement preStmtInsert = null;
		ResultSet rsGetMaxCus = null;
		ResultSet rsGetCurCus = null;
		ResultSet rsInsertCus = null;
		
		String sqlGetMaxCustomerPerRoom = "SELECT * FROM THAMSO WHERE TenThamSo = ?;";
		String sqlCountCurrentCustomer = "SELECT COUNT(MaKhachHang)\r\n"
				+ "FROM CHITIET_PTP CPTP JOIN PHIEUTHUEPHONG PTP ON CPTP.MaPhieuThue = PTP.MaPhieuThue\r\n"
				+ "WHERE PTP.MaPhieuThue=?;";
		String sqlCheckCustomerId = "SELECT * FROM CHITIET_PTP ORDER BY length(MaKhachHang), MaKhachHang;";
		String sqlInsertCustomer = "INSERT INTO CHITIET_PTP VALUES(?,?,?,?,?,?);";
		
		try {
			conn = dataSource.getConnection();
			stmtCheckCondition = conn.prepareStatement(sqlGetMaxCustomerPerRoom);
			stmtCheckCondition.setString(1, "SoKhachToiDaMotPhong");
			
			rsGetMaxCus = stmtCheckCondition.executeQuery();
			rsGetMaxCus.next();
			
			//Get maximum customer constraint
			int maxCustomer = (int) rsGetMaxCus.getFloat(2);
			//System.out.println("Max customer: " + maxCustomer);
			
			//Get current customer on that room bill
			preStmtCountCurrentCustomer = conn.prepareStatement(sqlCountCurrentCustomer);
			preStmtCountCurrentCustomer.setString(1, cs.getRoomBillId());
			rsGetCurCus = preStmtCountCurrentCustomer.executeQuery();
			rsGetCurCus.next();
			int currentCustomer = rsGetCurCus.getInt(1);
			//System.out.println("Current customer: " + currentCustomer);
			
			
			//If current customer is less than maximum customer constraint, insert new customer
			if(currentCustomer < maxCustomer) {
				String currentCustomerId = "";
				String nextCustomerId = "";
				String prefixCustomerId = "KHACH";
				
				stmtGetId = conn.createStatement();
				rsInsertCus = stmtGetId.executeQuery(sqlCheckCustomerId);
				
				int max = 1;
				int traceUnindexed = 1;
				int fillUnindexed = 0;
				
				while(rsInsertCus.next()) {
					currentCustomerId = rsInsertCus.getString(1);
					if(currentCustomerId != "") {
						if(Integer.valueOf(currentCustomerId.substring(5)) != traceUnindexed) {
							fillUnindexed = 1;
							break;
						}
						else {
							traceUnindexed++;
						}
						if(Integer.parseInt(currentCustomerId.substring(5)) > max){
							max = Integer.parseInt(currentCustomerId.substring(5));
						}
					}
				}
				
				if(currentCustomerId != "") {
					if(fillUnindexed == 1) {
						nextCustomerId = prefixCustomerId + String.valueOf(traceUnindexed);
					}
					else {
						nextCustomerId = prefixCustomerId + String.valueOf(max + 1);
					}
				}else {
					nextCustomerId = prefixCustomerId + "1";
				}
				
				//Insert into customer_roombilldetail
				preStmtInsert = conn.prepareStatement(sqlInsertCustomer);
				preStmtInsert.setString(1, nextCustomerId);
				preStmtInsert.setString(2, cs.getRoomBillId());
				preStmtInsert.setString(3, cs.getCustomerName());
				preStmtInsert.setString(4, cs.getCustomerAddress());
				preStmtInsert.setString(5, cs.getCustomerIdentityCode());
				preStmtInsert.setString(6, cs.getTypeCustomerId());
				preStmtInsert.execute();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			rsGetMaxCus.close();
			rsGetCurCus.close();
			if(rsInsertCus != null)
				rsInsertCus.close();
			if(preStmtInsert != null)
				preStmtInsert.close();
			preStmtCountCurrentCustomer.close();
			stmtCheckCondition.close();
			if(stmtGetId != null)
				stmtGetId.close();
			conn.close();
		}
		
	}
	
	public void updateCustomer(Customer_RoomBillDetail cs) throws SQLException {
		
		Connection conn = null;
		PreparedStatement preStmtUpdate = null;
		String sqlUpdate = "UPDATE CHITIET_PTP SET TenKhachHang = ?, DiaChi = ?, CMND = ?, MaLoaiKhach = ? WHERE MaKhachHang = ?";
		
		try {
			conn = dataSource.getConnection();
			preStmtUpdate = conn.prepareStatement(sqlUpdate);
			preStmtUpdate.setString(1, cs.getCustomerName());
			preStmtUpdate.setString(2, cs.getCustomerAddress());
			preStmtUpdate.setString(3, cs.getCustomerIdentityCode());
			preStmtUpdate.setString(4, cs.getTypeCustomerId());
			preStmtUpdate.setString(5, cs.getCustomerId());
			
			preStmtUpdate.execute();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(preStmtUpdate != null)
				preStmtUpdate.close();
			if(conn != null)
				conn.close();
		}
	}
	
	public void deleteCustomer(Customer_RoomBillDetail cs) throws SQLException {
		
		Connection conn = null;
		PreparedStatement preStmtDelete = null;
		String sqlDelete = "DELETE FROM CHITIET_PTP WHERE MaKhachHang = ?";
		
		try {
			conn = dataSource.getConnection();
			preStmtDelete = conn.prepareStatement(sqlDelete);
			preStmtDelete.setString(1, cs.getCustomerId());
			
			preStmtDelete.execute();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(preStmtDelete != null)
				preStmtDelete.close();
			if(conn != null)
				conn.close();
		}
		
		
	}
	
	public int countCustomer(String roomBillId) throws SQLException {
		int getCount = 0;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sqlSelectRoomBill = "SELECT COUNT(*)\r\n"
				+ "FROM CHITIET_PTP CPTP JOIN PHIEUTHUEPHONG PTP ON CPTP.MaPhieuThue = PTP.MaPhieuThue\r\n"
				+ "WHERE PTP.MaPhieuThue = ?;";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(sqlSelectRoomBill);
			stmt.setString(1, roomBillId);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				getCount = rs.getInt(1);
			}
			
		} catch(SQLException e) {
			
		} finally {
			close(conn, stmt, rs);
		}
		
		
		return getCount;
	}
	
	public int isExistedCustomerOnRoomBill(String roomBillId, Customer_RoomBillDetail getCustomer) throws SQLException {
		int isExisted = 0;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		
		String sqlGetCustomer = "SELECT * FROM CHITIET_PTP CPTP JOIN PHIEUTHUEPHONG PTP ON CPTP.MaPhieuThue = PTP.MaPhieuThue\r\n"
				+ "WHERE PTP.MaPhieuThue = ?;";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(sqlGetCustomer);
			stmt.setString(1, roomBillId);
			rs = stmt.executeQuery();
			
			List<Customer_RoomBillDetail> getList = new ArrayList<>();
			while(rs.next()) {
				Customer_RoomBillDetail getIndex = new Customer_RoomBillDetail();
				getIndex.setCustomerName(rs.getString(3));
				getIndex.setCustomerAddress(rs.getString(4));
				getIndex.setCustomerIdentityCode(rs.getString(5));
				getList.add(getIndex);
			}
			
			for(int i = 0; i < getList.size(); i++) {
				if(getCustomer.getCustomerName().equals(getList.get(i).getCustomerName()) &&
						getCustomer.getCustomerAddress().equals(getList.get(i).getCustomerAddress()) &&
						getCustomer.getCustomerIdentityCode().equals(getList.get(i).getCustomerIdentityCode())) {
					isExisted = 1;
					break;
				}
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(conn, stmt, rs);
		}
		
		return isExisted;
	}
	
	
}
