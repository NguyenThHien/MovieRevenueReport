package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Customer;
import util.dbConnection;

public class CustomerDAO {

    public boolean register(Customer customer) {
        String sqlCheck = "SELECT COUNT(*) FROM tblmember WHERE username = ? OR email = ? OR phoneNumber = ?";
        String sqlInsertMember = "INSERT INTO tblmember (fullname, username, password, email, phoneNumber, role, dateOfBirth, address, gender) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlInsertCustomer = "INSERT INTO tblcustomer (tblMemberId) VALUES (?)";

        Connection con = null;
        PreparedStatement psCheck = null;
        PreparedStatement psMember = null;
        PreparedStatement psCustomer = null;
        ResultSet rs = null;

        try {
            con = dbConnection.getConnection();
            con.setAutoCommit(false); 

            // 1. Kiểm tra trùng username/email/phoneNumber
            psCheck = con.prepareStatement(sqlCheck);
            psCheck.setString(1, customer.getUsername());
            psCheck.setString(2, customer.getEmail());
            psCheck.setString(3, customer.getPhoneNumber());
            rs = psCheck.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false;
            }

            // 2. Thêm vào tblmember
            psMember = con.prepareStatement(sqlInsertMember, PreparedStatement.RETURN_GENERATED_KEYS);
            psMember.setString(1, customer.getFullName());
            psMember.setString(2, customer.getUsername());
            psMember.setString(3, customer.getPassword());
            psMember.setString(4, customer.getEmail());
            psMember.setString(5, customer.getPhoneNumber());
            psMember.setString(6, "customer");
            psMember.setDate(7, new java.sql.Date(customer.getDateOfBirth().getTime()));
            psMember.setString(8, customer.getAddress());
            psMember.setString(9, customer.getGender());

            int affectedRows = psMember.executeUpdate();
            if (affectedRows == 0) {
                con.rollback();
                throw new SQLException("Insert Member failed, no rows affected.");
            }

           
            rs = psMember.getGeneratedKeys();
            int memberID = -1;
            if (rs.next()) {
                memberID = rs.getInt(1);
            } else {
                con.rollback();
                throw new SQLException("Insert Member failed, no ID obtained.");
            }

            // 3. Thêm vào tblCustomer
            psCustomer = con.prepareStatement(sqlInsertCustomer);
            psCustomer.setInt(1, memberID);
            int rowsCustomer = psCustomer.executeUpdate();
            if (rowsCustomer == 0) {
                con.rollback();
                throw new SQLException("Insert Customer failed.");
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (psCheck != null) psCheck.close();
                if (psMember != null) psMember.close();
                if (psCustomer != null) psCustomer.close();
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
