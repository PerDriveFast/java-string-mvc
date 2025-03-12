<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="Hỏi Dân IT - Dự án laptopshop" />
                <meta name="author" content="Hỏi Dân IT" />
                <title>Update Product</title>
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />

                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">
                                <h1 class="mt-4">Update Products</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                                    <li class="breadcrumb-item active">Update Product</li>
                                </ol>
                                <div class="container mt-5">
                                    <div class="row">
                                        <div class="col-md-6 col-12 mx-auto">
                                            <h3>Update Product </h3>
                                            <hr>
                                            <form:form method="post" action="/admin/product/update"
                                                modelAttribute="newProduct" enctype="multipart/form-data">
                                                <!-- Trường ID ẩn để đảm bảo gửi dữ liệu -->
                                                <form:hidden path="id" />

                                                <div class="mb-3">
                                                    <label class="form-label">Name:</label>
                                                    <form:input type="text" class="form-control" path="name" />
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label">Price:</label>
                                                    <form:input type="number" class="form-control" path="price" />
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label">DetailDesc:</label>
                                                    <form:input type="text" class="form-control" path="detailDesc" />
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label">ShortDesc:</label>
                                                    <form:input type="text" class="form-control" path="shortDesc" />
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label">Quantity:</label>
                                                    <form:input type="number" class="form-control" path="quantity" />
                                                </div>

                                                <div class="row g-3">

                                                    <div class="col">
                                                        <label class="form-label">Factory:</label>
                                                        <form:select class="form-select"
                                                            aria-label="Default select example" path="factory">
                                                            <form:option value="Nho Xanh">Nho xanh</form:option>
                                                            <form:option value="Đu đủ">Đu đủ</form:option>
                                                            <form:option value="Nho tím">Nho tím</form:option>
                                                        </form:select>
                                                    </div>
                                                    <div class="col">
                                                        <label class="form-label">Target:</label>
                                                        <form:select class="form-select"
                                                            aria-label="Default select example" path="target">
                                                            <form:option value="Theo mùa">Trái cây theo mùa
                                                            </form:option>
                                                            <form:option value="Ăn Liền">Trái cây ăn liền</form:option>
                                                        </form:select>
                                                    </div>

                                                </div>
                                                <br>
                                                <button type="submit" class="btn btn-warning">Update</button>
                                                <a href="/admin/product" class="btn btn-primary">Back</a>
                                            </form:form>

                                        </div>
                                    </div>
                                </div>
                            </div>

                        </main>
                        <jsp:include page="../layout/footer.jsp" />

                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="/js/scripts.js"></script>

            </body>

            </html>