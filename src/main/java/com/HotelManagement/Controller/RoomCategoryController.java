package com.HotelManagement.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.HotelManagement.DAO.RoomCategoryDAO;
import com.HotelManagement.DAO.TypeOfRoomDAO;
import com.HotelManagement.DAO.UserDAO;
import com.HotelManagement.Entity.Room;
import com.HotelManagement.Entity.TypeRoom;


@WebServlet("/room-category")
public class RoomCategoryController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private RoomCategoryDAO roomDAO;
	private TypeOfRoomDAO typeOfRoomDAO;
	
	@Resource(name="jdbc/hotel_db")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		try {
			roomDAO = new RoomCategoryDAO(dataSource);	
			typeOfRoomDAO = new TypeOfRoomDAO(dataSource);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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

	private void deleteRoom(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		
		String roomId = request.getParameter("roomId");
		
		roomDAO.deleteRoom(roomId);
		response.sendRedirect(request.getContextPath() + "/room-category");
	}

	private void updateRoom(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String roomId = request.getParameter("roomId");
		String nameRoom = request.getParameter("nameRoom");
		String typeRoom = request.getParameter("typeRoom");
		String noteRoom = request.getParameter("noteRoom");
		
		Room room = new Room();
		room.setRoomId(roomId);
		room.setRoomName(nameRoom);
		room.setTypeOfRoom(typeRoom);
		room.setNoteRoom(noteRoom);
		
		roomDAO.updateRoom(room);
		response.sendRedirect(request.getContextPath() + "/room-category");
				
	}
	

	private void addRoom(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
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
		
		roomDAO.addRoom(room);
		response.sendRedirect(request.getContextPath() + "/room-category");
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