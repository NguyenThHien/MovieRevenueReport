<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang giao diện đăng ký</title>
    <link rel="stylesheet" type="text/css" href="css/register.css">
    <script>
        // Regex dùng chung
        const usernameRegex = /^[A-Za-z0-9_]{6,}$/;
        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
        const phoneRegex = /^[0-9]{10}$/;
        const emailRegex = /^[\w.-]+@[\w.-]+\.[A-Za-z]{2,}$/;
        function validateField(field) {
            const val = field.value.trim();
            const id = field.id;
            const errEl = document.getElementById(id + '-error');

            // reset
            errEl.textContent = '';
            field.classList.remove('error-input');

            if (id === 'username') {
                if (!usernameRegex.test(val)) {
                    errEl.textContent = 'Username phải ít nhất 6 ký tự, không chứa khoảng trắng.';
                    field.classList.add('error-input');
                    return false;
                }
            } else if (id === 'password') {
                if (!passwordRegex.test(val)) {
                    errEl.textContent = 'Password ít nhất 8 ký tự, gồm chữ hoa, chữ thường, số và ký tự đặc biệt.';
                    field.classList.add('error-input');
                    return false;
                }
            } else if (id === 'phone') {
                if (!phoneRegex.test(val)) {
                    errEl.textContent = 'Số điện thoại phải đúng 10 chữ số.';
                    field.classList.add('error-input');
                    return false;
                }
            } else if (id === 'email') {
                if (!emailRegex.test(val)) {
                    errEl.textContent = 'Email không đúng định dạng.';
                    field.classList.add('error-input');
                    return false;
                }
            }
            return true;
        }
        function validateForm() {
            const fields = ['username','password','phone','email'];
            let ok = true;
            fields.forEach(id => {
                const el = document.getElementById(id);
                if (!validateField(el)) ok = false;
            });
            return ok;
        }

        document.addEventListener('DOMContentLoaded', function () {
            const inputs = document.querySelectorAll('#username, #password, #phone, #email');
            inputs.forEach(inp => {
                // khi rời ô -> hiện lỗi nếu có
                inp.addEventListener('blur', () => validateField(inp));
                // khi nhập -> xóa lỗi realtime
                inp.addEventListener('input', () => {
                    const err = document.getElementById(inp.id + '-error');
                    err.textContent = '';
                    inp.classList.remove('error-input');
                });
            });

            // optional: khi reset form -> xóa lỗi
            const form = document.querySelector('.register-form');
            form.addEventListener('reset', () => {
                document.querySelectorAll('.error').forEach(e => e.textContent = '');
                document.querySelectorAll('input').forEach(i => i.classList.remove('error-input'));
            });
        });
    </script>
</head>
<body>
    <div class="page-bg">
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

                <div class="field">
                    <label for="fullname">Fullname:</label>
                    <input type="text" id="fullname" name="fullname" required>
                    <span class="hint">Ví dụ: Nguyễn Văn A</span>
                </div>

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

                <div class="two-cols">
                    <div class="field">
                        <label for="dob">Date of birth:</label>
                        <input type="date" id="dob" name="dob" required>
                    </div>

                    <div class="field">
                        <label>Gender:</label>
                        <div class="gender">
                            <label><input type="radio" id="male" name="gender" value="Male" required> Male</label>
                            <label><input type="radio" id="female" name="gender" value="Female"> Female</label>
                            <label><input type="radio" id="other" name="gender" value="Other"> Other</label>
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
    </div>
</body>
</html>
