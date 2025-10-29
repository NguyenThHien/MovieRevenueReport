package servlet;

import dao.StatMovieDAO;
import model.StatMovie;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "MovieServlet", urlPatterns = {"/MovieServlet"})
public class MovieServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("movieList", null);
        request.getRequestDispatcher("/WEB-INF/MovieRevenueStatisticPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String fromDateStr = request.getParameter("fromDate");
            String toDateStr = request.getParameter("toDate");
            String pageStr = request.getParameter("page");
            int recordsPerPage = 5; 
            int currentPage = 1;
            if (pageStr != null && !pageStr.isEmpty()) {
                try {
                    currentPage = Integer.parseInt(pageStr);
                } catch (NumberFormatException e) {
                    currentPage = 1; 
                }
            }

            Date startDate = Date.valueOf(fromDateStr);
            Date endDate = Date.valueOf(toDateStr);

            StatMovieDAO dao = new StatMovieDAO();

            // 1. Lấy tổng số phim để tính số trang
            int totalRecords = dao.getTotalMovieCount(startDate, endDate);

            // 2. Tính tổng số trang (làm tròn lên)
            int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

            // 3. Lấy danh sách phim chỉ cho trang hiện tại
            List<StatMovie> movieList = dao.getMoviesByPage(startDate, endDate, currentPage, recordsPerPage);

            // 4. Lấy tổng doanh thu của tất cả các phim
            double grandTotalRevenue = dao.getGrandTotalRevenue(startDate, endDate);

            // Gửi tất cả dữ liệu cần thiết sang JSP
            request.setAttribute("movieList", movieList);
            request.setAttribute("fromDate", fromDateStr);
            request.setAttribute("toDate", toDateStr);
            
            // Gửi dữ liệu mới cho phân trang và tổng tiền
            request.setAttribute("grandTotalRevenue", grandTotalRevenue);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("recordsPerPage", recordsPerPage);

            request.getRequestDispatcher("/WEB-INF/MovieRevenueStatisticPage.jsp").forward(request, response);

        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Please select a valid time period");
            request.getRequestDispatcher("/WEB-INF/MovieRevenueStatisticPage.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An unexpected error occurred.");
            request.getRequestDispatcher("/WEB-INF/MovieRevenueStatisticPage.jsp").forward(request, response);
        }
    }
}



