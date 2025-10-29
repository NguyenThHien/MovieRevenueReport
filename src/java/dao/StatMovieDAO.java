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
   
    public List<StatMovie> getMoviesByPage(Date startDate, Date endDate, int currentPage, int recordsPerPage) {
        List<StatMovie> list = new ArrayList<>();
        String sql = """
             SELECT 
                 m.id AS movieID,
                 m.title,
                 m.releaseDate,
                 SUM(CASE WHEN DATE(i.issueDate) BETWEEN ? AND ? THEN i.totalAmount ELSE 0 END) AS totalRevenue
                     
             FROM 
                 tblMovie m
             JOIN (
                 -- Bắt đầu truy vấn con: Lấy các cặp (ID Phim, ID Hóa đơn) duy nhất
                 SELECT DISTINCT
                     st.tblMovieid,
                     t.tblInvoiceid
                 FROM 
                     tblShowTime st
                 JOIN 
                     tblShowTimeSeat sts ON sts.tblShowTimeid = st.id
                 JOIN 
                     tblTicket t ON t.tblShowTimeSeatid = sts.id
                 -- Chỉ lấy những vé có hóa đơn (tức là đã 'booked' và thanh toán)
             ) AS MovieInvoices ON m.id = MovieInvoices.tblMovieid
             JOIN 
                 -- Join với bảng hóa đơn BÊN NGOÀI truy vấn con
                 tblInvoice i ON i.id = MovieInvoices.tblInvoiceid
             GROUP BY 
                 m.id, m.title
             HAVING 
                 totalRevenue > 0
             ORDER BY 
                 m.releaseDate DESC, m.id ASC
             LIMIT ? OFFSET ? 
             """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, startDate);
            ps.setDate(2, endDate);
            ps.setInt(3, recordsPerPage); 
            ps.setInt(4, (currentPage - 1) * recordsPerPage); 

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                StatMovie sm = new StatMovie();
                sm.setMovieID(rs.getInt("movieID"));
                sm.setTitle(rs.getString("title"));
                sm.setTotalRevenue(rs.getFloat("totalRevenue"));
                sm.setReleaseDate(rs.getDate("releaseDate"));
                list.add(sm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getTotalMovieCount(Date startDate, Date endDate) {
        
       String sql = """
             SELECT COUNT(*) FROM (
                 -- Bắt đầu truy vấn con: Tìm các phim có doanh thu
                 SELECT 
                     m.id
                 FROM 
                     tblMovie m
                 JOIN (
                     -- Sub-query 2: Lấy các cặp (ID Phim, ID Hóa đơn) duy nhất
                     SELECT DISTINCT
                         st.tblMovieid,
                         t.tblInvoiceid
                     FROM 
                         tblShowTime st
                     JOIN 
                         tblShowTimeSeat sts ON sts.tblShowTimeid = st.id
                     JOIN 
                         tblTicket t ON t.tblShowTimeSeatid = sts.id
                 ) AS MovieInvoices ON m.id = MovieInvoices.tblMovieid
                 JOIN 
                     tblInvoice i ON i.id = MovieInvoices.tblInvoiceid
                 WHERE 
                     -- Lọc hóa đơn theo ngày
                     DATE(i.issueDate) BETWEEN ? AND ?
                 GROUP BY 
                     m.id
                 HAVING 
                     -- Sửa ở đây: Tính tổng doanh thu bằng totalAmount của hóa đơn
                     SUM(i.totalAmount) > 0 
             ) AS SubQuery
             """;
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, startDate);
            ps.setDate(2, endDate);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getGrandTotalRevenue(Date startDate, Date endDate) {
        String sql = """
             SELECT SUM(i.totalAmount) 
             FROM tblInvoice i
             WHERE DATE(i.issueDate) BETWEEN ? AND ?
             """;
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, startDate);
            ps.setDate(2, endDate);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
