package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "StatServlet", urlPatterns = {"/StatServlet"})
public class StatServlet extends HttpServlet {

    // Xử lý yêu cầu GET (khi người dùng nhấn "View Report")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Chuyển hướng (forward) đến trang thống kê
        request.getRequestDispatcher("/WEB-INF/StatisticMenuPage.jsp").forward(request, response);
    }
}
