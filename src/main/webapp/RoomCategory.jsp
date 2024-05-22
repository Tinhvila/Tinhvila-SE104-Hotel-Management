<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kala Hotel | Danh mục phòng</title>

    <!-- Bootstrap => css -->
    
    <link rel="stylesheet" href="./css/bootstrap.min.css">

    <!-- line icon => css -->
    <link href="https://cdn.lineicons.com/4.0/lineicons.css" rel="stylesheet" />


    <link rel="preconnect" href="https://fonts.googleapis.com">
		<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
		<link href="https://fonts.googleapis.com/css2?family=Lora:ital,wght@0,400..700;1,400..700&display=swap" rel="stylesheet">
    <!-- custom => css-->
    <link rel="stylesheet" href="./css/style.css">

</head>

<body>
	<% int i = 1; %>
    <div class="wrapper">
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
	                    <a href="<%=request.getContextPath()%>/room-category" class="sidebar-link active">
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
                <h1 style="margin: 0;">Danh mục phòng</h1>
                <h1 style="margin: 0;">Xin chào, <c:out value="${sessionScope.user.fullName}" /></h1>
            </div>
            
            <c:if test="${not empty message_delete_deny}">
	                    <div class="alert alert-danger alert-dismissible fw-bold text-danger fade show " role="alert">
						  ${message_delete_deny}
						  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
						</div>
                    </c:if>

            <div class="row mx-0">
                <div id="room-category" class=" h-full rounded overflow-auto shadowCustom  bg-white col-8 rounded-3 ">
                    <table class="table table-hover">
                    
                    
                    <thead>
                            <tr>
                                <th scope="col">STT</th>
                                <th scope="col">Phòng</th>
                                <th scope="col">Loại Phòng</th>
                                <th scope="col">Đơn giá</th>
                                <th scope="col">Ghi chú</th>
                                <th scope="col">Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="room" items="${listRooms}">
                  
                        <tr>
                                <th scope="row"><%= i++ %></th>
                                <td>${room.roomName}</td>
                                <td>${room.typeOfRoom}</td>
                                <td>${room.priceRoom}</td>
                                <td class="ghi-chu" data-bs-toggle="tooltip"
                                    data-bs-title="${room.noteRoom}">${room.noteRoom}</td>
                                <td><button data-bs-toggle="modal" data-bs-target="#<%=i%>updateModalRoomCategory"
                                        class="btn btn-info"><i class="lni lni-pencil-alt"></i></button> |
                                    <button data-bs-toggle="modal" data-bs-target="#<%=i%>deleteModalRoomCategory"
                                        class="btn btn-danger"><i class="lni lni-trash-can"></i></i></button>
                                </td>
                            </tr>
                            
                            <div class="modal fade" id="<%=i%>deleteModalRoomCategory" tabindex="-1" aria-labelledby="exampleModalLabel"
                                aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h1 class="modal-title fs-5" id="exampleModalLabel">Xóa phòng</h1>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            Bạn có chắc chắn muốn xóa phòng ${room.roomName} ?
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary"
                                                data-bs-dismiss="modal">Đóng</button>
                                            <a href="<%=request.getContextPath()%>/room-category?ACTION=DELETE&roomId=${room.roomId}"
                                                class="btn btn-danger">Xóa</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            
                            <div class="modal fade" id="<%=i%>updateModalRoomCategory" tabindex="-1" aria-labelledby="exampleModalLabel"
                                aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h1 class="modal-title fs-5" id="exampleModalLabel">Sửa thông tin phòng
                                                ${room.roomName}</h1>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <form action="<%=request.getContextPath()%>/room-category">
                                            <input type="hidden" name="ACTION" value="UPDATE"/>
                                            <input type="hidden" name="roomId" value="${room.roomId}"/>
                                                <div class="mb-3">
                                                    <label for="recipient-name" class="col-form-label">Tên phòng: </label>
                                                    <input type="text" class="form-control" name="nameRoom" value="${room.roomName}">
                                                </div>
                                                <div class="mb-3">
                                                    <label for="kind-priceRoom" class="col-form-label">Loại phòng:</label>
                                                    <select id="kind-priceRoom" class="form-select" name="typeRoom">
                                                    <c:forEach var="typeRoom" items="${listTypeRooms}">
                                                    	<option value="${typeRoom.typeRoomID}"
                                                    	 
                                                    	 <c:if  test="${typeRoom.nameTypeRoom == room.typeOfRoom}">
                    										 <c:out value=" selected "/>
                    									</c:if>
                                                    	
                                                    	>${typeRoom.nameTypeRoom} - ${typeRoom.price}</option>
                                                    </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="mb-3">
                                                    <label for="message-text" class="col-form-label">Ghi chú</label>
                                                    <textarea class="form-control" name="noteRoom" id="message-text">${room.noteRoom}</textarea>
                                                </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary"
                                                data-bs-dismiss="modal">Đóng</button>
                                            <input type="submit" class="btn btn-info" value="Cập nhật"/>
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
                <div class="col-1"></div>
                <div class="col-3 p-0 d-flex flex-column justify-content-between mh-100">
                    <h5 class="text-center mb-2  bg-white p-2 rounded-3 shadowCustom">Thêm phòng mới</h5>
                    <form action="<%=request.getContextPath()%>/room-category"
                        class="white bg- p-2 rounded-3 shadowCustom bg-white" method="get">
						
						<input type="hidden" name="ACTION" value="ADD"/>

                        <div class="mb-3">
                            <label for="exampleInputPassword1" class="form-label">Tên phòng</label>
                            <input type="text" required class="form-control" name="nameRoom" id="exampleInputPassword1">
                        </div>

                        <div class="mb-3">
                            <label for="label-room-category" class="form-label">Loại phòng</label>
                            <select id="label-room-category" name="typeRoom" class="form-select" aria-label="Default select example">

                                <c:forEach var="typeRoom" items="${listTypeRooms}">
                                
                                                    	<option value="${typeRoom.typeRoomID}"
                                                    	 
                                                    	 
                                                    	
                                                    	>${typeRoom.nameTypeRoom} - ${typeRoom.price}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label for="exampleInputPassword1" class="form-label">Ghi chú</label>
                            <textarea class="w-100" name="noteRoom" id="" rows="8"></textarea>
                        </div>

                        <input type="submit" class="btn btn-info  d-block w-100" style="font-weight: bold;"
                            value="Thêm">

                    </form>

                </div>
            </div>


        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
    <script src="./js/script.js"></script>
    <script src="./js/bootstrap.bundle.min.js"></script>
</body>

</html>