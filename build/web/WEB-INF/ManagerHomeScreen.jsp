<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Manager"%>
<%
    // Lấy thông tin manager từ session
    Manager manager = (Manager) session.getAttribute("loggedManager");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manager Home</title>
    <link rel="stylesheet" href="css/managerHome.css">
</head>
<body>
    <div class="container">
        <div class="profile">
            <img src="https://cdn-icons-png.flaticon.com/512/3135/3135715.png" alt="Manager Avatar">
            <span><%= manager.getFullName() %></span>
        </div>

        <h1>Manager Home</h1>

        <div class="menu">
            <form action="StatServlet" method="get">
                <button type="submit">View Report</button>
            </form>

            
            <form action="LogoutServlet" method="post">
                <button type="submit" class="logout-btn">Logout</button>
            </form>
        </div>
    </div>
</body>
</html>
