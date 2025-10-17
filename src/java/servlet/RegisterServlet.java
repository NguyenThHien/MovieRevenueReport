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
        request.getRequestDispatcher("/WEB-INF/MemberRegistrationPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
           
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String fullName = request.getParameter("fullname");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String gender = request.getParameter("gender");
            String address = request.getParameter("address");

           
            Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dob"));

            
            Customer customer = new Customer();
            customer.setUsername(username);
            customer.setPassword(password);
            customer.setFullName(fullName);
            customer.setPhoneNumber(phone);
            customer.setEmail(email);
            customer.setGender(gender);
            customer.setAddress(address);
            customer.setDateOfBirth(dob);

           
            CustomerDAO dao = new CustomerDAO();
            boolean success = dao.register(customer);

          
            if (success) {
                request.setAttribute("message", "Registration successful! You can now log in.");
                request.getRequestDispatcher("/CustomerHomeScreen.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Registration failed. Please try again.");
                request.getRequestDispatcher("/WEB-INF/MemberRegistrationPage.jsp").forward(request, response);
            }

        } catch (ParseException e) {
            request.setAttribute("error", "Invalid date format!");
            request.getRequestDispatcher("/WEB-INF/MemberRegistrationPage.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles customer registration";
    }
}
