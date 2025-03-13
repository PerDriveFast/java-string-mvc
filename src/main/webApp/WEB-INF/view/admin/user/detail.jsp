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
                <title>Details Users</title>
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
                                <h1 class="mt-4">Detail Users</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                                    <li class="breadcrumb-item "><a href="/admin/user">Users</a></li>
                                    <li class="breadcrumb-item active">Detail Users</li>
                                </ol>
                                <div class="container mt-5">
                                    <div class="row">
                                        <div class="col-12 mx-auto">
                                            <div class="d-flex justify-content-between">
                                                <h3>User Detail: ${id}</h3>
                                            </div>
                                            <hr>
                                            <div class="card" style="width: 60%;">
                                                <div class="card-header">
                                                    User Information
                                                </div>
                                                <ul class="list-group list-group-flush">
                                                    <li class="list-group-item"><strong>Id:</strong> ${id}</li>
                                                    <li class="list-group-item"><strong>Email:</strong> ${user.email}
                                                    </li>
                                                    <li class="list-group-item"><strong>Full Name:</strong>
                                                        ${user.fullName}</li>
                                                    <li class="list-group-item"><strong>Address:</strong>
                                                        ${user.address}</li>
                                                    <li class="list-group-item"><strong>Phone:</strong>
                                                        ${user.phone}</li>
                                                    <li class="list-group-item"><strong>Role:</strong> ${user.role.name}
                                                    </li>
                                                    <li class="list-group-item text-center">
                                                        <strong>Avatar:</strong> <br>

                                                        <c:choose>
                                                            <c:when test="${not empty user.avatar}">
                                                                <img src="/images/avatar/${user.avatar}"
                                                                    alt="User Avatar" class="img-thumbnail mt-2 shadow"
                                                                    style="max-width: 300px; border-radius: 10px;">
                                                                <p class="mt-2 text-muted">
                                                                    📁 Image file: <span
                                                                        class="fw-bold">${user.avatar}</span>
                                                                </p>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <p class="mt-2 text-danger"><i
                                                                        class="fas fa-exclamation-circle"></i> Not yet
                                                                    image</p>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </li>

                                                </ul>

                                            </div>
                                            <br>
                                            <a href="/admin/user" class="btn btn-primary">Back</a>

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