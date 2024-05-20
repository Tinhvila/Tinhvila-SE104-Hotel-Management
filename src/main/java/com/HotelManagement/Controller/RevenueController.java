package com.HotelManagement.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;

import java.util.List;
import java.util.Locale;

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


import com.HotelManagement.DAO.RevenueDAO;
import com.HotelManagement.DAO.UserDAO;
import com.HotelManagement.Entity.Revenue;
import com.HotelManagement.Entity.User;

/**
 * Servlet implementation class RevenueController
 */
@WebServlet("/revenue")
public class RevenueController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private RevenueDAO revenueDAO;
    private UserDAO userDAO;
    private final String SCREEN = "QuyenLapBaoCaoDoanhThu";
    
    
    @Resource(name="jdbc/hotel_db")
	private DataSource dataSource;
    
    @Override
	public void init() throws ServletException {
		super.init();
		try {
			userDAO = new UserDAO(dataSource);
			revenueDAO = new RevenueDAO(dataSource);
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
			case "LIST":
				searchRevenue(request, response);
				break;
			case "SEARCH":
				searchRevenue(request, response);
				break;
			case "EXPORT":
				exportList(request,response);
			}
			listAll(request,response);
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

	
	private void exportList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=Báo cáo doanh thu.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("Báo cáo doanh thu");
		
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		List<Revenue> listRevenue = (List<Revenue>) session.getAttribute("getAllRevenueList");
//		Create excel file
//		1. Header
		
		int rowNo = 0;
		XSSFRow row = sheet.createRow(rowNo++);
		
		int cellNum = 0;
		XSSFCell cell = row.createCell(cellNum++);
		cell.setCellValue("STT");
		
		cell = row.createCell(cellNum++);
		cell.setCellValue("Tháng");
		
		cell = row.createCell(cellNum++);
		cell.setCellValue("Năm");
		
		cell = row.createCell(cellNum++);
		cell.setCellValue("Loại phòng");
		
		cell = row.createCell(cellNum++);
		cell.setCellValue("Doanh thu");
		
		cell = row.createCell(cellNum++);
		cell.setCellValue("Tỉ lệ");
		
//		2 details
		int i = 1;
		for (Revenue revenue : listRevenue) {
			cellNum = 0;
			row = sheet.createRow(rowNo++);
			
			cell = row.createCell(cellNum++);
			cell.setCellValue(i++);
			
			cell = row.createCell(cellNum++);
			cell.setCellValue(revenue.getMonth());
			
			cell = row.createCell(cellNum++);
			cell.setCellValue(revenue.getYear());
			
			cell = row.createCell(cellNum++);
			cell.setCellValue(revenue.getTypeRoomName());
		
			cell = row.createCell(cellNum++);
			cell.setCellValue(revenue.getRevenueValue());
			
			cell = row.createCell(cellNum++);
			cell.setCellValue(revenue.getTypeRoomRevenueRate());
		
		}
		
//		End creat excel
		wb.write(response.getOutputStream());
		wb.close();
		return;
	}



	private void listAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		List<Integer> getMonth = revenueDAO.getMonth();
		List<Integer> getYear = revenueDAO.getYear();
		
		request.setAttribute("getMonth", getMonth);
		request.setAttribute("getYear", getYear);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/Revenue.jsp");
		dispatcher.forward(request, response);
	}
	
	private void searchRevenue(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		HttpSession session = request.getSession();
		
		String month = request.getParameter("month");
		String year = request.getParameter("year");
		if(month == null) month = "-1";
		if(year == null) year = "-1";
		
		List<Revenue> listRevenue = revenueDAO.getRevenueList(Integer.parseInt(month), Integer.parseInt(year));
		double totalPriceRevenue = 0;
		if(month != "-1" && year != "-1")
			for(int i = 0; i < listRevenue.size(); i++) {
				totalPriceRevenue = totalPriceRevenue + listRevenue.get(i).getRevenueValue();
			}
		Locale locale = new Locale("vi", "VN");
		NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
		request.setAttribute("totalPriceRevenue", numberFormat.format(totalPriceRevenue));
		request.setAttribute("getAllRevenueList", listRevenue);
		session.setAttribute("getAllRevenueList", listRevenue);
		request.setAttribute("monthResponse", month);
		request.setAttribute("yearResponse", year);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String action = request.getParameter("ACTION");
			if(action==null) action = "LIST";
			
			switch(action) {
			case "LIST":
				searchRevenue(request, response);
				break;
			case "SEARCH":
				searchRevenue(request, response);
				break;
			}
			listAll(request,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
