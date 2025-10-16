package servlet;

import dao.InvoiceDAO;
import model.Invoice;
import java.io.IOException;
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

        String showtimeIdStr = request.getParameter("ShowtimeID");
        String movieTitle = request.getParameter("movieTitle");
        String screeningDate = request.getParameter("screeningDate");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String roomName = request.getParameter("roomName");
        String totalRevenueStr = request.getParameter("totalRevenue");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");

        int showtimeID = 0;
        if (showtimeIdStr != null && !showtimeIdStr.isEmpty()) {
            try {
                showtimeID = Integer.parseInt(showtimeIdStr);
            } catch (NumberFormatException e) {
                System.out.println("Lỗi parse showtimeID: " + e.getMessage());
            }
        }

        double totalRevenue = 0;
        if (totalRevenueStr != null && !totalRevenueStr.isEmpty()) {
            try {
                totalRevenue = Double.parseDouble(totalRevenueStr);
            } catch (NumberFormatException e) {
                System.out.println("Erros parse totalRevenue: " + e.getMessage());
            }
        }

        // ?Gọi DAO lấy danh sách hóa đơn theo xuất chiếu
        InvoiceDAO invoiceDAO = new InvoiceDAO();
        System.out.println(showtimeID);
        List<Invoice> invoiceList = invoiceDAO.getInvoice(showtimeID);

        //Gửi dữ liệu sang JSP hiển thị
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

        //Forward tới trang chi tiết xuất chiếu
        request.getRequestDispatcher("/WEB-INF/ShowtimeDetailPage.jsp").forward(request, response);
    }
}
