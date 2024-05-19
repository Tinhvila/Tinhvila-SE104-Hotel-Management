package com.HotelManagement.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.HotelManagement.Entity.TypeCustomer;

public class TypeOfCustomerDAO {
	private DataSource dataSource;
	
	public TypeOfCustomerDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public List<TypeCustomer> getAllTypeCustomer() throws SQLException{
		List<TypeCustomer> listTypeCustomer= new ArrayList<TypeCustomer>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * \r\n"
				+ " FROM LOAIKHACH ";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String typeCustomerId = rs.getString(1);
				String typeCustomerName = rs.getString(2);
				float typeCustomerChargeRate = rs.getFloat(3);
				
				
				TypeCustomer typeCustomer = new TypeCustomer(typeCustomerId, typeCustomerName, typeCustomerChargeRate);
				
				listTypeCustomer.add(typeCustomer);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			close(conn,stmt,rs);
		}
		
		return listTypeCustomer;
		
		
	}
	
	public void updateTypeCustomer(TypeCustomer typeCustomer) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "UPDATE LOAIKHACH\r\n"
				+ "SET TenLoaiKhach = ?, HeSoPhuThu = ?\r\n" 
				+ "WHERE MaLoaiKhach = ? \r\n";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, typeCustomer.getTypeCustomerName());
			stmt.setFloat(2, typeCustomer.getTypeCustomerChargeRate());
			stmt.setString(3, typeCustomer.getTypeCustomerId());
			
			stmt.execute();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			close(conn,stmt,rs);
		}
	}
	
	public void insertTypeCustomer(TypeCustomer typeCustomer) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		String sqlCheckId = "SELECT MaLoaiKhach FROM LOAIKHACH ORDER BY length(MaLoaiKhach) ASC";
		String sqlInsert = "INSERT INTO LOAIKHACH\r\n" 
				+    "VALUES(?,?,?)";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlCheckId);
			String nextTypeCustomerId = "";
			String currentTypeCustomerId = "";
			while(rs.next()) {
				currentTypeCustomerId = rs.getString(1);					
			}
			if(currentTypeCustomerId != "") {
				// Suppose the last record has the ID format as "LOAIPHONGx"
				String prefixTable = currentTypeCustomerId.substring(0,9);  
				String suffix = currentTypeCustomerId.substring(9);
				
				int nextID = Integer.valueOf(suffix) + 1;
				nextTypeCustomerId = prefixTable + String.valueOf(nextID);
			}
			else
				nextTypeCustomerId = "LOAIKHACH1";
			
		
			preStmt = conn.prepareStatement(sqlInsert);
			
			preStmt.setString(1, nextTypeCustomerId);
			preStmt.setString(2, typeCustomer.getTypeCustomerName());
			preStmt.setFloat(3, typeCustomer.getTypeCustomerChargeRate());
		
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			close(conn,stmt,rs);
		}
		
	}
	
	private void close(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
		if(conn != null) conn.close();
		if(stmt != null) stmt.close();
		if(rs != null) rs.close();
	}

	public void deleteTypeCustomer(String typeCustomerId) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "delete from loaikhach "
						+ "where MaLoaiKhach = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, typeCustomerId);
			stmt.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(conn,stmt,rs);
		}
		
		
	}

	public void addTypeCustomer(String typeCustomerName, String typeCustomerChargeRate) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		String sqlGetId = "SELECT MaLoaiKhach FROM loaikhach order by length(MaLoaiKhach) asc, MaLoaiKhach asc";
		String sql = "INSERT INTO LOAIKHACH VALUES(?,?,?)";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlGetId);
			String nextTypeCustomer = "";
			String currentTypeCustomer = "";
			while(rs.next()) {
				currentTypeCustomer = rs.getString(1);					
			}
			if(currentTypeCustomer != "") {
				// Suppose the last record has the ID format as "LOAIKHACHx"
				System.out.println(currentTypeCustomer);
				String prefixTable = currentTypeCustomer.substring(0,9);  
				String suffix = currentTypeCustomer.substring(9);
				
				int nextID = Integer.valueOf(suffix) + 1;
				nextTypeCustomer = prefixTable + String.valueOf(nextID);
			}
			else 
				nextTypeCustomer = "LOAIKHACH1";
				
			preStmt = conn.prepareStatement(sql);
			
			preStmt.setString(1, nextTypeCustomer);
			preStmt.setString(2, typeCustomerName);
			preStmt.setFloat(3, Float.valueOf(typeCustomerChargeRate)); 
			
			preStmt.execute();
		
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			stmt.close();
			close(conn,preStmt,rs);
		}

		
	}
}
