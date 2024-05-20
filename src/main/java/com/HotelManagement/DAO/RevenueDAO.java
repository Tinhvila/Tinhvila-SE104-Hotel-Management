package com.HotelManagement.DAO;

import com.HotelManagement.Entity.Revenue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class RevenueDAO {
	private DataSource dataSource;
	
	public RevenueDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	private void close(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
		if(conn != null) conn.close();
		if(stmt != null) stmt.close();
		if(rs != null) rs.close();
	}
	
	private void close(Connection conn, PreparedStatement stmt, ResultSet rs) throws SQLException {
		if(conn != null) conn.close();
		if(stmt != null) stmt.close();
		if(rs != null) rs.close();
	}
	
	public List<Integer> getMonth() throws SQLException {
		List<Integer> listMonth = new ArrayList<>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String sqlGetMonthYear = "SELECT DISTINCT Thang FROM DOANHTHU;";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlGetMonthYear);
			
			while(rs.next()) {
				listMonth.add(rs.getInt(1));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(conn, stmt, rs);
		}
		
		return listMonth;
	}
	
	public List<Integer> getYear() throws SQLException {
		List<Integer> listYear = new ArrayList<>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String sqlGetMonthYear = "SELECT DISTINCT Nam FROM DOANHTHU;";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlGetMonthYear);
			
			while(rs.next()) {
				listYear.add(rs.getInt(1));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(conn, stmt, rs);
		}
		
		return listYear;
	}
	
	public List<Revenue> getRevenueList(int month, int year) throws SQLException{
		List<Revenue> listRevenue = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sqlGetRevenueList = "SELECT DT.Thang, DT.Nam, LP.MaLoaiPhong, LP.TenLoaiPhong, DT.DoanhThu, DT.TiLe\r\n"
				+ "FROM DOANHTHU DT JOIN LOAIPHONG LP ON DT.MaLoaiPhong = LP.MaLoaiPhong\r\n";
		
		if(month != -1 && year != -1) {
			sqlGetRevenueList += " WHERE Thang = ? AND Nam = ?\r\n";
		}
		else if(month != -1) {
			sqlGetRevenueList += "WHERE Thang = ?\r\n";
		}
		else if(year != -1) {
			sqlGetRevenueList += "WHERE Nam = ?\r\n";
		}
		sqlGetRevenueList += "ORDER BY Thang ASC, Nam ASC;";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(sqlGetRevenueList);
			if(month != -1 && year != -1) {
				stmt.setInt(1, month);
				stmt.setInt(2, year);
			}
			else if(month != -1 && year == -1) {
				stmt.setInt(1, month);
			}
			else if(year != -1 && month == -1) {
				stmt.setInt(1, year);
			}
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Revenue getIndex = new Revenue();
				getIndex.setMonth(rs.getInt(1));
				getIndex.setYear(rs.getInt(2));
				getIndex.setTypeRoomId(rs.getString(3));
				getIndex.setTypeRoomName(rs.getString(4));
				getIndex.setRevenueValue(rs.getFloat(5));
				getIndex.setTypeRoomRevenueRate(rs.getFloat(6) * 100);
				listRevenue.add(getIndex);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(conn, stmt, rs);
		}
		
		
		return listRevenue;
	}	
}
