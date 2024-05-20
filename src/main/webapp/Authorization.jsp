<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@page import="com.HotelManagement.Entity.User"%>
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
    
    <style >
   			<%@ include file="./css/style.css"%>
   		</style>

    <!-- line icon => css -->
    <link href="https://cdn.lineicons.com/4.0/lineicons.css" rel="stylesheet" />
	<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" rel="stylesheet">
   		

    <link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Lora:ital,wght@0,400..700;1,400..700&display=swap" rel="stylesheet">
    

</head>

<body>

	<%int i = 0;%>
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
                    <a href="<%=request.getContextPath()%>/authorization" class="sidebar-link active">
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
                <h1 style="margin: 0;">Quy định</h1>
                <h1 style="margin: 0;">Xin chào, <c:out value="${sessionScope.user.fullName}" /></h1>
            </div>

            <div class="row mx-0 mb-4">
                <div id="room-category"
                    class="position-relative authorization-table rounded overflow-auto shadowCustom bg-white rounded-3 mb-3">
                    <table class="table table-hover position-sticky sticky-top top-0">
                        <thead>
                            <tr>
                                <th scope="col">Nhóm quyền</th>
                                <th scope="col">Danh mục phòng</th>
                                <th scope="col">Lập phiếu thuê</th>
                                <th scope="col">Tra cứu</th>
                                <th scope="col">Lập hóa đơn</th>
                                <th scope="col">Xem doanh thu</th>
                                <th scope="col">Phân quyền</th>
                                <th scope="col">Quy định</th>
                                <th scope="col">Thao thác</th>
                            </tr>
                        </thead>
                        <tbody>
                        
                        <c:forEach var="auth" items="${listAuths}">
                        <%i++;%>
                        	<tr>
                                <th scope="row">${auth.authorizationName}</th>
                                
                                <td>
                               <c:choose>
								  <c:when test="${auth.roomCategoryScreen == 1}">
								    <img src="./img/check.png" width="20px" alt="">
								  </c:when>
								  <c:otherwise>
								    <img src="./img/uncheck.png" width="20px" alt="">
								  </c:otherwise>
								</c:choose>
                                </td>
                                
                                <td>
                                <c:choose>
								  <c:when test="${auth.billForRentScreen == 1}">
								    <img src="./img/check.png" width="20px" alt="">
								  </c:when>
								  <c:otherwise>
								    <img src="./img/uncheck.png" width="20px" alt="">
								  </c:otherwise>
								</c:choose>
                                </td>
                                
                                <td>
                                <c:choose>
								  <c:when test="${auth.searchScreen == 1}">
								    <img src="./img/check.png" width="20px" alt="">
								  </c:when>
								  <c:otherwise>
								    <img src="./img/uncheck.png" width="20px" alt="">
								  </c:otherwise>
								</c:choose>
                                </td>
                                
                                <td>
                                <c:choose>
								  <c:when test="${auth.recieptScreen == 1}">
								    <img src="./img/check.png" width="20px" alt="">
								  </c:when>
								  <c:otherwise>
								    <img src="./img/uncheck.png" width="20px" alt="">
								  </c:otherwise>
								</c:choose>
                                </td>
                                
                                <td>
                                <c:choose>
								  <c:when test="${auth.revenueScreen == 1}">
								    <img src="./img/check.png" width="20px" alt="">
								  </c:when>
								  <c:otherwise>
								    <img src="./img/uncheck.png" width="20px" alt="">
								  </c:otherwise>
								</c:choose>
                                </td>
                                
                                <td>
                                <c:choose>
								  <c:when test="${auth.authorizationScreen == 1}">
								    <img src="./img/check.png" width="20px" alt="">
								  </c:when>
								  <c:otherwise>
								    <img src="./img/uncheck.png" width="20px" alt="">
								  </c:otherwise>
								</c:choose>
                                </td>
                                
                                <td>
                                <c:choose>
								  <c:when test="${auth.settingScreen == 1}">
								    <img src="./img/check.png" width="20px" alt="">
								  </c:when>
								  <c:otherwise>
								    <img src="./img/uncheck.png" width="20px" alt="">
								  </c:otherwise>
								</c:choose>
                                </td>
                                
                                <td><button data-bs-toggle="modal" data-bs-target="#${auth.authorizationId}updateAuthorization" id="fix-btn"
                                        class="btn btn-info"><i class="lni lni-pencil-alt"></i></button> 
                                </td>
                            </tr>
                        
                        <div class="modal fade" id="${auth.authorizationId}updateAuthorization" tabindex="-1" aria-labelledby="exampleModalLabel"
                                aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h1 class="modal-title fs-5" id="exampleModalLabel">Sửa thông tin phân quyền
                                                </h1>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <form action="<%=request.getContextPath()%>/authorization" method="post">
                                            	
                                            	<input type="hidden" name="ACTION"  value="UPDATE_ROLE"/>
                                            	<input type="hidden" name="roleID"  value="${auth.authorizationId}"/>
                                            	<input type="hidden" name="roleName"  value="${auth.authorizationName}"/>
                                            		
                                                <div class="mb-3">
                                                    <label for="recipient-name" class="col-form-label fw-bold pt-0">Nhóm quyền:</label>
                                                    <input type="text" value="${auth.authorizationName}" readonly class="form-control" id="recipient-name">
                                                </div>
                                                
                                                <div class="form-check form-switch mb-3 p-0 d-flex justify-content-between align-center">
                                                <label class="form-check-label fw-bold" for="roomCategoryScreen_<%=i%>">Màn hình danh mục phòng: </label>
												  <input class="form-check-input" type="checkbox" name="roomCategoryScreen" role="switch" id="roomCategoryScreen_<%=i%>" 
												  <c:if test="${auth.roomCategoryScreen == 1}">
												  checked
												  </c:if>
												  >
												</div>
												
												<div class="form-check form-switch mb-3 p-0 d-flex justify-content-between align-center">
                                                <label class="form-check-label fw-bold" for="billForRentScreen_<%=i%>">Màn hình lập phiếu thuê phòng: </label>
												  <input class="form-check-input" type="checkbox" name="billForRentScreen" role="switch" id="billForRentScreen_<%=i%>" 
												  <c:if test="${auth.billForRentScreen == 1}">
												  checked
												  </c:if>
												  >
												</div>
												
												<div class="form-check form-switch mb-3 p-0 d-flex justify-content-between align-center">
                                                <label class="form-check-label fw-bold" for="searchScreen_<%=i%>">Màn hình tra cứu: </label>
												  <input class="form-check-input" type="checkbox" name="searchScreen"  role="switch" id="searchScreen_<%=i%>" 
												  <c:if test="${auth.searchScreen == 1}">
												  checked
												  </c:if>
												  >
												</div>
												
												<div class="form-check form-switch mb-3 p-0 d-flex justify-content-between align-center">
                                                <label class="form-check-label fw-bold" for="recieptScreen_<%=i%>">Màn hình thanh toán: </label>
												  <input class="form-check-input" type="checkbox" name="recieptScreen" role="switch" id="recieptScreen_<%=i%>" 
												  <c:if test="${auth.recieptScreen == 1}">
												  checked
												  </c:if>
												  >
												</div>
												
												<div class="form-check form-switch mb-3 p-0 d-flex justify-content-between align-center">
                                                <label class="form-check-label fw-bold" for="revenueScreen_<%=i%>">Màn hình doanh thu: </label>
												  <input class="form-check-input" type="checkbox" name="revenueScreen" role="switch" id="revenueScreen_<%=i%>" 
												  <c:if test="${auth.revenueScreen == 1}">
												  checked
												  </c:if>
												  >
												</div>
												
												<div class="form-check form-switch mb-3 p-0 d-flex justify-content-between align-center">
                                                <label class="form-check-label fw-bold" for="authorizationScreen_<%=i%>">Màn hình phân quyền: </label>
												  <input class="form-check-input" name="authorizationScreen" 
												  
												   <c:if test="${auth.authorizationScreen == 1}">
												  checked
												  </c:if> 
												  
												  <c:if test="${auth.authorizationId == 'PHANQUYEN1'}">
												  onclick="return false;" style="opacity: 0.7;"
												  </c:if>
												  
												  												  
												    type="checkbox" role="switch" id="authorizationScreen_<%=i%>" 
												  
												  >
												</div>
												
												<div class="form-check form-switch mb-3 p-0 d-flex justify-content-between align-center">
                                                <label class="form-check-label fw-bold" for="settingScreen_<%=i%>">Màn hình quy định: </label>
												  <input class="form-check-input" type="checkbox" name="settingScreen" role="switch" id="settingScreen_<%=i%>" 
												  <c:if test="${auth.settingScreen == 1}">
												  checked
												  </c:if>
												  >
												</div>
                                                
                                        <div class="modal-footer px-0">
                                            <button type="button" class="btn btn-secondary"
                                                data-bs-dismiss="modal">Đóng</button>
                                            <input type="submit" class="btn btn-info fw-bold" value="Cập nhật"/>
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

           
            <div class="row mx-0">
                <div class="col-7  p-0" style="height: 465.99px !important;">
                    <div id="room-category"
                    class="position-relative authorization-table-1 rounded overflow-auto shadowCustom bg-white rounded-3 mb-3">
                    
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th scope="col">Họ tên</th>
                                <th scope="col">Email</th>
                                <th scope="col">Nhóm quyền</th>
                                <th scope="col">Thao thác</th>
                                
                            </tr>
                        </thead>
                        <tbody>
                        
                        <c:forEach var="user" items="${listUsers}">
                        	<tr>
                                <th scope="row">${user.fullName}</th>
                                <td>
                                ${user.userName}
                                </td>
                                <td>
                                <c:if test="${user.authorizationID == 'PHANQUYEN1'}">
                                Siêu quản trị
                                </c:if>
                                <c:if test="${user.authorizationID == 'PHANQUYEN2'}">
                                Giám đốc
                                </c:if>
                                <c:if test="${user.authorizationID == 'PHANQUYEN3'}">
                                Quản lý
                                </c:if>
                                <c:if test="${user.authorizationID == 'PHANQUYEN4'}">
                                Nhân viên
                                </c:if>
                                </td>
                               <td><button data-bs-toggle="modal" data-bs-target="#${user.userName}updateUser
                               
                               <c:if test="${user.userName == 'superadmin@gmail.com'}">
                                    prevent
                                </c:if>"
                               
                                id="fix-btn"
                                        class="btn btn-info"><i class="lni lni-pencil-alt"></i></button> |
                                    <button data-bs-toggle="modal"
                                     data-bs-target="#${user.userName}deleteModal
                                     <c:if test="${user.userName == 'superadmin@gmail.com'}">
                                    	prevent
                                	</c:if>
                                     "
                                        class="btn btn-danger"><i class="lni lni-trash-can"></i></i></button>
                                </td>
                            </tr>
                            
                            <div class="modal fade" id="${user.userName}updateUser" tabindex="-1" aria-labelledby="exampleModalLabel"
                                aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h1 class="modal-title fs-5" id="exampleModalLabel">Sửa thông tin người dùng
                                                ${user.fullName}</h1>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <form action="<%=request.getContextPath()%>/authorization" method="get">
                                                <div class="mb-3">
                                                    <label for="recipient-name" class="col-form-label">Tên
                                                        người dùng:</label>
                                                    <input type="text" class="form-control" name="fullName" id="recipient-name" value="${user.fullName}">
                                                </div>
                                                <input type="hidden" name="ACTION" value="UPDATE_USER"/>
                                                <div class="mb-3">
                                                    <label for="recipient-name" class="col-form-label">Email:</label>
                                                    <input type="text" class="form-control" name ="userName" id="recipient-name" value="${user.userName}" readonly>
                                                </div>
                                                
                                                <div class="mb-3">
                                                    <label for="RoleGroup" class="col-form-label">Nhóm quyền:</label>
                                                    <select id="RoleGroup" class="form-select" name="roleId">
                                                    <c:forEach var="author" items="${listAuths}">
                                                    	<option value="${author.authorizationId}"
                                                    	 
                                                    	 <c:if  test="${author.authorizationId == user.authorizationID}">
                    										 <c:out value=" selected "/>
                    									</c:if>
                                                    	
                                                    	>${author.authorizationName}</option>
                                                    </c:forEach>
                                                    </select>
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

							<div class="modal fade" id="${user.userName}deleteModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                                aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h1 class="modal-title fs-5" id="exampleModalLabel">Xóa người dùng</h1>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            Bạn có chắc chắn muốn xóa tài khoản có email là ${user.userName}?
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary"
                                                data-bs-dismiss="modal">Đóng</button>
                                            <a href="<%=request.getContextPath()%>/authorization?ACTION=DELETE_USER&email=${user.userName}"
                                                class="btn btn-danger">Xóa</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
							                            
                            
                        </c:forEach>
                        

                        </tbody>
                    </table>
                    </div>
                </div>

                <div class="col-1"></div>

                <div class="col-4 p-0 d-flex flex-column justify-content-between mh-100">
                    <h5 class="text-center mb-2  bg-white p-2 rounded-3 shadowCustom">Thêm tài khoản</h5>
                    <form action="<%=request.getContextPath()%>/authorization"
                    
                        class="white bg- p-2 rounded-3 shadowCustom bg-white" method="get">

						<input type="hidden" name="ACTION" value="ADD_USER"/>                    
                        <div class="mb-3">
                            <label for="exampleInputPassword1" class="form-label">Họ và tên</label>
                            <input type="text" class="form-control" value="${fullName}" name="fullName" id="exampleInputPassword1" required>
                        </div>

                        <div class="mb-3">
                            <label for="exampleInputPassword1" class="form-label">Email</label>
                            <input type="email" class="form-control" value="${userName}" name="userName" id="exampleInputPassword1" required>
                        	<small class="text-danger">${message}</small>
                        </div>

                        <div class="mb-3 position-relative">
                            <label for="exampleInputPassword1" class="form-label">Mật khẩu</label>
                            <input type="password" class="form-control" value="${password}" name="password" id="passwordLogin" required>
                        	<i class="fa-solid fa-eye position-absolute "></i>
                            <i class="fa-solid fa-eye-slash position-absolute "></i>
                        </div>

                        <div class="mb-3">
                            <label for="label-room-category" class="form-label">Nhóm quyền</label>
                            <select id="label-room-category" name="roleId" class="form-select" aria-label="Default select example">
                                <c:forEach var="author" items="${listAuths}">
                                                    	<option value="${author.authorizationId}"
                                                   <c:if test="${roleId == author.authorizationId}]">
                                                   selected
                                                   </c:if>
                                                    	>${author.authorizationName}</option>
                                 </c:forEach>
                            </select>
                        </div>

                        <input type="submit" class="btn btn-info  d-block w-100" style="font-weight: bold;"
                            value="Thêm tài khoản">

                    </form>

                </div>
            </div>




        </div>
    </div>
    
    <script src="./js/script.js"></script>
    <script src="./js/bootstrap.bundle.min.js"></script>
    <script>
    var eye = document.getElementsByClassName("fa-eye")[0];
    var eye_slash = document.getElementsByClassName("fa-eye-slash")[0];

    eye_slash.addEventListener("click", () => {
        eye_slash.style.display = "none";
        eye.style.display = "block";
        passwordLogin.setAttribute("type", "text")
    })

    eye.addEventListener("click", () => {
        eye_slash.style.display = "block";
        eye.style.display = "none";
        passwordLogin.setAttribute("type", "password")
    })

    </script>
</body>

</html>