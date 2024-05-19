package com.HotelManagement.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.HotelManagement.Entity.Parameter;



public class ParameterDAO {
	private DataSource dataSource;

	public ParameterDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public List<Parameter> getAllSurchargeRate() throws SQLException{
		List<Parameter> listParams = new ArrayList<Parameter>();
	
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "select * from thamso order by length(TenThamSo) ASC, TenThamSo ASC ";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String ParamName = rs.getString(1);
				float Rate = rs.getFloat(2);
				Parameter p  = new Parameter(ParamName, Rate);
				listParams.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(conn,stmt,rs);
		}

		return listParams;
	}
	
	private void close(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
		if(conn != null) conn.close();
		if(stmt != null) stmt.close();
		if(rs != null) rs.close();
	}

	public void updateParam(Parameter par) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "update thamso "
					+ "set GiaTri = ? "
					+ "where TenThamSo = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setFloat(1, par.getParamValue());
			stmt.setString(2, par.getParamName());
			
			
			
			stmt.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(conn,stmt,rs);
		}

		
	}


}
