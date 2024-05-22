package com.HotelManagement.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.HotelManagement.Entity.Room;
import com.HotelManagement.Entity.TypeRoom;

public class TypeOfRoomDAO {

	
	private DataSource dataSource;
	
	public TypeOfRoomDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public List<TypeRoom> getAllTypeRooms() throws SQLException{
		List<TypeRoom> listTypeRooms= new ArrayList<TypeRoom>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * \r\n"
				+ " FROM loaiphong ";
				
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String typeRoomId = rs.getString(1);
				String typeRoomName = rs.getString(2);
				int price = rs.getInt(3);
				
				TypeRoom typeRoom = new TypeRoom(typeRoomId, typeRoomName,price);
				
				listTypeRooms.add(typeRoom);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			close(conn,stmt,rs);
		}
		
		return listTypeRooms;
		
		
	}

	private void close(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
		if(conn != null) conn.close();
		if(stmt != null) stmt.close();
		if(rs != null) rs.close();
	}

	public void updateTypeRoom(TypeRoom typeRoom) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "UPDATE loaiphong \r\n"
				+ " SET TenLoaiPhong = ?,DonGia = ? \r\n"
				+ " WHERE MaLoaiPhong = ? ";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, typeRoom.getNameTypeRoom());
			stmt.setInt(2, typeRoom.getPrice());
			stmt.setString(3, typeRoom.getTypeRoomID());
			
			stmt.execute();
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			close(conn,stmt,rs);
		}
		
	}

	public void addTypeRoom(TypeRoom typeRoom) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		String sqlGetId = "SELECT MaLoaiPhong FROM loaiphong order by length(MaLoaiPhong) ASC, MaLoaiPhong ASC";
		String sql = "INSERT INTO loaiphong VALUES(?,?,?)";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlGetId);
			String nextTypeRoom = "";
			String currentTypeRoom = "";
			
			
			//Getting value with deleted index.
			int traceUnindexed = 1;
			int fillTraceIndexed = 0;
			while(rs.next()) {
				//System.out.println(currentRoom);
				currentTypeRoom = rs.getString(1);
				if(traceUnindexed != Integer.valueOf(currentTypeRoom.substring(9))) {
					fillTraceIndexed = 1;
					break;
				}
				else
					traceUnindexed++;
			}
			if(currentTypeRoom != "") {
				// Suppose the last record has the ID format as "PHONGx"
				//System.out.println(currentRoom);
				String prefixTable = currentTypeRoom.substring(0,9);  
				String suffix = currentTypeRoom.substring(9);
				
				if(fillTraceIndexed == 0) {
					int nextID = Integer.valueOf(suffix) + 1;
					nextTypeRoom = prefixTable + String.valueOf(nextID);
				}
				else {
					nextTypeRoom = prefixTable + String.valueOf(traceUnindexed);
				}
			}
			else
				nextTypeRoom = "LOAIPHONG1";
					
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, nextTypeRoom);
			preStmt.setString(2, typeRoom.getNameTypeRoom());
			preStmt.setInt(3, typeRoom.getPrice()); // return typeRoomID
			
			preStmt.execute();
		
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			close(conn,stmt,rs);
		}
	}

	public void deleteTypeRoom(String typeRoomId) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "DELETE FROM loaiphong\r\n"
				+ "WHERE MaLoaiPhong = ?;";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, typeRoomId);
			stmt.execute();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			close(conn,stmt,rs);
		}
		
	}

	public  TypeRoom getTypeRoomById(String typeRoomId) throws SQLException {
		TypeRoom tr = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select * from loaiphong \r\n"
				+ " WHERE MaLoaiPhong = ? ";
		
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, typeRoomId);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				String typeRoomID = rs.getString(1);
				String typeRoomName = rs.getString(2);
				int price = rs.getInt(3);
				
			tr = new TypeRoom(typeRoomID, typeRoomName,price);
				
				
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			close(conn,stmt,rs);
		}
		
		return tr;
	}
}
