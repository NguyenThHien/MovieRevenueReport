package servlet;

import dao.InvoiceDAO;
import model.Invoice;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "STDetailServlet", urlPatterns = {"/STDetailServlet"})
public class STDetailServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Khởi tạo giá trị mặc định
        String movieTitle = request.getParameter("movieTitle");
        String screeningDateStr = request.getParameter("screeningDate");
        String startTimeStr = request.getParameter("startTime");
        String endTimeStr = request.getParameter("endTime");
        String roomName = request.getParameter("roomName");
        String totalRevenueStr = request.getParameter("totalRevenue");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String movieIDStr = request.getParameter("movieID");
        String showtimeIdStr = request.getParameter("ShowtimeID");
        String invoicePageStr = request.getParameter("page"); // Trang hiện tại của HÓA ĐƠN
        String movieReportPageStr = request.getParameter("moviePage"); // Trang của MovieReport
        String showtimeListPageStr = request.getParameter("showtimeListPage"); // Trang của DetailServlet

        int showtimeID = 0;
        int movieID = 0;
        int movieReportPage = 1;
        int showtimeListPage = 1; // Trang của danh sách suất chiếu (trang trước)
        int currentInvoicePage = 1; // Trang hiện tại của servlet này (danh sách hóa đơn)
        int recordsPerInvoicePage = 5; // hóa đơn/trang
        Date screeningDate = null;
        Time startTime = null;
        Time endTime = null;
        double totalRevenue = 0;

        try {
            // Parse các tham số
            if (showtimeIdStr != null) showtimeID = Integer.parseInt(showtimeIdStr);
            else throw new ServletException("Showtime ID is missing.");

            if (screeningDateStr != null) screeningDate = Date.valueOf(screeningDateStr);
            if (startTimeStr != null) {
                if (startTimeStr.matches("\\d{2}:\\d{2}")) startTimeStr += ":00";
                startTime = Time.valueOf(startTimeStr);
            }
            if (endTimeStr != null) {
                 if (endTimeStr.matches("\\d{2}:\\d{2}:\\d{2}")) { // Chấp nhận cả HH:mm:ss
                   
                 } else if (endTimeStr.matches("\\d{2}:\\d{2}")) { // Thêm :00 nếu là HH:mm
                     endTimeStr += ":00";
                 } else {
                     throw new IllegalArgumentException("Invalid end time format: " + endTimeStr);
                 }
                endTime = Time.valueOf(endTimeStr);
            }

            if (movieIDStr != null) movieID = Integer.parseInt(movieIDStr);
            if (totalRevenueStr != null) totalRevenue = Double.parseDouble(totalRevenueStr);

            // Parse các số trang trước đó
            if (movieReportPageStr != null) movieReportPage = Integer.parseInt(movieReportPageStr);
            if (showtimeListPageStr != null) showtimeListPage = Integer.parseInt(showtimeListPageStr); // Parse trang của trang trước
            if (invoicePageStr != null) currentInvoicePage = Integer.parseInt(invoicePageStr);

            if (currentInvoicePage < 1) currentInvoicePage = 1;

            // Gọi DAO
            InvoiceDAO invoiceDAO = new InvoiceDAO();
            int totalInvoiceRecords = invoiceDAO.getTotalInvoiceCount(showtimeID);
            int totalInvoicePages = (int) Math.ceil((double) totalInvoiceRecords / recordsPerInvoicePage);
            if (totalInvoicePages < 1) totalInvoicePages = 1;
            if (currentInvoicePage > totalInvoicePages) currentInvoicePage = totalInvoicePages;

            List<Invoice> invoiceList = invoiceDAO.getInvoiceByPage(showtimeID, currentInvoicePage, recordsPerInvoicePage);

            // Gửi dữ liệu sang JSP
            request.setAttribute("movieTitle", movieTitle);
            request.setAttribute("showtimeID", showtimeID);
            request.setAttribute("screeningDate", screeningDate);
            request.setAttribute("startTime", startTime);
            request.setAttribute("endTime", endTime);
            request.setAttribute("roomName", roomName);
            request.setAttribute("totalRevenue", totalRevenue);
            request.setAttribute("invoiceList", invoiceList);
            request.setAttribute("fromDate", fromDate);
            request.setAttribute("toDate", toDate);
            request.setAttribute("movieID", movieID);

            // Gửi thông tin trang trước đó cho nút Back
            request.setAttribute("moviePage", movieReportPage);           // Trang của Movie Report
            request.setAttribute("showtimeListPageForBack", showtimeListPage); // Trang của Showtime List

            // Gửi thông tin phân trang HÓA ĐƠN
            request.setAttribute("currentInvoicePage", currentInvoicePage);
            request.setAttribute("totalInvoicePages", totalInvoicePages);
            request.setAttribute("recordsPerInvoicePage", recordsPerInvoicePage);

            request.getRequestDispatcher("/WEB-INF/ShowtimeDetailPage.jsp").forward(request, response);

        } catch (IllegalArgumentException | NullPointerException e) { 
             request.setAttribute("error", "Invalid data received from the previous page. Please try again.");
             request.setAttribute("movieID", movieIDStr); 
             request.setAttribute("movieTitle", movieTitle);
             request.setAttribute("fromDate", fromDate);
             request.setAttribute("toDate", toDate);
             request.setAttribute("page", showtimeListPageStr); 
             request.setAttribute("moviePage", movieReportPageStr); 
             request.getRequestDispatcher("/WEB-INF/MovieRevenueDetailPage.jsp").forward(request, response); 
        } catch (Exception e) {
             e.printStackTrace();
             request.setAttribute("error", "An unexpected error occurred.");
             request.setAttribute("movieID", movieIDStr);
             request.setAttribute("movieTitle", movieTitle);
             request.setAttribute("fromDate", fromDate);
             request.setAttribute("toDate", toDate);
             request.setAttribute("page", showtimeListPageStr);
             request.setAttribute("moviePage", movieReportPageStr);
             request.getRequestDispatcher("/WEB-INF/MovieRevenueDetailPage.jsp").forward(request, response);
        }
    }
}