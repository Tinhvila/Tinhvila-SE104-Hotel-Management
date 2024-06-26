<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Kala Hotel | Thanh toán</title>
    <!-- Bootstrap => css -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">

    <!-- line icon => css -->
    <link href="https://cdn.lineicons.com/4.0/lineicons.css" rel="stylesheet" />
	<link rel="stylesheet" href="./css/bootstrap.min.css">
	

    <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=IM+Fell+DW+Pica:ital@0;1&family=Lora:ital,wght@0,400..700;1,400..700&display=swap"
            rel="stylesheet">
    <!-- custom => css-->
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
                    <a href="<%=request.getContextPath()%>/reciept" class="sidebar-link active">
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
        	<div class="shadowCustom overflow-hidden rounded d-flex justify-content-between align-items-center mb-4 p-3 bg-white">
                <h1 style="margin: 0;">Hóa đơn thanh toán</h1>
                <h1 style="margin: 0;">Xin chào, <c:out value="${sessionScope.user.fullName}" /></h1>
            </div>
            
            <div class="container">
            	<div class="row">
            		<div class="col-7 h-100 p-0">
            			<div id="receipt-bill-setting" class="h-100 rounded overflow-auto shadowCustom bg-white rounded-3 px-1">
            				<h1 class="p-2" style="font-size: 20px;">Thông tin hóa đơn thanh toán</h1>
            				
            				<form action="<%=request.getContextPath()%>/reciept">
                            	<input type="hidden" name="ACTION" value="INSERT_RECEIPT"/>
                                <div class="mx-3">
                                    <label for="dateRentTextField" class="form-label">Tên khách hàng/cơ quan thanh toán</label>
                                    <input type="text" class="form-control" name="receiptCustomerName" id="receiptCustomerName" required>
                                </div>
                                    <input type="submit" value="Thêm hóa đơn" class="btn btn-info d-block m-3">
                            </form>
            			</div>
            			
            			<div id="receipt-bill-display" class="h-100 rounded overflow-auto shadowCustom bg-white rounded-3 px-1 mt-3">
            				<h1 class="p-2" style="font-size: 20px;">Danh sách hóa đơn thanh toán</h1>
            				<div class="overflow-auto" style="height: 350px;">
	            				<table class="table table-hover">
	            					<thead>
	            						<tr>
	            							<th scope="col">STT</th>
	            							<th scope="col">Người trả</th>
	            							<th scope="col">Ngày lập</th>
	            							<th scope="col">Ngày thanh toán</th>
	            							<th scope="col">Tình trạng</th>
	            							<th scope="col">Thao tác</th>
	            						</tr>
	            					</thead>
	            					<tbody>
	            						<%int k = 1; %>
	            						<c:forEach var="listAllReceipt" items="${listAllReceipt}">
	            						<tr>
	            							<th scope="row"><%=k%></th>
	            							<td>${listAllReceipt.receiptCustomerName}</td>
	            							<td>${listAllReceipt.receiptDayCreated}</td>
	            							<td>${listAllReceipt.receiptDayPaid}</td>
	            							<td>
	            								<c:choose>
	            									<c:when test="${listAllReceipt.receiptPaymentStatus == 0}">
	            										<i class="lni lni-close" style="color: red; font-weight: bold;"></i> Chưa thanh toán
	            									</c:when>
	            									<c:otherwise>
	            										<i class="lni lni-checkmark" style="color: green; font-weight: bold;"></i> Đã thanh toán
	            									</c:otherwise>
	            								</c:choose>
	            							</td>
	            							<td>
	            								<button data-bs-toggle="modal" data-bs-target="#<%=k%>selectReceiptCategory"
			                     				class="btn btn-secondary"><i class="lni lni-information"></i>
			                     				</button> 
			                     				<c:if test="${listAllReceipt.receiptPaymentStatus == 0}">
			                     					|
		            								<button  
					                     			data-bs-toggle="modal" data-bs-target="#<%=k%>updateReceiptCategory"
					                                class="btn btn-info"><i class="lni lni-pencil-alt"></i></button>
				                                   	| <button data-bs-toggle="modal" data-bs-target="#<%=k%>deleteReceiptCategory"
				                                    class="btn btn-danger"><i class="lni lni-trash-can"></i></button>
			                                    </c:if>
			                                    
			                                    <div class="modal fade" id="<%=k%>deleteReceiptCategory" tabindex="-1" aria-labelledby="deleteReceiptCategoryLabel"
					                                aria-hidden="true">
					                                <div class="modal-dialog">
					                                    <div class="modal-content">
					                                        <div class="modal-header">
					                                            <h1 class="modal-title fs-5" id="deleteReceiptCategoryLabel">Xóa hóa đơn ${listAllReceipt.receiptId}</h1>
					                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
					                                                aria-label="Close"></button>
					                                        </div>
					                                        <div class="modal-body">
					                                            <p style="text-align: left">Bạn có chắc chắn muốn xóa hóa đơn này không?</p>
					                                            <p style="text-align: left; color: red; font-weight: bold;">Lưu ý: Nếu xóa hóa đơn thì các thông tin về phiếu thuê phòng
					                                            được thêm vào hóa đơn này sẽ mất (không bị xóa đi bên phiếu thuê phòng)!
					                                            </p>
					                                        </div>
					                                        <div class="modal-footer">
					                                            <button type="button" class="btn btn-secondary"
					                                                data-bs-dismiss="modal" data-bs-toggle="modal">Đóng</button>
					                                            <a href="<%=request.getContextPath()%>/reciept?ACTION=DELETE_RECEIPT&receiptId=${listAllReceipt.receiptId}"
					                                                class="btn btn-danger">Xóa</a>
					                                        </div>
					                                    </div>
					                                </div>
					                            </div>
					                            
					                            <div class="modal fade" id="<%=k%>selectReceiptCategory" tabindex="-1" aria-labelledby="selectReceiptCategoryLabel"
					                                aria-hidden="true">
					                                <div class="modal-dialog modal-lg">
					                                    <div class="modal-content">
					                                        <div class="modal-header">
					                                            <h1 class="modal-title fs-5" id="deleteReceiptCategoryLabel">Xem thông tin hóa đơn</h1>
					                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
					                                                aria-label="Close"></button>
					                                        </div>
					                                        <div class="modal-body">
					                                        	<div style="text-align: left;">
					                                        		<label for="dateRentTextField" class="form-label">Tên khách hàng/cơ quan thanh toán</label>
	                                    							<input type="text" class="form-control" name="receiptCustomerName" id="receiptCustomerName" value="${listAllReceipt.receiptCustomerName}" readonly>
	                                    							<label for="dateRentTextField" class="form-label">Ngày lập</label>
	                                    							<input type="text" class="form-control" name="receiptDayCreated" id="receiptCustomerName" value="${listAllReceipt.receiptDayCreated}" readonly>
	                                    							<label for="dateRentTextField" class="form-label">Ngày thanh toán</label>
	                                    							<input type="text" class="form-control" name="receiptDayPaid" id="receiptCustomerName" value="${listAllReceipt.receiptDayPaid}" readonly>
					                                        	</div>
					                                        	<div class="mt-3">
					                                        		<p style="text-align: left; font-weight: bold; font-size: 20px;">Danh sách phiếu thuê phòng</p>
					                                        		<table class="table table-hover">
					                                        			<thead>
											                   				<tr>
											                    				<th scope="col">STT</th>
											                    				<th scope="col">Phòng</th>
											                    				<th scope="col">Ngày thuê</th>
											                    				<th scope="col">Ngày trả</th>
											                    				<th scope="col">Số ngày thuê</th>
											                    				<th scope="col">Trị giá</th>
											                    			</tr>
											                   			</thead>
					                                        			<tbody>
					                                        				<%int l = 1;%>
					                                        				<c:forEach var="listReceiptDetail" items="${listReceiptDetail}">
					                                        				<c:if test="${listReceiptDetail.receiptId == listAllReceipt.receiptId}">
						                                        				<tr>
						                                        					<th scope="row"><%=l++%></th>
						                                        					<td>${listReceiptDetail.receiptRoomBillInfo.roomName}</td>
						                                        					<td>${listReceiptDetail.receiptRoomBillInfo.roomDateRent}</td>
						                                        					<td>${listReceiptDetail.receiptRoomBillInfo.roomDateReturn}</td>
						                                        					<td>${listReceiptDetail.countRoomDayRent}</td>
						                                        					<td><fmt:formatNumber type="number" groupingUsed="true" value="${listReceiptDetail.receiptTotalValue}" />đ</td>
						                                        				</tr>
					                                        				</c:if>
					                                        				</c:forEach>
					                                        			</tbody>	
					                                        		</table>
					                                        	</div>  
					                                        	<div class="mt-3">
					                                        		<c:forEach var="listReceiptTotalValue" items="${listReceiptTotalValue}">
					                                        			<c:if test="${listReceiptTotalValue.receiptId == listAllReceipt.receiptId}">
					                                        				<p style="text-align: right;">Thành tiền: <span style="color: red; font-weight: bold;"><fmt:formatNumber type="number" groupingUsed="true" value="${listReceiptTotalValue.receiptPrice}" />đ</span></p>
					                                        			</c:if>
					                                        		</c:forEach>
					                                        	</div>
					                                        </div>
					                                        <div class="modal-footer">
					                                            <button type="button" class="btn btn-secondary"
					                                                data-bs-dismiss="modal" data-bs-toggle="modal">Đóng</button>
					                                        </div>
					                                    </div>
					                                </div>
					                            </div>
					                            
					                            <form action="<%=request.getContextPath()%>/reciept">
					                            	<input type="hidden" name="ACTION" value="UPDATE_RECEIPT"/>
					                            	<input type="hidden" name="receiptId" value="${listAllReceipt.receiptId}"/>
						                            <div class="modal fade" id="<%=k%>updateReceiptCategory" tabindex="-1" aria-labelledby="updateReceiptCategoryLabel"
						                                aria-hidden="true">
						                                <div class="modal-dialog modal-lg">
						                                    <div class="modal-content">
						                                        <div class="modal-header">
						                                            <h1 class="modal-title fs-5" id="updateReceiptCategoryLabel">Cập nhật thông tin hóa đơn</h1>
						                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
						                                                aria-label="Close"></button>
						                                        </div>
						                                        <div class="modal-body" style="text-align: left">
						                                        	<div style="text-align: left;">
						                                        		<label for="dateRentTextField" class="form-label">Tên khách hàng/cơ quan thanh toán</label>
		                                    							<input type="text" class="form-control" name="receiptCustomerName" id="receiptCustomerName" value="${listAllReceipt.receiptCustomerName}" readonly>
		                                    							<label for="dateRentTextField" class="form-label">Ngày lập</label>
		                                    							<input type="text" class="form-control" name="receiptDayCreated" id="receiptCustomerName" value="${listAllReceipt.receiptDayCreated}" readonly>
						                                        	</div>
						                                        	<div class="mt-3">
						                                        		<p style="text-align: left; font-weight: bold; font-size: 20px;">Danh sách phiếu thuê phòng</p>
						                                        		<button type="button" class="btn btn-info" data-bs-dismiss="modal" data-bs-toggle="modal"
											                                	data-bs-target="#<%=k %>insertRoomBillReceipt">Thêm phiếu thuê phòng</button>
						                                        		<table class="table table-hover">
						                                        			<thead>
												                   				<tr>
												                    				<th scope="col">STT</th>
												                    				<th scope="col">Phòng</th>
												                    				<th scope="col">Ngày thuê</th>
												                    				<th scope="col">Ngày trả</th>
												                    				<th scope="col">Số ngày thuê</th>
												                    				<th scope="col">Trị giá</th>
												                    				<th scope="col">Thao tác</th>
												                    			</tr>
												                   			</thead>
						                                        			<tbody>
						                                        				<%l = 1;%>
						                                        				<c:forEach var="listReceiptDetail" items="${listReceiptDetail}">
						                                        					<c:if test="${listReceiptDetail.receiptId == listAllReceipt.receiptId}">
								                                        				<tr>
								                                        					<th scope="row"><%=l%></th>
								                                        					<td>${listReceiptDetail.receiptRoomBillInfo.roomName}</td>
								                                        					<td>${listReceiptDetail.receiptRoomBillInfo.roomDateRent}</td>
								                                        					<td>${listReceiptDetail.receiptRoomBillInfo.roomDateReturn}</td>
								                                        					<td>${listReceiptDetail.countRoomDayRent}</td>
								                                        					<td>${listReceiptDetail.receiptTotalValue}</td>
								                                        					<td>
								                                        						<button data-bs-toggle="modal" type="button"
					                                    										class="btn btn-danger" data-bs-target="#<%=k%><%=l%>deleteRoomBillReceipt"><i class="lni lni-trash-can"></i></button>
								                                        					</td>
								                                        				</tr>
								                                        				<%l++;%>
						                                        					</c:if>
						                                        				</c:forEach>
						                                        			</tbody>	
						                                        		</table>
						                                        	</div>  
						                                        	<div class="mt-3">
						                                        		<c:forEach var="listReceiptTotalValue" items="${listReceiptTotalValue}">
						                                        			<c:if test="${listReceiptTotalValue.receiptId == listAllReceipt.receiptId}">
						                                        				<p style="text-align: right;">Thành tiền: <span style="color: red; font-weight: bold;">${listReceiptTotalValue.receiptPrice}</span></p>
						                                        			</c:if>
					                                        			</c:forEach>
						                                        	</div>
						                                        </div>
						                                        <div class="modal-footer">
						                                            <button type="button" class="btn btn-secondary"
						                                                data-bs-dismiss="modal" data-bs-toggle="modal">Đóng</button>
						                                            <c:forEach var="listCountReceiptDetail" items="${listCountReceiptDetail}">
						                                            	<c:choose>
							                                            	<c:when test="${listCountReceiptDetail.receiptId == listAllReceipt.receiptId and listCountReceiptDetail.countReceipt != 0}">
							                                            		<button type="button" class="btn btn-success"
								                                            	data-bs-dismiss="modal" data-bs-toggle="modal" data-bs-target="#<%=k%>confirmUpdateReceiptCategory">Thanh toán</button>
							                                            	</c:when>
						                                            	</c:choose>
							                                        </c:forEach>
						                                        </div>
						                                    </div>
						                                </div>
						                            </div>
					                            	
						                            <div class="modal fade" id="<%=k%>confirmUpdateReceiptCategory" tabindex="-1" aria-labelledby="confirmUpdateReceiptCategoryLabel"
						                                aria-hidden="true">
						                                <div class="modal-dialog">
						                                    <div class="modal-content">
						                                        <div class="modal-header">
						                                            <h1 class="modal-title fs-5" id="confirmUpdateReceiptCategoryLabel">Thanh toán hóa đơn</h1>
						                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
						                                                aria-label="Close"></button>
						                                        </div>
						                                        <div class="modal-body">
						                                            <p style="text-align: left">Bạn có chắc chắn muốn thanh toán hóa đơn này không?</p>
						                                            <p style="text-align: left; color: red; font-weight: bold;">Lưu ý: Khi đã thanh toán rồi thì bạn không thể sửa lại hoặc
						                                            cập nhật cho hóa đơn và các phiếu thuê phòng có trong hóa đơn đó nữa.
						                                            </p>
						                                        </div>
						                                        <div class="modal-footer">
						                                            <button type="button" class="btn btn-secondary"
						                                                data-bs-dismiss="modal" data-bs-toggle="modal" data-bs-target="#<%=k%>updateReceiptCategory">Quay lại</button>
						                                            <button type="submit" class="btn btn-info"
						                                                data-bs-dismiss="modal" data-bs-toggle="modal">Xác nhận</button>
						                                        </div>
						                                    </div>
						                                </div>
						                            </div>
					                            </form>
					                            
					                            <div class="modal fade" id="<%=k%>insertRoomBillReceipt" tabindex="-1" aria-labelledby="insertRoomBillReceiptLabel"
						                                aria-hidden="true">
					                                <div class="modal-dialog">
					                                    <div class="modal-content">
					                                        <div class="modal-header">
					                                        	<h1 class="modal-title fs-5" id="insertRoomBillReceiptLabel">Thêm phiếu thuê phòng vào hóa đơn</h1>
					                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
					                                                aria-label="Close"></button>
					                                       	</div>
					                                       	<div class="modal-body" style="text-align: left;">
					                                       		<form action="<%=request.getContextPath()%>/reciept">
					                                       			<input type="hidden" name="ACTION" value="INSERT_ROOMBILLRECEIPT"/>
					                                       			<input type="hidden" name="receiptId" value="${listAllReceipt.receiptId}"/> <!-- ReceiptId -->
					                                       			<label for="label-receipt" class="form-label">Phiếu thuê phòng: <span style="color: red; font-weight:bold;">(Phòng: Ngày thuê - Ngày trả)</span></label>
					                                       			<select id="label-receipt" name="roomBillId" class="form-select" aria-label="Default select example" required>
									                                	<c:forEach var="unpaidRoomBills" items="${listUnpaidRoomBills}">
									                                		<c:if test="${unpaidRoomBills.roomPriceDay != 0}">
										                                		<option value="${unpaidRoomBills.roomBillId}">
										                                		${unpaidRoomBills.roomName}: ${unpaidRoomBills.roomDateRent} - ${unpaidRoomBills.roomDateReturn}
										                                		</option>
									                                		</c:if>
									                                	</c:forEach>
								                            		</select>
					                                       			
							                                       	<div class="modal-footer">
							                                       		<button type="button" class="btn btn-secondary"
						                                                data-bs-dismiss="modal" data-bs-toggle="modal" data-bs-target="#<%=k%>updateReceiptCategory">Quay lại</button>
						                                            	<button type="submit" class="btn btn-info">Xác nhận</button>
							                                       	</div>
						                                       	</form>
					                                       	</div>
					                                    </div>
					                                </div>
												</div>
					                            
					                            <%l=1;%>
					                            <c:forEach var="listReceiptDetail" items="${listReceiptDetail}">
							                        <c:if test="${listReceiptDetail.receiptId == listAllReceipt.receiptId}">
							                            <div class="modal fade" id="<%=k%><%=l%>deleteRoomBillReceipt" tabindex="-1" aria-labelledby="deleteReceiptCategoryLabel"
							                                aria-hidden="true">
							                                <div class="modal-dialog">
							                                    <div class="modal-content">
							                                        <div class="modal-header">
							                                            <h1 class="modal-title fs-5" id="deleteReceiptCategoryLabel">Xóa hóa đơn</h1>
							                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
							                                                aria-label="Close"></button>
							                                        </div>
							                                        <div class="modal-body">
							                                            <p style="text-align: left">Bạn có chắc muốn xóa phiếu thuê phòng ${listReceiptDetail.receiptRoomBillInfo.roomBillId} khỏi hóa đơn không?</p>
							                                            <p style="text-align: left; color: red; font-weight: bold;">Lưu ý: Phiếu thuê phòng chỉ xóa khỏi hóa đơn này và vẫn có thể 
							                                            thêm vào hóa đơn này hoặc các hóa đơn khác.
							                                            </p>
							                                        </div>
							                                        <div class="modal-footer">
							                                            <button type="button" class="btn btn-secondary"
							                                                data-bs-dismiss="modal" data-bs-toggle="modal" data-bs-target="#<%=k%>updateReceiptCategory">Quay lại</button>
							                                            <a href="<%=request.getContextPath()%>/reciept?ACTION=DELETE_ROOMBILLRECEIPT&receiptDetailId=${listReceiptDetail.receiptDetailId}&roomBillId=${listReceiptDetail.receiptRoomBillInfo.roomBillId}"
							                                                class="btn btn-danger">Xóa</a>
							                                        </div>
							                                    </div>
							                                </div>
							                            </div>
						                            <%l++;%>
						                            </c:if>
					                            </c:forEach>
	            							</td>
	            						</tr>
	            						<%k++;%>
	            						</c:forEach>
	            					</tbody>
	            				</table>
	            			</div>
            			</div>
            		</div>
					<div class="col-5">
						<div id="room-category-info" class="rounded overflow-auto shadowCustom bg-white rounded-3 px-1">
                            <h1 class="p-3" style="font-size: 20px">Danh sách phiếu thuê phòng</h1>
                            <div style="height: 60vh; overflow: auto;">
	                            <table class="table table-hover">
	                   			<thead>
	                   				<tr>
	                    				<th scope="col">STT</th>
	                    				<th scope="col">Phòng</th>
	                    				<th scope="col">Ngày thuê</th>
	                    				<th scope="col">Ngày trả</th>
	                    				<th scope="col">Tình trạng</th>
	                    				<th scope="col">Thông tin</th>
	                    			</tr>
	                   			</thead>
	                   			<tbody>
	                   				<%int i = 1; %>
	                   				<c:forEach var="roomBill" items="${listAllRoomBills}">
	                   				<tr>
	                   					<th scope="row"><%=i%></th>
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
	                   						<button data-bs-toggle="modal" data-bs-target="#<%=i%>selectBillRoomCategory"
	                     					class="btn btn-secondary"><i class="lni lni-information"></i>
	                     					</button >
	                     					<c:if test="${roomBill.roomPriceDay == 0}">
	                     						<p data-bs-toggle="tooltip" title="" data-bs-original-title="Phiếu này có trị giá là 0đ" 
	                     						class="btn btn-warning"><i class="lni lni-warning"></i>
		                     					</p>
	                     					</c:if>
	                     					
	                     					<div class="modal fade" id="<%=i%>selectBillRoomCategory" tabindex="-1" aria-labelledby="selectBillRoomLabel"
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
						                                	<p>Đơn giá 1 ngày dự tính: <span style="color: red; font-weight:bold;"><fmt:formatNumber type="number" groupingUsed="true" value="${roomBill.roomPriceDay}" />đ</span></p>
						                                	<p class="text-end">Số khách:
						                                		<c:set var="tag" value="0" />
						                                		<c:forEach var="listCountCustomer" items="${listCountCustomer}">
									                                <c:if test="${listCountCustomer.roomBillId == roomBill.roomBillId}">
																		<c:set var="tag" value="1" />
									                                	<c:if test="${not empty listCountCustomer.countCustomer}">
									                                		${listCountCustomer.countCustomer}
									                                	</c:if>
									                                </c:if>
									                             </c:forEach>
									                            <c:if test="${tag == 0}">0</c:if>
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
				                            					<%int j = 1; %>
			                            						<c:forEach var="listCustomer" items="${listCustomer}">
			                            						<c:if test="${roomBill.roomBillId==listCustomer.roomBillId}">
				                            						<tr>
				                            							<th scope="row"><%=j%></th>
				                            							<td>${listCustomer.customerName }</td>
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
				                            						<%j++;%>
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
	                   					</td>
	                   				</tr>
	                   				<%i++;%>
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