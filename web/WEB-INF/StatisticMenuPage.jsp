<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Statistic Menu</title>
    
    <link rel="stylesheet" href="css/menu.css">
    
    <script src="js/menu.js" defer></script>
</head>
<body>
    <header class="main-header">
        <ul class="nav-links">
            
            <c:if test="${not empty sessionScope.loggedManager}">
                <li><a href="managerHome.jsp">Admin Page</a></li>
            </c:if>
        </ul>
        <ul class="user-actions">
            
            <li><span class="user-info">Hello, ${sessionScope.loggedManager.fullName}</span></li>
            <li>
                <form action="LogoutServlet" method="post" style="margin: 0;">
                    <button type="submit" class="logout-button">Logout</button>
                </form>
            </li>
        </ul>
    </header>

    <div class="container">
        <h1>Statistic Menu</h1>

        <div class="menu">
            <button type="button" class="btn" onclick="toggleSubmenu()">View statistics by movie</button>

            <div class="submenu" id="movie-submenu" style="display:none;">
                <form action="MovieServlet" method="get">
                    <button type="submit" class="btn">View movie revenue statistics</button>
                </form>
            </div>

            <form action="CustomerStatisticPageServlet" method="get">
                <button type="submit" class="btn">View statistics by customer</button>
            </form>

            <form action="RevenueStatisticPageServlet" method="get">
                <button type="submit" class="btn">View statistics by revenue</button>
            </form>
        </div>
    </div>
    
</body>
</html>