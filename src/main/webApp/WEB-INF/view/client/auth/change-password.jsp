<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">

<head>
    <title>Change Password</title>
</head>

<body>
    <h2>Change Password</h2>

    <div th:if="${successMessage}" style="color: green;">
        <p th:text="${successMessage}"></p>
    </div>
    <div th:if="${errorMessage}" style="color: red;">
        <p th:text="${errorMessage}"></p>
    </div>

    <form th:action="@{/change-password}" th:object="${changePasswordDTO}" method="post">
        <div>
            <label for="currentPassword">Current Password:</label>
            <input type="password" id="currentPassword" th:field="*{currentPassword}" required />
            <span th:if="${#fields.hasErrors('currentPassword')}" th:text="${#fields.errors('currentPassword')}"></span>
        </div>

        <div>
            <label for="newPassword">New Password:</label>
            <input type="password" id="newPassword" th:field="*{newPassword}" required />
            <span th:if="${#fields.hasErrors('newPassword')}" th:text="${#fields.errors('newPassword')}"></span>
        </div>

        <div>
            <label for="confirmPassword">Confirm New Password:</label>
            <input type="password" id="confirmPassword" th:field="*{confirmPassword}" required />
            <span th:if="${#fields.hasErrors('confirmPassword')}" th:text="${#fields.errors('confirmPassword')}"></span>
        </div>

        <div>
            <button type="submit">Change Password</button>
        </div>
    </form>
</body>

</html>