package dao;

import java.sql.*;
import java.util.*;
import model.*;
import util.dbConnection;

public class InvoiceDAO {

    // Hàm lấy danh sách hóa đơn theo showTimeID, sắp xếp theo doanh thu giảm dần
    public List<Invoice> getInvoice(int showTimeId) {
        List<Invoice> invoices = new ArrayList<>();

        String sql = """
            SELECT 
                i.id AS invoiceID, 
                i.issueDate, 
                i.paymentMethod,
                i.issueTime,
                COALESCE(SUM(t.priceFinal), 0) AS totalAmount
            FROM tblInvoice i
            JOIN tblTicket t ON t.tblInvoiceid = i.id
            JOIN tblShowTimeSeat sts ON t.tblShowTimeSeatid = sts.id
            WHERE sts.tblShowTimeid = ?
            GROUP BY i.id, i.issueDate, i.paymentMethod, i.issueTime
            ORDER BY totalAmount DESC;
        """;

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, showTimeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setInvoiceID(rs.getString("invoiceID"));
                invoice.setTotalAmount(rs.getFloat("totalAmount"));
                invoice.setIssueDate(rs.getDate("issueDate"));
                invoice.setPaymentMethod(rs.getString("paymentMethod"));
                invoice.setIssueTime(rs.getTime("issueTime"));
                invoices.add(invoice);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return invoices;
    }
}
