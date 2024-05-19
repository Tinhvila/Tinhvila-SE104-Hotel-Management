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

import com.HotelManagement.DAO.AuthorizationDAO;
import com.HotelManagement.DAO.RoomCategoryDAO;
import com.HotelManagement.DAO.TypeOfRoomDAO;
import com.HotelManagement.DAO.UserDAO;
import com.HotelManagement.Entity.Authorization;
import com.HotelManagement.Entity.Room;
import com.HotelManagement.Entity.TypeRoom;
import com.HotelManagement.Entity.User;


@WebServlet("/authorization")
public class AuthorizationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    private AuthorizationDAO authorizationDAO;
    private UserDAO userDAO;
    
    private final String SCREEN = "QuyenLapPhanQuyen";
    
    @Resource(name="jdbc/hotel_db")
    private DataSource dataSource;
    
    @Override
	public void init() throws ServletException {
		super.init();
		try {
			authorizationDAO = new AuthorizationDAO(dataSource);	
			userDAO = new UserDAO(dataSource);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
    
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
				case "ADD_USER":
					addUser(request,response);
					break;
				case "UPDATE_ROLE":
					updateRole(request,response);
					break;
				case "UPDATE_USER":
					updateUser(request,response);
					break;
				case "DELETE_USER":
					deleteUser(request,response);
					break;
				case "LIST":
					listInfo(request,response);
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


private void updateRole(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		String roleId = request.getParameter("roleID");
		String roleName = request.getParameter("roleName");
		
		int roomCategory = 0;
		int billForRent = 0;
		int search = 0;
		int reciept = 0;
		int revenue = 0;
		int authorization = 0;
		int setting = 0;
		
		String roomCategoryScreen = request.getParameter("roomCategoryScreen");
		if(roomCategoryScreen != null) { roomCategory = 1;}
		
		String billForRentScreen = request.getParameter("billForRentScreen");
		if(billForRentScreen != null) { billForRent = 1;}
		
		String searchScreen = request.getParameter("searchScreen");
		if(searchScreen != null) { search = 1;}
		
		String recieptScreen = request.getParameter("recieptScreen");
		if(recieptScreen != null) {reciept = 1;}
		
		String revenueScreen = request.getParameter("revenueScreen");
		if(revenueScreen != null) {revenue = 1;}
		
		System.out.println(revenueScreen);
		
		String authorizationScreen = request.getParameter("authorizationScreen");
		if(authorizationScreen != null) {authorization = 1;}
		
		System.out.println(authorizationScreen);
		
		String settingScreen = request.getParameter("settingScreen");
		if(settingScreen != null) {setting = 1;}
		
		Authorization auth = new  Authorization(roleId, roleName, roomCategory, billForRent, search, reciept, revenue, authorization, setting);
		
		authorizationDAO.updateRole(auth);
}


private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
	String email = request.getParameter("email");
	
	userDAO.deleteUser(email);
		
	response.sendRedirect(request.getContextPath() + "/authorization");
	}


private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
	String fullName = request.getParameter("fullName");
	String userName = request.getParameter("userName"); 
	String roleId = request.getParameter("roleId");
	
	userDAO.updateUser(fullName,userName,roleId);
	
	response.sendRedirect(request.getContextPath() + "/authorization");
	}


private void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
	
	String fullName = request.getParameter("fullName");
	String userName = request.getParameter("userName");
	String password = request.getParameter("password");
	String roleId = request.getParameter("roleId");
	
	// userName is unique
	int isUnique = 1;
	List<User> listEmails = userDAO.getAllUsers();
	for(User user : listEmails) {
		if(user.getUserName().equals(userName)) {
			isUnique = 0;
		}
	}
	
	
	if(isUnique == 1) {
		User user = new User(userName, password, fullName,roleId);
		userDAO.addUser(user);
		response.sendRedirect(request.getContextPath() + "/authorization");
	}
	
	else {
		request.setAttribute("userName", userName);
		request.setAttribute("fullName", fullName);
		request.setAttribute("password", password);
		request.setAttribute("roleId", roleId);
		request.setAttribute("message", "*Email đã tồn tại, vui lòng nhập email khác");
		listInfo(request,response);
	}
	
}


private void listInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		
		List<Authorization> listAuths = authorizationDAO.getAllAuthorization();
		List<User> listUsers = userDAO.getAllUsers();
		request.setAttribute("listAuths", listAuths);
		request.setAttribute("listUsers", listUsers);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/Authorization.jsp");
		dispatcher.forward(request, response);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String action = request.getParameter("ACTION");
			if(action==null) action = "LIST";
			
			switch(action) {
			case "ADD_USER":
				addUser(request,response);
				break;
			case "UPDATE_ROLE":
				updateRole(request,response);
				break;
			case "UPDATE_USER":
				updateUser(request,response);
				break;
			case "DELETE_USER":
				deleteUser(request,response);
				break;
			}	
			
			listInfo(request,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	
	}

}
