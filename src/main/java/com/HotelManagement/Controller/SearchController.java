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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.HotelManagement.DAO.RoomCategoryDAO;
import com.HotelManagement.DAO.TypeOfRoomDAO;
import com.HotelManagement.DAO.UserDAO;
import com.HotelManagement.Entity.Room;
import com.HotelManagement.Entity.TypeRoom;
import com.HotelManagement.Entity.User;


@WebServlet("/search")
public class SearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private RoomCategoryDAO roomCategoryDAO;
	private TypeOfRoomDAO typeOfRoomDAO;
	private UserDAO userDAO;
	private final String SCREEN = "QuyenTraCuuPhong";
	
	@Resource(name="jdbc/hotel_db")
	private DataSource dataSource;
	
	
       
    
	@Override
	public void init() throws ServletException {
		
		super.init();
		userDAO = new UserDAO(dataSource);
		roomCategoryDAO = new RoomCategoryDAO(dataSource);
		typeOfRoomDAO = new TypeOfRoomDAO(dataSource);
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
				case "SEARCH":
					searchRooms(request,response);
					break;	
				case "LIST":
					searchRooms(request,response);
					break;
				}
				listRooms(request,response);
			}
				catch (Exception e) {
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



	
	private void searchRooms(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		String roomName = request.getParameter("roomName");
		String typeRoomId = request.getParameter("typeRoomId");
		String price = request.getParameter("price");
		String status = request.getParameter("status");
		if(roomName == null) roomName = "";
		if(typeRoomId == null) typeRoomId = "-1";
		if(price == null) price = "-1";
		if(status == null) status = "-1";
		List<Room> listRooms = roomCategoryDAO.searchRooms(roomName,typeRoomId,price,status);
		
		request.setAttribute("roomNameResponse", roomName);
		request.setAttribute("typeRoomIdResponse", typeRoomId);
		request.setAttribute("priceResponse", price);
		request.setAttribute("statusResponse", status);
		
		request.setAttribute("listRooms", listRooms);
	}


	private void listRooms(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		
		List<TypeRoom> listTypeRooms = typeOfRoomDAO.getAllTypeRooms();
		request.setAttribute("listTypeRooms", listTypeRooms);
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/Search.jsp");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String action = request.getParameter("ACTION");
			if(action==null) action = "LIST";
			
			switch(action) {
			case "SEARCH":
				searchRooms(request,response);
				break;	
			case "LIST":
				searchRooms(request,response);
				break;
			}
			listRooms(request,response);
		}
			catch (Exception e) {
			e.printStackTrace();
			}
	}

}
