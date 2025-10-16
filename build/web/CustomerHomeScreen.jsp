<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome Page</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <div class="container">
        <h1>Welcome to <span class="highlight">CineMan Cinema!</span></h1>
        <div class="auth-buttons">
            <!-- Form Login -->
            <form action="LoginServlet" method="get" style="display:inline;">
                <button type="submit" class="btn">Login</button>
            </form>
            <form action="RegisterServlet" method="get" style="display:inline;">
                <button type="submit" class="btn">Register</button>
            </form>
            <p style="color:green; text-align:center; font-weight:bold;">
                ${message}
            </p>
            
        </div>
    </div>
</body>
</html>
