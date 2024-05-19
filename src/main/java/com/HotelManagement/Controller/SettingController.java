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

import com.HotelManagement.DAO.ParameterDAO;
import com.HotelManagement.DAO.SurchargeRateDAO;
import com.HotelManagement.DAO.TypeOfCustomerDAO;
import com.HotelManagement.DAO.TypeOfRoomDAO;
import com.HotelManagement.DAO.UserDAO;
import com.HotelManagement.Entity.Parameter;
import com.HotelManagement.Entity.SurchargeRate;
import com.HotelManagement.Entity.TypeCustomer;
import com.HotelManagement.Entity.TypeRoom;
import com.HotelManagement.Entity.User;



@WebServlet("/setting")
public class SettingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final String SCREEN = "QuyenLapQuyDinh";
	
	private TypeOfRoomDAO typeOfRoomDAO;
	private TypeOfCustomerDAO typeOfCustomerDAO;
	private SurchargeRateDAO surchargeRateDAO;
	private ParameterDAO parameterDAO;
	private UserDAO userDAO;
	
	@Resource(name="jdbc/hotel_db")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		try {
			userDAO = new UserDAO(dataSource);
			typeOfRoomDAO = new TypeOfRoomDAO(dataSource);
			typeOfCustomerDAO = new TypeOfCustomerDAO(dataSource);
			surchargeRateDAO = new SurchargeRateDAO(dataSource);
			parameterDAO = new ParameterDAO(dataSource);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String roleGroupId = getRoleGroupOfUser(request);
		
		int permissionFlag = 0;;
		try {
			permissionFlag = getPermission(roleGroupId,SCREEN);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(permissionFlag == 1) {
			try {
				
				String action = request.getParameter("ACTION");
				if(action==null) action = "LIST";
				switch(action) {
				case "ADD_TYPE_ROOM":
					addTypeRoom(request,response);
					break;
				case "UPDATE_TYPE_ROOM":
					updateTypeRoom(request,response);
					break;
				case "DELETE_TYPE_ROOM":
					deleteTypeRoom(request,response);
					break;
				case "ADD_TYPE_CUSTOMER":
					addTypeCustomer(request,response);
					break;
				case "UPDATE_TYPE_CUSTOMER":
					updateTypeCustomer(request,response);
					break;
				case "DELETE_TYPE_CUSTOMER":
					deleteTypeCustomer(request,response);
					break;
				case "ADD_SURCHARGE_RATE":
					addSurchargeRate(request,response);
					break;
				case "UPDATE_SURCHARGE_RATE":
					updateSurchargeRate(request,response);
					break;
				case "DELETE_SURCHARGE_RATE":
					deleteSurchargeRate(request,response);
					break;
				case "UPDATE_PARAM":
					updateParameter(request,response);
					break;
				case "LIST":				
					listInfoRegulation(request,response);
					break;
				}	
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
		
		else {
			response.sendRedirect(request.getContextPath() + "/error");
		}
		
	}
	
	
private int getPermission(String roleGroupId, String _AUTHENTICATION_SCREEN) throws SQLException {
		
		return userDAO.getPermission(roleGroupId,_AUTHENTICATION_SCREEN);
	}

private String getRoleGroupOfUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		return user.getAuthorizationID();
	}


	
private void updateParameter(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		String papramName = request.getParameter("papramName");
		String paramValue = request.getParameter("paramValue");
		
		Parameter par = new Parameter(papramName, Float.valueOf(paramValue));
		parameterDAO.updateParam(par);
 	
		response.sendRedirect(request.getContextPath() + "/setting");
}

	private void deleteSurchargeRate(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		int orderOfCustomer = Integer.valueOf(request.getParameter("orderOfCustomer")); 
		
		surchargeRateDAO.deleteSurCharge(orderOfCustomer);
		response.sendRedirect(request.getContextPath() + "/setting");
	}

	private void updateSurchargeRate(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		String orderOfCustomer = request.getParameter("orderOfCustomer");
		String value = request.getParameter("surchargeRate");
		SurchargeRate sur = new SurchargeRate(Integer.valueOf(orderOfCustomer),Float.valueOf(value)); 
		
		surchargeRateDAO.updateSurchargeRate(sur);
		response.sendRedirect(request.getContextPath() + "/setting");
	}

	private void addSurchargeRate(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		String orderOfCustomer = request.getParameter("orderOfCustumer");
		String value = request.getParameter("surchargeRate");
		SurchargeRate sur = new SurchargeRate(Integer.valueOf(orderOfCustomer),Float.valueOf(value)); 
		
		surchargeRateDAO.addSurchargeRate(sur);
		
		response.sendRedirect(request.getContextPath() + "/setting");
	}

	private void deleteTypeCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		
		String typeCustomerId = request.getParameter("typeCustomerId");
		typeOfCustomerDAO.deleteTypeCustomer(typeCustomerId);
		
		response.sendRedirect(request.getContextPath() + "/setting");
	}

	private void updateTypeCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		String typeCustomerName = request.getParameter("typeCustomerName");
		String typeCustomerChargeRate = request.getParameter("typeCustomerChargeRate");
		String typeCustomerId = request.getParameter("typeCustomerId");
		
		TypeCustomer typeCustomer = new TypeCustomer(typeCustomerId, typeCustomerName, Float.valueOf(typeCustomerChargeRate));
		typeOfCustomerDAO.updateTypeCustomer(typeCustomer);
		response.sendRedirect(request.getContextPath() + "/setting");
	}

	private void addTypeCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
		String typeCustomerName = request.getParameter("typeCustomerName");
		String typeCustomerChargeRate = request.getParameter("typeCustomerChargeRate");
		
		typeOfCustomerDAO.addTypeCustomer(typeCustomerName,typeCustomerChargeRate);
		response.sendRedirect(request.getContextPath() + "/setting");
	}

	

	private void deleteTypeRoom(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String typeRoomId = request.getParameter("typeRoomId");
		typeOfRoomDAO.deleteTypeRoom(typeRoomId);
		response.sendRedirect(request.getContextPath() + "/setting");
	}

	private void updateTypeRoom(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String typeRoomId = request.getParameter("typeRoomId");
		String typeRoomName = request.getParameter("typeRoomName");
		String typeRoomPrice = request.getParameter("typeRoomPrice");
		
		TypeRoom typeRoom = new TypeRoom(typeRoomId,typeRoomName,Integer.valueOf(typeRoomPrice));
		typeOfRoomDAO.updateTypeRoom(typeRoom);
		response.sendRedirect(request.getContextPath() + "/setting");
	}

	private void addTypeRoom(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		
		String typeRoomName = request.getParameter("typeRoomName");
		String typeRoomPrice = request.getParameter("typeRoomPrice");
		TypeRoom typeRoom = new TypeRoom(typeRoomName,Integer.valueOf(typeRoomPrice));
		
		typeOfRoomDAO.addTypeRoom(typeRoom);
		response.sendRedirect(request.getContextPath() + "/setting");
		
	}
	
	private void listInfoRegulation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		
		List<TypeRoom> listTypeOfRooms = typeOfRoomDAO.getAllTypeRooms();
		List<TypeCustomer> listTypeOfCustomers = typeOfCustomerDAO.getAllTypeCustomer();
		List<SurchargeRate> listSurcharge = surchargeRateDAO.getAllSurchargeRate();
		List<Parameter> listParams = parameterDAO.getAllSurchargeRate();
		request.setAttribute("listTypeRooms", listTypeOfRooms);
		request.setAttribute("listTypeOfCustomers", listTypeOfCustomers);
		request.setAttribute("listSurcharge", listSurcharge);
		request.setAttribute("listParams", listParams);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/Setting.jsp");
		dispatcher.forward(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
try {
			
			String action = request.getParameter("ACTION");
			if(action==null) action = "LIST";
			
			switch(action) {
			case "ADD_TYPE_ROOM":
				addTypeRoom(request,response);
				break;
			case "UPDATE_TYPE_ROOM":
				updateTypeRoom(request,response);
				break;
			case "DELETE_TYPE_ROOM":
				deleteTypeRoom(request,response);
				break;
			case "ADD_TYPE_CUSTOMER":
				addTypeCustomer(request,response);
				break;
			case "UPDATE_TYPE_CUSTOMER":
				updateTypeCustomer(request,response);
				break;
			case "DELETE_TYPE_CUSTOMER":
				deleteTypeCustomer(request,response);
				break;
			case "ADD_SURCHARGE_RATE":
				addSurchargeRate(request,response);
				break;
			case "UPDATE_SURCHARGE_RATE":
				updateSurchargeRate(request,response);
				break;
			case "DELETE_SURCHARGE_RATE":
				deleteSurchargeRate(request,response);
				break;
			case "UPDATE_PARAM":
				updateParameter(request,response);
				break;
			}	
			
			listInfoRegulation(request,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

}
