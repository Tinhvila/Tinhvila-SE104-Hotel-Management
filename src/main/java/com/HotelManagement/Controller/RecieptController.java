package com.HotelManagement.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
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

import com.HotelManagement.DAO.Customer_RoomBillDetailDAO;
import com.HotelManagement.DAO.ReceiptDAO;
import com.HotelManagement.DAO.ReceiptDetailDAO;
import com.HotelManagement.DAO.RoomBillDAO;
import com.HotelManagement.DAO.RoomCategoryDAO;
import com.HotelManagement.DAO.TypeOfCustomerDAO;
import com.HotelManagement.DAO.UserDAO;
import com.HotelManagement.Entity.CountCustomerRoomBill;
import com.HotelManagement.Entity.CountReceiptDetail;
import com.HotelManagement.Entity.Customer_RoomBillDetail;
import com.HotelManagement.Entity.Receipt;
import com.HotelManagement.Entity.ReceiptDetail;
import com.HotelManagement.Entity.Room;
import com.HotelManagement.Entity.RoomBill;
import com.HotelManagement.Entity.TypeCustomer;
import com.HotelManagement.Entity.User;


@WebServlet("/reciept")
public class RecieptController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ReceiptDAO receiptDAO;
    private RoomBillDAO roomBillDAO;
    private Customer_RoomBillDetailDAO customer_RoomBillDetailDAO;
    private TypeOfCustomerDAO typeOfCustomerDAO;
    private ReceiptDetailDAO receiptDetailDAO;
    private UserDAO userDAO;
    private final String SCREEN = "QuyenLapHoaDonThanhToan";
	
    
    @Resource(name="jdbc/hotel_db")
	private DataSource dataSource;
    
    @Override
	public void init() throws ServletException {
		super.init();
		try {
			userDAO = new UserDAO(dataSource);
			roomBillDAO = new RoomBillDAO(dataSource);
			receiptDAO = new ReceiptDAO(dataSource);
			customer_RoomBillDetailDAO = new Customer_RoomBillDetailDAO(dataSource);
			typeOfCustomerDAO = new TypeOfCustomerDAO(dataSource);
			receiptDetailDAO = new ReceiptDetailDAO(dataSource);
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
			case "INSERT_RECEIPT":
				insertReceipt(request, response);
				break;
			case "DELETE_RECEIPT":
				deleteReceipt(request, response);
				break;
			case "INSERT_ROOMBILLRECEIPT":
				insertRoomBillReceipt(request, response);
				break;
			case "DELETE_ROOMBILLRECEIPT":
				deleteRoomBillReceipt(request, response);
				break;
			case "UPDATE_RECEIPT":
				updateReceipt(request, response);
				break;
			case "LIST":
				listAll(request, response);
				break;
			}
			//listAll(request,response);
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


	
	private void listAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		List<RoomBill> listAllRoomBills = roomBillDAO.getAllRoomBill();
		List<Customer_RoomBillDetail> listCustomer = customer_RoomBillDetailDAO.getCustomerInRoomBill();
		List<TypeCustomer> listTypeCustomer = typeOfCustomerDAO.getAllTypeCustomer();
		Integer getMaxCustomerConstraint = customer_RoomBillDetailDAO.getMaxCustomerConstraint();
		List<CountCustomerRoomBill> listCountCustomer = customer_RoomBillDetailDAO.getCurrentCustomerInRoomBillId();
		List<Receipt> listAllReceipt = receiptDAO.getAllReceipt();
		List<RoomBill> listUnpaidRoomBills = roomBillDAO.getUnpaidRoomBill();
		List<ReceiptDetail> listReceiptDetail = receiptDetailDAO.getAllReceiptDetail();
		List<Receipt> listReceiptTotalValue = receiptDAO.getAllTotalValue();
		List<CountReceiptDetail> listCountReceiptDetail = receiptDetailDAO.countReceiptDetail();
		
		request.setAttribute("listAllRoomBills", listAllRoomBills);
		request.setAttribute("listCustomer", listCustomer);
		request.setAttribute("listTypeCustomer", listTypeCustomer);
		request.setAttribute("listCountCustomer", listCountCustomer);
		request.setAttribute("getMaxCustomerConstraint", getMaxCustomerConstraint);
		request.setAttribute("listAllReceipt", listAllReceipt);
		request.setAttribute("listUnpaidRoomBills", listUnpaidRoomBills);
		request.setAttribute("listReceiptDetail", listReceiptDetail);
		request.setAttribute("listReceiptTotalValue", listReceiptTotalValue);
		request.setAttribute("listCountReceiptDetail", listCountReceiptDetail);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/Reciept.jsp");
		dispatcher.forward(request, response);
	}
	
	private void insertReceipt(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String receiptCustomerName = request.getParameter("receiptCustomerName");
		Receipt rcp = new Receipt();
		rcp.setReceiptCustomerName(receiptCustomerName);
		rcp.setReceiptPrice(0);
		
		receiptDAO.insertReceipt(rcp);
		
		response.sendRedirect(request.getContextPath() + "/reciept");
	}
	
	private void deleteReceipt(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String receiptId = request.getParameter("receiptId");
		receiptDAO.deleteReceipt(receiptId);
		
		response.sendRedirect(request.getContextPath() + "/reciept");
	}
	
	private void updateReceipt(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		Receipt getReceipt = new Receipt();
		getReceipt.setReceiptId(request.getParameter("receiptId"));
		getReceipt.setReceiptCustomerName(request.getParameter("receiptCustomerName"));
		getReceipt.setReceiptDayCreated(request.getParameter("receiptDayCreated"));
		
		receiptDAO.updateReceipt(getReceipt);
		receiptDAO.insertRevenue(getReceipt);
		response.sendRedirect(request.getContextPath() + "/reciept");
	}
	
	private void insertRoomBillReceipt(HttpServletRequest request, HttpServletResponse response) throws SQLException, ParseException, IOException {
		String receiptId = request.getParameter("receiptId");
		String roomBillId = request.getParameter("roomBillId");
		
		receiptDetailDAO.insertReceiptDetail(receiptId, roomBillId);
		response.sendRedirect(request.getContextPath() + "/reciept");
	}
	
	private void deleteRoomBillReceipt(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String receiptDetailId = request.getParameter("receiptDetailId");
		String roomBillId = request.getParameter("roomBillId");
		
		receiptDetailDAO.deleteReceiptDetail(receiptDetailId, roomBillId);
		response.sendRedirect(request.getContextPath() + "/reciept");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
