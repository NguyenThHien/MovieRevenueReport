package servlet;

import dao.ManagerDAO;
import model.Manager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username);
        ManagerDAO dao = new ManagerDAO();
        Manager manager = dao.login(username, password);
        System.out.println(manager);
        if (manager != null) {
            
            HttpSession session = request.getSession();
            session.setAttribute("loggedManager", manager);
            request.getRequestDispatcher("/WEB-INF/ManagerHomeScreen.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Invalid username or password.");
            request.getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);
        }
    }
}
