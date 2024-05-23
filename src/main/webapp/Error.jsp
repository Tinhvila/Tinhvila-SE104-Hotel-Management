<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Kala Hotel | Quyền hạn</title>

		<style >
   			<%@ include file="./css/error.css"%>
   		</style>
   		
   		
   		

</head>
<body>
	<h1>403</h1> 
    <div class="cloak__wrapper">
        <div class="cloak__container">
            <div class="cloak"></div>
        </div>
    </div>
    <div class="info">
        <h2>Truy cập bị từ chối</h2>
        <p>Yêu cầu quản trị viên cấp quyền truy cập hoặc đăng nhập bằng tài khoản khác để truy cập trang này.
        </p><a href="<%=request.getContextPath()%>/home" rel="noreferrer noopener">Trang chủ</a>
    </div>
	
</body>
</html>