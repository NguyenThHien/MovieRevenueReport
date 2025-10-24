<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manager Home</title>
    <link rel="stylesheet" href="css/ManagerHome.css">
</head>
<body>

    <header class="main-header">
        <ul class="nav-links">
            <li><a href="index.jsp">Home</a></li>
            
            <c:if test="${not empty sessionScope.loggedManager}">
                <li><a href="managerHome.jsp">Admin Page</a></li>
            </c:if>
        </ul>

        <ul class="user-actions">
            <li><span class="user-info">Hello, ${sessionScope.loggedManager.fullName}</span></li>
            <li>
                <form action="LogoutServlet" method="post">
                    <button type="submit" class="logout-button">Logout</button>
                </form>
            </li>
        </ul>
    </header>

    <div class="container">
        <h1>Welcome to Manager Dashboard</h1>

        <div class="menu">
            <form action="StatServlet" method="get">
                <button type="submit">View Report</button>
            </form>
        </div>
    </div>
</body>
</html>