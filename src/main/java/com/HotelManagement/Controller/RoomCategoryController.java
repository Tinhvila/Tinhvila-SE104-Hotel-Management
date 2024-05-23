package com.HotelManagement.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.HotelManagement.DAO.RoomBillDAO;
import com.HotelManagement.DAO.RoomCategoryDAO;
import com.HotelManagement.DAO.TypeOfRoomDAO;
import com.HotelManagement.DAO.UserDAO;
import com.HotelManagement.Entity.Room;
import com.HotelManagement.Entity.RoomBill;
import com.HotelManagement.Entity.TypeRoom;
import com.HotelManagement.Entity.User;


@WebServlet("/room-category")
public class RoomCategoryController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private RoomCategoryDAO roomDAO;
	private TypeOfRoomDAO typeOfRoomDAO;
	private UserDAO userDAO;
	private RoomBillDAO roomBillDAO;
	
	private final String SCREEN = "QuyenDanhMucPhong";
	
	@Resource(name="jdbc/hotel_db")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		try {
			userDAO = new UserDAO(dataSource);
			roomDAO = new RoomCategoryDAO(dataSource);	
			typeOfRoomDAO = new TypeOfRoomDAO(dataSource);
			roomBillDAO = new RoomBillDAO(dataSource);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			response.sendRedirect(request.getContextPath() +"/login");
			return;
		}
		String roleGroupId = getRoleGroupOfUser(request);
		
		int permissionFlag = 0;
		try {
			permissionFlag = getPermission(roleGroupId,SCREEN);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(permissionFlag == 1) {
			try {
				String action = request.getParameter("ACTION");
				if(action==null) action = "LIST";
				
				switch(action) {
				case "ADD":
					addRoom(request,response);
					break;
				case "UPDATE":
					updateRoom(request,response);
					break;
				case "DELETE":
					deleteRoom(request,response);
					break;
				case "LIST":
					listRooms(request,response);
					break;
				}	
					
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else 
			response.sendRedirect(request.getContextPath() + "/error");
	
	}
	
	private int getPermission(String roleGroupId, String _AUTHENTICATION_SCREEN) throws SQLException {
		
		return userDAO.getPermission(roleGroupId,_AUTHENTICATION_SCREEN);
	}


private String getRoleGroupOfUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		return user.getAuthorizationID();
	}



	private void deleteRoom(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		String roomId = request.getParameter("roomId");
		
		int flag = 0;
		List<RoomBill> listRoomBill = roomBillDAO.getAllRoomBill();
		
		for(RoomBill rb : listRoomBill) {
			if(rb.getRoomId().equals(roomId)) {
				flag = 1;
				break;
			}
				
		}
		
		if(flag == 1) {
			request.setAttribute("message_delete_deny", "Phòng đã được lập phiếu thuê, không thể xóa.");
			listRooms(request,response);
		}
	
		else {
			
			roomDAO.deleteRoom(roomId);
			response.sendRedirect(request.getContextPath() + "/room-category");
		}
		
	}

	private void updateRoom(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		String roomId = request.getParameter("roomId");
		String nameRoom = request.getParameter("nameRoom");
		String typeRoom = request.getParameter("typeRoom");
		String noteRoom = request.getParameter("noteRoom");
		
		Room room = new Room();
		room.setRoomId(roomId);
		room.setRoomName(nameRoom);
		room.setTypeOfRoom(typeRoom);
		room.setNoteRoom(noteRoom);
		
//		int flag = 0;
//		
//		List<Room> listRooms = roomDAO.getAllRooms();
//		
//		for(Room r : listRooms) {
//			if(r.getRoomName().equals(nameRoom)) {
//				flag = 1;
//				break;
//			}
//		}
//		
//		if(flag == 1) {
//			request.setAttribute("message_delete_deny", "Tên phòng đã tồn tại, vui lòng đổi thành tên phòng tên khác.");
//			listRooms(request,response);
//		}
//		else {
			
//	}
			roomDAO.updateRoom(room);
			roomBillDAO.autoUpdatePriceRoom_RoomBill();
			response.sendRedirect(request.getContextPath() + "/room-category");
		
				
	}
	

	private void addRoom(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		String nameRoom = request.getParameter("nameRoom");
		String typeRoom = request.getParameter("typeRoom");
		String noteRoom = request.getParameter("noteRoom");
		System.out.println(noteRoom);
		int status = 0;
		Room room = new Room();
		room.setRoomName(nameRoom);
		room.setTypeOfRoom(typeRoom);
		room.setNoteRoom(noteRoom);
		room.setStateRoom(status);
		
		List<Room> listRooms = roomDAO.getAllRooms();
		int flag = 0;
		
		for(Room r : listRooms) {
			if(r.getRoomName().equals(nameRoom)) {
				flag =1;
				break;
			}
		}
		
		if(flag == 1) {
			request.setAttribute("message_delete_deny", "Tên phòng đã tồn tại, vui lòng nhập tên khác.");
			listRooms(request,response);
		}
		else {
			
			roomDAO.addRoom(room);
			response.sendRedirect(request.getContextPath() + "/room-category");
		}
		
	}

	private void listRooms(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		
		List<Room> listRooms = roomDAO.getAllRooms();
		List<TypeRoom> listTypeOfRooms = typeOfRoomDAO.getAllTypeRooms();
		
		request.setAttribute("listRooms", listRooms);
		request.setAttribute("listTypeRooms", listTypeOfRooms);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/RoomCategory.jsp");
		dispatcher.forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String action = request.getParameter("ACTION");
			if(action==null) action = "LIST";
			
			switch(action) {
			case "ADD":
				addRoom(request,response);
				break;
			case "UPDATE":
				updateRoom(request,response);
				break;
			case "DELETE":
				deleteRoom(request,response);
				break;
			case "LIST":
				listRooms(request,response);
				break;
			}	
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
}
