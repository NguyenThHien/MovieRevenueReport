package com.cinema.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.CustomerDAO;
import model.Customer;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/MemberRegistrationPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String registerJsp = "/WEB-INF/jsp/MemberRegistrationPage.jsp";

        try {
          
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String fullName = request.getParameter("fullname");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            
            
            String gender = request.getParameter("gender");
            String address = request.getParameter("address");
            String dobString = request.getParameter("dob");

            // bat buoc
            if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                fullName == null || fullName.trim().isEmpty() ||
                email == null || email.trim().isEmpty() || 
                phone == null || phone.trim().isEmpty()) { 
                
                setAttributesForRepopulation(request);
                request.setAttribute("error", "Please fill in all required fields (*).");
                request.getRequestDispatcher(registerJsp).forward(request, response);
                return;
            }
            
            
            if (gender != null && gender.trim().isEmpty()) {
                gender = null;
            }
            if (address != null && address.trim().isEmpty()) {
                address = null;
            }

            Date dob = null;
            if (dobString != null && !dobString.trim().isEmpty()) {
                try {
                    dob = new SimpleDateFormat("yyyy-MM-dd").parse(dobString);
                } catch (ParseException e) {
                    setAttributesForRepopulation(request);
                    request.setAttribute("error", "Invalid date format!");
                    request.getRequestDispatcher(registerJsp).forward(request, response);
                    return; 
                }
            }

            
            Customer customer = new Customer();
            customer.setUsername(username);
            customer.setPassword(password);
            customer.setFullName(fullName);
            customer.setPhoneNumber(phone); 
            customer.setEmail(email);       
            
            customer.setGender(gender);     // Không bắt buộc (có thể là null)
            customer.setAddress(address);   // Không bắt buộc (có thể là null)
            customer.setDateOfBirth(dob); // Không bắt buộc (có thể là null)

           
            CustomerDAO dao = new CustomerDAO();
            String result = dao.register(customer);

            switch (result) {
                case "SUCCESS":
                    request.setAttribute("message", "Registration successful! You can now log in.");
                    request.getRequestDispatcher("CustomerHomeScreen.jsp").forward(request, response);
                    break;
                case "USER_EXISTS":
                    setAttributesForRepopulation(request);
                    request.setAttribute("error", "Username already exists. Please choose another one.");
                    request.getRequestDispatcher(registerJsp).forward(request, response);
                    break;
                case "EMAIL_EXISTS":
                    setAttributesForRepopulation(request);
                    request.setAttribute("error", "Email already exists. Please use another one.");
                    request.getRequestDispatcher(registerJsp).forward(request, response);
                    break;
                case "PHONE_EXISTS":
                    setAttributesForRepopulation(request);
                    request.setAttribute("error", "Phone number already exists. Please use another one.");
                    request.getRequestDispatcher(registerJsp).forward(request, response);
                    break;
                default: // "ERROR"
                    setAttributesForRepopulation(request);
                    request.setAttribute("error", "An error occurred during registration. Please try again.");
                    request.getRequestDispatcher(registerJsp).forward(request, response);
                    break;
            }

        } catch (Exception e) {
            setAttributesForRepopulation(request);
            request.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
            request.getRequestDispatcher(registerJsp).forward(request, response);
        }
    }

    private void setAttributesForRepopulation(HttpServletRequest request) {
        request.setAttribute("username", request.getParameter("username"));
        request.setAttribute("fullname", request.getParameter("fullname"));
        request.setAttribute("email", request.getParameter("email"));
        request.setAttribute("phone", request.getParameter("phone"));
        request.setAttribute("address", request.getParameter("address"));
        request.setAttribute("gender", request.getParameter("gender"));
        request.setAttribute("dob", request.getParameter("dob"));
    }

    
}