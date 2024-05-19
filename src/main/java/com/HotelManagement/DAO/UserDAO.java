package com.HotelManagement.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.HotelManagement.Entity.User;

public class UserDAO {

	private DataSource dataSource;

	public UserDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public List<User> getAllUsers() throws SQLException{
		
		List<User> listUsers = new ArrayList<User>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "select * from nguoidung order by length(TenDangNhap) ASC, TenDangNhap ASC ";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String TenDangNhap = rs.getString(1);
				String MatKhau = rs.getString(2);
				String TenNguoiDung = rs.getString(3);
				String MaNhom = rs.getString(4);
				User u = new User(TenDangNhap,MatKhau,TenNguoiDung, MaNhom);
				listUsers.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(conn,stmt,rs);
		}

		
		return listUsers;
	}
	
	
	
	public User getUser(String username, String password) throws SQLException {
		User u = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		
		try {
			conn = dataSource.getConnection();
			String sql = "select * from nguoidung where TenDangNhap = ? and MatKhau = ?";
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, username);
			stmt.setString(2, password);

			rs = stmt.executeQuery();
			
			while(rs.next()) {
				String TenDangNhap = rs.getString(1);
				String MatKhau = rs.getString(2);
				String TenNguoiDung = rs.getString(3);
				String MaNhom = rs.getString(4);
				
				u = new User(TenDangNhap,MatKhau,TenNguoiDung, MaNhom);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(conn,stmt,rs);
		}
	
		return u;
		
	}

	private void close(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
		if(conn != null) conn.close();
		if(stmt != null) stmt.close();
		if(rs != null) rs.close();
	}

	public int getPermission(String roleGroupId, String _AUTHENTICATION_SCREEN) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		int flag = 0;
		
		try {
			conn = dataSource.getConnection();
			String sql = "select "  + _AUTHENTICATION_SCREEN + " from phanquyen where MaPhanQuyen = ?";
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, roleGroupId);
			rs = stmt.executeQuery();
			rs.next();
			flag = rs.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(conn,stmt,rs);
		}
	
		return flag;

		
	}

	public void addUser(User user) throws SQLException {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "insert into nguoidung values(?,?,?,?)";
			stmt = conn.prepareStatement(sql);

			stmt.setString(1, user.getUserName());
			stmt.setString(2, user.getPassWord());
			stmt.setString(3, user.getFullName());
			stmt.setString(4, user.getAuthorizationID());
			
			stmt.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(conn,stmt,rs);
		}
		
		
	}

	public void updateUser(String fullName, String userName, String roleId) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "update nguoidung "
					+ "set HoTen = ?, MaPhanQuyen = ? "
					+ "where TenDangNhap = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, fullName);
			stmt.setString(2, roleId);
			stmt.setString(3, userName);
			
			
			stmt.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(conn,stmt,rs);
		}
		
	}

	public void deleteUser(String email) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "delete from nguoidung "
						+ "where TenDangNhap = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			
			stmt.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(conn,stmt,rs);
		}
		
	}
	
	
}
