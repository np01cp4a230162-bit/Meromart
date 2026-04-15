<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile — Mero Mart</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/profile.css">
</head>
<body class="dashboard-body">

    <!-- Navigation Bar -->
    <nav class="navbar">
        <div class="logo-container">
            <img src="${pageContext.request.contextPath}/assets/images/logo/logo5.png"
                 alt="Mero Mart Logo" class="nav-logo">
        </div>
        <div class="nav-links">
            <%--
                EL accesses the UserModel stored in the session by AuthFilter gating.
                ${sessionScope.current_user.name} calls getCurrentUser().getName() automatically.
            --%>
            <span>Welcome, <c:out value="${sessionScope.current_user.name}" />!</span>
            <a href="${pageContext.request.contextPath}/logout" class="btn danger-btn-small">Logout</a>
        </div>
    </nav>

    <!-- Dashboard -->
    <div class="dashboard-container">
        <div class="card profile-card">
            <h2>My Profile</h2>
            <div class="profile-info">
                <div class="info-group">
                    <label>Account ID:</label>
                    <p>#<c:out value="${sessionScope.current_user.id}" /></p>
                </div>
                <div class="info-group">
                    <label>Name:</label>
                    <p><c:out value="${sessionScope.current_user.name}" /></p>
                </div>
                <div class="info-group">
                    <label>Email Address:</label>
                    <p><c:out value="${sessionScope.current_user.email}" /></p>
                </div>
                <div class="info-group">
                    <label>Phone Number:</label>
                    <p><c:out value="${sessionScope.current_user.phone}" /></p>
                </div>
                <div class="info-group">
                    <label>Role:</label>
                    <c:choose>
                        <c:when test="${sessionScope.current_user.admin}">
                            <span class="badge badge-admin">ADMIN</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge badge-user">USER</span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <c:if test="${sessionScope.current_user.admin}">
                <div class="admin-panel-link">
                    <p>You have administrator privileges.</p>
                    <a href="${pageContext.request.contextPath}/pages/admin/admin-dashboard.jsp"
                       class="btn secondary-btn">Go to Admin Dashboard</a>
                </div>
            </c:if>
        </div>
    </div>
</body>
</html>