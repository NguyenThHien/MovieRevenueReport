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

    // Lấy toàn bộ danh sách suất chiếu trong khoảng thời gian hóa đơn
    public List<StatShowTime> getAllStatShowtime(int movieID, Date startDate, Date endDate) {
        List<StatShowTime> list = new ArrayList<>();

        String sql = """
            SELECT 
                st.id AS showTimeID,
                st.startTime,
                st.endTime,
                st.screeningDate,
                st.basePrice,
                s.roomName AS screeningRoomName,
                SUM(t.priceFinal) AS totalRevenue,
                COUNT(t.id) AS totalTicket
            FROM tblShowTime st
            JOIN tblScreeningRoom s ON st.tblScreeningRoomid = s.id
            JOIN tblShowTimeSeat sts ON sts.tblShowTimeid = st.id
            JOIN tblTicket t ON t.tblShowTimeSeatid = sts.id
            JOIN tblInvoice i ON t.tblInvoiceid = i.id
            WHERE st.tblMovieid = ? 
              AND DATE(i.issueDate) BETWEEN ? AND ?
            GROUP BY st.id, st.startTime, st.endTime, st.screeningDate, st.basePrice, s.roomName
            HAVING SUM(t.priceFinal) > 0
            ORDER BY totalRevenue DESC, st.id ASC
        """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, movieID);
            ps.setDate(2, startDate);
            ps.setDate(3, endDate);

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
                // gọi class room
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
}
