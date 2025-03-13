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
                <title>Update User</title>
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

                <script>
                    $(document).ready(() => {
                        const avatarFile = $("#avatarFile");
                        const avatarPreview = $("#avatarPreview");

                        // Kiểm tra và hiển thị ảnh gốc nếu có
                        const orgImage = "${newUser.avatar}";
                        if (orgImage && orgImage.trim() !== "") {
                            const urlImage = "/images/avatar/" + orgImage;
                            avatarPreview.attr("src", urlImage).css("display", "block");
                        }

                        // Khi chọn ảnh mới, hiển thị ngay lập tức
                        avatarFile.change(function (e) {
                            const file = e.target.files[0];
                            if (file) {
                                const imgURL = URL.createObjectURL(file);
                                avatarPreview.attr("src", imgURL).css("display", "block");
                            }
                        });
                    });

                </script>
            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />

                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">
                                <h1 class="mt-4">Update Users</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                                    <li class="breadcrumb-item "><a href="/admin/user">Users</a></li>
                                    <li class="breadcrumb-item active">Update Users</li>
                                </ol>
                                <div class="container mt-5">
                                    <div class="row">
                                        <div class="col-md-6 col-12 mx-auto">
                                            <h3>Update User </h3>
                                            <hr>
                                            <form:form method="post" action="/admin/user/update"
                                                modelAttribute="newUser" enctype="multipart/form-data">
                                                <!-- Trường ID ẩn để đảm bảo gửi dữ liệu -->
                                                <form:hidden path="id" />
                                                <c:set var="errorEmail">
                                                    <form:errors path="email" cssClass="invalid-feedback" />
                                                </c:set>
                                                <c:set var="errorFullName">
                                                    <form:errors path="fullName" cssClass="invalid-feedback" />
                                                </c:set>
                                                <c:set var="errorPhone">
                                                    <form:errors path="phone" cssClass="invalid-feedback" />
                                                </c:set>
                                                <c:set var="errorAddress">
                                                    <form:errors path="address" cssClass="invalid-feedback" />
                                                </c:set>

                                                <div class="row g-3">
                                                    <div class="mb-3 col-12 col-md-6">
                                                        <label class="form-label">Email:</label>
                                                        <form:input type="text"
                                                            class="form-control ${not empty errorEmail ? 'is-invalid' : (not empty user.email ? 'is-valid' : '')}"
                                                            path="email" />

                                                    </div>
                                                    <div class="mb-3 col-12 col-md-6">
                                                        <label class="form-label">Full Name:</label>
                                                        <form:input type="text"
                                                            class="form-control ${not empty errorFullName ? 'is-invalid' : (not empty user.fullName ? 'is-valid' : '')}"
                                                            path="fullName" />

                                                    </div>
                                                </div>
                                                <div class="row g-3">

                                                    <div class="mb-3 col-12 col-md-6">
                                                        <label class="form-label">Phone Number:</label>
                                                        <form:input type="number"
                                                            class="form-control ${not empty errorPhone ? 'is-invalid' : (not empty user.phone ? 'is-valid' : '')}"
                                                            path="phone" />
                                                    </div>

                                                    <div class="mb-3 col-12 col-md-6">
                                                        <label class="form-label">Address:</label>
                                                        <form:input type="text"
                                                            class="form-control ${not empty errorAddress ? 'is-invalid' : (not empty user.address ? 'is-valid' : '')}"
                                                            path="address" />

                                                    </div>
                                                </div>
                                                <div class="row g-3">
                                                    <div class="col col-12 col-md-6">
                                                        <label class="form-label">Role:</label>
                                                        <form:select class="form-select"
                                                            aria-label="Default select example" path="role.name">
                                                            <form:option value="ADMIN">ADMIN</form:option>
                                                            <form:option value="USER">USER</form:option>
                                                        </form:select>
                                                    </div>
                                                </div>
                                                <br>
                                                <div class="row g-3">
                                                    <div class="mb-3">
                                                        <label for="avatarFile" class="form-label">Avatar:</label>
                                                        <input class="form-control" type="file" id="avatarFile"
                                                            accept=".png, .jpg, .jpeg" name="hoidanitFile" />
                                                    </div>
                                                    <div class="col-12 mb-3">
                                                        <img style="max-height: 250px; display: none;"
                                                            alt="avatar preview" id="avatarPreview" />
                                                    </div>
                                                </div>

                                                <button type="submit" class="btn btn-warning">Update</button>
                                                <a href="/admin/user" class="btn btn-primary">Back</a>
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