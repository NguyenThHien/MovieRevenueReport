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

            
            Date startDate = Date.valueOf(fromDateStr);
            Date endDate = Date.valueOf(toDateStr);

            // Gọi DAO
            StatMovieDAO dao = new StatMovieDAO();
            List<StatMovie> movieList = dao.getAllStatMovie(startDate, endDate);

            // Gửi kết quả về JSP
            request.setAttribute("movieList", movieList);
            request.setAttribute("fromDate", fromDateStr);
            request.setAttribute("toDate", toDateStr);

            request.getRequestDispatcher("/WEB-INF/MovieRevenueStatisticPage.jsp").forward(request, response);

        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Vui lòng chọn khoảng thời gian hợp lệ!");
            request.getRequestDispatcher("/WEB-INF/MovieRevenueStatisticPage.jsp").forward(request, response);
        }
    }
}
