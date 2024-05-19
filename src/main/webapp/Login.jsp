<%@page import="com.HotelManagement.Entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <title>Kala Hotel | Đăng nhập</title>
        <link rel="stylesheet" href="./css/login.css">
   		<link rel="stylesheet" href="./css/bootstrap.min.css">
   		
   		<style >
   			<%@ include file="./css/login.css"%>
   		</style>
   		
   		<link rel="preconnect" href="https://fonts.googleapis.com">
		<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
		<link href="https://fonts.googleapis.com/css2?family=Lora:ital,wght@0,400..700;1,400..700&display=swap" rel="stylesheet">
    </head>

    <body>
    
    <%
    	HttpSession _session = request.getSession();
    	User user =(User)_session.getAttribute("user");
    	if (user != null) response.sendRedirect(request.getContextPath() +"/home");
    %>
    
        <div id="contanier" class="container-fluid d-flex justify-content-between align-items-center">

            <div class="col-1"></div>
            <div class="col-7">
                <div class="text text-white">
                    <h5 id="title" class="h2">Kala Hotel</h5>
                    <p class="card-text h5">
                        Cùng khám phá và lưu giữ từng khoảnh khắc trong cuộc sống.
                </div>
            </div>
            <div class="col-4">
                <form action="<%=request.getContextPath()%>/login" method="post" class="rounded px-8 bg-white form"
                    style="width: 300px;">
                    <div id="titleLogin" class="h1 text-center">Đăng nhập</div>
                    <input type="hidden" name="ACTION" value="login">
                    
                    <p id="message">
                    <c:if  test="${not empty message}" >
                    	${message}
                    </c:if>
                    </p>
                    <div id="emailGroup" class="form-group ">
                        <label for="exampleInputEmail1">Địa chỉ email</label>
                        <input type="text" name="email" class="form-control" id="exampleInputEmail1"
                            aria-describedby="emailHelp" placeholder="Nhập email...">
                    </div>

                    <div class="form-group mb-3">
                        <label for="exampleInputPassword1">Mật khẩu</label>
                        <input type="password" name="password" class="form-control" id="exampleInputPassword1"
                            placeholder="Nhập mật khẩu...">
                    </div>

                    <div class="form-group d-block text-center">
                        <input type="submit" value="Đăng nhập" class="btn btn-info text-white">
                    </div>
                </form>
            </div>
        </div>
	
	<script src="./js/bootstrap.bundle.min.js"></script>
    </body>

    </html>