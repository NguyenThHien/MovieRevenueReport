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
        
        // Lấy tham số
        String movieTitle = request.getParameter("movieTitle") != null ? request.getParameter("movieTitle") : "Unknown Movie";
        String fromDateStr = request.getParameter("fromDate");
        String toDateStr = request.getParameter("toDate");
        String movieIDStr = request.getParameter("movieID");
        String pageStr = request.getParameter("page"); 
        String moviePageStr = request.getParameter("moviePage");
        String totalRevenueStr = request.getParameter("totalRevenue");

        
        int movieID = 0;
        int moviePage = 1;
        int currentPage = 1; 
        int recordsPerPage = 5; 
        Date startDate = null;
        Date endDate = null;
        float totalRevenue = 0; 

        try {
            
            if (movieIDStr != null && !movieIDStr.isEmpty()) {
                movieID = Integer.parseInt(movieIDStr);
            }
            if (fromDateStr != null && !fromDateStr.isEmpty()) {
                startDate = Date.valueOf(fromDateStr);
            }
            if (toDateStr != null && !toDateStr.isEmpty()) {
                endDate = Date.valueOf(toDateStr);
            }
            
          
            if (totalRevenueStr != null && !totalRevenueStr.isEmpty()) {
                try {
                    totalRevenue = Float.parseFloat(totalRevenueStr);
                } catch (NumberFormatException e) {
                    totalRevenue = 0; 
                }
            }
            
           
            if (moviePageStr != null && !moviePageStr.isEmpty()) {
                try { moviePage = Integer.parseInt(moviePageStr); } catch (NumberFormatException e) { moviePage = 1; }
            }
            if (pageStr != null && !pageStr.isEmpty()) {
                try { currentPage = Integer.parseInt(pageStr); } catch (NumberFormatException e) { currentPage = 1; }
            }
            if (currentPage < 1) currentPage = 1;

          
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
                 request.setAttribute("error", "Missing required parameters (MovieID or Date Range).");
            }

            // Gửi sang JSP
            request.setAttribute("fromDate", fromDateStr);
            request.setAttribute("toDate", toDateStr);
            request.setAttribute("movieTitle", movieTitle);
            request.setAttribute("movieID", movieID);
            request.setAttribute("currentPage", currentPage); 
            request.setAttribute("recordsPerPage", recordsPerPage);
            request.setAttribute("totalRevenue", totalRevenue); 

            // Nút Back về trang
            request.setAttribute("showtimeListPage", currentPage); 
            request.setAttribute("moviePage", moviePage); 

            request.getRequestDispatcher("/WEB-INF/jsp/MovieRevenueDetailPage.jsp").forward(request, response);

        } catch (IllegalArgumentException e) { 
            request.setAttribute("error", "Invalid or missing date format/value.");
            request.setAttribute("fromDate", fromDateStr);
            request.setAttribute("toDate", toDateStr);
            request.setAttribute("moviePage", moviePage); 
            request.getRequestDispatcher("/WEB-INF/jsp/MovieRevenueStatisticPage.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An unexpected error occurred.");
            request.getRequestDispatcher("/WEB-INF/jsp/MovieRevenueStatisticPage.jsp").forward(request, response);
        }
    }

}