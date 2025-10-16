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
        // Khi vào trang lần đầu -> xóa session cũ
        HttpSession session = request.getSession();
        session.removeAttribute("movieList");
        session.removeAttribute("fromDate");
        session.removeAttribute("toDate");

        request.setAttribute("movieList", null);
        request.getRequestDispatcher("/WEB-INF/MovieRevenueStatisticPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        try {
            String fromDateStr = request.getParameter("fromDate");
            String toDateStr = request.getParameter("toDate");

            if ((fromDateStr == null || toDateStr == null)
                    && session.getAttribute("movieList") != null) {

                request.setAttribute("movieList", session.getAttribute("movieList"));
                request.setAttribute("fromDate", session.getAttribute("fromDate"));
                request.setAttribute("toDate", session.getAttribute("toDate"));

                request.getRequestDispatcher("/WEB-INF/MovieRevenueStatisticPage.jsp").forward(request, response);
                return;
            }

         
            Date startDate = Date.valueOf(fromDateStr);
            Date endDate = Date.valueOf(toDateStr);

            // Gọi DAO lấy danh sách phim
            StatMovieDAO dao = new StatMovieDAO();
            List<StatMovie> movieList = dao.getAllStatMovie(startDate, endDate);

            // Lưu kết quả vào session (để không cần tải lại khi back)
            session.setAttribute("movieList", movieList);
            session.setAttribute("fromDate", fromDateStr);
            session.setAttribute("toDate", toDateStr);

            // Đồng thời gắn vào request để render JSP
            request.setAttribute("movieList", movieList);
            request.setAttribute("fromDate", fromDateStr);
            request.setAttribute("toDate", toDateStr);

            request.getRequestDispatcher("/WEB-INF/MovieRevenueStatisticPage.jsp").forward(request, response);

        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Please select a valid time period");
            request.getRequestDispatcher("/WEB-INF/MovieRevenueStatisticPage.jsp").forward(request, response);
        }
    }
}
