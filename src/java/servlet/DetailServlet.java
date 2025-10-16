package servlet;

import dao.StatShowTimeDAO;
import model.StatShowTime;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "DetailServlet", urlPatterns = {"/DetailServlet"})
public class DetailServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        // Nếu click back, lấy dữ liệu từ session thay vì load lại
        String back = request.getParameter("back");
        if ("true".equals(back)) {
            // Kiểm tra session có dữ liệu không
            List<StatShowTime> showtimeList = (List<StatShowTime>) session.getAttribute("showtimeList");
            if (showtimeList != null) {
                request.setAttribute("showtimeList", showtimeList);
                request.setAttribute("fromDate", session.getAttribute("fromDate"));
                request.setAttribute("toDate", session.getAttribute("toDate"));
                request.setAttribute("movieTitle", session.getAttribute("movieTitle"));
                request.setAttribute("movieID", session.getAttribute("movieID"));
            }
            request.getRequestDispatcher("/WEB-INF/MovieRevenueDetailPage.jsp").forward(request, response);
            return;
        }

        try {
            int movieID = Integer.parseInt(request.getParameter("movieID"));
            String movieTitle = request.getParameter("movieTitle");
            String fromDateStr = request.getParameter("fromDate");
            String toDateStr = request.getParameter("toDate");

            Date startDate = Date.valueOf(fromDateStr);
            Date endDate = Date.valueOf(toDateStr);

            // Lấy dữ liệu từ DAO
            StatShowTimeDAO dao = new StatShowTimeDAO();
            List<StatShowTime> showtimeList = dao.getAllStatShowtime(movieID, startDate, endDate);

            // Lưu vào session
            session.setAttribute("showtimeList", showtimeList);
            session.setAttribute("fromDate", fromDateStr);
            session.setAttribute("toDate", toDateStr);
            session.setAttribute("movieTitle", movieTitle);
            session.setAttribute("movieID", movieID);

            // Gửi dữ liệu sang JSP
            request.setAttribute("showtimeList", showtimeList);
            request.setAttribute("fromDate", fromDateStr);
            request.setAttribute("toDate", toDateStr);
            request.setAttribute("movieTitle", movieTitle);
            request.setAttribute("movieID", movieID);

            request.getRequestDispatcher("/WEB-INF/MovieRevenueDetailPage.jsp").forward(request, response);

        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Please select a valid time period");
            request.getRequestDispatcher("/WEB-INF/MovieRevenueDetailPage.jsp").forward(request, response);
        }
    }
}
