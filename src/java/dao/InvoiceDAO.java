package dao;

import java.sql.*;
import java.util.*;
import model.Invoice; // Đảm bảo bạn đã import đúng model Invoice
import util.dbConnection; // Đảm bảo bạn có class dbConnection

public class InvoiceDAO {

    private Connection con;

    public InvoiceDAO() {
        this.con = dbConnection.getConnection();
    }

    /**
     * Lấy danh sách hóa đơn theo trang cho một suất chiếu cụ thể.
     * @param showTimeID ID của suất chiếu
     * @param currentPage Trang hiện tại (bắt đầu từ 1)
     * @param recordsPerPage Số bản ghi mỗi trang
     * @return Danh sách hóa đơn
     */
    public List<Invoice> getInvoiceByPage(int showTimeID, int currentPage, int recordsPerPage) {
        List<Invoice> invoices = new ArrayList<>();
        // Sửa SQL: Lấy totalAmount trực tiếp, dùng subquery DISTINCT để tránh trùng lặp
        String sql = """
                     SELECT 
                         i.id AS invoiceID, 
                         i.issueDate, 
                         i.paymentMethod,
                         i.issueTime,
                         i.totalAmount -- Lấy trực tiếp totalAmount từ hóa đơn
                     FROM 
                         tblInvoice i
                     JOIN (
                         -- Truy vấn con: Lấy các ID hóa đơn DUY NHẤT liên quan đến suất chiếu
                         SELECT DISTINCT t.tblInvoiceid 
                         FROM tblTicket t
                         JOIN tblShowTimeSeat sts ON t.tblShowTimeSeatid = sts.id
                         WHERE sts.tblShowTimeid = ? 
                     ) AS RelevantInvoices ON i.id = RelevantInvoices.tblInvoiceid
                     ORDER BY 
                         i.totalAmount DESC, i.id ASC -- Sắp xếp theo totalAmount thực tế
                     LIMIT ? OFFSET ?
                     """;

        if (con == null) {
            System.err.println("Database connection is not available.");
            return invoices; 
        }

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            int offset = (currentPage - 1) * recordsPerPage;
            if (offset < 0) {
                offset = 0;
            }

            ps.setInt(1, showTimeID);
            ps.setInt(2, recordsPerPage);
            ps.setInt(3, offset); 

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

    /**
     * Đếm tổng số hóa đơn duy nhất cho một suất chiếu cụ thể.
     * @param showTimeID ID của suất chiếu
     * @return Tổng số hóa đơn
     */
    public int getTotalInvoiceCount(int showTimeID) {
        String sql = """
                     SELECT COUNT(*) FROM (
                         SELECT DISTINCT i.id -- Dùng DISTINCT ở đây cũng được
                         FROM tblInvoice i
                         JOIN tblTicket t ON t.tblInvoiceid = i.id
                         JOIN tblShowTimeSeat sts ON t.tblShowTimeSeatid = sts.id
                         WHERE sts.tblShowTimeid = ?
                         -- GROUP BY i.id -- Không cần GROUP BY khi dùng DISTINCT i.id
                     ) AS SubQuery
                     """;

        if (con == null) {
            System.err.println("Database connection is not available.");
            return 0;
        }
                     
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, showTimeID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); 
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0; 
    }

}