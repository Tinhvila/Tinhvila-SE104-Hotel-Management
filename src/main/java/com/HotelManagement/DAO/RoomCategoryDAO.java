package com.HotelManagement.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.HotelManagement.Entity.*;
import javax.sql.DataSource;


public class RoomCategoryDAO {
	
	private DataSource dataSource;
	
	public RoomCategoryDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
		
	public List<Room> getAllRooms() throws SQLException{
		List<Room> listRooms = new ArrayList<Room>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT MaPhong, TenPhong, TenLoaiPhong, DonGia, GhiChu, TinhTrang \r\n"
				+ "FROM phong join loaiphong on \r\n"
				+ "phong.MaLoaiPhong = loaiphong.MaLoaiPhong \r\n"
				+ "order by length(TenPhong) asc, TenPhong asc";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String roomId = rs.getString(1);
				String roomName = rs.getString(2);
				String typeOfRoom = rs.getString(3);
				int price = rs.getInt(4);
				String note = rs.getString(5);
				int stateRoom = rs.getInt(6);
				
				Room room = new Room(roomId,roomName,typeOfRoom,price, note, stateRoom);
				
				listRooms.add(room);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			close(conn,stmt,rs);
		}
		
		return listRooms;
	}

	private void close(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
		if(conn != null) conn.close();
		if(stmt != null) stmt.close();
		if(rs != null) rs.close();
	}
	
	public void updateRoom(Room r) throws SQLException {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "UPDATE phong\r\n"
				+ "SET TenPhong = ?, MaLoaiPhong = ?, GhiChu = ? \r\n"
				+ "WHERE phong.MaPhong = ?;";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, r.getRoomName());
			stmt.setString(2,r.getTypeOfRoom());
			stmt.setString(3,r.getNoteRoom());
			stmt.setString(4, r.getRoomId());
			
			stmt.execute();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			close(conn,stmt,rs);
		}
		
	}

	public void deleteRoom(String roomId) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "DELETE FROM phong\r\n"
				+ "WHERE MaPhong = ?;";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, roomId);
			stmt.execute();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			close(conn,stmt,rs);
		}
		
	}

	public void addRoom(Room room) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		String sqlGetId = "SELECT MaPhong FROM phong order by length(MaPhong) asc, MaPhong asc";
		String sql = "INSERT INTO PHONG VALUES(?,?,?,?,?)";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlGetId);
			String nextRoom = "";
			String currentRoom = "";
			while(rs.next()) {
				currentRoom = rs.getString(1);					
			}
			if(currentRoom != "") {
				// Suppose the last record has the ID format as "PHONGx"
				System.out.println(currentRoom);
				String prefixTable = currentRoom.substring(0,5);  
				String suffix = currentRoom.substring(5);
				
				int nextID = Integer.valueOf(suffix) + 1;
				nextRoom = prefixTable + String.valueOf(nextID);
			}
			else 
				nextRoom = "PHONG1";
				
			preStmt = conn.prepareStatement(sql);
			
			preStmt.setString(1, nextRoom);
			preStmt.setString(2, room.getRoomName());
			preStmt.setString(3, room.getTypeOfRoom()); // return typeRoomID
			preStmt.setString(4, room.getNoteRoom());
			preStmt.setInt(5, 0);
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

	public List<Room> searchRooms(String roomName, String typeRoomId, String price, String status) throws SQLException {
		List<Room> searchResult = new ArrayList<Room>();
		
		String sql = "SELECT  TenPhong, TenLoaiPhong ,loaiphong.DonGia ,phong.TinhTrang "
				+ 	"FROM phong, loaiphong "
				+ " WHERE phong.MaLoaiPhong=loaiphong.MaLoaiPhong ";

		if(roomName.trim().isEmpty() == false) {
			sql += " and TenPhong = ? ";
		}
		
		if(!typeRoomId.equals("-1")) {
			sql += " and loaiphong.MaLoaiPhong = ? ";
		}
		
		if(!price.equals("-1")) {
			sql += " and DonGia = ? ";
		}
		
		if(!status.equals("-1")) {
			sql += " and TinhTrang = ? ";
		}
		
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int i = 1;
		
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(sql);
			
			if(roomName.trim().isEmpty() == false) {
				stmt.setString(i++, roomName);
				
			}
			
			if(!typeRoomId.equals("-1")) {
				stmt.setString(i++, typeRoomId);
				
			}
			
			if(!price.equals("-1")) {
				stmt.setInt(i++, Integer.valueOf(price));
				
			}
			
			if(!status.equals("-1")) {
				stmt.setInt(i++, Integer.valueOf(status));
			}
			
			rs = stmt.executeQuery();
			while(rs.next()) {
				
				String nameRoom = rs.getString(1);
				String typeOfRoomName = rs.getString(2);
				int priceRoom = rs.getInt(3);
				int statusRoom = rs.getInt(4);
				
				Room room = new Room(null,nameRoom,typeOfRoomName,priceRoom,null,statusRoom);
				
				searchResult.add(room);
			}
			
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			
			close(conn,stmt,rs);
		}
		return searchResult;
	}
	
	public List<Room> getAvailableRooms() throws SQLException{
		List<Room> listRooms = new ArrayList<Room>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT MaPhong, TenPhong, TenLoaiPhong, DonGia, GhiChu, TinhTrang \r\n"
				+ "FROM phong join loaiphong on \r\n"
				+ "phong.MaLoaiPhong = loaiphong.MaLoaiPhong AND phong.TinhTrang=0;";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String roomId = rs.getString(1);
				String roomName = rs.getString(2);
				String typeOfRoom = rs.getString(3);
				int price = rs.getInt(4);
				String note = rs.getString(5);
				int stateRoom = rs.getInt(6);
				
				Room room = new Room(roomId,roomName,typeOfRoom,price, note, stateRoom);
				
				listRooms.add(room);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			close(conn,stmt,rs);
		}
		
		return listRooms;
	}
	
	
}
