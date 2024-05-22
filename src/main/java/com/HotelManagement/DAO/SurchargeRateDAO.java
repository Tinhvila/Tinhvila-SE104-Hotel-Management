package com.HotelManagement.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.HotelManagement.Entity.SurchargeRate;
import com.HotelManagement.Entity.User;

public class SurchargeRateDAO {
	private DataSource dataSource;

	public SurchargeRateDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public List<SurchargeRate> getAllSurchargeRate() throws SQLException{
		List<SurchargeRate> listSurcharge = new ArrayList<SurchargeRate>();
	
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "select * from tilephuthu order by length(KhachThu) ASC, KhachThu ASC ";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				int KhachThu = rs.getInt(1);
				float TiLe = rs.getFloat(2);
				SurchargeRate s  = new SurchargeRate(KhachThu, TiLe);
				listSurcharge.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(conn,stmt,rs);
		}

		
		return listSurcharge;
	}
	
	private void close(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
		if(conn != null) conn.close();
		if(stmt != null) stmt.close();
		if(rs != null) rs.close();
	}

	public void updateSurchargeRate(SurchargeRate sur) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "UPDATE TILEPHUTHU\r\n"
				+ "SET TiLePhuThu = ?\r\n" 
				+ "WHERE KhachThu = ? \r\n";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(sql);
			
			stmt.setFloat(1, sur.getValue());
			stmt.setInt(2, sur.getOrderOfCustomer());
			
			stmt.execute();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			close(conn,stmt,rs);
		}
		
	}

	public void deleteSurCharge(int orderOfCustomer) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "delete from tilephuthu "
						+ "where KhachThu = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, orderOfCustomer);
			stmt.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(conn,stmt,rs);
		}
		
	}
	
	public int getNumberOfRecords() throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int n = 0;
		
		try {
			conn = dataSource.getConnection();
			String sql = "select count(*) from TILEPHUTHU";
						
			stmt = conn.createStatement();
		
			rs = stmt.executeQuery(sql);
		
			rs.next();
			
			n =  rs.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(conn,stmt,rs);
		}
		return n;
	}


	public void addSurchargeRate(SurchargeRate sur) throws SQLException {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "insert into tilephuthu values(?,?)";
						
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, sur.getOrderOfCustomer());
			stmt.setFloat(2, sur.getValue());
			stmt.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(conn,stmt,rs);
		}
		
	}

}
