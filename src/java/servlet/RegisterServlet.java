package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Customer;
import dao.CustomerDAO;

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

        // Lấy dữ liệu từ form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullname = request.getParameter("fullname");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String dobStr = request.getParameter("dob");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");

        // Chuyển đổi ngày sinh từ String → java.util.Date
        Date dob = null;
        try {
            dob = new SimpleDateFormat("yyyy-MM-dd").parse(dobStr);
        } catch (ParseException e) {
            e.printStackTrace();
            request.setAttribute("error", "Ngày sinh không hợp lệ!");
            request.getRequestDispatcher("/WEB-INF/MemberRegistrationPage.jsp").forward(request, response);
            return;
        }

        // Tạo đối tượng Customer
        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setPassword(password);
        customer.setFullName(fullname);
        customer.setPhoneNumber(phone);
        customer.setEmail(email);
        customer.setDateOfBirth(dob);
        customer.setGender(gender);
        customer.setAddress(address);

        // Gọi DAO để lưu vào DB
        CustomerDAO dao = new CustomerDAO();
        boolean success = dao.register(customer);

        // Điều hướng tuỳ kết quả
        if (success) {
            request.setAttribute("message", "Registration successful! You can log in now.");
            request.getRequestDispatcher("CustomerHomeScreen.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Registration unsuccessful. Please try again.");
            request.getRequestDispatcher("/WEB-INF/MemberRegistrationPage.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Xử lý đăng ký người dùng mới";
    }
}
