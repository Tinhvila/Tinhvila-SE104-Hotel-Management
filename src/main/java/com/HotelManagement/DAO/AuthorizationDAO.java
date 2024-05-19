package com.HotelManagement.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.HotelManagement.Entity.Authorization;
import com.HotelManagement.Entity.User;

public class AuthorizationDAO {
	
	private DataSource dataSource;
	
	public AuthorizationDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public List<Authorization> getAllAuthorization() throws SQLException{
		List<Authorization> listAuth = new ArrayList<Authorization>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "select * from phanquyen";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String maPhanQuyen = rs.getString(1);
				String tenPhanQuyen = rs.getString(2);
				int manHinhDanhMucPhong = rs.getInt(3);
				int manHinhThuePhong = rs.getInt(4);
				int manHinhTraCuu = rs.getInt(5);
				int manHinhThanhToan = rs.getInt(6);
				int manHinhDoanhThu = rs.getInt(7);
				int manHinhPhanQuyen = rs.getInt(8);
				int manHinhQuyDinh = rs.getInt(9);
				
				Authorization auth = new Authorization(maPhanQuyen, tenPhanQuyen, manHinhDanhMucPhong, manHinhThuePhong, manHinhTraCuu,manHinhThanhToan, manHinhDoanhThu, manHinhPhanQuyen, manHinhQuyDinh);
				listAuth.add(auth);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(conn,stmt,rs);
		}

		
		
		return listAuth;
	}
	
	private void close(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
		if(conn != null) conn.close();
		if(stmt != null) stmt.close();
		if(rs != null) rs.close();
	}

	public void updateRole(Authorization auth) throws SQLException {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "UPDATE phanquyen\r\n"
				+ "SET QuyenDanhMucPhong = ?, QuyenPhieuThuePhong = ?, QuyenTraCuuPhong = ?, QuyenLapHoaDonThanhToan = ?, QuyenLapBaoCaoDoanhThu = ?, QuyenLapPhanQuyen = ?, QuyenLapQuyDinh = ?\r\n"
				+ "WHERE MaPhanQuyen = ?;";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, auth.getRoomCategoryScreen());
			stmt.setInt(2, auth.getBillForRentScreen());
			stmt.setInt(3, auth.getSearchScreen());
			stmt.setInt(4, auth.getRecieptScreen());
			stmt.setInt(5, auth.getRevenueScreen());
			stmt.setInt(6, auth.getAuthorizationScreen());
			stmt.setInt(7, auth.getSettingScreen());
			
			stmt.setString(8, auth.getAuthorizationId());
			
			stmt.execute();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			close(conn,stmt,rs);
		}

		
	}
	
}
