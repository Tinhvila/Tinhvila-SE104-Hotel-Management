<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kala Hotel | Báo cáo doanh thu</title>

    <!-- Bootstrap => css -->
    <link rel="stylesheet" href="./css/bootstrap.min.css">
    <link rel="stylesheet" href="./css/style.css">

    <!-- line icon => css -->
    <link href="https://cdn.lineicons.com/4.0/lineicons.css" rel="stylesheet" />


    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link
        href="https://fonts.googleapis.com/css2?family=IM+Fell+DW+Pica:ital@0;1&family=Lora:ital,wght@0,400..700;1,400..700&display=swap"
        rel="stylesheet">
    <!-- custom => css-->

</head>

<body>
    <div class="wrapper">
        <aside id="sidebar" class="expand vh-100 sticky-top">
            <div class="d-flex">
                <button class="toggle-btn" type="button">
                    <i class="lni lni-grid-alt"></i>
                </button>
                <div class="sidebar-logo">
                    <a href="<%=request.getContextPath()%>/room-category">Kala Hotel</a>
                </div>
            </div>
            <ul class="sidebar-nav">
                <c:forEach var="auth" items="${sessionScope.listAuths}">
	           	<c:if test="${auth.authorizationId == sessionScope.user.authorizationID && auth.roomCategoryScreen == 1}">
	                <li class="sidebar-item">
	                    <a href="<%=request.getContextPath()%>/room-category" class="sidebar-link ">
	                        <i class="lni lni-tab"></i>
	                        <span>Danh mục phòng</span>
	                    </a>
	                </li>
	           	</c:if>
	           	
	           	<c:if test="${auth.authorizationId == sessionScope.user.authorizationID && auth.billForRentScreen == 1}">
	                <li class="sidebar-item">
	                    <a href="<%=request.getContextPath()%>/bill-for-rent" class="sidebar-link">
	                        <i class="lni lni-agenda"></i>
	                        <span>Phiếu thuê phòng</span>
	                    </a>
                	</li>
	           	</c:if>
	           	
	           	<c:if test="${auth.authorizationId == sessionScope.user.authorizationID && auth.searchScreen == 1}">
	                 <li class="sidebar-item">
                    <a href="<%=request.getContextPath()%>/search" class="sidebar-link">
                        <i class="lni lni-search-alt"></i>
                        <span>Tra cứu phòng</span>
                    </a>
                </li>
	           	</c:if>
	           	
	           	<c:if test="${auth.authorizationId == sessionScope.user.authorizationID && auth.recieptScreen == 1}">
	               <li class="sidebar-item">
                    <a href="<%=request.getContextPath()%>/reciept" class="sidebar-link">
                        <i class="lni lni-postcard"></i>
                        <span>Hóa đơn thanh toán</span>
                    </a>
                </li>
	           	</c:if>
	       	    	
	           	<c:if test="${auth.authorizationId == sessionScope.user.authorizationID && auth.revenueScreen == 1}">
	            <li class="sidebar-item">
                    <a href="<%=request.getContextPath()%>/revenue" class="sidebar-link active">
                        <i class="lni lni-target-revenue"></i>
                        <span>Báo cáo doanh thu</span>
                    </a>
                </li>
	           	</c:if>
	           	
	           	<c:if test="${auth.authorizationId == sessionScope.user.authorizationID && auth.authorizationScreen == 1}">
	            <li class="sidebar-item">
                    <a href="<%=request.getContextPath()%>/authorization" class="sidebar-link ">
                        <i class="lni lni-users"></i>
                        <span>Phân quyền tài khoản</span>
                    </a>
                </li>
	           	</c:if>
	           	
	           		<c:if test="${auth.authorizationId == sessionScope.user.authorizationID && auth.settingScreen == 1}">
	            <li class="sidebar-item">
                    <a href="<%=request.getContextPath()%>/setting" class="sidebar-link">
                        <i class="lni lni-cogs"></i>
                        <span>Quy định</span>
                    </a>
                </li>
	           	</c:if>
	           	
            </c:forEach>
                
            </ul>
            <div class="sidebar-footer">
                <a href="<%=request.getContextPath()%>/logout" class="sidebar-link">
                    <i class="lni lni-exit"></i>
                    <span>Đăng xuất</span>
                </a>
            </div>
        </aside>

        <div class="main p-3 bg-white-1">
            <div
                class="shadowCustom overflow-hidden rounded d-flex justify-content-between align-items-center mb-4 p-3 bg-white">
                <h1 style="margin: 0;">Báo cáo doanh thu tháng</h1>
                <h1 style="margin: 0;">Xin chào, <c:out value="${sessionScope.user.fullName}" /></h1>
            </div>

            <div class="row mx-0">
                <div id="type-room-revenue" class="rounded shadowCustom bg-white col-6 rounded-3" style="height: 400px;">
                	<div class="p-2 mb-4 d-flex justify-content-between">
                		<span class="fw-bold" style="font-size: 20px;">Thống kê doanh thu theo loại phòng</span>
                		<a href="<%=request.getContextPath()%>/revenue?ACTION=EXPORT" class="btn btn-success"><i class="lni lni-printer"></i></a>
                	</div>
                	<div class="overflow-auto h-75">
	                    <table class="table table-hover">
	                        <thead>
	                            <tr>
	                                <th scope="col">STT</th>
	                                <th scope="col">Tháng</th>
	                                <th scope="col">Năm</th>
	                                <th scope="col">Loại Phòng</th>
	                                <th scope="col">Doanh thu</th>
	                                <th scope="col">Tỉ lệ</th>
	                            </tr>
	                        </thead>
	                        <tbody>
	                        	<%int i = 1;%>
	                        	<c:forEach var="getAllRevenueList" items="${getAllRevenueList}">
	                        		<c:choose>
		                        		<c:when test="${monthResponse == -1 and yearResponse == -1}">
		                        		</c:when>
		                        		<c:otherwise>
				                            <tr>
				                                <th scope="row"><%=i%></th>
				                                <td>${getAllRevenueList.month}</td>
				                                <td>${getAllRevenueList.year}</td>
				                                <td>${getAllRevenueList.typeRoomName}</td>
				                                <td><fmt:formatNumber type="number" groupingUsed="true" value="${getAllRevenueList.revenueValue}" /></td>
				                                <td>${getAllRevenueList.typeRoomRevenueRate}%</td>
				                            </tr>
				                        	<%i++;%>
		                            	</c:otherwise>
	                            	</c:choose>
	                            </c:forEach>
	                        </tbody>
	                    </table>
                    </div>
                </div>
                <div class="col-1"></div>
                <div class="col-5 p-0 d-flex flex-column mh-100">
                    <h5 class="text-center mb-4  bg-white p-2 rounded-3 shadowCustom">Tổng doanh thu: <fmt:formatNumber type="number" groupingUsed="true" value="${requestScope.totalPriceRevenue}" />đ</h5>
                    <form action="<%=request.getContextPath()%>/revenue"
                        class="white bg- p-2 rounded-3 shadowCustom bg-white" method="post">
                        <input type="hidden" name="ACTION" value="SEARCH"/>
                        <div class="mb-3">
                            <label for="label-month" class="form-label">Tháng</label>
                            <select name="month" id="label-month" class="form-select" aria-label="Default select example" required>
                                <option value="-1" <c:if test="${monthResponse == -1}">selected</c:if>>Lựa chọn</option>
                                <c:forEach var="getMonth" items="${getMonth}">
                                	<option value="${getMonth}" <c:if test="${getMonth == monthResponse}">selected</c:if>>${getMonth}</option>
                                </c:forEach>
                            </select>
                            <label for="label-year" class="form-label">Năm</label>
                            <select name="year" id="label-year" class="form-select" aria-label="Default select example" required>
                                <option value="-1" <c:if test="${monthResponse == -1}">selected</c:if>>Lựa chọn</option>
                                <c:forEach var="getYear" items="${getYear}">
                                	<option value="${getYear}" <c:if test="${getYear == yearResponse}">selected</c:if>>${getYear}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <input type="submit" class="btn btn-info  d-block w-100" style="font-weight: bold;"
                            value="Xác nhận">

                    </form>

                </div>
            </div>


        </div>
    </div>
    <script src="./js/script.js"></script>
    <script src="./js/bootstrap.bundle.min.js"></script>
</body>

</html>