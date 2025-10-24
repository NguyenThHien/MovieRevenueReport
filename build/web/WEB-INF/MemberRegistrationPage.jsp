<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang giao diện đăng ký</title>
    <style>
        /* CSS chung */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            background-color: #f0f2f5;
            /* Thay đổi 1: Giảm font chữ cơ bản của toàn trang */
            font-size: 14px;
        }

        /* ============== CSS CHO HEADER (cũng được làm gọn hơn) ============== */
        .main-header {
            background-color: #333;
            color: white;
            /* Thay đổi 2: Giảm padding của header */
            padding: 8px 40px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .main-header .nav-links,
        .main-header .user-actions {
            list-style: none;
            margin: 0;
            padding: 0;
            display: flex;
            gap: 25px;
            align-items: center;
        }
        
        .main-header a {
            color: white;
            text-decoration: none;
            /* Font size sẽ thừa hưởng từ body (14px) */
        }

        /* ============== CSS CHO FORM ĐĂNG KÝ (Tối ưu độ nhỏ gọn) ============== */
        .container {
            max-width: 700px; 
            margin: 25px auto; /* Thay đổi 3: Giảm margin top/bottom */
            padding: 25px;      /* Thay đổi 4: Giảm padding bên trong container */
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }

        h1 {
            text-align: center;
            color: #333;
            margin-top: 0;
            margin-bottom: 15px; /* Giảm khoảng cách dưới heading */
        }
        
        .server-error {
            padding: 8px;
            margin-bottom: 15px;
        }

        .form-row {
            display: flex;
            gap: 20px;
        }

        .form-row .field {
            flex: 1;
            min-width: 0;
        }

        .field {
            /* Thay đổi 5: Giảm khoảng cách giữa các hàng */
            margin-bottom: 12px;
        }

        .field label {
            display: block;
            margin-bottom: 4px; /* Giảm khoảng cách giữa label và input */
            font-weight: bold;
            color: #555;
        }

        .field input[type="text"],
        .field input[type="password"],
        .field input[type="tel"],
        .field input[type="email"],
        .field input[type="date"] {
            width: 100%;
            /* Thay đổi 6: Giảm padding của input để làm nó thấp hơn */
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 14px; /* Đảm bảo font trong input cũng nhỏ lại */
        }
        
        .field input.error-input {
            border-color: red;
        }
        
        .gender {
            display: flex;
            gap: 15px;
            align-items: center;
            /* Căn chỉnh chiều cao cho vừa với input date */
            height: 35px; 
        }
        
        .gender label {
            font-weight: normal;
        }

        .error {
            color: red;
            font-size: 0.9em;
            height: 1em;
            display: block;
            margin-top: 2px;
        }

        .form-buttons {
            display: flex;
            justify-content: center;
            gap: 20px;
            margin-top: 20px;
        }

        .btn {
            /* Thay đổi 7: Giảm padding của button */
            padding: 10px 25px;
            border: none;
            border-radius: 5px;
            font-size: 14px;
            cursor: pointer;
        }

        .btn.primary { background-color: #007BFF; color: white; }
        .btn.ghost { background-color: transparent; color: #555; border: 1px solid #ccc; }
    </style>
    <script>
        const usernameRegex = /^[A-Za-z0-9_]{6,}$/;const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;const phoneRegex = /^[0-9]{10}$/;const emailRegex = /^[\w.-]+@[\w.-]+\.[A-Za-z]{2,}$/;function validateField(field){const val = field.value.trim();const id = field.id;const errEl = document.getElementById(id + '-error');if (!errEl) return true;errEl.textContent = '';field.classList.remove('error-input');if (id === 'username') {if (!usernameRegex.test(val)) {errEl.textContent = 'Username phải ít nhất 6 ký tự.';field.classList.add('error-input');return false;}} else if (id === 'password') {if (!passwordRegex.test(val)) {errEl.textContent = 'Password 8+ ký tự, đủ hoa, thường, số, đặc biệt.';field.classList.add('error-input');return false;}} else if (id === 'phone') {if (!phoneRegex.test(val)) {errEl.textContent = 'Số điện thoại phải đúng 10 chữ số.';field.classList.add('error-input');return false;}} else if (id === 'email') {if (!emailRegex.test(val)) {errEl.textContent = 'Email không đúng định dạng.';field.classList.add('error-input');return false;}}return true;}function validateForm(){const fields = ['username','password','phone','email'];let ok = true;fields.forEach(id => {const el = document.getElementById(id);if (!validateField(el)) ok = false;});return ok;}document.addEventListener('DOMContentLoaded', function (){const inputs = document.querySelectorAll('#username, #password, #phone, #email');inputs.forEach(inp => {inp.addEventListener('blur', () => validateField(inp));inp.addEventListener('input', () => {const err = document.getElementById(inp.id + '-error');if (err) {err.textContent = '';inp.classList.remove('error-input');}});});const form = document.querySelector('.register-form');form.addEventListener('reset', () => {document.querySelectorAll('.error').forEach(e => e.textContent = '');document.querySelectorAll('input').forEach(i => i.classList.remove('error-input'));});});
    </script>
</head>
<body>
    <header class="main-header">
        <ul class="nav-links">
            <li><a href="index.jsp">Home</a></li>
        </ul>
        
    </header>

    <div class="container">
        <h1>Register</h1>
        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
            <div class="server-error"><%= error %></div>
        <%
            }
        %>

        <form action="RegisterServlet" method="post" class="register-form" onsubmit="return validateForm()">
            <div class="form-row">
                <div class="field">
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" autocomplete="username" required>
                    <span id="username-error" class="error"></span>
                </div>
                <div class="field">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" autocomplete="new-password" required>
                    <span id="password-error" class="error"></span>
                </div>
            </div>

            <div class="field">
                <label for="fullname">Fullname:</label>
                <input type="text" id="fullname" name="fullname" required>
            </div>

            <div class="form-row">
                <div class="field">
                    <label for="phone">Phone:</label>
                    <input type="tel" id="phone" name="phone" inputmode="numeric" required>
                    <span id="phone-error" class="error"></span>
                </div>
                <div class="field">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" autocomplete="email" required>
                    <span id="email-error" class="error"></span>
                </div>
            </div>

            <div class="form-row">
                <div class="field">
                    <label for="dob">Date of birth:</label>
                    <input type="date" id="dob" name="dob" required>
                </div>
                <div class="field">
                    <label>Gender:</label>
                    <div class="gender">
                        <label><input type="radio" name="gender" value="Male" required> Male</label>
                        <label><input type="radio" name="gender" value="Female"> Female</label>
                        <label><input type="radio" name="gender" value="Other"> Other</label>
                    </div>
                </div>
            </div>

            <div class="field">
                <label for="address">Address:</label>
                <input type="text" id="address" name="address" required>
            </div>

            <div class="form-buttons">
                <button type="submit" class="btn primary">Register</button>
                <button type="reset" class="btn ghost">Cancel</button>
            </div>
        </form>
    </div>
</body>
</html>