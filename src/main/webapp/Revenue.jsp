<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
                <li class="sidebar-item">
                    <a href="<%=request.getContextPath()%>/room-category" class="sidebar-link">
                        <i class="lni lni-tab"></i>
                        <span>Danh mục phòng</span>
                    </a>
                </li>
                <li class="sidebar-item">
                    <a href="<%=request.getContextPath()%>/bill-for-rent" class="sidebar-link ">
                        <i class="lni lni-agenda"></i>
                        <span>Phiếu thuê phòng</span>
                    </a>
                </li>
                <li class="sidebar-item">
                    <a href="<%=request.getContextPath()%>/search" class="sidebar-link ">
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
                    <a href="<%=request.getContextPath()%>/revenue" class="sidebar-link active">
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
                <h1 style="margin: 0;">Báo cáo doanh thu tháng</h1>
                <h1 style="margin: 0;">Xin chào, Siêu quản trị</h1>
            </div>

            <div class="row mx-0">
                <div id="room-category" class=" h-full rounded overflow-auto shadowCustom bg-white col-6 rounded-3 ">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th scope="col">STT</th>
                                <th scope="col">Loại Phòng</th>
                                <th scope="col">Doanh thu</th>
                                <th scope="col">Tỉ lệ</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <th scope="row">1</th>
                                <td>Mark</td>
                                <td>Mark</td>
                                <td>Mark</td>
                            </tr>
                            <tr>
                                <th scope="row">1</th>
                                <td>Mark</td>
                                <td>Mark</td>
                                <td>Mark</td>
                            </tr>
                            <tr>
                                <th scope="row">1</th>
                                <td>Mark</td>
                                <td>Mark</td>
                                <td>Mark</td>

                            </tr>
                            <tr>
                                <th scope="row">1</th>
                                <td>Mark</td>
                                <td>Mark</td>
                                <td>Mark</td>

                            </tr>
                            <tr>
                                <th scope="row">1</th>
                                <td>Mark</td>
                                <td>Mark</td>
                                <td>Mark</td>
                            </tr>
                            <tr>
                                <th scope="row">1</th>
                                <td>Mark</td>
                                <td>Mark</td>
                                <td>Mark</td>

                            </tr>
                            <tr>
                                <th scope="row">1</th>
                                <td>Mark</td>
                                <td>Mark</td>
                                <td>Mark</td>

                            </tr>
                            <tr>
                                <th scope="row">1</th>
                                <td>Mark</td>
                                <td>Mark</td>
                                <td>Mark</td>

                            </tr>
                            <tr>
                                <th scope="row">1</th>
                                <td>Mark</td>
                                <td>Mark</td>
                                <td>Mark</td>

                            </tr>
                            <tr>
                                <th scope="row">1</th>
                                <td>Mark</td>
                                <td>Mark</td>
                                <td>Mark</td>

                            </tr>


                        </tbody>
                    </table>
                </div>


                <div class="col-1"></div>
                <div class="col-5 p-0 d-flex flex-column justify-content-between mh-100">
                    <div class="row-1"></div>
                    <h5 class="text-center mb-4  bg-white p-2 rounded-3 shadowCustom">Tổng doanh thu : 500000</h5>
                    <div><img src="bieudo.bmp" alt="" class="col-12 mh-40 mb-4"></div>
                    <form action="<%=request.getContextPath()%>/room-category"
                        class="white bg- p-2 rounded-3 shadowCustom bg-white">


                        <div class="mb-3">
                            <label for="label-room-category" class="form-label">Tháng</label>
                            <select id="label-room-category" class="form-select" aria-label="Default select example">
                                <option selected disabled>Open this select menu</option>
                                <option value="1">One</option>
                                <option value="2">Two</option>
                                <option value="3">Three</option>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label for="exampleInputPassword1" class="form-label">Năm</label>
                            <input type="password" class="form-control" id="exampleInputPassword1">
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