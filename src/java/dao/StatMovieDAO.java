package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.StatMovie;
import util.dbConnection;

public class StatMovieDAO {

    
    private Connection con;

    public StatMovieDAO() {
        con = dbConnection.getConnection();
    }

    // Lấy toàn bộ danh sách StatMovie trong khoảng thời gian theo hóa đơn
    public List<StatMovie> getAllStatMovie(Date startDate, Date endDate) {
        List<StatMovie> list = new ArrayList<>();

        String sql = """
            SELECT 
                m.id AS movieID,
                m.title,
                SUM(CASE WHEN DATE(i.issueDate) BETWEEN ? AND ? THEN t.priceFinal ELSE 0 END) AS totalRevenue
            FROM tblMovie m
            JOIN tblShowTime st ON st.tblMovieid = m.id
            JOIN tblShowTimeSeat sts ON sts.tblShowTimeid = st.id
            JOIN tblTicket t ON t.tblShowTimeSeatid = sts.id
            JOIN tblInvoice i ON t.tblInvoiceid = i.id
            GROUP BY m.id, m.title
            HAVING totalRevenue > 0
            ORDER BY totalRevenue DESC, m.id ASC
        """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, startDate);
            ps.setDate(2, endDate);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                StatMovie sm = new StatMovie();
                sm.setMovieID(rs.getInt("movieID"));
                sm.setTitle(rs.getString("title"));
                sm.setTotalRevenue(rs.getFloat("totalRevenue"));
                list.add(sm);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        
        return list;
    }
}
