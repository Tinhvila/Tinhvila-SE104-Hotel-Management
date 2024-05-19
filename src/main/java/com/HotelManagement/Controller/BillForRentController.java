package com.HotelManagement.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.HotelManagement.DAO.TypeOfCustomerDAO;
import com.HotelManagement.Entity.CountCustomerRoomBill;
import com.HotelManagement.Entity.Customer_RoomBillDetail;
//import com.HotelManagement.DAO.TypeOfRoomDAO;
import com.HotelManagement.Entity.Room;
import com.HotelManagement.DAO.Customer_RoomBillDetailDAO;
//import com.HotelManagement.Entity.TypeRoom;
import com.HotelManagement.DAO.RoomBillDAO;
import com.HotelManagement.Entity.RoomBill;
import com.HotelManagement.Entity.TypeCustomer;

@WebServlet("/bill-for-rent")
public class BillForRentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RoomCategoryDAO roomDAO;
	//private TypeOfRoomDAO typeOfRoomDAO;
	private RoomBillDAO roomBillDAO;
	private TypeOfCustomerDAO typeOfCustomerDAO;
	private Customer_RoomBillDetailDAO customer_RoomBillDetailDAO;

	@Resource(name="jdbc/hotel_db")
	private DataSource dataSource;
	
	
	@Override
	public void init() throws ServletException {
		super.init();
		try {
			roomDAO = new RoomCategoryDAO(dataSource);	
			roomBillDAO = new RoomBillDAO(dataSource);
			typeOfCustomerDAO = new TypeOfCustomerDAO(dataSource);
			customer_RoomBillDetailDAO = new Customer_RoomBillDetailDAO(dataSource);
			//typeOfRoomDAO = new TypeOfRoomDAO(dataSource);
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
			case "INSERT_ROOMBILL":
				insertRoomBill(request, response);
				break;
			case "DELETE_ROOMBILL":
				deleteRoomBill(request, response);
				break;
			case "UPDATE_ROOMBILL":
				updateRoomBill(request, response);
				break;
			case "INSERT_CUSTOMER":
				insertCustomer(request, response);
				break;
			case "UPDATE_CUSTOMER":
				updateCustomer(request, response);
				break;
			case "DELETE_CUSTOMER":
				deleteCustomer(request, response);
				break;
			case "LIST":
				listRooms(request, response);
				break;
			}
			

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void listRooms(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		
		List<Room> listRooms = roomDAO.getAllRooms();
		List<Room> listAvailableRooms = roomDAO.getAvailableRooms();
		List<RoomBill> listAllRoomBills = roomBillDAO.getAllRoomBill();
		List<TypeCustomer> listTypeCustomer = typeOfCustomerDAO.getAllTypeCustomer();
		List<Customer_RoomBillDetail> listCustomer = customer_RoomBillDetailDAO.getCustomerInRoomBill();
		Integer getMaxCustomerConstraint = customer_RoomBillDetailDAO.getMaxCustomerConstraint();
		List<CountCustomerRoomBill> listCountCustomer = customer_RoomBillDetailDAO.getCurrentCustomerInRoomBillId();
		
		request.setAttribute("listRooms", listRooms);
		request.setAttribute("listAvailableRooms", listAvailableRooms);
		request.setAttribute("listAllRoomBills", listAllRoomBills);
		request.setAttribute("listTypeCustomer",listTypeCustomer);
		request.setAttribute("listCustomer", listCustomer);
		request.setAttribute("listCountCustomer", listCountCustomer);
		
		request.setAttribute("getMaxCustomerConstraint", getMaxCustomerConstraint);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/BillForRent.jsp");
		dispatcher.forward(request, response);
	}
	
	private void insertRoomBill(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String roomName = request.getParameter("roomName");
		String roomDateRent = request.getParameter("roomDateRent");
		String roomDateReturn = request.getParameter("roomDateReturn");
		String roomId = roomBillDAO.getRoomId(roomName);
		
		RoomBill roomBill = new RoomBill(roomId, roomDateRent);
		roomBill.setRoomDateReturn(roomDateReturn);
		
		roomBillDAO.insertRoomBill(roomBill);
		response.sendRedirect(request.getContextPath() + "/bill-for-rent");
	}
	
	private void deleteRoomBill(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String roomBillId = request.getParameter("roomBillId");
		String roomId = request.getParameter("roomId");
		
		RoomBill roomBill = new RoomBill();
		roomBill.setRoomBillId(roomBillId);
		roomBill.setRoomId(roomId);
		
		roomBillDAO.deleteRoomBill(roomBill);
		response.sendRedirect(request.getContextPath() + "/bill-for-rent");
	}

	private void updateRoomBill(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String roomBillId = request.getParameter("roomBillId");
		String roomIdNew = request.getParameter("newRoomId");
		String roomIdOld = request.getParameter("oldRoomId");
		String roomDateRent = request.getParameter("roomDateRent");
		String roomDateReturn = request.getParameter("roomDateReturn");
		
		RoomBill roomBill = new RoomBill();
		roomBill.setRoomBillId(roomBillId);
		roomBill.setRoomId(roomIdNew);
		roomBill.setRoomDateRent(roomDateRent);
		roomBill.setRoomDateReturn(roomDateReturn);
		
		roomBillDAO.updateRoomBill(roomBill, roomIdOld);
		roomBillDAO.updatePriceRoom_RoomBill(roomBill);
		response.sendRedirect(request.getContextPath() + "/bill-for-rent");
	}
	
	private void insertCustomer(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String roomBillId = request.getParameter("roomBillId");
		String customerName = request.getParameter("customerName");
		String customerAddress = request.getParameter("customerAddress");
		String customerIdentityCode = request.getParameter("customerIdentityCode");
		String customerType = request.getParameter("typeCustomer");
		
		Customer_RoomBillDetail cs = new Customer_RoomBillDetail();
		cs.setCustomerName(customerName);
		cs.setCustomerAddress(customerAddress);
		cs.setCustomerIdentityCode(customerIdentityCode);
		cs.setTypeCustomerId(customerType);
		cs.setRoomBillId(roomBillId);

		
		customer_RoomBillDetailDAO.insertCustomer(cs);
		
		RoomBill rbUpdate = new RoomBill();
		rbUpdate.setRoomBillId(roomBillId);
		roomBillDAO.updatePriceRoom_RoomBill(rbUpdate);
		response.sendRedirect(request.getContextPath() + "/bill-for-rent");
		
	}
	
	private void updateCustomer(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String customerId = request.getParameter("customerId");
		String customerName = request.getParameter("customerName");
		String customerAddress = request.getParameter("customerAddress");
		String customerIdentityCode = request.getParameter("customerIdentityCode");
		String typeCustomerId = request.getParameter("typeCustomerId");
		String roomBillId = request.getParameter("roomBillId");
		
		Customer_RoomBillDetail getValue = new Customer_RoomBillDetail();
		getValue.setCustomerId(customerId);
		getValue.setCustomerName(customerName);
		getValue.setCustomerAddress(customerAddress);
		getValue.setCustomerIdentityCode(customerIdentityCode);
		getValue.setTypeCustomerId(typeCustomerId);
		
		customer_RoomBillDetailDAO.updateCustomer(getValue);
		
		RoomBill rbUpdate = new RoomBill();
		rbUpdate.setRoomBillId(roomBillId);
		roomBillDAO.updatePriceRoom_RoomBill(rbUpdate);
		response.sendRedirect(request.getContextPath() + "/bill-for-rent");
	}
	
	private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String customerId = request.getParameter("customerId");
		String roomBillId = request.getParameter("roomBillId");
		
		Customer_RoomBillDetail getValue = new Customer_RoomBillDetail();
		getValue.setCustomerId(customerId);
		
		
		
		customer_RoomBillDetailDAO.deleteCustomer(getValue);
		
		RoomBill rbUpdate = new RoomBill();
		rbUpdate.setRoomBillId(roomBillId);
		roomBillDAO.updatePriceRoom_RoomBill(rbUpdate);
		response.sendRedirect(request.getContextPath() + "/bill-for-rent");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
