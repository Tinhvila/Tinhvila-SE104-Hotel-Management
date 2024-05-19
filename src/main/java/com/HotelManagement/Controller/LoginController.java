package com.HotelManagement.Controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.HotelManagement.DAO.UserDAO;
import com.HotelManagement.Entity.User;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	private UserDAO userDAO;
	
	@Resource(name="jdbc/hotel_db")
	private DataSource dataSource;
	
	
	
   	@Override
	public void init() throws ServletException {
		super.init();
		try {
			userDAO = new UserDAO(dataSource);			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/Login.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		try {
			User u = userDAO.getUser(email, password);
			if(u != null) {
				HttpSession session = request.getSession();
				session.setAttribute("user", u);
				response.sendRedirect(request.getContextPath() + "/home");
				}
			else {
				String message = "*Tài khoản hoặc mật khẩu không đúng!";
				request.setAttribute("message", message);
				request.getRequestDispatcher("/Login.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}

}
