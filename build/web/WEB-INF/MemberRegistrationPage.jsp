<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Trang giao diện đăng ký</title>
        <link rel="stylesheet" type="text/css" href="css/register.css">
    </head>
    <body>
        <div class="container">
            <h1>Register</h1>
        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
            <script>
                alert("<%= error %>");
            </script>
        <%
            }
        %>
            <form action="RegisterServlet" method="post" class="register-form">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required>

                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
                
                <label for="fullname">Fullname:</label>
                <input type="text" id="fullname" name="fullname" required>

                <label for="phone">Phone:</label>
                <input type="tel" id="phone" name="phone" required>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>

                <label for="dob">Date of birth:</label>
                <input type="date" id="dob" name="dob" required>

                <label>Gender:</label>
                <div class="gender">
                    <input type="radio" id="male" name="gender" value="Male" required>
                    <label for="male">Male</label>
                    <input type="radio" id="female" name="gender" value="Female">
                    <label for="female">Female</label>
                    <input type="radio" id="other" name="gender" value="Other">
                    <label for="other">Other</label>
                </div>

                <label for="address">Address:</label>
                <input type="text" id="address" name="address" required>

                <div class="form-buttons">
                    <button type="submit" class="btn">Register</button>
                    <button type="reset" class="btn cancel-btn">Cancel</button>
                </div>

            </form>
        </div>
    </body>
</html>
