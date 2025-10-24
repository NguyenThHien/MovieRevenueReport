<%@page contentType="text-html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f7fc;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh; /* Căn giữa trang theo chiều dọc */
            margin: 0;
            color: #333;
        }

        .container {
            text-align: center;
            padding: 40px;
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.08);
        }

        h1 {
            font-size: 42px;
            margin-top: 0;
            margin-bottom: 30px;
        }

        .highlight {
            color: #007bff; /* Màu xanh dương nổi bật */
        }

        .auth-buttons {
            margin-top: 20px;
        }

        .btn {
            background-color: #007bff;
            color: white;
            padding: 12px 30px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            margin: 0 10px;
            transition: background-color 0.3s ease, transform 0.2s ease;
        }

        .btn:hover {
            background-color: #0056b3;
            transform: translateY(-2px); /* Hiệu ứng nhấc nút lên */
        }
        
        /* CSS cho thông báo (ví dụ: đăng ký thành công) */
        .success-message {
            color: green;
            text-align: center;
            font-weight: bold;
            margin-top: 25px;
            min-height: 1.2em; /* Giữ khoảng trống để layout không bị giật */
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Welcome to <span class="highlight">CineMan Cinema!</span></h1>
        
        <div class="auth-buttons">
            <form action="LoginServlet" method="get" style="display:inline;">
                <button type="submit" class="btn">Login</button>
            </form>
            <form action="RegisterServlet" method="get" style="display:inline;">
                <button type="submit" class="btn">Register</button>
            </form>
        </div>
        
        <p class="success-message">
            ${message}
        </p>
    </div>
</body>
</html>