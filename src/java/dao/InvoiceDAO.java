package dao;

import java.sql.*;
import java.util.*;
import model.Invoice;
import util.dbConnection;

public class InvoiceDAO {

    private Connection con;

    public InvoiceDAO() {
            con = dbConnection.getConnection();
    }

    // Lấy danh sách hóa đơn theo showTimeID 
    public List<Invoice> getInvoice(int showTimeID) {
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
            ORDER BY totalAmount DESC
        """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, showTimeID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setInvoiceID(rs.getString("invoiceID"));
                invoice.setIssueDate(rs.getDate("issueDate"));
                invoice.setIssueTime(rs.getTime("issueTime"));
                invoice.setPaymentMethod(rs.getString("paymentMethod"));
                invoice.setTotalAmount(rs.getFloat("totalAmount"));
                invoices.add(invoice);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

      
        return invoices;
    }
}
