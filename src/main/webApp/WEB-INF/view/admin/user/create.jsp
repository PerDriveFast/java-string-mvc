<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

                <!DOCTYPE html>
                <html lang="en">

                <head>
                    <meta charset="utf-8" />
                    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                    <meta name="description" content="Hỏi Dân IT - Dự án laptopshop" />
                    <meta name="author" content="Hỏi Dân IT" />
                    <title>Create User</title>
                    <link href="/css/styles.css" rel="stylesheet" />
                    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"
                        crossorigin="anonymous"></script>
                    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

                    <script>
                        $(document).ready(() => {
                            const avatarFile = $("#avatarFile");
                            const orgImage = "${newProduct.image}";
                            if (orgImage) {
                                const urlImage = "/images/product/" + orgImage;
                                $("#avatarPreview").attr("src", urlImage);
                                $("avatarPreview").css("display", "block");
                            }

                            avatarFile.change(function (e) {
                                const imgURL = URL.createObjectURL(e.target.files[0]);
                                $("#avatarPreview").attr("src", imgURL);
                                $("#avatarPreview").css({ "display": "block" });
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
                                    <h1 class="mt-4">Create Users</h1>
                                    <ol class="breadcrumb mb-4">
                                        <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                                        <li class="breadcrumb-item active">Create Users</li>
                                    </ol>
                                    <div class="container mt-5">
                                        <div class="row">
                                            <div class="col-md-6 col-12 mx-auto">
                                                <h3>Create a user</h3>
                                                <hr>
                                                <form:form method="post" action="/admin/user/create1"
                                                    modelAttribute="newUser" class="row" enctype="multipart/form-data">
                                                    <div class="mb-3 col-12 col-md-6">
                                                        <label class="form-label">Email:</label>
                                                        <c:set var="emailError">
                                                            <form:errors path="email" />
                                                        </c:set>
                                                        <form:input type="email"
                                                            class="form-control ${not empty emailError ? 'is-invalid' : (not empty user.email ? 'is-valid' : '')}"
                                                            path="email" />
                                                        <form:errors path="email" cssClass="invalid-feedback" />
                                                    </div>

                                                    <div class="mb-3 col-12 col-md-6">
                                                        <label class="form-label">Password:</label>
                                                        <c:set var="passwordError">
                                                            <form:errors path="password" />
                                                        </c:set>
                                                        <form:input type="password"
                                                            class="form-control ${not empty passwordError ? 'is-invalid' : (not empty user.password ? 'is-valid' : '')}"
                                                            path="password" />
                                                        <form:errors path="password" cssClass="invalid-feedback" />
                                                    </div>

                                                    <div class="mb-3 col-12 col-md-6">
                                                        <label class="form-label">Full Name:</label>
                                                        <c:set var="fullNameError">
                                                            <form:errors path="fullName" />
                                                        </c:set>
                                                        <form:input type="text"
                                                            class="form-control ${not empty fullNameError ? 'is-invalid' : (not empty user.fullName ? 'is-valid' : '')}"
                                                            path="fullName" />
                                                        <form:errors path="fullName" cssClass="invalid-feedback" />
                                                    </div>

                                                    <div class="mb-3 col-12 col-md-6">
                                                        <label class="form-label">Phone Number:</label>

                                                        <c:set var="phoneError">
                                                            <form:errors path="phone" />
                                                        </c:set>
                                                        <form:input type="number"
                                                            class="form-control ${not empty phoneError ? 'is-invalid' : (not empty user.phone ? 'is-valid' : '')}"
                                                            path="phone" />
                                                        <form:errors path="phone" cssClass="invalid-feedback" />
                                                    </div>

                                                    <div class="mb-3">
                                                        <label class="form-label">Address:</label>
                                                        <c:set var="addressError">
                                                            <form:errors path="address" />
                                                        </c:set>
                                                        <form:input type="text"
                                                            class="form-control ${not empty addressError ? 'is-invalid' : (not empty user.address ? 'is-valid' : '')}"
                                                            path="address" />
                                                        <form:errors path="address" cssClass="invalid-feedback" />
                                                    </div>
                                                    <div class="row g-3">
                                                        <div class="col">
                                                            <label class="form-label">Role:</label>
                                                            <form:select class="form-select"
                                                                aria-label="Default select example" path="role.name">
                                                                <form:option value="ADMIN">ADMIN</form:option>
                                                                <form:option value="USER">USER</form:option>
                                                            </form:select>
                                                        </div>
                                                        <div class="col">
                                                            <div class="mb-3">
                                                                <label for="avatarFile"
                                                                    class="form-label">Avatar</label>
                                                                <input class="form-control" type="file" id="avatarFile"
                                                                    accept=".png, .jpg, .jpeg" name="hoidanitFile" />
                                                            </div>
                                                        </div>
                                                        <div class="col-12 mb-3">
                                                            <img style="max-height: 250px; display: none;"
                                                                alt="avatar preview" id="avatarPreview" />
                                                        </div>
                                                    </div>
                                                    <div class="col-12 mb-5">
                                                        <button type="submit" class="btn btn-warning">Create</button>
                                                        <a href="/admin/user" class="btn btn-primary">Back</a>
                                                    </div>
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