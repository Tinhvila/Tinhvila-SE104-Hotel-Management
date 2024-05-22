<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kala Hotel | Phân quyền</title>

    <!-- Bootstrap => css -->
    <link rel="stylesheet" href="./css/bootstrap.min.css">
    <link rel="stylesheet" href="./css/style.css">

    <!-- line icon => css -->
    <link href="https://cdn.lineicons.com/4.0/lineicons.css" rel="stylesheet" />


    <link rel="preconnect" href="https://fonts.googleapis.com">
		<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
		<link href="https://fonts.googleapis.com/css2?family=Lora:ital,wght@0,400..700;1,400..700&display=swap" rel="stylesheet">
    <!-- custom => css-->

</head>

<body>
<% int i = 1; int j = 1; %>
    <div class="wrapper position-relative">
        <aside id="sidebar" class="expand vh-100 sticky-top">
            <div class="d-flex">
                <button class="toggle-btn" type="button">
                    <i class="lni lni-grid-alt"></i>
                </button>
                <div class="sidebar-logo">
                    <a href="<%=request.getContextPath()%>/home">Kala Hotel</a>
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
                    <a href="<%=request.getContextPath()%>/revenue" class="sidebar-link">
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
                    <a href="<%=request.getContextPath()%>/setting" class="sidebar-link active">
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
                <h1 style="margin: 0;">Quy định</h1>
                <h1 style="margin: 0;">Xin chào, <c:out value="${sessionScope.user.fullName}" /></h1>
            </div>
	<c:if test="${not empty message_error}">
	                    <div class="alert alert-danger alert-dismissible fw-bold text-danger fade show" role="alert">
						  ${message_error}
						  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
						</div>
                    </c:if>
    
	                    
            <div class="row mx-0 mb-3">
                <div class="col-6 p-0">
                    <div id="room-category"
                        class="position-relative authorization-table-1 rounded overflow-auto shadowCustom bg-white rounded-3 mb-3">
                        <h1 class="text-center py-2">Danh sách loại phòng</h1>
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th scope="col">STT</th>
                                    <th scope="col">Loại phòng</th>
                                    <th scope="col">Đơn giá</th>
                                    <th scope="col">Thao thác</th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="typeRoom" items="${listTypeRooms}">
                            <tr>
                                    <th scope="row"><%= i++ %></th>
                                    <td>${typeRoom.nameTypeRoom}</td>
                                    <td>${typeRoom.price}</td>
                                    <td><button data-bs-toggle="modal" data-bs-target="#<%=i%>updateModalTypeRoom" id="fix-btn"
                                            class="btn btn-info"><i class="lni lni-pencil-alt"></i></button> |
                                        <button data-bs-toggle="modal" data-bs-target="#<%=i%>deleteModalTypeRoom"
                                            class="btn btn-danger"><i class="lni lni-trash-can"></i></i></button>
                                    </td>
                                </tr>
                            
                            <div class="modal fade" id="<%=i%>updateModalTypeRoom" tabindex="-1"
                                    aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h1 class="modal-title fs-5" id="exampleModalLabel">Sửa thông tin loại phòng
                                                    ${typeRoom.nameTypeRoom}</h1>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                    aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <form action="<%=request.getContextPath()%>/setting" method="get">
                                                	<input type="hidden" name="ACTION" value="UPDATE_TYPE_ROOM" />
                                                	<input type="hidden" name="typeRoomId" value="${typeRoom.typeRoomID}" />
                                                    <div class="mb-3">
                                                        <label for="recipient-name" class="col-form-label">Loại
                                                            phòng:</label>
                                                        <input type="text" class="form-control" readonly name="typeRoomName" id="recipient-name" value="${typeRoom.nameTypeRoom}">
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="recipient-name" class="col-form-label">Đơn
                                                            giá:</label>
                                                        <input type="number" class="form-control" name="typeRoomPrice" id="recipient-name" value="${typeRoom.price}">
                                                    </div>
                                                    
                                            <div class="modal-footer p-0">
                                                <button type="button" class="btn btn-secondary"
                                                    data-bs-dismiss="modal">Đóng</button>
                                                <button type="submit" class="btn btn-info fw-bold">Cập nhật</button>
                                            </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            
                            
                            <div class="modal fade" id="<%=i%>deleteModalTypeRoom" tabindex="-1"
                                    aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h1 class="modal-title fs-5" id="exampleModalLabel">Xóa phòng</h1>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                    aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                Bạn có chắc chắn muốn xóa loại phòng ${typeRoom.nameTypeRoom} ?
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary"
                                                    data-bs-dismiss="modal">Đóng</button>
                                                <a href="<%=request.getContextPath()%>/setting?ACTION=DELETE_TYPE_ROOM&typeRoomId=${typeRoom.typeRoomID}"
                                                    class="btn btn-danger">Xóa</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            
                            
                            </c:forEach>
                                
                            </tbody>
                        
                        </table >
                        <div > 
                        <button data-bs-toggle="modal" data-bs-target="#addModalTypeRoom" id="fix-btn"
                                            class="btn btn-info mb-3 mx-3 fw-bold">Thêm loại phòng</button>
                        </div>
                        
                        <div class="modal fade" id="addModalTypeRoom" tabindex="-1"
                                    aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h1 class="modal-title fs-5" id="exampleModalLabel">Thêm loại phòng</h1>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                    aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <form action="<%=request.getContextPath()%>/setting" method="post">
                                                <input type="hidden" name="ACTION" value="ADD_TYPE_ROOM" />
                                                    <div class="mb-3">
                                                        <label for="recipient-name"  class="col-form-label">Loại
                                                            phòng:</label>
                                                        <input type="text" class="form-control" name="typeRoomName" id="recipient-name" value="${typeRoom.nameTypeRoom}">
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="recipient-name" class="col-form-label">Đơn
                                                            giá:</label>
                                                        <input type="number" class="form-control" name="typeRoomPrice" id="recipient-name" value="${typeRoom.price}">
                                                    </div>
                                                    
                                            <div class="modal-footer p-0">
                                                <button type="button" class="btn btn-secondary"
                                                    data-bs-dismiss="modal">Đóng</button>
                                                <button type="submit" class="btn btn-info fw-bold">Thêm</button>
                                            </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                    </div>
                </div>

                <div class="col-1"></div>

                <div class="col-5 p-0">
                    <div id="room-category"
                        class="position-relative authorization-table-1 rounded overflow-auto shadowCustom bg-white rounded-3 mb-3">
                        <h1 class="text-center py-2">Danh sách loại khách</h1>
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th scope="col">STT</th>
                                    <th scope="col">Loại khách</th>
                                    <th scope="col">Tỉ lệ phụ thu</th>
                                    <th scope="col">Thao thác</th>
                                </tr>
                            </thead>
                            <tbody>
                            
                            <c:forEach var="typeCustomer" items="${listTypeOfCustomers}">
								<tr>
                                    <th scope="row"><%=j++%></th>
                                    <td>${typeCustomer.typeCustomerName}</td>
                                    <td>${typeCustomer.typeCustomerChargeRate}</td>
                                    <td><button data-bs-toggle="modal" data-bs-target="#<%=j%>updateModalTypeCustomer" id="fix-btn"
                                            class="btn btn-info"><i class="lni lni-pencil-alt"></i></button> |
                                        <button data-bs-toggle="modal" data-bs-target="#<%=j%>deleteModalTypeCustomer"
                                            class="btn btn-danger"><i class="lni lni-trash-can"></i></i></button>
                                    </td>
                                </tr>
								    
								   <div class="modal fade" id="<%=j%>updateModalTypeCustomer" tabindex="-1"
                                    aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h1 class="modal-title fs-5" id="exampleModalLabel">Sửa thông tin loại khách 
                                                    ${typeCustomer.typeCustomerName}</h1>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                    aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <form action="<%=request.getContextPath()%>/setting" method="get">
                                                    <div class="mb-3">
                                                        <label for="recipient-name" class="col-form-label">Loại
                                                            khách:</label>
                                                        <input type="text" class="form-control" readonly  name="typeCustomerName" id="recipient-name" value="${typeCustomer.typeCustomerName}">
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="recipient-name" class="col-form-label">Tỉ lệ phụ thu:</label>
                                                        <input type="number" min="0" max="10" step="0.01" class="form-control" name="typeCustomerChargeRate" id="recipient-name" value="${typeCustomer.typeCustomerChargeRate}">
                                                    </div>
                                                    <input type="hidden" name="typeCustomerId" value="${typeCustomer.typeCustomerId}" />
                                                    <input type="hidden" name="ACTION" value="UPDATE_TYPE_CUSTOMER" />
                                            <div class="modal-footer p-0">
                                                <button type="button" class="btn btn-secondary"
                                                    data-bs-dismiss="modal">Đóng</button>
                                                <button type="submit" class="btn btn-info fw-bold">Cập nhật</button>
                                            </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
								    
								     <div class="modal fade" id="<%=j%>deleteModalTypeCustomer" tabindex="-1"
                                    aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h1 class="modal-title fs-5" id="exampleModalLabel">Xóa loại khách</h1>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                    aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                Bạn có chắc chắn muốn xóa loại khách ${typeCustomer.typeCustomerName}?
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary"
                                                    data-bs-dismiss="modal">Đóng</button>
                                                <a href="<%=request.getContextPath()%>/setting?ACTION=DELETE_TYPE_CUSTOMER&typeCustomerId=${typeCustomer.typeCustomerId}"
                                                    class="btn btn-danger">Xóa</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
								    
								                            
                            </c:forEach>

                            </tbody>
                        </table>
                        
                         <div > 
                        <button data-bs-toggle="modal" data-bs-target="#addModalTypeCustomer" id="fix-btn"
                                            class="btn btn-info mb-3 mx-3 fw-bold">Thêm loại khách</button>
                        </div>
                        
                        <div class="modal fade" id="addModalTypeCustomer" tabindex="-1"
                                    aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h1 class="modal-title fs-5" id="exampleModalLabel">Thêm loại khách</h1>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                    aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <form action="<%=request.getContextPath()%>/setting" method="get">
                                                    <input type="hidden" name="ACTION" value="ADD_TYPE_CUSTOMER" />
                                                    <div class="mb-3">
                                                        <label for="recipient-name" class="col-form-label">Loại
                                                            khách:</label>
                                                        <input type="text" name="typeCustomerName" class="form-control" id="recipient-name" >
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="recipient-name" class="col-form-label">Tỉ lệ phụ thu</label>
                                                        <input type="number" min="0" max="10" step="0.01" name="typeCustomerChargeRate" class="form-control" id="recipient-name" >
                                                    </div>
                                                    
                                            <div class="modal-footer p-0">
                                                <button type="button" class="btn btn-secondary"
                                                    data-bs-dismiss="modal">Đóng</button>
                                                <button type="submit" class="btn btn-info fw-bold">Thêm</button>
                                            </div>
                                            
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            
                    </div>
                </div>


            </div>
            
            
            

 			<div class="row mx-0">
                <div class="col-6 p-0">
                    <div id="room-category"
                        class="position-relative authorization-table-1 rounded overflow-auto shadowCustom bg-white rounded-3 mb-3">
                        <h1 class="text-center py-2">Tỉ lệ phụ thu</h1>
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th scope="col">Khách thứ</th>
                                    <th scope="col">Tỉ lệ</th>
                                    <th scope="col">Thao thác</th>
                                </tr>
                            </thead>
                            <tbody>
                            
                            <c:forEach var="surcharge" items="${listSurcharge}">
                            	
                            	<c:if test="${surcharge.orderOfCustomer <= requestScope.numberOfRecords}">
                            	
                            	<tr>
                            		
                            	
                                    <td>${surcharge.orderOfCustomer}</td>
                                    <td>${surcharge.value}</td>
                                    <td><button data-bs-toggle="modal" data-bs-target="#${surcharge.orderOfCustomer}updateModalSurcharge" id="fix-btn"
                                            class="btn btn-info"><i class="lni lni-pencil-alt"></i></button> 
                                            <!-- |
                                        <button data-bs-toggle="modal" data-bs-target="#${surcharge.orderOfCustomer}deleteModalSurcharge"
                                            class="btn btn-danger"><i class="lni lni-trash-can"></i></i></button> -->
                                    </td>
                                </tr>
                            	</c:if>
                                
                                <div class="modal fade" id="${surcharge.orderOfCustomer}updateModalSurcharge" tabindex="-1"
                                    aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h1 class="modal-title fs-5" id="exampleModalLabel">Sửa thông tin phụ thu khách thứ 
                                                    ${surcharge.orderOfCustomer}</h1>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                    aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <form action="<%=request.getContextPath()%>/setting" method="get">
                                                    <div class="mb-3">
                                                        <label for="recipient-name" class="col-form-label">Tỉ lệ:</label>
                                                        <input type="number" min="0" max="10" step="0.01" class="form-control" name="surchargeRate" id="recipient-name" value="${surcharge.value}">
                                                    </div>
                                                    <input type="hidden" name="orderOfCustomer" value="${surcharge.orderOfCustomer}" />
                                                    <input type="hidden" name="ACTION" value="UPDATE_SURCHARGE_RATE" />
                                            <div class="modal-footer p-0">
                                                <button type="button" class="btn btn-secondary"
                                                    data-bs-dismiss="modal">Đóng</button>
                                                <button type="submit" class="btn btn-info fw-bold">Cập nhật</button>
                                            </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                
                                
                                <div class="modal fade" id="${surcharge.orderOfCustomer}deleteModalSurcharge" tabindex="-1"
                                    aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h1 class="modal-title fs-5" id="exampleModalLabel">Xóa phụ thu</h1>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                    aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                Bạn có chắc chắn muốn xóa tỉ lệ phụ thu khách thứ ${surcharge.orderOfCustomer} ?
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary"
                                                    data-bs-dismiss="modal">Đóng</button>
                                                <a href="<%=request.getContextPath()%>/setting?ACTION=DELETE_SURCHARGE_RATE&orderOfCustomer=${surcharge.orderOfCustomer}"
                                                    class="btn btn-danger">Xóa</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                
                                
                            </c:forEach>


                            </tbody>
                        </table>
                         <div > 
                        <button data-bs-toggle="modal" data-bs-target="#addModalSurcharge" id="fix-btn"
                                            class="btn btn-info mb-3 mx-3 fw-bold">Thêm</button>
                        </div>
                        
                        <div class="modal fade" id="addModalSurcharge" tabindex="-1"
                                    aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h1 class="modal-title fs-5" id="exampleModalLabel">Thêm phụ thu khách
                                                    </h1>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                    aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <form action="<%=request.getContextPath()%>/setting" method="get">
                                                <input type="hidden"  name="ACTION" value="ADD_SURCHARGE_RATE"/>
                                                    <div class="mb-3">
                                                        <label for="recipient-name" class="col-form-label">Khách thứ: </label>
                                                        <input type="number" min="1" max="100" step="1" class="form-control" name="orderOfCustumer" id="recipient-name">
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="recipient-name" class="col-form-label">Tỉ lệ: </label>
                                                        <input type="number" min="0" max="100" step="0.01" class="form-control" name="surchargeRate" id="recipient-name">
                                                    </div>
                                                    
                                            <div class="modal-footer p-0">
                                                <button type="button" class="btn btn-secondary"
                                                    data-bs-dismiss="modal">Đóng</button>
                                                <button type="submit" class="btn btn-info fw-bold">Thêm</button>
                                            </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                        
                        
                    </div>
                </div>

                <div class="col-1"></div>

                <div class="col-5 p-0">
                    <div id="room-category"
                        class="position-relative authorization-table-1 rounded overflow-auto shadowCustom bg-white rounded-3 mb-3">
                        <h1 class="text-center py-2">Tham số</h1>
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th scope="col">Tham số</th>
                                    <th scope="col">Giá trị</th>
                                    <th scope="col">Thao thác</th>
                                </tr>
                            </thead>
                            <tbody>
                            
                            <c:forEach var="para" items="${listParams}">
                            <tr>
                                    <td>${para.paramName}</td>
                                    <td>${para.paramValue}</td>
                                    <td><button data-bs-toggle="modal" data-bs-target="#${para.paramName}updateModalParam" id="fix-btn"
                                            class="btn btn-info"><i class="lni lni-pencil-alt"></i></button>
                                       
                                    </td>
                                </tr>

                                <div class="modal fade" id="${para.paramName}updateModalParam" tabindex="-1"
                                    aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h1 class="modal-title fs-5" id="exampleModalLabel">Sửa thông tin tham số</h1>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                    aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <form action="<%=request.getContextPath()%>/setting" method="get">
                                                <input type="hidden"  name="ACTION" value="UPDATE_PARAM"/>
                                                    <div class="mb-3">
                                                        <label for="recipient-name" class="col-form-label">Tên tham số:</label>
                                                        <input type="text" class="form-control" name="papramName" id="recipient-name" value="${para.paramName}" readonly>
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="recipient-name" class="col-form-label">Giá trị:</label>
                                                        <input type="number" min="1" step="1" class="form-control" id="recipient-name" name="paramValue" value="${para.paramValue}">
                                                    </div>
                                                    
                                            <div class="modal-footer p-0">
                                                <button type="button" class="btn btn-secondary"
                                                    data-bs-dismiss="modal">Đóng</button>
                                                <button type="submit" class="btn btn-info fw-bold">Cập nhật</button>
                                            </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            
                            </c:forEach>
                            
                                
                            </tbody>
                        </table>
                    </div>
                </div>


            </div>



        </div>
    </div>
    
    <script src="./js/script.js"></script>
    <script src="./js/bootstrap.bundle.min.js"></script>
    
</body>

</html>