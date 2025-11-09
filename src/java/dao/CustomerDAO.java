package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Customer;
import org.mindrot.jbcrypt.BCrypt;
import util.dbConnection;

public class CustomerDAO {

    private Connection con;

    public CustomerDAO() {

        this.con = dbConnection.getConnection();
    }

    public String register(Customer customer) {
        String SQL_CHECK_USER = "SELECT 1 FROM tblmember WHERE username = ?";
        String SQL_CHECK_EMAIL = "SELECT 1 FROM tblmember WHERE email = ?";
        String SQL_CHECK_PHONE = "SELECT 1 FROM tblmember WHERE phoneNumber = ?";

        String SQL_INSERT_MEMBER
                = "INSERT INTO tblmember (fullname, username, password, email, phoneNumber, role, dateOfBirth, address, gender) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String SQL_INSERT_CUSTOMER
                = "INSERT INTO tblcustomer (tblMemberId) VALUES (?)";

        try {
            if (con == null) {
                System.out.println("Connection is null!");
                return "ERROR";
            }

            con.setAutoCommit(false);

            // 1. Kiểm tra Username
            try (PreparedStatement psCheck = con.prepareStatement(SQL_CHECK_USER)) {
                psCheck.setString(1, customer.getUsername());
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next()) {
                        con.rollback();
                        return "USER_EXISTS";
                    }
                }
            }

           
            if (customer.getEmail() != null) {
                try (PreparedStatement psCheck = con.prepareStatement(SQL_CHECK_EMAIL)) {
                    psCheck.setString(1, customer.getEmail());
                    try (ResultSet rs = psCheck.executeQuery()) {
                        if (rs.next()) {
                            con.rollback();
                            return "EMAIL_EXISTS";
                        }
                    }
                }
            }

            // 3. Kiểm tra Phone 
            if (customer.getPhoneNumber() != null) {
                try (PreparedStatement psCheck = con.prepareStatement(SQL_CHECK_PHONE)) {
                    psCheck.setString(1, customer.getPhoneNumber());
                    try (ResultSet rs = psCheck.executeQuery()) {
                        if (rs.next()) {
                            con.rollback();
                            return "PHONE_EXISTS";
                        }
                    }
                }
            }

            // 4. INSERT vào tblmember
            int memberID = -1;
            try (PreparedStatement psMember
                    = con.prepareStatement(SQL_INSERT_MEMBER, PreparedStatement.RETURN_GENERATED_KEYS)) {

                psMember.setString(1, customer.getFullName());
                psMember.setString(2, customer.getUsername());
                psMember.setString(3, BCrypt.hashpw(customer.getPassword(), BCrypt.gensalt(12)));
                psMember.setString(4, customer.getEmail());
                psMember.setString(5, customer.getPhoneNumber());
                psMember.setString(6, "customer");
                if (customer.getDateOfBirth() != null) {
                    psMember.setDate(7, new java.sql.Date(customer.getDateOfBirth().getTime()));
                } else {
                    // Nếu null, chèn SQL NULL
                    psMember.setNull(7, java.sql.Types.DATE);
                }
                psMember.setString(8, customer.getAddress());
                psMember.setString(9, customer.getGender());

                if (psMember.executeUpdate() == 0) {
                    con.rollback();
                    return "ERROR";
                }

                try (ResultSet rs = psMember.getGeneratedKeys()) {
                    if (rs.next()) {
                        memberID = rs.getInt(1);
                    } else {
                        con.rollback();
                        return "ERROR";
                    }
                }
            }

            // 5. Thêm vào tblcustomer
            try (PreparedStatement psCustomer = con.prepareStatement(SQL_INSERT_CUSTOMER)) {
                psCustomer.setInt(1, memberID);
                if (psCustomer.executeUpdate() == 0) {
                    con.rollback();
                    return "ERROR";
                }
            }

            con.commit();
            return "SUCCESS";

        } catch (SQLException e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return "ERROR";
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
