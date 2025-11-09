<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/register.css">
        <title>Register</title>
    </head>
    <body>

        <div class="container register-box">
            <h2>Create Account</h2>

            <form action="RegisterServlet" method="POST" id="registerForm">

                <div class="form-columns">
                    <div class="form-column">
                        <div class="form-group">
                            <label for="username">Username: <span class="required-star">*</span></label>
                            <input type="text" id="username"
                                   name="username" 
                                   value="${username}" 
                                   required 
                                   pattern="\S{6,}"
                                   title="Username must be at least 6 characters and contain no spaces.">
                            <span class="error-js" id="usernameError"></span>
                        </div>

                        <div class="form-group">
                            <label for="password">Password: <span class="required-star">*</span></label>
                            <input type="password" id="password" name="password" 
                                   required minlength="8"
                                   title="Password must be at least 8 characters.">
                            <span class="error-js" id="passwordError"></span>
                        </div>

                        <div class="form-group">
                            <label for="confirmPassword">Confirm Password: <span class="required-star">*</span></label>
                            <input type="password" id="confirmPassword" name="confirmPassword" required>
                            <span class="error-js" id="confirmPasswordError"></span>
                        </div>

                        <div class="form-group show-password-group">
                            <input type="checkbox" id="showPasswordToggle">
                            <label for="showPasswordToggle">
                                Show Passwords
                            </label>
                        </div>

                        <div class="form-group">
                            <label for="fullname">Full Name: <span class="required-star">*</span></label>
                            <input type="text" id="fullname" name="fullname" value="${fullname}" required>
                            <span class="error-js" id="fullnameError"></span>
                        </div>

                    </div>

                    <div class="form-column">


                        <div class="form-group">
                            <%-- KHÔNG BẮT BUỘC --%>
                            <label for="dob">Date of Birth:</label>
                            <input type="date" id="dob" name="dob" value="${dob}">
                            <span class="error-js" id="dobError"></span>
                        </div>
                        <div class="form-group">
                            <%-- BẮT BUỘC --%>
                            <label for="phone">Phone: <span class="required-star">*</span></label>
                            <input type="text" id="phone" name="phone" value="${phone}" 
                                   pattern="\d{10}" title="Phone number must be 10 digits."
                                   required>
                            <span class="error-js" id="phoneError"></span>
                        </div>

                        <div class="form-group">
                            <%-- BẮT BUỘC --%>
                            <label for="email">Email: <span class="required-star">*</span></label>
                            <input type="email" id="email" name="email" value="${email}"
                                   title="Please enter a valid email address (e.g., name@example.com)"
                                   required>
                            <span class="error-js" id="emailError"></span>
                        </div>
                        <div class="form-group">
                            <%-- KHÔNG BẮT BUỘC --%>
                            <label>Gender:</label>
                            <div class="gender-options">
                                <input type="radio" id="genderMale" name="gender" value="male" <c:if test="${gender eq 'male'}">checked</c:if>>
                                    <label for="genderMale">Male</label>

                                    <input type="radio" id="genderFemale" name="gender" value="female" <c:if test="${gender eq 'female'}">checked</c:if>>
                                    <label for="genderFemale">Female</label>

                                    <input type="radio" id="genderOther" name="gender" value="other" <c:if test="${gender eq 'other'}">checked</c:if>>
                                    <label for="genderOther">Other</label>
                                </div>
                                <span class="error-js" id="genderError"></span>
                            </div>
                            <div class="form-group">
                            <%-- KHÔNG BẮT BUỘC --%>
                            <label for="address">Address:</label>
                            <input type="text" id="address" name="address" value="${address}">
                            <span class="error-js" id="addressError"></span>
                        </div>

                    </div>


                </div>
                <div>
                </div>

                <c:if test="${not empty error}">
                    <p class="error">${error}</p>
                </c:if>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Register</button>
                    <button type="button" class="btn btn-secondary" onclick="window.location.href = 'CustomerHomeScreen.jsp'">
                        Back to Home
                    </button>
                </div>
            </form> 
        </div>

        <script src="js/register.js"></script>
    </body>
</html>