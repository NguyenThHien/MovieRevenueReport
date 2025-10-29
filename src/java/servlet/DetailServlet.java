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
        // Khởi tạo giá trị mặc định
        String movieTitle = request.getParameter("movieTitle") != null ? request.getParameter("movieTitle") : "Unknown Movie";
        String fromDateStr = request.getParameter("fromDate");
        String toDateStr = request.getParameter("toDate");
        String movieIDStr = request.getParameter("movieID");
        String pageStr = request.getParameter("page"); // Trang hiện tại của trang này (do Back hoặc pagination gửi)
        String moviePageStr = request.getParameter("moviePage"); // Trang của Movie Report (do trang trước gửi)

        int movieID = 0;
        int moviePage = 1; // Trang của Movie Report
        int currentPage = 1; // Trang hiện tại của servlet này (danh sách suất chiếu)
        int recordsPerPage = 5; //  suất chiếu/trang
        Date startDate = null;
        Date endDate = null;

        try {
            // Parse các tham số
            if (movieIDStr != null) movieID = Integer.parseInt(movieIDStr);
            if (fromDateStr != null) startDate = Date.valueOf(fromDateStr);
            if (toDateStr != null) endDate = Date.valueOf(toDateStr);

            // Parse trang của Movie Report
            if (moviePageStr != null && !moviePageStr.isEmpty()) {
                try { moviePage = Integer.parseInt(moviePageStr); } catch (NumberFormatException e) { moviePage = 1; }
            }
             // Parse trang hiện tại của trang này
            if (pageStr != null && !pageStr.isEmpty()) {
                try { currentPage = Integer.parseInt(pageStr); } catch (NumberFormatException e) { currentPage = 1; }
            }
            if (currentPage < 1) currentPage = 1;

            // Gọi DAO
            List<StatShowTime> showtimeList = null;
            int totalPages = 1;
            if (movieID > 0 && startDate != null && endDate != null) {
                StatShowTimeDAO dao = new StatShowTimeDAO();
                int totalRecords = dao.getTotalShowtimeCount(movieID, startDate, endDate);
                totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
                if (totalPages < 1) totalPages = 1;
                if (currentPage > totalPages) currentPage = totalPages;

                showtimeList = dao.getStatShowtimesByPage(movieID, startDate, endDate, currentPage, recordsPerPage);
                request.setAttribute("showtimeList", showtimeList);
                request.setAttribute("totalPages", totalPages);
            } else {
                 request.setAttribute("error", "Missing required parameters.");
            }

            // Gửi sang JSP
            request.setAttribute("fromDate", fromDateStr);
            request.setAttribute("toDate", toDateStr);
            request.setAttribute("movieTitle", movieTitle);
            request.setAttribute("movieID", movieID);
            request.setAttribute("currentPage", currentPage); // Trang hiện tại của trang này
            request.setAttribute("recordsPerPage", recordsPerPage);

            //nút Back về trang 
            request.setAttribute("showtimeListPage", currentPage); 
            request.setAttribute("moviePage", moviePage); 

            request.getRequestDispatcher("/WEB-INF/MovieRevenueDetailPage.jsp").forward(request, response);

        } catch (IllegalArgumentException | NullPointerException e) { 
             request.setAttribute("error", "Invalid or missing date format/value.");
             request.setAttribute("fromDate", fromDateStr);
             request.setAttribute("toDate", toDateStr);
             request.setAttribute("moviePage", moviePage); 
             request.getRequestDispatcher("/WEB-INF/MovieRevenueStatisticPage.jsp").forward(request, response);
        } catch (Exception e) {
             e.printStackTrace();
             request.setAttribute("error", "An unexpected error occurred.");
             request.getRequestDispatcher("/WEB-INF/MovieRevenueStatisticPage.jsp").forward(request, response);
        }
    }

}