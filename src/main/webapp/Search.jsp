<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Kala Hotel | Tra cứu phòng</title>

        
        <!-- Bootstrap => css -->
        <!-- <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
 -->
        <!-- line icon => css -->
        <link href="https://cdn.lineicons.com/4.0/lineicons.css" rel="stylesheet" />
        <!-- Bootstrap Icons -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" rel="stylesheet">

        <link rel="preconnect" href="https://fonts.googleapis.com">
		<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
		<link href="https://fonts.googleapis.com/css2?family=Lora:ital,wght@0,400..700;1,400..700&display=swap" rel="stylesheet">
        <!-- custom => css-->
        <link rel="stylesheet" href="./css/bootstrap.min.css">
    	<link rel="stylesheet" href="./css/style.css">

    </head>

    <body>
        
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
                <li class="sidebar-item">
                    <a href="<%=request.getContextPath()%>/room-category" class="sidebar-link">
                        <i class="lni lni-tab"></i>
                        <span>Danh mục phòng</span>
                    </a>
                </li>
                <li class="sidebar-item">
                    <a href="<%=request.getContextPath()%>/bill-for-rent" class="sidebar-link">
                        <i class="lni lni-agenda"></i>
                        <span>Phiếu thuê phòng</span>
                    </a>
                </li>
                <li class="sidebar-item">
                    <a href="<%=request.getContextPath()%>/search" class="sidebar-link active">
                        <i class="lni lni-search-alt"></i>
                        <span>Tra cứu phòng</span>
                    </a>
                </li>
                <li class="sidebar-item">
                    <a href="<%=request.getContextPath()%>/reciept" class="sidebar-link">
                        <i class="lni lni-postcard"></i>
                        <span>Hóa đơn thanh toán</span>
                    </a>
                </li>
                <li class="sidebar-item">
                    <a href="<%=request.getContextPath()%>/revenue" class="sidebar-link">
                        <i class="lni lni-target-revenue"></i>
                        <span>Báo cáo doanh thu</span>
                    </a>
                </li>

                <li class="sidebar-item">
                    <a href="<%=request.getContextPath()%>/authorization" class="sidebar-link ">
                        <i class="lni lni-users"></i>
                        <span>Phân quyền tài khoản</span>
                    </a>
                </li>
                
                <li class="sidebar-item">
                    <a href="<%=request.getContextPath()%>/setting" class="sidebar-link">
                        <i class="lni lni-cogs"></i>
                        <span>Quy định</span>
                    </a>
                </li>

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
                    class="shadowCustom overflow-hidden rounded d-flex justify-content-between align-items-center  p-3 bg-white">
                    <h1 style="margin: 0;">Danh sách phòng</h1>
                </div>
                
                
                <div class="row g-0">

                     <form method="post" action="<%=request.getContextPath()%>/search" class="row gy-2 gx-3 align-items-center">
                <div class="col-auto">
                  <label class="" for="autoSizingInput">Tên phòng</label>
                  <input type="text" name="roomName" value="${roomNameResponse}"  class="form-control" id="autoSizingInput" placeholder="Mã phòng...">
                </div>
                
                <input type="hidden" name="ACTION" value="SEARCH">

                <div class="col-auto">
                    <label class="" for="autoSizingSelect">Loại phòng</label>
                    <select class="form-select" name="typeRoomId" id="autoSizingSelect">
                      <option
                       value="-1"
                       <c:if test="${typeRoomIdResponse == '-1'}">
                      	selected
                      </c:if>
                       
                       >Tất cả</option>
                      <c:forEach var="typeRoom" items="${listTypeRooms}">
                    	<option value="${typeRoom.typeRoomID}"  
                    	<c:if test="${typeRoomIdResponse == typeRoom.typeRoomID}">
                      	selected
                      </c:if>
                    	>${typeRoom.nameTypeRoom}</option>
                    </c:forEach>
                    </select>
                  </div>

                <div class="col-auto">
                  <label class="" for="autoSizingSelect">Đơn giá</label>
                  <select class="form-select" name="price" id="autoSizingSelect">
                    <option value="-1"
                    <c:if test="${priceResponse == '-1'}">
                      	selected
                      </c:if>
                    >Tất cả</option>
                    <c:forEach var="price" items="${listTypeRooms}">
                    	<option value="${price.price}" 
                    	<c:if test="${priceResponse == price.price}">
                      	selected
                      </c:if>
                    	>${price.price}</option>
                    </c:forEach>
                    
                  </select>
                </div>

                <div class="col-auto">
                    <label class="" for="autoSizingSelect">Tình trạng</label>
                    <select class="form-select" name="status" id="autoSizingSelect">
                      <option value="-1" 
                      <c:if test="${statusResponse == '-1'}">
                      	selected
                      </c:if>
                      >Tất cả</option>
                      <option value="0"
                      <c:if test="${statusResponse == '0'}">
                      	selected
                      </c:if>
                      >Trống</option>
                      <option value="1"
                      <c:if test="${statusResponse == '1'}">
                      	selected
                      </c:if>
                      >Đã thuê</option>
                    </select>
                  </div>

                <div class="col-auto d-flex flex-column">
                    <label class=" h-25" for="submit" style="opacity: 0;">Submit</label>
                  <button type="submit" class="btn btn-info fw-bold align-self-end" id="submit">Tìm kiếm</button>
                </div>
              </form>
            
            </div>

                
                
                
                
                
                <div class="row mx-0 mt-3">
                    <div id="room-search" class=" h-full rounded overflow-auto shadowCustom bg-white rounded-3 ">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th scope="col">STT</th>
                                    <th scope="col">Phòng</th>
                                    <th scope="col">Loại Phòng</th>
                                    <th scope="col">Đơn giá</th>
                                    <th scope="col">Tình trạng</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% int i = 1; %>
                                <c:forEach items="${listRooms}" var="room" >
                                    <tr>
                                        <td class="fw-bold"><%=i++%></td>
                                        
                                        <td>${room.roomName}</td>
                                        <td>${room.typeOfRoom}</td>
                                        <td>${room.priceRoom}</td>
                                        <td>
                                        	<c:if test="${room.stateRoom == 0}">
                                        		<span class="text-success fw-bold"><i class="lni lni-checkmark" style="color: green; font-weight: bold;"></i> Trống</span>
                                        	</c:if>
                                        	
                                        	<c:if test="${room.stateRoom == 1}">
                                        		<span class="text-danger fw-bold"><i class="lni lni-close" style="color: red; font-weight: bold;"></i> Đã thuê</span>
                                        	</c:if>
                                        </td>
                                        
                                        
                                    </tr> 
                                    
                                </c:forEach>
                        
                            </tbody>
                            
                        </table>
                    </div>
                </div>
            
            
            </div>
        </div>
       
        <script src="./js/script.js"></script>
    <script src="./js/bootstrap.bundle.min.js"></script>
    </body>

</html>