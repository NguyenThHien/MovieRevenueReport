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

    private static final String SQL_CHECK =
        "SELECT COUNT(*) FROM tblmember WHERE username = ? OR email = ? OR phoneNumber = ?";
    private static final String SQL_INSERT_MEMBER =
        "INSERT INTO tblmember (fullname, username, password, email, phoneNumber, role, dateOfBirth, address, gender) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_INSERT_CUSTOMER =
        "INSERT INTO tblcustomer (tblMemberId) VALUES (?)";

   
    public CustomerDAO() {
        this.con = dbConnection.getConnection();
    }

    
    public boolean register(Customer customer) {
        if (con == null) {
            System.out.println("Connection is null!");
            return false;
        }

        try {
            con.setAutoCommit(false);
            try (PreparedStatement psCheck = con.prepareStatement(SQL_CHECK)) {
                psCheck.setString(1, customer.getUsername());
                psCheck.setString(2, customer.getEmail());
                psCheck.setString(3, customer.getPhoneNumber());
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        return false;
                    }
                }
            }

            int memberID = -1;
            try (PreparedStatement psMember =
                     con.prepareStatement(SQL_INSERT_MEMBER, PreparedStatement.RETURN_GENERATED_KEYS)) {

                psMember.setString(1, customer.getFullName());
                psMember.setString(2, customer.getUsername());
                psMember.setString(3, BCrypt.hashpw(customer.getPassword(), BCrypt.gensalt(12)));
                psMember.setString(4, customer.getEmail());
                psMember.setString(5, customer.getPhoneNumber());
                psMember.setString(6, "customer");
                psMember.setDate(7, new java.sql.Date(customer.getDateOfBirth().getTime()));
                psMember.setString(8, customer.getAddress());
                psMember.setString(9, customer.getGender());

                if (psMember.executeUpdate() == 0) {
                    con.rollback();
                    return false;
                }

                try (ResultSet rs = psMember.getGeneratedKeys()) {
                    if (rs.next()) {
                        memberID = rs.getInt(1);
                    } else {
                        con.rollback();
                        return false;
                    }
                }
            }

           //Thêm vào tblcustomer
            try (PreparedStatement psCustomer = con.prepareStatement(SQL_INSERT_CUSTOMER)) {
                psCustomer.setInt(1, memberID);
                if (psCustomer.executeUpdate() == 0) {
                    con.rollback();
                    return false;
                }
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                con.setAutoCommit(true);
                con.close(); 
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
