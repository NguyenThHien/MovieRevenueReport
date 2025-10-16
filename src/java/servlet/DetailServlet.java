package servlet;

import dao.ShowTimeDAO;
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

        try {
            int movieID = Integer.parseInt(request.getParameter("movieID"));
            String movieTitle = request.getParameter("movieTitle");
            String fromDateStr = request.getParameter("fromDate");
            String toDateStr = request.getParameter("toDate");

            // Chuyển String -> Date
            Date startDate = Date.valueOf(fromDateStr);
            Date endDate = Date.valueOf(toDateStr);

            // Gọi DAO lấy toàn bộ danh sách suất chiếu
            ShowTimeDAO dao = new ShowTimeDAO();
            List<StatShowTime> showtimeList = dao.getAllShowTime(movieID, startDate, endDate);

            // Gửi dữ liệu sang JSP
            request.setAttribute("showtimeList", showtimeList);
            request.setAttribute("fromDate", fromDateStr);
            request.setAttribute("toDate", toDateStr);
            request.setAttribute("movieTitle", movieTitle);
            request.setAttribute("movieID", movieID);

            // Chuyển đến JSP hiển thị
            request.getRequestDispatcher("/WEB-INF/MovieRevenueDetailPage.jsp").forward(request, response);

        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Vui lòng chọn dữ liệu hợp lệ!");
            request.getRequestDispatcher("/WEB-INF/MovieRevenueDetailPage.jsp").forward(request, response);
        }
    }
}
