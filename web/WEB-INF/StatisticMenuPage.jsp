<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Statistic Menu</title>
    <link rel="stylesheet" type="text/css" href="css/statistic.css">
    <script>
        function toggleSubmenu() {
            const submenu = document.getElementById("movie-submenu");
            submenu.style.display = submenu.style.display === "none" ? "flex" : "none";
        }
    </script>
</head>
<body>
    <div class="container">
        <!-- Tiêu đề gắn với container -->
        <div class="header">
            <h1>Statistic Menu</h1>
        </div>

        <div class="menu">
            <!-- Nút chính -->
            <button type="button" class="btn" onclick="toggleSubmenu()">View statistics by movie</button>

            <!-- Submenu ẩn/hiện -->
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
