<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kala Hotel | Phiếu thuê phòng</title>

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
    <link rel="stylesheet" href="style.css">

</head>

<body>
	<%int i = 1; %>
	<%int j = 1; %>
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
                <li class="sidebar-item">
                    <a href="<%=request.getContextPath()%>/room-category" class="sidebar-link">
                        <i class="lni lni-tab"></i>
                        <span>Danh mục phòng</span>
                    </a>
                </li>
                <li class="sidebar-item">
                    <a href="<%=request.getContextPath()%>/bill-for-rent" class="sidebar-link active">
                        <i class="lni lni-agenda"></i>
                        <span>Phiếu thuê phòng</span>
                    </a>
                </li>
                <li class="sidebar-item">
                    <a href="<%=request.getContextPath()%>/search" class="sidebar-link">
                        <i class="lni lni-search-alt"></i>
                        <span>Tra cứu phòng</span>
                    </a>
                </li>
                <li class="sidebar-item">
                    <a href="<%=request.getContextPath()%>/reciept" class="sidebar-link ">
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
                class="shadowCustom overflow-hidden rounded d-flex justify-content-between align-items-center mb-4 p-3 bg-white">
                <h1 style="margin: 0;">Phiếu thuê phòng</h1>
                <h1 style="margin: 0;">Xin chào, Siêu quản trị</h1>
            </div>

            <div class="container">
                <div class="row">
                    <div class="col-7 h-100 mx-0 px-0">
                        <div id="bill-rent-setting" class="h-100 rounded overflow-auto shadowCustom bg-white rounded-3 px-1">
                            <h1 class="p-2" style="font-size: 20px;">Thông tin phiếu thuê phòng</h1>

                            <form action="<%=request.getContextPath()%>/bill-for-rent" onsubmit="return checkSubmit();">
                            
                            	<input type="hidden" name="ACTION" value="INSERT_ROOMBILL"/>
                            
                                <div class="mx-3">
                                    <label for="label-room-category" class="form-label">Phòng</label>
                            		<select id="label-room-category" name="roomName" class="form-select" aria-label="Default select example" required>
	                                	<c:forEach var="room" items="${listAvailableRooms}">
	                                		<option value="${room.roomName}">${room.roomName}
	                                		</option>
	                                	</c:forEach>
                            		</select>

                                </div>
                                <div class="mx-3">
                                    <label for="dateRentTextField" class="form-label">Ngày thuê</label>
                                    <input type="datetime-local" class="form-control" name="roomDateRent" id="dateRentTextField" required>
                                </div>
                                <div class="mx-3">
                                    <label for="dateReturnTextField" class="form-label">Ngày kết thúc</label>
                                    <input type="datetime-local" class="form-control" name="roomDateReturn" id="dateReturnTextField" required>
                                </div>
                                    <input type="submit" value="Thêm phiếu thuê" class="btn btn-info d-block m-3">
                                <script>
	                        		var input1 = document.getElementById("dateRentTextField").value;
	                        		var input2 = document.getElementById("dateReturnTextField").value;
	                        		var date1 = new Date(input1);
		                        	var date2 = new Date(input2);
	                                document.getElementById("dateRentTextField").addEventListener("change", function() {
	                        		    input1 = this.value;
	                        		    delete date1;
	                        		    date1 = new Date(input1);
	                        		});
	                        		document.getElementById("dateReturnTextField").addEventListener("change", function() {
	                        		    input2 = this.value;
	                        		    delete date2;
	                        		    date2 = new Date(input2);
	                        		});
	                               	function checkSubmit(){
	                               		console.log(date1 + "\n" + date2)
	                               		if(date2.getTime() - date1.getTime() <= 0){	
	                               			alert("Không thể thêm phiếu thuê phòng do ngày kết thúc nhỏ hơn ngày bắt đầu.");
	                               			return false;
	                               		}
	                               		return true;
	                               	}
	                               </script>
                            </form>
                     	</div>   

                     	<div id="bill-rent-display" class="rounded overflow-auto shadowCustom bg-white rounded-3 px-1 mt-3">
                     		<h1 class="p-2" style="font-size: 20px;">Danh sách phiếu thuê phòng</h1>
                     		<div style="height: 200px; overflow: auto;">
	                    		<table class="table table-hover">
	                    			<thead>
	                    				<tr>
	                     				<th scope="col">STT</th>
	                     				<th scope="col">Phòng</th>
	                     				<th scope="col">Ngày thuê</th>
	                     				<th scope="col">Ngày trả</th>
	                     				<th scope="col">Tình trạng</th>
	                     				<th scope="col">Thao tác</th>
	                     			</tr>
	                    			</thead>
	                    			
	                    			<tbody>
	                    				<c:forEach var="roomBill" items="${listAllRoomBills}">
	                     				<tr>
		                     				<th scope="row"><%=j%></th>
		                     				<td>${roomBill.roomName}</td>
		                     				<td>${roomBill.roomDateRent}</td>
		                     				<td>${roomBill.roomDateReturn}</td>
		                     				<td>
		                     					<c:choose>
														<c:when test="${roomBill.roomPaymentStatus==0}">
															<i class="lni lni-close" style="color: red; font-weight: bold;"></i> Chưa thanh toán
														</c:when>
														<c:when test="${roomBill.roomPaymentStatus==-1}">
															<i class="lni lni-timer" style="color: orange; font-weight: bold;"></i> Chờ thanh toán
														</c:when>
														<c:otherwise>
																<i class="lni lni-checkmark" style="color: green; font-weight: bold;"></i> Đã thanh toán
														</c:otherwise>
													</c:choose>
		                     				</td>
		                     				<td>
		                     					<button data-bs-toggle="modal" data-bs-target="#<%=j%>selectBillRoomCategory"
		                     					class="btn btn-secondary"><i class="lni lni-information"></i>
		                     					</button> 
		                     					<c:if test="${roomBill.roomPaymentStatus==0}">|
				                     			<button  
				                     			data-bs-toggle="modal" data-bs-target="#<%=j%>updateBillRoomCategory"
				                                class="btn btn-info"><i class="lni lni-pencil-alt"></i></button>
			                                   	| <button data-bs-toggle="modal" data-bs-target="#<%=j%>deleteBillRoomCategory"
			                                    class="btn btn-danger"><i class="lni lni-trash-can"></i></button>  
		                                        </c:if>
		                                        <div class="modal fade" id="<%=j%>deleteBillRoomCategory" tabindex="-1" aria-labelledby="deleteBillRoomLabel"
				                                aria-hidden="true">
					                                <div class="modal-dialog">
					                                    <div class="modal-content">
					                                        <div class="modal-header">
					                                            <h1 class="modal-title fs-5" id="deleteBillRoomLabel">Xóa thông tin phiếu thuê phòng</h1>
					                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
					                                                aria-label="Close"></button>
					                                        </div>
					                                        <div class="modal-body">
					                                            <p style="text-align: left">Bạn có chắc chắn muốn xóa phiếu thuê phòng ${roomBill.roomBillId} ?</p>
					                                            <p style="color: red; font-weight:bold; text-align: left;" class="pt-2">Lưu ý: Nếu có khách hàng trong phiếu thuê phòng đó
					                                            thì tất cả các khách hàng cũng sẽ bị xóa theo!</p>
					                                        </div>
					                                        <div class="modal-footer">
					                                            <button type="button" class="btn btn-secondary"
					                                                data-bs-dismiss="modal">Đóng</button>
					                                            <a href="<%=request.getContextPath()%>/bill-for-rent?ACTION=DELETE_ROOMBILL&roomBillId=${roomBill.roomBillId}&roomId=${roomBill.roomId}"
					                                                class="btn btn-danger">Xóa</a>
					                                        </div>
					                                    </div>
					                                </div>
					                            </div>
					                            
					                            <div class="modal fade" id="<%=j %>selectBillRoomCategory" tabindex="-1" aria-labelledby="selectBillRoomLabel"
					                            aria-hidden="true">
					                            	<div class="modal-dialog modal-lg">
					                            		<div class="modal-content">
					                            			<div class="modal-header">
					                            				<h1 class="modal-title fs-5" id="selectBillRoomLabel">Thông tin phiếu thuê phòng</h1>
					                            				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					                            			</div>
					                            			<div class="modal-body">
					                            				<div class="mx-1" style="text-align: left;">
								                                    <label for="label-room-name" class="form-label">Phòng</label>
								                            		<input id="label-room-name" name="roomName" class="form-control" readonly value="${roomBill.roomName}">
																	<label for="label-room-date" class="form-label">Ngày thuê</label>
								                            		<input id="label-room-date" name="roomDateRent" class="form-control" readonly value="${roomBill.roomDateRent}">
																	<label for="label-room-date-return" class="form-label">Ngày kết thúc</label>
																	<input id="label-room-date-return" value="${roomBill.roomDateReturn}" class="form-control" name="roomDateReturn" readonly>    
								                                </div>
								                                <div class="mx-1 my-3" style="text-align: left;">
								                                	<p style="font-size: 20px; font-weight: bold;">Danh sách khách hàng</p>
								                                	<p>Đơn giá 1 ngày dự tính: <span style="color: red; font-weight:bold;">${roomBill.roomPriceDay}đ</span></p>
								                                	<p class="text-end">Số khách:<c:forEach var="listCountCustomer" items="${listCountCustomer}">
											                                <c:if test="${listCountCustomer.roomBillId == roomBill.roomBillId}">
											                                <c:choose>
											                                	<c:when test="${not empty listCountCustomer.countCustomer}">
											                                		${listCountCustomer.countCustomer}
											                                	</c:when>
											                                	<c:otherwise>0</c:otherwise>
											                                	</c:choose>
											                                </c:if>
											                             
											                             </c:forEach>/
											                             <c:out value="${requestScope.getMaxCustomerConstraint}" />
																	</p>
								                                </div>
								                                <div class="mx-3 overflow-auto" style="height: 200px;">
								                                	<table class="table table-hover">
						                            					<thead>
						                            						<tr>
						                            							<th scope="col">STT</th>
						                            							<th scope="col">Họ tên</th>
						                            							<th scope="col">Loại khách</th>
						                            							<th scope="col">Địa chỉ</th>
						                            							<th scope="col">CMND</th>
						                            						</tr>
						                            					</thead>
						                            					<tbody>
						                            					<%int k = 1; %>
					                            						<c:forEach var="listCustomer" items="${listCustomer}">
					                            						<c:if test="${roomBill.roomBillId==listCustomer.roomBillId}">
						                            						<tr>
						                            							<th scope="row"><%=k++%></th>
						                            							<td>${listCustomer.customerName}</td>
						                            							<td>
						                            								<c:forEach var="listTypeCustomer" items="${listTypeCustomer}">
						                            									<c:if test="${listCustomer.typeCustomerId==listTypeCustomer.typeCustomerId}">
						                            										${listTypeCustomer.typeCustomerName}
						                            									</c:if>
						                            								</c:forEach>
						                            							</td>
						                            							<td>${listCustomer.customerAddress }</td>
						                            							<td>${listCustomer.customerIdentityCode}</td>
						                            						</tr>
						                            					</c:if>
					                            						</c:forEach>
						                            					</tbody>
						                            				</table>
								                                </div>
					                            			</div>
					                            			<div class="modal-footer">
					                            				<button type="button" class="btn btn-secondary"
					                                                data-bs-dismiss="modal">Đóng</button>
					                            			</div>
					                            		</div>
					                            	</div>
					                            </div>
					                            
					                            <div class="modal fade" id="<%=j %>updateBillRoomCategory" tabindex="-1" aria-labelledby="updateBillRoomLabel"
					                            aria-hidden="true">
					                            	<div class="modal-dialog modal-lg">
					                            		<div class="modal-content">
					                            			<div class="modal-header">
					                            				<h1 class="modal-title fs-5" id="updateBillRoomLabel">Cập nhật thông tin phiếu thuê phòng ${roomBill.roomBillId}</h1>
					                            				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					                            			</div>
					                            			<div class="modal-body">
					                            				<form action="<%=request.getContextPath()%>/bill-for-rent" onsubmit="return checkSubmitUpdate();">
					                            					<input type="hidden" name="ACTION" value="UPDATE_ROOMBILL"/>
					                            					<input type="hidden" name="roomBillId" value="${roomBill.roomBillId}"/>
					                            					<input type="hidden" name="oldRoomId" value="${roomBill.roomId}"/>
						                            				<div class="mx-1" style="text-align: left;">
									                                    <div class="mx-3">
										                                    <label for="label-room-category" class="form-label">Phòng</label>
										                            		<select id="label-room-category" name="newRoomId" class="form-select" aria-label="Default select example" required>
											                                	<option value="${roomBill.roomId}" selected>${roomBill.roomName}</option>
											                                	<c:forEach var="room" items="${listAvailableRooms}">
											                                		<option value="${room.roomId}">${room.roomName}
											                                		</option>
											                                	</c:forEach>
										                            		</select>
										                                </div>
										                                <div class="mx-3">
										                                    <label for="dateRentTextFieldUpdate" class="form-label">Ngày thuê</label>
										                                    <input type="datetime-local" value="${roomBill.roomDateRent}" class="form-control" name="roomDateRent" id="<%=j%>dateRentTextFieldUpdate" required>
										                                </div>
										                                <div class="mx-3">
											                                <label for="dateReturnTextFieldUpdate" class="form-label">Ngày kết thúc</label>
											                                <input type="datetime-local" value="${roomBill.roomDateReturn}" class="form-control" name="roomDateReturn" id="<%=j%>dateReturnTextFieldUpdate" required>
											                            </div>								                                    
									                                </div>
									                                <div class="mx-3 my-3" style="text-align: left;">
									                                	<p style="font-size: 20px; font-weight: bold;">Danh sách khách hàng</p>
									                                	<p>Đơn giá 1 ngày dự tính: <span style="color: red; font-weight:bold;">${roomBill.roomPriceDay}đ</span></p>
									                                	<div class="d-flex justify-content-between align-center">
										                                	<button type="button" class="btn btn-info" data-bs-dismiss="modal" data-bs-toggle="modal"
										                                	data-bs-target="#<%=j %>insertCustomerCategory">Thêm khách hàng</button>
										                                	<p>Số khách:
										                                	<c:forEach var="listCountCustomer" items="${listCountCustomer}">
												                                <c:if test="${listCountCustomer.roomBillId == roomBill.roomBillId}">
												                                	${listCountCustomer.countCustomer}
												                                </c:if>
												                            </c:forEach>
										                                	/
											                                <c:out value="${requestScope.getMaxCustomerConstraint}" />
										                                	</p>
									                                	</div>
									                                </div>
									                                <div class="mx-3 overflow-auto" style="height: 200px;">
									                                	<table class="table table-hover">
							                            					<thead>
							                            						<tr>
							                            							<th scope="col">STT</th>
							                            							<th scope="col">Họ tên</th>
							                            							<th scope="col">Loại khách</th>
							                            							<th scope="col">Địa chỉ</th>
							                            							<th scope="col">CMND</th>
							                            							<th scope="col">Thao tác</th>
							                            						</tr>
							                            					</thead>
							                            					<tbody>
							                            						<%k = 1; %>
							                            						<c:forEach var="listCustomer" items="${listCustomer}">
							                            						<c:if test="${roomBill.roomBillId==listCustomer.roomBillId}">
								                            						<tr>
								                            							<tr>
									                            							<th scope="row"><%=k%></th>
									                            							<td>${listCustomer.customerName}</td>
									                            							<td>
									                            								<c:forEach var="listTypeCustomer" items="${listTypeCustomer}">
									                            									<c:if test="${listCustomer.typeCustomerId==listTypeCustomer.typeCustomerId}">
									                            										${listTypeCustomer.typeCustomerName}
									                            									</c:if>
									                            								</c:forEach>
									                            							</td>
									                            							<td>${listCustomer.customerAddress }</td>
									                            							<td>${listCustomer.customerIdentityCode}</td>
								                            							<td>
								                            								<button type="button" data-bs-toggle="modal" data-bs-target="#<%=j%><%=k%>updateCustomerCategory"
													                                        class="btn btn-info"><i class="lni lni-pencil-alt"></i></button> |
													                                    	<button type="button" data-bs-toggle="modal" data-bs-target="#<%=j%><%=k%>deleteCustomerCategory"
													                                        class="btn btn-danger"><i class="lni lni-trash-can"></i></button>  
								                            							</td>		
								                            						</tr>
								                            					<%k++;%>
								                            					</c:if>
							                            						</c:forEach>
							                            					</tbody>
							                            				</table>
									                                </div>
									                                <div class="modal-footer">
						                            					<button type="button" class="btn btn-secondary"
						                                                	data-bs-dismiss="modal">Đóng</button>
						                                                <input type="submit" class="btn btn-info" value="Cập nhật"/>
						                                                <script>
											                        		var input1 = document.getElementById("<%=j%>dateRentTextFieldUpdate").value;
											                        		var input2 = document.getElementById("<%=j%>dateReturnTextFieldUpdate").value;
											                        		var date1 = new Date(input1);
												                        	var date2 = new Date(input2);
											                                document.getElementById("<%=j%>dateRentTextFieldUpdate").addEventListener("change", function() {
											                        		    input1 = this.value;
											                        		    delete date1;
											                        		    date1 = new Date(input1);
											                        		});
											                        		document.getElementById("<%=j%>dateReturnTextFieldUpdate").addEventListener("change", function() {
											                        		    input2 = this.value;
											                        		    delete date2;
											                        		    date2 = new Date(input2);
											                        		});
										                                	function checkSubmitUpdate(){
										                                		console.log(date1 + "\n" + date2)
										                                		if(date2.getTime() - date1.getTime() <= 0){	
										                                			alert("Không thể cập nhật phiếu thuê phòng do ngày kết thúc nhỏ hơn ngày bắt đầu.");
										                                			return false;
										                                		}
										                                		return true;
										                                	}
										                                </script>
						                            				</div>
								                                </form>
					                            			</div>
					                            		</div>
					                            	</div>
					                            </div>
					                            
					                            
					                            <%k = 1; %>
					                            <c:forEach var="listCustomer" items="${listCustomer}">
												<c:if test="${roomBill.roomBillId==listCustomer.roomBillId}">
					                            <div class="modal fade" id="<%=j%><%=k%>updateCustomerCategory" tabindex="-1" aria-labelledby="updateCustomerLabel"
						                            aria-hidden="true">
					                            	<div class="modal-dialog">
					                            		<div class="modal-content">
					                            			<div class="modal-header">
					                            				<h1 class="modal-title fs-5" id="updateCustomerLabel">Cập nhật khách hàng ${listCustomer.customerId}</h1>
					                            				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					                            			</div>
					                            			<div class="modal-body">
					                            				<form action="<%=request.getContextPath()%>/bill-for-rent">
					                            					<input type="hidden" name="ACTION" value="UPDATE_CUSTOMER" />
					                            					<input type="hidden" name="roomBillId" value="${roomBill.roomBillId}" />
					                            					<input type="hidden" name="customerId" value="${listCustomer.customerId}" />
						                            				<div class="mx-1" style="text-align: left;">
						                            					<div class="m-3">
						                    								<label for="label-customer-name" class="form-label">Tên khách hàng</label>
									                            			<input id="label-customer-name" name="customerName" class="form-control" value="${listCustomer.customerName}" required>        					
						                            					</div>
									                                    <div class="m-3">
									                                    	<label for="label-customer-address" class="form-label">Địa chỉ</label>
									                            			<input id="label-customer-address" name="customerAddress" class="form-control" value="${listCustomer.customerAddress}" required>
									                                    </div>
									                                    <div class="m-3">
									                                    	<label for="label-customer-identity" class="form-label">CMND</label>
									                            			<input id="label-customer-identity" name="customerIdentityCode" class="form-control" value="${listCustomer.customerIdentityCode}" required>
									                                    </div>
									                                    <div class="m-3">
									              							<label for="kind-customerType" class="col-form-label">Loại khách</label>
						                                                    <select id="kind-customerType" class="form-select" name="typeCustomerId">
						                                                    <c:forEach var="typeCustomer" items="${listTypeCustomer}">
						                                                    	<c:choose>
							                                                    	<c:when test="${listCustomer.typeCustomerId == typeCustomer.typeCustomerId}">
							                                                    		<option value="${typeCustomer.typeCustomerId}" selected>${typeCustomer.typeCustomerName}</option>
							                                                    	</c:when>
							                                                    	<c:otherwise>
							                                                    		<option value="${typeCustomer.typeCustomerId}">${typeCustomer.typeCustomerName}</option>
							                                                    	</c:otherwise>
						                                                    	</c:choose>
						                                                    </c:forEach>
						                                                    </select>                      
									                                    </div>
									                                    <div class="my-3"></div>
									                                </div>
									                                <div class="modal-footer">
							                            				<button type="button" class="btn btn-secondary" 
							                            				data-bs-dismiss="modal" data-bs-toggle="modal" data-bs-target="#<%=j%>updateBillRoomCategory">
							                            					Quay lại
							                            				</button>
							                            				<input type="submit" class="btn btn-info" value="Cập nhật"/>
							                            			</div>
						                            			</form>
					                            			</div>
					                            		</div>
					                            	</div>
						                        </div>
						                        
						                        <div class="modal fade" id="<%=j%><%=k%>deleteCustomerCategory" tabindex="-1" aria-labelledby="deleteCustomerCategoryLabel"
				                                aria-hidden="true">
					                                <div class="modal-dialog">
					                                    <div class="modal-content">
					                                        <div class="modal-header">
					                                            <h1 class="modal-title fs-5" id="deleteCustomerCategoryLabel">Xóa thông tin khách hàng ${listCustomer.customerName}</h1>
					                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
					                                                aria-label="Close"></button>
					                                        </div>
					                                        <div class="modal-body">
					                                            <p style="text-align: left">Bạn có chắc chắn muốn xóa khách hàng ${listCustomer.customerName}?</p>
					                                        </div>
					                                        <div class="modal-footer">
					                                            <button type="button" class="btn btn-secondary"
					                                                data-bs-dismiss="modal" data-bs-toggle="modal" data-bs-target="#<%=j%>updateBillRoomCategory">Quay lại</button>
					                                            <a href="<%=request.getContextPath()%>/bill-for-rent?ACTION=DELETE_CUSTOMER&customerId=${listCustomer.customerId}&roomBillId=${roomBill.roomBillId}"
					                                                class="btn btn-danger">Xóa</a>
					                                        </div>
					                                    </div>
					                                </div>
					                            </div>
						                        
						                        </c:if>
						                        <%k++;%>
					                            </c:forEach>
					                            
					                            <div class="modal fade" id="<%=j %>insertCustomerCategory" tabindex="-1" aria-labelledby="insertCustomerLabel"
					                            aria-hidden="true">
					                            	<div class="modal-dialog">
					                            		<div class="modal-content">
					                            			<div class="modal-header">
					                            				<h1 class="modal-title fs-5" id="insertCustomerLabel">Thêm khách hàng cho phòng ${roomBill.roomName}</h1>
					                            				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					                            			</div>
					                            			<div class="modal-body">
					                            				<form action="<%=request.getContextPath()%>/bill-for-rent">
					                            					<input type="hidden" name="ACTION" value="INSERT_CUSTOMER" />
					                            					<input type="hidden" name="roomBillId" value="${roomBill.roomBillId}" />
						                            				<div class="mx-1" style="text-align: left;">
						                            					<div class="m-3">
						                    								<label for="label-customer-name" class="form-label">Tên khách hàng</label>
									                            			<input id="label-customer-name" name="customerName" class="form-control" value="" required>        					
						                            					</div>
									                                    <div class="m-3">
									                                    	<label for="label-customer-address" class="form-label">Địa chỉ</label>
									                            			<input id="label-customer-address" name="customerAddress" class="form-control" value="" required>
									                                    </div>
									                                    <div class="m-3">
									                                    	<label for="label-customer-identity" class="form-label">CMND</label>
									                            			<input id="label-customer-identity" name="customerIdentityCode" class="form-control" value="" required>
									                                    </div>
									                                    <div class="m-3">
									              							<label for="kind-customerType" class="col-form-label">Loại khách</label>
						                                                    <select id="kind-customerType" class="form-select" name="typeCustomer">
						                                                    <c:forEach var="typeCustomer" items="${listTypeCustomer}">
						                                                    	<option value="${typeCustomer.typeCustomerId}">${typeCustomer.typeCustomerName}</option>
						                                                    </c:forEach>
						                                                    </select>                      
									                                    </div>
									                                    <div class="my-3"></div>
									                                </div>
									                                <div class="modal-footer">
							                            				<button type="button" class="btn btn-secondary" 
							                            				data-bs-dismiss="modal" data-bs-toggle="modal" data-bs-target="#<%=j %>updateBillRoomCategory">
							                            					Quay lại
							                            				</button>
							                            				<input type="submit" class="btn btn-info" value="Thêm khách hàng"/>
							                            			</div>
						                            			</form>
					                            			</div>
					                            		</div>
					                            	</div>
					                            </div>
		                     				</td>
	                     				</tr>
	                     				<%j++; %>
	                    				</c:forEach>
	                    			</tbody>	
	                    		</table>
                     		</div>
                     	
                     	</div>
                     	
					
					</div>
                    
                    <div class="col-5">
                        <div id="room-category-info" class="rounded overflow-auto shadowCustom bg-white rounded-3 px-1">
                            <h1 class="p-3" style="font-size: 20px">Danh sách phòng</h1>
                            <div style="height: 400px; overflow: auto;">
	                           <table class="table table-hover overflow-y">
	                               <thead>
	                                   <tr>
	                                       <th scope="col">STT</th>
	                                       <th scope="col">Phòng</th>
	                                       <th scope="col">Loại Phòng</th>
	                                       <th scope="col">Đơn giá</th>
	                                       <th scope="col">Ghi chú</th>
	                                       <th scope="col">Tình trạng</th>
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
										title="" data-bs-original-title="${room.noteRoom}">${room.noteRoom}</td>
										<td data-bs-toggle="tooltip">
											<c:choose>
												<c:when test="${room.stateRoom==1}">
													<i class="lni lni-close" style="color: red; font-weight: bold;"></i> Đã thuê
												</c:when>
												<c:otherwise>
													<i class="lni lni-checkmark" style="color: green; font-weight: bold;"></i> Trống
												</c:otherwise>
											</c:choose>
										</td>
		                            </tr>
	                                   </c:forEach>
	                               </tbody>
	                           </table>
                            </div>
                        </div>
                    </div>
            </div>
        	</div>
    	</div>
    </div>
    <script src="./js/bootstrap.bundle.min.js"></script>
    <script src="./js/script.js"></script>
    
</body>

</html>