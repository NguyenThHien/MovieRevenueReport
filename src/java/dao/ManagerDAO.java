package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import model.Manager;
import util.dbConnection;

public class ManagerDAO {

    // Hàm login: kiểm tra username/password + role = 'manager'
    public Manager login(String username, String password) {
        String sql = """
            SELECT m.id AS memberID, m.fullname, m.username, m.password, m.email,
                   m.phoneNumber, m.role, m.dateOfBirth, m.address, m.gender
            FROM tblmember m
            JOIN tblManager mg ON m.id = mg.tblMemberId
            WHERE m.username = ? AND m.password = ? AND m.role = 'manager'
            """;

        try (
            Connection con = dbConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
        ) {
            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int memberID = rs.getInt("memberID");
                    String fullName = rs.getString("fullname");
                    String user = rs.getString("username");
                    String pass = rs.getString("password");
                    String email = rs.getString("email");
                    String phone = rs.getString("phoneNumber");
                    String role = rs.getString("role");
                    Date dob = rs.getDate("dateOfBirth");
                    String address = rs.getString("address");
                    String gender = rs.getString("gender");
                    
                    return new Manager(memberID, fullName, user, pass, email, phone, role, dob, address, gender);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; 
    }
}
