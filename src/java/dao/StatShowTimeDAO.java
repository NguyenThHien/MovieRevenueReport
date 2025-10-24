package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.StatShowTime;
import model.ScreeningRoom;
import util.dbConnection;

public class StatShowTimeDAO {

    private Connection con;

    public StatShowTimeDAO() {
          con = dbConnection.getConnection();
    }
    public List<StatShowTime> getStatShowtimesByPage(int movieID, Date startDate, Date endDate, int currentPage, int recordsPerPage) {
        List<StatShowTime> list = new ArrayList<>();
       String sql = """
             WITH 
             -- Bước 1: Lọc ra tất cả các vé hợp lệ (đúng phim, đúng ngày)
             FilteredTickets AS (
                 SELECT 
                     t.id AS ticketID, 
                     t.tblInvoiceid,
                     sts.tblShowTimeid
                 FROM 
                     tblTicket t
                 JOIN 
                     tblShowTimeSeat sts ON t.tblShowTimeSeatid = sts.id
                 JOIN 
                     tblShowTime st ON sts.tblShowTimeid = st.id
                 JOIN 
                     tblInvoice i ON t.tblInvoiceid = i.id
                 WHERE 
                     st.tblMovieid = ? 
                     AND DATE(i.issueDate) BETWEEN ? AND ?
             ),
             
             -- Bước 2: Từ vé, tìm các hóa đơn DUY NHẤT (tránh tính trùng)
             UniqueInvoices AS (
                 SELECT DISTINCT 
                     tblInvoiceid, 
                     tblShowTimeid
                 FROM 
                     FilteredTickets
             ),
             
             -- Bước 3: Tính tổng DOANH THU (từ Hóa đơn) cho mỗi Suất chiếu
             RevenueByShowtime AS (
                 SELECT 
                     ui.tblShowTimeid,
                     SUM(i.totalAmount) AS totalRevenue
                 FROM 
                     UniqueInvoices ui
                 JOIN 
                     tblInvoice i ON ui.tblInvoiceid = i.id
                 GROUP BY 
                     ui.tblShowTimeid
             ),
             
             -- Bước 4: Đếm tổng SỐ LƯỢNG VÉ (từ Vé) cho mỗi Suất chiếu
             TicketsByShowtime AS (
                 SELECT 
                     tblShowTimeid,
                     COUNT(ticketID) AS totalTicket
                 FROM 
                     FilteredTickets
                 GROUP BY 
                     tblShowTimeid
             )
             
             -- Bước 5: Kết hợp tất cả lại
             SELECT 
                 st.id AS showTimeID,
                 st.startTime,
                 st.endTime,
                 st.screeningDate,
                 st.basePrice,
                 s.roomName AS screeningRoomName,
                 r.totalRevenue,
                 t.totalTicket
             FROM 
                 tblShowTime st
             JOIN 
                 tblScreeningRoom s ON st.tblScreeningRoomid = s.id
             -- Join với kết quả doanh thu
             JOIN 
                 RevenueByShowtime r ON st.id = r.tblShowTimeid
             -- Join với kết quả số vé
             JOIN 
                 TicketsByShowtime t ON st.id = t.tblShowTimeid
             
             -- Không cần WHERE hoặc HAVING nữa, vì CTE đã lọc rồi
             
             ORDER BY 
                 totalRevenue DESC, st.id ASC
             LIMIT ? OFFSET ?
             """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, movieID);
            ps.setDate(2, startDate);
            ps.setDate(3, endDate);
            ps.setInt(4, recordsPerPage); 
            ps.setInt(5, (currentPage - 1) * recordsPerPage); 

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                StatShowTime st = new StatShowTime();
                st.setShowTimeID(rs.getInt("showTimeID"));
                st.setStartTime(rs.getTime("startTime"));
                st.setEndTime(rs.getTime("endTime"));
                st.setScreeningDate(rs.getDate("screeningDate"));
                st.setBasePrice(rs.getFloat("basePrice"));
                st.setTotalRevenue(rs.getFloat("totalRevenue"));
                st.setTotalTicket(rs.getInt("totalTicket"));
                
                ScreeningRoom sc = new ScreeningRoom();
                sc.setRoomName(rs.getString("screeningRoomName"));
                st.setSc(sc);

                list.add(st);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getTotalShowtimeCount(int movieID, Date startDate, Date endDate) {
        String sql = """
                     SELECT COUNT(*) FROM (
                         -- Bắt đầu truy vấn con: Tìm các suất chiếu có doanh thu > 0
                         SELECT 
                             st.id
                         FROM 
                             tblShowTime st -- Bắt đầu từ bảng Suất chiếu
                         JOIN (
                             -- Sub-query 2: Lấy các cặp (ID Suất chiếu, ID Hóa đơn) duy nhất
                             SELECT DISTINCT
                                 sts.tblShowTimeid,
                                 t.tblInvoiceid
                             FROM 
                                 tblShowTimeSeat sts
                             JOIN 
                                 tblTicket t ON t.tblShowTimeSeatid = sts.id
                             -- Không cần join tblInvoice ở đây vì chỉ cần ID
                         ) AS ShowTimeInvoices ON st.id = ShowTimeInvoices.tblShowTimeid
                         JOIN 
                             -- Join với bảng hóa đơn BÊN NGOÀI để lấy ngày và tổng tiền
                             tblInvoice i ON i.id = ShowTimeInvoices.tblInvoiceid
                         WHERE 
                             -- Lọc theo ID phim và ngày hóa đơn
                             st.tblMovieid = ? 
                             AND DATE(i.issueDate) BETWEEN ? AND ?
                         GROUP BY 
                             st.id -- Nhóm theo từng suất chiếu
                         HAVING 
                             
                             SUM(i.totalAmount) > 0 
                     ) AS SubQuery
                     """;
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, movieID);
            ps.setDate(2, startDate);
            ps.setDate(3, endDate);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); // Lấy giá trị COUNT(*)
            }
        } catch (SQLException e) {
            System.err.println("Error getting total showtime count: " + e.getMessage());
            e.printStackTrace(); 
        }
        return 0; 
    }

  
}
